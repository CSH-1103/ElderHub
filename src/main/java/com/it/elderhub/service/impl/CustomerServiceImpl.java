package com.it.elderhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.elderhub.entity.Bed;
import com.it.elderhub.entity.BedDetails;
import com.it.elderhub.entity.Customer;
import com.it.elderhub.mapper.BedDetailsMapper;
import com.it.elderhub.mapper.BedMapper;
import com.it.elderhub.service.CustomerService;
import com.it.elderhub.mapper.CustomerMapper;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
* @author Ljz
* @description 针对表【customer(在住客户/老人表)】的数据库操作Service实现
* @createDate 2026-10-08 09:52:05
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

    private static final int FREE = 1;
    private static final int OCCUPIED = 2;
    private static final int OUT = 3;

    private final BedMapper bedMapper;
    private final BedDetailsServiceImpl bedDetailsServiceImpl;
    private final BedDetailsMapper bedDetailsMapper;

    public CustomerServiceImpl(BedMapper bedMapper, BedDetailsServiceImpl bedDetailsServiceImpl, BedDetailsMapper bedDetailsMapper) {
        this.bedMapper = bedMapper;
        this.bedDetailsServiceImpl = bedDetailsServiceImpl;
        this.bedDetailsMapper = bedDetailsMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事务保证数据一致性
    public void saveCustomer(Customer customer) {
        //0.必填校验
        if (customer.getCustomer_name() == null || customer.getCustomer_name().isBlank()) {
            throw new RuntimeException("客户姓名不能为空");
        }
        if (customer.getIdcard() == null || customer.getIdcard().isBlank()) {
            throw new RuntimeException("身份证号不能为空");
        }
        customer.setIdcard(customer.getIdcard().trim());
        // 身份证号唯一（仅与未删除记录比较）
        Long idcardCount = this.count(new QueryWrapper<Customer>()
                .eq("idcard", customer.getIdcard())
                .eq("is_deleted", 0));
        if (idcardCount > 0) {
            throw new RuntimeException("身份证号已存在");
        }
        if (customer.getCheckin_date() == null || customer.getExpiration_date() == null) {
            throw new RuntimeException("入住日期和合同到期日期不能为空");
        }
        //1.校验
        if(customer.getExpiration_date().before(customer.getCheckin_date())){
            throw new RuntimeException("合同到期日期不能早于入住日期");
        }
        //2.根据出生日期自动计算年龄
        if (customer.getBirthday() != null) {
            int age = Period.between(customer.getBirthday(), LocalDate.now()).getYears();
            customer.setCustomer_age(age);
        }
        //3.校验床位状态
        Bed selectedBed = bedMapper.selectById(customer.getBed_id());
        if(selectedBed == null||selectedBed.getBed_status()!=FREE){
            throw new RuntimeException();
        }
        //4.插入customer
        this.save(customer);
        //5.更新床状态为OCCUPIED
        selectedBed.setBed_status(OCCUPIED);//OCCUPIED
        bedMapper.updateById(selectedBed);
        //6.插入bed_details(记录入住历史)
        BedDetails details = new BedDetails();
        details.setCustomer_id(customer.getId());
        details.setBed_id(customer.getBed_id());
        details.setStart_date(customer.getCheckin_date());
        // 退住时间=合同到期日（需求规约：床位详情的退住时间即合同到期时间）
        details.setEnd_date(customer.getExpiration_date());
        bedDetailsMapper.insert(details);
    }

    /**
     * 修改客户
     * @param customer
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(Customer customer) {
        //1.查出修改前的旧客户信息
        Customer oldcustomer = this.getById(customer.getId());
        if(oldcustomer==null){
            throw new RuntimeException("客户不存在");
        }
        //身份证号唯一性校验（排除自身）
        if (customer.getIdcard() != null && !customer.getIdcard().isBlank()) {
            customer.setIdcard(customer.getIdcard().trim());
            Long idcardCount = this.count(new QueryWrapper<Customer>()
                    .eq("idcard", customer.getIdcard())
                    .eq("is_deleted", 0)
                    .ne("id", customer.getId()));
            if (idcardCount > 0) {
                throw new RuntimeException("身份证号已存在");
            }
        }
        //2.校验日期
        if(customer.getExpiration_date()!=null&&customer.getCheckin_date()!=null){
            if(customer.getExpiration_date().before(customer.getCheckin_date())){
                throw new RuntimeException("到期日期不能早于入住日期");
            }
        }
        //3.出生日期变更时重算年龄
        if (customer.getBirthday() != null) {
            int age = Period.between(customer.getBirthday(), LocalDate.now()).getYears();
            customer.setCustomer_age(age);
        }
        //4.判断到期日期是否变更了
        boolean datechange = !oldcustomer.getExpiration_date().equals(customer.getExpiration_date());
        if(datechange && oldcustomer.getCheckin_date()!=null){
            BedDetails details = bedDetailsMapper.selectOne(//去数据库的bed_details表里查 当前生效的那条记录
                    new QueryWrapper<BedDetails>()
                            .eq("customer_id",customer.getId())//条件1-eq(等价)
                            .eq("is_deleted", 0)//条件2-当前生效（未退住、未调床）
                            .orderByDesc("id")//条件3-取最新一条
                            .last("LIMIT 1")
            );
            if(details!=null){
                //更新日期
                details.setEnd_date(customer.getExpiration_date());
                bedDetailsMapper.updateById(details);
            }
        }
        //4.更新客户信息
        this.updateById(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCustomer(Integer id) {
        Customer customer = this.getById(id);
        if(customer==null){
            throw new RuntimeException("客户不存在");
        }
        //逻辑删除客户（手动置 is_deleted=1，保留历史数据）
        customer.setIs_deleted(1);
        this.updateById(customer);
        //还原床位状态
        if(customer.getBed_id()!=null){
            Bed bed = bedMapper.selectById(customer.getBed_id());
            if(bed!=null){
                bed.setBed_status(FREE);
                bedMapper.updateById(bed);
            }
        }
        //逻辑删除当前生效的入住记录（保留使用历史）
        BedDetails details = bedDetailsMapper.selectOne(
                new QueryWrapper<BedDetails>()
                        .eq("customer_id",id)
                        .eq("is_deleted", 0)
                        .last("LIMIT 1")
        );
        if(details!=null){
            details.setEnd_date(new java.util.Date());
            details.setIs_deleted(1);
            bedDetailsMapper.updateById(details);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void goOut(Integer id) {
        Customer customer = this.getById(id);
        if (customer == null) {
            throw new RuntimeException("客户不存在");
        }
        if (customer.getBed_id() == null) {
            throw new RuntimeException("该客户未分配床位");
        }

        Bed bed = bedMapper.selectById(customer.getBed_id());
        if (bed == null) {
            throw new RuntimeException("床位不存在");
        }
        if (bed.getBed_status() != OCCUPIED) {
            throw new RuntimeException("只有已入住状态的老人才能外出");
        }

        // 床位状态改为"外出"
        bed.setBed_status(OUT);
        bedMapper.updateById(bed);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void comeback(Integer id) {
        Customer customer = this.getById(id);
        if (customer == null) {
            throw new RuntimeException("客户不存在");
        }
        if (customer.getBed_id() == null) {
            throw new RuntimeException("该客户未分配床位");
        }

        Bed bed = bedMapper.selectById(customer.getBed_id());
        if (bed == null) {
            throw new RuntimeException("床位不存在");
        }
        if (bed.getBed_status() != OUT) {
            throw new RuntimeException("该老人当前不是外出状态");
        }

        // 床位状态恢复为"已入住"
        bed.setBed_status(OCCUPIED);
        bedMapper.updateById(bed);
    }

    @Override
    public List<Customer> listNoNurse() {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.isNull(Customer::getBed_id) // 未分配床位
                .eq(Customer::getIs_deleted, 0) // 排除已退住/删除客户
                .orderByDesc(Customer::getCheckin_date); // 按入住时间倒序

        return this.list(queryWrapper);
    }

}