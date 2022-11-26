package lib2to3.fixes;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_next.py")
public class fix_next$py extends PyFunctionTable implements PyRunnable {
   static fix_next$py self;
   static final PyCode f$0;
   static final PyCode FixNext$1;
   static final PyCode start_tree$2;
   static final PyCode transform$3;
   static final PyCode is_assign_target$4;
   static final PyCode find_assign$5;
   static final PyCode is_subtree$6;
   static final PyCode f$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for it.next() -> next(it), per PEP 3114."));
      var1.setline(1);
      PyString.fromInterned("Fixer for it.next() -> next(it), per PEP 3114.");
      var1.setline(9);
      String[] var3 = new String[]{"token"};
      PyObject[] var5 = imp.importFrom("pgen2", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"python_symbols"};
      var5 = imp.importFrom("pygram", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"Name", "Call", "find_binding"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("find_binding", var4);
      var4 = null;
      var1.setline(14);
      PyString var6 = PyString.fromInterned("Calls to builtin next() possibly shadowed by global binding");
      var1.setlocal("bind_warning", var6);
      var3 = null;
      var1.setline(17);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixNext", var5, FixNext$1);
      var1.setlocal("FixNext", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(81);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, is_assign_target$4, (PyObject)null);
      var1.setlocal("is_assign_target", var7);
      var3 = null;
      var1.setline(93);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, find_assign$5, (PyObject)null);
      var1.setlocal("find_assign", var7);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, is_subtree$6, (PyObject)null);
      var1.setlocal("is_subtree", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixNext$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(19);
      PyString var4 = PyString.fromInterned("\n    power< base=any+ trailer< '.' attr='next' > trailer< '(' ')' > >\n    |\n    power< head=any+ trailer< '.' attr='next' > not trailer< '(' ')' > >\n    |\n    classdef< 'class' any+ ':'\n              suite< any*\n                     funcdef< 'def'\n                              name='next'\n                              parameters< '(' NAME ')' > any+ >\n                     any* > >\n    |\n    global=global_stmt< 'global' any* 'next' any* >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(34);
      var4 = PyString.fromInterned("pre");
      var1.setlocal("order", var4);
      var3 = null;
      var1.setline(36);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, start_tree$2, (PyObject)null);
      var1.setlocal("start_tree", var6);
      var3 = null;
      var1.setline(46);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, transform$3, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject start_tree$2(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getglobal("super").__call__(var2, var1.getglobal("FixNext"), var1.getlocal(0)).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(39);
      PyObject var3 = var1.getglobal("find_binding").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("next"), (PyObject)var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(40);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(41);
         var1.getlocal(0).__getattr__("warning").__call__(var2, var1.getlocal(3), var1.getglobal("bind_warning"));
         var1.setline(42);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("shadowed_next", var3);
         var3 = null;
      } else {
         var1.setline(44);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("shadowed_next", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(49);
         PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("base"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(50);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("attr"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(51);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(53);
         String[] var4;
         PyObject[] var8;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(54);
            PyObject var10002;
            if (var1.getlocal(0).__getattr__("shadowed_next").__nonzero__()) {
               var1.setline(55);
               var10000 = var1.getlocal(4).__getattr__("replace");
               var10002 = var1.getglobal("Name");
               var8 = new PyObject[]{PyUnicode.fromInterned("__next__"), var1.getlocal(4).__getattr__("prefix")};
               var4 = new String[]{"prefix"};
               var10002 = var10002.__call__(var2, var8, var4);
               var3 = null;
               var10000.__call__(var2, var10002);
            } else {
               var1.setline(57);
               PyList var12 = new PyList();
               var3 = var12.__getattr__("append");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(57);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(57);
                  PyObject var7 = var3.__iternext__();
                  if (var7 == null) {
                     var1.setline(57);
                     var1.dellocal(6);
                     PyList var9 = var12;
                     var1.setlocal(3, var9);
                     var3 = null;
                     var1.setline(58);
                     PyUnicode var10 = PyUnicode.fromInterned("");
                     var1.getlocal(3).__getitem__(Py.newInteger(0)).__setattr__((String)"prefix", var10);
                     var3 = null;
                     var1.setline(59);
                     var10000 = var1.getlocal(1).__getattr__("replace");
                     var10002 = var1.getglobal("Call");
                     PyObject var10004 = var1.getglobal("Name");
                     var8 = new PyObject[]{PyUnicode.fromInterned("next"), var1.getlocal(1).__getattr__("prefix")};
                     var4 = new String[]{"prefix"};
                     var10004 = var10004.__call__(var2, var8, var4);
                     var3 = null;
                     var10000.__call__(var2, var10002.__call__(var2, var10004, var1.getlocal(3)));
                     break;
                  }

                  var1.setlocal(7, var7);
                  var1.setline(57);
                  var1.getlocal(6).__call__(var2, var1.getlocal(7).__getattr__("clone").__call__(var2));
               }
            }
         } else {
            var1.setline(60);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(61);
               var10000 = var1.getglobal("Name");
               var8 = new PyObject[]{PyUnicode.fromInterned("__next__"), var1.getlocal(5).__getattr__("prefix")};
               var4 = new String[]{"prefix"};
               var10000 = var10000.__call__(var2, var8, var4);
               var3 = null;
               var3 = var10000;
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(62);
               var1.getlocal(5).__getattr__("replace").__call__(var2, var1.getlocal(7));
            } else {
               var1.setline(63);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(67);
                  if (var1.getglobal("is_assign_target").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                     var1.setline(68);
                     var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("head"));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(69);
                     var10000 = PyString.fromInterned("").__getattr__("join");
                     PyList var13 = new PyList();
                     PyObject var5 = var13.__getattr__("append");
                     var1.setlocal(9, var5);
                     var5 = null;
                     var1.setline(69);
                     var5 = var1.getlocal(8).__iter__();

                     while(true) {
                        var1.setline(69);
                        PyObject var6 = var5.__iternext__();
                        if (var6 == null) {
                           var1.setline(69);
                           var1.dellocal(9);
                           var3 = var10000.__call__((ThreadState)var2, (PyObject)var13).__getattr__("strip").__call__(var2);
                           var10000 = var3._eq(PyUnicode.fromInterned("__builtin__"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(70);
                              var1.getlocal(0).__getattr__("warning").__call__(var2, var1.getlocal(1), var1.getglobal("bind_warning"));
                           }

                           var1.setline(71);
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setlocal(7, var6);
                        var1.setline(69);
                        var1.getlocal(9).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(7)));
                     }
                  }

                  var1.setline(72);
                  var1.getlocal(4).__getattr__("replace").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("__next__")));
               } else {
                  var1.setline(73);
                  PyString var11 = PyString.fromInterned("global");
                  var10000 = var11._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(74);
                     var1.getlocal(0).__getattr__("warning").__call__(var2, var1.getlocal(1), var1.getglobal("bind_warning"));
                     var1.setline(75);
                     var3 = var1.getglobal("True");
                     var1.getlocal(0).__setattr__("shadowed_next", var3);
                     var3 = null;
                  }
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject is_assign_target$4(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getglobal("find_assign").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(84);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(86);
         PyObject var4 = var1.getlocal(1).__getattr__("children").__iter__();

         do {
            var1.setline(86);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(91);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var5);
            var1.setline(87);
            PyObject var6 = var1.getlocal(2).__getattr__("type");
            var10000 = var6._eq(var1.getglobal("token").__getattr__("EQUAL"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(88);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(89);
         } while(!var1.getglobal("is_subtree").__call__(var2, var1.getlocal(2), var1.getlocal(0)).__nonzero__());

         var1.setline(90);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject find_assign$5(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(95);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(96);
         PyObject var4 = var1.getlocal(0).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("parent");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(97);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(98);
            var3 = var1.getglobal("find_assign").__call__(var2, var1.getlocal(0).__getattr__("parent"));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject is_subtree$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(101);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getderef(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(102);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(103);
         var10000 = var1.getglobal("any");
         var1.setline(103);
         PyObject var10004 = var1.f_globals;
         PyObject[] var4 = Py.EmptyObjects;
         PyCode var10006 = f$7;
         PyObject[] var5 = new PyObject[]{var1.getclosure(0)};
         PyFunction var6 = new PyFunction(var10004, var4, var10006, (PyObject)null, var5);
         PyObject var10002 = var6.__call__(var2, var1.getlocal(0).__getattr__("children").__iter__());
         Arrays.fill(var4, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(103);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(103);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(103);
         var1.setline(103);
         var6 = var1.getglobal("is_subtree").__call__(var2, var1.getlocal(1), var1.getderef(0));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public fix_next$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixNext$1 = Py.newCode(0, var2, var1, "FixNext", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tree", "filename", "n"};
      start_tree$2 = Py.newCode(3, var2, var1, "start_tree", 36, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "base", "attr", "name", "_[57_24]", "n", "head", "_[69_28]"};
      transform$3 = Py.newCode(3, var2, var1, "transform", 46, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "assign", "child"};
      is_assign_target$4 = Py.newCode(1, var2, var1, "is_assign_target", 81, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      find_assign$5 = Py.newCode(1, var2, var1, "find_assign", 93, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"root", "node", "_(103_15)"};
      String[] var10001 = var2;
      fix_next$py var10007 = self;
      var2 = new String[]{"node"};
      is_subtree$6 = Py.newCode(2, var10001, var1, "is_subtree", 100, false, false, var10007, 6, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "c"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"node"};
      f$7 = Py.newCode(1, var10001, var1, "<genexpr>", 103, false, false, var10007, 7, (String[])null, var2, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_next$py("lib2to3/fixes/fix_next$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_next$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixNext$1(var2, var3);
         case 2:
            return this.start_tree$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         case 4:
            return this.is_assign_target$4(var2, var3);
         case 5:
            return this.find_assign$5(var2, var3);
         case 6:
            return this.is_subtree$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         default:
            return null;
      }
   }
}
