package com.bea.staxb.types;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class UnsignedLong extends Number implements DotNetType, Serializable {
   public static final QName QNAME = new QName("http://www.w3.org/2001/XMLSchema", "unsignedLong");
   protected BigInteger value;
   private static BigInteger MAX = new BigInteger("18446744073709551615");

   public UnsignedLong(BigInteger longValue) {
      this.setValue(longValue);
   }

   public UnsignedLong(String strValue) {
      this.valueOf(strValue);
   }

   public UnsignedLong() {
      this.setValue(BigInteger.ZERO);
   }

   public void valueOf(String value) {
      this.setValue(new BigInteger(value));
   }

   public String getStringValue() {
      return this.value.toString();
   }

   public void setValue(BigInteger value) {
      if (value.compareTo(BigInteger.ZERO) != -1 && value.compareTo(MAX) != 1) {
         this.value = value;
      } else {
         throw new NumberFormatException(value + " is out of range of " + this.getClass().getName());
      }
   }

   public int intValue() {
      return this.value.intValue();
   }

   public long longValue() {
      return this.value.longValue();
   }

   public float floatValue() {
      return this.value.floatValue();
   }

   public double doubleValue() {
      return this.value.doubleValue();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         UnsignedLong that = (UnsignedLong)o;
         return this.value.equals(that.value);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value.hashCode();
   }
}
