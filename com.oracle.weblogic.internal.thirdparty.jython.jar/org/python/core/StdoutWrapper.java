package org.python.core;

import java.io.OutputStream;
import org.python.core.util.StringUtil;

public class StdoutWrapper extends OutputStream {
   protected String name = "stdout";

   protected PyObject getObject(PySystemState ss) {
      return ss.stdout;
   }

   protected void setObject(PySystemState ss, PyObject out) {
      ss.stdout = out;
   }

   protected PyObject myFile() {
      PySystemState ss = Py.getSystemState();
      PyObject out = this.getObject(ss);
      if (out == null) {
         throw Py.AttributeError("missing sys." + this.name);
      } else if (out instanceof PyAttributeDeleted) {
         throw Py.RuntimeError("lost sys." + this.name);
      } else {
         if (out.getJavaProxy() != null) {
            PyFile f = null;
            Object tojava = out.__tojava__(OutputStream.class);
            if (tojava != null && tojava != Py.NoConversion) {
               f = new PyFile((OutputStream)tojava);
            }

            if (f != null) {
               this.setObject(ss, f);
               return f;
            }
         }

         return out;
      }
   }

   public void flush() {
      PyObject out = this.myFile();
      if (out instanceof PyFile) {
         ((PyFile)out).flush();
      } else {
         try {
            out.invoke("flush");
         } catch (PyException var3) {
         }
      }

   }

   public void write(String s) {
      PyObject out = this.myFile();
      if (out instanceof PyFile) {
         ((PyFile)out).write(s);
      } else {
         out.invoke("write", (PyObject)(new PyString(s)));
      }

   }

   public void write(int i) {
      this.write(new String(new char[]{(char)i}));
   }

   public void write(byte[] data, int off, int len) {
      this.write(StringUtil.fromBytes(data, off, len));
   }

   public void flushLine() {
      PyObject out = this.myFile();
      if (out instanceof PyFile) {
         PyFile file = (PyFile)out;
         if (file.softspace) {
            file.write("\n");
         }

         file.flush();
         file.softspace = false;
      } else {
         PyObject ss = out.__findattr__("softspace");
         if (ss != null && ss.__nonzero__()) {
            out.invoke("write", (PyObject)Py.Newline);
         }

         try {
            out.invoke("flush");
         } catch (PyException var4) {
         }

         out.__setattr__((String)"softspace", Py.Zero);
      }

   }

   private String printToFile(PyFile file, PyObject o) {
      if (!(o instanceof PyUnicode)) {
         o = ((PyObject)o).__str__();
      }

      String bytes;
      if (o instanceof PyUnicode) {
         bytes = ((PyUnicode)o).encode(file.encoding, file.errors);
      } else {
         bytes = ((PyString)o).getString();
      }

      file.write(bytes);
      return bytes;
   }

   private String printToFileWriter(PyFileWriter file, PyObject o) {
      String chars;
      if (o instanceof PyUnicode) {
         chars = ((PyString)o).getString();
      } else {
         chars = o.__str__().getString();
      }

      file.write(chars);
      return chars;
   }

   private void printToFileObject(PyObject file, PyObject o) {
      if (!(o instanceof PyUnicode)) {
         o = ((PyObject)o).__str__();
      }

      file.invoke("write", (PyObject)o);
   }

   public void print(PyObject[] args, PyObject sep, PyObject end) {
      PyObject out = this.myFile();
      int i;
      if (out instanceof PyFile) {
         PyFile file = (PyFile)out;

         for(i = 0; i < args.length; ++i) {
            this.printToFile(file, args[i]);
            if (i < args.length - 1) {
               this.printToFile(file, sep);
            }
         }

         this.printToFile(file, end);
      } else if (out instanceof PyFileWriter) {
         PyFileWriter file = (PyFileWriter)out;

         for(i = 0; i < args.length; ++i) {
            this.printToFileWriter(file, args[i]);
            if (i < args.length - 1) {
               this.printToFileWriter(file, sep);
            }
         }

         this.printToFileWriter(file, end);
      } else {
         for(int i = 0; i < args.length; ++i) {
            this.printToFileObject(out, args[i]);
            if (i < args.length - 1) {
               this.printToFileObject(out, sep);
            }
         }

         this.printToFileObject(out, end);
      }

   }

   public void print(PyObject o, boolean space, boolean newline) {
      PyObject out = this.myFile();
      String s;
      int len;
      if (out instanceof PyFile) {
         PyFile file = (PyFile)out;
         if (file.softspace) {
            file.write(" ");
            file.softspace = false;
         }

         s = this.printToFile(file, o);
         if (o instanceof PyString) {
            len = s.length();
            if (len == 0 || !Character.isWhitespace(s.charAt(len - 1)) || s.charAt(len - 1) == ' ') {
               file.softspace = space;
            }
         } else {
            file.softspace = space;
         }

         if (newline) {
            file.write("\n");
            file.softspace = false;
         }

         file.flush();
      } else if (out instanceof PyFileWriter) {
         PyFileWriter file = (PyFileWriter)out;
         if (file.softspace) {
            file.write(" ");
            file.softspace = false;
         }

         s = this.printToFileWriter(file, o);
         if (o instanceof PyString) {
            len = s.length();
            if (len == 0 || !Character.isWhitespace(s.charAt(len - 1)) || s.charAt(len - 1) == ' ') {
               file.softspace = space;
            }
         } else {
            file.softspace = space;
         }

         if (newline) {
            file.write("\n");
            file.softspace = false;
         }

         file.flush();
      } else {
         PyObject ss = out.__findattr__("softspace");
         if (ss != null && ss.__nonzero__()) {
            out.invoke("write", (PyObject)Py.Space);
            out.__setattr__((String)"softspace", Py.Zero);
         }

         this.printToFileObject(out, o);
         if (o instanceof PyString) {
            s = o.toString();
            len = s.length();
            if (len == 0 || !Character.isWhitespace(s.charAt(len - 1)) || s.charAt(len - 1) == ' ') {
               out.__setattr__((String)"softspace", space ? Py.One : Py.Zero);
            }
         } else {
            out.__setattr__((String)"softspace", space ? Py.One : Py.Zero);
         }

         if (newline) {
            out.invoke("write", (PyObject)Py.Newline);
            out.__setattr__((String)"softspace", Py.Zero);
         }
      }

   }

   public void print(String s) {
      this.print(Py.newUnicode(s), false, false);
   }

   public void println(String s) {
      this.print(Py.newUnicode(s), false, true);
   }

   public void print(PyObject o) {
      this.print(o, false, false);
   }

   public void printComma(PyObject o) {
      this.print(o, true, false);
   }

   public void println(PyObject o) {
      this.print(o, false, true);
   }

   public void println() {
      this.println(false);
   }

   public void println(boolean useUnicode) {
      PyObject out = this.myFile();
      if (out instanceof PyFile) {
         PyFile file = (PyFile)out;
         file.write("\n");
         file.flush();
         file.softspace = false;
      } else {
         if (useUnicode) {
            out.invoke("write", (PyObject)Py.UnicodeNewline);
         } else {
            out.invoke("write", (PyObject)Py.Newline);
         }

         out.__setattr__((String)"softspace", Py.Zero);
      }

   }
}
