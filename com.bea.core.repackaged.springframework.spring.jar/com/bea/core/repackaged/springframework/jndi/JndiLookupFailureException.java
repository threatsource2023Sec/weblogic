package com.bea.core.repackaged.springframework.jndi;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;
import javax.naming.NamingException;

public class JndiLookupFailureException extends NestedRuntimeException {
   public JndiLookupFailureException(String msg, NamingException cause) {
      super(msg, cause);
   }
}
