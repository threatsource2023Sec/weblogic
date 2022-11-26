package weblogic.jms.extensions;

import java.io.Serializable;
import weblogic.jms.common.JMSException;

public class JMSOrderException extends JMSException {
   Serializable member;

   public JMSOrderException(String reason) {
      super(reason);
   }

   public JMSOrderException(String message, String errorCode) {
      super(message, errorCode);
   }

   public JMSOrderException(String message, Throwable cause) {
      super(message);
      this.initCause(cause);
   }

   public void setMember(Serializable member) {
      this.member = member;
   }

   public Serializable getMember() {
      return this.member;
   }
}
