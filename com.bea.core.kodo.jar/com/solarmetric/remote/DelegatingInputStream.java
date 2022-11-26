package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;

public class DelegatingInputStream extends InputStream {
   private final InputStream _in;

   public DelegatingInputStream(InputStream delegate) {
      this._in = delegate;
   }

   public InputStream getDelegate() {
      return this._in;
   }

   public int read() throws IOException {
      return this._in.read();
   }

   public int read(byte[] b) throws IOException {
      return this._in.read(b);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this._in.read(b, off, len);
   }

   public long skip(long skip) throws IOException {
      return this._in.skip(skip);
   }

   public int available() throws IOException {
      return this._in.available();
   }

   public void close() throws IOException {
      this._in.close();
   }

   public void mark(int mark) {
      this._in.mark(mark);
   }

   public void reset() throws IOException {
      this._in.reset();
   }

   public boolean markSupported() {
      return this._in.markSupported();
   }
}
