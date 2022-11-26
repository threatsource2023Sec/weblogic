package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_msvc9compiler.py")
public class test_msvc9compiler$py extends PyFunctionTable implements PyRunnable {
   static test_msvc9compiler$py self;
   static final PyCode f$0;
   static final PyCode msvc9compilerTestCase$1;
   static final PyCode test_no_compiler$2;
   static final PyCode _find_vcvarsall$3;
   static final PyCode test_reg_class$4;
   static final PyCode test_remove_visual_c_ref$5;
   static final PyCode test_remove_entire_manifest$6;
   static final PyCode test_suite$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.msvc9compiler."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.msvc9compiler.");
      var1.setline(2);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      String[] var6 = new String[]{"DistutilsPlatformError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(7);
      var6 = new String[]{"support"};
      var7 = imp.importFrom("distutils.tests", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(8);
      var6 = new String[]{"run_unittest"};
      var7 = imp.importFrom("test.test_support", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(14);
      PyString var8 = PyString.fromInterned("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<assembly xmlns=\"urn:schemas-microsoft-com:asm.v1\"\n          manifestVersion=\"1.0\">\n  <trustInfo xmlns=\"urn:schemas-microsoft-com:asm.v3\">\n    <security>\n      <requestedPrivileges>\n        <requestedExecutionLevel level=\"asInvoker\" uiAccess=\"false\">\n        </requestedExecutionLevel>\n      </requestedPrivileges>\n    </security>\n  </trustInfo>\n  <dependency>\n    <dependentAssembly>\n      <assemblyIdentity type=\"win32\" name=\"Microsoft.VC90.CRT\"\n         version=\"9.0.21022.8\" processorArchitecture=\"x86\"\n         publicKeyToken=\"XXXX\">\n      </assemblyIdentity>\n    </dependentAssembly>\n  </dependency>\n</assembly>\n");
      var1.setlocal("_MANIFEST_WITH_ONLY_MSVC_REFERENCE", var8);
      var3 = null;
      var1.setline(39);
      var8 = PyString.fromInterned("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<assembly xmlns=\"urn:schemas-microsoft-com:asm.v1\"\n          manifestVersion=\"1.0\">\n  <trustInfo xmlns=\"urn:schemas-microsoft-com:asm.v3\">\n    <security>\n      <requestedPrivileges>\n        <requestedExecutionLevel level=\"asInvoker\" uiAccess=\"false\">\n        </requestedExecutionLevel>\n      </requestedPrivileges>\n    </security>\n  </trustInfo>\n  <dependency>\n    <dependentAssembly>\n      <assemblyIdentity type=\"win32\" name=\"Microsoft.VC90.CRT\"\n         version=\"9.0.21022.8\" processorArchitecture=\"x86\"\n         publicKeyToken=\"XXXX\">\n      </assemblyIdentity>\n    </dependentAssembly>\n  </dependency>\n  <dependency>\n    <dependentAssembly>\n      <assemblyIdentity type=\"win32\" name=\"Microsoft.VC90.MFC\"\n        version=\"9.0.21022.8\" processorArchitecture=\"x86\"\n        publicKeyToken=\"XXXX\"></assemblyIdentity>\n    </dependentAssembly>\n  </dependency>\n</assembly>\n");
      var1.setlocal("_MANIFEST_WITH_MULTIPLE_REFERENCES", var8);
      var3 = null;
      var1.setline(69);
      var8 = PyString.fromInterned("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<assembly xmlns=\"urn:schemas-microsoft-com:asm.v1\"\n          manifestVersion=\"1.0\">\n  <trustInfo xmlns=\"urn:schemas-microsoft-com:asm.v3\">\n    <security>\n      <requestedPrivileges>\n        <requestedExecutionLevel level=\"asInvoker\" uiAccess=\"false\">\n        </requestedExecutionLevel>\n      </requestedPrivileges>\n    </security>\n  </trustInfo>\n  <dependency>\n\n  </dependency>\n  <dependency>\n    <dependentAssembly>\n      <assemblyIdentity type=\"win32\" name=\"Microsoft.VC90.MFC\"\n        version=\"9.0.21022.8\" processorArchitecture=\"x86\"\n        publicKeyToken=\"XXXX\"></assemblyIdentity>\n    </dependentAssembly>\n  </dependency>\n</assembly>");
      var1.setlocal("_CLEANED_MANIFEST", var8);
      var3 = null;
      var1.setline(93);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var6 = new String[]{"get_build_version"};
         var7 = imp.importFrom("distutils.msvccompiler", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("get_build_version", var4);
         var4 = null;
         var1.setline(95);
         var3 = var1.getname("get_build_version").__call__(var2);
         var10000 = var3._ge(Py.newFloat(8.0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(96);
            var3 = var1.getname("None");
            var1.setlocal("SKIP_MESSAGE", var3);
            var3 = null;
         } else {
            var1.setline(98);
            var8 = PyString.fromInterned("These tests are only for MSVC8.0 or above");
            var1.setlocal("SKIP_MESSAGE", var8);
            var3 = null;
         }
      } else {
         var1.setline(100);
         var8 = PyString.fromInterned("These tests are only for win32");
         var1.setlocal("SKIP_MESSAGE", var8);
         var3 = null;
      }

      var1.setline(102);
      var7 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("msvc9compilerTestCase", var7, msvc9compilerTestCase$1);
      var10000 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var5 = var1.getname("SKIP_MESSAGE");
      PyObject var10002 = var5._is(var1.getname("None"));
      var5 = null;
      var4 = var10000.__call__(var2, var10002, var1.getname("SKIP_MESSAGE")).__call__(var2, var4);
      var1.setlocal("msvc9compilerTestCase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(180);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, test_suite$7, (PyObject)null);
      var1.setlocal("test_suite", var9);
      var3 = null;
      var1.setline(183);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(184);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject msvc9compilerTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(106);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_no_compiler$2, (PyObject)null);
      var1.setlocal("test_no_compiler", var4);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_reg_class$4, (PyObject)null);
      var1.setlocal("test_reg_class", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_remove_visual_c_ref$5, (PyObject)null);
      var1.setlocal("test_remove_visual_c_ref", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_remove_entire_manifest$6, (PyObject)null);
      var1.setlocal("test_remove_entire_manifest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_no_compiler$2(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      String[] var3 = new String[]{"query_vcvarsall"};
      PyObject[] var6 = imp.importFrom("distutils.msvc9compiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(111);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _find_vcvarsall$3, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(114);
      var3 = new String[]{"msvc9compiler"};
      var6 = imp.importFrom("distutils", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(115);
      PyObject var8 = var1.getlocal(3).__getattr__("find_vcvarsall");
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(116);
      var8 = var1.getlocal(2);
      var1.getlocal(3).__setattr__("find_vcvarsall", var8);
      var3 = null;
      var3 = null;

      try {
         var1.setline(118);
         var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsPlatformError"), (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wont find this version"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(121);
         var4 = var1.getlocal(4);
         var1.getlocal(3).__setattr__("find_vcvarsall", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(121);
      var4 = var1.getlocal(4);
      var1.getlocal(3).__setattr__("find_vcvarsall", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _find_vcvarsall$3(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_reg_class$4(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      String[] var3 = new String[]{"Reg"};
      PyObject[] var5 = imp.importFrom("distutils.msvc9compiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(125);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyError"), var1.getlocal(1).__getattr__("get_value"), PyString.fromInterned("xxx"), PyString.fromInterned("xxx"));
      var1.setline(129);
      PyString var6 = PyString.fromInterned("Control Panel\\Desktop");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(130);
      PyObject var7 = var1.getlocal(1).__getattr__("get_value").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyUnicode.fromInterned("dragfullwindows"));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(131);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var7 = var1.getlocal(3);
      PyObject var10002 = var7._in(new PyTuple(new PyObject[]{PyUnicode.fromInterned("0"), PyUnicode.fromInterned("1"), PyUnicode.fromInterned("2")}));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(133);
      var7 = imp.importOne("_winreg", var1, -1);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(134);
      var7 = var1.getlocal(4).__getattr__("HKEY_CURRENT_USER");
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(135);
      var7 = var1.getlocal(1).__getattr__("read_keys").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("xxxx"));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(136);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getglobal("None"));
      var1.setline(138);
      var7 = var1.getlocal(1).__getattr__("read_keys").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("Control Panel"));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(139);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var6 = PyString.fromInterned("Desktop");
      var10002 = var6._in(var1.getlocal(6));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_remove_visual_c_ref$5(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      String[] var3 = new String[]{"MSVCCompiler"};
      PyObject[] var8 = imp.importFrom("distutils.msvc9compiler", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(143);
      PyObject var9 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(144);
      var9 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("manifest"));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(145);
      var9 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var9);
      var3 = null;
      var3 = null;

      try {
         var1.setline(147);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getglobal("_MANIFEST_WITH_MULTIPLE_REFERENCES"));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(149);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(149);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(151);
      var9 = var1.getlocal(1).__call__(var2);
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(152);
      var1.getlocal(5).__getattr__("_remove_visual_c_ref").__call__(var2, var1.getlocal(3));
      var1.setline(155);
      var9 = var1.getglobal("open").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var9);
      var3 = null;
      var3 = null;

      try {
         var1.setline(158);
         PyObject var10000 = PyString.fromInterned("\n").__getattr__("join");
         PyList var10002 = new PyList();
         var4 = var10002.__getattr__("append");
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(158);
         var4 = var1.getlocal(4).__getattr__("readlines").__call__(var2).__iter__();

         while(true) {
            var1.setline(158);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(158);
               var1.dellocal(7);
               var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(6, var4);
               var4 = null;
               break;
            }

            var1.setlocal(8, var5);
            var1.setline(158);
            var1.getlocal(7).__call__(var2, var1.getlocal(8).__getattr__("rstrip").__call__(var2));
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(160);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(160);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(163);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getglobal("_CLEANED_MANIFEST"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_remove_entire_manifest$6(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      String[] var3 = new String[]{"MSVCCompiler"};
      PyObject[] var6 = imp.importFrom("distutils.msvc9compiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(167);
      PyObject var7 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(168);
      var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("manifest"));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(169);
      var7 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var7);
      var3 = null;
      var3 = null;

      try {
         var1.setline(171);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getglobal("_MANIFEST_WITH_ONLY_MSVC_REFERENCE"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(173);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(173);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(175);
      var7 = var1.getlocal(1).__call__(var2);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(176);
      var7 = var1.getlocal(5).__getattr__("_remove_visual_c_ref").__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(177);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(6), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$7(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("msvc9compilerTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_msvc9compiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      msvc9compilerTestCase$1 = Py.newCode(0, var2, var1, "msvc9compilerTestCase", 102, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "query_vcvarsall", "_find_vcvarsall", "msvc9compiler", "old_find_vcvarsall"};
      test_no_compiler$2 = Py.newCode(1, var2, var1, "test_no_compiler", 106, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"version"};
      _find_vcvarsall$3 = Py.newCode(1, var2, var1, "_find_vcvarsall", 111, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Reg", "path", "v", "_winreg", "HKCU", "keys"};
      test_reg_class$4 = Py.newCode(1, var2, var1, "test_reg_class", 123, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "MSVCCompiler", "tempdir", "manifest", "f", "compiler", "content", "_[158_33]", "line"};
      test_remove_visual_c_ref$5 = Py.newCode(1, var2, var1, "test_remove_visual_c_ref", 141, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "MSVCCompiler", "tempdir", "manifest", "f", "compiler", "got"};
      test_remove_entire_manifest$6 = Py.newCode(1, var2, var1, "test_remove_entire_manifest", 165, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$7 = Py.newCode(0, var2, var1, "test_suite", 180, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_msvc9compiler$py("distutils/tests/test_msvc9compiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_msvc9compiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.msvc9compilerTestCase$1(var2, var3);
         case 2:
            return this.test_no_compiler$2(var2, var3);
         case 3:
            return this._find_vcvarsall$3(var2, var3);
         case 4:
            return this.test_reg_class$4(var2, var3);
         case 5:
            return this.test_remove_visual_c_ref$5(var2, var3);
         case 6:
            return this.test_remove_entire_manifest$6(var2, var3);
         case 7:
            return this.test_suite$7(var2, var3);
         default:
            return null;
      }
   }
}
