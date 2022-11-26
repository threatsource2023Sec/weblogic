package distutils.tests;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/setuptools_extension.py")
public class setuptools_extension$py extends PyFunctionTable implements PyRunnable {
   static setuptools_extension$py self;
   static final PyCode f$0;
   static final PyCode _get_unpatched$1;
   static final PyCode Extension$2;
   static final PyCode __init__$3;
   static final PyCode Library$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"Extension"};
      PyObject[] var6 = imp.importFrom("distutils.core", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_Extension", var4);
      var4 = null;
      var1.setline(2);
      var3 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal("_Distribution", var4);
      var4 = null;
      var1.setline(4);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _get_unpatched$1, PyString.fromInterned("Protect against re-patching the distutils if reloaded\n\n    Also ensures that no other distutils extension monkeypatched the distutils\n    first.\n    "));
      var1.setlocal("_get_unpatched", var7);
      var3 = null;
      var1.setline(18);
      PyObject var8 = var1.getname("_get_unpatched").__call__(var2, var1.getname("_Distribution"));
      var1.setlocal("_Distribution", var8);
      var3 = null;
      var1.setline(19);
      var8 = var1.getname("_get_unpatched").__call__(var2, var1.getname("_Extension"));
      var1.setlocal("_Extension", var8);
      var3 = null;

      label23: {
         try {
            var1.setline(22);
            var3 = new String[]{"build_ext"};
            var6 = imp.importFrom("Pyrex.Distutils.build_ext", var3, var1, -1);
            var4 = var6[0];
            var1.setlocal("build_ext", var4);
            var4 = null;
         } catch (Throwable var5) {
            PyException var9 = Py.setException(var5, var1);
            if (var9.match(var1.getname("ImportError"))) {
               var1.setline(24);
               var4 = var1.getname("False");
               var1.setlocal("have_pyrex", var4);
               var4 = null;
               break label23;
            }

            throw var9;
         }

         var1.setline(26);
         var4 = var1.getname("True");
         var1.setlocal("have_pyrex", var4);
         var4 = null;
      }

      var1.setline(29);
      var6 = new PyObject[]{var1.getname("_Extension")};
      var4 = Py.makeClass("Extension", var6, Extension$2);
      var1.setlocal("Extension", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(44);
      var6 = new PyObject[]{var1.getname("Extension")};
      var4 = Py.makeClass("Library", var6, Library$4);
      var1.setlocal("Library", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(47);
      var8 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var8);
      var3 = null;
      var8 = imp.importOne("distutils.core", var1, -1);
      var1.setlocal("distutils", var8);
      var3 = null;
      var8 = imp.importOne("distutils.extension", var1, -1);
      var1.setlocal("distutils", var8);
      var3 = null;
      var1.setline(48);
      var8 = var1.getname("Extension");
      var1.getname("distutils").__getattr__("core").__setattr__("Extension", var8);
      var3 = null;
      var1.setline(49);
      var8 = var1.getname("Extension");
      var1.getname("distutils").__getattr__("extension").__setattr__("Extension", var8);
      var3 = null;
      var1.setline(50);
      PyString var10 = PyString.fromInterned("distutils.command.build_ext");
      PyObject var10000 = var10._in(var1.getname("sys").__getattr__("modules"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(51);
         var8 = var1.getname("Extension");
         var1.getname("sys").__getattr__("modules").__getitem__(PyString.fromInterned("distutils.command.build_ext")).__setattr__("Extension", var8);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_unpatched$1(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyString.fromInterned("Protect against re-patching the distutils if reloaded\n\n    Also ensures that no other distutils extension monkeypatched the distutils\n    first.\n    ");

      while(true) {
         var1.setline(10);
         PyObject var3;
         if (!var1.getlocal(0).__getattr__("__module__").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setuptools")).__nonzero__()) {
            var1.setline(12);
            if (var1.getlocal(0).__getattr__("__module__").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distutils")).__not__().__nonzero__()) {
               var1.setline(13);
               throw Py.makeException(var1.getglobal("AssertionError").__call__(var2, PyString.fromInterned("distutils has already been patched by %r")._mod(var1.getlocal(0))));
            } else {
               var1.setline(16);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(11);
         var3 = var1.getlocal(0).__getattr__("__bases__");
         PyObject[] var4 = Py.unpackSequence(var3, 1);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var3 = null;
      }
   }

   public PyObject Extension$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Extension that uses '.c' files in place of '.pyx' files"));
      var1.setline(30);
      PyString.fromInterned("Extension that uses '.c' files in place of '.pyx' files");
      var1.setline(32);
      if (var1.getname("have_pyrex").__not__().__nonzero__()) {
         var1.setline(34);
         PyObject[] var3 = Py.EmptyObjects;
         PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
         var1.setlocal("__init__", var4);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var10000 = var1.getglobal("_Extension").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.setline(36);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(37);
      PyObject var6 = var1.getlocal(0).__getattr__("sources").__iter__();

      while(true) {
         var1.setline(37);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(42);
            var6 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("sources", var6);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var7);
         var1.setline(38);
         if (var1.getlocal(4).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".pyx")).__nonzero__()) {
            var1.setline(39);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null)._add(PyString.fromInterned("c")));
         } else {
            var1.setline(41);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject Library$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Just like a regular Extension, but built as a library instead"));
      var1.setline(45);
      PyString.fromInterned("Just like a regular Extension, but built as a library instead");
      return var1.getf_locals();
   }

   public setuptools_extension$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      _get_unpatched$1 = Py.newCode(1, var2, var1, "_get_unpatched", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Extension$2 = Py.newCode(0, var2, var1, "Extension", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kw", "sources", "s"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 34, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Library$4 = Py.newCode(0, var2, var1, "Library", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new setuptools_extension$py("distutils/tests/setuptools_extension$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(setuptools_extension$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._get_unpatched$1(var2, var3);
         case 2:
            return this.Extension$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.Library$4(var2, var3);
         default:
            return null;
      }
   }
}
