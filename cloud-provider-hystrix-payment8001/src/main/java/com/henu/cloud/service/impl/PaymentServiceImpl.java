package com.henu.cloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.henu.cloud.dao.PaymentDao;
import com.henu.cloud.entities.Payment;
import com.henu.cloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
   private PaymentDao paymentDao;
    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
    /**
     * 正常访问OK
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_OK,id:  "+id;
    }

    /**
     * 访问失败
     *失败时服务降级@HystrixCommand(fallbackMethod ="paymentInfo_TimeOutHandler",commandProperties = {
     *             @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
     *     })
     * 这里要@HystrixProperty注明捕获什么异常，要不然会捕获全部异常
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod ="paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    public  String paymentInfo_TimeOut(Integer id){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_TimeOut,id:  "+id+"  耗时3秒钟";
    }

    /**
     * 服务降级，访问失败处理器
     * @param id
     * @return
     */
    public  String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   系统繁忙，请等待,id:  "+id;
    }

    /**
     * 服务熔断
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
             @HystrixProperty(name = "circuitBreaker.enabled" ,value ="true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold" ,value ="10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds" ,value ="10000"),//时间窗口期，失败后多少秒开启OPEN
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage" ,value ="60")//失败率达到多少后跳闸
    })
    @Override
    public String paymentCircuitBreaker(Integer id){
        if (id<0){
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber= IdUtil.simpleUUID();
        return Thread.currentThread().getName()+" 调用成功，流水号： "+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能负数，请稍后再试， id:"+id;
    }
}
