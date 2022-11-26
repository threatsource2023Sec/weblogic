package com.bea.core.repackaged.springframework.jmx;

import com.bea.core.repackaged.springframework.core.NestedRuntimeException;

public class JmxException extends NestedRuntimeException {
   public JmxException(String msg) {
      super(msg);
   }

   public JmxException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
