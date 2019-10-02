package wang.ismy.alipay;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceTest {

    @Autowired
    PayService payService;

    @Test
    public void createOrder() throws JsonProcessingException, AlipayApiException {
        payService.createOrder(88888L,"蔡徐坤的篮球");
        while (1==1){

        }
    }
}