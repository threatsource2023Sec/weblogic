package com.asn1c.core;

import java.io.PrintWriter;

public class Int16 extends Number implements Comparable, ASN1Object {
   public static final short MIN_VALUE = Short.MIN_VALUE;
   public static final short MAX_VALUE = Short.MAX_VALUE;
   protected short value;

   public Int16() {
      this.value = 0;
   }

   public Int16(short var1) {
      this.value = var1;
   }

   public Int16(Int16 var1) {
      this.value = var1.value;
   }

   public Int16(Short var1) {
      this.value = var1;
   }

   public Int16(String var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public Int16(String16 var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public Int16(String32 var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public void setValue(short var1) {
      this.value = var1;
   }

   public void setValue(Int16 var1) {
      this.value = var1.value;
   }

   public void setValue(Short var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = parseShort(var1);
   }

   public static String16 toString16(short var0) {
      return new String16(Short.toString(var0));
   }

   public static String32 toString32(short var0) {
      return new String32(Short.toString(var0));
   }

   public static String toString(short var0) {
      return Short.toString(var0);
   }

   public static short parseShort(String var0) throws NumberFormatException {
      return Short.parseShort(var0);
   }

   public static short parseShort(String16 var0) throws NumberFormatException {
      return Short.parseShort(var0.toString());
   }

   public static short parseShort(String32 var0) throws NumberFormatException {
      return Short.parseShort(var0.toString());
   }

   public static short parseShort(String var0, int var1) throws NumberFormatException {
      return Short.parseShort(var0, var1);
   }

   public static short parseShort(String16 var0, int var1) throws NumberFormatException {
      return Short.parseShort(var0.toString(), var1);
   }

   public static short parseShort(String32 var0, int var1) throws NumberFormatException {
      return Short.parseShort(var0.toString(), var1);
   }

   public static Int16 valueOf(String var0, int var1) throws NumberFormatException {
      return new Int16(Short.parseShort(var0, var1));
   }

   public static Int16 valueOf(String16 var0, int var1) throws NumberFormatException {
      return new Int16(Short.parseShort(var0.toString(), var1));
   }

   public static Int16 valueOf(String32 var0, int var1) throws NumberFormatException {
      return new Int16(Short.parseShort(var0.toString(), var1));
   }

   public static Int16 valueOf(String var0) throws NumberFormatException {
      return new Int16(Short.parseShort(var0));
   }

   public static Int16 valueOf(String16 var0) throws NumberFormatException {
      return new Int16(Short.parseShort(var0.toString()));
   }

   public static Int16 valueOf(String32 var0) throws NumberFormatException {
      return new Int16(Short.parseShort(var0.toString()));
   }

   public static Int16 decode(String var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String)var0.substring(1), 8) : valueOf((String)var0, 10);
      }
   }

   public static Int16 decode(String16 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String16)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String16)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String16)var0.substring(1), 8) : valueOf((String16)var0, 10);
      }
   }

   public static Int16 decode(String32 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String32)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String32)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String32)var0.substring(1), 8) : valueOf((String32)var0, 10);
      }
   }

   public byte byteValue() {
      return (byte)this.value;
   }

   public short shortValue() {
      return this.value;
   }

   public int intValue() {
      return this.value;
   }

   public long longValue() {
      return (long)this.value;
   }

   public float floatValue() {
      return (float)this.value;
   }

   public double doubleValue() {
      return (double)this.value;
   }

   public short getValue() {
      return this.value;
   }

   public String toString() {
      return Short.toString(this.value);
   }

   public int hashCode() {
      return this.value ^ this.value >>> 32;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Int16 && this.value == ((Int16)var1).value;
   }

   public int compareTo(Short var1) {
      return this.value - var1;
   }

   public int compareTo(Object var1) {
      Int16 var2 = (Int16)var1;
      return this.value - var2.value;
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
