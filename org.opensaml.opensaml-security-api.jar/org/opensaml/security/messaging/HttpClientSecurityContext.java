package org.opensaml.security.messaging;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;

public class HttpClientSecurityContext extends BaseContext {
   private HttpClientSecurityParameters securityParameters;

   @Nullable
   public HttpClientSecurityParameters getSecurityParameters() {
      return this.securityParameters;
   }

   public void setSecurityParameters(@Nullable HttpClientSecurityParameters parameters) {
      this.securityParameters = parameters;
   }
}
