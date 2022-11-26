package org.python.modules._io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import jnr.constants.Constant;
import jnr.constants.platform.Errno;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyBuffer;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyJavaType;
import org.python.core.PyLong;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.core.Untraversable;
import org.python.core.io.FileIO;
import org.python.core.io.RawIOBase;
import org.python.core.io.StreamIO;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "_io.FileIO",
   base = PyRawIOBase.class
)
public class PyFileIO extends PyRawIOBase {
   public static final PyType TYPE;
   private RawIOBase ioDelegate;
   private boolean readable;
   private boolean writable;
   private boolean seekableKnown;
   private boolean seekable;
   public final boolean closefd;
   public final PyString mode;
   private static final PyString defaultMode;
   private static final String[] openArgs;

   public final void mode_readonly(PyString value) {
      this.readonlyAttributeError("mode");
   }

   public PyFileIO(PyObject file, OpenMode mode, boolean closefd) {
      this(TYPE, file, mode, closefd);
   }

   public PyFileIO(PyType subtype, PyObject file, OpenMode mode, boolean closefd) {
      super(subtype);
      this.readable = mode.reading | mode.updating;
      this.writable = mode.writing | mode.updating | mode.appending;
      this.closefd = closefd;
      this.setDelegate(file, mode);
      if (this.readable) {
         this.mode = new PyString(this.writable ? "rb+" : "rb");
      } else {
         this.mode = new PyString("wb");
      }

   }

   private void setDelegate(PyObject file, OpenMode mode) {
      if (file instanceof PyString) {
         if (!this.closefd) {
            throw Py.ValueError("Cannot use closefd=False with file name");
         }

         this.ioDelegate = new FileIO((PyString)file, mode.forFileIO());
      } else {
         Object fd = file.__tojava__(Object.class);
         if (fd instanceof FileIO || fd instanceof StreamIO) {
            this.ioDelegate = (RawIOBase)fd;
         }
      }

      if (this.ioDelegate == null) {
         throw Py.TypeError(String.format("invalid file: %s", file.__repr__().asString()));
      } else if (this.ioDelegate.closed()) {
         throw Py.OSError((Constant)Errno.EBADF);
      } else if (this.readable && !this.ioDelegate.readable() || this.writable && !this.ioDelegate.writable()) {
         throw this.tailoredValueError(this.readable ? "read" : "writ");
      } else {
         this.fastGetDict().__setitem__("name", file);
      }
   }

   @ExposedNew
   static PyObject FileIO___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("FileIO", args, keywords, openArgs, 1);
      PyObject file = ap.getPyObject(0);
      PyObject m = ap.getPyObject(1, defaultMode);
      boolean closefd = Py.py2boolean(ap.getPyObject(2, Py.True));
      OpenMode mode = new OpenMode(m.asString()) {
         {
            this.invalid |= this.universal | this.text;
         }
      };
      mode.checkValid();
      return (PyObject)(subtype == TYPE ? new PyFileIO(subtype, file, mode, closefd) : new PyFileIODerived(subtype, file, mode, closefd));
   }

   public PyObject readinto(PyObject buf) {
      return this.FileIO_readinto(buf);
   }

   final PyLong FileIO_readinto(PyObject buf) {
      if (!this.readable) {
         throw this.tailoredValueError("read");
      } else {
         int count;
         if (buf instanceof PyArray) {
            PyArray a = (PyArray)buf;

            try {
               InputStream is = this.ioDelegate.asInputStream();
               count = a.fillFromStream(is);
               count *= a.getItemsize();
            } catch (IOException var13) {
               throw Py.IOError(var13);
            }
         } else {
            PyBuffer pybuf = writablePyBuffer(buf);

            try {
               ByteBuffer byteBuffer = pybuf.getNIOByteBuffer();
               synchronized(this.ioDelegate) {
                  count = this.ioDelegate.readinto(byteBuffer);
               }
            } finally {
               pybuf.release();
            }
         }

         return new PyLong((long)count);
      }
   }

   public PyObject write(PyObject buf) {
      return this.FileIO_write(buf);
   }

   final PyLong FileIO_write(PyObject buf) {
      if (!this.writable) {
         throw this.tailoredValueError("writ");
      } else {
         int count;
         if (buf instanceof PyArray) {
            try {
               OutputStream os = this.ioDelegate.asOutputStream();
               count = ((PyArray)buf).toStream(os);
            } catch (IOException var13) {
               throw Py.IOError(var13);
            }
         } else {
            PyBuffer pybuf = readablePyBuffer(buf);

            try {
               ByteBuffer byteBuffer = pybuf.getNIOByteBuffer();
               synchronized(this.ioDelegate) {
                  count = this.ioDelegate.write(byteBuffer);
               }
            } finally {
               pybuf.release();
            }
         }

         return new PyLong((long)count);
      }
   }

   public long seek(long pos, int whence) {
      return this.FileIO_seek(pos, whence);
   }

   final long FileIO_seek(long pos, int whence) {
      if (this.__closed) {
         throw this.closedValueError();
      } else {
         synchronized(this.ioDelegate) {
            return this.ioDelegate.seek(pos, whence);
         }
      }
   }

   public long truncate() {
      return this._truncate();
   }

   public long truncate(long size) {
      return this._truncate(size);
   }

   final long FileIO_truncate(PyObject size) {
      return size != null ? this._truncate(size.asLong()) : this._truncate();
   }

   private final long _truncate() {
      if (!this.writable) {
         throw this.tailoredValueError("writ");
      } else {
         synchronized(this.ioDelegate) {
            return this.ioDelegate.truncate(this.ioDelegate.tell());
         }
      }
   }

   private final long _truncate(long size) {
      if (!this.writable) {
         throw this.tailoredValueError("writ");
      } else {
         synchronized(this.ioDelegate) {
            return this.ioDelegate.truncate(size);
         }
      }
   }

   public void close() {
      this.FileIO_close();
   }

   final synchronized void FileIO_close() {
      super.close();
      if (this.closefd) {
         this.ioDelegate.close();
      }

      this.readable = false;
      this.writable = false;
   }

   public boolean seekable() {
      return this.FileIO_seekable();
   }

   final boolean FileIO_seekable() {
      if (this.__closed) {
         throw this.closedValueError();
      } else {
         if (!this.seekableKnown) {
            try {
               this.ioDelegate.seek(0L, 1);
               this.seekable = true;
            } catch (PyException var2) {
               if (!var2.match(Py.IOError)) {
                  throw var2;
               }

               this.seekable = false;
            }

            this.seekableKnown = true;
         }

         return this.seekable;
      }
   }

   public boolean readable() throws PyException {
      return this.FileIO_readable();
   }

   final boolean FileIO_readable() {
      if (this.__closed) {
         throw this.closedValueError();
      } else {
         return this.readable;
      }
   }

   public boolean writable() throws PyException {
      return this.FileIO_writable();
   }

   final boolean FileIO_writable() {
      if (this.__closed) {
         throw this.closedValueError();
      } else {
         return this.writable;
      }
   }

   public PyObject fileno() {
      return this.FileIO_fileno();
   }

   final PyObject FileIO_fileno() {
      return PyJavaType.wrapJavaObject(this.ioDelegate.fileno());
   }

   public boolean isatty() {
      return this.FileIO_isatty();
   }

   final boolean FileIO_isatty() {
      if (this.__closed) {
         throw this.closedValueError();
      } else {
         return this.ioDelegate.isatty();
      }
   }

   public void flush() {
      this.FileIO_flush();
   }

   final void FileIO_flush() {
      if (this.writable()) {
         this.ioDelegate.checkClosed();
         this.ioDelegate.flush();
      }

   }

   final String FileIO_toString() {
      if (this.closed()) {
         return "<_io.FileIO [closed]>";
      } else {
         PyObject name = this.fastGetDict().__finditem__("name");
         if (name != null && name instanceof PyString) {
            String xname = name.asString();
            if (name instanceof PyUnicode) {
               xname = PyString.encode_UnicodeEscape(xname, false);
            }

            return String.format("<_io.FileIO name='%s' mode='%s'>", xname, this.mode);
         } else {
            return String.format("<_io.FileIO fd=%s mode='%s'>", this.fileno(), this.mode);
         }
      }
   }

   public String toString() {
      return this.FileIO_toString().toString();
   }

   private PyException closedValueError() {
      return Py.ValueError("I/O operation on closed file");
   }

   private PyException tailoredValueError(String action) {
      return action != null && !this.__closed ? Py.ValueError("File not open for " + action + "ing") : this.closedValueError();
   }

   static {
      PyType.addBuilder(PyFileIO.class, new PyExposer());
      TYPE = PyType.fromClass(PyFileIO.class);
      defaultMode = new PyString("r");
      openArgs = new String[]{"file", "mode", "closefd"};
   }

   private static class FileIO_readinto_exposer extends PyBuiltinMethodNarrow {
      public FileIO_readinto_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Read up to len(b) bytes into bytearray b and return the number of bytes read.\nIf the object is in non-blocking mode and no bytes are available,\nNone is returned.";
      }

      public FileIO_readinto_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Read up to len(b) bytes into bytearray b and return the number of bytes read.\nIf the object is in non-blocking mode and no bytes are available,\nNone is returned.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_readinto_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFileIO)this.self).FileIO_readinto(var1);
      }
   }

   private static class FileIO_write_exposer extends PyBuiltinMethodNarrow {
      public FileIO_write_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Write the given bytes or bytearray object, b, to the underlying raw\nstream and return the number of bytes written.";
      }

      public FileIO_write_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Write the given bytes or bytearray object, b, to the underlying raw\nstream and return the number of bytes written.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_write_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFileIO)this.self).FileIO_write(var1);
      }
   }

   private static class FileIO_seek_exposer extends PyBuiltinMethodNarrow {
      public FileIO_seek_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "Change stream position.\n\nChange the stream position to byte offset offset. offset is\ninterpreted relative to the position indicated by whence.  Values\nfor whence are:\n\n* 0 -- start of stream (the default); offset should be zero or positive\n* 1 -- current stream position; offset may be negative\n* 2 -- end of stream; offset is usually negative\n\nReturn the new absolute position.";
      }

      public FileIO_seek_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Change stream position.\n\nChange the stream position to byte offset offset. offset is\ninterpreted relative to the position indicated by whence.  Values\nfor whence are:\n\n* 0 -- start of stream (the default); offset should be zero or positive\n* 1 -- current stream position; offset may be negative\n* 2 -- end of stream; offset is usually negative\n\nReturn the new absolute position.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_seek_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newLong(((PyFileIO)this.self).FileIO_seek(Py.py2long(var1), Py.py2int(var2)));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newLong(((PyFileIO)this.self).FileIO_seek(Py.py2long(var1), 0));
      }
   }

   private static class FileIO_truncate_exposer extends PyBuiltinMethodNarrow {
      public FileIO_truncate_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "Truncate file to size bytes.\n\nFile pointer is left unchanged.  Size defaults to the current IO\nposition as reported by tell().  Returns the new size.";
      }

      public FileIO_truncate_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Truncate file to size bytes.\n\nFile pointer is left unchanged.  Size defaults to the current IO\nposition as reported by tell().  Returns the new size.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_truncate_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newLong(((PyFileIO)this.self).FileIO_truncate(var1));
      }

      public PyObject __call__() {
         return Py.newLong(((PyFileIO)this.self).FileIO_truncate((PyObject)null));
      }
   }

   private static class FileIO_close_exposer extends PyBuiltinMethodNarrow {
      public FileIO_close_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public FileIO_close_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_close_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyFileIO)this.self).FileIO_close();
         return Py.None;
      }
   }

   private static class FileIO_seekable_exposer extends PyBuiltinMethodNarrow {
      public FileIO_seekable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object supports random access.\n\nIf False, seek(), tell() and truncate() will raise IOError.\nThis method may need to do a test seek().";
      }

      public FileIO_seekable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object supports random access.\n\nIf False, seek(), tell() and truncate() will raise IOError.\nThis method may need to do a test seek().";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_seekable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFileIO)this.self).FileIO_seekable());
      }
   }

   private static class FileIO_readable_exposer extends PyBuiltinMethodNarrow {
      public FileIO_readable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object was opened for reading.\n\nIf False, read() will raise IOError.";
      }

      public FileIO_readable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object was opened for reading.\n\nIf False, read() will raise IOError.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_readable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFileIO)this.self).FileIO_readable());
      }
   }

   private static class FileIO_writable_exposer extends PyBuiltinMethodNarrow {
      public FileIO_writable_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether object was opened for writing.\n\nIf False, read() will raise IOError.";
      }

      public FileIO_writable_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether object was opened for writing.\n\nIf False, read() will raise IOError.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_writable_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFileIO)this.self).FileIO_writable());
      }
   }

   private static class FileIO_fileno_exposer extends PyBuiltinMethodNarrow {
      public FileIO_fileno_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Returns underlying file descriptor if one exists.\n\nAn IOError is raised if the IO object does not use a file descriptor.\n";
      }

      public FileIO_fileno_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Returns underlying file descriptor if one exists.\n\nAn IOError is raised if the IO object does not use a file descriptor.\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_fileno_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFileIO)this.self).FileIO_fileno();
      }
   }

   private static class FileIO_isatty_exposer extends PyBuiltinMethodNarrow {
      public FileIO_isatty_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return whether this is an 'interactive' stream.\n\nReturn False if it can't be determined.\n";
      }

      public FileIO_isatty_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return whether this is an 'interactive' stream.\n\nReturn False if it can't be determined.\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_isatty_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyFileIO)this.self).FileIO_isatty());
      }
   }

   private static class FileIO_flush_exposer extends PyBuiltinMethodNarrow {
      public FileIO_flush_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Flush write buffers.";
      }

      public FileIO_flush_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Flush write buffers.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_flush_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyFileIO)this.self).FileIO_flush();
         return Py.None;
      }
   }

   private static class FileIO_toString_exposer extends PyBuiltinMethodNarrow {
      public FileIO_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public FileIO_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new FileIO_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyFileIO)this.self).FileIO_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class closefd_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public closefd_descriptor() {
         super("closefd", Boolean.class, "True if the file descriptor will be closed");
      }

      public Object invokeGet(PyObject var1) {
         return Py.newBoolean(((PyFileIO)var1).closefd);
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

   private static class mode_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public mode_descriptor() {
         super("mode", PyString.class, "String giving the file mode: 'rb', 'rb+', or 'wb'");
      }

      public Object invokeGet(PyObject var1) {
         return ((PyFileIO)var1).mode;
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyFileIO)var1).mode_readonly((PyString)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyFileIO.FileIO___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new FileIO_readinto_exposer("readinto"), new FileIO_write_exposer("write"), new FileIO_seek_exposer("seek"), new FileIO_truncate_exposer("truncate"), new FileIO_close_exposer("close"), new FileIO_seekable_exposer("seekable"), new FileIO_readable_exposer("readable"), new FileIO_writable_exposer("writable"), new FileIO_fileno_exposer("fileno"), new FileIO_isatty_exposer("isatty"), new FileIO_flush_exposer("flush"), new FileIO_toString_exposer("__str__"), new FileIO_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new closefd_descriptor(), new mode_descriptor()};
         super("_io.FileIO", PyFileIO.class, PyRawIOBase.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
