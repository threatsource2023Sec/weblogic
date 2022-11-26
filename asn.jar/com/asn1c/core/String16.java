package com.asn1c.core;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.Locale;

public class String16 implements ASN1Object, Serializable, Comparable {
   public static final Comparator CASE_INSENSITIVE_ORDER;
   protected String buffer;

   public String16() {
      this.buffer = "";
   }

   public String16(String var1) {
      this.buffer = var1;
   }

   public String16(String16 var1) {
      this.buffer = var1.buffer;
   }

   public String16(String32 var1) {
      this.buffer = var1.toString();
   }

   public String16(char[] var1) {
      this.buffer = new String(var1);
   }

   public String16(char[] var1, int var2, int var3) {
      this.buffer = new String(var1, var2, var3);
   }

   public String16(byte[] var1, int var2, int var3, String var4) throws UnsupportedEncodingException {
      this.buffer = new String(var1, var2, var3, var4);
   }

   public String16(byte[] var1, String var2) throws UnsupportedEncodingException {
      this.buffer = new String(var1, var2);
   }

   public String16(byte[] var1, int var2, int var3) {
      this.buffer = new String(var1, var2, var3);
   }

   public String16(byte[] var1) {
      this.buffer = new String(var1);
   }

   public String16(StringBuffer var1) {
      this.buffer = new String(var1);
   }

   public int length() {
      return this.buffer.length();
   }

   public char charAt(int var1) {
      return this.buffer.charAt(var1);
   }

   public void getChars(int var1, int var2, char[] var3, int var4) {
      this.buffer.getChars(var1, var2, var3, var4);
   }

   public byte[] getBytes(String var1) throws UnsupportedEncodingException {
      return this.buffer.getBytes(var1);
   }

   public byte[] getBytes() {
      return this.buffer.getBytes();
   }

   public void setValue(String var1) {
      this.buffer = var1;
   }

   public void setValue(String16 var1) {
      this.buffer = var1.buffer;
   }

   public void setValue(String32 var1) {
      this.buffer = var1.toString();
   }

   public void setValue(char[] var1) {
      this.buffer = new String(var1);
   }

   public void setValue(char[] var1, int var2, int var3) {
      this.buffer = new String(var1, var2, var3);
   }

   public void setValue(byte[] var1, int var2, int var3, String var4) throws UnsupportedEncodingException {
      this.buffer = new String(var1, var2, var3, var4);
   }

   public void setValue(byte[] var1, String var2) throws UnsupportedEncodingException {
      this.buffer = new String(var1, var2);
   }

   public void setValue(byte[] var1, int var2, int var3) {
      this.buffer = new String(var1, var2, var3);
   }

   public void setValue(byte[] var1) {
      this.buffer = new String(var1);
   }

   public void setValue(StringBuffer var1) {
      this.buffer = new String(var1);
   }

   public boolean equals(Object var1) {
      return var1 != null && var1 instanceof String16 && this.buffer.equals(((String16)var1).buffer);
   }

   public boolean equals(String var1) {
      return this.buffer.equals(var1);
   }

   public boolean equalsIgnoreCase(String16 var1) {
      return this.buffer.equalsIgnoreCase(var1.buffer);
   }

   public boolean equalsIgnoreCase(String var1) {
      return this.buffer.equalsIgnoreCase(var1);
   }

   public int compareTo(String16 var1) {
      return this.buffer.compareTo(var1.buffer);
   }

   public int compareTo(String var1) {
      return this.buffer.compareTo(var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo((String16)var1);
   }

   public int compareToIgnoreCase(String16 var1) {
      return this.buffer.compareToIgnoreCase(var1.buffer);
   }

   public int compareToIgnoreCase(String var1) {
      return this.buffer.compareToIgnoreCase(var1);
   }

   public boolean regionMatches(int var1, String16 var2, int var3, int var4) {
      return this.buffer.regionMatches(var1, var2.buffer, var3, var4);
   }

   public boolean regionMatches(int var1, String var2, int var3, int var4) {
      return this.buffer.regionMatches(var1, var2, var3, var4);
   }

   public boolean regionMatches(boolean var1, int var2, String16 var3, int var4, int var5) {
      return this.buffer.regionMatches(var1, var2, var3.buffer, var4, var5);
   }

   public boolean regionMatches(boolean var1, int var2, String var3, int var4, int var5) {
      return this.buffer.regionMatches(var1, var2, var3, var4, var5);
   }

   public boolean startsWith(String16 var1, int var2) {
      return this.buffer.startsWith(var1.buffer, var2);
   }

   public boolean startsWith(String var1, int var2) {
      return this.buffer.startsWith(var1, var2);
   }

   public boolean startsWith(String16 var1) {
      return this.buffer.startsWith(var1.buffer);
   }

   public boolean startsWith(String var1) {
      return this.buffer.startsWith(var1);
   }

   public boolean endsWith(String16 var1) {
      return this.buffer.endsWith(var1.buffer);
   }

   public boolean endsWith(String var1) {
      return this.buffer.endsWith(var1);
   }

   public int hashCode() {
      return this.buffer.hashCode();
   }

   public int indexOf(int var1) {
      return this.buffer.indexOf(var1);
   }

   public int indexOf(int var1, int var2) {
      return this.buffer.indexOf(var1, var2);
   }

   public int lastIndexOf(int var1) {
      return this.buffer.lastIndexOf(var1);
   }

   public int lastIndexOf(int var1, int var2) {
      return this.buffer.lastIndexOf(var1, var2);
   }

   public int indexOf(String16 var1) {
      return this.buffer.indexOf(var1.buffer);
   }

   public int indexOf(String var1) {
      return this.buffer.indexOf(var1);
   }

   public int indexOf(String16 var1, int var2) {
      return this.buffer.indexOf(var1.buffer, var2);
   }

   public int indexOf(String var1, int var2) {
      return this.buffer.indexOf(var1, var2);
   }

   public int lastIndexOf(String16 var1) {
      return this.buffer.lastIndexOf(var1.buffer);
   }

   public int lastIndexOf(String var1) {
      return this.buffer.lastIndexOf(var1);
   }

   public int lastIndexOf(String16 var1, int var2) {
      return this.buffer.lastIndexOf(var1.buffer, var2);
   }

   public int lastIndexOf(String var1, int var2) {
      return this.buffer.lastIndexOf(var1, var2);
   }

   public String16 substring(int var1) {
      return new String16(this.buffer.substring(var1));
   }

   public String16 substring(int var1, int var2) {
      return new String16(this.buffer.substring(var1, var2));
   }

   public String16 concat(String16 var1) {
      return new String16(this.buffer.concat(var1.buffer));
   }

   public String16 concat(String var1) {
      return new String16(this.buffer.concat(var1));
   }

   public String16 replace(char var1, char var2) {
      return new String16(this.buffer.replace(var1, var2));
   }

   public String16 toLowerCase(Locale var1) {
      return new String16(this.buffer.toLowerCase(var1));
   }

   public String16 toLowerCase() {
      return new String16(this.buffer.toLowerCase());
   }

   public String16 toUpperCase(Locale var1) {
      return new String16(this.buffer.toUpperCase(var1));
   }

   public String16 toUpperCase() {
      return new String16(this.buffer.toUpperCase());
   }

   public String16 trim() {
      return new String16(this.buffer.trim());
   }

   public String toString() {
      return this.buffer;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      int var7 = this.buffer.length();
      StringBuffer var9 = new StringBuffer(var7 + 2);
      boolean var10 = false;

      for(int var6 = 0; var6 < var7; ++var6) {
         char var8 = this.buffer.charAt(var6);
         if (var8 >= ' ' && var8 < 127) {
            if (!var10) {
               if (var6 > 0) {
                  var9.append(',');
               }

               var9.append(" \"");
               var10 = true;
            }

            if (var8 == '"') {
               var9.append(var8);
            }

            var9.append(var8);
         } else if (var8 < 128) {
            if (var10) {
               var9.append("\"");
               var10 = false;
            }

            if (var6 > 0) {
               var9.append(',');
            }

            var9.append(" { ").append(var8 / 16).append(", ").append(var8 & 15).append(" }");
         } else {
            if (var10) {
               var9.append("\"");
               var10 = false;
            }

            if (var6 > 0) {
               var9.append(',');
            }

            var9.append(" { 0, 0, ").append(var8 / 16).append(", ").append(var8 & 15).append(" }");
         }
      }

      if (var10) {
         var9.append("\"");
      }

      var1.println(var2 + var3 + "{" + var9.toString() + " }" + var4);
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public char[] toCharArray() {
      return this.buffer.toCharArray();
   }

   public static String16 valueOf(Object var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(char[] var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(char[] var0, int var1, int var2) {
      return new String16(String.valueOf(var0, var1, var2));
   }

   public static String16 copyValueOf(char[] var0, int var1, int var2) {
      return new String16(String.copyValueOf(var0, var1, var2));
   }

   public static String16 copyValueOf(char[] var0) {
      return new String16(String.copyValueOf(var0));
   }

   public static String16 valueOf(boolean var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(char var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(int var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(long var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(float var0) {
      return new String16(String.valueOf(var0));
   }

   public static String16 valueOf(double var0) {
      return new String16(String.valueOf(var0));
   }

   public String intern() {
      return this.buffer.intern();
   }

   static {
      CASE_INSENSITIVE_ORDER = String.CASE_INSENSITIVE_ORDER;
   }
}
