package com.bea.staxb.types;

import javax.xml.namespace.QName;

public class UnsignedByte extends UnsignedShort {
   public static final QName QNAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedByte");

   public UnsignedByte() {
   }

   public UnsignedByte(byte longValue) {
      super(longValue);
   }

   public UnsignedByte(String strValue) {
      super(strValue);
   }

   protected boolean isOutofRange(long value) {
      return value < 0L || value > 255L;
   }
}
