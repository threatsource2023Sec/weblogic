package weblogic.transaction.utils;

import java.io.PrintStream;

public final class PrintBox {
   private static final int WIDTH = 80;
   private static final char TL = '+';
   private static final char T = '-';
   private static final char TR = '+';
   private static final char L = '|';
   private static final char R = '|';
   private static final char BL = '+';
   private static final char B = '-';
   private static final char BR = '+';
   private static final char NL = '\n';
   private static final char SP = ' ';
   private final StringBuffer b = new StringBuffer(1000);
   private int col = 0;
   private final PrintStream ps;

   private final void reset() {
      this.col = 0;
      this.b.setLength(0);
   }

   private final void newline() {
      this.b.append('\n');
      this.col = 0;
   }

   private final void append(char c) {
      if (this.col + 1 <= 80) {
         this.b.append(c);
         ++this.col;
      }

   }

   private final void append(String s) {
      int len = s.length();
      if (this.col + len <= 80) {
         this.b.append(s);
         this.col += len;
      }

   }

   private final void appendWrap(String s) {
      int len = s.length();

      char c;
      for(int pos = 0; pos < len; this.append(c)) {
         if (this.col >= 78) {
            this.append(' ');
            this.append('|');
            this.newline();
            this.append('|');
            this.append(' ');
         }

         c = s.charAt(pos++);
         if (c == '\n') {
            while(this.col < 79) {
               this.append(' ');
            }

            this.append('|');
            this.newline();
            this.append('|');
            this.append(' ');
         }
      }

      while(this.col < 79) {
         this.append(' ');
      }

      this.append('|');
      this.newline();
   }

   public PrintBox(PrintStream aPS) {
      this.ps = aPS;
   }

   public void setTitle(String title) {
      this.append('+');

      while(this.col < 79) {
         this.append('-');
      }

      this.append('+');
      this.newline();
      this.append('|');
      this.append(' ');
      if (title.length() > 74) {
         title = title.substring(0, 73);
      }

      this.append(title);
      this.append(' ');
      this.append('|');

      while(this.col < 79) {
         this.append(' ');
      }

      this.append('|');
      this.newline();
      this.append('+');

      while(this.col < title.length() + 3) {
         this.append('-');
      }

      this.append('+');

      while(this.col < 79) {
         this.append(' ');
      }

      this.append('|');
      this.newline();
   }

   public void add(String tag, String val) {
      this.append('|');
      this.append(' ');
      this.appendWrap(tag + " = " + val);
   }

   public void print() {
      this.append('+');

      while(this.col < 79) {
         this.append('-');
      }

      this.append('+');
      this.newline();
      this.newline();
      this.ps.println(this.b);
      this.reset();
   }
}
