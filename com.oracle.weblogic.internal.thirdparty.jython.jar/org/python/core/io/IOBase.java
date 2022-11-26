package org.python.core.io;

import java.io.InputStream;
import java.io.OutputStream;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import org.python.core.Py;
import org.python.core.PyException;

public abstract class IOBase {
   public static final int DEFAULT_BUFFER_SIZE = 8192;
   protected static final byte LF_BYTE = 10;
   private boolean closed = false;

   public long seek(long pos) {
      return this.seek(pos, 0);
   }

   public long seek(long pos, int whence) {
      this.unsupported("seek");
      return -1L;
   }

   public long tell() {
      return this.seek(0L, 1);
   }

   public long truncate(long size) {
      this.unsupported("truncate");
      return -1L;
   }

   public void flush() {
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

         this.closed = true;
      }
   }

   public RawIOBase fileno() {
      this.unsupported("fileno");
      return null;
   }

   public boolean isatty() {
      this.checkClosed();
      return false;
   }

   public boolean readable() {
      return false;
   }

   public void checkReadable() {
      if (!this.readable()) {
         throw Py.IOError((Constant)Errno.EBADF);
      }
   }

   public boolean writable() {
      return false;
   }

   public void checkWritable() {
      if (!this.writable()) {
         throw Py.IOError((Constant)Errno.EBADF);
      }
   }

   public boolean closed() {
      return this.closed;
   }

   public void checkClosed() {
      if (this.closed()) {
         throw Py.ValueError("I/O operation on closed file");
      }
   }

   public OutputStream asOutputStream() {
      return null;
   }

   public InputStream asInputStream() {
      return null;
   }

   protected void unsupported(String methodName) {
      String qualifiedName = this.getClass().getName();
      String className = qualifiedName.substring(qualifiedName.lastIndexOf(46) + 1);
      throw Py.IOError(String.format("%s.%s() not supported", className, methodName));
   }
}
