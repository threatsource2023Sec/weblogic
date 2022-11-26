package lib2to3.fixes;

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
@Filename("lib2to3/fixes/fix_dict.py")
public class fix_dict$py extends PyFunctionTable implements PyRunnable {
   static fix_dict$py self;
   static final PyCode f$0;
   static final PyCode FixDict$1;
   static final PyCode transform$2;
   static final PyCode in_special_context$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for dict methods.\n\nd.keys() -> list(d.keys())\nd.items() -> list(d.items())\nd.values() -> list(d.values())\n\nd.iterkeys() -> iter(d.keys())\nd.iteritems() -> iter(d.items())\nd.itervalues() -> iter(d.values())\n\nd.viewkeys() -> d.keys()\nd.viewitems() -> d.items()\nd.viewvalues() -> d.values()\n\nExcept in certain very specific contexts: the iter() can be dropped\nwhen the context is list(), sorted(), iter() or for...in; the list()\ncan be dropped when the context is list() or sorted() (but not iter()\nor for...in!). Special contexts that apply to both: list(), sorted(), tuple()\nset(), any(), all(), sum().\n\nNote: iter(d.keys()) could be written as iter(d) but since the\noriginal d.iterkeys() was also redundant we don't fix this.  And there\nare (rare) contexts where it makes a difference (e.g. when passing it\nas an argument to a function that introspects the argument).\n"));
      var1.setline(28);
      PyString.fromInterned("Fixer for dict methods.\n\nd.keys() -> list(d.keys())\nd.items() -> list(d.items())\nd.values() -> list(d.values())\n\nd.iterkeys() -> iter(d.keys())\nd.iteritems() -> iter(d.items())\nd.itervalues() -> iter(d.values())\n\nd.viewkeys() -> d.keys()\nd.viewitems() -> d.items()\nd.viewvalues() -> d.values()\n\nExcept in certain very specific contexts: the iter() can be dropped\nwhen the context is list(), sorted(), iter() or for...in; the list()\ncan be dropped when the context is list() or sorted() (but not iter()\nor for...in!). Special contexts that apply to both: list(), sorted(), tuple()\nset(), any(), all(), sum().\n\nNote: iter(d.keys()) could be written as iter(d) but since the\noriginal d.iterkeys() was also redundant we don't fix this.  And there\nare (rare) contexts where it makes a difference (e.g. when passing it\nas an argument to a function that introspects the argument).\n");
      var1.setline(31);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(32);
      var3 = new String[]{"patcomp"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("patcomp", var4);
      var4 = null;
      var1.setline(33);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(34);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(35);
      var3 = new String[]{"Name", "Call", "LParen", "RParen", "ArgList", "Dot"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("LParen", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("RParen", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("ArgList", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("Dot", var4);
      var4 = null;
      var1.setline(36);
      var3 = new String[]{"fixer_util"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_util", var4);
      var4 = null;
      var1.setline(39);
      PyObject var6 = var1.getname("fixer_util").__getattr__("consuming_calls")._or(var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("iter")}))));
      var1.setlocal("iter_exempt", var6);
      var3 = null;
      var1.setline(42);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixDict", var5, FixDict$1);
      var1.setlocal("FixDict", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixDict$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(45);
      PyString var4 = PyString.fromInterned("\n    power< head=any+\n         trailer< '.' method=('keys'|'items'|'values'|\n                              'iterkeys'|'iteritems'|'itervalues'|\n                              'viewkeys'|'viewitems'|'viewvalues') >\n         parens=trailer< '(' ')' >\n         tail=any*\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(55);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      var1.setline(83);
      var4 = PyString.fromInterned("power< func=NAME trailer< '(' node=any ')' > any* >");
      var1.setlocal("P1", var4);
      var3 = null;
      var1.setline(84);
      var3 = var1.getname("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getname("P1"));
      var1.setlocal("p1", var3);
      var3 = null;
      var1.setline(86);
      var4 = PyString.fromInterned("for_stmt< 'for' any 'in' node=any ':' any* >\n            | comp_for< 'for' any 'in' node=any any* >\n         ");
      var1.setlocal("P2", var4);
      var3 = null;
      var1.setline(89);
      var3 = var1.getname("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getname("P2"));
      var1.setlocal("p2", var3);
      var3 = null;
      var1.setline(91);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, in_special_context$3, (PyObject)null);
      var1.setlocal("in_special_context", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("head"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("method")).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("tail"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(4).__getattr__("value");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("iter"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("view"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(63);
      PyObject var10000 = var1.getlocal(8);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(9);
      }

      if (var10000.__nonzero__()) {
         var1.setline(64);
         var3 = var1.getlocal(7).__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(65);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(7);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyUnicode.fromInterned("keys"), PyUnicode.fromInterned("items"), PyUnicode.fromInterned("values")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getglobal("repr").__call__(var2, var1.getlocal(4)));
         }
      }

      var1.setline(66);
      PyList var9 = new PyList();
      var3 = var9.__getattr__("append");
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(66);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(66);
            var1.dellocal(10);
            PyList var6 = var9;
            var1.setlocal(3, var6);
            var3 = null;
            var1.setline(67);
            var9 = new PyList();
            var3 = var9.__getattr__("append");
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(67);
            var3 = var1.getlocal(5).__iter__();

            while(true) {
               var1.setline(67);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(67);
                  var1.dellocal(12);
                  var6 = var9;
                  var1.setlocal(5, var6);
                  var3 = null;
                  var1.setline(68);
                  var10000 = var1.getlocal(5).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("in_special_context").__call__(var2, var1.getlocal(1), var1.getlocal(8));
                  }

                  var3 = var10000;
                  var1.setlocal(13, var3);
                  var3 = null;
                  var1.setline(69);
                  var10000 = var1.getlocal(3);
                  PyObject[] var10003 = new PyObject[2];
                  PyObject var10006 = var1.getglobal("pytree").__getattr__("Node");
                  PyObject var10008 = var1.getlocal(6).__getattr__("trailer");
                  PyObject[] var10011 = new PyObject[]{var1.getglobal("Dot").__call__(var2), null};
                  PyObject var10014 = var1.getglobal("Name");
                  PyObject[] var7 = new PyObject[]{var1.getlocal(7), var1.getlocal(4).__getattr__("prefix")};
                  String[] var5 = new String[]{"prefix"};
                  var10014 = var10014.__call__(var2, var7, var5);
                  var3 = null;
                  var10011[1] = var10014;
                  var10003[0] = var10006.__call__((ThreadState)var2, (PyObject)var10008, (PyObject)(new PyList(var10011)));
                  var10003[1] = var1.getlocal(2).__getitem__(PyString.fromInterned("parens")).__getattr__("clone").__call__(var2);
                  var3 = var10000._add(new PyList(var10003));
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(74);
                  var3 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(6).__getattr__("power"), var1.getlocal(14));
                  var1.setlocal(15, var3);
                  var3 = null;
                  var1.setline(75);
                  var10000 = var1.getlocal(13);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(9);
                  }

                  if (var10000.__not__().__nonzero__()) {
                     var1.setline(76);
                     PyUnicode var8 = PyUnicode.fromInterned("");
                     var1.getlocal(15).__setattr__((String)"prefix", var8);
                     var3 = null;
                     var1.setline(77);
                     var10000 = var1.getglobal("Call");
                     PyObject var10002 = var1.getglobal("Name");
                     var1.setline(77);
                     var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002.__call__((ThreadState)var2, (PyObject)(var1.getlocal(8).__nonzero__() ? PyUnicode.fromInterned("iter") : PyUnicode.fromInterned("list"))), (PyObject)(new PyList(new PyObject[]{var1.getlocal(15)})));
                     var1.setlocal(15, var3);
                     var3 = null;
                  }

                  var1.setline(78);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(79);
                     var3 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(6).__getattr__("power"), (new PyList(new PyObject[]{var1.getlocal(15)}))._add(var1.getlocal(5)));
                     var1.setlocal(15, var3);
                     var3 = null;
                  }

                  var1.setline(80);
                  var3 = var1.getlocal(1).__getattr__("prefix");
                  var1.getlocal(15).__setattr__("prefix", var3);
                  var3 = null;
                  var1.setline(81);
                  var3 = var1.getlocal(15);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(11, var4);
               var1.setline(67);
               var1.getlocal(12).__call__(var2, var1.getlocal(11).__getattr__("clone").__call__(var2));
            }
         }

         var1.setlocal(11, var4);
         var1.setline(66);
         var1.getlocal(10).__call__(var2, var1.getlocal(11).__getattr__("clone").__call__(var2));
      }
   }

   public PyObject in_special_context$3(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getlocal(1).__getattr__("parent");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(93);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(94);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(95);
         PyObject var5 = var1.getlocal(1).__getattr__("parent").__getattr__("parent");
         var10000 = var5._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("p1").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent").__getattr__("parent"), var1.getlocal(3));
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("node"));
               var10000 = var5._is(var1.getlocal(1));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(98);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(100);
               var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("func")).__getattr__("value");
               var10000 = var5._in(var1.getglobal("iter_exempt"));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(103);
               var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("func")).__getattr__("value");
               var10000 = var5._in(var1.getglobal("fixer_util").__getattr__("consuming_calls"));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(104);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(105);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(107);
               var10000 = var1.getlocal(0).__getattr__("p2").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent"), var1.getlocal(3));
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(3).__getitem__(PyString.fromInterned("node"));
                  var10000 = var5._is(var1.getlocal(1));
                  var4 = null;
               }

               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public fix_dict$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixDict$1 = Py.newCode(0, var2, var1, "FixDict", 42, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "head", "method", "tail", "syms", "method_name", "isiter", "isview", "_[66_16]", "n", "_[67_16]", "special", "args", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 55, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "isiter", "results"};
      in_special_context$3 = Py.newCode(3, var2, var1, "in_special_context", 91, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_dict$py("lib2to3/fixes/fix_dict$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_dict$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixDict$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         case 3:
            return this.in_special_context$3(var2, var3);
         default:
            return null;
      }
   }
}
