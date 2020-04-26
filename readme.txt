微服务模块流程-》
        1、建module
        2、改pom
        3、写yml
        4、主启动类
        5、业务类
        <!--spring cloud 整合了Netflix的 ribbon、eureka、openfeign、hystrix、consul-->
        <module>cloud-api-commons</module>

ribbon是一款客户端负载均衡机制，下面依赖中包含了ribbon的依赖，所以客户端可以直接使用，ribbon就是负载均衡+RestTemplate
        <!--eureka client端依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!--客户端使用ribbon作为客户端负载均衡-->
        <module>cloud-consumer-order80</module>
        <!--eureka服务注册中心
        服务注册eureka地址：
            http://eureka7001.com:7001/eureka
            http://eureka7002.com:7002/eureka-->
        <module>cloud-eureka-server7001</module>
        <module>cloud-eureka-server7002</module>
        <!--8001、8002使用eureka作为服务注册中心-->
        <module>cloud-provider-payment8001</module>
        <module>cloud-provider-payment8002</module>


         <!--8004、80使用zookeeper作为服务注册中心
         zookeeper地址192.168.0.108:2181-->
        <module>cloud-consumerzk-order80</module>
        <module>cloud-provider-payment8004</module>


         <!--8006、80使用consul作为服务注册中心
         consul注册中心访问地址http://localhost:8500-->
        <module>cloud-consumerconsul-order80</module>
        <module>cloud-provider-payment8006</module>


        <!--feign系列-->
        <module>cloud-consumer-feign-order80</module>
        <module>cloud-eureka-server7001</module>
        <module>cloud-eureka-server7002</module>
        <!--8001、8002使用eureka作为服务注册中心-->
        <module>cloud-provider-payment8001</module>
        <module>cloud-provider-payment8002</module>


        <!--Hystrix系列实现服务降级（一般用在客户端）、服务熔断、服务限流-->
        <module>cloud-consumer-feign-hystrix-order80</module>
        //允许服务降级
        @EnableCircuitBreaker
        服务降级策略fallbackMethod
        @HystrixCommand(fallbackMethod ="paymentInfo_TimeOutHandler",commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")})
        <module>cloud-provider-hystrix-payment8001</module>
        <module>cloud-eureka-server7001</module>
        <module>cloud-eureka-server7002</module>

        <!--Hystrix图形化监控工具Dashborad-->
        <module>cloud-consumer-hystrix-dashboard9001</module>
        <module>cloud-provider-hystrix-payment8001</module>
        <module>cloud-eureka-server7001</module>
        <module>cloud-eureka-server7002</module>
        如果要使用dashboard监控某个服务，微服务必须添加下面依赖用来暴露服务和允许spring mvc
         <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
         </dependency>

         <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-actuator</artifactId>
         </dependency>

         <!--服务网关-->
         <module>cloud-gateway-gateway9527</module>
         <module>cloud-eureka-server7001</module>
         <module>cloud-eureka-server7002</module>
         <!--8001、8002使用eureka作为服务注册中心-->
         <module>cloud-provider-payment8001</module>
         <module>cloud-provider-payment8002</module>

         <!--服务配置中心config从GitHub读取配置文件和消息总线bus（这里使用kafka做bus全局和单点刷新操作）-->
         <!--curl -X POST "http://localhost:3344/actuator/bus-refresh"刷新config-server-->
         <!--curl -X POST "http://localhost:3344/actuator/bus-refresh/{spring.application.name:端口号}"定点刷新服务-->
         <module>cloud-eureka-server7001</module>
         <module>cloud-eureka-server7002</module>
         <module>cloud-config-center3344</module>
         <module>cloud-config-client3355</module>
         <module>cloud-config-client3366</module>

        <!--Spring Cloud Stream封装消息生产者和消费者 消息驱动的微服务-->
        <!--服务注册中心-->
         <module>cloud-eureka-server7001</module>
         <module>cloud-eureka-server7002</module>
         <!--消息生产者-->
         <module>cloud-stream-kafka-provider8801</module>
         <module>cloud-stream-kafka-consumer8802</module>
         <module>cloud-stream-kafka-consumer8803</module>

         <!--使用Nacos可以替代eureka+hystrix-->
         <module>cloud-alibaba-provider-payment9001</module>
         <module>cloud-alibaba-provider-payment9002</module>
         <module>cloud-alibaba-consumer-nacos-order83</module>
         <!--使用Nacos可以替代eureka+config+bus实现服务注册和config+bus动态刷新-->
         <module>cloud-alibaba-config-nacos-client3377</module>

         <!--Spring Cloud Alibaba Sentinel服务限流、服务降级、 @SentinelResource()全部异常处理-->
         <module>cloud-alibaba-sentinel-service8401</module>
@EnableDiscoveryClient和@EnableEurekaClient共同点就是：都是能够让注册中心能够发现，扫描到改服务。
不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 可以是其他注册中心。


systemctl stop firewalld关闭防火墙

nacos部署在Linux使用nginx做反向代理，部署三个伪分布式节点集群，连接本地mysql数据库
nginx目录/usr/local/nginx
./nginx -c /usr/local/nginx/conf/nginx.conf

nacos源码本地mvn打包
git clone https://github.com/alibaba/nacos.git
cd nacos/
mvn -Prelease-nacos -Dmaven.test.skip=true clean install -U
ls -al distribution/target/
// change the $version to your actual path
cd distribution/target/nacos-server-$version/nacos/bin
打包完成使用7-zip压缩tar包，然后在压缩gz包，使用rz -E命令上传到Linux服务器
上传后，修改修改conf下的cluster.conf，然后修改startup.sh

修改完成后，使用./startup.sh -p port启动伪分布式节点

这里如果使用Linux配置nacos使用本地的mysql会出现连接不上的问题（访问权限问题）解决方案：
USE nacos_config;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
FLUSH PRIVILEGES;