package weblogic.jms.extensions;

import javax.jms.Destination;
import weblogic.jms.common.JMSException;

public final class DataOverrunException extends JMSException {
   static final long serialVersionUID = 6488522262679030149L;
   private String messageId;
   private String correlationId;
   private Destination destination;

   public DataOverrunException(String reason, String errorCode, String messageId, String correlationId, Destination destination) {
      super(reason, errorCode);
      this.messageId = messageId;
      this.correlationId = correlationId;
      this.destination = destination;
   }

   public DataOverrunException(String reason, String messageId, String correlationId, Destination destination) {
      super(reason);
      this.messageId = messageId;
      this.correlationId = correlationId;
      this.destination = destination;
   }

   public String getJMSMessageId() {
      return this.messageId;
   }

   public String getJMSCorrelationId() {
      return this.correlationId;
   }

   public Destination getJMSDestination() {
      return this.destination;
   }

   public boolean isInformational() {
      return true;
   }
}
