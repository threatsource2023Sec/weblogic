package org.mozilla.javascript.tools.idswitch;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class FileBody {
   private char[] buffer = new char[16384];
   private int bufferEnd;
   private int lineBegin;
   private int lineEnd;
   private int nextLineStart;
   private int lineNumber;
   ReplaceItem firstReplace;
   ReplaceItem lastReplace;

   private static boolean equals(String var0, char[] var1, int var2, int var3) {
      if (var0.length() != var3 - var2) {
         return false;
      } else {
         int var4 = var2;

         for(int var5 = 0; var4 != var3; ++var5) {
            if (var1[var4] != var0.charAt(var5)) {
               return false;
            }

            ++var4;
         }

         return true;
      }
   }

   public char[] getBuffer() {
      return this.buffer;
   }

   public int getLineBegin() {
      return this.lineBegin;
   }

   public int getLineEnd() {
      return this.lineEnd;
   }

   public int getLineNumber() {
      return this.lineNumber;
   }

   public boolean nextLine() {
      if (this.nextLineStart == this.bufferEnd) {
         this.lineNumber = 0;
         return false;
      } else {
         char var2 = 0;

         int var1;
         for(var1 = this.nextLineStart; var1 != this.bufferEnd; ++var1) {
            var2 = this.buffer[var1];
            if (var2 == '\n' || var2 == '\r') {
               break;
            }
         }

         this.lineBegin = this.nextLineStart;
         this.lineEnd = var1;
         if (var1 == this.bufferEnd) {
            this.nextLineStart = var1;
         } else if (var2 == '\r' && var1 + 1 != this.bufferEnd && this.buffer[var1 + 1] == '\n') {
            this.nextLineStart = var1 + 2;
         } else {
            this.nextLineStart = var1 + 1;
         }

         ++this.lineNumber;
         return true;
      }
   }

   public void readData(Reader var1) throws IOException {
      int var2 = this.buffer.length;
      int var3 = 0;

      while(true) {
         int var4 = var1.read(this.buffer, var3, var2 - var3);
         if (var4 < 0) {
            this.bufferEnd = var3;
            return;
         }

         var3 += var4;
         if (var2 == var3) {
            var2 *= 2;
            char[] var5 = new char[var2];
            System.arraycopy(this.buffer, 0, var5, 0, var3);
            this.buffer = var5;
         }
      }
   }

   public boolean setReplacement(int var1, int var2, String var3) {
      if (equals(var3, this.buffer, var1, var2)) {
         return false;
      } else {
         ReplaceItem var4 = new ReplaceItem(var1, var2, var3);
         if (this.firstReplace == null) {
            this.firstReplace = this.lastReplace = var4;
         } else if (var1 < this.firstReplace.begin) {
            var4.next = this.firstReplace;
            this.firstReplace = var4;
         } else {
            ReplaceItem var5 = this.firstReplace;

            ReplaceItem var6;
            for(var6 = var5.next; var6 != null; var6 = var6.next) {
               if (var1 < var6.begin) {
                  var4.next = var6;
                  var5.next = var4;
                  break;
               }

               var5 = var6;
            }

            if (var6 == null) {
               this.lastReplace.next = var4;
            }
         }

         return true;
      }
   }

   public void startLineLoop() {
      this.lineNumber = 0;
      this.lineBegin = this.lineEnd = this.nextLineStart = 0;
   }

   public boolean wasModified() {
      return this.firstReplace != null;
   }

   public void writeData(Writer var1) throws IOException {
      int var2 = 0;

      int var4;
      for(ReplaceItem var3 = this.firstReplace; var3 != null; var3 = var3.next) {
         var4 = var3.begin - var2;
         if (var4 > 0) {
            var1.write(this.buffer, var2, var4);
         }

         var1.write(var3.replacement);
         var2 = var3.end;
      }

      var4 = this.bufferEnd - var2;
      if (var4 != 0) {
         var1.write(this.buffer, var2, var4);
      }

   }

   public void writeInitialData(Writer var1) throws IOException {
      var1.write(this.buffer, 0, this.bufferEnd);
   }

   private static class ReplaceItem {
      ReplaceItem next;
      int begin;
      int end;
      String replacement;

      ReplaceItem(int var1, int var2, String var3) {
         this.begin = var1;
         this.end = var2;
         this.replacement = var3;
      }
   }
}
