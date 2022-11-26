package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_ccompiler.py")
public class test_ccompiler$py extends PyFunctionTable implements PyRunnable {
   static test_ccompiler$py self;
   static final PyCode f$0;
   static final PyCode FakeCompiler$1;
   static final PyCode library_dir_option$2;
   static final PyCode runtime_library_dir_option$3;
   static final PyCode find_library_file$4;
   static final PyCode library_option$5;
   static final PyCode CCompilerTestCase$6;
   static final PyCode test_gen_lib_options$7;
   static final PyCode test_debug_print$8;
   static final PyCode MyCCompiler$9;
   static final PyCode test_customize_compiler$10;
   static final PyCode compiler$11;
   static final PyCode set_executables$12;
   static final PyCode test_suite$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.ccompiler."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.ccompiler.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"captured_stdout"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"gen_lib_options", "CCompiler", "get_default_compiler"};
      var6 = imp.importFrom("distutils.ccompiler", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("get_default_compiler", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"customize_compiler"};
      var6 = imp.importFrom("distutils.sysconfig", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"debug"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("debug", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FakeCompiler", var6, FakeCompiler$1);
      var1.setlocal("FakeCompiler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(25);
      var6 = new PyObject[]{var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("CCompilerTestCase", var6, CCompilerTestCase$6);
      var1.setlocal("CCompilerTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(78);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$13, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(81);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         var10000 = var1.getname("unittest").__getattr__("main");
         var6 = new PyObject[]{PyString.fromInterned("test_suite")};
         String[] var8 = new String[]{"defaultTest"};
         var10000.__call__(var2, var6, var8);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, library_dir_option$2, (PyObject)null);
      var1.setlocal("library_dir_option", var4);
      var3 = null;
      var1.setline(16);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runtime_library_dir_option$3, (PyObject)null);
      var1.setlocal("runtime_library_dir_option", var4);
      var3 = null;
      var1.setline(19);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, find_library_file$4, (PyObject)null);
      var1.setlocal("find_library_file", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, library_option$5, (PyObject)null);
      var1.setlocal("library_option", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject library_dir_option$2(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var3 = PyString.fromInterned("-L")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runtime_library_dir_option$3(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("-cool"), PyString.fromInterned("-R")._add(var1.getlocal(1))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_library_file$4(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyString var3 = PyString.fromInterned("found");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject library_option$5(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = PyString.fromInterned("-l")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject CCompilerTestCase$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_gen_lib_options$7, (PyObject)null);
      var1.setlocal("test_gen_lib_options", var4);
      var3 = null;
      var1.setline(38);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_print$8, (PyObject)null);
      var1.setlocal("test_debug_print", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_customize_compiler$10, (PyObject)null);
      var1.setlocal("test_customize_compiler", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_gen_lib_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("FakeCompiler").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(29);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("lib1"), PyString.fromInterned("lib2")});
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(30);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("runlib1")});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(31);
      var4 = new PyList(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir"), (PyObject)PyString.fromInterned("name")), PyString.fromInterned("name2")});
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(33);
      var3 = var1.getglobal("gen_lib_options").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(34);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("-Llib1"), PyString.fromInterned("-Llib2"), PyString.fromInterned("-cool"), PyString.fromInterned("-Rrunlib1"), PyString.fromInterned("found"), PyString.fromInterned("-lname2")});
      var1.setlocal(6, var4);
      var3 = null;
      var1.setline(36);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_debug_print$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(40);
      PyObject[] var3 = new PyObject[]{var1.getglobal("CCompiler")};
      PyObject var4 = Py.makeClass("MyCCompiler", var3, MyCCompiler$9);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(43);
      PyObject var9 = var1.getlocal(1).__call__(var2);
      var1.setlocal(2, var9);
      var3 = null;
      ContextManager var10;
      var4 = (var10 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label38: {
         try {
            var1.setlocal(3, var4);
            var1.setline(45);
            var1.getlocal(2).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
         } catch (Throwable var8) {
            if (var10.__exit__(var2, Py.setException(var8, var1))) {
               break label38;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(46);
      var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(47);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(49);
      var9 = var1.getglobal("True");
      var1.getglobal("debug").__setattr__("DEBUG", var9);
      var3 = null;
      var3 = null;

      try {
         ContextManager var11;
         PyObject var5 = (var11 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

         label28: {
            try {
               var1.setlocal(3, var5);
               var1.setline(52);
               var1.getlocal(2).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
            } catch (Throwable var6) {
               if (var11.__exit__(var2, Py.setException(var6, var1))) {
                  break label28;
               }

               throw (Throwable)Py.makeException();
            }

            var11.__exit__(var2, (PyException)null);
         }

         var1.setline(53);
         var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(54);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned("xxx\n"));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(56);
         var4 = var1.getglobal("False");
         var1.getglobal("debug").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(56);
      var4 = var1.getglobal("False");
      var1.getglobal("debug").__setattr__("DEBUG", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MyCCompiler$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(41);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("executables", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_customize_compiler$10(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getglobal("get_default_compiler").__call__(var2);
      PyObject var10000 = var3._ne(PyString.fromInterned("unix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(62);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(64);
         PyString var5 = PyString.fromInterned("my_ar");
         var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("AR"), var5);
         var3 = null;
         var1.setline(65);
         var5 = PyString.fromInterned("-arflags");
         var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("ARFLAGS"), var5);
         var3 = null;
         var1.setline(68);
         PyObject[] var6 = Py.EmptyObjects;
         PyObject var4 = Py.makeClass("compiler", var6, compiler$11);
         var1.setlocal(1, var4);
         var4 = null;
         Arrays.fill(var6, (Object)null);
         var1.setline(74);
         var3 = var1.getlocal(1).__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(75);
         var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(2));
         var1.setline(76);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("exes").__getitem__(PyString.fromInterned("archiver")), (PyObject)PyString.fromInterned("my_ar -arflags"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject compiler$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(69);
      PyString var3 = PyString.fromInterned("unix");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(71);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, set_executables$12, (PyObject)null);
      var1.setlocal("set_executables", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject set_executables$12(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("exes", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$13(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("CCompilerTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_ccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FakeCompiler$1 = Py.newCode(0, var2, var1, "FakeCompiler", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dir"};
      library_dir_option$2 = Py.newCode(2, var2, var1, "library_dir_option", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      runtime_library_dir_option$3 = Py.newCode(2, var2, var1, "runtime_library_dir_option", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug"};
      find_library_file$4 = Py.newCode(4, var2, var1, "find_library_file", 19, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib"};
      library_option$5 = Py.newCode(2, var2, var1, "library_option", 22, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CCompilerTestCase$6 = Py.newCode(0, var2, var1, "CCompilerTestCase", 25, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "compiler", "libdirs", "runlibdirs", "libs", "opts", "wanted"};
      test_gen_lib_options$7 = Py.newCode(1, var2, var1, "test_gen_lib_options", 27, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "MyCCompiler", "compiler", "stdout"};
      test_debug_print$8 = Py.newCode(1, var2, var1, "test_debug_print", 38, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MyCCompiler$9 = Py.newCode(0, var2, var1, "MyCCompiler", 40, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "compiler", "comp"};
      test_customize_compiler$10 = Py.newCode(1, var2, var1, "test_customize_compiler", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      compiler$11 = Py.newCode(0, var2, var1, "compiler", 68, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kw"};
      set_executables$12 = Py.newCode(2, var2, var1, "set_executables", 71, false, true, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$13 = Py.newCode(0, var2, var1, "test_suite", 78, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_ccompiler$py("distutils/tests/test_ccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_ccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FakeCompiler$1(var2, var3);
         case 2:
            return this.library_dir_option$2(var2, var3);
         case 3:
            return this.runtime_library_dir_option$3(var2, var3);
         case 4:
            return this.find_library_file$4(var2, var3);
         case 5:
            return this.library_option$5(var2, var3);
         case 6:
            return this.CCompilerTestCase$6(var2, var3);
         case 7:
            return this.test_gen_lib_options$7(var2, var3);
         case 8:
            return this.test_debug_print$8(var2, var3);
         case 9:
            return this.MyCCompiler$9(var2, var3);
         case 10:
            return this.test_customize_compiler$10(var2, var3);
         case 11:
            return this.compiler$11(var2, var3);
         case 12:
            return this.set_executables$12(var2, var3);
         case 13:
            return this.test_suite$13(var2, var3);
         default:
            return null;
      }
   }
}
