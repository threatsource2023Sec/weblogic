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
@Filename("distutils/cmd.py")
public class cmd$py extends PyFunctionTable implements PyRunnable {
   static distutils.cmd$py self;
   static final PyCode f$0;
   static final PyCode Command$1;
   static final PyCode __init__$2;
   static final PyCode __getattr__$3;
   static final PyCode ensure_finalized$4;
   static final PyCode initialize_options$5;
   static final PyCode finalize_options$6;
   static final PyCode dump_options$7;
   static final PyCode run$8;
   static final PyCode announce$9;
   static final PyCode debug_print$10;
   static final PyCode _ensure_stringlike$11;
   static final PyCode ensure_string$12;
   static final PyCode ensure_string_list$13;
   static final PyCode _ensure_tested_string$14;
   static final PyCode ensure_filename$15;
   static final PyCode ensure_dirname$16;
   static final PyCode get_command_name$17;
   static final PyCode set_undefined_options$18;
   static final PyCode get_finalized_command$19;
   static final PyCode reinitialize_command$20;
   static final PyCode run_command$21;
   static final PyCode get_sub_commands$22;
   static final PyCode warn$23;
   static final PyCode execute$24;
   static final PyCode mkpath$25;
   static final PyCode copy_file$26;
   static final PyCode copy_tree$27;
   static final PyCode move_file$28;
   static final PyCode spawn$29;
   static final PyCode make_archive$30;
   static final PyCode make_file$31;
   static final PyCode install_misc$32;
   static final PyCode initialize_options$33;
   static final PyCode _install_dir_from$34;
   static final PyCode _copy_files$35;
   static final PyCode get_outputs$36;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.cmd\n\nProvides the Command class, the base class for the command classes\nin the distutils.command package.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.cmd\n\nProvides the Command class, the base class for the command classes\nin the distutils.command package.\n");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"DistutilsOptionError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"util", "dir_util", "file_util", "archive_util", "dep_util"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("util", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("dir_util", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("file_util", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("archive_util", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("dep_util", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(14);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Command", var7, Command$1);
      var1.setlocal("Command", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(433);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_misc", var7, install_misc$32);
      var1.setlocal("install_misc", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Command$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class for defining command classes, the \"worker bees\"\n    of the Distutils.  A useful analogy for command classes is to think of\n    them as subroutines with local variables called \"options\".  The options\n    are \"declared\" in 'initialize_options()' and \"defined\" (given their\n    final values, aka \"finalized\") in 'finalize_options()', both of which\n    must be defined by every command class.  The distinction between the\n    two is necessary because option values might come from the outside\n    world (command line, config file, ...), and any options dependent on\n    other options must be computed *after* these outside influences have\n    been processed -- hence 'finalize_options()'.  The \"body\" of the\n    subroutine, where it does all its work based on the values of its\n    options, is the 'run()' method, which must also be implemented by every\n    command class.\n    "));
      var1.setline(28);
      PyString.fromInterned("Abstract base class for defining command classes, the \"worker bees\"\n    of the Distutils.  A useful analogy for command classes is to think of\n    them as subroutines with local variables called \"options\".  The options\n    are \"declared\" in 'initialize_options()' and \"defined\" (given their\n    final values, aka \"finalized\") in 'finalize_options()', both of which\n    must be defined by every command class.  The distinction between the\n    two is necessary because option values might come from the outside\n    world (command line, config file, ...), and any options dependent on\n    other options must be computed *after* these outside influences have\n    been processed -- hence 'finalize_options()'.  The \"body\" of the\n    subroutine, where it does all its work based on the values of its\n    options, is the 'run()' method, which must also be implemented by every\n    command class.\n    ");
      var1.setline(44);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("sub_commands", var3);
      var3 = null;
      var1.setline(49);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("Create and initialize a new Command object.  Most importantly,\n        invokes the 'initialize_options()' method, which is the real\n        initializer and depends on the actual command being\n        instantiated.\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(97);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$3, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(107);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ensure_finalized$4, (PyObject)null);
      var1.setlocal("ensure_finalized", var5);
      var3 = null;
      var1.setline(125);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, initialize_options$5, PyString.fromInterned("Set default values for all the options that this command\n        supports.  Note that these defaults may be overridden by other\n        commands, by the setup script, by config files, or by the\n        command-line.  Thus, this is not the place to code dependencies\n        between options; generally, 'initialize_options()' implementations\n        are just a bunch of \"self.foo = None\" assignments.\n\n        This method must be implemented by all command classes.\n        "));
      var1.setlocal("initialize_options", var5);
      var3 = null;
      var1.setline(138);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finalize_options$6, PyString.fromInterned("Set final values for all the options that this command supports.\n        This is always called as late as possible, ie.  after any option\n        assignments from the command-line or from other commands have been\n        done.  Thus, this is the place to code option dependencies: if\n        'foo' depends on 'bar', then it is safe to set 'foo' from 'bar' as\n        long as 'foo' still has the same value it was assigned in\n        'initialize_options()'.\n\n        This method must be implemented by all command classes.\n        "));
      var1.setlocal("finalize_options", var5);
      var3 = null;
      var1.setline(153);
      var4 = new PyObject[]{var1.getname("None"), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, dump_options$7, (PyObject)null);
      var1.setlocal("dump_options", var5);
      var3 = null;
      var1.setline(167);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$8, PyString.fromInterned("A command's raison d'etre: carry out the action it exists to\n        perform, controlled by the options initialized in\n        'initialize_options()', customized by other commands, the setup\n        script, the command-line, and config files, and finalized in\n        'finalize_options()'.  All terminal output and filesystem\n        interaction should be done by 'run()'.\n\n        This method must be implemented by all command classes.\n        "));
      var1.setlocal("run", var5);
      var3 = null;
      var1.setline(180);
      var4 = new PyObject[]{Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, announce$9, PyString.fromInterned("If the current verbosity level is of greater than or equal to\n        'level' print 'msg' to stdout.\n        "));
      var1.setlocal("announce", var5);
      var3 = null;
      var1.setline(186);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, debug_print$10, PyString.fromInterned("Print 'msg' to stdout if the global DEBUG (taken from the\n        DISTUTILS_DEBUG environment variable) flag is true.\n        "));
      var1.setlocal("debug_print", var5);
      var3 = null;
      var1.setline(209);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _ensure_stringlike$11, (PyObject)null);
      var1.setlocal("_ensure_stringlike", var5);
      var3 = null;
      var1.setline(219);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, ensure_string$12, PyString.fromInterned("Ensure that 'option' is a string; if not defined, set it to\n        'default'.\n        "));
      var1.setlocal("ensure_string", var5);
      var3 = null;
      var1.setline(225);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ensure_string_list$13, PyString.fromInterned("Ensure that 'option' is a list of strings.  If 'option' is\n        currently a string, we split it either on /,\\s*/ or /\\s+/, so\n        \"foo bar baz\", \"foo,bar,baz\", and \"foo,   bar baz\" all become\n        [\"foo\", \"bar\", \"baz\"].\n        "));
      var1.setlocal("ensure_string_list", var5);
      var3 = null;
      var1.setline(253);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _ensure_tested_string$14, (PyObject)null);
      var1.setlocal("_ensure_tested_string", var5);
      var3 = null;
      var1.setline(260);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ensure_filename$15, PyString.fromInterned("Ensure that 'option' is the name of an existing file."));
      var1.setlocal("ensure_filename", var5);
      var3 = null;
      var1.setline(266);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ensure_dirname$16, (PyObject)null);
      var1.setlocal("ensure_dirname", var5);
      var3 = null;
      var1.setline(274);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_command_name$17, (PyObject)null);
      var1.setlocal("get_command_name", var5);
      var3 = null;
      var1.setline(280);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_undefined_options$18, PyString.fromInterned("Set the values of any \"undefined\" options from corresponding\n        option values in some other command object.  \"Undefined\" here means\n        \"is None\", which is the convention used to indicate that an option\n        has not been changed between 'initialize_options()' and\n        'finalize_options()'.  Usually called from 'finalize_options()' for\n        options that depend on some other command rather than another\n        option of the same command.  'src_cmd' is the other command from\n        which option values will be taken (a command object will be created\n        for it if necessary); the remaining arguments are\n        '(src_option,dst_option)' tuples which mean \"take the value of\n        'src_option' in the 'src_cmd' command object, and copy it to\n        'dst_option' in the current command object\".\n        "));
      var1.setlocal("set_undefined_options", var5);
      var3 = null;
      var1.setline(305);
      var4 = new PyObject[]{Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, get_finalized_command$19, PyString.fromInterned("Wrapper around Distribution's 'get_command_obj()' method: find\n        (create if necessary and 'create' is true) the command object for\n        'command', call its 'ensure_finalized()' method, and return the\n        finalized command object.\n        "));
      var1.setlocal("get_finalized_command", var5);
      var3 = null;
      var1.setline(317);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, reinitialize_command$20, (PyObject)null);
      var1.setlocal("reinitialize_command", var5);
      var3 = null;
      var1.setline(321);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run_command$21, PyString.fromInterned("Run some other command: uses the 'run_command()' method of\n        Distribution, which creates and finalizes the command object if\n        necessary and then invokes its 'run()' method.\n        "));
      var1.setlocal("run_command", var5);
      var3 = null;
      var1.setline(328);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_sub_commands$22, PyString.fromInterned("Determine the sub-commands that are relevant in the current\n        distribution (ie., that need to be run).  This is based on the\n        'sub_commands' class attribute: each tuple in that list may include\n        a method that we call to determine if the subcommand needs to be\n        run for the current distribution.  Return a list of command names.\n        "));
      var1.setlocal("get_sub_commands", var5);
      var3 = null;
      var1.setline(344);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, warn$23, (PyObject)null);
      var1.setlocal("warn", var5);
      var3 = null;
      var1.setline(348);
      var4 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, execute$24, (PyObject)null);
      var1.setlocal("execute", var5);
      var3 = null;
      var1.setline(351);
      var4 = new PyObject[]{Py.newInteger(511)};
      var5 = new PyFunction(var1.f_globals, var4, mkpath$25, (PyObject)null);
      var1.setlocal("mkpath", var5);
      var3 = null;
      var1.setline(354);
      var4 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), var1.getname("None"), Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, copy_file$26, PyString.fromInterned("Copy a file respecting verbose, dry-run and force flags.  (The\n        former two default to whatever is in the Distribution object, and\n        the latter defaults to false for commands that don't define it.)"));
      var1.setlocal("copy_file", var5);
      var3 = null;
      var1.setline(367);
      var4 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, copy_tree$27, PyString.fromInterned("Copy an entire directory tree respecting verbose, dry-run,\n        and force flags.\n        "));
      var1.setlocal("copy_tree", var5);
      var3 = null;
      var1.setline(379);
      var4 = new PyObject[]{Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, move_file$28, PyString.fromInterned("Move a file respecting dry-run flag."));
      var1.setlocal("move_file", var5);
      var3 = null;
      var1.setline(383);
      var4 = new PyObject[]{Py.newInteger(1), Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, spawn$29, PyString.fromInterned("Spawn an external command respecting dry-run flag."));
      var1.setlocal("spawn", var5);
      var3 = null;
      var1.setline(388);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, make_archive$30, (PyObject)null);
      var1.setlocal("make_archive", var5);
      var3 = null;
      var1.setline(394);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(1)};
      var5 = new PyFunction(var1.f_globals, var4, make_file$31, PyString.fromInterned("Special case of 'execute()' for operations that process one or\n        more input files and generate one output file.  Works just like\n        'execute()', except the operation is skipped and a different\n        message printed if 'outfile' already exists and is newer than all\n        files listed in 'infiles'.  If the command defined 'self.force',\n        and it is true, then the command is unconditionally run -- does no\n        timestamp checks.\n        "));
      var1.setlocal("make_file", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("Create and initialize a new Command object.  Most importantly,\n        invokes the 'initialize_options()' method, which is the real\n        initializer and depends on the actual command being\n        instantiated.\n        ");
      var1.setline(56);
      String[] var3 = new String[]{"Distribution"};
      PyObject[] var5 = imp.importFrom("distutils.dist", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(58);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(59);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("dist must be a Distribution instance"));
      } else {
         var1.setline(60);
         PyObject var6 = var1.getlocal(0).__getattr__("__class__");
         PyObject var10000 = var6._is(var1.getglobal("Command"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(61);
            throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Command is an abstract class"));
         } else {
            var1.setline(63);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("distribution", var6);
            var3 = null;
            var1.setline(64);
            var1.getlocal(0).__getattr__("initialize_options").__call__(var2);
            var1.setline(74);
            var6 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_dry_run", var6);
            var3 = null;
            var1.setline(78);
            var6 = var1.getlocal(1).__getattr__("verbose");
            var1.getlocal(0).__setattr__("verbose", var6);
            var3 = null;
            var1.setline(84);
            var6 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("force", var6);
            var3 = null;
            var1.setline(88);
            PyInteger var7 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"help", var7);
            var3 = null;
            var1.setline(94);
            var7 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"finalized", var7);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __getattr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("dry_run"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(99);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_")._add(var1.getlocal(1)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(100);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(101);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("distribution"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(103);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(105);
         throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
      }
   }

   public PyObject ensure_finalized$4(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      if (var1.getlocal(0).__getattr__("finalized").__not__().__nonzero__()) {
         var1.setline(109);
         var1.getlocal(0).__getattr__("finalize_options").__call__(var2);
      }

      var1.setline(110);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"finalized", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initialize_options$5(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("Set default values for all the options that this command\n        supports.  Note that these defaults may be overridden by other\n        commands, by the setup script, by config files, or by the\n        command-line.  Thus, this is not the place to code dependencies\n        between options; generally, 'initialize_options()' implementations\n        are just a bunch of \"self.foo = None\" assignments.\n\n        This method must be implemented by all command classes.\n        ");
      var1.setline(135);
      throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("abstract method -- subclass %s must override")._mod(var1.getlocal(0).__getattr__("__class__")));
   }

   public PyObject finalize_options$6(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Set final values for all the options that this command supports.\n        This is always called as late as possible, ie.  after any option\n        assignments from the command-line or from other commands have been\n        done.  Thus, this is the place to code option dependencies: if\n        'foo' depends on 'bar', then it is safe to set 'foo' from 'bar' as\n        long as 'foo' still has the same value it was assigned in\n        'initialize_options()'.\n\n        This method must be implemented by all command classes.\n        ");
      var1.setline(149);
      throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("abstract method -- subclass %s must override")._mod(var1.getlocal(0).__getattr__("__class__")));
   }

   public PyObject dump_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      String[] var3 = new String[]{"longopt_xlate"};
      PyObject[] var7 = imp.importFrom("distutils.fancy_getopt", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(155);
      PyObject var8 = var1.getlocal(1);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var8 = PyString.fromInterned("command options for '%s':")._mod(var1.getlocal(0).__getattr__("get_command_name").__call__(var2));
         var1.setlocal(1, var8);
         var3 = null;
      }

      var1.setline(157);
      var10000 = var1.getlocal(0).__getattr__("announce");
      var7 = new PyObject[]{var1.getlocal(2)._add(var1.getlocal(1)), var1.getglobal("log").__getattr__("INFO")};
      String[] var9 = new String[]{"level"};
      var10000.__call__(var2, var7, var9);
      var3 = null;
      var1.setline(158);
      var8 = var1.getlocal(2)._add(PyString.fromInterned("  "));
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(159);
      var8 = var1.getlocal(0).__getattr__("user_options").__iter__();

      while(true) {
         var1.setline(159);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(160);
         PyObject var10 = var1.getlocal(4).__getattr__("translate").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var10);
         var5 = null;
         var1.setline(161);
         var10 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
         var10000 = var10._eq(PyString.fromInterned("="));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(162);
            var10 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(4, var10);
            var5 = null;
         }

         var1.setline(163);
         var10 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(6, var10);
         var5 = null;
         var1.setline(164);
         var10000 = var1.getlocal(0).__getattr__("announce");
         var5 = new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("%s = %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6)}))), var1.getglobal("log").__getattr__("INFO")};
         String[] var11 = new String[]{"level"};
         var10000.__call__(var2, var5, var11);
         var5 = null;
      }
   }

   public PyObject run$8(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("A command's raison d'etre: carry out the action it exists to\n        perform, controlled by the options initialized in\n        'initialize_options()', customized by other commands, the setup\n        script, the command-line, and config files, and finalized in\n        'finalize_options()'.  All terminal output and filesystem\n        interaction should be done by 'run()'.\n\n        This method must be implemented by all command classes.\n        ");
      var1.setline(177);
      throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("abstract method -- subclass %s must override")._mod(var1.getlocal(0).__getattr__("__class__")));
   }

   public PyObject announce$9(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyString.fromInterned("If the current verbosity level is of greater than or equal to\n        'level' print 'msg' to stdout.\n        ");
      var1.setline(184);
      var1.getglobal("log").__getattr__("log").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug_print$10(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Print 'msg' to stdout if the global DEBUG (taken from the\n        DISTUTILS_DEBUG environment variable) flag is true.\n        ");
      var1.setline(190);
      String[] var3 = new String[]{"DEBUG"};
      PyObject[] var5 = imp.importFrom("distutils.debug", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(191);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(192);
         Py.println(var1.getlocal(1));
         var1.setline(193);
         var1.getglobal("sys").__getattr__("stdout").__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ensure_stringlike$11(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(211);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(3));
         var1.setline(213);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(214);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("str")).__not__().__nonzero__()) {
            var1.setline(215);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("'%s' must be a %s (got `%s`)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(4)})));
         } else {
            var1.setline(217);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject ensure_string$12(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyString.fromInterned("Ensure that 'option' is a string; if not defined, set it to\n        'default'.\n        ");
      var1.setline(223);
      var1.getlocal(0).__getattr__("_ensure_stringlike").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("string"), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ensure_string_list$13(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Ensure that 'option' is a list of strings.  If 'option' is\n        currently a string, we split it either on /,\\s*/ or /\\s+/, so\n        \"foo bar baz\", \"foo,bar,baz\", and \"foo,   bar baz\" all become\n        [\"foo\", \"bar\", \"baz\"].\n        ");
      var1.setline(231);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(232);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(233);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(234);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
            var1.setline(235);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("re").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",\\s*|\\s+"), (PyObject)var1.getlocal(2)));
         } else {
            var1.setline(237);
            PyInteger var6;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("list")).__nonzero__()) {
               var1.setline(239);
               var6 = Py.newInteger(1);
               var1.setlocal(3, var6);
               var3 = null;
               var1.setline(240);
               var3 = var1.getlocal(2).__iter__();

               while(true) {
                  var1.setline(240);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(241);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("str")).__not__().__nonzero__()) {
                     var1.setline(242);
                     PyInteger var5 = Py.newInteger(0);
                     var1.setlocal(3, var5);
                     var5 = null;
                     break;
                  }
               }
            } else {
               var1.setline(245);
               var6 = Py.newInteger(0);
               var1.setlocal(3, var6);
               var3 = null;
            }

            var1.setline(247);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(248);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("'%s' must be a list of strings (got %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _ensure_tested_string$14(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = var1.getlocal(0).__getattr__("_ensure_stringlike").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(256);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__call__(var2, var1.getlocal(6)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(257);
         throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("error in '%s' option: ")._add(var1.getlocal(4))._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)})));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject ensure_filename$15(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyString.fromInterned("Ensure that 'option' is the name of an existing file.");
      var1.setline(262);
      var1.getlocal(0).__getattr__("_ensure_tested_string").__call__(var2, var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("isfile"), PyString.fromInterned("filename"), PyString.fromInterned("'%s' does not exist or is not a file"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ensure_dirname$16(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      var1.getlocal(0).__getattr__("_ensure_tested_string").__call__(var2, var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("isdir"), PyString.fromInterned("directory name"), PyString.fromInterned("'%s' does not exist or is not a directory"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_command_name$17(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("command_name")).__nonzero__()) {
         var1.setline(276);
         var3 = var1.getlocal(0).__getattr__("command_name");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(278);
         var3 = var1.getlocal(0).__getattr__("__class__").__getattr__("__name__");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject set_undefined_options$18(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyString.fromInterned("Set the values of any \"undefined\" options from corresponding\n        option values in some other command object.  \"Undefined\" here means\n        \"is None\", which is the convention used to indicate that an option\n        has not been changed between 'initialize_options()' and\n        'finalize_options()'.  Usually called from 'finalize_options()' for\n        options that depend on some other command rather than another\n        option of the same command.  'src_cmd' is the other command from\n        which option values will be taken (a command object will be created\n        for it if necessary); the remaining arguments are\n        '(src_option,dst_option)' tuples which mean \"take the value of\n        'src_option' in the 'src_cmd' command object, and copy it to\n        'dst_option' in the current command object\".\n        ");
      var1.setline(297);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(298);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(299);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(299);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(300);
         PyObject var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(5));
         PyObject var10000 = var7._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(301);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(5), var1.getglobal("getattr").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
         }
      }
   }

   public PyObject get_finalized_command$19(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyString.fromInterned("Wrapper around Distribution's 'get_command_obj()' method: find\n        (create if necessary and 'create' is true) the command object for\n        'command', call its 'ensure_finalized()' method, and return the\n        finalized command object.\n        ");
      var1.setline(311);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(312);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(313);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reinitialize_command$20(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("reinitialize_command").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run_command$21(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyString.fromInterned("Run some other command: uses the 'run_command()' method of\n        Distribution, which creates and finalizes the command object if\n        necessary and then invokes its 'run()' method.\n        ");
      var1.setline(326);
      var1.getlocal(0).__getattr__("distribution").__getattr__("run_command").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_sub_commands$22(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyString.fromInterned("Determine the sub-commands that are relevant in the current\n        distribution (ie., that need to be run).  This is based on the\n        'sub_commands' class attribute: each tuple in that list may include\n        a method that we call to determine if the subcommand needs to be\n        run for the current distribution.  Return a list of command names.\n        ");
      var1.setline(335);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(336);
      PyObject var7 = var1.getlocal(0).__getattr__("sub_commands").__iter__();

      while(true) {
         var1.setline(336);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(339);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(337);
         PyObject var8 = var1.getlocal(3);
         PyObject var10000 = var8._is(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
         }

         if (var10000.__nonzero__()) {
            var1.setline(338);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject warn$23(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("warning: %s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("get_command_name").__call__(var2), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execute$24(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyObject var10000 = var1.getglobal("util").__getattr__("execute");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mkpath$25(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyObject var10000 = var1.getglobal("dir_util").__getattr__("mkpath");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy_file$26(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned("Copy a file respecting verbose, dry-run and force flags.  (The\n        former two default to whatever is in the Distribution object, and\n        the latter defaults to false for commands that don't define it.)");
      var1.setline(360);
      PyObject var10000 = var1.getglobal("file_util").__getattr__("copy_file");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(0).__getattr__("force").__not__(), var1.getlocal(5), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject copy_tree$27(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyString.fromInterned("Copy an entire directory tree respecting verbose, dry-run,\n        and force flags.\n        ");
      var1.setline(373);
      PyObject var10000 = var1.getglobal("dir_util").__getattr__("copy_tree");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(0).__getattr__("force").__not__(), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject move_file$28(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyString.fromInterned("Move a file respecting dry-run flag.");
      var1.setline(381);
      PyObject var10000 = var1.getglobal("file_util").__getattr__("move_file");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject spawn$29(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyString.fromInterned("Spawn an external command respecting dry-run flag.");
      var1.setline(385);
      String[] var3 = new String[]{"spawn"};
      PyObject[] var5 = imp.importFrom("distutils.spawn", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(386);
      PyObject var10000 = var1.getlocal(4);
      var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var6 = new String[]{"dry_run"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_archive$30(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var10000 = var1.getglobal("archive_util").__getattr__("make_archive");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(0).__getattr__("dry_run"), var1.getlocal(5), var1.getlocal(6)};
      String[] var4 = new String[]{"dry_run", "owner", "group"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject make_file$31(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyString.fromInterned("Special case of 'execute()' for operations that process one or\n        more input files and generate one output file.  Works just like\n        'execute()', except the operation is skipped and a different\n        message printed if 'outfile' already exists and is newer than all\n        files listed in 'infiles'.  If the command defined 'self.force',\n        and it is true, then the command is unconditionally run -- does no\n        timestamp checks.\n        ");
      var1.setline(404);
      PyObject var3 = var1.getlocal(6);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(405);
         var3 = PyString.fromInterned("skipping %s (inputs unchanged)")._mod(var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(408);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(409);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(410);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__().__nonzero__()) {
            var1.setline(411);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'infiles' must be a string, or a list or tuple of strings"));
         }
      }

      var1.setline(414);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(415);
         var3 = PyString.fromInterned("generating %s from %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))}));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(421);
      var10000 = var1.getlocal(0).__getattr__("force");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("dep_util").__getattr__("newer_group").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      if (var10000.__nonzero__()) {
         var1.setline(422);
         var1.getlocal(0).__getattr__("execute").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7));
      } else {
         var1.setline(426);
         var1.getglobal("log").__getattr__("debug").__call__(var2, var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_misc$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Common base class for installing some files in a subdirectory.\n    Currently used by install_data and install_scripts.\n    "));
      var1.setline(436);
      PyString.fromInterned("Common base class for installing some files in a subdirectory.\n    Currently used by install_data and install_scripts.\n    ");
      var1.setline(438);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to install the files to")})});
      var1.setlocal("user_options", var3);
      var3 = null;
      var1.setline(440);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, initialize_options$33, (PyObject)null);
      var1.setlocal("initialize_options", var5);
      var3 = null;
      var1.setline(444);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _install_dir_from$34, (PyObject)null);
      var1.setlocal("_install_dir_from", var5);
      var3 = null;
      var1.setline(447);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _copy_files$35, (PyObject)null);
      var1.setlocal("_copy_files", var5);
      var3 = null;
      var1.setline(456);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_outputs$36, (PyObject)null);
      var1.setlocal("get_outputs", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$33(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(442);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"outfiles", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _install_dir_from$34(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("install_dir")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _copy_files$35(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"outfiles", var3);
      var3 = null;
      var1.setline(449);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(450);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(451);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("install_dir"));
         var1.setline(452);
         PyObject var5 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(452);
            PyObject var4 = var5.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(453);
            var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("install_dir"));
            var1.setline(454);
            var1.getlocal(0).__getattr__("outfiles").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_dir"), var1.getlocal(2)));
         }
      }
   }

   public PyObject get_outputs$36(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyObject var3 = var1.getlocal(0).__getattr__("outfiles");
      var1.f_lasti = -1;
      return var3;
   }

   public cmd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Command$1 = Py.newCode(0, var2, var1, "Command", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dist", "Distribution"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 49, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "myval"};
      __getattr__$3 = Py.newCode(2, var2, var1, "__getattr__", 97, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ensure_finalized$4 = Py.newCode(1, var2, var1, "ensure_finalized", 107, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$5 = Py.newCode(1, var2, var1, "initialize_options", 125, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$6 = Py.newCode(1, var2, var1, "finalize_options", 138, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "indent", "longopt_xlate", "option", "_", "value"};
      dump_options$7 = Py.newCode(3, var2, var1, "dump_options", 153, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$8 = Py.newCode(1, var2, var1, "run", 167, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "level"};
      announce$9 = Py.newCode(3, var2, var1, "announce", 180, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "DEBUG"};
      debug_print$10 = Py.newCode(2, var2, var1, "debug_print", 186, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "what", "default", "val"};
      _ensure_stringlike$11 = Py.newCode(4, var2, var1, "_ensure_stringlike", 209, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "default"};
      ensure_string$12 = Py.newCode(3, var2, var1, "ensure_string", 219, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "val", "ok", "element"};
      ensure_string_list$13 = Py.newCode(2, var2, var1, "ensure_string_list", 225, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "tester", "what", "error_fmt", "default", "val"};
      _ensure_tested_string$14 = Py.newCode(6, var2, var1, "_ensure_tested_string", 253, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option"};
      ensure_filename$15 = Py.newCode(2, var2, var1, "ensure_filename", 260, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option"};
      ensure_dirname$16 = Py.newCode(2, var2, var1, "ensure_dirname", 266, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_command_name$17 = Py.newCode(1, var2, var1, "get_command_name", 274, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src_cmd", "option_pairs", "src_cmd_obj", "src_option", "dst_option"};
      set_undefined_options$18 = Py.newCode(3, var2, var1, "set_undefined_options", 280, true, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "create", "cmd_obj"};
      get_finalized_command$19 = Py.newCode(3, var2, var1, "get_finalized_command", 305, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "reinit_subcommands"};
      reinitialize_command$20 = Py.newCode(3, var2, var1, "reinitialize_command", 317, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command"};
      run_command$21 = Py.newCode(2, var2, var1, "run_command", 321, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "commands", "cmd_name", "method"};
      get_sub_commands$22 = Py.newCode(1, var2, var1, "get_sub_commands", 328, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      warn$23 = Py.newCode(2, var2, var1, "warn", 344, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "args", "msg", "level"};
      execute$24 = Py.newCode(5, var2, var1, "execute", 348, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "mode"};
      mkpath$25 = Py.newCode(3, var2, var1, "mkpath", 351, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "outfile", "preserve_mode", "preserve_times", "link", "level"};
      copy_file$26 = Py.newCode(7, var2, var1, "copy_file", 354, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "outfile", "preserve_mode", "preserve_times", "preserve_symlinks", "level"};
      copy_tree$27 = Py.newCode(7, var2, var1, "copy_tree", 367, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src", "dst", "level"};
      move_file$28 = Py.newCode(4, var2, var1, "move_file", 379, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "search_path", "level", "spawn"};
      spawn$29 = Py.newCode(4, var2, var1, "spawn", 383, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "base_name", "format", "root_dir", "base_dir", "owner", "group"};
      make_archive$30 = Py.newCode(7, var2, var1, "make_archive", 388, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infiles", "outfile", "func", "args", "exec_msg", "skip_msg", "level"};
      make_file$31 = Py.newCode(8, var2, var1, "make_file", 394, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      install_misc$32 = Py.newCode(0, var2, var1, "install_misc", 433, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$33 = Py.newCode(1, var2, var1, "initialize_options", 440, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirname"};
      _install_dir_from$34 = Py.newCode(2, var2, var1, "_install_dir_from", 444, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filelist", "f"};
      _copy_files$35 = Py.newCode(2, var2, var1, "_copy_files", 447, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_outputs$36 = Py.newCode(1, var2, var1, "get_outputs", 456, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new distutils.cmd$py("distutils/cmd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(distutils.cmd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Command$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__getattr__$3(var2, var3);
         case 4:
            return this.ensure_finalized$4(var2, var3);
         case 5:
            return this.initialize_options$5(var2, var3);
         case 6:
            return this.finalize_options$6(var2, var3);
         case 7:
            return this.dump_options$7(var2, var3);
         case 8:
            return this.run$8(var2, var3);
         case 9:
            return this.announce$9(var2, var3);
         case 10:
            return this.debug_print$10(var2, var3);
         case 11:
            return this._ensure_stringlike$11(var2, var3);
         case 12:
            return this.ensure_string$12(var2, var3);
         case 13:
            return this.ensure_string_list$13(var2, var3);
         case 14:
            return this._ensure_tested_string$14(var2, var3);
         case 15:
            return this.ensure_filename$15(var2, var3);
         case 16:
            return this.ensure_dirname$16(var2, var3);
         case 17:
            return this.get_command_name$17(var2, var3);
         case 18:
            return this.set_undefined_options$18(var2, var3);
         case 19:
            return this.get_finalized_command$19(var2, var3);
         case 20:
            return this.reinitialize_command$20(var2, var3);
         case 21:
            return this.run_command$21(var2, var3);
         case 22:
            return this.get_sub_commands$22(var2, var3);
         case 23:
            return this.warn$23(var2, var3);
         case 24:
            return this.execute$24(var2, var3);
         case 25:
            return this.mkpath$25(var2, var3);
         case 26:
            return this.copy_file$26(var2, var3);
         case 27:
            return this.copy_tree$27(var2, var3);
         case 28:
            return this.move_file$28(var2, var3);
         case 29:
            return this.spawn$29(var2, var3);
         case 30:
            return this.make_archive$30(var2, var3);
         case 31:
            return this.make_file$31(var2, var3);
         case 32:
            return this.install_misc$32(var2, var3);
         case 33:
            return this.initialize_options$33(var2, var3);
         case 34:
            return this._install_dir_from$34(var2, var3);
         case 35:
            return this._copy_files$35(var2, var3);
         case 36:
            return this.get_outputs$36(var2, var3);
         default:
            return null;
      }
   }
}
