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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("binhex.py")
public class binhex$py extends PyFunctionTable implements PyRunnable {
   static binhex$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode getfileinfo$2;
   static final PyCode openrsrc$3;
   static final PyCode FInfo$4;
   static final PyCode __init__$5;
   static final PyCode getfileinfo$6;
   static final PyCode openrsrc$7;
   static final PyCode __init__$8;
   static final PyCode read$9;
   static final PyCode write$10;
   static final PyCode close$11;
   static final PyCode _Hqxcoderengine$12;
   static final PyCode __init__$13;
   static final PyCode write$14;
   static final PyCode _flush$15;
   static final PyCode close$16;
   static final PyCode _Rlecoderengine$17;
   static final PyCode __init__$18;
   static final PyCode write$19;
   static final PyCode close$20;
   static final PyCode BinHex$21;
   static final PyCode __init__$22;
   static final PyCode _writeinfo$23;
   static final PyCode _write$24;
   static final PyCode _writecrc$25;
   static final PyCode write$26;
   static final PyCode close_data$27;
   static final PyCode write_rsrc$28;
   static final PyCode close$29;
   static final PyCode binhex$30;
   static final PyCode _Hqxdecoderengine$31;
   static final PyCode __init__$32;
   static final PyCode read$33;
   static final PyCode close$34;
   static final PyCode _Rledecoderengine$35;
   static final PyCode __init__$36;
   static final PyCode read$37;
   static final PyCode _fill$38;
   static final PyCode close$39;
   static final PyCode HexBin$40;
   static final PyCode __init__$41;
   static final PyCode _read$42;
   static final PyCode _checkcrc$43;
   static final PyCode _readheader$44;
   static final PyCode read$45;
   static final PyCode close_data$46;
   static final PyCode read_rsrc$47;
   static final PyCode close$48;
   static final PyCode hexbin$49;
   static final PyCode _test$50;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Macintosh binhex compression/decompression.\n\neasy interface:\nbinhex(inputfilename, outputfilename)\nhexbin(inputfilename, outputfilename)\n"));
      var1.setline(6);
      PyString.fromInterned("Macintosh binhex compression/decompression.\n\neasy interface:\nbinhex(inputfilename, outputfilename)\nhexbin(inputfilename, outputfilename)\n");
      var1.setline(24);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(25);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(26);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(27);
      var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var1.setline(29);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("binhex"), PyString.fromInterned("hexbin"), PyString.fromInterned("Error")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(31);
      PyObject[] var10 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("Error", var10, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(35);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
      PyObject[] var7 = Py.unpackSequence(var3, 3);
      PyObject var5 = var7[0];
      var1.setlocal("_DID_HEADER", var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal("_DID_DATA", var5);
      var5 = null;
      var5 = var7[2];
      var1.setlocal("_DID_RSRC", var5);
      var5 = null;
      var3 = null;
      var1.setline(38);
      PyInteger var11 = Py.newInteger(32768);
      var1.setlocal("REASONABLY_LARGE", var11);
      var3 = null;
      var1.setline(39);
      var11 = Py.newInteger(64);
      var1.setlocal("LINELEN", var11);
      var3 = null;
      var1.setline(40);
      var3 = var1.getname("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(144));
      var1.setlocal("RUNCHAR", var3);
      var3 = null;

      PyFunction var14;
      try {
         var1.setline(48);
         String[] var13 = new String[]{"FSSpec", "FInfo"};
         var10 = imp.importFrom("Carbon.File", var13, var1, -1);
         var4 = var10[0];
         var1.setlocal("FSSpec", var4);
         var4 = null;
         var4 = var10[1];
         var1.setlocal("FInfo", var4);
         var4 = null;
         var1.setline(49);
         var13 = new String[]{"openrf"};
         var10 = imp.importFrom("MacOS", var13, var1, -1);
         var4 = var10[0];
         var1.setlocal("openrf", var4);
         var4 = null;
         var1.setline(51);
         var10 = Py.EmptyObjects;
         var14 = new PyFunction(var1.f_globals, var10, getfileinfo$2, (PyObject)null);
         var1.setlocal("getfileinfo", var14);
         var3 = null;
         var1.setline(63);
         var10 = Py.EmptyObjects;
         var14 = new PyFunction(var1.f_globals, var10, openrsrc$3, (PyObject)null);
         var1.setlocal("openrsrc", var14);
         var3 = null;
      } catch (Throwable var6) {
         PyException var12 = Py.setException(var6, var1);
         if (!var12.match(var1.getname("ImportError"))) {
            throw var12;
         }

         var1.setline(75);
         var7 = Py.EmptyObjects;
         var5 = Py.makeClass("FInfo", var7, FInfo$4);
         var1.setlocal("FInfo", var5);
         var5 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(81);
         var7 = Py.EmptyObjects;
         PyFunction var9 = new PyFunction(var1.f_globals, var7, getfileinfo$6, (PyObject)null);
         var1.setlocal("getfileinfo", var9);
         var4 = null;
         var1.setline(98);
         var7 = Py.EmptyObjects;
         var5 = Py.makeClass("openrsrc", var7, openrsrc$7);
         var1.setlocal("openrsrc", var5);
         var5 = null;
         Arrays.fill(var7, (Object)null);
      }

      var1.setline(111);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_Hqxcoderengine", var10, _Hqxcoderengine$12);
      var1.setlocal("_Hqxcoderengine", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(150);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_Rlecoderengine", var10, _Rlecoderengine$17);
      var1.setlocal("_Rlecoderengine", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(172);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("BinHex", var10, BinHex$21);
      var1.setlocal("BinHex", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(250);
      var10 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var10, binhex$30, PyString.fromInterned("(infilename, outfilename) - Create binhex-encoded copy of a file"));
      var1.setlocal("binhex", var14);
      var3 = null;
      var1.setline(272);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_Hqxdecoderengine", var10, _Hqxdecoderengine$31);
      var1.setlocal("_Hqxdecoderengine", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(316);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_Rledecoderengine", var10, _Rledecoderengine$35);
      var1.setlocal("_Rledecoderengine", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(370);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("HexBin", var10, HexBin$40);
      var1.setlocal("HexBin", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(472);
      var10 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var10, hexbin$49, PyString.fromInterned("(infilename, outfilename) - Decode binhexed file"));
      var1.setlocal("hexbin", var14);
      var3 = null;
      var1.setline(500);
      var10 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var10, _test$50, (PyObject)null);
      var1.setlocal("_test", var14);
      var3 = null;
      var1.setline(507);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(508);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      return var1.getf_locals();
   }

   public PyObject getfileinfo$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("FSSpec").__call__(var2, var1.getlocal(0)).__getattr__("FSpGetFInfo").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(55);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(56);
      var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(57);
      var3 = var1.getlocal(4).__getattr__("tell").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("openrf").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("*rb"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(59);
      var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(60);
      var3 = var1.getlocal(4).__getattr__("tell").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(61);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1), var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject openrsrc$3(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyString var3;
      PyObject var4;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(65);
         var3 = PyString.fromInterned("*rb");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(67);
         var4 = PyString.fromInterned("*")._add(var1.getlocal(1).__getitem__(Py.newInteger(0)));
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(68);
      var4 = var1.getglobal("openrf").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject FInfo$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString var3 = PyString.fromInterned("????");
      var1.getlocal(0).__setattr__((String)"Type", var3);
      var3 = null;
      var1.setline(78);
      var3 = PyString.fromInterned("????");
      var1.getlocal(0).__setattr__((String)"Creator", var3);
      var3 = null;
      var1.setline(79);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"Flags", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getfileinfo$6(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getglobal("FInfo").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(85);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0)).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(256));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getlocal(3).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(86);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(90);
            PyString var7 = PyString.fromInterned("TEXT");
            var1.getlocal(1).__setattr__((String)"Type", var7);
            var5 = null;
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(87);
         var10000 = var1.getlocal(4).__getattr__("isspace").__call__(var2).__not__();
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(4);
            var10000 = var5._lt(PyString.fromInterned(" "));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(4));
               var10000 = var5._gt(Py.newInteger(127));
               var5 = null;
            }
         }
      } while(!var10000.__nonzero__());

      var1.setline(91);
      var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(92);
      var3 = var1.getlocal(2).__getattr__("tell").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(93);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(94);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      PyObject[] var6 = Py.unpackSequence(var3, 2);
      var5 = var6[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var6[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(95);
      var3 = var1.getlocal(7).__getattr__("replace").__call__((ThreadState)var2, PyString.fromInterned(":"), (PyObject)PyString.fromInterned("-"), (PyObject)Py.newInteger(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(96);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1), var1.getlocal(5), Py.newInteger(0)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject openrsrc$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(99);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$9, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$10, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$11, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$9(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$10(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$11(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Hqxcoderengine$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Write data to the coder in 3-byte chunks"));
      var1.setline(112);
      PyString.fromInterned("Write data to the coder in 3-byte chunks");
      var1.setline(114);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$14, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _flush$15, (PyObject)null);
      var1.setlocal("_flush", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$16, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ofp", var3);
      var3 = null;
      var1.setline(116);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"data", var4);
      var3 = null;
      var1.setline(117);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"hqxdata", var4);
      var3 = null;
      var1.setline(118);
      var3 = var1.getglobal("LINELEN")._sub(Py.newInteger(1));
      var1.getlocal(0).__setattr__("linelen", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$14(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getlocal(2)._floordiv(Py.newInteger(3))._mul(Py.newInteger(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(126);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(127);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(128);
         var3 = var1.getlocal(0).__getattr__("hqxdata")._add(var1.getglobal("binascii").__getattr__("b2a_hqx").__call__(var2, var1.getlocal(1)));
         var1.getlocal(0).__setattr__("hqxdata", var3);
         var3 = null;
         var1.setline(129);
         var1.getlocal(0).__getattr__("_flush").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _flush$15(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(133);
         PyObject var4 = var1.getlocal(2);
         PyObject var10000 = var4._le(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("hqxdata"))._sub(var1.getlocal(0).__getattr__("linelen")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(138);
            var4 = var1.getlocal(0).__getattr__("hqxdata").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__setattr__("hqxdata", var4);
            var3 = null;
            var1.setline(139);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(140);
               var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("hqxdata")._add(PyString.fromInterned(":\n")));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(134);
         var4 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("linelen"));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(135);
         var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("hqxdata").__getslice__(var1.getlocal(2), var1.getlocal(3), (PyObject)null)._add(PyString.fromInterned("\n")));
         var1.setline(136);
         var4 = var1.getglobal("LINELEN");
         var1.getlocal(0).__setattr__("linelen", var4);
         var3 = null;
         var1.setline(137);
         var4 = var1.getlocal(3);
         var1.setlocal(2, var4);
         var3 = null;
      }
   }

   public PyObject close$16(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      if (var1.getlocal(0).__getattr__("data").__nonzero__()) {
         var1.setline(144);
         PyObject var3 = var1.getlocal(0).__getattr__("hqxdata")._add(var1.getglobal("binascii").__getattr__("b2a_hqx").__call__(var2, var1.getlocal(0).__getattr__("data")));
         var1.getlocal(0).__setattr__("hqxdata", var3);
         var3 = null;
      }

      var1.setline(146);
      var1.getlocal(0).__getattr__("_flush").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(147);
      var1.getlocal(0).__getattr__("ofp").__getattr__("close").__call__(var2);
      var1.setline(148);
      var1.getlocal(0).__delattr__("ofp");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Rlecoderengine$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Write data to the RLE-coder in suitably large chunks"));
      var1.setline(151);
      PyString.fromInterned("Write data to the RLE-coder in suitably large chunks");
      var1.setline(153);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$19, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$20, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ofp", var3);
      var3 = null;
      var1.setline(155);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"data", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$19(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getlocal(0).__getattr__("data")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      PyObject var10000 = var3._lt(var1.getglobal("REASONABLY_LARGE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(160);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(161);
         var3 = var1.getglobal("binascii").__getattr__("rlecode_hqx").__call__(var2, var1.getlocal(0).__getattr__("data"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(162);
         var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getlocal(2));
         var1.setline(163);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"data", var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$20(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      if (var1.getlocal(0).__getattr__("data").__nonzero__()) {
         var1.setline(167);
         PyObject var3 = var1.getglobal("binascii").__getattr__("rlecode_hqx").__call__(var2, var1.getlocal(0).__getattr__("data"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(168);
         var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.setline(169);
      var1.getlocal(0).__getattr__("ofp").__getattr__("close").__call__(var2);
      var1.setline(170);
      var1.getlocal(0).__delattr__("ofp");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BinHex$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(173);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$22, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(189);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _writeinfo$23, (PyObject)null);
      var1.setlocal("_writeinfo", var4);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write$24, (PyObject)null);
      var1.setlocal("_write", var4);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _writecrc$25, (PyObject)null);
      var1.setlocal("_writecrc", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$26, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close_data$27, (PyObject)null);
      var1.setlocal("close_data", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_rsrc$28, (PyObject)null);
      var1.setlocal("write_rsrc", var4);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$29, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$22(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(175);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(176);
         var3 = var1.getlocal(2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(177);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(178);
      var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(This file must be converted with BinHex 4.0)\n\n:"));
      var1.setline(179);
      var3 = var1.getglobal("_Hqxcoderengine").__call__(var2, var1.getlocal(2));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(180);
      var3 = var1.getglobal("_Rlecoderengine").__call__(var2, var1.getlocal(8));
      var1.getlocal(0).__setattr__("ofp", var3);
      var3 = null;
      var1.setline(181);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"crc", var6);
      var3 = null;
      var1.setline(182);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(183);
         var3 = var1.getglobal("FInfo").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(184);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("dlen", var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("rlen", var3);
      var3 = null;
      var1.setline(186);
      var1.getlocal(0).__getattr__("_writeinfo").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setline(187);
      var3 = var1.getglobal("_DID_HEADER");
      var1.getlocal(0).__setattr__("state", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _writeinfo$23(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(191);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._gt(Py.newInteger(63));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(192);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Filename too long"));
      } else {
         var1.setline(193);
         var3 = var1.getglobal("chr").__call__(var2, var1.getlocal(3))._add(var1.getlocal(1))._add(PyString.fromInterned("\u0000"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(194);
         var3 = var1.getlocal(2).__getattr__("Type")._add(var1.getlocal(2).__getattr__("Creator"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(197);
         var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">h"), (PyObject)var1.getlocal(2).__getattr__("Flags"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(198);
         var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, PyString.fromInterned(">ii"), (PyObject)var1.getlocal(0).__getattr__("dlen"), (PyObject)var1.getlocal(0).__getattr__("rlen"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(199);
         var3 = var1.getlocal(4)._add(var1.getlocal(5))._add(var1.getlocal(6))._add(var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(200);
         var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getlocal(8));
         var1.setline(201);
         var1.getlocal(0).__getattr__("_writecrc").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _write$24(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getglobal("binascii").__getattr__("crc_hqx").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc"));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(205);
      var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _writecrc$25(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyObject var3 = var1.getlocal(0).__getattr__("crc");
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(211);
         var4 = PyString.fromInterned(">h");
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(213);
         var4 = PyString.fromInterned(">H");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(214);
      var1.getlocal(0).__getattr__("ofp").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc")));
      var1.setline(215);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"crc", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$26(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._ne(var1.getglobal("_DID_HEADER"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(219);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Writing data at the wrong time"));
      } else {
         var1.setline(220);
         var3 = var1.getlocal(0).__getattr__("dlen")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var1.getlocal(0).__setattr__("dlen", var3);
         var3 = null;
         var1.setline(221);
         var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close_data$27(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject var3 = var1.getlocal(0).__getattr__("dlen");
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(225);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Incorrect data size, diff=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("rlen")})));
      } else {
         var1.setline(226);
         var1.getlocal(0).__getattr__("_writecrc").__call__(var2);
         var1.setline(227);
         var3 = var1.getglobal("_DID_DATA");
         var1.getlocal(0).__setattr__("state", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject write_rsrc$28(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._lt(var1.getglobal("_DID_DATA"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(231);
         var1.getlocal(0).__getattr__("close_data").__call__(var2);
      }

      var1.setline(232);
      var3 = var1.getlocal(0).__getattr__("state");
      var10000 = var3._ne(var1.getglobal("_DID_DATA"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(233);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Writing resource data at the wrong time"));
      } else {
         var1.setline(234);
         var3 = var1.getlocal(0).__getattr__("rlen")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
         var1.getlocal(0).__setattr__("rlen", var3);
         var3 = null;
         var1.setline(235);
         var1.getlocal(0).__getattr__("_write").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$29(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._lt(var1.getglobal("_DID_DATA"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(239);
         var1.getlocal(0).__getattr__("close_data").__call__(var2);
      }

      var1.setline(240);
      var3 = var1.getlocal(0).__getattr__("state");
      var10000 = var3._ne(var1.getglobal("_DID_DATA"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(241);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Close at the wrong time"));
      } else {
         var1.setline(242);
         var3 = var1.getlocal(0).__getattr__("rlen");
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(243);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Incorrect resource-datasize, diff=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("rlen")})));
         } else {
            var1.setline(245);
            var1.getlocal(0).__getattr__("_writecrc").__call__(var2);
            var1.setline(246);
            var1.getlocal(0).__getattr__("ofp").__getattr__("close").__call__(var2);
            var1.setline(247);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("state", var3);
            var3 = null;
            var1.setline(248);
            var1.getlocal(0).__delattr__("ofp");
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject binhex$30(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyString.fromInterned("(infilename, outfilename) - Create binhex-encoded copy of a file");
      var1.setline(252);
      PyObject var3 = var1.getglobal("getfileinfo").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(253);
      var3 = var1.getglobal("BinHex").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(257);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(258);
         var3 = var1.getlocal(4).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(128000));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(259);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(260);
         var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(5));
      }

      var1.setline(261);
      var1.getlocal(3).__getattr__("close_data").__call__(var2);
      var1.setline(262);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(264);
      var3 = var1.getglobal("openrsrc").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(265);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(266);
         var3 = var1.getlocal(4).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(128000));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(267);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(268);
         var1.getlocal(3).__getattr__("write_rsrc").__call__(var2, var1.getlocal(5));
      }

      var1.setline(269);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(270);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Hqxdecoderengine$31(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Read data via the decoder in 4-byte chunks"));
      var1.setline(273);
      PyString.fromInterned("Read data via the decoder in 4-byte chunks");
      var1.setline(275);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$32, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(279);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$33, PyString.fromInterned("Read at least wtd bytes (or until EOF)"));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$34, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$32(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ifp", var3);
      var3 = null;
      var1.setline(277);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"eof", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$33(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("Read at least wtd bytes (or until EOF)");
      var1.setline(281);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(282);
      PyObject var8 = var1.getlocal(1);
      var1.setlocal(3, var8);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(286);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(311);
            var8 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setline(287);
         if (var1.getlocal(0).__getattr__("eof").__nonzero__()) {
            var1.setline(287);
            var8 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setline(288);
         var4 = var1.getlocal(3)._add(Py.newInteger(2))._floordiv(Py.newInteger(3))._mul(Py.newInteger(4));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(289);
         var4 = var1.getlocal(0).__getattr__("ifp").__getattr__("read").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;

         while(true) {
            var1.setline(295);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            try {
               var1.setline(297);
               var4 = var1.getglobal("binascii").__getattr__("a2b_hqx").__call__(var2, var1.getlocal(4));
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.getlocal(0).__setattr__("eof", var6);
               var6 = null;
               var4 = null;
               break;
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (!var9.match(var1.getglobal("binascii").__getattr__("Incomplete"))) {
                  throw var9;
               }

               var1.setline(301);
               var1.setline(302);
               var4 = var1.getlocal(0).__getattr__("ifp").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(303);
               if (var1.getlocal(6).__not__().__nonzero__()) {
                  var1.setline(304);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Premature EOF on binhex file"));
               }

               var1.setline(306);
               var4 = var1.getlocal(4)._add(var1.getlocal(6));
               var1.setlocal(4, var4);
               var4 = null;
            }
         }

         var1.setline(307);
         var4 = var1.getlocal(2)._add(var1.getlocal(5));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(308);
         var4 = var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(309);
         var10000 = var1.getlocal(2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("eof").__not__();
         }
      } while(!var10000.__nonzero__());

      var1.setline(310);
      throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Premature EOF on binhex file"));
   }

   public PyObject close$34(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      var1.getlocal(0).__getattr__("ifp").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Rledecoderengine$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Read data via the RLE-coder"));
      var1.setline(317);
      PyString.fromInterned("Read data via the RLE-coder");
      var1.setline(319);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$36, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(325);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$37, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fill$38, (PyObject)null);
      var1.setlocal("_fill", var4);
      var3 = null;
      var1.setline(367);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$39, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ifp", var3);
      var3 = null;
      var1.setline(321);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"pre_buffer", var4);
      var3 = null;
      var1.setline(322);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"post_buffer", var4);
      var3 = null;
      var1.setline(323);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"eof", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$37(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("post_buffer")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(327);
         var1.getlocal(0).__getattr__("_fill").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("post_buffer"))));
      }

      var1.setline(328);
      var3 = var1.getlocal(0).__getattr__("post_buffer").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(329);
      var3 = var1.getlocal(0).__getattr__("post_buffer").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("post_buffer", var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _fill$38(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      PyObject var3 = var1.getlocal(0).__getattr__("pre_buffer")._add(var1.getlocal(0).__getattr__("ifp").__getattr__("read").__call__(var2, var1.getlocal(1)._add(Py.newInteger(4))));
      var1.getlocal(0).__setattr__("pre_buffer", var3);
      var3 = null;
      var1.setline(334);
      if (var1.getlocal(0).__getattr__("ifp").__getattr__("eof").__nonzero__()) {
         var1.setline(335);
         var3 = var1.getlocal(0).__getattr__("post_buffer")._add(var1.getglobal("binascii").__getattr__("rledecode_hqx").__call__(var2, var1.getlocal(0).__getattr__("pre_buffer")));
         var1.getlocal(0).__setattr__("post_buffer", var3);
         var3 = null;
         var1.setline(337);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"pre_buffer", var4);
         var3 = null;
         var1.setline(338);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(351);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("pre_buffer"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(352);
         var3 = var1.getlocal(0).__getattr__("pre_buffer").__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null);
         PyObject var10000 = var3._eq(var1.getglobal("RUNCHAR")._add(PyString.fromInterned("\u0000"))._add(var1.getglobal("RUNCHAR")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(353);
            var3 = var1.getlocal(2)._sub(Py.newInteger(3));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(354);
            var3 = var1.getlocal(0).__getattr__("pre_buffer").__getitem__(Py.newInteger(-1));
            var10000 = var3._eq(var1.getglobal("RUNCHAR"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(355);
               var3 = var1.getlocal(2)._sub(Py.newInteger(2));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(356);
               var3 = var1.getlocal(0).__getattr__("pre_buffer").__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
               var10000 = var3._eq(var1.getglobal("RUNCHAR")._add(PyString.fromInterned("\u0000")));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(357);
                  var3 = var1.getlocal(2)._sub(Py.newInteger(2));
                  var1.setlocal(2, var3);
                  var3 = null;
               } else {
                  var1.setline(358);
                  var3 = var1.getlocal(0).__getattr__("pre_buffer").__getitem__(Py.newInteger(-2));
                  var10000 = var3._eq(var1.getglobal("RUNCHAR"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(359);
                  } else {
                     var1.setline(361);
                     var3 = var1.getlocal(2)._sub(Py.newInteger(1));
                     var1.setlocal(2, var3);
                     var3 = null;
                  }
               }
            }
         }

         var1.setline(363);
         var3 = var1.getlocal(0).__getattr__("post_buffer")._add(var1.getglobal("binascii").__getattr__("rledecode_hqx").__call__(var2, var1.getlocal(0).__getattr__("pre_buffer").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)));
         var1.getlocal(0).__setattr__("post_buffer", var3);
         var3 = null;
         var1.setline(365);
         var3 = var1.getlocal(0).__getattr__("pre_buffer").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("pre_buffer", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$39(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      var1.getlocal(0).__getattr__("ifp").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HexBin$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(371);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read$42, (PyObject)null);
      var1.setlocal("_read", var4);
      var3 = null;
      var1.setline(400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _checkcrc$43, (PyObject)null);
      var1.setlocal("_checkcrc", var4);
      var3 = null;
      var1.setline(410);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _readheader$44, (PyObject)null);
      var1.setlocal("_readheader", var4);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$45, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close_data$46, (PyObject)null);
      var1.setlocal("close_data", var4);
      var3 = null;
      var1.setline(452);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_rsrc$47, (PyObject)null);
      var1.setlocal("read_rsrc", var4);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$48, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(373);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      while(true) {
         var1.setline(377);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(378);
         var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(379);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(380);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("No binhex data found"));
         }

         var1.setline(383);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("\r"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(385);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned(":"));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(387);
            var3 = var1.getlocal(2);
            var10000 = var3._ne(PyString.fromInterned("\n"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(388);
               var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }

      var1.setline(390);
      var3 = var1.getglobal("_Hqxdecoderengine").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(391);
      var3 = var1.getglobal("_Rledecoderengine").__call__(var2, var1.getlocal(4));
      var1.getlocal(0).__setattr__("ifp", var3);
      var3 = null;
      var1.setline(392);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"crc", var4);
      var3 = null;
      var1.setline(393);
      var1.getlocal(0).__getattr__("_readheader").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read$42(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var3 = var1.getlocal(0).__getattr__("ifp").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(397);
      var3 = var1.getglobal("binascii").__getattr__("crc_hqx").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("crc"));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkcrc$43(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">h"), (PyObject)var1.getlocal(0).__getattr__("ifp").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2))).__getitem__(Py.newInteger(0))._and(Py.newInteger(65535));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(404);
      var3 = var1.getlocal(0).__getattr__("crc")._and(Py.newInteger(65535));
      var1.getlocal(0).__setattr__("crc", var3);
      var3 = null;
      var1.setline(405);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("crc"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(406);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("CRC error, computed %x, read %x")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("crc"), var1.getlocal(1)})));
      } else {
         var1.setline(408);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"crc", var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _readheader$44(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyObject var3 = var1.getlocal(0).__getattr__("_read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(412);
      var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(413);
      var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, Py.newInteger(1)._add(Py.newInteger(4))._add(Py.newInteger(4))._add(Py.newInteger(2))._add(Py.newInteger(4))._add(Py.newInteger(4)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(414);
      var1.getlocal(0).__getattr__("_checkcrc").__call__(var2);
      var1.setline(416);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), Py.newInteger(5), (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(417);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(5), Py.newInteger(9), (PyObject)null);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(418);
      var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">h"), (PyObject)var1.getlocal(3).__getslice__(Py.newInteger(9), Py.newInteger(11), (PyObject)null)).__getitem__(Py.newInteger(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(419);
      var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(3).__getslice__(Py.newInteger(11), Py.newInteger(15), (PyObject)null)).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("dlen", var3);
      var3 = null;
      var1.setline(420);
      var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(3).__getslice__(Py.newInteger(15), Py.newInteger(19), (PyObject)null)).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("rlen", var3);
      var3 = null;
      var1.setline(422);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("FName", var3);
      var3 = null;
      var1.setline(423);
      var3 = var1.getglobal("FInfo").__call__(var2);
      var1.getlocal(0).__setattr__("FInfo", var3);
      var3 = null;
      var1.setline(424);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__getattr__("FInfo").__setattr__("Creator", var3);
      var3 = null;
      var1.setline(425);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__getattr__("FInfo").__setattr__("Type", var3);
      var3 = null;
      var1.setline(426);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__getattr__("FInfo").__setattr__("Flags", var3);
      var3 = null;
      var1.setline(428);
      var3 = var1.getglobal("_DID_HEADER");
      var1.getlocal(0).__setattr__("state", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$45(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._ne(var1.getglobal("_DID_HEADER"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(432);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Read data at wrong time"));
      } else {
         var1.setline(433);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(434);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(435);
            var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("dlen"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(437);
            var3 = var1.getlocal(0).__getattr__("dlen");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(438);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var3 = null;

         while(true) {
            var1.setline(439);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._lt(var1.getlocal(1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(441);
               var3 = var1.getlocal(0).__getattr__("dlen")._sub(var1.getlocal(1));
               var1.getlocal(0).__setattr__("dlen", var3);
               var3 = null;
               var1.setline(442);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(440);
            var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)))));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }
   }

   public PyObject close_data$46(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._ne(var1.getglobal("_DID_HEADER"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(446);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("close_data at wrong time"));
      } else {
         var1.setline(447);
         if (var1.getlocal(0).__getattr__("dlen").__nonzero__()) {
            var1.setline(448);
            var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(0).__getattr__("dlen"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(449);
         var1.getlocal(0).__getattr__("_checkcrc").__call__(var2);
         var1.setline(450);
         var3 = var1.getglobal("_DID_DATA");
         var1.getlocal(0).__setattr__("state", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject read_rsrc$47(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._eq(var1.getglobal("_DID_HEADER"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(454);
         var1.getlocal(0).__getattr__("close_data").__call__(var2);
      }

      var1.setline(455);
      var3 = var1.getlocal(0).__getattr__("state");
      var10000 = var3._ne(var1.getglobal("_DID_DATA"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(456);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Read resource data at wrong time"));
      } else {
         var1.setline(457);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(458);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(459);
            var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("rlen"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(461);
            var3 = var1.getlocal(0).__getattr__("rlen");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(462);
         var3 = var1.getlocal(0).__getattr__("rlen")._sub(var1.getlocal(1));
         var1.getlocal(0).__setattr__("rlen", var3);
         var3 = null;
         var1.setline(463);
         var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject close$48(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("rlen").__nonzero__()) {
         var1.setline(467);
         var3 = var1.getlocal(0).__getattr__("read_rsrc").__call__(var2, var1.getlocal(0).__getattr__("rlen"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(468);
      var1.getlocal(0).__getattr__("_checkcrc").__call__(var2);
      var1.setline(469);
      var3 = var1.getglobal("_DID_RSRC");
      var1.getlocal(0).__setattr__("state", var3);
      var3 = null;
      var1.setline(470);
      var1.getlocal(0).__getattr__("ifp").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject hexbin$49(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyString.fromInterned("(infilename, outfilename) - Decode binhexed file");
      var1.setline(474);
      PyObject var3 = var1.getglobal("HexBin").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getlocal(2).__getattr__("FInfo");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(476);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(477);
         var3 = var1.getlocal(2).__getattr__("FName");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(479);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(481);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(482);
         var3 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(128000));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(483);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(484);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5));
      }

      var1.setline(485);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(486);
      var1.getlocal(2).__getattr__("close_data").__call__(var2);
      var1.setline(488);
      var3 = var1.getlocal(2).__getattr__("read_rsrc").__call__((ThreadState)var2, (PyObject)Py.newInteger(128000));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(489);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(490);
         var3 = var1.getglobal("openrsrc").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(491);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5));

         while(true) {
            var1.setline(492);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(493);
            var3 = var1.getlocal(2).__getattr__("read_rsrc").__call__((ThreadState)var2, (PyObject)Py.newInteger(128000));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(494);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               break;
            }

            var1.setline(495);
            var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5));
         }

         var1.setline(496);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      }

      var1.setline(498);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _test$50(PyFrame var1, ThreadState var2) {
      var1.setline(501);
      PyObject var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(502);
      var1.getglobal("binhex").__call__(var2, var1.getlocal(0), var1.getlocal(0)._add(PyString.fromInterned(".hqx")));
      var1.setline(503);
      var1.getglobal("hexbin").__call__(var2, var1.getlocal(0)._add(PyString.fromInterned(".hqx")), var1.getlocal(0)._add(PyString.fromInterned(".viahqx")));
      var1.setline(505);
      var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public binhex$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 31, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "finfo", "dir", "file", "fp", "dlen", "rlen"};
      getfileinfo$2 = Py.newCode(1, var2, var1, "getfileinfo", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "mode"};
      openrsrc$3 = Py.newCode(2, var2, var1, "openrsrc", 63, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FInfo$4 = Py.newCode(0, var2, var1, "FInfo", 75, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$5 = Py.newCode(1, var2, var1, "__init__", 76, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "finfo", "fp", "data", "c", "dsize", "dir", "file"};
      getfileinfo$6 = Py.newCode(1, var2, var1, "getfileinfo", 81, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      openrsrc$7 = Py.newCode(0, var2, var1, "openrsrc", 98, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$8 = Py.newCode(2, var2, var1, "__init__", 99, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      read$9 = Py.newCode(2, var2, var1, "read", 102, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      write$10 = Py.newCode(2, var2, var1, "write", 105, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$11 = Py.newCode(1, var2, var1, "close", 108, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Hqxcoderengine$12 = Py.newCode(0, var2, var1, "_Hqxcoderengine", 111, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ofp"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 114, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "datalen", "todo"};
      write$14 = Py.newCode(2, var2, var1, "write", 120, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "force", "first", "last"};
      _flush$15 = Py.newCode(2, var2, var1, "_flush", 131, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$16 = Py.newCode(1, var2, var1, "close", 142, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Rlecoderengine$17 = Py.newCode(0, var2, var1, "_Rlecoderengine", 150, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ofp"};
      __init__$18 = Py.newCode(2, var2, var1, "__init__", 153, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "rledata"};
      write$19 = Py.newCode(2, var2, var1, "write", 157, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rledata"};
      close$20 = Py.newCode(1, var2, var1, "close", 165, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BinHex$21 = Py.newCode(0, var2, var1, "BinHex", 172, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name_finfo_dlen_rlen", "ofp", "name", "finfo", "dlen", "rlen", "ofname", "hqxer"};
      __init__$22 = Py.newCode(3, var2, var1, "__init__", 173, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "finfo", "nl", "d", "d2", "d3", "d4", "info"};
      _writeinfo$23 = Py.newCode(3, var2, var1, "_writeinfo", 189, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _write$24 = Py.newCode(2, var2, var1, "_write", 203, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fmt"};
      _writecrc$25 = Py.newCode(1, var2, var1, "_writecrc", 207, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write$26 = Py.newCode(2, var2, var1, "write", 217, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close_data$27 = Py.newCode(1, var2, var1, "close_data", 223, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write_rsrc$28 = Py.newCode(2, var2, var1, "write_rsrc", 229, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$29 = Py.newCode(1, var2, var1, "close", 237, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"inp", "out", "finfo", "ofp", "ifp", "d"};
      binhex$30 = Py.newCode(2, var2, var1, "binhex", 250, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Hqxdecoderengine$31 = Py.newCode(0, var2, var1, "_Hqxdecoderengine", 272, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ifp"};
      __init__$32 = Py.newCode(2, var2, var1, "__init__", 275, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "totalwtd", "decdata", "wtd", "data", "decdatacur", "newdata"};
      read$33 = Py.newCode(2, var2, var1, "read", 279, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$34 = Py.newCode(1, var2, var1, "close", 313, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Rledecoderengine$35 = Py.newCode(0, var2, var1, "_Rledecoderengine", 316, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ifp"};
      __init__$36 = Py.newCode(2, var2, var1, "__init__", 319, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "wtd", "rv"};
      read$37 = Py.newCode(2, var2, var1, "read", 325, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "wtd", "mark"};
      _fill$38 = Py.newCode(2, var2, var1, "_fill", 332, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$39 = Py.newCode(1, var2, var1, "close", 367, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HexBin$40 = Py.newCode(0, var2, var1, "HexBin", 370, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ifp", "ch", "dummy", "hqxifp"};
      __init__$41 = Py.newCode(2, var2, var1, "__init__", 371, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len", "data"};
      _read$42 = Py.newCode(2, var2, var1, "_read", 395, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filecrc"};
      _checkcrc$43 = Py.newCode(1, var2, var1, "_checkcrc", 400, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len", "fname", "rest", "type", "creator", "flags"};
      _readheader$44 = Py.newCode(1, var2, var1, "_readheader", 410, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "rv"};
      read$45 = Py.newCode(2, var2, var1, "read", 430, true, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dummy"};
      close_data$46 = Py.newCode(1, var2, var1, "close_data", 444, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      read_rsrc$47 = Py.newCode(2, var2, var1, "read_rsrc", 452, true, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dummy"};
      close$48 = Py.newCode(1, var2, var1, "close", 465, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"inp", "out", "ifp", "finfo", "ofp", "d"};
      hexbin$49 = Py.newCode(2, var2, var1, "hexbin", 472, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fname"};
      _test$50 = Py.newCode(0, var2, var1, "_test", 500, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new binhex$py("binhex$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(binhex$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.getfileinfo$2(var2, var3);
         case 3:
            return this.openrsrc$3(var2, var3);
         case 4:
            return this.FInfo$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.getfileinfo$6(var2, var3);
         case 7:
            return this.openrsrc$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.read$9(var2, var3);
         case 10:
            return this.write$10(var2, var3);
         case 11:
            return this.close$11(var2, var3);
         case 12:
            return this._Hqxcoderengine$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.write$14(var2, var3);
         case 15:
            return this._flush$15(var2, var3);
         case 16:
            return this.close$16(var2, var3);
         case 17:
            return this._Rlecoderengine$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.write$19(var2, var3);
         case 20:
            return this.close$20(var2, var3);
         case 21:
            return this.BinHex$21(var2, var3);
         case 22:
            return this.__init__$22(var2, var3);
         case 23:
            return this._writeinfo$23(var2, var3);
         case 24:
            return this._write$24(var2, var3);
         case 25:
            return this._writecrc$25(var2, var3);
         case 26:
            return this.write$26(var2, var3);
         case 27:
            return this.close_data$27(var2, var3);
         case 28:
            return this.write_rsrc$28(var2, var3);
         case 29:
            return this.close$29(var2, var3);
         case 30:
            return this.binhex$30(var2, var3);
         case 31:
            return this._Hqxdecoderengine$31(var2, var3);
         case 32:
            return this.__init__$32(var2, var3);
         case 33:
            return this.read$33(var2, var3);
         case 34:
            return this.close$34(var2, var3);
         case 35:
            return this._Rledecoderengine$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         case 37:
            return this.read$37(var2, var3);
         case 38:
            return this._fill$38(var2, var3);
         case 39:
            return this.close$39(var2, var3);
         case 40:
            return this.HexBin$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this._read$42(var2, var3);
         case 43:
            return this._checkcrc$43(var2, var3);
         case 44:
            return this._readheader$44(var2, var3);
         case 45:
            return this.read$45(var2, var3);
         case 46:
            return this.close_data$46(var2, var3);
         case 47:
            return this.read_rsrc$47(var2, var3);
         case 48:
            return this.close$48(var2, var3);
         case 49:
            return this.hexbin$49(var2, var3);
         case 50:
            return this._test$50(var2, var3);
         default:
            return null;
      }
   }
}
