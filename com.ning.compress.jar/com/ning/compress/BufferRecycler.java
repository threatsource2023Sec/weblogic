package com.ning.compress;

import java.lang.ref.SoftReference;

public final class BufferRecycler {
   private static final int MIN_ENCODING_BUFFER = 4000;
   private static final int MIN_OUTPUT_BUFFER = 8000;
   protected static final ThreadLocal _recyclerRef = new ThreadLocal();
   private byte[] _inputBuffer;
   private byte[] _outputBuffer;
   private byte[] _decodingBuffer;
   private byte[] _encodingBuffer;
   private int[] _encodingHash;

   public static BufferRecycler instance() {
      SoftReference ref = (SoftReference)_recyclerRef.get();
      BufferRecycler br = ref == null ? null : (BufferRecycler)ref.get();
      if (br == null) {
         br = new BufferRecycler();
         _recyclerRef.set(new SoftReference(br));
      }

      return br;
   }

   public byte[] allocEncodingBuffer(int minSize) {
      byte[] buf = this._encodingBuffer;
      if (buf != null && buf.length >= minSize) {
         this._encodingBuffer = null;
      } else {
         buf = new byte[Math.max(minSize, 4000)];
      }

      return buf;
   }

   public void releaseEncodeBuffer(byte[] buffer) {
      if (this._encodingBuffer == null || buffer != null && buffer.length > this._encodingBuffer.length) {
         this._encodingBuffer = buffer;
      }

   }

   public byte[] allocOutputBuffer(int minSize) {
      byte[] buf = this._outputBuffer;
      if (buf != null && buf.length >= minSize) {
         this._outputBuffer = null;
      } else {
         buf = new byte[Math.max(minSize, 8000)];
      }

      return buf;
   }

   public void releaseOutputBuffer(byte[] buffer) {
      if (this._outputBuffer == null || buffer != null && buffer.length > this._outputBuffer.length) {
         this._outputBuffer = buffer;
      }

   }

   public int[] allocEncodingHash(int suggestedSize) {
      int[] buf = this._encodingHash;
      if (buf != null && buf.length >= suggestedSize) {
         this._encodingHash = null;
      } else {
         buf = new int[suggestedSize];
      }

      return buf;
   }

   public void releaseEncodingHash(int[] buffer) {
      if (this._encodingHash == null || buffer != null && buffer.length > this._encodingHash.length) {
         this._encodingHash = buffer;
      }

   }

   public byte[] allocInputBuffer(int minSize) {
      byte[] buf = this._inputBuffer;
      if (buf != null && buf.length >= minSize) {
         this._inputBuffer = null;
      } else {
         buf = new byte[Math.max(minSize, 8000)];
      }

      return buf;
   }

   public void releaseInputBuffer(byte[] buffer) {
      if (this._inputBuffer == null || buffer != null && buffer.length > this._inputBuffer.length) {
         this._inputBuffer = buffer;
      }

   }

   public byte[] allocDecodeBuffer(int size) {
      byte[] buf = this._decodingBuffer;
      if (buf != null && buf.length >= size) {
         this._decodingBuffer = null;
      } else {
         buf = new byte[size];
      }

      return buf;
   }

   public void releaseDecodeBuffer(byte[] buffer) {
      if (this._decodingBuffer == null || buffer != null && buffer.length > this._decodingBuffer.length) {
         this._decodingBuffer = buffer;
      }

   }
}
