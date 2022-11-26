package com.bea.core.repackaged.springframework.jmx.access;

import com.bea.core.repackaged.springframework.jmx.JmxException;

public class InvocationFailureException extends JmxException {
   public InvocationFailureException(String msg) {
      super(msg);
   }

   public InvocationFailureException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
