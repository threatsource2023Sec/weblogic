package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;

class MethodTerm implements Term {
   private Object method;
   private Object[] params;
   private Object value;

   public MethodTerm(Object method, Object[] params, Object result) {
      this.method = method;
      this.params = params;
      this.value = result;
   }

   public boolean isArray() {
      return false;
   }

   public boolean isMap() {
      return false;
   }

   public boolean isList() {
      return false;
   }

   public boolean isTerminalNode() {
      return this.isTrackedValue() || this.value != null && DiagnosticsUtils.isLeafValueType(this.value.getClass());
   }

   public boolean isRoot() {
      return false;
   }

   public Object getValue() {
      return this.value;
   }

   public boolean isMethod() {
      return true;
   }

   public boolean isTrackedValue() {
      return this.value != null && this.value instanceof TrackedValue;
   }

   public boolean isTraceable() {
      return false;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (!this.isRoot()) {
         buf.append('.');
      }

      buf.append(this.method.toString()).append('(');

      for(int i = 0; i < this.params.length; ++i) {
         Object param = this.params[i];
         buf.append(param);
         if (i < this.params.length - 1) {
            buf.append(',');
         }
      }

      buf.append(')');
      return buf.toString();
   }
}
