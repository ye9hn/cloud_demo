微服务模块流程
1、建module
2、改pom
3、写yml
4、主启动类
5、业务类
<modules>
        <module>cloud-api-commons</module>
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
         zookeeper地址
             192.168.0.108:2181-->
        <module>cloud-provider-payment8004</module>
         <module>cloud-consumerzk-order80</module>
         <!--8006、80使用consul作为服务注册中心
         consul注册中心访问地址http://localhost:8500
         -->
        <module>cloud-provider-payment8006</module>
        <module>cloud-consumerconsul-order80</module>
    </modules>


ribbon是一款客户端负载均衡机制，下面依赖中包含了ribbon的依赖，所以客户端可以直接使用，ribbon就是负载均衡+RestTemplate
 <!--eureka client端依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

