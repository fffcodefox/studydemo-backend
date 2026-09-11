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
│  │  ├─ config/CorsConfig.java            # 跨域：来源从 app.cors.allowed-origins 读
│  │  ├─ constant/RedisKeyConstants.java    # Redis 键常量（私有构造）
│  │  ├─ common/Result.java / ResultCode.java  # 统一返回 + 状态码常量
│  │  ├─ dto/HelloVO.java                  # 接口返回 VO
│  │  ├─ domain/DemoMessage.java           # 实体（t_demo_message）
│  │  ├─ mapper/DemoMessageMapper.java      # MyBatis-Plus Mapper
│  │  ├─ service/HelloService.java          # 业务接口
│  │  ├─ service/impl/HelloServiceImpl.java # 业务实现（Redis 计数 + MySQL 落库）
│  │  └─ controller/HelloController.java     # GET /api/hello
│  └─ resources/
│     ├─ application.yaml     # 通用配置（端口 + 激活 dev）
│     ├─ application-dev.yaml # 开发：MySQL + Redis（给定地址）+ 自动建表 + 跨域来源
│     ├─ application-prod.yaml# 生产：连接池/SSL/日志调成生产向 + 跨域来源
│     └─ schema.sql          # 幂等建表
└─ .gitignore
```

## 配置项

### 端口

`application.yaml` 里统一配置，默认 8080：

```yaml
server:
  port: ${SERVER_PORT:8080}   # 可用环境变量覆盖
```

```sh
# 环境变量覆盖（Windows 用 set）
set SERVER_PORT=9090 && mvn spring-boot:run

# 或启动参数覆盖
java -jar target/studydemo-server-1.0.0.jar --server.port=9090
```

> ⚠️ 改了端口，前端 `.env.development` 里的 `DEV_PROXY_TARGET` 要跟着改，
> 否则前端会被代理到一个没人监听的端口，表现是 502 而不是跨域报错。

### 跨域来源

来自 `app.cors.allowed-origins`（多个用逗号分隔），在 profile 各自的 yaml 里配置：

- dev：`http://localhost:5173,http://127.0.0.1:5173`（端口对应前端 `.env.development` 的 `DEV_SERVER_PORT`）
- prod：前端真实域名

> 前端走 Vite 代理（开发）/ Nginx 反代（生产）时，浏览器看到的是同源请求，**根本不会触发跨域**，
> 这里的配置是「前端直连后端」场景的兜底。

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

1. **建库**（MySQL 在 `120.48.43.201`，账号 `root / Aa@123`）：

   ```sql
   CREATE DATABASE IF NOT EXISTS study_db DEFAULT CHARACTER SET utf8mb4;
   ```

   > ✅ `study_db` 与表 `t_demo_message` 已在服务器上建好（2026-09-12 已验证连通，MySQL 8.0.45）。
   > 表在 dev 环境启动时也会由 `schema.sql` 幂等创建，所以这一步现在可以跳过。

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

前端（`studydemo`）**不直连**本服务，而是请求同源的 `/api/hello`，由中间层转发过来：

```
浏览器 (5173) → /api/hello → Vite Dev Server（server.proxy）→ 127.0.0.1:8080 → MySQL + Redis
```

好处是浏览器眼里始终同源，不触发跨域，也不用维护 CORS 白名单。前端调用代码在 `src/api/`：

```ts
import { fetchHello } from '@/api/hello'

const vo = await fetchHello('Vue')
console.log(vo.message) // Hello, Vue! 欢迎来到 studydemo 前后端分离示例。
```

> 生产环境建议用 Nginx 反向代理把 `/api/` 转发到后端，避免跨域、也隐藏端口。
> Nginx 要写 `location /api/` 而不是 `location /api` —— 后者是前缀匹配，会把 `/api-xxx` 路径一起吞掉。

如果确实要让前端**直连**本服务（真跨域），把前端 `.env.*` 的 `VITE_API_BASE` 改成完整地址
（如 `http://localhost:8080/api`），并在本服务的 `app.cors.allowed-origins` 里放行前端域名。

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

- `application-dev.yaml` / `application-prod.yaml` 当前都指向你提供的 `120.48.43.201` 那套 MySQL/Redis，库名均为 `study_db`。
- ⚠️ 生产环境请改为内网地址，并改用**专属受限账号**，不要用 `root` 直连；密码走配置中心或环境变量注入，勿明文提交。
- Redis `db 0`、无密码；生产建议启用密码与 ACL。

## 排坑记录（启动失败常见原因）

| 现象 | 原因 | 处理 |
| --- | --- | --- |
| `java.sql.SQLException: Unsupported character encoding 'utf8mb4'` | JDBC URL 的 `characterEncoding` 填了 MySQL 字符集名 | 必须填 **Java 字符集名 `UTF-8`**，Connector/J 8 会自动协商为服务端 `utf8mb4`。已修正 |
| 启动建 Redis 连接工厂时 `NoClassDefFoundError: org/apache/commons/pool2/...` | 配了 `spring.redis.lettuce.pool.*` 但缺 `commons-pool2`（starter 不自带） | 已在 `pom.xml` 引入 `org.apache.commons:commons-pool2` |
| `Access denied for user 'root'@'<公网IP>' (using password: YES)` | ① 密码填错（本次就是这个：实际密码是 `Aa@123`，不是 `Aa@123456`）；② 或 MySQL 未放行该主机的远程登录 | 先核对密码；若密码正确仍被拒，在服务器执行授权：`CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'Aa@123'; GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION; FLUSH PRIVILEGES;`（生产建议改用只授权 `study_db` 的专属账号） |
| 端口不是 8080 | 存在 `SERVER_PORT` 环境变量覆盖了 yaml | 检查环境变量，或用 `mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8080` |

## 前端侧常见现象（联调时先看这里）

| 现象 | 原因 | 处理 |
| --- | --- | --- |
| 前端页面提示「请求发不出去」，浏览器 Network 里 `/api/hello` 是 500/502 | 后端没启动，或端口与前端 `DEV_PROXY_TARGET` 不一致 | 先 `curl "http://127.0.0.1:8080/api/hello"` 确认后端在跑，再核对前端 `.env.development` 的 `DEV_PROXY_TARGET` |
| 代理报 `502 upstream connect failed ... (os error 10061)`，但后端日志显示启动成功 | 代理目标写成了 `localhost`。Node 解析 `localhost` 优先拿 IPv6 `::1`，而 Spring Boot 默认只监听 IPv4 | 前端代理目标改用 `127.0.0.1` |
| 直接访问前端路由 `/api-xxx` 拿到了后端 JSON 而不是页面 | 代理规则 `'/api'` 是前缀匹配，把前端路由也转发了 | 代理规则改成 `^/api/`；Nginx 同理写 `location /api/` |
| 直连模式下浏览器报 CORS | 前端来源不在 `app.cors.allowed-origins` 里（比如 5173 被占用、Vite 自动跳到了 5174） | 要么走代理（推荐，天然无跨域），要么把新来源补进配置 |
