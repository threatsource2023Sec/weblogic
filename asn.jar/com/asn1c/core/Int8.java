package com.asn1c.core;

import java.io.PrintWriter;

public class Int8 extends Number implements Comparable, ASN1Object {
   public static final byte MIN_VALUE = -128;
   public static final byte MAX_VALUE = 127;
   protected byte value;

   public Int8() {
      this.value = 0;
   }

   public Int8(byte var1) {
      this.value = var1;
   }

   public Int8(Int8 var1) {
      this.value = var1.value;
   }

   public Int8(Byte var1) {
      this.value = var1;
   }

   public Int8(String var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public Int8(String16 var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public Int8(String32 var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public void setValue(byte var1) {
      this.value = var1;
   }

   public void setValue(Int8 var1) {
      this.value = var1.value;
   }

   public void setValue(Byte var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = parseByte(var1);
   }

   public static String toString(byte var0) {
      return Byte.toString(var0);
   }

   public static String16 toString16(byte var0) {
      return new String16(Byte.toString(var0));
   }

   public static String32 toString32(byte var0) {
      return new String32(Byte.toString(var0));
   }

   public static byte parseByte(String var0) throws NumberFormatException {
      return Byte.parseByte(var0);
   }

   public static byte parseByte(String16 var0) throws NumberFormatException {
      return Byte.parseByte(var0.toString());
   }

   public static byte parseByte(String32 var0) throws NumberFormatException {
      return Byte.parseByte(var0.toString());
   }

   public static byte parseByte(String var0, int var1) throws NumberFormatException {
      return Byte.parseByte(var0, var1);
   }

   public static byte parseByte(String16 var0, int var1) throws NumberFormatException {
      return Byte.parseByte(var0.toString(), var1);
   }

   public static byte parseByte(String32 var0, int var1) throws NumberFormatException {
      return Byte.parseByte(var0.toString(), var1);
   }

   public static Int8 valueOf(String var0, int var1) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0, var1));
   }

   public static Int8 valueOf(String16 var0, int var1) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0.toString(), var1));
   }

   public static Int8 valueOf(String32 var0, int var1) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0.toString(), var1));
   }

   public static Int8 valueOf(String var0) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0));
   }

   public static Int8 valueOf(String16 var0) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0.toString()));
   }

   public static Int8 valueOf(String32 var0) throws NumberFormatException {
      return new Int8(Byte.parseByte(var0.toString()));
   }

   public static Int8 decode(String var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String)var0.substring(1), 8) : valueOf((String)var0, 10);
      }
   }

   public static Int8 decode(String16 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String16)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String16)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String16)var0.substring(1), 8) : valueOf((String16)var0, 10);
      }
   }

   public static Int8 decode(String32 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String32)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String32)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String32)var0.substring(1), 8) : valueOf((String32)var0, 10);
      }
   }

   public byte byteValue() {
      return this.value;
   }

   public short shortValue() {
      return (short)this.value;
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

   public byte getValue() {
      return this.value;
   }

   public String toString() {
      return Byte.toString(this.value);
   }

   public int hashCode() {
      return this.value ^ this.value >>> 32;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Int8 && this.value == ((Int8)var1).value;
   }

   public int compareTo(Int8 var1) {
      return this.value - var1.value;
   }

   public int compareTo(Byte var1) {
      return this.value - var1;
   }

   public int compareTo(Object var1) {
      return this.compareTo((Int8)var1);
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
