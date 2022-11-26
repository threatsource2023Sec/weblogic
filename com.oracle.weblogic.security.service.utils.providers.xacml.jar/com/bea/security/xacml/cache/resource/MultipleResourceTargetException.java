package com.bea.security.xacml.cache.resource;

import com.bea.common.security.xacml.DocumentParseException;

public class MultipleResourceTargetException extends DocumentParseException {
   public MultipleResourceTargetException(Throwable cause) {
      super(cause);
   }

   public MultipleResourceTargetException(String msg) {
      super(msg);
   }

   public MultipleResourceTargetException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
