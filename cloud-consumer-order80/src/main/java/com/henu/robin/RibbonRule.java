package com.henu.robin;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * robin负载均衡规则配置，必须放在项目的其他package下不能被@ComponentScan即com.henu.cloud扫描下，要不然会被其他组件共享
 */
@Configuration
public class RibbonRule {
    @Bean
    public IRule myRule(){
        //随机值负债均衡
        return new RandomRule();
       // return new BestAvailableRule();
    }
    //坏均衡算法：rest接口第几次请求书%服务器集群总数量=实际调用服务器位置下标，每次重启服务计数从1开始
    @Autowired
    private DiscoveryClient discoveryClient;
    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
    //具体原理可以参考IRule、RoundRobinRule（轮询）源码，代码比较短
}
