package com.bea.security.xacml;

import com.bea.common.security.xacml.XACMLException;

public class PolicyStoreException extends XACMLException {
   public PolicyStoreException(Throwable cause) {
      super(cause);
   }

   public PolicyStoreException(String msg) {
      super(msg);
   }
}
