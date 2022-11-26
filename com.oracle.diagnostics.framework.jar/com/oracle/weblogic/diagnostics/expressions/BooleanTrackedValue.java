package com.oracle.weblogic.diagnostics.expressions;

public class BooleanTrackedValue extends AbstractTrackedValue implements Comparable {
   public BooleanTrackedValue(String instance, Boolean b) {
      this(instance, (String)null, b);
   }

   public BooleanTrackedValue(String instance, String pathToValue, Boolean b) {
      this((Traceable)null, instance, pathToValue, b);
   }

   public BooleanTrackedValue(Traceable parent, String instance, String pathToValue, Boolean b) {
      super(parent, instance, pathToValue, b);
   }

   public String toString() {
      return this.getValue() == null ? "" : this.getValue().toString();
   }

   public Boolean valueOf() {
      return (Boolean)this.getValue();
   }

   public int compareTo(Boolean o) {
      return this.valueOf().compareTo(o);
   }
}
