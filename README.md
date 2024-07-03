# 动态金融数据采集与展示系统 📊💹

## 📌 项目概述

本项目是一个基于Spring Boot的动态金融数据采集与展示系统。它利用Selenium WebDriver实时抓取多个金融市场的关键数据，包括黄金价格、美元兑人民币汇率、新加坡元兑人民币汇率以及NVIDIA（英伟达）股票信息。系统通过RESTful API将这些实时数据提供给前端应用，实现了金融数据的自动化采集、存储和展示。

## 🚀 核心特性

- **🕷️ 实时数据抓取**：利用Selenium WebDriver从多个金融网站动态抓取最新数据。
- **⏰ 定时任务**：使用Spring的`@Scheduled`注解实现定期数据更新。
- **💾 数据持久化**：采用Spring Data JPA将抓取的数据存储到MySQL数据库。
- **🌐 RESTful API**：提供多个端点，供前端应用获取最新的金融数据。
- **🔄 跨域资源共享（CORS）**：配置CORS以支持前后端分离架构。
- **🌟 分布式抓取**：使用Selenium Grid支持分布式的数据抓取任务。
- **🐳 Docker集成**：Chrome浏览器已部署到Docker，简化了环境配置和部署流程。

## 🛠️ 技术栈

- 后端框架：Spring Boot 2
- ORM框架：Spring Data JPA
- 数据库：MySQL 8.0
- Web自动化：Selenium WebDriver 4
- 分布式测试：Selenium Grid
- 构建工具：Maven
- 版本控制：Git
- 容器化：Docker (用于Chrome浏览器)

## 📋 详细功能描述

### 1. 数据抓取服务 🕷️

项目核心是`SeleniumUtils`类，它封装了所有的数据抓取逻辑：

- 黄金价格抓取：从东方财富网实时获取国际金价。
- 美元兑人民币汇率抓取：获取最新的USD/CNH汇率数据。
- 新加坡元兑人民币汇率抓取：抓取SGD/CNY的实时汇率信息。
- NVIDIA股票数据：预留了获取NVIDIA股票数据的接口。

所有抓取任务都通过Selenium WebDriver在Docker容器中的Chrome浏览器中执行，确保了环境的一致性和高效的数据采集过程。

### 2. 数据模型 📊

系统定义了多个实体类来映射不同类型的金融数据：

- `GoldData`：存储黄金价格和涨跌幅。
- `UsdchnData`：记录美元兑人民币汇率信息。
- `SgdcnycData`：保存新加坡元兑人民币汇率数据。
- `NvdaStock`：用于存储NVIDIA的股票信息。

这些实体类通过JPA注解与数据库表进行映射，实现了对象关系映射（ORM）。

### 3. 数据访问层 💾

每种数据类型都有对应的Repository接口，继承自Spring Data JPA的`JpaRepository`：

- `GoldDataRepository`
- `UsdchnDataRepository`
- `SgdcnycDataRepository`
- `NvdaStockRepository`

这些接口不仅提供了基本的CRUD操作，还定义了一些自定义查询方法，如获取最新的N条数据记录。

### 4. 业务逻辑层 🔧

服务层包含了各种Service类，封装了具体的业务逻辑：

- `GoldDataService`
- `UsdchnDataService`
- `SgdcnycDataService`
- `NvdaStockService`

这些服务类负责协调数据的抓取、存储和检索过程，是连接控制器和数据访问层的桥梁。

### 5. Web层 🌐

`DataController`类作为系统的RESTful API入口，提供了多个端点：

- `/api/latest-gold-data`：获取最新的黄金价格数据。
- `/api/latest-usdchn-data`：检索最新的美元兑人民币汇率。
- `/api/latest-sgdcnyc-data`：返回最新的新加坡元兑人民币汇率。
- `/api/nvda-stocks`：获取NVIDIA的股票数据。

控制器还实现了一个定时任务，每30秒自动触发一次数据抓取流程。

### 6. 跨域配置 🔄

`CorsConfig`类通过实现`WebMvcConfigurer`接口，为系统配置了跨域资源共享（CORS）策略，使前端应用能够顺利访问后端API。

### 7. 异常处理 🛡️

系统实现了全局异常处理机制，通过`@ControllerAdvice`注解的类捕获和处理各种异常，确保了API响应的一致性和系统的健壮性。

### 8. 配置管理 ⚙️

项目使用Spring Boot的配置文件`application.properties`管理各种系统设置，包括数据库连接、JPA配置、Selenium相关配置等。

## 🚀 部署与运行

1. 确保安装了Docker、Java 8+和MySQL 8.0。
2. 克隆项目代码到本地。
3. 在MySQL中创建名为`forex_data`的数据库。
4. 更新`application.properties`文件中的数据库连接信息。
5. 启动Docker中的Chrome容器： `docker run -d -p 4444:4444 selenium/standalone-chrome:latest`
6. 使用Maven构建项目： `mvn clean install`
7. 运行Spring Boot应用： `java -jar target/my-stock-0.0.1-SNAPSHOT.jar`

## 📈 使用示例

以下是如何使用系统API的简单示例：

1. 获取最新的黄金价格数据： `GET http://localhost:8080/api/latest-gold-data`

2. 获取最新的美元兑人民币汇率： `GET http://localhost:8080/api/latest-usdchn-data`

3. 获取最新的新加坡元兑人民币汇率： `GET http://localhost:8080/api/latest-sgdcnyc-data`

4. 获取NVIDIA的股票数据： `GET http://localhost:8080/api/nvda-stocks`

## 🛠️ 配置

主要配置项位于`application.properties`文件中，包括：

- 数据库连接信息
- JPA配置
- Selenium WebDriver配置
- 定时任务配置

请根据实际环境修改这些配置。

## 🤝 贡献

欢迎贡献代码、报告问题或提出新功能建议。请遵循以下步骤：

1. Fork 本仓库
2. 创建你的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交你的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启一个 Pull Request

## 📄 许可证

本项目采用 MIT 许可证。详情请参见 [LICENSE](LICENSE) 文件。

## 📞 联系方式

项目维护者：张力匀 - 743478304@qq.com

项目链接：[https://github.com/LiyunZhang10/my-stock-backend-spring-boot](https://github.com/LiyunZhang10/my-stock-backend-spring-boot)

## 🙏 致谢

- 特别感谢 [Selenium](https://www.selenium.dev/) 和 [Spring Boot](https://spring.io/projects/spring-boot) 项目
