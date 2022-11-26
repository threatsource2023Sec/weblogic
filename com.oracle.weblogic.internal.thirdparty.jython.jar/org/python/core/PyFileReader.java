package org.python.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

@Untraversable
public class PyFileReader extends PyObject {
   static final int DEFAULT_BUF_SIZE = 1024;
   private final BufferedReader reader;
   private boolean closed;
   private char[] reuseableBuffer = null;

   public PyFileReader(Reader reader) {
      this.reader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
      this.closed = false;
   }

   public boolean closed() {
      return this.closed;
   }

   public void checkClosed() {
      if (this.closed()) {
         throw Py.ValueError("I/O operation on closed file");
      }
   }

   public synchronized void flush() {
      this.checkClosed();
   }

   public void close() {
      try {
         if (!this.closed()) {
            this.reader.close();
            this.closed = true;
         }

      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   protected char[] needBuffer(int size) {
      if (this.reuseableBuffer == null) {
         if (size > 1024) {
            return new char[size];
         }

         this.reuseableBuffer = new char[1024];
      }

      return size <= this.reuseableBuffer.length ? this.reuseableBuffer : new char[size];
   }

   public PyString read(int n) {
      int buflen;
      if (n < 0) {
         synchronized(this.reader) {
            this.checkClosed();
            StringBuilder sb = new StringBuilder();
            char[] cbuf = this.needBuffer(1024);
            buflen = cbuf.length;

            while(true) {
               try {
                  int x = this.reader.read(cbuf, 0, buflen);
                  if (x < 0) {
                     break;
                  }

                  sb.append(cbuf, 0, x);
                  if (x < buflen) {
                     break;
                  }
               } catch (IOException var9) {
                  throw Py.IOError(var9);
               }
            }

            return new PyString(sb.toString());
         }
      } else {
         synchronized(this.reader) {
            this.checkClosed();
            char[] cbuf = this.needBuffer(n);
            int buflen = cbuf.length;

            PyString var10000;
            try {
               buflen = this.reader.read(cbuf, 0, n);
               if (buflen < 1) {
                  var10000 = new PyString("");
                  return var10000;
               }

               var10000 = new PyString(new String(cbuf, 0, buflen));
            } catch (IOException var11) {
               throw Py.IOError(var11);
            }

            return var10000;
         }
      }
   }

   public PyString read() {
      return this.read(-1);
   }

   public PyString readline(int max) {
      if (max >= 0) {
         throw Py.NotImplementedError("size argument to readline not implemented for PyFileReader");
      } else {
         synchronized(this.reader) {
            PyString var10000;
            try {
               String line = this.reader.readLine();
               if (line == null) {
                  var10000 = new PyString("");
                  return var10000;
               }

               var10000 = new PyString(line + "\n");
            } catch (IOException var5) {
               throw Py.IOError(var5);
            }

            return var10000;
         }
      }
   }

   public PyString readline() {
      return this.readline(-1);
   }

   public PyObject readlines(int sizehint) {
      synchronized(this.reader) {
         this.checkClosed();
         PyList list = new PyList();
         int size = 0;

         do {
            PyString line = this.readline(-1);
            int len = line.getString().length();
            if (len == 0) {
               break;
            }

            size += len;
            list.append(line);
         } while(sizehint <= 0 || size < sizehint);

         return list;
      }
   }

   public PyObject readlines() {
      return this.readlines(0);
   }
}
