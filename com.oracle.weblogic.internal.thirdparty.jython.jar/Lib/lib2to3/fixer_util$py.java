package lib2to3;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixer_util.py")
public class fixer_util$py extends PyFunctionTable implements PyRunnable {
   static fixer_util$py self;
   static final PyCode f$0;
   static final PyCode KeywordArg$1;
   static final PyCode LParen$2;
   static final PyCode RParen$3;
   static final PyCode Assign$4;
   static final PyCode Name$5;
   static final PyCode Attr$6;
   static final PyCode Comma$7;
   static final PyCode Dot$8;
   static final PyCode ArgList$9;
   static final PyCode Call$10;
   static final PyCode Newline$11;
   static final PyCode BlankLine$12;
   static final PyCode Number$13;
   static final PyCode Subscript$14;
   static final PyCode String$15;
   static final PyCode ListComp$16;
   static final PyCode FromImport$17;
   static final PyCode is_tuple$18;
   static final PyCode is_list$19;
   static final PyCode parenthesize$20;
   static final PyCode attr_chain$21;
   static final PyCode in_special_context$22;
   static final PyCode is_probably_builtin$23;
   static final PyCode find_indentation$24;
   static final PyCode make_suite$25;
   static final PyCode find_root$26;
   static final PyCode does_tree_import$27;
   static final PyCode is_import$28;
   static final PyCode touch_import$29;
   static final PyCode is_import_stmt$30;
   static final PyCode find_binding$31;
   static final PyCode _find$32;
   static final PyCode _is_import_binding$33;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utility functions, node construction macros, etc."));
      var1.setline(1);
      PyString.fromInterned("Utility functions, node construction macros, etc.");
      var1.setline(4);
      String[] var3 = new String[]{"islice"};
      PyObject[] var5 = imp.importFrom("itertools", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("islice", var4);
      var4 = null;
      var1.setline(7);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"Leaf", "Node"};
      var5 = imp.importFrom("pytree", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("Leaf", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Node", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"python_symbols"};
      var5 = imp.importFrom("pygram", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"patcomp"};
      var5 = imp.importFrom("", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("patcomp", var4);
      var4 = null;
      var1.setline(17);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, KeywordArg$1, (PyObject)null);
      var1.setlocal("KeywordArg", var6);
      var3 = null;
      var1.setline(21);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, LParen$2, (PyObject)null);
      var1.setlocal("LParen", var6);
      var3 = null;
      var1.setline(24);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, RParen$3, (PyObject)null);
      var1.setlocal("RParen", var6);
      var3 = null;
      var1.setline(27);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Assign$4, PyString.fromInterned("Build an assignment statement"));
      var1.setlocal("Assign", var6);
      var3 = null;
      var1.setline(38);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, Name$5, PyString.fromInterned("Return a NAME leaf"));
      var1.setlocal("Name", var6);
      var3 = null;
      var1.setline(42);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Attr$6, PyString.fromInterned("A node tuple for obj.attr"));
      var1.setlocal("Attr", var6);
      var3 = null;
      var1.setline(46);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Comma$7, PyString.fromInterned("A comma leaf"));
      var1.setlocal("Comma", var6);
      var3 = null;
      var1.setline(50);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Dot$8, PyString.fromInterned("A period (.) leaf"));
      var1.setlocal("Dot", var6);
      var3 = null;
      var1.setline(54);
      var5 = new PyObject[]{var1.getname("LParen").__call__(var2), var1.getname("RParen").__call__(var2)};
      var6 = new PyFunction(var1.f_globals, var5, ArgList$9, PyString.fromInterned("A parenthesised argument list, used by Call()"));
      var1.setlocal("ArgList", var6);
      var3 = null;
      var1.setline(61);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, Call$10, PyString.fromInterned("A function call"));
      var1.setlocal("Call", var6);
      var3 = null;
      var1.setline(68);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Newline$11, PyString.fromInterned("A newline literal"));
      var1.setlocal("Newline", var6);
      var3 = null;
      var1.setline(72);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, BlankLine$12, PyString.fromInterned("A blank line"));
      var1.setlocal("BlankLine", var6);
      var3 = null;
      var1.setline(76);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, Number$13, (PyObject)null);
      var1.setlocal("Number", var6);
      var3 = null;
      var1.setline(79);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, Subscript$14, PyString.fromInterned("A numeric or string subscript"));
      var1.setlocal("Subscript", var6);
      var3 = null;
      var1.setline(85);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, String$15, PyString.fromInterned("A string leaf"));
      var1.setlocal("String", var6);
      var3 = null;
      var1.setline(89);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, ListComp$16, PyString.fromInterned("A list comprehension of the form [xp for fp in it if test].\n\n    If test is None, the \"if test\" part is omitted.\n    "));
      var1.setlocal("ListComp", var6);
      var3 = null;
      var1.setline(113);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, FromImport$17, PyString.fromInterned(" Return an import statement in the form:\n        from package import name_leafs"));
      var1.setlocal("FromImport", var6);
      var3 = null;
      var1.setline(137);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_tuple$18, PyString.fromInterned("Does the node represent a tuple literal?"));
      var1.setlocal("is_tuple", var6);
      var3 = null;
      var1.setline(149);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_list$19, PyString.fromInterned("Does the node represent a list literal?"));
      var1.setlocal("is_list", var6);
      var3 = null;
      var1.setline(163);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parenthesize$20, (PyObject)null);
      var1.setlocal("parenthesize", var6);
      var3 = null;
      var1.setline(167);
      PyObject var7 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("sorted"), PyString.fromInterned("list"), PyString.fromInterned("set"), PyString.fromInterned("any"), PyString.fromInterned("all"), PyString.fromInterned("tuple"), PyString.fromInterned("sum"), PyString.fromInterned("min"), PyString.fromInterned("max"), PyString.fromInterned("enumerate")})));
      var1.setlocal("consuming_calls", var7);
      var3 = null;
      var1.setline(170);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, attr_chain$21, PyString.fromInterned("Follow an attribute chain.\n\n    If you have a chain of objects where a.foo -> b, b.foo-> c, etc,\n    use this to iterate over all objects in the chain. Iteration is\n    terminated by getattr(x, attr) is None.\n\n    Args:\n        obj: the starting object\n        attr: the name of the chaining attribute\n\n    Yields:\n        Each successive object in the chain.\n    "));
      var1.setlocal("attr_chain", var6);
      var3 = null;
      var1.setline(189);
      PyString var8 = PyString.fromInterned("for_stmt< 'for' any 'in' node=any ':' any* >\n        | comp_for< 'for' any 'in' node=any any* >\n     ");
      var1.setlocal("p0", var8);
      var3 = null;
      var1.setline(192);
      var8 = PyString.fromInterned("\npower<\n    ( 'iter' | 'list' | 'tuple' | 'sorted' | 'set' | 'sum' |\n      'any' | 'all' | 'enumerate' | (any* trailer< '.' 'join' >) )\n    trailer< '(' node=any ')' >\n    any*\n>\n");
      var1.setlocal("p1", var8);
      var3 = null;
      var1.setline(200);
      var8 = PyString.fromInterned("\npower<\n    ( 'sorted' | 'enumerate' )\n    trailer< '(' arglist<node=any any*> ')' >\n    any*\n>\n");
      var1.setlocal("p2", var8);
      var3 = null;
      var1.setline(207);
      var7 = var1.getname("False");
      var1.setlocal("pats_built", var7);
      var3 = null;
      var1.setline(208);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, in_special_context$22, PyString.fromInterned(" Returns true if node is in an environment where all that is required\n        of it is being iterable (ie, it doesn't matter if it returns a list\n        or an iterator).\n        See test_map_nochange in test_fixers.py for some examples and tests.\n        "));
      var1.setlocal("in_special_context", var6);
      var3 = null;
      var1.setline(227);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_probably_builtin$23, PyString.fromInterned("\n    Check that something isn't an attribute or function name etc.\n    "));
      var1.setlocal("is_probably_builtin", var6);
      var3 = null;
      var1.setline(250);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, find_indentation$24, PyString.fromInterned("Find the indentation of *node*."));
      var1.setlocal("find_indentation", var6);
      var3 = null;
      var1.setline(264);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, make_suite$25, (PyObject)null);
      var1.setlocal("make_suite", var6);
      var3 = null;
      var1.setline(273);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, find_root$26, PyString.fromInterned("Find the top level namespace."));
      var1.setlocal("find_root", var6);
      var3 = null;
      var1.setline(282);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, does_tree_import$27, PyString.fromInterned(" Returns true if name is imported from package at the\n        top level of the tree which node belongs to.\n        To cover the case of an import like 'import foo', use\n        None for the package and 'foo' for the name. "));
      var1.setlocal("does_tree_import", var6);
      var3 = null;
      var1.setline(290);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_import$28, PyString.fromInterned("Returns true if the node is an import statement."));
      var1.setlocal("is_import", var6);
      var3 = null;
      var1.setline(294);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, touch_import$29, PyString.fromInterned(" Works like `does_tree_import` but adds an import statement\n        if it was not imported. "));
      var1.setlocal("touch_import", var6);
      var3 = null;
      var1.setline(339);
      var7 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("syms").__getattr__("classdef"), var1.getname("syms").__getattr__("funcdef")})));
      var1.setlocal("_def_syms", var7);
      var3 = null;
      var1.setline(340);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, find_binding$31, PyString.fromInterned(" Returns the node which binds variable name, otherwise None.\n        If optional argument package is supplied, only imports will\n        be returned.\n        See test cases for examples."));
      var1.setlocal("find_binding", var6);
      var3 = null;
      var1.setline(382);
      var7 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getname("syms").__getattr__("funcdef"), var1.getname("syms").__getattr__("classdef"), var1.getname("syms").__getattr__("trailer")})));
      var1.setlocal("_block_syms", var7);
      var3 = null;
      var1.setline(383);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _find$32, (PyObject)null);
      var1.setlocal("_find", var6);
      var3 = null;
      var1.setline(393);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, _is_import_binding$33, PyString.fromInterned(" Will reuturn node if node will import name, or node\n        will import * from package.  None is returned otherwise.\n        See test cases for examples. "));
      var1.setlocal("_is_import_binding", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject KeywordArg$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("argument"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("EQUAL"), (PyObject)PyUnicode.fromInterned("=")), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LParen$2(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("LPAR"), (PyObject)PyUnicode.fromInterned("("));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RParen$3(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RPAR"), (PyObject)PyUnicode.fromInterned(")"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Assign$4(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Build an assignment statement");
      var1.setline(29);
      PyList var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(30);
         var3 = new PyList(new PyObject[]{var1.getlocal(0)});
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(31);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(32);
         PyUnicode var5 = PyUnicode.fromInterned(" ");
         var1.getlocal(1).__setattr__((String)"prefix", var5);
         var3 = null;
         var1.setline(33);
         var3 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(35);
      PyObject var10000 = var1.getglobal("Node");
      PyObject var10002 = var1.getglobal("syms").__getattr__("atom");
      PyObject var10003 = var1.getlocal(0);
      PyObject[] var10006 = new PyObject[1];
      PyObject var10009 = var1.getglobal("Leaf");
      PyObject[] var6 = new PyObject[]{var1.getglobal("token").__getattr__("EQUAL"), PyUnicode.fromInterned("="), PyUnicode.fromInterned(" ")};
      String[] var4 = new String[]{"prefix"};
      var10009 = var10009.__call__(var2, var6, var4);
      var3 = null;
      var10006[0] = var10009;
      PyObject var7 = var10000.__call__(var2, var10002, var10003._add(new PyList(var10006))._add(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject Name$5(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Return a NAME leaf");
      var1.setline(40);
      PyObject var10000 = var1.getglobal("Leaf");
      PyObject[] var3 = new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject Attr$6(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("A node tuple for obj.attr");
      var1.setline(44);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Dot").__call__(var2), var1.getlocal(1)})))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Comma$7(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString.fromInterned("A comma leaf");
      var1.setline(48);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("COMMA"), (PyObject)PyUnicode.fromInterned(","));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Dot$8(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("A period (.) leaf");
      var1.setline(52);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("DOT"), (PyObject)PyUnicode.fromInterned("."));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ArgList$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("A parenthesised argument list, used by Call()");
      var1.setline(56);
      PyObject var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__getattr__("clone").__call__(var2), var1.getlocal(2).__getattr__("clone").__call__(var2)})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(57);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(58);
         var1.getlocal(3).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("arglist"), var1.getlocal(0)));
      }

      var1.setline(59);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Call$10(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("A function call");
      var1.setline(63);
      PyObject var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("power"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("ArgList").__call__(var2, var1.getlocal(1))})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var3 = var1.getlocal(2);
         var1.getlocal(3).__setattr__("prefix", var3);
         var3 = null;
      }

      var1.setline(66);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Newline$11(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("A newline literal");
      var1.setline(70);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NEWLINE"), (PyObject)PyUnicode.fromInterned("\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BlankLine$12(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyString.fromInterned("A blank line");
      var1.setline(74);
      PyObject var3 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NEWLINE"), (PyObject)PyUnicode.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Number$13(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var10000 = var1.getglobal("Leaf");
      PyObject[] var3 = new PyObject[]{var1.getglobal("token").__getattr__("NUMBER"), var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject Subscript$14(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("A numeric or string subscript");
      var1.setline(81);
      PyObject var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("LBRACE"), (PyObject)PyUnicode.fromInterned("[")), var1.getlocal(0), var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RBRACE"), (PyObject)PyUnicode.fromInterned("]"))})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject String$15(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyString.fromInterned("A string leaf");
      var1.setline(87);
      PyObject var10000 = var1.getglobal("Leaf");
      PyObject[] var3 = new PyObject[]{var1.getglobal("token").__getattr__("STRING"), var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject ListComp$16(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("A list comprehension of the form [xp for fp in it if test].\n\n    If test is None, the \"if test\" part is omitted.\n    ");
      var1.setline(94);
      PyUnicode var3 = PyUnicode.fromInterned("");
      var1.getlocal(0).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(95);
      var3 = PyUnicode.fromInterned(" ");
      var1.getlocal(1).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(96);
      var3 = PyUnicode.fromInterned(" ");
      var1.getlocal(2).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(97);
      PyObject var4 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NAME"), (PyObject)PyUnicode.fromInterned("for"));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(98);
      var3 = PyUnicode.fromInterned(" ");
      var1.getlocal(4).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(99);
      var4 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NAME"), (PyObject)PyUnicode.fromInterned("in"));
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(100);
      var3 = PyUnicode.fromInterned(" ");
      var1.getlocal(5).__setattr__((String)"prefix", var3);
      var3 = null;
      var1.setline(101);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(4), var1.getlocal(1), var1.getlocal(5), var1.getlocal(2)});
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(102);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(103);
         var3 = PyUnicode.fromInterned(" ");
         var1.getlocal(3).__setattr__((String)"prefix", var3);
         var3 = null;
         var1.setline(104);
         var4 = var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NAME"), (PyObject)PyUnicode.fromInterned("if"));
         var1.setlocal(7, var4);
         var3 = null;
         var1.setline(105);
         var3 = PyUnicode.fromInterned(" ");
         var1.getlocal(7).__setattr__((String)"prefix", var3);
         var3 = null;
         var1.setline(106);
         var1.getlocal(6).__getattr__("append").__call__(var2, var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("comp_if"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(7), var1.getlocal(3)}))));
      }

      var1.setline(107);
      var4 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("listmaker"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(0), var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("comp_for"), var1.getlocal(6))})));
      var1.setlocal(8, var4);
      var3 = null;
      var1.setline(108);
      var4 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("atom"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("LBRACE"), (PyObject)PyUnicode.fromInterned("[")), var1.getlocal(8), var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RBRACE"), (PyObject)PyUnicode.fromInterned("]"))})));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject FromImport$17(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned(" Return an import statement in the form:\n        from package import name_leafs");
      var1.setline(121);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(121);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(125);
            PyObject[] var10002 = new PyObject[]{var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NAME"), (PyObject)PyUnicode.fromInterned("from")), null, null, null};
            PyObject var10005 = var1.getglobal("Leaf");
            PyObject[] var5 = new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(0), PyUnicode.fromInterned(" ")};
            String[] var6 = new String[]{"prefix"};
            var10005 = var10005.__call__(var2, var5, var6);
            var3 = null;
            var10002[1] = var10005;
            var10005 = var1.getglobal("Leaf");
            var5 = new PyObject[]{var1.getglobal("token").__getattr__("NAME"), PyUnicode.fromInterned("import"), PyUnicode.fromInterned(" ")};
            var6 = new String[]{"prefix"};
            var10005 = var10005.__call__(var2, var5, var6);
            var3 = null;
            var10002[2] = var10005;
            var10002[3] = var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("import_as_names"), var1.getlocal(1));
            PyList var7 = new PyList(var10002);
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(129);
            var3 = var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("import_from"), var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(130);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(123);
         var1.getlocal(2).__getattr__("remove").__call__(var2);
      }
   }

   public PyObject is_tuple$18(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Does the node represent a tuple literal?");
      var1.setline(139);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Node"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("children");
         var10000 = var3._eq(new PyList(new PyObject[]{var1.getglobal("LParen").__call__(var2), var1.getglobal("RParen").__call__(var2)}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(140);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(141);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Node"));
         if (var10000.__nonzero__()) {
            PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
            var10000 = var4._eq(Py.newInteger(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getglobal("Leaf"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)), var1.getglobal("Node"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(2)), var1.getglobal("Leaf"));
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                        var10000 = var4._eq(PyUnicode.fromInterned("("));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var4 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(2)).__getattr__("value");
                           var10000 = var4._eq(PyUnicode.fromInterned(")"));
                           var4 = null;
                        }
                     }
                  }
               }
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject is_list$19(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Does the node represent a list literal?");
      var1.setline(151);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Node"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getglobal("Leaf"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(-1)), var1.getglobal("Leaf"));
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                  var10000 = var3._eq(PyUnicode.fromInterned("["));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("value");
                     var10000 = var3._eq(PyUnicode.fromInterned("]"));
                     var3 = null;
                  }
               }
            }
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parenthesize$20(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("atom"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("LParen").__call__(var2), var1.getlocal(0), var1.getglobal("RParen").__call__(var2)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject attr_chain$21(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      PyObject var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(183);
            PyString.fromInterned("Follow an attribute chain.\n\n    If you have a chain of objects where a.foo -> b, b.foo-> c, etc,\n    use this to iterate over all objects in the chain. Iteration is\n    terminated by getattr(x, attr) is None.\n\n    Args:\n        obj: the starting object\n        attr: the name of the chaining attribute\n\n    Yields:\n        Each successive object in the chain.\n    ");
            var1.setline(184);
            var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(2, var4);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var5 = (PyObject)var10000;
            var1.setline(187);
            var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            var1.setlocal(2, var4);
            var3 = null;
      }

      var1.setline(185);
      if (!var1.getlocal(2).__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(186);
         var1.setline(186);
         var5 = var1.getlocal(2);
         var1.f_lasti = 1;
         var3 = new Object[4];
         var1.f_savedlocals = var3;
         return var5;
      }
   }

   public PyObject in_special_context$22(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyString.fromInterned(" Returns true if node is in an environment where all that is required\n        of it is being iterable (ie, it doesn't matter if it returns a list\n        or an iterator).\n        See test_map_nochange in test_fixers.py for some examples and tests.\n        ");
      var1.setline(215);
      PyObject var3;
      if (var1.getglobal("pats_built").__not__().__nonzero__()) {
         var1.setline(216);
         var3 = var1.getglobal("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getglobal("p0"));
         var1.setglobal("p0", var3);
         var3 = null;
         var1.setline(217);
         var3 = var1.getglobal("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getglobal("p1"));
         var1.setglobal("p1", var3);
         var3 = null;
         var1.setline(218);
         var3 = var1.getglobal("patcomp").__getattr__("compile_pattern").__call__(var2, var1.getglobal("p2"));
         var1.setglobal("p2", var3);
         var3 = null;
         var1.setline(219);
         var3 = var1.getglobal("True");
         var1.setglobal("pats_built", var3);
         var3 = null;
      }

      var1.setline(220);
      PyList var8 = new PyList(new PyObject[]{var1.getglobal("p0"), var1.getglobal("p1"), var1.getglobal("p2")});
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(221);
      var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(1), var1.getglobal("attr_chain").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("parent"))).__iter__();

      PyObject var10000;
      PyObject var9;
      do {
         var1.setline(221);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(225);
            var9 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var9;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(222);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(223);
         var10000 = var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         if (var10000.__nonzero__()) {
            var9 = var1.getlocal(4).__getitem__(PyString.fromInterned("node"));
            var10000 = var9._is(var1.getlocal(0));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(224);
      var9 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject is_probably_builtin$23(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("\n    Check that something isn't an attribute or function name etc.\n    ");
      var1.setline(231);
      PyObject var3 = var1.getlocal(0).__getattr__("prev_sibling");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(232);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("token").__getattr__("DOT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(234);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(235);
         PyObject var4 = var1.getlocal(0).__getattr__("parent");
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(236);
         var4 = var1.getlocal(2).__getattr__("type");
         var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("syms").__getattr__("funcdef"), var1.getglobal("syms").__getattr__("classdef")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(237);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(238);
            var4 = var1.getlocal(2).__getattr__("type");
            var10000 = var4._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0));
               var10000 = var4._is(var1.getlocal(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(240);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(241);
               var4 = var1.getlocal(2).__getattr__("type");
               var10000 = var4._eq(var1.getglobal("syms").__getattr__("parameters"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(2).__getattr__("type");
                  var10000 = var4._eq(var1.getglobal("syms").__getattr__("typedargslist"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(1);
                     var10000 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(1).__getattr__("type");
                        var10000 = var4._eq(var1.getglobal("token").__getattr__("COMMA"));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var4 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0));
                        var10000 = var4._is(var1.getlocal(0));
                        var4 = null;
                     }
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(247);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(248);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject find_indentation$24(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyString.fromInterned("Find the indentation of *node*.");

      while(true) {
         var1.setline(252);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(258);
            PyUnicode var5 = PyUnicode.fromInterned("");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(253);
         PyObject var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("syms").__getattr__("suite"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(254);
            var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(255);
            var3 = var1.getlocal(1).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("token").__getattr__("INDENT"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(256);
               var3 = var1.getlocal(1).__getattr__("value");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(257);
         var4 = var1.getlocal(0).__getattr__("parent");
         var1.setlocal(0, var4);
         var4 = null;
      }
   }

   public PyObject make_suite$25(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("suite"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(266);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(267);
         PyObject var4 = var1.getlocal(0).__getattr__("clone").__call__(var2);
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(268);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("parent"), var1.getglobal("None")});
         PyObject[] var5 = Py.unpackSequence(var7, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.getlocal(0).__setattr__("parent", var6);
         var6 = null;
         var4 = null;
         var1.setline(269);
         var4 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("suite"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(0)})));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(270);
         var4 = var1.getlocal(1);
         var1.getlocal(2).__setattr__("parent", var4);
         var4 = null;
         var1.setline(271);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject find_root$26(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString.fromInterned("Find the top level namespace.");

      do {
         var1.setline(276);
         PyObject var3 = var1.getlocal(0).__getattr__("type");
         PyObject var10000 = var3._ne(var1.getglobal("syms").__getattr__("file_input"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(280);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(277);
         var3 = var1.getlocal(0).__getattr__("parent");
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(278);
      } while(!var1.getlocal(0).__not__().__nonzero__());

      var1.setline(279);
      throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("root found before file_input node was found.")));
   }

   public PyObject does_tree_import$27(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyString.fromInterned(" Returns true if name is imported from package at the\n        top level of the tree which node belongs to.\n        To cover the case of an import like 'import foo', use\n        None for the package and 'foo' for the name. ");
      var1.setline(287);
      PyObject var3 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(1), var1.getglobal("find_root").__call__(var2, var1.getlocal(2)), var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_import$28(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("Returns true if the node is an import statement.");
      var1.setline(292);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("syms").__getattr__("import_name"), var1.getglobal("syms").__getattr__("import_from")}));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject touch_import$29(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyString.fromInterned(" Works like `does_tree_import` but adds an import statement\n        if it was not imported. ");
      var1.setline(297);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var3, is_import_stmt$30, (PyObject)null);
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(301);
      PyObject var10 = var1.getglobal("find_root").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(303);
      if (var1.getglobal("does_tree_import").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(4)).__nonzero__()) {
         var1.setline(304);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(308);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(5, var11);
         var1.setlocal(6, var11);
         var1.setline(309);
         var10 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(4).__getattr__("children")).__iter__();

         PyObject var4;
         PyObject[] var5;
         PyObject var6;
         PyObject var13;
         while(true) {
            var1.setline(309);
            var4 = var10.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(310);
            if (!var1.getlocal(3).__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
               var1.setline(312);
               var13 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(4).__getattr__("children").__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null)).__iter__();

               do {
                  var1.setline(312);
                  var6 = var13.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(6, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(8, var8);
                  var8 = null;
                  var1.setline(313);
               } while(!var1.getlocal(3).__call__(var2, var1.getlocal(8)).__not__().__nonzero__());

               var1.setline(315);
               var13 = var1.getlocal(7)._add(var1.getlocal(6));
               var1.setlocal(5, var13);
               var5 = null;
               break;
            }
         }

         var1.setline(320);
         var10 = var1.getlocal(5);
         PyObject var10000 = var10._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(321);
            var10 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(4).__getattr__("children")).__iter__();

            while(true) {
               var1.setline(321);
               var4 = var10.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(7, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(322);
               var13 = var1.getlocal(2).__getattr__("type");
               var10000 = var13._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2).__getattr__("children");
                  if (var10000.__nonzero__()) {
                     var13 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("type");
                     var10000 = var13._eq(var1.getglobal("token").__getattr__("STRING"));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(324);
                  var13 = var1.getlocal(7)._add(Py.newInteger(1));
                  var1.setlocal(5, var13);
                  var5 = null;
                  break;
               }
            }
         }

         var1.setline(327);
         var10 = var1.getlocal(0);
         var10000 = var10._is(var1.getglobal("None"));
         var3 = null;
         String[] var12;
         PyObject var10002;
         PyObject[] var10005;
         PyObject var10008;
         if (var10000.__nonzero__()) {
            var1.setline(328);
            var10000 = var1.getglobal("Node");
            var10002 = var1.getglobal("syms").__getattr__("import_name");
            var10005 = new PyObject[]{var1.getglobal("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("NAME"), (PyObject)PyUnicode.fromInterned("import")), null};
            var10008 = var1.getglobal("Leaf");
            var3 = new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(1), PyUnicode.fromInterned(" ")};
            var12 = new String[]{"prefix"};
            var10008 = var10008.__call__(var2, var3, var12);
            var3 = null;
            var10005[1] = var10008;
            var10 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(var10005)));
            var1.setlocal(9, var10);
            var3 = null;
         } else {
            var1.setline(333);
            var10000 = var1.getglobal("FromImport");
            var10002 = var1.getlocal(0);
            var10005 = new PyObject[1];
            var10008 = var1.getglobal("Leaf");
            var3 = new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(1), PyUnicode.fromInterned(" ")};
            var12 = new String[]{"prefix"};
            var10008 = var10008.__call__(var2, var3, var12);
            var3 = null;
            var10005[0] = var10008;
            var10 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(var10005)));
            var1.setlocal(9, var10);
            var3 = null;
         }

         var1.setline(335);
         PyList var14 = new PyList(new PyObject[]{var1.getlocal(9), var1.getglobal("Newline").__call__(var2)});
         var1.setlocal(10, var14);
         var3 = null;
         var1.setline(336);
         var1.getlocal(4).__getattr__("insert_child").__call__(var2, var1.getlocal(5), var1.getglobal("Node").__call__(var2, var1.getglobal("syms").__getattr__("simple_stmt"), var1.getlocal(10)));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject is_import_stmt$30(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("children");
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("is_import").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)));
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_binding$31(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned(" Returns the node which binds variable name, otherwise None.\n        If optional argument package is supplied, only imports will\n        be returned.\n        See test cases for examples.");
      var1.setline(345);
      PyObject var3 = var1.getlocal(1).__getattr__("children").__iter__();

      while(true) {
         var1.setline(345);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(380);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(346);
         var5 = var1.getglobal("None");
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(347);
         var5 = var1.getlocal(3).__getattr__("type");
         PyObject var10000 = var5._eq(var1.getglobal("syms").__getattr__("for_stmt"));
         var5 = null;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(348);
            if (var1.getglobal("_find").__call__(var2, var1.getlocal(0), var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(1))).__nonzero__()) {
               var1.setline(349);
               var5 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(350);
            var6 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(0), var1.getglobal("make_suite").__call__(var2, var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(-1))), var1.getlocal(2));
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(351);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(351);
               var6 = var1.getlocal(5);
               var1.setlocal(4, var6);
               var6 = null;
            }
         } else {
            var1.setline(352);
            var6 = var1.getlocal(3).__getattr__("type");
            var10000 = var6._in(new PyTuple(new PyObject[]{var1.getglobal("syms").__getattr__("if_stmt"), var1.getglobal("syms").__getattr__("while_stmt")}));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(353);
               var6 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(0), var1.getglobal("make_suite").__call__(var2, var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(-1))), var1.getlocal(2));
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(354);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(354);
                  var6 = var1.getlocal(5);
                  var1.setlocal(4, var6);
                  var6 = null;
               }
            } else {
               var1.setline(355);
               var6 = var1.getlocal(3).__getattr__("type");
               var10000 = var6._eq(var1.getglobal("syms").__getattr__("try_stmt"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(356);
                  var6 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(0), var1.getglobal("make_suite").__call__(var2, var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(2))), var1.getlocal(2));
                  var1.setlocal(5, var6);
                  var6 = null;
                  var1.setline(357);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(358);
                     var6 = var1.getlocal(5);
                     var1.setlocal(4, var6);
                     var6 = null;
                  } else {
                     var1.setline(360);
                     var6 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3).__getattr__("children").__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null)).__iter__();

                     while(true) {
                        var1.setline(360);
                        PyObject var7 = var6.__iternext__();
                        if (var7 == null) {
                           break;
                        }

                        PyObject[] var8 = Py.unpackSequence(var7, 2);
                        PyObject var9 = var8[0];
                        var1.setlocal(6, var9);
                        var9 = null;
                        var9 = var8[1];
                        var1.setlocal(7, var9);
                        var9 = null;
                        var1.setline(361);
                        PyObject var10 = var1.getlocal(7).__getattr__("type");
                        var10000 = var10._eq(var1.getglobal("token").__getattr__("COLON"));
                        var8 = null;
                        if (var10000.__nonzero__()) {
                           var10 = var1.getlocal(7).__getattr__("value");
                           var10000 = var10._eq(PyString.fromInterned(":"));
                           var8 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(363);
                           var10 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(0), var1.getglobal("make_suite").__call__(var2, var1.getlocal(3).__getattr__("children").__getitem__(var1.getlocal(6)._add(Py.newInteger(4)))), var1.getlocal(2));
                           var1.setlocal(5, var10);
                           var8 = null;
                           var1.setline(364);
                           if (var1.getlocal(5).__nonzero__()) {
                              var1.setline(364);
                              var10 = var1.getlocal(5);
                              var1.setlocal(4, var10);
                              var8 = null;
                           }
                        }
                     }
                  }
               } else {
                  var1.setline(365);
                  var6 = var1.getlocal(3).__getattr__("type");
                  var10000 = var6._in(var1.getglobal("_def_syms"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("value");
                     var10000 = var6._eq(var1.getlocal(0));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(366);
                     var6 = var1.getlocal(3);
                     var1.setlocal(4, var6);
                     var6 = null;
                  } else {
                     var1.setline(367);
                     if (var1.getglobal("_is_import_binding").__call__(var2, var1.getlocal(3), var1.getlocal(0), var1.getlocal(2)).__nonzero__()) {
                        var1.setline(368);
                        var6 = var1.getlocal(3);
                        var1.setlocal(4, var6);
                        var6 = null;
                     } else {
                        var1.setline(369);
                        var6 = var1.getlocal(3).__getattr__("type");
                        var10000 = var6._eq(var1.getglobal("syms").__getattr__("simple_stmt"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(370);
                           var6 = var1.getglobal("find_binding").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(2));
                           var1.setlocal(4, var6);
                           var6 = null;
                        } else {
                           var1.setline(371);
                           var6 = var1.getlocal(3).__getattr__("type");
                           var10000 = var6._eq(var1.getglobal("syms").__getattr__("expr_stmt"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(372);
                              if (var1.getglobal("_find").__call__(var2, var1.getlocal(0), var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(0))).__nonzero__()) {
                                 var1.setline(373);
                                 var6 = var1.getlocal(3);
                                 var1.setlocal(4, var6);
                                 var6 = null;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var1.setline(375);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(376);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(377);
               var5 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(378);
            if (var1.getglobal("is_import").__call__(var2, var1.getlocal(4)).__nonzero__()) {
               var1.setline(379);
               var5 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var5;
            }
         }
      }
   }

   public PyObject _find$32(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(385);
         PyObject var4;
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(391);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(386);
         var4 = var1.getlocal(2).__getattr__("pop").__call__(var2);
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(387);
         var4 = var1.getlocal(1).__getattr__("type");
         PyObject var10000 = var4._gt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(1).__getattr__("type");
            var10000 = var4._notin(var1.getglobal("_block_syms"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(388);
            var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1).__getattr__("children"));
         } else {
            var1.setline(389);
            var4 = var1.getlocal(1).__getattr__("type");
            var10000 = var4._eq(var1.getglobal("token").__getattr__("NAME"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(1).__getattr__("value");
               var10000 = var4._eq(var1.getlocal(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(390);
               var4 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var4;
            }
         }
      }
   }

   public PyObject _is_import_binding$33(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyString.fromInterned(" Will reuturn node if node will import name, or node\n        will import * from package.  None is returned otherwise.\n        See test cases for examples. ");
      var1.setline(398);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_name"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(399);
         var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(400);
         var3 = var1.getlocal(3).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("syms").__getattr__("dotted_as_names"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(401);
            var3 = var1.getlocal(3).__getattr__("children").__iter__();

            while(true) {
               var1.setline(401);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(402);
               var5 = var1.getlocal(4).__getattr__("type");
               var10000 = var5._eq(var1.getglobal("syms").__getattr__("dotted_as_name"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(403);
                  var5 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(2)).__getattr__("value");
                  var10000 = var5._eq(var1.getlocal(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(404);
                     var5 = var1.getlocal(0);
                     var1.f_lasti = -1;
                     return var5;
                  }
               } else {
                  var1.setline(405);
                  PyObject var6 = var1.getlocal(4).__getattr__("type");
                  var10000 = var6._eq(var1.getglobal("token").__getattr__("NAME"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(4).__getattr__("value");
                     var10000 = var6._eq(var1.getlocal(1));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(406);
                     var5 = var1.getlocal(0);
                     var1.f_lasti = -1;
                     return var5;
                  }
               }
            }
         } else {
            var1.setline(407);
            var3 = var1.getlocal(3).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("syms").__getattr__("dotted_as_name"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(408);
               var3 = var1.getlocal(3).__getattr__("children").__getitem__(Py.newInteger(-1));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(409);
               var3 = var1.getlocal(5).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(5).__getattr__("value");
                  var10000 = var3._eq(var1.getlocal(1));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(410);
                  var5 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var5;
               }
            } else {
               var1.setline(411);
               var3 = var1.getlocal(3).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(3).__getattr__("value");
                  var10000 = var3._eq(var1.getlocal(1));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(412);
                  var5 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      } else {
         var1.setline(413);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_from"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(416);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1))).__getattr__("strip").__call__(var2);
               var10000 = var3._ne(var1.getlocal(2));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(417);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(418);
            var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(3));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(419);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("_find").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("as"), (PyObject)var1.getlocal(6));
            }

            if (var10000.__nonzero__()) {
               var1.setline(421);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(422);
            var3 = var1.getlocal(6).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_as_names"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("_find").__call__(var2, var1.getlocal(1), var1.getlocal(6));
            }

            if (var10000.__nonzero__()) {
               var1.setline(423);
               var5 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(424);
            var3 = var1.getlocal(6).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("syms").__getattr__("import_as_name"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(425);
               var3 = var1.getlocal(6).__getattr__("children").__getitem__(Py.newInteger(2));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(426);
               var3 = var1.getlocal(4).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(4).__getattr__("value");
                  var10000 = var3._eq(var1.getlocal(1));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(427);
                  var5 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var5;
               }
            } else {
               var1.setline(428);
               var3 = var1.getlocal(6).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(6).__getattr__("value");
                  var10000 = var3._eq(var1.getlocal(1));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(429);
                  var5 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setline(430);
               var10000 = var1.getlocal(2);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(6).__getattr__("type");
                  var10000 = var3._eq(var1.getglobal("token").__getattr__("STAR"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(431);
                  var5 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      }

      var1.setline(432);
      var5 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var5;
   }

   public fixer_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"keyword", "value"};
      KeywordArg$1 = Py.newCode(2, var2, var1, "KeywordArg", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LParen$2 = Py.newCode(0, var2, var1, "LParen", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RParen$3 = Py.newCode(0, var2, var1, "RParen", 24, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target", "source"};
      Assign$4 = Py.newCode(2, var2, var1, "Assign", 27, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "prefix"};
      Name$5 = Py.newCode(2, var2, var1, "Name", 38, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "attr"};
      Attr$6 = Py.newCode(2, var2, var1, "Attr", 42, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Comma$7 = Py.newCode(0, var2, var1, "Comma", 46, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Dot$8 = Py.newCode(0, var2, var1, "Dot", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "lparen", "rparen", "node"};
      ArgList$9 = Py.newCode(3, var2, var1, "ArgList", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func_name", "args", "prefix", "node"};
      Call$10 = Py.newCode(3, var2, var1, "Call", 61, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Newline$11 = Py.newCode(0, var2, var1, "Newline", 68, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BlankLine$12 = Py.newCode(0, var2, var1, "BlankLine", 72, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "prefix"};
      Number$13 = Py.newCode(2, var2, var1, "Number", 76, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"index_node"};
      Subscript$14 = Py.newCode(1, var2, var1, "Subscript", 79, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "prefix"};
      String$15 = Py.newCode(2, var2, var1, "String", 85, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"xp", "fp", "it", "test", "for_leaf", "in_leaf", "inner_args", "if_leaf", "inner"};
      ListComp$16 = Py.newCode(4, var2, var1, "ListComp", 89, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"package_name", "name_leafs", "leaf", "children", "imp"};
      FromImport$17 = Py.newCode(2, var2, var1, "FromImport", 113, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      is_tuple$18 = Py.newCode(1, var2, var1, "is_tuple", 137, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      is_list$19 = Py.newCode(1, var2, var1, "is_list", 149, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      parenthesize$20 = Py.newCode(1, var2, var1, "parenthesize", 163, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "attr", "next"};
      attr_chain$21 = Py.newCode(2, var2, var1, "attr_chain", 170, false, false, self, 21, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"node", "patterns", "pattern", "parent", "results"};
      in_special_context$22 = Py.newCode(1, var2, var1, "in_special_context", 208, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "prev", "parent"};
      is_probably_builtin$23 = Py.newCode(1, var2, var1, "is_probably_builtin", 227, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "indent"};
      find_indentation$24 = Py.newCode(1, var2, var1, "find_indentation", 250, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "parent", "suite"};
      make_suite$25 = Py.newCode(1, var2, var1, "make_suite", 264, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      find_root$26 = Py.newCode(1, var2, var1, "find_root", 273, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"package", "name", "node", "binding"};
      does_tree_import$27 = Py.newCode(3, var2, var1, "does_tree_import", 282, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      is_import$28 = Py.newCode(1, var2, var1, "is_import", 290, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"package", "name", "node", "is_import_stmt", "root", "insert_pos", "offset", "idx", "node2", "import_", "children"};
      touch_import$29 = Py.newCode(3, var2, var1, "touch_import", 294, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node"};
      is_import_stmt$30 = Py.newCode(1, var2, var1, "is_import_stmt", 297, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "node", "package", "child", "ret", "n", "i", "kid"};
      find_binding$31 = Py.newCode(3, var2, var1, "find_binding", 340, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "node", "nodes"};
      _find$32 = Py.newCode(2, var2, var1, "_find", 383, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "name", "package", "imp", "child", "last", "n"};
      _is_import_binding$33 = Py.newCode(3, var2, var1, "_is_import_binding", 393, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fixer_util$py("lib2to3/fixer_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fixer_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.KeywordArg$1(var2, var3);
         case 2:
            return this.LParen$2(var2, var3);
         case 3:
            return this.RParen$3(var2, var3);
         case 4:
            return this.Assign$4(var2, var3);
         case 5:
            return this.Name$5(var2, var3);
         case 6:
            return this.Attr$6(var2, var3);
         case 7:
            return this.Comma$7(var2, var3);
         case 8:
            return this.Dot$8(var2, var3);
         case 9:
            return this.ArgList$9(var2, var3);
         case 10:
            return this.Call$10(var2, var3);
         case 11:
            return this.Newline$11(var2, var3);
         case 12:
            return this.BlankLine$12(var2, var3);
         case 13:
            return this.Number$13(var2, var3);
         case 14:
            return this.Subscript$14(var2, var3);
         case 15:
            return this.String$15(var2, var3);
         case 16:
            return this.ListComp$16(var2, var3);
         case 17:
            return this.FromImport$17(var2, var3);
         case 18:
            return this.is_tuple$18(var2, var3);
         case 19:
            return this.is_list$19(var2, var3);
         case 20:
            return this.parenthesize$20(var2, var3);
         case 21:
            return this.attr_chain$21(var2, var3);
         case 22:
            return this.in_special_context$22(var2, var3);
         case 23:
            return this.is_probably_builtin$23(var2, var3);
         case 24:
            return this.find_indentation$24(var2, var3);
         case 25:
            return this.make_suite$25(var2, var3);
         case 26:
            return this.find_root$26(var2, var3);
         case 27:
            return this.does_tree_import$27(var2, var3);
         case 28:
            return this.is_import$28(var2, var3);
         case 29:
            return this.touch_import$29(var2, var3);
         case 30:
            return this.is_import_stmt$30(var2, var3);
         case 31:
            return this.find_binding$31(var2, var3);
         case 32:
            return this._find$32(var2, var3);
         case 33:
            return this._is_import_binding$33(var2, var3);
         default:
            return null;
      }
   }
}
