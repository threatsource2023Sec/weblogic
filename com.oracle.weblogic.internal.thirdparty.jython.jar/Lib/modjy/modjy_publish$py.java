package modjy;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_publish.py")
public class modjy_publish$py extends PyFunctionTable implements PyRunnable {
   static modjy_publish$py self;
   static final PyCode f$0;
   static final PyCode modjy_publisher$1;
   static final PyCode init_publisher$2;
   static final PyCode map_uri$3;
   static final PyCode get_app_object$4;
   static final PyCode get_app_object_importable$5;
   static final PyCode load_importable$6;
   static final PyCode get_app_object_old_style$7;
   static final PyCode load_object$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(22);
      var3 = imp.importOne("synchronize", var1, -1);
      var1.setlocal("synchronize", var3);
      var3 = null;
      var1.setline(24);
      String[] var5 = new String[]{"File"};
      PyObject[] var6 = imp.importFrom("java.io", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("File", var4);
      var4 = null;
      var1.setline(26);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(28);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("modjy_publisher", var6, modjy_publisher$1);
      var1.setlocal("modjy_publisher", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject modjy_publisher$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(30);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, init_publisher$2, (PyObject)null);
      var1.setlocal("init_publisher", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, map_uri$3, (PyObject)null);
      var1.setlocal("map_uri", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_app_object$4, (PyObject)null);
      var1.setlocal("get_app_object", var4);
      var3 = null;
      var1.setline(70);
      PyObject var5 = var1.getname("synchronize").__getattr__("make_synchronized").__call__(var2, var1.getname("get_app_object"));
      var1.setlocal("get_app_object", var5);
      var3 = null;
      var1.setline(72);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_app_object_importable$5, (PyObject)null);
      var1.setlocal("get_app_object_importable", var4);
      var3 = null;
      var1.setline(93);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_importable$6, (PyObject)null);
      var1.setlocal("load_importable", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_app_object_old_style$7, (PyObject)null);
      var1.setlocal("get_app_object_old_style", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_object$8, (PyObject)null);
      var1.setlocal("load_object", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject init_publisher$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("cache", var3);
      var3 = null;
      var1.setline(32);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_directory")).__nonzero__()) {
         var1.setline(33);
         var3 = var1.getlocal(0).__getattr__("expand_relative_path").__call__(var2, var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_directory")));
         var1.getlocal(0).__setattr__("app_directory", var3);
         var3 = null;
      } else {
         var1.setline(35);
         var3 = var1.getlocal(0).__getattr__("servlet_context").__getattr__("getRealPath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.getlocal(0).__setattr__("app_directory", var3);
         var3 = null;
      }

      var1.setline(36);
      var3 = var1.getlocal(0).__getattr__("app_directory");
      var1.getlocal(0).__getattr__("params").__setitem__((PyObject)PyString.fromInterned("app_directory"), var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getlocal(0).__getattr__("app_directory");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("app_directory");
         var10000 = var3._in(var1.getglobal("sys").__getattr__("path"));
         var3 = null;
         var10000 = var10000.__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(38);
         var1.getglobal("sys").__getattr__("path").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("app_directory"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject map_uri$3(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("app_directory"), var1.getglobal("File").__getattr__("separator"), var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_filename"))}));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_callable_name"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(43);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("callable_query_name")).__nonzero__()) {
         var1.setline(44);
         var3 = var1.getlocal(1).__getattr__("getQueryString").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(45);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(46);
            var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&")).__iter__();

            while(true) {
               var1.setline(46);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(6, var4);
               var1.setline(47);
               PyObject var5 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
               PyObject var10000 = var5._ne(Py.newInteger(-1));
               var5 = null;
               PyObject[] var6;
               PyObject var7;
               if (var10000.__nonzero__()) {
                  var1.setline(48);
                  var5 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(8, var7);
                  var7 = null;
                  var5 = null;
               } else {
                  var1.setline(50);
                  PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned("")});
                  var6 = Py.unpackSequence(var10, 2);
                  var7 = var6[0];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(8, var7);
                  var7 = null;
                  var5 = null;
               }

               var1.setline(51);
               var5 = var1.getlocal(7);
               var10000 = var5._eq(var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("callable_query_name")));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(52);
                  var5 = var1.getlocal(8);
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            }
         } else {
            var1.setline(54);
            PyString var8 = PyString.fromInterned("");
            var1.setlocal(4, var8);
            var3 = null;
         }
      }

      var1.setline(55);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject get_app_object$4(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("getContextPath").__call__(var2), var1.getlocal(1).__getattr__("getServletPath").__call__(var2)}));
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("SCRIPT_NAME"), var3);
      var3 = null;
      var1.setline(59);
      Object var10000 = var1.getlocal(1).__getattr__("getPathInfo").__call__(var2);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var5 = var10000;
      var1.setlocal(3, (PyObject)var5);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(3);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("PATH_INFO"), var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getglobal("File").__call__(var2, var1.getlocal(0).__getattr__("app_directory"), var1.getlocal(3)).__getattr__("getPath").__call__(var2);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("PATH_TRANSLATED"), var3);
      var3 = null;
      var1.setline(63);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_import_name")).__nonzero__()) {
         var1.setline(64);
         var3 = var1.getlocal(0).__getattr__("get_app_object_importable").__call__(var2, var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("app_import_name")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(66);
         PyObject var4 = var1.getlocal(0).__getattr__("cache");
         PyObject var7 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var7.__nonzero__()) {
            var1.setline(67);
            PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"cache", var6);
            var4 = null;
         }

         var1.setline(68);
         var3 = var1.getlocal(0).__getattr__("get_app_object_old_style").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_app_object_importable$5(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Attempting to import application callable '%s'\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.setline(75);
      PyObject var3 = var1.getlocal(0).__getattr__("cache");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getlocal(0).__getattr__("load_importable").__call__(var2, var1.getlocal(1).__getattr__("strip").__call__(var2));
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(77);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("cache_callables"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(78);
            var3 = var1.getlocal(2).__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(79);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
         var1.getlocal(0).__setattr__((String)"cache", var6);
         var3 = null;
      }

      var1.setline(80);
      var3 = var1.getlocal(0).__getattr__("cache");
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(81);
      var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Application is ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(2))));
      var1.setline(82);
      var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("cache_callables")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(83);
         var3 = var1.getlocal(2).__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(84);
         var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Instantiated application is ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(2))));
      }

      var1.setline(85);
      var3 = var1.getlocal(4);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(2), var1.getlocal(4)).__not__().__nonzero__()) {
            var1.setline(87);
            var1.getlocal(0).__getattr__("log").__getattr__("fatal").__call__(var2, PyString.fromInterned("Attribute error application callable '%s' as no method '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
            var1.setline(88);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("ApplicationNotFound"), PyString.fromInterned("Attribute error application callable '%s' as no method '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
         }

         var1.setline(89);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(4));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(90);
         var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Application method is ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(2))));
      }

      var1.setline(91);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_importable$6(PyFrame var1, ThreadState var2) {
      PyObject var5;
      try {
         var1.setline(95);
         PyObject var3 = var1.getglobal("False");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(95);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(96);
         var3 = var1.getlocal(1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(97);
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("()"));
         PyObject var10000 = var3._ne(Py.newInteger(-1));
         var3 = null;
         PyObject[] var7;
         if (var10000.__nonzero__()) {
            var1.setline(98);
            var3 = var1.getglobal("True");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(99);
            var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("()"));
            var7 = Py.unpackSequence(var3, 2);
            var5 = var7[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
            var1.setline(100);
            if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
               var1.setline(101);
               var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(102);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(103);
               var3 = var1.getglobal("None");
               var1.setlocal(3, var3);
               var3 = null;
            }
         }

         var1.setline(104);
         var3 = var1.getlocal(4).__getattr__("rsplit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)Py.newInteger(1));
         var7 = Py.unpackSequence(var3, 2);
         var5 = var7[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(105);
         var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(5), var1.getglobal("globals").__call__(var2), var1.getglobal("locals").__call__(var2), new PyList(new PyObject[]{var1.getlocal(6)}));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(106);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(7), var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(107);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var8;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("AttributeError")}))) {
            var5 = var4.value;
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(109);
            var1.getlocal(0).__getattr__("log").__getattr__("fatal").__call__(var2, PyString.fromInterned("Import error import application callable '%s': %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("str").__call__(var2, var1.getlocal(8))})));
            var1.setline(110);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("ApplicationNotFound"), PyString.fromInterned("Failed to import app callable '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("str").__call__(var2, var1.getlocal(8))})));
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var4;
         }
      }
   }

   public PyObject get_app_object_old_style$7(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getlocal(0).__getattr__("map_uri").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(114);
      var3 = var1.getlocal(3);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(115);
      if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("cache_callables")).__not__().__nonzero__()) {
         var1.setline(116);
         var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Caching of callables disabled"));
         var1.setline(117);
         var3 = var1.getlocal(0).__getattr__("load_object").__call__(var2, var1.getlocal(5), var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(118);
         if (var1.getlocal(0).__getattr__("cache").__getattr__("has_key").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)}))).__not__().__nonzero__()) {
            var1.setline(119);
            var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Callable object not in cache: %s#%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})));
            var1.setline(120);
            var3 = var1.getlocal(0).__getattr__("load_object").__call__(var2, var1.getlocal(5), var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(121);
            PyObject var7 = var1.getlocal(0).__getattr__("cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})));
            PyObject[] var8 = Py.unpackSequence(var7, 2);
            PyObject var6 = var8[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var8[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
            var1.setline(122);
            var1.getlocal(0).__getattr__("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Callable object was in cache: %s#%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})));
            var1.setline(123);
            if (var1.getlocal(0).__getattr__("params").__getitem__(PyString.fromInterned("reload_on_mod")).__nonzero__()) {
               var1.setline(124);
               var7 = var1.getglobal("File").__call__(var2, var1.getlocal(5));
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(125);
               var7 = var1.getlocal(8).__getattr__("lastModified").__call__(var2);
               PyObject var10000 = var7._gt(var1.getlocal(7));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(126);
                  var1.getlocal(0).__getattr__("log").__getattr__("info").__call__(var2, PyString.fromInterned("Source file '%s' has been modified: reloading")._mod(var1.getlocal(5)));
                  var1.setline(127);
                  var3 = var1.getlocal(0).__getattr__("load_object").__call__(var2, var1.getlocal(5), var1.getlocal(4));
                  var1.f_lasti = -1;
                  return var3;
               }
            }

            var1.setline(128);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject load_object$8(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(132);
         PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(132);
         var1.getglobal("execfile").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.setline(133);
         PyObject var7 = var1.getlocal(3).__getitem__(var1.getlocal(2));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(134);
         var7 = var1.getglobal("File").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(135);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5).__getattr__("lastModified").__call__(var2)});
         var1.getlocal(0).__getattr__("cache").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), var8);
         var3 = null;
         var1.setline(136);
         var7 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var7;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         PyObject var5;
         if (var4.match(var1.getglobal("IOError"))) {
            var5 = var4.value;
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(138);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("ApplicationNotFound"), PyString.fromInterned("Application filename not found: %s")._mod(var1.getlocal(1)));
         } else if (var4.match(var1.getglobal("KeyError"))) {
            var5 = var4.value;
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(140);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("NoCallable"), PyString.fromInterned("No callable named '%s' in %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})));
         } else {
            if (!var4.match(var1.getglobal("Exception"))) {
               throw var4;
            }

            var5 = var4.value;
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(142);
            var1.getlocal(0).__getattr__("raise_exc").__call__(var2, var1.getglobal("NoCallable"), PyString.fromInterned("Error loading jython callable '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("str").__call__(var2, var1.getlocal(8))})));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public modjy_publish$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      modjy_publisher$1 = Py.newCode(0, var2, var1, "modjy_publisher", 28, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      init_publisher$2 = Py.newCode(1, var2, var1, "init_publisher", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "environ", "source_uri", "callable_name", "query_string", "name_val", "name", "value"};
      map_uri$3 = Py.newCode(3, var2, var1, "map_uri", 40, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "environ", "path_info"};
      get_app_object$4 = Py.newCode(3, var2, var1, "get_app_object", 57, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "importable_name", "application", "instantiable", "method_name"};
      get_app_object_importable$5 = Py.newCode(2, var2, var1, "get_app_object_importable", 72, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "instantiable", "method_name", "importable_name", "module_path", "from_name", "imported", "aix"};
      load_importable$6 = Py.newCode(2, var2, var1, "load_importable", 93, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "req", "environ", "source_uri", "callable_name", "source_filename", "app_callable", "last_mod", "f"};
      get_app_object_old_style$7 = Py.newCode(3, var2, var1, "get_app_object_old_style", 112, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "callable_name", "app_ns", "app_callable", "f", "ioe", "k", "x"};
      load_object$8 = Py.newCode(3, var2, var1, "load_object", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_publish$py("modjy/modjy_publish$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_publish$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.modjy_publisher$1(var2, var3);
         case 2:
            return this.init_publisher$2(var2, var3);
         case 3:
            return this.map_uri$3(var2, var3);
         case 4:
            return this.get_app_object$4(var2, var3);
         case 5:
            return this.get_app_object_importable$5(var2, var3);
         case 6:
            return this.load_importable$6(var2, var3);
         case 7:
            return this.get_app_object_old_style$7(var2, var3);
         case 8:
            return this.load_object$8(var2, var3);
         default:
            return null;
      }
   }
}
