package distutils;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/command/__init__.py")
public class command$py extends PyFunctionTable implements PyRunnable {
   static command$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command\n\nPackage containing implementation of all the standard Distutils\ncommands."));
      var1.setline(4);
      PyString.fromInterned("distutils.command\n\nPackage containing implementation of all the standard Distutils\ncommands.");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("build"), PyString.fromInterned("build_py"), PyString.fromInterned("build_ext"), PyString.fromInterned("build_clib"), PyString.fromInterned("build_scripts"), PyString.fromInterned("clean"), PyString.fromInterned("install"), PyString.fromInterned("install_lib"), PyString.fromInterned("install_headers"), PyString.fromInterned("install_scripts"), PyString.fromInterned("install_data"), PyString.fromInterned("sdist"), PyString.fromInterned("register"), PyString.fromInterned("bdist"), PyString.fromInterned("bdist_dumb"), PyString.fromInterned("bdist_rpm"), PyString.fromInterned("bdist_wininst"), PyString.fromInterned("upload"), PyString.fromInterned("check")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public command$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new command$py("distutils/command$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(command$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
