package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 向数据库中新增数据
     * @param payment
     * @return
     */
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {

        Integer result = paymentService.create(payment);
        System.out.println("*****返回结果是："+result);
        if(result > 0) {
            return new CommonResult(200,"新增数据成功,serverport:"+serverPort,result);
        }else{
            return new CommonResult(500,"新增数据失败",null);
        }
    }

    /**
     * 根据id在数据库中查询数据
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {

        Payment payment = paymentService.getPaymentById(id);
        System.out.println("*****返回结果是："+payment);
        if(null != payment) {
            return new CommonResult(200,"查询数据成功,serverport:"+serverPort,payment);
        }else{
            return new CommonResult(500,"新增数据失败",null);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("***element:"+element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("serverId:"+instance.getServiceId()+",host:"+instance.getHost()+",port:"+instance.getPort()+",uri:"+instance.getUri());
        }
        return this.discoveryClient;
    }
}
