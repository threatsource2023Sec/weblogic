package org.apache.oro.text.awk;

import java.io.IOException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;

public final class AwkMatcher implements PatternMatcher {
   private int __lastMatchedBufferOffset;
   private AwkMatchResult __lastMatchResult = null;
   private AwkStreamInput __scratchBuffer = new AwkStreamInput();
   private AwkStreamInput __streamSearchBuffer;
   private AwkPattern __awkPattern;
   private int[] __offsets = new int[2];
   private int __beginOffset;

   public AwkMatcher() {
      this.__scratchBuffer._endOfStreamReached = true;
   }

   public boolean matchesPrefix(char[] var1, Pattern var2, int var3) {
      boolean var4 = true;
      this.__awkPattern = (AwkPattern)var2;
      this.__scratchBuffer._buffer = var1;
      this.__scratchBuffer._bufferSize = var1.length;
      this.__scratchBuffer._bufferOffset = this.__beginOffset = 0;
      this.__scratchBuffer._endOfStreamReached = true;
      this.__streamSearchBuffer = this.__scratchBuffer;
      this.__offsets[0] = var3;

      int var7;
      try {
         var7 = this.__streamMatchPrefix();
      } catch (IOException var6) {
         var7 = -1;
      }

      if (var7 < 0) {
         this.__lastMatchResult = null;
         return false;
      } else {
         this.__lastMatchResult = new AwkMatchResult(new String(var1, 0, var7), var3);
         return true;
      }
   }

   public boolean matchesPrefix(char[] var1, Pattern var2) {
      return this.matchesPrefix(var1, var2, 0);
   }

   public boolean matchesPrefix(String var1, Pattern var2) {
      return this.matchesPrefix(var1.toCharArray(), var2, 0);
   }

   public boolean matchesPrefix(PatternMatcherInput var1, Pattern var2) {
      boolean var3 = true;
      this.__awkPattern = (AwkPattern)var2;
      this.__scratchBuffer._buffer = var1.getBuffer();
      this.__scratchBuffer._bufferOffset = this.__beginOffset = var1.getBeginOffset();
      this.__offsets[0] = var1.getCurrentOffset();
      this.__scratchBuffer._bufferSize = var1.length();
      this.__scratchBuffer._endOfStreamReached = true;
      this.__streamSearchBuffer = this.__scratchBuffer;

      int var6;
      try {
         var6 = this.__streamMatchPrefix();
      } catch (IOException var5) {
         var6 = -1;
      }

      if (var6 < 0) {
         this.__lastMatchResult = null;
         return false;
      } else {
         this.__lastMatchResult = new AwkMatchResult(new String(this.__scratchBuffer._buffer, this.__offsets[0], var6), this.__offsets[0]);
         return true;
      }
   }

   public boolean matches(char[] var1, Pattern var2) {
      boolean var3 = true;
      this.__awkPattern = (AwkPattern)var2;
      this.__scratchBuffer._buffer = var1;
      this.__scratchBuffer._bufferSize = var1.length;
      this.__scratchBuffer._bufferOffset = this.__beginOffset = 0;
      this.__scratchBuffer._endOfStreamReached = true;
      this.__streamSearchBuffer = this.__scratchBuffer;
      this.__offsets[0] = 0;

      int var6;
      try {
         var6 = this.__streamMatchPrefix();
      } catch (IOException var5) {
         var6 = -1;
      }

      if (var6 != var1.length) {
         this.__lastMatchResult = null;
         return false;
      } else {
         this.__lastMatchResult = new AwkMatchResult(new String(var1, 0, var6), 0);
         return true;
      }
   }

   public boolean matches(String var1, Pattern var2) {
      return this.matches(var1.toCharArray(), var2);
   }

   public boolean matches(PatternMatcherInput var1, Pattern var2) {
      boolean var3 = true;
      this.__awkPattern = (AwkPattern)var2;
      this.__scratchBuffer._buffer = var1.getBuffer();
      this.__scratchBuffer._bufferSize = var1.length();
      this.__scratchBuffer._bufferOffset = this.__beginOffset = var1.getBeginOffset();
      this.__offsets[0] = var1.getBeginOffset();
      this.__scratchBuffer._endOfStreamReached = true;
      this.__streamSearchBuffer = this.__scratchBuffer;

      int var6;
      try {
         var6 = this.__streamMatchPrefix();
      } catch (IOException var5) {
         var6 = -1;
      }

      if (var6 != this.__scratchBuffer._bufferSize) {
         this.__lastMatchResult = null;
         return false;
      } else {
         this.__lastMatchResult = new AwkMatchResult(new String(this.__scratchBuffer._buffer, this.__offsets[0], this.__scratchBuffer._bufferSize), this.__offsets[0]);
         return true;
      }
   }

   public boolean contains(char[] var1, Pattern var2) {
      this.__awkPattern = (AwkPattern)var2;
      if (this.__awkPattern._hasBeginAnchor && !this.__awkPattern._fastMap[var1[0]]) {
         this.__lastMatchResult = null;
         return false;
      } else {
         this.__scratchBuffer._buffer = var1;
         this.__scratchBuffer._bufferSize = var1.length;
         this.__scratchBuffer._bufferOffset = this.__beginOffset = 0;
         this.__scratchBuffer._endOfStreamReached = true;
         this.__streamSearchBuffer = this.__scratchBuffer;
         this.__lastMatchedBufferOffset = 0;

         try {
            this._search();
         } catch (IOException var4) {
         }

         return this.__lastMatchResult != null;
      }
   }

   public boolean contains(String var1, Pattern var2) {
      return this.contains(var1.toCharArray(), var2);
   }

   public boolean contains(PatternMatcherInput var1, Pattern var2) {
      this.__awkPattern = (AwkPattern)var2;
      this.__scratchBuffer._buffer = var1.getBuffer();
      this.__scratchBuffer._bufferOffset = this.__beginOffset = var1.getBeginOffset();
      this.__lastMatchedBufferOffset = var1.getCurrentOffset();
      if (!this.__awkPattern._hasBeginAnchor || this.__beginOffset == this.__lastMatchedBufferOffset && this.__awkPattern._fastMap[this.__scratchBuffer._buffer[this.__beginOffset]]) {
         this.__scratchBuffer._bufferSize = var1.length();
         this.__scratchBuffer._endOfStreamReached = true;
         this.__streamSearchBuffer = this.__scratchBuffer;

         try {
            this._search();
         } catch (IOException var4) {
         }

         var1.setCurrentOffset(this.__lastMatchedBufferOffset);
         if (this.__lastMatchResult == null) {
            return false;
         } else {
            var1.setMatchOffsets(this.__lastMatchResult.beginOffset(0), this.__lastMatchResult.endOffset(0));
            return true;
         }
      } else {
         this.__lastMatchResult = null;
         return false;
      }
   }

   public boolean contains(AwkStreamInput var1, Pattern var2) throws IOException {
      this.__awkPattern = (AwkPattern)var2;
      if (this.__awkPattern._hasBeginAnchor) {
         if (var1._bufferOffset != 0) {
            this.__lastMatchResult = null;
            return false;
         }

         if (var1.read() && !this.__awkPattern._fastMap[var1._buffer[0]]) {
            this.__lastMatchResult = null;
            return false;
         }
      }

      this.__lastMatchedBufferOffset = var1._currentOffset;
      this.__streamSearchBuffer = var1;
      this.__beginOffset = 0;
      this._search();
      var1._currentOffset = this.__lastMatchedBufferOffset;
      if (this.__lastMatchResult != null) {
         this.__lastMatchResult._incrementMatchBeginOffset(var1._bufferOffset);
         return true;
      } else {
         return false;
      }
   }

   private int __streamMatchPrefix() throws IOException {
      int var2 = 1;
      int var7 = -1;
      int var5;
      int var4 = var5 = this.__offsets[0];
      int var6 = this.__streamSearchBuffer._bufferSize + this.__beginOffset;

      while(var4 < var6) {
         char var1 = this.__streamSearchBuffer._buffer[var4++];
         if (var2 >= this.__awkPattern._numStates) {
            break;
         }

         int var3 = var2;
         int[] var8 = this.__awkPattern._getStateArray(var2);
         var2 = var8[var1];
         if (var2 == 0) {
            this.__awkPattern._createNewState(var3, var1, var8);
            var2 = var8[var1];
         }

         if (var2 == -1) {
            break;
         }

         if (this.__awkPattern._endStates.get(var2)) {
            var7 = var4;
         }

         if (var4 == var6) {
            var4 = this.__streamSearchBuffer._reallocate(var5) + this.__beginOffset;
            var6 = this.__streamSearchBuffer._bufferSize + this.__beginOffset;
            if (var4 != var6) {
               if (var7 != -1) {
                  var7 -= var5;
               }

               var5 = 0;
            }
         }
      }

      this.__offsets[0] = var5;
      this.__offsets[1] = var7 - 1;
      if (var7 == -1 && this.__awkPattern._matchesNullString) {
         return 0;
      } else {
         return !this.__awkPattern._hasEndAnchor || this.__streamSearchBuffer._endOfStreamReached && var7 >= this.__streamSearchBuffer._bufferSize + this.__beginOffset ? var7 - var5 : -1;
      }
   }

   void _search() throws IOException {
      this.__lastMatchResult = null;

      while(true) {
         if (this.__lastMatchedBufferOffset >= this.__streamSearchBuffer._bufferSize + this.__beginOffset) {
            if (this.__streamSearchBuffer._endOfStreamReached) {
               this.__streamSearchBuffer = null;
               return;
            }

            if (!this.__streamSearchBuffer.read()) {
               return;
            }

            this.__lastMatchedBufferOffset = 0;
         }

         int var1;
         for(var1 = this.__lastMatchedBufferOffset; var1 < this.__streamSearchBuffer._bufferSize + this.__beginOffset; var1 = this.__offsets[0] + 1) {
            this.__offsets[0] = var1;
            int var2;
            if (this.__awkPattern._fastMap[this.__streamSearchBuffer._buffer[var1]] && (var2 = this.__streamMatchPrefix()) > -1) {
               this.__lastMatchResult = new AwkMatchResult(new String(this.__streamSearchBuffer._buffer, this.__offsets[0], var2), this.__offsets[0]);
               this.__lastMatchedBufferOffset = var2 > 0 ? this.__offsets[1] + 1 : this.__offsets[0] + 1;
               return;
            }

            if (this.__awkPattern._matchesNullString) {
               this.__lastMatchResult = new AwkMatchResult(new String(), var1);
               this.__lastMatchedBufferOffset = var1 + 1;
               return;
            }
         }

         this.__lastMatchedBufferOffset = var1;
      }
   }

   public MatchResult getMatch() {
      return this.__lastMatchResult;
   }
}
