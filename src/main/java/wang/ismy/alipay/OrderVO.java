package wang.ismy.alipay;

/**
 * @author MY
 * @date 2019/10/2 19:01
 */
public class OrderVO {

    private String QRCode;

    private String orderId;

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
