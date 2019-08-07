package net.suncaper.hotel_manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelManagerApplicationTests {
    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1026960839@qq.com");
        message.setTo("1026960839@qq.com");
        message.setSubject("测试邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);
    }

}
