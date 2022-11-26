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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/cp1251.py")
public class cp1251$py extends PyFunctionTable implements PyRunnable {
   static cp1251$py self;
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
      var1.setglobal("__doc__", PyString.fromInterned(" Python Character Mapping Codec cp1251 generated from 'MAPPINGS/VENDORS/MICSFT/WINDOWS/CP1251.TXT' with gencodec.py.\n\n"));
      var1.setline(3);
      PyString.fromInterned(" Python Character Mapping Codec cp1251 generated from 'MAPPINGS/VENDORS/MICSFT/WINDOWS/CP1251.TXT' with gencodec.py.\n\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(9);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(17);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$4);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(21);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$6);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(25);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$8);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(28);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$9);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(33);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$10, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.setline(47);
      PyUnicode var7 = PyUnicode.fromInterned("\u0000\u0001\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u007fЂЃ‚ѓ„…†‡€‰Љ‹ЊЌЋЏђ‘’“”•–—\ufffe™љ›њќћџ ЎўЈ¤Ґ¦§Ё©Є«¬\u00ad®Ї°±Ііґµ¶·ё№є»јЅѕїАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя");
      var1.setlocal("decoding_table", var7);
      var3 = null;
      var1.setline(307);
      var3 = var1.getname("codecs").__getattr__("charmap_build").__call__(var2, var1.getname("decoding_table"));
      var1.setlocal("encoding_table", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$2, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(14);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$3, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("encoding_table"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("decoding_table"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("encoding_table")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("decoding_table")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(26);
      return var1.getf_locals();
   }

   public PyObject StreamReader$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      return var1.getf_locals();
   }

   public PyObject getregentry$10(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("cp1251"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public cp1251$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$2 = Py.newCode(3, var2, var1, "encode", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$3 = Py.newCode(3, var2, var1, "decode", 14, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$4 = Py.newCode(0, var2, var1, "IncrementalEncoder", 17, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 18, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$6 = Py.newCode(0, var2, var1, "IncrementalDecoder", 21, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 22, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$8 = Py.newCode(0, var2, var1, "StreamWriter", 25, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$9 = Py.newCode(0, var2, var1, "StreamReader", 28, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$10 = Py.newCode(0, var2, var1, "getregentry", 33, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cp1251$py("encodings/cp1251$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cp1251$py.class);
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
