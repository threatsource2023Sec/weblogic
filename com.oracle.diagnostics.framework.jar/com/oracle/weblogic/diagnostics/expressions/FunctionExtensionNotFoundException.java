package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.l10n.DiagnosticsFrameworkTextTextFormatter;

public class FunctionExtensionNotFoundException extends ExpressionExtensionRuntimeException {
   private static final DiagnosticsFrameworkTextTextFormatter txtFormatter = DiagnosticsFrameworkTextTextFormatter.getInstance();

   public FunctionExtensionNotFoundException() {
   }

   public FunctionExtensionNotFoundException(String message) {
      super(message);
   }

   public FunctionExtensionNotFoundException(Throwable cause) {
      super(cause);
   }

   public FunctionExtensionNotFoundException(String message, Throwable cause) {
      super(message, cause);
   }

   public FunctionExtensionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

   public FunctionExtensionNotFoundException(String prefix, String name) {
      super(txtFormatter.getFunctionExtensionNotFoundText(prefix, name));
   }
}
