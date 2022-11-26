package encodings;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/charmap.py")
public class charmap$py extends PyFunctionTable implements PyRunnable {
   static charmap$py self;
   static final PyCode f$0;
   static final PyCode Codec$1;
   static final PyCode IncrementalEncoder$2;
   static final PyCode __init__$3;
   static final PyCode encode$4;
   static final PyCode IncrementalDecoder$5;
   static final PyCode __init__$6;
   static final PyCode decode$7;
   static final PyCode StreamWriter$8;
   static final PyCode __init__$9;
   static final PyCode encode$10;
   static final PyCode StreamReader$11;
   static final PyCode __init__$12;
   static final PyCode decode$13;
   static final PyCode getregentry$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Generic Python Character Mapping Codec.\n\n    Use this codec directly rather than through the automatic\n    conversion mechanisms supplied by unicode() and .encode().\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n"));
      var1.setline(11);
      PyString.fromInterned(" Generic Python Character Mapping Codec.\n\n    Use this codec directly rather than through the automatic\n    conversion mechanisms supplied by unicode() and .encode().\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(17);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(24);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$2);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(32);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$5);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(40);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$8);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(49);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$11);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(60);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$14, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject var3 = var1.getname("codecs").__getattr__("charmap_encode");
      var1.setlocal("encode", var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getname("codecs").__getattr__("charmap_decode");
      var1.setlocal("decode", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject IncrementalEncoder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(29);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(27);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mapping", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getlocal(0).__getattr__("mapping")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(33);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(37);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      var1.getglobal("codecs").__getattr__("IncrementalDecoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(35);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mapping", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getlocal(0).__getattr__("mapping")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(46);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, encode$10, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(44);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("mapping", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$10(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getglobal("Codec").__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("mapping"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamReader$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(51);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(55);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$13, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      var1.getglobal("codecs").__getattr__("StreamReader").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(53);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("mapping", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$13(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getglobal("Codec").__getattr__("decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("mapping"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getregentry$14(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("charmap"), var1.getglobal("Codec").__getattr__("encode"), var1.getglobal("Codec").__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public charmap$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IncrementalEncoder$2 = Py.newCode(0, var2, var1, "IncrementalEncoder", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors", "mapping"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 25, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 29, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$5 = Py.newCode(0, var2, var1, "IncrementalDecoder", 32, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors", "mapping"};
      __init__$6 = Py.newCode(3, var2, var1, "__init__", 33, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 37, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$8 = Py.newCode(0, var2, var1, "StreamWriter", 40, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors", "mapping"};
      __init__$9 = Py.newCode(4, var2, var1, "__init__", 42, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      encode$10 = Py.newCode(3, var2, var1, "encode", 46, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$11 = Py.newCode(0, var2, var1, "StreamReader", 49, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors", "mapping"};
      __init__$12 = Py.newCode(4, var2, var1, "__init__", 51, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$13 = Py.newCode(3, var2, var1, "decode", 55, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getregentry$14 = Py.newCode(0, var2, var1, "getregentry", 60, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new charmap$py("encodings/charmap$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(charmap$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Codec$1(var2, var3);
         case 2:
            return this.IncrementalEncoder$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.encode$4(var2, var3);
         case 5:
            return this.IncrementalDecoder$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.decode$7(var2, var3);
         case 8:
            return this.StreamWriter$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.encode$10(var2, var3);
         case 11:
            return this.StreamReader$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.decode$13(var2, var3);
         case 14:
            return this.getregentry$14(var2, var3);
         default:
            return null;
      }
   }
}
