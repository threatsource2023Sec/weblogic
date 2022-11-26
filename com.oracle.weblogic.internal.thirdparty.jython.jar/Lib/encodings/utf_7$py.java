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
@Filename("encodings/utf_7.py")
public class utf_7$py extends PyFunctionTable implements PyRunnable {
   static utf_7$py self;
   static final PyCode f$0;
   static final PyCode decode$1;
   static final PyCode IncrementalEncoder$2;
   static final PyCode encode$3;
   static final PyCode IncrementalDecoder$4;
   static final PyCode StreamWriter$5;
   static final PyCode StreamReader$6;
   static final PyCode getregentry$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python 'utf-7' Codec\n\nWritten by Brian Quinlan (brian@sweetapp.com).\n"));
      var1.setline(4);
      PyString.fromInterned(" Python 'utf-7' Codec\n\nWritten by Brian Quinlan (brian@sweetapp.com).\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(9);
      var3 = var1.getname("codecs").__getattr__("utf_7_encode");
      var1.setlocal("encode", var3);
      var3 = null;
      var1.setline(11);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, decode$1, (PyObject)null);
      var1.setlocal("decode", var6);
      var3 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      PyObject var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$2);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$4);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(21);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$5);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(24);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$6);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(29);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$7, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$1(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_7_decode").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$3, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$3(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_7_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject var3 = var1.getname("codecs").__getattr__("utf_7_decode");
      var1.setlocal("_buffer_decode", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject StreamWriter$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject var3 = var1.getname("codecs").__getattr__("utf_7_encode");
      var1.setlocal("encode", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject StreamReader$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject var3 = var1.getname("codecs").__getattr__("utf_7_decode");
      var1.setlocal("decode", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getregentry$7(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("utf-7"), var1.getglobal("encode"), var1.getglobal("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public utf_7$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors"};
      decode$1 = Py.newCode(2, var2, var1, "decode", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$2 = Py.newCode(0, var2, var1, "IncrementalEncoder", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$3 = Py.newCode(3, var2, var1, "encode", 15, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$4 = Py.newCode(0, var2, var1, "IncrementalDecoder", 18, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamWriter$5 = Py.newCode(0, var2, var1, "StreamWriter", 21, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$6 = Py.newCode(0, var2, var1, "StreamReader", 24, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$7 = Py.newCode(0, var2, var1, "getregentry", 29, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new utf_7$py("encodings/utf_7$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(utf_7$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.decode$1(var2, var3);
         case 2:
            return this.IncrementalEncoder$2(var2, var3);
         case 3:
            return this.encode$3(var2, var3);
         case 4:
            return this.IncrementalDecoder$4(var2, var3);
         case 5:
            return this.StreamWriter$5(var2, var3);
         case 6:
            return this.StreamReader$6(var2, var3);
         case 7:
            return this.getregentry$7(var2, var3);
         default:
            return null;
      }
   }
}
