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
@Filename("encodings/cp932.py")
public class cp932$py extends PyFunctionTable implements PyRunnable {
   static cp932$py self;
   static final PyCode f$0;
   static final PyCode Codec$1;
   static final PyCode IncrementalEncoder$2;
   static final PyCode IncrementalDecoder$3;
   static final PyCode StreamReader$4;
   static final PyCode StreamWriter$5;
   static final PyCode getregentry$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyObject var3 = imp.importOne("_codecs_jp", var1, -1);
      var1.setlocal("_codecs_jp", var3);
      var3 = null;
      var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOneAs("_multibytecodec", var1, -1);
      var1.setlocal("mbc", var3);
      var3 = null;
      var1.setline(10);
      var3 = var1.getname("_codecs_jp").__getattr__("getcodec").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cp932"));
      var1.setlocal("codec", var3);
      var3 = null;
      var1.setline(12);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("mbc").__getattr__("MultibyteIncrementalEncoder"), var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$2);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(20);
      var5 = new PyObject[]{var1.getname("mbc").__getattr__("MultibyteIncrementalDecoder"), var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$3);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(24);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("mbc").__getattr__("MultibyteStreamReader"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$4);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(27);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("mbc").__getattr__("MultibyteStreamWriter"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$5);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(30);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$6, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject var3 = var1.getname("codec").__getattr__("encode");
      var1.setlocal("encode", var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getname("codec").__getattr__("decode");
      var1.setlocal("decode", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject IncrementalEncoder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject var3 = var1.getname("codec");
      var1.setlocal("codec", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject IncrementalDecoder$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject var3 = var1.getname("codec");
      var1.setlocal("codec", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject StreamReader$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject var3 = var1.getname("codec");
      var1.setlocal("codec", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject StreamWriter$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      PyObject var3 = var1.getname("codec");
      var1.setlocal("codec", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getregentry$6(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("cp932"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public cp932$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IncrementalEncoder$2 = Py.newCode(0, var2, var1, "IncrementalEncoder", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IncrementalDecoder$3 = Py.newCode(0, var2, var1, "IncrementalDecoder", 20, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$4 = Py.newCode(0, var2, var1, "StreamReader", 24, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamWriter$5 = Py.newCode(0, var2, var1, "StreamWriter", 27, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$6 = Py.newCode(0, var2, var1, "getregentry", 30, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cp932$py("encodings/cp932$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cp932$py.class);
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
            return this.IncrementalDecoder$3(var2, var3);
         case 4:
            return this.StreamReader$4(var2, var3);
         case 5:
            return this.StreamWriter$5(var2, var3);
         case 6:
            return this.getregentry$6(var2, var3);
         default:
            return null;
      }
   }
}
