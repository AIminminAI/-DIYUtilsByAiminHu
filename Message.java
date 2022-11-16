package entity;

import java.io.Serializable;

/**
 * Created by HHB on 2022/11/16.
 */
public class Message implements Serializable {
    public Message(String sendContent) {
        this.sendContent = sendContent;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }

    private String sendContent;


}
