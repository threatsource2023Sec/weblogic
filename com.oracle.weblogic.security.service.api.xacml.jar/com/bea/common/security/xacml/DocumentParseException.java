package com.bea.common.security.xacml;

public class DocumentParseException extends XACMLException {
   private static final long serialVersionUID = 6914789966923511546L;

   public DocumentParseException(Throwable cause) {
      super(cause);
   }

   public DocumentParseException(String msg) {
      super(msg);
   }

   public DocumentParseException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
