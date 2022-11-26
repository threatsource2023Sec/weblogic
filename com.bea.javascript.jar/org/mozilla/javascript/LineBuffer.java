package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;

final class LineBuffer {
   static final int BUFLEN = 256;
   private Reader in;
   private char[] otherBuffer = null;
   private char[] buffer = null;
   private int offset = 0;
   private int end = 0;
   private int otherEnd;
   private int lineno;
   private int lineStart = 0;
   private int otherStart = 0;
   private int prevStart = 0;
   private boolean lastWasCR = false;
   private boolean hitEOF = false;
   private int stringStart = -1;
   private StringBuffer stringSoFar = null;
   private boolean hadCFSinceStringStart = false;

   LineBuffer(Reader var1, int var2) {
      this.in = var1;
      this.lineno = var2;
   }

   boolean eof() {
      return this.hitEOF;
   }

   boolean fill() throws IOException {
      if (this.end - this.offset != 0) {
         throw new IOException("fill of non-empty buffer");
      } else {
         int var1 = this.offset > 0 && this.lastWasCR ? 1 : 0;
         if (this.stringStart != -1) {
            this.stringSoFar = new StringBuffer();
            this.stringSoFar.append(this.buffer, this.stringStart, this.end - this.stringStart - var1);
            this.stringStart = -1;
         } else if (this.stringSoFar != null) {
            this.stringSoFar.append(this.buffer, 0, this.end - var1);
         }

         char[] var2 = this.buffer;
         this.buffer = this.otherBuffer;
         this.otherBuffer = var2;
         if (this.buffer == null) {
            this.buffer = new char[256];
         }

         this.otherStart = this.lineStart;
         this.otherEnd = this.end;
         this.prevStart = this.lineStart = this.otherBuffer == null ? 0 : this.buffer.length + 1;
         this.offset = 0;
         this.end = this.in.read(this.buffer, 0, this.buffer.length);
         if (this.end < 0) {
            this.end = 0;
            this.hitEOF = true;
            return false;
         } else {
            if (this.lastWasCR) {
               if (this.buffer[0] == '\n') {
                  ++this.offset;
                  if (this.end == 1) {
                     return this.fill();
                  }
               }

               this.lineStart = this.offset;
               this.lastWasCR = false;
            }

            return true;
         }
      }
   }

   String getLine() {
      StringBuffer var1 = new StringBuffer();
      int var2 = this.lineStart;
      if (var2 >= this.offset) {
         if (this.otherStart < this.otherEnd) {
            var1.append(this.otherBuffer, this.otherStart, this.otherEnd - this.otherStart);
         }

         var2 = 0;
      }

      var1.append(this.buffer, var2, this.offset - var2);
      int var3 = this.offset;

      while(true) {
         if (var3 == this.buffer.length) {
            char[] var4 = new char[this.buffer.length * 2];
            System.arraycopy(this.buffer, 0, var4, 0, this.buffer.length);
            this.buffer = var4;
            int var5 = 0;

            try {
               var5 = this.in.read(this.buffer, this.end, this.buffer.length - this.end);
            } catch (IOException var6) {
            }

            if (var5 < 0) {
               break;
            }

            this.end += var5;
         }

         if (this.buffer[var3] == '\r' || this.buffer[var3] == '\n') {
            break;
         }

         ++var3;
      }

      var1.append(this.buffer, this.offset, var3 - this.offset);
      return var1.toString();
   }

   int getLineno() {
      return this.lineno;
   }

   int getOffset() {
      return this.lineStart >= this.offset ? this.offset + (this.otherEnd - this.otherStart) : this.offset - this.lineStart;
   }

   String getString() {
      int var2 = this.offset > 0 && this.buffer[this.offset] == '\n' && this.buffer[this.offset - 1] == '\r' ? 1 : 0;
      String var1;
      if (this.stringStart != -1) {
         var1 = new String(this.buffer, this.stringStart, this.offset - this.stringStart - var2);
      } else {
         if (this.stringSoFar == null) {
            this.stringSoFar = new StringBuffer();
         }

         var1 = this.stringSoFar.append(this.buffer, 0, this.offset - var2).toString();
      }

      this.stringStart = -1;
      this.stringSoFar = null;
      if (this.hadCFSinceStringStart) {
         char[] var3 = var1.toCharArray();
         StringBuffer var4 = null;

         for(int var5 = 0; var5 < var3.length; ++var5) {
            if (Character.getType(var3[var5]) == 16) {
               if (var4 == null) {
                  var4 = new StringBuffer();
                  var4.append(var3, 0, var5);
               }
            } else if (var4 != null) {
               var4.append(var3[var5]);
            }
         }

         if (var4 != null) {
            var1 = var4.toString();
         }
      }

      return var1;
   }

   boolean match(char var1) throws IOException {
      if (this.end == this.offset && !this.fill()) {
         return false;
      } else if (this.buffer[this.offset] == var1) {
         ++this.offset;
         return true;
      } else {
         return false;
      }
   }

   int peek() throws IOException {
      if (this.end == this.offset && !this.fill()) {
         return -1;
      } else {
         return this.buffer[this.offset] == '\r' ? 10 : this.buffer[this.offset];
      }
   }

   int read() throws IOException {
      while(this.end != this.offset || this.fill()) {
         if ((this.buffer[this.offset] & '\udfd0') == 0) {
            if (this.buffer[this.offset] == '\r') {
               if (this.offset + 1 < this.end) {
                  if (this.buffer[this.offset + 1] == '\n') {
                     ++this.offset;
                  }
               } else {
                  this.lastWasCR = true;
               }
            } else if (this.buffer[this.offset] != '\n' && this.buffer[this.offset] != 8232 && this.buffer[this.offset] != 8233) {
               if (Character.getType(this.buffer[this.offset]) == 16) {
                  this.hadCFSinceStringStart = true;
                  ++this.offset;
                  continue;
               }

               return this.buffer[this.offset++];
            }

            ++this.offset;
            this.prevStart = this.lineStart;
            this.lineStart = this.offset;
            ++this.lineno;
            return 10;
         } else {
            if (this.buffer[this.offset] < 128 || Character.getType(this.buffer[this.offset]) != 16) {
               return this.buffer[this.offset++];
            }

            this.hadCFSinceStringStart = true;
            ++this.offset;
         }
      }

      return -1;
   }

   void startString() {
      if (this.offset == 0) {
         this.stringSoFar = new StringBuffer();
         this.stringSoFar.append(this.otherBuffer, this.otherEnd - 1, 1);
         this.stringStart = -1;
         this.hadCFSinceStringStart = this.otherBuffer[this.otherEnd - 1] >= 128 && Character.getType(this.otherBuffer[this.otherEnd - 1]) == 16;
      } else {
         this.stringSoFar = null;
         this.stringStart = this.offset - 1;
         this.hadCFSinceStringStart = this.buffer[this.stringStart] >= 128 && Character.getType(this.buffer[this.stringStart]) == 16;
      }

   }

   void unread() {
      if (this.offset != 0) {
         --this.offset;
         if ((this.buffer[this.offset] & '\ufff0') == 0 && (this.buffer[this.offset] == '\r' || this.buffer[this.offset] == '\n')) {
            this.lineStart = this.prevStart;
            --this.lineno;
         }

      }
   }
}
