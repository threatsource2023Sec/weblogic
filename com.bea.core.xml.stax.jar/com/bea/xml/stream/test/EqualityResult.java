package com.bea.xml.stream.test;

public class EqualityResult {
   boolean value;
   String message;

   public EqualityResult(boolean equal, String message) {
      this.value = equal;
      this.message = message;
   }

   public boolean getValue() {
      return this.value;
   }

   public String getMessage() {
      return this.message;
   }

   public String toString() {
      return this.value ? "EQUAL with Message:[" + this.message + "]" : "NOT EQUAL with Message:[" + this.message + "]";
   }
}
