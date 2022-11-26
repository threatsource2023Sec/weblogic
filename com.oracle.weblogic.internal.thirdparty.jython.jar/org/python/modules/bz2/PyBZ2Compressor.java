package org.python.modules.bz2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Untraversable;
import org.python.core.util.StringUtil;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@Untraversable
@ExposedType(
   name = "bz2.BZ2Compressor"
)
public class PyBZ2Compressor extends PyObject {
   private CaptureStream captureStream = null;
   private BZip2CompressorOutputStream compressStream = null;
   public static final PyType TYPE;

   public PyBZ2Compressor() {
      super(TYPE);
   }

   public PyBZ2Compressor(PyType subType) {
      super(subType);
   }

   @ExposedNew
   final void BZ2Compressor___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("bz2compressor", args, kwds, new String[]{"compresslevel"}, 0);
      int compresslevel = ap.getInt(0, 9);

      try {
         this.captureStream = new CaptureStream();
         this.compressStream = new BZip2CompressorOutputStream(this.captureStream, compresslevel);
      } catch (IOException var6) {
         throw Py.IOError(var6.getMessage());
      }
   }

   public PyString BZ2Compressor_compress(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("compress", args, kwds, new String[]{"data"}, 1);
      PyString data = (PyString)ap.getPyObject(0);
      PyString returnData = null;

      try {
         this.compressStream.write(data.toBytes());
         returnData = this.readData();
         return returnData;
      } catch (IOException var7) {
         throw Py.IOError(var7.getMessage());
      }
   }

   private PyString readData() {
      if (!this.captureStream.hasData()) {
         return Py.EmptyString;
      } else {
         byte[] buf = this.captureStream.readData();
         this.captureStream.resetByteArray();
         return new PyString(StringUtil.fromBytes(buf));
      }
   }

   public PyString BZ2Compressor_flush(PyObject[] args, String[] kwds) {
      PyString finalData = Py.EmptyString;

      try {
         this.compressStream.finish();
         this.compressStream.close();
         finalData = this.readData();
         this.captureStream.close();
         return finalData;
      } catch (IOException var5) {
         throw Py.IOError(var5.getMessage());
      }
   }

   static {
      PyType.addBuilder(PyBZ2Compressor.class, new PyExposer());
      TYPE = PyType.fromClass(PyBZ2Compressor.class);
   }

   private class CaptureStream extends OutputStream {
      private final ByteArrayOutputStream capturedData;

      private CaptureStream() {
         this.capturedData = new ByteArrayOutputStream();
      }

      public void write(int byteData) throws IOException {
         this.capturedData.write(byteData);
      }

      public byte[] readData() {
         return this.capturedData.toByteArray();
      }

      public void resetByteArray() {
         this.capturedData.reset();
      }

      public boolean hasData() {
         return this.capturedData.size() > 0;
      }

      // $FF: synthetic method
      CaptureStream(Object x1) {
         this();
      }
   }

   private static class BZ2Compressor_compress_exposer extends PyBuiltinMethod {
      public BZ2Compressor_compress_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2Compressor_compress_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2Compressor_compress_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2Compressor)this.self).BZ2Compressor_compress(var1, var2);
      }
   }

   private static class BZ2Compressor_flush_exposer extends PyBuiltinMethod {
      public BZ2Compressor_flush_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2Compressor_flush_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2Compressor_flush_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2Compressor)this.self).BZ2Compressor_flush(var1, var2);
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyBZ2Compressor var4 = new PyBZ2Compressor(this.for_type);
         if (var1) {
            var4.BZ2Compressor___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyBZ2CompressorDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BZ2Compressor_compress_exposer("compress"), new BZ2Compressor_flush_exposer("flush")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("bz2.BZ2Compressor", PyBZ2Compressor.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
