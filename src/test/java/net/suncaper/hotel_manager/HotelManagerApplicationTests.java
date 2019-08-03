package net.suncaper.hotel_manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelManagerApplicationTests {

    @Test
    public void contextLoads() {
        String dates = "08-01-19 > 08-02-19";
        String[] splitDate = dates.split(" > ");

        String checkin = splitDate[0];
        String checkout = splitDate[1];

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        try {
            Date o_checkin = sdf.parse(checkin);
            Date o_checkout = sdf.parse(checkout);
            System.out.println("这是由未格式化的date转化过来的日期: " + o_checkin + ", " + o_checkout);
            System.out.println("这是正常的date格式: " + new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
