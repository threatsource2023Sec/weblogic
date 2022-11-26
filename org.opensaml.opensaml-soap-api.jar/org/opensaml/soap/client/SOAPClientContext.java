package org.opensaml.soap.client;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;

public class SOAPClientContext extends BaseContext {
   private SOAPClient.SOAPRequestParameters requestParameters;

   @Nullable
   public SOAPClient.SOAPRequestParameters getSOAPRequestParameters() {
      return this.requestParameters;
   }

   public void setSOAPRequestParameters(@Nullable SOAPClient.SOAPRequestParameters parameters) {
      this.requestParameters = parameters;
   }
}
