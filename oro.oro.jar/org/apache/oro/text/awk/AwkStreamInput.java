package org.apache.oro.text.awk;

import java.io.IOException;
import java.io.Reader;

public final class AwkStreamInput {
   static final int _DEFAULT_BUFFER_INCREMENT = 2048;
   private Reader __searchStream;
   private int __bufferIncrementUnit;
   boolean _endOfStreamReached;
   int _bufferSize;
   int _bufferOffset;
   int _currentOffset;
   char[] _buffer;

   AwkStreamInput() {
      this._currentOffset = 0;
   }

   public AwkStreamInput(Reader var1, int var2) {
      this.__searchStream = var1;
      this.__bufferIncrementUnit = var2;
      this._buffer = new char[var2];
      this._bufferOffset = this._bufferSize = this._currentOffset = 0;
      this._endOfStreamReached = false;
   }

   public AwkStreamInput(Reader var1) {
      this(var1, 2048);
   }

   int _reallocate(int var1) throws IOException {
      if (this._endOfStreamReached) {
         return this._bufferSize;
      } else {
         int var2 = this._bufferSize - var1;
         char[] var4 = new char[var2 + this.__bufferIncrementUnit];
         int var3 = this.__searchStream.read(var4, var2, this.__bufferIncrementUnit);
         if (var3 <= 0) {
            this._endOfStreamReached = true;
            if (var3 == 0) {
               throw new IOException("read from input stream returned 0 bytes.");
            } else {
               return this._bufferSize;
            }
         } else {
            this._bufferOffset += var1;
            this._bufferSize = var2 + var3;
            System.arraycopy(this._buffer, var1, var4, 0, var2);
            this._buffer = var4;
            return var2;
         }
      }
   }

   boolean read() throws IOException {
      this._bufferOffset += this._bufferSize;
      this._bufferSize = this.__searchStream.read(this._buffer);
      this._endOfStreamReached = this._bufferSize == -1;
      return !this._endOfStreamReached;
   }

   public boolean endOfStream() {
      return this._endOfStreamReached;
   }
}
