# SpringBoot 集成 MybatisPlus
##本项目作为参考
* [集成参考文档](https://www.cnblogs.com/leeego-123/p/10734330.html)
* [mybatis yml配置参考文档](https://www.jianshu.com/p/cfb84fee0a98)
### 集成步骤
<strong>pom.xml 添加配置</strong>
```text
       <!--spring boot 启动引擎-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <!--mybatis plus 包-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.0.5</version>
        </dependency>
        <!--mysql 引擎-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.29</version>
        </dependency>
        <!-- 模板freemarker -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
        <!--lombok 插件-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>provided</scope>
        </dependency>
        <!--hutool 工具-->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>4.1.19</version>
        </dependency>
        <!--阿里巴巴 数据池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.7</version>
        </dependency>
        <!--spring boot web 包-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
```
<strong>复制 或添加 application.yml 添加配置</strong>
```text
server:
  port: 8081
spring:
  application:
    name: demo
  resources:
    static-locations: classpath:/resources/,classpath:/resource/
  profiles:
    active: dev
  datasource:
    url: jdbc:mysql://localhost:3306/adm?useUnicode=true&characterEncoding=utf8
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    initialSize: 1
    minIdle: 3
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
    filters: stat,slf4j
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
    useGlobalDataSourceStat: true
    type: com.alibaba.druid.pool.DruidDataSource
mybatis-plus:
  mapper-locations: classpath*:/mybatis/**/*.xml
  global-config:
    db-config:
      id-type: auto
      field-strategy: not_empty
      column-underline: true
      logic-delete-value: 0
      logic-not-delete-value: 1
      db-type: mysql
    refresh: true
  configuration:
#    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
    map-underscore-to-camel-case: false
    cache-enabled: true
```
<strong>复制 或添加 logback-spring.xml 添加日志配置</strong>
 ```xml
<?xml version="1.0" encoding="utf-8"?>
<configuration scan="true" scanPeriod="60 seconds">
    <springProperty scop="context" name="spring.application.name" source="spring.application.name"/>
    <conversionRule conversionWord="clr" converterClass="org.springframework.boot.logging.logback.ColorConverter" />
    <conversionRule conversionWord="wex" converterClass="org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter" />
    <conversionRule conversionWord="wEx" converterClass="org.springframework.boot.logging.logback.ExtendedWhitespaceThrowableProxyConverter" />
    <property name="CONSOLE_LOG_PATTERN" value="${CONSOLE_LOG_PATTERN:-%clr(%d{yyyy-MM-dd HH:mm:ss}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>
    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>logs/logback/${spring.application.name}_%d{yyyy-M-d}.log
            </FileNamePattern>
            <MaxHistory>10</MaxHistory>
        </rollingPolicy>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d{yyyy-M-d HH:mm:ss} %t %p %m%n</pattern>
        </encoder>
        <!--日志文件最大的大小-->
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
    </appender>
    <logger name="org.springframework.boot" level="debug"
            additivity="false">
        <appender-ref ref="file" />
    </logger>
    <!-- name包必须能够扫描到所以类，包括启动类 -->
    <logger name="org.triber" level="debug" additivity="false">
        <appender-ref ref="file" />
    </logger>
    <logger name="org.triber" level="debug">
        <appender-ref ref="stdout" />
    </logger>

    <!-- 日志输出级别 -->
    <root level="info">
        <appender-ref ref="stdout" />
        <appender-ref ref="file" />
    </root>
</configuration>
```
<strong>bulid打包排除yml 以及 lib  中yml配置</strong>
```xml
<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <!--此处为应用入口类全路径-->
                            <mainClass>org/triber/demo/demo/DemoApplication</mainClass>
                        </manifest>
                        <manifestEntries>
                            <!--配置文件目录-->
                            <Class-Path>./</Class-Path>
                        </manifestEntries>
                    </archive>
                    <!--打包时排除配置文件-->
                    <excludes>
                        <exclude>*.properties</exclude>
                        <exclude>*.yml</exclude>
                    </excludes>
                </configuration>
            </plugin>
            <!--打包时排除内部依赖,并将依赖放到target/lib目录-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy-dependencies</id>
                        <phase>prepare-package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/lib</outputDirectory>
                            <overWriteReleases>false</overWriteReleases>
                            <overWriteSnapshots>false</overWriteSnapshots>
                            <overWriteIfNewer>true</overWriteIfNewer>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <!--拷贝配置文件 copy-resources-->
            <plugin>
                <artifactId>maven-resources-plugin</artifactId>
                <executions>
                    <execution>
                        <id>copy-resources</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-resources</goal>
                        </goals>
                        <configuration>
                            <resources>
                                <resource>
                                    <directory>src/main/resources</directory>
                                    <includes>
                                        <include>*.yml</include>
                                        <include>*.properties</include>
                                    </includes>
                                </resource>
                            </resources>
                            <outputDirectory>${project.build.directory}</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```
<strong>代码生成器</strong>
* 将templates 文件夹复制在resources 下
* 将CodeGenerator 复制到和启动类同级目录下 并修改数据库 描述人等配置
* 将common文件夹 包括（MybatisPlusConfig，Query，R）
* 注意在Mapper接口中配置@Mapper 注解 以及  application.yml中 `mapper-locations: classpath*:/mybatis/**/*.xml`的路径配置

<strong>Swagger 使用</strong>
### 导入包
#### 访问界面 http://{ip}:{port}/docs.html
```$xml
        <!--引入Swagger2的依赖-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.6.1</version>
        </dependency>
        <!--引入Swagger 美化包-->
        <dependency>
            <groupId>com.github.caspar-chen</groupId>
            <artifactId>swagger-ui-layer</artifactId>
            <version>1.1.3</version>
        </dependency>
```
### 创建配置类
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.triber.demo.demo.api.loginUser"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("Swagger2构建RESTful APIs")
                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")
                .build();
    }
}
```
### 注解配置参考
```text
@ApiOperation：用在方法上，说明方法的作用
    value: 表示接口名称
    notes: 表示接口详细描述 
@ApiOperation(value="获取用户列表", notes="获取所有用户列表",produces = "application/json")

@ApiImplicitParams：用在方法上包含一组参数说明
@ApiImplicitParams({
        @ApiImplicitParam(name = "id",value = "用户ID",paramType = "path",dataType = "int"),
        @ApiImplicitParam(name = "userName",value = "用户名称",paramType = "form",dataType = "string")
})

@ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
@ApiImplicitParam(name = "userId",value = "用户ID",dataType = "int",paramType = "path")
paramType：参数位置
header 对应注解：@RequestHeader
query 对应注解：@RequestParam
path  对应注解: @PathVariable
body 对应注解: @RequestBody
name：参数名
dataType：参数类型
required：参数是否必须传
value：参数的描述
defaultValue：参数的默认值

@ApiModel(value = "user", description = "user对象")
public class UserModel {
    @ApiModelProperty(value = "ID", dataType = "Integer", required = true)
    private Integer userId;
    @ApiModelProperty(value = "用戶名", dataType = "String")
    private String userName;
    @ApiModelProperty(value = "性別", dataType = "Integer", allowableValues = "0,1,2")
    private Integer sex;
}

@ApiResponses：用于表示一组响应

@ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
code：状态码
message：返回自定义信息
response：抛出异常的类

@ApiIgnore: 表示该接口函数不对swagger2开放展示
```