package com.oracle.weblogic.diagnostics.expressions;

import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;

public class EvaluationContextHelper {
   private static AuditableThreadLocal evalContext = AuditableThreadLocalFactory.createThreadLocal();

   private EvaluationContextHelper() {
   }

   public static EvaluationContext getCurrentContext() {
      return (EvaluationContext)evalContext.get();
   }

   static void setCurrentContext(EvaluationContext ctx) {
      evalContext.set(ctx);
   }
}
