package lib2to3;

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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/btm_utils.py")
public class btm_utils$py extends PyFunctionTable implements PyRunnable {
   static btm_utils$py self;
   static final PyCode f$0;
   static final PyCode MinNode$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode leaf_to_root$4;
   static final PyCode get_linear_subpattern$5;
   static final PyCode leaves$6;
   static final PyCode reduce_tree$7;
   static final PyCode get_characteristic_subpattern$8;
   static final PyCode f$9;
   static final PyCode f$10;
   static final PyCode f$11;
   static final PyCode rec_test$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utility functions used by the btm_matcher module"));
      var1.setline(1);
      PyString.fromInterned("Utility functions used by the btm_matcher module");
      var1.setline(3);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 1);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(4);
      var3 = new String[]{"grammar", "token"};
      var5 = imp.importFrom("pgen2", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("grammar", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(5);
      var3 = new String[]{"pattern_symbols", "python_symbols"};
      var5 = imp.importFrom("pygram", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("pattern_symbols", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("python_symbols", var4);
      var4 = null;
      var1.setline(7);
      PyObject var6 = var1.getname("pattern_symbols");
      var1.setlocal("syms", var6);
      var3 = null;
      var1.setline(8);
      var6 = var1.getname("python_symbols");
      var1.setlocal("pysyms", var6);
      var3 = null;
      var1.setline(9);
      var6 = var1.getname("grammar").__getattr__("opmap");
      var1.setlocal("tokens", var6);
      var3 = null;
      var1.setline(10);
      var6 = var1.getname("token");
      var1.setlocal("token_labels", var6);
      var3 = null;
      var1.setline(12);
      PyInteger var7 = Py.newInteger(-1);
      var1.setlocal("TYPE_ANY", var7);
      var3 = null;
      var1.setline(13);
      var7 = Py.newInteger(-2);
      var1.setlocal("TYPE_ALTERNATIVES", var7);
      var3 = null;
      var1.setline(14);
      var7 = Py.newInteger(-3);
      var1.setlocal("TYPE_GROUP", var7);
      var3 = null;
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("MinNode", var5, MinNode$1);
      var1.setlocal("MinNode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(104);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var5, reduce_tree$7, PyString.fromInterned("\n    Internal function. Reduces a compiled pattern tree to an\n    intermediate representation suitable for feeding the\n    automaton. This also trims off any optional pattern elements(like\n    [a], a*).\n    "));
      var1.setlocal("reduce_tree", var8);
      var3 = null;
      var1.setline(238);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_characteristic_subpattern$8, PyString.fromInterned("Picks the most characteristic from a list of linear patterns\n    Current order used is:\n    names > common_names > common_chars\n    "));
      var1.setlocal("get_characteristic_subpattern", var8);
      var3 = null;
      var1.setline(275);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, rec_test$12, PyString.fromInterned("Tests test_func on all items of sequence and items of included\n    sub-iterables"));
      var1.setlocal("rec_test", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MinNode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class serves as an intermediate representation of the\n    pattern tree during the conversion to sets of leaf-to-root\n    subpatterns"));
      var1.setline(19);
      PyString.fromInterned("This class serves as an intermediate representation of the\n    pattern tree during the conversion to sets of leaf-to-root\n    subpatterns");
      var1.setline(21);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, leaf_to_root$4, PyString.fromInterned("Internal method. Returns a characteristic path of the\n        pattern tree. This method must be run for all leaves until the\n        linear subpatterns are merged into a single"));
      var1.setlocal("leaf_to_root", var4);
      var3 = null;
      var1.setline(75);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_linear_subpattern$5, PyString.fromInterned("Drives the leaf_to_root method. The reason that\n        leaf_to_root must be run multiple times is because we need to\n        reject 'group' matches; for example the alternative form\n        (a | b c) creates a group [b c] that needs to be matched. Since\n        matching multiple linear patterns overcomes the automaton's\n        capabilities, leaf_to_root merges each group into a single\n        choice based on 'characteristic'ity,\n\n        i.e. (a|b c) -> (a|b) if b more characteristic than c\n\n        Returns: The most 'characteristic'(as defined by\n          get_characteristic_subpattern) path for the compiled pattern\n          tree.\n        "));
      var1.setlocal("get_linear_subpattern", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, leaves$6, PyString.fromInterned("Generator that returns the leaves of the tree"));
      var1.setlocal("leaves", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(24);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"children", var4);
      var3 = null;
      var1.setline(25);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("leaf", var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("parent", var3);
      var3 = null;
      var1.setline(27);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"alternatives", var4);
      var3 = null;
      var1.setline(28);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"group", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("type"))._add(PyString.fromInterned(" "))._add(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("name")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject leaf_to_root$4(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Internal method. Returns a characteristic path of the\n        pattern tree. This method must be run for all leaves until the\n        linear subpatterns are merged into a single");
      var1.setline(37);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var4);
      var3 = null;

      while(true) {
         var1.setline(39);
         if (!var1.getlocal(1).__nonzero__()) {
            break;
         }

         var1.setline(40);
         var3 = var1.getlocal(1).__getattr__("type");
         PyObject var10000 = var3._eq(var1.getglobal("TYPE_ALTERNATIVES"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(41);
            var1.getlocal(1).__getattr__("alternatives").__getattr__("append").__call__(var2, var1.getlocal(2));
            var1.setline(42);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("alternatives"));
            var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(49);
               var3 = var1.getlocal(1).__getattr__("parent");
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(50);
               var3 = var1.getglobal("None");
               var1.setlocal(2, var3);
               var3 = null;
               break;
            }

            var1.setline(44);
            var4 = new PyList(new PyObject[]{var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getattr__("alternatives"))});
            var1.setlocal(2, var4);
            var3 = null;
            var1.setline(45);
            var4 = new PyList(Py.EmptyObjects);
            var1.getlocal(1).__setattr__((String)"alternatives", var4);
            var3 = null;
            var1.setline(46);
            var3 = var1.getlocal(1).__getattr__("parent");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(53);
            var3 = var1.getlocal(1).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("TYPE_GROUP"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(54);
               var1.getlocal(1).__getattr__("group").__getattr__("append").__call__(var2, var1.getlocal(2));
               var1.setline(56);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("group"));
               var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("children")));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(62);
                  var3 = var1.getlocal(1).__getattr__("parent");
                  var1.setlocal(1, var3);
                  var3 = null;
                  var1.setline(63);
                  var3 = var1.getglobal("None");
                  var1.setlocal(2, var3);
                  var3 = null;
                  break;
               }

               var1.setline(57);
               var3 = var1.getglobal("get_characteristic_subpattern").__call__(var2, var1.getlocal(1).__getattr__("group"));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(58);
               var4 = new PyList(Py.EmptyObjects);
               var1.getlocal(1).__setattr__((String)"group", var4);
               var3 = null;
               var1.setline(59);
               var3 = var1.getlocal(1).__getattr__("parent");
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(66);
               var3 = var1.getlocal(1).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token_labels").__getattr__("NAME"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getattr__("name");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(68);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("name"));
               } else {
                  var1.setline(70);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("type"));
               }

               var1.setline(72);
               var3 = var1.getlocal(1).__getattr__("parent");
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(73);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_linear_subpattern$5(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Drives the leaf_to_root method. The reason that\n        leaf_to_root must be run multiple times is because we need to\n        reject 'group' matches; for example the alternative form\n        (a | b c) creates a group [b c] that needs to be matched. Since\n        matching multiple linear patterns overcomes the automaton's\n        capabilities, leaf_to_root merges each group into a single\n        choice based on 'characteristic'ity,\n\n        i.e. (a|b c) -> (a|b) if b more characteristic than c\n\n        Returns: The most 'characteristic'(as defined by\n          get_characteristic_subpattern) path for the compiled pattern\n          tree.\n        ");
      var1.setline(91);
      PyObject var3 = var1.getlocal(0).__getattr__("leaves").__call__(var2).__iter__();

      PyObject var5;
      do {
         var1.setline(91);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(92);
         var5 = var1.getlocal(1).__getattr__("leaf_to_root").__call__(var2);
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(93);
      } while(!var1.getlocal(2).__nonzero__());

      var1.setline(94);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject leaves$6(PyFrame var1, ThreadState var2) {
      label38: {
         Object var10000;
         Object[] var3;
         PyObject var4;
         PyObject var5;
         PyObject var6;
         Object[] var7;
         PyObject var8;
         PyObject var9;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(97);
               PyString.fromInterned("Generator that returns the leaves of the tree");
               var1.setline(98);
               var8 = var1.getlocal(0).__getattr__("children").__iter__();
               break;
            case 1:
               var7 = var1.f_savedlocals;
               var8 = (PyObject)var7[3];
               var4 = (PyObject)var7[4];
               var5 = (PyObject)var7[5];
               var6 = (PyObject)var7[6];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var9 = (PyObject)var10000;
               var1.setline(99);
               var6 = var5.__iternext__();
               if (var6 != null) {
                  var1.setlocal(2, var6);
                  var1.setline(100);
                  var1.setline(100);
                  var9 = var1.getlocal(2);
                  var1.f_lasti = 1;
                  var7 = new Object[]{null, null, null, var8, var4, var5, var6};
                  var1.f_savedlocals = var7;
                  return var9;
               }
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var9 = (PyObject)var10000;
               break label38;
         }

         while(true) {
            var1.setline(98);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(101);
               if (var1.getlocal(0).__getattr__("children").__not__().__nonzero__()) {
                  var1.setline(102);
                  var1.setline(102);
                  var9 = var1.getlocal(0);
                  var1.f_lasti = 2;
                  var3 = new Object[8];
                  var1.f_savedlocals = var3;
                  return var9;
               }
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(99);
            var5 = var1.getlocal(1).__getattr__("leaves").__call__(var2).__iter__();
            var1.setline(99);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(2, var6);
               var1.setline(100);
               var1.setline(100);
               var9 = var1.getlocal(2);
               var1.f_lasti = 1;
               var7 = new Object[]{null, null, null, var8, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var9;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reduce_tree$7(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("\n    Internal function. Reduces a compiled pattern tree to an\n    intermediate representation suitable for feeding the\n    automaton. This also trims off any optional pattern elements(like\n    [a], a*).\n    ");
      var1.setline(112);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(114);
      var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("syms").__getattr__("Matcher"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(118);
      var3 = var1.getlocal(0).__getattr__("type");
      var10000 = var3._eq(var1.getglobal("syms").__getattr__("Alternatives"));
      var3 = null;
      String[] var4;
      PyObject var5;
      PyObject var7;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         var1.setline(120);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
         var10000 = var3._le(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(122);
            var3 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(125);
            var10000 = var1.getglobal("MinNode");
            var8 = new PyObject[]{var1.getglobal("TYPE_ALTERNATIVES")};
            var4 = new String[]{"type"};
            var10000 = var10000.__call__(var2, var8, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(127);
            var3 = var1.getlocal(0).__getattr__("children").__iter__();

            while(true) {
               var1.setline(127);
               var7 = var3.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(3, var7);
               var1.setline(128);
               if (!var1.getlocal(0).__getattr__("children").__getattr__("index").__call__(var2, var1.getlocal(3))._mod(Py.newInteger(2)).__nonzero__()) {
                  var1.setline(130);
                  var5 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(3), var1.getlocal(2));
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(131);
                  var5 = var1.getlocal(4);
                  var10000 = var5._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(132);
                     var1.getlocal(2).__getattr__("children").__getattr__("append").__call__(var2, var1.getlocal(4));
                  }
               }
            }
         }
      } else {
         var1.setline(133);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("syms").__getattr__("Alternative"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(134);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
            var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(136);
               var10000 = var1.getglobal("MinNode");
               var8 = new PyObject[]{var1.getglobal("TYPE_GROUP")};
               var4 = new String[]{"type"};
               var10000 = var10000.__call__(var2, var8, var4);
               var3 = null;
               var3 = var10000;
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(137);
               var3 = var1.getlocal(0).__getattr__("children").__iter__();

               while(true) {
                  var1.setline(137);
                  var7 = var3.__iternext__();
                  if (var7 == null) {
                     var1.setline(141);
                     if (var1.getlocal(2).__getattr__("children").__not__().__nonzero__()) {
                        var1.setline(143);
                        var3 = var1.getglobal("None");
                        var1.setlocal(2, var3);
                        var3 = null;
                     }
                     break;
                  }

                  var1.setlocal(3, var7);
                  var1.setline(138);
                  var5 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(3), var1.getlocal(2));
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(139);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(140);
                     var1.getlocal(2).__getattr__("children").__getattr__("append").__call__(var2, var1.getlocal(4));
                  }
               }
            } else {
               var1.setline(146);
               var3 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getlocal(1));
               var1.setlocal(2, var3);
               var3 = null;
            }
         } else {
            var1.setline(148);
            var3 = var1.getlocal(0).__getattr__("type");
            var10000 = var3._eq(var1.getglobal("syms").__getattr__("Unit"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(149);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getglobal("pytree").__getattr__("Leaf"));
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                  var10000 = var3._eq(PyString.fromInterned("("));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(152);
                  var3 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)), var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(153);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)), var1.getglobal("pytree").__getattr__("Leaf"));
               if (var10000.__nonzero__()) {
                  var7 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                  var10000 = var7._eq(PyString.fromInterned("["));
                  var4 = null;
               }

               if (!var10000.__nonzero__()) {
                  var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("children"));
                  var10000 = var7._gt(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("value"));
                     if (var10000.__nonzero__()) {
                        var7 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("value");
                        var10000 = var7._eq(PyString.fromInterned("["));
                        var4 = null;
                     }
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(160);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(162);
               var7 = var1.getglobal("True");
               var1.setlocal(5, var7);
               var4 = null;
               var1.setline(163);
               var7 = var1.getglobal("None");
               var1.setlocal(6, var7);
               var4 = null;
               var1.setline(164);
               var7 = var1.getglobal("None");
               var1.setlocal(7, var7);
               var4 = null;
               var1.setline(165);
               var7 = var1.getglobal("False");
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(166);
               var7 = var1.getglobal("None");
               var1.setlocal(9, var7);
               var4 = null;
               var1.setline(167);
               var7 = var1.getglobal("False");
               var1.setlocal(10, var7);
               var4 = null;
               var1.setline(169);
               var7 = var1.getlocal(0).__getattr__("children").__iter__();

               label154:
               while(true) {
                  var1.setline(169);
                  var5 = var7.__iternext__();
                  PyObject var6;
                  if (var5 == null) {
                     var1.setline(182);
                     if (var1.getlocal(10).__nonzero__()) {
                        var1.setline(184);
                        var7 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(2));
                        var1.setlocal(11, var7);
                        var4 = null;
                        var1.setline(185);
                        var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)PyString.fromInterned("value"));
                        if (var10000.__nonzero__()) {
                           var7 = var1.getlocal(11).__getattr__("value");
                           var10000 = var7._eq(PyString.fromInterned("("));
                           var4 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(187);
                           var7 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(3));
                           var1.setlocal(11, var7);
                           var4 = null;
                        }
                     } else {
                        var1.setline(189);
                        var7 = var1.getlocal(0).__getattr__("children").__getitem__(Py.newInteger(0));
                        var1.setlocal(11, var7);
                        var4 = null;
                     }

                     var1.setline(192);
                     var7 = var1.getlocal(11).__getattr__("type");
                     var10000 = var7._eq(var1.getglobal("token_labels").__getattr__("NAME"));
                     var4 = null;
                     String[] var9;
                     PyObject[] var10;
                     if (var10000.__nonzero__()) {
                        var1.setline(194);
                        var7 = var1.getlocal(11).__getattr__("value");
                        var10000 = var7._eq(PyString.fromInterned("any"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(195);
                           var10000 = var1.getglobal("MinNode");
                           var10 = new PyObject[]{var1.getglobal("TYPE_ANY")};
                           var9 = new String[]{"type"};
                           var10000 = var10000.__call__(var2, var10, var9);
                           var4 = null;
                           var7 = var10000;
                           var1.setlocal(2, var7);
                           var4 = null;
                        } else {
                           var1.setline(197);
                           if (var1.getglobal("hasattr").__call__(var2, var1.getglobal("token_labels"), var1.getlocal(11).__getattr__("value")).__nonzero__()) {
                              var1.setline(198);
                              var10000 = var1.getglobal("MinNode");
                              var10 = new PyObject[]{var1.getglobal("getattr").__call__(var2, var1.getglobal("token_labels"), var1.getlocal(11).__getattr__("value"))};
                              var9 = new String[]{"type"};
                              var10000 = var10000.__call__(var2, var10, var9);
                              var4 = null;
                              var7 = var10000;
                              var1.setlocal(2, var7);
                              var4 = null;
                           } else {
                              var1.setline(200);
                              var10000 = var1.getglobal("MinNode");
                              var10 = new PyObject[]{var1.getglobal("getattr").__call__(var2, var1.getglobal("pysyms"), var1.getlocal(11).__getattr__("value"))};
                              var9 = new String[]{"type"};
                              var10000 = var10000.__call__(var2, var10, var9);
                              var4 = null;
                              var7 = var10000;
                              var1.setlocal(2, var7);
                              var4 = null;
                           }
                        }
                     } else {
                        var1.setline(202);
                        var7 = var1.getlocal(11).__getattr__("type");
                        var10000 = var7._eq(var1.getglobal("token_labels").__getattr__("STRING"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(205);
                           var7 = var1.getlocal(11).__getattr__("value").__getattr__("strip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
                           var1.setlocal(12, var7);
                           var4 = null;
                           var1.setline(206);
                           var7 = var1.getlocal(12);
                           var10000 = var7._in(var1.getglobal("tokens"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(207);
                              var10000 = var1.getglobal("MinNode");
                              var10 = new PyObject[]{var1.getglobal("tokens").__getitem__(var1.getlocal(12))};
                              var9 = new String[]{"type"};
                              var10000 = var10000.__call__(var2, var10, var9);
                              var4 = null;
                              var7 = var10000;
                              var1.setlocal(2, var7);
                              var4 = null;
                           } else {
                              var1.setline(209);
                              var10000 = var1.getglobal("MinNode");
                              var10 = new PyObject[]{var1.getglobal("token_labels").__getattr__("NAME"), var1.getlocal(12)};
                              var9 = new String[]{"type", "name"};
                              var10000 = var10000.__call__(var2, var10, var9);
                              var4 = null;
                              var7 = var10000;
                              var1.setlocal(2, var7);
                              var4 = null;
                           }
                        } else {
                           var1.setline(210);
                           var7 = var1.getlocal(11).__getattr__("type");
                           var10000 = var7._eq(var1.getglobal("syms").__getattr__("Alternatives"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(211);
                              var7 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(7), var1.getlocal(1));
                              var1.setlocal(2, var7);
                              var4 = null;
                           }
                        }
                     }

                     var1.setline(214);
                     if (var1.getlocal(8).__nonzero__()) {
                        var1.setline(215);
                        var7 = var1.getlocal(9).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                        var10000 = var7._eq(PyString.fromInterned("*"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(217);
                           var7 = var1.getglobal("None");
                           var1.setlocal(2, var7);
                           var4 = null;
                        } else {
                           var1.setline(218);
                           var7 = var1.getlocal(9).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
                           var10000 = var7._eq(PyString.fromInterned("+"));
                           var4 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(223);
                              throw Py.makeException(var1.getglobal("NotImplementedError"));
                           }

                           var1.setline(220);
                        }
                     }

                     var1.setline(227);
                     var10000 = var1.getlocal(6);
                     if (var10000.__nonzero__()) {
                        var7 = var1.getlocal(2);
                        var10000 = var7._isnot(var1.getglobal("None"));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(228);
                        var7 = var1.getlocal(6).__getattr__("children").__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__iter__();

                        while(true) {
                           var1.setline(228);
                           var5 = var7.__iternext__();
                           if (var5 == null) {
                              break label154;
                           }

                           var1.setlocal(3, var5);
                           var1.setline(230);
                           var6 = var1.getglobal("reduce_tree").__call__(var2, var1.getlocal(3), var1.getlocal(2));
                           var1.setlocal(4, var6);
                           var6 = null;
                           var1.setline(231);
                           var6 = var1.getlocal(4);
                           var10000 = var6._isnot(var1.getglobal("None"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(232);
                              var1.getlocal(2).__getattr__("children").__getattr__("append").__call__(var2, var1.getlocal(4));
                           }
                        }
                     }
                     break;
                  }

                  var1.setlocal(3, var5);
                  var1.setline(170);
                  var6 = var1.getlocal(3).__getattr__("type");
                  var10000 = var6._eq(var1.getglobal("syms").__getattr__("Details"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(171);
                     var6 = var1.getglobal("False");
                     var1.setlocal(5, var6);
                     var6 = null;
                     var1.setline(172);
                     var6 = var1.getlocal(3);
                     var1.setlocal(6, var6);
                     var6 = null;
                  } else {
                     var1.setline(173);
                     var6 = var1.getlocal(3).__getattr__("type");
                     var10000 = var6._eq(var1.getglobal("syms").__getattr__("Repeater"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(174);
                        var6 = var1.getglobal("True");
                        var1.setlocal(8, var6);
                        var6 = null;
                        var1.setline(175);
                        var6 = var1.getlocal(3);
                        var1.setlocal(9, var6);
                        var6 = null;
                     } else {
                        var1.setline(176);
                        var6 = var1.getlocal(3).__getattr__("type");
                        var10000 = var6._eq(var1.getglobal("syms").__getattr__("Alternatives"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(177);
                           var6 = var1.getlocal(3);
                           var1.setlocal(7, var6);
                           var6 = null;
                        }
                     }
                  }

                  var1.setline(178);
                  var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("value"));
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(3).__getattr__("value");
                     var10000 = var6._eq(PyString.fromInterned("="));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(179);
                     var6 = var1.getglobal("True");
                     var1.setlocal(10, var6);
                     var6 = null;
                  }
               }
            }
         }
      }

      var1.setline(233);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(234);
         var7 = var1.getlocal(1);
         var1.getlocal(2).__setattr__("parent", var7);
         var4 = null;
      }

      var1.setline(235);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_characteristic_subpattern$8(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyString.fromInterned("Picks the most characteristic from a list of linear patterns\n    Current order used is:\n    names > common_names > common_chars\n    ");
      var1.setline(243);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(244);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(245);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(246);
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(249);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(1, var7);
            var4 = null;
            var1.setline(250);
            var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(2, var7);
            var4 = null;
            var1.setline(251);
            var7 = new PyList(new PyObject[]{PyString.fromInterned("in"), PyString.fromInterned("for"), PyString.fromInterned("if"), PyString.fromInterned("not"), PyString.fromInterned("None")});
            var1.setderef(1, var7);
            var4 = null;
            var1.setline(252);
            var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var7);
            var4 = null;
            var1.setline(253);
            PyString var9 = PyString.fromInterned("[]().,:");
            var1.setderef(0, var9);
            var4 = null;
            var1.setline(254);
            var4 = var1.getlocal(0).__iter__();

            while(true) {
               var1.setline(254);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(266);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(267);
                     var4 = var1.getlocal(1);
                     var1.setlocal(0, var4);
                     var4 = null;
                  } else {
                     var1.setline(268);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(269);
                        var4 = var1.getlocal(2);
                        var1.setlocal(0, var4);
                        var4 = null;
                     } else {
                        var1.setline(270);
                        if (var1.getlocal(3).__nonzero__()) {
                           var1.setline(271);
                           var4 = var1.getlocal(3);
                           var1.setlocal(0, var4);
                           var4 = null;
                        }
                     }
                  }

                  var1.setline(273);
                  var10000 = var1.getglobal("max");
                  PyObject[] var10 = new PyObject[]{var1.getlocal(0), var1.getglobal("len")};
                  String[] var8 = new String[]{"key"};
                  var10000 = var10000.__call__(var2, var10, var8);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(255);
               var10000 = var1.getglobal("any");
               PyObject var10002 = var1.getglobal("rec_test");
               PyObject var10004 = var1.getlocal(4);
               var1.setline(255);
               PyObject[] var6 = Py.EmptyObjects;
               if (var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)(new PyFunction(var1.f_globals, var6, f$9)))).__nonzero__()) {
                  var1.setline(256);
                  var10000 = var1.getglobal("any");
                  var10002 = var1.getglobal("rec_test");
                  var10004 = var1.getlocal(4);
                  var1.setline(257);
                  var6 = Py.EmptyObjects;
                  PyObject[] var10008 = var6;
                  PyObject var10007 = var1.f_globals;
                  PyCode var10009 = f$10;
                  var6 = new PyObject[]{var1.getclosure(0)};
                  if (var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)(new PyFunction(var10007, var10008, var10009, var6)))).__nonzero__()) {
                     var1.setline(258);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
                  } else {
                     var1.setline(259);
                     var10000 = var1.getglobal("any");
                     var10002 = var1.getglobal("rec_test");
                     var10004 = var1.getlocal(4);
                     var1.setline(260);
                     var6 = Py.EmptyObjects;
                     var10008 = var6;
                     var10007 = var1.f_globals;
                     var10009 = f$11;
                     var6 = new PyObject[]{var1.getclosure(1)};
                     if (var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)(new PyFunction(var10007, var10008, var10009, var6)))).__nonzero__()) {
                        var1.setline(261);
                        var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                     } else {
                        var1.setline(264);
                        var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._is(var1.getglobal("str"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getderef(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getderef(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rec_test$12(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      PyObject var10;
      Object var10000;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(277);
            PyString.fromInterned("Tests test_func on all items of sequence and items of included\n    sub-iterables");
            var1.setline(278);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var8 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
            var1.setline(280);
            var6 = var8.__iternext__();
            if (var6 != null) {
               var1.setlocal(3, var6);
               var1.setline(281);
               var1.setline(281);
               var10 = var1.getlocal(3);
               var1.f_lasti = 1;
               var7 = new Object[]{null, null, null, var3, var4, var8, var6};
               var1.f_savedlocals = var7;
               return var10;
            }
            break;
         case 2:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
      }

      do {
         var1.setline(278);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(279);
         var10 = var1.getglobal("isinstance");
         PyObject var10002 = var1.getlocal(2);
         PyObject[] var9 = new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")};
         PyTuple var10003 = new PyTuple(var9);
         Arrays.fill(var9, (Object)null);
         if (!var10.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003).__nonzero__()) {
            var1.setline(283);
            var1.setline(283);
            var10 = var1.getlocal(1).__call__(var2, var1.getlocal(2));
            var1.f_lasti = 2;
            var5 = new Object[8];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var10;
         }

         var1.setline(280);
         var8 = var1.getglobal("rec_test").__call__(var2, var1.getlocal(2), var1.getlocal(1)).__iter__();
         var1.setline(280);
         var6 = var8.__iternext__();
      } while(var6 == null);

      var1.setlocal(3, var6);
      var1.setline(281);
      var1.setline(281);
      var10 = var1.getlocal(3);
      var1.f_lasti = 1;
      var7 = new Object[]{null, null, null, var3, var4, var8, var6};
      var1.f_savedlocals = var7;
      return var10;
   }

   public btm_utils$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MinNode$1 = Py.newCode(0, var2, var1, "MinNode", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type", "name"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 21, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 30, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "subp"};
      leaf_to_root$4 = Py.newCode(1, var2, var1, "leaf_to_root", 33, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "subp"};
      get_linear_subpattern$5 = Py.newCode(1, var2, var1, "get_linear_subpattern", 75, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "child", "x"};
      leaves$6 = Py.newCode(1, var2, var1, "leaves", 96, false, false, self, 6, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"node", "parent", "new_node", "child", "reduced", "leaf", "details_node", "alternatives_node", "has_repeater", "repeater_node", "has_variable_name", "name_leaf", "name"};
      reduce_tree$7 = Py.newCode(2, var2, var1, "reduce_tree", 104, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"subpatterns", "subpatterns_with_names", "subpatterns_with_common_names", "subpatterns_with_common_chars", "subpattern", "common_chars", "common_names"};
      String[] var10001 = var2;
      btm_utils$py var10007 = self;
      var2 = new String[]{"common_chars", "common_names"};
      get_characteristic_subpattern$8 = Py.newCode(1, var10001, var1, "get_characteristic_subpattern", 238, false, false, var10007, 8, var2, (String[])null, 2, 4097);
      var2 = new String[]{"x"};
      f$9 = Py.newCode(1, var2, var1, "<lambda>", 255, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"common_chars"};
      f$10 = Py.newCode(1, var10001, var1, "<lambda>", 257, false, false, var10007, 10, (String[])null, var2, 0, 4097);
      var2 = new String[]{"x"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"common_names"};
      f$11 = Py.newCode(1, var10001, var1, "<lambda>", 260, false, false, var10007, 11, (String[])null, var2, 0, 4097);
      var2 = new String[]{"sequence", "test_func", "x", "y"};
      rec_test$12 = Py.newCode(2, var2, var1, "rec_test", 275, false, false, self, 12, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new btm_utils$py("lib2to3/btm_utils$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(btm_utils$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MinNode$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.leaf_to_root$4(var2, var3);
         case 5:
            return this.get_linear_subpattern$5(var2, var3);
         case 6:
            return this.leaves$6(var2, var3);
         case 7:
            return this.reduce_tree$7(var2, var3);
         case 8:
            return this.get_characteristic_subpattern$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.rec_test$12(var2, var3);
         default:
            return null;
      }
   }
}
