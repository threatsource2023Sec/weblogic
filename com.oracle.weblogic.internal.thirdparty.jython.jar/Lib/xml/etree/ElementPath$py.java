package xml.etree;

import java.util.Arrays;
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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("xml/etree/ElementPath.py")
public class ElementPath$py extends PyFunctionTable implements PyRunnable {
   static ElementPath$py self;
   static final PyCode f$0;
   static final PyCode xpath_tokenizer$1;
   static final PyCode get_parent_map$2;
   static final PyCode prepare_child$3;
   static final PyCode select$4;
   static final PyCode prepare_star$5;
   static final PyCode select$6;
   static final PyCode prepare_self$7;
   static final PyCode select$8;
   static final PyCode prepare_descendant$9;
   static final PyCode select$10;
   static final PyCode prepare_parent$11;
   static final PyCode select$12;
   static final PyCode prepare_predicate$13;
   static final PyCode select$14;
   static final PyCode select$15;
   static final PyCode select$16;
   static final PyCode select$17;
   static final PyCode select$18;
   static final PyCode _SelectorContext$19;
   static final PyCode __init__$20;
   static final PyCode iterfind$21;
   static final PyCode find$22;
   static final PyCode findall$23;
   static final PyCode findtext$24;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("('[^']*'|\"[^\"]*\"|::|//?|\\.\\.|\\(\\)|[/.*:\\[\\]\\(\\)@=])|((?:\\{[^}]+\\})?[^/\\[\\]\\(\\)@=\\s]+)|\\s+"));
      var1.setlocal("xpath_tokenizer_re", var3);
      var3 = null;
      var1.setline(73);
      PyObject[] var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, xpath_tokenizer$1, (PyObject)null);
      var1.setlocal("xpath_tokenizer", var6);
      var3 = null;
      var1.setline(87);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_parent_map$2, (PyObject)null);
      var1.setlocal("get_parent_map", var6);
      var3 = null;
      var1.setline(96);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_child$3, (PyObject)null);
      var1.setlocal("prepare_child", var6);
      var3 = null;
      var1.setline(105);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_star$5, (PyObject)null);
      var1.setlocal("prepare_star", var6);
      var3 = null;
      var1.setline(112);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_self$7, (PyObject)null);
      var1.setlocal("prepare_self", var6);
      var3 = null;
      var1.setline(118);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_descendant$9, (PyObject)null);
      var1.setlocal("prepare_descendant", var6);
      var3 = null;
      var1.setline(133);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_parent$11, (PyObject)null);
      var1.setlocal("prepare_parent", var6);
      var3 = null;
      var1.setline(146);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, prepare_predicate$13, (PyObject)null);
      var1.setlocal("prepare_predicate", var6);
      var3 = null;
      var1.setline(226);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), var1.getname("prepare_child"), PyString.fromInterned("*"), var1.getname("prepare_star"), PyString.fromInterned("."), var1.getname("prepare_self"), PyString.fromInterned(".."), var1.getname("prepare_parent"), PyString.fromInterned("//"), var1.getname("prepare_descendant"), PyString.fromInterned("["), var1.getname("prepare_predicate")});
      var1.setlocal("ops", var7);
      var3 = null;
      var1.setline(235);
      var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache", var7);
      var3 = null;
      var1.setline(237);
      var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("_SelectorContext", var5, _SelectorContext$19);
      var1.setlocal("_SelectorContext", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(247);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, iterfind$21, (PyObject)null);
      var1.setlocal("iterfind", var6);
      var3 = null;
      var1.setline(283);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, find$22, (PyObject)null);
      var1.setlocal("find", var6);
      var3 = null;
      var1.setline(292);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, findall$23, (PyObject)null);
      var1.setlocal("findall", var6);
      var3 = null;
      var1.setline(298);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, findtext$24, (PyObject)null);
      var1.setlocal("findtext", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject xpath_tokenizer$1(PyFrame var1, ThreadState var2) {
      Throwable var15;
      label59: {
         PyObject var3;
         PyObject var4;
         Object[] var5;
         PyObject var14;
         Object var10000;
         boolean var10001;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(74);
               var3 = var1.getglobal("xpath_tokenizer_re").__getattr__("findall").__call__(var2, var1.getlocal(0)).__iter__();
               break;
            case 1:
               var5 = var1.f_savedlocals;
               var3 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];

               try {
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var14 = (PyObject)var10000;
                  break;
               } catch (Throwable var9) {
                  var15 = var9;
                  var10001 = false;
                  break label59;
               }
            case 2:
               var5 = var1.f_savedlocals;
               var3 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var14 = (PyObject)var10000;
         }

         var1.setline(74);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(75);
         PyObject var10 = var1.getlocal(2).__getitem__(Py.newInteger(1));
         var1.setlocal(3, var10);
         var5 = null;
         var1.setline(76);
         var14 = var1.getlocal(3);
         if (var14.__nonzero__()) {
            var10 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var14 = var10._ne(PyString.fromInterned("{"));
            var5 = null;
            if (var14.__nonzero__()) {
               PyString var11 = PyString.fromInterned(":");
               var14 = var11._in(var1.getlocal(3));
               var5 = null;
            }
         }

         if (!var14.__nonzero__()) {
            var1.setline(85);
            var1.setline(85);
            var14 = var1.getlocal(2);
            var1.f_lasti = 2;
            var5 = new Object[8];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var14;
         }

         try {
            var1.setline(78);
            var10 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
            PyObject[] var6 = Py.unpackSequence(var10, 2);
            PyObject var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(79);
            if (var1.getlocal(1).__not__().__nonzero__()) {
               var1.setline(80);
               throw Py.makeException(var1.getglobal("KeyError"));
            }

            var1.setline(81);
            var1.setline(81);
            PyObject[] var13 = new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), null};
            PyString var17 = PyString.fromInterned("{%s}%s");
            var6 = new PyObject[]{var1.getlocal(1).__getitem__(var1.getlocal(4)), var1.getlocal(5)};
            PyTuple var16 = new PyTuple(var6);
            Arrays.fill(var6, (Object)null);
            var13[1] = var17._mod(var16);
            PyTuple var18 = new PyTuple(var13);
            Arrays.fill(var13, (Object)null);
            var1.f_lasti = 1;
            var5 = new Object[8];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var18;
         } catch (Throwable var8) {
            var15 = var8;
            var10001 = false;
         }
      }

      PyException var12 = Py.setException(var15, var1);
      if (var12.match(var1.getglobal("KeyError"))) {
         var1.setline(83);
         throw Py.makeException(var1.getglobal("SyntaxError").__call__(var2, PyString.fromInterned("prefix %r not found in prefix map")._mod(var1.getlocal(4))));
      } else {
         throw var12;
      }
   }

   public PyObject get_parent_map$2(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getlocal(0).__getattr__("parent_map");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(90);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"parent_map", var8);
         var1.setlocal(1, var8);
         var1.setline(91);
         var3 = var1.getlocal(0).__getattr__("root").__getattr__("iter").__call__(var2).__iter__();

         while(true) {
            var1.setline(91);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(92);
            PyObject var5 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(92);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(3, var6);
               var1.setline(93);
               PyObject var7 = var1.getlocal(2);
               var1.getlocal(1).__setitem__(var1.getlocal(3), var7);
               var7 = null;
            }
         }
      }

      var1.setline(94);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject prepare_child$3(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(98);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = select$4;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(103);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject select$4(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(99);
            var3 = var1.getlocal(1).__iter__();
            var1.setline(99);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(100);
            var5 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      while(true) {
         while(true) {
            var1.setline(100);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(99);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(2, var4);
               var1.setline(100);
               var5 = var1.getlocal(2).__iter__();
            } else {
               var1.setlocal(3, var6);
               var1.setline(101);
               PyObject var8 = var1.getlocal(3).__getattr__("tag");
               var9 = var8._eq(var1.getderef(0));
               var7 = null;
               if (var9.__nonzero__()) {
                  var1.setline(102);
                  var1.setline(102);
                  var9 = var1.getlocal(3);
                  var1.f_lasti = 1;
                  var7 = new Object[9];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var5;
                  var7[6] = var6;
                  var1.f_savedlocals = var7;
                  return var9;
               }
            }
         }
      }
   }

   public PyObject prepare_star$5(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, select$6, (PyObject)null);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(110);
      PyObject var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject select$6(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(107);
            var3 = var1.getlocal(1).__iter__();
            var1.setline(107);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(108);
            var5 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(108);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(3, var6);
            var1.setline(109);
            var1.setline(109);
            var8 = var1.getlocal(3);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var8;
         }

         var1.setline(107);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(108);
         var5 = var1.getlocal(2).__iter__();
      }
   }

   public PyObject prepare_self$7(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, select$8, (PyObject)null);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(116);
      PyObject var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject select$8(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(114);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(114);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(2, var4);
         var1.setline(115);
         var1.setline(115);
         var6 = var1.getlocal(2);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject prepare_descendant$9(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(PyString.fromInterned("*"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         PyString var4 = PyString.fromInterned("*");
         var1.setderef(0, var4);
         var3 = null;
      } else {
         var1.setline(122);
         if (!var1.getlocal(1).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
            var1.setline(125);
            throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid descendant")));
         }

         var1.setline(123);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var1.setderef(0, var3);
         var3 = null;
      }

      var1.setline(126);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = select$10;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject select$10(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(127);
            var3 = var1.getlocal(1).__iter__();
            var1.setline(127);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(128);
            var5 = var1.getlocal(2).__getattr__("iter").__call__(var2, var1.getderef(0)).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      while(true) {
         while(true) {
            var1.setline(128);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(127);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(2, var4);
               var1.setline(128);
               var5 = var1.getlocal(2).__getattr__("iter").__call__(var2, var1.getderef(0)).__iter__();
            } else {
               var1.setlocal(3, var6);
               var1.setline(129);
               PyObject var8 = var1.getlocal(3);
               var9 = var8._isnot(var1.getlocal(2));
               var7 = null;
               if (var9.__nonzero__()) {
                  var1.setline(130);
                  var1.setline(130);
                  var9 = var1.getlocal(3);
                  var1.f_lasti = 1;
                  var7 = new Object[9];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var5;
                  var7[6] = var6;
                  var1.f_savedlocals = var7;
                  return var9;
               }
            }
         }
      }
   }

   public PyObject prepare_parent$11(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, select$12, (PyObject)null);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(144);
      PyObject var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject select$12(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(136);
            var3 = var1.getglobal("get_parent_map").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(137);
            PyObject[] var6 = Py.EmptyObjects;
            PyDictionary var10 = new PyDictionary(var6);
            Arrays.fill(var6, (Object)null);
            PyDictionary var7 = var10;
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(138);
            var3 = var1.getlocal(1).__iter__();
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
         var1.setline(138);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(139);
         PyObject var8 = var1.getlocal(4);
         var9 = var8._in(var1.getlocal(2));
         var5 = null;
         if (var9.__nonzero__()) {
            var1.setline(140);
            var8 = var1.getlocal(2).__getitem__(var1.getlocal(4));
            var1.setlocal(5, var8);
            var5 = null;
            var1.setline(141);
            var8 = var1.getlocal(5);
            var9 = var8._notin(var1.getlocal(3));
            var5 = null;
            if (var9.__nonzero__()) {
               var1.setline(142);
               var8 = var1.getglobal("None");
               var1.getlocal(3).__setitem__(var1.getlocal(5), var8);
               var5 = null;
               var1.setline(143);
               var1.setline(143);
               var9 = var1.getlocal(5);
               var1.f_lasti = 1;
               var5 = new Object[7];
               var5[3] = var3;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var9;
            }
         }
      }
   }

   public PyObject prepare_predicate$13(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(151);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var6;
      PyObject var10000;
      while(true) {
         var1.setline(152);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(153);
         var6 = var1.getlocal(0).__call__(var2);
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(154);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(PyString.fromInterned("]"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(156);
         var10000 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var6._in(PyString.fromInterned("'\""));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(157);
            PyTuple var7 = new PyTuple(new PyObject[]{PyString.fromInterned("'"), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null)});
            var1.setlocal(1, var7);
            var3 = null;
         }

         var1.setline(158);
         var10000 = var1.getlocal(2).__getattr__("append");
         Object var10002 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = PyString.fromInterned("-");
         }

         var10000.__call__((ThreadState)var2, (PyObject)var10002);
         var1.setline(159);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
      }

      var1.setline(160);
      var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(162);
      var6 = var1.getlocal(2);
      var10000 = var6._eq(PyString.fromInterned("@-"));
      var3 = null;
      PyObject var14;
      PyObject[] var10003;
      PyCode var10004;
      if (var10000.__nonzero__()) {
         var1.setline(164);
         var6 = var1.getlocal(3).__getitem__(Py.newInteger(1));
         var1.setderef(2, var6);
         var3 = null;
         var1.setline(165);
         PyObject[] var8 = Py.EmptyObjects;
         var14 = var1.f_globals;
         var10003 = var8;
         var10004 = select$14;
         var8 = new PyObject[]{var1.getclosure(2)};
         PyFunction var10 = new PyFunction(var14, var10003, var10004, (PyObject)null, var8);
         var1.setlocal(4, var10);
         var3 = null;
         var1.setline(169);
         var6 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(170);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("@-='"));
         var4 = null;
         PyObject[] var12;
         PyFunction var13;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var4 = var1.getlocal(3).__getitem__(Py.newInteger(1));
            var1.setderef(2, var4);
            var4 = null;
            var1.setline(173);
            var4 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
            var1.setderef(1, var4);
            var4 = null;
            var1.setline(174);
            var12 = Py.EmptyObjects;
            var14 = var1.f_globals;
            var10003 = var12;
            var10004 = select$15;
            var12 = new PyObject[]{var1.getclosure(2), var1.getclosure(1)};
            var13 = new PyFunction(var14, var10003, var10004, (PyObject)null, var12);
            var1.setlocal(4, var13);
            var4 = null;
            var1.setline(178);
            var6 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(179);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("-"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\d+$"), (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0))).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(181);
               var4 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var1.setderef(3, var4);
               var4 = null;
               var1.setline(182);
               var12 = Py.EmptyObjects;
               var14 = var1.f_globals;
               var10003 = var12;
               var10004 = select$16;
               var12 = new PyObject[]{var1.getclosure(3)};
               var13 = new PyFunction(var14, var10003, var10004, (PyObject)null, var12);
               var1.setlocal(4, var13);
               var4 = null;
               var1.setline(186);
               var6 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(187);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(PyString.fromInterned("-='"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\d+$"), (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0))).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(189);
                  var4 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                  var1.setderef(3, var4);
                  var4 = null;
                  var1.setline(190);
                  var4 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
                  var1.setderef(1, var4);
                  var4 = null;
                  var1.setline(191);
                  var12 = Py.EmptyObjects;
                  var14 = var1.f_globals;
                  var10003 = var12;
                  var10004 = select$17;
                  var12 = new PyObject[]{var1.getclosure(3), var1.getclosure(1)};
                  var13 = new PyFunction(var14, var10003, var10004, (PyObject)null, var12);
                  var1.setlocal(4, var13);
                  var4 = null;
                  var1.setline(197);
                  var6 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var6;
               } else {
                  var1.setline(198);
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(PyString.fromInterned("-"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(PyString.fromInterned("-()"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var4 = var1.getlocal(2);
                        var10000 = var4._eq(PyString.fromInterned("-()-"));
                        var4 = null;
                     }
                  }

                  if (!var10000.__nonzero__()) {
                     var1.setline(224);
                     throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid predicate")));
                  } else {
                     var1.setline(200);
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(PyString.fromInterned("-"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(201);
                        var4 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)))._sub(Py.newInteger(1));
                        var1.setderef(0, var4);
                        var4 = null;
                     } else {
                        var1.setline(203);
                        var4 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                        var10000 = var4._ne(PyString.fromInterned("last"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(204);
                           throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unsupported function")));
                        }

                        var1.setline(205);
                        var4 = var1.getlocal(2);
                        var10000 = var4._eq(PyString.fromInterned("-()-"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           try {
                              var1.setline(207);
                              var4 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(2)))._sub(Py.newInteger(1));
                              var1.setderef(0, var4);
                              var4 = null;
                           } catch (Throwable var5) {
                              PyException var9 = Py.setException(var5, var1);
                              if (var9.match(var1.getglobal("ValueError"))) {
                                 var1.setline(209);
                                 throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unsupported expression")));
                              }

                              throw var9;
                           }
                        } else {
                           var1.setline(211);
                           PyInteger var11 = Py.newInteger(-1);
                           var1.setderef(0, var11);
                           var4 = null;
                        }
                     }

                     var1.setline(212);
                     var12 = Py.EmptyObjects;
                     var14 = var1.f_globals;
                     var10003 = var12;
                     var10004 = select$18;
                     var12 = new PyObject[]{var1.getclosure(0)};
                     var13 = new PyFunction(var14, var10003, var10004, (PyObject)null, var12);
                     var1.setlocal(4, var13);
                     var4 = null;
                     var1.setline(223);
                     var6 = var1.getlocal(4);
                     var1.f_lasti = -1;
                     return var6;
                  }
               }
            }
         }
      }
   }

   public PyObject select$14(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(166);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(166);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(167);
         PyObject var6 = var1.getlocal(2).__getattr__("get").__call__(var2, var1.getderef(0));
         var7 = var6._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(168);
      var1.setline(168);
      var7 = var1.getlocal(2);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject select$15(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(175);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(175);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(176);
         PyObject var6 = var1.getlocal(2).__getattr__("get").__call__(var2, var1.getderef(0));
         var7 = var6._eq(var1.getderef(1));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(177);
      var1.setline(177);
      var7 = var1.getlocal(2);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject select$16(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(183);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(183);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(184);
         PyObject var6 = var1.getlocal(2).__getattr__("find").__call__(var2, var1.getderef(0));
         var7 = var6._isnot(var1.getglobal("None"));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(185);
      var1.setline(185);
      var7 = var1.getlocal(2);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject select$17(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(192);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      label28:
      while(true) {
         var1.setline(192);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(193);
         var5 = var1.getlocal(2).__getattr__("findall").__call__(var2, var1.getderef(0)).__iter__();

         do {
            var1.setline(193);
            var6 = var5.__iternext__();
            if (var6 == null) {
               continue label28;
            }

            var1.setlocal(3, var6);
            var1.setline(194);
            PyObject var8 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3).__getattr__("itertext").__call__(var2));
            var9 = var8._eq(var1.getderef(1));
            var7 = null;
         } while(!var9.__nonzero__());

         var1.setline(195);
         var1.setline(195);
         var9 = var1.getlocal(2);
         var1.f_lasti = 1;
         var7 = new Object[9];
         var7[3] = var3;
         var7[4] = var4;
         var7[5] = var5;
         var7[6] = var6;
         var1.f_savedlocals = var7;
         return var9;
      }
   }

   public PyObject select$18(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _SelectorContext$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(238);
      PyObject var3 = var1.getname("None");
      var1.setlocal("parent_map", var3);
      var3 = null;
      var1.setline(239);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("root", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject iterfind$21(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(250);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("*"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      PyObject var4;
      try {
         var1.setline(252);
         var3 = var1.getglobal("_cache").__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var8) {
         PyException var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getglobal("KeyError"))) {
            throw var9;
         }

         var1.setline(254);
         var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache"));
         var10000 = var4._gt(Py.newInteger(100));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(255);
            var1.getglobal("_cache").__getattr__("clear").__call__(var2);
         }

         var1.setline(256);
         var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("/"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(257);
            throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot use absolute path on element")));
         }

         var1.setline(258);
         var4 = var1.getglobal("iter").__call__(var2, var1.getglobal("xpath_tokenizer").__call__(var2, var1.getlocal(1), var1.getlocal(2))).__getattr__("next");
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(259);
         var4 = var1.getlocal(4).__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(260);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var11);
         var4 = null;

         while(true) {
            var1.setline(261);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            PyException var12;
            try {
               var1.setline(263);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("ops").__getitem__(var1.getlocal(5).__getitem__(Py.newInteger(0))).__call__(var2, var1.getlocal(4), var1.getlocal(5)));
            } catch (Throwable var6) {
               var12 = Py.setException(var6, var1);
               if (var12.match(var1.getglobal("StopIteration"))) {
                  var1.setline(265);
                  throw Py.makeException(var1.getglobal("SyntaxError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid path")));
               }

               throw var12;
            }

            try {
               var1.setline(267);
               var4 = var1.getlocal(4).__call__(var2);
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(268);
               var4 = var1.getlocal(5).__getitem__(Py.newInteger(0));
               var10000 = var4._eq(PyString.fromInterned("/"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(269);
                  var4 = var1.getlocal(4).__call__(var2);
                  var1.setlocal(5, var4);
                  var4 = null;
               }
            } catch (Throwable var7) {
               var12 = Py.setException(var7, var1);
               if (var12.match(var1.getglobal("StopIteration"))) {
                  break;
               }

               throw var12;
            }
         }

         var1.setline(272);
         var4 = var1.getlocal(3);
         var1.getglobal("_cache").__setitem__(var1.getlocal(1), var4);
         var4 = null;
      }

      var1.setline(274);
      PyList var10 = new PyList(new PyObject[]{var1.getlocal(0)});
      var1.setlocal(6, var10);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("_SelectorContext").__call__(var2, var1.getlocal(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(276);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(276);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(278);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(8, var4);
         var1.setline(277);
         PyObject var5 = var1.getlocal(8).__call__(var2, var1.getlocal(7), var1.getlocal(6));
         var1.setlocal(6, var5);
         var5 = null;
      }
   }

   public PyObject find$22(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(285);
         var3 = var1.getglobal("iterfind").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__getattr__("next").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("StopIteration"))) {
            var1.setline(287);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject findall$23(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getglobal("iterfind").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findtext$24(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(300);
         var3 = var1.getglobal("iterfind").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(3)).__getattr__("next").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(301);
         Object var10000 = var1.getlocal(0).__getattr__("text");
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         Object var6 = var10000;
         var1.f_lasti = -1;
         return (PyObject)var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("StopIteration"))) {
            var1.setline(303);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public ElementPath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pattern", "namespaces", "token", "tag", "prefix", "uri"};
      xpath_tokenizer$1 = Py.newCode(2, var2, var1, "xpath_tokenizer", 73, false, false, self, 1, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"context", "parent_map", "p", "e"};
      get_parent_map$2 = Py.newCode(1, var2, var1, "get_parent_map", 87, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"next", "token", "select", "tag"};
      String[] var10001 = var2;
      ElementPath$py var10007 = self;
      var2 = new String[]{"tag"};
      prepare_child$3 = Py.newCode(2, var10001, var1, "prepare_child", 96, false, false, var10007, 3, var2, (String[])null, 1, 4097);
      var2 = new String[]{"context", "result", "elem", "e"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"tag"};
      select$4 = Py.newCode(2, var10001, var1, "select", 98, false, false, var10007, 4, (String[])null, var2, 0, 4129);
      var2 = new String[]{"next", "token", "select"};
      prepare_star$5 = Py.newCode(2, var2, var1, "prepare_star", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context", "result", "elem", "e"};
      select$6 = Py.newCode(2, var2, var1, "select", 106, false, false, self, 6, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"next", "token", "select"};
      prepare_self$7 = Py.newCode(2, var2, var1, "prepare_self", 112, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context", "result", "elem"};
      select$8 = Py.newCode(2, var2, var1, "select", 113, false, false, self, 8, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"next", "token", "select", "tag"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"tag"};
      prepare_descendant$9 = Py.newCode(2, var10001, var1, "prepare_descendant", 118, false, false, var10007, 9, var2, (String[])null, 1, 4097);
      var2 = new String[]{"context", "result", "elem", "e"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"tag"};
      select$10 = Py.newCode(2, var10001, var1, "select", 126, false, false, var10007, 10, (String[])null, var2, 0, 4129);
      var2 = new String[]{"next", "token", "select"};
      prepare_parent$11 = Py.newCode(2, var2, var1, "prepare_parent", 133, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context", "result", "parent_map", "result_map", "elem", "parent"};
      select$12 = Py.newCode(2, var2, var1, "select", 134, false, false, self, 12, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"next", "token", "signature", "predicate", "select", "index", "value", "key", "tag"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"index", "value", "key", "tag"};
      prepare_predicate$13 = Py.newCode(2, var10001, var1, "prepare_predicate", 146, false, false, var10007, 13, var2, (String[])null, 4, 4097);
      var2 = new String[]{"context", "result", "elem"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"key"};
      select$14 = Py.newCode(2, var10001, var1, "select", 165, false, false, var10007, 14, (String[])null, var2, 0, 4129);
      var2 = new String[]{"context", "result", "elem"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"key", "value"};
      select$15 = Py.newCode(2, var10001, var1, "select", 174, false, false, var10007, 15, (String[])null, var2, 0, 4129);
      var2 = new String[]{"context", "result", "elem"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"tag"};
      select$16 = Py.newCode(2, var10001, var1, "select", 182, false, false, var10007, 16, (String[])null, var2, 0, 4129);
      var2 = new String[]{"context", "result", "elem", "e"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"tag", "value"};
      select$17 = Py.newCode(2, var10001, var1, "select", 191, false, false, var10007, 17, (String[])null, var2, 0, 4129);
      var2 = new String[]{"context", "result", "parent_map", "elem", "parent", "elems"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"index"};
      select$18 = Py.newCode(2, var10001, var1, "select", 212, false, false, var10007, 18, (String[])null, var2, 0, 4129);
      var2 = new String[0];
      _SelectorContext$19 = Py.newCode(0, var2, var1, "_SelectorContext", 237, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "root"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 239, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "path", "namespaces", "selector", "next", "token", "result", "context", "select"};
      iterfind$21 = Py.newCode(3, var2, var1, "iterfind", 247, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "path", "namespaces"};
      find$22 = Py.newCode(3, var2, var1, "find", 283, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "path", "namespaces"};
      findall$23 = Py.newCode(3, var2, var1, "findall", 292, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "path", "default", "namespaces"};
      findtext$24 = Py.newCode(4, var2, var1, "findtext", 298, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ElementPath$py("xml/etree/ElementPath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ElementPath$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.xpath_tokenizer$1(var2, var3);
         case 2:
            return this.get_parent_map$2(var2, var3);
         case 3:
            return this.prepare_child$3(var2, var3);
         case 4:
            return this.select$4(var2, var3);
         case 5:
            return this.prepare_star$5(var2, var3);
         case 6:
            return this.select$6(var2, var3);
         case 7:
            return this.prepare_self$7(var2, var3);
         case 8:
            return this.select$8(var2, var3);
         case 9:
            return this.prepare_descendant$9(var2, var3);
         case 10:
            return this.select$10(var2, var3);
         case 11:
            return this.prepare_parent$11(var2, var3);
         case 12:
            return this.select$12(var2, var3);
         case 13:
            return this.prepare_predicate$13(var2, var3);
         case 14:
            return this.select$14(var2, var3);
         case 15:
            return this.select$15(var2, var3);
         case 16:
            return this.select$16(var2, var3);
         case 17:
            return this.select$17(var2, var3);
         case 18:
            return this.select$18(var2, var3);
         case 19:
            return this._SelectorContext$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.iterfind$21(var2, var3);
         case 22:
            return this.find$22(var2, var3);
         case 23:
            return this.findall$23(var2, var3);
         case 24:
            return this.findtext$24(var2, var3);
         default:
            return null;
      }
   }
}
