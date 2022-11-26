package com.asn1c.core;

import java.io.PrintWriter;

public class Bool implements ASN1Object, Comparable {
   public static final Bool FALSE = new Bool(false);
   public static final Bool TRUE = new Bool(true);
   protected boolean value;

   public Bool() {
      this.value = false;
   }

   public Bool(boolean var1) {
      this.value = var1;
   }

   public Bool(Bool var1) {
      this.value = var1.value;
   }

   public Bool(Boolean var1) {
      this.value = var1;
   }

   public Bool(String16 var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public Bool(String32 var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public Bool(String var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public boolean booleanValue() {
      return this.value;
   }

   public boolean getValue() {
      return this.value;
   }

   public void setValue(boolean var1) {
      this.value = var1;
   }

   public void setValue(Bool var1) {
      this.value = var1.value;
   }

   public void setValue(Boolean var1) {
      this.value = var1;
   }

   public void setValue(String16 var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public void setValue(String32 var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public void setValue(String var1) {
      this.value = var1 != null && var1.equalsIgnoreCase("true");
   }

   public static Bool valueOf(String16 var0) {
      return new Bool(var0);
   }

   public static Bool valueOf(String32 var0) {
      return new Bool(var0);
   }

   public static Bool valueOf(String var0) {
      return new Bool(var0);
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + (this.value ? "TRUE" : "FALSE") + var4);
   }

   public String toString() {
      return this.value ? "TRUE" : "FALSE";
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public int hashCode() {
      return this.value ? 1231 : 1237;
   }

   public int compareTo(Object var1) {
      Bool var2 = (Bool)var1;
      if (this.value == var2.value) {
         return 0;
      } else {
         return this.value ? 1 : -1;
      }
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof Bool && this.value == ((Bool)var1).value;
   }

   public static boolean getBoolean(String16 var0) {
      return Boolean.getBoolean(var0.toString());
   }

   public static boolean getBoolean(String32 var0) {
      return Boolean.getBoolean(var0.toString());
   }

   public static boolean getBoolean(String var0) {
      return Boolean.getBoolean(var0);
   }
}
