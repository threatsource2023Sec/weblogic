package weblogic.apache.xerces.util;

import weblogic.apache.xerces.xni.XMLString;

public class XMLStringBuffer extends XMLString {
   public static final int DEFAULT_SIZE = 32;

   public XMLStringBuffer() {
      this((int)32);
   }

   public XMLStringBuffer(int var1) {
      this.ch = new char[var1];
   }

   public XMLStringBuffer(char var1) {
      this((int)1);
      this.append(var1);
   }

   public XMLStringBuffer(String var1) {
      this(var1.length());
      this.append(var1);
   }

   public XMLStringBuffer(char[] var1, int var2, int var3) {
      this(var3);
      this.append(var1, var2, var3);
   }

   public XMLStringBuffer(XMLString var1) {
      this(var1.length);
      this.append(var1);
   }

   public void clear() {
      this.offset = 0;
      this.length = 0;
   }

   public void append(char var1) {
      if (this.length + 1 > this.ch.length) {
         int var2 = this.ch.length * 2;
         if (var2 < this.ch.length + 32) {
            var2 = this.ch.length + 32;
         }

         char[] var3 = new char[var2];
         System.arraycopy(this.ch, 0, var3, 0, this.length);
         this.ch = var3;
      }

      this.ch[this.length] = var1;
      ++this.length;
   }

   public void append(String var1) {
      int var2 = var1.length();
      if (this.length + var2 > this.ch.length) {
         int var3 = this.ch.length * 2;
         if (var3 < this.length + var2 + 32) {
            var3 = this.ch.length + var2 + 32;
         }

         char[] var4 = new char[var3];
         System.arraycopy(this.ch, 0, var4, 0, this.length);
         this.ch = var4;
      }

      var1.getChars(0, var2, this.ch, this.length);
      this.length += var2;
   }

   public void append(char[] var1, int var2, int var3) {
      if (this.length + var3 > this.ch.length) {
         int var4 = this.ch.length * 2;
         if (var4 < this.length + var3 + 32) {
            var4 = this.ch.length + var3 + 32;
         }

         char[] var5 = new char[var4];
         System.arraycopy(this.ch, 0, var5, 0, this.length);
         this.ch = var5;
      }

      System.arraycopy(var1, var2, this.ch, this.length, var3);
      this.length += var3;
   }

   public void append(XMLString var1) {
      this.append(var1.ch, var1.offset, var1.length);
   }
}
