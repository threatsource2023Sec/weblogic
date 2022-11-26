package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.bayeux.BayeuxConstants;

public class UnsubscribeMessage extends AbstractBayeuxMessage {
   private static final long serialVersionUID = 7537244310371175976L;
   private String subscription;

   public String getSubscription() {
      return this.subscription;
   }

   public void setSubscription(String subscription) {
      this.subscription = subscription;
      this.fieldUpdated();
   }

   public String getChannel() {
      return BayeuxConstants.META_UNSUBSCRIBE;
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.UNSUBSCRIBE;
   }

   public String toJSONRequestString() {
      if (this.toRequest != null) {
         return this.toRequest;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
         sbuf.append("\"subscription\": \"").append(this.getSubscription()).append("\"");
         sbuf.append("}");
         this.toRequest = sbuf.toString();
         return this.toRequest;
      }
   }

   public String toJSONResponseString() {
      if (this.toResponse != null) {
         return this.toResponse;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"successful\": ").append(this.successful).append(", ");
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
         sbuf.append("\"subscription\": \"").append(this.getSubscription()).append("\", ");
         sbuf.append("\"error\": \"").append(this.getError()).append("\", ");
         sbuf.append("\"timestamp\": \"").append(this.getTimestampString()).append("\"");
         if (this.advice != null) {
            sbuf.append(", ");
            sbuf.append(this.advice.toJSONString());
         }

         sbuf.append("}");
         this.toResponse = sbuf.toString();
         return this.toResponse;
      }
   }

   public boolean isMetaMessage() {
      return true;
   }
}
