package encodings;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/bz2_codec.py")
public class bz2_codec$py extends PyFunctionTable implements PyRunnable {
   static bz2_codec$py self;
   static final PyCode f$0;
   static final PyCode bz2_encode$1;
   static final PyCode bz2_decode$2;
   static final PyCode Codec$3;
   static final PyCode encode$4;
   static final PyCode decode$5;
   static final PyCode IncrementalEncoder$6;
   static final PyCode __init__$7;
   static final PyCode encode$8;
   static final PyCode reset$9;
   static final PyCode IncrementalDecoder$10;
   static final PyCode __init__$11;
   static final PyCode decode$12;
   static final PyCode reset$13;
   static final PyCode StreamWriter$14;
   static final PyCode StreamReader$15;
   static final PyCode getregentry$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python 'bz2_codec' Codec - bz2 compression encoding\n\n    Unlike most of the other codecs which target Unicode, this codec\n    will return Python string objects for both encode and decode.\n\n    Adapted by Raymond Hettinger from zlib_codec.py which was written\n    by Marc-Andre Lemburg (mal@lemburg.com).\n\n"));
      var1.setline(9);
      PyString.fromInterned(" Python 'bz2_codec' Codec - bz2 compression encoding\n\n    Unlike most of the other codecs which target Unicode, this codec\n    will return Python string objects for both encode and decode.\n\n    Adapted by Raymond Hettinger from zlib_codec.py which was written\n    by Marc-Andre Lemburg (mal@lemburg.com).\n\n");
      var1.setline(10);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("bz2", var1, -1);
      var1.setlocal("bz2", var3);
      var3 = null;
      var1.setline(15);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, bz2_encode$1, PyString.fromInterned(" Encodes the object input and returns a tuple (output\n        object, length consumed).\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n    "));
      var1.setlocal("bz2_encode", var6);
      var3 = null;
      var1.setline(29);
      var5 = new PyObject[]{PyString.fromInterned("strict")};
      var6 = new PyFunction(var1.f_globals, var5, bz2_decode$2, PyString.fromInterned(" Decodes the object input and returns a tuple (output\n        object, length consumed).\n\n        input must be an object which provides the bf_getreadbuf\n        buffer slot. Python strings, buffer objects and memory\n        mapped files are examples of objects providing this slot.\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n    "));
      var1.setlocal("bz2_decode", var6);
      var3 = null;
      var1.setline(47);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$3);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(54);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$6);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(70);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$10);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(85);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$14);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(88);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$15);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(93);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$16, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bz2_encode$1(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyString.fromInterned(" Encodes the object input and returns a tuple (output\n        object, length consumed).\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n    ");
      var1.setline(25);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("strict"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(26);
      var3 = var1.getglobal("bz2").__getattr__("compress").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(27);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject bz2_decode$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyString.fromInterned(" Decodes the object input and returns a tuple (output\n        object, length consumed).\n\n        input must be an object which provides the bf_getreadbuf\n        buffer slot. Python strings, buffer objects and memory\n        mapped files are examples of objects providing this slot.\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n    ");
      var1.setline(43);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("strict"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(44);
      var3 = var1.getglobal("bz2").__getattr__("decompress").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(45);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Codec$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(49);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(51);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$5, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getglobal("bz2_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$5(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("bz2_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(55);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(60);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$8, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$9, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("strict"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(57);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("bz2").__getattr__("BZ2Compressor").__call__(var2);
      var1.getlocal(0).__setattr__("compressobj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(62);
         var3 = var1.getlocal(0).__getattr__("compressobj").__getattr__("compress").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(63);
         var3 = var1.getlocal(3)._add(var1.getlocal(0).__getattr__("compressobj").__getattr__("flush").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(65);
         var3 = var1.getlocal(0).__getattr__("compressobj").__getattr__("compress").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$9(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getglobal("bz2").__getattr__("BZ2Compressor").__call__(var2);
      var1.getlocal(0).__setattr__("compressobj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IncrementalDecoder$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(71);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(76);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$12, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$13, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("strict"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(73);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("bz2").__getattr__("BZ2Decompressor").__call__(var2);
      var1.getlocal(0).__setattr__("decompressobj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$12(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(78);
         PyObject var6 = var1.getlocal(0).__getattr__("decompressobj").__getattr__("decompress").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("EOFError"))) {
            var1.setline(80);
            PyString var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject reset$13(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyObject var3 = var1.getglobal("bz2").__getattr__("BZ2Decompressor").__call__(var2);
      var1.getlocal(0).__setattr__("decompressobj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(86);
      return var1.getf_locals();
   }

   public PyObject StreamReader$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(89);
      return var1.getf_locals();
   }

   public PyObject getregentry$16(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("bz2"), var1.getglobal("bz2_encode"), var1.getglobal("bz2_decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public bz2_codec$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors", "output"};
      bz2_encode$1 = Py.newCode(2, var2, var1, "bz2_encode", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "errors", "output"};
      bz2_decode$2 = Py.newCode(2, var2, var1, "bz2_decode", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$3 = Py.newCode(0, var2, var1, "Codec", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$5 = Py.newCode(3, var2, var1, "decode", 51, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$6 = Py.newCode(0, var2, var1, "IncrementalEncoder", 54, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 55, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "c"};
      encode$8 = Py.newCode(3, var2, var1, "encode", 60, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$9 = Py.newCode(1, var2, var1, "reset", 67, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$10 = Py.newCode(0, var2, var1, "IncrementalDecoder", 70, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 71, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      decode$12 = Py.newCode(3, var2, var1, "decode", 76, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$13 = Py.newCode(1, var2, var1, "reset", 82, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$14 = Py.newCode(0, var2, var1, "StreamWriter", 85, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$15 = Py.newCode(0, var2, var1, "StreamReader", 88, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$16 = Py.newCode(0, var2, var1, "getregentry", 93, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bz2_codec$py("encodings/bz2_codec$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bz2_codec$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.bz2_encode$1(var2, var3);
         case 2:
            return this.bz2_decode$2(var2, var3);
         case 3:
            return this.Codec$3(var2, var3);
         case 4:
            return this.encode$4(var2, var3);
         case 5:
            return this.decode$5(var2, var3);
         case 6:
            return this.IncrementalEncoder$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.encode$8(var2, var3);
         case 9:
            return this.reset$9(var2, var3);
         case 10:
            return this.IncrementalDecoder$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.decode$12(var2, var3);
         case 13:
            return this.reset$13(var2, var3);
         case 14:
            return this.StreamWriter$14(var2, var3);
         case 15:
            return this.StreamReader$15(var2, var3);
         case 16:
            return this.getregentry$16(var2, var3);
         default:
            return null;
      }
   }
}
