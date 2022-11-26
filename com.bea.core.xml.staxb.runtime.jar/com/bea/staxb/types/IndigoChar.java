package com.bea.staxb.types;

import javax.xml.namespace.QName;

public class IndigoChar extends UnsignedInt {
   public static final QName QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");

   public IndigoChar() {
      super(0L);
   }

   public IndigoChar(int value) {
      super((long)value);
   }

   public IndigoChar(String value) {
      super(value);
   }

   protected boolean isOutofRange(long value) {
      return value > 65535L || value < 0L;
   }
}
