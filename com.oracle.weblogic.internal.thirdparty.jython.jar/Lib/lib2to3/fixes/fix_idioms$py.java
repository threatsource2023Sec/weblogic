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
@Filename("lib2to3/fixes/fix_idioms.py")
public class fix_idioms$py extends PyFunctionTable implements PyRunnable {
   static fix_idioms$py self;
   static final PyCode f$0;
   static final PyCode FixIdioms$1;
   static final PyCode match$2;
   static final PyCode transform$3;
   static final PyCode transform_isinstance$4;
   static final PyCode transform_while$5;
   static final PyCode transform_sort$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Adjust some old Python 2 idioms to their modern counterparts.\n\n* Change some type comparisons to isinstance() calls:\n    type(x) == T -> isinstance(x, T)\n    type(x) is T -> isinstance(x, T)\n    type(x) != T -> not isinstance(x, T)\n    type(x) is not T -> not isinstance(x, T)\n\n* Change \"while 1:\" into \"while True:\".\n\n* Change both\n\n    v = list(EXPR)\n    v.sort()\n    foo(v)\n\nand the more general\n\n    v = EXPR\n    v.sort()\n    foo(v)\n\ninto\n\n    v = sorted(EXPR)\n    foo(v)\n"));
      var1.setline(27);
      PyString.fromInterned("Adjust some old Python 2 idioms to their modern counterparts.\n\n* Change some type comparisons to isinstance() calls:\n    type(x) == T -> isinstance(x, T)\n    type(x) is T -> isinstance(x, T)\n    type(x) != T -> not isinstance(x, T)\n    type(x) is not T -> not isinstance(x, T)\n\n* Change \"while 1:\" into \"while True:\".\n\n* Change both\n\n    v = list(EXPR)\n    v.sort()\n    foo(v)\n\nand the more general\n\n    v = EXPR\n    v.sort()\n    foo(v)\n\ninto\n\n    v = sorted(EXPR)\n    foo(v)\n");
      var1.setline(31);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(32);
      var3 = new String[]{"Call", "Comma", "Name", "Node", "BlankLine", "syms"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Node", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("BlankLine", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(34);
      PyString var6 = PyString.fromInterned("(n='!=' | '==' | 'is' | n=comp_op< 'is' 'not' >)");
      var1.setlocal("CMP", var6);
      var3 = null;
      var1.setline(35);
      var6 = PyString.fromInterned("power< 'type' trailer< '(' x=any ')' > >");
      var1.setlocal("TYPE", var6);
      var3 = null;
      var1.setline(37);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixIdioms", var5, FixIdioms$1);
      var1.setlocal("FixIdioms", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixIdioms$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(38);
      PyObject var3 = var1.getname("True");
      var1.setlocal("explicit", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned("\n        isinstance=comparison< %s %s T=any >\n        |\n        isinstance=comparison< T=any %s %s >\n        |\n        while_stmt< 'while' while='1' ':' any+ >\n        |\n        sorted=any<\n            any*\n            simple_stmt<\n              expr_stmt< id1=any '='\n                         power< list='list' trailer< '(' (not arglist<any+>) any ')' > >\n              >\n              '\\n'\n            >\n            sort=\n            simple_stmt<\n              power< id2=any\n                     trailer< '.' 'sort' > trailer< '(' ')' >\n              >\n              '\\n'\n            >\n            next=any*\n        >\n        |\n        sorted=any<\n            any*\n            simple_stmt< expr_stmt< id1=any '=' expr=any > '\\n' >\n            sort=\n            simple_stmt<\n              power< id2=any\n                     trailer< '.' 'sort' > trailer< '(' ')' >\n              >\n              '\\n'\n            >\n            next=any*\n        >\n    ")._mod(new PyTuple(new PyObject[]{var1.getname("TYPE"), var1.getname("CMP"), var1.getname("CMP"), var1.getname("TYPE")}));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(79);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, match$2, (PyObject)null);
      var1.setlocal("match", var5);
      var3 = null;
      var1.setline(90);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, transform$3, (PyObject)null);
      var1.setlocal("transform", var5);
      var3 = null;
      var1.setline(100);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, transform_isinstance$4, (PyObject)null);
      var1.setlocal("transform_isinstance", var5);
      var3 = null;
      var1.setline(112);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, transform_while$5, (PyObject)null);
      var1.setlocal("transform_while", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, transform_sort$6, (PyObject)null);
      var1.setlocal("transform_sort", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject match$2(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("FixIdioms"), var1.getlocal(0)).__getattr__("match").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(84);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         PyString var4 = PyString.fromInterned("sorted");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(85);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("id1"));
         var10000 = var3._eq(var1.getlocal(2).__getitem__(PyString.fromInterned("id2")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(86);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(87);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(88);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString var3 = PyString.fromInterned("isinstance");
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(92);
         var5 = var1.getlocal(0).__getattr__("transform_isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(93);
         PyString var4 = PyString.fromInterned("while");
         var10000 = var4._in(var1.getlocal(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(94);
            var5 = var1.getlocal(0).__getattr__("transform_while").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(95);
            var4 = PyString.fromInterned("sorted");
            var10000 = var4._in(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(96);
               var5 = var1.getlocal(0).__getattr__("transform_sort").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(98);
               throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid match")));
            }
         }
      }
   }

   public PyObject transform_isinstance$4(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("x")).__getattr__("clone").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("T")).__getattr__("clone").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(103);
      PyUnicode var4 = PyUnicode.fromInterned("");
      var1.getlocal(3).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(104);
      var4 = PyUnicode.fromInterned(" ");
      var1.getlocal(4).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(105);
      var3 = var1.getglobal("Call").__call__((ThreadState)var2, (PyObject)var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("isinstance")), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getglobal("Comma").__call__(var2), var1.getlocal(4)})));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(106);
      PyString var5 = PyString.fromInterned("n");
      PyObject var10000 = var5._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var4 = PyUnicode.fromInterned(" ");
         var1.getlocal(5).__setattr__((String)"prefix", var4);
         var3 = null;
         var1.setline(108);
         var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("not_test"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("not")), var1.getlocal(5)})));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(109);
      var3 = var1.getlocal(1).__getattr__("prefix");
      var1.getlocal(5).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject transform_while$5(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("while"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(114);
      PyObject var10000 = var1.getlocal(3).__getattr__("replace");
      PyObject var10002 = var1.getglobal("Name");
      PyObject[] var5 = new PyObject[]{PyUnicode.fromInterned("True"), var1.getlocal(3).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform_sort$6(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("sort"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("next"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("list"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expr"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(122);
      PyObject var10000;
      PyObject var10002;
      String[] var4;
      PyObject[] var5;
      PyUnicode var6;
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(123);
         var10000 = var1.getlocal(5).__getattr__("replace");
         var10002 = var1.getglobal("Name");
         var5 = new PyObject[]{PyUnicode.fromInterned("sorted"), var1.getlocal(5).__getattr__("prefix")};
         var4 = new String[]{"prefix"};
         var10002 = var10002.__call__(var2, var5, var4);
         var3 = null;
         var10000.__call__(var2, var10002);
      } else {
         var1.setline(124);
         if (!var1.getlocal(6).__nonzero__()) {
            var1.setline(130);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("should not have reached here")));
         }

         var1.setline(125);
         var3 = var1.getlocal(6).__getattr__("clone").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(126);
         var6 = PyUnicode.fromInterned("");
         var1.getlocal(7).__setattr__((String)"prefix", var6);
         var3 = null;
         var1.setline(127);
         var10000 = var1.getlocal(6).__getattr__("replace");
         var10002 = var1.getglobal("Call");
         var5 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("sorted")), new PyList(new PyObject[]{var1.getlocal(7)}), var1.getlocal(6).__getattr__("prefix")};
         var4 = new String[]{"prefix"};
         var10002 = var10002.__call__(var2, var5, var4);
         var3 = null;
         var10000.__call__(var2, var10002);
      }

      var1.setline(131);
      var1.getlocal(3).__getattr__("remove").__call__(var2);
      var1.setline(133);
      var3 = var1.getlocal(3).__getattr__("prefix");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(136);
      var6 = PyUnicode.fromInterned("\n");
      var10000 = var6._in(var1.getlocal(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(137);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(141);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("rpartition").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n")).__getitem__(Py.newInteger(0)), var1.getlocal(4).__getitem__(Py.newInteger(0)).__getattr__("prefix")});
            var1.setlocal(9, var7);
            var3 = null;
            var1.setline(142);
            var3 = PyUnicode.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(9));
            var1.getlocal(4).__getitem__(Py.newInteger(0)).__setattr__("prefix", var3);
            var3 = null;
         } else {
            var1.setline(144);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(5).__getattr__("parent").__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }

            var1.setline(145);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(5).__getattr__("next_sibling");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(147);
            var3 = var1.getglobal("BlankLine").__call__(var2);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(148);
            var1.getlocal(5).__getattr__("parent").__getattr__("append_child").__call__(var2, var1.getlocal(10));
            var1.setline(149);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(5).__getattr__("next_sibling");
               var10000 = var3._is(var1.getlocal(10));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(152);
            var3 = var1.getlocal(8).__getattr__("rpartition").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n")).__getitem__(Py.newInteger(0));
            var1.getlocal(10).__setattr__("prefix", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_idioms$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixIdioms$1 = Py.newCode(0, var2, var1, "FixIdioms", 37, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "r"};
      match$2 = Py.newCode(2, var2, var1, "match", 79, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      transform$3 = Py.newCode(3, var2, var1, "transform", 90, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "x", "T", "test"};
      transform_isinstance$4 = Py.newCode(3, var2, var1, "transform_isinstance", 100, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "one"};
      transform_while$5 = Py.newCode(3, var2, var1, "transform_while", 112, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "sort_stmt", "next_stmt", "list_call", "simple_expr", "new", "btwn", "prefix_lines", "end_line"};
      transform_sort$6 = Py.newCode(3, var2, var1, "transform_sort", 116, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_idioms$py("lib2to3/fixes/fix_idioms$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_idioms$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixIdioms$1(var2, var3);
         case 2:
            return this.match$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         case 4:
            return this.transform_isinstance$4(var2, var3);
         case 5:
            return this.transform_while$5(var2, var3);
         case 6:
            return this.transform_sort$6(var2, var3);
         default:
            return null;
      }
   }
}
