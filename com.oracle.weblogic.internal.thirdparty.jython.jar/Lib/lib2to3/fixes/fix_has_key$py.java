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
@Filename("lib2to3/fixes/fix_has_key.py")
public class fix_has_key$py extends PyFunctionTable implements PyRunnable {
   static fix_has_key$py self;
   static final PyCode f$0;
   static final PyCode FixHasKey$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for has_key().\n\nCalls to .has_key() methods are expressed in terms of the 'in'\noperator:\n\n    d.has_key(k) -> k in d\n\nCAVEATS:\n1) While the primary target of this fixer is dict.has_key(), the\n   fixer will change any has_key() method call, regardless of its\n   class.\n\n2) Cases like this will not be converted:\n\n    m = d.has_key\n    if m(k):\n        ...\n\n   Only *calls* to has_key() are converted. While it is possible to\n   convert the above to something like\n\n    m = d.__contains__\n    if m(k):\n        ...\n\n   this is currently not done.\n"));
      var1.setline(30);
      PyString.fromInterned("Fixer for has_key().\n\nCalls to .has_key() methods are expressed in terms of the 'in'\noperator:\n\n    d.has_key(k) -> k in d\n\nCAVEATS:\n1) While the primary target of this fixer is dict.has_key(), the\n   fixer will change any has_key() method call, regardless of its\n   class.\n\n2) Cases like this will not be converted:\n\n    m = d.has_key\n    if m(k):\n        ...\n\n   Only *calls* to has_key() are converted. While it is possible to\n   convert the above to something like\n\n    m = d.__contains__\n    if m(k):\n        ...\n\n   this is currently not done.\n");
      var1.setline(33);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(34);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(35);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(36);
      var3 = new String[]{"Name", "parenthesize"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("parenthesize", var4);
      var4 = null;
      var1.setline(39);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixHasKey", var5, FixHasKey$1);
      var1.setlocal("FixHasKey", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixHasKey$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(40);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(42);
      PyString var4 = PyString.fromInterned("\n    anchor=power<\n        before=any+\n        trailer< '.' 'has_key' >\n        trailer<\n            '('\n            ( not(arglist | argument<any '=' any>) arg=any\n            | arglist<(not argument<any '=' any>) arg=any ','>\n            )\n            ')'\n        >\n        after=any*\n    >\n    |\n    negation=not_test<\n        'not'\n        anchor=power<\n            before=any+\n            trailer< '.' 'has_key' >\n            trailer<\n                '('\n                ( not(arglist | argument<any '=' any>) arg=any\n                | arglist<(not argument<any '=' any>) arg=any ','>\n                )\n                ')'\n            >\n        >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(72);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(74);
         PyObject var3 = var1.getlocal(0).__getattr__("syms");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(75);
         var3 = var1.getlocal(1).__getattr__("parent").__getattr__("type");
         var10000 = var3._eq(var1.getlocal(3).__getattr__("not_test"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("pattern").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(79);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(80);
            PyObject var4 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("negation"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(81);
            var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("anchor"));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(82);
            var4 = var1.getlocal(1).__getattr__("prefix");
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(83);
            PyList var10 = new PyList();
            var4 = var10.__getattr__("append");
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(83);
            var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("before")).__iter__();

            while(true) {
               var1.setline(83);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(83);
                  var1.dellocal(8);
                  PyList var7 = var10;
                  var1.setlocal(7, var7);
                  var4 = null;
                  var1.setline(84);
                  var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("arg")).__getattr__("clone").__call__(var2);
                  var1.setlocal(10, var4);
                  var4 = null;
                  var1.setline(85);
                  var4 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("after"));
                  var1.setlocal(11, var4);
                  var4 = null;
                  var1.setline(86);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(87);
                     var10 = new PyList();
                     var4 = var10.__getattr__("append");
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(87);
                     var4 = var1.getlocal(11).__iter__();

                     while(true) {
                        var1.setline(87);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(87);
                           var1.dellocal(12);
                           var7 = var10;
                           var1.setlocal(11, var7);
                           var4 = null;
                           break;
                        }

                        var1.setlocal(9, var5);
                        var1.setline(87);
                        var1.getlocal(12).__call__(var2, var1.getlocal(9).__getattr__("clone").__call__(var2));
                     }
                  }

                  var1.setline(88);
                  var4 = var1.getlocal(10).__getattr__("type");
                  var10000 = var4._in(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("comparison"), var1.getlocal(3).__getattr__("not_test"), var1.getlocal(3).__getattr__("and_test"), var1.getlocal(3).__getattr__("or_test"), var1.getlocal(3).__getattr__("test"), var1.getlocal(3).__getattr__("lambdef"), var1.getlocal(3).__getattr__("argument")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(90);
                     var4 = var1.getglobal("parenthesize").__call__(var2, var1.getlocal(10));
                     var1.setlocal(10, var4);
                     var4 = null;
                  }

                  var1.setline(91);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                  var10000 = var4._eq(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(92);
                     var4 = var1.getlocal(7).__getitem__(Py.newInteger(0));
                     var1.setlocal(7, var4);
                     var4 = null;
                  } else {
                     var1.setline(94);
                     var4 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(3).__getattr__("power"), var1.getlocal(7));
                     var1.setlocal(7, var4);
                     var4 = null;
                  }

                  var1.setline(95);
                  PyUnicode var8 = PyUnicode.fromInterned(" ");
                  var1.getlocal(7).__setattr__((String)"prefix", var8);
                  var4 = null;
                  var1.setline(96);
                  var10000 = var1.getglobal("Name");
                  PyObject[] var9 = new PyObject[]{PyUnicode.fromInterned("in"), PyUnicode.fromInterned(" ")};
                  String[] var6 = new String[]{"prefix"};
                  var10000 = var10000.__call__(var2, var9, var6);
                  var4 = null;
                  var4 = var10000;
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(97);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(98);
                     var10000 = var1.getglobal("Name");
                     var9 = new PyObject[]{PyUnicode.fromInterned("not"), PyUnicode.fromInterned(" ")};
                     var6 = new String[]{"prefix"};
                     var10000 = var10000.__call__(var2, var9, var6);
                     var4 = null;
                     var4 = var10000;
                     var1.setlocal(14, var4);
                     var4 = null;
                     var1.setline(99);
                     var4 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("comp_op"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(13)})));
                     var1.setlocal(13, var4);
                     var4 = null;
                  }

                  var1.setline(100);
                  var4 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("comparison"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(13), var1.getlocal(7)})));
                  var1.setlocal(15, var4);
                  var4 = null;
                  var1.setline(101);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(102);
                     var4 = var1.getglobal("parenthesize").__call__(var2, var1.getlocal(15));
                     var1.setlocal(15, var4);
                     var4 = null;
                     var1.setline(103);
                     var4 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(3).__getattr__("power"), (new PyTuple(new PyObject[]{var1.getlocal(15)}))._add(var1.getglobal("tuple").__call__(var2, var1.getlocal(11))));
                     var1.setlocal(15, var4);
                     var4 = null;
                  }

                  var1.setline(104);
                  var4 = var1.getlocal(1).__getattr__("parent").__getattr__("type");
                  var10000 = var4._in(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("comparison"), var1.getlocal(3).__getattr__("expr"), var1.getlocal(3).__getattr__("xor_expr"), var1.getlocal(3).__getattr__("and_expr"), var1.getlocal(3).__getattr__("shift_expr"), var1.getlocal(3).__getattr__("arith_expr"), var1.getlocal(3).__getattr__("term"), var1.getlocal(3).__getattr__("factor"), var1.getlocal(3).__getattr__("power")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(108);
                     var4 = var1.getglobal("parenthesize").__call__(var2, var1.getlocal(15));
                     var1.setlocal(15, var4);
                     var4 = null;
                  }

                  var1.setline(109);
                  var4 = var1.getlocal(6);
                  var1.getlocal(15).__setattr__("prefix", var4);
                  var4 = null;
                  var1.setline(110);
                  var3 = var1.getlocal(15);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(9, var5);
               var1.setline(83);
               var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("clone").__call__(var2));
            }
         }
      }
   }

   public fix_has_key$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixHasKey$1 = Py.newCode(0, var2, var1, "FixHasKey", 39, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "negation", "anchor", "prefix", "before", "_[83_18]", "n", "arg", "after", "_[87_21]", "n_op", "n_not", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 72, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_has_key$py("lib2to3/fixes/fix_has_key$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_has_key$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixHasKey$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
