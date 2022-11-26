package com.oracle.weblogic.diagnostics.watch.beans.jmx;

import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;

public class JMXExpressionBeanRuntimeException extends ExpressionBeanRuntimeException {
   public JMXExpressionBeanRuntimeException() {
   }

   public JMXExpressionBeanRuntimeException(String message) {
      super(message);
   }

   public JMXExpressionBeanRuntimeException(Throwable cause) {
      super(cause);
   }

   public JMXExpressionBeanRuntimeException(String message, Throwable cause) {
      super(message, cause);
   }
}
