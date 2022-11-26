import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@MTime(1498849384000L)
@Filename("ensurepip/__init__.py")
public class ensurepip$py extends PyFunctionTable implements PyRunnable {
   static ensurepip$py self;
   static final PyCode f$0;
   static final PyCode _require_ssl_for_pip$1;
   static final PyCode _require_ssl_for_pip$2;
   static final PyCode _run_pip$3;
   static final PyCode version$4;
   static final PyCode _disable_pip_configuration_settings$5;
   static final PyCode bootstrap$6;
   static final PyCode _uninstall_helper$7;
   static final PyCode _main$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(2);
      String[] var3 = new String[]{"print_function"};
      PyObject[] var6 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("print_function", var4);
      var4 = null;
      var1.setline(4);
      PyObject var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(5);
      var7 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(6);
      var7 = imp.importOne("pkgutil", var1, -1);
      var1.setlocal("pkgutil", var7);
      var3 = null;
      var1.setline(7);
      var7 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var7);
      var3 = null;
      var1.setline(8);
      var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(9);
      var7 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var7);
      var3 = null;
      var1.setline(12);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("bootstrap")});
      var1.setlocal("__all__", var10);
      var3 = null;
      var1.setline(15);
      PyString var11 = PyString.fromInterned("28.8.0");
      var1.setlocal("_SETUPTOOLS_VERSION", var11);
      var3 = null;
      var1.setline(17);
      var11 = PyString.fromInterned("9.0.1");
      var1.setlocal("_PIP_VERSION", var11);
      var3 = null;
      var1.setline(21);
      var7 = PyString.fromInterned("pip {} requires SSL/TLS").__getattr__("format").__call__(var2, var1.getname("_PIP_VERSION"));
      var1.setlocal("_MISSING_SSL_MESSAGE", var7);
      var3 = null;

      label19: {
         PyObject[] var8;
         PyFunction var9;
         try {
            var1.setline(23);
            var7 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var7);
            var3 = null;
         } catch (Throwable var5) {
            PyException var12 = Py.setException(var5, var1);
            if (var12.match(var1.getname("ImportError"))) {
               var1.setline(25);
               var4 = var1.getname("None");
               var1.setlocal("ssl", var4);
               var4 = null;
               var1.setline(27);
               var8 = Py.EmptyObjects;
               var9 = new PyFunction(var1.f_globals, var8, _require_ssl_for_pip$1, (PyObject)null);
               var1.setlocal("_require_ssl_for_pip", var9);
               var4 = null;
               break label19;
            }

            throw var12;
         }

         var1.setline(30);
         var8 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var8, _require_ssl_for_pip$2, (PyObject)null);
         var1.setlocal("_require_ssl_for_pip", var9);
         var4 = null;
      }

      var1.setline(33);
      var10 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("setuptools"), var1.getname("_SETUPTOOLS_VERSION")}), new PyTuple(new PyObject[]{PyString.fromInterned("pip"), var1.getname("_PIP_VERSION")})});
      var1.setlocal("_PROJECTS", var10);
      var3 = null;
      var1.setline(39);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var13 = new PyFunction(var1.f_globals, var6, _run_pip$3, (PyObject)null);
      var1.setlocal("_run_pip", var13);
      var3 = null;
      var1.setline(49);
      var6 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var6, version$4, PyString.fromInterned("\n    Returns a string specifying the bundled version of pip.\n    "));
      var1.setlocal("version", var13);
      var3 = null;
      var1.setline(56);
      var6 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var6, _disable_pip_configuration_settings$5, (PyObject)null);
      var1.setlocal("_disable_pip_configuration_settings", var13);
      var3 = null;
      var1.setline(68);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False"), var1.getname("False"), var1.getname("True"), Py.newInteger(0)};
      var13 = new PyFunction(var1.f_globals, var6, bootstrap$6, PyString.fromInterned("\n    Bootstrap pip into the current Python installation (or the given root\n    directory).\n\n    Note that calling this function will alter both sys.path and os.environ.\n    "));
      var1.setlocal("bootstrap", var13);
      var3 = null;
      var1.setline(128);
      var6 = new PyObject[]{Py.newInteger(0)};
      var13 = new PyFunction(var1.f_globals, var6, _uninstall_helper$7, PyString.fromInterned("Helper to support a clean default uninstall process on Windows\n\n    Note that calling this function may alter os.environ.\n    "));
      var1.setlocal("_uninstall_helper", var13);
      var3 = null;
      var1.setline(157);
      var6 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var6, _main$8, (PyObject)null);
      var1.setlocal("_main", var13);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _require_ssl_for_pip$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2, var1.getglobal("_MISSING_SSL_MESSAGE")));
   }

   public PyObject _require_ssl_for_pip$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _run_pip$3(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         var3 = var1.getlocal(1)._add(var1.getglobal("sys").__getattr__("path"));
         var1.getglobal("sys").__setattr__("path", var3);
         var3 = null;
      }

      var1.setline(45);
      var3 = imp.importOne("pip", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(46);
      var1.getlocal(2).__getattr__("main").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject version$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("\n    Returns a string specifying the bundled version of pip.\n    ");
      var1.setline(53);
      PyObject var3 = var1.getglobal("_PIP_VERSION");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _disable_pip_configuration_settings$5(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("os").__getattr__("environ").__iter__();

      while(true) {
         var1.setline(60);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(60);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.setlocal(0, var5);
            var3 = null;
            var1.setline(61);
            var3 = var1.getlocal(0).__iter__();

            while(true) {
               var1.setline(61);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(65);
                  var3 = var1.getglobal("os").__getattr__("devnull");
                  var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("PIP_CONFIG_FILE"), var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(2, var4);
               var1.setline(62);
               var1.getglobal("os").__getattr__("environ").__delitem__(var1.getlocal(2));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(60);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PIP_")).__nonzero__()) {
            var1.setline(60);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject bootstrap$6(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(76);
      PyString.fromInterned("\n    Bootstrap pip into the current Python installation (or the given root\n    directory).\n\n    Note that calling this function will alter both sys.path and os.environ.\n    ");
      var1.setline(77);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(4);
      }

      if (var10000.__nonzero__()) {
         var1.setline(78);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot use altinstall and default_pip together")));
      } else {
         var1.setline(80);
         var1.getglobal("_require_ssl_for_pip").__call__(var2);
         var1.setline(81);
         var1.getglobal("_disable_pip_configuration_settings").__call__(var2);
         var1.setline(89);
         PyString var3;
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(91);
            var3 = PyString.fromInterned("altinstall");
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("ENSUREPIP_OPTIONS"), var3);
            var3 = null;
         } else {
            var1.setline(92);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(94);
               var3 = PyString.fromInterned("install");
               var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("ENSUREPIP_OPTIONS"), var3);
               var3 = null;
            }
         }

         var1.setline(96);
         PyObject var10 = var1.getglobal("tempfile").__getattr__("mkdtemp").__call__(var2);
         var1.setlocal(6, var10);
         var3 = null;
         var3 = null;

         PyObject[] var4;
         String[] var5;
         try {
            var1.setline(100);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(7, var11);
            var4 = null;
            var1.setline(101);
            PyObject var12 = var1.getglobal("_PROJECTS").__iter__();

            label66:
            while(true) {
               var1.setline(101);
               PyObject var13 = var12.__iternext__();
               if (var13 == null) {
                  var1.setline(113);
                  var11 = new PyList(new PyObject[]{PyString.fromInterned("install"), PyString.fromInterned("--no-index"), PyString.fromInterned("--find-links"), var1.getlocal(6)});
                  var1.setlocal(13, var11);
                  var4 = null;
                  var1.setline(114);
                  if (var1.getlocal(0).__nonzero__()) {
                     var1.setline(115);
                     var12 = var1.getlocal(13);
                     var12 = var12._iadd(new PyList(new PyObject[]{PyString.fromInterned("--root"), var1.getlocal(0)}));
                     var1.setlocal(13, var12);
                  }

                  var1.setline(116);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(117);
                     var12 = var1.getlocal(13);
                     var12 = var12._iadd(new PyList(new PyObject[]{PyString.fromInterned("--upgrade")}));
                     var1.setlocal(13, var12);
                  }

                  var1.setline(118);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(119);
                     var12 = var1.getlocal(13);
                     var12 = var12._iadd(new PyList(new PyObject[]{PyString.fromInterned("--user")}));
                     var1.setlocal(13, var12);
                  }

                  var1.setline(120);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(121);
                     var12 = var1.getlocal(13);
                     var12 = var12._iadd(new PyList(new PyObject[]{PyString.fromInterned("-")._add(PyString.fromInterned("v")._mul(var1.getlocal(5)))}));
                     var1.setlocal(13, var12);
                  }

                  var1.setline(123);
                  var10000 = var1.getglobal("_run_pip");
                  PyObject var10002 = var1.getlocal(13);
                  PyList var10003 = new PyList();
                  var12 = var10003.__getattr__("append");
                  var1.setlocal(14, var12);
                  var4 = null;
                  var1.setline(123);
                  var12 = var1.getglobal("_PROJECTS").__iter__();

                  while(true) {
                     var1.setline(123);
                     var13 = var12.__iternext__();
                     if (var13 == null) {
                        var1.setline(123);
                        var1.dellocal(14);
                        var10000.__call__(var2, var10002._add(var10003), var1.getlocal(7));
                        break label66;
                     }

                     var1.setlocal(15, var13);
                     var1.setline(123);
                     var1.getlocal(14).__call__(var2, var1.getlocal(15).__getitem__(Py.newInteger(0)));
                  }
               }

               PyObject[] var6 = Py.unpackSequence(var13, 2);
               PyObject var7 = var6[0];
               var1.setlocal(8, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(9, var7);
               var7 = null;
               var1.setline(102);
               PyObject var14 = PyString.fromInterned("{}-{}-py2.py3-none-any.whl").__getattr__("format").__call__(var2, var1.getlocal(8), var1.getlocal(9));
               var1.setlocal(10, var14);
               var6 = null;
               var1.setline(103);
               var14 = var1.getglobal("pkgutil").__getattr__("get_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ensurepip"), (PyObject)PyString.fromInterned("_bundled/{}").__getattr__("format").__call__(var2, var1.getlocal(10)));
               var1.setlocal(11, var14);
               var6 = null;
               ContextManager var15;
               var7 = (var15 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(10)), (PyObject)PyString.fromInterned("wb")))).__enter__(var2);

               label63: {
                  try {
                     var1.setlocal(12, var7);
                     var1.setline(108);
                     var1.getlocal(12).__getattr__("write").__call__(var2, var1.getlocal(11));
                  } catch (Throwable var8) {
                     if (var15.__exit__(var2, Py.setException(var8, var1))) {
                        break label63;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var15.__exit__(var2, (PyException)null);
               }

               var1.setline(110);
               var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(10)));
            }
         } catch (Throwable var9) {
            Py.addTraceback(var9, var1);
            var1.setline(125);
            var10000 = var1.getglobal("shutil").__getattr__("rmtree");
            var4 = new PyObject[]{var1.getlocal(6), var1.getglobal("True")};
            var5 = new String[]{"ignore_errors"};
            var10000.__call__(var2, var4, var5);
            var4 = null;
            throw (Throwable)var9;
         }

         var1.setline(125);
         var10000 = var1.getglobal("shutil").__getattr__("rmtree");
         var4 = new PyObject[]{var1.getlocal(6), var1.getglobal("True")};
         var5 = new String[]{"ignore_errors"};
         var10000.__call__(var2, var4, var5);
         var4 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _uninstall_helper$7(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Helper to support a clean default uninstall process on Windows\n\n    Note that calling this function may alter os.environ.\n    ");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(135);
         var6 = imp.importOne("pip", var1, -1);
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(137);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(140);
      var6 = var1.getlocal(1).__getattr__("__version__");
      PyObject var10000 = var6._ne(var1.getglobal("_PIP_VERSION"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(141);
         PyString var9 = PyString.fromInterned("ensurepip will only uninstall a matching version ({!r} installed, {!r} bundled)");
         var1.setlocal(2, var9);
         var3 = null;
         var1.setline(143);
         var10000 = var1.getglobal("print");
         PyObject[] var10 = new PyObject[]{var1.getlocal(2).__getattr__("format").__call__(var2, var1.getlocal(1).__getattr__("__version__"), var1.getglobal("_PIP_VERSION")), var1.getglobal("sys").__getattr__("stderr")};
         String[] var7 = new String[]{"file"};
         var10000.__call__(var2, var10, var7);
         var3 = null;
         var1.setline(144);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(146);
         var1.getglobal("_require_ssl_for_pip").__call__(var2);
         var1.setline(147);
         var1.getglobal("_disable_pip_configuration_settings").__call__(var2);
         var1.setline(150);
         PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("uninstall"), PyString.fromInterned("-y")});
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(151);
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(152);
            var6 = var1.getlocal(3);
            var6 = var6._iadd(new PyList(new PyObject[]{PyString.fromInterned("-")._add(PyString.fromInterned("v")._mul(var1.getlocal(0)))}));
            var1.setlocal(3, var6);
         }

         var1.setline(154);
         var10000 = var1.getglobal("_run_pip");
         PyObject var10002 = var1.getlocal(3);
         PyList var10003 = new PyList();
         var6 = var10003.__getattr__("append");
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(154);
         var6 = var1.getglobal("reversed").__call__(var2, var1.getglobal("_PROJECTS")).__iter__();

         while(true) {
            var1.setline(154);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(154);
               var1.dellocal(4);
               var10000.__call__(var2, var10002._add(var10003));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(5, var4);
            var1.setline(154);
            var1.getlocal(4).__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(0)));
         }
      }
   }

   public PyObject _main$8(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getglobal("ssl");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(159);
         var10000 = var1.getglobal("print");
         var5 = new PyObject[]{PyString.fromInterned("Ignoring ensurepip failure: {}").__getattr__("format").__call__(var2, var1.getglobal("_MISSING_SSL_MESSAGE")), var1.getglobal("sys").__getattr__("stderr")};
         var4 = new String[]{"file"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(161);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(163);
         var3 = imp.importOne("argparse", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(164);
         var10000 = var1.getlocal(1).__getattr__("ArgumentParser");
         var5 = new PyObject[]{PyString.fromInterned("python -m ensurepip")};
         var4 = new String[]{"prog"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(165);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--version"), PyString.fromInterned("version"), PyString.fromInterned("pip {}").__getattr__("format").__call__(var2, var1.getglobal("version").__call__(var2)), PyString.fromInterned("Show the version of pip that is bundled with this Python.")};
         var4 = new String[]{"action", "version", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(171);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose"), PyString.fromInterned("count"), Py.newInteger(0), PyString.fromInterned("verbosity"), PyString.fromInterned("Give more output. Option is additive, and can be used up to 3 times.")};
         var4 = new String[]{"action", "default", "dest", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(179);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("-U"), PyString.fromInterned("--upgrade"), PyString.fromInterned("store_true"), var1.getglobal("False"), PyString.fromInterned("Upgrade pip and dependencies, even if already installed.")};
         var4 = new String[]{"action", "default", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(185);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--user"), PyString.fromInterned("store_true"), var1.getglobal("False"), PyString.fromInterned("Install using the user scheme.")};
         var4 = new String[]{"action", "default", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(191);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--root"), var1.getglobal("None"), PyString.fromInterned("Install everything relative to this alternate root directory.")};
         var4 = new String[]{"default", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(196);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--altinstall"), PyString.fromInterned("store_true"), var1.getglobal("False"), PyString.fromInterned("Make an alternate install, installing only the X.Y versionedscripts (Default: pipX, pipX.Y, easy_install-X.Y)")};
         var4 = new String[]{"action", "default", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(203);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--default-pip"), PyString.fromInterned("store_true"), var1.getglobal("True"), PyString.fromInterned("default_pip"), var1.getlocal(1).__getattr__("SUPPRESS")};
         var4 = new String[]{"action", "default", "dest", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(210);
         var10000 = var1.getlocal(2).__getattr__("add_argument");
         var5 = new PyObject[]{PyString.fromInterned("--no-default-pip"), PyString.fromInterned("store_false"), PyString.fromInterned("default_pip"), PyString.fromInterned("Make a non default install, installing only the X and X.Y versioned scripts.")};
         var4 = new String[]{"action", "dest", "help"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.setline(218);
         var3 = var1.getlocal(2).__getattr__("parse_args").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(220);
         var10000 = var1.getglobal("bootstrap");
         var5 = new PyObject[]{var1.getlocal(3).__getattr__("root"), var1.getlocal(3).__getattr__("upgrade"), var1.getlocal(3).__getattr__("user"), var1.getlocal(3).__getattr__("verbosity"), var1.getlocal(3).__getattr__("altinstall"), var1.getlocal(3).__getattr__("default_pip")};
         var4 = new String[]{"root", "upgrade", "user", "verbosity", "altinstall", "default_pip"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public ensurepip$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _require_ssl_for_pip$1 = Py.newCode(0, var2, var1, "_require_ssl_for_pip", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _require_ssl_for_pip$2 = Py.newCode(0, var2, var1, "_require_ssl_for_pip", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "additional_paths", "pip"};
      _run_pip$3 = Py.newCode(2, var2, var1, "_run_pip", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      version$4 = Py.newCode(0, var2, var1, "version", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"keys_to_remove", "_[60_22]", "k"};
      _disable_pip_configuration_settings$5 = Py.newCode(0, var2, var1, "_disable_pip_configuration_settings", 56, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"root", "upgrade", "user", "altinstall", "default_pip", "verbosity", "tmpdir", "additional_paths", "project", "version", "wheel_name", "whl", "fp", "args", "_[123_25]", "p"};
      bootstrap$6 = Py.newCode(6, var2, var1, "bootstrap", 68, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"verbosity", "pip", "msg", "args", "_[154_21]", "p"};
      _uninstall_helper$7 = Py.newCode(1, var2, var1, "_uninstall_helper", 128, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"argv", "argparse", "parser", "args"};
      _main$8 = Py.newCode(1, var2, var1, "_main", 157, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ensurepip$py("ensurepip$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ensurepip$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._require_ssl_for_pip$1(var2, var3);
         case 2:
            return this._require_ssl_for_pip$2(var2, var3);
         case 3:
            return this._run_pip$3(var2, var3);
         case 4:
            return this.version$4(var2, var3);
         case 5:
            return this._disable_pip_configuration_settings$5(var2, var3);
         case 6:
            return this.bootstrap$6(var2, var3);
         case 7:
            return this._uninstall_helper$7(var2, var3);
         case 8:
            return this._main$8(var2, var3);
         default:
            return null;
      }
   }
}
