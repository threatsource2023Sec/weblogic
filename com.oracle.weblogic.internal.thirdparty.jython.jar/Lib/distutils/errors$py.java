package distutils;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/errors.py")
public class errors$py extends PyFunctionTable implements PyRunnable {
   static errors$py self;
   static final PyCode f$0;
   static final PyCode DistutilsError$1;
   static final PyCode DistutilsModuleError$2;
   static final PyCode DistutilsClassError$3;
   static final PyCode DistutilsGetoptError$4;
   static final PyCode DistutilsArgError$5;
   static final PyCode DistutilsFileError$6;
   static final PyCode DistutilsOptionError$7;
   static final PyCode DistutilsSetupError$8;
   static final PyCode DistutilsPlatformError$9;
   static final PyCode DistutilsExecError$10;
   static final PyCode DistutilsInternalError$11;
   static final PyCode DistutilsTemplateError$12;
   static final PyCode DistutilsByteCompileError$13;
   static final PyCode CCompilerError$14;
   static final PyCode PreprocessError$15;
   static final PyCode CompileError$16;
   static final PyCode LibError$17;
   static final PyCode LinkError$18;
   static final PyCode UnknownFileError$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.errors\n\nProvides exceptions used by the Distutils modules.  Note that Distutils\nmodules may raise standard exceptions; in particular, SystemExit is\nusually raised for errors that are obviously the end-user's fault\n(eg. bad command-line arguments).\n\nThis module is safe to use in \"from ... import *\" mode; it only exports\nsymbols whose names start with \"Distutils\" and end with \"Error\"."));
      var1.setline(9);
      PyString.fromInterned("distutils.errors\n\nProvides exceptions used by the Distutils modules.  Note that Distutils\nmodules may raise standard exceptions; in particular, SystemExit is\nusually raised for errors that are obviously the end-user's fault\n(eg. bad command-line arguments).\n\nThis module is safe to use in \"from ... import *\" mode; it only exports\nsymbols whose names start with \"Distutils\" and end with \"Error\".");
      var1.setline(11);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(13);
      PyObject[] var5 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("DistutilsError", var5, DistutilsError$1);
      var1.setlocal("DistutilsError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsModuleError", var5, DistutilsModuleError$2);
      var1.setlocal("DistutilsModuleError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(20);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsClassError", var5, DistutilsClassError$3);
      var1.setlocal("DistutilsClassError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsGetoptError", var5, DistutilsGetoptError$4);
      var1.setlocal("DistutilsGetoptError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(29);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsArgError", var5, DistutilsArgError$5);
      var1.setlocal("DistutilsArgError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsFileError", var5, DistutilsFileError$6);
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(38);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsOptionError", var5, DistutilsOptionError$7);
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(46);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsSetupError", var5, DistutilsSetupError$8);
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(50);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsPlatformError", var5, DistutilsPlatformError$9);
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(55);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsExecError", var5, DistutilsExecError$10);
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(59);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsInternalError", var5, DistutilsInternalError$11);
      var1.setlocal("DistutilsInternalError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(63);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsTemplateError", var5, DistutilsTemplateError$12);
      var1.setlocal("DistutilsTemplateError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(66);
      var5 = new PyObject[]{var1.getname("DistutilsError")};
      var4 = Py.makeClass("DistutilsByteCompileError", var5, DistutilsByteCompileError$13);
      var1.setlocal("DistutilsByteCompileError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(70);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("CCompilerError", var5, CCompilerError$14);
      var1.setlocal("CCompilerError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(73);
      var5 = new PyObject[]{var1.getname("CCompilerError")};
      var4 = Py.makeClass("PreprocessError", var5, PreprocessError$15);
      var1.setlocal("PreprocessError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(76);
      var5 = new PyObject[]{var1.getname("CCompilerError")};
      var4 = Py.makeClass("CompileError", var5, CompileError$16);
      var1.setlocal("CompileError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(79);
      var5 = new PyObject[]{var1.getname("CCompilerError")};
      var4 = Py.makeClass("LibError", var5, LibError$17);
      var1.setlocal("LibError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(83);
      var5 = new PyObject[]{var1.getname("CCompilerError")};
      var4 = Py.makeClass("LinkError", var5, LinkError$18);
      var1.setlocal("LinkError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(87);
      var5 = new PyObject[]{var1.getname("CCompilerError")};
      var4 = Py.makeClass("UnknownFileError", var5, UnknownFileError$19);
      var1.setlocal("UnknownFileError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DistutilsError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The root of all Distutils evil."));
      var1.setline(14);
      PyString.fromInterned("The root of all Distutils evil.");
      return var1.getf_locals();
   }

   public PyObject DistutilsModuleError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Unable to load an expected module, or to find an expected class\n    within some module (in particular, command modules and classes)."));
      var1.setline(18);
      PyString.fromInterned("Unable to load an expected module, or to find an expected class\n    within some module (in particular, command modules and classes).");
      return var1.getf_locals();
   }

   public PyObject DistutilsClassError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Some command class (or possibly distribution class, if anyone\n    feels a need to subclass Distribution) is found not to be holding\n    up its end of the bargain, ie. implementing some part of the\n    \"command \"interface."));
      var1.setline(24);
      PyString.fromInterned("Some command class (or possibly distribution class, if anyone\n    feels a need to subclass Distribution) is found not to be holding\n    up its end of the bargain, ie. implementing some part of the\n    \"command \"interface.");
      return var1.getf_locals();
   }

   public PyObject DistutilsGetoptError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The option table provided to 'fancy_getopt()' is bogus."));
      var1.setline(27);
      PyString.fromInterned("The option table provided to 'fancy_getopt()' is bogus.");
      return var1.getf_locals();
   }

   public PyObject DistutilsArgError$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised by fancy_getopt in response to getopt.error -- ie. an\n    error in the command line usage."));
      var1.setline(31);
      PyString.fromInterned("Raised by fancy_getopt in response to getopt.error -- ie. an\n    error in the command line usage.");
      return var1.getf_locals();
   }

   public PyObject DistutilsFileError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Any problems in the filesystem: expected file not found, etc.\n    Typically this is for problems that we detect before IOError or\n    OSError could be raised."));
      var1.setline(36);
      PyString.fromInterned("Any problems in the filesystem: expected file not found, etc.\n    Typically this is for problems that we detect before IOError or\n    OSError could be raised.");
      return var1.getf_locals();
   }

   public PyObject DistutilsOptionError$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Syntactic/semantic errors in command options, such as use of\n    mutually conflicting options, or inconsistent options,\n    badly-spelled values, etc.  No distinction is made between option\n    values originating in the setup script, the command line, config\n    files, or what-have-you -- but if we *know* something originated in\n    the setup script, we'll raise DistutilsSetupError instead."));
      var1.setline(44);
      PyString.fromInterned("Syntactic/semantic errors in command options, such as use of\n    mutually conflicting options, or inconsistent options,\n    badly-spelled values, etc.  No distinction is made between option\n    values originating in the setup script, the command line, config\n    files, or what-have-you -- but if we *know* something originated in\n    the setup script, we'll raise DistutilsSetupError instead.");
      return var1.getf_locals();
   }

   public PyObject DistutilsSetupError$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("For errors that can be definitely blamed on the setup script,\n    such as invalid keyword arguments to 'setup()'."));
      var1.setline(48);
      PyString.fromInterned("For errors that can be definitely blamed on the setup script,\n    such as invalid keyword arguments to 'setup()'.");
      return var1.getf_locals();
   }

   public PyObject DistutilsPlatformError$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("We don't know how to do something on the current platform (but\n    we do know how to do it on some platform) -- eg. trying to compile\n    C files on a platform not supported by a CCompiler subclass."));
      var1.setline(53);
      PyString.fromInterned("We don't know how to do something on the current platform (but\n    we do know how to do it on some platform) -- eg. trying to compile\n    C files on a platform not supported by a CCompiler subclass.");
      return var1.getf_locals();
   }

   public PyObject DistutilsExecError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Any problems executing an external program (such as the C\n    compiler, when compiling C files)."));
      var1.setline(57);
      PyString.fromInterned("Any problems executing an external program (such as the C\n    compiler, when compiling C files).");
      return var1.getf_locals();
   }

   public PyObject DistutilsInternalError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Internal inconsistencies or impossibilities (obviously, this\n    should never be seen if the code is working!)."));
      var1.setline(61);
      PyString.fromInterned("Internal inconsistencies or impossibilities (obviously, this\n    should never be seen if the code is working!).");
      return var1.getf_locals();
   }

   public PyObject DistutilsTemplateError$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Syntax error in a file list template."));
      var1.setline(64);
      PyString.fromInterned("Syntax error in a file list template.");
      return var1.getf_locals();
   }

   public PyObject DistutilsByteCompileError$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Byte compile error."));
      var1.setline(67);
      PyString.fromInterned("Byte compile error.");
      return var1.getf_locals();
   }

   public PyObject CCompilerError$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Some compile/link operation failed."));
      var1.setline(71);
      PyString.fromInterned("Some compile/link operation failed.");
      return var1.getf_locals();
   }

   public PyObject PreprocessError$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Failure to preprocess one or more C/C++ files."));
      var1.setline(74);
      PyString.fromInterned("Failure to preprocess one or more C/C++ files.");
      return var1.getf_locals();
   }

   public PyObject CompileError$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Failure to compile one or more C/C++ source files."));
      var1.setline(77);
      PyString.fromInterned("Failure to compile one or more C/C++ source files.");
      return var1.getf_locals();
   }

   public PyObject LibError$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Failure to create a static library from one or more C/C++ object\n    files."));
      var1.setline(81);
      PyString.fromInterned("Failure to create a static library from one or more C/C++ object\n    files.");
      return var1.getf_locals();
   }

   public PyObject LinkError$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Failure to link one or more C/C++ object files into an executable\n    or shared library file."));
      var1.setline(85);
      PyString.fromInterned("Failure to link one or more C/C++ object files into an executable\n    or shared library file.");
      return var1.getf_locals();
   }

   public PyObject UnknownFileError$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Attempt to process an unknown file type."));
      var1.setline(88);
      PyString.fromInterned("Attempt to process an unknown file type.");
      return var1.getf_locals();
   }

   public errors$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsError$1 = Py.newCode(0, var2, var1, "DistutilsError", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsModuleError$2 = Py.newCode(0, var2, var1, "DistutilsModuleError", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsClassError$3 = Py.newCode(0, var2, var1, "DistutilsClassError", 20, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsGetoptError$4 = Py.newCode(0, var2, var1, "DistutilsGetoptError", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsArgError$5 = Py.newCode(0, var2, var1, "DistutilsArgError", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsFileError$6 = Py.newCode(0, var2, var1, "DistutilsFileError", 33, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsOptionError$7 = Py.newCode(0, var2, var1, "DistutilsOptionError", 38, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsSetupError$8 = Py.newCode(0, var2, var1, "DistutilsSetupError", 46, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsPlatformError$9 = Py.newCode(0, var2, var1, "DistutilsPlatformError", 50, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsExecError$10 = Py.newCode(0, var2, var1, "DistutilsExecError", 55, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsInternalError$11 = Py.newCode(0, var2, var1, "DistutilsInternalError", 59, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsTemplateError$12 = Py.newCode(0, var2, var1, "DistutilsTemplateError", 63, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DistutilsByteCompileError$13 = Py.newCode(0, var2, var1, "DistutilsByteCompileError", 66, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CCompilerError$14 = Py.newCode(0, var2, var1, "CCompilerError", 70, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PreprocessError$15 = Py.newCode(0, var2, var1, "PreprocessError", 73, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CompileError$16 = Py.newCode(0, var2, var1, "CompileError", 76, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      LibError$17 = Py.newCode(0, var2, var1, "LibError", 79, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      LinkError$18 = Py.newCode(0, var2, var1, "LinkError", 83, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnknownFileError$19 = Py.newCode(0, var2, var1, "UnknownFileError", 87, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new errors$py("distutils/errors$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(errors$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.DistutilsError$1(var2, var3);
         case 2:
            return this.DistutilsModuleError$2(var2, var3);
         case 3:
            return this.DistutilsClassError$3(var2, var3);
         case 4:
            return this.DistutilsGetoptError$4(var2, var3);
         case 5:
            return this.DistutilsArgError$5(var2, var3);
         case 6:
            return this.DistutilsFileError$6(var2, var3);
         case 7:
            return this.DistutilsOptionError$7(var2, var3);
         case 8:
            return this.DistutilsSetupError$8(var2, var3);
         case 9:
            return this.DistutilsPlatformError$9(var2, var3);
         case 10:
            return this.DistutilsExecError$10(var2, var3);
         case 11:
            return this.DistutilsInternalError$11(var2, var3);
         case 12:
            return this.DistutilsTemplateError$12(var2, var3);
         case 13:
            return this.DistutilsByteCompileError$13(var2, var3);
         case 14:
            return this.CCompilerError$14(var2, var3);
         case 15:
            return this.PreprocessError$15(var2, var3);
         case 16:
            return this.CompileError$16(var2, var3);
         case 17:
            return this.LibError$17(var2, var3);
         case 18:
            return this.LinkError$18(var2, var3);
         case 19:
            return this.UnknownFileError$19(var2, var3);
         default:
            return null;
      }
   }
}
