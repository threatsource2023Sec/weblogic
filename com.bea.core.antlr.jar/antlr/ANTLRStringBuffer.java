package antlr;

public class ANTLRStringBuffer {
   protected char[] buffer = null;
   protected int length = 0;

   public ANTLRStringBuffer() {
      this.buffer = new char[50];
   }

   public ANTLRStringBuffer(int var1) {
      this.buffer = new char[var1];
   }

   public final void append(char var1) {
      if (this.length >= this.buffer.length) {
         int var2;
         for(var2 = this.buffer.length; this.length >= var2; var2 *= 2) {
         }

         char[] var3 = new char[var2];

         for(int var4 = 0; var4 < this.length; ++var4) {
            var3[var4] = this.buffer[var4];
         }

         this.buffer = var3;
      }

      this.buffer[this.length] = var1;
      ++this.length;
   }

   public final void append(String var1) {
      for(int var2 = 0; var2 < var1.length(); ++var2) {
         this.append(var1.charAt(var2));
      }

   }

   public final char charAt(int var1) {
      return this.buffer[var1];
   }

   public final char[] getBuffer() {
      return this.buffer;
   }

   public final int length() {
      return this.length;
   }

   public final void setCharAt(int var1, char var2) {
      this.buffer[var1] = var2;
   }

   public final void setLength(int var1) {
      if (var1 < this.length) {
         this.length = var1;
      } else {
         while(var1 > this.length) {
            this.append('\u0000');
         }
      }

   }

   public final String toString() {
      return new String(this.buffer, 0, this.length);
   }
}
