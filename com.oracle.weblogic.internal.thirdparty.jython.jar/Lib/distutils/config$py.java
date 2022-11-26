package distutils;

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
@Filename("distutils/config.py")
public class config$py extends PyFunctionTable implements PyRunnable {
   static config$py self;
   static final PyCode f$0;
   static final PyCode PyPIRCCommand$1;
   static final PyCode _get_rc_file$2;
   static final PyCode _store_pypirc$3;
   static final PyCode _read_pypirc$4;
   static final PyCode initialize_options$5;
   static final PyCode finalize_options$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.pypirc\n\nProvides the PyPIRCCommand class, the base class for the command classes\nthat uses .pypirc in the distutils.command package.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.pypirc\n\nProvides the PyPIRCCommand class, the base class for the command classes\nthat uses .pypirc in the distutils.command package.\n");
      var1.setline(6);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"ConfigParser"};
      PyObject[] var6 = imp.importFrom("ConfigParser", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("ConfigParser", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"Command"};
      var6 = imp.importFrom("distutils.cmd", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(11);
      PyString var7 = PyString.fromInterned("[distutils]\nindex-servers =\n    pypi\n\n[pypi]\nusername:%s\npassword:%s\n");
      var1.setlocal("DEFAULT_PYPIRC", var7);
      var3 = null;
      var1.setline(21);
      var6 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("PyPIRCCommand", var6, PyPIRCCommand$1);
      var1.setlocal("PyPIRCCommand", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PyPIRCCommand$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base command that knows how to handle the .pypirc file\n    "));
      var1.setline(23);
      PyString.fromInterned("Base command that knows how to handle the .pypirc file\n    ");
      var1.setline(24);
      PyString var3 = PyString.fromInterned("https://pypi.python.org/pypi");
      var1.setlocal("DEFAULT_REPOSITORY", var3);
      var3 = null;
      var1.setline(25);
      var3 = PyString.fromInterned("pypi");
      var1.setlocal("DEFAULT_REALM", var3);
      var3 = null;
      var1.setline(26);
      PyObject var4 = var1.getname("None");
      var1.setlocal("repository", var4);
      var3 = null;
      var1.setline(27);
      var4 = var1.getname("None");
      var1.setlocal("realm", var4);
      var3 = null;
      var1.setline(29);
      PyList var5 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("repository="), PyString.fromInterned("r"), PyString.fromInterned("url of repository [default: %s]")._mod(var1.getname("DEFAULT_REPOSITORY"))}), new PyTuple(new PyObject[]{PyString.fromInterned("show-response"), var1.getname("None"), PyString.fromInterned("display full response text from server")})});
      var1.setlocal("user_options", var5);
      var3 = null;
      var1.setline(36);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("show-response")});
      var1.setlocal("boolean_options", var5);
      var3 = null;
      var1.setline(38);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _get_rc_file$2, PyString.fromInterned("Returns rc file path."));
      var1.setlocal("_get_rc_file", var7);
      var3 = null;
      var1.setline(42);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _store_pypirc$3, PyString.fromInterned("Creates a default .pypirc file."));
      var1.setlocal("_store_pypirc", var7);
      var3 = null;
      var1.setline(51);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _read_pypirc$4, PyString.fromInterned("Reads the .pypirc file."));
      var1.setlocal("_read_pypirc", var7);
      var3 = null;
      var1.setline(105);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, initialize_options$5, PyString.fromInterned("Initialize options."));
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(111);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$6, PyString.fromInterned("Finalizes options."));
      var1.setlocal("finalize_options", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_rc_file$2(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Returns rc file path.");
      var1.setline(40);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~")), (PyObject)PyString.fromInterned(".pypirc"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _store_pypirc$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Creates a default .pypirc file.");
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_rc_file").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getglobal("os").__getattr__("O_CREAT")._or(var1.getglobal("os").__getattr__("O_WRONLY")), (PyObject)Py.newInteger(384)), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(47);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getglobal("DEFAULT_PYPIRC")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(49);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(49);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read_pypirc$4(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Reads the .pypirc file.");
      var1.setline(53);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_rc_file").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(54);
      PyDictionary var15;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(55);
         var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Using PyPI login from %s")._mod(var1.getlocal(1)));
         var1.setline(56);
         PyObject var10000 = var1.getlocal(0).__getattr__("repository");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY");
         }

         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(57);
         var3 = var1.getglobal("ConfigParser").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(58);
         var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setline(59);
         var3 = var1.getlocal(3).__getattr__("sections").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(60);
         PyString var11 = PyString.fromInterned("distutils");
         var10000 = var11._in(var1.getlocal(4));
         var3 = null;
         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var3 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distutils"), (PyObject)PyString.fromInterned("index-servers"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(63);
            PyList var16 = new PyList();
            var3 = var16.__getattr__("append");
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(63);
            var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

            label63:
            while(true) {
               var1.setline(63);
               var4 = var3.__iternext__();
               PyObject var5;
               if (var4 == null) {
                  var1.setline(63);
                  var1.dellocal(7);
                  PyList var13 = var16;
                  var1.setlocal(6, var13);
                  var3 = null;
                  var1.setline(66);
                  var3 = var1.getlocal(6);
                  var10000 = var3._eq(new PyList(Py.EmptyObjects));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(68);
                     var11 = PyString.fromInterned("pypi");
                     var10000 = var11._in(var1.getlocal(4));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(73);
                        var15 = new PyDictionary(Py.EmptyObjects);
                        var1.f_lasti = -1;
                        return var15;
                     }

                     var1.setline(69);
                     var13 = new PyList(new PyObject[]{PyString.fromInterned("pypi")});
                     var1.setlocal(6, var13);
                     var3 = null;
                  }

                  var1.setline(74);
                  var4 = var1.getlocal(6).__iter__();

                  while(true) {
                     var1.setline(74);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        break label63;
                     }

                     var1.setlocal(8, var5);
                     var1.setline(75);
                     PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("server"), var1.getlocal(8)});
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(76);
                     PyObject var12 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("username"));
                     var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("username"), var12);
                     var6 = null;
                     var1.setline(79);
                     var12 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("repository"), var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY")}), new PyTuple(new PyObject[]{PyString.fromInterned("realm"), var1.getlocal(0).__getattr__("DEFAULT_REALM")}), new PyTuple(new PyObject[]{PyString.fromInterned("password"), var1.getglobal("None")})})).__iter__();

                     while(true) {
                        var1.setline(79);
                        PyObject var7 = var12.__iternext__();
                        if (var7 == null) {
                           var1.setline(87);
                           var12 = var1.getlocal(9).__getitem__(PyString.fromInterned("server"));
                           var10000 = var12._eq(var1.getlocal(2));
                           var6 = null;
                           if (!var10000.__nonzero__()) {
                              var12 = var1.getlocal(9).__getitem__(PyString.fromInterned("repository"));
                              var10000 = var12._eq(var1.getlocal(2));
                              var6 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(89);
                              var3 = var1.getlocal(9);
                              var1.f_lasti = -1;
                              return var3;
                           }
                           break;
                        }

                        PyObject[] var8 = Py.unpackSequence(var7, 2);
                        PyObject var9 = var8[0];
                        var1.setlocal(10, var9);
                        var9 = null;
                        var9 = var8[1];
                        var1.setlocal(11, var9);
                        var9 = null;
                        var1.setline(83);
                        PyObject var14;
                        if (var1.getlocal(3).__getattr__("has_option").__call__(var2, var1.getlocal(8), var1.getlocal(10)).__nonzero__()) {
                           var1.setline(84);
                           var14 = var1.getlocal(3).__getattr__("get").__call__(var2, var1.getlocal(8), var1.getlocal(10));
                           var1.getlocal(9).__setitem__(var1.getlocal(10), var14);
                           var8 = null;
                        } else {
                           var1.setline(86);
                           var14 = var1.getlocal(11);
                           var1.getlocal(9).__setitem__(var1.getlocal(10), var14);
                           var8 = null;
                        }
                     }
                  }
               }

               var1.setlocal(8, var4);
               var1.setline(65);
               var5 = var1.getlocal(8).__getattr__("strip").__call__(var2);
               PyObject var10001 = var5._ne(PyString.fromInterned(""));
               var5 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(63);
                  var1.getlocal(7).__call__(var2, var1.getlocal(8).__getattr__("strip").__call__(var2));
               }
            }
         } else {
            var1.setline(90);
            PyString var10 = PyString.fromInterned("server-login");
            var10000 = var10._in(var1.getlocal(4));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(92);
               var10 = PyString.fromInterned("server-login");
               var1.setlocal(8, var10);
               var4 = null;
               var1.setline(93);
               if (var1.getlocal(3).__getattr__("has_option").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("repository")).__nonzero__()) {
                  var1.setline(94);
                  var4 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("repository"));
                  var1.setlocal(2, var4);
                  var4 = null;
               } else {
                  var1.setline(96);
                  var4 = var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY");
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(97);
               var15 = new PyDictionary(new PyObject[]{PyString.fromInterned("username"), var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("username")), PyString.fromInterned("password"), var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("password")), PyString.fromInterned("repository"), var1.getlocal(2), PyString.fromInterned("server"), var1.getlocal(8), PyString.fromInterned("realm"), var1.getlocal(0).__getattr__("DEFAULT_REALM")});
               var1.f_lasti = -1;
               return var15;
            }
         }
      }

      var1.setline(103);
      var15 = new PyDictionary(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var15;
   }

   public PyObject initialize_options$5(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyString.fromInterned("Initialize options.");
      var1.setline(107);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("repository", var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("realm", var3);
      var3 = null;
      var1.setline(109);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"show_response", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$6(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Finalizes options.");
      var1.setline(113);
      PyObject var3 = var1.getlocal(0).__getattr__("repository");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var3 = var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY");
         var1.getlocal(0).__setattr__("repository", var3);
         var3 = null;
      }

      var1.setline(115);
      var3 = var1.getlocal(0).__getattr__("realm");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = var1.getlocal(0).__getattr__("DEFAULT_REALM");
         var1.getlocal(0).__setattr__("realm", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public config$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PyPIRCCommand$1 = Py.newCode(0, var2, var1, "PyPIRCCommand", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_rc_file$2 = Py.newCode(1, var2, var1, "_get_rc_file", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "username", "password", "rc", "f"};
      _store_pypirc$3 = Py.newCode(3, var2, var1, "_store_pypirc", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rc", "repository", "config", "sections", "index_servers", "_servers", "_[63_28]", "server", "current", "key", "default"};
      _read_pypirc$4 = Py.newCode(1, var2, var1, "_read_pypirc", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$5 = Py.newCode(1, var2, var1, "initialize_options", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$6 = Py.newCode(1, var2, var1, "finalize_options", 111, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new config$py("distutils/config$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(config$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PyPIRCCommand$1(var2, var3);
         case 2:
            return this._get_rc_file$2(var2, var3);
         case 3:
            return this._store_pypirc$3(var2, var3);
         case 4:
            return this._read_pypirc$4(var2, var3);
         case 5:
            return this.initialize_options$5(var2, var3);
         case 6:
            return this.finalize_options$6(var2, var3);
         default:
            return null;
      }
   }
}
