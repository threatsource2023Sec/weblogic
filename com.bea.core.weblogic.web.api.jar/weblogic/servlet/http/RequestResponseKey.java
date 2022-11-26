package weblogic.servlet.http;

import java.io.IOException;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.internal.FutureServletResponse;

public class RequestResponseKey {
   private HttpServletRequest request;
   private HttpServletResponse response;
   private long timeout;
   private int originalTimeout;
   private boolean valid;
   private boolean isCallDoResponse;
   private boolean isImmediateSendOnDoRequestFalse;
   private final int hashcode;

   RequestResponseKey(HttpServletRequest request, HttpServletResponse response, int timeout) {
      this.originalTimeout = timeout;
      this.timeout = System.currentTimeMillis() + (long)timeout;
      this.request = request;
      this.response = response;
      this.valid = true;
      this.isCallDoResponse = true;
      this.isImmediateSendOnDoRequestFalse = true;
      this.hashcode = 29 * request.hashCode() ^ response.hashCode();
   }

   public void setTimeout(int timeout) {
      this.timeout += (long)(timeout - this.originalTimeout);
      this.originalTimeout = timeout;
   }

   public long getTimeout() {
      return this.timeout;
   }

   boolean isTimeout(long now) {
      return this.originalTimeout < 0 ? false : this.timeout <= now;
   }

   public boolean isValid() {
      return this.valid;
   }

   public void setCallDoResponse(boolean isCallDoResponse) {
      this.isCallDoResponse = isCallDoResponse;
   }

   public boolean isCallDoResponse() {
      return this.isCallDoResponse;
   }

   public void setImmediateSendOnDoRequestFalse(boolean isImmediateSendOnDoRequestFalse) {
      this.isImmediateSendOnDoRequestFalse = isImmediateSendOnDoRequestFalse;
   }

   public boolean isImmediateSendOnDoRequestFalse() {
      return this.isImmediateSendOnDoRequestFalse;
   }

   public HttpServletRequest getRequest() {
      return this.request;
   }

   public HttpServletResponse getResponse() {
      return this.response;
   }

   synchronized void closeResponse() throws IOException {
      if (this.valid) {
         this.valid = false;
         this.getOriginalResponse(this.response).send();
      }
   }

   synchronized void notifyRequestComplete() throws IOException {
      if (this.valid) {
         this.valid = false;
         FutureServletResponse f = this.getOriginalResponse(this.response);
         if (this.isImmediateSendOnDoRequestFalse()) {
            f.send();
         } else {
            f.setResponseReady();
         }

      }
   }

   public int hashCode() {
      return this.hashcode;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof RequestResponseKey) {
         RequestResponseKey other = (RequestResponseKey)obj;
         return this.request.equals(other.request) && this.response.equals(other.response);
      } else {
         return false;
      }
   }

   private FutureServletResponse getOriginalResponse(ServletResponse res) {
      while(res instanceof ServletResponseWrapper) {
         res = ((ServletResponseWrapper)res).getResponse();
      }

      if (res == null) {
         throw new AssertionError("Original response not available");
      } else {
         return (FutureServletResponse)res;
      }
   }
}
