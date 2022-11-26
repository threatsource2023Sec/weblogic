package org.python.modules.bz2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.python.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

public class bz2 implements ClassDictInit {
   public static final PyString __doc__ = new PyString("bz2 module");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"BZ2File", PyBZ2File.TYPE);
      dict.__setitem__((String)"BZ2Compressor", PyBZ2Compressor.TYPE);
      dict.__setitem__((String)"BZ2Decompressor", PyBZ2Decompressor.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   public static PyString compress(PyString data) {
      return compress(data, 9);
   }

   public static PyString compress(PyString data, int compresslevel) {
      PyString returnData = null;

      try {
         ByteArrayOutputStream compressedArray = new ByteArrayOutputStream();
         BZip2CompressorOutputStream bzbuf = new BZip2CompressorOutputStream(compressedArray);
         bzbuf.write(data.toBytes());
         bzbuf.finish();
         bzbuf.close();
         returnData = new PyString(compressedArray.toString("iso-8859-1"));
         compressedArray.close();
         return returnData;
      } catch (IOException var5) {
         throw Py.IOError(var5.getMessage());
      }
   }

   public static PyString decompress(PyString data) {
      PyString returnString = null;
      if (data.toString().equals("")) {
         return Py.EmptyString;
      } else {
         try {
            ByteArrayInputStream inputArray = new ByteArrayInputStream(data.toBytes());
            BZip2CompressorInputStream bzbuf = new BZip2CompressorInputStream(inputArray);
            ByteArrayOutputStream outputArray = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int n = false;

            int n;
            while((n = bzbuf.read(buffer)) != -1) {
               outputArray.write(buffer, 0, n);
            }

            returnString = new PyString(outputArray.toString("iso-8859-1"));
            outputArray.close();
            bzbuf.close();
            inputArray.close();
            return returnString;
         } catch (IOException var7) {
            throw Py.ValueError(var7.getMessage());
         }
      }
   }
}
