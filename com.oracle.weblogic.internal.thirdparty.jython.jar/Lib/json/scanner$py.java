package json;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/scanner.py")
public class scanner$py extends PyFunctionTable implements PyRunnable {
   static scanner$py self;
   static final PyCode f$0;
   static final PyCode py_make_scanner$1;
   static final PyCode _scan_once$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("JSON token scanner\n"));
      var1.setline(2);
      PyString.fromInterned("JSON token scanner\n");
      var1.setline(3);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;

      PyObject var4;
      PyObject[] var8;
      try {
         var1.setline(5);
         String[] var7 = new String[]{"make_scanner"};
         var8 = imp.importFrom("_json", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("c_make_scanner", var4);
         var4 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getname("ImportError"))) {
            throw var6;
         }

         var1.setline(7);
         var4 = var1.getname("None");
         var1.setlocal("c_make_scanner", var4);
         var4 = null;
      }

      var1.setline(9);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("make_scanner")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(11);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(-?(?:0|[1-9]\\d*))(\\.\\d+)?([eE][-+]?\\d+)?"), (PyObject)var1.getname("re").__getattr__("VERBOSE")._or(var1.getname("re").__getattr__("MULTILINE"))._or(var1.getname("re").__getattr__("DOTALL")));
      var1.setlocal("NUMBER_RE", var3);
      var3 = null;
      var1.setline(15);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, py_make_scanner$1, (PyObject)null);
      var1.setlocal("py_make_scanner", var10);
      var3 = null;
      var1.setline(67);
      PyObject var10000 = var1.getname("c_make_scanner");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getname("py_make_scanner");
      }

      var3 = var10000;
      var1.setlocal("make_scanner", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject py_make_scanner$1(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getlocal(0).__getattr__("parse_object");
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getlocal(0).__getattr__("parse_array");
      var1.setderef(7, var3);
      var3 = null;
      var1.setline(18);
      var3 = var1.getlocal(0).__getattr__("parse_string");
      var1.setderef(6, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("NUMBER_RE").__getattr__("match");
      var1.setderef(9, var3);
      var3 = null;
      var1.setline(20);
      var3 = var1.getlocal(0).__getattr__("encoding");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getlocal(0).__getattr__("strict");
      var1.setderef(10, var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(0).__getattr__("parse_float");
      var1.setderef(5, var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getlocal(0).__getattr__("parse_int");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getlocal(0).__getattr__("parse_constant");
      var1.setderef(11, var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getlocal(0).__getattr__("object_hook");
      var1.setderef(8, var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getlocal(0).__getattr__("object_pairs_hook");
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(28);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = _scan_once$2;
      var4 = new PyObject[]{var1.getclosure(6), var1.getclosure(0), var1.getclosure(10), var1.getclosure(2), var1.getclosure(4), var1.getclosure(8), var1.getclosure(3), var1.getclosure(7), var1.getclosure(9), var1.getclosure(5), var1.getclosure(1), var1.getclosure(11)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setderef(4, var5);
      var3 = null;
      var1.setline(65);
      var3 = var1.getderef(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _scan_once$2(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var8;
      try {
         var1.setline(30);
         var8 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("IndexError"))) {
            var1.setline(32);
            throw Py.makeException(var1.getglobal("StopIteration"));
         }

         throw var3;
      }

      var1.setline(34);
      var8 = var1.getlocal(2);
      PyObject var10000 = var8._eq(PyString.fromInterned("\""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(35);
         var8 = var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getlocal(1)._add(Py.newInteger(1)), var1.getderef(1), var1.getderef(2));
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(36);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("{"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(37);
            var10000 = var1.getderef(3);
            PyObject[] var10 = new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)._add(Py.newInteger(1))}), var1.getderef(1), var1.getderef(2), var1.getderef(4), var1.getderef(5), var1.getderef(6)};
            var8 = var10000.__call__(var2, var10);
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(39);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("["));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(40);
               var8 = var1.getderef(7).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)._add(Py.newInteger(1))})), (PyObject)var1.getderef(4));
               var1.f_lasti = -1;
               return var8;
            } else {
               var1.setline(41);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(PyString.fromInterned("n"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(4)), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("null"));
                  var4 = null;
               }

               PyTuple var9;
               if (var10000.__nonzero__()) {
                  var1.setline(42);
                  var9 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(1)._add(Py.newInteger(4))});
                  var1.f_lasti = -1;
                  return var9;
               } else {
                  var1.setline(43);
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(PyString.fromInterned("t"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(4)), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("true"));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(44);
                     var9 = new PyTuple(new PyObject[]{var1.getglobal("True"), var1.getlocal(1)._add(Py.newInteger(4))});
                     var1.f_lasti = -1;
                     return var9;
                  } else {
                     var1.setline(45);
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(PyString.fromInterned("f"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(5)), (PyObject)null);
                        var10000 = var4._eq(PyString.fromInterned("false"));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(46);
                        var9 = new PyTuple(new PyObject[]{var1.getglobal("False"), var1.getlocal(1)._add(Py.newInteger(5))});
                        var1.f_lasti = -1;
                        return var9;
                     } else {
                        var1.setline(48);
                        var4 = var1.getderef(8).__call__(var2, var1.getlocal(0), var1.getlocal(1));
                        var1.setlocal(3, var4);
                        var4 = null;
                        var1.setline(49);
                        var4 = var1.getlocal(3);
                        var10000 = var4._isnot(var1.getglobal("None"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(50);
                           var4 = var1.getlocal(3).__getattr__("groups").__call__(var2);
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
                           var4 = null;
                           var1.setline(51);
                           var10000 = var1.getlocal(5);
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(6);
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(52);
                              var10000 = var1.getderef(9);
                              PyObject var10002 = var1.getlocal(4);
                              Object var10003 = var1.getlocal(5);
                              if (!((PyObject)var10003).__nonzero__()) {
                                 var10003 = PyString.fromInterned("");
                              }

                              var10002 = var10002._add((PyObject)var10003);
                              var10003 = var1.getlocal(6);
                              if (!((PyObject)var10003).__nonzero__()) {
                                 var10003 = PyString.fromInterned("");
                              }

                              var4 = var10000.__call__(var2, var10002._add((PyObject)var10003));
                              var1.setlocal(7, var4);
                              var4 = null;
                           } else {
                              var1.setline(54);
                              var4 = var1.getderef(10).__call__(var2, var1.getlocal(4));
                              var1.setlocal(7, var4);
                              var4 = null;
                           }

                           var1.setline(55);
                           var9 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(3).__getattr__("end").__call__(var2)});
                           var1.f_lasti = -1;
                           return var9;
                        } else {
                           var1.setline(56);
                           var4 = var1.getlocal(2);
                           var10000 = var4._eq(PyString.fromInterned("N"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(3)), (PyObject)null);
                              var10000 = var4._eq(PyString.fromInterned("NaN"));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(57);
                              var9 = new PyTuple(new PyObject[]{var1.getderef(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NaN")), var1.getlocal(1)._add(Py.newInteger(3))});
                              var1.f_lasti = -1;
                              return var9;
                           } else {
                              var1.setline(58);
                              var4 = var1.getlocal(2);
                              var10000 = var4._eq(PyString.fromInterned("I"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(8)), (PyObject)null);
                                 var10000 = var4._eq(PyString.fromInterned("Infinity"));
                                 var4 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(59);
                                 var9 = new PyTuple(new PyObject[]{var1.getderef(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Infinity")), var1.getlocal(1)._add(Py.newInteger(8))});
                                 var1.f_lasti = -1;
                                 return var9;
                              } else {
                                 var1.setline(60);
                                 var4 = var1.getlocal(2);
                                 var10000 = var4._eq(PyString.fromInterned("-"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var4 = var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(9)), (PyObject)null);
                                    var10000 = var4._eq(PyString.fromInterned("-Infinity"));
                                    var4 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(61);
                                    var9 = new PyTuple(new PyObject[]{var1.getderef(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-Infinity")), var1.getlocal(1)._add(Py.newInteger(9))});
                                    var1.f_lasti = -1;
                                    return var9;
                                 } else {
                                    var1.setline(63);
                                    throw Py.makeException(var1.getglobal("StopIteration"));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public scanner$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"context", "encoding", "parse_int", "parse_object", "object_pairs_hook", "_scan_once", "parse_float", "parse_string", "parse_array", "object_hook", "match_number", "strict", "parse_constant"};
      String[] var10001 = var2;
      scanner$py var10007 = self;
      var2 = new String[]{"encoding", "parse_int", "parse_object", "object_pairs_hook", "_scan_once", "parse_float", "parse_string", "parse_array", "object_hook", "match_number", "strict", "parse_constant"};
      py_make_scanner$1 = Py.newCode(1, var10001, var1, "py_make_scanner", 15, false, false, var10007, 1, var2, (String[])null, 12, 4097);
      var2 = new String[]{"string", "idx", "nextchar", "m", "integer", "frac", "exp", "res"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"parse_string", "encoding", "strict", "parse_object", "_scan_once", "object_hook", "object_pairs_hook", "parse_array", "match_number", "parse_float", "parse_int", "parse_constant"};
      _scan_once$2 = Py.newCode(2, var10001, var1, "_scan_once", 28, false, false, var10007, 2, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new scanner$py("json/scanner$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(scanner$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.py_make_scanner$1(var2, var3);
         case 2:
            return this._scan_once$2(var2, var3);
         default:
            return null;
      }
   }
}
