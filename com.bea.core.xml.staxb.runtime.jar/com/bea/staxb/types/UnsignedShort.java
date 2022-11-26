package com.bea.staxb.types;

import javax.xml.namespace.QName;

public class UnsignedShort extends UnsignedInt {
   public static final QName QNAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedShort");

   public UnsignedShort(int intValue) {
      super((long)intValue);
   }

   public UnsignedShort(String strValue) {
      super(strValue);
   }

   public UnsignedShort() {
   }

   protected boolean isOutofRange(long value) {
      return value < 0L || value > 65535L;
   }
}
