import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("zlib.py")
public class zlib$py extends PyFunctionTable implements PyRunnable {
   static zlib$py self;
   static final PyCode f$0;
   static final PyCode error$1;
   static final PyCode adler32$2;
   static final PyCode crc32$3;
   static final PyCode compress$4;
   static final PyCode decompress$5;
   static final PyCode compressobj$6;
   static final PyCode __init__$7;
   static final PyCode compress$8;
   static final PyCode flush$9;
   static final PyCode decompressobj$10;
   static final PyCode __init__$11;
   static final PyCode decompress$12;
   static final PyCode flush$13;
   static final PyCode _to_input$14;
   static final PyCode _get_deflate_data$15;
   static final PyCode _get_inflate_data$16;
   static final PyCode _skip_gzip_header$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThe functions in this module allow compression and decompression using the\nzlib library, which is based on GNU zip.\n\nadler32(string[, start]) -- Compute an Adler-32 checksum.\ncompress(string[, level]) -- Compress string, with compression level in 1-9.\ncompressobj([level]) -- Return a compressor object.\ncrc32(string[, start]) -- Compute a CRC-32 checksum.\ndecompress(string,[wbits],[bufsize]) -- Decompresses a compressed string.\ndecompressobj([wbits]) -- Return a decompressor object.\n\n'wbits' is window buffer size.\nCompressor objects support compress() and flush() methods; decompressor\nobjects support decompress() and flush().\n"));
      var1.setline(15);
      PyString.fromInterned("\nThe functions in this module allow compression and decompression using the\nzlib library, which is based on GNU zip.\n\nadler32(string[, start]) -- Compute an Adler-32 checksum.\ncompress(string[, level]) -- Compress string, with compression level in 1-9.\ncompressobj([level]) -- Return a compressor object.\ncrc32(string[, start]) -- Compute a CRC-32 checksum.\ndecompress(string,[wbits],[bufsize]) -- Decompresses a compressed string.\ndecompressobj([wbits]) -- Return a decompressor object.\n\n'wbits' is window buffer size.\nCompressor objects support compress() and flush() methods; decompressor\nobjects support decompress() and flush().\n");
      var1.setline(16);
      PyObject var3 = imp.importOne("array", var1, -1);
      var1.setlocal("array", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var1.setline(18);
      var3 = imp.importOne("jarray", var1, -1);
      var1.setlocal("jarray", var3);
      var3 = null;
      var1.setline(19);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(20);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(21);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(23);
      var5 = new String[]{"Long", "String", "System"};
      var6 = imp.importFrom("java.lang", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Long", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("String", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("System", var4);
      var4 = null;
      var1.setline(24);
      var5 = new String[]{"Adler32", "CRC32", "Deflater", "Inflater", "DataFormatException"};
      var6 = imp.importFrom("java.util.zip", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Adler32", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CRC32", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("Deflater", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("Inflater", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("DataFormatException", var4);
      var4 = null;
      var1.setline(27);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("error", var6, error$1);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(31);
      PyInteger var7 = Py.newInteger(8);
      var1.setlocal("DEFLATED", var7);
      var3 = null;
      var1.setline(32);
      var7 = Py.newInteger(15);
      var1.setlocal("MAX_WBITS", var7);
      var3 = null;
      var1.setline(33);
      var7 = Py.newInteger(8);
      var1.setlocal("DEF_MEM_LEVEL", var7);
      var3 = null;
      var1.setline(34);
      PyString var8 = PyString.fromInterned("1.1.3");
      var1.setlocal("ZLIB_VERSION", var8);
      var3 = null;
      var1.setline(35);
      var7 = Py.newInteger(9);
      var1.setlocal("Z_BEST_COMPRESSION", var7);
      var3 = null;
      var1.setline(36);
      var7 = Py.newInteger(1);
      var1.setlocal("Z_BEST_SPEED", var7);
      var3 = null;
      var1.setline(38);
      var7 = Py.newInteger(1);
      var1.setlocal("Z_FILTERED", var7);
      var3 = null;
      var1.setline(39);
      var7 = Py.newInteger(2);
      var1.setlocal("Z_HUFFMAN_ONLY", var7);
      var3 = null;
      var1.setline(41);
      var7 = Py.newInteger(-1);
      var1.setlocal("Z_DEFAULT_COMPRESSION", var7);
      var3 = null;
      var1.setline(42);
      var7 = Py.newInteger(0);
      var1.setlocal("Z_DEFAULT_STRATEGY", var7);
      var3 = null;
      var1.setline(44);
      var7 = Py.newInteger(0);
      var1.setlocal("Z_NO_FLUSH", var7);
      var3 = null;
      var1.setline(45);
      var7 = Py.newInteger(2);
      var1.setlocal("Z_SYNC_FLUSH", var7);
      var3 = null;
      var1.setline(46);
      var7 = Py.newInteger(3);
      var1.setlocal("Z_FULL_FLUSH", var7);
      var3 = null;
      var1.setline(47);
      var7 = Py.newInteger(4);
      var1.setlocal("Z_FINISH", var7);
      var3 = null;
      var1.setline(48);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getname("Z_NO_FLUSH"), var1.getname("Z_SYNC_FLUSH"), var1.getname("Z_FULL_FLUSH"), var1.getname("Z_FINISH")});
      var1.setlocal("_valid_flush_modes", var9);
      var3 = null;
      var1.setline(50);
      PyDictionary var10 = new PyDictionary(new PyObject[]{var1.getname("Z_NO_FLUSH"), var1.getname("Deflater").__getattr__("NO_FLUSH"), var1.getname("Z_SYNC_FLUSH"), var1.getname("Deflater").__getattr__("SYNC_FLUSH"), var1.getname("Z_FULL_FLUSH"), var1.getname("Deflater").__getattr__("FULL_FLUSH")});
      var1.setlocal("_zlib_to_deflater", var10);
      var3 = null;
      var1.setline(57);
      var7 = Py.newInteger(65521);
      var1.setlocal("_ADLER_BASE", var7);
      var3 = null;
      var1.setline(59);
      var6 = new PyObject[]{Py.newInteger(1)};
      PyFunction var11 = new PyFunction(var1.f_globals, var6, adler32$2, (PyObject)null);
      var1.setlocal("adler32", var11);
      var3 = null;
      var1.setline(76);
      var6 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var6, crc32$3, (PyObject)null);
      var1.setlocal("crc32", var11);
      var3 = null;
      var1.setline(79);
      var6 = new PyObject[]{Py.newInteger(6)};
      var11 = new PyFunction(var1.f_globals, var6, compress$4, (PyObject)null);
      var1.setlocal("compress", var11);
      var3 = null;
      var1.setline(91);
      var6 = new PyObject[]{Py.newInteger(0), Py.newInteger(16384)};
      var11 = new PyFunction(var1.f_globals, var6, decompress$5, (PyObject)null);
      var1.setlocal("decompress", var11);
      var3 = null;
      var1.setline(113);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("compressobj", var6, compressobj$6);
      var1.setlocal("compressobj", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(174);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("decompressobj", var6, decompressobj$10);
      var1.setlocal("decompressobj", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(248);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, _to_input$14, (PyObject)null);
      var1.setlocal("_to_input", var11);
      var3 = null;
      var1.setline(258);
      var6 = new PyObject[]{var1.getname("Z_NO_FLUSH")};
      var11 = new PyFunction(var1.f_globals, var6, _get_deflate_data$15, (PyObject)null);
      var1.setlocal("_get_deflate_data", var11);
      var3 = null;
      var1.setline(270);
      var6 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var6, _get_inflate_data$16, (PyObject)null);
      var1.setlocal("_get_inflate_data", var11);
      var3 = null;
      var1.setline(295);
      var7 = Py.newInteger(1);
      var1.setlocal("FTEXT", var7);
      var3 = null;
      var1.setline(296);
      var7 = Py.newInteger(2);
      var1.setlocal("FHCRC", var7);
      var3 = null;
      var1.setline(297);
      var7 = Py.newInteger(4);
      var1.setlocal("FEXTRA", var7);
      var3 = null;
      var1.setline(298);
      var7 = Py.newInteger(8);
      var1.setlocal("FNAME", var7);
      var3 = null;
      var1.setline(299);
      var7 = Py.newInteger(16);
      var1.setlocal("FCOMMENT", var7);
      var3 = null;
      var1.setline(301);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, _skip_gzip_header$17, (PyObject)null);
      var1.setlocal("_skip_gzip_header", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      return var1.getf_locals();
   }

   public PyObject adler32$2(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(1)._and(Py.newInteger(65535));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(1)._rshift(Py.newInteger(16))._and(Py.newInteger(65535));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(67);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(72);
            var1.setline(72);
            Object var6 = var1.getlocal(3)._and(Py.newInteger(32768)).__nonzero__() ? Py.newLong("-2147483648") : Py.newInteger(0);
            var1.setlocal(5, (PyObject)var6);
            var3 = null;
            var1.setline(73);
            var3 = var1.getlocal(3)._and(Py.newInteger(32767));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(74);
            var3 = var1.getlocal(5)._add(var1.getlocal(6)._lshift(Py.newInteger(16)))._add(var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(68);
         PyObject var5 = var1.getlocal(2)._add(var1.getglobal("ord").__call__(var2, var1.getlocal(4)))._mod(var1.getglobal("_ADLER_BASE"));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(69);
         var5 = var1.getlocal(3)._add(var1.getlocal(2))._mod(var1.getglobal("_ADLER_BASE"));
         var1.setlocal(3, var5);
         var5 = null;
      }
   }

   public PyObject crc32$3(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getglobal("binascii").__getattr__("crc32").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compress$4(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(var1.getglobal("Z_BEST_SPEED"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(var1.getglobal("Z_BEST_COMPRESSION"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(81);
         throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("Bad compression level"));
      } else {
         var1.setline(82);
         var3 = var1.getglobal("Deflater").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
         var1.setlocal(2, var3);
         var3 = null;
         var3 = null;

         Throwable var8;
         label33: {
            boolean var10001;
            PyObject var4;
            try {
               var1.setline(84);
               var4 = var1.getglobal("_to_input").__call__(var2, var1.getlocal(0));
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(85);
               var1.getlocal(2).__getattr__("setInput").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)));
               var1.setline(86);
               var1.getlocal(2).__getattr__("finish").__call__(var2);
               var1.setline(87);
               var4 = var1.getglobal("_get_deflate_data").__call__(var2, var1.getlocal(2));
            } catch (Throwable var6) {
               var8 = var6;
               var10001 = false;
               break label33;
            }

            var1.setline(89);
            var1.getlocal(2).__getattr__("end").__call__(var2);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var8 = var5;
               var10001 = false;
            }
         }

         Throwable var7 = var8;
         Py.addTraceback(var7, var1);
         var1.setline(89);
         var1.getlocal(2).__getattr__("end").__call__(var2);
         throw (Throwable)var7;
      }
   }

   public PyObject decompress$5(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var10000 = var1.getglobal("Inflater");
      PyObject var3 = var1.getlocal(1);
      PyObject var10002 = var3._lt(Py.newInteger(0));
      var3 = null;
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      Throwable var8;
      label31: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(94);
            var1.getlocal(3).__getattr__("setInput").__call__(var2, var1.getglobal("_to_input").__call__(var2, var1.getlocal(0)));
            var1.setline(95);
            var4 = var1.getglobal("_get_inflate_data").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(96);
            if (var1.getlocal(3).__getattr__("finished").__call__(var2).__not__().__nonzero__()) {
               var1.setline(97);
               throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("Error -5 while decompressing data: incomplete or truncated stream"));
            }

            var1.setline(98);
            var4 = var1.getlocal(4);
         } catch (Throwable var6) {
            var8 = var6;
            var10001 = false;
            break label31;
         }

         var1.setline(100);
         var1.getlocal(3).__getattr__("end").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var8 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var8;
      Py.addTraceback(var7, var1);
      var1.setline(100);
      var1.getlocal(3).__getattr__("end").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject compressobj$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyString var3 = PyString.fromInterned("\u001f\u008b\b\u0000\u0000\u0000\u0000\u0000\u0004\u0003");
      var1.setlocal("GZIP_HEADER", var3);
      var3 = null;
      var1.setline(124);
      PyObject var4 = var1.getname("struct").__getattr__("Struct").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Ii"));
      var1.setlocal("GZIP_TRAILER_FORMAT", var4);
      var3 = null;
      var1.setline(126);
      PyObject[] var5 = new PyObject[]{Py.newInteger(6), var1.getname("DEFLATED"), var1.getname("MAX_WBITS"), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(144);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, compress$8, (PyObject)null);
      var1.setlocal("compress", var6);
      var3 = null;
      var1.setline(157);
      var5 = new PyObject[]{var1.getname("Z_FINISH")};
      var6 = new PyFunction(var1.f_globals, var5, flush$9, (PyObject)null);
      var1.setlocal("flush", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("abs").__call__(var2, var1.getlocal(3))._and(Py.newInteger(16)).__nonzero__()) {
         var1.setline(129);
         var3 = var1.getlocal(3);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(130);
            var3 = var1.getlocal(3);
            var3 = var3._isub(Py.newInteger(16));
            var1.setlocal(3, var3);
         } else {
            var1.setline(132);
            var3 = var1.getlocal(3);
            var3 = var3._iadd(Py.newInteger(16));
            var1.setlocal(3, var3);
         }

         var1.setline(133);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_gzip", var3);
         var3 = null;
      } else {
         var1.setline(135);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_gzip", var3);
         var3 = null;
      }

      var1.setline(136);
      var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(3));
      var10000 = var3._gt(var1.getglobal("MAX_WBITS"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(3));
         var10000 = var3._lt(Py.newInteger(8));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(137);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid initialization option: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)})));
      } else {
         var1.setline(138);
         var10000 = var1.getglobal("Deflater");
         PyObject var10002 = var1.getlocal(1);
         var3 = var1.getlocal(3);
         PyObject var10003 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10003.__nonzero__()) {
            var10003 = var1.getlocal(0).__getattr__("_gzip");
         }

         var3 = var10000.__call__(var2, var10002, var10003);
         var1.getlocal(0).__setattr__("deflater", var3);
         var3 = null;
         var1.setline(139);
         var1.getlocal(0).__getattr__("deflater").__getattr__("setStrategy").__call__(var2, var1.getlocal(5));
         var1.setline(140);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_ended", var3);
         var3 = null;
         var1.setline(141);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_size", var4);
         var3 = null;
         var1.setline(142);
         var3 = var1.getglobal("CRC32").__call__(var2);
         var1.getlocal(0).__setattr__("_crc32", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject compress$8(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      if (var1.getlocal(0).__getattr__("_ended").__nonzero__()) {
         var1.setline(146);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("compressobj may not be used after flush(Z_FINISH)")));
      } else {
         var1.setline(147);
         PyObject var3 = var1.getglobal("_to_input").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(148);
         var1.getlocal(0).__getattr__("deflater").__getattr__("setInput").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var1.setline(149);
         var3 = var1.getglobal("_get_deflate_data").__call__(var2, var1.getlocal(0).__getattr__("deflater"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(150);
         PyObject var10000 = var1.getlocal(0);
         String var6 = "_size";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var4.__setattr__(var6, var5);
         var1.setline(151);
         var1.getlocal(0).__getattr__("_crc32").__getattr__("update").__call__(var2, var1.getlocal(1));
         var1.setline(152);
         if (var1.getlocal(0).__getattr__("_gzip").__nonzero__()) {
            var1.setline(153);
            var3 = var1.getlocal(0).__getattr__("GZIP_HEADER")._add(var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(155);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject flush$9(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      if (var1.getlocal(0).__getattr__("_ended").__nonzero__()) {
         var1.setline(159);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("compressobj may not be used after flush(Z_FINISH)")));
      } else {
         var1.setline(160);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(var1.getglobal("_valid_flush_modes"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(161);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid flush option"));
         } else {
            var1.setline(162);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getglobal("Z_FINISH"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(163);
               var1.getlocal(0).__getattr__("deflater").__getattr__("finish").__call__(var2);
            }

            var1.setline(164);
            var3 = var1.getglobal("_get_deflate_data").__call__(var2, var1.getlocal(0).__getattr__("deflater"), var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(165);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getglobal("Z_FINISH"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(166);
               if (var1.getlocal(0).__getattr__("_gzip").__nonzero__()) {
                  var1.setline(167);
                  var3 = var1.getlocal(2);
                  var3 = var3._iadd(var1.getlocal(0).__getattr__("GZIP_TRAILER_FORMAT").__getattr__("pack").__call__(var2, var1.getlocal(0).__getattr__("_crc32").__getattr__("getValue").__call__(var2), var1.getlocal(0).__getattr__("_size")._mod(var1.getglobal("sys").__getattr__("maxint"))));
                  var1.setlocal(2, var3);
               }

               var1.setline(169);
               var1.getlocal(0).__getattr__("deflater").__getattr__("end").__call__(var2);
               var1.setline(170);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_ended", var3);
               var3 = null;
            }

            var1.setline(171);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject decompressobj$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(176);
      PyObject[] var3 = new PyObject[]{var1.getname("MAX_WBITS")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(194);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, decompress$12, (PyObject)null);
      var1.setlocal("decompress", var4);
      var3 = null;
      var1.setline(237);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, flush$13, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._lt(Py.newInteger(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(183);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid initialization option"));
      } else {
         var1.setline(184);
         var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(16));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(185);
            PyInteger var4 = Py.newInteger(-1);
            var1.setlocal(1, var4);
            var3 = null;
         }

         var1.setline(187);
         var10000 = var1.getglobal("Inflater");
         var3 = var1.getlocal(1);
         PyObject var10002 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000.__call__(var2, var10002);
         var1.getlocal(0).__setattr__("inflater", var3);
         var3 = null;
         var1.setline(188);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_ended", var3);
         var3 = null;
         var1.setline(189);
         PyString var5 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"unused_data", var5);
         var3 = null;
         var1.setline(190);
         var5 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"unconsumed_tail", var5);
         var3 = null;
         var1.setline(191);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("gzip", var3);
         var3 = null;
         var1.setline(192);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("gzip_header_skipped", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject decompress$12(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      if (var1.getlocal(0).__getattr__("_ended").__nonzero__()) {
         var1.setline(196);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("decompressobj may not be used after flush()")));
      } else {
         var1.setline(204);
         PyString var3 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"unconsumed_tail", var3);
         var3 = null;
         var1.setline(205);
         PyObject var10000 = var1.getlocal(0).__getattr__("inflater").__getattr__("finished").__call__(var2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("gzip");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("gzip_header_skipped").__not__();
            }

            var10000 = var10000.__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(206);
            var3 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"unused_data", var3);
            var3 = null;
         }

         var1.setline(208);
         PyObject var8 = var1.getlocal(2);
         var10000 = var8._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(209);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("max_length must be a positive integer")));
         } else {
            var1.setline(212);
            var10000 = var1.getlocal(0).__getattr__("gzip");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("gzip_header_skipped").__not__();
            }

            PyObject var4;
            if (var10000.__nonzero__()) {
               var1.setline(213);
               var8 = var1.getlocal(0).__getattr__("unused_data")._add(var1.getlocal(1));
               var1.setlocal(1, var8);
               var3 = null;
               var1.setline(214);
               var3 = PyString.fromInterned("");
               var1.getlocal(0).__setattr__((String)"unused_data", var3);
               var3 = null;

               try {
                  var1.setline(216);
                  var8 = var1.getglobal("_skip_gzip_header").__call__(var2, var1.getlocal(1));
                  var1.setlocal(1, var8);
                  var3 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (var10.match(var1.getglobal("IndexError"))) {
                     var1.setline(219);
                     var4 = var1.getlocal(1);
                     var1.getlocal(0).__setattr__("unused_data", var4);
                     var4 = null;
                     var1.setline(220);
                     PyString var9 = PyString.fromInterned("");
                     var1.f_lasti = -1;
                     return var9;
                  }

                  throw var10;
               }

               var1.setline(221);
               var8 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("gzip_header_skipped", var8);
               var3 = null;
            }

            var1.setline(223);
            var8 = var1.getglobal("_to_input").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var8);
            var3 = null;
            var1.setline(225);
            var1.getlocal(0).__getattr__("inflater").__getattr__("setInput").__call__(var2, var1.getlocal(1));
            var1.setline(226);
            var8 = var1.getglobal("_get_inflate_data").__call__(var2, var1.getlocal(0).__getattr__("inflater"), var1.getlocal(2));
            var1.setlocal(3, var8);
            var3 = null;
            var1.setline(228);
            var8 = var1.getlocal(0).__getattr__("inflater").__getattr__("getRemaining").__call__(var2);
            var1.setlocal(4, var8);
            var3 = null;
            var1.setline(229);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(230);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("inflater").__getattr__("finished").__call__(var2).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(231);
                  var8 = var1.getlocal(1).__getslice__(var1.getlocal(4).__neg__(), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("unconsumed_tail", var8);
                  var3 = null;
               } else {
                  var1.setline(233);
                  var10000 = var1.getlocal(0);
                  String var11 = "unused_data";
                  PyObject var5 = var10000;
                  PyObject var6 = var5.__getattr__(var11);
                  var6 = var6._iadd(var1.getlocal(1).__getslice__(var1.getlocal(4).__neg__(), (PyObject)null, (PyObject)null));
                  var5.__setattr__(var11, var6);
               }
            }

            var1.setline(235);
            var4 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject flush$13(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      if (var1.getlocal(0).__getattr__("_ended").__nonzero__()) {
         var1.setline(239);
         throw Py.makeException(var1.getglobal("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("decompressobj may not be used after flush()")));
      } else {
         var1.setline(240);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(241);
            PyInteger var4 = Py.newInteger(0);
            var1.setlocal(1, var4);
            var3 = null;
         } else {
            var1.setline(242);
            var3 = var1.getlocal(1);
            var10000 = var3._le(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(243);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("length must be greater than zero")));
            }
         }

         var1.setline(244);
         var3 = var1.getglobal("_get_inflate_data").__call__(var2, var1.getlocal(0).__getattr__("inflater"), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(245);
         var1.getlocal(0).__getattr__("inflater").__getattr__("end").__call__(var2);
         var1.setline(246);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _to_input$14(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(250);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(251);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("array").__getattr__("array")).__nonzero__()) {
            var1.setline(252);
            var3 = var1.getlocal(0).__getattr__("tostring").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(253);
            PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("buffer"));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("memoryview"));
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(254);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(256);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("must be string or read-only buffer, not %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)))));
            }
         }
      }
   }

   public PyObject _get_deflate_data$15(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyInteger var3 = Py.newInteger(1024);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(260);
      PyObject var4 = var1.getglobal("jarray").__getattr__("zeros").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("b"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(261);
      var4 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(4, var4);
      var3 = null;

      while(true) {
         var1.setline(262);
         if (!var1.getlocal(0).__getattr__("finished").__call__(var2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(263);
         var4 = var1.getlocal(0).__getattr__("deflate").__call__(var2, var1.getlocal(3), Py.newInteger(0), var1.getlocal(2), var1.getglobal("_zlib_to_deflater").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("Deflater").__getattr__("NO_FLUSH")));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(264);
         var4 = var1.getlocal(5);
         PyObject var10000 = var4._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(266);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(3), Py.newInteger(0), Py.newInteger(0), var1.getlocal(5)));
      }

      var1.setline(267);
      var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(268);
      var4 = var1.getlocal(4).__getattr__("read").__call__(var2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _get_inflate_data$16(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getglobal("jarray").__getattr__("zeros").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024), (PyObject)PyString.fromInterned("b"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(272);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(273);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(4, var6);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(274);
         if (!var1.getlocal(0).__getattr__("finished").__call__(var2).__not__().__nonzero__()) {
            break;
         }

         try {
            var1.setline(276);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(277);
               var3 = var1.getlocal(0).__getattr__("inflate").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("min").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024), (PyObject)var1.getlocal(1)._sub(var1.getlocal(4))));
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(279);
               var3 = var1.getlocal(0).__getattr__("inflate").__call__(var2, var1.getlocal(2));
               var1.setlocal(5, var3);
               var3 = null;
            }
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("DataFormatException"))) {
               PyObject var4 = var7.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(281);
               throw Py.makeException(var1.getglobal("error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6))));
            }

            throw var7;
         }

         var1.setline(283);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(286);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(var1.getlocal(5));
         var1.setlocal(4, var3);
         var1.setline(287);
         var1.getlocal(3).__getattr__("write").__call__(var2, var1.getglobal("String").__call__(var2, var1.getlocal(2), Py.newInteger(0), Py.newInteger(0), var1.getlocal(5)));
         var1.setline(288);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(4);
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(290);
      var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(291);
      var3 = var1.getlocal(3).__getattr__("read").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _skip_gzip_header$17(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyObject var3 = var1.getglobal("array").__getattr__("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("B"), (PyObject)var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(307);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(308);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(311);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(Py.newInteger(31));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._ne(Py.newInteger(139));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(312);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(314);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(2));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(315);
         var4 = var1.getlocal(1).__getitem__(Py.newInteger(3));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(316);
         var4 = var1.getlocal(1).__getslice__(Py.newInteger(4), Py.newInteger(8), (PyObject)null);
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(317);
         var4 = var1.getlocal(1).__getitem__(Py.newInteger(8));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(318);
         var4 = var1.getlocal(1).__getitem__(Py.newInteger(9));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(321);
         var4 = var1.getlocal(1).__getslice__(Py.newInteger(10), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(323);
         if (var1.getlocal(5)._and(var1.getglobal("FEXTRA")).__nonzero__()) {
            var1.setline(325);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(0))._add(var1.getlocal(1).__getitem__(Py.newInteger(1))._mul(Py.newInteger(256)));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(326);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(2)._add(var1.getlocal(9)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(327);
         if (var1.getlocal(5)._and(var1.getglobal("FNAME")).__nonzero__()) {
            var1.setline(329);
            var4 = var1.getlocal(1).__getslice__(var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(330);
         if (var1.getlocal(5)._and(var1.getglobal("FCOMMENT")).__nonzero__()) {
            var1.setline(332);
            var4 = var1.getlocal(1).__getslice__(var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(333);
         if (var1.getlocal(5)._and(var1.getglobal("FHCRC")).__nonzero__()) {
            var1.setline(335);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(337);
         var3 = var1.getlocal(1).__getattr__("tostring").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public zlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error$1 = Py.newCode(0, var2, var1, "error", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "value", "s1", "s2", "c", "high_bit", "remaining_high_word"};
      adler32$2 = Py.newCode(2, var2, var1, "adler32", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "value"};
      crc32$3 = Py.newCode(2, var2, var1, "crc32", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "level", "deflater"};
      compress$4 = Py.newCode(2, var2, var1, "compress", 79, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "wbits", "bufsize", "inflater", "data"};
      decompress$5 = Py.newCode(3, var2, var1, "decompress", 91, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      compressobj$6 = Py.newCode(0, var2, var1, "compressobj", 113, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "level", "method", "wbits", "memLevel", "strategy"};
      __init__$7 = Py.newCode(6, var2, var1, "__init__", 126, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "deflated"};
      compress$8 = Py.newCode(2, var2, var1, "compress", 144, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode", "last"};
      flush$9 = Py.newCode(2, var2, var1, "flush", 157, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      decompressobj$10 = Py.newCode(0, var2, var1, "decompressobj", 174, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "wbits"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 176, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "max_length", "inflated", "r"};
      decompress$12 = Py.newCode(3, var2, var1, "decompress", 194, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "length", "last"};
      flush$13 = Py.newCode(2, var2, var1, "flush", 237, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      _to_input$14 = Py.newCode(1, var2, var1, "_to_input", 248, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"deflater", "mode", "buflen", "buf", "s", "l"};
      _get_deflate_data$15 = Py.newCode(2, var2, var1, "_get_deflate_data", 258, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"inflater", "max_length", "buf", "s", "total", "l", "e"};
      _get_inflate_data$16 = Py.newCode(2, var2, var1, "_get_inflate_data", 270, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "s", "id1", "id2", "cm", "flg", "mtime", "xfl", "os", "xlen"};
      _skip_gzip_header$17 = Py.newCode(1, var2, var1, "_skip_gzip_header", 301, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new zlib$py("zlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(zlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.error$1(var2, var3);
         case 2:
            return this.adler32$2(var2, var3);
         case 3:
            return this.crc32$3(var2, var3);
         case 4:
            return this.compress$4(var2, var3);
         case 5:
            return this.decompress$5(var2, var3);
         case 6:
            return this.compressobj$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.compress$8(var2, var3);
         case 9:
            return this.flush$9(var2, var3);
         case 10:
            return this.decompressobj$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.decompress$12(var2, var3);
         case 13:
            return this.flush$13(var2, var3);
         case 14:
            return this._to_input$14(var2, var3);
         case 15:
            return this._get_deflate_data$15(var2, var3);
         case 16:
            return this._get_inflate_data$16(var2, var3);
         case 17:
            return this._skip_gzip_header$17(var2, var3);
         default:
            return null;
      }
   }
}
