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
import org.python.core.PyInteger;
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
@Filename("lib2to3/fixes/fix_tuple_params.py")
public class fix_tuple_params$py extends PyFunctionTable implements PyRunnable {
   static fix_tuple_params$py self;
   static final PyCode f$0;
   static final PyCode is_docstring$1;
   static final PyCode FixTupleParams$2;
   static final PyCode transform$3;
   static final PyCode handle_tuple$4;
   static final PyCode transform_lambda$5;
   static final PyCode simplify_args$6;
   static final PyCode find_params$7;
   static final PyCode map_to_index$8;
   static final PyCode tuple_name$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for function definitions with tuple parameters.\n\ndef func(((a, b), c), d):\n    ...\n\n    ->\n\ndef func(x, d):\n    ((a, b), c) = x\n    ...\n\nIt will also support lambdas:\n\n    lambda (x, y): x + y -> lambda t: t[0] + t[1]\n\n    # The parens are a syntax error in Python 3\n    lambda (x): x + y -> lambda x: x + y\n"));
      var1.setline(18);
      PyString.fromInterned("Fixer for function definitions with tuple parameters.\n\ndef func(((a, b), c), d):\n    ...\n\n    ->\n\ndef func(x, d):\n    ((a, b), c) = x\n    ...\n\nIt will also support lambdas:\n\n    lambda (x, y): x + y -> lambda t: t[0] + t[1]\n\n    # The parens are a syntax error in Python 3\n    lambda (x): x + y -> lambda x: x + y\n");
      var1.setline(22);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(23);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(24);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(25);
      var3 = new String[]{"Assign", "Name", "Newline", "Number", "Subscript", "syms"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Assign", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Newline", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Number", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("Subscript", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(27);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, is_docstring$1, (PyObject)null);
      var1.setlocal("is_docstring", var6);
      var3 = null;
      var1.setline(31);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixTupleParams", var5, FixTupleParams$2);
      var1.setlocal("FixTupleParams", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(139);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, simplify_args$6, (PyObject)null);
      var1.setlocal("simplify_args", var6);
      var3 = null;
      var1.setline(150);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, find_params$7, (PyObject)null);
      var1.setlocal("find_params", var6);
      var3 = null;
      var1.setline(157);
      var5 = new PyObject[]{new PyList(Py.EmptyObjects), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, map_to_index$8, (PyObject)null);
      var1.setlocal("map_to_index", var6);
      var3 = null;
      var1.setline(168);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, tuple_name$9, (PyObject)null);
      var1.setlocal("tuple_name", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_docstring$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("pytree").__getattr__("Node"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("token").__getattr__("STRING"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FixTupleParams$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyInteger var3 = Py.newInteger(4);
      var1.setlocal("run_order", var3);
      var3 = null;
      var1.setline(34);
      PyObject var4 = var1.getname("True");
      var1.setlocal("BM_compatible", var4);
      var3 = null;
      var1.setline(36);
      PyString var5 = PyString.fromInterned("\n              funcdef< 'def' any parameters< '(' args=any ')' >\n                       ['->' any] ':' suite=any+ >\n              |\n              lambda=\n              lambdef< 'lambda' args=vfpdef< '(' inner=any ')' >\n                       ':' body=any\n              >\n              ");
      var1.setlocal("PATTERN", var5);
      var3 = null;
      var1.setline(46);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$3, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      var1.setline(110);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, transform_lambda$5, (PyObject)null);
      var1.setlocal("transform_lambda", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 1);
      var1.setline(47);
      PyString var3 = PyString.fromInterned("lambda");
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         PyObject var8 = var1.getderef(1).__getattr__("transform_lambda").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(50);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setderef(2, var4);
         var4 = null;
         var1.setline(51);
         PyObject var9 = var1.getlocal(2).__getitem__(PyString.fromInterned("suite"));
         var1.setlocal(3, var9);
         var4 = null;
         var1.setline(52);
         var9 = var1.getlocal(2).__getitem__(PyString.fromInterned("args"));
         var1.setlocal(4, var9);
         var4 = null;
         var1.setline(55);
         var9 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("type");
         var10000 = var9._eq(var1.getglobal("token").__getattr__("INDENT"));
         var4 = null;
         PyInteger var11;
         PyUnicode var13;
         if (var10000.__nonzero__()) {
            var1.setline(56);
            var11 = Py.newInteger(2);
            var1.setlocal(5, var11);
            var4 = null;
            var1.setline(57);
            var9 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("value");
            var1.setlocal(6, var9);
            var4 = null;
            var1.setline(58);
            var9 = var1.getglobal("Newline").__call__(var2);
            var1.setderef(0, var9);
            var4 = null;
         } else {
            var1.setline(60);
            var11 = Py.newInteger(0);
            var1.setlocal(5, var11);
            var4 = null;
            var1.setline(61);
            var13 = PyUnicode.fromInterned("; ");
            var1.setlocal(6, var13);
            var4 = null;
            var1.setline(62);
            var9 = var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("INDENT"), (PyObject)PyUnicode.fromInterned(""));
            var1.setderef(0, var9);
            var4 = null;
         }

         var1.setline(67);
         PyObject[] var14 = new PyObject[]{var1.getglobal("False")};
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var14;
         PyCode var10004 = handle_tuple$4;
         var14 = new PyObject[]{var1.getclosure(1), var1.getclosure(2), var1.getclosure(0)};
         PyFunction var15 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var14);
         var1.setlocal(7, var15);
         var4 = null;
         var1.setline(78);
         var9 = var1.getlocal(4).__getattr__("type");
         var10000 = var9._eq(var1.getglobal("syms").__getattr__("tfpdef"));
         var4 = null;
         PyObject var5;
         PyObject[] var6;
         PyObject var10;
         if (var10000.__nonzero__()) {
            var1.setline(79);
            var1.getlocal(7).__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(80);
            var9 = var1.getlocal(4).__getattr__("type");
            var10000 = var9._eq(var1.getglobal("syms").__getattr__("typedargslist"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(81);
               var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(4).__getattr__("children")).__iter__();

               while(true) {
                  var1.setline(81);
                  var5 = var9.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(8, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(9, var7);
                  var7 = null;
                  var1.setline(82);
                  var10 = var1.getlocal(9).__getattr__("type");
                  var10000 = var10._eq(var1.getglobal("syms").__getattr__("tfpdef"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(85);
                     var10000 = var1.getlocal(7);
                     var6 = new PyObject[]{var1.getlocal(9), null};
                     var7 = var1.getlocal(8);
                     var10002 = var7._gt(Py.newInteger(0));
                     var7 = null;
                     var6[1] = var10002;
                     String[] var12 = new String[]{"add_prefix"};
                     var10000.__call__(var2, var6, var12);
                     var6 = null;
                  }
               }
            }
         }

         var1.setline(87);
         if (var1.getderef(2).__not__().__nonzero__()) {
            var1.setline(88);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(92);
            var9 = var1.getderef(2).__iter__();

            while(true) {
               var1.setline(92);
               var5 = var9.__iternext__();
               if (var5 == null) {
                  var1.setline(96);
                  var9 = var1.getlocal(5);
                  var1.setlocal(11, var9);
                  var4 = null;
                  var1.setline(97);
                  var9 = var1.getlocal(5);
                  var10000 = var9._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(98);
                     var13 = PyUnicode.fromInterned(" ");
                     var1.getderef(2).__getitem__(Py.newInteger(0)).__setattr__((String)"prefix", var13);
                     var4 = null;
                  } else {
                     var1.setline(99);
                     if (var1.getglobal("is_docstring").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(var1.getlocal(5))).__nonzero__()) {
                        var1.setline(100);
                        var9 = var1.getlocal(6);
                        var1.getderef(2).__getitem__(Py.newInteger(0)).__setattr__("prefix", var9);
                        var4 = null;
                        var1.setline(101);
                        var9 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(11, var9);
                        var4 = null;
                     }
                  }

                  var1.setline(103);
                  var9 = var1.getderef(2).__iter__();

                  while(true) {
                     var1.setline(103);
                     var5 = var9.__iternext__();
                     if (var5 == null) {
                        var1.setline(105);
                        var9 = var1.getderef(2);
                        var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("children").__setslice__(var1.getlocal(11), var1.getlocal(11), (PyObject)null, var9);
                        var4 = null;
                        var1.setline(106);
                        var9 = var1.getglobal("range").__call__(var2, var1.getlocal(11)._add(Py.newInteger(1)), var1.getlocal(11)._add(var1.getglobal("len").__call__(var2, var1.getderef(2)))._add(Py.newInteger(1))).__iter__();

                        while(true) {
                           var1.setline(106);
                           var5 = var9.__iternext__();
                           if (var5 == null) {
                              var1.setline(108);
                              var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("changed").__call__(var2);
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setlocal(8, var5);
                           var1.setline(107);
                           var10 = var1.getlocal(6);
                           var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(var1.getlocal(8)).__setattr__("prefix", var10);
                           var6 = null;
                        }
                     }

                     var1.setlocal(10, var5);
                     var1.setline(104);
                     var10 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                     var1.getlocal(10).__setattr__("parent", var10);
                     var6 = null;
                  }
               }

               var1.setlocal(10, var5);
               var1.setline(93);
               var10 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var1.getlocal(10).__setattr__("parent", var10);
               var6 = null;
            }
         }
      }
   }

   public PyObject handle_tuple$4(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getglobal("Name").__call__(var2, var1.getderef(0).__getattr__("new_name").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getlocal(0).__getattr__("clone").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(70);
      PyUnicode var4 = PyUnicode.fromInterned("");
      var1.getlocal(3).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("Assign").__call__(var2, var1.getlocal(3), var1.getlocal(2).__getattr__("clone").__call__(var2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(72);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(73);
         var4 = PyUnicode.fromInterned(" ");
         var1.getlocal(2).__setattr__((String)"prefix", var4);
         var3 = null;
      }

      var1.setline(74);
      var1.getlocal(0).__getattr__("replace").__call__(var2, var1.getlocal(2));
      var1.setline(75);
      var1.getderef(1).__getattr__("append").__call__(var2, var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("simple_stmt"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(4), var1.getderef(2).__getattr__("clone").__call__(var2)}))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transform_lambda$5(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("args"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("body"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getglobal("simplify_args").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("inner")));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getlocal(5).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(117);
         var3 = var1.getlocal(5).__getattr__("clone").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(118);
         PyUnicode var10 = PyUnicode.fromInterned(" ");
         var1.getlocal(5).__setattr__((String)"prefix", var10);
         var3 = null;
         var1.setline(119);
         var1.getlocal(3).__getattr__("replace").__call__(var2, var1.getlocal(5));
         var1.setline(120);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(122);
         var3 = var1.getglobal("find_params").__call__(var2, var1.getlocal(3));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(123);
         var3 = var1.getglobal("map_to_index").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(124);
         var3 = var1.getlocal(0).__getattr__("new_name").__call__(var2, var1.getglobal("tuple_name").__call__(var2, var1.getlocal(6)));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(126);
         var10000 = var1.getglobal("Name");
         PyObject[] var9 = new PyObject[]{var1.getlocal(8), PyUnicode.fromInterned(" ")};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var9, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(127);
         var1.getlocal(3).__getattr__("replace").__call__(var2, var1.getlocal(9).__getattr__("clone").__call__(var2));
         var1.setline(128);
         var3 = var1.getlocal(4).__getattr__("post_order").__call__(var2).__iter__();

         while(true) {
            PyObject var5;
            do {
               var1.setline(128);
               PyObject var7 = var3.__iternext__();
               if (var7 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(10, var7);
               var1.setline(129);
               var5 = var1.getlocal(10).__getattr__("type");
               var10000 = var5._eq(var1.getglobal("token").__getattr__("NAME"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(10).__getattr__("value");
                  var10000 = var5._in(var1.getlocal(7));
                  var5 = null;
               }
            } while(!var10000.__nonzero__());

            var1.setline(130);
            PyList var11 = new PyList();
            var5 = var11.__getattr__("append");
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(130);
            var5 = var1.getlocal(7).__getitem__(var1.getlocal(10).__getattr__("value")).__iter__();

            while(true) {
               var1.setline(130);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(130);
                  var1.dellocal(12);
                  PyList var8 = var11;
                  var1.setlocal(11, var8);
                  var5 = null;
                  var1.setline(131);
                  var5 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getglobal("syms").__getattr__("power"), (new PyList(new PyObject[]{var1.getlocal(9).__getattr__("clone").__call__(var2)}))._add(var1.getlocal(11)));
                  var1.setlocal(14, var5);
                  var5 = null;
                  var1.setline(133);
                  var5 = var1.getlocal(10).__getattr__("prefix");
                  var1.getlocal(14).__setattr__("prefix", var5);
                  var5 = null;
                  var1.setline(134);
                  var1.getlocal(10).__getattr__("replace").__call__(var2, var1.getlocal(14));
                  break;
               }

               var1.setlocal(13, var6);
               var1.setline(130);
               var1.getlocal(12).__call__(var2, var1.getlocal(13).__getattr__("clone").__call__(var2));
            }
         }
      }
   }

   public PyObject simplify_args$6(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("syms").__getattr__("vfplist"), var1.getglobal("token").__getattr__("NAME")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(141);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(142);
         PyObject var4 = var1.getlocal(0).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("syms").__getattr__("vfpdef"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(148);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, PyString.fromInterned("Received unexpected node %s")._mod(var1.getlocal(0))));
         } else {
            while(true) {
               var1.setline(145);
               var4 = var1.getlocal(0).__getattr__("type");
               var10000 = var4._eq(var1.getglobal("syms").__getattr__("vfpdef"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(147);
                  var3 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(146);
               var4 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1));
               var1.setlocal(0, var4);
               var4 = null;
            }
         }
      }
   }

   public PyObject find_params$7(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("vfpdef"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         var3 = var1.getglobal("find_params").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(153);
         PyObject var4 = var1.getlocal(0).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(154);
            var3 = var1.getlocal(0).__getattr__("value");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(155);
            PyList var8 = new PyList();
            var4 = var8.__getattr__("append");
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(155);
            var4 = var1.getlocal(0).__getattr__("children").__iter__();

            while(true) {
               var1.setline(155);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(155);
                  var1.dellocal(1);
                  PyList var7 = var8;
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setlocal(2, var5);
               var1.setline(155);
               PyObject var6 = var1.getlocal(2).__getattr__("type");
               PyObject var10001 = var6._ne(var1.getglobal("token").__getattr__("COMMA"));
               var6 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(155);
                  var1.getlocal(1).__call__(var2, var1.getglobal("find_params").__call__(var2, var1.getlocal(2)));
               }
            }
         }
      }
   }

   public PyObject map_to_index$8(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(159);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(160);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(160);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(166);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(161);
         PyList var8 = new PyList(new PyObject[]{var1.getglobal("Subscript").__call__(var2, var1.getglobal("Number").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(3))))});
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(162);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")).__nonzero__()) {
            var1.setline(163);
            var10000 = var1.getglobal("map_to_index");
            var5 = new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)};
            String[] var10 = new String[]{"d"};
            var10000.__call__(var2, var5, var10);
            var5 = null;
         } else {
            var1.setline(165);
            PyObject var9 = var1.getlocal(1)._add(var1.getlocal(5));
            var1.getlocal(2).__setitem__(var1.getlocal(4), var9);
            var5 = null;
         }
      }
   }

   public PyObject tuple_name$9(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(170);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(170);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(175);
            var5 = PyUnicode.fromInterned("_").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(171);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("list")).__nonzero__()) {
            var1.setline(172);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("tuple_name").__call__(var2, var1.getlocal(2)));
         } else {
            var1.setline(174);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public fix_tuple_params$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"stmt"};
      is_docstring$1 = Py.newCode(1, var2, var1, "is_docstring", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FixTupleParams$2 = Py.newCode(0, var2, var1, "FixTupleParams", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "suite", "args", "start", "indent", "handle_tuple", "i", "arg", "line", "after", "end", "new_lines"};
      String[] var10001 = var2;
      fix_tuple_params$py var10007 = self;
      var2 = new String[]{"end", "self", "new_lines"};
      transform$3 = Py.newCode(3, var10001, var1, "transform", 46, false, false, var10007, 3, var2, (String[])null, 2, 4097);
      var2 = new String[]{"tuple_arg", "add_prefix", "n", "arg", "stmt"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "new_lines", "end"};
      handle_tuple$4 = Py.newCode(2, var10001, var1, "handle_tuple", 67, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "node", "results", "args", "body", "inner", "params", "to_index", "tup_name", "new_param", "n", "subscripts", "_[130_30]", "c", "new"};
      transform_lambda$5 = Py.newCode(3, var2, var1, "transform_lambda", 110, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      simplify_args$6 = Py.newCode(1, var2, var1, "simplify_args", 139, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "_[155_12]", "c"};
      find_params$7 = Py.newCode(1, var2, var1, "find_params", 150, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"param_list", "prefix", "d", "i", "obj", "trailer"};
      map_to_index$8 = Py.newCode(3, var2, var1, "map_to_index", 157, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"param_list", "l", "obj"};
      tuple_name$9 = Py.newCode(1, var2, var1, "tuple_name", 168, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_tuple_params$py("lib2to3/fixes/fix_tuple_params$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_tuple_params$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.is_docstring$1(var2, var3);
         case 2:
            return this.FixTupleParams$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         case 4:
            return this.handle_tuple$4(var2, var3);
         case 5:
            return this.transform_lambda$5(var2, var3);
         case 6:
            return this.simplify_args$6(var2, var3);
         case 7:
            return this.find_params$7(var2, var3);
         case 8:
            return this.map_to_index$8(var2, var3);
         case 9:
            return this.tuple_name$9(var2, var3);
         default:
            return null;
      }
   }
}
