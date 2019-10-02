package wang.ismy.alipay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MY
 * @date 2019/10/2 16:52
 */
@RestController
@RequestMapping("alipay")
public class NotifyController {

    @GetMapping("notify")
    public String alipayNotify(){
        return null;
    }
}
