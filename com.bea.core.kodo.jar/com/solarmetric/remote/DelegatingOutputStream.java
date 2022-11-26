package com.solarmetric.remote;

import java.io.IOException;
import java.io.OutputStream;

public class DelegatingOutputStream extends OutputStream {
   final OutputStream _out;

   public DelegatingOutputStream(OutputStream out) {
      this._out = out;
   }

   public OutputStream getDelegate() {
      return this._out;
   }

   public void write(int b) throws IOException {
      this._out.write(b);
   }

   public void write(byte[] b) throws IOException {
      this._out.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this._out.write(b, off, len);
   }

   public void flush() throws IOException {
      this._out.flush();
   }

   public void close() throws IOException {
      this._out.close();
   }
}
