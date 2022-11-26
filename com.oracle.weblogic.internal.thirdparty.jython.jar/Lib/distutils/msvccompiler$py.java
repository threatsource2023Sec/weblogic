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
@MTime(1498849383000L)
@Filename("distutils/msvccompiler.py")
public class msvccompiler$py extends PyFunctionTable implements PyRunnable {
   static msvccompiler$py self;
   static final PyCode f$0;
   static final PyCode read_keys$1;
   static final PyCode read_values$2;
   static final PyCode convert_mbcs$3;
   static final PyCode MacroExpander$4;
   static final PyCode __init__$5;
   static final PyCode set_macro$6;
   static final PyCode load_macros$7;
   static final PyCode sub$8;
   static final PyCode get_build_version$9;
   static final PyCode get_build_architecture$10;
   static final PyCode normalize_and_reduce_paths$11;
   static final PyCode MSVCCompiler$12;
   static final PyCode __init__$13;
   static final PyCode initialize$14;
   static final PyCode object_filenames$15;
   static final PyCode compile$16;
   static final PyCode create_static_lib$17;
   static final PyCode link$18;
   static final PyCode library_dir_option$19;
   static final PyCode runtime_library_dir_option$20;
   static final PyCode library_option$21;
   static final PyCode find_library_file$22;
   static final PyCode find_exe$23;
   static final PyCode get_msvc_paths$24;
   static final PyCode set_path_env_var$25;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.msvccompiler\n\nContains MSVCCompiler, an implementation of the abstract CCompiler class\nfor the Microsoft Visual Studio.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.msvccompiler\n\nContains MSVCCompiler, an implementation of the abstract CCompiler class\nfor the Microsoft Visual Studio.\n");
      var1.setline(11);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(13);
      PyObject var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var1.setline(14);
      var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(15);
      var7 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var7);
      var3 = null;
      var1.setline(17);
      String[] var8 = new String[]{"DistutilsExecError", "DistutilsPlatformError", "CompileError", "LibError", "LinkError"};
      PyObject[] var9 = imp.importFrom("distutils.errors", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var9[3];
      var1.setlocal("LibError", var4);
      var4 = null;
      var4 = var9[4];
      var1.setlocal("LinkError", var4);
      var4 = null;
      var1.setline(19);
      var8 = new String[]{"CCompiler", "gen_lib_options"};
      var9 = imp.importFrom("distutils.ccompiler", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var1.setline(20);
      var8 = new String[]{"log"};
      var9 = imp.importFrom("distutils", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(22);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal("_can_read_reg", var10);
      var3 = null;

      try {
         var1.setline(24);
         var7 = imp.importOne("_winreg", var1, -1);
         var1.setlocal("_winreg", var7);
         var3 = null;
         var1.setline(26);
         var10 = Py.newInteger(1);
         var1.setlocal("_can_read_reg", var10);
         var3 = null;
         var1.setline(27);
         var7 = var1.getname("_winreg");
         var1.setlocal("hkey_mod", var7);
         var3 = null;
         var1.setline(29);
         var7 = var1.getname("_winreg").__getattr__("OpenKeyEx");
         var1.setlocal("RegOpenKeyEx", var7);
         var3 = null;
         var1.setline(30);
         var7 = var1.getname("_winreg").__getattr__("EnumKey");
         var1.setlocal("RegEnumKey", var7);
         var3 = null;
         var1.setline(31);
         var7 = var1.getname("_winreg").__getattr__("EnumValue");
         var1.setlocal("RegEnumValue", var7);
         var3 = null;
         var1.setline(32);
         var7 = var1.getname("_winreg").__getattr__("error");
         var1.setlocal("RegError", var7);
         var3 = null;
      } catch (Throwable var6) {
         PyException var11 = Py.setException(var6, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         try {
            var1.setline(36);
            var4 = imp.importOne("win32api", var1, -1);
            var1.setlocal("win32api", var4);
            var4 = null;
            var1.setline(37);
            var4 = imp.importOne("win32con", var1, -1);
            var1.setlocal("win32con", var4);
            var4 = null;
            var1.setline(38);
            PyInteger var13 = Py.newInteger(1);
            var1.setlocal("_can_read_reg", var13);
            var4 = null;
            var1.setline(39);
            var4 = var1.getname("win32con");
            var1.setlocal("hkey_mod", var4);
            var4 = null;
            var1.setline(41);
            var4 = var1.getname("win32api").__getattr__("RegOpenKeyEx");
            var1.setlocal("RegOpenKeyEx", var4);
            var4 = null;
            var1.setline(42);
            var4 = var1.getname("win32api").__getattr__("RegEnumKey");
            var1.setlocal("RegEnumKey", var4);
            var4 = null;
            var1.setline(43);
            var4 = var1.getname("win32api").__getattr__("RegEnumValue");
            var1.setlocal("RegEnumValue", var4);
            var4 = null;
            var1.setline(44);
            var4 = var1.getname("win32api").__getattr__("error");
            var1.setlocal("RegError", var4);
            var4 = null;
         } catch (Throwable var5) {
            PyException var12 = Py.setException(var5, var1);
            if (!var12.match(var1.getname("ImportError"))) {
               throw var12;
            }

            var1.setline(47);
            var1.getname("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Warning: Can't read registry to find the necessary compiler setting\nMake sure that Python modules _winreg, win32api or win32con are installed."));
            var1.setline(51);
         }
      }

      var1.setline(53);
      if (var1.getname("_can_read_reg").__nonzero__()) {
         var1.setline(54);
         PyTuple var14 = new PyTuple(new PyObject[]{var1.getname("hkey_mod").__getattr__("HKEY_USERS"), var1.getname("hkey_mod").__getattr__("HKEY_CURRENT_USER"), var1.getname("hkey_mod").__getattr__("HKEY_LOCAL_MACHINE"), var1.getname("hkey_mod").__getattr__("HKEY_CLASSES_ROOT")});
         var1.setlocal("HKEYS", var14);
         var3 = null;
      }

      var1.setline(59);
      var9 = Py.EmptyObjects;
      PyFunction var15 = new PyFunction(var1.f_globals, var9, read_keys$1, PyString.fromInterned("Return list of registry keys."));
      var1.setlocal("read_keys", var15);
      var3 = null;
      var1.setline(77);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, read_values$2, PyString.fromInterned("Return dict of registry keys and values.\n\n    All names are converted to lowercase.\n    "));
      var1.setlocal("read_values", var15);
      var3 = null;
      var1.setline(98);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, convert_mbcs$3, (PyObject)null);
      var1.setlocal("convert_mbcs", var15);
      var3 = null;
      var1.setline(107);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("MacroExpander", var9, MacroExpander$4);
      var1.setlocal("MacroExpander", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(153);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, get_build_version$9, PyString.fromInterned("Return the version of MSVC that was used to build Python.\n\n    For Python 2.3 and up, the version number is included in\n    sys.version.  For earlier versions, assume the compiler is MSVC 6.\n    "));
      var1.setlocal("get_build_version", var15);
      var3 = null;
      var1.setline(176);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, get_build_architecture$10, PyString.fromInterned("Return the processor architecture.\n\n    Possible results are \"Intel\", \"Itanium\", or \"AMD64\".\n    "));
      var1.setlocal("get_build_architecture", var15);
      var3 = null;
      var1.setline(189);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, normalize_and_reduce_paths$11, PyString.fromInterned("Return a list of normalized paths with duplicates removed.\n\n    The current order of paths is maintained.\n    "));
      var1.setlocal("normalize_and_reduce_paths", var15);
      var3 = null;
      var1.setline(204);
      var9 = new PyObject[]{var1.getname("CCompiler")};
      var4 = Py.makeClass("MSVCCompiler", var9, MSVCCompiler$12);
      var1.setlocal("MSVCCompiler", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(654);
      var7 = var1.getname("get_build_version").__call__(var2);
      PyObject var10000 = var7._ge(Py.newFloat(8.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(655);
         var1.getname("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Importing new compiler from distutils.msvc9compiler"));
         var1.setline(656);
         var7 = var1.getname("MSVCCompiler");
         var1.setlocal("OldMSVCCompiler", var7);
         var3 = null;
         var1.setline(657);
         var8 = new String[]{"MSVCCompiler"};
         var9 = imp.importFrom("distutils.msvc9compiler", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("MSVCCompiler", var4);
         var4 = null;
         var1.setline(659);
         var8 = new String[]{"MacroExpander"};
         var9 = imp.importFrom("distutils.msvc9compiler", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("MacroExpander", var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_keys$1(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Return list of registry keys.");

      PyException var3;
      PyObject var4;
      PyObject var7;
      try {
         var1.setline(63);
         var7 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("RegError"))) {
            var1.setline(65);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(66);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(67);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(4, var9);
      var3 = null;

      while(true) {
         var1.setline(68);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         try {
            var1.setline(70);
            var7 = var1.getglobal("RegEnumKey").__call__(var2, var1.getlocal(2), var1.getlocal(4));
            var1.setlocal(5, var7);
            var3 = null;
         } catch (Throwable var6) {
            var3 = Py.setException(var6, var1);
            if (var3.match(var1.getglobal("RegError"))) {
               break;
            }

            throw var3;
         }

         var1.setline(73);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         var1.setline(74);
         var7 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(4, var7);
         var3 = null;
      }

      var1.setline(75);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject read_values$2(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Return dict of registry keys and values.\n\n    All names are converted to lowercase.\n    ");

      PyException var3;
      PyObject var4;
      PyObject var9;
      try {
         var1.setline(83);
         var9 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var9);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("RegError"))) {
            var1.setline(85);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(86);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(87);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(4, var11);
      var3 = null;

      while(true) {
         var1.setline(88);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         try {
            var1.setline(90);
            var9 = var1.getglobal("RegEnumValue").__call__(var2, var1.getlocal(2), var1.getlocal(4));
            PyObject[] var5 = Py.unpackSequence(var9, 3);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(7, var6);
            var6 = null;
            var3 = null;
         } catch (Throwable var8) {
            var3 = Py.setException(var8, var1);
            if (var3.match(var1.getglobal("RegError"))) {
               break;
            }

            throw var3;
         }

         var1.setline(93);
         var9 = var1.getlocal(5).__getattr__("lower").__call__(var2);
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(94);
         var9 = var1.getglobal("convert_mbcs").__call__(var2, var1.getlocal(6));
         var1.getlocal(3).__setitem__(var1.getglobal("convert_mbcs").__call__(var2, var1.getlocal(5)), var9);
         var3 = null;
         var1.setline(95);
         var9 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.setlocal(4, var9);
         var3 = null;
      }

      var1.setline(96);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject convert_mbcs$3(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("encode"), (PyObject)var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(102);
            var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
            var1.setlocal(0, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("UnicodeError"))) {
               throw var5;
            }

            var1.setline(104);
         }
      }

      var1.setline(105);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MacroExpander$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(109);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_macro$6, (PyObject)null);
      var1.setlocal("set_macro", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_macros$7, (PyObject)null);
      var1.setlocal("load_macros", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sub$8, (PyObject)null);
      var1.setlocal("sub", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"macros", var3);
      var3 = null;
      var1.setline(111);
      var1.getlocal(0).__getattr__("load_macros").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_macro$6(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getglobal("HKEYS").__iter__();

      while(true) {
         var1.setline(114);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(115);
         PyObject var5 = var1.getglobal("read_values").__call__(var2, var1.getlocal(4), var1.getlocal(2));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(116);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(117);
            var5 = var1.getlocal(5).__getitem__(var1.getlocal(3));
            var1.getlocal(0).__getattr__("macros").__setitem__(PyString.fromInterned("$(%s)")._mod(var1.getlocal(1)), var5);
            var5 = null;
            break;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_macros$7(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = PyString.fromInterned("Software\\Microsoft\\VisualStudio\\%0.1f")._mod(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(122);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("VCInstallDir"), (PyObject)var1.getlocal(2)._add(PyString.fromInterned("\\Setup\\VC")), (PyObject)PyString.fromInterned("productdir"));
      var1.setline(123);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("VSInstallDir"), (PyObject)var1.getlocal(2)._add(PyString.fromInterned("\\Setup\\VS")), (PyObject)PyString.fromInterned("productdir"));
      var1.setline(124);
      PyString var8 = PyString.fromInterned("Software\\Microsoft\\.NETFramework");
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(125);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkDir"), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("installroot"));

      try {
         var1.setline(127);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._gt(Py.newFloat(7.0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(128);
            var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkSDKDir"), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("sdkinstallrootv1.1"));
         } else {
            var1.setline(130);
            var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkSDKDir"), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("sdkinstallroot"));
         }
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (var9.match(var1.getglobal("KeyError"))) {
            var1.setline(132);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("Python was built with Visual Studio 2003;\nextensions must be built with a compiler than can generate compatible binaries.\nVisual Studio 2003 was not found on this system. If you have Cygwin installed,\nyou can try compiling with MingW32, by passing \"-c mingw32\" to setup.py."));
         }

         throw var9;
      }

      var1.setline(138);
      var8 = PyString.fromInterned("Software\\Microsoft\\NET Framework Setup\\Product");
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(139);
      var3 = var1.getglobal("HKEYS").__iter__();

      while(true) {
         var1.setline(139);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);

         PyException var5;
         PyObject var10;
         try {
            var1.setline(141);
            var10 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(5), var1.getlocal(4));
            var1.setlocal(6, var10);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("RegError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(144);
         var10 = var1.getglobal("RegEnumKey").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0));
         var1.setlocal(7, var10);
         var5 = null;
         var1.setline(145);
         var10 = var1.getglobal("read_values").__call__(var2, var1.getlocal(5), PyString.fromInterned("%s\\%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(7)})));
         var1.setlocal(8, var10);
         var5 = null;
         var1.setline(146);
         var10 = var1.getlocal(8).__getitem__(PyString.fromInterned("version"));
         var1.getlocal(0).__getattr__("macros").__setitem__((PyObject)PyString.fromInterned("$(FrameworkVersion)"), var10);
         var5 = null;
      }
   }

   public PyObject sub$8(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(0).__getattr__("macros").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(149);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(151);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(150);
         PyObject var7 = var1.getglobal("string").__getattr__("replace").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(1, var7);
         var5 = null;
      }
   }

   public PyObject get_build_version$9(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("Return the version of MSVC that was used to build Python.\n\n    For Python 2.3 and up, the version number is included in\n    sys.version.  For earlier versions, assume the compiler is MSVC 6.\n    ");
      var1.setline(160);
      PyString var3 = PyString.fromInterned("MSC v.");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(161);
      PyObject var7 = var1.getglobal("string").__getattr__("find").__call__(var2, var1.getglobal("sys").__getattr__("version"), var1.getlocal(0));
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(162);
      var7 = var1.getlocal(1);
      PyObject var10000 = var7._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         PyInteger var8 = Py.newInteger(6);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(164);
         PyObject var4 = var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(165);
         var4 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(166);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null))._sub(Py.newInteger(6));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(167);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(2), Py.newInteger(3), (PyObject)null))._div(Py.newFloat(10.0));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(169);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(170);
            PyInteger var9 = Py.newInteger(0);
            var1.setlocal(5, var9);
            var4 = null;
         }

         var1.setline(171);
         var4 = var1.getlocal(4);
         var10000 = var4._ge(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var7 = var1.getlocal(4)._add(var1.getlocal(5));
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(174);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject get_build_architecture$10(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Return the processor architecture.\n\n    Possible results are \"Intel\", \"Itanium\", or \"AMD64\".\n    ");
      var1.setline(182);
      PyString var3 = PyString.fromInterned(" bit (");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(183);
      PyObject var5 = var1.getglobal("string").__getattr__("find").__call__(var2, var1.getglobal("sys").__getattr__("version"), var1.getlocal(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(184);
      var5 = var1.getlocal(1);
      PyObject var10000 = var5._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(185);
         var3 = PyString.fromInterned("Intel");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(186);
         PyObject var4 = var1.getglobal("string").__getattr__("find").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("version"), (PyObject)PyString.fromInterned(")"), (PyObject)var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(187);
         var5 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0))), var1.getlocal(2), (PyObject)null);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject normalize_and_reduce_paths$11(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("Return a list of normalized paths with duplicates removed.\n\n    The current order of paths is maintained.\n    ");
      var1.setline(195);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(196);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(196);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(201);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(197);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(199);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._notin(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(200);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject MSVCCompiler$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete class that implements an interface to Microsoft Visual C++,\n       as defined by the CCompiler abstract class."));
      var1.setline(206);
      PyString.fromInterned("Concrete class that implements an interface to Microsoft Visual C++,\n       as defined by the CCompiler abstract class.");
      var1.setline(208);
      PyString var3 = PyString.fromInterned("msvc");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(215);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("executables", var4);
      var3 = null;
      var1.setline(218);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned(".c")});
      var1.setlocal("_c_extensions", var5);
      var3 = null;
      var1.setline(219);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".cc"), PyString.fromInterned(".cpp"), PyString.fromInterned(".cxx")});
      var1.setlocal("_cpp_extensions", var5);
      var3 = null;
      var1.setline(220);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".rc")});
      var1.setlocal("_rc_extensions", var5);
      var3 = null;
      var1.setline(221);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".mc")});
      var1.setlocal("_mc_extensions", var5);
      var3 = null;
      var1.setline(225);
      PyObject var6 = var1.getname("_c_extensions")._add(var1.getname("_cpp_extensions"))._add(var1.getname("_rc_extensions"))._add(var1.getname("_mc_extensions"));
      var1.setlocal("src_extensions", var6);
      var3 = null;
      var1.setline(227);
      var3 = PyString.fromInterned(".res");
      var1.setlocal("res_extension", var3);
      var3 = null;
      var1.setline(228);
      var3 = PyString.fromInterned(".obj");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(229);
      var3 = PyString.fromInterned(".lib");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(230);
      var3 = PyString.fromInterned(".dll");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(231);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("static_lib_format", var3);
      var1.setlocal("shared_lib_format", var3);
      var1.setline(232);
      var3 = PyString.fromInterned(".exe");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(234);
      PyObject[] var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(252);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, initialize$14, (PyObject)null);
      var1.setlocal("initialize", var8);
      var3 = null;
      var1.setline(316);
      var7 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var8 = new PyFunction(var1.f_globals, var7, object_filenames$15, (PyObject)null);
      var1.setlocal("object_filenames", var8);
      var3 = null;
      var1.setline(349);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, compile$16, (PyObject)null);
      var1.setlocal("compile", var8);
      var3 = null;
      var1.setline(438);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, create_static_lib$17, (PyObject)null);
      var1.setlocal("create_static_lib", var8);
      var3 = null;
      var1.setline(464);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, link$18, (PyObject)null);
      var1.setlocal("link", var8);
      var3 = null;
      var1.setline(548);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_dir_option$19, (PyObject)null);
      var1.setlocal("library_dir_option", var8);
      var3 = null;
      var1.setline(551);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, runtime_library_dir_option$20, (PyObject)null);
      var1.setlocal("runtime_library_dir_option", var8);
      var3 = null;
      var1.setline(555);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_option$21, (PyObject)null);
      var1.setlocal("library_option", var8);
      var3 = null;
      var1.setline(559);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, find_library_file$22, (PyObject)null);
      var1.setlocal("find_library_file", var8);
      var3 = null;
      var1.setline(579);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, find_exe$23, PyString.fromInterned("Return path to an MSVC executable program.\n\n        Tries to find the program in several places: first, one of the\n        MSVC program search paths from the registry; next, the directories\n        in the PATH environment variable.  If any of those work, return an\n        absolute path that is known to exist.  If none of them work, just\n        return the original program name, 'exe'.\n        "));
      var1.setlocal("find_exe", var8);
      var3 = null;
      var1.setline(602);
      var7 = new PyObject[]{PyString.fromInterned("x86")};
      var8 = new PyFunction(var1.f_globals, var7, get_msvc_paths$24, PyString.fromInterned("Get a list of devstudio directories (include, lib or path).\n\n        Return a list of strings.  The list will be empty if unable to\n        access the registry or appropriate registry keys not found.\n        "));
      var1.setlocal("get_msvc_paths", var8);
      var3 = null;
      var1.setline(639);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, set_path_env_var$25, PyString.fromInterned("Set environment variable 'name' to an MSVC path type value.\n\n        This is equivalent to a SET command prior to execution of spawned\n        commands.\n        "));
      var1.setlocal("set_path_env_var", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      var1.getglobal("CCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(236);
      PyObject var3 = var1.getglobal("get_build_version").__call__(var2);
      var1.getlocal(0).__setattr__("_MSVCCompiler__version", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getglobal("get_build_architecture").__call__(var2);
      var1.getlocal(0).__setattr__("_MSVCCompiler__arch", var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__arch");
      PyObject var10000 = var3._eq(PyString.fromInterned("Intel"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
         var10000 = var3._ge(Py.newInteger(7));
         var3 = null;
         PyString var4;
         if (var10000.__nonzero__()) {
            var1.setline(241);
            var4 = PyString.fromInterned("Software\\Microsoft\\VisualStudio");
            var1.getlocal(0).__setattr__((String)"_MSVCCompiler__root", var4);
            var3 = null;
            var1.setline(242);
            var3 = var1.getglobal("MacroExpander").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__version"));
            var1.getlocal(0).__setattr__("_MSVCCompiler__macros", var3);
            var3 = null;
         } else {
            var1.setline(244);
            var4 = PyString.fromInterned("Software\\Microsoft\\Devstudio");
            var1.getlocal(0).__setattr__((String)"_MSVCCompiler__root", var4);
            var3 = null;
         }

         var1.setline(245);
         var3 = PyString.fromInterned("Visual Studio version %s")._mod(var1.getlocal(0).__getattr__("_MSVCCompiler__version"));
         var1.getlocal(0).__setattr__("_MSVCCompiler__product", var3);
         var3 = null;
      } else {
         var1.setline(248);
         var3 = PyString.fromInterned("Microsoft SDK compiler %s")._mod(var1.getlocal(0).__getattr__("_MSVCCompiler__version")._add(Py.newInteger(6)));
         var1.getlocal(0).__setattr__("_MSVCCompiler__product", var3);
         var3 = null;
      }

      var1.setline(250);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("initialized", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initialize$14(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_MSVCCompiler__paths", var3);
      var3 = null;
      var1.setline(254);
      PyString var6 = PyString.fromInterned("DISTUTILS_USE_SDK");
      PyObject var10000 = var6._in(var1.getglobal("os").__getattr__("environ"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var6 = PyString.fromInterned("MSSdk");
         var10000 = var6._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cl.exe"));
         }
      }

      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(257);
         var6 = PyString.fromInterned("cl.exe");
         var1.getlocal(0).__setattr__((String)"cc", var6);
         var3 = null;
         var1.setline(258);
         var6 = PyString.fromInterned("link.exe");
         var1.getlocal(0).__setattr__((String)"linker", var6);
         var3 = null;
         var1.setline(259);
         var6 = PyString.fromInterned("lib.exe");
         var1.getlocal(0).__setattr__((String)"lib", var6);
         var3 = null;
         var1.setline(260);
         var6 = PyString.fromInterned("rc.exe");
         var1.getlocal(0).__setattr__((String)"rc", var6);
         var3 = null;
         var1.setline(261);
         var6 = PyString.fromInterned("mc.exe");
         var1.getlocal(0).__setattr__((String)"mc", var6);
         var3 = null;
      } else {
         var1.setline(263);
         var7 = var1.getlocal(0).__getattr__("get_msvc_paths").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("path"));
         var1.getlocal(0).__setattr__("_MSVCCompiler__paths", var7);
         var3 = null;
         var1.setline(265);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__paths"));
         var10000 = var7._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(266);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("Python was built with %s, and extensions need to be built with the same version of the compiler, but it isn't installed.")._mod(var1.getlocal(0).__getattr__("_MSVCCompiler__product")));
         }

         var1.setline(271);
         var7 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cl.exe"));
         var1.getlocal(0).__setattr__("cc", var7);
         var3 = null;
         var1.setline(272);
         var7 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("link.exe"));
         var1.getlocal(0).__setattr__("linker", var7);
         var3 = null;
         var1.setline(273);
         var7 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lib.exe"));
         var1.getlocal(0).__setattr__("lib", var7);
         var3 = null;
         var1.setline(274);
         var7 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rc.exe"));
         var1.getlocal(0).__setattr__("rc", var7);
         var3 = null;
         var1.setline(275);
         var7 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mc.exe"));
         var1.getlocal(0).__setattr__("mc", var7);
         var3 = null;
         var1.setline(276);
         var1.getlocal(0).__getattr__("set_path_env_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lib"));
         var1.setline(277);
         var1.getlocal(0).__getattr__("set_path_env_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include"));
      }

      try {
         var1.setline(281);
         var7 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("path")), (PyObject)PyString.fromInterned(";")).__iter__();

         while(true) {
            var1.setline(281);
            PyObject var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(282);
            var1.getlocal(0).__getattr__("_MSVCCompiler__paths").__getattr__("append").__call__(var2, var1.getlocal(1));
         }
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getglobal("KeyError"))) {
            throw var8;
         }

         var1.setline(284);
      }

      var1.setline(285);
      var7 = var1.getglobal("normalize_and_reduce_paths").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__paths"));
      var1.getlocal(0).__setattr__("_MSVCCompiler__paths", var7);
      var3 = null;
      var1.setline(286);
      var7 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_MSVCCompiler__paths"), (PyObject)PyString.fromInterned(";"));
      var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("path"), var7);
      var3 = null;
      var1.setline(288);
      var7 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("preprocess_options", var7);
      var3 = null;
      var1.setline(289);
      var7 = var1.getlocal(0).__getattr__("_MSVCCompiler__arch");
      var10000 = var7._eq(PyString.fromInterned("Intel"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Ox"), PyString.fromInterned("/MD"), PyString.fromInterned("/W3"), PyString.fromInterned("/GX"), PyString.fromInterned("/DNDEBUG")});
         var1.getlocal(0).__setattr__((String)"compile_options", var3);
         var3 = null;
         var1.setline(292);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Od"), PyString.fromInterned("/MDd"), PyString.fromInterned("/W3"), PyString.fromInterned("/GX"), PyString.fromInterned("/Z7"), PyString.fromInterned("/D_DEBUG")});
         var1.getlocal(0).__setattr__((String)"compile_options_debug", var3);
         var3 = null;
      } else {
         var1.setline(296);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Ox"), PyString.fromInterned("/MD"), PyString.fromInterned("/W3"), PyString.fromInterned("/GS-"), PyString.fromInterned("/DNDEBUG")});
         var1.getlocal(0).__setattr__((String)"compile_options", var3);
         var3 = null;
         var1.setline(298);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Od"), PyString.fromInterned("/MDd"), PyString.fromInterned("/W3"), PyString.fromInterned("/GS-"), PyString.fromInterned("/Z7"), PyString.fromInterned("/D_DEBUG")});
         var1.getlocal(0).__setattr__((String)"compile_options_debug", var3);
         var3 = null;
      }

      var1.setline(301);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("/DLL"), PyString.fromInterned("/nologo"), PyString.fromInterned("/INCREMENTAL:NO")});
      var1.getlocal(0).__setattr__((String)"ldflags_shared", var3);
      var3 = null;
      var1.setline(302);
      var7 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
      var10000 = var7._ge(Py.newInteger(7));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(303);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/DLL"), PyString.fromInterned("/nologo"), PyString.fromInterned("/INCREMENTAL:no"), PyString.fromInterned("/DEBUG")});
         var1.getlocal(0).__setattr__((String)"ldflags_shared_debug", var3);
         var3 = null;
      } else {
         var1.setline(307);
         var3 = new PyList(new PyObject[]{PyString.fromInterned("/DLL"), PyString.fromInterned("/nologo"), PyString.fromInterned("/INCREMENTAL:no"), PyString.fromInterned("/pdb:None"), PyString.fromInterned("/DEBUG")});
         var1.getlocal(0).__setattr__((String)"ldflags_shared_debug", var3);
         var3 = null;
      }

      var1.setline(310);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("/nologo")});
      var1.getlocal(0).__setattr__((String)"ldflags_static", var3);
      var3 = null;
      var1.setline(312);
      var7 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("initialized", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject object_filenames$15(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(322);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(323);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(324);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(324);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(344);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(325);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(5));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(326);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(6)).__getitem__(Py.newInteger(1));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(327);
         var5 = var1.getlocal(6).__getslice__(var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(6)), (PyObject)null, (PyObject)null);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(328);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(332);
            throw Py.makeException(var1.getglobal("CompileError").__call__(var2, PyString.fromInterned("Don't know how to compile %s")._mod(var1.getlocal(5))));
         }

         var1.setline(333);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(334);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(335);
         var5 = var1.getlocal(7);
         var10000 = var5._in(var1.getlocal(0).__getattr__("_rc_extensions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(336);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("res_extension"))));
         } else {
            var1.setline(338);
            var5 = var1.getlocal(7);
            var10000 = var5._in(var1.getlocal(0).__getattr__("_mc_extensions"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(339);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("res_extension"))));
            } else {
               var1.setline(342);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
            }
         }
      }
   }

   public PyObject compile$16(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(353);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(354);
      PyObject var10000 = var1.getlocal(0).__getattr__("_setup_compile");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1), var1.getlocal(8), var1.getlocal(7)};
      PyObject var12 = var10000.__call__(var2, var3);
      PyObject[] var4 = Py.unpackSequence(var12, 5);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(358);
      Object var17 = var1.getlocal(6);
      if (!((PyObject)var17).__nonzero__()) {
         var17 = new PyList(Py.EmptyObjects);
      }

      Object var13 = var17;
      var1.setlocal(12, (PyObject)var13);
      var3 = null;
      var1.setline(359);
      var1.getlocal(12).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/c"));
      var1.setline(360);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(361);
         var1.getlocal(12).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options_debug"));
      } else {
         var1.setline(363);
         var1.getlocal(12).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options"));
      }

      var1.setline(365);
      var12 = var1.getlocal(9).__iter__();

      while(true) {
         var1.setline(365);
         PyObject var14 = var12.__iternext__();
         if (var14 == null) {
            var1.setline(433);
            var12 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var12;
         }

         var1.setlocal(13, var14);

         PyObject[] var6;
         PyObject var7;
         PyException var16;
         try {
            var1.setline(367);
            var5 = var1.getlocal(11).__getitem__(var1.getlocal(13));
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(14, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(15, var7);
            var7 = null;
            var5 = null;
         } catch (Throwable var11) {
            var16 = Py.setException(var11, var1);
            if (var16.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var16;
         }

         var1.setline(370);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(374);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(14));
            var1.setlocal(14, var5);
            var5 = null;
         }

         var1.setline(376);
         var5 = var1.getlocal(15);
         var10000 = var5._in(var1.getlocal(0).__getattr__("_c_extensions"));
         var5 = null;
         PyObject var15;
         if (var10000.__nonzero__()) {
            var1.setline(377);
            var5 = PyString.fromInterned("/Tc")._add(var1.getlocal(14));
            var1.setlocal(16, var5);
            var5 = null;
         } else {
            var1.setline(378);
            var5 = var1.getlocal(15);
            var10000 = var5._in(var1.getlocal(0).__getattr__("_cpp_extensions"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(380);
               var5 = var1.getlocal(15);
               var10000 = var5._in(var1.getlocal(0).__getattr__("_rc_extensions"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(382);
                  var5 = var1.getlocal(14);
                  var1.setlocal(16, var5);
                  var5 = null;
                  var1.setline(383);
                  var5 = PyString.fromInterned("/fo")._add(var1.getlocal(13));
                  var1.setlocal(17, var5);
                  var5 = null;

                  try {
                     var1.setline(385);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rc")}))._add(var1.getlocal(10))._add(new PyList(new PyObject[]{var1.getlocal(17)}))._add(new PyList(new PyObject[]{var1.getlocal(16)})));
                     continue;
                  } catch (Throwable var10) {
                     var16 = Py.setException(var10, var1);
                     if (var16.match(var1.getglobal("DistutilsExecError"))) {
                        var15 = var16.value;
                        var1.setlocal(18, var15);
                        var6 = null;
                        var1.setline(388);
                        throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(18));
                     }

                     throw var16;
                  }
               } else {
                  var1.setline(390);
                  var5 = var1.getlocal(15);
                  var10000 = var5._in(var1.getlocal(0).__getattr__("_mc_extensions"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(421);
                     throw Py.makeException(var1.getglobal("CompileError").__call__(var2, PyString.fromInterned("Don't know how to compile %s to %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(13)}))));
                  }

                  var1.setline(404);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(14));
                  var1.setlocal(19, var5);
                  var5 = null;
                  var1.setline(405);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(13));
                  var1.setlocal(20, var5);
                  var5 = null;

                  try {
                     var1.setline(408);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("mc")}))._add(new PyList(new PyObject[]{PyString.fromInterned("-h"), var1.getlocal(19), PyString.fromInterned("-r"), var1.getlocal(20)}))._add(new PyList(new PyObject[]{var1.getlocal(14)})));
                     var1.setline(410);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(14)));
                     var6 = Py.unpackSequence(var5, 2);
                     var7 = var6[0];
                     var1.setlocal(21, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(22, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(411);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(20), var1.getlocal(21)._add(PyString.fromInterned(".rc")));
                     var1.setlocal(23, var5);
                     var5 = null;
                     var1.setline(413);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rc")}))._add(new PyList(new PyObject[]{PyString.fromInterned("/fo")._add(var1.getlocal(13))}))._add(new PyList(new PyObject[]{var1.getlocal(23)})));
                     continue;
                  } catch (Throwable var8) {
                     var16 = Py.setException(var8, var1);
                     if (var16.match(var1.getglobal("DistutilsExecError"))) {
                        var15 = var16.value;
                        var1.setlocal(18, var15);
                        var6 = null;
                        var1.setline(417);
                        throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(18));
                     }

                     throw var16;
                  }
               }
            }

            var1.setline(379);
            var5 = PyString.fromInterned("/Tp")._add(var1.getlocal(14));
            var1.setlocal(16, var5);
            var5 = null;
         }

         var1.setline(425);
         var5 = PyString.fromInterned("/Fo")._add(var1.getlocal(13));
         var1.setlocal(17, var5);
         var5 = null;

         try {
            var1.setline(427);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("cc")}))._add(var1.getlocal(12))._add(var1.getlocal(10))._add(new PyList(new PyObject[]{var1.getlocal(16), var1.getlocal(17)}))._add(var1.getlocal(7)));
         } catch (Throwable var9) {
            var16 = Py.setException(var9, var1);
            if (var16.match(var1.getglobal("DistutilsExecError"))) {
               var15 = var16.value;
               var1.setlocal(18, var15);
               var6 = null;
               var1.setline(431);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(18));
            }

            throw var16;
         }
      }
   }

   public PyObject create_static_lib$17(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(445);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(447);
      PyObject var10000 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
      String[] var8 = new String[]{"output_dir"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(450);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(1), var1.getlocal(6)).__nonzero__()) {
         var1.setline(451);
         var3 = var1.getlocal(1)._add(new PyList(new PyObject[]{PyString.fromInterned("/OUT:")._add(var1.getlocal(6))}));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(452);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(453);
         }

         try {
            var1.setline(455);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("lib")}))._add(var1.getlocal(7)));
         } catch (Throwable var6) {
            PyException var10 = Py.setException(var6, var1);
            if (var10.match(var1.getglobal("DistutilsExecError"))) {
               PyObject var9 = var10.value;
               var1.setlocal(8, var9);
               var4 = null;
               var1.setline(457);
               throw Py.makeException(var1.getglobal("LibError"), var1.getlocal(8));
            }

            throw var10;
         }
      } else {
         var1.setline(460);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$18(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(479);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(480);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(481);
      var3 = var1.getlocal(0).__getattr__("_fix_lib_args").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(484);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(485);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("I don't know what to do with 'runtime_library_dirs': ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(7))));
      }

      var1.setline(488);
      var3 = var1.getglobal("gen_lib_options").__call__(var2, var1.getlocal(0), var1.getlocal(6), var1.getlocal(7), var1.getlocal(5));
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(491);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(492);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(494);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
         var1.setline(496);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("CCompiler").__getattr__("EXECUTABLE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(497);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(498);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared_debug").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            } else {
               var1.setline(500);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            }
         } else {
            var1.setline(502);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(503);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared_debug");
               var1.setlocal(15, var3);
               var3 = null;
            } else {
               var1.setline(505);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared");
               var1.setlocal(15, var3);
               var3 = null;
            }
         }

         var1.setline(507);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(16, var8);
         var3 = null;
         var1.setline(508);
         Object var10 = var1.getlocal(8);
         if (!((PyObject)var10).__nonzero__()) {
            var10 = new PyList(Py.EmptyObjects);
         }

         var3 = ((PyObject)var10).__iter__();

         while(true) {
            var1.setline(508);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               var1.setline(511);
               var3 = var1.getlocal(15)._add(var1.getlocal(14))._add(var1.getlocal(16))._add(var1.getlocal(2))._add(new PyList(new PyObject[]{PyString.fromInterned("/OUT:")._add(var1.getlocal(3))}));
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(519);
               var3 = var1.getlocal(8);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(520);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(19, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(20, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(522);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0))), var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(19)));
                  var1.setlocal(21, var3);
                  var3 = null;
                  var1.setline(525);
                  var1.getlocal(18).__getattr__("append").__call__(var2, PyString.fromInterned("/IMPLIB:")._add(var1.getlocal(21)));
               }

               var1.setline(527);
               if (var1.getlocal(10).__nonzero__()) {
                  var1.setline(528);
                  var3 = var1.getlocal(10);
                  var1.getlocal(18).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
                  var3 = null;
               }

               var1.setline(529);
               if (var1.getlocal(11).__nonzero__()) {
                  var1.setline(530);
                  var1.getlocal(18).__getattr__("extend").__call__(var2, var1.getlocal(11));
               }

               var1.setline(532);
               var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3)));

               try {
                  var1.setline(534);
                  var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("linker")}))._add(var1.getlocal(18)));
                  break;
               } catch (Throwable var6) {
                  PyException var9 = Py.setException(var6, var1);
                  if (var9.match(var1.getglobal("DistutilsExecError"))) {
                     var7 = var9.value;
                     var1.setlocal(22, var7);
                     var4 = null;
                     var1.setline(536);
                     throw Py.makeException(var1.getglobal("LinkError"), var1.getlocal(22));
                  }

                  throw var9;
               }
            }

            var1.setlocal(17, var7);
            var1.setline(509);
            var1.getlocal(16).__getattr__("append").__call__(var2, PyString.fromInterned("/EXPORT:")._add(var1.getlocal(17)));
         }
      } else {
         var1.setline(539);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject library_dir_option$19(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyObject var3 = PyString.fromInterned("/LIBPATH:")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runtime_library_dir_option$20(PyFrame var1, ThreadState var2) {
      var1.setline(552);
      throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to set runtime library search path for MSVC++"));
   }

   public PyObject library_option$21(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyObject var3 = var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_library_file$22(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyList var3;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(563);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("_d")), var1.getlocal(2)});
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(565);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(566);
      PyObject var8 = var1.getlocal(1).__iter__();

      label24:
      while(true) {
         var1.setline(566);
         PyObject var4 = var8.__iternext__();
         PyObject var7;
         if (var4 == null) {
            var1.setline(573);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(5, var4);
         var1.setline(567);
         PyObject var5 = var1.getlocal(4).__iter__();

         do {
            var1.setline(567);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               continue label24;
            }

            var1.setlocal(6, var6);
            var1.setline(568);
            var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(6)));
            var1.setlocal(7, var7);
            var7 = null;
            var1.setline(569);
         } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)).__nonzero__());

         var1.setline(570);
         var7 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject find_exe$23(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      PyString.fromInterned("Return path to an MSVC executable program.\n\n        Tries to find the program in several places: first, one of the\n        MSVC program search paths from the registry; next, the directories\n        in the PATH environment variable.  If any of those work, return an\n        absolute path that is known to exist.  If none of them work, just\n        return the original program name, 'exe'.\n        ");
      var1.setline(589);
      PyObject var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__paths").__iter__();

      PyObject var5;
      do {
         var1.setline(589);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(595);
            var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("Path")), (PyObject)PyString.fromInterned(";")).__iter__();

            do {
               var1.setline(595);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(600);
                  var5 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(2, var4);
               var1.setline(596);
               PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2)), var1.getlocal(1));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(597);
            } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__());

            var1.setline(598);
            var5 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(590);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2)), var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(591);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__());

      var1.setline(592);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject get_msvc_paths$24(PyFrame var1, ThreadState var2) {
      var1.setline(607);
      PyString.fromInterned("Get a list of devstudio directories (include, lib or path).\n\n        Return a list of strings.  The list will be empty if unable to\n        access the registry or appropriate registry keys not found.\n        ");
      var1.setline(609);
      PyList var7;
      if (var1.getglobal("_can_read_reg").__not__().__nonzero__()) {
         var1.setline(610);
         var7 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(612);
         PyObject var4 = var1.getlocal(1)._add(PyString.fromInterned(" dirs"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(613);
         var4 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
         PyObject var10000 = var4._ge(Py.newInteger(7));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(614);
            var4 = PyString.fromInterned("%s\\%0.1f\\VC\\VC_OBJECTS_PLATFORM_INFO\\Win32\\Directories")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_MSVCCompiler__root"), var1.getlocal(0).__getattr__("_MSVCCompiler__version")}));
            var1.setlocal(3, var4);
            var4 = null;
         } else {
            var1.setline(617);
            var4 = PyString.fromInterned("%s\\6.0\\Build System\\Components\\Platforms\\Win32 (%s)\\Directories")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_MSVCCompiler__root"), var1.getlocal(2)}));
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(620);
         var4 = var1.getglobal("HKEYS").__iter__();

         PyObject var6;
         do {
            var1.setline(620);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(629);
               var4 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
               var10000 = var4._eq(Py.newInteger(6));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(630);
                  var4 = var1.getglobal("HKEYS").__iter__();

                  while(true) {
                     var1.setline(630);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        break;
                     }

                     var1.setlocal(4, var5);
                     var1.setline(631);
                     var6 = var1.getglobal("read_values").__call__(var2, var1.getlocal(4), PyString.fromInterned("%s\\6.0")._mod(var1.getlocal(0).__getattr__("_MSVCCompiler__root")));
                     var10000 = var6._isnot(var1.getglobal("None"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(632);
                        var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("It seems you have Visual Studio 6 installed, but the expected registry settings are not present.\nYou must at least run the Visual Studio GUI once so that these entries are created."));
                        break;
                     }
                  }
               }

               var1.setline(637);
               var7 = new PyList(Py.EmptyObjects);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(4, var5);
            var1.setline(621);
            var6 = var1.getglobal("read_values").__call__(var2, var1.getlocal(4), var1.getlocal(3));
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(622);
         } while(!var1.getlocal(5).__nonzero__());

         var1.setline(623);
         var6 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
         var10000 = var6._ge(Py.newInteger(7));
         var6 = null;
         PyObject var3;
         if (var10000.__nonzero__()) {
            var1.setline(624);
            var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_MSVCCompiler__macros").__getattr__("sub").__call__(var2, var1.getlocal(5).__getitem__(var1.getlocal(1))), (PyObject)PyString.fromInterned(";"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(626);
            var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(var1.getlocal(1)), (PyObject)PyString.fromInterned(";"));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject set_path_env_var$25(PyFrame var1, ThreadState var2) {
      var1.setline(644);
      PyString.fromInterned("Set environment variable 'name' to an MSVC path type value.\n\n        This is equivalent to a SET command prior to execution of spawned\n        commands.\n        ");
      var1.setline(646);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("lib"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(647);
         var3 = var1.getlocal(0).__getattr__("get_msvc_paths").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("library"));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(649);
         var3 = var1.getlocal(0).__getattr__("get_msvc_paths").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(650);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(651);
         var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(";"));
         var1.getglobal("os").__getattr__("environ").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public msvccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"base", "key", "handle", "L", "i", "k"};
      read_keys$1 = Py.newCode(2, var2, var1, "read_keys", 59, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base", "key", "handle", "d", "i", "name", "value", "type"};
      read_values$2 = Py.newCode(2, var2, var1, "read_values", 77, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "enc"};
      convert_mbcs$3 = Py.newCode(1, var2, var1, "convert_mbcs", 98, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MacroExpander$4 = Py.newCode(0, var2, var1, "MacroExpander", 107, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 109, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "macro", "path", "key", "base", "d"};
      set_macro$6 = Py.newCode(4, var2, var1, "set_macro", 113, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "version", "vsbase", "net", "p", "base", "h", "key", "d"};
      load_macros$7 = Py.newCode(2, var2, var1, "load_macros", 120, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "k", "v"};
      sub$8 = Py.newCode(2, var2, var1, "sub", 148, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "i", "s", "rest", "majorVersion", "minorVersion"};
      get_build_version$9 = Py.newCode(0, var2, var1, "get_build_version", 153, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "i", "j"};
      get_build_architecture$10 = Py.newCode(0, var2, var1, "get_build_architecture", 176, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"paths", "reduced_paths", "p", "np"};
      normalize_and_reduce_paths$11 = Py.newCode(1, var2, var1, "normalize_and_reduce_paths", 189, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MSVCCompiler$12 = Py.newCode(0, var2, var1, "MSVCCompiler", 204, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force"};
      __init__$13 = Py.newCode(4, var2, var1, "__init__", 234, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "p"};
      initialize$14 = Py.newCode(1, var2, var1, "initialize", 252, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$15 = Py.newCode(4, var2, var1, "object_filenames", 316, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "output_dir", "macros", "include_dirs", "debug", "extra_preargs", "extra_postargs", "depends", "objects", "pp_opts", "build", "compile_opts", "obj", "src", "ext", "input_opt", "output_opt", "msg", "h_dir", "rc_dir", "base", "_", "rc_file"};
      compile$16 = Py.newCode(9, var2, var1, "compile", 349, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "debug", "target_lang", "output_filename", "lib_args", "msg"};
      create_static_lib$17 = Py.newCode(6, var2, var1, "create_static_lib", 438, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "lib_opts", "ldflags", "export_opts", "sym", "ld_args", "dll_name", "dll_ext", "implib_file", "msg"};
      link$18 = Py.newCode(14, var2, var1, "link", 464, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      library_dir_option$19 = Py.newCode(2, var2, var1, "library_dir_option", 548, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      runtime_library_dir_option$20 = Py.newCode(2, var2, var1, "runtime_library_dir_option", 551, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib"};
      library_option$21 = Py.newCode(2, var2, var1, "library_option", 555, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug", "try_names", "dir", "name", "libfile"};
      find_library_file$22 = Py.newCode(4, var2, var1, "find_library_file", 559, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exe", "p", "fn"};
      find_exe$23 = Py.newCode(2, var2, var1, "find_exe", 579, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "platform", "key", "base", "d"};
      get_msvc_paths$24 = Py.newCode(3, var2, var1, "get_msvc_paths", 602, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "p"};
      set_path_env_var$25 = Py.newCode(2, var2, var1, "set_path_env_var", 639, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new msvccompiler$py("distutils/msvccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(msvccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.read_keys$1(var2, var3);
         case 2:
            return this.read_values$2(var2, var3);
         case 3:
            return this.convert_mbcs$3(var2, var3);
         case 4:
            return this.MacroExpander$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.set_macro$6(var2, var3);
         case 7:
            return this.load_macros$7(var2, var3);
         case 8:
            return this.sub$8(var2, var3);
         case 9:
            return this.get_build_version$9(var2, var3);
         case 10:
            return this.get_build_architecture$10(var2, var3);
         case 11:
            return this.normalize_and_reduce_paths$11(var2, var3);
         case 12:
            return this.MSVCCompiler$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.initialize$14(var2, var3);
         case 15:
            return this.object_filenames$15(var2, var3);
         case 16:
            return this.compile$16(var2, var3);
         case 17:
            return this.create_static_lib$17(var2, var3);
         case 18:
            return this.link$18(var2, var3);
         case 19:
            return this.library_dir_option$19(var2, var3);
         case 20:
            return this.runtime_library_dir_option$20(var2, var3);
         case 21:
            return this.library_option$21(var2, var3);
         case 22:
            return this.find_library_file$22(var2, var3);
         case 23:
            return this.find_exe$23(var2, var3);
         case 24:
            return this.get_msvc_paths$24(var2, var3);
         case 25:
            return this.set_path_env_var$25(var2, var3);
         default:
            return null;
      }
   }
}
