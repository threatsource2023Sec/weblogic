package org.python.modules.bz2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyException;
import org.python.core.PyIterator;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyNone;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PySequence;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.core.finalization.FinalizableBuiltin;
import org.python.core.finalization.FinalizablePyObject;
import org.python.core.finalization.FinalizeTrigger;
import org.python.core.io.BinaryIOWrapper;
import org.python.core.io.BufferedReader;
import org.python.core.io.BufferedWriter;
import org.python.core.io.StreamIO;
import org.python.core.io.TextIOBase;
import org.python.core.io.UniversalIOWrapper;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "bz2.BZ2File"
)
public class PyBZ2File extends PyObject implements FinalizablePyObject, FinalizableBuiltin {
   public static final PyType TYPE;
   private int buffering;
   private TextIOBase buffer;
   private String fileName = null;
   private boolean inIterMode = false;
   private boolean inUniversalNewlineMode = false;
   private boolean needReadBufferInit = false;
   private boolean inReadMode = false;
   private boolean inWriteMode = false;

   public PyObject PyBZ2File_newlines() {
      return this.buffer != null ? this.buffer.getNewlines() : Py.None;
   }

   public PyBZ2File() {
      super(TYPE);
      FinalizeTrigger.ensureFinalizer(this);
   }

   public PyBZ2File(PyType subType) {
      super(subType);
      FinalizeTrigger.ensureFinalizer(this);
   }

   public void __del_builtin__() {
      this.BZ2File_close();
   }

   @ExposedNew
   final void BZ2File___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("bz2file", args, kwds, new String[]{"filename", "mode", "buffering", "compresslevel"}, 1);
      PyObject filename = ap.getPyObject(0);
      if (!(filename instanceof PyString)) {
         throw Py.TypeError("coercing to Unicode: need string, '" + filename.getType().fastGetName() + "' type found");
      } else {
         String mode = ap.getString(1, "r");
         int buffering = ap.getInt(2, 0);
         int compresslevel = ap.getInt(3, 9);
         this.BZ2File___init__((PyString)filename, mode, buffering, compresslevel);
      }
   }

   private void BZ2File___init__(PyString inFileName, String mode, int buffering, int compresslevel) {
      try {
         this.fileName = inFileName.asString();
         this.buffering = buffering;
         if (mode.contains("U")) {
            this.inUniversalNewlineMode = true;
         }

         File f;
         if (mode.contains("w")) {
            this.inWriteMode = true;
            f = new File(this.fileName);
            if (!f.exists()) {
               f.createNewFile();
            }

            BZip2CompressorOutputStream writeStream = new BZip2CompressorOutputStream(new FileOutputStream(this.fileName), compresslevel);
            this.buffer = new BinaryIOWrapper(new BufferedWriter(new SkippableStreamIO(writeStream, true), buffering));
         } else {
            f = new File(this.fileName);
            if (!f.exists()) {
               throw new FileNotFoundException();
            }

            this.inReadMode = true;
            this.needReadBufferInit = true;
         }

      } catch (IOException var7) {
         throw Py.IOError("File " + this.fileName + " not found,");
      }
   }

   private void makeReadBuffer() {
      try {
         FileInputStream fin = new FileInputStream(this.fileName);
         BufferedInputStream bin = new BufferedInputStream(fin);
         BZip2CompressorInputStream bZin = new BZip2CompressorInputStream(bin, true);
         BufferedReader bufferedReader = new BufferedReader(new SkippableStreamIO(bZin, true), this.buffering);
         if (this.inUniversalNewlineMode) {
            this.buffer = new UniversalIOWrapper(bufferedReader);
         } else {
            this.buffer = new BinaryIOWrapper(bufferedReader);
         }

      } catch (FileNotFoundException var5) {
         throw Py.IOError((IOException)var5);
      } catch (IOException var6) {
         throw Py.EOFError(var6.getMessage());
      }
   }

   public void __del__() {
      this.BZ2File_close();
   }

   public void BZ2File_close() {
      this.needReadBufferInit = false;
      this.BZ2File_flush();
      if (this.buffer != null) {
         this.buffer.close();
      }

   }

   private void BZ2File_flush() {
      if (this.buffer != null) {
         this.buffer.flush();
      }

   }

   public PyObject BZ2File_read(PyObject[] args, String[] kwds) {
      this.checkInIterMode();
      this.checkReadBufferInit();
      ArgParser ap = new ArgParser("read", args, kwds, new String[]{"size"}, 0);
      int size = ap.getInt(0, -1);
      if (size == 0) {
         return Py.EmptyString;
      } else if (size < 0) {
         return new PyString(this.buffer.readall());
      } else {
         StringBuilder data = new StringBuilder(size);

         while(data.length() < size) {
            String chunk = this.buffer.read(size - data.length());
            if (chunk.length() == 0) {
               break;
            }

            data.append(chunk);
         }

         return new PyString(data.toString());
      }
   }

   public PyObject BZ2File_next(PyObject[] args, String[] kwds) {
      this.checkReadBufferInit();
      if (this.buffer != null && !this.buffer.closed()) {
         this.inIterMode = true;
         return null;
      } else {
         throw Py.ValueError("Cannot call next() on closed file");
      }
   }

   public PyString BZ2File_readline(PyObject[] args, String[] kwds) {
      this.checkInIterMode();
      this.checkReadBufferInit();
      ArgParser ap = new ArgParser("read", args, kwds, new String[]{"size"}, 0);
      int size = ap.getInt(0, -1);
      return new PyString(this.buffer.readline(size));
   }

   public PyList BZ2File_readlines(PyObject[] args, String[] kwds) {
      this.checkInIterMode();
      this.checkReadBufferInit();
      if (this.buffer != null && !this.buffer.closed()) {
         PyList lineList = new PyList();
         PyString line = null;

         while(!(line = this.BZ2File_readline(args, kwds)).equals(Py.EmptyString)) {
            lineList.add(line);
         }

         return lineList;
      } else {
         throw Py.ValueError("Cannot call readlines() on a closed file");
      }
   }

   private void checkInIterMode() {
      if (this.inReadMode && this.inIterMode) {
         throw Py.ValueError("Cannot mix iteration and reads");
      }
   }

   private void checkReadBufferInit() {
      if (this.inReadMode && this.needReadBufferInit) {
         this.makeReadBuffer();
         this.needReadBufferInit = false;
      }

   }

   public PyList BZ2File_xreadlines() {
      return this.BZ2File_readlines(new PyObject[0], new String[0]);
   }

   public void BZ2File_seek(PyObject[] args, String[] kwds) {
      if (!this.inReadMode) {
         Py.IOError("seek works only while reading");
      }

      ArgParser ap = new ArgParser("seek", args, kwds, new String[]{"offset", "whence"}, 1);
      this.checkReadBufferInit();
      int newOffset = ap.getInt(0);
      int whence = ap.getInt(1, 0);
      long currentPos = this.buffer.tell();
      long finalOffset = 0L;
      switch (whence) {
         case 0:
            finalOffset = (long)newOffset;
            break;
         case 1:
            finalOffset = currentPos + (long)newOffset;
            break;
         case 2:
            long fileSize = currentPos;

            while(true) {
               String data = this.buffer.read(8192);
               if (data.isEmpty()) {
                  finalOffset = fileSize + (long)newOffset;
                  this.buffer.close();
                  this.makeReadBuffer();
                  break;
               }

               fileSize += (long)data.length();
            }
      }

      if (finalOffset < 0L) {
         finalOffset = 0L;
      }

      if (whence != 2 && finalOffset < currentPos) {
         this.buffer.close();
         this.makeReadBuffer();
      }

      this.buffer.seek(finalOffset, 0);
   }

   public PyLong BZ2File_tell() {
      this.checkReadBufferInit();
      return this.buffer == null ? Py.newLong(0) : Py.newLong(this.buffer.tell());
   }

   public void BZ2File_write(PyObject[] args, String[] kwds) {
      this.checkFileWritable();
      ArgParser ap = new ArgParser("write", args, kwds, new String[]{"data"}, 0);
      PyObject data = ap.getPyObject(0);
      if (data.getType() == PyNone.TYPE) {
         throw Py.TypeError("Expecting str argument");
      } else {
         this.buffer.write(ap.getString(0));
      }
   }

   public void BZ2File_writelines(PyObject[] args, String[] kwds) {
      this.checkFileWritable();
      ArgParser ap = new ArgParser("writelines", args, kwds, new String[]{"sequence_of_strings"}, 0);
      PySequence seq = (PySequence)ap.getPyObject(0);
      Iterator iterator = seq.asIterable().iterator();

      while(iterator.hasNext()) {
         PyObject line = (PyObject)iterator.next();
         this.BZ2File_write(new PyObject[]{line}, new String[]{"data"});
      }

   }

   private void checkFileWritable() {
      if (this.inReadMode) {
         throw Py.IOError("File in read-only mode");
      } else if (this.buffer == null || this.buffer.closed()) {
         throw Py.ValueError("Stream closed");
      }
   }

   public PyObject __iter__() {
      return new BZ2FileIterator();
   }

   public PyObject BZ2File___enter__() {
      if (this.inWriteMode) {
         if (this.buffer == null) {
            throw Py.ValueError("Stream closed");
         }
      } else if (this.inReadMode && !this.needReadBufferInit && (this.buffer == null || this.buffer.closed())) {
         throw Py.ValueError("Stream closed");
      }

      return this;
   }

   public boolean BZ2File___exit__(PyObject exc_type, PyObject exc_value, PyObject traceback) {
      this.BZ2File_close();
      return false;
   }

   static {
      PyType.addBuilder(PyBZ2File.class, new PyExposer());
      TYPE = PyType.fromClass(PyBZ2File.class);
   }

   private static class SkippableStreamIO extends StreamIO {
      private long position = 0L;

      public SkippableStreamIO(InputStream inputStream, boolean closefd) {
         super(inputStream, closefd);
      }

      public SkippableStreamIO(OutputStream outputStream, boolean closefd) {
         super(outputStream, closefd);
      }

      public int readinto(ByteBuffer buf) {
         int bytesRead = false;

         int bytesRead;
         try {
            bytesRead = super.readinto(buf);
         } catch (PyException var4) {
            throw Py.EOFError(var4.value.asStringOrNull());
         }

         this.position += (long)bytesRead;
         return bytesRead;
      }

      public long tell() {
         return this.position;
      }

      public long seek(long offset, int whence) {
         long skipBytes = offset - this.position;
         if (whence == 0 && skipBytes >= 0L) {
            if (skipBytes == 0L) {
               return this.position;
            } else {
               long skipped = 0L;

               try {
                  skipped = this.asInputStream().skip(skipBytes);
               } catch (IOException var11) {
                  throw Py.IOError(var11);
               }

               long newPosition = this.position + skipped;
               this.position = newPosition;
               return newPosition;
            }
         } else {
            throw Py.IOError("can only seek forward");
         }
      }
   }

   private class BZ2FileIterator extends PyIterator {
      private BZ2FileIterator() {
      }

      public PyObject __iternext__() {
         PyString s = PyBZ2File.this.BZ2File_readline(new PyObject[0], new String[0]);
         return s.equals(Py.EmptyString) ? null : s;
      }

      // $FF: synthetic method
      BZ2FileIterator(Object x1) {
         this();
      }
   }

   private static class BZ2File___init___exposer extends PyBuiltinMethod {
      public BZ2File___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBZ2File)this.self).BZ2File___init__(var1, var2);
         return Py.None;
      }
   }

   private static class __del___exposer extends PyBuiltinMethodNarrow {
      public __del___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __del___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __del___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyBZ2File)this.self).__del__();
         return Py.None;
      }
   }

   private static class BZ2File_close_exposer extends PyBuiltinMethodNarrow {
      public BZ2File_close_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BZ2File_close_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_close_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyBZ2File)this.self).BZ2File_close();
         return Py.None;
      }
   }

   private static class BZ2File_read_exposer extends PyBuiltinMethod {
      public BZ2File_read_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_read_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_read_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2File)this.self).BZ2File_read(var1, var2);
      }
   }

   private static class BZ2File_next_exposer extends PyBuiltinMethod {
      public BZ2File_next_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_next_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_next_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2File)this.self).BZ2File_next(var1, var2);
      }
   }

   private static class BZ2File_readline_exposer extends PyBuiltinMethod {
      public BZ2File_readline_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_readline_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_readline_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2File)this.self).BZ2File_readline(var1, var2);
      }
   }

   private static class BZ2File_readlines_exposer extends PyBuiltinMethod {
      public BZ2File_readlines_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_readlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_readlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2File)this.self).BZ2File_readlines(var1, var2);
      }
   }

   private static class BZ2File_xreadlines_exposer extends PyBuiltinMethodNarrow {
      public BZ2File_xreadlines_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BZ2File_xreadlines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_xreadlines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBZ2File)this.self).BZ2File_xreadlines();
      }
   }

   private static class BZ2File_seek_exposer extends PyBuiltinMethod {
      public BZ2File_seek_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_seek_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_seek_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBZ2File)this.self).BZ2File_seek(var1, var2);
         return Py.None;
      }
   }

   private static class BZ2File_tell_exposer extends PyBuiltinMethodNarrow {
      public BZ2File_tell_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BZ2File_tell_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_tell_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBZ2File)this.self).BZ2File_tell();
      }
   }

   private static class BZ2File_write_exposer extends PyBuiltinMethod {
      public BZ2File_write_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_write_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_write_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBZ2File)this.self).BZ2File_write(var1, var2);
         return Py.None;
      }
   }

   private static class BZ2File_writelines_exposer extends PyBuiltinMethod {
      public BZ2File_writelines_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2File_writelines_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File_writelines_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBZ2File)this.self).BZ2File_writelines(var1, var2);
         return Py.None;
      }
   }

   private static class __iter___exposer extends PyBuiltinMethodNarrow {
      public __iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBZ2File)this.self).__iter__();
      }
   }

   private static class BZ2File___enter___exposer extends PyBuiltinMethodNarrow {
      public BZ2File___enter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public BZ2File___enter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File___enter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyBZ2File)this.self).BZ2File___enter__();
      }
   }

   private static class BZ2File___exit___exposer extends PyBuiltinMethodNarrow {
      public BZ2File___exit___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public BZ2File___exit___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2File___exit___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newBoolean(((PyBZ2File)this.self).BZ2File___exit__(var1, var2, var3));
      }
   }

   private static class newlines_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public newlines_descriptor() {
         super("newlines", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBZ2File)var1).PyBZ2File_newlines();
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
         PyBZ2File var4 = new PyBZ2File(this.for_type);
         if (var1) {
            var4.BZ2File___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyBZ2FileDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BZ2File___init___exposer("__init__"), new __del___exposer("__del__"), new BZ2File_close_exposer("close"), new BZ2File_read_exposer("read"), new BZ2File_next_exposer("next"), new BZ2File_readline_exposer("readline"), new BZ2File_readlines_exposer("readlines"), new BZ2File_xreadlines_exposer("xreadlines"), new BZ2File_seek_exposer("seek"), new BZ2File_tell_exposer("tell"), new BZ2File_write_exposer("write"), new BZ2File_writelines_exposer("writelines"), new __iter___exposer("__iter__"), new BZ2File___enter___exposer("__enter__"), new BZ2File___exit___exposer("__exit__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new newlines_descriptor()};
         super("bz2.BZ2File", PyBZ2File.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
