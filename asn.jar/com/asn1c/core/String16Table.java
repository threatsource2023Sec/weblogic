package com.asn1c.core;

public class String16Table {
   protected String16TableEntry[] entries;

   public String16Table(String16TableEntry[] var1) {
      this.entries = var1;
   }

   public int size() {
      return this.entries.length;
   }

   public char mapToChar(char var1) throws BadDataException {
      int var2 = this.entries.length;
      int var3 = 0;

      while(var2 >= 1) {
         int var4 = var2 / 2;
         switch (this.entries[var3 + var4].compareToValue(var1)) {
            case -1:
               var3 += var4 + 1;
               var2 -= var4 + 1;
               break;
            case 0:
               return this.entries[var3 + var4].mapToChar(var1);
            case 1:
               var2 = var4;
         }
      }

      throw new BadDataException();
   }

   public char mapToValue(char var1) throws BadValueException {
      int var2 = this.entries.length;
      int var3 = 0;

      while(var2 >= 1) {
         int var4 = var2 / 2;
         switch (this.entries[var3 + var4].compareToChar(var1)) {
            case -1:
               var3 += var4 + 1;
               var2 -= var4 + 1;
               break;
            case 0:
               return this.entries[var3 + var4].mapToValue(var1);
            case 1:
               var2 = var4;
         }
      }

      throw new BadValueException();
   }
}
