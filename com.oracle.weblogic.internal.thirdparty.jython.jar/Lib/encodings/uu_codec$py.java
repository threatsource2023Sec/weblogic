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
@Filename("encodings/uu_codec.py")
public class uu_codec$py extends PyFunctionTable implements PyRunnable {
   static uu_codec$py self;
   static final PyCode f$0;
   static final PyCode uu_encode$1;
   static final PyCode uu_decode$2;
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
      var1.setglobal("__doc__", PyString.fromInterned(" Python 'uu_codec' Codec - UU content transfer encoding\n\n    Unlike most of the other codecs which target Unicode, this codec\n    will return Python string objects for both encode and decode.\n\n    Written by Marc-Andre Lemburg (mal@lemburg.com). Some details were\n    adapted from uu.py which was written by Lance Ellinghouse and\n    modified by Jack Jansen and Fredrik Lundh.\n\n"));
      var1.setline(10);
      PyString.fromInterned(" Python 'uu_codec' Codec - UU content transfer encoding\n\n    Unlike most of the other codecs which target Unicode, this codec\n    will return Python string objects for both encode and decode.\n\n    Written by Marc-Andre Lemburg (mal@lemburg.com). Some details were\n    adapted from uu.py which was written by Lance Ellinghouse and\n    modified by Jack Jansen and Fredrik Lundh.\n\n");
      var1.setline(11);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var1.setline(15);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict"), PyString.fromInterned("<data>"), Py.newInteger(438)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, uu_encode$1, PyString.fromInterned(" Encodes the object input and returns a tuple (output\n        object, length consumed).\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n    "));
      var1.setlocal("uu_encode", var6);
      var3 = null;
      var1.setline(44);
      var5 = new PyObject[]{PyString.fromInterned("strict")};
      var6 = new PyFunction(var1.f_globals, var5, uu_decode$2, PyString.fromInterned(" Decodes the object input and returns a tuple (output\n        object, length consumed).\n\n        input must be an object which provides the bf_getreadbuf\n        buffer slot. Python strings, buffer objects and memory\n        mapped files are examples of objects providing this slot.\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n        Note: filename and file mode information in the input data is\n        ignored.\n\n    "));
      var1.setlocal("uu_decode", var6);
      var3 = null;
      var1.setline(96);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$3);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(104);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$6);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(108);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$8);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(112);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$10);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(115);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$11);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(120);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$12, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject uu_encode$1(PyFrame var1, ThreadState var2) {
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
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(27);
      var5 = new String[]{"b2a_uu"};
      var6 = imp.importFrom("binascii", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(29);
      var3 = var1.getlocal(4).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(4).__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(6).__getattr__("read");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getlocal(7).__getattr__("write");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(35);
      var1.getlocal(9).__call__(var2, PyString.fromInterned("begin %o %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)._and(Py.newInteger(511)), var1.getlocal(2)})));
      var1.setline(36);
      var3 = var1.getlocal(8).__call__((ThreadState)var2, (PyObject)Py.newInteger(45));
      var1.setlocal(10, var3);
      var3 = null;

      while(true) {
         var1.setline(37);
         if (!var1.getlocal(10).__nonzero__()) {
            var1.setline(40);
            var1.getlocal(9).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" \nend\n"));
            var1.setline(42);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(7).__getattr__("getvalue").__call__(var2), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(38);
         var1.getlocal(9).__call__(var2, var1.getlocal(5).__call__(var2, var1.getlocal(10)));
         var1.setline(39);
         var3 = var1.getlocal(8).__call__((ThreadState)var2, (PyObject)Py.newInteger(45));
         var1.setlocal(10, var3);
         var3 = null;
      }
   }

   public PyObject uu_decode$2(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned(" Decodes the object input and returns a tuple (output\n        object, length consumed).\n\n        input must be an object which provides the bf_getreadbuf\n        buffer slot. Python strings, buffer objects and memory\n        mapped files are examples of objects providing this slot.\n\n        errors defines the error handling to apply. It defaults to\n        'strict' handling which is the only currently supported\n        error handling for this codec.\n\n        Note: filename and file mode information in the input data is\n        ignored.\n\n    ");
      var1.setline(61);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("strict"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(62);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(63);
      var6 = new String[]{"a2b_uu"};
      var7 = imp.importFrom("binascii", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(64);
      var3 = var1.getlocal(2).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getlocal(2).__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(4).__getattr__("readline");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getlocal(5).__getattr__("write");
      var1.setlocal(7, var3);
      var3 = null;

      do {
         var1.setline(70);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(71);
         var3 = var1.getlocal(6).__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(72);
         if (var1.getlocal(8).__not__().__nonzero__()) {
            var1.setline(73);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Missing \"begin\" line in input data"));
         }

         var1.setline(74);
         var3 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("begin"));
         var3 = null;
      } while(!var10000.__nonzero__());

      while(true) {
         var1.setline(78);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(79);
         var3 = var1.getlocal(6).__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(80);
         var10000 = var1.getlocal(8).__not__();
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(8);
            var10000 = var3._eq(PyString.fromInterned("end\n"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            break;
         }

         try {
            var1.setline(84);
            var3 = var1.getlocal(3).__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (!var8.match(var1.getglobal("binascii").__getattr__("Error"))) {
               throw var8;
            }

            var4 = var8.value;
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(87);
            var4 = var1.getglobal("ord").__call__(var2, var1.getlocal(8).__getitem__(Py.newInteger(0)))._sub(Py.newInteger(32))._and(Py.newInteger(63))._mul(Py.newInteger(4))._add(Py.newInteger(5))._div(Py.newInteger(3));
            var1.setlocal(11, var4);
            var4 = null;
            var1.setline(88);
            var4 = var1.getlocal(3).__call__(var2, var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(11), (PyObject)null));
            var1.setlocal(9, var4);
            var4 = null;
         }

         var1.setline(90);
         var1.getlocal(7).__call__(var2, var1.getlocal(9));
      }

      var1.setline(91);
      if (var1.getlocal(8).__not__().__nonzero__()) {
         var1.setline(92);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Truncated input data"));
      } else {
         var1.setline(94);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("getvalue").__call__(var2), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject Codec$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(98);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(101);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$5, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getglobal("uu_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$5(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getglobal("uu_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(105);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$7, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$7(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("uu_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(109);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$9, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$9(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getglobal("uu_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(113);
      return var1.getf_locals();
   }

   public PyObject StreamReader$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(116);
      return var1.getf_locals();
   }

   public PyObject getregentry$12(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("uu"), var1.getglobal("uu_encode"), var1.getglobal("uu_decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public uu_codec$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors", "filename", "mode", "StringIO", "b2a_uu", "infile", "outfile", "read", "write", "chunk"};
      uu_encode$1 = Py.newCode(4, var2, var1, "uu_encode", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "errors", "StringIO", "a2b_uu", "infile", "outfile", "readline", "write", "s", "data", "v", "nbytes"};
      uu_decode$2 = Py.newCode(2, var2, var1, "uu_decode", 44, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$3 = Py.newCode(0, var2, var1, "Codec", 96, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 98, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$5 = Py.newCode(3, var2, var1, "decode", 101, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$6 = Py.newCode(0, var2, var1, "IncrementalEncoder", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$7 = Py.newCode(3, var2, var1, "encode", 105, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$8 = Py.newCode(0, var2, var1, "IncrementalDecoder", 108, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$9 = Py.newCode(3, var2, var1, "decode", 109, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$10 = Py.newCode(0, var2, var1, "StreamWriter", 112, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$11 = Py.newCode(0, var2, var1, "StreamReader", 115, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$12 = Py.newCode(0, var2, var1, "getregentry", 120, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new uu_codec$py("encodings/uu_codec$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(uu_codec$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.uu_encode$1(var2, var3);
         case 2:
            return this.uu_decode$2(var2, var3);
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
