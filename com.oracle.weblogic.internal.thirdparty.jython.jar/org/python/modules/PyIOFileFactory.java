package org.python.modules;

import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.__builtin__;

public class PyIOFileFactory {
   private static PyType FileType = PyType.fromClass(PyFile.class);

   private PyIOFileFactory() {
   }

   public static PyIOFile createIOFile(PyObject file) {
      Object f = file.__tojava__(cStringIO.StringIO.class);
      if (f != Py.NoConversion) {
         return new cStringIOFile(file);
      } else {
         return (PyIOFile)(__builtin__.isinstance(file, FileType) ? new FileIOFile(file) : new ObjectIOFile(file));
      }
   }

   static class ObjectIOFile implements PyIOFile, Traverseproc {
      char[] charr = new char[1];
      StringBuilder buff = new StringBuilder();
      PyObject write;
      PyObject read;
      PyObject readline;
      final int BUF_SIZE = 256;

      ObjectIOFile(PyObject file) {
         this.write = file.__findattr__("write");
         this.read = file.__findattr__("read");
         this.readline = file.__findattr__("readline");
      }

      public void write(String str) {
         this.buff.append(str);
         if (this.buff.length() > 256) {
            this.flush();
         }

      }

      public void write(char ch) {
         this.buff.append(ch);
         if (this.buff.length() > 256) {
            this.flush();
         }

      }

      public void flush() {
         this.write.__call__((PyObject)(new PyString(this.buff.toString())));
         this.buff.setLength(0);
      }

      public String read(int len) {
         return this.read.__call__((PyObject)(new PyInteger(len))).toString();
      }

      public String readlineNoNl() {
         String line = this.readline.__call__().toString();
         return line.substring(0, line.length() - 1);
      }

      public int traverse(Visitproc visit, Object arg) {
         int retVal;
         if (this.write != null) {
            retVal = visit.visit(this.write, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.read != null) {
            retVal = visit.visit(this.read, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         return this.readline == null ? 0 : visit.visit(this.readline, arg);
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && (ob == this.write || ob == this.read || ob == this.readline);
      }
   }

   static class FileIOFile implements PyIOFile, Traverseproc {
      PyFile file;

      FileIOFile(PyObject file) {
         this.file = (PyFile)file.__tojava__(PyFile.class);
         if (this.file.getClosed()) {
            throw Py.ValueError("I/O operation on closed file");
         }
      }

      public void write(String str) {
         this.file.write(str);
      }

      public void write(char ch) {
         this.file.write(cStringIO.getString(ch));
      }

      public void flush() {
      }

      public String read(int len) {
         return this.file.read(len).toString();
      }

      public String readlineNoNl() {
         String line = this.file.readline().toString();
         return line.substring(0, line.length() - 1);
      }

      public int traverse(Visitproc visit, Object arg) {
         return this.file == null ? 0 : visit.visit(this.file, arg);
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && ob == this.file;
      }
   }

   static class cStringIOFile implements PyIOFile {
      cStringIO.StringIO file;

      cStringIOFile(PyObject file) {
         this.file = (cStringIO.StringIO)file.__tojava__(Object.class);
      }

      public void write(String str) {
         this.file.write(str);
      }

      public void write(char ch) {
         this.file.writeChar(ch);
      }

      public void flush() {
      }

      public String read(int len) {
         return this.file.read((long)len).asString();
      }

      public String readlineNoNl() {
         return this.file.readlineNoNl().asString();
      }
   }
}
