package distutils.command;

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
@Filename("distutils/command/build_scripts.py")
public class build_scripts$py extends PyFunctionTable implements PyRunnable {
   static build_scripts$py self;
   static final PyCode f$0;
   static final PyCode build_scripts$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode get_source_files$4;
   static final PyCode run$5;
   static final PyCode copy_scripts$6;
   static final PyCode is_sh$7;
   static final PyCode fix_jython_executable$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.build_scripts\n\nImplements the Distutils 'build_scripts' command."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.build_scripts\n\nImplements the Distutils 'build_scripts' command.");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(8);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(9);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"ST_MODE"};
      PyObject[] var7 = imp.importFrom("stat", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("ST_MODE", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"newer"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"convert_path"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(17);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^#!.*python[0-9.]*([ \t].*)?$"));
      var1.setlocal("first_line_re", var5);
      var3 = null;
      var1.setline(19);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("build_scripts", var7, build_scripts$1);
      var1.setlocal("build_scripts", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(137);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, is_sh$7, PyString.fromInterned("Determine if the specified executable is a .sh (contains a #! line)"));
      var1.setlocal("is_sh", var8);
      var3 = null;
      var1.setline(148);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, fix_jython_executable$8, (PyObject)null);
      var1.setlocal("fix_jython_executable", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build_scripts$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyString var3 = PyString.fromInterned("\"build\" scripts (copy and fixup #! line)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(23);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to \"build\" (copy) to")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("forcibly build everything (ignore file timestamps")}), new PyTuple(new PyObject[]{PyString.fromInterned("executable="), PyString.fromInterned("e"), PyString.fromInterned("specify final destination interpreter path")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(29);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(32);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(39);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(46);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_source_files$4, (PyObject)null);
      var1.setlocal("get_source_files", var6);
      var3 = null;
      var1.setline(49);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$5, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(55);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, copy_scripts$6, PyString.fromInterned("Copy each script listed in 'self.scripts'; if it's marked as a\n        Python script in the Unix way (first line matches 'first_line_re',\n        ie. starts with \"\\#!\" and contains \"python\"), then adjust the first\n        line to refer to the current Python interpreter as we copy.\n        "));
      var1.setlocal("copy_scripts", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_dir", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("scripts", var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("force", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("executable", var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("outfiles", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__(var2, PyString.fromInterned("build"), new PyTuple(new PyObject[]{PyString.fromInterned("build_scripts"), PyString.fromInterned("build_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")}), new PyTuple(new PyObject[]{PyString.fromInterned("executable"), PyString.fromInterned("executable")}));
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("scripts");
      var1.getlocal(0).__setattr__("scripts", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_source_files$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(0).__getattr__("scripts");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      if (var1.getlocal(0).__getattr__("scripts").__not__().__nonzero__()) {
         var1.setline(51);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(52);
         var1.getlocal(0).__getattr__("copy_scripts").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject copy_scripts$6(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Copy each script listed in 'self.scripts'; if it's marked as a\n        Python script in the Unix way (first line matches 'first_line_re',\n        ie. starts with \"\\#!\" and contains \"python\"), then adjust the first\n        line to refer to the current Python interpreter as we copy.\n        ");
      var1.setline(61);
      PyObject var3 = var1.getglobal("__import__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sysconfig"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(62);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("build_dir"));
      var1.setline(63);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(0).__getattr__("scripts").__iter__();

      while(true) {
         var1.setline(64);
         PyObject var4 = var3.__iternext__();
         PyInteger var5;
         PyObject var9;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(120);
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._eq(PyString.fromInterned("posix"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(121);
               var3 = var1.getlocal(2).__iter__();

               while(true) {
                  var1.setline(121);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(11, var4);
                  var1.setline(122);
                  if (var1.getlocal(0).__getattr__("dry_run").__nonzero__()) {
                     var1.setline(123);
                     var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing mode of %s"), (PyObject)var1.getlocal(11));
                  } else {
                     var1.setline(125);
                     var9 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(11)).__getitem__(var1.getglobal("ST_MODE"))._and(Py.newInteger(4095));
                     var1.setlocal(12, var9);
                     var5 = null;
                     var1.setline(126);
                     var9 = var1.getlocal(12)._or(Py.newInteger(365))._and(Py.newInteger(4095));
                     var1.setlocal(13, var9);
                     var5 = null;
                     var1.setline(127);
                     var9 = var1.getlocal(13);
                     var10000 = var9._ne(var1.getlocal(12));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(128);
                        var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("changing mode of %s from %o to %o"), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13));
                        var1.setline(130);
                        var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(11), var1.getlocal(13));
                     }
                  }
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(65);
         var5 = Py.newInteger(0);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(66);
         var9 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var9);
         var5 = null;
         var1.setline(67);
         var9 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_dir"), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
         var1.setlocal(5, var9);
         var5 = null;
         var1.setline(68);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
         var1.setline(70);
         var10000 = var1.getlocal(0).__getattr__("force").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("newer").__call__(var2, var1.getlocal(3), var1.getlocal(5)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(71);
            var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not copying %s (up-to-date)"), (PyObject)var1.getlocal(3));
         } else {
            label89: {
               PyObject var6;
               try {
                  var1.setline(78);
                  var9 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("r"));
                  var1.setlocal(6, var9);
                  var5 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (var10.match(var1.getglobal("IOError"))) {
                     var1.setline(80);
                     if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
                        var1.setline(81);
                        throw Py.makeException();
                     }

                     var1.setline(82);
                     var6 = var1.getglobal("None");
                     var1.setlocal(6, var6);
                     var6 = null;
                     break label89;
                  }

                  throw var10;
               }

               var1.setline(84);
               var6 = var1.getlocal(6).__getattr__("readline").__call__(var2);
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(85);
               if (var1.getlocal(7).__not__().__nonzero__()) {
                  var1.setline(86);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("%s is an empty file (skipping)")._mod(var1.getlocal(3)));
                  continue;
               }

               var1.setline(89);
               var6 = var1.getglobal("first_line_re").__getattr__("match").__call__(var2, var1.getlocal(7));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(90);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(91);
                  PyInteger var11 = Py.newInteger(1);
                  var1.setlocal(4, var11);
                  var6 = null;
                  var1.setline(92);
                  Object var13 = var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  if (!((PyObject)var13).__nonzero__()) {
                     var13 = PyString.fromInterned("");
                  }

                  Object var12 = var13;
                  var1.setlocal(9, (PyObject)var12);
                  var6 = null;
               }
            }

            var1.setline(94);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(95);
               var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("copying and adjusting %s -> %s"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(0).__getattr__("build_dir"));
               var1.setline(97);
               var9 = var1.getglobal("fix_jython_executable").__call__(var2, var1.getlocal(0).__getattr__("executable"), var1.getlocal(9));
               var1.getlocal(0).__setattr__("executable", var9);
               var5 = null;
               var1.setline(98);
               if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
                  var1.setline(99);
                  var9 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("w"));
                  var1.setlocal(10, var9);
                  var5 = null;
                  var1.setline(100);
                  if (var1.getlocal(1).__getattr__("is_python_build").__call__(var2).__not__().__nonzero__()) {
                     var1.setline(101);
                     var1.getlocal(10).__getattr__("write").__call__(var2, PyString.fromInterned("#!%s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("executable"), var1.getlocal(9)})));
                  } else {
                     var1.setline(105);
                     var1.getlocal(10).__getattr__("write").__call__(var2, PyString.fromInterned("#!%s%s\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINDIR")), PyString.fromInterned("python%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("VERSION")), var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EXE"))}))), var1.getlocal(9)})));
                  }

                  var1.setline(111);
                  var1.getlocal(10).__getattr__("writelines").__call__(var2, var1.getlocal(6).__getattr__("readlines").__call__(var2));
                  var1.setline(112);
                  var1.getlocal(10).__getattr__("close").__call__(var2);
               }

               var1.setline(113);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(114);
                  var1.getlocal(6).__getattr__("close").__call__(var2);
               }
            } else {
               var1.setline(116);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(117);
                  var1.getlocal(6).__getattr__("close").__call__(var2);
               }

               var1.setline(118);
               var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            }
         }
      }
   }

   public PyObject is_sh$7(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Determine if the specified executable is a .sh (contains a #! line)");

      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(140);
         var6 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(141);
         var6 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(142);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(144);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(145);
      var6 = var1.getlocal(2);
      PyObject var10000 = var6._eq(PyString.fromInterned("#!"));
      var3 = null;
      var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject fix_jython_executable$8(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("is_sh").__call__(var2, var1.getlocal(0));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(159);
            var3 = PyString.fromInterned("/usr/bin/env %s")._mod(var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(154);
         var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("WARNING: Unable to adapt shebang line for Jython, the following script is NOT executable\n         see http://bugs.jython.org/issue1112 for more information."));
      }

      var1.setline(160);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public build_scripts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      build_scripts$1 = Py.newCode(0, var2, var1, "build_scripts", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_source_files$4 = Py.newCode(1, var2, var1, "get_source_files", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$5 = Py.newCode(1, var2, var1, "run", 49, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_sysconfig", "outfiles", "script", "adjust", "outfile", "f", "first_line", "match", "post_interp", "outf", "file", "oldmode", "newmode"};
      copy_scripts$6 = Py.newCode(1, var2, var1, "copy_scripts", 55, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"executable", "fp", "magic", "OSError"};
      is_sh$7 = Py.newCode(1, var2, var1, "is_sh", 137, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"executable", "options"};
      fix_jython_executable$8 = Py.newCode(2, var2, var1, "fix_jython_executable", 148, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new build_scripts$py("distutils/command/build_scripts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(build_scripts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.build_scripts$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.get_source_files$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         case 6:
            return this.copy_scripts$6(var2, var3);
         case 7:
            return this.is_sh$7(var2, var3);
         case 8:
            return this.fix_jython_executable$8(var2, var3);
         default:
            return null;
      }
   }
}
