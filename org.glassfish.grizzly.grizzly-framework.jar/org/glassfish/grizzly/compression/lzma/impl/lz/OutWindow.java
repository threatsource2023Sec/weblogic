package org.glassfish.grizzly.compression.lzma.impl.lz;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class OutWindow {
   LZMADecoder.LZMAInputState _decoderState;
   byte[] _buffer;
   int _pos;
   int _windowSize = 0;
   int _streamPos;

   public void create(int windowSize) {
      if (this._buffer == null || this._windowSize != windowSize) {
         this._buffer = new byte[windowSize];
      }

      this._windowSize = windowSize;
      this._pos = 0;
      this._streamPos = 0;
   }

   public void initFromState(LZMADecoder.LZMAInputState decoderState) throws IOException {
      this._decoderState = decoderState;
   }

   public void releaseBuffer() throws IOException {
      this._decoderState = null;
   }

   public void init(boolean solid) {
      if (!solid) {
         this._streamPos = 0;
         this._pos = 0;
      }

   }

   public void flush() throws IOException {
      int size = this._pos - this._streamPos;
      if (size != 0) {
         Buffer dst = this._decoderState.getDst();
         if (dst == null || dst.remaining() < size) {
            dst = resizeBuffer(this._decoderState.getMemoryManager(), dst, size);
            this._decoderState.setDst(dst);
         }

         dst.put(this._buffer, this._streamPos, size);
         dst.trim();
         dst.position(dst.limit());
         if (this._pos >= this._windowSize) {
            this._pos = 0;
         }

         this._streamPos = this._pos;
      }
   }

   public void copyBlock(int distance, int len) throws IOException {
      int pos = this._pos - distance - 1;
      if (pos < 0) {
         pos += this._windowSize;
      }

      for(; len != 0; --len) {
         if (pos >= this._windowSize) {
            pos = 0;
         }

         this._buffer[this._pos++] = this._buffer[pos++];
         if (this._pos >= this._windowSize) {
            this.flush();
         }
      }

   }

   public void putByte(byte b) throws IOException {
      this._buffer[this._pos++] = b;
      if (this._pos >= this._windowSize) {
         this.flush();
      }

   }

   public byte getByte(int distance) {
      int pos = this._pos - distance - 1;
      if (pos < 0) {
         pos += this._windowSize;
      }

      return this._buffer[pos];
   }

   private static Buffer resizeBuffer(MemoryManager memoryManager, Buffer buffer, int grow) {
      return buffer == null ? memoryManager.allocate(Math.max(grow, 4096)) : memoryManager.reallocate(buffer, Math.max(buffer.capacity() + grow, buffer.capacity() * 3 / 2 + 1));
   }
}
