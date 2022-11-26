package org.glassfish.tyrus.core;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class TyrusUpgradeResponse extends UpgradeResponse {
   private final Map headers = new TreeMap(new Comparator() {
      public int compare(String o1, String o2) {
         return o1.toLowerCase().compareTo(o2.toLowerCase());
      }
   });
   private int status;
   private String reasonPhrase;

   public int getStatus() {
      return this.status;
   }

   public String getReasonPhrase() {
      return this.reasonPhrase;
   }

   public Map getHeaders() {
      return this.headers;
   }

   public void setStatus(int statusCode) {
      this.status = statusCode;
   }

   public void setReasonPhrase(String reasonPhrase) {
      this.reasonPhrase = reasonPhrase;
   }
}
