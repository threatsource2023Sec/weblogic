package com.asn1c.core;

import java.io.PrintWriter;

public class Int64 extends Number implements Comparable, ASN1Object {
   public static final long MIN_VALUE = Long.MIN_VALUE;
   public static final long MAX_VALUE = Long.MAX_VALUE;
   protected long value;

   public Int64() {
      this.value = 0L;
   }

   public Int64(long var1) {
      this.value = var1;
   }

   public Int64(Int64 var1) {
      this.value = var1.value;
   }

   public Int64(Long var1) {
      this.value = var1;
   }

   public Int64(String var1) throws NumberFormatException {
      this.value = parseLong(var1);
   }

   public Int64(String16 var1) throws NumberFormatException {
      this.value = parseLong(var1.toString());
   }

   public Int64(String32 var1) throws NumberFormatException {
      this.value = parseLong(var1.toString());
   }

   public void setValue(long var1) {
      this.value = var1;
   }

   public void setValue(Int64 var1) {
      this.value = var1.value;
   }

   public void setValue(Long var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = parseLong(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = parseLong(var1.toString());
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = parseLong(var1.toString());
   }

   public static String16 toString16(long var0) {
      return new String16(Long.toString(var0));
   }

   public static String32 toString32(long var0) {
      return new String32(Long.toString(var0));
   }

   public static String toString(long var0) {
      return Long.toString(var0);
   }

   public static long parseLong(String var0) throws NumberFormatException {
      return Long.parseLong(var0);
   }

   public static long parseLong(String16 var0) throws NumberFormatException {
      return Long.parseLong(var0.toString());
   }

   public static long parseLong(String32 var0) throws NumberFormatException {
      return Long.parseLong(var0.toString());
   }

   public static long parseLong(String var0, int var1) throws NumberFormatException {
      return Long.parseLong(var0, var1);
   }

   public static long parseLong(String16 var0, int var1) throws NumberFormatException {
      return Long.parseLong(var0.toString(), var1);
   }

   public static long parseLong(String32 var0, int var1) throws NumberFormatException {
      return Long.parseLong(var0.toString(), var1);
   }

   public static Int64 valueOf(String var0, int var1) throws NumberFormatException {
      return new Int64(Long.parseLong(var0, var1));
   }

   public static Int64 valueOf(String16 var0, int var1) throws NumberFormatException {
      return new Int64(Long.parseLong(var0.toString(), var1));
   }

   public static Int64 valueOf(String32 var0, int var1) throws NumberFormatException {
      return new Int64(Long.parseLong(var0.toString(), var1));
   }

   public static Int64 valueOf(String var0) throws NumberFormatException {
      return new Int64(Long.parseLong(var0));
   }

   public static Int64 valueOf(String16 var0) throws NumberFormatException {
      return new Int64(Long.parseLong(var0.toString()));
   }

   public static Int64 valueOf(String32 var0) throws NumberFormatException {
      return new Int64(Long.parseLong(var0.toString()));
   }

   public static Int64 decode(String var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String)var0.substring(1), 8) : valueOf((String)var0, 10);
      }
   }

   public static Int64 decode(String16 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String16)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String16)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String16)var0.substring(1), 8) : valueOf((String16)var0, 10);
      }
   }

   public static Int64 decode(String32 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String32)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String32)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String32)var0.substring(1), 8) : valueOf((String32)var0, 10);
      }
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
      return this.value;
   }

   public float floatValue() {
      return (float)this.value;
   }

   public double doubleValue() {
      return (double)this.value;
   }

   public long getValue() {
      return this.value;
   }

   public String toString() {
      return Long.toString(this.value);
   }

   public int hashCode() {
      return (int)(this.value ^ this.value >>> 32);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Int64 && this.value == ((Int64)var1).value;
   }

   public int compareTo(Long var1) {
      return this.value < var1 ? -1 : (this.value > var1 ? 1 : 0);
   }

   public int compareTo(Int64 var1) {
      return this.value < var1.value ? -1 : (this.value > var1.value ? 1 : 0);
   }

   public int compareTo(Object var1) {
      return this.compareTo((Int64)var1);
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
