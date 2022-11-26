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
@Filename("encodings/rot_13.py")
public class rot_13$py extends PyFunctionTable implements PyRunnable {
   static rot_13$py self;
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
   static final PyCode rot13$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python Character Mapping Codec for ROT13.\n\n    See http://ucsub.colorado.edu/~kominek/rot13/ for details.\n\n    Written by Marc-Andre Lemburg (mal@lemburg.com).\n\n"));
      var1.setline(8);
      PyString.fromInterned(" Python Character Mapping Codec for ROT13.\n\n    See http://ucsub.colorado.edu/~kominek/rot13/ for details.\n\n    Written by Marc-Andre Lemburg (mal@lemburg.com).\n\n");
      var1.setline(10);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(14);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(22);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$4);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$6);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(30);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$8);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$9);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(38);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$10, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.setline(51);
      var3 = var1.getname("codecs").__getattr__("make_identity_dict").__call__(var2, var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)));
      var1.setlocal("decoding_map", var3);
      var3 = null;
      var1.setline(52);
      PyObject var10000 = var1.getname("decoding_map").__getattr__("update");
      PyObject[] var10004 = new PyObject[104];
      set$$0(var10004);
      var10000.__call__((ThreadState)var2, (PyObject)(new PyDictionary(var10004)));
      var1.setline(109);
      var3 = var1.getname("codecs").__getattr__("make_encoding_map").__call__(var2, var1.getname("decoding_map"));
      var1.setlocal("encoding_map", var3);
      var3 = null;
      var1.setline(113);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, rot13$11, (PyObject)null);
      var1.setlocal("rot13", var6);
      var3 = null;
      var1.setline(116);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(117);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var1.setline(118);
         var1.getname("rot13").__call__(var2, var1.getname("sys").__getattr__("stdin"), var1.getname("sys").__getattr__("stdout"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$2, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(19);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$3, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("encoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("decoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("encoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("decoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      return var1.getf_locals();
   }

   public PyObject StreamReader$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      return var1.getf_locals();
   }

   public PyObject getregentry$10(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("rot-13"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamWriter"), var1.getglobal("StreamReader")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamwriter", "streamreader"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   private static void set$$0(PyObject[] var0) {
      var0[0] = Py.newInteger(65);
      var0[1] = Py.newInteger(78);
      var0[2] = Py.newInteger(66);
      var0[3] = Py.newInteger(79);
      var0[4] = Py.newInteger(67);
      var0[5] = Py.newInteger(80);
      var0[6] = Py.newInteger(68);
      var0[7] = Py.newInteger(81);
      var0[8] = Py.newInteger(69);
      var0[9] = Py.newInteger(82);
      var0[10] = Py.newInteger(70);
      var0[11] = Py.newInteger(83);
      var0[12] = Py.newInteger(71);
      var0[13] = Py.newInteger(84);
      var0[14] = Py.newInteger(72);
      var0[15] = Py.newInteger(85);
      var0[16] = Py.newInteger(73);
      var0[17] = Py.newInteger(86);
      var0[18] = Py.newInteger(74);
      var0[19] = Py.newInteger(87);
      var0[20] = Py.newInteger(75);
      var0[21] = Py.newInteger(88);
      var0[22] = Py.newInteger(76);
      var0[23] = Py.newInteger(89);
      var0[24] = Py.newInteger(77);
      var0[25] = Py.newInteger(90);
      var0[26] = Py.newInteger(78);
      var0[27] = Py.newInteger(65);
      var0[28] = Py.newInteger(79);
      var0[29] = Py.newInteger(66);
      var0[30] = Py.newInteger(80);
      var0[31] = Py.newInteger(67);
      var0[32] = Py.newInteger(81);
      var0[33] = Py.newInteger(68);
      var0[34] = Py.newInteger(82);
      var0[35] = Py.newInteger(69);
      var0[36] = Py.newInteger(83);
      var0[37] = Py.newInteger(70);
      var0[38] = Py.newInteger(84);
      var0[39] = Py.newInteger(71);
      var0[40] = Py.newInteger(85);
      var0[41] = Py.newInteger(72);
      var0[42] = Py.newInteger(86);
      var0[43] = Py.newInteger(73);
      var0[44] = Py.newInteger(87);
      var0[45] = Py.newInteger(74);
      var0[46] = Py.newInteger(88);
      var0[47] = Py.newInteger(75);
      var0[48] = Py.newInteger(89);
      var0[49] = Py.newInteger(76);
      var0[50] = Py.newInteger(90);
      var0[51] = Py.newInteger(77);
      var0[52] = Py.newInteger(97);
      var0[53] = Py.newInteger(110);
      var0[54] = Py.newInteger(98);
      var0[55] = Py.newInteger(111);
      var0[56] = Py.newInteger(99);
      var0[57] = Py.newInteger(112);
      var0[58] = Py.newInteger(100);
      var0[59] = Py.newInteger(113);
      var0[60] = Py.newInteger(101);
      var0[61] = Py.newInteger(114);
      var0[62] = Py.newInteger(102);
      var0[63] = Py.newInteger(115);
      var0[64] = Py.newInteger(103);
      var0[65] = Py.newInteger(116);
      var0[66] = Py.newInteger(104);
      var0[67] = Py.newInteger(117);
      var0[68] = Py.newInteger(105);
      var0[69] = Py.newInteger(118);
      var0[70] = Py.newInteger(106);
      var0[71] = Py.newInteger(119);
      var0[72] = Py.newInteger(107);
      var0[73] = Py.newInteger(120);
      var0[74] = Py.newInteger(108);
      var0[75] = Py.newInteger(121);
      var0[76] = Py.newInteger(109);
      var0[77] = Py.newInteger(122);
      var0[78] = Py.newInteger(110);
      var0[79] = Py.newInteger(97);
      var0[80] = Py.newInteger(111);
      var0[81] = Py.newInteger(98);
      var0[82] = Py.newInteger(112);
      var0[83] = Py.newInteger(99);
      var0[84] = Py.newInteger(113);
      var0[85] = Py.newInteger(100);
      var0[86] = Py.newInteger(114);
      var0[87] = Py.newInteger(101);
      var0[88] = Py.newInteger(115);
      var0[89] = Py.newInteger(102);
      var0[90] = Py.newInteger(116);
      var0[91] = Py.newInteger(103);
      var0[92] = Py.newInteger(117);
      var0[93] = Py.newInteger(104);
      var0[94] = Py.newInteger(118);
      var0[95] = Py.newInteger(105);
      var0[96] = Py.newInteger(119);
      var0[97] = Py.newInteger(106);
      var0[98] = Py.newInteger(120);
      var0[99] = Py.newInteger(107);
      var0[100] = Py.newInteger(121);
      var0[101] = Py.newInteger(108);
      var0[102] = Py.newInteger(122);
      var0[103] = Py.newInteger(109);
   }

   public PyObject rot13$11(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rot-13")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public rot_13$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$2 = Py.newCode(3, var2, var1, "encode", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$3 = Py.newCode(3, var2, var1, "decode", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$4 = Py.newCode(0, var2, var1, "IncrementalEncoder", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 23, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$6 = Py.newCode(0, var2, var1, "IncrementalDecoder", 26, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 27, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$8 = Py.newCode(0, var2, var1, "StreamWriter", 30, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$9 = Py.newCode(0, var2, var1, "StreamReader", 33, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$10 = Py.newCode(0, var2, var1, "getregentry", 38, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"infile", "outfile"};
      rot13$11 = Py.newCode(2, var2, var1, "rot13", 113, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new rot_13$py("encodings/rot_13$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(rot_13$py.class);
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
         case 11:
            return this.rot13$11(var2, var3);
         default:
            return null;
      }
   }
}
