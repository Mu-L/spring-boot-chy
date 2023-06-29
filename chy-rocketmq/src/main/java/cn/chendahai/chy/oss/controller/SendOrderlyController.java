package cn.chendahai.chy.oss.controller;

import cn.chendahai.chy.oss.config.RocketMQProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sendOrderly")
public class SendOrderlyController {

    @Autowired
    private RocketMQProducer rocketMQProducer;


    @RequestMapping("/send")
    public String sendByTopic1(@RequestParam(defaultValue = "hello") String msg, @RequestParam(defaultValue = "1") Integer count, @RequestParam(defaultValue = "0") Integer arg) {
        for (int i = 1; i <= count; i++) {
            SendResult send = rocketMQProducer.sendOrderlyDemo1("topic1>>>" + msg, arg);
            System.out.println("发送的顺序消息arg：" + arg);
            System.out.println("发送的顺序消息消息：" + send.toString());
        }
        return "success";
    }


}
