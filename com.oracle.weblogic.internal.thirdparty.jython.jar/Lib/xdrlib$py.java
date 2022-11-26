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
@Filename("xdrlib.py")
public class xdrlib$py extends PyFunctionTable implements PyRunnable {
   static xdrlib$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode __str__$4;
   static final PyCode ConversionError$5;
   static final PyCode Packer$6;
   static final PyCode __init__$7;
   static final PyCode reset$8;
   static final PyCode get_buffer$9;
   static final PyCode pack_uint$10;
   static final PyCode pack_int$11;
   static final PyCode pack_bool$12;
   static final PyCode pack_uhyper$13;
   static final PyCode pack_float$14;
   static final PyCode pack_double$15;
   static final PyCode pack_fstring$16;
   static final PyCode pack_string$17;
   static final PyCode pack_list$18;
   static final PyCode pack_farray$19;
   static final PyCode pack_array$20;
   static final PyCode Unpacker$21;
   static final PyCode __init__$22;
   static final PyCode reset$23;
   static final PyCode get_position$24;
   static final PyCode set_position$25;
   static final PyCode get_buffer$26;
   static final PyCode done$27;
   static final PyCode unpack_uint$28;
   static final PyCode unpack_int$29;
   static final PyCode unpack_bool$30;
   static final PyCode unpack_uhyper$31;
   static final PyCode unpack_hyper$32;
   static final PyCode unpack_float$33;
   static final PyCode unpack_double$34;
   static final PyCode unpack_fstring$35;
   static final PyCode unpack_string$36;
   static final PyCode unpack_list$37;
   static final PyCode unpack_farray$38;
   static final PyCode unpack_array$39;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Implements (a subset of) Sun XDR -- eXternal Data Representation.\n\nSee: RFC 1014\n\n"));
      var1.setline(5);
      PyString.fromInterned("Implements (a subset of) Sun XDR -- eXternal Data Representation.\n\nSee: RFC 1014\n\n");
      var1.setline(7);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;

      String[] var4;
      PyObject[] var9;
      PyObject var11;
      try {
         var1.setline(9);
         String[] var8 = new String[]{"StringIO"};
         var9 = imp.importFrom("cStringIO", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("_StringIO", var11);
         var4 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(11);
         var4 = new String[]{"StringIO"};
         PyObject[] var10 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("_StringIO", var5);
         var5 = null;
      }

      var1.setline(13);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("Error"), PyString.fromInterned("Packer"), PyString.fromInterned("Unpacker"), PyString.fromInterned("ConversionError")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(16);
      var9 = new PyObject[]{var1.getname("Exception")};
      var11 = Py.makeClass("Error", var9, Error$1);
      var1.setlocal("Error", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(34);
      var9 = new PyObject[]{var1.getname("Error")};
      var11 = Py.makeClass("ConversionError", var9, ConversionError$5);
      var1.setlocal("ConversionError", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(39);
      var9 = Py.EmptyObjects;
      var11 = Py.makeClass("Packer", var9, Packer$6);
      var1.setlocal("Packer", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(118);
      var9 = Py.EmptyObjects;
      var11 = Py.makeClass("Unpacker", var9, Unpacker$21);
      var1.setlocal("Unpacker", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception class for this module. Use:\n\n    except xdrlib.Error, var:\n        # var has the Error instance for the exception\n\n    Public ivars:\n        msg -- contains the message\n\n    "));
      var1.setline(25);
      PyString.fromInterned("Exception class for this module. Use:\n\n    except xdrlib.Error, var:\n        # var has the Error instance for the exception\n\n    Public ivars:\n        msg -- contains the message\n\n    ");
      var1.setline(26);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$4, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$4(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("msg"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ConversionError$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(35);
      return var1.getf_locals();
   }

   public PyObject Packer$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Pack various data representations into a buffer."));
      var1.setline(40);
      PyString.fromInterned("Pack various data representations into a buffer.");
      var1.setline(42);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$8, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_buffer$9, (PyObject)null);
      var1.setlocal("get_buffer", var4);
      var3 = null;
      var1.setline(51);
      PyObject var5 = var1.getname("get_buffer");
      var1.setlocal("get_buf", var5);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_uint$10, (PyObject)null);
      var1.setlocal("pack_uint", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_int$11, (PyObject)null);
      var1.setlocal("pack_int", var4);
      var3 = null;
      var1.setline(59);
      var5 = var1.getname("pack_int");
      var1.setlocal("pack_enum", var5);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_bool$12, (PyObject)null);
      var1.setlocal("pack_bool", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_uhyper$13, (PyObject)null);
      var1.setlocal("pack_uhyper", var4);
      var3 = null;
      var1.setline(69);
      var5 = var1.getname("pack_uhyper");
      var1.setlocal("pack_hyper", var5);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_float$14, (PyObject)null);
      var1.setlocal("pack_float", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_double$15, (PyObject)null);
      var1.setlocal("pack_double", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_fstring$16, (PyObject)null);
      var1.setlocal("pack_fstring", var4);
      var3 = null;
      var1.setline(89);
      var5 = var1.getname("pack_fstring");
      var1.setlocal("pack_fopaque", var5);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_string$17, (PyObject)null);
      var1.setlocal("pack_string", var4);
      var3 = null;
      var1.setline(96);
      var5 = var1.getname("pack_string");
      var1.setlocal("pack_opaque", var5);
      var3 = null;
      var1.setline(97);
      var5 = var1.getname("pack_string");
      var1.setlocal("pack_bytes", var5);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_list$18, (PyObject)null);
      var1.setlocal("pack_list", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_farray$19, (PyObject)null);
      var1.setlocal("pack_farray", var4);
      var3 = null;
      var1.setline(111);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack_array$20, (PyObject)null);
      var1.setlocal("pack_array", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$8(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("_StringIO").__call__(var2);
      var1.getlocal(0).__setattr__("_Packer__buf", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_buffer$9(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pack_uint$10(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_int$11(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_bool$12(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(62);
         var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000\u0000\u0000\u0001"));
      } else {
         var1.setline(63);
         var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000\u0000\u0000\u0000"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_uhyper$13(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      var1.getlocal(0).__getattr__("pack_uint").__call__(var2, var1.getlocal(1)._rshift(Py.newInteger(32))._and(Py.newLong("4294967295")));
      var1.setline(67);
      var1.getlocal(0).__getattr__("pack_uint").__call__(var2, var1.getlocal(1)._and(Py.newLong("4294967295")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_float$14(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(72);
         var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">f"), (PyObject)var1.getlocal(1)));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("struct").__getattr__("error"))) {
            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(74);
            throw Py.makeException(var1.getglobal("ConversionError"), var1.getlocal(2));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_double$15(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(77);
         var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">d"), (PyObject)var1.getlocal(1)));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("struct").__getattr__("error"))) {
            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(79);
            throw Py.makeException(var1.getglobal("ConversionError"), var1.getlocal(2));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_fstring$16(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(83);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("fstring size must be nonnegative"));
      } else {
         var1.setline(84);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(85);
         var3 = var1.getlocal(1)._add(Py.newInteger(3))._floordiv(Py.newInteger(4))._mul(Py.newInteger(4));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(86);
         var3 = var1.getlocal(3)._add(var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3)))._mul(PyString.fromInterned("\u0000")));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(87);
         var1.getlocal(0).__getattr__("_Packer__buf").__getattr__("write").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject pack_string$17(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(93);
      var1.getlocal(0).__getattr__("pack_uint").__call__(var2, var1.getlocal(2));
      var1.setline(94);
      var1.getlocal(0).__getattr__("pack_fstring").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack_list$18(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(100);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(103);
            var1.getlocal(0).__getattr__("pack_uint").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(101);
         var1.getlocal(0).__getattr__("pack_uint").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setline(102);
         var1.getlocal(2).__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject pack_farray$19(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("wrong array size"));
      } else {
         var1.setline(108);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(108);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(109);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject pack_array$20(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(113);
      var1.getlocal(0).__getattr__("pack_uint").__call__(var2, var1.getlocal(3));
      var1.setline(114);
      var1.getlocal(0).__getattr__("pack_farray").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Unpacker$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Unpacks various data representations from the given buffer."));
      var1.setline(119);
      PyString.fromInterned("Unpacks various data representations from the given buffer.");
      var1.setline(121);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$22, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(124);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$23, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_position$24, (PyObject)null);
      var1.setlocal("get_position", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_position$25, (PyObject)null);
      var1.setlocal("set_position", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_buffer$26, (PyObject)null);
      var1.setlocal("get_buffer", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, done$27, (PyObject)null);
      var1.setlocal("done", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_uint$28, (PyObject)null);
      var1.setlocal("unpack_uint", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_int$29, (PyObject)null);
      var1.setlocal("unpack_int", var4);
      var3 = null;
      var1.setline(161);
      PyObject var5 = var1.getname("unpack_int");
      var1.setlocal("unpack_enum", var5);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_bool$30, (PyObject)null);
      var1.setlocal("unpack_bool", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_uhyper$31, (PyObject)null);
      var1.setlocal("unpack_uhyper", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_hyper$32, (PyObject)null);
      var1.setlocal("unpack_hyper", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_float$33, (PyObject)null);
      var1.setlocal("unpack_float", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_double$34, (PyObject)null);
      var1.setlocal("unpack_double", var4);
      var3 = null;
      var1.setline(193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_fstring$35, (PyObject)null);
      var1.setlocal("unpack_fstring", var4);
      var3 = null;
      var1.setline(203);
      var5 = var1.getname("unpack_fstring");
      var1.setlocal("unpack_fopaque", var5);
      var3 = null;
      var1.setline(205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_string$36, (PyObject)null);
      var1.setlocal("unpack_string", var4);
      var3 = null;
      var1.setline(209);
      var5 = var1.getname("unpack_string");
      var1.setlocal("unpack_opaque", var5);
      var3 = null;
      var1.setline(210);
      var5 = var1.getname("unpack_string");
      var1.setlocal("unpack_bytes", var5);
      var3 = null;
      var1.setline(212);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_list$37, (PyObject)null);
      var1.setlocal("unpack_list", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_farray$38, (PyObject)null);
      var1.setlocal("unpack_farray", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unpack_array$39, (PyObject)null);
      var1.setlocal("unpack_array", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$22(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      var1.getlocal(0).__getattr__("reset").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$23(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Unpacker__buf", var3);
      var3 = null;
      var1.setline(126);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_Unpacker__pos", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_position$24(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_position$25(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_buffer$26(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__buf");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject done$27(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_Unpacker__buf")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unextracted data remains")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject unpack_uint$28(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getlocal(1)._add(Py.newInteger(4));
      var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
      var1.setlocal(2, var3);
      var1.setline(144);
      var3 = var1.getlocal(0).__getattr__("_Unpacker__buf").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._lt(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(146);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         var1.setline(147);
         var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;

         try {
            var1.setline(149);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("OverflowError"))) {
               var1.setline(151);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject unpack_int$29(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getlocal(1)._add(Py.newInteger(4));
      var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
      var1.setlocal(2, var3);
      var1.setline(156);
      var3 = var1.getlocal(0).__getattr__("_Unpacker__buf").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._lt(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(158);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         var1.setline(159);
         var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unpack_bool$30(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(0).__getattr__("unpack_int").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unpack_uhyper$31(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject var3 = var1.getlocal(0).__getattr__("unpack_uint").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getlocal(0).__getattr__("unpack_uint").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getglobal("long").__call__(var2, var1.getlocal(1))._lshift(Py.newInteger(32))._or(var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unpack_hyper$32(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getlocal(0).__getattr__("unpack_uhyper").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newLong("9223372036854775808"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(174);
         var3 = var1.getlocal(1)._sub(Py.newLong("18446744073709551616"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(175);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unpack_float$33(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getlocal(1)._add(Py.newInteger(4));
      var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
      var1.setlocal(2, var3);
      var1.setline(180);
      var3 = var1.getlocal(0).__getattr__("_Unpacker__buf").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(181);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._lt(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(182);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         var1.setline(183);
         var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">f"), (PyObject)var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unpack_double$34(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getlocal(1)._add(Py.newInteger(8));
      var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
      var1.setlocal(2, var3);
      var1.setline(188);
      var3 = var1.getlocal(0).__getattr__("_Unpacker__buf").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._lt(Py.newInteger(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(190);
         throw Py.makeException(var1.getglobal("EOFError"));
      } else {
         var1.setline(191);
         var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">d"), (PyObject)var1.getlocal(3)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unpack_fstring$35(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(195);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("fstring size must be nonnegative"));
      } else {
         var1.setline(196);
         var3 = var1.getlocal(0).__getattr__("_Unpacker__pos");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(197);
         var3 = var1.getlocal(2)._add(var1.getlocal(1)._add(Py.newInteger(3))._floordiv(Py.newInteger(4))._mul(Py.newInteger(4)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(198);
         var3 = var1.getlocal(3);
         var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_Unpacker__buf")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(199);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            var1.setline(200);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("_Unpacker__pos", var3);
            var3 = null;
            var1.setline(201);
            var3 = var1.getlocal(0).__getattr__("_Unpacker__buf").__getslice__(var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(1)), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject unpack_string$36(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyObject var3 = var1.getlocal(0).__getattr__("unpack_uint").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getlocal(0).__getattr__("unpack_fstring").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unpack_list$37(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(214);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(215);
         var4 = var1.getlocal(0).__getattr__("unpack_uint").__call__(var2);
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(216);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(217);
         var4 = var1.getlocal(3);
         var10000 = var4._ne(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(218);
            throw Py.makeException(var1.getglobal("ConversionError"), PyString.fromInterned("0 or 1 expected, got %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)})));
         }

         var1.setline(219);
         var4 = var1.getlocal(1).__call__(var2);
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(220);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
      }

      var1.setline(221);
      var4 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject unpack_farray$38(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(225);
      PyObject var5 = var1.getglobal("range").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(225);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(227);
            var5 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(226);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2));
      }
   }

   public PyObject unpack_array$39(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getlocal(0).__getattr__("unpack_uint").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(231);
      var3 = var1.getlocal(0).__getattr__("unpack_farray").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public xdrlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 28, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$4 = Py.newCode(1, var2, var1, "__str__", 30, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConversionError$5 = Py.newCode(0, var2, var1, "ConversionError", 34, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Packer$6 = Py.newCode(0, var2, var1, "Packer", 39, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$7 = Py.newCode(1, var2, var1, "__init__", 42, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$8 = Py.newCode(1, var2, var1, "reset", 45, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_buffer$9 = Py.newCode(1, var2, var1, "get_buffer", 48, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      pack_uint$10 = Py.newCode(2, var2, var1, "pack_uint", 53, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      pack_int$11 = Py.newCode(2, var2, var1, "pack_int", 56, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      pack_bool$12 = Py.newCode(2, var2, var1, "pack_bool", 61, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      pack_uhyper$13 = Py.newCode(2, var2, var1, "pack_uhyper", 65, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "msg"};
      pack_float$14 = Py.newCode(2, var2, var1, "pack_float", 71, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "msg"};
      pack_double$15 = Py.newCode(2, var2, var1, "pack_double", 76, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "s", "data"};
      pack_fstring$16 = Py.newCode(3, var2, var1, "pack_fstring", 81, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "n"};
      pack_string$17 = Py.newCode(2, var2, var1, "pack_string", 91, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "pack_item", "item"};
      pack_list$18 = Py.newCode(3, var2, var1, "pack_list", 99, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "list", "pack_item", "item"};
      pack_farray$19 = Py.newCode(4, var2, var1, "pack_farray", 105, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "pack_item", "n"};
      pack_array$20 = Py.newCode(3, var2, var1, "pack_array", 111, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Unpacker$21 = Py.newCode(0, var2, var1, "Unpacker", 118, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data"};
      __init__$22 = Py.newCode(2, var2, var1, "__init__", 121, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      reset$23 = Py.newCode(2, var2, var1, "reset", 124, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_position$24 = Py.newCode(1, var2, var1, "get_position", 128, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position"};
      set_position$25 = Py.newCode(2, var2, var1, "set_position", 131, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_buffer$26 = Py.newCode(1, var2, var1, "get_buffer", 134, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      done$27 = Py.newCode(1, var2, var1, "done", 137, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "data", "x"};
      unpack_uint$28 = Py.newCode(1, var2, var1, "unpack_uint", 141, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "data"};
      unpack_int$29 = Py.newCode(1, var2, var1, "unpack_int", 153, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unpack_bool$30 = Py.newCode(1, var2, var1, "unpack_bool", 163, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hi", "lo"};
      unpack_uhyper$31 = Py.newCode(1, var2, var1, "unpack_uhyper", 166, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      unpack_hyper$32 = Py.newCode(1, var2, var1, "unpack_hyper", 171, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "data"};
      unpack_float$33 = Py.newCode(1, var2, var1, "unpack_float", 177, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "data"};
      unpack_double$34 = Py.newCode(1, var2, var1, "unpack_double", 185, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "i", "j"};
      unpack_fstring$35 = Py.newCode(2, var2, var1, "unpack_fstring", 193, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      unpack_string$36 = Py.newCode(1, var2, var1, "unpack_string", 205, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unpack_item", "list", "x", "item"};
      unpack_list$37 = Py.newCode(2, var2, var1, "unpack_list", 212, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "unpack_item", "list", "i"};
      unpack_farray$38 = Py.newCode(3, var2, var1, "unpack_farray", 223, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unpack_item", "n"};
      unpack_array$39 = Py.newCode(2, var2, var1, "unpack_array", 229, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xdrlib$py("xdrlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xdrlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.__str__$4(var2, var3);
         case 5:
            return this.ConversionError$5(var2, var3);
         case 6:
            return this.Packer$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.reset$8(var2, var3);
         case 9:
            return this.get_buffer$9(var2, var3);
         case 10:
            return this.pack_uint$10(var2, var3);
         case 11:
            return this.pack_int$11(var2, var3);
         case 12:
            return this.pack_bool$12(var2, var3);
         case 13:
            return this.pack_uhyper$13(var2, var3);
         case 14:
            return this.pack_float$14(var2, var3);
         case 15:
            return this.pack_double$15(var2, var3);
         case 16:
            return this.pack_fstring$16(var2, var3);
         case 17:
            return this.pack_string$17(var2, var3);
         case 18:
            return this.pack_list$18(var2, var3);
         case 19:
            return this.pack_farray$19(var2, var3);
         case 20:
            return this.pack_array$20(var2, var3);
         case 21:
            return this.Unpacker$21(var2, var3);
         case 22:
            return this.__init__$22(var2, var3);
         case 23:
            return this.reset$23(var2, var3);
         case 24:
            return this.get_position$24(var2, var3);
         case 25:
            return this.set_position$25(var2, var3);
         case 26:
            return this.get_buffer$26(var2, var3);
         case 27:
            return this.done$27(var2, var3);
         case 28:
            return this.unpack_uint$28(var2, var3);
         case 29:
            return this.unpack_int$29(var2, var3);
         case 30:
            return this.unpack_bool$30(var2, var3);
         case 31:
            return this.unpack_uhyper$31(var2, var3);
         case 32:
            return this.unpack_hyper$32(var2, var3);
         case 33:
            return this.unpack_float$33(var2, var3);
         case 34:
            return this.unpack_double$34(var2, var3);
         case 35:
            return this.unpack_fstring$35(var2, var3);
         case 36:
            return this.unpack_string$36(var2, var3);
         case 37:
            return this.unpack_list$37(var2, var3);
         case 38:
            return this.unpack_farray$38(var2, var3);
         case 39:
            return this.unpack_array$39(var2, var3);
         default:
            return null;
      }
   }
}
