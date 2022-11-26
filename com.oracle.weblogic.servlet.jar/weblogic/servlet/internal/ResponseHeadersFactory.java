package weblogic.servlet.internal;

import weblogic.servlet.http2.Http2ResponseHeaders;

class ResponseHeadersFactory {
   public static AbstractResponseHeaders createResponseHeaders(ServletResponseImpl response) {
      ServletRequestImpl request = response.getRequest();
      if (request != null && request.getInputHelper() != null && request.getInputHelper().getRequestParser() != null) {
         return (AbstractResponseHeaders)(request.getInputHelper().getRequestParser().isProtocolVersion_2() ? new Http2ResponseHeaders(response) : new ResponseHeaders(response));
      } else {
         return new ResponseHeaders(response);
      }
   }
}
