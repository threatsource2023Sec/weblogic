package weblogic.security.jaspic;

import java.util.Map;
import javax.security.auth.message.MessageInfo;

public class MessageInfoImpl implements MessageInfo {
   private Map map;
   private Object requestMessage;
   private Object responseMessage;

   public MessageInfoImpl(Object requestMessage, Object responseMessage, Map map) {
      this.requestMessage = requestMessage;
      this.responseMessage = responseMessage;
      this.map = map;
   }

   public Map getMap() {
      return this.map;
   }

   public Object getRequestMessage() {
      return this.requestMessage;
   }

   public Object getResponseMessage() {
      return this.responseMessage;
   }

   public void setRequestMessage(Object message) {
      this.requestMessage = message;
   }

   public void setResponseMessage(Object message) {
      this.responseMessage = message;
   }
}
