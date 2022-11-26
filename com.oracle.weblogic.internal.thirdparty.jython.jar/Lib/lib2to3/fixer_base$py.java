package lib2to3;

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
@Filename("lib2to3/fixer_base.py")
public class fixer_base$py extends PyFunctionTable implements PyRunnable {
   static fixer_base$py self;
   static final PyCode f$0;
   static final PyCode BaseFix$1;
   static final PyCode __init__$2;
   static final PyCode compile_pattern$3;
   static final PyCode set_filename$4;
   static final PyCode match$5;
   static final PyCode transform$6;
   static final PyCode new_name$7;
   static final PyCode log_message$8;
   static final PyCode cannot_convert$9;
   static final PyCode warning$10;
   static final PyCode start_tree$11;
   static final PyCode finish_tree$12;
   static final PyCode ConditionalFix$13;
   static final PyCode start_tree$14;
   static final PyCode should_skip$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Base class for fixers (optional, but recommended)."));
      var1.setline(4);
      PyString.fromInterned("Base class for fixers (optional, but recommended).");
      var1.setline(7);
      PyObject var3 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("itertools", var1, -1);
      var1.setlocal("itertools", var3);
      var3 = null;
      var1.setline(11);
      String[] var5 = new String[]{"PatternCompiler"};
      PyObject[] var6 = imp.importFrom("patcomp", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("PatternCompiler", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"pygram"};
      var6 = imp.importFrom("", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("pygram", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"does_tree_import"};
      var6 = imp.importFrom("fixer_util", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("does_tree_import", var4);
      var4 = null;
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BaseFix", var6, BaseFix$1);
      var1.setlocal("BaseFix", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(172);
      var6 = new PyObject[]{var1.getname("BaseFix")};
      var4 = Py.makeClass("ConditionalFix", var6, ConditionalFix$13);
      var1.setlocal("ConditionalFix", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BaseFix$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Optional base class for fixers.\n\n    The subclass name must be FixFooBar where FooBar is the result of\n    removing underscores and capitalizing the words of the fix name.\n    For example, the class name for a fixer named 'has_key' should be\n    FixHasKey.\n    "));
      var1.setline(23);
      PyString.fromInterned("Optional base class for fixers.\n\n    The subclass name must be FixFooBar where FooBar is the result of\n    removing underscores and capitalizing the words of the fix name.\n    For example, the class name for a fixer named 'has_key' should be\n    FixHasKey.\n    ");
      var1.setline(25);
      PyObject var3 = var1.getname("None");
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getname("None");
      var1.setlocal("pattern", var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getname("None");
      var1.setlocal("pattern_tree", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getname("None");
      var1.setlocal("options", var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getname("None");
      var1.setlocal("filename", var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getname("None");
      var1.setlocal("logger", var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getname("itertools").__getattr__("count").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal("numbers", var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getname("set").__call__(var2);
      var1.setlocal("used_names", var3);
      var3 = null;
      var1.setline(33);
      PyString var4 = PyString.fromInterned("post");
      var1.setlocal("order", var4);
      var3 = null;
      var1.setline(34);
      var3 = var1.getname("False");
      var1.setlocal("explicit", var3);
      var3 = null;
      var1.setline(35);
      PyInteger var5 = Py.newInteger(5);
      var1.setlocal("run_order", var5);
      var3 = null;
      var1.setline(37);
      var3 = var1.getname("None");
      var1.setlocal("_accept_type", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getname("False");
      var1.setlocal("keep_line_order", var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getname("False");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("pygram").__getattr__("python_symbols");
      var1.setlocal("syms", var3);
      var3 = null;
      var1.setline(49);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$2, PyString.fromInterned("Initializer.  Subclass may override.\n\n        Args:\n            options: an dict containing the options passed to RefactoringTool\n            that could be used to customize the fixer through the command line.\n            log: a list to append warnings and other messages to.\n        "));
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(61);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, compile_pattern$3, PyString.fromInterned("Compiles self.PATTERN into self.pattern.\n\n        Subclass may override if it doesn't want to use\n        self.{pattern,PATTERN} in .match().\n        "));
      var1.setlocal("compile_pattern", var7);
      var3 = null;
      var1.setline(72);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_filename$4, PyString.fromInterned("Set the filename, and a logger derived from it.\n\n        The main refactoring tool should call this.\n        "));
      var1.setlocal("set_filename", var7);
      var3 = null;
      var1.setline(80);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, match$5, PyString.fromInterned("Returns match for a given parse tree node.\n\n        Should return a true or false object (not necessarily a bool).\n        It may return a non-empty dict of matching sub-nodes as\n        returned by a matching pattern.\n\n        Subclass may override.\n        "));
      var1.setlocal("match", var7);
      var3 = null;
      var1.setline(92);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, transform$6, PyString.fromInterned("Returns the transformation for a given parse tree node.\n\n        Args:\n          node: the root of the parse tree that matched the fixer.\n          results: a dict mapping symbolic names to part of the match.\n\n        Returns:\n          None, or a node that is a modified copy of the\n          argument node.  The node argument may also be modified in-place to\n          effect the same change.\n\n        Subclass *must* override.\n        "));
      var1.setlocal("transform", var7);
      var3 = null;
      var1.setline(108);
      var6 = new PyObject[]{PyUnicode.fromInterned("xxx_todo_changeme")};
      var7 = new PyFunction(var1.f_globals, var6, new_name$7, PyString.fromInterned("Return a string suitable for use as an identifier\n\n        The new name is guaranteed not to conflict with other identifiers.\n        "));
      var1.setlocal("new_name", var7);
      var3 = null;
      var1.setline(119);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, log_message$8, (PyObject)null);
      var1.setlocal("log_message", var7);
      var3 = null;
      var1.setline(125);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, cannot_convert$9, PyString.fromInterned("Warn the user that a given chunk of code is not valid Python 3,\n        but that it cannot be converted automatically.\n\n        First argument is the top-level node for the code in question.\n        Optional second argument is why it can't be converted.\n        "));
      var1.setlocal("cannot_convert", var7);
      var3 = null;
      var1.setline(140);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, warning$10, PyString.fromInterned("Used for warning the user about possible uncertainty in the\n        translation.\n\n        First argument is the top-level node for the code in question.\n        Optional second argument is why it can't be converted.\n        "));
      var1.setlocal("warning", var7);
      var3 = null;
      var1.setline(150);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, start_tree$11, PyString.fromInterned("Some fixers need to maintain tree-wide state.\n        This method is called once, at the start of tree fix-up.\n\n        tree - the root node of the tree to be processed.\n        filename - the name of the file the tree came from.\n        "));
      var1.setlocal("start_tree", var7);
      var3 = null;
      var1.setline(162);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finish_tree$12, PyString.fromInterned("Some fixers need to maintain tree-wide state.\n        This method is called once, at the conclusion of tree fix-up.\n\n        tree - the root node of the tree to be processed.\n        filename - the name of the file the tree came from.\n        "));
      var1.setlocal("finish_tree", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString.fromInterned("Initializer.  Subclass may override.\n\n        Args:\n            options: an dict containing the options passed to RefactoringTool\n            that could be used to customize the fixer through the command line.\n            log: a list to append warnings and other messages to.\n        ");
      var1.setline(57);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("options", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("log", var3);
      var3 = null;
      var1.setline(59);
      var1.getlocal(0).__getattr__("compile_pattern").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile_pattern$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Compiles self.PATTERN into self.pattern.\n\n        Subclass may override if it doesn't want to use\n        self.{pattern,PATTERN} in .match().\n        ");
      var1.setline(67);
      PyObject var3 = var1.getlocal(0).__getattr__("PATTERN");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(68);
         var3 = var1.getglobal("PatternCompiler").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(69);
         var10000 = var1.getlocal(1).__getattr__("compile_pattern");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("PATTERN"), var1.getglobal("True")};
         String[] var4 = new String[]{"with_tree"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         PyObject[] var7 = Py.unpackSequence(var3, 2);
         PyObject var5 = var7[0];
         var1.getlocal(0).__setattr__("pattern", var5);
         var5 = null;
         var5 = var7[1];
         var1.getlocal(0).__setattr__("pattern_tree", var5);
         var5 = null;
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_filename$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("Set the filename, and a logger derived from it.\n\n        The main refactoring tool should call this.\n        ");
      var1.setline(77);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getglobal("logging").__getattr__("getLogger").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("logger", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject match$5(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned("Returns match for a given parse tree node.\n\n        Should return a true or false object (not necessarily a bool).\n        It may return a non-empty dict of matching sub-nodes as\n        returned by a matching pattern.\n\n        Subclass may override.\n        ");
      var1.setline(89);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("node"), var1.getlocal(1)});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(90);
      PyObject var10000 = var1.getlocal(0).__getattr__("pattern").__getattr__("match").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject transform$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("Returns the transformation for a given parse tree node.\n\n        Args:\n          node: the root of the parse tree that matched the fixer.\n          results: a dict mapping symbolic names to part of the match.\n\n        Returns:\n          None, or a node that is a modified copy of the\n          argument node.  The node argument may also be modified in-place to\n          effect the same change.\n\n        Subclass *must* override.\n        ");
      var1.setline(106);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject new_name$7(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Return a string suitable for use as an identifier\n\n        The new name is guaranteed not to conflict with other identifiers.\n        ");
      var1.setline(113);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(114);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("used_names"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(116);
            var1.getlocal(0).__getattr__("used_names").__getattr__("add").__call__(var2, var1.getlocal(2));
            var1.setline(117);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(115);
         var3 = var1.getlocal(1)._add(var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("numbers").__getattr__("next").__call__(var2)));
         var1.setlocal(2, var3);
         var3 = null;
      }
   }

   public PyObject log_message$8(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      if (var1.getlocal(0).__getattr__("first_log").__nonzero__()) {
         var1.setline(121);
         PyObject var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("first_log", var3);
         var3 = null;
         var1.setline(122);
         var1.getlocal(0).__getattr__("log").__getattr__("append").__call__(var2, PyString.fromInterned("### In file %s ###")._mod(var1.getlocal(0).__getattr__("filename")));
      }

      var1.setline(123);
      var1.getlocal(0).__getattr__("log").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cannot_convert$9(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("Warn the user that a given chunk of code is not valid Python 3,\n        but that it cannot be converted automatically.\n\n        First argument is the top-level node for the code in question.\n        Optional second argument is why it can't be converted.\n        ");
      var1.setline(132);
      PyObject var3 = var1.getlocal(1).__getattr__("get_lineno").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(1).__getattr__("clone").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(134);
      PyUnicode var4 = PyUnicode.fromInterned("");
      var1.getlocal(4).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(135);
      PyString var5 = PyString.fromInterned("Line %d: could not convert: %s");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(136);
      var1.getlocal(0).__getattr__("log_message").__call__(var2, var1.getlocal(5)._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
      var1.setline(137);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(138);
         var1.getlocal(0).__getattr__("log_message").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warning$10(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyString.fromInterned("Used for warning the user about possible uncertainty in the\n        translation.\n\n        First argument is the top-level node for the code in question.\n        Optional second argument is why it can't be converted.\n        ");
      var1.setline(147);
      PyObject var3 = var1.getlocal(1).__getattr__("get_lineno").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(148);
      var1.getlocal(0).__getattr__("log_message").__call__(var2, PyString.fromInterned("Line %d: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_tree$11(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyString.fromInterned("Some fixers need to maintain tree-wide state.\n        This method is called once, at the start of tree fix-up.\n\n        tree - the root node of the tree to be processed.\n        filename - the name of the file the tree came from.\n        ");
      var1.setline(157);
      PyObject var3 = var1.getlocal(1).__getattr__("used_names");
      var1.getlocal(0).__setattr__("used_names", var3);
      var3 = null;
      var1.setline(158);
      var1.getlocal(0).__getattr__("set_filename").__call__(var2, var1.getlocal(2));
      var1.setline(159);
      var3 = var1.getglobal("itertools").__getattr__("count").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.getlocal(0).__setattr__("numbers", var3);
      var3 = null;
      var1.setline(160);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("first_log", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finish_tree$12(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("Some fixers need to maintain tree-wide state.\n        This method is called once, at the conclusion of tree fix-up.\n\n        tree - the root node of the tree to be processed.\n        filename - the name of the file the tree came from.\n        ");
      var1.setline(169);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ConditionalFix$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Base class for fixers which not execute if an import is found. "));
      var1.setline(173);
      PyString.fromInterned(" Base class for fixers which not execute if an import is found. ");
      var1.setline(176);
      PyObject var3 = var1.getname("None");
      var1.setlocal("skip_on", var3);
      var3 = null;
      var1.setline(178);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, start_tree$14, (PyObject)null);
      var1.setlocal("start_tree", var5);
      var3 = null;
      var1.setline(182);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, should_skip$15, (PyObject)null);
      var1.setlocal("should_skip", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject start_tree$14(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("ConditionalFix"), var1.getlocal(0)).__getattr__("start_tree");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.setline(180);
      PyObject var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_should_skip", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject should_skip$15(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyObject var3 = var1.getlocal(0).__getattr__("_should_skip");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(184);
         var3 = var1.getlocal(0).__getattr__("_should_skip");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(185);
         PyObject var4 = var1.getlocal(0).__getattr__("skip_on").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(186);
         var4 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(187);
         var4 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(188);
         var4 = var1.getglobal("does_tree_import").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
         var1.getlocal(0).__setattr__("_should_skip", var4);
         var4 = null;
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("_should_skip");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fixer_base$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BaseFix$1 = Py.newCode(0, var2, var1, "BaseFix", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "options", "log"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 49, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "PC"};
      compile_pattern$3 = Py.newCode(1, var2, var1, "compile_pattern", 61, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      set_filename$4 = Py.newCode(2, var2, var1, "set_filename", 72, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      match$5 = Py.newCode(2, var2, var1, "match", 80, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      transform$6 = Py.newCode(3, var2, var1, "transform", 92, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "template", "name"};
      new_name$7 = Py.newCode(2, var2, var1, "new_name", 108, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      log_message$8 = Py.newCode(2, var2, var1, "log_message", 119, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "reason", "lineno", "for_output", "msg"};
      cannot_convert$9 = Py.newCode(3, var2, var1, "cannot_convert", 125, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "reason", "lineno"};
      warning$10 = Py.newCode(3, var2, var1, "warning", 140, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "filename"};
      start_tree$11 = Py.newCode(3, var2, var1, "start_tree", 150, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "filename"};
      finish_tree$12 = Py.newCode(3, var2, var1, "finish_tree", 162, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConditionalFix$13 = Py.newCode(0, var2, var1, "ConditionalFix", 172, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      start_tree$14 = Py.newCode(2, var2, var1, "start_tree", 178, true, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "pkg", "name"};
      should_skip$15 = Py.newCode(2, var2, var1, "should_skip", 182, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fixer_base$py("lib2to3/fixer_base$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fixer_base$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BaseFix$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.compile_pattern$3(var2, var3);
         case 4:
            return this.set_filename$4(var2, var3);
         case 5:
            return this.match$5(var2, var3);
         case 6:
            return this.transform$6(var2, var3);
         case 7:
            return this.new_name$7(var2, var3);
         case 8:
            return this.log_message$8(var2, var3);
         case 9:
            return this.cannot_convert$9(var2, var3);
         case 10:
            return this.warning$10(var2, var3);
         case 11:
            return this.start_tree$11(var2, var3);
         case 12:
            return this.finish_tree$12(var2, var3);
         case 13:
            return this.ConditionalFix$13(var2, var3);
         case 14:
            return this.start_tree$14(var2, var3);
         case 15:
            return this.should_skip$15(var2, var3);
         default:
            return null;
      }
   }
}
