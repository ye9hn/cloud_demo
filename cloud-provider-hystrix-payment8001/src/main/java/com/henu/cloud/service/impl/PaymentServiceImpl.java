package com.henu.cloud.service.impl;

import com.henu.cloud.dao.PaymentDao;
import com.henu.cloud.entities.Payment;
import com.henu.cloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
