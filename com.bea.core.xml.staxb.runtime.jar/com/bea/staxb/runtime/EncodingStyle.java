package com.bea.staxb.runtime;

public final class EncodingStyle {
   public static final EncodingStyle SOAP11 = new EncodingStyle("SOAP11");
   public static final EncodingStyle SOAP12 = new EncodingStyle("SOAP12");
   private final String myName;

   private EncodingStyle(String name) {
      this.myName = name;
   }

   public String toString() {
      return this.myName;
   }
}
