package com.bea.security.xacml.cache.role;

import com.bea.common.security.xacml.DocumentParseException;

public class MultipleRoleTargetException extends DocumentParseException {
   public MultipleRoleTargetException(Throwable cause) {
      super(cause);
   }

   public MultipleRoleTargetException(String msg) {
      super(msg);
   }

   public MultipleRoleTargetException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
