package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.utils.DiagnosticsUtils;
import java.util.List;
import java.util.Map;

class AttributeTerm implements Term {
   private Object base;
   private Object property;
   private Object value;

   public AttributeTerm(Object base, Object property, Object value) {
      this.base = base;
      this.property = property;
      this.value = value;
   }

   public boolean isMethod() {
      return false;
   }

   public boolean isTrackedValue() {
      return this.value != null && this.value instanceof TrackedValue;
   }

   public boolean isTraceable() {
      return this.value != null && this.value instanceof Traceable;
   }

   public boolean isArray() {
      return this.base != null && this.base.getClass().isArray();
   }

   public boolean isMap() {
      return this.base != null && this.base instanceof Map;
   }

   public boolean isList() {
      return this.base != null && this.base instanceof List;
   }

   public boolean isTerminalNode() {
      return this.isTrackedValue() || this.value != null && DiagnosticsUtils.isLeafValueType(this.value.getClass());
   }

   public boolean isRoot() {
      return this.base == null;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      if (!this.isArray() && !this.isList()) {
         if (this.isMap()) {
            buf.append('(').append(this.property).append(')');
         } else {
            buf.append(this.property);
         }
      } else {
         buf.append('[').append(this.property).append(']');
      }

      return buf.toString();
   }
}
