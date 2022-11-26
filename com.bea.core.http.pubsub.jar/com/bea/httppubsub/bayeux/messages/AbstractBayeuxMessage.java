package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.util.StringUtils;
import com.bea.httppubsub.util.TimeUtils;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpSession;

public abstract class AbstractBayeuxMessage implements BayeuxMessage {
   protected String toRequest;
   protected String toResponse;
   protected transient Client client;
   protected transient HttpSession httpSession;
   protected boolean successful;
   protected String error;
   protected long timestamp;
   protected String clientId;
   protected Advice advice;
   protected String connectionType;
   protected Ext ext;

   protected AbstractBayeuxMessage() {
   }

   public void setSuccessful(boolean successful) {
      this.successful = successful;
      this.fieldUpdated();
   }

   public void setError(String error) {
      this.error = error;
      this.fieldUpdated();
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
      this.fieldUpdated();
   }

   public void setClient(Client client) {
      this.client = client;
      this.fieldUpdated();
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
      this.fieldUpdated();
   }

   public void setAdvice(Advice advice) {
      this.advice = advice;
      this.fieldUpdated();
   }

   public void setExt(Ext ext) {
      this.ext = ext;
      this.fieldUpdated();
   }

   public void setConnectionType(String connectionType) {
      this.connectionType = connectionType;
      this.fieldUpdated();
   }

   public Advice getAdvice() {
      return this.advice;
   }

   public Ext getExt() {
      return this.ext;
   }

   public String getError() {
      return this.successful ? "" : StringUtils.defaultString(this.error);
   }

   public String getTimestampString() {
      return TimeUtils.getTime(this.timestamp);
   }

   public Client getClient() {
      return this.client;
   }

   public String getClientId() {
      return this.clientId;
   }

   public boolean isSuccessful() {
      return this.successful;
   }

   public String getConnectionType() {
      return this.connectionType;
   }

   public void updateClient(Client client) {
      if (client != null) {
         this.setClient(client);
         this.setClientId(client.getId());
      }
   }

   public void clearClient() {
      this.setClient((Client)null);
   }

   protected void fieldUpdated() {
      this.toRequest = null;
      this.toResponse = null;
   }

   public HttpSession getHttpSession() {
      return this.httpSession;
   }

   public void setHttpSession(HttpSession httpSession) {
      this.httpSession = httpSession;
   }

   public abstract boolean isMetaMessage();

   public byte[] getJSONResponseUTF8Bytes() {
      String response = this.toJSONResponseString();
      if (!this.successful && this.getError().length() != 0) {
         try {
            return response.getBytes("UTF-8");
         } catch (UnsupportedEncodingException var3) {
            return null;
         }
      } else {
         byte[] bytes = new byte[response.length()];
         response.getBytes(0, response.length(), bytes, 0);
         return bytes;
      }
   }

   protected String convertPayLoadToString(String payLoad) {
      if (payLoad == null) {
         return "null";
      } else if (!this.isJSONArray(payLoad) && !this.isJSONObject(payLoad)) {
         String str = payLoad.replaceAll("\"", "\\\\\"");
         return "\"" + str + "\"";
      } else {
         return payLoad;
      }
   }

   private boolean isJSONArray(String msg) {
      String msgForChecking = msg.trim();
      return msgForChecking.startsWith("[") && msgForChecking.endsWith("]");
   }

   private boolean isJSONObject(String msg) {
      String msgForChecking = msg.trim();
      return msgForChecking.startsWith("{") && msgForChecking.endsWith("}");
   }
}
