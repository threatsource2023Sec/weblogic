package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.bayeux.BayeuxConstants;

public class DisconnectMessage extends AbstractBayeuxMessage {
   private static final long serialVersionUID = 7116400158890891892L;

   public String getChannel() {
      return BayeuxConstants.META_DISCONNECT;
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.DISCONNECT;
   }

   public String toJSONRequestString() {
      if (this.toRequest != null) {
         return this.toRequest;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\"");
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
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
         sbuf.append("\"successful\": ").append(this.successful).append(", ");
         sbuf.append("\"error\": \"").append(this.getError()).append("\"");
         sbuf.append("}");
         this.toResponse = sbuf.toString();
         return this.toResponse;
      }
   }

   public boolean isMetaMessage() {
      return true;
   }
}
