package org.apache.taglibs.standard.lang.jstl.test;

public class Bean2 {
   String mValue;

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String pValue) {
      this.mValue = pValue;
   }

   public Bean2(String pValue) {
      this.mValue = pValue;
   }

   public String toString() {
      return "Bean2[" + this.mValue + "]";
   }
}
