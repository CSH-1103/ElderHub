# ElderHub 项目依赖版本文档
> 项目：东软颐养平台 ElderHub
> 维护人：LYS

## 1. 基础环境依赖
| 软件 | 版本 | 用途 |
| ---- | ---- | ---- |
| docker-desktop | 4.27.0 | 容器部署环境 |
| JDK | 17 | Java后端运行环境 |
| MySQL | 8.0.36 | 数据库 |
| Node.js | 20.11.0 | Vue前端运行环境 |

## 2. 后端Maven依赖
| 依赖包 | 版本 |
| ---- | ---- |
| spring-boot-starter | 3.2.0 | Web框架 |
| mybatis-plus-boot-starter | 3.5.5 | ORM持久层框架 |
| mysql-connector-java | 8.0.33 | MySQL驱动 |
| lombok | 1.18.30 | 简化实体类代码 |

## 3. 前端npm依赖
| 包名 | 版本 |
| ---- | ---- |
| vue | 3.4.15 | 前端框架 |
| vite | 5.1.0 | 构建工具 |
| element-plus | 2.5.3 | UI组件库 |
| axios | 1.6.7 | 网络请求 |

### 说明
1. 全部依赖版本互相兼容，可在docker容器内统一部署。
2. 数据库字段设计参考`yyzx数据字典.xlsx`。
