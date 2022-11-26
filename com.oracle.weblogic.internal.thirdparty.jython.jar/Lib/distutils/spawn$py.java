package distutils;

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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("distutils/spawn.py")
public class spawn$py extends PyFunctionTable implements PyRunnable {
   static spawn$py self;
   static final PyCode f$0;
   static final PyCode spawn$1;
   static final PyCode _nt_quote_args$2;
   static final PyCode _spawn_nt$3;
   static final PyCode _spawn_os2$4;
   static final PyCode _spawn_posix$5;
   static final PyCode _spawn_java$6;
   static final PyCode find_executable$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.spawn\n\nProvides the 'spawn()' function, a front-end to various platform-\nspecific functions for launching another program in a sub-process.\nAlso provides the 'find_executable()' to search the path for a given\nexecutable name.\n"));
      var1.setline(7);
      PyString.fromInterned("distutils.spawn\n\nProvides the 'spawn()' function, a front-end to various platform-\nspecific functions for launching another program in a sub-process.\nAlso provides the 'find_executable()' to search the path for a given\nexecutable name.\n");
      var1.setline(9);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(11);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(12);
      var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(14);
      String[] var6 = new String[]{"DistutilsPlatformError", "DistutilsExecError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"DEBUG"};
      var7 = imp.importFrom("distutils.debug", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(18);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, spawn$1, PyString.fromInterned("Run another program, specified as a command list 'cmd', in a new process.\n\n    'cmd' is just the argument list for the new process, ie.\n    cmd[0] is the program to run and cmd[1:] are the rest of its arguments.\n    There is no way to run a program with a name different from that of its\n    executable.\n\n    If 'search_path' is true (the default), the system's executable\n    search path will be used to find the program; otherwise, cmd[0]\n    must be the exact path to the executable.  If 'dry_run' is true,\n    the command will not actually be run.\n\n    Raise DistutilsExecError if running the program fails in any way; just\n    return on success.\n    "));
      var1.setlocal("spawn", var8);
      var3 = null;
      var1.setline(49);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _nt_quote_args$2, PyString.fromInterned("Quote command-line arguments for DOS/Windows conventions.\n\n    Just wraps every argument which contains blanks in double quotes, and\n    returns a new argument list.\n    "));
      var1.setlocal("_nt_quote_args", var8);
      var3 = null;
      var1.setline(65);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, _spawn_nt$3, (PyObject)null);
      var1.setlocal("_spawn_nt", var8);
      var3 = null;
      var1.setline(89);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, _spawn_os2$4, (PyObject)null);
      var1.setlocal("_spawn_os2", var8);
      var3 = null;
      var1.setline(113);
      var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var5._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var6 = new String[]{"sysconfig"};
         var7 = imp.importFrom("distutils", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("sysconfig", var4);
         var4 = null;
         var1.setline(115);
         var5 = var1.getname("None");
         var1.setlocal("_cfg_target", var5);
         var3 = null;
         var1.setline(116);
         var5 = var1.getname("None");
         var1.setlocal("_cfg_target_split", var5);
         var3 = null;
      }

      var1.setline(118);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, _spawn_posix$5, (PyObject)null);
      var1.setlocal("_spawn_posix", var8);
      var3 = null;
      var1.setline(206);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, _spawn_java$6, (PyObject)null);
      var1.setlocal("_spawn_java", var8);
      var3 = null;
      var1.setline(227);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, find_executable$7, PyString.fromInterned("Tries to find 'executable' in the directories listed in 'path'.\n\n    A string listing directories separated by 'os.pathsep'; defaults to\n    os.environ['PATH'].  Returns the complete filename or None if not found.\n    "));
      var1.setlocal("find_executable", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject spawn$1(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Run another program, specified as a command list 'cmd', in a new process.\n\n    'cmd' is just the argument list for the new process, ie.\n    cmd[0] is the program to run and cmd[1:] are the rest of its arguments.\n    There is no way to run a program with a name different from that of its\n    executable.\n\n    If 'search_path' is true (the default), the system's executable\n    search path will be used to find the program; otherwise, cmd[0]\n    must be the exact path to the executable.  If 'dry_run' is true,\n    the command will not actually be run.\n\n    Raise DistutilsExecError if running the program fails in any way; just\n    return on success.\n    ");
      var1.setline(36);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var10000 = var1.getglobal("_spawn_posix");
         var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3)};
         var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      } else {
         var1.setline(39);
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(40);
            var10000 = var1.getglobal("_spawn_nt");
            var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3)};
            var4 = new String[]{"dry_run"};
            var10000.__call__(var2, var5, var4);
            var3 = null;
         } else {
            var1.setline(41);
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._eq(PyString.fromInterned("os2"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(42);
               var10000 = var1.getglobal("_spawn_os2");
               var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3)};
               var4 = new String[]{"dry_run"};
               var10000.__call__(var2, var5, var4);
               var3 = null;
            } else {
               var1.setline(43);
               var3 = var1.getglobal("os").__getattr__("name");
               var10000 = var3._eq(PyString.fromInterned("java"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(46);
                  throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to spawn programs on platform '%s'")._mod(var1.getglobal("os").__getattr__("name")));
               }

               var1.setline(44);
               var10000 = var1.getglobal("_spawn_java");
               var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3)};
               var4 = new String[]{"dry_run"};
               var10000.__call__(var2, var5, var4);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _nt_quote_args$2(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Quote command-line arguments for DOS/Windows conventions.\n\n    Just wraps every argument which contains blanks in double quotes, and\n    returns a new argument list.\n    ");
      var1.setline(60);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(60);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(63);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(61);
         PyString var7 = PyString.fromInterned(" ");
         PyObject var10000 = var7._in(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            PyObject var8 = PyString.fromInterned("\"%s\"")._mod(var1.getlocal(2));
            var1.getlocal(0).__setitem__(var1.getlocal(1), var8);
            var5 = null;
         }
      }
   }

   public PyObject _spawn_nt$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getglobal("_nt_quote_args").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(68);
      PyObject var10000;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(70);
         var10000 = var1.getglobal("find_executable").__call__(var2, var1.getlocal(4));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(71);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned(" ").__getattr__("join").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(4)}))._add(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null))));
      var1.setline(72);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         try {
            var1.setline(75);
            var3 = var1.getglobal("os").__getattr__("spawnv").__call__(var2, var1.getglobal("os").__getattr__("P_WAIT"), var1.getlocal(4), var1.getlocal(0));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("OSError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(78);
               if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                  var1.setline(79);
                  var4 = var1.getlocal(4);
                  var1.setlocal(0, var4);
                  var4 = null;
               }

               var1.setline(80);
               throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(6).__getitem__(Py.newInteger(-1))})));
            }

            throw var6;
         }

         var1.setline(82);
         var3 = var1.getlocal(5);
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(84);
            if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
               var1.setline(85);
               var3 = var1.getlocal(4);
               var1.setlocal(0, var3);
               var3 = null;
            }

            var1.setline(86);
            throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _spawn_os2$4(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(91);
      PyObject var10000;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(93);
         var10000 = var1.getglobal("find_executable").__call__(var2, var1.getlocal(4));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(94);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned(" ").__getattr__("join").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(4)}))._add(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null))));
      var1.setline(95);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         try {
            var1.setline(98);
            var3 = var1.getglobal("os").__getattr__("spawnv").__call__(var2, var1.getglobal("os").__getattr__("P_WAIT"), var1.getlocal(4), var1.getlocal(0));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("OSError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(101);
               if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                  var1.setline(102);
                  var4 = var1.getlocal(4);
                  var1.setlocal(0, var4);
                  var4 = null;
               }

               var1.setline(103);
               throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(6).__getitem__(Py.newInteger(-1))})));
            }

            throw var6;
         }

         var1.setline(105);
         var3 = var1.getlocal(5);
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(107);
            if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
               var1.setline(108);
               var3 = var1.getlocal(4);
               var1.setlocal(0, var3);
               var3 = null;
            }

            var1.setline(109);
            var1.getglobal("log").__getattr__("debug").__call__(var2, PyString.fromInterned("command %r failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
            var1.setline(110);
            throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(5)})));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _spawn_posix$5(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0)));
      var1.setline(120);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(121);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(122);
         PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(123);
         PyObject var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("execvp");
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("execv");
         }

         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(124);
         var3 = var1.getglobal("None");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(125);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("darwin"));
         var3 = null;
         PyObject var4;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(127);
            var3 = var1.getglobal("_cfg_target");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyList var16;
            if (var10000.__nonzero__()) {
               var1.setline(128);
               Object var15 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
               if (!((PyObject)var15).__nonzero__()) {
                  var15 = PyString.fromInterned("");
               }

               Object var10 = var15;
               var1.setglobal("_cfg_target", (PyObject)var10);
               var3 = null;
               var1.setline(130);
               if (var1.getglobal("_cfg_target").__nonzero__()) {
                  var1.setline(131);
                  var16 = new PyList();
                  var3 = var16.__getattr__("append");
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(131);
                  var3 = var1.getglobal("_cfg_target").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__iter__();

                  while(true) {
                     var1.setline(131);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(131);
                        var1.dellocal(7);
                        PyList var12 = var16;
                        var1.setglobal("_cfg_target_split", var12);
                        var3 = null;
                        break;
                     }

                     var1.setlocal(8, var4);
                     var1.setline(131);
                     var1.getlocal(7).__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(8)));
                  }
               }
            }

            var1.setline(132);
            if (var1.getglobal("_cfg_target").__nonzero__()) {
               var1.setline(136);
               var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"), (PyObject)var1.getglobal("_cfg_target"));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(137);
               var3 = var1.getglobal("_cfg_target_split");
               var16 = new PyList();
               var5 = var16.__getattr__("append");
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(137);
               var5 = var1.getlocal(9).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__iter__();

               while(true) {
                  var1.setline(137);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(137);
                     var1.dellocal(10);
                     var10000 = var3._gt(var16);
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(138);
                        var3 = PyString.fromInterned("$MACOSX_DEPLOYMENT_TARGET mismatch: now \"%s\" but \"%s\" during configure")._mod(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getglobal("_cfg_target")}));
                        var1.setlocal(11, var3);
                        var3 = null;
                        var1.setline(141);
                        throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, var1.getlocal(11)));
                     }

                     var1.setline(142);
                     var10000 = var1.getglobal("dict");
                     PyObject[] var13 = new PyObject[]{var1.getglobal("os").__getattr__("environ"), var1.getlocal(9)};
                     String[] var9 = new String[]{"MACOSX_DEPLOYMENT_TARGET"};
                     var10000 = var10000.__call__(var2, var13, var9);
                     var3 = null;
                     var3 = var10000;
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(144);
                     var10000 = var1.getlocal(1);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("execvpe");
                     }

                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("execve");
                     }

                     var3 = var10000;
                     var1.setlocal(5, var3);
                     var3 = null;
                     break;
                  }

                  var1.setlocal(8, var6);
                  var1.setline(137);
                  var1.getlocal(10).__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(8)));
               }
            }
         }

         var1.setline(145);
         var3 = var1.getglobal("os").__getattr__("fork").__call__(var2);
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(147);
         var3 = var1.getlocal(12);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         PyException var14;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(149);
               var3 = var1.getlocal(6);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(150);
                  var1.getlocal(5).__call__(var2, var1.getlocal(4), var1.getlocal(0));
               } else {
                  var1.setline(152);
                  var1.getlocal(5).__call__(var2, var1.getlocal(4), var1.getlocal(0), var1.getlocal(6));
               }
            } catch (Throwable var7) {
               var14 = Py.setException(var7, var1);
               if (!var14.match(var1.getglobal("OSError"))) {
                  throw var14;
               }

               var4 = var14.value;
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(154);
               if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                  var1.setline(155);
                  var4 = var1.getlocal(4);
                  var1.setlocal(0, var4);
                  var4 = null;
               }

               var1.setline(156);
               var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("unable to execute %r: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(13).__getattr__("strerror")})));
               var1.setline(158);
               var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var1.setline(160);
            if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
               var1.setline(161);
               var3 = var1.getlocal(4);
               var1.setlocal(0, var3);
               var3 = null;
            }

            var1.setline(162);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("unable to execute %r for unknown reasons")._mod(var1.getlocal(0)));
            var1.setline(163);
            var1.getglobal("os").__getattr__("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         } else {
            label123:
            while(true) {
               while(true) {
                  var1.setline(167);
                  if (!Py.newInteger(1).__nonzero__()) {
                     break label123;
                  }

                  try {
                     var1.setline(169);
                     var3 = var1.getglobal("os").__getattr__("waitpid").__call__((ThreadState)var2, (PyObject)var1.getlocal(12), (PyObject)Py.newInteger(0));
                     PyObject[] var11 = Py.unpackSequence(var3, 2);
                     var5 = var11[0];
                     var1.setlocal(12, var5);
                     var5 = null;
                     var5 = var11[1];
                     var1.setlocal(14, var5);
                     var5 = null;
                     var3 = null;
                     break;
                  } catch (Throwable var8) {
                     var14 = Py.setException(var8, var1);
                     if (!var14.match(var1.getglobal("OSError"))) {
                        throw var14;
                     }

                     var4 = var14.value;
                     var1.setlocal(15, var4);
                     var4 = null;
                     var1.setline(171);
                     var4 = imp.importOne("errno", var1, -1);
                     var1.setlocal(16, var4);
                     var4 = null;
                     var1.setline(172);
                     var4 = var1.getlocal(15).__getattr__("errno");
                     var10000 = var4._eq(var1.getlocal(16).__getattr__("EINTR"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(174);
                        if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                           var1.setline(175);
                           var4 = var1.getlocal(4);
                           var1.setlocal(0, var4);
                           var4 = null;
                        }

                        var1.setline(176);
                        throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(15).__getitem__(Py.newInteger(-1))})));
                     }
                  }
               }

               var1.setline(178);
               if (var1.getglobal("os").__getattr__("WIFSIGNALED").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                  var1.setline(179);
                  if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                     var1.setline(180);
                     var3 = var1.getlocal(4);
                     var1.setlocal(0, var3);
                     var3 = null;
                  }

                  var1.setline(181);
                  throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r terminated by signal %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("os").__getattr__("WTERMSIG").__call__(var2, var1.getlocal(14))})));
               }

               var1.setline(185);
               if (var1.getglobal("os").__getattr__("WIFEXITED").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                  var1.setline(186);
                  var3 = var1.getglobal("os").__getattr__("WEXITSTATUS").__call__(var2, var1.getlocal(14));
                  var1.setlocal(17, var3);
                  var3 = null;
                  var1.setline(187);
                  var3 = var1.getlocal(17);
                  var10000 = var3._eq(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(188);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(190);
                  if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                     var1.setline(191);
                     var3 = var1.getlocal(4);
                     var1.setlocal(0, var3);
                     var3 = null;
                  }

                  var1.setline(192);
                  throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command %r failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(17)})));
               }

               var1.setline(196);
               if (!var1.getglobal("os").__getattr__("WIFSTOPPED").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                  var1.setline(200);
                  if (var1.getglobal("DEBUG").__not__().__nonzero__()) {
                     var1.setline(201);
                     var3 = var1.getlocal(4);
                     var1.setlocal(0, var3);
                     var3 = null;
                  }

                  var1.setline(202);
                  throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("unknown error executing %r: termination status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(14)})));
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _spawn_java$6(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0)));
      var1.setline(208);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(209);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(211);
         PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(212);
         var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getglobal("_nt_quote_args").__call__(var2, var1.getlocal(0)));
         var1.setlocal(0, var3);
         var3 = null;

         try {
            var1.setline(215);
            var3 = var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(0))._rshift(Py.newInteger(8));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("OSError"))) {
               PyObject var4 = var6.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(218);
               throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command '%s' failed: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6).__getitem__(Py.newInteger(-1))})));
            }

            throw var6;
         }

         var1.setline(221);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(223);
            Py.println(PyString.fromInterned("command '%s' failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
            var1.setline(224);
            throw Py.makeException(var1.getglobal("DistutilsExecError"), PyString.fromInterned("command '%s' failed with exit status %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject find_executable$7(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      PyString.fromInterned("Tries to find 'executable' in the directories listed in 'path'.\n\n    A string listing directories separated by 'os.pathsep'; defaults to\n    os.environ['PATH'].  Returns the complete filename or None if not found.\n    ");
      var1.setline(233);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(234);
         var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("PATH"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(235);
      var3 = var1.getlocal(1).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(238);
      var3 = var1.getglobal("sys").__getattr__("platform");
      var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("os2"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("os").__getattr__("_name");
               var10000 = var3._eq(PyString.fromInterned("nt"));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(240);
         var3 = var1.getlocal(4);
         var10000 = var3._ne(PyString.fromInterned(".exe"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(241);
            var3 = var1.getlocal(0)._add(PyString.fromInterned(".exe"));
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(243);
      if (!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(251);
         var5 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(244);
         var3 = var1.getlocal(2).__iter__();

         do {
            var1.setline(244);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(249);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            }

            var1.setlocal(5, var6);
            var1.setline(245);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(0));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(246);
         } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(6)).__nonzero__());

         var1.setline(248);
         var5 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var5;
      }
   }

   public spawn$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cmd", "search_path", "verbose", "dry_run"};
      spawn$1 = Py.newCode(4, var2, var1, "spawn", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "i", "arg"};
      _nt_quote_args$2 = Py.newCode(1, var2, var1, "_nt_quote_args", 49, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "search_path", "verbose", "dry_run", "executable", "rc", "exc"};
      _spawn_nt$3 = Py.newCode(4, var2, var1, "_spawn_nt", 65, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "search_path", "verbose", "dry_run", "executable", "rc", "exc"};
      _spawn_os2$4 = Py.newCode(4, var2, var1, "_spawn_os2", 89, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "search_path", "verbose", "dry_run", "executable", "exec_fn", "env", "_[131_37]", "x", "cur_target", "_[137_36]", "my_msg", "pid", "e", "status", "exc", "errno", "exit_status"};
      _spawn_posix$5 = Py.newCode(4, var2, var1, "_spawn_posix", 118, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "search_path", "verbose", "dry_run", "executable", "rc", "exc"};
      _spawn_java$6 = Py.newCode(4, var2, var1, "_spawn_java", 206, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"executable", "path", "paths", "base", "ext", "p", "f"};
      find_executable$7 = Py.newCode(2, var2, var1, "find_executable", 227, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new spawn$py("distutils/spawn$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(spawn$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.spawn$1(var2, var3);
         case 2:
            return this._nt_quote_args$2(var2, var3);
         case 3:
            return this._spawn_nt$3(var2, var3);
         case 4:
            return this._spawn_os2$4(var2, var3);
         case 5:
            return this._spawn_posix$5(var2, var3);
         case 6:
            return this._spawn_java$6(var2, var3);
         case 7:
            return this.find_executable$7(var2, var3);
         default:
            return null;
      }
   }
}
