package com.bea.common.security.xacml;

public class InvalidAttributeException extends DocumentParseException {
   private static final long serialVersionUID = 3763098578632913456L;

   public InvalidAttributeException(Throwable cause) {
      super(cause);
   }

   public InvalidAttributeException(String msg) {
      super(msg);
   }
}
