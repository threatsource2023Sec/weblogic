package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.util.StringUtils;

public class HandshakeMessage extends AbstractBayeuxMessage {
   private static final long serialVersionUID = 8969528336986319705L;
   private String version;
   private String[] supportedConnectionTypes = new String[0];
   private String minimumVersion;
   private boolean authSuccessful;

   public void setVersion(String version) {
      this.version = version;
      this.fieldUpdated();
   }

   public void setMinimumVersion(String minimumVersion) {
      this.minimumVersion = minimumVersion;
      this.fieldUpdated();
   }

   public void setSupportedConnectionTypes(String[] supportedConnectionTypes) {
      this.supportedConnectionTypes = supportedConnectionTypes;
      this.fieldUpdated();
   }

   public void setAuthSuccessful(boolean authSuccessful) {
      this.authSuccessful = authSuccessful;
      this.fieldUpdated();
   }

   public String getVersion() {
      return this.version;
   }

   public String getMinimumVersion() {
      return this.minimumVersion;
   }

   public String[] getSupportedConnectionTypes() {
      return this.supportedConnectionTypes == null ? new String[0] : this.supportedConnectionTypes;
   }

   public boolean shouldCommentFilter() {
      return this.ext != null && this.ext.isJsonCommentFiltered();
   }

   public String getChannel() {
      return BayeuxConstants.META_HANDSHAKE;
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.HANDSHAKE;
   }

   public String toJSONRequestString() {
      if (this.toRequest != null) {
         return this.toRequest;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("{");
         sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
         sbuf.append("\"version\": \"").append(StringUtils.defaultString(this.version)).append("\", ");
         sbuf.append("\"supportedConnectionTypes\": [").append(StringUtils.arrayToString("\"", this.supportedConnectionTypes)).append("], ");
         sbuf.append("\"minimumVersion\": \"").append(this.minimumVersion).append("\"");
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
         if (this.successful) {
            sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
            sbuf.append("\"version\": \"").append(StringUtils.defaultString(this.version)).append("\", ");
            sbuf.append("\"supportedConnectionTypes\": [").append(StringUtils.arrayToString("\"", this.supportedConnectionTypes)).append("], ");
            sbuf.append("\"clientId\": \"").append(this.getClientId()).append("\", ");
            sbuf.append("\"successful\": ").append(this.successful).append(", ");
            sbuf.append("\"minimumVersion\": \"").append(this.minimumVersion).append("\", ");
            sbuf.append("\"authSuccessful\": ").append(this.authSuccessful);
            if (this.advice != null) {
               sbuf.append(", ");
               sbuf.append(this.advice.toJSONString());
            }
         } else {
            sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
            sbuf.append("\"successful\": ").append(this.successful).append(", ");
            sbuf.append("\"error\": \"").append(this.getError()).append("\", ");
            sbuf.append("\"supportedConnectionTypes\": [").append(StringUtils.arrayToString("\"", this.supportedConnectionTypes)).append("], ");
            sbuf.append("\"version\": \"").append(StringUtils.defaultString(this.version)).append("\", ");
            sbuf.append("\"minimumVersion\": \"").append(this.minimumVersion).append("\"");
            if (this.advice != null) {
               sbuf.append(", ");
               sbuf.append(this.advice.toJSONString());
            }
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
