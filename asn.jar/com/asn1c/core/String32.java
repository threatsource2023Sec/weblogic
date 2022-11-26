package com.asn1c.core;

import java.io.CharConversionException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class String32 implements ASN1Object, Serializable, Cloneable, Comparable {
   private String32Buffer buffer;

   public String32() {
      this.buffer = new String32Buffer();
   }

   public String32(String32 var1) {
      this.buffer = new String32Buffer(var1);
   }

   public String32(String16 var1) {
      this.buffer = new String32Buffer(var1);
   }

   public String32(String var1) {
      this.buffer = new String32Buffer(var1);
   }

   public String32(int[] var1) {
      this.buffer = new String32Buffer(var1.length);
      int var2 = var1.length;

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, var1[var2]);
      }

   }

   public String32(char[] var1) {
      this.buffer = new String32Buffer(var1.length);
      int var2 = var1.length;

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, var1[var2]);
      }

   }

   public String32(int[] var1, int var2, int var3) {
      this.buffer = new String32Buffer(var3);
      int var4 = var3;

      while(var4-- > 0) {
         this.buffer.setCharAt(var4, var1[var2 + var4]);
      }

   }

   public String32(char[] var1, int var2, int var3) {
      this.buffer = new String32Buffer(var3);
      int var4 = var3;

      while(var4-- > 0) {
         this.buffer.setCharAt(var4, var1[var2 + var4]);
      }

   }

   private String32(byte[] var1, int var2, int var3, ByteToChar32Converter var4) {
      int var5 = var4.getMaxCharsPerByte() * var3;
      int[] var6 = new int[var5];

      int var7;
      try {
         var7 = var4.convert(var1, var2, var2 + var3, var6, 0, var5);
         var7 += var4.flush(var6, var4.nextCharIndex(), var5);
      } catch (CharConversionException var9) {
         var7 = var4.nextCharIndex();
      }

      this.buffer = new String32Buffer(var7);
      int var8 = var7;

      while(var8-- > 0) {
         this.buffer.setCharAt(var8, var6[var8]);
      }

   }

   public String32(byte[] var1, int var2, int var3, String var4) throws UnsupportedEncodingException {
      this(var1, var2, var3, ByteToChar32Converter.getConverter(var4));
   }

   public String32(byte[] var1, String var2) throws UnsupportedEncodingException {
      this(var1, 0, var1.length, (String)var2);
   }

   public String32(byte[] var1, int var2, int var3) {
      this(var1, var2, var3, ByteToChar32Converter.getDefault());
   }

   public String32(byte[] var1) {
      this(var1, 0, var1.length, (ByteToChar32Converter)ByteToChar32Converter.getDefault());
   }

   public String32(String32Buffer var1) {
      this.buffer = (String32Buffer)var1.clone();
   }

   public String32(StringBuffer var1) {
      this.buffer = new String32Buffer(var1.length());
      this.buffer.setLength(var1.length());
      int var2 = var1.length();

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, this.buffer.charAt(var2));
      }

   }

   public static String32 valueOf(int[] var0) {
      return new String32(var0);
   }

   public static String32 valueOf(char[] var0) {
      return new String32(var0);
   }

   public static String32 valueOf(int[] var0, int var1, int var2) {
      return new String32(var0, var1, var2);
   }

   public static String32 valueOf(char[] var0, int var1, int var2) {
      return new String32(var0, var1, var2);
   }

   public static String32 valueOf(Object var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(boolean var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(char var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(int var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(long var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(float var0) {
      return new String32(String.valueOf(var0));
   }

   public static String32 valueOf(double var0) {
      return new String32(String.valueOf(var0));
   }

   public int charAt(int var1) {
      return this.buffer.charAt(var1);
   }

   public void setValue(String32 var1) {
      this.buffer = new String32Buffer(var1);
   }

   public void setValue(String16 var1) {
      this.buffer = new String32Buffer(var1);
   }

   public void setValue(String var1) {
      this.buffer = new String32Buffer(var1);
   }

   public void setValue(int[] var1) {
      this.buffer = new String32Buffer(var1.length);
      int var2 = var1.length;

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, var1[var2]);
      }

   }

   public void setValue(char[] var1) {
      this.buffer = new String32Buffer(var1.length);
      int var2 = var1.length;

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, var1[var2]);
      }

   }

   public void setValue(int[] var1, int var2, int var3) {
      this.buffer = new String32Buffer(var3);
      int var4 = var3;

      while(var4-- > 0) {
         this.buffer.setCharAt(var4, var1[var2 + var4]);
      }

   }

   public void setValue(char[] var1, int var2, int var3) {
      this.buffer = new String32Buffer(var3);
      int var4 = var3;

      while(var4-- > 0) {
         this.buffer.setCharAt(var4, var1[var2 + var4]);
      }

   }

   private void setValue(byte[] var1, int var2, int var3, ByteToChar32Converter var4) {
      int var5 = var4.getMaxCharsPerByte() * var3;
      int[] var6 = new int[var5];

      int var7;
      try {
         var7 = var4.convert(var1, var2, var2 + var3, var6, 0, var5);
         var7 += var4.flush(var6, var4.nextCharIndex(), var5);
      } catch (CharConversionException var9) {
         var7 = var4.nextCharIndex();
      }

      this.buffer = new String32Buffer(var7);
      int var8 = var7;

      while(var8-- > 0) {
         this.buffer.setCharAt(var8, var6[var8]);
      }

   }

   public void setValue(byte[] var1, int var2, int var3, String var4) throws UnsupportedEncodingException {
      this.setValue(var1, var2, var3, ByteToChar32Converter.getConverter(var4));
   }

   public void setValue(byte[] var1, String var2) throws UnsupportedEncodingException {
      this.setValue(var1, 0, var1.length, (String)var2);
   }

   public void setValue(byte[] var1, int var2, int var3) {
      this.setValue(var1, var2, var3, ByteToChar32Converter.getDefault());
   }

   public void setValue(byte[] var1) {
      this.setValue(var1, 0, var1.length, (ByteToChar32Converter)ByteToChar32Converter.getDefault());
   }

   public void setValue(String32Buffer var1) {
      this.buffer = (String32Buffer)var1.clone();
   }

   public void setValue(StringBuffer var1) {
      this.buffer = new String32Buffer(var1.length());
      this.buffer.setLength(var1.length());
      int var2 = var1.length();

      while(var2-- > 0) {
         this.buffer.setCharAt(var2, this.buffer.charAt(var2));
      }

   }

   public int compareTo(String16 var1) {
      int var2 = Math.min(this.buffer.length(), var1.length());

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.buffer.charAt(var3);
         char var5 = var1.charAt(var3);
         if (var4 < var5) {
            return -1;
         }

         if (var4 > var5) {
            return 1;
         }
      }

      if (this.buffer.length() < var1.length()) {
         return -1;
      } else if (this.buffer.length() > var1.length()) {
         return 1;
      } else {
         return 0;
      }
   }

   public int compareTo(String var1) {
      int var2 = Math.min(this.buffer.length(), var1.length());

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.buffer.charAt(var3);
         char var5 = var1.charAt(var3);
         if (var4 < var5) {
            return -1;
         }

         if (var4 > var5) {
            return 1;
         }
      }

      if (this.buffer.length() < var1.length()) {
         return -1;
      } else if (this.buffer.length() > var1.length()) {
         return 1;
      } else {
         return 0;
      }
   }

   public int compareTo(Object var1) {
      String32 var2 = (String32)var1;
      int var3 = Math.min(this.buffer.length(), var2.length());

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = this.buffer.charAt(var4);
         int var6 = var2.charAt(var4);
         if (var5 < var6) {
            return -1;
         }

         if (var5 > var6) {
            return 1;
         }
      }

      if (this.buffer.length() < var2.length()) {
         return -1;
      } else if (this.buffer.length() > var2.length()) {
         return 1;
      } else {
         return 0;
      }
   }

   public int compareToIgnoreCase(String16 var1) {
      int var2 = Math.min(this.buffer.length(), var1.length());

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.buffer.charAt(var3);
         char var5 = var1.charAt(var3);
         if (var4 >= 0 && var4 <= 65535) {
            var4 = Character.toLowerCase((char)var4);
         }

         var5 = Character.toLowerCase((char)var5);
         if (var4 < var5) {
            return -1;
         }

         if (var4 > var5) {
            return 1;
         }
      }

      if (this.buffer.length() < var1.length()) {
         return -1;
      } else {
         return this.buffer.length() > var1.length() ? 1 : 0;
      }
   }

   public int compareToIgnoreCase(String var1) {
      int var2 = Math.min(this.buffer.length(), var1.length());

      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.buffer.charAt(var3);
         char var5 = var1.charAt(var3);
         if (var4 >= 0 && var4 <= 65535) {
            var4 = Character.toLowerCase((char)var4);
         }

         var5 = Character.toLowerCase((char)var5);
         if (var4 < var5) {
            return -1;
         }

         if (var4 > var5) {
            return 1;
         }
      }

      if (this.buffer.length() < var1.length()) {
         return -1;
      } else {
         return this.buffer.length() > var1.length() ? 1 : 0;
      }
   }

   public int compareToIgnoreCase(Object var1) {
      String32 var2 = (String32)var1;
      int var3 = Math.min(this.buffer.length(), var2.length());

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = this.buffer.charAt(var4);
         int var6 = var2.charAt(var4);
         if (var5 >= 0 && var5 <= 65535) {
            var5 = Character.toLowerCase((char)var5);
         }

         if (var6 >= 0 && var6 <= 65535) {
            var6 = Character.toLowerCase((char)var6);
         }

         if (var5 < var6) {
            return -1;
         }

         if (var5 > var6) {
            return 1;
         }
      }

      if (this.buffer.length() < var2.length()) {
         return -1;
      } else if (this.buffer.length() > var2.length()) {
         return 1;
      } else {
         return 0;
      }
   }

   public String32 concat(String32 var1) {
      int var2 = var1.length();
      if (var2 == 0) {
         return this;
      } else {
         int var3 = this.buffer.length();
         int[] var4 = new int[var3 + var2];
         this.getChars(0, var3, var4, 0);
         var1.getChars(0, var2, var4, var3);
         return new String32(var4, 0, var4.length);
      }
   }

   public String32 concat(String16 var1) {
      int var2 = var1.length();
      if (var2 == 0) {
         return this;
      } else {
         int var3 = this.buffer.length();
         int[] var4 = new int[var3 + var2];
         this.getChars(0, var3, var4, 0);

         for(int var5 = var2; var5-- > 0; var4[var3 + var5] = var1.charAt(var5)) {
         }

         return new String32(var4, 0, var4.length);
      }
   }

   public String32 concat(String var1) {
      int var2 = var1.length();
      if (var2 == 0) {
         return this;
      } else {
         int var3 = this.buffer.length();
         int[] var4 = new int[var3 + var2];
         this.getChars(0, var3, var4, 0);

         for(int var5 = var2; var5-- > 0; var4[var3 + var5] = var1.charAt(var5)) {
         }

         return new String32(var4, 0, var4.length);
      }
   }

   public String32 replace(int var1, int var2) {
      if (var1 != var2) {
         int var3 = this.buffer.length();

         int var4;
         for(var4 = var3 - 1; var4 >= 0 && this.buffer.charAt(var4) != var1; --var4) {
         }

         if (var4 >= 0) {
            int[] var5 = new int[var3];
            int var6 = var3;

            while(true) {
               --var6;
               if (var6 <= var4) {
                  int var7;
                  for(var5[var4] = var2; var4-- > 0; var5[var4] = var7 == var1 ? var2 : var7) {
                     var7 = this.buffer.charAt(var4);
                  }

                  return new String32(var5, 0, var3);
               }

               var5[var6] = this.buffer.charAt(var6);
            }
         }
      }

      return this;
   }

   public boolean startsWith(String32 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 >= 0 && var2 <= var3 - var4) {
         int var5 = var4;

         while(var5-- > 0) {
            if (this.buffer.charAt(var2 + var5) != var1.charAt(var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean startsWith(String16 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 >= 0 && var2 <= var3 - var4) {
         int var5 = var4;

         while(var5-- > 0) {
            if (this.buffer.charAt(var2 + var5) != var1.charAt(var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean startsWith(String var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 >= 0 && var2 <= var3 - var4) {
         int var5 = var4;

         while(var5-- > 0) {
            if (this.buffer.charAt(var2 + var5) != var1.charAt(var5)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean startsWith(String32 var1) {
      return this.startsWith((String32)var1, 0);
   }

   public boolean startsWith(String16 var1) {
      return this.startsWith((String16)var1, 0);
   }

   public boolean startsWith(String var1) {
      return this.startsWith((String)var1, 0);
   }

   public boolean endsWith(String32 var1) {
      return this.startsWith(var1, this.buffer.length() - var1.length());
   }

   public boolean endsWith(String16 var1) {
      return this.startsWith(var1, this.buffer.length() - var1.length());
   }

   public boolean endsWith(String var1) {
      return this.startsWith(var1, this.buffer.length() - var1.length());
   }

   public boolean equals(Object var1) {
      return var1 != null && this.compareTo(var1) == 0;
   }

   public boolean equals(String var1) {
      return var1 != null && this.compareTo(var1) == 0;
   }

   public boolean equals(String16 var1) {
      return var1 != null && this.compareTo(var1) == 0;
   }

   public boolean equalsIgnoreCase(Object var1) {
      return var1 != null && this.compareToIgnoreCase(var1) == 0;
   }

   public boolean equalsIgnoreCase(String var1) {
      return var1 != null && this.compareToIgnoreCase(var1) == 0;
   }

   public boolean equalsIgnoreCase(String16 var1) {
      return var1 != null && this.compareToIgnoreCase(var1) == 0;
   }

   private byte[] getBytes(Char32ToByteConverter var1) {
      int var2 = this.buffer.length();
      var1.reset();
      int var3 = var1.getMaxBytesPerChar() * var2;
      byte[] var4 = new byte[var3];
      int[] var6 = new int[var2];

      for(int var7 = var2; var7-- > 0; var6[var7] = this.buffer.charAt(var7)) {
      }

      int var5;
      try {
         var5 = var1.convert(var6, 0, var2, var4, 0, var3);
         var5 += var1.flush(var4, var1.nextByteIndex(), var3);
      } catch (CharConversionException var9) {
         var5 = var1.nextByteIndex();
      }

      if (var5 < var3) {
         byte[] var8 = new byte[var5];
         System.arraycopy(var4, 0, var8, 0, var5);
         return var8;
      } else {
         return var4;
      }
   }

   public byte[] getBytes(String var1) throws UnsupportedEncodingException {
      return this.getBytes(Char32ToByteConverter.getConverter(var1));
   }

   public byte[] getBytes() {
      return this.getBytes(Char32ToByteConverter.getDefault());
   }

   public void getChars(int var1, int var2, int[] var3, int var4) {
      this.buffer.getChars(var1, var2, var3, var4);
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = this.buffer.length();
      int var3;
      if (var2 < 16) {
         for(var3 = 0; var3 < var2; ++var3) {
            var1 = var1 * 37 + this.buffer.charAt(var3);
         }
      } else {
         var3 = var2 / 8;

         for(int var4 = 0; var4 < var2; var4 += var3) {
            var1 = var1 * 39 + this.buffer.charAt(var4);
         }
      }

      return var1;
   }

   public int indexOf(int var1) {
      return this.indexOf(var1, 0);
   }

   public int indexOf(int var1, int var2) {
      int var3 = this.buffer.length();
      if (var2 < 0) {
         var2 = 0;
      } else if (var2 >= var3) {
         return -1;
      }

      while(var2 < var3) {
         if (this.buffer.charAt(var2) == var1) {
            return var2;
         }

         ++var2;
      }

      return -1;
   }

   public int indexOf(String32 var1) {
      return this.indexOf((String32)var1, 0);
   }

   public int indexOf(String16 var1) {
      return this.indexOf((String16)var1, 0);
   }

   public int indexOf(String var1) {
      return this.indexOf((String)var1, 0);
   }

   public int indexOf(String32 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         var2 = 0;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      while(var2 <= var3 - var4) {
         int var5 = var4;

         do {
            if (var5-- <= 0) {
               return var2;
            }
         } while(var1.charAt(var5) == this.buffer.charAt(var2 + var5));

         ++var2;
      }

      return -1;
   }

   public int indexOf(String16 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         var2 = 0;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      while(var2 <= var3 - var4) {
         int var5 = var4;

         do {
            if (var5-- <= 0) {
               return var2;
            }
         } while(var1.charAt(var5) == this.buffer.charAt(var2 + var5));

         ++var2;
      }

      return -1;
   }

   public int indexOf(String var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         var2 = 0;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      while(var2 <= var3 - var4) {
         int var5 = var4;

         do {
            if (var5-- <= 0) {
               return var2;
            }
         } while(var1.charAt(var5) == this.buffer.charAt(var2 + var5));

         ++var2;
      }

      return -1;
   }

   public int lastIndexOf(int var1) {
      return this.lastIndexOf(var1, this.buffer.length() - 1);
   }

   public int lastIndexOf(int var1, int var2) {
      int var3 = this.buffer.length();
      if (var2 >= var3) {
         var2 = var3 - 1;
      } else if (var2 < 0) {
         return -1;
      }

      while(var2 >= 0) {
         if (this.buffer.charAt(var2) == var1) {
            return var2;
         }

         --var2;
      }

      return -1;
   }

   public int lastIndexOf(String32 var1) {
      return this.lastIndexOf(var1, this.buffer.length());
   }

   public int lastIndexOf(String16 var1) {
      return this.lastIndexOf(var1, this.buffer.length());
   }

   public int lastIndexOf(String var1) {
      return this.lastIndexOf(var1, this.buffer.length());
   }

   public int lastIndexOf(String32 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         boolean var7 = false;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      for(int var5 = var3 - var4; var5 > 0; --var5) {
         int var6 = var4;

         do {
            if (var6-- <= 0) {
               return var5;
            }
         } while(var1.charAt(var6) == this.buffer.charAt(var5 + var6));
      }

      return -1;
   }

   public int lastIndexOf(String16 var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         boolean var7 = false;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      for(int var5 = var3 - var4; var5 > 0; --var5) {
         int var6 = var4;

         do {
            if (var6-- <= 0) {
               return var5;
            }
         } while(var1.charAt(var6) == this.buffer.charAt(var5 + var6));
      }

      return -1;
   }

   public int lastIndexOf(String var1, int var2) {
      int var3 = this.buffer.length();
      int var4 = var1.length();
      if (var2 < 0) {
         boolean var7 = false;
      } else if (var2 > var3 - var4) {
         return -1;
      }

      for(int var5 = var3 - var4; var5 > 0; --var5) {
         int var6 = var4;

         do {
            if (var6-- <= 0) {
               return var5;
            }
         } while(var1.charAt(var6) == this.buffer.charAt(var5 + var6));
      }

      return -1;
   }

   public int length() {
      return this.buffer.length();
   }

   public boolean regionMatches(int var1, String32 var2, int var3, int var4) {
      int var5 = this.buffer.length();
      int var6 = var2.length();
      if (var1 >= 0 && var3 >= 0 && var1 <= var5 - var4 && var3 <= var6 - var4) {
         while(var4-- > 0) {
            if (this.buffer.charAt(var1) != this.buffer.charAt(var3)) {
               return false;
            }

            ++var1;
            ++var3;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean regionMatches(int var1, String16 var2, int var3, int var4) {
      int var5 = this.buffer.length();
      int var6 = var2.length();
      if (var1 >= 0 && var3 >= 0 && var1 <= var5 - var4 && var3 <= var6 - var4) {
         while(var4-- > 0) {
            if (this.buffer.charAt(var1) != this.buffer.charAt(var3)) {
               return false;
            }

            ++var1;
            ++var3;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean regionMatches(int var1, String var2, int var3, int var4) {
      int var5 = this.buffer.length();
      int var6 = var2.length();
      if (var1 >= 0 && var3 >= 0 && var1 <= var5 - var4 && var3 <= var6 - var4) {
         while(var4-- > 0) {
            if (this.buffer.charAt(var1) != this.buffer.charAt(var3)) {
               return false;
            }

            ++var1;
            ++var3;
         }

         return true;
      } else {
         return false;
      }
   }

   public String32 substring(int var1) {
      return this.substring(var1, this.buffer.length());
   }

   public String32 substring(int var1, int var2) {
      int var3 = this.buffer.length();
      if (var1 < 0) {
         throw new StringIndexOutOfBoundsException(var1);
      } else if (var2 > var3) {
         throw new StringIndexOutOfBoundsException(var2);
      } else if (var1 > var2) {
         throw new StringIndexOutOfBoundsException(var2 - var1);
      } else if (var1 == 0 && var2 == var3) {
         return this;
      } else {
         int[] var4 = new int[var2 - var1];
         this.buffer.getChars(var1, var2, var4, 0);
         return new String32(var4);
      }
   }

   public int[] toIntArray() {
      int var1 = this.buffer.length();
      int[] var2 = new int[var1];
      this.buffer.getChars(0, var1, var2, 0);
      return var2;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      int var7 = this.buffer.length();
      StringBuffer var9 = new StringBuffer(var7 + 2);
      boolean var10 = false;

      for(int var6 = 0; var6 < var7; ++var6) {
         int var8 = this.buffer.charAt(var6);
         if (var8 >= 32 && var8 < 127) {
            if (!var10) {
               if (var6 > 0) {
                  var9.append(',');
               }

               var9.append(" \"");
               var10 = true;
            }

            if (var8 == 34) {
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

            var9.append(" { ").append(var8 >>> 24 & 255).append(", ").append(var8 >>> 16 & 255).append(", ").append(var8 >>> 8 & 255).append(", ").append(var8 & 255).append(" }");
         }
      }

      if (var10) {
         var9.append("\"");
      }

      var1.println(var2 + var3 + "{" + var9.toString() + " }" + var4);
   }

   public String toString() {
      return this.buffer.toString();
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return this;
   }

   public String32 trim() {
      int var1 = this.buffer.length();
      int var2 = 0;

      int var3;
      for(var3 = var1; var2 < var1 && this.buffer.charAt(var2) <= 32; ++var2) {
      }

      while(var3 > var2 && this.buffer.charAt(var3 - 1) <= 32) {
         --var3;
      }

      return var2 == 0 && var3 == var1 ? this : this.substring(var2, var3);
   }
}
