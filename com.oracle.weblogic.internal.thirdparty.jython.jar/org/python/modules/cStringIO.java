package org.python.modules;

import java.util.Iterator;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.PyUnicode;

public class cStringIO {
   public static final String __doc__ = "A simple fast partial StringIO replacement.\n\nThis module provides a simple useful replacement for\nthe StringIO module that is written in Java.  It does not provide the\nfull generality of StringIO, but it provides enough for most\napplications and is especially useful in conjunction with the\npickle module.\n\nUsage:\n\n  from cStringIO import StringIO\n\n  an_output_stream=StringIO()\n  an_output_stream.write(some_stuff)\n  ...\n  value=an_output_stream.getvalue()\n\n  an_input_stream=StringIO(a_string)\n  spam=an_input_stream.readline()\n  spam=an_input_stream.read(5)\n  an_input_stream.seek(0)           # OK, start over\n  spam=an_input_stream.read()       # and read it all\n  \nIf someone else wants to provide a more complete implementation,\ngo for it. :-)  \n\ncStringIO.java,v 1.10 1999/05/20 18:03:20 fb Exp\nPython-level doc was inserted on 2017/02/01, copied from\ncStringIO.c,v 1.29 1999/06/15 14:10:27 jim Exp\n";
   public static PyType InputType = PyType.fromClass(StringIO.class);
   public static PyType OutputType = PyType.fromClass(StringIO.class);
   public static final String __doc__StringIO = "StringIO([s]) -- Return a StringIO-like stream for reading or writing";
   private static String[] strings = new String[256];

   public static StringIO StringIO() {
      return new StringIO();
   }

   public static StringIO StringIO(CharSequence buffer) {
      return new StringIO(buffer);
   }

   public static StringIO StringIO(PyArray array) {
      return new StringIO(array);
   }

   static String getString(char ch) {
      if (ch > 255) {
         return new String(new char[]{ch});
      } else {
         String s = strings[ch];
         if (s == null) {
            s = new String(new char[]{ch});
            strings[ch] = s;
         }

         return s;
      }
   }

   public static class StringIO extends PyIterator {
      public boolean softspace = false;
      public boolean closed = false;
      public int pos = 0;
      private final StringBuilder buf;
      public static final String __doc__close = "close(): explicitly release resources held.";
      public static final String __doc__isatty = "isatty(): always returns 0";
      public static final String __doc__seek = "seek(position)       -- set the current position\nseek(position, mode) -- mode 0: absolute; 1: relative; 2: relative to EOF";
      public static final String __doc__reset = "reset() -- Reset the file position to the beginning";
      public static final String __doc__tell = "tell() -- get the current position.";
      public static final String __doc__read = "read([s]) -- Read s characters, or the rest of the string";
      public static final String __doc__readline = "readline() -- Read one line";
      public static final String __doc__readlines = "readlines() -- Read all lines";
      public static final String __doc__truncate = "truncate(): truncate the file at the current position.";
      public static final String __doc__write = "write(s) -- Write a string to the file\n\nNote (hack:) writing None resets the buffer";
      public static final String __doc__writelines = "writelines(sequence_of_strings) -> None.  Write the strings to the file.\n\nNote that newlines are not added.  The sequence can be any iterable object\nproducing strings. This is equivalent to calling write() for each string.";
      public static final String __doc__flush = "flush(): does nothing.";
      public static final String __doc__getvalue = "getvalue([use_pos]) -- Get the string value.\nIf use_pos is specified and is a true value, then the string returned\nwill include only the text up to the current file position.\n";

      public StringIO() {
         this.buf = new StringBuilder();
      }

      public StringIO(CharSequence buffer) {
         this.buf = new StringBuilder((CharSequence)(buffer instanceof PyUnicode ? ((PyUnicode)buffer).encode() : buffer));
      }

      public StringIO(PyArray array) {
         this.buf = new StringBuilder(array.tostring());
      }

      private void _complain_ifclosed() {
         if (this.closed) {
            throw Py.ValueError("I/O operation on closed file");
         }
      }

      private int _convert_to_int(long val) {
         if (val > 2147483647L) {
            throw Py.OverflowError("long int too large to convert to int");
         } else {
            return (int)val;
         }
      }

      public void __setattr__(String name, PyObject value) {
         if (name == "softspace") {
            this.softspace = value.__nonzero__();
         } else {
            super.__setattr__(name, value);
         }
      }

      public PyObject __iternext__() {
         this._complain_ifclosed();
         PyString r = this.readline();
         return r.__len__() == 0 ? null : r;
      }

      public void close() {
         this.closed = true;
      }

      public boolean isatty() {
         this._complain_ifclosed();
         return false;
      }

      public void seek(long pos) {
         this.seek(pos, 0);
      }

      public synchronized void seek(long pos, int mode) {
         this._complain_ifclosed();
         switch (mode) {
            case 0:
            default:
               this.pos = this._convert_to_int(pos);
               break;
            case 1:
               this.pos = (int)((long)this.pos + pos);
               break;
            case 2:
               this.pos = this._convert_to_int(pos + (long)this.buf.length());
         }

      }

      public synchronized void reset() {
         this.pos = 0;
      }

      public synchronized int tell() {
         this._complain_ifclosed();
         return this.pos;
      }

      public PyString read() {
         return this.read(-1L);
      }

      public synchronized PyString read(long size) {
         this._complain_ifclosed();
         this._convert_to_int(size);
         int len = this.buf.length();
         String substr;
         if (size < 0L) {
            substr = this.pos >= len ? "" : this.buf.substring(this.pos);
            this.pos = len;
         } else {
            int newpos = this._convert_to_int(Math.min((long)this.pos + size, (long)len));
            substr = this.buf.substring(this.pos, newpos);
            this.pos = newpos;
         }

         return new PyString(substr);
      }

      public PyString readline() {
         return this.readline(-1L);
      }

      public synchronized PyString readline(long size) {
         this._complain_ifclosed();
         this._convert_to_int(size);
         int len = this.buf.length();
         if (this.pos == len) {
            return new PyString("");
         } else {
            int i = this.buf.indexOf("\n", this.pos);
            int newpos = i < 0 ? len : i + 1;
            if (size >= 0L) {
               newpos = this._convert_to_int(Math.min((long)(newpos - this.pos), size) + (long)this.pos);
            }

            String r = this.buf.substring(this.pos, newpos);
            this.pos = newpos;
            return new PyString(r);
         }
      }

      public synchronized PyString readlineNoNl() {
         this._complain_ifclosed();
         int len = this.buf.length();
         int i = this.buf.indexOf("\n", this.pos);
         int newpos = i < 0 ? len : i;
         String r = this.buf.substring(this.pos, newpos);
         this.pos = newpos;
         if (this.pos < len) {
            ++this.pos;
         }

         return new PyString(r);
      }

      public PyObject readlines() {
         return this.readlines(0L);
      }

      public PyObject readlines(long sizehint) {
         this._complain_ifclosed();
         int sizehint_int = (int)sizehint;
         int total = 0;
         PyList lines = new PyList();

         for(PyString line = this.readline(); line.__len__() > 0; line = this.readline()) {
            lines.append(line);
            total += line.__len__();
            if (0 < sizehint_int && sizehint_int <= total) {
               break;
            }
         }

         return lines;
      }

      public synchronized void truncate() {
         this.buf.setLength(this.pos);
      }

      public synchronized void truncate(long pos) {
         if (pos < 0L) {
            throw Py.IOError("Negative size not allowed");
         } else {
            int pos_int = this._convert_to_int(pos);
            if (pos_int < 0) {
               pos_int = this.pos;
            }

            this.buf.setLength(pos_int);
            this.pos = pos_int;
         }
      }

      public void write(PyObject obj) {
         this.write(obj.toString());
      }

      public synchronized void write(String s) {
         this._complain_ifclosed();
         int spos = this.pos;
         int slen = this.buf.length();
         if (spos == slen) {
            this.buf.append(s);
            this.buf.setLength(slen + s.length());
            this.pos = spos + s.length();
         } else {
            int newpos;
            if (spos > slen) {
               newpos = spos - slen;
               char[] bytes = new char[newpos];

               for(int i = 0; i < newpos - 1; ++i) {
                  bytes[i] = 0;
               }

               this.buf.append(bytes);
               slen = spos;
            }

            newpos = spos + s.length();
            if (spos < slen) {
               if (newpos > slen) {
                  this.buf.replace(spos, slen, s);
                  this.buf.append(s.substring(slen - spos));
                  slen = newpos;
               } else {
                  this.buf.replace(spos, spos + s.length(), s);
               }
            } else {
               this.buf.append(s);
               slen = newpos;
            }

            this.buf.setLength(slen);
            this.pos = newpos;
         }
      }

      public synchronized void writeChar(char ch) {
         int len = this.buf.length();
         if (len <= this.pos) {
            this.buf.setLength(this.pos + 1);
         }

         this.buf.setCharAt(this.pos++, ch);
      }

      public void writelines(PyObject lines) {
         Iterator var2 = lines.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject line = (PyObject)var2.next();
            this.write(line);
         }

      }

      public void flush() {
         this._complain_ifclosed();
      }

      public synchronized PyString getvalue() {
         this._complain_ifclosed();
         return new PyString(this.buf.toString());
      }
   }

   private static class os {
      public static final int SEEK_SET = 0;
      public static final int SEEK_CUR = 1;
      public static final int SEEK_END = 2;
   }
}
