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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/utf_16.py")
public class utf_16$py extends PyFunctionTable implements PyRunnable {
   static utf_16$py self;
   static final PyCode f$0;
   static final PyCode decode$1;
   static final PyCode IncrementalEncoder$2;
   static final PyCode __init__$3;
   static final PyCode encode$4;
   static final PyCode reset$5;
   static final PyCode getstate$6;
   static final PyCode setstate$7;
   static final PyCode IncrementalDecoder$8;
   static final PyCode __init__$9;
   static final PyCode _buffer_decode$10;
   static final PyCode reset$11;
   static final PyCode StreamWriter$12;
   static final PyCode __init__$13;
   static final PyCode reset$14;
   static final PyCode encode$15;
   static final PyCode StreamReader$16;
   static final PyCode reset$17;
   static final PyCode decode$18;
   static final PyCode getregentry$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python 'utf-16' Codec\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n"));
      var1.setline(8);
      PyString.fromInterned(" Python 'utf-16' Codec\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n");
      var1.setline(9);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getname("codecs").__getattr__("utf_16_encode");
      var1.setlocal("encode", var3);
      var3 = null;
      var1.setline(15);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, decode$1, (PyObject)null);
      var1.setlocal("decode", var6);
      var3 = null;
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      PyObject var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$2);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(53);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$8);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(75);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$12);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(95);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$16);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(117);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$19, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$1(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_16_decode").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$5, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$6, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$7, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(21);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(25);
         var3 = var1.getglobal("codecs").__getattr__("utf_16_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(26);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(27);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(29);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }

         var1.setline(30);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(31);
         var3 = var1.getlocal(0).__getattr__("encoder").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$5(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(35);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$6(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var4 = var10000.__nonzero__() ? Py.newInteger(2) : Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject setstate$7(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(46);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("encoder", var3);
         var3 = null;
      } else {
         var1.setline(48);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         PyObject var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(49);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(51);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IncrementalDecoder$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(54);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _buffer_decode$10, (PyObject)null);
      var1.setlocal("_buffer_decode", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$11, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(56);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("decoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _buffer_decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         var3 = var1.getglobal("codecs").__getattr__("utf_16_ex_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(62);
         var3 = var1.getlocal(6);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(63);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_le_decode");
            var1.getlocal(0).__setattr__("decoder", var3);
            var3 = null;
         } else {
            var1.setline(64);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(65);
               var3 = var1.getglobal("codecs").__getattr__("utf_16_be_decode");
               var1.getlocal(0).__setattr__("decoder", var3);
               var3 = null;
            } else {
               var1.setline(66);
               var3 = var1.getlocal(5);
               var10000 = var3._ge(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(67);
                  throw Py.makeException(var1.getglobal("UnicodeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UTF-16 stream does not start with BOM")));
               }
            }
         }

         var1.setline(68);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(69);
         var3 = var1.getlocal(0).__getattr__("decoder").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$11(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(73);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("decoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$14, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(84);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, encode$15, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(78);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$14(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(82);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$15(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var3 = var1.getglobal("codecs").__getattr__("utf_16_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(87);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(88);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(90);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }

         var1.setline(91);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(93);
         var3 = var1.getlocal(0).__getattr__("encoder").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject StreamReader$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(97);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$17, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(104);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$18, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$17(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      var1.getglobal("codecs").__getattr__("StreamReader").__getattr__("reset").__call__(var2, var1.getlocal(0));

      try {
         var1.setline(100);
         var1.getlocal(0).__delattr__("decode");
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(102);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$18(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_16_ex_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getglobal("False"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(107);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(108);
         var3 = var1.getglobal("codecs").__getattr__("utf_16_le_decode");
         var1.getlocal(0).__setattr__("decode", var3);
         var3 = null;
      } else {
         var1.setline(109);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(110);
            var3 = var1.getglobal("codecs").__getattr__("utf_16_be_decode");
            var1.getlocal(0).__setattr__("decode", var3);
            var3 = null;
         } else {
            var1.setline(111);
            var3 = var1.getlocal(4);
            var10000 = var3._ge(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(112);
               throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("UTF-16 stream does not start with BOM"));
            }
         }
      }

      var1.setline(113);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getregentry$19(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("utf-16"), var1.getglobal("encode"), var1.getglobal("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public utf_16$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors"};
      decode$1 = Py.newCode(2, var2, var1, "decode", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$2 = Py.newCode(0, var2, var1, "IncrementalEncoder", 18, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "result"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 23, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$5 = Py.newCode(1, var2, var1, "reset", 33, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$6 = Py.newCode(1, var2, var1, "getstate", 37, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$7 = Py.newCode(2, var2, var1, "setstate", 44, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$8 = Py.newCode(0, var2, var1, "IncrementalDecoder", 53, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final", "output", "consumed", "byteorder"};
      _buffer_decode$10 = Py.newCode(4, var2, var1, "_buffer_decode", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$11 = Py.newCode(1, var2, var1, "reset", 71, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$12 = Py.newCode(0, var2, var1, "StreamWriter", 75, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors"};
      __init__$13 = Py.newCode(3, var2, var1, "__init__", 76, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$14 = Py.newCode(1, var2, var1, "reset", 80, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "result"};
      encode$15 = Py.newCode(3, var2, var1, "encode", 84, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$16 = Py.newCode(0, var2, var1, "StreamReader", 95, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$17 = Py.newCode(1, var2, var1, "reset", 97, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "object", "consumed", "byteorder"};
      decode$18 = Py.newCode(3, var2, var1, "decode", 104, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getregentry$19 = Py.newCode(0, var2, var1, "getregentry", 117, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new utf_16$py("encodings/utf_16$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(utf_16$py.class);
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
            return this.__init__$3(var2, var3);
         case 4:
            return this.encode$4(var2, var3);
         case 5:
            return this.reset$5(var2, var3);
         case 6:
            return this.getstate$6(var2, var3);
         case 7:
            return this.setstate$7(var2, var3);
         case 8:
            return this.IncrementalDecoder$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this._buffer_decode$10(var2, var3);
         case 11:
            return this.reset$11(var2, var3);
         case 12:
            return this.StreamWriter$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
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
