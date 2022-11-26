package com.asn1c.core;

import java.io.PrintWriter;

public class Real32 extends Number implements Comparable, ASN1Object {
   public static final float POSITIVE_INFINITY = Float.POSITIVE_INFINITY;
   public static final float NEGATIVE_INFINITY = Float.NEGATIVE_INFINITY;
   public static final float NaN = Float.NaN;
   public static final float MAX_VALUE = Float.MAX_VALUE;
   public static final float MIN_VALUE = Float.MIN_VALUE;
   protected float value;

   public Real32() {
      this.value = 0.0F;
   }

   public Real32(float var1) {
      this.value = var1;
   }

   public Real32(double var1) {
      this.value = (float)var1;
   }

   public Real32(Real32 var1) {
      this.value = var1.value;
   }

   public Real32(Float var1) {
      this.value = var1;
   }

   public Real32(String var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1);
   }

   public Real32(String16 var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1.toString());
   }

   public Real32(String32 var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1.toString());
   }

   public static String toString(float var0) {
      return Float.toString(var0);
   }

   public static Real32 valueOf(String var0) throws NumberFormatException {
      return new Real32(var0);
   }

   public static Real32 valueOf(String16 var0) throws NumberFormatException {
      return new Real32(var0);
   }

   public static Real32 valueOf(String32 var0) throws NumberFormatException {
      return new Real32(var0);
   }

   public static float parseFloat(String var0) throws NumberFormatException {
      return Float.parseFloat(var0);
   }

   public static float parseFloat(String16 var0) throws NumberFormatException {
      return Float.parseFloat(var0.toString());
   }

   public static float parseFloat(String32 var0) throws NumberFormatException {
      return Float.parseFloat(var0.toString());
   }

   public static boolean isNaN(float var0) {
      return Float.isNaN(var0);
   }

   public static boolean isInfinite(float var0) {
      return Float.isInfinite(var0);
   }

   public boolean isNaN() {
      return Float.isNaN(this.value);
   }

   public boolean isInfinite() {
      return Float.isInfinite(this.value);
   }

   public String toString() {
      return Float.toString(this.value);
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
      return this.value;
   }

   public double doubleValue() {
      return (double)this.value;
   }

   public float getValue() {
      return this.value;
   }

   public void setValue(float var1) {
      this.value = var1;
   }

   public void setValue(double var1) {
      this.value = (float)var1;
   }

   public void setValue(Real32 var1) {
      this.value = var1.value;
   }

   public void setValue(Float var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1.toString());
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = Float.parseFloat(var1.toString());
   }

   public int hashCode() {
      return Float.floatToIntBits(this.value);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Real32 && Float.floatToIntBits(this.value) == Float.floatToIntBits(((Real32)var1).value);
   }

   public static int floatToIntBits(float var0) {
      return Float.floatToIntBits(var0);
   }

   public static int floatToRawIntBits(float var0) {
      return Float.floatToRawIntBits(var0);
   }

   public static float intBitsToFloat(int var0) {
      return Float.intBitsToFloat(var0);
   }

   public int compareTo(Real32 var1) {
      if (this.isNaN()) {
         return var1.isNaN() ? 0 : 1;
      } else if (var1.isNaN()) {
         return -1;
      } else if (this.value == 0.0F && var1.value == 0.0F) {
         if (1.0F / this.value == Float.POSITIVE_INFINITY) {
            return 1.0F / var1.value == Float.POSITIVE_INFINITY ? 0 : 1;
         } else {
            return 1.0F / var1.value == Float.POSITIVE_INFINITY ? -1 : 0;
         }
      } else {
         return this.value < var1.value ? -1 : (this.value > var1.value ? 1 : 0);
      }
   }

   public int compareTo(Float var1) {
      if (this.isNaN()) {
         return var1.isNaN() ? 0 : 1;
      } else if (var1.isNaN()) {
         return -1;
      } else if (this.value == 0.0F && var1 == 0.0F) {
         if (1.0F / this.value == Float.POSITIVE_INFINITY) {
            return 1.0F / var1 == Float.POSITIVE_INFINITY ? 0 : 1;
         } else {
            return 1.0F / var1 == Float.POSITIVE_INFINITY ? -1 : 0;
         }
      } else {
         return this.value < var1 ? -1 : (this.value > var1 ? 1 : 0);
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((Real32)var1);
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
}
