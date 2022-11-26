package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;

public class ExpressionExtensionBeanNotFound extends ExpressionBeanRuntimeException {
   private static final transient DiagnosticsFrameworkTextTextFormatter textFormatter = DiagnosticsFrameworkTextTextFormatter.getInstance();
   private static final long serialVersionUID = 6783215477203103222L;

   public ExpressionExtensionBeanNotFound() {
   }

   public ExpressionExtensionBeanNotFound(String message) {
      super(message);
   }

   public ExpressionExtensionBeanNotFound(Throwable cause) {
      super(cause);
   }

   public ExpressionExtensionBeanNotFound(String message, Throwable cause) {
      super(message, cause);
   }

   public ExpressionExtensionBeanNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public ExpressionExtensionBeanNotFound(String namespace, String beanName) {
      super(textFormatter.getExpressionExtensionBeanNotFound(namespace, beanName));
   }
}
