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
@Filename("dis.py")
public class dis$py extends PyFunctionTable implements PyRunnable {
   static dis$py self;
   static final PyCode f$0;
   static final PyCode dis$1;
   static final PyCode distb$2;
   static final PyCode disassemble$3;
   static final PyCode disassemble_string$4;
   static final PyCode findlabels$5;
   static final PyCode findlinestarts$6;
   static final PyCode _test$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Disassembler of Python byte code into mnemonics."));
      var1.setline(1);
      PyString.fromInterned("Disassembler of Python byte code into mnemonics.");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(6);
      imp.importAll("opcode", var1, -1);
      var1.setline(7);
      String[] var5 = new String[]{"__all__"};
      PyObject[] var6 = imp.importFrom("opcode", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_opcodes_all", var4);
      var4 = null;
      var1.setline(9);
      var3 = (new PyList(new PyObject[]{PyString.fromInterned("dis"), PyString.fromInterned("disassemble"), PyString.fromInterned("distb"), PyString.fromInterned("disco"), PyString.fromInterned("findlinestarts"), PyString.fromInterned("findlabels")}))._add(var1.getname("_opcodes_all"));
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(11);
      var1.dellocal("_opcodes_all");
      var1.setline(13);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getname("types").__getattr__("MethodType"), var1.getname("types").__getattr__("FunctionType"), var1.getname("types").__getattr__("CodeType"), var1.getname("types").__getattr__("ClassType"), var1.getname("type")});
      var1.setlocal("_have_code", var7);
      var3 = null;
      var1.setline(16);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, dis$1, PyString.fromInterned("Disassemble classes, methods, functions, or code.\n\n    With no argument, disassemble the last traceback.\n\n    "));
      var1.setlocal("dis", var8);
      var3 = null;
      var1.setline(51);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, distb$2, PyString.fromInterned("Disassemble a traceback (default: last traceback)."));
      var1.setlocal("distb", var8);
      var3 = null;
      var1.setline(61);
      var6 = new PyObject[]{Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var6, disassemble$3, PyString.fromInterned("Disassemble a code object."));
      var1.setlocal("disassemble", var8);
      var3 = null;
      var1.setline(110);
      var6 = new PyObject[]{Py.newInteger(-1), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, disassemble_string$4, (PyObject)null);
      var1.setlocal("disassemble_string", var8);
      var3 = null;
      var1.setline(150);
      var3 = var1.getname("disassemble");
      var1.setlocal("disco", var3);
      var3 = null;
      var1.setline(152);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, findlabels$5, PyString.fromInterned("Detect all offsets in a byte code which are jump targets.\n\n    Return the list of offsets.\n\n    "));
      var1.setlocal("findlabels", var8);
      var3 = null;
      var1.setline(178);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, findlinestarts$6, PyString.fromInterned("Find the offsets in a byte code which are start of lines in the source.\n\n    Generate pairs (offset, lineno) as described in Python/compile.c.\n\n    "));
      var1.setlocal("findlinestarts", var8);
      var3 = null;
      var1.setline(200);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _test$7, PyString.fromInterned("Simple test program to disassemble a file."));
      var1.setlocal("_test", var8);
      var3 = null;
      var1.setline(223);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(224);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dis$1(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyString.fromInterned("Disassemble classes, methods, functions, or code.\n\n    With no argument, disassemble the last traceback.\n\n    ");
      var1.setline(22);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(23);
         var1.getglobal("distb").__call__(var2);
         var1.setline(24);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(25);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("InstanceType")).__nonzero__()) {
            var1.setline(26);
            var3 = var1.getlocal(0).__getattr__("__class__");
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(27);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("im_func")).__nonzero__()) {
            var1.setline(28);
            var3 = var1.getlocal(0).__getattr__("im_func");
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(29);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("func_code")).__nonzero__()) {
            var1.setline(30);
            var3 = var1.getlocal(0).__getattr__("func_code");
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(31);
         if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__dict__")).__nonzero__()) {
            var1.setline(42);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("co_code")).__nonzero__()) {
               var1.setline(43);
               var1.getglobal("disassemble").__call__(var2, var1.getlocal(0));
            } else {
               var1.setline(44);
               if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
                  var1.setline(47);
                  throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("don't know how to disassemble %s objects")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__")));
               }

               var1.setline(45);
               var1.getglobal("disassemble_string").__call__(var2, var1.getlocal(0));
            }
         } else {
            var1.setline(32);
            var3 = var1.getlocal(0).__getattr__("__dict__").__getattr__("items").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(33);
            var1.getlocal(1).__getattr__("sort").__call__(var2);
            var1.setline(34);
            var3 = var1.getlocal(1).__iter__();

            label53:
            while(true) {
               PyObject var6;
               do {
                  var1.setline(34);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label53;
                  }

                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(2, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var1.setline(35);
               } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("_have_code")).__nonzero__());

               var1.setline(36);
               Py.println(PyString.fromInterned("Disassembly of %s:")._mod(var1.getlocal(2)));

               try {
                  var1.setline(38);
                  var1.getglobal("dis").__call__(var2, var1.getlocal(3));
               } catch (Throwable var7) {
                  PyException var8 = Py.setException(var7, var1);
                  if (!var8.match(var1.getglobal("TypeError"))) {
                     throw var8;
                  }

                  var6 = var8.value;
                  var1.setlocal(4, var6);
                  var6 = null;
                  var1.setline(40);
                  Py.printComma(PyString.fromInterned("Sorry:"));
                  Py.println(var1.getlocal(4));
               }

               var1.setline(41);
               Py.println();
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject distb$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Disassemble a traceback (default: last traceback).");
      var1.setline(53);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(55);
            var3 = var1.getglobal("sys").__getattr__("last_traceback");
            var1.setlocal(0, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(57);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("no last traceback to disassemble"));
            }

            throw var5;
         }

         while(true) {
            var1.setline(58);
            if (!var1.getlocal(0).__getattr__("tb_next").__nonzero__()) {
               break;
            }

            var1.setline(58);
            var3 = var1.getlocal(0).__getattr__("tb_next");
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(59);
      var1.getglobal("disassemble").__call__(var2, var1.getlocal(0).__getattr__("tb_frame").__getattr__("f_code"), var1.getlocal(0).__getattr__("tb_lasti"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject disassemble$3(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("Disassemble a code object.");
      var1.setline(63);
      PyObject var3 = var1.getlocal(0).__getattr__("co_code");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getglobal("findlabels").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("dict").__call__(var2, var1.getglobal("findlinestarts").__call__(var2, var1.getlocal(0)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(67);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(68);
      var4 = Py.newInteger(0);
      var1.setlocal(7, var4);
      var3 = null;
      var1.setline(69);
      var3 = var1.getglobal("None");
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(70);
         var3 = var1.getlocal(6);
         PyObject var10000 = var3._lt(var1.getlocal(5));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(71);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(6));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(72);
         var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(9));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(73);
         var3 = var1.getlocal(6);
         var10000 = var3._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(74);
            var3 = var1.getlocal(6);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(75);
               Py.println();
            }

            var1.setline(76);
            Py.printComma(PyString.fromInterned("%3d")._mod(var1.getlocal(4).__getitem__(var1.getlocal(6))));
         } else {
            var1.setline(78);
            Py.printComma(PyString.fromInterned("   "));
         }

         var1.setline(80);
         var3 = var1.getlocal(6);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(80);
            Py.printComma(PyString.fromInterned("-->"));
         } else {
            var1.setline(81);
            Py.printComma(PyString.fromInterned("   "));
         }

         var1.setline(82);
         var3 = var1.getlocal(6);
         var10000 = var3._in(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(82);
            Py.printComma(PyString.fromInterned(">>"));
         } else {
            var1.setline(83);
            Py.printComma(PyString.fromInterned("  "));
         }

         var1.setline(84);
         Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(6)).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)));
         var1.setline(85);
         Py.printComma(var1.getglobal("opname").__getitem__(var1.getlocal(10)).__getattr__("ljust").__call__((ThreadState)var2, (PyObject)Py.newInteger(20)));
         var1.setline(86);
         var3 = var1.getlocal(6)._add(Py.newInteger(1));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(87);
         var3 = var1.getlocal(10);
         var10000 = var3._ge(var1.getglobal("HAVE_ARGUMENT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(88);
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6)))._add(var1.getglobal("ord").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6)._add(Py.newInteger(1))))._mul(Py.newInteger(256)))._add(var1.getlocal(7));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(89);
            var4 = Py.newInteger(0);
            var1.setlocal(7, var4);
            var3 = null;
            var1.setline(90);
            var3 = var1.getlocal(6)._add(Py.newInteger(2));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(91);
            var3 = var1.getlocal(10);
            var10000 = var3._eq(var1.getglobal("EXTENDED_ARG"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(92);
               var3 = var1.getlocal(11)._mul(Py.newLong("65536"));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(93);
            Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(11)).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(5)));
            var1.setline(94);
            var3 = var1.getlocal(10);
            var10000 = var3._in(var1.getglobal("hasconst"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(95);
               Py.printComma(PyString.fromInterned("(")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("co_consts").__getitem__(var1.getlocal(11))))._add(PyString.fromInterned(")")));
            } else {
               var1.setline(96);
               var3 = var1.getlocal(10);
               var10000 = var3._in(var1.getglobal("hasname"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(97);
                  Py.printComma(PyString.fromInterned("(")._add(var1.getlocal(0).__getattr__("co_names").__getitem__(var1.getlocal(11)))._add(PyString.fromInterned(")")));
               } else {
                  var1.setline(98);
                  var3 = var1.getlocal(10);
                  var10000 = var3._in(var1.getglobal("hasjrel"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(99);
                     Py.printComma(PyString.fromInterned("(to ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(6)._add(var1.getlocal(11))))._add(PyString.fromInterned(")")));
                  } else {
                     var1.setline(100);
                     var3 = var1.getlocal(10);
                     var10000 = var3._in(var1.getglobal("haslocal"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(101);
                        Py.printComma(PyString.fromInterned("(")._add(var1.getlocal(0).__getattr__("co_varnames").__getitem__(var1.getlocal(11)))._add(PyString.fromInterned(")")));
                     } else {
                        var1.setline(102);
                        var3 = var1.getlocal(10);
                        var10000 = var3._in(var1.getglobal("hascompare"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(103);
                           Py.printComma(PyString.fromInterned("(")._add(var1.getglobal("cmp_op").__getitem__(var1.getlocal(11)))._add(PyString.fromInterned(")")));
                        } else {
                           var1.setline(104);
                           var3 = var1.getlocal(10);
                           var10000 = var3._in(var1.getglobal("hasfree"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(105);
                              var3 = var1.getlocal(8);
                              var10000 = var3._is(var1.getglobal("None"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(106);
                                 var3 = var1.getlocal(0).__getattr__("co_cellvars")._add(var1.getlocal(0).__getattr__("co_freevars"));
                                 var1.setlocal(8, var3);
                                 var3 = null;
                              }

                              var1.setline(107);
                              Py.printComma(PyString.fromInterned("(")._add(var1.getlocal(8).__getitem__(var1.getlocal(11)))._add(PyString.fromInterned(")")));
                           }
                        }
                     }
                  }
               }
            }
         }

         var1.setline(108);
         Py.println();
      }
   }

   public PyObject disassemble_string$4(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("findlabels").__call__(var2, var1.getlocal(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(114);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(7, var4);
      var3 = null;

      while(true) {
         var1.setline(115);
         var3 = var1.getlocal(7);
         PyObject var10000 = var3._lt(var1.getlocal(6));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(116);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(117);
         var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(8));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(118);
         var3 = var1.getlocal(7);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(118);
            Py.printComma(PyString.fromInterned("-->"));
         } else {
            var1.setline(119);
            Py.printComma(PyString.fromInterned("   "));
         }

         var1.setline(120);
         var3 = var1.getlocal(7);
         var10000 = var3._in(var1.getlocal(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(120);
            Py.printComma(PyString.fromInterned(">>"));
         } else {
            var1.setline(121);
            Py.printComma(PyString.fromInterned("  "));
         }

         var1.setline(122);
         Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(7)).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)));
         var1.setline(123);
         Py.printComma(var1.getglobal("opname").__getitem__(var1.getlocal(9)).__getattr__("ljust").__call__((ThreadState)var2, (PyObject)Py.newInteger(15)));
         var1.setline(124);
         var3 = var1.getlocal(7)._add(Py.newInteger(1));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(125);
         var3 = var1.getlocal(9);
         var10000 = var3._ge(var1.getglobal("HAVE_ARGUMENT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(126);
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(7)))._add(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(7)._add(Py.newInteger(1))))._mul(Py.newInteger(256)));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(127);
            var3 = var1.getlocal(7)._add(Py.newInteger(2));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(128);
            Py.printComma(var1.getglobal("repr").__call__(var2, var1.getlocal(10)).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(5)));
            var1.setline(129);
            var3 = var1.getlocal(9);
            var10000 = var3._in(var1.getglobal("hasconst"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(130);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(131);
                  Py.printComma(PyString.fromInterned("(")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(10))))._add(PyString.fromInterned(")")));
               } else {
                  var1.setline(133);
                  Py.printComma(PyString.fromInterned("(%d)")._mod(var1.getlocal(10)));
               }
            } else {
               var1.setline(134);
               var3 = var1.getlocal(9);
               var10000 = var3._in(var1.getglobal("hasname"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(135);
                  var3 = var1.getlocal(3);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(136);
                     Py.printComma(PyString.fromInterned("(")._add(var1.getlocal(3).__getitem__(var1.getlocal(10)))._add(PyString.fromInterned(")")));
                  } else {
                     var1.setline(138);
                     Py.printComma(PyString.fromInterned("(%d)")._mod(var1.getlocal(10)));
                  }
               } else {
                  var1.setline(139);
                  var3 = var1.getlocal(9);
                  var10000 = var3._in(var1.getglobal("hasjrel"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(140);
                     Py.printComma(PyString.fromInterned("(to ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(7)._add(var1.getlocal(10))))._add(PyString.fromInterned(")")));
                  } else {
                     var1.setline(141);
                     var3 = var1.getlocal(9);
                     var10000 = var3._in(var1.getglobal("haslocal"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(142);
                        if (var1.getlocal(2).__nonzero__()) {
                           var1.setline(143);
                           Py.printComma(PyString.fromInterned("(")._add(var1.getlocal(2).__getitem__(var1.getlocal(10)))._add(PyString.fromInterned(")")));
                        } else {
                           var1.setline(145);
                           Py.printComma(PyString.fromInterned("(%d)")._mod(var1.getlocal(10)));
                        }
                     } else {
                        var1.setline(146);
                        var3 = var1.getlocal(9);
                        var10000 = var3._in(var1.getglobal("hascompare"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(147);
                           Py.printComma(PyString.fromInterned("(")._add(var1.getglobal("cmp_op").__getitem__(var1.getlocal(10)))._add(PyString.fromInterned(")")));
                        }
                     }
                  }
               }
            }
         }

         var1.setline(148);
         Py.println();
      }
   }

   public PyObject findlabels$5(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyString.fromInterned("Detect all offsets in a byte code which are jump targets.\n\n    Return the list of offsets.\n\n    ");
      var1.setline(158);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(159);
      PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(160);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(3, var5);
      var3 = null;

      while(true) {
         var1.setline(161);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._lt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(176);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(162);
         var4 = var1.getlocal(0).__getitem__(var1.getlocal(3));
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(163);
         var4 = var1.getglobal("ord").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(164);
         var4 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(165);
         var4 = var1.getlocal(5);
         var10000 = var4._ge(var1.getglobal("HAVE_ARGUMENT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(166);
            var4 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(3)))._add(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(3)._add(Py.newInteger(1))))._mul(Py.newInteger(256)));
            var1.setlocal(6, var4);
            var3 = null;
            var1.setline(167);
            var4 = var1.getlocal(3)._add(Py.newInteger(2));
            var1.setlocal(3, var4);
            var3 = null;
            var1.setline(168);
            var5 = Py.newInteger(-1);
            var1.setlocal(7, var5);
            var3 = null;
            var1.setline(169);
            var4 = var1.getlocal(5);
            var10000 = var4._in(var1.getglobal("hasjrel"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(170);
               var4 = var1.getlocal(3)._add(var1.getlocal(6));
               var1.setlocal(7, var4);
               var3 = null;
            } else {
               var1.setline(171);
               var4 = var1.getlocal(5);
               var10000 = var4._in(var1.getglobal("hasjabs"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(172);
                  var4 = var1.getlocal(6);
                  var1.setlocal(7, var4);
                  var3 = null;
               }
            }

            var1.setline(173);
            var4 = var1.getlocal(7);
            var10000 = var4._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(174);
               var4 = var1.getlocal(7);
               var10000 = var4._notin(var1.getlocal(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(175);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(7));
               }
            }
         }
      }
   }

   public PyObject findlinestarts$6(PyFrame var1, ThreadState var2) {
      label62: {
         Object[] var3;
         PyObject var4;
         Object[] var5;
         PyObject var7;
         PyObject var9;
         PyObject var13;
         Object var10000;
         label59:
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(183);
               PyString.fromInterned("Find the offsets in a byte code which are start of lines in the source.\n\n    Generate pairs (offset, lineno) as described in Python/compile.c.\n\n    ");
               var1.setline(184);
               PyList var14 = new PyList();
               var7 = var14.__getattr__("append");
               var1.setlocal(2, var7);
               var3 = null;
               var1.setline(184);
               var7 = var1.getlocal(0).__getattr__("co_lnotab").__getslice__(Py.newInteger(0), (PyObject)null, Py.newInteger(2)).__iter__();

               while(true) {
                  var1.setline(184);
                  var4 = var7.__iternext__();
                  if (var4 == null) {
                     var1.setline(184);
                     var1.dellocal(2);
                     PyList var8 = var14;
                     var1.setlocal(1, var8);
                     var3 = null;
                     var1.setline(185);
                     var14 = new PyList();
                     var7 = var14.__getattr__("append");
                     var1.setlocal(5, var7);
                     var3 = null;
                     var1.setline(185);
                     var7 = var1.getlocal(0).__getattr__("co_lnotab").__getslice__(Py.newInteger(1), (PyObject)null, Py.newInteger(2)).__iter__();

                     while(true) {
                        var1.setline(185);
                        var4 = var7.__iternext__();
                        if (var4 == null) {
                           var1.setline(185);
                           var1.dellocal(5);
                           var8 = var14;
                           var1.setlocal(4, var8);
                           var3 = null;
                           var1.setline(187);
                           var7 = var1.getglobal("None");
                           var1.setlocal(6, var7);
                           var3 = null;
                           var1.setline(188);
                           var7 = var1.getlocal(0).__getattr__("co_firstlineno");
                           var1.setlocal(7, var7);
                           var3 = null;
                           var1.setline(189);
                           PyInteger var11 = Py.newInteger(0);
                           var1.setlocal(8, var11);
                           var3 = null;
                           var1.setline(190);
                           var7 = var1.getglobal("zip").__call__(var2, var1.getlocal(1), var1.getlocal(4)).__iter__();
                           break label59;
                        }

                        var1.setlocal(3, var4);
                        var1.setline(185);
                        var1.getlocal(5).__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(3)));
                     }
                  }

                  var1.setlocal(3, var4);
                  var1.setline(184);
                  var1.getlocal(2).__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(3)));
               }
            case 1:
               var5 = var1.f_savedlocals;
               var7 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var13 = (PyObject)var10000;
               var1.setline(194);
               var9 = var1.getlocal(7);
               var1.setlocal(6, var9);
               var5 = null;
               var1.setline(195);
               var9 = var1.getlocal(8);
               var9 = var9._iadd(var1.getlocal(9));
               var1.setlocal(8, var9);
               var1.setline(196);
               var9 = var1.getlocal(7);
               var9 = var9._iadd(var1.getlocal(10));
               var1.setlocal(7, var9);
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var13 = (PyObject)var10000;
               break label62;
         }

         while(true) {
            var1.setline(190);
            var4 = var7.__iternext__();
            PyTuple var15;
            if (var4 == null) {
               var1.setline(197);
               var7 = var1.getlocal(7);
               var13 = var7._ne(var1.getlocal(6));
               var3 = null;
               if (var13.__nonzero__()) {
                  var1.setline(198);
                  var1.setline(198);
                  PyObject[] var12 = new PyObject[]{var1.getlocal(8), var1.getlocal(7)};
                  var15 = new PyTuple(var12);
                  Arrays.fill(var12, (Object)null);
                  var1.f_lasti = 2;
                  var3 = new Object[7];
                  var1.f_savedlocals = var3;
                  return var15;
               }
               break;
            }

            PyObject[] var10 = Py.unpackSequence(var4, 2);
            PyObject var6 = var10[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(191);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(192);
               var9 = var1.getlocal(7);
               var13 = var9._ne(var1.getlocal(6));
               var5 = null;
               if (var13.__nonzero__()) {
                  var1.setline(193);
                  var1.setline(193);
                  var10 = new PyObject[]{var1.getlocal(8), var1.getlocal(7)};
                  var15 = new PyTuple(var10);
                  Arrays.fill(var10, (Object)null);
                  var1.f_lasti = 1;
                  var5 = new Object[7];
                  var5[3] = var7;
                  var5[4] = var4;
                  var1.f_savedlocals = var5;
                  return var15;
               }

               var1.setline(195);
               var9 = var1.getlocal(8);
               var9 = var9._iadd(var1.getlocal(9));
               var1.setlocal(8, var9);
            }

            var1.setline(196);
            var9 = var1.getlocal(7);
            var9 = var9._iadd(var1.getlocal(10));
            var1.setlocal(7, var9);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _test$7(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyString.fromInterned("Simple test program to disassemble a file.");
      var1.setline(202);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
         var1.setline(203);
         if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(204);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usage: python dis.py [-|file]\n"));
            var1.setline(205);
            var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         }

         var1.setline(206);
         var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(207);
         var10000 = var1.getlocal(0).__not__();
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._eq(PyString.fromInterned("-"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(208);
            var3 = var1.getglobal("None");
            var1.setlocal(0, var3);
            var3 = null;
         }
      } else {
         var1.setline(210);
         var3 = var1.getglobal("None");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(211);
      var3 = var1.getlocal(0);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         var3 = var1.getglobal("sys").__getattr__("stdin");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(214);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(215);
      var3 = var1.getlocal(1).__getattr__("read").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getlocal(0);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(217);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      } else {
         var1.setline(219);
         PyString var4 = PyString.fromInterned("<stdin>");
         var1.setlocal(0, var4);
         var3 = null;
      }

      var1.setline(220);
      var3 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("exec"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(221);
      var1.getglobal("dis").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public dis$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x", "items", "name", "x1", "msg"};
      dis$1 = Py.newCode(1, var2, var1, "dis", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb"};
      distb$2 = Py.newCode(1, var2, var1, "distb", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"co", "lasti", "code", "labels", "linestarts", "n", "i", "extended_arg", "free", "c", "op", "oparg"};
      disassemble$3 = Py.newCode(2, var2, var1, "disassemble", 61, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "lasti", "varnames", "names", "constants", "labels", "n", "i", "c", "op", "oparg"};
      disassemble_string$4 = Py.newCode(5, var2, var1, "disassemble_string", 110, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "labels", "n", "i", "c", "op", "oparg", "label"};
      findlabels$5 = Py.newCode(1, var2, var1, "findlabels", 152, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "byte_increments", "_[184_23]", "c", "line_increments", "_[185_23]", "lastlineno", "lineno", "addr", "byte_incr", "line_incr"};
      findlinestarts$6 = Py.newCode(1, var2, var1, "findlinestarts", 178, false, false, self, 6, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"fn", "f", "source", "code"};
      _test$7 = Py.newCode(0, var2, var1, "_test", 200, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dis$py("dis$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dis$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.dis$1(var2, var3);
         case 2:
            return this.distb$2(var2, var3);
         case 3:
            return this.disassemble$3(var2, var3);
         case 4:
            return this.disassemble_string$4(var2, var3);
         case 5:
            return this.findlabels$5(var2, var3);
         case 6:
            return this.findlinestarts$6(var2, var3);
         case 7:
            return this._test$7(var2, var3);
         default:
            return null;
      }
   }
}
