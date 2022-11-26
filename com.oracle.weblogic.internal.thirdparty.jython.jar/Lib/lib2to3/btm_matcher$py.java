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
@Filename("lib2to3/btm_matcher.py")
public class btm_matcher$py extends PyFunctionTable implements PyRunnable {
   static btm_matcher$py self;
   static final PyCode f$0;
   static final PyCode BMNode$1;
   static final PyCode __init__$2;
   static final PyCode BottomMatcher$3;
   static final PyCode __init__$4;
   static final PyCode add_fixer$5;
   static final PyCode add$6;
   static final PyCode run$7;
   static final PyCode print_ac$8;
   static final PyCode print_node$9;
   static final PyCode type_repr$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A bottom-up tree matching algorithm implementation meant to speed\nup 2to3's matching process. After the tree patterns are reduced to\ntheir rarest linear path, a linear Aho-Corasick automaton is\ncreated. The linear automaton traverses the linear paths from the\nleaves to the root of the AST and returns a set of nodes for further\nmatching. This reduces significantly the number of candidate nodes."));
      var1.setline(6);
      PyString.fromInterned("A bottom-up tree matching algorithm implementation meant to speed\nup 2to3's matching process. After the tree patterns are reduced to\ntheir rarest linear path, a linear Aho-Corasick automaton is\ncreated. The linear automaton traverses the linear paths from the\nleaves to the root of the AST and returns a set of nodes for further\nmatching. This reduces significantly the number of candidate nodes.");
      var1.setline(8);
      PyString var3 = PyString.fromInterned("George Boutsioukis <gboutsioukis@gmail.com>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(10);
      PyObject var5 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var5);
      var3 = null;
      var1.setline(11);
      var5 = imp.importOne("itertools", var1, -1);
      var1.setlocal("itertools", var5);
      var3 = null;
      var1.setline(12);
      String[] var6 = new String[]{"defaultdict"};
      PyObject[] var7 = imp.importFrom("collections", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("defaultdict", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"pytree"};
      var7 = imp.importFrom("", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"reduce_tree"};
      var7 = imp.importFrom("btm_utils", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("reduce_tree", var4);
      var4 = null;
      var1.setline(17);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BMNode", var7, BMNode$1);
      var1.setlocal("BMNode", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(26);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BottomMatcher", var7, BottomMatcher$3);
      var1.setlocal("BottomMatcher", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(159);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_type_reprs", var8);
      var3 = null;
      var1.setline(160);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, type_repr$10, (PyObject)null);
      var1.setlocal("type_repr", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BMNode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for a node of the Aho-Corasick automaton used in matching"));
      var1.setline(18);
      PyString.fromInterned("Class for a node of the Aho-Corasick automaton used in matching");
      var1.setline(19);
      PyObject var3 = var1.getname("itertools").__getattr__("count").__call__(var2);
      var1.setlocal("count", var3);
      var3 = null;
      var1.setline(20);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"transition_table", var3);
      var3 = null;
      var1.setline(22);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"fixers", var4);
      var3 = null;
      var1.setline(23);
      PyObject var5 = var1.getglobal("next").__call__(var2, var1.getglobal("BMNode").__getattr__("count"));
      var1.getlocal(0).__setattr__("id", var5);
      var3 = null;
      var1.setline(24);
      PyString var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"content", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BottomMatcher$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The main matcher class. After instantiating the patterns should\n    be added using the add_fixer method"));
      var1.setline(28);
      PyString.fromInterned("The main matcher class. After instantiating the patterns should\n    be added using the add_fixer method");
      var1.setline(30);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_fixer$5, PyString.fromInterned("Reduces a fixer's pattern tree to a linear path and adds it\n        to the matcher(a common Aho-Corasick automaton). The fixer is\n        appended on the matching states and called when they are\n        reached"));
      var1.setlocal("add_fixer", var4);
      var3 = null;
      var1.setline(49);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$6, PyString.fromInterned("Recursively adds a linear pattern to the AC automaton"));
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$7, PyString.fromInterned("The main interface with the bottom matcher. The tree is\n        traversed from the bottom using the constructed\n        automaton. Nodes are only checked once as the tree is\n        retraversed. When the automaton fails, we give it one more\n        shot(in case the above tree matches as a whole with the\n        rejected leaf), then we break for the next leaf. There is the\n        special case of multiple arguments(see code comments) where we\n        recheck the nodes\n\n        Args:\n           The leaves of the AST tree to be matched\n\n        Returns:\n           A dictionary of node matches with fixers as the keys\n        "));
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_ac$8, PyString.fromInterned("Prints a graphviz diagram of the BM automaton(for debugging)"));
      var1.setlocal("print_ac", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.getlocal(0).__setattr__("match", var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getglobal("BMNode").__call__(var2);
      var1.getlocal(0).__setattr__("root", var3);
      var3 = null;
      var1.setline(33);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("root")});
      var1.getlocal(0).__setattr__((String)"nodes", var4);
      var3 = null;
      var1.setline(34);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"fixers", var4);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RefactoringTool"));
      var1.getlocal(0).__setattr__("logger", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_fixer$5(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyString.fromInterned("Reduces a fixer's pattern tree to a linear path and adds it\n        to the matcher(a common Aho-Corasick automaton). The fixer is\n        appended on the matching states and called when they are\n        reached");
      var1.setline(42);
      var1.getlocal(0).__getattr__("fixers").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(43);
      PyObject var3 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(1).__getattr__("pattern_tree"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(2).__getattr__("get_linear_subpattern").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(45);
      PyObject var10000 = var1.getlocal(0).__getattr__("add");
      PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("root")};
      String[] var4 = new String[]{"start"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(46);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var6);
         var1.setline(47);
         var1.getlocal(5).__getattr__("fixers").__getattr__("append").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject add$6(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyString.fromInterned("Recursively adds a linear pattern to the AC automaton");
      var1.setline(52);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(54);
         PyList var8 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(55);
         PyObject var3;
         PyList var4;
         PyObject var9;
         PyObject var10000;
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(69);
            var9 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var10000 = var9._notin(var1.getlocal(2).__getattr__("transition_table"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(71);
               var9 = var1.getglobal("BMNode").__call__(var2);
               var1.setlocal(7, var9);
               var4 = null;
               var1.setline(72);
               var9 = var1.getlocal(7);
               var1.getlocal(2).__getattr__("transition_table").__setitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)), var9);
               var4 = null;
            } else {
               var1.setline(75);
               var9 = var1.getlocal(2).__getattr__("transition_table").__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)));
               var1.setlocal(7, var9);
               var4 = null;
            }

            var1.setline(77);
            if (var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
               var1.setline(78);
               var10000 = var1.getlocal(0).__getattr__("add");
               PyObject[] var13 = new PyObject[]{var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(7)};
               String[] var10 = new String[]{"start"};
               var10000 = var10000.__call__(var2, var13, var10);
               var4 = null;
               var9 = var10000;
               var1.setlocal(5, var9);
               var4 = null;
            } else {
               var1.setline(80);
               var4 = new PyList(new PyObject[]{var1.getlocal(7)});
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(81);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(58);
            var4 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(59);
            var9 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__iter__();

            while(true) {
               var1.setline(59);
               PyObject var5 = var9.__iternext__();
               if (var5 == null) {
                  var1.setline(65);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(62);
               var10000 = var1.getlocal(0).__getattr__("add");
               PyObject[] var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(2)};
               String[] var7 = new String[]{"start"};
               var10000 = var10000.__call__(var2, var6, var7);
               var6 = null;
               PyObject var11 = var10000;
               var1.setlocal(5, var11);
               var6 = null;
               var1.setline(63);
               var11 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(63);
                  PyObject var12 = var11.__iternext__();
                  if (var12 == null) {
                     break;
                  }

                  var1.setlocal(6, var12);
                  var1.setline(64);
                  var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("add").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(6)));
               }
            }
         }
      }
   }

   public PyObject run$7(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("The main interface with the bottom matcher. The tree is\n        traversed from the bottom using the constructed\n        automaton. Nodes are only checked once as the tree is\n        retraversed. When the automaton fails, we give it one more\n        shot(in case the above tree matches as a whole with the\n        rejected leaf), then we break for the next leaf. There is the\n        special case of multiple arguments(see code comments) where we\n        recheck the nodes\n\n        Args:\n           The leaves of the AST tree to be matched\n\n        Returns:\n           A dictionary of node matches with fixers as the keys\n        ");
      var1.setline(99);
      PyObject var3 = var1.getlocal(0).__getattr__("root");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("defaultdict").__call__(var2, var1.getglobal("list"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(101);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(142);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(102);
         PyObject var5 = var1.getlocal(4);
         var1.setlocal(5, var5);
         var5 = null;

         while(true) {
            var1.setline(103);
            if (!var1.getlocal(5).__nonzero__()) {
               break;
            }

            var1.setline(104);
            var5 = var1.getglobal("True");
            var1.getlocal(5).__setattr__("was_checked", var5);
            var5 = null;
            var1.setline(105);
            var5 = var1.getlocal(5).__getattr__("children").__iter__();

            PyObject var10000;
            PyObject var6;
            PyObject var7;
            while(true) {
               var1.setline(105);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(6, var6);
               var1.setline(107);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("pytree").__getattr__("Leaf"));
               if (var10000.__nonzero__()) {
                  var7 = var1.getlocal(6).__getattr__("value");
                  var10000 = var7._eq(PyUnicode.fromInterned(";"));
                  var7 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(108);
                  var7 = var1.getglobal("False");
                  var1.getlocal(5).__setattr__("was_checked", var7);
                  var7 = null;
                  break;
               }
            }

            var1.setline(110);
            var5 = var1.getlocal(5).__getattr__("type");
            var10000 = var5._eq(Py.newInteger(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(112);
               var5 = var1.getlocal(5).__getattr__("value");
               var1.setlocal(7, var5);
               var5 = null;
            } else {
               var1.setline(114);
               var5 = var1.getlocal(5).__getattr__("type");
               var1.setlocal(7, var5);
               var5 = null;
            }

            var1.setline(116);
            var5 = var1.getlocal(7);
            var10000 = var5._in(var1.getlocal(2).__getattr__("transition_table"));
            var5 = null;
            PyList var8;
            if (var10000.__nonzero__()) {
               var1.setline(118);
               var5 = var1.getlocal(2).__getattr__("transition_table").__getitem__(var1.getlocal(7));
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(119);
               var5 = var1.getlocal(2).__getattr__("fixers").__iter__();

               while(true) {
                  var1.setline(119);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(8, var6);
                  var1.setline(120);
                  var7 = var1.getlocal(8);
                  var10000 = var7._in(var1.getlocal(3));
                  var7 = null;
                  if (var10000.__not__().__nonzero__()) {
                     var1.setline(121);
                     var8 = new PyList(Py.EmptyObjects);
                     var1.getlocal(3).__setitem__((PyObject)var1.getlocal(8), var8);
                     var7 = null;
                  }

                  var1.setline(122);
                  var1.getlocal(3).__getitem__(var1.getlocal(8)).__getattr__("append").__call__(var2, var1.getlocal(5));
               }
            } else {
               var1.setline(126);
               var5 = var1.getlocal(0).__getattr__("root");
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(127);
               var5 = var1.getlocal(5).__getattr__("parent");
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(5).__getattr__("parent").__getattr__("was_checked");
               }

               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(133);
               var5 = var1.getlocal(7);
               var10000 = var5._in(var1.getlocal(2).__getattr__("transition_table"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(135);
                  var5 = var1.getlocal(2).__getattr__("transition_table").__getitem__(var1.getlocal(7));
                  var1.setlocal(2, var5);
                  var5 = null;
                  var1.setline(136);
                  var5 = var1.getlocal(2).__getattr__("fixers").__iter__();

                  while(true) {
                     var1.setline(136);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(8, var6);
                     var1.setline(137);
                     var7 = var1.getlocal(8);
                     var10000 = var7._in(var1.getlocal(3).__getattr__("keys").__call__(var2));
                     var7 = null;
                     if (var10000.__not__().__nonzero__()) {
                        var1.setline(138);
                        var8 = new PyList(Py.EmptyObjects);
                        var1.getlocal(3).__setitem__((PyObject)var1.getlocal(8), var8);
                        var7 = null;
                     }

                     var1.setline(139);
                     var1.getlocal(3).__getitem__(var1.getlocal(8)).__getattr__("append").__call__(var2, var1.getlocal(5));
                  }
               }
            }

            var1.setline(141);
            var5 = var1.getlocal(5).__getattr__("parent");
            var1.setlocal(5, var5);
            var5 = null;
         }
      }
   }

   public PyObject print_ac$8(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyString.fromInterned("Prints a graphviz diagram of the BM automaton(for debugging)");
      var1.setline(146);
      Py.println(PyString.fromInterned("digraph g{"));
      var1.setline(147);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = print_node$9;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setderef(0, var4);
      var3 = null;
      var1.setline(155);
      var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("root"));
      var1.setline(156);
      Py.println(PyString.fromInterned("}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_node$9(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = var1.getlocal(0).__getattr__("transition_table").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(148);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(149);
         PyObject var5 = var1.getlocal(0).__getattr__("transition_table").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(150);
         Py.println(PyString.fromInterned("%d -> %d [label=%s] //%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("id"), var1.getlocal(2).__getattr__("id"), var1.getglobal("type_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("fixers"))})));
         var1.setline(152);
         var5 = var1.getlocal(1);
         PyObject var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(153);
            Py.println(var1.getlocal(2).__getattr__("content"));
         }

         var1.setline(154);
         var1.getderef(0).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject type_repr$10(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var8;
      if (var1.getglobal("_type_reprs").__not__().__nonzero__()) {
         var1.setline(163);
         String[] var3 = new String[]{"python_symbols"};
         PyObject[] var7 = imp.importFrom("pygram", var3, var1, 1);
         PyObject var4 = var7[0];
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(166);
         var8 = var1.getlocal(1).__getattr__("__dict__").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(166);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(167);
            PyObject var9 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
            PyObject var10000 = var9._eq(var1.getglobal("int"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(167);
               var9 = var1.getlocal(2);
               var1.getglobal("_type_reprs").__setitem__(var1.getlocal(3), var9);
               var5 = null;
            }
         }
      }

      var1.setline(168);
      var8 = var1.getglobal("_type_reprs").__getattr__("setdefault").__call__(var2, var1.getlocal(0), var1.getlocal(0));
      var1.f_lasti = -1;
      return var8;
   }

   public btm_matcher$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BMNode$1 = Py.newCode(0, var2, var1, "BMNode", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BottomMatcher$3 = Py.newCode(0, var2, var1, "BottomMatcher", 26, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$4 = Py.newCode(1, var2, var1, "__init__", 30, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fixer", "tree", "linear", "match_nodes", "match_node"};
      add_fixer$5 = Py.newCode(2, var2, var1, "add_fixer", 37, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pattern", "start", "match_nodes", "alternative", "end_nodes", "end", "next_node"};
      add$6 = Py.newCode(3, var2, var1, "add", 49, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "leaves", "current_ac_node", "results", "leaf", "current_ast_node", "child", "node_token", "fixer"};
      run$7 = Py.newCode(2, var2, var1, "run", 83, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "print_node"};
      String[] var10001 = var2;
      btm_matcher$py var10007 = self;
      var2 = new String[]{"print_node"};
      print_ac$8 = Py.newCode(1, var10001, var1, "print_ac", 144, false, false, var10007, 8, var2, (String[])null, 1, 4097);
      var2 = new String[]{"node", "subnode_key", "subnode"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"print_node"};
      print_node$9 = Py.newCode(1, var10001, var1, "print_node", 147, false, false, var10007, 9, (String[])null, var2, 0, 4097);
      var2 = new String[]{"type_num", "python_symbols", "name", "val"};
      type_repr$10 = Py.newCode(1, var2, var1, "type_repr", 160, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new btm_matcher$py("lib2to3/btm_matcher$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(btm_matcher$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BMNode$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.BottomMatcher$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.add_fixer$5(var2, var3);
         case 6:
            return this.add$6(var2, var3);
         case 7:
            return this.run$7(var2, var3);
         case 8:
            return this.print_ac$8(var2, var3);
         case 9:
            return this.print_node$9(var2, var3);
         case 10:
            return this.type_repr$10(var2, var3);
         default:
            return null;
      }
   }
}
