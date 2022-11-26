package distutils;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/core.py")
public class core$py extends PyFunctionTable implements PyRunnable {
   static core$py self;
   static final PyCode f$0;
   static final PyCode gen_usage$1;
   static final PyCode setup$2;
   static final PyCode run_setup$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.core\n\nThe only module that needs to be imported to use the Distutils; provides\nthe 'setup' function (which is to be called from the setup script).  Also\nindirectly provides the Distribution and Command classes, although they are\nreally defined in distutils.dist and distutils.cmd.\n"));
      var1.setline(7);
      PyString.fromInterned("distutils.core\n\nThe only module that needs to be imported to use the Distutils; provides\nthe 'setup' function (which is to be called from the setup script).  Also\nindirectly provides the Distribution and Command classes, although they are\nreally defined in distutils.dist and distutils.cmd.\n");
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
      String[] var6 = new String[]{"DEBUG"};
      PyObject[] var7 = imp.importFrom("distutils.debug", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"DistutilsSetupError", "DistutilsArgError", "DistutilsError", "CCompilerError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsArgError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("DistutilsError", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("CCompilerError", var4);
      var4 = null;
      var1.setline(19);
      var6 = new String[]{"Distribution"};
      var7 = imp.importFrom("distutils.dist", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.cmd", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"PyPIRCCommand"};
      var7 = imp.importFrom("distutils.config", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("PyPIRCCommand", var4);
      var4 = null;
      var1.setline(22);
      var6 = new String[]{"Extension"};
      var7 = imp.importFrom("distutils.extension", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Extension", var4);
      var4 = null;
      var1.setline(28);
      var3 = PyString.fromInterned("usage: %(script)s [global_opts] cmd1 [cmd1_opts] [cmd2 [cmd2_opts] ...]\n   or: %(script)s --help [cmd1 cmd2 ...]\n   or: %(script)s --help-commands\n   or: %(script)s cmd --help\n");
      var1.setlocal("USAGE", var3);
      var3 = null;
      var1.setline(35);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, gen_usage$1, (PyObject)null);
      var1.setlocal("gen_usage", var8);
      var3 = null;
      var1.setline(41);
      var5 = var1.getname("None");
      var1.setlocal("_setup_stop_after", var5);
      var3 = null;
      var1.setline(42);
      var5 = var1.getname("None");
      var1.setlocal("_setup_distribution", var5);
      var3 = null;
      var1.setline(45);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned("distclass"), PyString.fromInterned("script_name"), PyString.fromInterned("script_args"), PyString.fromInterned("options"), PyString.fromInterned("name"), PyString.fromInterned("version"), PyString.fromInterned("author"), PyString.fromInterned("author_email"), PyString.fromInterned("maintainer"), PyString.fromInterned("maintainer_email"), PyString.fromInterned("url"), PyString.fromInterned("license"), PyString.fromInterned("description"), PyString.fromInterned("long_description"), PyString.fromInterned("keywords"), PyString.fromInterned("platforms"), PyString.fromInterned("classifiers"), PyString.fromInterned("download_url"), PyString.fromInterned("requires"), PyString.fromInterned("provides"), PyString.fromInterned("obsoletes")});
      var1.setlocal("setup_keywords", var9);
      var3 = null;
      var1.setline(54);
      var9 = new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("sources"), PyString.fromInterned("include_dirs"), PyString.fromInterned("define_macros"), PyString.fromInterned("undef_macros"), PyString.fromInterned("library_dirs"), PyString.fromInterned("libraries"), PyString.fromInterned("runtime_library_dirs"), PyString.fromInterned("extra_objects"), PyString.fromInterned("extra_compile_args"), PyString.fromInterned("extra_link_args"), PyString.fromInterned("swig_opts"), PyString.fromInterned("export_symbols"), PyString.fromInterned("depends"), PyString.fromInterned("language")});
      var1.setlocal("extension_keywords", var9);
      var3 = null;
      var1.setline(60);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, setup$2, PyString.fromInterned("The gateway to the Distutils: do everything your setup script needs\n    to do, in a highly flexible and user-driven way.  Briefly: create a\n    Distribution instance; find and parse config files; parse the command\n    line; run each Distutils command found there, customized by the options\n    supplied to 'setup()' (as keyword arguments), in config files, and on\n    the command line.\n\n    The Distribution instance might be an instance of a class supplied via\n    the 'distclass' keyword argument to 'setup'; if no such class is\n    supplied, then the Distribution class (in dist.py) is instantiated.\n    All other arguments to 'setup' (except for 'cmdclass') are used to set\n    attributes of the Distribution instance.\n\n    The 'cmdclass' argument, if supplied, is a dictionary mapping command\n    names to command classes.  Each command encountered on the command line\n    will be turned into a command class, which is in turn instantiated; any\n    class found in 'cmdclass' is used in place of the default, which is\n    (for command 'foo_bar') class 'foo_bar' in module\n    'distutils.command.foo_bar'.  The command class must provide a\n    'user_options' attribute which is a list of option specifiers for\n    'distutils.fancy_getopt'.  Any command-line options between the current\n    and the next command are used to set attributes of the current command\n    object.\n\n    When the entire command-line has been successfully parsed, calls the\n    'run()' method on each command object in turn.  This method will be\n    driven entirely by the Distribution object (which each command object\n    has a reference to, thanks to its constructor), and the\n    command-specific options that became attributes of each command\n    object.\n    "));
      var1.setlocal("setup", var8);
      var3 = null;
      var1.setline(171);
      var7 = new PyObject[]{var1.getname("None"), PyString.fromInterned("run")};
      var8 = new PyFunction(var1.f_globals, var7, run_setup$3, PyString.fromInterned("Run a setup script in a somewhat controlled environment, and\n    return the Distribution instance that drives things.  This is useful\n    if you need to find out the distribution meta-data (passed as\n    keyword args from 'script' to 'setup()', or the contents of the\n    config files or command-line.\n\n    'script_name' is a file that will be run with 'execfile()';\n    'sys.argv[0]' will be replaced with 'script' for the duration of the\n    call.  'script_args' is a list of strings; if supplied,\n    'sys.argv[1:]' will be replaced by 'script_args' for the duration of\n    the call.\n\n    'stop_after' tells 'setup()' when to stop processing; possible\n    values:\n      init\n        stop after the Distribution instance has been created and\n        populated with the keyword arguments to 'setup()'\n      config\n        stop after config files have been parsed (and their data\n        stored in the Distribution instance)\n      commandline\n        stop after the command-line ('sys.argv[1:]' or 'script_args')\n        have been parsed (and the data stored in the Distribution)\n      run [default]\n        stop after all commands have been run (the same as if 'setup()'\n        had been called in the usual way\n\n    Returns the Distribution instance, which provides all information\n    used to drive the Distutils.\n    "));
      var1.setlocal("run_setup", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gen_usage$1(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("USAGE")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("script"), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setup$2(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("The gateway to the Distutils: do everything your setup script needs\n    to do, in a highly flexible and user-driven way.  Briefly: create a\n    Distribution instance; find and parse config files; parse the command\n    line; run each Distutils command found there, customized by the options\n    supplied to 'setup()' (as keyword arguments), in config files, and on\n    the command line.\n\n    The Distribution instance might be an instance of a class supplied via\n    the 'distclass' keyword argument to 'setup'; if no such class is\n    supplied, then the Distribution class (in dist.py) is instantiated.\n    All other arguments to 'setup' (except for 'cmdclass') are used to set\n    attributes of the Distribution instance.\n\n    The 'cmdclass' argument, if supplied, is a dictionary mapping command\n    names to command classes.  Each command encountered on the command line\n    will be turned into a command class, which is in turn instantiated; any\n    class found in 'cmdclass' is used in place of the default, which is\n    (for command 'foo_bar') class 'foo_bar' in module\n    'distutils.command.foo_bar'.  The command class must provide a\n    'user_options' attribute which is a list of option specifiers for\n    'distutils.fancy_getopt'.  Any command-line options between the current\n    and the next command are used to set attributes of the current command\n    object.\n\n    When the entire command-line has been successfully parsed, calls the\n    'run()' method on each command object in turn.  This method will be\n    driven entirely by the Distribution object (which each command object\n    has a reference to, thanks to its constructor), and the\n    command-specific options that became attributes of each command\n    object.\n    ");
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distclass"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(98);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(99);
         var1.getlocal(0).__delitem__((PyObject)PyString.fromInterned("distclass"));
      } else {
         var1.setline(101);
         var3 = var1.getglobal("Distribution");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(103);
      PyString var9 = PyString.fromInterned("script_name");
      PyObject var10000 = var9._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(104);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("script_name"), var3);
         var3 = null;
      }

      var1.setline(105);
      var9 = PyString.fromInterned("script_args");
      var10000 = var9._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("script_args"), var3);
         var3 = null;
      }

      PyObject var4;
      try {
         var1.setline(111);
         var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
         var1.setglobal("_setup_distribution", var3);
         var1.setlocal(2, var3);
      } catch (Throwable var8) {
         PyException var12 = Py.setException(var8, var1);
         if (var12.match(var1.getglobal("DistutilsSetupError"))) {
            var4 = var12.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(113);
            PyString var10 = PyString.fromInterned("name");
            var10000 = var10._in(var1.getlocal(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(114);
               throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("error in %s setup command: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(PyString.fromInterned("name")), var1.getlocal(3)})));
            }

            var1.setline(117);
            throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("error in setup command: %s")._mod(var1.getlocal(3)));
         }

         throw var12;
      }

      var1.setline(119);
      var3 = var1.getglobal("_setup_stop_after");
      var10000 = var3._eq(PyString.fromInterned("init"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(120);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(124);
         var1.getlocal(2).__getattr__("parse_config_files").__call__(var2);
         var1.setline(126);
         if (var1.getglobal("DEBUG").__nonzero__()) {
            var1.setline(127);
            Py.println(PyString.fromInterned("options (after parsing config files):"));
            var1.setline(128);
            var1.getlocal(2).__getattr__("dump_option_dicts").__call__(var2);
         }

         var1.setline(130);
         var4 = var1.getglobal("_setup_stop_after");
         var10000 = var4._eq(PyString.fromInterned("config"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            PyObject var5;
            PyException var11;
            try {
               var1.setline(137);
               var4 = var1.getlocal(2).__getattr__("parse_command_line").__call__(var2);
               var1.setlocal(4, var4);
               var4 = null;
            } catch (Throwable var6) {
               var11 = Py.setException(var6, var1);
               if (var11.match(var1.getglobal("DistutilsArgError"))) {
                  var5 = var11.value;
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(139);
                  throw Py.makeException(var1.getglobal("SystemExit"), var1.getglobal("gen_usage").__call__(var2, var1.getlocal(2).__getattr__("script_name"))._add(PyString.fromInterned("\nerror: %s")._mod(var1.getlocal(3))));
               }

               throw var11;
            }

            var1.setline(141);
            if (var1.getglobal("DEBUG").__nonzero__()) {
               var1.setline(142);
               Py.println(PyString.fromInterned("options (after parsing command line):"));
               var1.setline(143);
               var1.getlocal(2).__getattr__("dump_option_dicts").__call__(var2);
            }

            var1.setline(145);
            var4 = var1.getglobal("_setup_stop_after");
            var10000 = var4._eq(PyString.fromInterned("commandline"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(146);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(149);
               if (var1.getlocal(4).__nonzero__()) {
                  try {
                     var1.setline(151);
                     var1.getlocal(2).__getattr__("run_commands").__call__(var2);
                  } catch (Throwable var7) {
                     var11 = Py.setException(var7, var1);
                     if (var11.match(var1.getglobal("KeyboardInterrupt"))) {
                        var1.setline(153);
                        throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("interrupted"));
                     }

                     if (var11.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("os").__getattr__("error")}))) {
                        var5 = var11.value;
                        var1.setlocal(5, var5);
                        var5 = null;
                        var1.setline(155);
                        if (var1.getglobal("DEBUG").__nonzero__()) {
                           var1.setline(156);
                           var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("error: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)})));
                           var1.setline(157);
                           throw Py.makeException();
                        }

                        var1.setline(159);
                        throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("error: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)})));
                     }

                     if (var11.match(new PyTuple(new PyObject[]{var1.getglobal("DistutilsError"), var1.getglobal("CCompilerError")}))) {
                        var5 = var11.value;
                        var1.setlocal(3, var5);
                        var5 = null;
                        var1.setline(163);
                        if (var1.getglobal("DEBUG").__nonzero__()) {
                           var1.setline(164);
                           throw Py.makeException();
                        }

                        var1.setline(166);
                        throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("error: ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(3))));
                     }

                     throw var11;
                  }
               }

               var1.setline(168);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject run_setup$3(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyString.fromInterned("Run a setup script in a somewhat controlled environment, and\n    return the Distribution instance that drives things.  This is useful\n    if you need to find out the distribution meta-data (passed as\n    keyword args from 'script' to 'setup()', or the contents of the\n    config files or command-line.\n\n    'script_name' is a file that will be run with 'execfile()';\n    'sys.argv[0]' will be replaced with 'script' for the duration of the\n    call.  'script_args' is a list of strings; if supplied,\n    'sys.argv[1:]' will be replaced by 'script_args' for the duration of\n    the call.\n\n    'stop_after' tells 'setup()' when to stop processing; possible\n    values:\n      init\n        stop after the Distribution instance has been created and\n        populated with the keyword arguments to 'setup()'\n      config\n        stop after config files have been parsed (and their data\n        stored in the Distribution instance)\n      commandline\n        stop after the command-line ('sys.argv[1:]' or 'script_args')\n        have been parsed (and the data stored in the Distribution)\n      run [default]\n        stop after all commands have been run (the same as if 'setup()'\n        had been called in the usual way\n\n    Returns the Distribution instance, which provides all information\n    used to drive the Distutils.\n    ");
      var1.setline(202);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("init"), PyString.fromInterned("config"), PyString.fromInterned("commandline"), PyString.fromInterned("run")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(203);
         throw Py.makeException(var1.getname("ValueError"), PyString.fromInterned("invalid value for 'stop_after': %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
      } else {
         var1.setline(206);
         var3 = var1.getlocal(2);
         var1.setglobal("_setup_stop_after", var3);
         var3 = null;
         var1.setline(208);
         var3 = var1.getname("sys").__getattr__("argv");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(209);
         PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("__file__"), var1.getlocal(0)});
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(210);
         var8 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(5, var8);
         var3 = null;

         try {
            var3 = null;

            PyObject var4;
            try {
               var1.setline(213);
               var4 = var1.getlocal(0);
               var1.getname("sys").__getattr__("argv").__setitem__((PyObject)Py.newInteger(0), var4);
               var4 = null;
               var1.setline(214);
               var4 = var1.getlocal(1);
               var10000 = var4._isnot(var1.getname("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(215);
                  var4 = var1.getlocal(1);
                  var1.getname("sys").__getattr__("argv").__setslice__(Py.newInteger(1), (PyObject)null, (PyObject)null, var4);
                  var4 = null;
               }

               var1.setline(216);
               var4 = var1.getname("open").__call__(var2, var1.getlocal(0));
               var1.setlocal(6, var4);
               var4 = null;
               var4 = null;

               try {
                  var1.setline(218);
                  Py.exec(var1.getlocal(6).__getattr__("read").__call__(var2), var1.getlocal(4), var1.getlocal(5));
               } catch (Throwable var5) {
                  Py.addTraceback(var5, var1);
                  var1.setline(220);
                  var1.getlocal(6).__getattr__("close").__call__(var2);
                  throw (Throwable)var5;
               }

               var1.setline(220);
               var1.getlocal(6).__getattr__("close").__call__(var2);
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(222);
               var4 = var1.getlocal(3);
               var1.getname("sys").__setattr__("argv", var4);
               var4 = null;
               var1.setline(223);
               var4 = var1.getname("None");
               var1.setglobal("_setup_stop_after", var4);
               var4 = null;
               throw (Throwable)var6;
            }

            var1.setline(222);
            var4 = var1.getlocal(3);
            var1.getname("sys").__setattr__("argv", var4);
            var4 = null;
            var1.setline(223);
            var4 = var1.getname("None");
            var1.setglobal("_setup_stop_after", var4);
            var4 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (!var9.match(var1.getname("SystemExit"))) {
               var1.setline(229);
               throw Py.makeException();
            }

            var1.setline(227);
         }

         var1.setline(231);
         var3 = var1.getglobal("_setup_distribution");
         var10000 = var3._is(var1.getname("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(232);
            throw Py.makeException(var1.getname("RuntimeError"), PyString.fromInterned("'distutils.core.setup()' was never called -- perhaps '%s' is not a Distutils setup script?")._mod(var1.getlocal(0)));
         } else {
            var1.setline(239);
            var3 = var1.getglobal("_setup_distribution");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public core$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"script_name", "script"};
      gen_usage$1 = Py.newCode(1, var2, var1, "gen_usage", 35, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"attrs", "klass", "dist", "msg", "ok", "exc"};
      setup$2 = Py.newCode(1, var2, var1, "setup", 60, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"script_name", "script_args", "stop_after", "save_argv", "g", "l", "f"};
      run_setup$3 = Py.newCode(3, var2, var1, "run_setup", 171, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new core$py("distutils/core$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(core$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.gen_usage$1(var2, var3);
         case 2:
            return this.setup$2(var2, var3);
         case 3:
            return this.run_setup$3(var2, var3);
         default:
            return null;
      }
   }
}
