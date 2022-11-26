package com.asn1c.core;

import java.io.PrintWriter;

public class Int32 extends Number implements Comparable, ASN1Object {
   public static final int MIN_VALUE = Integer.MIN_VALUE;
   public static final int MAX_VALUE = Integer.MAX_VALUE;
   protected int value;

   public Int32() {
      this.value = 0;
   }

   public Int32(int var1) {
      this.value = var1;
   }

   public Int32(Int32 var1) {
      this.value = var1.value;
   }

   public Int32(Integer var1) {
      this.value = var1;
   }

   public Int32(String var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public Int32(String16 var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public Int32(String32 var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public void setValue(int var1) {
      this.value = var1;
   }

   public void setValue(Int32 var1) {
      this.value = var1.value;
   }

   public void setValue(Integer var1) {
      this.value = var1;
   }

   public void setValue(String var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public void setValue(String16 var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public void setValue(String32 var1) throws NumberFormatException {
      this.value = parseInt(var1);
   }

   public static String toString(int var0) {
      return Integer.toString(var0);
   }

   public static String16 toString16(int var0) {
      return new String16(Integer.toString(var0));
   }

   public static String32 toString32(int var0) {
      return new String32(Integer.toString(var0));
   }

   public static int parseInt(String var0) throws NumberFormatException {
      return Integer.parseInt(var0);
   }

   public static int parseInt(String16 var0) throws NumberFormatException {
      return Integer.parseInt(var0.toString());
   }

   public static int parseInt(String32 var0) throws NumberFormatException {
      return Integer.parseInt(var0.toString());
   }

   public static int parseInt(String var0, int var1) throws NumberFormatException {
      return Integer.parseInt(var0, var1);
   }

   public static int parseInt(String16 var0, int var1) throws NumberFormatException {
      return Integer.parseInt(var0.toString(), var1);
   }

   public static int parseInt(String32 var0, int var1) throws NumberFormatException {
      return Integer.parseInt(var0.toString(), var1);
   }

   public static Int32 valueOf(String var0, int var1) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0, var1));
   }

   public static Int32 valueOf(String16 var0, int var1) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0.toString(), var1));
   }

   public static Int32 valueOf(String32 var0, int var1) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0.toString(), var1));
   }

   public static Int32 valueOf(String var0) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0));
   }

   public static Int32 valueOf(String16 var0) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0.toString()));
   }

   public static Int32 valueOf(String32 var0) throws NumberFormatException {
      return new Int32(Integer.parseInt(var0.toString()));
   }

   public static Int32 decode(String var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String)var0.substring(1), 8) : valueOf((String)var0, 10);
      }
   }

   public static Int32 decode(String16 var0) throws NumberFormatException {
      if (var0.startsWith("0x")) {
         return valueOf((String16)var0.substring(2), 16);
      } else if (var0.startsWith("#")) {
         return valueOf((String16)var0.substring(1), 16);
      } else {
         return var0.startsWith("0") && var0.length() > 1 ? valueOf((String16)var0.substring(1), 8) : valueOf((String16)var0, 10);
      }
   }

   public static Int32 decode(String32 var0) throws NumberFormatException {
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

   public int getValue() {
      return this.value;
   }

   public String toString() {
      return Integer.toString(this.value);
   }

   public int hashCode() {
      return this.value ^ this.value >>> 32;
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Int32 && this.value == ((Int32)var1).value;
   }

   public int compareTo(Int32 var1) {
      return this.value < var1.value ? -1 : (this.value > var1.value ? 1 : 0);
   }

   public int compareTo(Integer var1) {
      return this.value < var1 ? -1 : (this.value > var1 ? 1 : 0);
   }

   public int compareTo(Object var1) {
      return this.compareTo((Int32)var1);
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
