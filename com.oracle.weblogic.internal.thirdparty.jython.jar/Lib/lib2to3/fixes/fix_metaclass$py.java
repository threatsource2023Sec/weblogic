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
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_metaclass.py")
public class fix_metaclass$py extends PyFunctionTable implements PyRunnable {
   static fix_metaclass$py self;
   static final PyCode f$0;
   static final PyCode has_metaclass$1;
   static final PyCode fixup_parse_tree$2;
   static final PyCode fixup_simple_stmt$3;
   static final PyCode remove_trailing_newline$4;
   static final PyCode find_metas$5;
   static final PyCode fixup_indent$6;
   static final PyCode FixMetaclass$7;
   static final PyCode transform$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for __metaclass__ = X -> (metaclass=X) methods.\n\n   The various forms of classef (inherits nothing, inherits once, inherints\n   many) don't parse the same in the CST so we look at ALL classes for\n   a __metaclass__ and if we find one normalize the inherits to all be\n   an arglist.\n\n   For one-liner classes ('class X: pass') there is no indent/dedent so\n   we normalize those into having a suite.\n\n   Moving the __metaclass__ into the classdef can also cause the class\n   body to be empty so there is some special casing for that as well.\n\n   This fixer also tries very hard to keep original indenting and spacing\n   in all those corner cases.\n\n"));
      var1.setline(17);
      PyString.fromInterned("Fixer for __metaclass__ = X -> (metaclass=X) methods.\n\n   The various forms of classef (inherits nothing, inherits once, inherints\n   many) don't parse the same in the CST so we look at ALL classes for\n   a __metaclass__ and if we find one normalize the inherits to all be\n   an arglist.\n\n   For one-liner classes ('class X: pass') there is no indent/dedent so\n   we normalize those into having a suite.\n\n   Moving the __metaclass__ into the classdef can also cause the class\n   body to be empty so there is some special casing for that as well.\n\n   This fixer also tries very hard to keep original indenting and spacing\n   in all those corner cases.\n\n");
      var1.setline(21);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(22);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pygram", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(23);
      var3 = new String[]{"Name", "syms", "Node", "Leaf"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("syms", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Node", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Leaf", var4);
      var4 = null;
      var1.setline(26);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, has_metaclass$1, PyString.fromInterned(" we have to check the cls_node without changing it.\n        There are two possiblities:\n          1)  clsdef => suite => simple_stmt => expr_stmt => Leaf('__meta')\n          2)  clsdef => simple_stmt => expr_stmt => Leaf('__meta')\n    "));
      var1.setlocal("has_metaclass", var6);
      var3 = null;
      var1.setline(45);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fixup_parse_tree$2, PyString.fromInterned(" one-line classes don't get a suite in the parse tree so we add\n        one to normalize the tree\n    "));
      var1.setlocal("fixup_parse_tree", var6);
      var3 = null;
      var1.setline(71);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fixup_simple_stmt$3, PyString.fromInterned(" if there is a semi-colon all the parts count as part of the same\n        simple_stmt.  We just want the __metaclass__ part so we move\n        everything efter the semi-colon into its own simple_stmt node\n    "));
      var1.setlocal("fixup_simple_stmt", var6);
      var3 = null;
      var1.setline(95);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, remove_trailing_newline$4, (PyObject)null);
      var1.setlocal("remove_trailing_newline", var6);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, find_metas$5, (PyObject)null);
      var1.setlocal("find_metas", var6);
      var3 = null;
      var1.setline(123);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, fixup_indent$6, PyString.fromInterned(" If an INDENT is followed by a thing with a prefix then nuke the prefix\n        Otherwise we get in trouble when removing __metaclass__ at suite start\n    "));
      var1.setlocal("fixup_indent", var6);
      var3 = null;
      var1.setline(145);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixMetaclass", var5, FixMetaclass$7);
      var1.setlocal("FixMetaclass", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_metaclass$1(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned(" we have to check the cls_node without changing it.\n        There are two possiblities:\n          1)  clsdef => suite => simple_stmt => expr_stmt => Leaf('__meta')\n          2)  clsdef => simple_stmt => expr_stmt => Leaf('__meta')\n    ");
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getattr__("children").__iter__();

      while(true) {
         var1.setline(32);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(42);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(1, var4);
         var1.setline(33);
         var5 = var1.getlocal(1).__getattr__("type");
         PyObject var10000 = var5._eq(var1.getglobal("syms").__getattr__("suite"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(34);
            var5 = var1.getglobal("has_metaclass").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(35);
         PyObject var6 = var1.getlocal(1).__getattr__("type");
         var10000 = var6._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
         var6 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("children");
         }

         if (var10000.__nonzero__()) {
            var1.setline(36);
            var6 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(0));
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(37);
            var6 = var1.getlocal(2).__getattr__("type");
            var10000 = var6._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__getattr__("children");
            }

            if (var10000.__nonzero__()) {
               var1.setline(38);
               var6 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(39);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Leaf"));
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(3).__getattr__("value");
                  var10000 = var6._eq(PyString.fromInterned("__metaclass__"));
                  var6 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(41);
                  var5 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      }
   }

   public PyObject fixup_parse_tree$2(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyString.fromInterned(" one-line classes don't get a suite in the parse tree so we add\n        one to normalize the tree\n    ");
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("children").__iter__();

      PyObject var10000;
      do {
         var1.setline(49);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(55);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0).__getattr__("children")).__iter__();

            do {
               var1.setline(55);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(59);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No class suite and no ':'!")));
               }

               PyObject[] var7 = Py.unpackSequence(var4, 2);
               PyObject var6 = var7[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var7[1];
               var1.setlocal(1, var6);
               var6 = null;
               var1.setline(56);
               var5 = var1.getlocal(1).__getattr__("type");
               var10000 = var5._eq(var1.getglobal("token").__getattr__("COLON"));
               var5 = null;
            } while(!var10000.__nonzero__());

            var1.setline(62);
            var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("suite"), (PyObject)(new PyList(Py.EmptyObjects)));
            var1.setlocal(3, var3);
            var3 = null;

            while(true) {
               var1.setline(63);
               if (!var1.getlocal(0).__getattr__("children").__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__nonzero__()) {
                  var1.setline(67);
                  var1.getlocal(0).__getattr__("append_child").__call__(var2, var1.getlocal(3));
                  var1.setline(68);
                  var3 = var1.getlocal(3);
                  var1.setlocal(1, var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(64);
               var3 = var1.getlocal(0).__getattr__("children").__getitem__(var1.getlocal(2)._add(Py.newInteger(1)));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(65);
               var1.getlocal(3).__getattr__("append_child").__call__(var2, var1.getlocal(4).__getattr__("clone").__call__(var2));
               var1.setline(66);
               var1.getlocal(4).__getattr__("remove").__call__(var2);
            }
         }

         var1.setlocal(1, var4);
         var1.setline(50);
         var5 = var1.getlocal(1).__getattr__("type");
         var10000 = var5._eq(var1.getglobal("syms").__getattr__("suite"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(52);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fixup_simple_stmt$3(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyString.fromInterned(" if there is a semi-colon all the parts count as part of the same\n        simple_stmt.  We just want the __metaclass__ part so we move\n        everything efter the semi-colon into its own simple_stmt node\n    ");
      var1.setline(76);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2).__getattr__("children")).__iter__();

      PyObject var10000;
      do {
         var1.setline(76);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(80);
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(77);
         PyObject var7 = var1.getlocal(4).__getattr__("type");
         var10000 = var7._eq(var1.getglobal("token").__getattr__("SEMI"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(82);
      var1.getlocal(4).__getattr__("remove").__call__(var2);
      var1.setline(83);
      var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("expr_stmt"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("simple_stmt"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      var1.setlocal(6, var3);
      var3 = null;

      while(true) {
         var1.setline(85);
         if (!var1.getlocal(2).__getattr__("children").__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(89);
            var1.getlocal(0).__getattr__("insert_child").__call__(var2, var1.getlocal(1), var1.getlocal(6));
            var1.setline(90);
            var3 = var1.getlocal(6).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(Py.newInteger(0));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(91);
            var3 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(Py.newInteger(0));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(92);
            var3 = var1.getlocal(9).__getattr__("prefix");
            var1.getlocal(8).__setattr__("prefix", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(86);
         var3 = var1.getlocal(2).__getattr__("children").__getitem__(var1.getlocal(3));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(87);
         var1.getlocal(5).__getattr__("append_child").__call__(var2, var1.getlocal(7).__getattr__("clone").__call__(var2));
         var1.setline(88);
         var1.getlocal(7).__getattr__("remove").__call__(var2);
      }
   }

   public PyObject remove_trailing_newline$4(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var10000 = var1.getlocal(0).__getattr__("children");
      if (var10000.__nonzero__()) {
         PyObject var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("token").__getattr__("NEWLINE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(97);
         var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("remove").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_metas$5(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(102);
            var3 = var1.getlocal(0).__getattr__("children").__iter__();

            do {
               var1.setline(102);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(106);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No class suite!")));
               }

               var1.setlocal(1, var4);
               var1.setline(103);
               var7 = var1.getlocal(1).__getattr__("type");
               var9 = var7._eq(var1.getglobal("syms").__getattr__("suite"));
               var5 = null;
            } while(!var9.__nonzero__());

            var1.setline(109);
            var3 = var1.getglobal("list").__call__(var2, var1.getglobal("enumerate").__call__(var2, var1.getlocal(1).__getattr__("children"))).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(109);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var8 = Py.unpackSequence(var4, 2);
         PyObject var6 = var8[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(110);
         var7 = var1.getlocal(3).__getattr__("type");
         var9 = var7._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
         var5 = null;
         if (var9.__nonzero__()) {
            var9 = var1.getlocal(3).__getattr__("children");
         }

         if (var9.__nonzero__()) {
            var1.setline(111);
            var7 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(0));
            var1.setlocal(4, var7);
            var5 = null;
            var1.setline(112);
            var7 = var1.getlocal(4).__getattr__("type");
            var9 = var7._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
            var5 = null;
            if (var9.__nonzero__()) {
               var9 = var1.getlocal(4).__getattr__("children");
            }

            if (var9.__nonzero__()) {
               var1.setline(114);
               var7 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(0));
               var1.setlocal(5, var7);
               var5 = null;
               var1.setline(115);
               var9 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("Leaf"));
               if (var9.__nonzero__()) {
                  var7 = var1.getlocal(5).__getattr__("value");
                  var9 = var7._eq(PyUnicode.fromInterned("__metaclass__"));
                  var5 = null;
               }

               if (var9.__nonzero__()) {
                  var1.setline(118);
                  var1.getglobal("fixup_simple_stmt").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                  var1.setline(119);
                  var1.getglobal("remove_trailing_newline").__call__(var2, var1.getlocal(3));
                  var1.setline(120);
                  var1.setline(120);
                  var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
                  PyTuple var10 = new PyTuple(var8);
                  Arrays.fill(var8, (Object)null);
                  var1.f_lasti = 1;
                  var5 = new Object[7];
                  var5[3] = var3;
                  var5[4] = var4;
                  var1.f_savedlocals = var5;
                  return var10;
               }
            }
         }
      }
   }

   public PyObject fixup_indent$6(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned(" If an INDENT is followed by a thing with a prefix then nuke the prefix\n        Otherwise we get in trouble when removing __metaclass__ at suite start\n    ");
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("children").__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(129);
         if (!var1.getlocal(1).__nonzero__()) {
            break;
         }

         var1.setline(130);
         var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(131);
         var3 = var1.getlocal(2).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("token").__getattr__("INDENT"));
         var3 = null;
      } while(!var10000.__nonzero__());

      while(true) {
         var1.setline(135);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(136);
         var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(137);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Leaf"));
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getattr__("type");
            var10000 = var3._ne(var1.getglobal("token").__getattr__("DEDENT"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(138);
            if (var1.getlocal(2).__getattr__("prefix").__nonzero__()) {
               var1.setline(139);
               PyUnicode var4 = PyUnicode.fromInterned("");
               var1.getlocal(2).__setattr__((String)"prefix", var4);
               var3 = null;
            }

            var1.setline(140);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(142);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("children").__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1)));
      }
   }

   public PyObject FixMetaclass$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(146);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(148);
      PyString var4 = PyString.fromInterned("\n    classdef<any*>\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(152);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$8, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$8(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      if (var1.getglobal("has_metaclass").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(154);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(156);
         var1.getglobal("fixup_parse_tree").__call__(var2, var1.getlocal(1));
         var1.setline(159);
         PyObject var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(160);
         var3 = var1.getglobal("find_metas").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(160);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(164);
               var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("type");
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(167);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children"));
               PyObject var10000 = var3._eq(Py.newInteger(7));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(170);
                  var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(3)).__getattr__("type");
                  var10000 = var3._eq(var1.getglobal("syms").__getattr__("arglist"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(171);
                     var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(3));
                     var1.setlocal(8, var3);
                     var3 = null;
                  } else {
                     var1.setline(174);
                     var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(3)).__getattr__("clone").__call__(var2);
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(175);
                     var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("arglist"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(9)})));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(176);
                     var1.getlocal(1).__getattr__("set_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)var1.getlocal(8));
                  }
               } else {
                  var1.setline(177);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children"));
                  var10000 = var3._eq(Py.newInteger(6));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(180);
                     var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("arglist"), (PyObject)(new PyList(Py.EmptyObjects)));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(181);
                     var1.getlocal(1).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)var1.getlocal(8));
                  } else {
                     var1.setline(182);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children"));
                     var10000 = var3._eq(Py.newInteger(4));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(190);
                        throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unexpected class definition")));
                     }

                     var1.setline(185);
                     var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("arglist"), (PyObject)(new PyList(Py.EmptyObjects)));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(186);
                     var1.getlocal(1).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RPAR"), (PyObject)PyUnicode.fromInterned(")")));
                     var1.setline(187);
                     var1.getlocal(1).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(8));
                     var1.setline(188);
                     var1.getlocal(1).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("LPAR"), (PyObject)PyUnicode.fromInterned("(")));
                  }
               }

               var1.setline(193);
               var3 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("children").__getitem__(Py.newInteger(0));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(194);
               PyString var8 = PyString.fromInterned("metaclass");
               var1.getlocal(10).__setattr__((String)"value", var8);
               var3 = null;
               var1.setline(195);
               var3 = var1.getlocal(10).__getattr__("prefix");
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(197);
               PyUnicode var9;
               if (var1.getlocal(8).__getattr__("children").__nonzero__()) {
                  var1.setline(198);
                  var1.getlocal(8).__getattr__("append_child").__call__(var2, var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("COMMA"), (PyObject)PyUnicode.fromInterned(",")));
                  var1.setline(199);
                  var9 = PyUnicode.fromInterned(" ");
                  var1.getlocal(10).__setattr__((String)"prefix", var9);
                  var3 = null;
               } else {
                  var1.setline(201);
                  var9 = PyUnicode.fromInterned("");
                  var1.getlocal(10).__setattr__((String)"prefix", var9);
                  var3 = null;
               }

               var1.setline(204);
               var3 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(0));
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(205);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var3 = var1.getlocal(12).__getattr__("type");
                  var10000 = var3._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(206);
               var9 = PyUnicode.fromInterned("");
               var1.getlocal(12).__getattr__("children").__getitem__(Py.newInteger(1)).__setattr__((String)"prefix", var9);
               var3 = null;
               var1.setline(207);
               var9 = PyUnicode.fromInterned("");
               var1.getlocal(12).__getattr__("children").__getitem__(Py.newInteger(2)).__setattr__((String)"prefix", var9);
               var3 = null;
               var1.setline(209);
               var1.getlocal(8).__getattr__("append_child").__call__(var2, var1.getlocal(3));
               var1.setline(211);
               var1.getglobal("fixup_indent").__call__(var2, var1.getlocal(4));
               var1.setline(214);
               if (var1.getlocal(4).__getattr__("children").__not__().__nonzero__()) {
                  var1.setline(216);
                  var1.getlocal(4).__getattr__("remove").__call__(var2);
                  var1.setline(217);
                  var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyUnicode.fromInterned("pass"));
                  var1.setlocal(13, var3);
                  var3 = null;
                  var1.setline(218);
                  var3 = var1.getlocal(11);
                  var1.getlocal(13).__setattr__("prefix", var3);
                  var3 = null;
                  var1.setline(219);
                  var1.getlocal(1).__getattr__("append_child").__call__(var2, var1.getlocal(13));
                  var1.setline(220);
                  var1.getlocal(1).__getattr__("append_child").__call__(var2, var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NEWLINE"), (PyObject)PyUnicode.fromInterned("\n")));
               } else {
                  var1.setline(222);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("children"));
                  var10000 = var3._gt(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(-2)).__getattr__("type");
                     var10000 = var3._eq(var1.getglobal("token").__getattr__("INDENT"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("type");
                        var10000 = var3._eq(var1.getglobal("token").__getattr__("DEDENT"));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(226);
                     var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyUnicode.fromInterned("pass"));
                     var1.setlocal(13, var3);
                     var3 = null;
                     var1.setline(227);
                     var1.getlocal(4).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)var1.getlocal(13));
                     var1.setline(228);
                     var1.getlocal(4).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NEWLINE"), (PyObject)PyUnicode.fromInterned("\n")));
                  }
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(161);
            PyObject var7 = var1.getlocal(6);
            var1.setlocal(3, var7);
            var5 = null;
            var1.setline(162);
            var1.getlocal(6).__getattr__("remove").__call__(var2);
         }
      }
   }

   public fix_metaclass$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"parent", "node", "expr_node", "left_side"};
      has_metaclass$1 = Py.newCode(1, var2, var1, "has_metaclass", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls_node", "node", "i", "suite", "move_node"};
      fixup_parse_tree$2 = Py.newCode(1, var2, var1, "fixup_parse_tree", 45, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"parent", "i", "stmt_node", "semi_ind", "node", "new_expr", "new_stmt", "move_node", "new_leaf1", "old_leaf1"};
      fixup_simple_stmt$3 = Py.newCode(3, var2, var1, "fixup_simple_stmt", 71, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      remove_trailing_newline$4 = Py.newCode(1, var2, var1, "remove_trailing_newline", 95, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls_node", "node", "i", "simple_node", "expr_node", "left_node"};
      find_metas$5 = Py.newCode(1, var2, var1, "find_metas", 100, false, false, self, 5, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"suite", "kids", "node"};
      fixup_indent$6 = Py.newCode(1, var2, var1, "fixup_indent", 123, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FixMetaclass$7 = Py.newCode(0, var2, var1, "FixMetaclass", 145, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "last_metaclass", "suite", "i", "stmt", "text_type", "arglist", "parent", "meta_txt", "orig_meta_prefix", "expr_stmt", "pass_leaf"};
      transform$8 = Py.newCode(3, var2, var1, "transform", 152, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_metaclass$py("lib2to3/fixes/fix_metaclass$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_metaclass$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.has_metaclass$1(var2, var3);
         case 2:
            return this.fixup_parse_tree$2(var2, var3);
         case 3:
            return this.fixup_simple_stmt$3(var2, var3);
         case 4:
            return this.remove_trailing_newline$4(var2, var3);
         case 5:
            return this.find_metas$5(var2, var3);
         case 6:
            return this.fixup_indent$6(var2, var3);
         case 7:
            return this.FixMetaclass$7(var2, var3);
         case 8:
            return this.transform$8(var2, var3);
         default:
            return null;
      }
   }
}
