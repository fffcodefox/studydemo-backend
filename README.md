# studydemo-server

`studydemo` 前端（Vite + Vue3）的**后端试水项目**：一个 HTTP 接口打通前后端分离，使用 MySQL + Redis，代码严格遵循 **P3C（阿里巴巴 Java 开发手册）** 规范。

技术栈：Spring Boot 2.7.18 + Java 11 + MyBatis-Plus 3.5.3.1 + Spring Data Redis（Lettuce）。

## 目录结构

```
studydemo-backend/
├─ pom.xml
├─ src/main/
│  ├─ java/com/studydemo/server/
│  │  ├─ StudyDemoServerApplication.java   # 启动类
│  │  ├─ config/CorsConfig.java            # 跨域：放行前端 5173
│  │  ├─ constant/RedisKeyConstants.java    # Redis 键常量（私有构造）
│  │  ├─ common/Result.java / ResultCode.java  # 统一返回 + 状态码常量
│  │  ├─ dto/HelloVO.java                  # 接口返回 VO
│  │  ├─ domain/DemoMessage.java           # 实体（t_demo_message）
│  │  ├─ mapper/DemoMessageMapper.java      # MyBatis-Plus Mapper
│  │  ├─ service/HelloService.java          # 业务接口
│  │  ├─ service/impl/HelloServiceImpl.java # 业务实现（Redis 计数 + MySQL 落库）
│  │  └─ controller/HelloController.java     # GET /api/hello
│  └─ resources/
│     ├─ application.yaml     # 通用配置（激活 dev）
│     ├─ application-dev.yaml # 开发：MySQL + Redis（给定地址）+ 自动建表
│     ├─ application-prod.yaml# 生产：连接池/SSL/日志调成生产向
│     └─ schema.sql          # 幂等建表
└─ .gitignore
```

## 那个接口

`GET /api/hello?name=张三`

返回统一结构：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "name": "张三",
    "message": "Hello, 张三! 欢迎来到 studydemo 前后端分离示例。",
    "visits": 7,
    "dbCount": 7
  }
}
```

- `visits`：来自 Redis 的自增计数（验证 Redis 连通）
- `dbCount`：来自 MySQL 表 `t_demo_message` 的总行数（验证 MySQL 连通）

## 运行步骤

1. **建库**（MySQL 在 `120.48.43.201`，账号 `root / Aa@123456`）：

   ```sql
   CREATE DATABASE IF NOT EXISTS studydemo DEFAULT CHARACTER SET utf8mb4;
   ```

   表 `t_demo_message` 会在 dev 环境启动时自动创建（`schema.sql`，幂等）。

2. **启动**（需要 JDK 11）：

   ```sh
   mvn spring-boot:run
   # 或
   mvn package && java -jar target/studydemo-server-1.0.0.jar
   ```

   默认端口 `8080`，默认激活 `dev` 环境。

3. **自测**：

   ```sh
   curl "http://localhost:8080/api/hello?name=Vue"
   ```

## 前端怎么调（前后端分离试水）

前端 Vite 跑在 `http://localhost:5173`，后端已在 `CorsConfig` 中放行该源。直接 fetch 即可：

```ts
// 在 Vue 组件里
const res = await fetch('http://localhost:8080/api/hello?name=Vue')
const json = await res.json()
console.log(json.data.message) // Hello, Vue! 欢迎来到 studydemo 前后端分离示例。
```

对应后端封装（axios 写法）：

```ts
import axios from 'axios'
const api = axios.create({ baseURL: 'http://localhost:8080' })
const { data } = await api.get('/api/hello', { params: { name: 'Vue' } })
```

> 生产环境建议用 Nginx 反向代理把 `/api` 转发到后端，避免跨域、也隐藏端口。

## P3C 规范落点（本项目已遵守）

- 包名全小写 `com.studydemo.server`；类 UpperCamelCase；方法/变量 lowerCamelCase
- 禁止魔法值：状态码 `ResultCode`、Redis 键 `RedisKeyConstants`、默认名/前缀等全部抽常量
- 常量类 `private` 构造方法；`equals` 时常量放左侧（`EMPTY_NAME.equals(name)`）
- 日期格式用小写 `yyyy`（application.yaml 中 `jackson.date-format`）
- POJO 不使用 Lombok `@Data`，手写 getter/setter，避免 `equals/hashCode` 误包含父类字段
- 布尔字段不加 `is` 前缀（`deleted`），用 `@TableLogic` 逻辑删除
- SLF4J 打日志；`catch` 中 `log.warn` 而非空 catch
- 数组风格 `String[] args`；单行不超过 120 字符；大括号即使单行也保留

### P3C 静态校验（推荐方式）

代码已按上述规则手写。建议装 **Alibaba Java Coding Guidelines** IDEA 插件（Settings → Plugins 搜 `Alibaba Java Coding Guidelines`），右键包 → `编码规约扫描` 即可实时校验。

也可在 `pom.xml` 自行引入 `maven-pmd-plugin` + `com.alibaba.p3c:p3c-pmd`，按你本地 p3c-pmd 版本里的 `rulesets/java/ali-*.xml` 名称配置后执行：

```sh
mvn pmd:check
```

> 注意：p3c-pmd 不同版本的 ruleset 文件名略有差异，请以你本地 jar 内 `rulesets/java/` 实际文件名为准。

## 环境说明

- `application-dev.yaml` / `application-prod.yaml` 当前都指向你提供的 `120.48.43.201` 那套 MySQL/Redis。
- ⚠️ 生产环境请改为内网地址，并改用**专属受限账号**，不要用 `root` 直连；密码走配置中心或环境变量注入，勿明文提交。
- Redis `db 0`、无密码；生产建议启用密码与 ACL。
