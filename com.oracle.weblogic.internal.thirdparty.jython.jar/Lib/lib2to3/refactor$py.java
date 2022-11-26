package lib2to3;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("lib2to3/refactor.py")
public class refactor$py extends PyFunctionTable implements PyRunnable {
   static refactor$py self;
   static final PyCode f$0;
   static final PyCode get_all_fix_names$1;
   static final PyCode _EveryNode$2;
   static final PyCode _get_head_types$3;
   static final PyCode _get_headnode_dict$4;
   static final PyCode get_fixers_from_package$5;
   static final PyCode _identity$6;
   static final PyCode _from_system_newlines$7;
   static final PyCode _to_system_newlines$8;
   static final PyCode _detect_future_features$9;
   static final PyCode advance$10;
   static final PyCode FixerError$11;
   static final PyCode RefactoringTool$12;
   static final PyCode __init__$13;
   static final PyCode get_fixers$14;
   static final PyCode log_error$15;
   static final PyCode log_message$16;
   static final PyCode log_debug$17;
   static final PyCode print_output$18;
   static final PyCode refactor$19;
   static final PyCode refactor_dir$20;
   static final PyCode _read_python_source$21;
   static final PyCode refactor_file$22;
   static final PyCode refactor_string$23;
   static final PyCode refactor_stdin$24;
   static final PyCode refactor_tree$25;
   static final PyCode traverse_by$26;
   static final PyCode processed_file$27;
   static final PyCode write_file$28;
   static final PyCode refactor_docstring$29;
   static final PyCode refactor_doctest$30;
   static final PyCode summarize$31;
   static final PyCode parse_block$32;
   static final PyCode wrap_toks$33;
   static final PyCode gen_lines$34;
   static final PyCode MultiprocessingUnsupported$35;
   static final PyCode MultiprocessRefactoringTool$36;
   static final PyCode __init__$37;
   static final PyCode refactor$38;
   static final PyCode _child$39;
   static final PyCode refactor_file$40;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Refactoring framework.\n\nUsed as a main program, this can refactor any number of files and/or\nrecursively descend down directories.  Imported as a module, this\nprovides infrastructure to write your own refactoring tool.\n"));
      var1.setline(9);
      PyString.fromInterned("Refactoring framework.\n\nUsed as a main program, this can refactor any number of files and/or\nrecursively descend down directories.  Imported as a module, this\nprovides infrastructure to write your own refactoring tool.\n");
      var1.setline(11);
      String[] var3 = new String[]{"with_statement"};
      PyObject[] var5 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("with_statement", var4);
      var4 = null;
      var1.setline(13);
      PyString var6 = PyString.fromInterned("Guido van Rossum <guido@python.org>");
      var1.setlocal("__author__", var6);
      var3 = null;
      var1.setline(17);
      PyObject var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(18);
      var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(19);
      var7 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var7);
      var3 = null;
      var1.setline(20);
      var7 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var7);
      var3 = null;
      var1.setline(21);
      var7 = imp.importOne("collections", var1, -1);
      var1.setlocal("collections", var7);
      var3 = null;
      var1.setline(22);
      var7 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var7);
      var3 = null;
      var1.setline(23);
      var3 = new String[]{"chain"};
      var5 = imp.importFrom("itertools", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("chain", var4);
      var4 = null;
      var1.setline(26);
      var3 = new String[]{"driver", "tokenize", "token"};
      var5 = imp.importFrom("pgen2", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("driver", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("tokenize", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(27);
      var3 = new String[]{"find_root"};
      var5 = imp.importFrom("fixer_util", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("find_root", var4);
      var4 = null;
      var1.setline(28);
      var3 = new String[]{"pytree", "pygram"};
      var5 = imp.importFrom("", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("pygram", var4);
      var4 = null;
      var1.setline(29);
      var3 = new String[]{"btm_utils"};
      var5 = imp.importFrom("", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("bu", var4);
      var4 = null;
      var1.setline(30);
      var3 = new String[]{"btm_matcher"};
      var5 = imp.importFrom("", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("bm", var4);
      var4 = null;
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("True")};
      PyFunction var8 = new PyFunction(var1.f_globals, var5, get_all_fix_names$1, PyString.fromInterned("Return a sorted list of all available fix names in the given package."));
      var1.setlocal("get_all_fix_names", var8);
      var3 = null;
      var1.setline(46);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("_EveryNode", var5, _EveryNode$2);
      var1.setlocal("_EveryNode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(50);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _get_head_types$3, PyString.fromInterned(" Accepts a pytree Pattern Node and returns a set\n        of the pattern types which will match first. "));
      var1.setlocal("_get_head_types", var8);
      var3 = null;
      var1.setline(78);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _get_headnode_dict$4, PyString.fromInterned(" Accepts a list of fixers and returns a dictionary\n        of head node type --> fixer list.  "));
      var1.setlocal("_get_headnode_dict", var8);
      var3 = null;
      var1.setline(103);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_fixers_from_package$5, PyString.fromInterned("\n    Return the fully qualified names for fixers in the package pkg_name.\n    "));
      var1.setlocal("get_fixers_from_package", var8);
      var3 = null;
      var1.setline(110);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _identity$6, (PyObject)null);
      var1.setlocal("_identity", var8);
      var3 = null;
      var1.setline(113);
      var7 = var1.getname("sys").__getattr__("version_info");
      PyObject var10000 = var7._lt(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var7 = imp.importOne("codecs", var1, -1);
         var1.setlocal("codecs", var7);
         var3 = null;
         var1.setline(115);
         var7 = var1.getname("codecs").__getattr__("open");
         var1.setlocal("_open_with_encoding", var7);
         var3 = null;
         var1.setline(117);
         var5 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var5, _from_system_newlines$7, (PyObject)null);
         var1.setlocal("_from_system_newlines", var8);
         var3 = null;
         var1.setline(119);
         var5 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var5, _to_system_newlines$8, (PyObject)null);
         var1.setlocal("_to_system_newlines", var8);
         var3 = null;
      } else {
         var1.setline(125);
         var7 = var1.getname("open");
         var1.setlocal("_open_with_encoding", var7);
         var3 = null;
         var1.setline(126);
         var7 = var1.getname("_identity");
         var1.setlocal("_from_system_newlines", var7);
         var3 = null;
         var1.setline(127);
         var7 = var1.getname("_identity");
         var1.setlocal("_to_system_newlines", var7);
         var3 = null;
      }

      var1.setline(130);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _detect_future_features$9, (PyObject)null);
      var1.setlocal("_detect_future_features", var8);
      var3 = null;
      var1.setline(170);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("FixerError", var5, FixerError$11);
      var1.setlocal("FixerError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(174);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("RefactoringTool", var5, RefactoringTool$12);
      var1.setlocal("RefactoringTool", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(691);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("MultiprocessingUnsupported", var5, MultiprocessingUnsupported$35);
      var1.setlocal("MultiprocessingUnsupported", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(695);
      var5 = new PyObject[]{var1.getname("RefactoringTool")};
      var4 = Py.makeClass("MultiprocessRefactoringTool", var5, MultiprocessRefactoringTool$36);
      var1.setlocal("MultiprocessRefactoringTool", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_all_fix_names$1(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyString.fromInterned("Return a sorted list of all available fix names in the given package.");
      var1.setline(35);
      PyObject var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(0), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{PyString.fromInterned("*")}));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getattr__("__file__"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(37);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("sorted").__call__(var2, var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(3))).__iter__();

      while(true) {
         var1.setline(38);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(43);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(39);
         PyObject var10000 = var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fix_"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(40);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(41);
               PyObject var5 = var1.getlocal(5).__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null);
               var1.setlocal(5, var5);
               var5 = null;
            }

            var1.setline(42);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null));
         }
      }
   }

   public PyObject _EveryNode$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(47);
      return var1.getf_locals();
   }

   public PyObject _get_head_types$3(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned(" Accepts a pytree Pattern Node and returns a set\n        of the pattern types which will match first. ");
      var1.setline(54);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("pytree").__getattr__("NodePattern"), var1.getglobal("pytree").__getattr__("LeafPattern")}))).__nonzero__()) {
         var1.setline(58);
         var3 = var1.getlocal(0).__getattr__("type");
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(59);
            throw Py.makeException(var1.getglobal("_EveryNode"));
         } else {
            var1.setline(60);
            var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("type")})));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(62);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("pytree").__getattr__("NegatedPattern")).__nonzero__()) {
            var1.setline(63);
            if (var1.getlocal(0).__getattr__("content").__nonzero__()) {
               var1.setline(64);
               var3 = var1.getglobal("_get_head_types").__call__(var2, var1.getlocal(0).__getattr__("content"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(65);
               throw Py.makeException(var1.getglobal("_EveryNode"));
            }
         } else {
            var1.setline(67);
            if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("pytree").__getattr__("WildcardPattern")).__nonzero__()) {
               var1.setline(75);
               throw Py.makeException(var1.getglobal("Exception").__call__(var2, PyString.fromInterned("Oh no! I don't understand pattern %s")._mod(var1.getlocal(0))));
            } else {
               var1.setline(69);
               PyObject var4 = var1.getglobal("set").__call__(var2);
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(70);
               var4 = var1.getlocal(0).__getattr__("content").__iter__();

               while(true) {
                  var1.setline(70);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(73);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(2, var5);
                  var1.setline(71);
                  PyObject var6 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(71);
                     PyObject var7 = var6.__iternext__();
                     if (var7 == null) {
                        break;
                     }

                     var1.setlocal(3, var7);
                     var1.setline(72);
                     var1.getlocal(1).__getattr__("update").__call__(var2, var1.getglobal("_get_head_types").__call__(var2, var1.getlocal(3)));
                  }
               }
            }
         }
      }
   }

   public PyObject _get_headnode_dict$4(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned(" Accepts a list of fixers and returns a dictionary\n        of head node type --> fixer list.  ");
      var1.setline(81);
      PyObject var3 = var1.getglobal("collections").__getattr__("defaultdict").__call__(var2, var1.getglobal("list"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(82);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         label44:
         while(true) {
            while(true) {
               var1.setline(83);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(97);
                  var3 = var1.getglobal("chain").__call__(var2, var1.getglobal("pygram").__getattr__("python_grammar").__getattr__("symbol2number").__getattr__("itervalues").__call__(var2), var1.getglobal("pygram").__getattr__("python_grammar").__getattr__("tokens")).__iter__();

                  while(true) {
                     var1.setline(97);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(100);
                        var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(1));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(99);
                     var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("extend").__call__(var2, var1.getlocal(2));
                  }
               }

               var1.setlocal(3, var4);
               var1.setline(84);
               PyObject var5;
               if (var1.getlocal(3).__getattr__("pattern").__nonzero__()) {
                  try {
                     var1.setline(86);
                     var5 = var1.getglobal("_get_head_types").__call__(var2, var1.getlocal(3).__getattr__("pattern"));
                     var1.setlocal(4, var5);
                     var5 = null;
                     break label44;
                  } catch (Throwable var8) {
                     PyException var10 = Py.setException(var8, var1);
                     if (!var10.match(var1.getglobal("_EveryNode"))) {
                        throw var10;
                     }

                     var1.setline(88);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
                  }
               } else {
                  var1.setline(93);
                  var5 = var1.getlocal(3).__getattr__("_accept_type");
                  PyObject var10000 = var5._isnot(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(94);
                     var1.getlocal(1).__getitem__(var1.getlocal(3).__getattr__("_accept_type")).__getattr__("append").__call__(var2, var1.getlocal(3));
                  } else {
                     var1.setline(96);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
                  }
               }
            }
         }

         var1.setline(90);
         PyObject var6 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(90);
            PyObject var7 = var6.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(5, var7);
            var1.setline(91);
            var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject get_fixers_from_package$5(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyString.fromInterned("\n    Return the fully qualified names for fixers in the package pkg_name.\n    ");
      var1.setline(107);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getglobal("get_all_fix_names").__call__(var2, var1.getlocal(0), var1.getglobal("False")).__iter__();

      while(true) {
         var1.setline(108);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(108);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(107);
         var1.getlocal(1).__call__(var2, var1.getlocal(0)._add(PyString.fromInterned("."))._add(var1.getlocal(2)));
      }
   }

   public PyObject _identity$6(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _from_system_newlines$7(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\r\n"), (PyObject)PyUnicode.fromInterned("\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _to_system_newlines$8(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyObject var3 = var1.getglobal("os").__getattr__("linesep");
      PyObject var10000 = var3._ne(PyString.fromInterned("\n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(123);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _detect_future_features$9(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0)).__getattr__("readline"));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(133);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var7;
      PyCode var10004 = advance$10;
      var7 = new PyObject[]{var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(136);
      var3 = var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("token").__getattr__("NEWLINE"), var1.getglobal("tokenize").__getattr__("NL"), var1.getglobal("token").__getattr__("COMMENT")})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      try {
         while(true) {
            var1.setline(139);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(140);
            var3 = var1.getlocal(2).__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(141);
            var3 = var1.getlocal(5);
            PyObject var10000 = var3._in(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(143);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(var1.getglobal("token").__getattr__("STRING"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(144);
                  if (var1.getlocal(1).__nonzero__()) {
                     break;
                  }

                  var1.setline(146);
                  var3 = var1.getglobal("True");
                  var1.setlocal(1, var3);
                  var3 = null;
               } else {
                  var1.setline(147);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._eq(PyUnicode.fromInterned("from"));
                     var3 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(148);
                  var3 = var1.getlocal(2).__call__(var2);
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(149);
                  var3 = var1.getlocal(5);
                  var10000 = var3._ne(var1.getglobal("token").__getattr__("NAME"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._ne(PyUnicode.fromInterned("__future__"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(151);
                  var3 = var1.getlocal(2).__call__(var2);
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(152);
                  var3 = var1.getlocal(5);
                  var10000 = var3._ne(var1.getglobal("token").__getattr__("NAME"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._ne(PyUnicode.fromInterned("import"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(154);
                  var3 = var1.getlocal(2).__call__(var2);
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(155);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(var1.getglobal("token").__getattr__("OP"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._eq(PyUnicode.fromInterned("("));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(156);
                     var3 = var1.getlocal(2).__call__(var2);
                     var4 = Py.unpackSequence(var3, 2);
                     var5 = var4[0];
                     var1.setlocal(5, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var3 = null;
                  }

                  while(true) {
                     var1.setline(157);
                     var3 = var1.getlocal(5);
                     var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(158);
                     var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(6));
                     var1.setline(159);
                     var3 = var1.getlocal(2).__call__(var2);
                     var4 = Py.unpackSequence(var3, 2);
                     var5 = var4[0];
                     var1.setlocal(5, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(160);
                     var3 = var1.getlocal(5);
                     var10000 = var3._ne(var1.getglobal("token").__getattr__("OP"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getlocal(6);
                        var10000 = var3._ne(PyUnicode.fromInterned(","));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(162);
                     var3 = var1.getlocal(2).__call__(var2);
                     var4 = Py.unpackSequence(var3, 2);
                     var5 = var4[0];
                     var1.setlocal(5, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var3 = null;
                  }
               }
            }
         }
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getglobal("StopIteration"))) {
            throw var9;
         }

         var1.setline(166);
      }

      var1.setline(167);
      var3 = var1.getglobal("frozenset").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject advance$10(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var3 = var1.getderef(0).__getattr__("next").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(135);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(1))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject FixerError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A fixer could not be loaded."));
      var1.setline(171);
      PyString.fromInterned("A fixer could not be loaded.");
      return var1.getf_locals();
   }

   public PyObject RefactoringTool$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(176);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("print_function"), var1.getname("False"), PyString.fromInterned("write_unchanged_files"), var1.getname("False")});
      var1.setlocal("_default_options", var3);
      var3 = null;
      var1.setline(179);
      PyString var4 = PyString.fromInterned("Fix");
      var1.setlocal("CLASS_PREFIX", var4);
      var3 = null;
      var1.setline(180);
      var4 = PyString.fromInterned("fix_");
      var1.setlocal("FILE_PREFIX", var4);
      var3 = null;
      var1.setline(182);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$13, PyString.fromInterned("Initializer.\n\n        Args:\n            fixer_names: a list of fixers to import\n            options: an dict with configuration.\n            explicit: a list of fixers to run even if they are explicit.\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(234);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_fixers$14, PyString.fromInterned("Inspects the options to load the requested patterns and handlers.\n\n        Returns:\n          (pre_order, post_order), where pre_order is the list of fixers that\n          want a pre-order AST traversal, and post_order is the list that want\n          post-order traversal.\n        "));
      var1.setlocal("get_fixers", var6);
      var3 = null;
      var1.setline(274);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_error$15, PyString.fromInterned("Called when an error occurs."));
      var1.setlocal("log_error", var6);
      var3 = null;
      var1.setline(278);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_message$16, PyString.fromInterned("Hook to log a message."));
      var1.setlocal("log_message", var6);
      var3 = null;
      var1.setline(284);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, log_debug$17, (PyObject)null);
      var1.setlocal("log_debug", var6);
      var3 = null;
      var1.setline(289);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, print_output$18, PyString.fromInterned("Called with the old version, new version, and filename of a\n        refactored file."));
      var1.setlocal("print_output", var6);
      var3 = null;
      var1.setline(294);
      var5 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, refactor$19, PyString.fromInterned("Refactor a list of files and directories."));
      var1.setlocal("refactor", var6);
      var3 = null;
      var1.setline(303);
      var5 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, refactor_dir$20, PyString.fromInterned("Descends down a directory and refactor every Python file found.\n\n        Python files are assumed to have a .py extension.\n\n        Files and subdirectories starting with '.' are skipped.\n        "));
      var1.setlocal("refactor_dir", var6);
      var3 = null;
      var1.setline(323);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _read_python_source$21, PyString.fromInterned("\n        Do our best to decode a Python source file correctly.\n        "));
      var1.setlocal("_read_python_source", var6);
      var3 = null;
      var1.setline(339);
      var5 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, refactor_file$22, PyString.fromInterned("Refactors a file."));
      var1.setlocal("refactor_file", var6);
      var3 = null;
      var1.setline(362);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, refactor_string$23, PyString.fromInterned("Refactor a given input string.\n\n        Args:\n            data: a string holding the code to be refactored.\n            name: a human-readable name for use in error/log messages.\n\n        Returns:\n            An AST corresponding to the refactored input stream; None if\n            there were errors during the parse.\n        "));
      var1.setlocal("refactor_string", var6);
      var3 = null;
      var1.setline(389);
      var5 = new PyObject[]{var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, refactor_stdin$24, (PyObject)null);
      var1.setlocal("refactor_stdin", var6);
      var3 = null;
      var1.setline(405);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, refactor_tree$25, PyString.fromInterned("Refactors a parse tree (modifying the tree in place).\n\n        For compatible patterns the bottom matcher module is\n        used. Otherwise the tree is traversed node-to-node for\n        matches.\n\n        Args:\n            tree: a pytree.Node instance representing the root of the tree\n                  to be refactored.\n            name: a human-readable name for this tree.\n\n        Returns:\n            True if the tree was modified, False otherwise.\n        "));
      var1.setlocal("refactor_tree", var6);
      var3 = null;
      var1.setline(484);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, traverse_by$26, PyString.fromInterned("Traverse an AST, applying a set of fixers to each node.\n\n        This is a helper method for refactor_tree().\n\n        Args:\n            fixers: a list of fixer instances.\n            traversal: a generator that yields AST nodes.\n\n        Returns:\n            None\n        "));
      var1.setlocal("traverse_by", var6);
      var3 = null;
      var1.setline(507);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, processed_file$27, PyString.fromInterned("\n        Called when a file has been refactored and there may be changes.\n        "));
      var1.setlocal("processed_file", var6);
      var3 = null;
      var1.setline(528);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, write_file$28, PyString.fromInterned("Writes a string to a file.\n\n        It first shows a unified diff between the old text and the new text, and\n        then rewrites the file; the latter is only done if the write option is\n        set.\n        "));
      var1.setlocal("write_file", var6);
      var3 = null;
      var1.setline(549);
      var4 = PyString.fromInterned(">>> ");
      var1.setlocal("PS1", var4);
      var3 = null;
      var1.setline(550);
      var4 = PyString.fromInterned("... ");
      var1.setlocal("PS2", var4);
      var3 = null;
      var1.setline(552);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, refactor_docstring$29, PyString.fromInterned("Refactors a docstring, looking for doctests.\n\n        This returns a modified version of the input string.  It looks\n        for doctests, which start with a \">>>\" prompt, and may be\n        continued with \"...\" prompts, as long as the \"...\" is indented\n        the same as the \">>>\".\n\n        (Unfortunately we can't use the doctest module's parser,\n        since, like most parsers, it is not geared towards preserving\n        the original source.)\n        "));
      var1.setlocal("refactor_docstring", var6);
      var3 = null;
      var1.setline(595);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, refactor_doctest$30, PyString.fromInterned("Refactors one doctest.\n\n        A doctest is given as a block of lines, the first of which starts\n        with \">>>\" (possibly indented), while the remaining lines start\n        with \"...\" (identically indented).\n\n        "));
      var1.setlocal("refactor_doctest", var6);
      var3 = null;
      var1.setline(624);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, summarize$31, (PyObject)null);
      var1.setlocal("summarize", var6);
      var3 = null;
      var1.setline(647);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parse_block$32, PyString.fromInterned("Parses a block into a tree.\n\n        This is necessary to get correct line number / offset information\n        in the parser diagnostics and embedded into the parse tree.\n        "));
      var1.setlocal("parse_block", var6);
      var3 = null;
      var1.setline(657);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, wrap_toks$33, PyString.fromInterned("Wraps a tokenize stream to systematically modify start/end."));
      var1.setlocal("wrap_toks", var6);
      var3 = null;
      var1.setline(671);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, gen_lines$34, PyString.fromInterned("Generates lines as expected by tokenize from a list of lines.\n\n        This strips the first len(indent + self.PS1) characters off each line.\n        "));
      var1.setlocal("gen_lines", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Initializer.\n\n        Args:\n            fixer_names: a list of fixers to import\n            options: an dict with configuration.\n            explicit: a list of fixers to run even if they are explicit.\n        ");
      var1.setline(190);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fixers", var3);
      var3 = null;
      var1.setline(191);
      Object var10000 = var1.getlocal(3);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var6 = var10000;
      var1.getlocal(0).__setattr__((String)"explicit", (PyObject)var6);
      var3 = null;
      var1.setline(192);
      var3 = var1.getlocal(0).__getattr__("_default_options").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("options", var3);
      var3 = null;
      var1.setline(193);
      var3 = var1.getlocal(2);
      PyObject var11 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var11.__nonzero__()) {
         var1.setline(194);
         var1.getlocal(0).__getattr__("options").__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.setline(195);
      if (var1.getlocal(0).__getattr__("options").__getitem__(PyString.fromInterned("print_function")).__nonzero__()) {
         var1.setline(196);
         var3 = var1.getglobal("pygram").__getattr__("python_grammar_no_print_statement");
         var1.getlocal(0).__setattr__("grammar", var3);
         var3 = null;
      } else {
         var1.setline(198);
         var3 = var1.getglobal("pygram").__getattr__("python_grammar");
         var1.getlocal(0).__setattr__("grammar", var3);
         var3 = null;
      }

      var1.setline(202);
      var3 = var1.getlocal(0).__getattr__("options").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("write_unchanged_files"));
      var1.getlocal(0).__setattr__("write_unchanged_files", var3);
      var3 = null;
      var1.setline(203);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"errors", var9);
      var3 = null;
      var1.setline(204);
      var3 = var1.getglobal("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RefactoringTool"));
      var1.getlocal(0).__setattr__("logger", var3);
      var3 = null;
      var1.setline(205);
      var9 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"fixer_log", var9);
      var3 = null;
      var1.setline(206);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("wrote", var3);
      var3 = null;
      var1.setline(207);
      var11 = var1.getglobal("driver").__getattr__("Driver");
      PyObject[] var10 = new PyObject[]{var1.getlocal(0).__getattr__("grammar"), var1.getglobal("pytree").__getattr__("convert"), var1.getlocal(0).__getattr__("logger")};
      String[] var4 = new String[]{"convert", "logger"};
      var11 = var11.__call__(var2, var10, var4);
      var3 = null;
      var3 = var11;
      var1.getlocal(0).__setattr__("driver", var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getlocal(0).__getattr__("get_fixers").__call__(var2);
      PyObject[] var7 = Py.unpackSequence(var3, 2);
      PyObject var5 = var7[0];
      var1.getlocal(0).__setattr__("pre_order", var5);
      var5 = null;
      var5 = var7[1];
      var1.getlocal(0).__setattr__("post_order", var5);
      var5 = null;
      var3 = null;
      var1.setline(213);
      var9 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"files", var9);
      var3 = null;
      var1.setline(215);
      var3 = var1.getglobal("bm").__getattr__("BottomMatcher").__call__(var2);
      var1.getlocal(0).__setattr__("BM", var3);
      var3 = null;
      var1.setline(216);
      var9 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"bmi_pre_order", var9);
      var3 = null;
      var1.setline(217);
      var9 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"bmi_post_order", var9);
      var3 = null;
      var1.setline(219);
      var3 = var1.getglobal("chain").__call__(var2, var1.getlocal(0).__getattr__("post_order"), var1.getlocal(0).__getattr__("pre_order")).__iter__();

      while(true) {
         var1.setline(219);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(229);
            var3 = var1.getglobal("_get_headnode_dict").__call__(var2, var1.getlocal(0).__getattr__("bmi_pre_order"));
            var1.getlocal(0).__setattr__("bmi_pre_order_heads", var3);
            var3 = null;
            var1.setline(230);
            var3 = var1.getglobal("_get_headnode_dict").__call__(var2, var1.getlocal(0).__getattr__("bmi_post_order"));
            var1.getlocal(0).__setattr__("bmi_post_order_heads", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var8);
         var1.setline(220);
         if (var1.getlocal(4).__getattr__("BM_compatible").__nonzero__()) {
            var1.setline(221);
            var1.getlocal(0).__getattr__("BM").__getattr__("add_fixer").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(224);
            var5 = var1.getlocal(4);
            var11 = var5._in(var1.getlocal(0).__getattr__("pre_order"));
            var5 = null;
            if (var11.__nonzero__()) {
               var1.setline(225);
               var1.getlocal(0).__getattr__("bmi_pre_order").__getattr__("append").__call__(var2, var1.getlocal(4));
            } else {
               var1.setline(226);
               var5 = var1.getlocal(4);
               var11 = var5._in(var1.getlocal(0).__getattr__("post_order"));
               var5 = null;
               if (var11.__nonzero__()) {
                  var1.setline(227);
                  var1.getlocal(0).__getattr__("bmi_post_order").__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject get_fixers$14(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("Inspects the options to load the requested patterns and handlers.\n\n        Returns:\n          (pre_order, post_order), where pre_order is the list of fixers that\n          want a pre-order AST traversal, and post_order is the list that want\n          post-order traversal.\n        ");
      var1.setline(242);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(243);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(244);
      PyObject var8 = var1.getlocal(0).__getattr__("fixers").__iter__();

      while(true) {
         var1.setline(244);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(269);
            var8 = var1.getglobal("operator").__getattr__("attrgetter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("run_order"));
            var1.setlocal(12, var8);
            var3 = null;
            var1.setline(270);
            var10000 = var1.getlocal(1).__getattr__("sort");
            PyObject[] var10 = new PyObject[]{var1.getlocal(12)};
            String[] var9 = new String[]{"key"};
            var10000.__call__(var2, var10, var9);
            var3 = null;
            var1.setline(271);
            var10000 = var1.getlocal(2).__getattr__("sort");
            var10 = new PyObject[]{var1.getlocal(12)};
            var9 = new String[]{"key"};
            var10000.__call__(var2, var10, var9);
            var3 = null;
            var1.setline(272);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(3, var4);
         var1.setline(245);
         PyObject var5 = var1.getglobal("__import__").__call__(var2, var1.getlocal(3), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyList(new PyObject[]{PyString.fromInterned("*")}));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(246);
         var5 = var1.getlocal(3).__getattr__("rsplit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(-1));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(247);
         if (var1.getlocal(5).__getattr__("startswith").__call__(var2, var1.getlocal(0).__getattr__("FILE_PREFIX")).__nonzero__()) {
            var1.setline(248);
            var5 = var1.getlocal(5).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("FILE_PREFIX")), (PyObject)null, (PyObject)null);
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(249);
         var5 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(250);
         var10000 = var1.getlocal(0).__getattr__("CLASS_PREFIX");
         PyObject var10001 = PyString.fromInterned("").__getattr__("join");
         PyList var10003 = new PyList();
         var5 = var10003.__getattr__("append");
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(250);
         var5 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(250);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(250);
               var1.dellocal(8);
               var5 = var10000._add(var10001.__call__((ThreadState)var2, (PyObject)var10003));
               var1.setlocal(7, var5);
               var5 = null;

               try {
                  var1.setline(252);
                  var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(7));
                  var1.setlocal(10, var5);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var12 = Py.setException(var7, var1);
                  if (var12.match(var1.getglobal("AttributeError"))) {
                     var1.setline(254);
                     throw Py.makeException(var1.getglobal("FixerError").__call__(var2, PyString.fromInterned("Can't find %s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(7)}))));
                  }

                  throw var12;
               }

               var1.setline(255);
               var5 = var1.getlocal(10).__call__(var2, var1.getlocal(0).__getattr__("options"), var1.getlocal(0).__getattr__("fixer_log"));
               var1.setlocal(11, var5);
               var5 = null;
               var1.setline(256);
               var10000 = var1.getlocal(11).__getattr__("explicit");
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(0).__getattr__("explicit");
                  var10000 = var5._isnot(var1.getglobal("True"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(3);
                     var10000 = var5._notin(var1.getlocal(0).__getattr__("explicit"));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(258);
                  var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Skipping implicit fixer: %s"), (PyObject)var1.getlocal(5));
               } else {
                  var1.setline(261);
                  var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Adding transformation: %s"), (PyObject)var1.getlocal(5));
                  var1.setline(262);
                  var5 = var1.getlocal(11).__getattr__("order");
                  var10000 = var5._eq(PyString.fromInterned("pre"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(263);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(11));
                  } else {
                     var1.setline(264);
                     var5 = var1.getlocal(11).__getattr__("order");
                     var10000 = var5._eq(PyString.fromInterned("post"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(267);
                        throw Py.makeException(var1.getglobal("FixerError").__call__(var2, PyString.fromInterned("Illegal fixer order: %r")._mod(var1.getlocal(11).__getattr__("order"))));
                     }

                     var1.setline(265);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(11));
                  }
               }
               break;
            }

            var1.setlocal(9, var6);
            var1.setline(250);
            var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("title").__call__(var2));
         }
      }
   }

   public PyObject log_error$15(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyString.fromInterned("Called when an error occurs.");
      var1.setline(276);
      throw Py.makeException();
   }

   public PyObject log_message$16(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyString.fromInterned("Hook to log a message.");
      var1.setline(280);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(281);
         PyObject var3 = var1.getlocal(1)._mod(var1.getlocal(2));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(282);
      var1.getlocal(0).__getattr__("logger").__getattr__("info").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_debug$17(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(286);
         PyObject var3 = var1.getlocal(1)._mod(var1.getlocal(2));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(287);
      var1.getlocal(0).__getattr__("logger").__getattr__("debug").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_output$18(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("Called with the old version, new version, and filename of a\n        refactored file.");
      var1.setline(292);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject refactor$19(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyString.fromInterned("Refactor a list of files and directories.");
      var1.setline(297);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(297);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(298);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(299);
            var1.getlocal(0).__getattr__("refactor_dir").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
         } else {
            var1.setline(301);
            var1.getlocal(0).__getattr__("refactor_file").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
         }
      }
   }

   public PyObject refactor_dir$20(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyString.fromInterned("Descends down a directory and refactor every Python file found.\n\n        Python files are assumed to have a .py extension.\n\n        Files and subdirectories starting with '.' are skipped.\n        ");
      var1.setline(310);
      PyObject var3 = var1.getglobal("os").__getattr__("extsep")._add(PyString.fromInterned("py"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(311);
      var3 = var1.getglobal("os").__getattr__("walk").__call__(var2, var1.getlocal(1)).__iter__();

      label36:
      while(true) {
         var1.setline(311);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(312);
         var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Descending into %s"), (PyObject)var1.getlocal(5));
         var1.setline(313);
         var1.getlocal(6).__getattr__("sort").__call__(var2);
         var1.setline(314);
         var1.getlocal(7).__getattr__("sort").__call__(var2);
         var1.setline(315);
         PyObject var8 = var1.getlocal(7).__iter__();

         while(true) {
            var1.setline(315);
            var6 = var8.__iternext__();
            if (var6 == null) {
               var1.setline(321);
               PyList var10 = new PyList();
               var8 = var10.__getattr__("append");
               var1.setlocal(10, var8);
               var5 = null;
               var1.setline(321);
               var8 = var1.getlocal(6).__iter__();

               while(true) {
                  var1.setline(321);
                  var6 = var8.__iternext__();
                  if (var6 == null) {
                     var1.setline(321);
                     var1.dellocal(10);
                     PyList var9 = var10;
                     var1.getlocal(6).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var9);
                     var5 = null;
                     continue label36;
                  }

                  var1.setlocal(11, var6);
                  var1.setline(321);
                  if (var1.getlocal(11).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__().__nonzero__()) {
                     var1.setline(321);
                     var1.getlocal(10).__call__(var2, var1.getlocal(11));
                  }
               }
            }

            var1.setlocal(8, var6);
            var1.setline(316);
            PyObject var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__();
            PyObject var7;
            if (var10000.__nonzero__()) {
               var7 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(8)).__getitem__(Py.newInteger(1));
               var10000 = var7._eq(var1.getlocal(4));
               var7 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(318);
               var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(8));
               var1.setlocal(9, var7);
               var7 = null;
               var1.setline(319);
               var1.getlocal(0).__getattr__("refactor_file").__call__(var2, var1.getlocal(9), var1.getlocal(2), var1.getlocal(3));
            }
         }
      }
   }

   public PyObject _read_python_source$21(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(326);
      PyString.fromInterned("\n        Do our best to decode a Python source file correctly.\n        ");

      PyException var3;
      PyTuple var12;
      try {
         var1.setline(328);
         PyObject var10 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(2, var10);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(330);
            var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, PyString.fromInterned("Can't open %s: %s"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(3));
            var1.setline(331);
            var12 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var12;
         }

         throw var3;
      }

      var3 = null;

      PyObject var5;
      try {
         var1.setline(333);
         var5 = var1.getglobal("tokenize").__getattr__("detect_encoding").__call__(var2, var1.getlocal(2).__getattr__("readline")).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var5);
         var5 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(335);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(335);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      PyObject var10000 = var1.getglobal("_open_with_encoding");
      PyObject[] var11 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("r"), var1.getlocal(4)};
      String[] var14 = new String[]{"encoding"};
      var10000 = var10000.__call__(var2, var11, var14);
      var3 = null;
      ContextManager var13;
      var5 = (var13 = ContextGuard.getManager(var10000)).__enter__(var2);

      Throwable var15;
      label50: {
         boolean var10001;
         try {
            var1.setlocal(2, var5);
            var1.setline(337);
            var12 = new PyTuple(new PyObject[]{var1.getglobal("_from_system_newlines").__call__(var2, var1.getlocal(2).__getattr__("read").__call__(var2)), var1.getlocal(4)});
         } catch (Throwable var9) {
            var15 = var9;
            var10001 = false;
            break label50;
         }

         var13.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var12;
         } catch (Throwable var8) {
            var15 = var8;
            var10001 = false;
         }
      }

      if (!var13.__exit__(var2, Py.setException(var15, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject refactor_file$22(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyString.fromInterned("Refactors a file.");
      var1.setline(341);
      PyObject var3 = var1.getlocal(0).__getattr__("_read_python_source").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(342);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(344);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(345);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(PyUnicode.fromInterned("\n"));
         var1.setlocal(4, var3);
         var1.setline(346);
         PyObject[] var7;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(347);
            var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Refactoring doctests in %s"), (PyObject)var1.getlocal(1));
            var1.setline(348);
            var3 = var1.getlocal(0).__getattr__("refactor_docstring").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(349);
            var10000 = var1.getlocal(0).__getattr__("write_unchanged_files");
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(6);
               var10000 = var3._ne(var1.getlocal(4));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(350);
               var10000 = var1.getlocal(0).__getattr__("processed_file");
               var7 = new PyObject[]{var1.getlocal(6), var1.getlocal(1), var1.getlocal(4), var1.getlocal(2), var1.getlocal(5)};
               var10000.__call__(var2, var7);
            } else {
               var1.setline(352);
               var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No doctest changes in %s"), (PyObject)var1.getlocal(1));
            }
         } else {
            var1.setline(354);
            var3 = var1.getlocal(0).__getattr__("refactor_string").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(355);
            var10000 = var1.getlocal(0).__getattr__("write_unchanged_files");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(7);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(7).__getattr__("was_changed");
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(357);
               var10000 = var1.getlocal(0).__getattr__("processed_file");
               var7 = new PyObject[]{var1.getglobal("unicode").__call__(var2, var1.getlocal(7)).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(1), var1.getlocal(2), var1.getlocal(5)};
               String[] var6 = new String[]{"write", "encoding"};
               var10000.__call__(var2, var7, var6);
               var3 = null;
            } else {
               var1.setline(360);
               var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No changes in %s"), (PyObject)var1.getlocal(1));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject refactor_string$23(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyString.fromInterned("Refactor a given input string.\n\n        Args:\n            data: a string holding the code to be refactored.\n            name: a human-readable name for use in error/log messages.\n\n        Returns:\n            An AST corresponding to the refactored input stream; None if\n            there were errors during the parse.\n        ");
      var1.setline(373);
      PyObject var3 = var1.getglobal("_detect_future_features").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(374);
      PyString var10 = PyString.fromInterned("print_function");
      PyObject var10000 = var10._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(375);
         var3 = var1.getglobal("pygram").__getattr__("python_grammar_no_print_statement");
         var1.getlocal(0).__getattr__("driver").__setattr__("grammar", var3);
         var3 = null;
      }

      var3 = null;

      PyException var4;
      PyObject var11;
      label41: {
         Throwable var13;
         label47: {
            PyObject var5;
            boolean var10001;
            label39: {
               try {
                  try {
                     var1.setline(377);
                     var11 = var1.getlocal(0).__getattr__("driver").__getattr__("parse_string").__call__(var2, var1.getlocal(1));
                     var1.setlocal(4, var11);
                     var4 = null;
                     break label41;
                  } catch (Throwable var8) {
                     var4 = Py.setException(var8, var1);
                     if (var4.match(var1.getglobal("Exception"))) {
                        var5 = var4.value;
                        var1.setlocal(5, var5);
                        var5 = null;
                        var1.setline(379);
                        var1.getlocal(0).__getattr__("log_error").__call__(var2, PyString.fromInterned("Can't parse %s: %s: %s"), var1.getlocal(2), var1.getlocal(5).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(5));
                        var1.setline(381);
                        break label39;
                     }
                  }
               } catch (Throwable var9) {
                  var13 = var9;
                  var10001 = false;
                  break label47;
               }

               try {
                  throw var4;
               } catch (Throwable var7) {
                  var13 = var7;
                  var10001 = false;
                  break label47;
               }
            }

            var1.setline(383);
            var5 = var1.getlocal(0).__getattr__("grammar");
            var1.getlocal(0).__getattr__("driver").__setattr__("grammar", var5);
            var5 = null;

            try {
               var1.f_lasti = -1;
               return Py.None;
            } catch (Throwable var6) {
               var13 = var6;
               var10001 = false;
            }
         }

         Throwable var12 = var13;
         Py.addTraceback(var12, var1);
         var1.setline(383);
         var11 = var1.getlocal(0).__getattr__("grammar");
         var1.getlocal(0).__getattr__("driver").__setattr__("grammar", var11);
         var4 = null;
         throw (Throwable)var12;
      }

      var1.setline(383);
      var11 = var1.getlocal(0).__getattr__("grammar");
      var1.getlocal(0).__getattr__("driver").__setattr__("grammar", var11);
      var4 = null;
      var1.setline(384);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("future_features", var3);
      var3 = null;
      var1.setline(385);
      var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Refactoring %s"), (PyObject)var1.getlocal(2));
      var1.setline(386);
      var1.getlocal(0).__getattr__("refactor_tree").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      var1.setline(387);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject refactor_stdin$24(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("read").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(391);
      PyObject var10000;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(392);
         var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Refactoring doctests in stdin"));
         var1.setline(393);
         var3 = var1.getlocal(0).__getattr__("refactor_docstring").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("<stdin>"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(394);
         var10000 = var1.getlocal(0).__getattr__("write_unchanged_files");
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._ne(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(395);
            var1.getlocal(0).__getattr__("processed_file").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("<stdin>"), (PyObject)var1.getlocal(2));
         } else {
            var1.setline(397);
            var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No doctest changes in stdin"));
         }
      } else {
         var1.setline(399);
         var3 = var1.getlocal(0).__getattr__("refactor_string").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("<stdin>"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(400);
         var10000 = var1.getlocal(0).__getattr__("write_unchanged_files");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__getattr__("was_changed");
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(401);
            var1.getlocal(0).__getattr__("processed_file").__call__((ThreadState)var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("<stdin>"), (PyObject)var1.getlocal(2));
         } else {
            var1.setline(403);
            var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No changes in stdin"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject refactor_tree$25(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyString.fromInterned("Refactors a parse tree (modifying the tree in place).\n\n        For compatible patterns the bottom matcher module is\n        used. Otherwise the tree is traversed node-to-node for\n        matches.\n\n        Args:\n            tree: a pytree.Node instance representing the root of the tree\n                  to be refactored.\n            name: a human-readable name for this tree.\n\n        Returns:\n            True if the tree was modified, False otherwise.\n        ");
      var1.setline(421);
      PyObject var3 = var1.getglobal("chain").__call__(var2, var1.getlocal(0).__getattr__("pre_order"), var1.getlocal(0).__getattr__("post_order")).__iter__();

      while(true) {
         var1.setline(421);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(425);
            var1.getlocal(0).__getattr__("traverse_by").__call__(var2, var1.getlocal(0).__getattr__("bmi_pre_order_heads"), var1.getlocal(1).__getattr__("pre_order").__call__(var2));
            var1.setline(426);
            var1.getlocal(0).__getattr__("traverse_by").__call__(var2, var1.getlocal(0).__getattr__("bmi_post_order_heads"), var1.getlocal(1).__getattr__("post_order").__call__(var2));
            var1.setline(429);
            var3 = var1.getlocal(0).__getattr__("BM").__getattr__("run").__call__(var2, var1.getlocal(1).__getattr__("leaves").__call__(var2));
            var1.setlocal(4, var3);
            var3 = null;

            label116:
            while(true) {
               var1.setline(431);
               if (!var1.getglobal("any").__call__(var2, var1.getlocal(4).__getattr__("values").__call__(var2)).__nonzero__()) {
                  var1.setline(480);
                  var3 = var1.getglobal("chain").__call__(var2, var1.getlocal(0).__getattr__("pre_order"), var1.getlocal(0).__getattr__("post_order")).__iter__();

                  while(true) {
                     var1.setline(480);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(482);
                        var3 = var1.getlocal(1).__getattr__("was_changed");
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(3, var4);
                     var1.setline(481);
                     var1.getlocal(3).__getattr__("finish_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                  }
               }

               var1.setline(432);
               var3 = var1.getlocal(0).__getattr__("BM").__getattr__("fixers").__iter__();

               label114:
               while(true) {
                  PyObject var5;
                  PyObject var10000;
                  do {
                     var1.setline(432);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        continue label116;
                     }

                     var1.setlocal(3, var4);
                     var1.setline(433);
                     var5 = var1.getlocal(3);
                     var10000 = var5._in(var1.getlocal(4));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(4).__getitem__(var1.getlocal(3));
                     }
                  } while(!var10000.__nonzero__());

                  var1.setline(435);
                  var10000 = var1.getlocal(4).__getitem__(var1.getlocal(3)).__getattr__("sort");
                  PyObject[] var11 = new PyObject[]{var1.getglobal("pytree").__getattr__("Base").__getattr__("depth"), var1.getglobal("True")};
                  String[] var6 = new String[]{"key", "reverse"};
                  var10000.__call__(var2, var11, var6);
                  var5 = null;
                  var1.setline(437);
                  if (var1.getlocal(3).__getattr__("keep_line_order").__nonzero__()) {
                     var1.setline(440);
                     var10000 = var1.getlocal(4).__getitem__(var1.getlocal(3)).__getattr__("sort");
                     var11 = new PyObject[]{var1.getglobal("pytree").__getattr__("Base").__getattr__("get_lineno")};
                     var6 = new String[]{"key"};
                     var10000.__call__(var2, var11, var6);
                     var5 = null;
                  }

                  var1.setline(442);
                  var5 = var1.getglobal("list").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(3))).__iter__();

                  label112:
                  while(true) {
                     PyObject var7;
                     do {
                        do {
                           do {
                              while(true) {
                                 var1.setline(442);
                                 PyObject var12 = var5.__iternext__();
                                 if (var12 == null) {
                                    continue label114;
                                 }

                                 var1.setlocal(5, var12);
                                 var1.setline(443);
                                 var7 = var1.getlocal(5);
                                 var10000 = var7._in(var1.getlocal(4).__getitem__(var1.getlocal(3)));
                                 var7 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(444);
                                    var1.getlocal(4).__getitem__(var1.getlocal(3)).__getattr__("remove").__call__(var2, var1.getlocal(5));
                                 }

                                 try {
                                    var1.setline(447);
                                    var1.getglobal("find_root").__call__(var2, var1.getlocal(5));
                                    break;
                                 } catch (Throwable var10) {
                                    PyException var13 = Py.setException(var10, var1);
                                    if (!var13.match(var1.getglobal("ValueError"))) {
                                       throw var13;
                                    }
                                 }
                              }

                              var1.setline(453);
                              var10000 = var1.getlocal(5).__getattr__("fixers_applied");
                              if (var10000.__nonzero__()) {
                                 var7 = var1.getlocal(3);
                                 var10000 = var7._in(var1.getlocal(5).__getattr__("fixers_applied"));
                                 var7 = null;
                              }
                           } while(var10000.__nonzero__());

                           var1.setline(457);
                           var7 = var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(5));
                           var1.setlocal(6, var7);
                           var7 = null;
                           var1.setline(459);
                        } while(!var1.getlocal(6).__nonzero__());

                        var1.setline(460);
                        var7 = var1.getlocal(3).__getattr__("transform").__call__(var2, var1.getlocal(5), var1.getlocal(6));
                        var1.setlocal(7, var7);
                        var7 = null;
                        var1.setline(461);
                        var7 = var1.getlocal(7);
                        var10000 = var7._isnot(var1.getglobal("None"));
                        var7 = null;
                     } while(!var10000.__nonzero__());

                     var1.setline(462);
                     var1.getlocal(5).__getattr__("replace").__call__(var2, var1.getlocal(7));
                     var1.setline(464);
                     var7 = var1.getlocal(7).__getattr__("post_order").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(464);
                        PyObject var8 = var7.__iternext__();
                        PyList var9;
                        if (var8 == null) {
                           var1.setline(473);
                           var7 = var1.getlocal(0).__getattr__("BM").__getattr__("run").__call__(var2, var1.getlocal(7).__getattr__("leaves").__call__(var2));
                           var1.setlocal(8, var7);
                           var7 = null;
                           var1.setline(474);
                           var7 = var1.getlocal(8).__iter__();

                           while(true) {
                              var1.setline(474);
                              var8 = var7.__iternext__();
                              if (var8 == null) {
                                 continue label112;
                              }

                              var1.setlocal(9, var8);
                              var1.setline(475);
                              PyObject var14 = var1.getlocal(9);
                              var10000 = var14._in(var1.getlocal(4));
                              var9 = null;
                              if (var10000.__not__().__nonzero__()) {
                                 var1.setline(476);
                                 var9 = new PyList(Py.EmptyObjects);
                                 var1.getlocal(4).__setitem__((PyObject)var1.getlocal(9), var9);
                                 var9 = null;
                              }

                              var1.setline(478);
                              var1.getlocal(4).__getitem__(var1.getlocal(9)).__getattr__("extend").__call__(var2, var1.getlocal(8).__getitem__(var1.getlocal(9)));
                           }
                        }

                        var1.setlocal(5, var8);
                        var1.setline(467);
                        if (var1.getlocal(5).__getattr__("fixers_applied").__not__().__nonzero__()) {
                           var1.setline(468);
                           var9 = new PyList(Py.EmptyObjects);
                           var1.getlocal(5).__setattr__((String)"fixers_applied", var9);
                           var9 = null;
                        }

                        var1.setline(469);
                        var1.getlocal(5).__getattr__("fixers_applied").__getattr__("append").__call__(var2, var1.getlocal(3));
                     }
                  }
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(422);
         var1.getlocal(3).__getattr__("start_tree").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }
   }

   public PyObject traverse_by$26(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("Traverse an AST, applying a set of fixers to each node.\n\n        This is a helper method for refactor_tree().\n\n        Args:\n            fixers: a list of fixer instances.\n            traversal: a generator that yields AST nodes.\n\n        Returns:\n            None\n        ");
      var1.setline(496);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(497);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(498);
         PyObject var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(498);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(3, var4);
            var1.setline(499);
            PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(3).__getattr__("type")).__iter__();

            while(true) {
               var1.setline(499);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(4, var6);
               var1.setline(500);
               PyObject var7 = var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(3));
               var1.setlocal(5, var7);
               var7 = null;
               var1.setline(501);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(502);
                  var7 = var1.getlocal(4).__getattr__("transform").__call__(var2, var1.getlocal(3), var1.getlocal(5));
                  var1.setlocal(6, var7);
                  var7 = null;
                  var1.setline(503);
                  var7 = var1.getlocal(6);
                  PyObject var10000 = var7._isnot(var1.getglobal("None"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(504);
                     var1.getlocal(3).__getattr__("replace").__call__(var2, var1.getlocal(6));
                     var1.setline(505);
                     var7 = var1.getlocal(6);
                     var1.setlocal(3, var7);
                     var7 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject processed_file$27(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyString.fromInterned("\n        Called when a file has been refactored and there may be changes.\n        ");
      var1.setline(512);
      var1.getlocal(0).__getattr__("files").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.setline(513);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(514);
         var3 = var1.getlocal(0).__getattr__("_read_python_source").__call__(var2, var1.getlocal(2)).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(515);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(516);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(517);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(518);
      var1.getlocal(0).__getattr__("print_output").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2), var1.getlocal(6));
      var1.setline(519);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(520);
         var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No changes to %s"), (PyObject)var1.getlocal(2));
         var1.setline(521);
         if (var1.getlocal(0).__getattr__("write_unchanged_files").__not__().__nonzero__()) {
            var1.setline(522);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(523);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(524);
         var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5));
      } else {
         var1.setline(526);
         var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Not writing changes to %s"), (PyObject)var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_file$28(PyFrame var1, ThreadState var2) {
      var1.setline(534);
      PyString.fromInterned("Writes a string to a file.\n\n        It first shows a unified diff between the old text and the new text, and\n        then rewrites the file; the latter is only done if the write option is\n        set.\n        ");

      PyException var3;
      PyObject var10;
      try {
         var1.setline(536);
         PyObject var10000 = var1.getglobal("_open_with_encoding");
         PyObject[] var9 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("w"), var1.getlocal(4)};
         String[] var11 = new String[]{"encoding"};
         var10000 = var10000.__call__(var2, var9, var11);
         var3 = null;
         var10 = var10000;
         var1.setlocal(5, var10);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            PyObject var4 = var3.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(538);
            var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, PyString.fromInterned("Can't create %s: %s"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(6));
            var1.setline(539);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var3 = null;

      try {
         try {
            var1.setline(541);
            var1.getlocal(5).__getattr__("write").__call__(var2, var1.getglobal("_to_system_newlines").__call__(var2, var1.getlocal(1)));
         } catch (Throwable var7) {
            PyException var12 = Py.setException(var7, var1);
            if (!var12.match(var1.getglobal("os").__getattr__("error"))) {
               throw var12;
            }

            PyObject var5 = var12.value;
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(543);
            var1.getlocal(0).__getattr__("log_error").__call__((ThreadState)var2, PyString.fromInterned("Can't write %s: %s"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(6));
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(545);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         throw (Throwable)var8;
      }

      var1.setline(545);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(546);
      var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wrote changes to %s"), (PyObject)var1.getlocal(2));
      var1.setline(547);
      var10 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("wrote", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject refactor_docstring$29(PyFrame var1, ThreadState var2) {
      var1.setline(563);
      PyString.fromInterned("Refactors a docstring, looking for doctests.\n\n        This returns a modified version of the input string.  It looks\n        for doctests, which start with a \">>>\" prompt, and may be\n        continued with \"...\" prompts, as long as the \"...\" is indented\n        the same as the \">>>\".\n\n        (Unfortunately we can't use the doctest module's parser,\n        since, like most parsers, it is not geared towards preserving\n        the original source.)\n        ");
      var1.setline(564);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(565);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(566);
      var6 = var1.getglobal("None");
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(567);
      var6 = var1.getglobal("None");
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(568);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(569);
      var6 = var1.getlocal(1).__getattr__("splitlines").__call__(var2, var1.getglobal("True")).__iter__();

      while(true) {
         var1.setline(569);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(590);
            var6 = var1.getlocal(4);
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(591);
               var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("refactor_doctest").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)));
            }

            var1.setline(593);
            var6 = PyUnicode.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(8, var4);
         var1.setline(570);
         PyObject var5 = var1.getlocal(7);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(7, var5);
         var1.setline(571);
         if (var1.getlocal(8).__getattr__("lstrip").__call__(var2).__getattr__("startswith").__call__(var2, var1.getlocal(0).__getattr__("PS1")).__nonzero__()) {
            var1.setline(572);
            var5 = var1.getlocal(4);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(573);
               var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("refactor_doctest").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)));
            }

            var1.setline(575);
            var5 = var1.getlocal(7);
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(576);
            PyList var8 = new PyList(new PyObject[]{var1.getlocal(8)});
            var1.setlocal(4, var8);
            var5 = null;
            var1.setline(577);
            var5 = var1.getlocal(8).__getattr__("find").__call__(var2, var1.getlocal(0).__getattr__("PS1"));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(578);
            var5 = var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
            var1.setlocal(6, var5);
            var5 = null;
         } else {
            var1.setline(579);
            var5 = var1.getlocal(6);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(8).__getattr__("startswith").__call__(var2, var1.getlocal(6)._add(var1.getlocal(0).__getattr__("PS2")));
               if (!var10000.__nonzero__()) {
                  var5 = var1.getlocal(8);
                  var10000 = var5._eq(var1.getlocal(6)._add(var1.getlocal(0).__getattr__("PS2").__getattr__("rstrip").__call__(var2))._add(PyUnicode.fromInterned("\n")));
                  var5 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(582);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
            } else {
               var1.setline(584);
               var5 = var1.getlocal(4);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(585);
                  var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("refactor_doctest").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)));
               }

               var1.setline(587);
               var5 = var1.getglobal("None");
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(588);
               var5 = var1.getglobal("None");
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(589);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(8));
            }
         }
      }
   }

   public PyObject refactor_doctest$30(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyString.fromInterned("Refactors one doctest.\n\n        A doctest is given as a block of lines, the first of which starts\n        with \">>>\" (possibly indented), while the remaining lines start\n        with \"...\" (identically indented).\n\n        ");

      PyException var3;
      PyObject var4;
      PyObject var5;
      PyObject var8;
      PyObject var10000;
      try {
         var1.setline(604);
         var8 = var1.getlocal(0).__getattr__("parse_block").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(5, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("Exception"))) {
            var4 = var3.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(606);
            if (var1.getlocal(0).__getattr__("logger").__getattr__("isEnabledFor").__call__(var2, var1.getglobal("logging").__getattr__("DEBUG")).__nonzero__()) {
               var1.setline(607);
               var4 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(607);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var1.setlocal(7, var5);
                  var1.setline(608);
                  var1.getlocal(0).__getattr__("log_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Source: %s"), (PyObject)var1.getlocal(7).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n")));
               }
            }

            var1.setline(609);
            var10000 = var1.getlocal(0).__getattr__("log_error");
            PyObject[] var10 = new PyObject[]{PyString.fromInterned("Can't parse docstring in %s line %s: %s: %s"), var1.getlocal(4), var1.getlocal(2), var1.getlocal(6).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(6)};
            var10000.__call__(var2, var10);
            var1.setline(611);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(612);
      if (var1.getlocal(0).__getattr__("refactor_tree").__call__(var2, var1.getlocal(5), var1.getlocal(4)).__nonzero__()) {
         var1.setline(613);
         var8 = var1.getglobal("unicode").__call__(var2, var1.getlocal(5)).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.setlocal(8, var8);
         var3 = null;
         var1.setline(615);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(2)._sub(Py.newInteger(1)), (PyObject)null), var1.getlocal(8).__getslice__(var1.getlocal(2)._sub(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
         PyObject[] var11 = Py.unpackSequence(var9, 2);
         PyObject var6 = var11[0];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(8, var6);
         var6 = null;
         var3 = null;
         var1.setline(616);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var8 = var1.getlocal(9);
            var10000 = var8._eq((new PyList(new PyObject[]{PyUnicode.fromInterned("\n")}))._mul(var1.getlocal(2)._sub(Py.newInteger(1))));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(9));
            }
         }

         var1.setline(617);
         if (var1.getlocal(8).__getitem__(Py.newInteger(-1)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\n")).__not__().__nonzero__()) {
            var1.setline(618);
            var10000 = var1.getlocal(8);
            PyInteger var12 = Py.newInteger(-1);
            var5 = var10000;
            var6 = var5.__getitem__(var12);
            var6 = var6._iadd(PyUnicode.fromInterned("\n"));
            var5.__setitem__((PyObject)var12, var6);
         }

         var1.setline(619);
         PyList var13 = new PyList(new PyObject[]{var1.getlocal(3)._add(var1.getlocal(0).__getattr__("PS1"))._add(var1.getlocal(8).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)))});
         var1.setlocal(1, var13);
         var3 = null;
         var1.setline(620);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(621);
            var8 = var1.getlocal(1);
            PyList var14 = new PyList();
            var5 = var14.__getattr__("append");
            var1.setlocal(10, var5);
            var5 = null;
            var1.setline(621);
            var5 = var1.getlocal(8).__iter__();

            while(true) {
               var1.setline(621);
               var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(621);
                  var1.dellocal(10);
                  var8 = var8._iadd(var14);
                  var1.setlocal(1, var8);
                  break;
               }

               var1.setlocal(7, var6);
               var1.setline(621);
               var1.getlocal(10).__call__(var2, var1.getlocal(3)._add(var1.getlocal(0).__getattr__("PS2"))._add(var1.getlocal(7)));
            }
         }
      }

      var1.setline(622);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject summarize$31(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyString var3;
      if (var1.getlocal(0).__getattr__("wrote").__nonzero__()) {
         var1.setline(626);
         var3 = PyString.fromInterned("were");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(628);
         var3 = PyString.fromInterned("need to be");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(629);
      PyObject var4;
      PyObject var7;
      if (var1.getlocal(0).__getattr__("files").__not__().__nonzero__()) {
         var1.setline(630);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No files %s modified."), (PyObject)var1.getlocal(1));
      } else {
         var1.setline(632);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Files that %s modified:"), (PyObject)var1.getlocal(1));
         var1.setline(633);
         var7 = var1.getlocal(0).__getattr__("files").__iter__();

         while(true) {
            var1.setline(633);
            var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(634);
            var1.getlocal(0).__getattr__("log_message").__call__(var2, var1.getlocal(2));
         }
      }

      var1.setline(635);
      if (var1.getlocal(0).__getattr__("fixer_log").__nonzero__()) {
         var1.setline(636);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Warnings/messages while refactoring:"));
         var1.setline(637);
         var7 = var1.getlocal(0).__getattr__("fixer_log").__iter__();

         while(true) {
            var1.setline(637);
            var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(638);
            var1.getlocal(0).__getattr__("log_message").__call__(var2, var1.getlocal(3));
         }
      }

      var1.setline(639);
      if (var1.getlocal(0).__getattr__("errors").__nonzero__()) {
         var1.setline(640);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("errors"));
         PyObject var10000 = var7._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(641);
            var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("There was 1 error:"));
         } else {
            var1.setline(643);
            var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("There were %d errors:"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("errors")));
         }

         var1.setline(644);
         var7 = var1.getlocal(0).__getattr__("errors").__iter__();

         while(true) {
            var1.setline(644);
            var4 = var7.__iternext__();
            if (var4 == null) {
               break;
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
            var1.setline(645);
            var10000 = var1.getlocal(0).__getattr__("log_message");
            var5 = new PyObject[]{var1.getlocal(4)};
            String[] var8 = new String[0];
            var10000._callextra(var5, var8, var1.getlocal(5), var1.getlocal(6));
            var5 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_block$32(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyString.fromInterned("Parses a block into a tree.\n\n        This is necessary to get correct line number / offset information\n        in the parser diagnostics and embedded into the parse tree.\n        ");
      var1.setline(653);
      PyObject var3 = var1.getlocal(0).__getattr__("driver").__getattr__("parse_tokens").__call__(var2, var1.getlocal(0).__getattr__("wrap_toks").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(654);
      var3 = var1.getglobal("frozenset").__call__(var2);
      var1.getlocal(4).__setattr__("future_features", var3);
      var3 = null;
      var1.setline(655);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wrap_toks$33(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(658);
            PyString.fromInterned("Wraps a tokenize stream to systematically modify start/end.");
            var1.setline(659);
            var3 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(0).__getattr__("gen_lines").__call__(var2, var1.getlocal(1), var1.getlocal(3)).__getattr__("next"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(660);
            var3 = var1.getlocal(4).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var12 = (PyObject)var10000;
      }

      var1.setline(660);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var9 = Py.unpackSequence(var4, 5);
         PyObject var6 = var9[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var9[2];
         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(7, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(8, var8);
         var8 = null;
         var6 = null;
         var6 = var9[3];
         var7 = Py.unpackSequence(var6, 2);
         var8 = var7[0];
         var1.setlocal(9, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(10, var8);
         var8 = null;
         var6 = null;
         var6 = var9[4];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(661);
         PyObject var10 = var1.getlocal(7);
         var10 = var10._iadd(var1.getlocal(2)._sub(Py.newInteger(1)));
         var1.setlocal(7, var10);
         var1.setline(662);
         var10 = var1.getlocal(9);
         var10 = var10._iadd(var1.getlocal(2)._sub(Py.newInteger(1)));
         var1.setlocal(9, var10);
         var1.setline(668);
         var1.setline(668);
         var9 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), null, null, null};
         PyObject[] var11 = new PyObject[]{var1.getlocal(7), var1.getlocal(8)};
         PyTuple var13 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var9[2] = var13;
         var11 = new PyObject[]{var1.getlocal(9), var1.getlocal(10)};
         var13 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var9[3] = var13;
         var9[4] = var1.getlocal(11);
         var13 = new PyTuple(var9);
         Arrays.fill(var9, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[9];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var13;
      }
   }

   public PyObject gen_lines$34(PyFrame var1, ThreadState var2) {
      Object[] var3;
      label44: {
         PyObject var4;
         Object[] var5;
         PyObject var6;
         PyObject var7;
         PyObject var9;
         label43: {
            Object var10000;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(675);
                  PyString.fromInterned("Generates lines as expected by tokenize from a list of lines.\n\n        This strips the first len(indent + self.PS1) characters off each line.\n        ");
                  var1.setline(676);
                  var6 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("PS1"));
                  var1.setlocal(3, var6);
                  var3 = null;
                  var1.setline(677);
                  var6 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("PS2"));
                  var1.setlocal(4, var6);
                  var3 = null;
                  var1.setline(678);
                  var6 = var1.getlocal(3);
                  var1.setlocal(5, var6);
                  var3 = null;
                  var1.setline(679);
                  var6 = var1.getlocal(1).__iter__();
                  break label43;
               case 1:
                  var5 = var1.f_savedlocals;
                  var6 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var9 = (PyObject)var10000;
                  break;
               case 2:
                  var5 = var1.f_savedlocals;
                  var6 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var9 = (PyObject)var10000;
                  break;
               case 3:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var9 = (PyObject)var10000;
                  break label44;
            }

            var1.setline(686);
            var7 = var1.getlocal(4);
            var1.setlocal(5, var7);
            var5 = null;
         }

         var1.setline(679);
         var4 = var6.__iternext__();
         if (var4 != null) {
            var1.setlocal(6, var4);
            var1.setline(680);
            if (var1.getlocal(6).__getattr__("startswith").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(681);
               var1.setline(681);
               var9 = var1.getlocal(6).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)null, (PyObject)null);
               var1.f_lasti = 1;
               var5 = new Object[]{null, null, null, var6, var4};
               var1.f_savedlocals = var5;
               return var9;
            }

            var1.setline(682);
            var7 = var1.getlocal(6);
            var9 = var7._eq(var1.getlocal(5).__getattr__("rstrip").__call__(var2)._add(PyUnicode.fromInterned("\n")));
            var5 = null;
            if (var9.__nonzero__()) {
               var1.setline(683);
               var1.setline(683);
               PyUnicode var11 = PyUnicode.fromInterned("\n");
               var1.f_lasti = 2;
               var5 = new Object[7];
               var5[3] = var6;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var11;
            }

            var1.setline(685);
            var9 = var1.getglobal("AssertionError");
            PyString var10002 = PyString.fromInterned("line=%r, prefix=%r");
            PyObject[] var8 = new PyObject[]{var1.getlocal(6), var1.getlocal(5)};
            PyTuple var10003 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            throw Py.makeException(var9.__call__(var2, var10002._mod(var10003)));
         }
      }

      var1.setline(687);
      if (!var1.getglobal("True").__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(688);
         var1.setline(688);
         PyString var10 = PyString.fromInterned("");
         var1.f_lasti = 3;
         var3 = new Object[7];
         var1.f_savedlocals = var3;
         return var10;
      }
   }

   public PyObject MultiprocessingUnsupported$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(692);
      return var1.getf_locals();
   }

   public PyObject MultiprocessRefactoringTool$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(697);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$37, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(702);
      var3 = new PyObject[]{var1.getname("False"), var1.getname("False"), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, refactor$38, (PyObject)null);
      var1.setlocal("refactor", var4);
      var3 = null;
      var1.setline(731);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _child$39, (PyObject)null);
      var1.setlocal("_child", var4);
      var3 = null;
      var1.setline(742);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, refactor_file$40, (PyObject)null);
      var1.setlocal("refactor_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$37(PyFrame var1, ThreadState var2) {
      var1.setline(698);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("MultiprocessRefactoringTool"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.setline(699);
      PyObject var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("queue", var5);
      var3 = null;
      var1.setline(700);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("output_lock", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject refactor$38(PyFrame var1, ThreadState var2) {
      var1.setline(704);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(705);
         var3 = var1.getglobal("super").__call__(var2, var1.getglobal("MultiprocessRefactoringTool"), var1.getlocal(0)).__getattr__("refactor").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var10;
         try {
            var1.setline(708);
            var10 = imp.importOne("multiprocessing", var1, -1);
            var1.setlocal(5, var10);
            var4 = null;
         } catch (Throwable var8) {
            var4 = Py.setException(var8, var1);
            if (var4.match(var1.getglobal("ImportError"))) {
               var1.setline(710);
               throw Py.makeException(var1.getglobal("MultiprocessingUnsupported"));
            }

            throw var4;
         }

         var1.setline(711);
         var10 = var1.getlocal(0).__getattr__("queue");
         var10000 = var10._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(712);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("already doing multiple processes")));
         } else {
            var1.setline(713);
            var10 = var1.getlocal(5).__getattr__("JoinableQueue").__call__(var2);
            var1.getlocal(0).__setattr__("queue", var10);
            var4 = null;
            var1.setline(714);
            var10 = var1.getlocal(5).__getattr__("Lock").__call__(var2);
            var1.getlocal(0).__setattr__("output_lock", var10);
            var4 = null;
            var1.setline(715);
            PyList var13 = new PyList();
            var10 = var13.__getattr__("append");
            var1.setlocal(7, var10);
            var4 = null;
            var1.setline(716);
            var10 = var1.getglobal("xrange").__call__(var2, var1.getlocal(4)).__iter__();

            while(true) {
               var1.setline(716);
               PyObject var5 = var10.__iternext__();
               if (var5 == null) {
                  var1.setline(716);
                  var1.dellocal(7);
                  PyList var12 = var13;
                  var1.setlocal(6, var12);
                  var4 = null;
                  var4 = null;

                  PyObject var11;
                  try {
                     var1.setline(718);
                     var5 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(718);
                        var11 = var5.__iternext__();
                        if (var11 == null) {
                           var1.setline(720);
                           var1.getglobal("super").__call__(var2, var1.getglobal("MultiprocessRefactoringTool"), var1.getlocal(0)).__getattr__("refactor").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                           break;
                        }

                        var1.setlocal(9, var11);
                        var1.setline(719);
                        var1.getlocal(9).__getattr__("start").__call__(var2);
                     }
                  } catch (Throwable var9) {
                     Py.addTraceback(var9, var1);
                     var1.setline(723);
                     var1.getlocal(0).__getattr__("queue").__getattr__("join").__call__(var2);
                     var1.setline(724);
                     var5 = var1.getglobal("xrange").__call__(var2, var1.getlocal(4)).__iter__();

                     while(true) {
                        var1.setline(724);
                        var11 = var5.__iternext__();
                        if (var11 == null) {
                           var1.setline(726);
                           var5 = var1.getlocal(6).__iter__();

                           while(true) {
                              var1.setline(726);
                              var11 = var5.__iternext__();
                              if (var11 == null) {
                                 var1.setline(729);
                                 var5 = var1.getglobal("None");
                                 var1.getlocal(0).__setattr__("queue", var5);
                                 var5 = null;
                                 throw (Throwable)var9;
                              }

                              var1.setlocal(9, var11);
                              var1.setline(727);
                              if (var1.getlocal(9).__getattr__("is_alive").__call__(var2).__nonzero__()) {
                                 var1.setline(728);
                                 var1.getlocal(9).__getattr__("join").__call__(var2);
                              }
                           }
                        }

                        var1.setlocal(8, var11);
                        var1.setline(725);
                        var1.getlocal(0).__getattr__("queue").__getattr__("put").__call__(var2, var1.getglobal("None"));
                     }
                  }

                  var1.setline(723);
                  var1.getlocal(0).__getattr__("queue").__getattr__("join").__call__(var2);
                  var1.setline(724);
                  var5 = var1.getglobal("xrange").__call__(var2, var1.getlocal(4)).__iter__();

                  while(true) {
                     var1.setline(724);
                     var11 = var5.__iternext__();
                     if (var11 == null) {
                        var1.setline(726);
                        var5 = var1.getlocal(6).__iter__();

                        while(true) {
                           var1.setline(726);
                           var11 = var5.__iternext__();
                           if (var11 == null) {
                              var1.setline(729);
                              var5 = var1.getglobal("None");
                              var1.getlocal(0).__setattr__("queue", var5);
                              var5 = null;
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setlocal(9, var11);
                           var1.setline(727);
                           if (var1.getlocal(9).__getattr__("is_alive").__call__(var2).__nonzero__()) {
                              var1.setline(728);
                              var1.getlocal(9).__getattr__("join").__call__(var2);
                           }
                        }
                     }

                     var1.setlocal(8, var11);
                     var1.setline(725);
                     var1.getlocal(0).__getattr__("queue").__getattr__("put").__call__(var2, var1.getglobal("None"));
                  }
               }

               var1.setlocal(8, var5);
               var1.setline(715);
               PyObject var10001 = var1.getlocal(7);
               PyObject var10003 = var1.getlocal(5).__getattr__("Process");
               PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("_child")};
               String[] var7 = new String[]{"target"};
               var10003 = var10003.__call__(var2, var6, var7);
               var6 = null;
               var10001.__call__(var2, var10003);
            }
         }
      }
   }

   public PyObject _child$39(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      PyObject var3 = var1.getlocal(0).__getattr__("queue").__getattr__("get").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(733);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(734);
         var3 = var1.getlocal(1);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var3 = null;

         try {
            var1.setline(736);
            var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("MultiprocessRefactoringTool"), var1.getlocal(0)).__getattr__("refactor_file");
            var4 = Py.EmptyObjects;
            String[] var7 = new String[0];
            var10000._callextra(var4, var7, var1.getlocal(2), var1.getlocal(3));
            var4 = null;
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(739);
            var1.getlocal(0).__getattr__("queue").__getattr__("task_done").__call__(var2);
            throw (Throwable)var6;
         }

         var1.setline(739);
         var1.getlocal(0).__getattr__("queue").__getattr__("task_done").__call__(var2);
         var1.setline(740);
         var3 = var1.getlocal(0).__getattr__("queue").__getattr__("get").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject refactor_file$40(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      PyObject var3 = var1.getlocal(0).__getattr__("queue");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(744);
         var1.getlocal(0).__getattr__("queue").__getattr__("put").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(746);
         var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("MultiprocessRefactoringTool"), var1.getlocal(0)).__getattr__("refactor_file");
         PyObject[] var5 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public refactor$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fixer_pkg", "remove_prefix", "pkg", "fixer_dir", "fix_names", "name"};
      get_all_fix_names$1 = Py.newCode(2, var2, var1, "get_all_fix_names", 33, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _EveryNode$2 = Py.newCode(0, var2, var1, "_EveryNode", 46, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pat", "r", "p", "x"};
      _get_head_types$3 = Py.newCode(1, var2, var1, "_get_head_types", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fixer_list", "head_nodes", "every", "fixer", "heads", "node_type"};
      _get_headnode_dict$4 = Py.newCode(1, var2, var1, "_get_headnode_dict", 78, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pkg_name", "_[107_12]", "fix_name"};
      get_fixers_from_package$5 = Py.newCode(1, var2, var1, "get_fixers_from_package", 103, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj"};
      _identity$6 = Py.newCode(1, var2, var1, "_identity", 110, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input"};
      _from_system_newlines$7 = Py.newCode(1, var2, var1, "_from_system_newlines", 117, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input"};
      _to_system_newlines$8 = Py.newCode(1, var2, var1, "_to_system_newlines", 119, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "have_docstring", "advance", "ignore", "features", "tp", "value", "gen"};
      String[] var10001 = var2;
      refactor$py var10007 = self;
      var2 = new String[]{"gen"};
      _detect_future_features$9 = Py.newCode(1, var10001, var1, "_detect_future_features", 130, false, false, var10007, 9, var2, (String[])null, 1, 4097);
      var2 = new String[]{"tok"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"gen"};
      advance$10 = Py.newCode(0, var10001, var1, "advance", 133, false, false, var10007, 10, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      FixerError$11 = Py.newCode(0, var2, var1, "FixerError", 170, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      RefactoringTool$12 = Py.newCode(0, var2, var1, "RefactoringTool", 174, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fixer_names", "options", "explicit", "fixer"};
      __init__$13 = Py.newCode(4, var2, var1, "__init__", 182, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pre_order_fixers", "post_order_fixers", "fix_mod_path", "mod", "fix_name", "parts", "class_name", "_[250_54]", "p", "fix_class", "fixer", "key_func"};
      get_fixers$14 = Py.newCode(1, var2, var1, "get_fixers", 234, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwds"};
      log_error$15 = Py.newCode(4, var2, var1, "log_error", 274, true, true, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      log_message$16 = Py.newCode(3, var2, var1, "log_message", 278, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      log_debug$17 = Py.newCode(3, var2, var1, "log_debug", 284, true, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old_text", "new_text", "filename", "equal"};
      print_output$18 = Py.newCode(5, var2, var1, "print_output", 289, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "write", "doctests_only", "dir_or_file"};
      refactor$19 = Py.newCode(4, var2, var1, "refactor", 294, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir_name", "write", "doctests_only", "py_ext", "dirpath", "dirnames", "filenames", "name", "fullname", "_[321_27]", "dn"};
      refactor_dir$20 = Py.newCode(4, var2, var1, "refactor_dir", 303, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f", "err", "encoding"};
      _read_python_source$21 = Py.newCode(2, var2, var1, "_read_python_source", 323, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "write", "doctests_only", "input", "encoding", "output", "tree"};
      refactor_file$22 = Py.newCode(4, var2, var1, "refactor_file", 339, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "name", "features", "tree", "err"};
      refactor_string$23 = Py.newCode(3, var2, var1, "refactor_string", 362, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doctests_only", "input", "output", "tree"};
      refactor_stdin$24 = Py.newCode(2, var2, var1, "refactor_stdin", 389, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tree", "name", "fixer", "match_set", "node", "results", "new", "new_matches", "fxr"};
      refactor_tree$25 = Py.newCode(3, var2, var1, "refactor_tree", 405, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fixers", "traversal", "node", "fixer", "results", "new"};
      traverse_by$26 = Py.newCode(3, var2, var1, "traverse_by", 484, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_text", "filename", "old_text", "write", "encoding", "equal"};
      processed_file$27 = Py.newCode(6, var2, var1, "processed_file", 507, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_text", "filename", "old_text", "encoding", "f", "err"};
      write_file$28 = Py.newCode(5, var2, var1, "write_file", 528, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "filename", "result", "block", "block_lineno", "indent", "lineno", "line", "i"};
      refactor_docstring$29 = Py.newCode(3, var2, var1, "refactor_docstring", 552, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block", "lineno", "indent", "filename", "tree", "err", "line", "new", "clipped", "_[621_26]"};
      refactor_doctest$30 = Py.newCode(5, var2, var1, "refactor_doctest", 595, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "were", "file", "message", "msg", "args", "kwds"};
      summarize$31 = Py.newCode(1, var2, var1, "summarize", 624, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block", "lineno", "indent", "tree"};
      parse_block$32 = Py.newCode(4, var2, var1, "parse_block", 647, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "block", "lineno", "indent", "tokens", "type", "value", "line0", "col0", "line1", "col1", "line_text"};
      wrap_toks$33 = Py.newCode(4, var2, var1, "wrap_toks", 657, false, false, self, 33, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "block", "indent", "prefix1", "prefix2", "prefix", "line"};
      gen_lines$34 = Py.newCode(3, var2, var1, "gen_lines", 671, false, false, self, 34, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      MultiprocessingUnsupported$35 = Py.newCode(0, var2, var1, "MultiprocessingUnsupported", 691, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MultiprocessRefactoringTool$36 = Py.newCode(0, var2, var1, "MultiprocessRefactoringTool", 695, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwargs"};
      __init__$37 = Py.newCode(3, var2, var1, "__init__", 697, true, true, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "write", "doctests_only", "num_processes", "multiprocessing", "processes", "_[715_21]", "i", "p"};
      refactor$38 = Py.newCode(5, var2, var1, "refactor", 702, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "task", "args", "kwargs"};
      _child$39 = Py.newCode(1, var2, var1, "_child", 731, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs"};
      refactor_file$40 = Py.newCode(3, var2, var1, "refactor_file", 742, true, true, self, 40, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new refactor$py("lib2to3/refactor$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(refactor$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.get_all_fix_names$1(var2, var3);
         case 2:
            return this._EveryNode$2(var2, var3);
         case 3:
            return this._get_head_types$3(var2, var3);
         case 4:
            return this._get_headnode_dict$4(var2, var3);
         case 5:
            return this.get_fixers_from_package$5(var2, var3);
         case 6:
            return this._identity$6(var2, var3);
         case 7:
            return this._from_system_newlines$7(var2, var3);
         case 8:
            return this._to_system_newlines$8(var2, var3);
         case 9:
            return this._detect_future_features$9(var2, var3);
         case 10:
            return this.advance$10(var2, var3);
         case 11:
            return this.FixerError$11(var2, var3);
         case 12:
            return this.RefactoringTool$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.get_fixers$14(var2, var3);
         case 15:
            return this.log_error$15(var2, var3);
         case 16:
            return this.log_message$16(var2, var3);
         case 17:
            return this.log_debug$17(var2, var3);
         case 18:
            return this.print_output$18(var2, var3);
         case 19:
            return this.refactor$19(var2, var3);
         case 20:
            return this.refactor_dir$20(var2, var3);
         case 21:
            return this._read_python_source$21(var2, var3);
         case 22:
            return this.refactor_file$22(var2, var3);
         case 23:
            return this.refactor_string$23(var2, var3);
         case 24:
            return this.refactor_stdin$24(var2, var3);
         case 25:
            return this.refactor_tree$25(var2, var3);
         case 26:
            return this.traverse_by$26(var2, var3);
         case 27:
            return this.processed_file$27(var2, var3);
         case 28:
            return this.write_file$28(var2, var3);
         case 29:
            return this.refactor_docstring$29(var2, var3);
         case 30:
            return this.refactor_doctest$30(var2, var3);
         case 31:
            return this.summarize$31(var2, var3);
         case 32:
            return this.parse_block$32(var2, var3);
         case 33:
            return this.wrap_toks$33(var2, var3);
         case 34:
            return this.gen_lines$34(var2, var3);
         case 35:
            return this.MultiprocessingUnsupported$35(var2, var3);
         case 36:
            return this.MultiprocessRefactoringTool$36(var2, var3);
         case 37:
            return this.__init__$37(var2, var3);
         case 38:
            return this.refactor$38(var2, var3);
         case 39:
            return this._child$39(var2, var3);
         case 40:
            return this.refactor_file$40(var2, var3);
         default:
            return null;
      }
   }
}
