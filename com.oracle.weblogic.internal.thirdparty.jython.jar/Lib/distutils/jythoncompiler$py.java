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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("distutils/jythoncompiler.py")
public class jythoncompiler$py extends PyFunctionTable implements PyRunnable {
   static jythoncompiler$py self;
   static final PyCode f$0;
   static final PyCode JythonCompiler$1;
   static final PyCode refuse_compilation$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.jythoncompiler\n\nJython does not support extension libraries. This CCompiler simply\nraises CCompiler exceptions.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.jythoncompiler\n\nJython does not support extension libraries. This CCompiler simply\nraises CCompiler exceptions.\n");
      var1.setline(7);
      String[] var3 = new String[]{"CCompiler"};
      PyObject[] var5 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var1.setline(8);
      PyObject var6 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var6);
      var3 = null;
      var1.setline(10);
      var5 = new PyObject[]{var1.getname("CCompiler")};
      var4 = Py.makeClass("JythonCompiler", var5, JythonCompiler$1);
      var1.setlocal("JythonCompiler", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject JythonCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Refuses to compile C extensions on Jython"));
      var1.setline(12);
      PyString.fromInterned("Refuses to compile C extensions on Jython");
      var1.setline(14);
      PyString var3 = PyString.fromInterned("jython");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(15);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("executables", var4);
      var3 = null;
      var1.setline(17);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, refuse_compilation$2, PyString.fromInterned("Refuse compilation"));
      var1.setlocal("refuse_compilation", var6);
      var3 = null;
      var1.setline(22);
      PyObject var7 = var1.getname("refuse_compilation");
      var1.setlocal("preprocess", var7);
      var1.setlocal("compile", var7);
      var1.setlocal("create_static_lib", var7);
      var1.setlocal("link", var7);
      return var1.getf_locals();
   }

   public PyObject refuse_compilation$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyString.fromInterned("Refuse compilation");
      var1.setline(19);
      var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Compiling extensions is not supported on Jython"));
      var1.setline(20);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public jythoncompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      JythonCompiler$1 = Py.newCode(0, var2, var1, "JythonCompiler", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwargs"};
      refuse_compilation$2 = Py.newCode(3, var2, var1, "refuse_compilation", 17, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new jythoncompiler$py("distutils/jythoncompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(jythoncompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.JythonCompiler$1(var2, var3);
         case 2:
            return this.refuse_compilation$2(var2, var3);
         default:
            return null;
      }
   }
}
