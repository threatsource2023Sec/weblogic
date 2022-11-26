package org.antlr.runtime.tree;

public class TreePatternLexer {
   public static final int EOF = -1;
   public static final int BEGIN = 1;
   public static final int END = 2;
   public static final int ID = 3;
   public static final int ARG = 4;
   public static final int PERCENT = 5;
   public static final int COLON = 6;
   public static final int DOT = 7;
   protected String pattern;
   protected int p = -1;
   protected int c;
   protected int n;
   public StringBuffer sval = new StringBuffer();
   public boolean error = false;

   public TreePatternLexer(String pattern) {
      this.pattern = pattern;
      this.n = pattern.length();
      this.consume();
   }

   public int nextToken() {
      this.sval.setLength(0);

      while(this.c != -1) {
         if (this.c != 32 && this.c != 10 && this.c != 13 && this.c != 9) {
            if ((this.c < 97 || this.c > 122) && (this.c < 65 || this.c > 90) && this.c != 95) {
               if (this.c == 40) {
                  this.consume();
                  return 1;
               }

               if (this.c == 41) {
                  this.consume();
                  return 2;
               }

               if (this.c == 37) {
                  this.consume();
                  return 5;
               }

               if (this.c == 58) {
                  this.consume();
                  return 6;
               }

               if (this.c == 46) {
                  this.consume();
                  return 7;
               }

               if (this.c == 91) {
                  this.consume();

                  for(; this.c != 93; this.consume()) {
                     if (this.c == 92) {
                        this.consume();
                        if (this.c != 93) {
                           this.sval.append('\\');
                        }

                        this.sval.append((char)this.c);
                     } else {
                        this.sval.append((char)this.c);
                     }
                  }

                  this.consume();
                  return 4;
               }

               this.consume();
               this.error = true;
               return -1;
            }

            this.sval.append((char)this.c);
            this.consume();

            while(this.c >= 97 && this.c <= 122 || this.c >= 65 && this.c <= 90 || this.c >= 48 && this.c <= 57 || this.c == 95) {
               this.sval.append((char)this.c);
               this.consume();
            }

            return 3;
         }

         this.consume();
      }

      return -1;
   }

   protected void consume() {
      ++this.p;
      if (this.p >= this.n) {
         this.c = -1;
      } else {
         this.c = this.pattern.charAt(this.p);
      }

   }
}
