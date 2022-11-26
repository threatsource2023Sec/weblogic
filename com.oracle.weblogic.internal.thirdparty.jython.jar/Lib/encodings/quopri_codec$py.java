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
@Filename("encodings/quopri_codec.py")
public class quopri_codec$py extends PyFunctionTable implements PyRunnable {
   static quopri_codec$py self;
   static final PyCode f$0;
   static final PyCode quopri_encode$1;
   static final PyCode quopri_decode$2;
   static final PyCode Codec$3;
   static final PyCode encode$4;
   static final PyCode decode$5;
   static final PyCode IncrementalEncoder$6;
   static final PyCode encode$7;
   static final PyCode IncrementalDecoder$8;
   static final PyCode decode$9;
   static final PyCode StreamWriter$10;
   static final PyCode StreamReader$11;
   static final PyCode getregentry$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Codec for quoted-printable encoding.\n\nLike base64 and rot13, this returns Python strings, not Unicode.\n"));
      var1.setline(4);
      PyString.fromInterned("Codec for quoted-printable encoding.\n\nLike base64 and rot13, this returns Python strings, not Unicode.\n");
      var1.setline(6);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var3 = imp.importOne("quopri", var1, -1);
      var1.setlocal("quopri", var3);
      var3 = null;

      String[] var4;
      PyObject var10;
      PyObject[] var11;
      try {
         var1.setline(8);
         String[] var9 = new String[]{"StringIO"};
         var11 = imp.importFrom("cStringIO", var9, var1, -1);
         var10 = var11[0];
         var1.setlocal("StringIO", var10);
         var4 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(10);
         var4 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var8[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(12);
      var11 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var12 = new PyFunction(var1.f_globals, var11, quopri_encode$1, PyString.fromInterned("Encode the input, returning a tuple (output object, length consumed).\n\n    errors defines the error handling to apply. It defaults to\n    'strict' handling which is the only currently supported\n    error handling for this codec.\n\n    "));
      var1.setlocal("quopri_encode", var12);
      var3 = null;
      var1.setline(28);
      var11 = new PyObject[]{PyString.fromInterned("strict")};
      var12 = new PyFunction(var1.f_globals, var11, quopri_decode$2, PyString.fromInterned("Decode the input, returning a tuple (output object, length consumed).\n\n    errors defines the error handling to apply. It defaults to\n    'strict' handling which is the only currently supported\n    error handling for this codec.\n\n    "));
      var1.setlocal("quopri_decode", var12);
      var3 = null;
      var1.setline(43);
      var11 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      var10 = Py.makeClass("Codec", var11, Codec$3);
      var1.setlocal("Codec", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(50);
      var11 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var10 = Py.makeClass("IncrementalEncoder", var11, IncrementalEncoder$6);
      var1.setlocal("IncrementalEncoder", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(54);
      var11 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var10 = Py.makeClass("IncrementalDecoder", var11, IncrementalDecoder$8);
      var1.setlocal("IncrementalDecoder", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(58);
      var11 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var10 = Py.makeClass("StreamWriter", var11, StreamWriter$10);
      var1.setlocal("StreamWriter", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(61);
      var11 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var10 = Py.makeClass("StreamReader", var11, StreamReader$11);
      var1.setlocal("StreamReader", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(66);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, getregentry$12, (PyObject)null);
      var1.setlocal("getregentry", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject quopri_encode$1(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyString.fromInterned("Encode the input, returning a tuple (output object, length consumed).\n\n    errors defines the error handling to apply. It defaults to\n    'strict' handling which is the only currently supported\n    error handling for this codec.\n\n    ");
      var1.setline(20);
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

      var1.setline(22);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(24);
      var1.getglobal("quopri").__getattr__("encode").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1));
      var1.setline(25);
      var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(26);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject quopri_decode$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyString.fromInterned("Decode the input, returning a tuple (output object, length consumed).\n\n    errors defines the error handling to apply. It defaults to\n    'strict' handling which is the only currently supported\n    error handling for this codec.\n\n    ");
      var1.setline(36);
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

      var1.setline(37);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var1.getglobal("quopri").__getattr__("decode").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(40);
      var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(41);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Codec$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(47);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$5, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("quopri_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$5(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getglobal("quopri_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(51);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$7, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$7(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("quopri_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(55);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$9, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$9(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getglobal("quopri_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      return var1.getf_locals();
   }

   public PyObject StreamReader$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(62);
      return var1.getf_locals();
   }

   public PyObject getregentry$12(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("quopri"), var1.getglobal("quopri_encode"), var1.getglobal("quopri_decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public quopri_codec$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors", "f", "g", "output"};
      quopri_encode$1 = Py.newCode(2, var2, var1, "quopri_encode", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "errors", "f", "g", "output"};
      quopri_decode$2 = Py.newCode(2, var2, var1, "quopri_decode", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$3 = Py.newCode(0, var2, var1, "Codec", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$5 = Py.newCode(3, var2, var1, "decode", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$6 = Py.newCode(0, var2, var1, "IncrementalEncoder", 50, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$7 = Py.newCode(3, var2, var1, "encode", 51, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$8 = Py.newCode(0, var2, var1, "IncrementalDecoder", 54, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$9 = Py.newCode(3, var2, var1, "decode", 55, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$10 = Py.newCode(0, var2, var1, "StreamWriter", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$11 = Py.newCode(0, var2, var1, "StreamReader", 61, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$12 = Py.newCode(0, var2, var1, "getregentry", 66, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new quopri_codec$py("encodings/quopri_codec$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(quopri_codec$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.quopri_encode$1(var2, var3);
         case 2:
            return this.quopri_decode$2(var2, var3);
         case 3:
            return this.Codec$3(var2, var3);
         case 4:
            return this.encode$4(var2, var3);
         case 5:
            return this.decode$5(var2, var3);
         case 6:
            return this.IncrementalEncoder$6(var2, var3);
         case 7:
            return this.encode$7(var2, var3);
         case 8:
            return this.IncrementalDecoder$8(var2, var3);
         case 9:
            return this.decode$9(var2, var3);
         case 10:
            return this.StreamWriter$10(var2, var3);
         case 11:
            return this.StreamReader$11(var2, var3);
         case 12:
            return this.getregentry$12(var2, var3);
         default:
            return null;
      }
   }
}
