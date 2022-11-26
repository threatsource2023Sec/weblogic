package com.oracle.weblogic.diagnostics.expressions;

public class StringTrackedValue extends AbstractTrackedValue implements Comparable {
   public StringTrackedValue(String instance, String pathToValue, String stringValue) {
      this((Traceable)null, instance, pathToValue, stringValue);
   }

   public StringTrackedValue(Traceable parent, String instance, String pathToValue, String stringValue) {
      super(parent, instance, pathToValue, stringValue);
   }

   public int compareTo(String o) {
      return ((String)this.getValue()).compareTo(o);
   }
}
