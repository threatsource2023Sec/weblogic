package com.asn1c.core;

import java.io.PrintWriter;

public class Real64 extends Number implements Comparable, ASN1Object {
   public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
   public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
   public static final double NaN = Double.NaN;
   public static final double MAX_VALUE = Double.MAX_VALUE;
   public static final double MIN_VALUE;
   protected double value;

   public Real64() {
      this.value = 0.0;
   }

   public Real64(double var1) {
      this.value = var1;
   }

   public Real64(Real64 var1) {
      this.value = var1.value;
   }

   public Real64(Double var1) {
      this.value = var1;
   }

   public Real64(String var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1);
   }

   public Real64(String16 var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1.toString());
   }

   public Real64(String32 var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1.toString());
   }

   public static String toString(double var0) {
      return Double.toString(var0);
   }

   public static Real64 valueOf(String var0) throws NumberFormatException {
      return new Real64(var0);
   }

   public static Real64 valueOf(String16 var0) throws NumberFormatException {
      return new Real64(var0.toString());
   }

   public static Real64 valueOf(String32 var0) throws NumberFormatException {
      return new Real64(var0.toString());
   }

   public static double parseDouble(String var0) throws NumberFormatException {
      return Double.parseDouble(var0);
   }

   public static double parseDouble(String16 var0) throws NumberFormatException {
      return Double.parseDouble(var0.toString());
   }

   public static double parseDouble(String32 var0) throws NumberFormatException {
      return Double.parseDouble(var0.toString());
   }

   public static boolean isNaN(double var0) {
      return Double.isNaN(var0);
   }

   public static boolean isInfinite(double var0) {
      return Double.isInfinite(var0);
   }

   public boolean isNaN() {
      return Double.isNaN(this.value);
   }

   public boolean isInfinite() {
      return Double.isInfinite(this.value);
   }

   public String toString() {
      return Double.toString(this.value);
   }

   public byte byteValue() {
      return (byte)((int)this.value);
   }

   public short shortValue() {
      return (short)((int)this.value);
   }

   public int intValue() {
      return (int)this.value;
   }

   public long longValue() {
      return (long)this.value;
   }

   public float floatValue() {
      return (float)this.value;
   }

   public double doubleValue() {
      return this.value;
   }

   public double getValue() {
      return this.value;
   }

   public void setValue(double var1) {
      this.value = var1;
   }

   public void setValue(Real64 var1) {
      this.value = var1.value;
   }

   public void setValue(Double var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1.toString());
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = Double.parseDouble(var1.toString());
   }

   public int hashCode() {
      long var1 = Double.doubleToLongBits(this.value);
      return (int)(var1 ^ var1 >>> 32);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Real64 && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(((Real64)var1).value);
   }

   public static long doubleToLongBits(double var0) {
      return Double.doubleToLongBits(var0);
   }

   public static long doubleToRawLongBits(double var0) {
      return Double.doubleToRawLongBits(var0);
   }

   public static double longBitsToDouble(long var0) {
      return Double.longBitsToDouble(var0);
   }

   public int compareTo(Real64 var1) {
      if (this.isNaN()) {
         return var1.isNaN() ? 0 : 1;
      } else if (var1.isNaN()) {
         return -1;
      } else if (this.value == 0.0 && var1.value == 0.0) {
         if (1.0 / this.value == Double.POSITIVE_INFINITY) {
            return 1.0 / var1.value == Double.POSITIVE_INFINITY ? 0 : 1;
         } else {
            return 1.0 / var1.value == Double.POSITIVE_INFINITY ? -1 : 0;
         }
      } else {
         return this.value < var1.value ? -1 : (this.value > var1.value ? 1 : 0);
      }
   }

   public int compareTo(Double var1) {
      if (this.isNaN()) {
         return var1.isNaN() ? 0 : 1;
      } else if (var1.isNaN()) {
         return -1;
      } else if (this.value == 0.0 && var1 == 0.0) {
         if (1.0 / this.value == Double.POSITIVE_INFINITY) {
            return 1.0 / var1 == Double.POSITIVE_INFINITY ? 0 : 1;
         } else {
            return 1.0 / var1 == Double.POSITIVE_INFINITY ? -1 : 0;
         }
      } else {
         return this.value < var1 ? -1 : (this.value > var1 ? 1 : 0);
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((Real64)var1);
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + this.toString() + var4);
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   static {
      MIN_VALUE = Double.MIN_VALUE;
   }
}
