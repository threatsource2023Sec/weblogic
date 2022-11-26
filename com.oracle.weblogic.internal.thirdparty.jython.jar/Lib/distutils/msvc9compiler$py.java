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
@Filename("distutils/msvc9compiler.py")
public class msvc9compiler$py extends PyFunctionTable implements PyRunnable {
   static msvc9compiler$py self;
   static final PyCode f$0;
   static final PyCode Reg$1;
   static final PyCode get_value$2;
   static final PyCode read_keys$3;
   static final PyCode read_values$4;
   static final PyCode convert_mbcs$5;
   static final PyCode MacroExpander$6;
   static final PyCode __init__$7;
   static final PyCode set_macro$8;
   static final PyCode load_macros$9;
   static final PyCode sub$10;
   static final PyCode get_build_version$11;
   static final PyCode normalize_and_reduce_paths$12;
   static final PyCode removeDuplicates$13;
   static final PyCode find_vcvarsall$14;
   static final PyCode query_vcvarsall$15;
   static final PyCode MSVCCompiler$16;
   static final PyCode __init__$17;
   static final PyCode initialize$18;
   static final PyCode object_filenames$19;
   static final PyCode compile$20;
   static final PyCode create_static_lib$21;
   static final PyCode link$22;
   static final PyCode manifest_setup_ldargs$23;
   static final PyCode manifest_get_embed_info$24;
   static final PyCode _remove_visual_c_ref$25;
   static final PyCode library_dir_option$26;
   static final PyCode runtime_library_dir_option$27;
   static final PyCode library_option$28;
   static final PyCode find_library_file$29;
   static final PyCode find_exe$30;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.msvc9compiler\n\nContains MSVCCompiler, an implementation of the abstract CCompiler class\nfor the Microsoft Visual Studio 2008.\n\nThe module is compatible with VS 2005 and VS 2008. You can find legacy support\nfor older versions of VS in distutils.msvccompiler.\n"));
      var1.setline(8);
      PyString.fromInterned("distutils.msvc9compiler\n\nContains MSVCCompiler, an implementation of the abstract CCompiler class\nfor the Microsoft Visual Studio 2008.\n\nThe module is compatible with VS 2005 and VS 2008. You can find legacy support\nfor older versions of VS in distutils.msvccompiler.\n");
      var1.setline(15);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(17);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(18);
      var5 = imp.importOne("subprocess", var1, -1);
      var1.setlocal("subprocess", var5);
      var3 = null;
      var1.setline(19);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(20);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(22);
      String[] var6 = new String[]{"DistutilsExecError", "DistutilsPlatformError", "CompileError", "LibError", "LinkError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("LibError", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("LinkError", var4);
      var4 = null;
      var1.setline(24);
      var6 = new String[]{"CCompiler", "gen_lib_options"};
      var7 = imp.importFrom("distutils.ccompiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var1.setline(25);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(26);
      var6 = new String[]{"get_platform"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(28);
      var5 = imp.importOne("_winreg", var1, -1);
      var1.setlocal("_winreg", var5);
      var3 = null;
      var1.setline(30);
      var5 = var1.getname("_winreg").__getattr__("OpenKeyEx");
      var1.setlocal("RegOpenKeyEx", var5);
      var3 = null;
      var1.setline(31);
      var5 = var1.getname("_winreg").__getattr__("EnumKey");
      var1.setlocal("RegEnumKey", var5);
      var3 = null;
      var1.setline(32);
      var5 = var1.getname("_winreg").__getattr__("EnumValue");
      var1.setlocal("RegEnumValue", var5);
      var3 = null;
      var1.setline(33);
      var5 = var1.getname("_winreg").__getattr__("error");
      var1.setlocal("RegError", var5);
      var3 = null;
      var1.setline(35);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getname("_winreg").__getattr__("HKEY_USERS"), var1.getname("_winreg").__getattr__("HKEY_CURRENT_USER"), var1.getname("_winreg").__getattr__("HKEY_LOCAL_MACHINE"), var1.getname("_winreg").__getattr__("HKEY_CLASSES_ROOT")});
      var1.setlocal("HKEYS", var8);
      var3 = null;
      var1.setline(40);
      var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var5._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var5 = var1.getname("sys").__getattr__("maxsize");
         var10000 = var5._gt(Py.newInteger(2)._pow(Py.newInteger(32)));
         var3 = null;
      }

      var5 = var10000;
      var1.setlocal("NATIVE_WIN64", var5);
      var3 = null;
      var1.setline(41);
      if (var1.getname("NATIVE_WIN64").__nonzero__()) {
         var1.setline(45);
         var3 = PyString.fromInterned("Software\\Wow6432Node\\Microsoft\\VisualStudio\\%0.1f");
         var1.setlocal("VS_BASE", var3);
         var3 = null;
         var1.setline(46);
         var3 = PyString.fromInterned("Software\\Wow6432Node\\Microsoft\\VCExpress\\%0.1f");
         var1.setlocal("VSEXPRESS_BASE", var3);
         var3 = null;
         var1.setline(47);
         var3 = PyString.fromInterned("Software\\Wow6432Node\\Microsoft\\Microsoft SDKs\\Windows");
         var1.setlocal("WINSDK_BASE", var3);
         var3 = null;
         var1.setline(48);
         var3 = PyString.fromInterned("Software\\Wow6432Node\\Microsoft\\.NETFramework");
         var1.setlocal("NET_BASE", var3);
         var3 = null;
      } else {
         var1.setline(50);
         var3 = PyString.fromInterned("Software\\Microsoft\\VisualStudio\\%0.1f");
         var1.setlocal("VS_BASE", var3);
         var3 = null;
         var1.setline(51);
         var3 = PyString.fromInterned("Software\\Microsoft\\VCExpress\\%0.1f");
         var1.setlocal("VSEXPRESS_BASE", var3);
         var3 = null;
         var1.setline(52);
         var3 = PyString.fromInterned("Software\\Microsoft\\Microsoft SDKs\\Windows");
         var1.setlocal("WINSDK_BASE", var3);
         var3 = null;
         var1.setline(53);
         var3 = PyString.fromInterned("Software\\Microsoft\\.NETFramework");
         var1.setlocal("NET_BASE", var3);
         var3 = null;
      }

      var1.setline(58);
      PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("win32"), PyString.fromInterned("x86"), PyString.fromInterned("win-amd64"), PyString.fromInterned("amd64"), PyString.fromInterned("win-ia64"), PyString.fromInterned("ia64")});
      var1.setlocal("PLAT_TO_VCVARS", var9);
      var3 = null;
      var1.setline(64);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Reg", var7, Reg$1);
      var1.setlocal("Reg", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(126);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("MacroExpander", var7, MacroExpander$6);
      var1.setlocal("MacroExpander", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(172);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, get_build_version$11, PyString.fromInterned("Return the version of MSVC that was used to build Python.\n\n    For Python 2.3 and up, the version number is included in\n    sys.version.  For earlier versions, assume the compiler is MSVC 6.\n    "));
      var1.setlocal("get_build_version", var10);
      var3 = null;
      var1.setline(194);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, normalize_and_reduce_paths$12, PyString.fromInterned("Return a list of normalized paths with duplicates removed.\n\n    The current order of paths is maintained.\n    "));
      var1.setlocal("normalize_and_reduce_paths", var10);
      var3 = null;
      var1.setline(208);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, removeDuplicates$13, PyString.fromInterned("Remove duplicate values of an environment variable.\n    "));
      var1.setlocal("removeDuplicates", var10);
      var3 = null;
      var1.setline(219);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, find_vcvarsall$14, PyString.fromInterned("Find the vcvarsall.bat file\n\n    At first it tries to find the productdir of VS 2008 in the registry. If\n    that fails it falls back to the VS90COMNTOOLS env var.\n    "));
      var1.setlocal("find_vcvarsall", var10);
      var3 = null;
      var1.setline(263);
      var7 = new PyObject[]{PyString.fromInterned("x86")};
      var10 = new PyFunction(var1.f_globals, var7, query_vcvarsall$15, PyString.fromInterned("Launch vcvarsall.bat and read the settings from its environment\n    "));
      var1.setlocal("query_vcvarsall", var10);
      var3 = null;
      var1.setline(304);
      var5 = var1.getname("get_build_version").__call__(var2);
      var1.setlocal("VERSION", var5);
      var3 = null;
      var1.setline(305);
      var5 = var1.getname("VERSION");
      var10000 = var5._lt(Py.newFloat(8.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(306);
         throw Py.makeException(var1.getname("DistutilsPlatformError").__call__(var2, PyString.fromInterned("VC %0.1f is not supported by this module")._mod(var1.getname("VERSION"))));
      } else {
         var1.setline(309);
         var7 = new PyObject[]{var1.getname("CCompiler")};
         var4 = Py.makeClass("MSVCCompiler", var7, MSVCCompiler$16);
         var1.setlocal("MSVCCompiler", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject Reg$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Helper class to read values from the registry\n    "));
      var1.setline(66);
      PyString.fromInterned("Helper class to read values from the registry\n    ");
      var1.setline(68);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, get_value$2, (PyObject)null);
      var1.setlocal("get_value", var4);
      var3 = null;
      var1.setline(74);
      PyObject var5 = var1.getname("classmethod").__call__(var2, var1.getname("get_value"));
      var1.setlocal("get_value", var5);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_keys$3, PyString.fromInterned("Return list of registry keys."));
      var1.setlocal("read_keys", var4);
      var3 = null;
      var1.setline(92);
      var5 = var1.getname("classmethod").__call__(var2, var1.getname("read_keys"));
      var1.setlocal("read_keys", var5);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read_values$4, PyString.fromInterned("Return dict of registry keys and values.\n\n        All names are converted to lowercase.\n        "));
      var1.setlocal("read_values", var4);
      var3 = null;
      var1.setline(114);
      var5 = var1.getname("classmethod").__call__(var2, var1.getname("read_values"));
      var1.setlocal("read_values", var5);
      var3 = null;
      var1.setline(116);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, convert_mbcs$5, (PyObject)null);
      var1.setlocal("convert_mbcs", var4);
      var3 = null;
      var1.setline(124);
      var5 = var1.getname("staticmethod").__call__(var2, var1.getname("convert_mbcs"));
      var1.setlocal("convert_mbcs", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get_value$2(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getglobal("HKEYS").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(69);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(73);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(2)));
         }

         var1.setlocal(3, var4);
         var1.setline(70);
         var5 = var1.getlocal(0).__getattr__("read_values").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(71);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2);
            var10000 = var5._in(var1.getlocal(4));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(72);
      var5 = var1.getlocal(4).__getitem__(var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject read_keys$3(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Return list of registry keys.");

      PyException var3;
      PyObject var4;
      PyObject var7;
      try {
         var1.setline(79);
         var7 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("RegError"))) {
            var1.setline(81);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(82);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(83);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(5, var9);
      var3 = null;

      while(true) {
         var1.setline(84);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         try {
            var1.setline(86);
            var7 = var1.getglobal("RegEnumKey").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            var1.setlocal(6, var7);
            var3 = null;
         } catch (Throwable var6) {
            var3 = Py.setException(var6, var1);
            if (var3.match(var1.getglobal("RegError"))) {
               break;
            }

            throw var3;
         }

         var1.setline(89);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
         var1.setline(90);
         var7 = var1.getlocal(5);
         var7 = var7._iadd(Py.newInteger(1));
         var1.setlocal(5, var7);
      }

      var1.setline(91);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject read_values$4(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("Return dict of registry keys and values.\n\n        All names are converted to lowercase.\n        ");

      PyException var3;
      PyObject var4;
      PyObject var9;
      try {
         var1.setline(100);
         var9 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var9);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("RegError"))) {
            var1.setline(102);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(103);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(104);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(5, var11);
      var3 = null;

      while(true) {
         var1.setline(105);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         try {
            var1.setline(107);
            var9 = var1.getglobal("RegEnumValue").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            PyObject[] var5 = Py.unpackSequence(var9, 3);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(8, var6);
            var6 = null;
            var3 = null;
         } catch (Throwable var8) {
            var3 = Py.setException(var8, var1);
            if (var3.match(var1.getglobal("RegError"))) {
               break;
            }

            throw var3;
         }

         var1.setline(110);
         var9 = var1.getlocal(6).__getattr__("lower").__call__(var2);
         var1.setlocal(6, var9);
         var3 = null;
         var1.setline(111);
         var9 = var1.getlocal(0).__getattr__("convert_mbcs").__call__(var2, var1.getlocal(7));
         var1.getlocal(4).__setitem__(var1.getlocal(0).__getattr__("convert_mbcs").__call__(var2, var1.getlocal(6)), var9);
         var3 = null;
         var1.setline(112);
         var9 = var1.getlocal(5);
         var9 = var9._iadd(Py.newInteger(1));
         var1.setlocal(5, var9);
      }

      var1.setline(113);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject convert_mbcs$5(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("decode"), (PyObject)var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(120);
            var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
            var1.setlocal(0, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("UnicodeError"))) {
               throw var5;
            }

            var1.setline(122);
         }
      }

      var1.setline(123);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MacroExpander$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(128);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_macro$8, (PyObject)null);
      var1.setlocal("set_macro", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_macros$9, (PyObject)null);
      var1.setlocal("load_macros", var4);
      var3 = null;
      var1.setline(167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sub$10, (PyObject)null);
      var1.setlocal("sub", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"macros", var3);
      var3 = null;
      var1.setline(130);
      PyObject var4 = var1.getglobal("VS_BASE")._mod(var1.getlocal(1));
      var1.getlocal(0).__setattr__("vsbase", var4);
      var3 = null;
      var1.setline(131);
      var1.getlocal(0).__getattr__("load_macros").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_macro$8(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var3 = var1.getglobal("Reg").__getattr__("get_value").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.getlocal(0).__getattr__("macros").__setitem__(PyString.fromInterned("$(%s)")._mod(var1.getlocal(1)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_macros$9(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("VCInstallDir"), (PyObject)var1.getlocal(0).__getattr__("vsbase")._add(PyString.fromInterned("\\Setup\\VC")), (PyObject)PyString.fromInterned("productdir"));
      var1.setline(138);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("VSInstallDir"), (PyObject)var1.getlocal(0).__getattr__("vsbase")._add(PyString.fromInterned("\\Setup\\VS")), (PyObject)PyString.fromInterned("productdir"));
      var1.setline(139);
      var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkDir"), (PyObject)var1.getglobal("NET_BASE"), (PyObject)PyString.fromInterned("installroot"));

      PyObject var10000;
      PyException var3;
      PyObject var8;
      try {
         var1.setline(141);
         var8 = var1.getlocal(1);
         var10000 = var8._ge(Py.newFloat(8.0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(145);
            throw Py.makeException(var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sdkinstallrootv2.0")));
         }

         var1.setline(142);
         var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkSDKDir"), (PyObject)var1.getglobal("NET_BASE"), (PyObject)PyString.fromInterned("sdkinstallrootv2.0"));
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(147);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Python was built with Visual Studio 2008;\nextensions must be built with a compiler than can generate compatible binaries.\nVisual Studio 2008 was not found on this system. If you have Cygwin installed,\nyou can try compiling with MingW32, by passing \"-c mingw32\" to setup.py.")));
         }

         throw var3;
      }

      var1.setline(153);
      var8 = var1.getlocal(1);
      var10000 = var8._ge(Py.newFloat(9.0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(154);
         var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("FrameworkVersion"), (PyObject)var1.getlocal(0).__getattr__("vsbase"), (PyObject)PyString.fromInterned("clr version"));
         var1.setline(155);
         var1.getlocal(0).__getattr__("set_macro").__call__((ThreadState)var2, PyString.fromInterned("WindowsSdkDir"), (PyObject)var1.getglobal("WINSDK_BASE"), (PyObject)PyString.fromInterned("currentinstallfolder"));
      } else {
         var1.setline(157);
         PyString var9 = PyString.fromInterned("Software\\Microsoft\\NET Framework Setup\\Product");
         var1.setlocal(2, var9);
         var3 = null;
         var1.setline(158);
         var8 = var1.getglobal("HKEYS").__iter__();

         label44:
         while(true) {
            PyException var5;
            PyObject var10;
            while(true) {
               var1.setline(158);
               PyObject var4 = var8.__iternext__();
               if (var4 == null) {
                  break label44;
               }

               var1.setlocal(3, var4);

               try {
                  var1.setline(160);
                  var10 = var1.getglobal("RegOpenKeyEx").__call__(var2, var1.getlocal(3), var1.getlocal(2));
                  var1.setlocal(4, var10);
                  var5 = null;
                  break;
               } catch (Throwable var6) {
                  var5 = Py.setException(var6, var1);
                  if (!var5.match(var1.getglobal("RegError"))) {
                     throw var5;
                  }
               }
            }

            var1.setline(163);
            var10 = var1.getglobal("RegEnumKey").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
            var1.setlocal(5, var10);
            var5 = null;
            var1.setline(164);
            var10 = var1.getglobal("Reg").__getattr__("get_value").__call__(var2, var1.getlocal(3), PyString.fromInterned("%s\\%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5)})));
            var1.setlocal(6, var10);
            var5 = null;
            var1.setline(165);
            var10 = var1.getlocal(6).__getitem__(PyString.fromInterned("version"));
            var1.getlocal(0).__getattr__("macros").__setitem__((PyObject)PyString.fromInterned("$(FrameworkVersion)"), var10);
            var5 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sub$10(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyObject var3 = var1.getlocal(0).__getattr__("macros").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(168);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(170);
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
         var1.setline(169);
         PyObject var7 = var1.getlocal(1).__getattr__("replace").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(1, var7);
         var5 = null;
      }
   }

   public PyObject get_build_version$11(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyString.fromInterned("Return the version of MSVC that was used to build Python.\n\n    For Python 2.3 and up, the version number is included in\n    sys.version.  For earlier versions, assume the compiler is MSVC 6.\n    ");
      var1.setline(178);
      PyString var3 = PyString.fromInterned("MSC v.");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(179);
      PyObject var7 = var1.getglobal("sys").__getattr__("version").__getattr__("find").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(180);
      var7 = var1.getlocal(1);
      PyObject var10000 = var7._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(181);
         PyInteger var8 = Py.newInteger(6);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(182);
         PyObject var4 = var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(183);
         var4 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)Py.newInteger(1));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(184);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null))._sub(Py.newInteger(6));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(185);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(2), Py.newInteger(3), (PyObject)null))._div(Py.newFloat(10.0));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(187);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(188);
            PyInteger var9 = Py.newInteger(0);
            var1.setlocal(5, var9);
            var4 = null;
         }

         var1.setline(189);
         var4 = var1.getlocal(4);
         var10000 = var4._ge(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var7 = var1.getlocal(4)._add(var1.getlocal(5));
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(192);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject normalize_and_reduce_paths$12(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyString.fromInterned("Return a list of normalized paths with duplicates removed.\n\n    The current order of paths is maintained.\n    ");
      var1.setline(200);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(201);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(201);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(206);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(202);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(204);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._notin(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(205);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject removeDuplicates$13(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyString.fromInterned("Remove duplicate values of an environment variable.\n    ");
      var1.setline(211);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(212);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(213);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(213);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(216);
            var3 = var1.getglobal("os").__getattr__("pathsep").__getattr__("join").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(217);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(214);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._notin(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject find_vcvarsall$14(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyString.fromInterned("Find the vcvarsall.bat file\n\n    At first it tries to find the productdir of VS 2008 in the registry. If\n    that fails it falls back to the VS90COMNTOOLS env var.\n    ");
      var1.setline(225);
      PyObject var3 = var1.getglobal("VS_BASE")._mod(var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyException var7;
      try {
         var1.setline(227);
         var3 = var1.getglobal("Reg").__getattr__("get_value").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s\\Setup\\VC")._mod(var1.getlocal(1)), (PyObject)PyString.fromInterned("productdir"));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var6) {
         var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("KeyError"))) {
            throw var7;
         }

         var1.setline(230);
         var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(233);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(234);
         var3 = var1.getglobal("VSEXPRESS_BASE")._mod(var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;

         try {
            var1.setline(236);
            var3 = var1.getglobal("Reg").__getattr__("get_value").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s\\Setup\\VC")._mod(var1.getlocal(1)), (PyObject)PyString.fromInterned("productdir"));
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("KeyError"))) {
               throw var7;
            }

            var1.setline(239);
            var4 = var1.getglobal("None");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(240);
            var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unable to find productdir in registry"));
         }
      }

      var1.setline(242);
      var10000 = var1.getlocal(2).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(243);
         var3 = PyString.fromInterned("VS%0.f0COMNTOOLS")._mod(var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(244);
         var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(246);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(4));
         }

         if (var10000.__nonzero__()) {
            var1.setline(247);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getglobal("os").__getattr__("pardir"), var1.getglobal("os").__getattr__("pardir"), PyString.fromInterned("VC"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(248);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(249);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
               var1.setline(250);
               var1.getglobal("log").__getattr__("debug").__call__(var2, PyString.fromInterned("%s is not a valid directory")._mod(var1.getlocal(2)));
               var1.setline(251);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(253);
            var1.getglobal("log").__getattr__("debug").__call__(var2, PyString.fromInterned("Env var %s is not set or invalid")._mod(var1.getlocal(3)));
         }
      }

      var1.setline(254);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(255);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No productdir found"));
         var1.setline(256);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(257);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("vcvarsall.bat"));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(258);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__nonzero__()) {
            var1.setline(259);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(260);
            var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unable to find vcvarsall.bat"));
            var1.setline(261);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject query_vcvarsall$15(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Launch vcvarsall.bat and read the settings from its environment\n    ");
      var1.setline(266);
      PyObject var3 = var1.getglobal("find_vcvarsall").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(267);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("include"), PyString.fromInterned("lib"), PyString.fromInterned("libpath"), PyString.fromInterned("path")})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(268);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(270);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(271);
         throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unable to find vcvarsall.bat")));
      } else {
         var1.setline(272);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, PyString.fromInterned("Calling 'vcvarsall.bat %s' (version=%s)"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(0));
         var1.setline(273);
         var10000 = var1.getglobal("subprocess").__getattr__("Popen");
         PyObject[] var13 = new PyObject[]{PyString.fromInterned("\"%s\" %s & set")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})), var1.getglobal("subprocess").__getattr__("PIPE"), var1.getglobal("subprocess").__getattr__("PIPE")};
         String[] var4 = new String[]{"stdout", "stderr"};
         var10000 = var10000.__call__(var2, var13, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(277);
            PyObject var11 = var1.getlocal(5).__getattr__("communicate").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var11, 2);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
            var1.setline(278);
            var11 = var1.getlocal(5).__getattr__("wait").__call__(var2);
            var10000 = var11._ne(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(279);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, var1.getlocal(7).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"))));
            }

            var1.setline(281);
            var11 = var1.getlocal(6).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
            var1.setlocal(6, var11);
            var4 = null;
            var1.setline(282);
            var11 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

            while(true) {
               var1.setline(282);
               PyObject var12 = var11.__iternext__();
               if (var12 == null) {
                  break;
               }

               var1.setlocal(8, var12);
               var1.setline(283);
               var6 = var1.getglobal("Reg").__getattr__("convert_mbcs").__call__(var2, var1.getlocal(8));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(284);
               PyString var14 = PyString.fromInterned("=");
               var10000 = var14._notin(var1.getlocal(8));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(286);
                  var6 = var1.getlocal(8).__getattr__("strip").__call__(var2);
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(287);
                  var6 = var1.getlocal(8).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(9, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(10, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(288);
                  var6 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(289);
                  var6 = var1.getlocal(9);
                  var10000 = var6._in(var1.getlocal(3));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(290);
                     if (var1.getlocal(10).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("pathsep")).__nonzero__()) {
                        var1.setline(291);
                        var6 = var1.getlocal(10).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                        var1.setlocal(10, var6);
                        var6 = null;
                     }

                     var1.setline(292);
                     var6 = var1.getglobal("removeDuplicates").__call__(var2, var1.getlocal(10));
                     var1.getlocal(4).__setitem__(var1.getlocal(9), var6);
                     var6 = null;
                  }
               }
            }
         } catch (Throwable var9) {
            Py.addTraceback(var9, var1);
            var1.setline(295);
            var1.getlocal(5).__getattr__("stdout").__getattr__("close").__call__(var2);
            var1.setline(296);
            var1.getlocal(5).__getattr__("stderr").__getattr__("close").__call__(var2);
            throw (Throwable)var9;
         }

         var1.setline(295);
         var1.getlocal(5).__getattr__("stdout").__getattr__("close").__call__(var2);
         var1.setline(296);
         var1.getlocal(5).__getattr__("stderr").__getattr__("close").__call__(var2);
         var1.setline(298);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(299);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(4).__getattr__("keys").__call__(var2)))));
         } else {
            var1.setline(301);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject MSVCCompiler$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete class that implements an interface to Microsoft Visual C++,\n       as defined by the CCompiler abstract class."));
      var1.setline(311);
      PyString.fromInterned("Concrete class that implements an interface to Microsoft Visual C++,\n       as defined by the CCompiler abstract class.");
      var1.setline(313);
      PyString var3 = PyString.fromInterned("msvc");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(320);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("executables", var4);
      var3 = null;
      var1.setline(323);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned(".c")});
      var1.setlocal("_c_extensions", var5);
      var3 = null;
      var1.setline(324);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".cc"), PyString.fromInterned(".cpp"), PyString.fromInterned(".cxx")});
      var1.setlocal("_cpp_extensions", var5);
      var3 = null;
      var1.setline(325);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".rc")});
      var1.setlocal("_rc_extensions", var5);
      var3 = null;
      var1.setline(326);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".mc")});
      var1.setlocal("_mc_extensions", var5);
      var3 = null;
      var1.setline(330);
      PyObject var6 = var1.getname("_c_extensions")._add(var1.getname("_cpp_extensions"))._add(var1.getname("_rc_extensions"))._add(var1.getname("_mc_extensions"));
      var1.setlocal("src_extensions", var6);
      var3 = null;
      var1.setline(332);
      var3 = PyString.fromInterned(".res");
      var1.setlocal("res_extension", var3);
      var3 = null;
      var1.setline(333);
      var3 = PyString.fromInterned(".obj");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(334);
      var3 = PyString.fromInterned(".lib");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(335);
      var3 = PyString.fromInterned(".dll");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(336);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("static_lib_format", var3);
      var1.setlocal("shared_lib_format", var3);
      var1.setline(337);
      var3 = PyString.fromInterned(".exe");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(339);
      PyObject[] var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(350);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, initialize$18, (PyObject)null);
      var1.setlocal("initialize", var8);
      var3 = null;
      var1.setline(437);
      var7 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var8 = new PyFunction(var1.f_globals, var7, object_filenames$19, (PyObject)null);
      var1.setlocal("object_filenames", var8);
      var3 = null;
      var1.setline(468);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, compile$20, (PyObject)null);
      var1.setlocal("compile", var8);
      var3 = null;
      var1.setline(553);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, create_static_lib$21, (PyObject)null);
      var1.setlocal("create_static_lib", var8);
      var3 = null;
      var1.setline(578);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, link$22, (PyObject)null);
      var1.setlocal("link", var8);
      var3 = null;
      var1.setline(673);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, manifest_setup_ldargs$23, (PyObject)null);
      var1.setlocal("manifest_setup_ldargs", var8);
      var3 = null;
      var1.setline(685);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, manifest_get_embed_info$24, (PyObject)null);
      var1.setlocal("manifest_get_embed_info", var8);
      var3 = null;
      var1.setline(709);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _remove_visual_c_ref$25, (PyObject)null);
      var1.setlocal("_remove_visual_c_ref", var8);
      var3 = null;
      var1.setline(752);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_dir_option$26, (PyObject)null);
      var1.setlocal("library_dir_option", var8);
      var3 = null;
      var1.setline(755);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, runtime_library_dir_option$27, (PyObject)null);
      var1.setlocal("runtime_library_dir_option", var8);
      var3 = null;
      var1.setline(759);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_option$28, (PyObject)null);
      var1.setlocal("library_option", var8);
      var3 = null;
      var1.setline(763);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, find_library_file$29, (PyObject)null);
      var1.setlocal("find_library_file", var8);
      var3 = null;
      var1.setline(781);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, find_exe$30, PyString.fromInterned("Return path to an MSVC executable program.\n\n        Tries to find the program in several places: first, one of the\n        MSVC program search paths from the registry; next, the directories\n        in the PATH environment variable.  If any of those work, return an\n        absolute path that is known to exist.  If none of them work, just\n        return the original program name, 'exe'.\n        "));
      var1.setlocal("find_exe", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      var1.getglobal("CCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(341);
      PyObject var3 = var1.getglobal("VERSION");
      var1.getlocal(0).__setattr__("_MSVCCompiler__version", var3);
      var3 = null;
      var1.setline(342);
      PyString var4 = PyString.fromInterned("Software\\Microsoft\\VisualStudio");
      var1.getlocal(0).__setattr__((String)"_MSVCCompiler__root", var4);
      var3 = null;
      var1.setline(344);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_MSVCCompiler__paths", var5);
      var3 = null;
      var1.setline(346);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(347);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_MSVCCompiler__arch", var3);
      var3 = null;
      var1.setline(348);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("initialized", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initialize$18(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("don't init multiple times"));
      } else {
         var1.setline(353);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(354);
            var3 = var1.getglobal("get_platform").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(356);
         PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("win32"), PyString.fromInterned("win-amd64"), PyString.fromInterned("win-ia64")});
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(357);
         var3 = var1.getlocal(1);
         var10000 = var3._notin(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(358);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, PyString.fromInterned("--plat-name must be one of %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}))));
         } else {
            var1.setline(361);
            PyString var7 = PyString.fromInterned("DISTUTILS_USE_SDK");
            var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var7 = PyString.fromInterned("MSSdk");
               var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cl.exe"));
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(364);
               var7 = PyString.fromInterned("cl.exe");
               var1.getlocal(0).__setattr__((String)"cc", var7);
               var3 = null;
               var1.setline(365);
               var7 = PyString.fromInterned("link.exe");
               var1.getlocal(0).__setattr__((String)"linker", var7);
               var3 = null;
               var1.setline(366);
               var7 = PyString.fromInterned("lib.exe");
               var1.getlocal(0).__setattr__((String)"lib", var7);
               var3 = null;
               var1.setline(367);
               var7 = PyString.fromInterned("rc.exe");
               var1.getlocal(0).__setattr__((String)"rc", var7);
               var3 = null;
               var1.setline(368);
               var7 = PyString.fromInterned("mc.exe");
               var1.getlocal(0).__setattr__((String)"mc", var7);
               var3 = null;
            } else {
               var1.setline(375);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(var1.getglobal("get_platform").__call__(var2));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(1);
                  var10000 = var3._eq(PyString.fromInterned("win32"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(377);
                  var3 = var1.getglobal("PLAT_TO_VCVARS").__getitem__(var1.getlocal(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(380);
                  var3 = var1.getglobal("PLAT_TO_VCVARS").__getitem__(var1.getglobal("get_platform").__call__(var2))._add(PyString.fromInterned("_"))._add(var1.getglobal("PLAT_TO_VCVARS").__getitem__(var1.getlocal(1)));
                  var1.setlocal(3, var3);
                  var3 = null;
               }

               var1.setline(383);
               var3 = var1.getglobal("query_vcvarsall").__call__(var2, var1.getglobal("VERSION"), var1.getlocal(3));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(386);
               var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("path")).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs")).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
               var1.getlocal(0).__setattr__("_MSVCCompiler__paths", var3);
               var3 = null;
               var1.setline(387);
               var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("lib")).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
               var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("lib"), var3);
               var3 = null;
               var1.setline(388);
               var3 = var1.getlocal(4).__getitem__(PyString.fromInterned("include")).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
               var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("include"), var3);
               var3 = null;
               var1.setline(390);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__paths"));
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(391);
                  throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, PyString.fromInterned("Python was built with %s, and extensions need to be built with the same version of the compiler, but it isn't installed.")._mod(var1.getlocal(0).__getattr__("_MSVCCompiler__product"))));
               }

               var1.setline(396);
               var3 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cl.exe"));
               var1.getlocal(0).__setattr__("cc", var3);
               var3 = null;
               var1.setline(397);
               var3 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("link.exe"));
               var1.getlocal(0).__setattr__("linker", var3);
               var3 = null;
               var1.setline(398);
               var3 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lib.exe"));
               var1.getlocal(0).__setattr__("lib", var3);
               var3 = null;
               var1.setline(399);
               var3 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rc.exe"));
               var1.getlocal(0).__setattr__("rc", var3);
               var3 = null;
               var1.setline(400);
               var3 = var1.getlocal(0).__getattr__("find_exe").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mc.exe"));
               var1.getlocal(0).__setattr__("mc", var3);
               var3 = null;
            }

            try {
               var1.setline(406);
               var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("path")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

               while(true) {
                  var1.setline(406);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(407);
                  var1.getlocal(0).__getattr__("_MSVCCompiler__paths").__getattr__("append").__call__(var2, var1.getlocal(5));
               }
            } catch (Throwable var5) {
               PyException var8 = Py.setException(var5, var1);
               if (!var8.match(var1.getglobal("KeyError"))) {
                  throw var8;
               }

               var1.setline(409);
            }

            var1.setline(410);
            var3 = var1.getglobal("normalize_and_reduce_paths").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__paths"));
            var1.getlocal(0).__setattr__("_MSVCCompiler__paths", var3);
            var3 = null;
            var1.setline(411);
            var3 = PyString.fromInterned(";").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_MSVCCompiler__paths"));
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("path"), var3);
            var3 = null;
            var1.setline(413);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("preprocess_options", var3);
            var3 = null;
            var1.setline(414);
            var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__arch");
            var10000 = var3._eq(PyString.fromInterned("x86"));
            var3 = null;
            PyList var9;
            if (var10000.__nonzero__()) {
               var1.setline(415);
               var9 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Ox"), PyString.fromInterned("/MD"), PyString.fromInterned("/W3"), PyString.fromInterned("/DNDEBUG")});
               var1.getlocal(0).__setattr__((String)"compile_options", var9);
               var3 = null;
               var1.setline(417);
               var9 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Od"), PyString.fromInterned("/MDd"), PyString.fromInterned("/W3"), PyString.fromInterned("/Z7"), PyString.fromInterned("/D_DEBUG")});
               var1.getlocal(0).__setattr__((String)"compile_options_debug", var9);
               var3 = null;
            } else {
               var1.setline(421);
               var9 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Ox"), PyString.fromInterned("/MD"), PyString.fromInterned("/W3"), PyString.fromInterned("/GS-"), PyString.fromInterned("/DNDEBUG")});
               var1.getlocal(0).__setattr__((String)"compile_options", var9);
               var3 = null;
               var1.setline(423);
               var9 = new PyList(new PyObject[]{PyString.fromInterned("/nologo"), PyString.fromInterned("/Od"), PyString.fromInterned("/MDd"), PyString.fromInterned("/W3"), PyString.fromInterned("/GS-"), PyString.fromInterned("/Z7"), PyString.fromInterned("/D_DEBUG")});
               var1.getlocal(0).__setattr__((String)"compile_options_debug", var9);
               var3 = null;
            }

            var1.setline(426);
            var9 = new PyList(new PyObject[]{PyString.fromInterned("/DLL"), PyString.fromInterned("/nologo"), PyString.fromInterned("/INCREMENTAL:NO")});
            var1.getlocal(0).__setattr__((String)"ldflags_shared", var9);
            var3 = null;
            var1.setline(427);
            var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__version");
            var10000 = var3._ge(Py.newInteger(7));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(428);
               var9 = new PyList(new PyObject[]{PyString.fromInterned("/DLL"), PyString.fromInterned("/nologo"), PyString.fromInterned("/INCREMENTAL:no"), PyString.fromInterned("/DEBUG"), PyString.fromInterned("/pdb:None")});
               var1.getlocal(0).__setattr__((String)"ldflags_shared_debug", var9);
               var3 = null;
            }

            var1.setline(431);
            var9 = new PyList(new PyObject[]{PyString.fromInterned("/nologo")});
            var1.getlocal(0).__setattr__((String)"ldflags_static", var9);
            var3 = null;
            var1.setline(433);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("initialized", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject object_filenames$19(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(443);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(444);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(445);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(445);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(465);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(446);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(5));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(447);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(6)).__getitem__(Py.newInteger(1));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(448);
         var5 = var1.getlocal(6).__getslice__(var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(6)), (PyObject)null, (PyObject)null);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(449);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(453);
            throw Py.makeException(var1.getglobal("CompileError").__call__(var2, PyString.fromInterned("Don't know how to compile %s")._mod(var1.getlocal(5))));
         }

         var1.setline(454);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(455);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(456);
         var5 = var1.getlocal(7);
         var10000 = var5._in(var1.getlocal(0).__getattr__("_rc_extensions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(457);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("res_extension"))));
         } else {
            var1.setline(459);
            var5 = var1.getlocal(7);
            var10000 = var5._in(var1.getlocal(0).__getattr__("_mc_extensions"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(460);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("res_extension"))));
            } else {
               var1.setline(463);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
            }
         }
      }
   }

   public PyObject compile$20(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(473);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(474);
      PyObject var10000 = var1.getlocal(0).__getattr__("_setup_compile");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1), var1.getlocal(8), var1.getlocal(7)};
      PyObject var12 = var10000.__call__(var2, var3);
      var1.setlocal(9, var12);
      var3 = null;
      var1.setline(476);
      var12 = var1.getlocal(9);
      PyObject[] var4 = Py.unpackSequence(var12, 5);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(12, var5);
      var5 = null;
      var3 = null;
      var1.setline(478);
      Object var17 = var1.getlocal(6);
      if (!((PyObject)var17).__nonzero__()) {
         var17 = new PyList(Py.EmptyObjects);
      }

      Object var14 = var17;
      var1.setlocal(13, (PyObject)var14);
      var3 = null;
      var1.setline(479);
      var1.getlocal(13).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/c"));
      var1.setline(480);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(481);
         var1.getlocal(13).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options_debug"));
      } else {
         var1.setline(483);
         var1.getlocal(13).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options"));
      }

      var1.setline(485);
      var12 = var1.getlocal(10).__iter__();

      while(true) {
         var1.setline(485);
         PyObject var13 = var12.__iternext__();
         if (var13 == null) {
            var1.setline(550);
            var12 = var1.getlocal(10);
            var1.f_lasti = -1;
            return var12;
         }

         var1.setlocal(14, var13);

         PyObject[] var6;
         PyObject var7;
         PyException var16;
         try {
            var1.setline(487);
            var5 = var1.getlocal(12).__getitem__(var1.getlocal(14));
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(15, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(16, var7);
            var7 = null;
            var5 = null;
         } catch (Throwable var11) {
            var16 = Py.setException(var11, var1);
            if (var16.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var16;
         }

         var1.setline(490);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(494);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(15));
            var1.setlocal(15, var5);
            var5 = null;
         }

         var1.setline(496);
         var5 = var1.getlocal(16);
         var10000 = var5._in(var1.getlocal(0).__getattr__("_c_extensions"));
         var5 = null;
         PyObject var15;
         if (var10000.__nonzero__()) {
            var1.setline(497);
            var5 = PyString.fromInterned("/Tc")._add(var1.getlocal(15));
            var1.setlocal(17, var5);
            var5 = null;
         } else {
            var1.setline(498);
            var5 = var1.getlocal(16);
            var10000 = var5._in(var1.getlocal(0).__getattr__("_cpp_extensions"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(500);
               var5 = var1.getlocal(16);
               var10000 = var5._in(var1.getlocal(0).__getattr__("_rc_extensions"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(502);
                  var5 = var1.getlocal(15);
                  var1.setlocal(17, var5);
                  var5 = null;
                  var1.setline(503);
                  var5 = PyString.fromInterned("/fo")._add(var1.getlocal(14));
                  var1.setlocal(18, var5);
                  var5 = null;

                  try {
                     var1.setline(505);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rc")}))._add(var1.getlocal(11))._add(new PyList(new PyObject[]{var1.getlocal(18)}))._add(new PyList(new PyObject[]{var1.getlocal(17)})));
                     continue;
                  } catch (Throwable var8) {
                     var16 = Py.setException(var8, var1);
                     if (var16.match(var1.getglobal("DistutilsExecError"))) {
                        var15 = var16.value;
                        var1.setlocal(19, var15);
                        var6 = null;
                        var1.setline(508);
                        throw Py.makeException(var1.getglobal("CompileError").__call__(var2, var1.getlocal(19)));
                     }

                     throw var16;
                  }
               } else {
                  var1.setline(510);
                  var5 = var1.getlocal(16);
                  var10000 = var5._in(var1.getlocal(0).__getattr__("_mc_extensions"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(539);
                     throw Py.makeException(var1.getglobal("CompileError").__call__(var2, PyString.fromInterned("Don't know how to compile %s to %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(15), var1.getlocal(14)}))));
                  }

                  var1.setline(522);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(15));
                  var1.setlocal(20, var5);
                  var5 = null;
                  var1.setline(523);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(14));
                  var1.setlocal(21, var5);
                  var5 = null;

                  try {
                     var1.setline(526);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("mc")}))._add(new PyList(new PyObject[]{PyString.fromInterned("-h"), var1.getlocal(20), PyString.fromInterned("-r"), var1.getlocal(21)}))._add(new PyList(new PyObject[]{var1.getlocal(15)})));
                     var1.setline(528);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(15)));
                     var6 = Py.unpackSequence(var5, 2);
                     var7 = var6[0];
                     var1.setlocal(22, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(23, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(529);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(21), var1.getlocal(22)._add(PyString.fromInterned(".rc")));
                     var1.setlocal(24, var5);
                     var5 = null;
                     var1.setline(531);
                     var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("rc")}))._add(new PyList(new PyObject[]{PyString.fromInterned("/fo")._add(var1.getlocal(14))}))._add(new PyList(new PyObject[]{var1.getlocal(24)})));
                     continue;
                  } catch (Throwable var10) {
                     var16 = Py.setException(var10, var1);
                     if (var16.match(var1.getglobal("DistutilsExecError"))) {
                        var15 = var16.value;
                        var1.setlocal(19, var15);
                        var6 = null;
                        var1.setline(535);
                        throw Py.makeException(var1.getglobal("CompileError").__call__(var2, var1.getlocal(19)));
                     }

                     throw var16;
                  }
               }
            }

            var1.setline(499);
            var5 = PyString.fromInterned("/Tp")._add(var1.getlocal(15));
            var1.setlocal(17, var5);
            var5 = null;
         }

         var1.setline(542);
         var5 = PyString.fromInterned("/Fo")._add(var1.getlocal(14));
         var1.setlocal(18, var5);
         var5 = null;

         try {
            var1.setline(544);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("cc")}))._add(var1.getlocal(13))._add(var1.getlocal(11))._add(new PyList(new PyObject[]{var1.getlocal(17), var1.getlocal(18)}))._add(var1.getlocal(7)));
         } catch (Throwable var9) {
            var16 = Py.setException(var9, var1);
            if (var16.match(var1.getglobal("DistutilsExecError"))) {
               var15 = var16.value;
               var1.setlocal(19, var15);
               var6 = null;
               var1.setline(548);
               throw Py.makeException(var1.getglobal("CompileError").__call__(var2, var1.getlocal(19)));
            }

            throw var16;
         }
      }
   }

   public PyObject create_static_lib$21(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(561);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(562);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(563);
      PyObject var10000 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
      String[] var8 = new String[]{"output_dir"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(566);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(1), var1.getlocal(6)).__nonzero__()) {
         var1.setline(567);
         var3 = var1.getlocal(1)._add(new PyList(new PyObject[]{PyString.fromInterned("/OUT:")._add(var1.getlocal(6))}));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(568);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(569);
         }

         try {
            var1.setline(571);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("lib")}))._add(var1.getlocal(7)));
         } catch (Throwable var6) {
            PyException var10 = Py.setException(var6, var1);
            if (var10.match(var1.getglobal("DistutilsExecError"))) {
               PyObject var9 = var10.value;
               var1.setlocal(8, var9);
               var4 = null;
               var1.setline(573);
               throw Py.makeException(var1.getglobal("LibError").__call__(var2, var1.getlocal(8)));
            }

            throw var10;
         }
      } else {
         var1.setline(575);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$22(PyFrame var1, ThreadState var2) {
      var1.setline(593);
      if (var1.getlocal(0).__getattr__("initialized").__not__().__nonzero__()) {
         var1.setline(594);
         var1.getlocal(0).__getattr__("initialize").__call__(var2);
      }

      var1.setline(595);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(596);
      var3 = var1.getlocal(0).__getattr__("_fix_lib_args").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(598);
      var3 = var1.getlocal(14);
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
      var1.setline(600);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(601);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("I don't know what to do with 'runtime_library_dirs': ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(7))));
      }

      var1.setline(604);
      var3 = var1.getglobal("gen_lib_options").__call__(var2, var1.getlocal(0), var1.getlocal(6), var1.getlocal(7), var1.getlocal(5));
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(607);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(608);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(610);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
         var1.setline(611);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("CCompiler").__getattr__("EXECUTABLE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(612);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(613);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared_debug").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(16, var3);
               var3 = null;
            } else {
               var1.setline(615);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(16, var3);
               var3 = null;
            }
         } else {
            var1.setline(617);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(618);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared_debug");
               var1.setlocal(16, var3);
               var3 = null;
            } else {
               var1.setline(620);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared");
               var1.setlocal(16, var3);
               var3 = null;
            }
         }

         var1.setline(622);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(17, var9);
         var3 = null;
         var1.setline(623);
         Object var11 = var1.getlocal(8);
         if (!((PyObject)var11).__nonzero__()) {
            var11 = new PyList(Py.EmptyObjects);
         }

         var3 = ((PyObject)var11).__iter__();

         while(true) {
            var1.setline(623);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               var1.setline(626);
               var3 = var1.getlocal(16)._add(var1.getlocal(15))._add(var1.getlocal(17))._add(var1.getlocal(2))._add(new PyList(new PyObject[]{PyString.fromInterned("/OUT:")._add(var1.getlocal(3))}));
               var1.setlocal(19, var3);
               var3 = null;
               var1.setline(634);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(635);
               var3 = var1.getlocal(8);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(636);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(20, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(21, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(638);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(12), var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(20)));
                  var1.setlocal(22, var3);
                  var3 = null;
                  var1.setline(641);
                  var1.getlocal(19).__getattr__("append").__call__(var2, PyString.fromInterned("/IMPLIB:")._add(var1.getlocal(22)));
               }

               var1.setline(643);
               var1.getlocal(0).__getattr__("manifest_setup_ldargs").__call__(var2, var1.getlocal(3), var1.getlocal(12), var1.getlocal(19));
               var1.setline(645);
               if (var1.getlocal(10).__nonzero__()) {
                  var1.setline(646);
                  var3 = var1.getlocal(10);
                  var1.getlocal(19).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
                  var3 = null;
               }

               var1.setline(647);
               if (var1.getlocal(11).__nonzero__()) {
                  var1.setline(648);
                  var1.getlocal(19).__getattr__("extend").__call__(var2, var1.getlocal(11));
               }

               var1.setline(650);
               var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3)));

               PyException var10;
               try {
                  var1.setline(652);
                  var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("linker")}))._add(var1.getlocal(19)));
               } catch (Throwable var7) {
                  var10 = Py.setException(var7, var1);
                  if (var10.match(var1.getglobal("DistutilsExecError"))) {
                     var8 = var10.value;
                     var1.setlocal(23, var8);
                     var4 = null;
                     var1.setline(654);
                     throw Py.makeException(var1.getglobal("LinkError").__call__(var2, var1.getlocal(23)));
                  }

                  throw var10;
               }

               var1.setline(661);
               var3 = var1.getlocal(0).__getattr__("manifest_get_embed_info").__call__(var2, var1.getlocal(1), var1.getlocal(19));
               var1.setlocal(24, var3);
               var3 = null;
               var1.setline(662);
               var3 = var1.getlocal(24);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(663);
                  var3 = var1.getlocal(24);
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(25, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(26, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(664);
                  var3 = PyString.fromInterned("-outputresource:%s;%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(26)}));
                  var1.setlocal(27, var3);
                  var3 = null;

                  try {
                     var1.setline(666);
                     var1.getlocal(0).__getattr__("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("mt.exe"), PyString.fromInterned("-nologo"), PyString.fromInterned("-manifest"), var1.getlocal(25), var1.getlocal(27)})));
                  } catch (Throwable var6) {
                     var10 = Py.setException(var6, var1);
                     if (var10.match(var1.getglobal("DistutilsExecError"))) {
                        var8 = var10.value;
                        var1.setlocal(23, var8);
                        var4 = null;
                        var1.setline(669);
                        throw Py.makeException(var1.getglobal("LinkError").__call__(var2, var1.getlocal(23)));
                     }

                     throw var10;
                  }
               }
               break;
            }

            var1.setlocal(18, var8);
            var1.setline(624);
            var1.getlocal(17).__getattr__("append").__call__(var2, PyString.fromInterned("/EXPORT:")._add(var1.getlocal(18)));
         }
      } else {
         var1.setline(671);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject manifest_setup_ldargs$23(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned(".manifest")));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(683);
      var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("/MANIFESTFILE:")._add(var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject manifest_get_embed_info$24(PyFrame var1, ThreadState var2) {
      var1.setline(690);
      PyObject var3 = var1.getlocal(2).__iter__();

      PyObject var5;
      do {
         var1.setline(690);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(696);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(691);
      } while(!var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/MANIFESTFILE:")).__nonzero__());

      var1.setline(692);
      var5 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1));
      var1.setlocal(4, var5);
      var5 = null;
      var1.setline(697);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("CCompiler").__getattr__("EXECUTABLE"));
      var3 = null;
      PyInteger var6;
      if (var10000.__nonzero__()) {
         var1.setline(700);
         var6 = Py.newInteger(1);
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(703);
         var6 = Py.newInteger(2);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(704);
         var3 = var1.getlocal(0).__getattr__("_remove_visual_c_ref").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(705);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(706);
         var5 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(707);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _remove_visual_c_ref$25(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject library_dir_option$26(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3 = PyString.fromInterned("/LIBPATH:")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runtime_library_dir_option$27(PyFrame var1, ThreadState var2) {
      var1.setline(756);
      throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("don't know how to set runtime library search path for MSVC++")));
   }

   public PyObject library_option$28(PyFrame var1, ThreadState var2) {
      var1.setline(760);
      PyObject var3 = var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_library_file$29(PyFrame var1, ThreadState var2) {
      var1.setline(766);
      PyList var3;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(767);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("_d")), var1.getlocal(2)});
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(769);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(770);
      PyObject var8 = var1.getlocal(1).__iter__();

      label24:
      while(true) {
         var1.setline(770);
         PyObject var4 = var8.__iternext__();
         PyObject var7;
         if (var4 == null) {
            var1.setline(777);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(5, var4);
         var1.setline(771);
         PyObject var5 = var1.getlocal(4).__iter__();

         do {
            var1.setline(771);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               continue label24;
            }

            var1.setlocal(6, var6);
            var1.setline(772);
            var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(6)));
            var1.setlocal(7, var7);
            var7 = null;
            var1.setline(773);
         } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)).__nonzero__());

         var1.setline(774);
         var7 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject find_exe$30(PyFrame var1, ThreadState var2) {
      var1.setline(789);
      PyString.fromInterned("Return path to an MSVC executable program.\n\n        Tries to find the program in several places: first, one of the\n        MSVC program search paths from the registry; next, the directories\n        in the PATH environment variable.  If any of those work, return an\n        absolute path that is known to exist.  If none of them work, just\n        return the original program name, 'exe'.\n        ");
      var1.setline(790);
      PyObject var3 = var1.getlocal(0).__getattr__("_MSVCCompiler__paths").__iter__();

      PyObject var5;
      do {
         var1.setline(790);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(796);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("Path")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";")).__iter__();

            do {
               var1.setline(796);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(801);
                  var5 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(2, var4);
               var1.setline(797);
               PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2)), var1.getlocal(1));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(798);
            } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__());

            var1.setline(799);
            var5 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(791);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2)), var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(792);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__());

      var1.setline(793);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public msvc9compiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Reg$1 = Py.newCode(0, var2, var1, "Reg", 64, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "path", "key", "base", "d"};
      get_value$2 = Py.newCode(3, var2, var1, "get_value", 68, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "base", "key", "handle", "L", "i", "k"};
      read_keys$3 = Py.newCode(3, var2, var1, "read_keys", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "base", "key", "handle", "d", "i", "name", "value", "type"};
      read_values$4 = Py.newCode(3, var2, var1, "read_values", 94, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "dec"};
      convert_mbcs$5 = Py.newCode(1, var2, var1, "convert_mbcs", 116, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MacroExpander$6 = Py.newCode(0, var2, var1, "MacroExpander", 126, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 128, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "macro", "path", "key"};
      set_macro$8 = Py.newCode(4, var2, var1, "set_macro", 133, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "version", "p", "base", "h", "key", "d"};
      load_macros$9 = Py.newCode(2, var2, var1, "load_macros", 136, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "k", "v"};
      sub$10 = Py.newCode(2, var2, var1, "sub", 167, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "i", "s", "rest", "majorVersion", "minorVersion"};
      get_build_version$11 = Py.newCode(0, var2, var1, "get_build_version", 172, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"paths", "reduced_paths", "p", "np"};
      normalize_and_reduce_paths$12 = Py.newCode(1, var2, var1, "normalize_and_reduce_paths", 194, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"variable", "oldList", "newList", "i", "newVariable"};
      removeDuplicates$13 = Py.newCode(1, var2, var1, "removeDuplicates", 208, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"version", "vsbase", "productdir", "toolskey", "toolsdir", "vcvarsall"};
      find_vcvarsall$14 = Py.newCode(1, var2, var1, "find_vcvarsall", 219, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"version", "arch", "vcvarsall", "interesting", "result", "popen", "stdout", "stderr", "line", "key", "value"};
      query_vcvarsall$15 = Py.newCode(2, var2, var1, "query_vcvarsall", 263, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MSVCCompiler$16 = Py.newCode(0, var2, var1, "MSVCCompiler", 309, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force"};
      __init__$17 = Py.newCode(4, var2, var1, "__init__", 339, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "plat_name", "ok_plats", "plat_spec", "vc_env", "p"};
      initialize$18 = Py.newCode(2, var2, var1, "initialize", 350, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$19 = Py.newCode(4, var2, var1, "object_filenames", 437, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "output_dir", "macros", "include_dirs", "debug", "extra_preargs", "extra_postargs", "depends", "compile_info", "objects", "pp_opts", "build", "compile_opts", "obj", "src", "ext", "input_opt", "output_opt", "msg", "h_dir", "rc_dir", "base", "_", "rc_file"};
      compile$20 = Py.newCode(9, var2, var1, "compile", 468, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "debug", "target_lang", "output_filename", "lib_args", "msg"};
      create_static_lib$21 = Py.newCode(6, var2, var1, "create_static_lib", 553, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "fixed_args", "lib_opts", "ldflags", "export_opts", "sym", "ld_args", "dll_name", "dll_ext", "implib_file", "msg", "mfinfo", "mffilename", "mfid", "out_arg"};
      link$22 = Py.newCode(14, var2, var1, "link", 578, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "output_filename", "build_temp", "ld_args", "temp_manifest"};
      manifest_setup_ldargs$23 = Py.newCode(4, var2, var1, "manifest_setup_ldargs", 673, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "ld_args", "arg", "temp_manifest", "mfid"};
      manifest_get_embed_info$24 = Py.newCode(3, var2, var1, "manifest_get_embed_info", 685, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "manifest_file", "manifest_f", "manifest_buf", "pattern"};
      _remove_visual_c_ref$25 = Py.newCode(2, var2, var1, "_remove_visual_c_ref", 709, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      library_dir_option$26 = Py.newCode(2, var2, var1, "library_dir_option", 752, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      runtime_library_dir_option$27 = Py.newCode(2, var2, var1, "runtime_library_dir_option", 755, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib"};
      library_option$28 = Py.newCode(2, var2, var1, "library_option", 759, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug", "try_names", "dir", "name", "libfile"};
      find_library_file$29 = Py.newCode(4, var2, var1, "find_library_file", 763, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exe", "p", "fn"};
      find_exe$30 = Py.newCode(2, var2, var1, "find_exe", 781, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new msvc9compiler$py("distutils/msvc9compiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(msvc9compiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Reg$1(var2, var3);
         case 2:
            return this.get_value$2(var2, var3);
         case 3:
            return this.read_keys$3(var2, var3);
         case 4:
            return this.read_values$4(var2, var3);
         case 5:
            return this.convert_mbcs$5(var2, var3);
         case 6:
            return this.MacroExpander$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.set_macro$8(var2, var3);
         case 9:
            return this.load_macros$9(var2, var3);
         case 10:
            return this.sub$10(var2, var3);
         case 11:
            return this.get_build_version$11(var2, var3);
         case 12:
            return this.normalize_and_reduce_paths$12(var2, var3);
         case 13:
            return this.removeDuplicates$13(var2, var3);
         case 14:
            return this.find_vcvarsall$14(var2, var3);
         case 15:
            return this.query_vcvarsall$15(var2, var3);
         case 16:
            return this.MSVCCompiler$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.initialize$18(var2, var3);
         case 19:
            return this.object_filenames$19(var2, var3);
         case 20:
            return this.compile$20(var2, var3);
         case 21:
            return this.create_static_lib$21(var2, var3);
         case 22:
            return this.link$22(var2, var3);
         case 23:
            return this.manifest_setup_ldargs$23(var2, var3);
         case 24:
            return this.manifest_get_embed_info$24(var2, var3);
         case 25:
            return this._remove_visual_c_ref$25(var2, var3);
         case 26:
            return this.library_dir_option$26(var2, var3);
         case 27:
            return this.runtime_library_dir_option$27(var2, var3);
         case 28:
            return this.library_option$28(var2, var3);
         case 29:
            return this.find_library_file$29(var2, var3);
         case 30:
            return this.find_exe$30(var2, var3);
         default:
            return null;
      }
   }
}
