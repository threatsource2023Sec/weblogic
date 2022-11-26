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
@MTime(1498849384000L)
@Filename("py_compile.py")
public class py_compile$py extends PyFunctionTable implements PyRunnable {
   static py_compile$py self;
   static final PyCode f$0;
   static final PyCode PyCompileError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode compile$4;
   static final PyCode main$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Routine to \"compile\" a .py file to a .pyc (or .pyo) file.\n\nThis module has intimate knowledge of the format of .pyc files.\n"));
      var1.setline(4);
      PyString.fromInterned("Routine to \"compile\" a .py file to a .pyc (or .pyo) file.\n\nThis module has intimate knowledge of the format of .pyc files.\n");
      var1.setline(6);
      PyObject var3 = imp.importOne("_py_compile", var1, -1);
      var1.setlocal("_py_compile", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(11);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("main"), PyString.fromInterned("PyCompileError")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(14);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("PyCompileError", var6, PyCompileError$1);
      var1.setlocal("PyCompileError", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(60);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("False")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, compile$4, PyString.fromInterned("Byte-compile one Python source file to Python bytecode.\n\n    Arguments:\n\n    file:    source filename\n    cfile:   target filename; defaults to source with 'c' or 'o' appended\n             ('c' normally, 'o' in optimizing mode, giving .pyc or .pyo)\n    dfile:   purported filename; defaults to source (this is the filename\n             that will show up in error messages)\n    doraise: flag indicating whether or not an exception should be\n             raised when a compile error is found. If an exception\n             occurs and this flag is set to False, a string\n             indicating the nature of the exception will be printed,\n             and the function will return to the caller. If an\n             exception occurs and this flag is set to True, a\n             PyCompileError exception will be raised.\n\n    Note that it isn't necessary to byte-compile Python modules for\n    execution efficiency -- Python itself byte-compiles a module when\n    it is loaded, and if it can, writes out the bytecode to the\n    corresponding .pyc (or .pyo) file.\n\n    However, if a Python installation is shared between users, it is a\n    good idea to byte-compile all modules upon installation, since\n    other users may not be able to write in the source directories,\n    and thus they won't be able to write the .pyc/.pyo file, and then\n    they would be byte-compiling every module each time it is loaded.\n    This can slow down program start-up considerably.\n\n    See compileall.py for a script/module that uses this module to\n    byte-compile all installed files (or all files in selected\n    directories).\n\n    "));
      var1.setlocal("compile", var7);
      var3 = null;
      var1.setline(105);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, main$5, PyString.fromInterned("Compile several source files.\n\n    The files named in 'args' (or on the command line, if 'args' is\n    not specified) are compiled and the resulting bytecode is cached\n    in the normal manner.  This function does not search a directory\n    structure to locate source files; it only compiles files named\n    explicitly.\n\n    "));
      var1.setlocal("main", var7);
      var3 = null;
      var1.setline(127);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(128);
         var1.getname("sys").__getattr__("exit").__call__(var2, var1.getname("main").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PyCompileError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception raised when an error occurs while attempting to\n    compile the file.\n\n    To raise this exception, use\n\n        raise PyCompileError(exc_type,exc_value,file[,msg])\n\n    where\n\n        exc_type:   exception type to be used in error message\n                    type name can be accesses as class variable\n                    'exc_type_name'\n\n        exc_value:  exception value to be used in error message\n                    can be accesses as class variable 'exc_value'\n\n        file:       name of file being compiled to be used in error message\n                    can be accesses as class variable 'file'\n\n        msg:        string message to be written as error message\n                    If no value is given, a default exception message will be given,\n                    consistent with 'standard' py_compile output.\n                    message (or default) can be accesses as class variable 'msg'\n\n    "));
      var1.setline(39);
      PyString.fromInterned("Exception raised when an error occurs while attempting to\n    compile the file.\n\n    To raise this exception, use\n\n        raise PyCompileError(exc_type,exc_value,file[,msg])\n\n    where\n\n        exc_type:   exception type to be used in error message\n                    type name can be accesses as class variable\n                    'exc_type_name'\n\n        exc_value:  exception value to be used in error message\n                    can be accesses as class variable 'exc_value'\n\n        file:       name of file being compiled to be used in error message\n                    can be accesses as class variable 'file'\n\n        msg:        string message to be written as error message\n                    If no value is given, a default exception message will be given,\n                    consistent with 'standard' py_compile output.\n                    message (or default) can be accesses as class variable 'msg'\n\n    ");
      var1.setline(41);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(1).__getattr__("__name__");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("SyntaxError"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(44);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("traceback").__getattr__("format_exception_only").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(45);
         var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("File \"<string>\""), (PyObject)PyString.fromInterned("File \"%s\"")._mod(var1.getlocal(3)));
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(47);
         var3 = PyString.fromInterned("Sorry: %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2)}));
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(49);
      var10000 = var1.getglobal("Exception").__getattr__("__init__");
      PyObject[] var4 = new PyObject[5];
      var4[0] = var1.getlocal(0);
      PyObject var10002 = var1.getlocal(4);
      if (!var10002.__nonzero__()) {
         var10002 = var1.getlocal(7);
      }

      var4[1] = var10002;
      var4[2] = var1.getlocal(5);
      var4[3] = var1.getlocal(2);
      var4[4] = var1.getlocal(3);
      var10000.__call__(var2, var4);
      var1.setline(51);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("exc_type_name", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("exc_value", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(54);
      var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(7);
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile$4(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Byte-compile one Python source file to Python bytecode.\n\n    Arguments:\n\n    file:    source filename\n    cfile:   target filename; defaults to source with 'c' or 'o' appended\n             ('c' normally, 'o' in optimizing mode, giving .pyc or .pyo)\n    dfile:   purported filename; defaults to source (this is the filename\n             that will show up in error messages)\n    doraise: flag indicating whether or not an exception should be\n             raised when a compile error is found. If an exception\n             occurs and this flag is set to False, a string\n             indicating the nature of the exception will be printed,\n             and the function will return to the caller. If an\n             exception occurs and this flag is set to True, a\n             PyCompileError exception will be raised.\n\n    Note that it isn't necessary to byte-compile Python modules for\n    execution efficiency -- Python itself byte-compiles a module when\n    it is loaded, and if it can, writes out the bytecode to the\n    corresponding .pyc (or .pyo) file.\n\n    However, if a Python installation is shared between users, it is a\n    good idea to byte-compile all modules upon installation, since\n    other users may not be able to write in the source directories,\n    and thus they won't be able to write the .pyc/.pyo file, and then\n    they would be byte-compiling every module each time it is loaded.\n    This can slow down program start-up considerably.\n\n    See compileall.py for a script/module that uses this module to\n    byte-compile all installed files (or all files in selected\n    directories).\n\n    ");

      try {
         var1.setline(96);
         var1.getglobal("_py_compile").__getattr__("compile").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("Exception"))) {
            PyObject var4 = var3.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(98);
            PyObject var10000 = var1.getglobal("PyCompileError");
            PyObject var10002 = var1.getlocal(4).__getattr__("__class__");
            PyObject var10003 = var1.getlocal(4).__getattr__("args");
            PyObject var10004 = var1.getlocal(2);
            if (!var10004.__nonzero__()) {
               var10004 = var1.getlocal(0);
            }

            var4 = var10000.__call__(var2, var10002, var10003, var10004);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(99);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(100);
               throw Py.makeException(var1.getlocal(5));
            }

            var1.setline(102);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(5).__getattr__("msg")._add(PyString.fromInterned("\n")));
            var1.setline(103);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$5(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyString.fromInterned("Compile several source files.\n\n    The files named in 'args' (or on the command line, if 'args' is\n    not specified) are compiled and the resulting bytecode is cached\n    in the normal manner.  This function does not search a directory\n    structure to locate source files; it only compiles files named\n    explicitly.\n\n    ");
      var1.setline(115);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(117);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(118);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(118);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(125);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);

         PyException var5;
         try {
            var1.setline(120);
            var10000 = var1.getglobal("compile");
            PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getglobal("True")};
            String[] var11 = new String[]{"doraise"};
            var10000.__call__(var2, var9, var11);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getglobal("PyCompileError"))) {
               throw var5;
            }

            PyObject var6 = var5.value;
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(123);
            PyInteger var10 = Py.newInteger(1);
            var1.setlocal(1, var10);
            var6 = null;
            var1.setline(124);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("msg"));
         }
      }
   }

   public py_compile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PyCompileError$1 = Py.newCode(0, var2, var1, "PyCompileError", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "exc_type", "exc_value", "file", "msg", "exc_type_name", "tbtext", "errmsg"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 41, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 56, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "cfile", "dfile", "doraise", "err", "py_exc"};
      compile$4 = Py.newCode(4, var2, var1, "compile", 60, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "rv", "filename", "err"};
      main$5 = Py.newCode(1, var2, var1, "main", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new py_compile$py("py_compile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(py_compile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PyCompileError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.compile$4(var2, var3);
         case 5:
            return this.main$5(var2, var3);
         default:
            return null;
      }
   }
}
