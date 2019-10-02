package wang.ismy.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.PriceInfo;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MY
 * @date 2019/10/2 15:40
 */
@Service
public class PayService {

    private final String appId = "";
    private final String privateKey = "应用私钥";
    private final String publicKey = "支付宝公钥";

    private String serverUrl = "https://openapi.alipaydev.com/gateway.do";
    private AlipayClient client;

    @PostConstruct
    public void init() {

        client = new DefaultAlipayClient(serverUrl,
                appId, privateKey, "json", "utf-8", publicKey, "RSA2");


    }

    public OrderVO createOrder(Long price, String title) throws AlipayApiException, JsonProcessingException {

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", System.currentTimeMillis() + ""); // 商户订单号
        map.put("total_amount", price / 100); // 订单总金额
        map.put("subject", title); //订单标题

        request.setBizContent(new ObjectMapper().writeValueAsString(map));

        AlipayTradePrecreateResponse response = client.execute(request);
        if (response.isSuccess()) {

            String qrCode = response.getQrCode();
            String outTradeNo = response.getOutTradeNo();

            OrderVO vo = new OrderVO();

            vo.setQRCode(qrCode);
            vo.setOrderId(outTradeNo);
            System.out.println("调用成功");
            System.out.println("支付宝二维码网址:" + qrCode);
            System.out.println("商户订单号:" + outTradeNo);
            return vo;

        } else {
            System.out.println("调用失败");

            System.out.println(response.getCode());
            System.out.println(response.getSubCode());
            System.out.println(response.getSubMsg());
            System.out.println(response.getMsg());
            System.out.println(request.getBizContent());
            if (response.getCode().equals("20000")){
                System.out.println("2000错误，重试...");
                return createOrder(price,title);
            }
        }
        return null;
    }

    public String getOrderStatus(String orderId) throws JsonProcessingException, AlipayApiException {
        Map<String, Object> map = new HashMap<>();
        map.put("out_trade_no", orderId);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        request.setBizContent(new ObjectMapper().writeValueAsString(map));
        AlipayTradeQueryResponse response = client.execute(request);
        if (response.isSuccess()) {
            return response.getTradeStatus();

        } else {

            System.out.println(response.getCode());
            System.out.println(response.getSubCode());
            System.out.println(request.getBizContent());
            if (response.getCode().equals("20000")){
                System.out.println("2000错误，重试...");
                return getOrderStatus(orderId);
            }
            return "TRADE_NOT_EXIST";

        }


    }
}
