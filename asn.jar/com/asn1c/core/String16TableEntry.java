package com.asn1c.core;

public class String16TableEntry {
   protected int lowerChar;
   protected int upperChar;
   protected int lowerVal;
   protected int upperVal;

   public String16TableEntry(char var1, char var2, char var3, char var4) {
      this.lowerChar = var1 & '\uffff';
      this.upperChar = var2 & '\uffff';
      this.lowerVal = var3 & '\uffff';
      this.upperVal = var4 & '\uffff';
   }

   public char mapToValue(char var1) {
      return (char)(var1 - this.lowerChar + this.lowerVal);
   }

   public char mapToChar(char var1) {
      return (char)(var1 - this.lowerVal + this.lowerChar);
   }

   public int compareToChar(char var1) {
      if ((var1 & '\uffff') < this.lowerChar) {
         return 1;
      } else {
         return (var1 & '\uffff') > this.upperChar ? -1 : 0;
      }
   }

   public int compareToValue(char var1) {
      if ((var1 & '\uffff') < this.lowerVal) {
         return 1;
      } else {
         return (var1 & '\uffff') > this.upperVal ? -1 : 0;
      }
   }
}
