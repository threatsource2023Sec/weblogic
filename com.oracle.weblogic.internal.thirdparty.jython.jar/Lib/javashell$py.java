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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("javashell.py")
public class javashell$py extends PyFunctionTable implements PyRunnable {
   static javashell$py self;
   static final PyCode f$0;
   static final PyCode __warn$1;
   static final PyCode _ShellEnv$2;
   static final PyCode __init__$3;
   static final PyCode execute$4;
   static final PyCode _formatCmd$5;
   static final PyCode _formatEnvironment$6;
   static final PyCode _getOsType$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nImplement subshell functionality for Jython.\n\nThis is mostly to provide the environ object for the os module,\nand subshell execution functionality for os.system and popen* functions.\n\njavashell attempts to determine a suitable command shell for the host\noperating system, and uses that shell to determine environment variables\nand to provide subshell execution functionality.\n"));
      var1.setline(10);
      PyString.fromInterned("\nImplement subshell functionality for Jython.\n\nThis is mostly to provide the environ object for the os module,\nand subshell execution functionality for os.system and popen* functions.\n\njavashell attempts to determine a suitable command shell for the host\noperating system, and uses that shell to determine environment variables\nand to provide subshell execution functionality.\n");
      var1.setline(11);
      String[] var3 = new String[]{"System", "Runtime"};
      PyObject[] var5 = imp.importFrom("java.lang", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("System", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Runtime", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"File"};
      var5 = imp.importFrom("java.io", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("File", var4);
      var4 = null;
      var1.setline(13);
      var3 = new String[]{"IOException"};
      var5 = imp.importFrom("java.io", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("IOException", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"InputStreamReader"};
      var5 = imp.importFrom("java.io", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("InputStreamReader", var4);
      var4 = null;
      var1.setline(15);
      var3 = new String[]{"BufferedReader"};
      var5 = imp.importFrom("java.io", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("BufferedReader", var4);
      var4 = null;
      var1.setline(16);
      var3 = new String[]{"UserDict"};
      var5 = imp.importFrom("UserDict", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("UserDict", var4);
      var4 = null;
      var1.setline(17);
      PyObject var6 = imp.importOne("jarray", var1, -1);
      var1.setlocal("jarray", var6);
      var3 = null;
      var1.setline(18);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(19);
      var6 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var6);
      var3 = null;
      var1.setline(20);
      var6 = imp.importOne("subprocess", var1, -1);
      var1.setlocal("subprocess", var6);
      var3 = null;
      var1.setline(21);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(22);
      var6 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var6);
      var3 = null;
      var1.setline(23);
      var6 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var6);
      var3 = null;
      var1.setline(24);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("The javashell module is deprecated. Use the subprocess module."), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(27);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("shellexecute")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(29);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, __warn$1, (PyObject)null);
      var1.setlocal("__warn", var8);
      var3 = null;
      var1.setline(32);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("_ShellEnv", var5, _ShellEnv$2);
      var1.setlocal("_ShellEnv", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(89);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _getOsType$7, (PyObject)null);
      var1.setlocal("_getOsType", var8);
      var3 = null;
      var1.setline(92);
      var6 = var1.getname("_ShellEnv").__call__(var2, var1.getname("subprocess").__getattr__("_shell_command"));
      var1.setlocal("_shellEnv", var6);
      var3 = null;
      var1.setline(93);
      var6 = var1.getname("_shellEnv").__getattr__("execute");
      var1.setlocal("shellexecute", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __warn$1(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var10000 = PyString.fromInterned(" ").__getattr__("join");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(30);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(30);
            var1.dellocal(1);
            Py.println(var10000.__call__((ThreadState)var2, (PyObject)var10002));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(30);
         var1.getlocal(1).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject _ShellEnv$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Provide environment derived by spawning a subshell and parsing its\n    environment.  Also supports subshell execution functions and provides\n    empty environment support for platforms with unknown shell functionality.\n    "));
      var1.setline(36);
      PyString.fromInterned("Provide environment derived by spawning a subshell and parsing its\n    environment.  Also supports subshell execution functions and provides\n    empty environment support for platforms with unknown shell functionality.\n    ");
      var1.setline(37);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Construct _ShellEnv instance.\n        cmd: list of exec() arguments required to run a command in\n            subshell, or None\n        getEnv: shell command to list environment variables, or None.\n            deprecated\n        keyTransform: normalization function for environment keys,\n          such as 'string.upper', or None. deprecated.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(49);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, execute$4, PyString.fromInterned("Execute cmd in a shell, and return the java.lang.Process instance.\n        Accepts either a string command to be executed in a shell,\n        or a sequence of [executable, args...].\n        "));
      var1.setlocal("execute", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _formatCmd$5, PyString.fromInterned("Format a command for execution in a shell."));
      var1.setlocal("_formatCmd", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _formatEnvironment$6, PyString.fromInterned("Format enviroment in lines suitable for Runtime.exec"));
      var1.setlocal("_formatEnvironment", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Construct _ShellEnv instance.\n        cmd: list of exec() arguments required to run a command in\n            subshell, or None\n        getEnv: shell command to list environment variables, or None.\n            deprecated\n        keyTransform: normalization function for environment keys,\n          such as 'string.upper', or None. deprecated.\n        ");
      var1.setline(46);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("cmd", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("os").__getattr__("environ");
      var1.getlocal(0).__setattr__("environment", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execute$4(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString.fromInterned("Execute cmd in a shell, and return the java.lang.Process instance.\n        Accepts either a string command to be executed in a shell,\n        or a sequence of [executable, args...].\n        ");
      var1.setline(54);
      PyObject var3 = var1.getlocal(0).__getattr__("_formatCmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(0).__getattr__("_formatEnvironment").__call__(var2, var1.getlocal(0).__getattr__("environment"));
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(58);
         var3 = var1.getglobal("Runtime").__getattr__("getRuntime").__call__(var2).__getattr__("exec").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getglobal("File").__call__(var2, var1.getglobal("os").__getattr__("getcwdu").__call__(var2)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(59);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("IOException"))) {
            PyObject var5 = var4.value;
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(61);
            throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("Failed to execute command (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5)}))));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _formatCmd$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Format a command for execution in a shell.");
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("cmd");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(70);
         PyString var4 = PyString.fromInterned("Unable to execute commands in subshell because shell functionality not implemented for OS %s Failed command=%s");
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(73);
         throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2)._mod(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("_name"), var1.getlocal(1)}))));
      } else {
         var1.setline(75);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(76);
            var3 = var1.getlocal(0).__getattr__("cmd")._add(new PyList(new PyObject[]{var1.getlocal(1)}));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(78);
            var3 = var1.getlocal(1);
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(80);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _formatEnvironment$6(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString.fromInterned("Format enviroment in lines suitable for Runtime.exec");
      var1.setline(84);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(85);
      PyObject var5 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(85);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(87);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(86);
         var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(var1.getlocal(3)));
      }
   }

   public PyObject _getOsType$7(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getglobal("os").__getattr__("_name");
      var1.f_lasti = -1;
      return var3;
   }

   public javashell$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args", "_[30_21]", "arg"};
      __warn$1 = Py.newCode(1, var2, var1, "__warn", 29, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ShellEnv$2 = Py.newCode(0, var2, var1, "_ShellEnv", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cmd", "getEnv", "keyTransform"};
      __init__$3 = Py.newCode(4, var2, var1, "__init__", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "shellCmd", "env", "p", "ex"};
      execute$4 = Py.newCode(2, var2, var1, "execute", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "msgFmt", "shellCmd"};
      _formatCmd$5 = Py.newCode(2, var2, var1, "_formatCmd", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "env", "lines", "keyValue"};
      _formatEnvironment$6 = Py.newCode(2, var2, var1, "_formatEnvironment", 82, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _getOsType$7 = Py.newCode(0, var2, var1, "_getOsType", 89, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new javashell$py("javashell$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(javashell$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.__warn$1(var2, var3);
         case 2:
            return this._ShellEnv$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.execute$4(var2, var3);
         case 5:
            return this._formatCmd$5(var2, var3);
         case 6:
            return this._formatEnvironment$6(var2, var3);
         case 7:
            return this._getOsType$7(var2, var3);
         default:
            return null;
      }
   }
}
