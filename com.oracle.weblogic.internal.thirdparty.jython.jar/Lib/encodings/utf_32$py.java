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
@Filename("encodings/utf_32.py")
public class utf_32$py extends PyFunctionTable implements PyRunnable {
   static utf_32$py self;
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
   static final PyCode getstate$12;
   static final PyCode setstate$13;
   static final PyCode StreamWriter$14;
   static final PyCode __init__$15;
   static final PyCode reset$16;
   static final PyCode encode$17;
   static final PyCode StreamReader$18;
   static final PyCode reset$19;
   static final PyCode decode$20;
   static final PyCode getregentry$21;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nPython 'utf-32' Codec\n"));
      var1.setline(3);
      PyString.fromInterned("\nPython 'utf-32' Codec\n");
      var1.setline(4);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      var3 = var1.getname("codecs").__getattr__("utf_32_encode");
      var1.setlocal("encode", var3);
      var3 = null;
      var1.setline(10);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, decode$1, (PyObject)null);
      var1.setlocal("decode", var6);
      var3 = null;
      var1.setline(13);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      PyObject var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$2);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(48);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("BufferedIncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$8);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(99);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$14);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(119);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$18);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(141);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getregentry$21, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$1(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_32_decode").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(18);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$4, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$5, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$6, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$7, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(16);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$4(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(20);
         var3 = var1.getglobal("codecs").__getattr__("utf_32_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(21);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(22);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(24);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }

         var1.setline(25);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(26);
         var3 = var1.getlocal(0).__getattr__("encoder").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors")).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$5(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      var1.getglobal("codecs").__getattr__("IncrementalEncoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(30);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$6(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.setline(37);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var4 = var10000.__nonzero__() ? Py.newInteger(2) : Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject setstate$7(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(41);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("encoder", var3);
         var3 = null;
      } else {
         var1.setline(43);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         PyObject var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(44);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(46);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IncrementalDecoder$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(49);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _buffer_decode$10, (PyObject)null);
      var1.setlocal("_buffer_decode", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$11, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$12, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$13, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(51);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("decoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _buffer_decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var3 = var1.getglobal("codecs").__getattr__("utf_32_ex_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3));
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
         var1.setline(57);
         var3 = var1.getlocal(6);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(58);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_le_decode");
            var1.getlocal(0).__setattr__("decoder", var3);
            var3 = null;
         } else {
            var1.setline(59);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(60);
               var3 = var1.getglobal("codecs").__getattr__("utf_32_be_decode");
               var1.getlocal(0).__setattr__("decoder", var3);
               var3 = null;
            } else {
               var1.setline(61);
               var3 = var1.getlocal(5);
               var10000 = var3._ge(Py.newInteger(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(62);
                  throw Py.makeException(var1.getglobal("UnicodeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UTF-32 stream does not start with BOM")));
               }
            }
         }

         var1.setline(63);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(64);
         var3 = var1.getlocal(0).__getattr__("decoder").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$11(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(68);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("decoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$12(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("getstate").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(0).__getattr__("decoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(79);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(2)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(80);
         var10000 = var1.getglobal("int");
         PyObject var6 = var1.getglobal("sys").__getattr__("byteorder");
         PyObject var10002 = var6._eq(PyString.fromInterned("big"));
         var6 = null;
         PyObject var4 = var10002;
         var6 = var1.getlocal(0).__getattr__("decoder");
         var10002 = var6._is(var1.getglobal("codecs").__getattr__("utf_32_be_decode"));
         var6 = null;
         var10002 = var4._ne(var10002);
         var4 = null;
         var4 = var10000.__call__(var2, var10002);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(82);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject setstate$13(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      var1.getglobal("codecs").__getattr__("BufferedIncrementalDecoder").__getattr__("setstate").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(87);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(89);
         var1.setline(90);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         var10000 = var3._eq(PyString.fromInterned("big"));
         var3 = null;
         var3 = var10000.__nonzero__() ? var1.getglobal("codecs").__getattr__("utf_32_be_decode") : var1.getglobal("codecs").__getattr__("utf_32_le_decode");
         var1.getlocal(0).__setattr__("decoder", var3);
         var3 = null;
      } else {
         var1.setline(92);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(93);
            var1.setline(94);
            var3 = var1.getglobal("sys").__getattr__("byteorder");
            var10000 = var3._eq(PyString.fromInterned("big"));
            var3 = null;
            var3 = var10000.__nonzero__() ? var1.getglobal("codecs").__getattr__("utf_32_le_decode") : var1.getglobal("codecs").__getattr__("utf_32_be_decode");
            var1.getlocal(0).__setattr__("decoder", var3);
            var3 = null;
         } else {
            var1.setline(97);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("decoder", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(100);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$16, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(108);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, encode$17, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.setline(102);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$16(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(106);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("encoder", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$17(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getlocal(0).__getattr__("encoder");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(110);
         var3 = var1.getglobal("codecs").__getattr__("utf_32_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(111);
         var3 = var1.getglobal("sys").__getattr__("byteorder");
         var10000 = var3._eq(PyString.fromInterned("little"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(112);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_le_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         } else {
            var1.setline(114);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_be_encode");
            var1.getlocal(0).__setattr__("encoder", var3);
            var3 = null;
         }

         var1.setline(115);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(117);
         var3 = var1.getlocal(0).__getattr__("encoder").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject StreamReader$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(121);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, reset$19, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(128);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$20, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject reset$19(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      var1.getglobal("codecs").__getattr__("StreamReader").__getattr__("reset").__call__(var2, var1.getlocal(0));

      try {
         var1.setline(124);
         var1.getlocal(0).__delattr__("decode");
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(126);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$20(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getglobal("codecs").__getattr__("utf_32_ex_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getglobal("False"));
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
      var1.setline(131);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         var3 = var1.getglobal("codecs").__getattr__("utf_32_le_decode");
         var1.getlocal(0).__setattr__("decode", var3);
         var3 = null;
      } else {
         var1.setline(133);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(134);
            var3 = var1.getglobal("codecs").__getattr__("utf_32_be_decode");
            var1.getlocal(0).__setattr__("decode", var3);
            var3 = null;
         } else {
            var1.setline(135);
            var3 = var1.getlocal(4);
            var10000 = var3._ge(Py.newInteger(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(136);
               throw Py.makeException(var1.getglobal("UnicodeError"), PyString.fromInterned("UTF-32 stream does not start with BOM"));
            }
         }
      }

      var1.setline(137);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getregentry$21(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("utf-32"), var1.getglobal("encode"), var1.getglobal("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public utf_32$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"input", "errors"};
      decode$1 = Py.newCode(2, var2, var1, "decode", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$2 = Py.newCode(0, var2, var1, "IncrementalEncoder", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 14, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "result"};
      encode$4 = Py.newCode(3, var2, var1, "encode", 18, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$5 = Py.newCode(1, var2, var1, "reset", 28, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$6 = Py.newCode(1, var2, var1, "getstate", 32, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$7 = Py.newCode(2, var2, var1, "setstate", 39, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$8 = Py.newCode(0, var2, var1, "IncrementalDecoder", 48, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 49, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final", "output", "consumed", "byteorder"};
      _buffer_decode$10 = Py.newCode(4, var2, var1, "_buffer_decode", 53, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$11 = Py.newCode(1, var2, var1, "reset", 66, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "addstate"};
      getstate$12 = Py.newCode(1, var2, var1, "getstate", 70, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$13 = Py.newCode(2, var2, var1, "setstate", 84, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$14 = Py.newCode(0, var2, var1, "StreamWriter", 99, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors"};
      __init__$15 = Py.newCode(3, var2, var1, "__init__", 100, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$16 = Py.newCode(1, var2, var1, "reset", 104, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "result"};
      encode$17 = Py.newCode(3, var2, var1, "encode", 108, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$18 = Py.newCode(0, var2, var1, "StreamReader", 119, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      reset$19 = Py.newCode(1, var2, var1, "reset", 121, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "object", "consumed", "byteorder"};
      decode$20 = Py.newCode(3, var2, var1, "decode", 128, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getregentry$21 = Py.newCode(0, var2, var1, "getregentry", 141, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new utf_32$py("encodings/utf_32$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(utf_32$py.class);
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
            return this.getstate$12(var2, var3);
         case 13:
            return this.setstate$13(var2, var3);
         case 14:
            return this.StreamWriter$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this.reset$16(var2, var3);
         case 17:
            return this.encode$17(var2, var3);
         case 18:
            return this.StreamReader$18(var2, var3);
         case 19:
            return this.reset$19(var2, var3);
         case 20:
            return this.decode$20(var2, var3);
         case 21:
            return this.getregentry$21(var2, var3);
         default:
            return null;
      }
   }
}
