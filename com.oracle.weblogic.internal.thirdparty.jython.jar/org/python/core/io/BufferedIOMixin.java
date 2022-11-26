package org.python.core.io;

import java.io.InputStream;
import java.io.OutputStream;
import org.python.core.Py;
import org.python.core.PyException;

public abstract class BufferedIOMixin extends BufferedIOBase {
   protected RawIOBase rawIO;
   protected int bufferSize;

   public BufferedIOMixin(RawIOBase rawIO) {
      this(rawIO, 8192);
   }

   public BufferedIOMixin(RawIOBase rawIO, int bufferSize) {
      this.rawIO = rawIO;
      this.bufferSize = bufferSize;
   }

   public long seek(long pos, int whence) {
      return this.rawIO.seek(pos, whence);
   }

   public long tell() {
      return this.rawIO.tell();
   }

   public long truncate(long size) {
      return this.rawIO.truncate(size);
   }

   public void flush() {
      this.rawIO.flush();
   }

   public void close() {
      if (!this.closed()) {
         try {
            this.flush();
         } catch (PyException var2) {
            if (!var2.match(Py.IOError)) {
               throw var2;
            }
         }

         this.rawIO.close();
      }
   }

   public RawIOBase fileno() {
      return this.rawIO.fileno();
   }

   public boolean isatty() {
      return this.rawIO.isatty();
   }

   public boolean readable() {
      return this.rawIO.readable();
   }

   public boolean writable() {
      return this.rawIO.writable();
   }

   public boolean closed() {
      return this.rawIO.closed();
   }

   public InputStream asInputStream() {
      return this.rawIO.asInputStream();
   }

   public OutputStream asOutputStream() {
      return this.rawIO.asOutputStream();
   }
}
