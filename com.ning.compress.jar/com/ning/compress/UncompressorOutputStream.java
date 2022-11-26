package com.ning.compress;

import java.io.IOException;
import java.io.OutputStream;

public class UncompressorOutputStream extends OutputStream {
   protected final Uncompressor _uncompressor;
   private byte[] _singleByte = null;

   public UncompressorOutputStream(Uncompressor uncomp) {
      this._uncompressor = uncomp;
   }

   public void close() throws IOException {
      this._uncompressor.complete();
   }

   public void flush() {
   }

   public void write(byte[] b) throws IOException {
      this._uncompressor.feedCompressedData(b, 0, b.length);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this._uncompressor.feedCompressedData(b, off, len);
   }

   public void write(int b) throws IOException {
      if (this._singleByte == null) {
         this._singleByte = new byte[1];
      }

      this._singleByte[0] = (byte)b;
      this._uncompressor.feedCompressedData(this._singleByte, 0, 1);
   }
}
