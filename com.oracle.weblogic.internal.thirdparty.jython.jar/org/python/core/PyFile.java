package org.python.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import org.python.core.finalization.FinalizableBuiltin;
import org.python.core.finalization.FinalizeTrigger;
import org.python.core.io.BinaryIOWrapper;
import org.python.core.io.BufferedIOBase;
import org.python.core.io.BufferedRandom;
import org.python.core.io.BufferedReader;
import org.python.core.io.BufferedWriter;
import org.python.core.io.FileIO;
import org.python.core.io.LineBufferedRandom;
import org.python.core.io.LineBufferedWriter;
import org.python.core.io.RawIOBase;
import org.python.core.io.StreamIO;
import org.python.core.io.TextIOBase;
import org.python.core.io.TextIOWrapper;
import org.python.core.io.UniversalIOWrapper;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "file",
   doc = "file(name[, mode[, buffering]]) -> file object\n\nOpen a file.  The mode can be 'r', 'w' or 'a' for reading (default),\nwriting or appending.  The file will be created if it doesn't exist\nwhen opened for writing or appending; it will be truncated when\nopened for writing.  Add a 'b' to the mode for binary files.\nAdd a '+' to the mode to allow simultaneous reading and writing.\nIf the buffering argument is given, 0 means unbuffered, 1 means line\nbuffered, and larger numbers specify the buffer size.  The preferred way\nto open a file is with the builtin open() function.\nAdd a 'U' to mode to open the file for input with universal newline\nsupport.  Any line ending in the input file will be seen as a '\\n'\nin Python.  Also, a file so opened gains the attribute 'newlines';\nthe value for this attribute is one of None (no newline read yet),\n'\\r', '\\n', '\\r\\n' or a tuple containing all the newline types seen.\n\n'U' cannot be combined with 'w' or '+' mode.\n"
)
public class PyFile extends PyObject implements FinalizableBuiltin, Traverseproc {
   public static final PyType TYPE;
   public PyObject name;
   public String mode;
   public String encoding;
   public String errors;
   public boolean softspace;
   private boolean reading;
   private boolean writing;
   private boolean appending;
   private boolean updating;
   private boolean binary;
   private boolean universal;
   private TextIOBase file;
   private Closer closer;

   public PyFile() {
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyFile(PyType subType) {
      super(subType);
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyFile(RawIOBase raw, String name, String mode, int bufsize) {
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      this.parseMode(mode);
      this.file___init__(raw, name, mode, bufsize);
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyFile(InputStream istream, String name, String mode, int bufsize, boolean closefd) {
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      this.parseMode(mode);
      this.file___init__(new StreamIO(istream, closefd), (String)name, mode, bufsize);
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyFile(InputStream istream, String mode, int bufsize) {
      this(istream, "<Java InputStream '" + istream + "' as file>", mode, bufsize, true);
   }

   public PyFile(InputStream istream, String mode) {
      this((InputStream)istream, mode, -1);
   }

   public PyFile(InputStream istream, int bufsize) {
      this(istream, "r", bufsize);
   }

   public PyFile(InputStream istream) {
      this((InputStream)istream, -1);
   }

   public PyFile(OutputStream ostream, String name, String mode, int bufsize, boolean closefd) {
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      this.parseMode(mode);
      this.file___init__(new StreamIO(ostream, closefd), (String)name, mode, bufsize);
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyFile(OutputStream ostream, String mode, int bufsize) {
      this(ostream, "<Java OutputStream '" + ostream + "' as file>", mode, bufsize, true);
   }

   public PyFile(OutputStream ostream, int bufsize) {
      this(ostream, "w", bufsize);
   }

   public PyFile(OutputStream ostream) {
      this((OutputStream)ostream, -1);
   }

   public PyFile(String name, String mode, int bufsize) {
      this.softspace = false;
      this.reading = false;
      this.writing = false;
      this.appending = false;
      this.updating = false;
      this.binary = false;
      this.universal = false;
      this.file___init__(new FileIO(name, this.parseMode(mode)), (String)name, mode, bufsize);
      FinalizeTrigger.ensureFinalizer(this);
   }

   @ExposedNew
   final void file___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("file", args, kwds, new String[]{"name", "mode", "buffering"}, 1);
      PyObject name = ap.getPyObject(0);
      String mode = ap.getString(1, "r");
      int bufsize = ap.getInt(2, -1);
      this.file___init__(new FileIO((PyString)name, this.parseMode(mode)), (PyObject)name, mode, bufsize);
      this.closer = new Closer(this.file, Py.getSystemState());
   }

   private void file___init__(RawIOBase raw, String name, String mode, int bufsize) {
      this.file___init__(raw, (PyObject)Py.newStringOrUnicode(name), mode, bufsize);
   }

   private void file___init__(RawIOBase raw, PyObject name, String mode, int bufsize) {
      this.name = name;
      this.mode = mode;
      BufferedIOBase buffer = this.createBuffer(raw, bufsize);
      if (this.universal) {
         this.file = new UniversalIOWrapper(buffer);
      } else if (!this.binary) {
         this.file = new TextIOWrapper(buffer);
      } else {
         this.file = new BinaryIOWrapper(buffer);
      }

   }

   void setEncoding(String encoding, String errors) {
      this.encoding = encoding;
      this.errors = errors;
   }

   private BufferedIOBase createBuffer(RawIOBase raw, int bufsize) {
      if (bufsize < 0) {
         bufsize = 8192;
      }

      boolean lineBuffered = bufsize == 1;
      Object buffer;
      if (this.updating) {
         buffer = lineBuffered ? new LineBufferedRandom(raw) : new BufferedRandom(raw, bufsize);
      } else if (!this.writing && !this.appending) {
         if (!this.reading) {
            throw Py.ValueError("unknown mode: '" + this.mode + "'");
         }

         buffer = new BufferedReader(raw, lineBuffered ? 8192 : bufsize);
      } else {
         buffer = lineBuffered ? new LineBufferedWriter(raw) : new BufferedWriter(raw, bufsize);
      }

      return (BufferedIOBase)buffer;
   }

   private String parseMode(String mode) {
      String message = null;
      boolean duplicate = false;
      boolean invalid = false;
      boolean text_intent = false;
      int n = mode.length();

      for(int i = 0; i < n; ++i) {
         char c = mode.charAt(i);
         switch (c) {
            case '+':
               duplicate = this.updating;
               this.updating = true;
               break;
            case 'U':
               duplicate = this.universal;
               this.universal = true;
               break;
            case 'a':
               duplicate = this.appending;
               this.appending = true;
               break;
            case 'b':
               duplicate = this.binary;
               this.binary = true;
               break;
            case 'r':
               duplicate = this.reading;
               this.reading = true;
               break;
            case 't':
               duplicate = text_intent;
               text_intent = true;
               this.binary = false;
               break;
            case 'w':
               duplicate = this.writing;
               this.writing = true;
               break;
            default:
               invalid = true;
         }

         if (duplicate) {
            invalid = true;
            break;
         }
      }

      this.reading |= this.universal;
      this.binary |= this.universal;
      StringBuilder fileioMode = new StringBuilder();
      if (!invalid) {
         if (!this.universal || !this.writing && !this.appending) {
            if (this.reading) {
               fileioMode.append('r');
            }

            if (this.writing) {
               fileioMode.append('w');
            }

            if (this.appending) {
               fileioMode.append('a');
            }

            if (fileioMode.length() != 1) {
               message = "mode string must begin with one of 'r', 'w', 'a' or 'U', not '" + mode + "'";
            }

            if (this.updating) {
               fileioMode.append('+');
            }
         } else {
            message = "universal newline mode can only be used with modes starting with 'r'";
         }

         invalid |= message != null;
      }

      if (invalid) {
         if (message == null) {
            message = String.format("invalid mode: '%.20s'", mode);
         }

         throw Py.ValueError(message);
      } else {
         return fileioMode.toString();
      }
   }

   final synchronized PyString file_read(int size) {
      this.checkClosed();
      return new PyString(this.file.read(size));
   }

   public PyString read(int size) {
      return this.file_read(size);
   }

   public PyString read() {
      return this.file_read(-1);
   }

   final synchronized int file_readinto(PyObject buf) {
      this.checkClosed();
      return this.file.readinto(buf);
   }

   public int readinto(PyObject buf) {
      return this.file_readinto(buf);
   }

   final synchronized PyString file_readline(int max) {
      this.checkClosed();
      return new PyString(this.file.readline(max));
   }

   public PyString readline(int max) {
      return this.file_readline(max);
   }

   public PyString readline() {
      return this.file_readline(-1);
   }

   final synchronized PyObject file_readlines(int sizehint) {
      this.checkClosed();
      PyList list = new PyList();
      int count = 0;

      do {
         String line = this.file.readline(-1);
         int len = line.length();
         if (len == 0) {
            break;
         }

         count += len;
         list.append(new PyString(line));
      } while(sizehint <= 0 || count < sizehint);

      return list;
   }

   public PyObject readlines(int sizehint) {
      return this.file_readlines(sizehint);
   }

   public PyObject readlines() {
      return this.file_readlines(0);
   }

   public PyObject __iternext__() {
      return this.file___iternext__();
   }

   final synchronized PyObject file___iternext__() {
      this.checkClosed();
      String next = this.file.readline(-1);
      return next.length() == 0 ? null : new PyString(next);
   }

   final PyObject file_next() {
      PyObject ret = this.file___iternext__();
      if (ret == null) {
         throw Py.StopIteration("");
      } else {
         return ret;
      }
   }

   public PyObject next() {
      return this.file_next();
   }

   final PyObject file_self() {
      this.checkClosed();
      return this;
   }

   public PyObject __enter__() {
      return this.file_self();
   }

   public PyObject __iter__() {
      return this.file_self();
   }

   public PyObject xreadlines() {
      return this.file_self();
   }

   final void file_write(PyObject obj) {
      this.file_write(this.asWritable(obj, (String)null));
   }

   final synchronized void file_write(String string) {
      this.checkClosed();
      this.softspace = false;
      this.file.write(string);
   }

   public void write(String string) {
      this.file_write(string);
   }

   final synchronized void file_writelines(PyObject lines) {
      this.checkClosed();
      PyObject iter = Py.iter(lines, "writelines() requires an iterable argument");
      PyObject item = null;

      while((item = iter.__iternext__()) != null) {
         this.checkClosed();
         this.softspace = false;
         this.file.write(this.asWritable(item, "writelines() argument must be a sequence of strings"));
      }

   }

   public void writelines(PyObject lines) {
      this.file_writelines(lines);
   }

   private String asWritable(PyObject obj, String message) {
      if (obj instanceof PyUnicode) {
         return ((PyUnicode)obj).encode(this.encoding, this.errors);
      } else if (obj instanceof PyString) {
         return ((PyString)obj).getString();
      } else if ((!(obj instanceof PyArray) || this.binary) && obj instanceof BufferProtocol) {
         PyBuffer buf = ((BufferProtocol)obj).getBuffer(284);
         Throwable var4 = null;

         String var5;
         try {
            var5 = buf.toString();
         } catch (Throwable var14) {
            var4 = var14;
            throw var14;
         } finally {
            if (buf != null) {
               if (var4 != null) {
                  try {
                     buf.close();
                  } catch (Throwable var13) {
                     var4.addSuppressed(var13);
                  }
               } else {
                  buf.close();
               }
            }

         }

         return var5;
      } else {
         if (message == null) {
            String fmt = "expected a string or%s buffer, not %.200s";
            message = String.format(fmt, this.binary ? "" : " character", obj.getType().fastGetName());
         }

         throw Py.TypeError(message);
      }
   }

   final synchronized long file_tell() {
      this.checkClosed();
      return this.file.tell();
   }

   public long tell() {
      return this.file_tell();
   }

   final synchronized void file_seek(long pos, int how) {
      this.checkClosed();
      this.file.seek(pos, how);
   }

   public void seek(long pos, int how) {
      this.file_seek(pos, how);
   }

   public void seek(long pos) {
      this.file_seek(pos, 0);
   }

   final synchronized void file_flush() {
      this.checkClosed();
      this.file.flush();
   }

   public void flush() {
      this.file_flush();
   }

   final synchronized void file_close() {
      if (this.closer != null) {
         this.closer.close();
         this.closer = null;
      } else {
         this.file.close();
      }

   }

   public void close() {
      this.file_close();
   }

   final void file___exit__(PyObject type, PyObject value, PyObject traceback) {
      this.close();
   }

   public void __exit__(PyObject type, PyObject value, PyObject traceback) {
      this.file___exit__(type, value, traceback);
   }

   final void file_truncate(PyObject position) {
      if (position == null) {
         this.file_truncate();
      } else {
         this.file_truncate(position.asLong());
      }
   }

   final synchronized void file_truncate(long position) {
      this.file.truncate(position);
   }

   public void truncate(long position) {
      this.file_truncate(position);
   }

   final synchronized void file_truncate() {
      this.file.truncate(this.file.tell());
   }

   public void truncate() {
      this.file_truncate();
   }

   public boolean isatty() {
      return this.file_isatty();
   }

   final boolean file_isatty() {
      return this.file.isatty();
   }

   public PyObject fileno() {
      return this.file_fileno();
   }

   final PyObject file_fileno() {
      return PyJavaType.wrapJavaObject(this.file.fileno());
   }

   final String file_toString() {
      String state = this.file.closed() ? "closed" : "open";
      String id = Py.idstr(this);
      String escapedName;
      if (this.name instanceof PyUnicode) {
         escapedName = "u'" + PyString.encode_UnicodeEscape(this.name.toString(), false) + "'";
      } else {
         escapedName = this.name.__repr__().getString();
      }

      return String.format("<%s file %s, mode '%s' at %s>", state, escapedName, this.mode, id);
   }

   public String toString() {
      return this.file_toString();
   }

   private void checkClosed() {
      this.file.checkClosed();
   }

   public boolean getClosed() {
      return this.file.closed();
   }

   public PyObject getNewlines() {
      return this.file.getNewlines();
   }

   public PyObject getSoftspace() {
      return this.softspace ? Py.One : Py.Zero;
   }

   public void setSoftspace(PyObject obj) {
      this.softspace = obj.__nonzero__();
   }

   public void delSoftspace() {
      throw Py.TypeError("can't delete numeric/char attribute");
   }

   public Object __tojava__(Class cls) {
      Object obj = null;
      if (InputStream.class.isAssignableFrom(cls)) {
         obj = this.file.asInputStream();
      } else if (OutputStream.class.isAssignableFrom(cls)) {
         obj = this.file.asOutputStream();
      }

      if (obj == null) {
         obj = super.__tojava__(cls);
      }

      return obj;
   }

   public void __del_builtin__() {
      if (this.closer != null) {
         this.closer.close();
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      return this.name == null ? 0 : visit.visit(this.name, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.name;
   }

   static {
      PyType.addBuilder(PyFile.class, new PyExposer());
      TYPE = PyType.fromClass(PyFile.class);
   }

   private static class Closer implements Callable {
      private final TextIOBase file;
      private PySystemState sys;

      public Closer(TextIOBase file, PySystemState sys) {
         this.file = file;
         this.sys = sys;
         sys.registerCloser(this);
      }

      public void close() {
         this.sys.unregisterCloser(this);
         this.file.close();
         this.sys = null;
      }

      public Void call() {
         this.file.close();
         this.sys = null;
         return null;
      }
   }

   private static class file___init___exposer extends PyBuiltinMethod {
      public file___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public file___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyFile)this.self).file___init__(var1, var2);
         return Py.None;
      }
   }

   private static class file_read_exposer extends PyBuiltinMethodNarrow {
      public file_read_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "read([size]) -> read at most size bytes, returned as a string.\n\nIf the size argument is negative or omitted, read until EOF is reached.\nNotice that when in non-blocking mode, less data than what was requested\nmay be returned, even if no size parameter was given.";
      }

      public file_read_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "read([size]) -> read at most size bytes, returned as a string.\n\nIf the size argument is negative or omitted, read until EOF is reached.\nNotice that when in non-blocking mode, less data than what was requested\nmay be returned, even if no size parameter was given.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_read_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFile)this.self).file_read(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_read(-1);
      }
   }

   private static class file_readinto_exposer extends PyBuiltinMethodNarrow {
      public file_readinto_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "readinto() -> Undocumented.  Don't use this; it may go away.";
      }

      public file_readinto_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "readinto() -> Undocumented.  Don't use this; it may go away.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_readinto_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyFile)this.self).file_readinto(var1));
      }
   }

   private static class file_readline_exposer extends PyBuiltinMethodNarrow {
      public file_readline_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "readline([size]) -> next line from the file, as a string.\n\nRetain newline.  A non-negative size argument limits the maximum\nnumber of bytes to return (an incomplete line may be returned then).\nReturn an empty string at EOF.";
      }

      public file_readline_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "readline([size]) -> next line from the file, as a string.\n\nRetain newline.  A non-negative size argument limits the maximum\nnumber of bytes to return (an incomplete line may be returned then).\nReturn an empty string at EOF.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_readline_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFile)this.self).file_readline(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_readline(-1);
      }
   }

   private static class file_readlines_exposer extends PyBuiltinMethodNarrow {
      public file_readlines_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "readlines([size]) -> list of strings, each a line from the file.\n\nCall readline() repeatedly and return a list of the lines so read.\nThe optional size argument, if given, is an approximate bound on the\ntotal number of bytes in the lines returned.";
      }

      public file_readlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "readlines([size]) -> list of strings, each a line from the file.\n\nCall readline() repeatedly and return a list of the lines so read.\nThe optional size argument, if given, is an approximate bound on the\ntotal number of bytes in the lines returned.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_readlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFile)this.self).file_readlines(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_readlines(0);
      }
   }

   private static class file_next_exposer extends PyBuiltinMethodNarrow {
      public file_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public file_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.next() -> the next value, or raise StopIteration";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_next();
      }
   }

   private static class file_self_exposer extends PyBuiltinMethodNarrow {
      public file_self_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public file_self_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_self_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_self();
      }
   }

   private static class file_write_exposer extends PyBuiltinMethodNarrow {
      public file_write_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "write(str) -> None.  Write string str to file.\n\nNote that due to buffering, flush() or close() may be needed before\nthe file on disk reflects the data written.";
      }

      public file_write_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "write(str) -> None.  Write string str to file.\n\nNote that due to buffering, flush() or close() may be needed before\nthe file on disk reflects the data written.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_write_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyFile)this.self).file_write(var1);
         return Py.None;
      }
   }

   private static class file_writelines_exposer extends PyBuiltinMethodNarrow {
      public file_writelines_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "writelines(sequence_of_strings) -> None.  Write the strings to the file.\n\nNote that newlines are not added.  The sequence can be any iterable object\nproducing strings. This is equivalent to calling write() for each string.";
      }

      public file_writelines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "writelines(sequence_of_strings) -> None.  Write the strings to the file.\n\nNote that newlines are not added.  The sequence can be any iterable object\nproducing strings. This is equivalent to calling write() for each string.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_writelines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyFile)this.self).file_writelines(var1);
         return Py.None;
      }
   }

   private static class file_tell_exposer extends PyBuiltinMethodNarrow {
      public file_tell_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "tell() -> current file position, an integer (may be a long integer).";
      }

      public file_tell_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "tell() -> current file position, an integer (may be a long integer).";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_tell_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newLong(((PyFile)this.self).file_tell());
      }
   }

   private static class file_seek_exposer extends PyBuiltinMethodNarrow {
      public file_seek_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "seek(offset[, whence]) -> None.  Move to new file position.\n\nArgument offset is a byte count.  Optional argument whence defaults to\n0 (offset from start of file, offset should be >= 0); other values are 1\n(move relative to current position, positive or negative), and 2 (move\nrelative to end of file, usually negative, although many platforms allow\nseeking beyond the end of a file).  If the file is opened in text mode,\nonly offsets returned by tell() are legal.  Use of other offsets causes\nundefined behavior.\nNote that not all file objects are seekable.";
      }

      public file_seek_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "seek(offset[, whence]) -> None.  Move to new file position.\n\nArgument offset is a byte count.  Optional argument whence defaults to\n0 (offset from start of file, offset should be >= 0); other values are 1\n(move relative to current position, positive or negative), and 2 (move\nrelative to end of file, usually negative, although many platforms allow\nseeking beyond the end of a file).  If the file is opened in text mode,\nonly offsets returned by tell() are legal.  Use of other offsets causes\nundefined behavior.\nNote that not all file objects are seekable.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_seek_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyFile)this.self).file_seek(Py.py2long(var1), Py.py2int(var2));
         return Py.None;
      }

      public PyObject __call__(PyObject var1) {
         ((PyFile)this.self).file_seek(Py.py2long(var1), 0);
         return Py.None;
      }
   }

   private static class file_flush_exposer extends PyBuiltinMethodNarrow {
      public file_flush_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "flush() -> None.  Flush the internal I/O buffer.";
      }

      public file_flush_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "flush() -> None.  Flush the internal I/O buffer.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_flush_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyFile)this.self).file_flush();
         return Py.None;
      }
   }

   private static class file_close_exposer extends PyBuiltinMethodNarrow {
      public file_close_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "close() -> None or (perhaps) an integer.  Close the file.\n\nSets data attribute .closed to True.  A closed file cannot be used for\nfurther I/O operations.  close() may be called more than once without\nerror.  Some kinds of file objects (for example, opened by popen())\nmay return an exit status upon closing.";
      }

      public file_close_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "close() -> None or (perhaps) an integer.  Close the file.\n\nSets data attribute .closed to True.  A closed file cannot be used for\nfurther I/O operations.  close() may be called more than once without\nerror.  Some kinds of file objects (for example, opened by popen())\nmay return an exit status upon closing.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_close_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyFile)this.self).file_close();
         return Py.None;
      }
   }

   private static class file___exit___exposer extends PyBuiltinMethodNarrow {
      public file___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "__exit__(*excinfo) -> None.  Closes the file.";
      }

      public file___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "__exit__(*excinfo) -> None.  Closes the file.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyFile)this.self).file___exit__(var1, var2, var3);
         return Py.None;
      }
   }

   private static class file_truncate_exposer extends PyBuiltinMethodNarrow {
      public file_truncate_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "truncate([size]) -> None.  Truncate the file to at most size bytes.\n\nSize defaults to the current file position, as returned by tell().";
      }

      public file_truncate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "truncate([size]) -> None.  Truncate the file to at most size bytes.\n\nSize defaults to the current file position, as returned by tell().";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_truncate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyFile)this.self).file_truncate(var1);
         return Py.None;
      }

      public PyObject __call__() {
         ((PyFile)this.self).file_truncate((PyObject)null);
         return Py.None;
      }
   }

   private static class file_isatty_exposer extends PyBuiltinMethodNarrow {
      public file_isatty_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "isatty() -> true or false.  True if the file is connected to a tty device.";
      }

      public file_isatty_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "isatty() -> true or false.  True if the file is connected to a tty device.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_isatty_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFile)this.self).file_isatty());
      }
   }

   private static class file_fileno_exposer extends PyBuiltinMethodNarrow {
      public file_fileno_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "fileno() -> integer \"file descriptor\".\n\nThis is needed for lower-level file interfaces, such os.read().";
      }

      public file_fileno_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "fileno() -> integer \"file descriptor\".\n\nThis is needed for lower-level file interfaces, such os.read().";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_fileno_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFile)this.self).file_fileno();
      }
   }

   private static class file_toString_exposer extends PyBuiltinMethodNarrow {
      public file_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public file_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new file_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyFile)this.self).file_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class mode_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public mode_descriptor() {
         super("mode", String.class, "file mode ('r', 'U', 'w', 'a', possibly with 'b' or '+' added)");
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyFile)var1).mode;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class newlines_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public newlines_descriptor() {
         super("newlines", PyObject.class, "end-of-line convention used in this file");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFile)var1).getNewlines();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public name_descriptor() {
         super("name", PyObject.class, "file name");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFile)var1).name;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class closed_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public closed_descriptor() {
         super("closed", Boolean.class, "True if the file is closed");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyFile)var1).getClosed());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class softspace_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public softspace_descriptor() {
         super("softspace", PyObject.class, "flag indicating that a space needs to be printed; used by print");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFile)var1).getSoftspace();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFile)var1).setSoftspace((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyFile)var1).delSoftspace();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class encoding_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public encoding_descriptor() {
         super("encoding", String.class, "file encoding");
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyFile)var1).encoding;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class errors_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public errors_descriptor() {
         super("errors", String.class, "Unicode error handler");
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((PyFile)var1).errors;
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyFile var4 = new PyFile(this.for_type);
         if (var1) {
            var4.file___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyFileDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new file___init___exposer("__init__"), new file_read_exposer("read"), new file_readinto_exposer("readinto"), new file_readline_exposer("readline"), new file_readlines_exposer("readlines"), new file_next_exposer("next"), new file_self_exposer("__enter__"), new file_self_exposer("__iter__"), new file_self_exposer("xreadlines"), new file_write_exposer("write"), new file_writelines_exposer("writelines"), new file_tell_exposer("tell"), new file_seek_exposer("seek"), new file_flush_exposer("flush"), new file_close_exposer("close"), new file___exit___exposer("__exit__"), new file_truncate_exposer("truncate"), new file_isatty_exposer("isatty"), new file_fileno_exposer("fileno"), new file_toString_exposer("__str__"), new file_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new mode_descriptor(), new newlines_descriptor(), new name_descriptor(), new closed_descriptor(), new softspace_descriptor(), new encoding_descriptor(), new errors_descriptor()};
         super("file", PyFile.class, Object.class, (boolean)1, "file(name[, mode[, buffering]]) -> file object\n\nOpen a file.  The mode can be 'r', 'w' or 'a' for reading (default),\nwriting or appending.  The file will be created if it doesn't exist\nwhen opened for writing or appending; it will be truncated when\nopened for writing.  Add a 'b' to the mode for binary files.\nAdd a '+' to the mode to allow simultaneous reading and writing.\nIf the buffering argument is given, 0 means unbuffered, 1 means line\nbuffered, and larger numbers specify the buffer size.  The preferred way\nto open a file is with the builtin open() function.\nAdd a 'U' to mode to open the file for input with universal newline\nsupport.  Any line ending in the input file will be seen as a '\\n'\nin Python.  Also, a file so opened gains the attribute 'newlines';\nthe value for this attribute is one of None (no newline read yet),\n'\\r', '\\n', '\\r\\n' or a tuple containing all the newline types seen.\n\n'U' cannot be combined with 'w' or '+' mode.\n", var1, var2, new exposed___new__());
      }
   }
}
