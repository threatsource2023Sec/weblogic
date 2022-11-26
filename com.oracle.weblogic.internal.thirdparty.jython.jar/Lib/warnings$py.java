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
@MTime(1498849384000L)
@Filename("warnings.py")
public class warnings$py extends PyFunctionTable implements PyRunnable {
   static warnings$py self;
   static final PyCode f$0;
   static final PyCode warnpy3k$1;
   static final PyCode _show_warning$2;
   static final PyCode formatwarning$3;
   static final PyCode filterwarnings$4;
   static final PyCode simplefilter$5;
   static final PyCode resetwarnings$6;
   static final PyCode _OptionError$7;
   static final PyCode _processoptions$8;
   static final PyCode _setoption$9;
   static final PyCode _getaction$10;
   static final PyCode _getcategory$11;
   static final PyCode SysGlobals$12;
   static final PyCode __getitem__$13;
   static final PyCode get$14;
   static final PyCode setdefault$15;
   static final PyCode __contains__$16;
   static final PyCode warn$17;
   static final PyCode warn_explicit$18;
   static final PyCode WarningMessage$19;
   static final PyCode __init__$20;
   static final PyCode __str__$21;
   static final PyCode catch_warnings$22;
   static final PyCode __init__$23;
   static final PyCode __repr__$24;
   static final PyCode __enter__$25;
   static final PyCode showwarning$26;
   static final PyCode __exit__$27;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Python part of the warnings subsystem."));
      var1.setline(1);
      PyString.fromInterned("Python part of the warnings subsystem.");
      var1.setline(6);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(10);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("warn"), PyString.fromInterned("showwarning"), PyString.fromInterned("formatwarning"), PyString.fromInterned("filterwarnings"), PyString.fromInterned("resetwarnings"), PyString.fromInterned("catch_warnings")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(13);
      PyObject[] var9 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      PyFunction var10 = new PyFunction(var1.f_globals, var9, warnpy3k$1, PyString.fromInterned("Issue a deprecation warning for Python 3.x related changes.\n\n    Warnings are omitted unless Python is started with the -3 option.\n    "));
      var1.setlocal("warnpy3k", var10);
      var3 = null;
      var1.setline(23);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var9, _show_warning$2, PyString.fromInterned("Hook to write a warning to a file; replace if you like."));
      var1.setlocal("_show_warning", var10);
      var3 = null;
      var1.setline(33);
      var3 = var1.getname("_show_warning");
      var1.setlocal("showwarning", var3);
      var3 = null;
      var1.setline(35);
      var9 = new PyObject[]{var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var9, formatwarning$3, PyString.fromInterned("Function to format a warning the standard way."));
      var1.setlocal("formatwarning", var10);
      var3 = null;
      var1.setline(44);
      var9 = new PyObject[]{PyString.fromInterned(""), var1.getname("Warning"), PyString.fromInterned(""), Py.newInteger(0), Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var9, filterwarnings$4, PyString.fromInterned("Insert an entry into the list of warnings filters (at the front).\n\n    'action' -- one of \"error\", \"ignore\", \"always\", \"default\", \"module\",\n                or \"once\"\n    'message' -- a regex that the warning message must match\n    'category' -- a class that the warning must be a subclass of\n    'module' -- a regex that the module name must match\n    'lineno' -- an integer line number, 0 matches all warnings\n    'append' -- if true, append to the list of filters\n    "));
      var1.setlocal("filterwarnings", var10);
      var3 = null;
      var1.setline(73);
      var9 = new PyObject[]{var1.getname("Warning"), Py.newInteger(0), Py.newInteger(0)};
      var10 = new PyFunction(var1.f_globals, var9, simplefilter$5, PyString.fromInterned("Insert a simple entry into the list of warnings filters (at the front).\n\n    A simple filter matches all modules and messages.\n    'action' -- one of \"error\", \"ignore\", \"always\", \"default\", \"module\",\n                or \"once\"\n    'category' -- a class that the warning must be a subclass of\n    'lineno' -- an integer line number, 0 matches all warnings\n    'append' -- if true, append to the list of filters\n    "));
      var1.setlocal("simplefilter", var10);
      var3 = null;
      var1.setline(93);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, resetwarnings$6, PyString.fromInterned("Clear the list of warning filters, so that no filters are active."));
      var1.setlocal("resetwarnings", var10);
      var3 = null;
      var1.setline(97);
      var9 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("_OptionError", var9, _OptionError$7);
      var1.setlocal("_OptionError", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(102);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _processoptions$8, (PyObject)null);
      var1.setlocal("_processoptions", var10);
      var3 = null;
      var1.setline(110);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _setoption$9, (PyObject)null);
      var1.setlocal("_setoption", var10);
      var3 = null;
      var1.setline(137);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _getaction$10, (PyObject)null);
      var1.setlocal("_getaction", var10);
      var3 = null;
      var1.setline(147);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _getcategory$11, (PyObject)null);
      var1.setlocal("_getcategory", var10);
      var3 = null;
      var1.setline(172);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("SysGlobals", var9, SysGlobals$12);
      var1.setlocal("SysGlobals", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(194);
      var9 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      var10 = new PyFunction(var1.f_globals, var9, warn$17, PyString.fromInterned("Issue a warning, or maybe ignore it or raise an exception."));
      var1.setlocal("warn", var10);
      var3 = null;
      var1.setline(236);
      var9 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var9, warn_explicit$18, (PyObject)null);
      var1.setlocal("warn_explicit", var10);
      var3 = null;
      var1.setline(304);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("WarningMessage", var9, WarningMessage$19);
      var1.setlocal("WarningMessage", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(324);
      var9 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("catch_warnings", var9, catch_warnings$22);
      var1.setlocal("catch_warnings", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(393);
      var3 = var1.getname("False");
      var1.setlocal("_warnings_defaults", var3);
      var3 = null;

      try {
         var1.setline(395);
         String[] var16 = new String[]{"filters", "default_action", "once_registry", "warn", "warn_explicit"};
         var9 = imp.importFrom("_warnings", var16, var1, -1);
         var4 = var9[0];
         var1.setlocal("filters", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("default_action", var4);
         var4 = null;
         var4 = var9[2];
         var1.setlocal("once_registry", var4);
         var4 = null;
         var4 = var9[3];
         var1.setlocal("warn", var4);
         var4 = null;
         var4 = var9[4];
         var1.setlocal("warn_explicit", var4);
         var4 = null;
         var1.setline(397);
         var3 = var1.getname("default_action");
         var1.setlocal("defaultaction", var3);
         var3 = null;
         var1.setline(398);
         var3 = var1.getname("once_registry");
         var1.setlocal("onceregistry", var3);
         var3 = null;
         var1.setline(399);
         var3 = var1.getname("True");
         var1.setlocal("_warnings_defaults", var3);
         var3 = null;
         var1.setline(400);
         var3 = var1.getname("filters");
         var1.setlocal("_filters", var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var15 = Py.setException(var7, var1);
         if (!var15.match(var1.getname("ImportError"))) {
            throw var15;
         }

         var1.setline(402);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal("filters", var11);
         var1.setlocal("_filters", var11);
         var1.setline(403);
         PyString var12 = PyString.fromInterned("default");
         var1.setlocal("defaultaction", var12);
         var1.setlocal("default_action", var12);
         var1.setline(404);
         PyDictionary var13 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal("onceregistry", var13);
         var1.setlocal("once_registry", var13);
      }

      var1.setline(408);
      var1.getname("_processoptions").__call__(var2, var1.getname("sys").__getattr__("warnoptions"));
      var1.setline(409);
      if (var1.getname("_warnings_defaults").__not__().__nonzero__()) {
         var1.setline(410);
         var8 = new PyList(new PyObject[]{var1.getname("ImportWarning"), var1.getname("PendingDeprecationWarning")});
         var1.setlocal("silence", var8);
         var3 = null;
         var1.setline(412);
         PyObject var10000 = var1.getname("sys").__getattr__("py3kwarning").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getname("sys").__getattr__("flags").__getattr__("division_warning").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(413);
            var1.getname("silence").__getattr__("append").__call__(var2, var1.getname("DeprecationWarning"));
         }

         var1.setline(414);
         var3 = var1.getname("silence").__iter__();

         while(true) {
            var1.setline(414);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(416);
               var3 = var1.getname("sys").__getattr__("flags").__getattr__("bytes_warning");
               var1.setlocal("bytes_warning", var3);
               var3 = null;
               var1.setline(417);
               var3 = var1.getname("bytes_warning");
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
               PyString var17;
               if (var10000.__nonzero__()) {
                  var1.setline(418);
                  var17 = PyString.fromInterned("error");
                  var1.setlocal("bytes_action", var17);
                  var3 = null;
               } else {
                  var1.setline(419);
                  if (var1.getname("bytes_warning").__nonzero__()) {
                     var1.setline(420);
                     var17 = PyString.fromInterned("default");
                     var1.setlocal("bytes_action", var17);
                     var3 = null;
                  } else {
                     var1.setline(422);
                     var17 = PyString.fromInterned("ignore");
                     var1.setlocal("bytes_action", var17);
                     var3 = null;
                  }
               }

               var1.setline(423);
               var10000 = var1.getname("simplefilter");
               var9 = new PyObject[]{var1.getname("bytes_action"), var1.getname("BytesWarning"), Py.newInteger(1)};
               String[] var14 = new String[]{"category", "append"};
               var10000.__call__(var2, var9, var14);
               var3 = null;
               break;
            }

            var1.setlocal("cls", var4);
            var1.setline(415);
            var10000 = var1.getname("simplefilter");
            PyObject[] var5 = new PyObject[]{PyString.fromInterned("ignore"), var1.getname("cls")};
            String[] var6 = new String[]{"category"};
            var10000.__call__(var2, var5, var6);
            var5 = null;
         }
      }

      var1.setline(424);
      var1.dellocal("_warnings_defaults");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warnpy3k$1(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyString.fromInterned("Issue a deprecation warning for Python 3.x related changes.\n\n    Warnings are omitted unless Python is started with the -3 option.\n    ");
      var1.setline(18);
      if (var1.getglobal("sys").__getattr__("py3kwarning").__nonzero__()) {
         var1.setline(19);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(20);
            var3 = var1.getglobal("DeprecationWarning");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(21);
         var1.getglobal("warn").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)._add(Py.newInteger(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _show_warning$2(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyString.fromInterned("Hook to write a warning to a file; replace if you like.");
      var1.setline(25);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(26);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(4, var3);
         var3 = null;
      }

      try {
         var1.setline(28);
         var10000 = var1.getlocal(4).__getattr__("write");
         PyObject var10002 = var1.getglobal("formatwarning");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5)};
         var10000.__call__(var2, var10002.__call__(var2, var6));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("IOError"))) {
            throw var5;
         }

         var1.setline(30);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatwarning$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyString.fromInterned("Function to format a warning the standard way.");
      var1.setline(37);
      PyObject var3 = PyString.fromInterned("%s:%s: %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(1).__getattr__("__name__"), var1.getlocal(0)}));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(38);
      var1.setline(38);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000.__nonzero__() ? var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(2), var1.getlocal(3)) : var1.getlocal(4);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(39);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(40);
         var3 = var1.getlocal(4).__getattr__("strip").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(41);
         var3 = var1.getlocal(5);
         var3 = var3._iadd(PyString.fromInterned("  %s\n")._mod(var1.getlocal(4)));
         var1.setlocal(5, var3);
      }

      var1.setline(42);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject filterwarnings$4(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Insert an entry into the list of warnings filters (at the front).\n\n    'action' -- one of \"error\", \"ignore\", \"always\", \"default\", \"module\",\n                or \"once\"\n    'message' -- a regex that the warning message must match\n    'category' -- a class that the warning must be a subclass of\n    'module' -- a regex that the module name must match\n    'lineno' -- an integer line number, 0 matches all warnings\n    'append' -- if true, append to the list of filters\n    ");
      var1.setline(56);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(57);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("error"), PyString.fromInterned("ignore"), PyString.fromInterned("always"), PyString.fromInterned("default"), PyString.fromInterned("module"), PyString.fromInterned("once")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("invalid action: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
         }
      }

      var1.setline(59);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("message must be a string"));
      } else {
         var1.setline(60);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type"), var1.getglobal("types").__getattr__("ClassType")}))).__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("category must be a class"));
         } else {
            var1.setline(62);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("issubclass").__call__(var2, var1.getlocal(2), var1.getglobal("Warning")).__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("category must be a Warning subclass"));
            } else {
               var1.setline(63);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("module must be a string"));
               } else {
                  var1.setline(64);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("int"));
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(4);
                        var10000 = var3._ge(Py.newInteger(0));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("lineno must be an int >= 0"));
                     }
                  }

                  var1.setline(66);
                  PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(6).__getattr__("compile").__call__(var2, var1.getlocal(1), var1.getlocal(6).__getattr__("I")), var1.getlocal(2), var1.getlocal(6).__getattr__("compile").__call__(var2, var1.getlocal(3)), var1.getlocal(4)});
                  var1.setlocal(7, var4);
                  var3 = null;
                  var1.setline(68);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(69);
                     var1.getglobal("filters").__getattr__("append").__call__(var2, var1.getlocal(7));
                  } else {
                     var1.setline(71);
                     var1.getglobal("filters").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(7));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject simplefilter$5(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Insert a simple entry into the list of warnings filters (at the front).\n\n    A simple filter matches all modules and messages.\n    'action' -- one of \"error\", \"ignore\", \"always\", \"default\", \"module\",\n                or \"once\"\n    'category' -- a class that the warning must be a subclass of\n    'lineno' -- an integer line number, 0 matches all warnings\n    'append' -- if true, append to the list of filters\n    ");
      var1.setline(83);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("error"), PyString.fromInterned("ignore"), PyString.fromInterned("always"), PyString.fromInterned("default"), PyString.fromInterned("module"), PyString.fromInterned("once")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("invalid action: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
         }
      }

      var1.setline(85);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("int"));
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("lineno must be an int >= 0"));
         }
      }

      var1.setline(87);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("None"), var1.getlocal(1), var1.getglobal("None"), var1.getlocal(2)});
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(88);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(89);
         var1.getglobal("filters").__getattr__("append").__call__(var2, var1.getlocal(4));
      } else {
         var1.setline(91);
         var1.getglobal("filters").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resetwarnings$6(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Clear the list of warning filters, so that no filters are active.");
      var1.setline(95);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getglobal("filters").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _OptionError$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception used by option processing helpers."));
      var1.setline(98);
      PyString.fromInterned("Exception used by option processing helpers.");
      var1.setline(99);
      return var1.getf_locals();
   }

   public PyObject _processoptions$8(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(103);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(105);
            var1.getglobal("_setoption").__call__(var2, var1.getlocal(1));
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getglobal("_OptionError"))) {
               throw var5;
            }

            PyObject var6 = var5.value;
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(107);
            var6 = var1.getglobal("sys").__getattr__("stderr");
            Py.printComma(var6, PyString.fromInterned("Invalid -W option ignored:"));
            Py.println(var6, var1.getlocal(2));
         }
      }
   }

   public PyObject _setoption$9(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(5));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("too many fields (max 5): %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
      } else {
         while(true) {
            var1.setline(115);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._lt(Py.newInteger(5));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(117);
               PyList var12 = new PyList();
               var3 = var12.__getattr__("append");
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(118);
               var3 = var1.getlocal(2).__iter__();

               while(true) {
                  var1.setline(118);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(118);
                     var1.dellocal(8);
                     PyList var8 = var12;
                     PyObject[] var7 = Py.unpackSequence(var8, 5);
                     PyObject var5 = var7[0];
                     var1.setlocal(3, var5);
                     var5 = null;
                     var5 = var7[1];
                     var1.setlocal(4, var5);
                     var5 = null;
                     var5 = var7[2];
                     var1.setlocal(5, var5);
                     var5 = null;
                     var5 = var7[3];
                     var1.setlocal(6, var5);
                     var5 = null;
                     var5 = var7[4];
                     var1.setlocal(7, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(119);
                     var3 = var1.getglobal("_getaction").__call__(var2, var1.getlocal(3));
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(120);
                     var3 = var1.getlocal(1).__getattr__("escape").__call__(var2, var1.getlocal(4));
                     var1.setlocal(4, var3);
                     var3 = null;
                     var1.setline(121);
                     var3 = var1.getglobal("_getcategory").__call__(var2, var1.getlocal(5));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(122);
                     var3 = var1.getlocal(1).__getattr__("escape").__call__(var2, var1.getlocal(6));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(123);
                     if (var1.getlocal(6).__nonzero__()) {
                        var1.setline(124);
                        var3 = var1.getlocal(6)._add(PyString.fromInterned("$"));
                        var1.setlocal(6, var3);
                        var3 = null;
                     }

                     var1.setline(125);
                     if (var1.getlocal(7).__nonzero__()) {
                        try {
                           var1.setline(127);
                           var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
                           var1.setlocal(7, var3);
                           var3 = null;
                           var1.setline(128);
                           var3 = var1.getlocal(7);
                           var10000 = var3._lt(Py.newInteger(0));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(129);
                              throw Py.makeException(var1.getglobal("ValueError"));
                           }
                        } catch (Throwable var6) {
                           PyException var9 = Py.setException(var6, var1);
                           if (var9.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("OverflowError")}))) {
                              var1.setline(131);
                              throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("invalid lineno %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(7)}))));
                           }

                           throw var9;
                        }
                     } else {
                        var1.setline(133);
                        PyInteger var10 = Py.newInteger(0);
                        var1.setlocal(7, var10);
                        var3 = null;
                     }

                     var1.setline(134);
                     var10000 = var1.getglobal("filterwarnings");
                     PyObject[] var11 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
                     var10000.__call__(var2, var11);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(9, var4);
                  var1.setline(117);
                  var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("strip").__call__(var2));
               }
            }

            var1.setline(116);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
         }
      }
   }

   public PyObject _getaction$10(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString var6;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(139);
         var6 = PyString.fromInterned("default");
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(140);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._eq(PyString.fromInterned("all"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(140);
            var6 = PyString.fromInterned("always");
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(141);
            var4 = (new PyTuple(new PyObject[]{PyString.fromInterned("default"), PyString.fromInterned("always"), PyString.fromInterned("ignore"), PyString.fromInterned("module"), PyString.fromInterned("once"), PyString.fromInterned("error")})).__iter__();

            do {
               var1.setline(141);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(144);
                  throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("invalid action: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
               }

               var1.setlocal(1, var5);
               var1.setline(142);
            } while(!var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(0)).__nonzero__());

            var1.setline(143);
            PyObject var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _getcategory$11(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(149);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(150);
         var3 = var1.getglobal("Warning");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(151);
         PyException var4;
         PyObject var8;
         if (var1.getlocal(1).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[a-zA-Z0-9_]+$"), (PyObject)var1.getlocal(0)).__nonzero__()) {
            try {
               var1.setline(153);
               var8 = var1.getglobal("eval").__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var8);
               var4 = null;
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (var4.match(var1.getglobal("NameError"))) {
                  var1.setline(155);
                  throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("unknown warning category: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
               }

               throw var4;
            }
         } else {
            var1.setline(157);
            var8 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setlocal(3, var8);
            var4 = null;
            var1.setline(158);
            var8 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(159);
            var8 = var1.getlocal(0).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(5, var8);
            var4 = null;

            try {
               var1.setline(161);
               var8 = var1.getglobal("__import__").__call__(var2, var1.getlocal(4), var1.getglobal("None"), var1.getglobal("None"), new PyList(new PyObject[]{var1.getlocal(5)}));
               var1.setlocal(6, var8);
               var4 = null;
            } catch (Throwable var6) {
               var4 = Py.setException(var6, var1);
               if (var4.match(var1.getglobal("ImportError"))) {
                  var1.setline(163);
                  throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("invalid module name: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)}))));
               }

               throw var4;
            }

            try {
               var1.setline(165);
               var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(5));
               var1.setlocal(2, var8);
               var4 = null;
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (var4.match(var1.getglobal("AttributeError"))) {
                  var1.setline(167);
                  throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("unknown warning category: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
               }

               throw var4;
            }
         }

         var1.setline(168);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(2), var1.getglobal("Warning")).__not__().__nonzero__()) {
            var1.setline(169);
            throw Py.makeException(var1.getglobal("_OptionError").__call__(var2, PyString.fromInterned("invalid warning category: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)}))));
         } else {
            var1.setline(170);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject SysGlobals$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("sys.__dict__ values are reflectedfields, so we use this."));
      var1.setline(173);
      PyString.fromInterned("sys.__dict__ values are reflectedfields, so we use this.");
      var1.setline(174);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getitem__$13, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(180);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$14, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(185);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$15, (PyObject)null);
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$16, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getitem__$13(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(176);
         PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getglobal("sys"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(178);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         } else {
            throw var4;
         }
      }
   }

   public PyObject get$14(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(182);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(183);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setdefault$15(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(187);
         var3 = var1.getlocal(2);
         var1.getglobal("sys").__getattr__("__dict__").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(188);
      var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$16(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("sys").__getattr__("__dict__"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject warn$17(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyString.fromInterned("Issue a warning, or maybe ignore it or raise an exception.");
      var1.setline(197);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Warning")).__nonzero__()) {
         var1.setline(198);
         var3 = var1.getlocal(0).__getattr__("__class__");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(200);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(201);
         var3 = var1.getglobal("UserWarning");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(202);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getglobal("Warning")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         PyObject var4;
         PyException var8;
         label63: {
            try {
               var1.setline(205);
               var3 = var1.getglobal("sys").__getattr__("_getframe").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
            } catch (Throwable var6) {
               var8 = Py.setException(var6, var1);
               if (var8.match(var1.getglobal("ValueError"))) {
                  var1.setline(207);
                  var4 = var1.getglobal("SysGlobals").__call__(var2);
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(208);
                  PyInteger var7 = Py.newInteger(1);
                  var1.setlocal(5, var7);
                  var4 = null;
                  break label63;
               }

               throw var8;
            }

            var1.setline(210);
            var4 = var1.getlocal(3).__getattr__("f_globals");
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(211);
            var4 = var1.getlocal(3).__getattr__("f_lineno");
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(212);
         PyString var9 = PyString.fromInterned("__name__");
         var10000 = var9._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(213);
            var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("__name__"));
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(215);
            var9 = PyString.fromInterned("<string>");
            var1.setlocal(6, var9);
            var3 = null;
         }

         var1.setline(216);
         var3 = var1.getlocal(4).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__file__"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(217);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(218);
            var3 = var1.getlocal(7).__getattr__("lower").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(219);
            if (var1.getlocal(8).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}))).__nonzero__()) {
               var1.setline(220);
               var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(7, var3);
               var3 = null;
            } else {
               var1.setline(221);
               if (var1.getlocal(8).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py.class")).__nonzero__()) {
                  var1.setline(222);
                  var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(".py"));
                  var1.setlocal(7, var3);
                  var3 = null;
               }
            }
         } else {
            var1.setline(224);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(PyString.fromInterned("__main__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(226);
                  var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0));
                  var1.setlocal(7, var3);
                  var3 = null;
               } catch (Throwable var5) {
                  var8 = Py.setException(var5, var1);
                  if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("TypeError")}))) {
                     throw var8;
                  }

                  var1.setline(229);
                  PyString var10 = PyString.fromInterned("__main__");
                  var1.setlocal(7, var10);
                  var4 = null;
               }
            }

            var1.setline(230);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(231);
               var3 = var1.getlocal(6);
               var1.setlocal(7, var3);
               var3 = null;
            }
         }

         var1.setline(232);
         var3 = var1.getlocal(4).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__warningregistry__"), (PyObject)(new PyDictionary(Py.EmptyObjects)));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(233);
         var10000 = var1.getglobal("warn_explicit");
         PyObject[] var11 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(7), var1.getlocal(5), var1.getlocal(6), var1.getlocal(9), var1.getlocal(4)};
         var10000.__call__(var2, var11);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject warn_explicit$18(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(239);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         Object var12 = var1.getlocal(2);
         if (!((PyObject)var12).__nonzero__()) {
            var12 = PyString.fromInterned("<unknown>");
         }

         Object var8 = var12;
         var1.setlocal(4, (PyObject)var8);
         var3 = null;
         var1.setline(241);
         var3 = var1.getlocal(4).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned(".py"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(242);
            var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
         }
      }

      var1.setline(243);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(244);
         PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(5, var9);
         var3 = null;
      }

      var1.setline(245);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("Warning")).__nonzero__()) {
         var1.setline(246);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(247);
         var3 = var1.getlocal(0).__getattr__("__class__");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(249);
         var3 = var1.getlocal(0);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(250);
         var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(251);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1), var1.getlocal(3)});
      var1.setlocal(8, var10);
      var3 = null;
      var1.setline(253);
      if (var1.getlocal(5).__getattr__("get").__call__(var2, var1.getlocal(8)).__nonzero__()) {
         var1.setline(254);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(256);
         var3 = var1.getglobal("globals").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filters"), (PyObject)var1.getglobal("_filters")).__iter__();

         do {
            var1.setline(256);
            PyObject var4 = var3.__iternext__();
            PyObject var5;
            if (var4 == null) {
               var1.setline(264);
               var5 = var1.getglobal("globals").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("defaultaction"), (PyObject)var1.getglobal("default_action"));
               var1.setlocal(10, var5);
               var5 = null;
               break;
            }

            var1.setlocal(9, var4);
            var1.setline(257);
            var5 = var1.getlocal(9);
            PyObject[] var6 = Py.unpackSequence(var5, 5);
            PyObject var7 = var6[0];
            var1.setlocal(10, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(11, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(12, var7);
            var7 = null;
            var7 = var6[3];
            var1.setlocal(13, var7);
            var7 = null;
            var7 = var6[4];
            var1.setlocal(14, var7);
            var7 = null;
            var5 = null;
            var1.setline(258);
            var5 = var1.getlocal(11);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(11).__getattr__("match").__call__(var2, var1.getlocal(7));
            }

            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getlocal(12));
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(13);
                  var10000 = var5._is(var1.getglobal("None"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(13).__getattr__("match").__call__(var2, var1.getlocal(4));
                  }

                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(14);
                     var10000 = var5._eq(Py.newInteger(0));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var5 = var1.getlocal(3);
                        var10000 = var5._eq(var1.getlocal(14));
                        var5 = null;
                     }
                  }
               }
            }
         } while(!var10000.__nonzero__());

         var1.setline(266);
         var3 = var1.getlocal(10);
         var10000 = var3._eq(PyString.fromInterned("ignore"));
         var3 = null;
         PyInteger var11;
         if (var10000.__nonzero__()) {
            var1.setline(267);
            var11 = Py.newInteger(1);
            var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var11);
            var3 = null;
            var1.setline(268);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(272);
            var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(2), var1.getlocal(6));
            var1.setline(274);
            var3 = var1.getlocal(10);
            var10000 = var3._eq(PyString.fromInterned("error"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(275);
               throw Py.makeException(var1.getlocal(0));
            } else {
               var1.setline(277);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(PyString.fromInterned("once"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(278);
                  var3 = var1.getglobal("globals").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("onceregistry"), (PyObject)var1.getglobal("once_registry"));
                  var1.setlocal(15, var3);
                  var3 = null;
                  var1.setline(279);
                  var11 = Py.newInteger(1);
                  var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var11);
                  var3 = null;
                  var1.setline(280);
                  var10 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1)});
                  var1.setlocal(16, var10);
                  var3 = null;
                  var1.setline(281);
                  if (var1.getlocal(15).__getattr__("get").__call__(var2, var1.getlocal(16)).__nonzero__()) {
                     var1.setline(282);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(283);
                  var11 = Py.newInteger(1);
                  var1.getlocal(15).__setitem__((PyObject)var1.getlocal(16), var11);
                  var3 = null;
               } else {
                  var1.setline(284);
                  var3 = var1.getlocal(10);
                  var10000 = var3._eq(PyString.fromInterned("always"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(285);
                  } else {
                     var1.setline(286);
                     var3 = var1.getlocal(10);
                     var10000 = var3._eq(PyString.fromInterned("module"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(287);
                        var11 = Py.newInteger(1);
                        var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var11);
                        var3 = null;
                        var1.setline(288);
                        var10 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1), Py.newInteger(0)});
                        var1.setlocal(17, var10);
                        var3 = null;
                        var1.setline(289);
                        if (var1.getlocal(5).__getattr__("get").__call__(var2, var1.getlocal(17)).__nonzero__()) {
                           var1.setline(290);
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setline(291);
                        var11 = Py.newInteger(1);
                        var1.getlocal(5).__setitem__((PyObject)var1.getlocal(17), var11);
                        var3 = null;
                     } else {
                        var1.setline(292);
                        var3 = var1.getlocal(10);
                        var10000 = var3._eq(PyString.fromInterned("default"));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(296);
                           throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, PyString.fromInterned("Unrecognized action (%r) in warnings.filters:\n %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(9)}))));
                        }

                        var1.setline(293);
                        var11 = Py.newInteger(1);
                        var1.getlocal(5).__setitem__((PyObject)var1.getlocal(8), var11);
                        var3 = null;
                     }
                  }
               }

               var1.setline(300);
               var3 = var1.getglobal("globals").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("showwarning"), (PyObject)var1.getglobal("_show_warning"));
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(301);
               var1.getlocal(18).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject WarningMessage$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Holds the result of a single showwarning() call."));
      var1.setline(306);
      PyString.fromInterned("Holds the result of a single showwarning() call.");
      var1.setline(308);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("message"), PyString.fromInterned("category"), PyString.fromInterned("filename"), PyString.fromInterned("lineno"), PyString.fromInterned("file"), PyString.fromInterned("line")});
      var1.setlocal("_WARNING_DETAILS", var3);
      var3 = null;
      var1.setline(311);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(318);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$21, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyObject var3 = var1.getglobal("locals").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(314);
      var3 = var1.getlocal(0).__getattr__("_WARNING_DETAILS").__iter__();

      while(true) {
         var1.setline(314);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(316);
            var1.setline(316);
            var3 = var1.getlocal(2).__nonzero__() ? var1.getlocal(2).__getattr__("__name__") : var1.getglobal("None");
            var1.getlocal(0).__setattr__("_category_name", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(8, var4);
         var1.setline(315);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(8), var1.getlocal(7).__getitem__(var1.getlocal(8)));
      }
   }

   public PyObject __str__$21(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyObject var3 = PyString.fromInterned("{message : %r, category : %r, filename : %r, lineno : %s, line : %r}")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("message"), var1.getlocal(0).__getattr__("_category_name"), var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("lineno"), var1.getlocal(0).__getattr__("line")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject catch_warnings$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A context manager that copies and restores the warnings filter upon\n    exiting the context.\n\n    The 'record' argument specifies whether warnings should be captured by a\n    custom implementation of warnings.showwarning() and be appended to a list\n    returned by the context manager. Otherwise None is returned by the context\n    manager. The objects appended to the list are arguments whose attributes\n    mirror the arguments to showwarning().\n\n    The 'module' argument is to specify an alternative module to the module\n    named 'warnings' and imported under that name. This argument is only useful\n    when testing the warnings module itself.\n\n    "));
      var1.setline(339);
      PyString.fromInterned("A context manager that copies and restores the warnings filter upon\n    exiting the context.\n\n    The 'record' argument specifies whether warnings should be captured by a\n    custom implementation of warnings.showwarning() and be appended to a list\n    returned by the context manager. Otherwise None is returned by the context\n    manager. The objects appended to the list are arguments whose attributes\n    mirror the arguments to showwarning().\n\n    The 'module' argument is to specify an alternative module to the module\n    named 'warnings' and imported under that name. This argument is only useful\n    when testing the warnings module itself.\n\n    ");
      var1.setline(341);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, PyString.fromInterned("Specify whether to record warnings and if an alternative module\n        should be used other than sys.modules['warnings'].\n\n        For compatibility with Python 3.0, please consider all arguments to be\n        keyword-only.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$24, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(362);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$25, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$27, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Specify whether to record warnings and if an alternative module\n        should be used other than sys.modules['warnings'].\n\n        For compatibility with Python 3.0, please consider all arguments to be\n        keyword-only.\n\n        ");
      var1.setline(349);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_record", var3);
      var3 = null;
      var1.setline(350);
      var1.setline(350);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000.__nonzero__() ? var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("warnings")) : var1.getlocal(2);
      var1.getlocal(0).__setattr__("_module", var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_entered", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$24(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(355);
      if (var1.getlocal(0).__getattr__("_record").__nonzero__()) {
         var1.setline(356);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("record=True"));
      }

      var1.setline(357);
      PyObject var4 = var1.getlocal(0).__getattr__("_module");
      PyObject var10000 = var4._isnot(var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("warnings")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(358);
         var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("module=%r")._mod(var1.getlocal(0).__getattr__("_module")));
      }

      var1.setline(359);
      var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(360);
      var4 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))}));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __enter__$25(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      if (var1.getlocal(0).__getattr__("_entered").__nonzero__()) {
         var1.setline(364);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, PyString.fromInterned("Cannot enter %r twice")._mod(var1.getlocal(0))));
      } else {
         var1.setline(365);
         PyObject var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_entered", var3);
         var3 = null;
         var1.setline(366);
         var3 = var1.getlocal(0).__getattr__("_module").__getattr__("filters");
         var1.getlocal(0).__setattr__("_filters", var3);
         var3 = null;
         var1.setline(367);
         var3 = var1.getlocal(0).__getattr__("_filters").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.getlocal(0).__getattr__("_module").__setattr__("filters", var3);
         var1.getlocal(0).__getattr__("_module").__setattr__("_filters", var3);
         var1.setline(368);
         var3 = var1.getlocal(0).__getattr__("_module").__getattr__("showwarning");
         var1.getlocal(0).__setattr__("_showwarning", var3);
         var3 = null;
         var1.setline(369);
         if (var1.getlocal(0).__getattr__("_record").__nonzero__()) {
            var1.setline(370);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.setderef(0, var4);
            var3 = null;
            var1.setline(371);
            PyObject[] var5 = Py.EmptyObjects;
            PyObject var10002 = var1.f_globals;
            PyObject[] var10003 = var5;
            PyCode var10004 = showwarning$26;
            var5 = new PyObject[]{var1.getclosure(0)};
            PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
            var1.setlocal(1, var6);
            var3 = null;
            var1.setline(373);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__getattr__("_module").__setattr__("showwarning", var3);
            var3 = null;
            var1.setline(374);
            var3 = var1.getderef(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(376);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject showwarning$26(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var10000 = var1.getderef(0).__getattr__("append");
      PyObject var10002 = var1.getglobal("WarningMessage");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __exit__$27(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      if (var1.getlocal(0).__getattr__("_entered").__not__().__nonzero__()) {
         var1.setline(380);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, PyString.fromInterned("Cannot exit %r without entering first")._mod(var1.getlocal(0))));
      } else {
         var1.setline(381);
         PyObject var3 = var1.getlocal(0).__getattr__("_filters");
         var1.getlocal(0).__getattr__("_module").__setattr__("filters", var3);
         var1.getlocal(0).__getattr__("_module").__setattr__("_filters", var3);
         var1.setline(382);
         var3 = var1.getlocal(0).__getattr__("_showwarning");
         var1.getlocal(0).__getattr__("_module").__setattr__("showwarning", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public warnings$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"message", "category", "stacklevel"};
      warnpy3k$1 = Py.newCode(3, var2, var1, "warnpy3k", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message", "category", "filename", "lineno", "file", "line"};
      _show_warning$2 = Py.newCode(6, var2, var1, "_show_warning", 23, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message", "category", "filename", "lineno", "line", "s"};
      formatwarning$3 = Py.newCode(5, var2, var1, "formatwarning", 35, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"action", "message", "category", "module", "lineno", "append", "re", "item"};
      filterwarnings$4 = Py.newCode(6, var2, var1, "filterwarnings", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"action", "category", "lineno", "append", "item"};
      simplefilter$5 = Py.newCode(4, var2, var1, "simplefilter", 73, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      resetwarnings$6 = Py.newCode(0, var2, var1, "resetwarnings", 93, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _OptionError$7 = Py.newCode(0, var2, var1, "_OptionError", 97, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args", "arg", "msg"};
      _processoptions$8 = Py.newCode(1, var2, var1, "_processoptions", 102, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"arg", "re", "parts", "action", "message", "category", "module", "lineno", "_[117_49]", "s"};
      _setoption$9 = Py.newCode(1, var2, var1, "_setoption", 110, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"action", "a"};
      _getaction$10 = Py.newCode(1, var2, var1, "_getaction", 137, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"category", "re", "cat", "i", "module", "klass", "m"};
      _getcategory$11 = Py.newCode(1, var2, var1, "_getcategory", 147, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SysGlobals$12 = Py.newCode(0, var2, var1, "SysGlobals", 172, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key"};
      __getitem__$13 = Py.newCode(2, var2, var1, "__getitem__", 174, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$14 = Py.newCode(3, var2, var1, "get", 180, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      setdefault$15 = Py.newCode(3, var2, var1, "setdefault", 185, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$16 = Py.newCode(2, var2, var1, "__contains__", 190, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message", "category", "stacklevel", "caller", "globals", "lineno", "module", "filename", "fnl", "registry"};
      warn$17 = Py.newCode(3, var2, var1, "warn", 194, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message", "category", "filename", "lineno", "module", "registry", "module_globals", "text", "key", "item", "action", "msg", "cat", "mod", "ln", "_onceregistry", "oncekey", "altkey", "fn"};
      warn_explicit$18 = Py.newCode(7, var2, var1, "warn_explicit", 236, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WarningMessage$19 = Py.newCode(0, var2, var1, "WarningMessage", 304, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message", "category", "filename", "lineno", "file", "line", "local_values", "attr"};
      __init__$20 = Py.newCode(7, var2, var1, "__init__", 311, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$21 = Py.newCode(1, var2, var1, "__str__", 318, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      catch_warnings$22 = Py.newCode(0, var2, var1, "catch_warnings", 324, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "record", "module"};
      __init__$23 = Py.newCode(3, var2, var1, "__init__", 341, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "name"};
      __repr__$24 = Py.newCode(1, var2, var1, "__repr__", 353, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "showwarning", "log"};
      String[] var10001 = var2;
      warnings$py var10007 = self;
      var2 = new String[]{"log"};
      __enter__$25 = Py.newCode(1, var10001, var1, "__enter__", 362, false, false, var10007, 25, var2, (String[])null, 1, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"log"};
      showwarning$26 = Py.newCode(2, var10001, var1, "showwarning", 371, true, true, var10007, 26, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "exc_info"};
      __exit__$27 = Py.newCode(2, var2, var1, "__exit__", 378, true, false, self, 27, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new warnings$py("warnings$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(warnings$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.warnpy3k$1(var2, var3);
         case 2:
            return this._show_warning$2(var2, var3);
         case 3:
            return this.formatwarning$3(var2, var3);
         case 4:
            return this.filterwarnings$4(var2, var3);
         case 5:
            return this.simplefilter$5(var2, var3);
         case 6:
            return this.resetwarnings$6(var2, var3);
         case 7:
            return this._OptionError$7(var2, var3);
         case 8:
            return this._processoptions$8(var2, var3);
         case 9:
            return this._setoption$9(var2, var3);
         case 10:
            return this._getaction$10(var2, var3);
         case 11:
            return this._getcategory$11(var2, var3);
         case 12:
            return this.SysGlobals$12(var2, var3);
         case 13:
            return this.__getitem__$13(var2, var3);
         case 14:
            return this.get$14(var2, var3);
         case 15:
            return this.setdefault$15(var2, var3);
         case 16:
            return this.__contains__$16(var2, var3);
         case 17:
            return this.warn$17(var2, var3);
         case 18:
            return this.warn_explicit$18(var2, var3);
         case 19:
            return this.WarningMessage$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__str__$21(var2, var3);
         case 22:
            return this.catch_warnings$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.__repr__$24(var2, var3);
         case 25:
            return this.__enter__$25(var2, var3);
         case 26:
            return this.showwarning$26(var2, var3);
         case 27:
            return this.__exit__$27(var2, var3);
         default:
            return null;
      }
   }
}
