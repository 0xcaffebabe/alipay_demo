package wang.ismy.alipay.controller;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.ismy.alipay.OrderVO;
import wang.ismy.alipay.PayService;

/**
 * @author MY
 * @date 2019/10/2 18:55
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private PayService payService;

    @PostMapping
    public ResponseEntity<OrderVO> createOrder(@RequestParam String title, @RequestParam Long price){
        try {
            return ResponseEntity.ok(payService.createOrder(price,title));
        } catch (AlipayApiException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public String getOrderStatus(@RequestParam String orderId) throws JsonProcessingException, AlipayApiException {
        return payService.getOrderStatus(orderId);
    }
}
