package com.bea.core.repackaged.aspectj.weaver;

public class AnnotationNameValuePair {
   private String name;
   private AnnotationValue val;

   public AnnotationNameValuePair(String name, AnnotationValue val) {
      this.name = name;
      this.val = val;
   }

   public String getName() {
      return this.name;
   }

   public AnnotationValue getValue() {
      return this.val;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.name + "=" + this.val.toString());
      return sb.toString();
   }

   public String stringify() {
      StringBuffer sb = new StringBuffer();
      if (!this.name.equals("value")) {
         sb.append(this.name + "=");
      }

      sb.append(this.val.stringify());
      return sb.toString();
   }
}
