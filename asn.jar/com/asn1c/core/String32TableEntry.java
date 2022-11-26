package com.asn1c.core;

public class String32TableEntry {
   protected long lowerChar;
   protected long upperChar;
   protected long lowerVal;
   protected long upperVal;

   public String32TableEntry(int var1, int var2, int var3, int var4) {
      this.lowerChar = (long)var1 & 4294967295L;
      this.upperChar = (long)var2 & 4294967295L;
      this.lowerVal = (long)var3 & 4294967295L;
      this.upperVal = (long)var4 & 4294967295L;
   }

   public int mapToValue(int var1) {
      return (int)((long)var1 - this.lowerChar + this.lowerVal);
   }

   public int mapToChar(int var1) {
      return (int)((long)var1 - this.lowerVal + this.lowerChar);
   }

   public int compareToChar(int var1) {
      if (((long)var1 & 4294967295L) < this.lowerChar) {
         return 1;
      } else {
         return ((long)var1 & 4294967295L) > this.upperChar ? -1 : 0;
      }
   }

   public int compareToValue(int var1) {
      if (((long)var1 & 4294967295L) < this.lowerVal) {
         return 1;
      } else {
         return ((long)var1 & 4294967295L) > this.upperVal ? -1 : 0;
      }
   }
}
