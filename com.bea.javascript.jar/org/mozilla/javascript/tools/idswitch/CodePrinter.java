package org.mozilla.javascript.tools.idswitch;

class CodePrinter {
   private static final int LITERAL_CHAR_MAX_SIZE = 6;
   private String lineTerminator = "\n";
   private int indentStep = 4;
   private int indentTabSize = 8;
   private char[] buffer = new char[4096];
   private int offset;

   private int add_area(int var1) {
      int var2 = this.ensure_area(var1);
      this.offset = var2 + var1;
      return var2;
   }

   public void clear() {
      this.offset = 0;
   }

   private static char digit_to_hex_letter(int var0) {
      return (char)(var0 < 10 ? 48 + var0 : 55 + var0);
   }

   private int ensure_area(int var1) {
      int var2 = this.offset;
      int var3 = var2 + var1;
      if (var3 > this.buffer.length) {
         int var4 = this.buffer.length * 2;
         if (var3 > var4) {
            var4 = var3;
         }

         char[] var5 = new char[var4];
         System.arraycopy(this.buffer, 0, var5, 0, var2);
         this.buffer = var5;
      }

      return var2;
   }

   public void erase(int var1, int var2) {
      System.arraycopy(this.buffer, var2, this.buffer, var1, this.offset - var2);
      this.offset -= var2 - var1;
   }

   public int getIndentStep() {
      return this.indentStep;
   }

   public int getIndentTabSize() {
      return this.indentTabSize;
   }

   public int getLastChar() {
      return this.offset == 0 ? -1 : this.buffer[this.offset - 1];
   }

   public String getLineTerminator() {
      return this.lineTerminator;
   }

   public int getOffset() {
      return this.offset;
   }

   public void indent(int var1) {
      int var2 = this.indentStep * var1;
      int var3;
      int var4;
      if (this.indentTabSize <= 0) {
         var4 = 0;
         var3 = var2;
      } else {
         var4 = var2 / this.indentTabSize;
         var3 = var4 + var2 % this.indentTabSize;
      }

      int var5 = this.add_area(var3);
      int var6 = var5 + var4;

      int var7;
      for(var7 = var5 + var3; var5 != var6; ++var5) {
         this.buffer[var5] = '\t';
      }

      while(var5 != var7) {
         this.buffer[var5] = ' ';
         ++var5;
      }

   }

   public void line(int var1, String var2) {
      this.indent(var1);
      this.p(var2);
      this.nl();
   }

   public void nl() {
      this.p('\n');
   }

   public void p(char var1) {
      int var2 = this.add_area(1);
      this.buffer[var2] = var1;
   }

   public void p(int var1) {
      this.p(Integer.toString(var1));
   }

   public void p(String var1) {
      int var2 = var1.length();
      int var3 = this.add_area(var2);
      var1.getChars(0, var2, this.buffer, var3);
   }

   public final void p(char[] var1) {
      this.p(var1, 0, var1.length);
   }

   public void p(char[] var1, int var2, int var3) {
      int var4 = var3 - var2;
      int var5 = this.add_area(var4);
      System.arraycopy(var1, var2, this.buffer, var5, var4);
   }

   private int put_string_literal_char(int var1, int var2, boolean var3) {
      boolean var4 = true;
      switch (var2) {
         case 8:
            var2 = 98;
            break;
         case 9:
            var2 = 116;
            break;
         case 10:
            var2 = 110;
            break;
         case 12:
            var2 = 102;
            break;
         case 13:
            var2 = 114;
            break;
         case 34:
            var4 = var3;
            break;
         case 39:
            var4 = var3 ^ true;
            break;
         default:
            var4 = false;
      }

      if (var4) {
         this.buffer[var1] = '\\';
         this.buffer[var1 + 1] = (char)var2;
         var1 += 2;
      } else if (var2 >= 32 && var2 <= 126) {
         this.buffer[var1] = (char)var2;
         ++var1;
      } else {
         this.buffer[var1] = '\\';
         this.buffer[var1 + 1] = 'u';
         this.buffer[var1 + 2] = digit_to_hex_letter(15 & var2 >> 12);
         this.buffer[var1 + 3] = digit_to_hex_letter(15 & var2 >> 8);
         this.buffer[var1 + 4] = digit_to_hex_letter(15 & var2 >> 4);
         this.buffer[var1 + 5] = digit_to_hex_letter(15 & var2);
         var1 += 6;
      }

      return var1;
   }

   public void qchar(int var1) {
      int var2 = this.ensure_area(8);
      this.buffer[var2] = '\'';
      var2 = this.put_string_literal_char(var2 + 1, var1, false);
      this.buffer[var2] = '\'';
      this.offset = var2 + 1;
   }

   public void qstring(String var1) {
      int var2 = var1.length();
      int var3 = this.ensure_area(2 + 6 * var2);
      this.buffer[var3] = '"';
      ++var3;

      for(int var4 = 0; var4 != var2; ++var4) {
         var3 = this.put_string_literal_char(var3, var1.charAt(var4), true);
      }

      this.buffer[var3] = '"';
      this.offset = var3 + 1;
   }

   public void setIndentStep(int var1) {
      this.indentStep = var1;
   }

   public void setIndentTabSize(int var1) {
      this.indentTabSize = var1;
   }

   public void setLineTerminator(String var1) {
      this.lineTerminator = var1;
   }

   public String toString() {
      return new String(this.buffer, 0, this.offset);
   }
}
