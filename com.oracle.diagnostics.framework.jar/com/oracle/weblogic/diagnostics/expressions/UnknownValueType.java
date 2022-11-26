package com.oracle.weblogic.diagnostics.expressions;

public final class UnknownValueType extends AbstractTrackedValue {
   public static final UnknownValueType UNKNOWN_VALUE = new UnknownValueType();

   public UnknownValueType() {
      this((Traceable)null, (String)null, (String)null, (Object)null);
   }

   public UnknownValueType(Traceable parent, String instance, String pathToValue, Object wrappedValue) {
      super(parent, instance, pathToValue, wrappedValue);
   }

   public boolean isNull() {
      return this.getValue() == null;
   }
}
