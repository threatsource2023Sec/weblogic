package org.python.modules._io;

import java.util.Iterator;
import org.python.core.BufferProtocol;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuffer;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyByteArray;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.buffer.SimpleStringBuffer;
import org.python.core.finalization.FinalizableBuiltin;
import org.python.core.finalization.FinalizeTrigger;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_io._IOBase",
   doc = "The abstract base class for all I/O classes, acting on streams of\nbytes. There is no public constructor.\n\nThis class provides dummy implementations for many methods that\nderived classes can override selectively; the default implementations\nrepresent a file that cannot be read, written or seeked.\n\nEven though IOBase does not declare read, readinto, or write because\ntheir signatures will vary, implementations and clients should\nconsider those methods part of the interface. Also, implementations\nmay raise a IOError when operations they do not support are called.\n\nThe basic type used for binary data read from or written to a file is\nbytes. bytearrays are accepted too, and in some cases (such as\nreadinto) needed. Text I/O classes work with str data.\n\nNote that calling any method (even inquiries) on a closed stream is\nundefined. Implementations may raise IOError in this case.\n\nIOBase (and its subclasses) support the iterator protocol, meaning\nthat an IOBase object can be iterated over yielding the lines in a\nstream.\n\nIOBase also supports the :keyword:`with` statement. In this example,\nfp is closed after the suite of the with statement is complete:\n\nwith open('spam.txt', 'r') as fp:\n    fp.write('Spam and eggs!')\n"
)
public class PyIOBase extends PyObject implements FinalizableBuiltin, Traverseproc {
   public static final PyType TYPE;
   private Closer closer;
   protected PyStringMap __dict__;
   protected boolean __closed;
   public static final String seek_doc = "Change stream position.\n\nChange the stream position to byte offset offset. offset is\ninterpreted relative to the position indicated by whence.  Values\nfor whence are:\n\n* 0 -- start of stream (the default); offset should be zero or positive\n* 1 -- current stream position; offset may be negative\n* 2 -- end of stream; offset is usually negative\n\nReturn the new absolute position.";
   public static final String tell_doc = "Return current stream position.";
   public static final String truncate_doc = "Truncate file to size bytes.\n\nFile pointer is left unchanged.  Size defaults to the current IO\nposition as reported by tell().  Returns the new size.";
   public static final String flush_doc = "Flush write buffers, if applicable.\n\nThis is not implemented for read-only and non-blocking streams.";
   public static final String close_doc = "Flush and close the IO object.\n\nThis method has no effect if the file is already closed.";
   public static final String closed_doc = "True if the stream is closed.\n";
   public static final String seekable_doc = "Return whether object supports random access.\n\nIf False, seek(), tell() and truncate() will raise IOError.\nThis method may need to do a test seek().";
   public static final String readable_doc = "Return whether object was opened for reading.\n\nIf False, read() will raise IOError.";
   public static final String writable_doc = "Return whether object was opened for writing.\n\nIf False, read() will raise IOError.";
   public static final String fileno_doc = "Returns underlying file descriptor if one exists.\n\nAn IOError is raised if the IO object does not use a file descriptor.\n";
   public static final String isatty_doc = "Return whether this is an 'interactive' stream.\n\nReturn False if it can't be determined.\n";
   public static final String readline_doc = "Read and return a line from the stream.\n\nIf limit is specified, at most limit bytes will be read.\n\nThe line terminator is always b'\n' for binary files; for text\nfiles, the newlines argument to open can be used to select the line\nterminator(s) recognized.\n";
   public static final String readlines_doc = "Return a list of lines from the stream.\n\nhint can be specified to control the number of lines read: no more\nlines will be read if the total size (in bytes/characters) of all\nlines so far exceeds hint.";
   public static final String writelines_doc = "Write a list of lines to the stream. Line separators are not added,\nso it is usual for each of the lines provided to have a line separator\nat the end.";
   static final String doc = "The abstract base class for all I/O classes, acting on streams of\nbytes. There is no public constructor.\n\nThis class provides dummy implementations for many methods that\nderived classes can override selectively; the default implementations\nrepresent a file that cannot be read, written or seeked.\n\nEven though IOBase does not declare read, readinto, or write because\ntheir signatures will vary, implementations and clients should\nconsider those methods part of the interface. Also, implementations\nmay raise a IOError when operations they do not support are called.\n\nThe basic type used for binary data read from or written to a file is\nbytes. bytearrays are accepted too, and in some cases (such as\nreadinto) needed. Text I/O classes work with str data.\n\nNote that calling any method (even inquiries) on a closed stream is\nundefined. Implementations may raise IOError in this case.\n\nIOBase (and its subclasses) support the iterator protocol, meaning\nthat an IOBase object can be iterated over yielding the lines in a\nstream.\n\nIOBase also supports the :keyword:`with` statement. In this example,\nfp is closed after the suite of the with statement is complete:\n\nwith open('spam.txt', 'r') as fp:\n    fp.write('Spam and eggs!')\n";

   protected PyIOBase() {
      this(TYPE);
      FinalizeTrigger.ensureFinalizer(this);
   }

   protected PyIOBase(PyType subtype) {
      super(subtype);
      this.__dict__ = new PyStringMap();
      this.closer = new Closer(this, Py.getSystemState());
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyStringMap fastGetDict() {
      return this.__dict__;
   }

   @ExposedNew
   static PyObject _IOBase___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      return (PyObject)(new_.for_type == subtype ? new PyIOBase() : new PyIOBaseDerived(subtype));
   }

   protected PyException unsupported(String op) {
      String fmt = "%s.%s() not supported";
      String msg = String.format(fmt, this.getType().fastGetName(), op);
      return _jyio.UnsupportedOperation(msg);
   }

   final void _IOBase__unsupported(String name) {
      throw this.unsupported(name);
   }

   public long seek(long pos, int whence) {
      return this._IOBase_seek(pos, whence);
   }

   public final long seek(long pos) {
      return this.seek(pos, 0);
   }

   final long _IOBase_seek(long pos, int whence) {
      throw this.unsupported("seek");
   }

   public long tell() {
      return this._IOBase_tell();
   }

   final long _IOBase_tell() {
      return this.seek(0L, 1);
   }

   public long truncate(long size) {
      return this._IOBase_truncate((PyObject)null);
   }

   public long truncate() {
      return this._IOBase_truncate((PyObject)null);
   }

   final long _IOBase_truncate(PyObject size) {
      throw this.unsupported("truncate");
   }

   public void flush() {
      this._IOBase_flush();
   }

   final void _IOBase_flush() {
      this._checkClosed();
   }

   public final void closed_readonly(boolean value) {
      this.readonlyAttributeError("closed");
   }

   public void close() {
      this._IOBase_close();
   }

   final void _IOBase_close() {
      if (!this.__closed) {
         try {
            this.closer.dismiss();
            this.invoke("flush");
         } finally {
            this.__closed = true;
         }
      }

   }

   public boolean seekable() throws PyException {
      return this._IOBase_seekable();
   }

   final boolean _IOBase_seekable() throws PyException {
      return false;
   }

   public void _checkSeekable(String msg) {
      this._IOBase__checkSeekable(msg);
   }

   public final void _checkSeekable() {
      this._checkSeekable((String)null);
   }

   final void _IOBase__checkSeekable(String msg) {
      if (!this.invoke("seekable").__nonzero__()) {
         throw tailoredIOError(msg, "seek");
      }
   }

   public boolean readable() throws PyException {
      return this._IOBase_readable();
   }

   final boolean _IOBase_readable() throws PyException {
      return false;
   }

   public void _checkReadable(String msg) {
      this._IOBase__checkReadable(msg);
   }

   public final void _checkReadable() {
      this._checkReadable((String)null);
   }

   final void _IOBase__checkReadable(String msg) {
      if (!this.invoke("readable").__nonzero__()) {
         throw tailoredIOError(msg, "read");
      }
   }

   public boolean writable() throws PyException {
      return this._IOBase_writable();
   }

   final boolean _IOBase_writable() throws PyException {
      return false;
   }

   public void _checkWritable(String msg) throws PyException {
      this._IOBase__checkWritable(msg);
   }

   public final void _checkWritable() throws PyException {
      this._checkWritable((String)null);
   }

   final void _IOBase__checkWritable(String msg) throws PyException {
      if (!this.invoke("writable").__nonzero__()) {
         throw tailoredIOError(msg, "writ");
      }
   }

   public final boolean closed() {
      return this.__closed;
   }

   public void _checkClosed(String msg) throws PyException {
      this._IOBase__checkClosed(msg);
   }

   public final void _checkClosed() throws PyException {
      this._checkClosed((String)null);
   }

   final void _IOBase__checkClosed(String msg) throws PyException {
      if (this.closed()) {
         throw Py.ValueError(msg != null ? msg : "I/O operation on closed file");
      }
   }

   public PyObject __enter__() {
      return this._IOBase___enter__();
   }

   final PyObject _IOBase___enter__() {
      this._checkClosed();
      return this;
   }

   public boolean __exit__(PyObject type, PyObject value, PyObject traceback) {
      return this._IOBase___exit__(type, value, traceback);
   }

   final boolean _IOBase___exit__(PyObject type, PyObject value, PyObject traceback) {
      this.invoke("close");
      return false;
   }

   public PyObject fileno() {
      return this._IOBase_fileno();
   }

   final PyObject _IOBase_fileno() {
      throw this.unsupported("fileno");
   }

   public boolean isatty() {
      return this._IOBase_isatty();
   }

   final boolean _IOBase_isatty() {
      this._checkClosed();
      return false;
   }

   public PyObject readline(int limit) {
      return this._readline(limit);
   }

   public PyObject readline() {
      return this._readline(-1);
   }

   final PyObject _IOBase_readline(PyObject limit) {
      if (limit != null && limit != Py.None) {
         if (limit.isIndex()) {
            return this._readline(limit.asInt());
         } else {
            throw tailoredTypeError("integer limit", limit);
         }
      } else {
         return this._readline(-1);
      }
   }

   private PyObject _readline(int limit) {
      PyObject peekMethod = this.__findattr__("peek");
      PyObject readMethod = this.__getattr__("read");
      int remainingLimit = limit >= 0 ? limit : Integer.MAX_VALUE;
      if (peekMethod == null) {
         PyByteArray res = new PyByteArray();

         while(true) {
            --remainingLimit;
            if (remainingLimit < 0) {
               return res.__str__();
            }

            PyObject curr = readMethod.__call__((PyObject)Py.One);
            if (curr.__nonzero__()) {
               if (!(curr instanceof PyString)) {
                  String fmt = "read() should have returned a bytes object, not '%.200s'";
                  throw Py.IOError(String.format(fmt, curr.getType().fastGetName()));
               }

               char c = ((PyString)curr).getString().charAt(0);
               if (c == '\n') {
                  remainingLimit = 0;
               }

               res.append((byte)c);
            } else {
               remainingLimit = 0;
            }
         }
      } else {
         PyList list = null;
         PyObject curr = Py.EmptyString;

         while(true) {
            while(remainingLimit > 0) {
               if (((PyObject)curr).__nonzero__()) {
                  if (list == null) {
                     list = new PyList();
                  }

                  list.add(curr);
               }

               PyObject peekResult = peekMethod.__call__((PyObject)Py.One);
               if (peekResult.__nonzero__()) {
                  PyBuffer peekBuffer = readablePyBuffer(peekResult);

                  try {
                     int p = 0;
                     int nr = peekBuffer.getLen();
                     if (nr > remainingLimit) {
                        nr = remainingLimit;
                     }

                     while(p < nr) {
                        if (peekBuffer.byteAt(p++) == 10) {
                           remainingLimit = p;
                           break;
                        }
                     }

                     curr = readMethod.__call__((PyObject)Py.newInteger(p));
                     remainingLimit -= p;
                  } finally {
                     peekBuffer.release();
                  }
               } else {
                  curr = Py.EmptyString;
                  remainingLimit = 0;
               }
            }

            if (list == null) {
               return (PyObject)curr;
            }

            if (((PyObject)curr).__nonzero__()) {
               list.add(curr);
            }

            return Py.EmptyString.join(list);
         }
      }
   }

   public PyObject __iter__() {
      this._checkClosed();
      return this;
   }

   public PyObject __iternext__() {
      PyObject line = this.invoke("readline");
      return !line.__nonzero__() ? null : line;
   }

   public PyObject next() throws PyException {
      return this._IOBase_next();
   }

   final PyObject _IOBase_next() throws PyException {
      PyObject line = this.invoke("readline");
      if (!line.__nonzero__()) {
         throw Py.StopIteration("");
      } else {
         return line;
      }
   }

   public PyObject readlines(PyObject hint) {
      return this._IOBase_readlines(hint);
   }

   final PyObject _IOBase_readlines(PyObject hint) {
      int h = false;
      if (hint != null && hint != Py.None) {
         if (!hint.isIndex()) {
            throw tailoredTypeError("integer or None", hint);
         } else {
            int h;
            if ((h = hint.asIndex()) <= 0) {
               return new PyList(this);
            } else {
               int n = 0;
               PyList lines = new PyList();
               Iterator var5 = this.asIterable().iterator();

               while(var5.hasNext()) {
                  PyObject line = (PyObject)var5.next();
                  lines.append(line);
                  n += line.__len__();
                  if (n >= h) {
                     break;
                  }
               }

               return lines;
            }
         }
      } else {
         return new PyList(this);
      }
   }

   public void writelines(PyObject lines) {
      this._IOBase_writelines(lines);
   }

   final void _IOBase_writelines(PyObject lines) {
      this._checkClosed();
      PyObject writeMethod = this.__getattr__("write");
      Iterator var3 = lines.asIterable().iterator();

      while(var3.hasNext()) {
         PyObject line = (PyObject)var3.next();
         writeMethod.__call__(line);
      }

   }

   public void __del_builtin__() {
      this.closer.dismiss();
      this.invoke("close");
   }

   private static PyException tailoredIOError(String msg, String oper) {
      return msg == null ? Py.IOError("File or stream is not " + oper + "able.") : Py.IOError(msg);
   }

   protected static PyBuffer readablePyBuffer(PyObject obj) throws PyException {
      if (obj instanceof BufferProtocol) {
         try {
            return ((BufferProtocol)obj).getBuffer(0);
         } catch (PyException var2) {
            if (var2.match(Py.BufferError)) {
               throw Py.TypeError(String.format("(BufferError) %s", var2.getMessage()));
            } else {
               throw var2;
            }
         }
      } else {
         String s;
         if (obj instanceof PyUnicode) {
            s = ((PyUnicode)obj).encode();
         } else {
            if (!(obj instanceof PyArray)) {
               throw tailoredTypeError("read-write buffer", obj);
            }

            s = ((PyArray)obj).tostring();
         }

         return new SimpleStringBuffer(0, (BufferProtocol)null, s);
      }
   }

   protected static PyBuffer writablePyBuffer(PyObject obj) throws PyException {
      if (obj instanceof BufferProtocol) {
         try {
            return ((BufferProtocol)obj).getBuffer(1);
         } catch (PyException var2) {
            if (var2.match(Py.BufferError)) {
               throw Py.TypeError(String.format("(BufferError) %s", var2.getMessage()));
            } else {
               throw var2;
            }
         }
      } else {
         throw tailoredTypeError("read-write buffer", obj);
      }
   }

   protected static PyException tailoredTypeError(String type, PyObject arg) {
      return Py.TypeError(String.format("%s argument expected, got %.100s.", type, arg.getType().fastGetName()));
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.closer.sys != null) {
         int retVal = visit.visit(this.closer.sys, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return visit.visit(this.__dict__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.closer.sys || ob == this.__dict__);
   }

   static {
      PyType.addBuilder(PyIOBase.class, new PyExposer());
      TYPE = PyType.fromClass(PyIOBase.class);
   }

   private static class _IOBase__unsupported_exposer extends PyBuiltinMethodNarrow {
      public _IOBase__unsupported_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Internal: raise an exception for unsupported operations.";
      }

      public _IOBase__unsupported_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Internal: raise an exception for unsupported operations.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase__unsupported_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase__unsupported(var1.asString());
         return Py.None;
      }
   }

   private static class _IOBase_seek_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_seek_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "Change stream position.\n\nChange the stream position to byte offset offset. offset is\ninterpreted relative to the position indicated by whence.  Values\nfor whence are:\n\n* 0 -- start of stream (the default); offset should be zero or positive\n* 1 -- current stream position; offset may be negative\n* 2 -- end of stream; offset is usually negative\n\nReturn the new absolute position.";
      }

      public _IOBase_seek_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Change stream position.\n\nChange the stream position to byte offset offset. offset is\ninterpreted relative to the position indicated by whence.  Values\nfor whence are:\n\n* 0 -- start of stream (the default); offset should be zero or positive\n* 1 -- current stream position; offset may be negative\n* 2 -- end of stream; offset is usually negative\n\nReturn the new absolute position.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_seek_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newLong(((PyIOBase)this.self)._IOBase_seek(Py.py2long(var1), Py.py2int(var2)));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newLong(((PyIOBase)this.self)._IOBase_seek(Py.py2long(var1), 0));
      }
   }

   private static class _IOBase_tell_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_tell_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return current stream position.";
      }

      public _IOBase_tell_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return current stream position.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_tell_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newLong(((PyIOBase)this.self)._IOBase_tell());
      }
   }

   private static class _IOBase_truncate_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_truncate_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "Truncate file to size bytes.\n\nFile pointer is left unchanged.  Size defaults to the current IO\nposition as reported by tell().  Returns the new size.";
      }

      public _IOBase_truncate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Truncate file to size bytes.\n\nFile pointer is left unchanged.  Size defaults to the current IO\nposition as reported by tell().  Returns the new size.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_truncate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newLong(((PyIOBase)this.self)._IOBase_truncate(var1));
      }

      public PyObject __call__() {
         return Py.newLong(((PyIOBase)this.self)._IOBase_truncate((PyObject)null));
      }
   }

   private static class _IOBase_flush_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_flush_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Flush write buffers, if applicable.\n\nThis is not implemented for read-only and non-blocking streams.";
      }

      public _IOBase_flush_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Flush write buffers, if applicable.\n\nThis is not implemented for read-only and non-blocking streams.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_flush_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase_flush();
         return Py.None;
      }
   }

   private static class _IOBase_close_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_close_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Flush and close the IO object.\n\nThis method has no effect if the file is already closed.";
      }

      public _IOBase_close_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Flush and close the IO object.\n\nThis method has no effect if the file is already closed.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_close_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase_close();
         return Py.None;
      }
   }

   private static class _IOBase_seekable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_seekable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object supports random access.\n\nIf False, seek(), tell() and truncate() will raise IOError.\nThis method may need to do a test seek().";
      }

      public _IOBase_seekable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object supports random access.\n\nIf False, seek(), tell() and truncate() will raise IOError.\nThis method may need to do a test seek().";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_seekable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyIOBase)this.self)._IOBase_seekable());
      }
   }

   private static class _IOBase__checkSeekable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase__checkSeekable_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public _IOBase__checkSeekable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase__checkSeekable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase__checkSeekable(var1.asStringOrNull());
         return Py.None;
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase__checkSeekable((String)null);
         return Py.None;
      }
   }

   private static class _IOBase_readable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_readable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object was opened for reading.\n\nIf False, read() will raise IOError.";
      }

      public _IOBase_readable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object was opened for reading.\n\nIf False, read() will raise IOError.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_readable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyIOBase)this.self)._IOBase_readable());
      }
   }

   private static class _IOBase__checkReadable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase__checkReadable_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public _IOBase__checkReadable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase__checkReadable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase__checkReadable(var1.asStringOrNull());
         return Py.None;
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase__checkReadable((String)null);
         return Py.None;
      }
   }

   private static class _IOBase_writable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_writable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object was opened for writing.\n\nIf False, read() will raise IOError.";
      }

      public _IOBase_writable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object was opened for writing.\n\nIf False, read() will raise IOError.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_writable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyIOBase)this.self)._IOBase_writable());
      }
   }

   private static class _IOBase__checkWritable_exposer extends PyBuiltinMethodNarrow {
      public _IOBase__checkWritable_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public _IOBase__checkWritable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase__checkWritable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase__checkWritable(var1.asStringOrNull());
         return Py.None;
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase__checkWritable((String)null);
         return Py.None;
      }
   }

   private static class _IOBase__checkClosed_exposer extends PyBuiltinMethodNarrow {
      public _IOBase__checkClosed_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public _IOBase__checkClosed_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase__checkClosed_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase__checkClosed(var1.asStringOrNull());
         return Py.None;
      }

      public PyObject __call__() {
         ((PyIOBase)this.self)._IOBase__checkClosed((String)null);
         return Py.None;
      }
   }

   private static class _IOBase___enter___exposer extends PyBuiltinMethodNarrow {
      public _IOBase___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public _IOBase___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyIOBase)this.self)._IOBase___enter__();
      }
   }

   private static class _IOBase___exit___exposer extends PyBuiltinMethodNarrow {
      public _IOBase___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public _IOBase___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyIOBase)this.self)._IOBase___exit__(var1, var2, var3));
      }
   }

   private static class _IOBase_fileno_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_fileno_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns underlying file descriptor if one exists.\n\nAn IOError is raised if the IO object does not use a file descriptor.\n";
      }

      public _IOBase_fileno_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns underlying file descriptor if one exists.\n\nAn IOError is raised if the IO object does not use a file descriptor.\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_fileno_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyIOBase)this.self)._IOBase_fileno();
      }
   }

   private static class _IOBase_isatty_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_isatty_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether this is an 'interactive' stream.\n\nReturn False if it can't be determined.\n";
      }

      public _IOBase_isatty_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether this is an 'interactive' stream.\n\nReturn False if it can't be determined.\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_isatty_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyIOBase)this.self)._IOBase_isatty());
      }
   }

   private static class _IOBase_readline_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_readline_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "Read and return a line from the stream.\n\nIf limit is specified, at most limit bytes will be read.\n\nThe line terminator is always b'\n' for binary files; for text\nfiles, the newlines argument to open can be used to select the line\nterminator(s) recognized.\n";
      }

      public _IOBase_readline_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Read and return a line from the stream.\n\nIf limit is specified, at most limit bytes will be read.\n\nThe line terminator is always b'\n' for binary files; for text\nfiles, the newlines argument to open can be used to select the line\nterminator(s) recognized.\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_readline_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyIOBase)this.self)._IOBase_readline(var1);
      }

      public PyObject __call__() {
         return ((PyIOBase)this.self)._IOBase_readline((PyObject)null);
      }
   }

   private static class _IOBase_next_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_next_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__next__() <==> next(x)";
      }

      public _IOBase_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__next__() <==> next(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyIOBase)this.self)._IOBase_next();
      }
   }

   private static class _IOBase_readlines_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_readlines_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "Return a list of lines from the stream.\n\nhint can be specified to control the number of lines read: no more\nlines will be read if the total size (in bytes/characters) of all\nlines so far exceeds hint.";
      }

      public _IOBase_readlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return a list of lines from the stream.\n\nhint can be specified to control the number of lines read: no more\nlines will be read if the total size (in bytes/characters) of all\nlines so far exceeds hint.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_readlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyIOBase)this.self)._IOBase_readlines(var1);
      }

      public PyObject __call__() {
         return ((PyIOBase)this.self)._IOBase_readlines((PyObject)null);
      }
   }

   private static class _IOBase_writelines_exposer extends PyBuiltinMethodNarrow {
      public _IOBase_writelines_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Write a list of lines to the stream. Line separators are not added,\nso it is usual for each of the lines provided to have a line separator\nat the end.";
      }

      public _IOBase_writelines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Write a list of lines to the stream. Line separators are not added,\nso it is usual for each of the lines provided to have a line separator\nat the end.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new _IOBase_writelines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyIOBase)this.self)._IOBase_writelines(var1);
         return Py.None;
      }
   }

   private static class closed_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public closed_descriptor() {
         super("closed", Boolean.class, "True if the stream is closed.\n");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyIOBase)var1).__closed);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyIOBase)var1).closed_readonly((Boolean)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyStringMap.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyIOBase)var1).__dict__;
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

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyIOBase._IOBase___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new _IOBase__unsupported_exposer("_unsupported"), new _IOBase_seek_exposer("seek"), new _IOBase_tell_exposer("tell"), new _IOBase_truncate_exposer("truncate"), new _IOBase_flush_exposer("flush"), new _IOBase_close_exposer("close"), new _IOBase_seekable_exposer("seekable"), new _IOBase__checkSeekable_exposer("_checkSeekable"), new _IOBase_readable_exposer("readable"), new _IOBase__checkReadable_exposer("_checkReadable"), new _IOBase_writable_exposer("writable"), new _IOBase__checkWritable_exposer("_checkWritable"), new _IOBase__checkClosed_exposer("_checkClosed"), new _IOBase___enter___exposer("__enter__"), new _IOBase___enter___exposer("__iter__"), new _IOBase___exit___exposer("__exit__"), new _IOBase_fileno_exposer("fileno"), new _IOBase_isatty_exposer("isatty"), new _IOBase_readline_exposer("readline"), new _IOBase_next_exposer("next"), new _IOBase_readlines_exposer("readlines"), new _IOBase_writelines_exposer("writelines")};
         PyDataDescr[] var2 = new PyDataDescr[]{new closed_descriptor(), new __dict___descriptor()};
         super("_io._IOBase", PyIOBase.class, Object.class, (boolean)1, "The abstract base class for all I/O classes, acting on streams of\nbytes. There is no public constructor.\n\nThis class provides dummy implementations for many methods that\nderived classes can override selectively; the default implementations\nrepresent a file that cannot be read, written or seeked.\n\nEven though IOBase does not declare read, readinto, or write because\ntheir signatures will vary, implementations and clients should\nconsider those methods part of the interface. Also, implementations\nmay raise a IOError when operations they do not support are called.\n\nThe basic type used for binary data read from or written to a file is\nbytes. bytearrays are accepted too, and in some cases (such as\nreadinto) needed. Text I/O classes work with str data.\n\nNote that calling any method (even inquiries) on a closed stream is\nundefined. Implementations may raise IOError in this case.\n\nIOBase (and its subclasses) support the iterator protocol, meaning\nthat an IOBase object can be iterated over yielding the lines in a\nstream.\n\nIOBase also supports the :keyword:`with` statement. In this example,\nfp is closed after the suite of the with statement is complete:\n\nwith open('spam.txt', 'r') as fp:\n    fp.write('Spam and eggs!')\n", var1, var2, new exposed___new__());
      }
   }
}
