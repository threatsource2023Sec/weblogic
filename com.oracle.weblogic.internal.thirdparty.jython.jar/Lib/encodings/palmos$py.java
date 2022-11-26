package encodings;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("encodings/palmos.py")
public class palmos$py extends PyFunctionTable implements PyRunnable {
   static palmos$py self;
   static final PyCode f$0;
   static final PyCode Codec$1;
   static final PyCode encode$2;
   static final PyCode decode$3;
   static final PyCode IncrementalEncoder$4;
   static final PyCode encode$5;
   static final PyCode IncrementalDecoder$6;
   static final PyCode decode$7;
   static final PyCode StreamWriter$8;
   static final PyCode StreamReader$9;
   static final PyCode getregentry$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python Character Mapping Codec for PalmOS 3.5.\n\nWritten by Sjoerd Mullender (sjoerd@acm.org); based on iso8859_15.py.\n\n"));
      var1.setline(5);
      PyString.fromInterned(" Python Character Mapping Codec for PalmOS 3.5.\n\nWritten by Sjoerd Mullender (sjoerd@acm.org); based on iso8859_15.py.\n\n");
      var1.setline(7);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(11);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$4);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(22);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$6);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$8);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(29);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$9);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(34);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$10, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("codecs").__getattr__("make_identity_dict").__call__(var2, var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)));
      var1.setlocal("decoding_map", var3);
      var3 = null;
      var1.setline(50);
      var1.getname("decoding_map").__getattr__("update").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{Py.newInteger(128), Py.newInteger(8364), Py.newInteger(130), Py.newInteger(8218), Py.newInteger(131), Py.newInteger(402), Py.newInteger(132), Py.newInteger(8222), Py.newInteger(133), Py.newInteger(8230), Py.newInteger(134), Py.newInteger(8224), Py.newInteger(135), Py.newInteger(8225), Py.newInteger(136), Py.newInteger(710), Py.newInteger(137), Py.newInteger(8240), Py.newInteger(138), Py.newInteger(352), Py.newInteger(139), Py.newInteger(8249), Py.newInteger(140), Py.newInteger(338), Py.newInteger(141), Py.newInteger(9830), Py.newInteger(142), Py.newInteger(9827), Py.newInteger(143), Py.newInteger(9829), Py.newInteger(144), Py.newInteger(9824), Py.newInteger(145), Py.newInteger(8216), Py.newInteger(146), Py.newInteger(8217), Py.newInteger(147), Py.newInteger(8220), Py.newInteger(148), Py.newInteger(8221), Py.newInteger(149), Py.newInteger(8226), Py.newInteger(150), Py.newInteger(8211), Py.newInteger(151), Py.newInteger(8212), Py.newInteger(152), Py.newInteger(732), Py.newInteger(153), Py.newInteger(8482), Py.newInteger(154), Py.newInteger(353), Py.newInteger(156), Py.newInteger(339), Py.newInteger(159), Py.newInteger(376)})));
      var1.setline(83);
      var3 = var1.getname("codecs").__getattr__("make_encoding_map").__call__(var2, var1.getname("decoding_map"));
      var1.setlocal("encoding_map", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$2, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(15);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$3, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("encoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("decoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("encoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("decoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      return var1.getf_locals();
   }

   public PyObject StreamReader$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(30);
      return var1.getf_locals();
   }

   public PyObject getregentry$10(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("palmos"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public palmos$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$2 = Py.newCode(3, var2, var1, "encode", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$3 = Py.newCode(3, var2, var1, "decode", 15, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$4 = Py.newCode(0, var2, var1, "IncrementalEncoder", 18, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 19, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$6 = Py.newCode(0, var2, var1, "IncrementalDecoder", 22, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 23, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$8 = Py.newCode(0, var2, var1, "StreamWriter", 26, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$9 = Py.newCode(0, var2, var1, "StreamReader", 29, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$10 = Py.newCode(0, var2, var1, "getregentry", 34, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new palmos$py("encodings/palmos$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(palmos$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Codec$1(var2, var3);
         case 2:
            return this.encode$2(var2, var3);
         case 3:
            return this.decode$3(var2, var3);
         case 4:
            return this.IncrementalEncoder$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.IncrementalDecoder$6(var2, var3);
         case 7:
            return this.decode$7(var2, var3);
         case 8:
            return this.StreamWriter$8(var2, var3);
         case 9:
            return this.StreamReader$9(var2, var3);
         case 10:
            return this.getregentry$10(var2, var3);
         default:
            return null;
      }
   }
}
