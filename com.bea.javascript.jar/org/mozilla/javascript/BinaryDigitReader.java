package org.mozilla.javascript;

final class BinaryDigitReader {
   int lgBase;
   int digit;
   int digitPos;
   String digits;
   int start;
   int end;

   BinaryDigitReader(int var1, String var2, int var3, int var4) {
      for(this.lgBase = 0; var1 != 1; var1 >>= 1) {
         ++this.lgBase;
      }

      this.digitPos = 0;
      this.digits = var2;
      this.start = var3;
      this.end = var4;
   }

   int getNextBinaryDigit() {
      if (this.digitPos == 0) {
         if (this.start == this.end) {
            return -1;
         }

         char var1 = this.digits.charAt(this.start++);
         if (var1 >= '0' && var1 <= '9') {
            this.digit = var1 - 48;
         } else if (var1 >= 'a' && var1 <= 'z') {
            this.digit = var1 - 97 + 10;
         } else {
            this.digit = var1 - 65 + 10;
         }

         this.digitPos = this.lgBase;
      }

      return this.digit >> --this.digitPos & 1;
   }
}
