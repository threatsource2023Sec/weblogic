package weblogicx.jsp.tags;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public abstract class Rewriter {
   private char c;
   private Reader is;
   private Writer os;
   private static final int LA_NONE = 0;
   private static final int LA_A = 1;
   private static final int LA_IMG = 2;
   private static final int LA_FORM = 3;
   private static final int LA_FRAME = 4;

   protected abstract String getLinkString();

   protected abstract String getFormString();

   public void setStreams(Reader is, Writer os) {
      this.is = is;
      this.os = os;
   }

   private static void p(String s) {
      System.out.println("[Rewriter]: " + s);
   }

   static boolean isWS(int i) {
      return i == 32 || i == 13 || i == 10 || i == 9;
   }

   void advance() throws IOException {
      int i = this.is.read();
      if (i == -1) {
         throw new NoMoreInputException();
      } else {
         this.c = (char)i;
      }
   }

   private boolean cmp(int i) {
      return i == this.c;
   }

   private boolean casecmp(int i) {
      if (i >= 97 && i <= 122) {
         return this.c == i || this.c == i - 32;
      } else {
         throw new IllegalArgumentException("bad char, not lowercase ascii: " + this.c);
      }
   }

   private static boolean isAlpha(int i) {
      return i >= 97 && i <= 122;
   }

   void passWS() throws IOException {
      while(true) {
         this.advance();
         if (!isWS(this.c)) {
            return;
         }

         this.os.write(this.c);
      }
   }

   private int lookingAt() throws IOException {
      this.os.write(this.c);
      switch (this.c) {
         case 'F':
         case 'f':
            this.advance();
            if (this.casecmp(111)) {
               return this.lookingAt("orm ") ? 3 : 0;
            } else if (this.casecmp(114)) {
               return this.lookingAt("rame ") ? 4 : 0;
            }
         case 'A':
         case 'a':
            this.advance();
            this.os.write(this.c);
            return isWS(this.c) ? 1 : 0;
         case 'I':
         case 'i':
            this.advance();
            return this.lookingAt("mg ") ? 2 : 0;
         default:
            return 0;
      }
   }

   private boolean lookingAt(String s) throws IOException {
      this.os.write(this.c);
      if (!this.casecmp(s.charAt(0))) {
         return false;
      } else {
         int len = s.length();

         for(int x = 1; x < len; ++x) {
            this.advance();
            this.os.write(this.c);
            if (isWS(this.c)) {
               if (!isWS(s.charAt(x))) {
                  return false;
               }
            } else if (!this.casecmp(s.charAt(x))) {
               return false;
            }
         }

         return true;
      }
   }

   public void parse() throws Exception {
      try {
         int i = false;

         int i;
         while((i = this.is.read()) > 0) {
            this.c = (char)i;
            this.os.write(this.c);
            if (this.c == '<') {
               this.passWS();
               switch (this.lookingAt()) {
                  case 0:
                  default:
                     break;
                  case 1:
                     this.processLink();
                     break;
                  case 2:
                     this.processImg();
                     break;
                  case 3:
                     this.processForm();
                     break;
                  case 4:
                     this.processFrame();
               }
            }
         }
      } catch (NoMoreInputException var2) {
      }

   }

   protected char upto(String s) throws IOException {
      int len = s.length();

      while(true) {
         this.advance();

         for(int i = 0; i < len; ++i) {
            if (this.c == s.charAt(i)) {
               return this.c;
            }
         }

         this.os.write(this.c);
      }
   }

   private void processFrame() throws IOException {
      this.processLink();
   }

   private void processForm() throws IOException {
      this.upto(">");
      this.os.write(62);
      String s = null;
      if ((s = this.getFormString()) != null) {
         this.os.write(s);
      }

   }

   private void processLink() throws IOException {
      char quot = 0;
      char first = this.upto("\"'?>:");
      if (first == ':') {
         this.os.write(58);
      } else {
         char extraWriteChar = 0;
         if (first != '\'' && first != '"') {
            if (first == '?') {
               this.os.write(63);
               extraWriteChar = '&';
            } else {
               this.os.write(63);
               extraWriteChar = '>';
            }
         } else {
            quot = first;
         }

         if (quot != 0) {
            this.os.write(quot);
            char[] search = new char[]{'>', ':', quot, '?'};
            char second = this.upto(new String(search, 0, 4));
            if (second == ':') {
               this.os.write(second);
               return;
            }

            this.os.write(63);
            if (second == '?') {
               extraWriteChar = '&';
            } else {
               extraWriteChar = second;
            }
         }

         String linkStr = this.getLinkString();
         if (linkStr != null) {
            this.os.write(linkStr);
         }

         if (extraWriteChar != 0) {
            this.os.write(extraWriteChar);
         }

      }
   }

   private void processImg() throws IOException {
      this.processLink();
   }
}
