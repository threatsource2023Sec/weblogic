package org.python.core;

import java.io.IOException;
import java.io.Writer;

@Untraversable
public class PyFileWriter extends PyObject {
   private final Writer writer;
   private boolean closed;
   public boolean softspace = false;

   public PyFileWriter(Writer writer) {
      this.writer = writer;
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

      try {
         this.writer.flush();
      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   public void close() {
      try {
         this.writer.close();
         this.closed = true;
      } catch (IOException var2) {
         throw Py.IOError(var2);
      }
   }

   public void write(PyObject o) {
      if (o instanceof PyUnicode) {
         this.write(((PyUnicode)o).getString());
      } else {
         if (!(o instanceof PyString)) {
            throw Py.TypeError("write requires a string as its argument");
         }

         this.write(((PyString)o).getString());
      }

   }

   public synchronized void write(String s) {
      this.checkClosed();

      try {
         this.writer.write(s);
      } catch (IOException var3) {
         throw Py.IOError(var3);
      }
   }

   public synchronized void writelines(PyObject a) {
      this.checkClosed();
      PyObject iter = Py.iter(a, "writelines() requires an iterable argument");
      PyObject item = null;

      while((item = iter.__iternext__()) != null) {
         if (!(item instanceof PyString)) {
            throw Py.TypeError("writelines() argument must be a sequence of strings");
         }

         this.write(item);
      }

   }
}
