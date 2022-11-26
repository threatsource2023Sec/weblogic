package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.util.StringUtils;

public class ServiceMessageImpl extends EventMessageImpl {
   public String toJSONResponseString() {
      if (this.toResponse != null) {
         return this.toResponse;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
         sbuf.append("\"data\": ").append(this.convertPayLoadToString(this.getPayLoad())).append(", ");
         sbuf.append("\"successful\": ").append(this.successful).append(", ");
         sbuf.append("\"id\": \"").append(StringUtils.defaultString(this.getId())).append("\", ");
         sbuf.append("\"error\": \"").append(this.getError()).append("\"");
         sbuf.append("}");
         this.toResponse = sbuf.toString();
         return this.toResponse;
      }
   }
}
