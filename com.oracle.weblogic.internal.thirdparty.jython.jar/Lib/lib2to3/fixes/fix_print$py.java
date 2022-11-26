package lib2to3.fixes;

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
import org.python.core.PyList;
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
@Filename("lib2to3/fixes/fix_print.py")
public class fix_print$py extends PyFunctionTable implements PyRunnable {
   static fix_print$py self;
   static final PyCode f$0;
   static final PyCode FixPrint$1;
   static final PyCode transform$2;
   static final PyCode add_kwarg$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for print.\n\nChange:\n    'print'          into 'print()'\n    'print ...'      into 'print(...)'\n    'print ... ,'    into 'print(..., end=\" \")'\n    'print >>x, ...' into 'print(..., file=x)'\n\nNo changes are applied if print_function is imported from __future__\n\n"));
      var1.setline(14);
      PyString.fromInterned("Fixer for print.\n\nChange:\n    'print'          into 'print()'\n    'print ...'      into 'print(...)'\n    'print ... ,'    into 'print(..., end=\" \")'\n    'print >>x, ...' into 'print(..., file=x)'\n\nNo changes are applied if print_function is imported from __future__\n\n");
      var1.setline(17);
      String[] var3 = new String[]{"patcomp"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("patcomp", var4);
      var4 = null;
      var1.setline(18);
      var3 = new String[]{"pytree"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(19);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(20);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(21);
      var3 = new String[]{"Name", "Call", "Comma", "String", "is_tuple"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("String", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("is_tuple", var4);
      var4 = null;
      var1.setline(24);
      PyObject var6 = var1.getname("patcomp").__getattr__("compile_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("atom< '(' [atom|STRING|NAME] ')' >"));
      var1.setlocal("parend_expr", var6);
      var3 = null;
      var1.setline(29);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixPrint", var5, FixPrint$1);
      var1.setlocal("FixPrint", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixPrint$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(33);
      PyString var4 = PyString.fromInterned("\n              simple_stmt< any* bare='print' any* > | print_stmt\n              ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(37);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      var1.setline(77);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_kwarg$3, (PyObject)null);
      var1.setlocal("add_kwarg", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(40);
         PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bare"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(42);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(44);
            var10000 = var1.getlocal(3).__getattr__("replace");
            PyObject var10002 = var1.getglobal("Call");
            PyObject[] var9 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("print")), new PyList(Py.EmptyObjects), var1.getlocal(3).__getattr__("prefix")};
            String[] var5 = new String[]{"prefix"};
            var10002 = var10002.__call__(var2, var9, var5);
            var3 = null;
            var10000.__call__(var2, var10002);
            var1.setline(46);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(47);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(0));
               var10000 = var3._eq(var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("print")));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(48);
            var3 = var1.getlocal(1).__getattr__("children").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(49);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("parend_expr").__getattr__("match").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)));
            }

            if (var10000.__nonzero__()) {
               var1.setline(52);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(54);
               var3 = var1.getglobal("None");
               var1.setlocal(5, var3);
               var1.setlocal(6, var3);
               var1.setlocal(7, var3);
               var1.setline(55);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
                  var10000 = var3._eq(var1.getglobal("Comma").__call__(var2));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(56);
                  var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(57);
                  PyString var6 = PyString.fromInterned(" ");
                  var1.setlocal(6, var6);
                  var3 = null;
               }

               var1.setline(58);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
                  var10000 = var3._eq(var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RIGHTSHIFT"), (PyObject)PyUnicode.fromInterned(">>")));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(59);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                     var10000 = var3._ge(Py.newInteger(2));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(60);
                  var3 = var1.getlocal(4).__getitem__(Py.newInteger(1)).__getattr__("clone").__call__(var2);
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(61);
                  var3 = var1.getlocal(4).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
                  var1.setlocal(4, var3);
                  var3 = null;
               }

               var1.setline(63);
               PyList var10 = new PyList();
               var3 = var10.__getattr__("append");
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(63);
               var3 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(63);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(63);
                     var1.dellocal(9);
                     PyList var7 = var10;
                     var1.setlocal(8, var7);
                     var3 = null;
                     var1.setline(64);
                     if (var1.getlocal(8).__nonzero__()) {
                        var1.setline(65);
                        PyUnicode var8 = PyUnicode.fromInterned("");
                        var1.getlocal(8).__getitem__(Py.newInteger(0)).__setattr__((String)"prefix", var8);
                        var3 = null;
                     }

                     var1.setline(66);
                     var3 = var1.getlocal(5);
                     var10000 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getlocal(6);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var3 = var1.getlocal(7);
                           var10000 = var3._isnot(var1.getglobal("None"));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(67);
                        var3 = var1.getlocal(5);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(68);
                           var1.getlocal(0).__getattr__("add_kwarg").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyUnicode.fromInterned("sep"), (PyObject)var1.getglobal("String").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(5))));
                        }

                        var1.setline(69);
                        var3 = var1.getlocal(6);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(70);
                           var1.getlocal(0).__getattr__("add_kwarg").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyUnicode.fromInterned("end"), (PyObject)var1.getglobal("String").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(6))));
                        }

                        var1.setline(71);
                        var3 = var1.getlocal(7);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(72);
                           var1.getlocal(0).__getattr__("add_kwarg").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyUnicode.fromInterned("file"), (PyObject)var1.getlocal(7));
                        }
                     }

                     var1.setline(73);
                     var3 = var1.getglobal("Call").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("print")), var1.getlocal(8));
                     var1.setlocal(11, var3);
                     var3 = null;
                     var1.setline(74);
                     var3 = var1.getlocal(1).__getattr__("prefix");
                     var1.getlocal(11).__setattr__("prefix", var3);
                     var3 = null;
                     var1.setline(75);
                     var3 = var1.getlocal(11);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(10, var4);
                  var1.setline(63);
                  var1.getlocal(9).__call__(var2, var1.getlocal(10).__getattr__("clone").__call__(var2));
               }
            }
         }
      }
   }

   public PyObject add_kwarg$3(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyUnicode var3 = PyUnicode.fromInterned("");
      var1.getlocal(3).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(80);
      PyObject var4 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("syms").__getattr__("argument"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("Name").__call__(var2, var1.getlocal(2)), var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("EQUAL"), (PyObject)PyUnicode.fromInterned("=")), var1.getlocal(3)})));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(84);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(85);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("Comma").__call__(var2));
         var1.setline(86);
         var3 = PyUnicode.fromInterned(" ");
         var1.getlocal(4).__setattr__((String)"prefix", var3);
         var3 = null;
      }

      var1.setline(87);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_print$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixPrint$1 = Py.newCode(0, var2, var1, "FixPrint", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "bare_print", "args", "sep", "end", "file", "l_args", "_[63_18]", "arg", "n_stmt"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l_nodes", "s_kwd", "n_expr", "n_argument"};
      add_kwarg$3 = Py.newCode(4, var2, var1, "add_kwarg", 77, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_print$py("lib2to3/fixes/fix_print$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_print$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixPrint$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         case 3:
            return this.add_kwarg$3(var2, var3);
         default:
            return null;
      }
   }
}
