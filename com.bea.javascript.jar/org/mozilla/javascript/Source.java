package org.mozilla.javascript;

final class Source {
   char functionNumber;
   StringBuffer buf = new StringBuffer(64);

   void addNumber(Number var1) {
      this.buf.append('-');
      long var2;
      if (!(var1 instanceof Double) && !(var1 instanceof Float)) {
         var2 = var1.longValue();
         if (var2 <= 65535L) {
            this.buf.append('S');
            this.buf.append((char)((int)var2));
         } else {
            this.buf.append('J');
            this.buf.append((char)((int)(var2 >> 48 & 65535L)));
            this.buf.append((char)((int)(var2 >> 32 & 65535L)));
            this.buf.append((char)((int)(var2 >> 16 & 65535L)));
            this.buf.append((char)((int)(var2 & 65535L)));
         }
      } else {
         this.buf.append('D');
         var2 = Double.doubleToLongBits(var1.doubleValue());
         this.buf.append((char)((int)(var2 >> 48 & 65535L)));
         this.buf.append((char)((int)(var2 >> 32 & 65535L)));
         this.buf.append((char)((int)(var2 >> 16 & 65535L)));
         this.buf.append((char)((int)(var2 & 65535L)));
      }

   }

   void addString(int var1, String var2) {
      this.buf.append((char)var1);
      this.buf.append((char)var2.length());
      this.buf.append(var2);
   }

   void append(char var1) {
      this.buf.append(var1);
   }
}
