package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.bayeux.BayeuxConstants;

public class ConnectMessage extends AbstractBayeuxMessage {
   private static final long serialVersionUID = -6510670860963451341L;
   private String connectionId;

   public void setConnectionId(String connectionId) {
      this.connectionId = connectionId;
      this.fieldUpdated();
   }

   public String getChannel() {
      return BayeuxConstants.META_CONNECT;
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.CONNECT;
   }

   public String toJSONRequestString() {
      if (this.toRequest != null) {
         return this.toRequest;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
         sbuf.append("\"connectionType\": \"").append(this.connectionType).append("\"");
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
         sbuf.append("\"error\": \"").append(this.getError()).append("\", ");
         sbuf.append("\"timestamp\": \"").append(this.getTimestampString()).append("\", ");
         sbuf.append("\"connectionId\": \"").append(this.connectionId).append("\"");
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
