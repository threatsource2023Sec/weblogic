package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.util.StringUtils;

public class EventMessageImpl extends AbstractBayeuxMessage implements EventMessage {
   private static final long serialVersionUID = -6178082204971633332L;
   private String channel;
   private String data;
   private String id;

   public String getPayLoad() {
      return this.data;
   }

   public void setPayLoad(String obj) {
      this.data = obj;
      this.fieldUpdated();
   }

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
      this.fieldUpdated();
   }

   public String getChannel() {
      return this.channel;
   }

   public void setChannel(String channel) {
      this.channel = channel;
      this.fieldUpdated();
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.PUBLISH;
   }

   public String toJSONRequestString() {
      if (this.toRequest != null) {
         return this.toRequest;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"data\": ").append(this.convertPayLoadToString(this.data));
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
         sbuf.append("\"id\": \"").append(StringUtils.defaultString(this.id)).append("\", ");
         sbuf.append("\"error\": \"").append(this.getError()).append("\"");
         sbuf.append("}");
         this.toResponse = sbuf.toString();
         return this.toResponse;
      }
   }

   public boolean isMetaMessage() {
      return false;
   }
}
