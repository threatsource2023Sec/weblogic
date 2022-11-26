package com.oracle.weblogic.diagnostics.expressions;

import java.util.HashMap;
import java.util.Map;

public class EvaluationContext {
   private DiagnosticsELContext context;

   protected EvaluationContext(DiagnosticsELContext elContext) {
      this.context = elContext;
   }

   public Map getEvaluatedData() {
      HashMap result = new HashMap();
      if (this.context != null) {
         Map resolvedValuesMap = this.context.getResolvedValues();
         result.putAll(resolvedValuesMap);
      }

      return result;
   }

   public DiagnosticsELContext getELContext() {
      return this.context;
   }

   void setELContext(DiagnosticsELContext context) {
      this.context = context;
   }
}
