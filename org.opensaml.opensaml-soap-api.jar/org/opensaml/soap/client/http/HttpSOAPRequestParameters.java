package org.opensaml.soap.client.http;

import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.soap.client.SOAPClient;

@ThreadSafe
public class HttpSOAPRequestParameters implements SOAPClient.SOAPRequestParameters {
   public static final String SOAP_ACTION_HEADER = "SOAPAction";
   private String soapAction;

   public HttpSOAPRequestParameters(@Nullable String action) {
      this.soapAction = StringSupport.trimOrNull(action);
   }

   @Nullable
   public String getSOAPAction() {
      return this.soapAction;
   }
}
