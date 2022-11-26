package org.python.modules.bz2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyByteArray;
import org.python.core.PyDataDescr;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "bz2.BZ2Decompressor"
)
public class PyBZ2Decompressor extends PyObject implements Traverseproc {
   public PyString unused_data;
   private boolean eofReached;
   private BZip2CompressorInputStream decompressStream;
   private byte[] accumulator;
   public static final PyType TYPE;

   public PyBZ2Decompressor() {
      super(TYPE);
      this.unused_data = Py.EmptyString;
      this.eofReached = false;
      this.decompressStream = null;
      this.accumulator = new byte[0];
   }

   public PyBZ2Decompressor(PyType objtype) {
      super(objtype);
      this.unused_data = Py.EmptyString;
      this.eofReached = false;
      this.decompressStream = null;
      this.accumulator = new byte[0];
   }

   @ExposedNew
   final void BZ2Decompressor___init__(PyObject[] args, String[] kwds) {
      new ArgParser("bz2decompressor", args, kwds, new String[0], 0);
   }

   final PyString BZ2Decompressor_decompress(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("compress", args, kwds, new String[]{"data"}, 1);
      PyString data = (PyString)ap.getPyObject(0);
      PyString returnData = Py.EmptyString;
      if (this.eofReached) {
         throw Py.EOFError("Data stream EOF reached");
      } else {
         byte[] indata = data.toBytes();
         if (indata.length > 0) {
            ByteBuffer bytebuf = ByteBuffer.allocate(this.accumulator.length + indata.length);
            bytebuf.put(this.accumulator);
            bytebuf.put(indata);
            this.accumulator = bytebuf.array();
         }

         ByteArrayOutputStream decodedStream = new ByteArrayOutputStream();
         byte[] buf = this.accumulator;

         for(int i = 0; i < buf.length; ++i) {
            if (i + 3 < buf.length && (char)buf[i] == '\\' && (char)buf[i + 1] == 'x') {
               int decodedByte = (Character.digit((char)buf[i + 2], 16) << 4) + Character.digit((char)buf[i + 3], 16);
               decodedStream.write(decodedByte);
               i += 3;
            } else {
               decodedStream.write(buf[i]);
            }
         }

         ByteArrayInputStream compressedData = new ByteArrayInputStream(decodedStream.toByteArray());

         try {
            this.decompressStream = new BZip2CompressorInputStream(compressedData);
         } catch (IOException var13) {
            return Py.EmptyString;
         }

         PyByteArray databuf = new PyByteArray();
         int currentByte = true;

         try {
            int currentByte;
            while((currentByte = this.decompressStream.read()) != -1) {
               databuf.append((byte)currentByte);
            }

            returnData = databuf.__str__();
            if (compressedData.available() > 0) {
               byte[] unusedbuf = new byte[compressedData.available()];
               compressedData.read(unusedbuf);
               this.unused_data = (PyString)this.unused_data.__add__((new PyByteArray(unusedbuf)).__str__());
            }

            this.eofReached = true;
            return returnData;
         } catch (IOException var14) {
            return Py.EmptyString;
         }
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.unused_data != null ? visit.visit(this.unused_data, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.unused_data == ob;
   }

   static {
      PyType.addBuilder(PyBZ2Decompressor.class, new PyExposer());
      TYPE = PyType.fromClass(PyBZ2Decompressor.class);
   }

   private static class BZ2Decompressor___init___exposer extends PyBuiltinMethod {
      public BZ2Decompressor___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2Decompressor___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2Decompressor___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyBZ2Decompressor)this.self).BZ2Decompressor___init__(var1, var2);
         return Py.None;
      }
   }

   private static class BZ2Decompressor_decompress_exposer extends PyBuiltinMethod {
      public BZ2Decompressor_decompress_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BZ2Decompressor_decompress_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BZ2Decompressor_decompress_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyBZ2Decompressor)this.self).BZ2Decompressor_decompress(var1, var2);
      }
   }

   private static class unused_data_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public unused_data_descriptor() {
         super("unused_data", PyString.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyBZ2Decompressor)var1).unused_data;
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
         PyBZ2Decompressor var4 = new PyBZ2Decompressor(this.for_type);
         if (var1) {
            var4.BZ2Decompressor___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyBZ2DecompressorDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BZ2Decompressor___init___exposer("__init__"), new BZ2Decompressor_decompress_exposer("decompress")};
         PyDataDescr[] var2 = new PyDataDescr[]{new unused_data_descriptor()};
         super("bz2.BZ2Decompressor", PyBZ2Decompressor.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
