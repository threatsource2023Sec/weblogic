package com.bea.staxb.types;

import java.io.Serializable;
import javax.xml.namespace.QName;

public class UnsignedInt extends Number implements DotNetType, Serializable {
   protected Long longValue;
   public static final QName QNAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedInt");

   public UnsignedInt(long longValue) {
      this.setValue(longValue);
   }

   public UnsignedInt(String strValue) {
      this.setValue(Long.parseLong(strValue));
   }

   public UnsignedInt() {
      this.setValue(0L);
   }

   public void valueOf(String value) {
      this.setValue(Long.parseLong(value));
   }

   public String getStringValue() {
      return this.longValue.toString();
   }

   public void setValue(long longValue) {
      if (this.isOutofRange(longValue)) {
         throw new NumberFormatException(longValue + " is out of range of " + this.getClass().getName());
      } else {
         this.longValue = new Long(longValue);
      }
   }

   protected boolean isOutofRange(long value) {
      return value < 0L || value > 4294967295L;
   }

   public int intValue() {
      return this.longValue.intValue();
   }

   public long longValue() {
      return this.longValue;
   }

   public float floatValue() {
      return this.longValue.floatValue();
   }

   public double doubleValue() {
      return this.longValue.doubleValue();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         UnsignedInt that = (UnsignedInt)o;
         return this.longValue.equals(that.longValue);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.longValue.hashCode();
   }
}
