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
@Filename("lib2to3/fixes/fix_except.py")
public class fix_except$py extends PyFunctionTable implements PyRunnable {
   static fix_except$py self;
   static final PyCode f$0;
   static final PyCode find_excepts$1;
   static final PyCode FixExcept$2;
   static final PyCode transform$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for except statements with named exceptions.\n\nThe following cases will be converted:\n\n- \"except E, T:\" where T is a name:\n\n    except E as T:\n\n- \"except E, T:\" where T is not a name, tuple or list:\n\n        except E as t:\n            T = t\n\n    This is done because the target of an \"except\" clause must be a\n    name.\n\n- \"except E, T:\" where T is a tuple or list literal:\n\n        except E as t:\n            T = t.args\n"));
      var1.setline(21);
      PyString.fromInterned("Fixer for except statements with named exceptions.\n\nThe following cases will be converted:\n\n- \"except E, T:\" where T is a name:\n\n    except E as T:\n\n- \"except E, T:\" where T is not a name, tuple or list:\n\n        except E as t:\n            T = t\n\n    This is done because the target of an \"except\" clause must be a\n    name.\n\n- \"except E, T:\" where T is a tuple or list literal:\n\n        except E as t:\n            T = t.args\n");
      var1.setline(25);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(26);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(27);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(28);
      var3 = new String[]{"Assign", "Attr", "Name", "is_tuple", "is_list", "syms"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Assign", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("is_tuple", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("is_list", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(30);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, find_excepts$1, (PyObject)null);
      var1.setlocal("find_excepts", var6);
      var3 = null;
      var1.setline(36);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixExcept", var5, FixExcept$2);
      var1.setlocal("FixExcept", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_excepts$1(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(31);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();
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
         var1.setline(31);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(32);
         PyObject var8 = var1.getlocal(2).__getattr__("type");
         var9 = var8._eq(var1.getglobal("syms").__getattr__("except_clause"));
         var5 = null;
         if (var9.__nonzero__()) {
            var1.setline(33);
            var8 = var1.getlocal(2).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("value");
            var9 = var8._eq(PyUnicode.fromInterned("except"));
            var5 = null;
            if (var9.__nonzero__()) {
               var1.setline(34);
               var1.setline(34);
               var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getitem__(var1.getlocal(1)._add(Py.newInteger(2)))};
               PyTuple var10 = new PyTuple(var7);
               Arrays.fill(var7, (Object)null);
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

   public PyObject FixExcept$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(39);
      PyString var4 = PyString.fromInterned("\n    try_stmt< 'try' ':' (simple_stmt | suite)\n                  cleanup=(except_clause ':' (simple_stmt | suite))+\n                  tail=(['except' ':' (simple_stmt | suite)]\n                        ['else' ':' (simple_stmt | suite)]\n                        ['finally' ':' (simple_stmt | suite)]) >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(47);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$3, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(50);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("tail")).__iter__();

      while(true) {
         var1.setline(50);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(50);
            var1.dellocal(5);
            PyList var9 = var10000;
            var1.setlocal(4, var9);
            var3 = null;
            var1.setline(52);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(52);
            var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("cleanup")).__iter__();

            while(true) {
               var1.setline(52);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(52);
                  var1.dellocal(8);
                  var9 = var10000;
                  var1.setlocal(7, var9);
                  var3 = null;
                  var1.setline(53);
                  var3 = var1.getglobal("find_excepts").__call__(var2, var1.getlocal(7)).__iter__();

                  while(true) {
                     while(true) {
                        PyObject[] var5;
                        PyObject var6;
                        PyObject var10;
                        PyObject var15;
                        do {
                           var1.setline(53);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(92);
                              var10000 = new PyList();
                              var3 = var10000.__getattr__("append");
                              var1.setlocal(23, var3);
                              var3 = null;
                              var1.setline(92);
                              var3 = var1.getlocal(1).__getattr__("children").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null).__iter__();

                              while(true) {
                                 var1.setline(92);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(92);
                                    var1.dellocal(23);
                                    var3 = var10000._add(var1.getlocal(7))._add(var1.getlocal(4));
                                    var1.setlocal(22, var3);
                                    var3 = null;
                                    var1.setline(93);
                                    var3 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(1).__getattr__("type"), var1.getlocal(22));
                                    var1.f_lasti = -1;
                                    return var3;
                                 }

                                 var1.setlocal(24, var4);
                                 var1.setline(92);
                                 var1.getlocal(23).__call__(var2, var1.getlocal(24).__getattr__("clone").__call__(var2));
                              }
                           }

                           var5 = Py.unpackSequence(var4, 2);
                           var6 = var5[0];
                           var1.setlocal(10, var6);
                           var6 = null;
                           var6 = var5[1];
                           var1.setlocal(11, var6);
                           var6 = null;
                           var1.setline(54);
                           var10 = var1.getglobal("len").__call__(var2, var1.getlocal(10).__getattr__("children"));
                           var15 = var10._eq(Py.newInteger(4));
                           var5 = null;
                        } while(!var15.__nonzero__());

                        var1.setline(55);
                        var10 = var1.getlocal(10).__getattr__("children").__getslice__(Py.newInteger(1), Py.newInteger(4), (PyObject)null);
                        PyObject[] var11 = Py.unpackSequence(var10, 3);
                        PyObject var7 = var11[0];
                        var1.setlocal(12, var7);
                        var7 = null;
                        var7 = var11[1];
                        var1.setlocal(13, var7);
                        var7 = null;
                        var7 = var11[2];
                        var1.setlocal(14, var7);
                        var7 = null;
                        var5 = null;
                        var1.setline(56);
                        var15 = var1.getlocal(13).__getattr__("replace");
                        PyObject var10002 = var1.getglobal("Name");
                        var5 = new PyObject[]{PyUnicode.fromInterned("as"), PyUnicode.fromInterned(" ")};
                        String[] var12 = new String[]{"prefix"};
                        var10002 = var10002.__call__(var2, var5, var12);
                        var5 = null;
                        var15.__call__(var2, var10002);
                        var1.setline(58);
                        var10 = var1.getlocal(14).__getattr__("type");
                        var15 = var10._ne(var1.getglobal("token").__getattr__("NAME"));
                        var5 = null;
                        PyUnicode var13;
                        if (var15.__nonzero__()) {
                           var1.setline(60);
                           var15 = var1.getglobal("Name");
                           var5 = new PyObject[]{var1.getlocal(0).__getattr__("new_name").__call__(var2), PyUnicode.fromInterned(" ")};
                           var12 = new String[]{"prefix"};
                           var15 = var15.__call__(var2, var5, var12);
                           var5 = null;
                           var10 = var15;
                           var1.setlocal(15, var10);
                           var5 = null;
                           var1.setline(61);
                           var10 = var1.getlocal(14).__getattr__("clone").__call__(var2);
                           var1.setlocal(16, var10);
                           var5 = null;
                           var1.setline(62);
                           var13 = PyUnicode.fromInterned("");
                           var1.getlocal(16).__setattr__((String)"prefix", var13);
                           var5 = null;
                           var1.setline(63);
                           var1.getlocal(14).__getattr__("replace").__call__(var2, var1.getlocal(15));
                           var1.setline(64);
                           var10 = var1.getlocal(15).__getattr__("clone").__call__(var2);
                           var1.setlocal(15, var10);
                           var5 = null;
                           var1.setline(70);
                           var10 = var1.getlocal(11).__getattr__("children");
                           var1.setlocal(17, var10);
                           var5 = null;
                           var1.setline(71);
                           var10 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(17)).__iter__();

                           do {
                              var1.setline(71);
                              var6 = var10.__iternext__();
                              if (var6 == null) {
                                 break;
                              }

                              PyObject[] var14 = Py.unpackSequence(var6, 2);
                              PyObject var8 = var14[0];
                              var1.setlocal(18, var8);
                              var8 = null;
                              var8 = var14[1];
                              var1.setlocal(19, var8);
                              var8 = null;
                              var1.setline(72);
                           } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(19), var1.getglobal("pytree").__getattr__("Node")).__nonzero__());

                           var1.setline(77);
                           var15 = var1.getglobal("is_tuple").__call__(var2, var1.getlocal(14));
                           if (!var15.__nonzero__()) {
                              var15 = var1.getglobal("is_list").__call__(var2, var1.getlocal(14));
                           }

                           if (var15.__nonzero__()) {
                              var1.setline(78);
                              var10 = var1.getglobal("Assign").__call__(var2, var1.getlocal(16), var1.getglobal("Attr").__call__(var2, var1.getlocal(15), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("args"))));
                              var1.setlocal(20, var10);
                              var5 = null;
                           } else {
                              var1.setline(80);
                              var10 = var1.getglobal("Assign").__call__(var2, var1.getlocal(16), var1.getlocal(15));
                              var1.setlocal(20, var10);
                              var5 = null;
                           }

                           var1.setline(83);
                           var10 = var1.getglobal("reversed").__call__(var2, var1.getlocal(17).__getslice__((PyObject)null, var1.getlocal(18), (PyObject)null)).__iter__();

                           while(true) {
                              var1.setline(83);
                              var6 = var10.__iternext__();
                              if (var6 == null) {
                                 var1.setline(85);
                                 var1.getlocal(11).__getattr__("insert_child").__call__(var2, var1.getlocal(18), var1.getlocal(20));
                                 break;
                              }

                              var1.setlocal(21, var6);
                              var1.setline(84);
                              var1.getlocal(11).__getattr__("insert_child").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(21));
                           }
                        } else {
                           var1.setline(86);
                           var10 = var1.getlocal(14).__getattr__("prefix");
                           var15 = var10._eq(PyUnicode.fromInterned(""));
                           var5 = null;
                           if (var15.__nonzero__()) {
                              var1.setline(89);
                              var13 = PyUnicode.fromInterned(" ");
                              var1.getlocal(14).__setattr__((String)"prefix", var13);
                              var5 = null;
                           }
                        }
                     }
                  }
               }

               var1.setlocal(9, var4);
               var1.setline(52);
               var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("clone").__call__(var2));
            }
         }

         var1.setlocal(6, var4);
         var1.setline(50);
         var1.getlocal(5).__call__(var2, var1.getlocal(6).__getattr__("clone").__call__(var2));
      }
   }

   public fix_except$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"nodes", "i", "n"};
      find_excepts$1 = Py.newCode(1, var2, var1, "find_excepts", 30, false, false, self, 1, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      FixExcept$2 = Py.newCode(0, var2, var1, "FixExcept", 36, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "tail", "_[50_16]", "n", "try_cleanup", "_[52_23]", "ch", "except_clause", "e_suite", "E", "comma", "N", "new_N", "target", "suite_stmts", "i", "stmt", "assign", "child", "children", "_[92_20]", "c"};
      transform$3 = Py.newCode(3, var2, var1, "transform", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_except$py("lib2to3/fixes/fix_except$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_except$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.find_excepts$1(var2, var3);
         case 2:
            return this.FixExcept$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         default:
            return null;
      }
   }
}
