package com.bea.common.security.xacml;

public class URISyntaxException extends XACMLException {
   private static final long serialVersionUID = 6136776534657235034L;

   public URISyntaxException(java.net.URISyntaxException cause) {
      super((Throwable)cause);
   }
}
