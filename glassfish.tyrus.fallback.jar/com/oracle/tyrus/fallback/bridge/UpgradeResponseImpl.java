package com.oracle.tyrus.fallback.bridge;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.tyrus.spi.UpgradeResponse;

public class UpgradeResponseImpl extends UpgradeResponse {
   private HttpServletResponse res;
   private final Map headers = new HashMap();

   public UpgradeResponseImpl(HttpServletResponse res) {
      this.res = res;
   }

   public int getStatus() {
      this.validateResponse();
      return this.res.getStatus();
   }

   public void setStatus(int status) {
      this.validateResponse();
      this.res.setStatus(status);
   }

   public void setReasonPhrase(String reason) {
      this.validateResponse();
   }

   public Map getHeaders() {
      this.validateResponse();
      return this.headers;
   }

   public void done() {
      this.res = null;
   }

   private void validateResponse() {
      if (this.res == null) {
         throw new IllegalStateException("HTTP response is used beyond the upgrade");
      }
   }
}
