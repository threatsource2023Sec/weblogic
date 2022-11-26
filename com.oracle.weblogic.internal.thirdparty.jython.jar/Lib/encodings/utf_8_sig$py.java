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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/utf_8_sig.py")
public class utf_8_sig$py extends PyFunctionTable implements PyRunnable {
   static utf_8_sig$py self;
   static final PyCode f$0;
   static final PyCode encode$1;
   static final PyCode decode$2;
   static final PyCode IncrementalEncoder$3;
   static final PyCode __init__$4;
   static final PyCode encode$5;
   static final PyCode reset$6;
   static final PyCode getstate$7;
   static final PyCode setstate$8;
   static final PyCode IncrementalDecoder$9;
   static final PyCode __init__$10;
   static final PyCode _buffer_decode$11;
   static final PyCode reset$12;
   static final PyCode StreamWriter$13;
   static final PyCode reset$14;
   static final PyCode encode$15;
   static final PyCode StreamReader$16;
   static final PyCode reset$17;
   static final PyCode decode$18;
   static final PyCode getregentry$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python 'utf-8-sig' Codec\nThis work similar to UTF-8 with the following changes:\n\n* On encoding/writing a UTF-8 encoded BOM will be prepended/written as the\n  first three bytes.\n\n* On decoding/reading if the first three bytes are a UTF-8 encoded BOM, these\n  bytes will be skipped.\n"));
      var1.setline(9);
      PyString.fromInterned(" Python 'utf-8-sig' Codec\nThis work similar to UTF-8 with the following changes:\n\n* On encoding/writing a UTF-8 encoded BOM will be prepended/written as the\n  first three bytes.\n\n* On decoding/reading if the first three bytes are a UTF-8 encoded BOM, these\n  bytes will be skipped.\n");
      var1.setline(10);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(14);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, encode$1, (PyObject)null);
      var1.setlocal("encode", var6);
      var3 = null;
      var1.setline(17);
      var5 = new PyObject[]{PyString.fromInterned("strict")};
      var6 = new PyFunction(var1.f_globals, var5, decode$2, (PyObject)null);
      var1.setlocal("decode", var6);
      var3 = null;
      var1.setline(25);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      PyObject var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$3);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(47);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$9);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(72);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$13);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(84);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$16);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(108);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$19, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$1(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("codecs").__getattr__("BOM_UTF8")._add(var1.getglobal("codecs").__getattr__("utf_8_encode").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__getitem__(Py.newInteger(0))), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(19);
      PyObject var6 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var6._eq(var1.getglobal("codecs").__getattr__("BOM_UTF8"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         var6 = var1.getlocal(0).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var6);
         var3 = null;
         var1.setline(21);
         var3 = Py.newInteger(3);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(22);
      var6 = var1.getglobal("codecs").__getattr__("utf_8_decode").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(23);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)._add(var1.getlocal(2))});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject IncrementalEncoder$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(26);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(30);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$6, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$7, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$8, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(28);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"first", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("first").__nonzero__()) {
         var1.setline(32);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"first", var4);
         var3 = null;
         var1.setline(33);
         var3 = var1.getglobal("codecs").__getattr__("BOM_UTF8")._add(var1.getglobal("codecs").__getattr__("utf_8_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(35);
         var3 = var1.getglobal("codecs").__getattr__("utf_8_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$6(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(39);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"first", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$7(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getattr__("first");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$8(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("first", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IncrementalDecoder$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(48);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _buffer_decode$11, (PyObject)null);
      var1.setlocal("_buffer_decode", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$12, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(50);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("first", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _buffer_decode$11(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("first").__nonzero__()) {
         var1.setline(54);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._lt(Py.newInteger(3));
         var3 = null;
         PyObject var4;
         PyTuple var7;
         if (var10000.__nonzero__()) {
            var1.setline(55);
            if (var1.getglobal("codecs").__getattr__("BOM_UTF8").__getattr__("startswith").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(58);
               var7 = new PyTuple(new PyObject[]{PyUnicode.fromInterned(""), Py.newInteger(0)});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(60);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("first", var4);
            var4 = null;
         } else {
            var1.setline(62);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("first", var4);
            var4 = null;
            var1.setline(63);
            var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
            var10000 = var4._eq(var1.getglobal("codecs").__getattr__("BOM_UTF8"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(64);
               var4 = var1.getglobal("codecs").__getattr__("utf_8_decode").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null), var1.getlocal(2), var1.getlocal(3));
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(65);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)._add(Py.newInteger(3))});
               var1.f_lasti = -1;
               return var7;
            }
         }
      }

      var1.setline(66);
      var3 = var1.getglobal("codecs").__getattr__("utf_8_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$12(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(70);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("first", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(73);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$14, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(80);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, encode$15, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$14(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("reset").__call__(var2, var1.getlocal(0));

      try {
         var1.setline(76);
         var1.getlocal(0).__delattr__("encode");
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(78);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$15(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_8_encode");
      var1.getlocal(0).__setattr__("encode", var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getglobal("encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamReader$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(85);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$17, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(92);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$18, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$17(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      var1.getglobal("codecs").__getattr__("StreamReader").__getattr__("reset").__call__(var2, var1.getlocal(0));

      try {
         var1.setline(88);
         var1.getlocal(0).__delattr__("decode");
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(90);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$18(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._lt(Py.newInteger(3));
      var3 = null;
      PyObject var4;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         if (var1.getglobal("codecs").__getattr__("BOM_UTF8").__getattr__("startswith").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(97);
            var7 = new PyTuple(new PyObject[]{PyUnicode.fromInterned(""), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var7;
         }
      } else {
         var1.setline(98);
         var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var4._eq(var1.getglobal("codecs").__getattr__("BOM_UTF8"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(99);
            var4 = var1.getglobal("codecs").__getattr__("utf_8_decode");
            var1.getlocal(0).__setattr__("decode", var4);
            var4 = null;
            var1.setline(100);
            var4 = var1.getglobal("codecs").__getattr__("utf_8_decode").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null), var1.getlocal(2));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(101);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)._add(Py.newInteger(3))});
            var1.f_lasti = -1;
            return var7;
         }
      }

      var1.setline(103);
      var4 = var1.getglobal("codecs").__getattr__("utf_8_decode");
      var1.getlocal(0).__setattr__("decode", var4);
      var4 = null;
      var1.setline(104);
      var3 = var1.getglobal("codecs").__getattr__("utf_8_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getregentry$19(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("utf-8-sig"), var1.getglobal("encode"), var1.getglobal("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public utf_8_sig$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors"};
      encode$1 = Py.newCode(2, var2, var1, "encode", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input", "errors", "prefix", "output", "consumed"};
      decode$2 = Py.newCode(2, var2, var1, "decode", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$3 = Py.newCode(0, var2, var1, "IncrementalEncoder", 25, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 30, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$6 = Py.newCode(1, var2, var1, "reset", 37, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$7 = Py.newCode(1, var2, var1, "getstate", 41, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$8 = Py.newCode(2, var2, var1, "setstate", 44, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$9 = Py.newCode(0, var2, var1, "IncrementalDecoder", 47, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 48, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final", "output", "consumed"};
      _buffer_decode$11 = Py.newCode(4, var2, var1, "_buffer_decode", 52, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$12 = Py.newCode(1, var2, var1, "reset", 68, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$13 = Py.newCode(0, var2, var1, "StreamWriter", 72, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$14 = Py.newCode(1, var2, var1, "reset", 73, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      encode$15 = Py.newCode(3, var2, var1, "encode", 80, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$16 = Py.newCode(0, var2, var1, "StreamReader", 84, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$17 = Py.newCode(1, var2, var1, "reset", 85, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "output", "consumed"};
      decode$18 = Py.newCode(3, var2, var1, "decode", 92, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getregentry$19 = Py.newCode(0, var2, var1, "getregentry", 108, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new utf_8_sig$py("encodings/utf_8_sig$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(utf_8_sig$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.encode$1(var2, var3);
         case 2:
            return this.decode$2(var2, var3);
         case 3:
            return this.IncrementalEncoder$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.reset$6(var2, var3);
         case 7:
            return this.getstate$7(var2, var3);
         case 8:
            return this.setstate$8(var2, var3);
         case 9:
            return this.IncrementalDecoder$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this._buffer_decode$11(var2, var3);
         case 12:
            return this.reset$12(var2, var3);
         case 13:
            return this.StreamWriter$13(var2, var3);
         case 14:
            return this.reset$14(var2, var3);
         case 15:
            return this.encode$15(var2, var3);
         case 16:
            return this.StreamReader$16(var2, var3);
         case 17:
            return this.reset$17(var2, var3);
         case 18:
            return this.decode$18(var2, var3);
         case 19:
            return this.getregentry$19(var2, var3);
         default:
            return null;
      }
   }
}
