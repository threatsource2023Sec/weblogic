package org.glassfish.grizzly.compression.lzma.impl.lz;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;

public class InWindow {
   public byte[] _bufferBase;
   Buffer _buffer;
   int _posLimit;
   boolean _streamEndWasReached;
   int _pointerToLastSafePosition;
   public int _bufferOffset;
   public int _blockSize;
   public int _pos;
   int _keepSizeBefore;
   int _keepSizeAfter;
   public int _streamPos;

   public void moveBlock() {
      int offset = this._bufferOffset + this._pos - this._keepSizeBefore;
      if (offset > 0) {
         --offset;
      }

      int numBytes = this._bufferOffset + this._streamPos - offset;
      System.arraycopy(this._bufferBase, offset, this._bufferBase, 0, numBytes);
      this._bufferOffset -= offset;
   }

   public void readBlock() throws IOException {
      if (!this._streamEndWasReached) {
         while(true) {
            int size = 0 - this._bufferOffset + this._blockSize - this._streamPos;
            if (size == 0) {
               return;
            }

            int pos = this._buffer.position();
            size = Math.min(size, this._buffer.remaining());
            this._buffer.get(this._bufferBase, this._bufferOffset + this._streamPos, size);
            int numReadBytes = this._buffer.position() - pos;
            if (numReadBytes == 0) {
               this._posLimit = this._streamPos;
               int pointerToPostion = this._bufferOffset + this._posLimit;
               if (pointerToPostion > this._pointerToLastSafePosition) {
                  this._posLimit = this._pointerToLastSafePosition - this._bufferOffset;
               }

               this._streamEndWasReached = true;
               return;
            }

            this._streamPos += numReadBytes;
            if (this._streamPos >= this._pos + this._keepSizeAfter) {
               this._posLimit = this._streamPos - this._keepSizeAfter;
            }
         }
      }
   }

   void free() {
      this._bufferBase = null;
   }

   public void create(int keepSizeBefore, int keepSizeAfter, int keepSizeReserv) {
      this._keepSizeBefore = keepSizeBefore;
      this._keepSizeAfter = keepSizeAfter;
      int blockSize = keepSizeBefore + keepSizeAfter + keepSizeReserv;
      if (this._bufferBase == null || this._blockSize != blockSize) {
         this.free();
         this._blockSize = blockSize;
         this._bufferBase = new byte[this._blockSize];
      }

      this._pointerToLastSafePosition = this._blockSize - keepSizeAfter;
   }

   public void setBuffer(Buffer buffer) {
      this._buffer = buffer;
   }

   public void releaseBuffer() {
      this._buffer = null;
   }

   public void init() throws IOException {
      this._bufferOffset = 0;
      this._pos = 0;
      this._streamPos = 0;
      this._streamEndWasReached = false;
      this.readBlock();
   }

   public void movePos() throws IOException {
      ++this._pos;
      if (this._pos > this._posLimit) {
         int pointerToPostion = this._bufferOffset + this._pos;
         if (pointerToPostion > this._pointerToLastSafePosition) {
            this.moveBlock();
         }

         this.readBlock();
      }

   }

   public byte getIndexByte(int index) {
      return this._bufferBase[this._bufferOffset + this._pos + index];
   }

   public int getMatchLen(int index, int distance, int limit) {
      if (this._streamEndWasReached && this._pos + index + limit > this._streamPos) {
         limit = this._streamPos - (this._pos + index);
      }

      ++distance;
      int pby = this._bufferOffset + this._pos + index;

      int i;
      for(i = 0; i < limit && this._bufferBase[pby + i] == this._bufferBase[pby + i - distance]; ++i) {
      }

      return i;
   }

   public int getNumAvailableBytes() {
      return this._streamPos - this._pos;
   }

   public void reduceOffsets(int subValue) {
      this._bufferOffset += subValue;
      this._posLimit -= subValue;
      this._pos -= subValue;
      this._streamPos -= subValue;
   }
}
