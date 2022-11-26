package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.util.StringUtils;
import java.io.Serializable;

public class Advice implements Serializable {
   private static final long serialVersionUID = -1128664969783691225L;
   private static final int DEFAULT_INTERVAL = 500;
   private String jsonString;
   private RECONNECT reconnect;
   private int interval;
   private boolean multipleClients;
   private String[] hosts;

   public Advice() {
      this.reconnect = Advice.RECONNECT.nil;
      this.interval = 500;
      this.multipleClients = false;
      this.hosts = new String[0];
   }

   public RECONNECT getReconnect() {
      return this.reconnect;
   }

   public void setReconnect(RECONNECT reconnect) {
      this.reconnect = reconnect;
      this.fieldUpdated();
   }

   public int getInterval() {
      return this.interval;
   }

   public void setInterval(int interval) {
      this.interval = interval;
      this.fieldUpdated();
   }

   public boolean isMultipleClients() {
      return this.multipleClients;
   }

   public void setMultipleClients(boolean multipleClients) {
      this.multipleClients = multipleClients;
      this.fieldUpdated();
   }

   public String[] getHosts() {
      return this.hosts;
   }

   public void setHosts(String[] hosts) {
      this.hosts = hosts;
      this.fieldUpdated();
   }

   public String toJSONString() {
      if (this.jsonString != null) {
         return this.jsonString;
      } else {
         StringBuilder sbuf = new StringBuilder();
         sbuf.append("\"advice\": {");
         if (this.getReconnect() != null && this.getReconnect() != Advice.RECONNECT.nil) {
            sbuf.append("\"reconnect\": \"").append(this.getReconnect().name()).append("\", ");
         }

         sbuf.append("\"interval\": ").append(this.interval).append(", ");
         sbuf.append("\"multiple-clients\": ").append(this.isMultipleClients());
         if (this.getHosts() != null && this.getHosts().length > 0) {
            sbuf.append(", ");
            sbuf.append("\"hosts\": [").append(StringUtils.arrayToString("\"", this.getHosts())).append("]");
         }

         sbuf.append("}");
         this.jsonString = sbuf.toString();
         return this.jsonString;
      }
   }

   private void fieldUpdated() {
      this.jsonString = null;
   }

   public static enum RECONNECT {
      none,
      retry,
      handshake,
      recover,
      nil;
   }
}
