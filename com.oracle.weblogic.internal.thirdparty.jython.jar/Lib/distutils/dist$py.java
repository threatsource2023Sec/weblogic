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
@Filename("distutils/dist.py")
public class dist$py extends PyFunctionTable implements PyRunnable {
   static dist$py self;
   static final PyCode f$0;
   static final PyCode Distribution$1;
   static final PyCode f$2;
   static final PyCode __init__$3;
   static final PyCode get_option_dict$4;
   static final PyCode dump_option_dicts$5;
   static final PyCode find_config_files$6;
   static final PyCode parse_config_files$7;
   static final PyCode parse_command_line$8;
   static final PyCode _get_toplevel_options$9;
   static final PyCode _parse_command_opts$10;
   static final PyCode finalize_options$11;
   static final PyCode _show_help$12;
   static final PyCode handle_display_options$13;
   static final PyCode print_command_list$14;
   static final PyCode print_commands$15;
   static final PyCode get_command_list$16;
   static final PyCode get_command_packages$17;
   static final PyCode get_command_class$18;
   static final PyCode get_command_obj$19;
   static final PyCode _set_command_options$20;
   static final PyCode reinitialize_command$21;
   static final PyCode announce$22;
   static final PyCode run_commands$23;
   static final PyCode run_command$24;
   static final PyCode has_pure_modules$25;
   static final PyCode has_ext_modules$26;
   static final PyCode has_c_libraries$27;
   static final PyCode has_modules$28;
   static final PyCode has_headers$29;
   static final PyCode has_scripts$30;
   static final PyCode has_data_files$31;
   static final PyCode is_pure$32;
   static final PyCode DistributionMetadata$33;
   static final PyCode __init__$34;
   static final PyCode read_pkg_file$35;
   static final PyCode _read_field$36;
   static final PyCode _read_list$37;
   static final PyCode write_pkg_info$38;
   static final PyCode write_pkg_file$39;
   static final PyCode _write_field$40;
   static final PyCode _write_list$41;
   static final PyCode _encode_field$42;
   static final PyCode get_name$43;
   static final PyCode get_version$44;
   static final PyCode get_fullname$45;
   static final PyCode get_author$46;
   static final PyCode get_author_email$47;
   static final PyCode get_maintainer$48;
   static final PyCode get_maintainer_email$49;
   static final PyCode get_contact$50;
   static final PyCode get_contact_email$51;
   static final PyCode get_url$52;
   static final PyCode get_license$53;
   static final PyCode get_description$54;
   static final PyCode get_long_description$55;
   static final PyCode get_keywords$56;
   static final PyCode get_platforms$57;
   static final PyCode get_classifiers$58;
   static final PyCode get_download_url$59;
   static final PyCode get_requires$60;
   static final PyCode set_requires$61;
   static final PyCode get_provides$62;
   static final PyCode set_provides$63;
   static final PyCode get_obsoletes$64;
   static final PyCode set_obsoletes$65;
   static final PyCode fix_help_options$66;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.dist\n\nProvides the Distribution class, which represents the module distribution\nbeing built/installed/distributed.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.dist\n\nProvides the Distribution class, which represents the module distribution\nbeing built/installed/distributed.\n");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(10);
      String[] var7 = new String[]{"message_from_file"};
      PyObject[] var8 = imp.importFrom("email", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("message_from_file", var4);
      var4 = null;

      try {
         var1.setline(13);
         var6 = imp.importOne("warnings", var1, -1);
         var1.setlocal("warnings", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(15);
         var4 = var1.getname("None");
         var1.setlocal("warnings", var4);
         var4 = null;
      }

      var1.setline(17);
      var7 = new String[]{"DistutilsOptionError", "DistutilsArgError", "DistutilsModuleError", "DistutilsClassError"};
      var8 = imp.importFrom("distutils.errors", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("DistutilsArgError", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("DistutilsModuleError", var4);
      var4 = null;
      var4 = var8[3];
      var1.setlocal("DistutilsClassError", var4);
      var4 = null;
      var1.setline(19);
      var7 = new String[]{"FancyGetopt", "translate_longopt"};
      var8 = imp.importFrom("distutils.fancy_getopt", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("FancyGetopt", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("translate_longopt", var4);
      var4 = null;
      var1.setline(20);
      var7 = new String[]{"check_environ", "strtobool", "rfc822_escape"};
      var8 = imp.importFrom("distutils.util", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("check_environ", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("strtobool", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("rfc822_escape", var4);
      var4 = null;
      var1.setline(21);
      var7 = new String[]{"log"};
      var8 = imp.importFrom("distutils", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(22);
      var7 = new String[]{"DEBUG"};
      var8 = imp.importFrom("distutils.debug", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var1.setline(25);
      var3 = PyString.fromInterned("utf-8");
      var1.setlocal("PKG_INFO_ENCODING", var3);
      var3 = null;
      var1.setline(31);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[a-zA-Z]([a-zA-Z0-9_]*)$"));
      var1.setlocal("command_re", var6);
      var3 = null;
      var1.setline(34);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Distribution", var8, Distribution$1);
      var1.setlocal("Distribution", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1011);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("DistributionMetadata", var8, DistributionMetadata$33);
      var1.setlocal("DistributionMetadata", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1242);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, fix_help_options$66, PyString.fromInterned("Convert a 4-tuple 'help_options' list as found in various command\n    classes to the 3-tuple form required by FancyGetopt.\n    "));
      var1.setlocal("fix_help_options", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Distribution$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The core of the Distutils.  Most of the work hiding behind 'setup'\n    is really done within a Distribution instance, which farms the work out\n    to the Distutils commands specified on the command line.\n\n    Setup scripts will almost never instantiate Distribution directly,\n    unless the 'setup()' function is totally inadequate to their needs.\n    However, it is conceivable that a setup script might wish to subclass\n    Distribution for some specialized purpose, and then pass the subclass\n    to 'setup()' as the 'distclass' keyword argument.  If so, it is\n    necessary to respect the expectations that 'setup' has of Distribution.\n    See the code for 'setup()', in core.py, for details.\n    "));
      var1.setline(46);
      PyString.fromInterned("The core of the Distutils.  Most of the work hiding behind 'setup'\n    is really done within a Distribution instance, which farms the work out\n    to the Distutils commands specified on the command line.\n\n    Setup scripts will almost never instantiate Distribution directly,\n    unless the 'setup()' function is totally inadequate to their needs.\n    However, it is conceivable that a setup script might wish to subclass\n    Distribution for some specialized purpose, and then pass the subclass\n    to 'setup()' as the 'distclass' keyword argument.  If so, it is\n    necessary to respect the expectations that 'setup' has of Distribution.\n    See the code for 'setup()', in core.py, for details.\n    ");
      var1.setline(57);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("verbose"), PyString.fromInterned("v"), PyString.fromInterned("run verbosely (default)"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("quiet"), PyString.fromInterned("q"), PyString.fromInterned("run quietly (turns verbosity off)")}), new PyTuple(new PyObject[]{PyString.fromInterned("dry-run"), PyString.fromInterned("n"), PyString.fromInterned("don't actually do anything")}), new PyTuple(new PyObject[]{PyString.fromInterned("help"), PyString.fromInterned("h"), PyString.fromInterned("show detailed help message")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-user-cfg"), var1.getname("None"), PyString.fromInterned("ignore pydistutils.cfg in your home directory")})});
      var1.setlocal("global_options", var3);
      var3 = null;
      var1.setline(67);
      PyString var4 = PyString.fromInterned("Common commands: (see '--help-commands' for more)\n\n  setup.py build      will build the package underneath 'build/'\n  setup.py install    will install the package\n");
      var1.setlocal("common_usage", var4);
      var3 = null;
      var1.setline(75);
      var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-commands"), var1.getname("None"), PyString.fromInterned("list all available commands")}), new PyTuple(new PyObject[]{PyString.fromInterned("name"), var1.getname("None"), PyString.fromInterned("print package name")}), new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("V"), PyString.fromInterned("print package version")}), new PyTuple(new PyObject[]{PyString.fromInterned("fullname"), var1.getname("None"), PyString.fromInterned("print <package name>-<version>")}), new PyTuple(new PyObject[]{PyString.fromInterned("author"), var1.getname("None"), PyString.fromInterned("print the author's name")}), new PyTuple(new PyObject[]{PyString.fromInterned("author-email"), var1.getname("None"), PyString.fromInterned("print the author's email address")}), new PyTuple(new PyObject[]{PyString.fromInterned("maintainer"), var1.getname("None"), PyString.fromInterned("print the maintainer's name")}), new PyTuple(new PyObject[]{PyString.fromInterned("maintainer-email"), var1.getname("None"), PyString.fromInterned("print the maintainer's email address")}), new PyTuple(new PyObject[]{PyString.fromInterned("contact"), var1.getname("None"), PyString.fromInterned("print the maintainer's name if known, else the author's")}), new PyTuple(new PyObject[]{PyString.fromInterned("contact-email"), var1.getname("None"), PyString.fromInterned("print the maintainer's email address if known, else the author's")}), new PyTuple(new PyObject[]{PyString.fromInterned("url"), var1.getname("None"), PyString.fromInterned("print the URL for this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("license"), var1.getname("None"), PyString.fromInterned("print the license of the package")}), new PyTuple(new PyObject[]{PyString.fromInterned("licence"), var1.getname("None"), PyString.fromInterned("alias for --license")}), new PyTuple(new PyObject[]{PyString.fromInterned("description"), var1.getname("None"), PyString.fromInterned("print the package description")}), new PyTuple(new PyObject[]{PyString.fromInterned("long-description"), var1.getname("None"), PyString.fromInterned("print the long package description")}), new PyTuple(new PyObject[]{PyString.fromInterned("platforms"), var1.getname("None"), PyString.fromInterned("print the list of platforms")}), new PyTuple(new PyObject[]{PyString.fromInterned("classifiers"), var1.getname("None"), PyString.fromInterned("print the list of classifiers")}), new PyTuple(new PyObject[]{PyString.fromInterned("keywords"), var1.getname("None"), PyString.fromInterned("print the list of keywords")}), new PyTuple(new PyObject[]{PyString.fromInterned("provides"), var1.getname("None"), PyString.fromInterned("print the list of packages/modules provided")}), new PyTuple(new PyObject[]{PyString.fromInterned("requires"), var1.getname("None"), PyString.fromInterned("print the list of packages/modules required")}), new PyTuple(new PyObject[]{PyString.fromInterned("obsoletes"), var1.getname("None"), PyString.fromInterned("print the list of packages/modules made obsolete")})});
      var1.setlocal("display_options", var3);
      var3 = null;
      var1.setline(119);
      PyObject var10000 = var1.getname("map");
      var1.setline(119);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$2)), (PyObject)var1.getname("display_options"));
      var1.setlocal("display_option_names", var6);
      var3 = null;
      var1.setline(123);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("quiet"), PyString.fromInterned("verbose")});
      var1.setlocal("negative_opt", var7);
      var3 = null;
      var1.setline(128);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var5, __init__$3, PyString.fromInterned("Construct a new Distribution instance: initialize all the\n        attributes of a Distribution, and then use 'attrs' (a dictionary\n        mapping attribute names to values) to assign some of those\n        attributes their \"real\" values.  (Any attributes not mentioned in\n        'attrs' will be assigned to some null value: 0, None, an empty list\n        or dictionary, etc.)  Most importantly, initialize the\n        'command_obj' attribute to the empty dictionary; this will be\n        filled in with real command objects by 'parse_command_line()'.\n        "));
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(289);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_option_dict$4, PyString.fromInterned("Get the option dictionary for a given command.  If that\n        command's option dictionary hasn't been created yet, then create it\n        and return the new dictionary; otherwise, return the existing\n        option dictionary.\n        "));
      var1.setlocal("get_option_dict", var8);
      var3 = null;
      var1.setline(300);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), PyString.fromInterned("")};
      var8 = new PyFunction(var1.f_globals, var5, dump_option_dicts$5, (PyObject)null);
      var1.setlocal("dump_option_dicts", var8);
      var3 = null;
      var1.setline(329);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, find_config_files$6, PyString.fromInterned("Find as many configuration files as should be processed for this\n        platform, and return a list of filenames in the order in which they\n        should be parsed.  The filenames returned are guaranteed to exist\n        (modulo nasty race conditions).\n\n        There are three possible config files: distutils.cfg in the\n        Distutils installation directory (ie. where the top-level\n        Distutils __inst__.py file lives), a file in the user's home\n        directory named .pydistutils.cfg on Unix and pydistutils.cfg\n        on Windows/Mac; and setup.cfg in the current directory.\n\n        The file in the user's home directory can be disabled with the\n        --no-user-cfg option.\n        "));
      var1.setlocal("find_config_files", var8);
      var3 = null;
      var1.setline(377);
      var5 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var5, parse_config_files$7, (PyObject)null);
      var1.setlocal("parse_config_files", var8);
      var3 = null;
      var1.setline(423);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, parse_command_line$8, PyString.fromInterned("Parse the setup script's command line, taken from the\n        'script_args' instance attribute (which defaults to 'sys.argv[1:]'\n        -- see 'setup()' in core.py).  This list is first processed for\n        \"global options\" -- options that set attributes of the Distribution\n        instance.  Then, it is alternately scanned for Distutils commands\n        and options for that command.  Each new command terminates the\n        options for the previous command.  The allowed options for a\n        command are determined by the 'user_options' attribute of the\n        command class -- thus, we have to be able to load command classes\n        in order to parse the command line.  Any error in that 'options'\n        attribute raises DistutilsGetoptError; any error on the\n        command-line raises DistutilsArgError.  If no Distutils commands\n        were found on the command line, raises DistutilsArgError.  Return\n        true if command-line was successfully parsed and we should carry\n        on with executing commands; false if no errors but we shouldn't\n        execute commands (currently, this only happens if user asks for\n        help).\n        "));
      var1.setlocal("parse_command_line", var8);
      var3 = null;
      var1.setline(490);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _get_toplevel_options$9, PyString.fromInterned("Return the non-display options recognized at the top level.\n\n        This includes options that are recognized *only* at the top\n        level as well as options recognized for commands.\n        "));
      var1.setlocal("_get_toplevel_options", var8);
      var3 = null;
      var1.setline(501);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, _parse_command_opts$10, PyString.fromInterned("Parse the command-line options for a single command.\n        'parser' must be a FancyGetopt instance; 'args' must be the list\n        of arguments, starting with the current command (whose options\n        we are about to parse).  Returns a new version of 'args' with\n        the next command at the front of the list; will be the empty\n        list if there are no more commands on the command line.  Returns\n        None if the user asked for help on this command.\n        "));
      var1.setlocal("_parse_command_opts", var8);
      var3 = null;
      var1.setline(594);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, finalize_options$11, PyString.fromInterned("Set final values for all the options on the Distribution\n        instance, analogous to the .finalize_options() method of Command\n        objects.\n        "));
      var1.setlocal("finalize_options", var8);
      var3 = null;
      var1.setline(607);
      var5 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), new PyList(Py.EmptyObjects)};
      var8 = new PyFunction(var1.f_globals, var5, _show_help$12, PyString.fromInterned("Show help for the setup script command-line in the form of\n        several lists of command-line options.  'parser' should be a\n        FancyGetopt instance; do not expect it to be returned in the\n        same state, as its option table will be reset to make it\n        generate the correct help text.\n\n        If 'global_options' is true, lists the global options:\n        --verbose, --dry-run, etc.  If 'display_options' is true, lists\n        the \"display-only\" options: --name, --version, etc.  Finally,\n        lists per-command help for every command name or command class\n        in 'commands'.\n        "));
      var1.setlocal("_show_help", var8);
      var3 = null;
      var1.setline(657);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, handle_display_options$13, PyString.fromInterned("If there were any non-global \"display-only\" options\n        (--help-commands or the metadata display options) on the command\n        line, display the requested info and return true; else return\n        false.\n        "));
      var1.setlocal("handle_display_options", var8);
      var3 = null;
      var1.setline(697);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, print_command_list$14, PyString.fromInterned("Print a subset of the list of all commands -- used by\n        'print_commands()'.\n        "));
      var1.setlocal("print_command_list", var8);
      var3 = null;
      var1.setline(714);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, print_commands$15, PyString.fromInterned("Print out a help message listing all available commands with a\n        description of each.  The list is divided into \"standard commands\"\n        (listed in distutils.command.__all__) and \"extra commands\"\n        (mentioned in self.cmdclass, but not a standard command).  The\n        descriptions come from the command class attribute\n        'description'.\n        "));
      var1.setlocal("print_commands", var8);
      var3 = null;
      var1.setline(747);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_command_list$16, PyString.fromInterned("Get a list of (command, description) tuples.\n        The list is divided into \"standard commands\" (listed in\n        distutils.command.__all__) and \"extra commands\" (mentioned in\n        self.cmdclass, but not a standard command).  The descriptions come\n        from the command class attribute 'description'.\n        "));
      var1.setlocal("get_command_list", var8);
      var3 = null;
      var1.setline(782);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_command_packages$17, PyString.fromInterned("Return a list of packages from which commands are loaded."));
      var1.setlocal("get_command_packages", var8);
      var3 = null;
      var1.setline(794);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, get_command_class$18, PyString.fromInterned("Return the class that implements the Distutils command named by\n        'command'.  First we check the 'cmdclass' dictionary; if the\n        command is mentioned there, we fetch the class object from the\n        dictionary and return it.  Otherwise we load the command module\n        (\"distutils.command.\" + command) and fetch the command class from\n        the module.  The loaded class is also stored in 'cmdclass'\n        to speed future calls to 'get_command_class()'.\n\n        Raises DistutilsModuleError if the expected module could not be\n        found, or if that module does not define the expected class.\n        "));
      var1.setlocal("get_command_class", var8);
      var3 = null;
      var1.setline(833);
      var5 = new PyObject[]{Py.newInteger(1)};
      var8 = new PyFunction(var1.f_globals, var5, get_command_obj$19, PyString.fromInterned("Return the command object for 'command'.  Normally this object\n        is cached on a previous call to 'get_command_obj()'; if no command\n        object for 'command' is in the cache, then we either create and\n        return it (if 'create' is true) or return None.\n        "));
      var1.setlocal("get_command_obj", var8);
      var3 = null;
      var1.setline(860);
      var5 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var5, _set_command_options$20, PyString.fromInterned("Set the options for 'command_obj' from 'option_dict'.  Basically\n        this means copying elements of a dictionary ('option_dict') to\n        attributes of an instance ('command').\n\n        'command_obj' must be a Command instance.  If 'option_dict' is not\n        supplied, uses the standard option dictionary for this command\n        (from 'self.command_options').\n        "));
      var1.setlocal("_set_command_options", var8);
      var3 = null;
      var1.setline(903);
      var5 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var5, reinitialize_command$21, PyString.fromInterned("Reinitializes a command to the state it was in when first\n        returned by 'get_command_obj()': ie., initialized but not yet\n        finalized.  This provides the opportunity to sneak option\n        values in programmatically, overriding or supplementing\n        user-supplied values from the config files and command line.\n        You'll have to re-finalize the command object (by calling\n        'finalize_options()' or 'ensure_finalized()') before using it for\n        real.\n\n        'command' should be a command name (string) or command object.  If\n        'reinit_subcommands' is true, also reinitializes the command's\n        sub-commands, as declared by the 'sub_commands' class attribute (if\n        it has one).  See the \"install\" command for an example.  Only\n        reinitializes the sub-commands that actually matter, ie. those\n        whose test predicates return true.\n\n        Returns the reinitialized command object.\n        "));
      var1.setlocal("reinitialize_command", var8);
      var3 = null;
      var1.setline(944);
      var5 = new PyObject[]{var1.getname("log").__getattr__("INFO")};
      var8 = new PyFunction(var1.f_globals, var5, announce$22, (PyObject)null);
      var1.setlocal("announce", var8);
      var3 = null;
      var1.setline(947);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, run_commands$23, PyString.fromInterned("Run each command that was seen on the setup script command line.\n        Uses the list of commands found and cache of command objects\n        created by 'get_command_obj()'.\n        "));
      var1.setlocal("run_commands", var8);
      var3 = null;
      var1.setline(957);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, run_command$24, PyString.fromInterned("Do whatever it takes to run a command (including nothing at all,\n        if the command has already been run).  Specifically: if we have\n        already created and run the command named by 'command', return\n        silently without doing anything.  If the command named by 'command'\n        doesn't even have a command object yet, create one.  Then invoke\n        'run()' on that command object (or an existing one).\n        "));
      var1.setlocal("run_command", var8);
      var3 = null;
      var1.setline(978);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_pure_modules$25, (PyObject)null);
      var1.setlocal("has_pure_modules", var8);
      var3 = null;
      var1.setline(981);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_ext_modules$26, (PyObject)null);
      var1.setlocal("has_ext_modules", var8);
      var3 = null;
      var1.setline(984);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_c_libraries$27, (PyObject)null);
      var1.setlocal("has_c_libraries", var8);
      var3 = null;
      var1.setline(987);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_modules$28, (PyObject)null);
      var1.setlocal("has_modules", var8);
      var3 = null;
      var1.setline(990);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_headers$29, (PyObject)null);
      var1.setlocal("has_headers", var8);
      var3 = null;
      var1.setline(993);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_scripts$30, (PyObject)null);
      var1.setlocal("has_scripts", var8);
      var3 = null;
      var1.setline(996);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, has_data_files$31, (PyObject)null);
      var1.setlocal("has_data_files", var8);
      var3 = null;
      var1.setline(999);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, is_pure$32, (PyObject)null);
      var1.setlocal("is_pure", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getglobal("translate_longopt").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Construct a new Distribution instance: initialize all the\n        attributes of a Distribution, and then use 'attrs' (a dictionary\n        mapping attribute names to values) to assign some of those\n        attributes their \"real\" values.  (Any attributes not mentioned in\n        'attrs' will be assigned to some null value: 0, None, an empty list\n        or dictionary, etc.)  Most importantly, initialize the\n        'command_obj' attribute to the empty dictionary; this will be\n        filled in with real command objects by 'parse_command_line()'.\n        ");
      var1.setline(140);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"verbose", var3);
      var3 = null;
      var1.setline(141);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"dry_run", var3);
      var3 = null;
      var1.setline(142);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"help", var3);
      var3 = null;
      var1.setline(143);
      PyObject var9 = var1.getlocal(0).__getattr__("display_option_names").__iter__();

      while(true) {
         var1.setline(143);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(151);
            var9 = var1.getglobal("DistributionMetadata").__call__(var2);
            var1.getlocal(0).__setattr__("metadata", var9);
            var3 = null;
            var1.setline(152);
            var9 = var1.getlocal(0).__getattr__("metadata").__getattr__("_METHOD_BASENAMES").__iter__();

            while(true) {
               var1.setline(152);
               var4 = var9.__iternext__();
               PyObject var5;
               if (var4 == null) {
                  var1.setline(160);
                  PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"cmdclass", var11);
                  var3 = null;
                  var1.setline(168);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("command_packages", var9);
                  var3 = null;
                  var1.setline(173);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("script_name", var9);
                  var3 = null;
                  var1.setline(174);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("script_args", var9);
                  var3 = null;
                  var1.setline(181);
                  var11 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"command_options", var11);
                  var3 = null;
                  var1.setline(192);
                  PyList var13 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"dist_files", var13);
                  var3 = null;
                  var1.setline(197);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("packages", var9);
                  var3 = null;
                  var1.setline(198);
                  var11 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"package_data", var11);
                  var3 = null;
                  var1.setline(199);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("package_dir", var9);
                  var3 = null;
                  var1.setline(200);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("py_modules", var9);
                  var3 = null;
                  var1.setline(201);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("libraries", var9);
                  var3 = null;
                  var1.setline(202);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("headers", var9);
                  var3 = null;
                  var1.setline(203);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("ext_modules", var9);
                  var3 = null;
                  var1.setline(204);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("ext_package", var9);
                  var3 = null;
                  var1.setline(205);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("include_dirs", var9);
                  var3 = null;
                  var1.setline(206);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("extra_path", var9);
                  var3 = null;
                  var1.setline(207);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("scripts", var9);
                  var3 = null;
                  var1.setline(208);
                  var9 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("data_files", var9);
                  var3 = null;
                  var1.setline(209);
                  PyString var14 = PyString.fromInterned("");
                  var1.getlocal(0).__setattr__((String)"password", var14);
                  var3 = null;
                  var1.setline(215);
                  var11 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"command_obj", var11);
                  var3 = null;
                  var1.setline(227);
                  var11 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"have_run", var11);
                  var3 = null;
                  var1.setline(233);
                  PyObject var10000;
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(238);
                     var9 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("options"));
                     var1.setlocal(5, var9);
                     var3 = null;
                     var1.setline(239);
                     var9 = var1.getlocal(5);
                     var10000 = var9._isnot(var1.getglobal("None"));
                     var3 = null;
                     PyObject var6;
                     PyObject[] var10;
                     if (var10000.__nonzero__()) {
                        var1.setline(240);
                        var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("options"));
                        var1.setline(241);
                        var9 = var1.getlocal(5).__getattr__("items").__call__(var2).__iter__();

                        while(true) {
                           var1.setline(241);
                           var4 = var9.__iternext__();
                           if (var4 == null) {
                              break;
                           }

                           var10 = Py.unpackSequence(var4, 2);
                           var6 = var10[0];
                           var1.setlocal(6, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(7, var6);
                           var6 = null;
                           var1.setline(242);
                           var5 = var1.getlocal(0).__getattr__("get_option_dict").__call__(var2, var1.getlocal(6));
                           var1.setlocal(8, var5);
                           var5 = null;
                           var1.setline(243);
                           var5 = var1.getlocal(7).__getattr__("items").__call__(var2).__iter__();

                           while(true) {
                              var1.setline(243);
                              var6 = var5.__iternext__();
                              if (var6 == null) {
                                 break;
                              }

                              PyObject[] var7 = Py.unpackSequence(var6, 2);
                              PyObject var8 = var7[0];
                              var1.setlocal(9, var8);
                              var8 = null;
                              var8 = var7[1];
                              var1.setlocal(10, var8);
                              var8 = null;
                              var1.setline(244);
                              PyTuple var12 = new PyTuple(new PyObject[]{PyString.fromInterned("setup script"), var1.getlocal(10)});
                              var1.getlocal(8).__setitem__((PyObject)var1.getlocal(9), var12);
                              var7 = null;
                           }
                        }
                     }

                     var1.setline(246);
                     var14 = PyString.fromInterned("licence");
                     var10000 = var14._in(var1.getlocal(1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(247);
                        var9 = var1.getlocal(1).__getitem__(PyString.fromInterned("licence"));
                        var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("license"), var9);
                        var3 = null;
                        var1.setline(248);
                        var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("licence"));
                        var1.setline(249);
                        var14 = PyString.fromInterned("'licence' distribution option is deprecated; use 'license'");
                        var1.setlocal(11, var14);
                        var3 = null;
                        var1.setline(250);
                        var9 = var1.getglobal("warnings");
                        var10000 = var9._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(251);
                           var1.getglobal("warnings").__getattr__("warn").__call__(var2, var1.getlocal(11));
                        } else {
                           var1.setline(253);
                           var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(11)._add(PyString.fromInterned("\n")));
                        }
                     }

                     var1.setline(257);
                     var9 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(257);
                        var4 = var9.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var10 = Py.unpackSequence(var4, 2);
                        var6 = var10[0];
                        var1.setlocal(12, var6);
                        var6 = null;
                        var6 = var10[1];
                        var1.setlocal(10, var6);
                        var6 = null;
                        var1.setline(258);
                        if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), PyString.fromInterned("set_")._add(var1.getlocal(12))).__nonzero__()) {
                           var1.setline(259);
                           var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), PyString.fromInterned("set_")._add(var1.getlocal(12))).__call__(var2, var1.getlocal(10));
                        } else {
                           var1.setline(260);
                           if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), var1.getlocal(12)).__nonzero__()) {
                              var1.setline(261);
                              var1.getglobal("setattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), var1.getlocal(12), var1.getlocal(10));
                           } else {
                              var1.setline(262);
                              if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(12)).__nonzero__()) {
                                 var1.setline(263);
                                 var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(12), var1.getlocal(10));
                              } else {
                                 var1.setline(265);
                                 var5 = PyString.fromInterned("Unknown distribution option: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(12)));
                                 var1.setlocal(11, var5);
                                 var5 = null;
                                 var1.setline(266);
                                 var5 = var1.getglobal("warnings");
                                 var10000 = var5._isnot(var1.getglobal("None"));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(267);
                                    var1.getglobal("warnings").__getattr__("warn").__call__(var2, var1.getlocal(11));
                                 } else {
                                    var1.setline(269);
                                    var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(11)._add(PyString.fromInterned("\n")));
                                 }
                              }
                           }
                        }
                     }
                  }

                  var1.setline(277);
                  var9 = var1.getglobal("True");
                  var1.getlocal(0).__setattr__("want_user_cfg", var9);
                  var3 = null;
                  var1.setline(279);
                  var9 = var1.getlocal(0).__getattr__("script_args");
                  var10000 = var9._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(280);
                     var9 = var1.getlocal(0).__getattr__("script_args").__iter__();

                     while(true) {
                        var1.setline(280);
                        var4 = var9.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(13, var4);
                        var1.setline(281);
                        if (var1.getlocal(13).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-")).__not__().__nonzero__()) {
                           break;
                        }

                        var1.setline(283);
                        var5 = var1.getlocal(13);
                        var10000 = var5._eq(PyString.fromInterned("--no-user-cfg"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(284);
                           var5 = var1.getglobal("False");
                           var1.getlocal(0).__setattr__("want_user_cfg", var5);
                           var5 = null;
                           break;
                        }
                     }
                  }

                  var1.setline(287);
                  var1.getlocal(0).__getattr__("finalize_options").__call__(var2);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(153);
               var5 = PyString.fromInterned("get_")._add(var1.getlocal(3));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(154);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), var1.getlocal(4)));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(144);
         var1.getglobal("setattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
      }
   }

   public PyObject get_option_dict$4(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyString.fromInterned("Get the option dictionary for a given command.  If that\n        command's option dictionary hasn't been created yet, then create it\n        and return the new dictionary; otherwise, return the existing\n        option dictionary.\n        ");
      var1.setline(295);
      PyObject var3 = var1.getlocal(0).__getattr__("command_options").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(296);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(297);
         PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var1.getlocal(0).__getattr__("command_options").__setitem__((PyObject)var1.getlocal(1), var4);
      }

      var1.setline(298);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dump_option_dicts$5(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      String[] var3 = new String[]{"pformat"};
      PyObject[] var7 = imp.importFrom("pprint", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(303);
      PyObject var8 = var1.getlocal(2);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(304);
         var8 = var1.getlocal(0).__getattr__("command_options").__getattr__("keys").__call__(var2);
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(305);
         var1.getlocal(2).__getattr__("sort").__call__(var2);
      }

      var1.setline(307);
      var8 = var1.getlocal(1);
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(308);
         var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(3)._add(var1.getlocal(1)));
         var1.setline(309);
         var8 = var1.getlocal(3)._add(PyString.fromInterned("  "));
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(311);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(312);
         var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("no commands known yet")));
         var1.setline(313);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(315);
         var8 = var1.getlocal(2).__iter__();

         while(true) {
            while(true) {
               var1.setline(315);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(5, var4);
               var1.setline(316);
               PyObject var5 = var1.getlocal(0).__getattr__("command_options").__getattr__("get").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(317);
               var5 = var1.getlocal(6);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(318);
                  var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("no option dict for '%s' command")._mod(var1.getlocal(5))));
               } else {
                  var1.setline(321);
                  var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("option dict for '%s' command:")._mod(var1.getlocal(5))));
                  var1.setline(323);
                  var5 = var1.getlocal(4).__call__(var2, var1.getlocal(6));
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(324);
                  var5 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

                  while(true) {
                     var1.setline(324);
                     PyObject var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(8, var6);
                     var1.setline(325);
                     var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("  "))._add(var1.getlocal(8)));
                  }
               }
            }
         }
      }
   }

   public PyObject find_config_files$6(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyString.fromInterned("Find as many configuration files as should be processed for this\n        platform, and return a list of filenames in the order in which they\n        should be parsed.  The filenames returned are guaranteed to exist\n        (modulo nasty race conditions).\n\n        There are three possible config files: distutils.cfg in the\n        Distutils installation directory (ie. where the top-level\n        Distutils __inst__.py file lives), a file in the user's home\n        directory named .pydistutils.cfg on Unix and pydistutils.cfg\n        on Windows/Mac; and setup.cfg in the current directory.\n\n        The file in the user's home directory can be disabled with the\n        --no-user-cfg option.\n        ");
      var1.setline(344);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(345);
      var1.getglobal("check_environ").__call__(var2);
      var1.setline(348);
      PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("distutils")).__getattr__("__file__"));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(351);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("distutils.cfg"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(352);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__()) {
         var1.setline(353);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.setline(356);
      var4 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var4._eq(PyString.fromInterned("posix"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(357);
         var5 = PyString.fromInterned(".pydistutils.cfg");
         var1.setlocal(4, var5);
         var3 = null;
      } else {
         var1.setline(359);
         var5 = PyString.fromInterned("pydistutils.cfg");
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(362);
      if (var1.getlocal(0).__getattr__("want_user_cfg").__nonzero__()) {
         var1.setline(363);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~")), var1.getlocal(4));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(364);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__nonzero__()) {
            var1.setline(365);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }

      var1.setline(368);
      var5 = PyString.fromInterned("setup.cfg");
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(369);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(6)).__nonzero__()) {
         var1.setline(370);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(6));
      }

      var1.setline(372);
      if (var1.getglobal("DEBUG").__nonzero__()) {
         var1.setline(373);
         var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("using config files: %s")._mod(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))));
      }

      var1.setline(375);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject parse_config_files$7(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      String[] var3 = new String[]{"ConfigParser"};
      PyObject[] var11 = imp.importFrom("ConfigParser", var3, var1, -1);
      PyObject var4 = var11[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(380);
      PyObject var12 = var1.getlocal(1);
      PyObject var10000 = var12._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(381);
         var12 = var1.getlocal(0).__getattr__("find_config_files").__call__(var2);
         var1.setlocal(1, var12);
         var3 = null;
      }

      var1.setline(383);
      if (var1.getglobal("DEBUG").__nonzero__()) {
         var1.setline(384);
         var1.getlocal(0).__getattr__("announce").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Distribution.parse_config_files():"));
      }

      var1.setline(386);
      var12 = var1.getlocal(2).__call__(var2);
      var1.setlocal(3, var12);
      var3 = null;
      var1.setline(387);
      var12 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(387);
         var4 = var12.__iternext__();
         PyObject var5;
         PyObject var6;
         PyObject var8;
         if (var4 == null) {
            var1.setline(408);
            PyString var14 = PyString.fromInterned("global");
            var10000 = var14._in(var1.getlocal(0).__getattr__("command_options"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(409);
               var12 = var1.getlocal(0).__getattr__("command_options").__getitem__(PyString.fromInterned("global")).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(409);
                  var4 = var12.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  PyObject[] var13 = Py.unpackSequence(var4, 2);
                  var6 = var13[0];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var13[1];
                  PyObject[] var16 = Py.unpackSequence(var6, 2);
                  var8 = var16[0];
                  var1.setlocal(10, var8);
                  var8 = null;
                  var8 = var16[1];
                  var1.setlocal(9, var8);
                  var8 = null;
                  var6 = null;
                  var1.setline(410);
                  var5 = var1.getlocal(0).__getattr__("negative_opt").__getattr__("get").__call__(var2, var1.getlocal(8));
                  var1.setlocal(11, var5);
                  var5 = null;

                  try {
                     var1.setline(412);
                     if (var1.getlocal(11).__nonzero__()) {
                        var1.setline(413);
                        var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(11), var1.getglobal("strtobool").__call__(var2, var1.getlocal(9)).__not__());
                     } else {
                        var1.setline(414);
                        var5 = var1.getlocal(8);
                        var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("verbose"), PyString.fromInterned("dry_run")}));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(415);
                           var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(8), var1.getglobal("strtobool").__call__(var2, var1.getlocal(9)));
                        } else {
                           var1.setline(417);
                           var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(8), var1.getlocal(9));
                        }
                     }
                  } catch (Throwable var10) {
                     PyException var15 = Py.setException(var10, var1);
                     if (var15.match(var1.getglobal("ValueError"))) {
                        var6 = var15.value;
                        var1.setlocal(12, var6);
                        var6 = null;
                        var1.setline(419);
                        throw Py.makeException(var1.getglobal("DistutilsOptionError"), var1.getlocal(12));
                     }

                     throw var15;
                  }
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(388);
         if (var1.getglobal("DEBUG").__nonzero__()) {
            var1.setline(389);
            var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("  reading %s")._mod(var1.getlocal(4)));
         }

         var1.setline(390);
         var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(4));
         var1.setline(391);
         var5 = var1.getlocal(3).__getattr__("sections").__call__(var2).__iter__();

         while(true) {
            var1.setline(391);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(403);
               var1.getlocal(3).__getattr__("__init__").__call__(var2);
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(392);
            PyObject var7 = var1.getlocal(3).__getattr__("options").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(393);
            var7 = var1.getlocal(0).__getattr__("get_option_dict").__call__(var2, var1.getlocal(5));
            var1.setlocal(7, var7);
            var7 = null;
            var1.setline(395);
            var7 = var1.getlocal(6).__iter__();

            while(true) {
               var1.setline(395);
               var8 = var7.__iternext__();
               if (var8 == null) {
                  break;
               }

               var1.setlocal(8, var8);
               var1.setline(396);
               PyObject var9 = var1.getlocal(8);
               var10000 = var9._ne(PyString.fromInterned("__name__"));
               var9 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(397);
                  var9 = var1.getlocal(3).__getattr__("get").__call__(var2, var1.getlocal(5), var1.getlocal(8));
                  var1.setlocal(9, var9);
                  var9 = null;
                  var1.setline(398);
                  var9 = var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
                  var1.setlocal(8, var9);
                  var9 = null;
                  var1.setline(399);
                  PyTuple var17 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(9)});
                  var1.getlocal(7).__setitem__((PyObject)var1.getlocal(8), var17);
                  var9 = null;
               }
            }
         }
      }
   }

   public PyObject parse_command_line$8(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyString.fromInterned("Parse the setup script's command line, taken from the\n        'script_args' instance attribute (which defaults to 'sys.argv[1:]'\n        -- see 'setup()' in core.py).  This list is first processed for\n        \"global options\" -- options that set attributes of the Distribution\n        instance.  Then, it is alternately scanned for Distutils commands\n        and options for that command.  Each new command terminates the\n        options for the previous command.  The allowed options for a\n        command are determined by the 'user_options' attribute of the\n        command class -- thus, we have to be able to load command classes\n        in order to parse the command line.  Any error in that 'options'\n        attribute raises DistutilsGetoptError; any error on the\n        command-line raises DistutilsArgError.  If no Distutils commands\n        were found on the command line, raises DistutilsArgError.  Return\n        true if command-line was successfully parsed and we should carry\n        on with executing commands; false if no errors but we shouldn't\n        execute commands (currently, this only happens if user asks for\n        help).\n        ");
      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_toplevel_options").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(455);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commands", var5);
      var3 = null;
      var1.setline(456);
      var3 = var1.getglobal("FancyGetopt").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getattr__("display_options")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(457);
      var1.getlocal(2).__getattr__("set_negative_aliases").__call__(var2, var1.getlocal(0).__getattr__("negative_opt"));
      var1.setline(458);
      var1.getlocal(2).__getattr__("set_aliases").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("licence"), PyString.fromInterned("license")})));
      var1.setline(459);
      PyObject var10000 = var1.getlocal(2).__getattr__("getopt");
      PyObject[] var7 = new PyObject[]{var1.getlocal(0).__getattr__("script_args"), var1.getlocal(0)};
      String[] var4 = new String[]{"args", "object"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(460);
      var3 = var1.getlocal(2).__getattr__("get_option_order").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(461);
      var1.getglobal("log").__getattr__("set_verbosity").__call__(var2, var1.getlocal(0).__getattr__("verbose"));
      var1.setline(464);
      if (var1.getlocal(0).__getattr__("handle_display_options").__call__(var2, var1.getlocal(4)).__nonzero__()) {
         var1.setline(465);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         do {
            var1.setline(466);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(477);
               if (var1.getlocal(0).__getattr__("help").__nonzero__()) {
                  var1.setline(478);
                  var10000 = var1.getlocal(0).__getattr__("_show_help");
                  var7 = new PyObject[]{var1.getlocal(2), null, null};
                  PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("commands"));
                  PyObject var10002 = var6._eq(Py.newInteger(0));
                  var4 = null;
                  var7[1] = var10002;
                  var7[2] = var1.getlocal(0).__getattr__("commands");
                  var4 = new String[]{"display_options", "commands"};
                  var10000.__call__(var2, var7, var4);
                  var3 = null;
                  var1.setline(481);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(484);
                  if (var1.getlocal(0).__getattr__("commands").__not__().__nonzero__()) {
                     var1.setline(485);
                     throw Py.makeException(var1.getglobal("DistutilsArgError"), PyString.fromInterned("no commands supplied"));
                  } else {
                     var1.setline(488);
                     PyInteger var8 = Py.newInteger(1);
                     var1.f_lasti = -1;
                     return var8;
                  }
               }
            }

            var1.setline(467);
            var3 = var1.getlocal(0).__getattr__("_parse_command_opts").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(468);
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         } while(!var10000.__nonzero__());

         var1.setline(469);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_toplevel_options$9(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("Return the non-display options recognized at the top level.\n\n        This includes options that are recognized *only* at the top\n        level as well as options recognized for commands.\n        ");
      var1.setline(496);
      PyObject var3 = var1.getlocal(0).__getattr__("global_options")._add(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("command-packages="), var1.getglobal("None"), PyString.fromInterned("list of packages that provide distutils commands")})}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parse_command_opts$10(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyString.fromInterned("Parse the command-line options for a single command.\n        'parser' must be a FancyGetopt instance; 'args' must be the list\n        of arguments, starting with the current command (whose options\n        we are about to parse).  Returns a new version of 'args' with\n        the next command at the front of the list; will be the empty\n        list if there are no more commands on the command line.  Returns\n        None if the user asked for help on this command.\n        ");
      var1.setline(511);
      String[] var3 = new String[]{"Command"};
      PyObject[] var8 = imp.importFrom("distutils.cmd", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(514);
      PyObject var9 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(515);
      if (var1.getglobal("command_re").__getattr__("match").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
         var1.setline(516);
         throw Py.makeException(var1.getglobal("SystemExit"), PyString.fromInterned("invalid command name '%s'")._mod(var1.getlocal(4)));
      } else {
         var1.setline(517);
         var1.getlocal(0).__getattr__("commands").__getattr__("append").__call__(var2, var1.getlocal(4));

         try {
            var1.setline(523);
            var9 = var1.getlocal(0).__getattr__("get_command_class").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var9);
            var3 = null;
         } catch (Throwable var7) {
            PyException var10 = Py.setException(var7, var1);
            if (var10.match(var1.getglobal("DistutilsModuleError"))) {
               var4 = var10.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(525);
               throw Py.makeException(var1.getglobal("DistutilsArgError"), var1.getlocal(6));
            }

            throw var10;
         }

         var1.setline(529);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(5), var1.getlocal(3)).__not__().__nonzero__()) {
            var1.setline(530);
            throw Py.makeException(var1.getglobal("DistutilsClassError"), PyString.fromInterned("command class %s must subclass Command")._mod(var1.getlocal(5)));
         } else {
            var1.setline(535);
            PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("user_options"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5).__getattr__("user_options"), var1.getglobal("list"));
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(537);
               throw Py.makeException(var1.getglobal("DistutilsClassError"), PyString.fromInterned("command class %s must provide ")._add(PyString.fromInterned("'user_options' attribute (a list of tuples)"))._mod(var1.getlocal(5)));
            } else {
               var1.setline(544);
               var9 = var1.getlocal(0).__getattr__("negative_opt");
               var1.setlocal(7, var9);
               var3 = null;
               var1.setline(545);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("negative_opt")).__nonzero__()) {
                  var1.setline(546);
                  var9 = var1.getlocal(7).__getattr__("copy").__call__(var2);
                  var1.setlocal(7, var9);
                  var3 = null;
                  var1.setline(547);
                  var1.getlocal(7).__getattr__("update").__call__(var2, var1.getlocal(5).__getattr__("negative_opt"));
               }

               var1.setline(551);
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("help_options"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5).__getattr__("help_options"), var1.getglobal("list"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(553);
                  var9 = var1.getglobal("fix_help_options").__call__(var2, var1.getlocal(5).__getattr__("help_options"));
                  var1.setlocal(8, var9);
                  var3 = null;
               } else {
                  var1.setline(555);
                  PyList var15 = new PyList(Py.EmptyObjects);
                  var1.setlocal(8, var15);
                  var3 = null;
               }

               var1.setline(560);
               var1.getlocal(1).__getattr__("set_option_table").__call__(var2, var1.getlocal(0).__getattr__("global_options")._add(var1.getlocal(5).__getattr__("user_options"))._add(var1.getlocal(8)));
               var1.setline(563);
               var1.getlocal(1).__getattr__("set_negative_aliases").__call__(var2, var1.getlocal(7));
               var1.setline(564);
               var9 = var1.getlocal(1).__getattr__("getopt").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               PyObject[] var11 = Py.unpackSequence(var9, 2);
               PyObject var5 = var11[0];
               var1.setlocal(2, var5);
               var5 = null;
               var5 = var11[1];
               var1.setlocal(9, var5);
               var5 = null;
               var3 = null;
               var1.setline(565);
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("help"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(9).__getattr__("help");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(566);
                  var10000 = var1.getlocal(0).__getattr__("_show_help");
                  var8 = new PyObject[]{var1.getlocal(1), Py.newInteger(0), new PyList(new PyObject[]{var1.getlocal(5)})};
                  String[] var12 = new String[]{"display_options", "commands"};
                  var10000.__call__(var2, var8, var12);
                  var3 = null;
                  var1.setline(567);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(569);
                  var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("help_options"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5).__getattr__("help_options"), var1.getglobal("list"));
                  }

                  PyObject var6;
                  PyObject[] var13;
                  if (var10000.__nonzero__()) {
                     var1.setline(571);
                     PyInteger var17 = Py.newInteger(0);
                     var1.setlocal(10, var17);
                     var3 = null;
                     var1.setline(572);
                     var9 = var1.getlocal(5).__getattr__("help_options").__iter__();

                     while(true) {
                        var1.setline(572);
                        var4 = var9.__iternext__();
                        if (var4 == null) {
                           var1.setline(583);
                           if (var1.getlocal(10).__nonzero__()) {
                              var1.setline(584);
                              var1.f_lasti = -1;
                              return Py.None;
                           }
                           break;
                        }

                        var13 = Py.unpackSequence(var4, 4);
                        var6 = var13[0];
                        var1.setlocal(11, var6);
                        var6 = null;
                        var6 = var13[1];
                        var1.setlocal(12, var6);
                        var6 = null;
                        var6 = var13[2];
                        var1.setlocal(13, var6);
                        var6 = null;
                        var6 = var13[3];
                        var1.setlocal(14, var6);
                        var6 = null;
                        var1.setline(573);
                        if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(9), var1.getlocal(1).__getattr__("get_attr_name").__call__(var2, var1.getlocal(11))).__nonzero__()) {
                           var1.setline(574);
                           PyInteger var14 = Py.newInteger(1);
                           var1.setlocal(10, var14);
                           var5 = null;
                           var1.setline(575);
                           if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(14), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
                              var1.setline(578);
                              throw Py.makeException(var1.getglobal("DistutilsClassError").__call__(var2, PyString.fromInterned("invalid help function %r for help option '%s': must be a callable object (function, etc.)")._mod(new PyTuple(new PyObject[]{var1.getlocal(14), var1.getlocal(11)}))));
                           }

                           var1.setline(576);
                           var1.getlocal(14).__call__(var2);
                        }
                     }
                  }

                  var1.setline(588);
                  var9 = var1.getlocal(0).__getattr__("get_option_dict").__call__(var2, var1.getlocal(4));
                  var1.setlocal(15, var9);
                  var3 = null;
                  var1.setline(589);
                  var9 = var1.getglobal("vars").__call__(var2, var1.getlocal(9)).__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(589);
                     var4 = var9.__iternext__();
                     if (var4 == null) {
                        var1.setline(592);
                        var9 = var1.getlocal(2);
                        var1.f_lasti = -1;
                        return var9;
                     }

                     var13 = Py.unpackSequence(var4, 2);
                     var6 = var13[0];
                     var1.setlocal(16, var6);
                     var6 = null;
                     var6 = var13[1];
                     var1.setlocal(17, var6);
                     var6 = null;
                     var1.setline(590);
                     PyTuple var16 = new PyTuple(new PyObject[]{PyString.fromInterned("command line"), var1.getlocal(17)});
                     var1.getlocal(15).__setitem__((PyObject)var1.getlocal(16), var16);
                     var5 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject finalize_options$11(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyString.fromInterned("Set final values for all the options on the Distribution\n        instance, analogous to the .finalize_options() method of Command\n        objects.\n        ");
      var1.setline(599);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("keywords"), PyString.fromInterned("platforms")})).__iter__();

      while(true) {
         PyObject var5;
         do {
            PyObject var10000;
            do {
               var1.setline(599);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(600);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), var1.getlocal(1));
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(601);
               var5 = var1.getlocal(2);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
            } while(var10000.__nonzero__());

            var1.setline(603);
         } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__());

         var1.setline(604);
         PyList var8 = new PyList();
         var5 = var8.__getattr__("append");
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(604);
         var5 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

         while(true) {
            var1.setline(604);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(604);
               var1.dellocal(3);
               PyList var7 = var8;
               var1.setlocal(2, var7);
               var5 = null;
               var1.setline(605);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), var1.getlocal(1), var1.getlocal(2));
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(604);
            var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("strip").__call__(var2));
         }
      }
   }

   public PyObject _show_help$12(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyString.fromInterned("Show help for the setup script command-line in the form of\n        several lists of command-line options.  'parser' should be a\n        FancyGetopt instance; do not expect it to be returned in the\n        same state, as its option table will be reset to make it\n        generate the correct help text.\n\n        If 'global_options' is true, lists the global options:\n        --verbose, --dry-run, etc.  If 'display_options' is true, lists\n        the \"display-only\" options: --name, --version, etc.  Finally,\n        lists per-command help for every command name or command class\n        in 'commands'.\n        ");
      var1.setline(622);
      String[] var3 = new String[]{"gen_usage"};
      PyObject[] var6 = imp.importFrom("distutils.core", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(623);
      var3 = new String[]{"Command"};
      var6 = imp.importFrom("distutils.cmd", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal(6, var4);
      var4 = null;
      var1.setline(625);
      PyObject var7;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(626);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(627);
            var7 = var1.getlocal(0).__getattr__("_get_toplevel_options").__call__(var2);
            var1.setlocal(7, var7);
            var3 = null;
         } else {
            var1.setline(629);
            var7 = var1.getlocal(0).__getattr__("global_options");
            var1.setlocal(7, var7);
            var3 = null;
         }

         var1.setline(630);
         var1.getlocal(1).__getattr__("set_option_table").__call__(var2, var1.getlocal(7));
         var1.setline(631);
         var1.getlocal(1).__getattr__("print_help").__call__(var2, var1.getlocal(0).__getattr__("common_usage")._add(PyString.fromInterned("\nGlobal options:")));
         var1.setline(632);
         Py.println(PyString.fromInterned(""));
      }

      var1.setline(634);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(635);
         var1.getlocal(1).__getattr__("set_option_table").__call__(var2, var1.getlocal(0).__getattr__("display_options"));
         var1.setline(636);
         var1.getlocal(1).__getattr__("print_help").__call__(var2, PyString.fromInterned("Information display options (just display ")._add(PyString.fromInterned("information, ignore any commands)")));
         var1.setline(639);
         Py.println(PyString.fromInterned(""));
      }

      var1.setline(641);
      var7 = var1.getlocal(0).__getattr__("commands").__iter__();

      while(true) {
         var1.setline(641);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(655);
            Py.println(var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("script_name")));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(8, var4);
         var1.setline(642);
         PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("type"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(8), var1.getlocal(6));
         }

         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(643);
            var5 = var1.getlocal(8);
            var1.setlocal(9, var5);
            var5 = null;
         } else {
            var1.setline(645);
            var5 = var1.getlocal(0).__getattr__("get_command_class").__call__(var2, var1.getlocal(8));
            var1.setlocal(9, var5);
            var5 = null;
         }

         var1.setline(646);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("help_options"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(9).__getattr__("help_options"), var1.getglobal("list"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(648);
            var1.getlocal(1).__getattr__("set_option_table").__call__(var2, var1.getlocal(9).__getattr__("user_options")._add(var1.getglobal("fix_help_options").__call__(var2, var1.getlocal(9).__getattr__("help_options"))));
         } else {
            var1.setline(651);
            var1.getlocal(1).__getattr__("set_option_table").__call__(var2, var1.getlocal(9).__getattr__("user_options"));
         }

         var1.setline(652);
         var1.getlocal(1).__getattr__("print_help").__call__(var2, PyString.fromInterned("Options for '%s' command:")._mod(var1.getlocal(9).__getattr__("__name__")));
         var1.setline(653);
         Py.println(PyString.fromInterned(""));
      }
   }

   public PyObject handle_display_options$13(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      PyString.fromInterned("If there were any non-global \"display-only\" options\n        (--help-commands or the metadata display options) on the command\n        line, display the requested info and return true; else return\n        false.\n        ");
      var1.setline(663);
      String[] var3 = new String[]{"gen_usage"};
      PyObject[] var8 = imp.importFrom("distutils.core", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(668);
      if (var1.getlocal(0).__getattr__("help_commands").__nonzero__()) {
         var1.setline(669);
         var1.getlocal(0).__getattr__("print_commands").__call__(var2);
         var1.setline(670);
         Py.println(PyString.fromInterned(""));
         var1.setline(671);
         Py.println(var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("script_name")));
         var1.setline(672);
         PyInteger var10 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(677);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(3, var11);
         var4 = null;
         var1.setline(678);
         PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(4, var12);
         var4 = null;
         var1.setline(679);
         var4 = var1.getlocal(0).__getattr__("display_options").__iter__();

         while(true) {
            var1.setline(679);
            PyObject var5 = var4.__iternext__();
            PyInteger var6;
            if (var5 == null) {
               var1.setline(682);
               var4 = var1.getlocal(1).__iter__();

               while(true) {
                  var1.setline(682);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(695);
                     PyObject var9 = var1.getlocal(3);
                     var1.f_lasti = -1;
                     return var9;
                  }

                  PyObject[] var13 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var13[0];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var7 = var13[1];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var1.setline(683);
                  PyObject var10000 = var1.getlocal(7);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(4).__getattr__("get").__call__(var2, var1.getlocal(6));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(684);
                     PyObject var14 = var1.getglobal("translate_longopt").__call__(var2, var1.getlocal(6));
                     var1.setlocal(6, var14);
                     var6 = null;
                     var1.setline(685);
                     var14 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("metadata"), PyString.fromInterned("get_")._add(var1.getlocal(6))).__call__(var2);
                     var1.setlocal(8, var14);
                     var6 = null;
                     var1.setline(686);
                     var14 = var1.getlocal(6);
                     var10000 = var14._in(new PyList(new PyObject[]{PyString.fromInterned("keywords"), PyString.fromInterned("platforms")}));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(687);
                        Py.println(PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(8)));
                     } else {
                        var1.setline(688);
                        var14 = var1.getlocal(6);
                        var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("classifiers"), PyString.fromInterned("provides"), PyString.fromInterned("requires"), PyString.fromInterned("obsoletes")}));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(690);
                           Py.println(PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(8)));
                        } else {
                           var1.setline(692);
                           Py.println(var1.getlocal(8));
                        }
                     }

                     var1.setline(693);
                     var6 = Py.newInteger(1);
                     var1.setlocal(3, var6);
                     var6 = null;
                  }
               }
            }

            var1.setlocal(5, var5);
            var1.setline(680);
            var6 = Py.newInteger(1);
            var1.getlocal(4).__setitem__((PyObject)var1.getlocal(5).__getitem__(Py.newInteger(0)), var6);
            var6 = null;
         }
      }
   }

   public PyObject print_command_list$14(PyFrame var1, ThreadState var2) {
      var1.setline(700);
      PyString.fromInterned("Print a subset of the list of all commands -- used by\n        'print_commands()'.\n        ");
      var1.setline(701);
      Py.println(var1.getlocal(2)._add(PyString.fromInterned(":")));
      var1.setline(703);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(703);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(704);
         PyObject var5 = var1.getlocal(0).__getattr__("cmdclass").__getattr__("get").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(705);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(706);
            var5 = var1.getlocal(0).__getattr__("get_command_class").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
         }

         try {
            var1.setline(708);
            var5 = var1.getlocal(5).__getattr__("description");
            var1.setlocal(6, var5);
            var5 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("AttributeError"))) {
               throw var8;
            }

            var1.setline(710);
            PyString var6 = PyString.fromInterned("(no description available)");
            var1.setlocal(6, var6);
            var6 = null;
         }

         var1.setline(712);
         Py.println(PyString.fromInterned("  %-*s  %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)})));
      }
   }

   public PyObject print_commands$15(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyString.fromInterned("Print out a help message listing all available commands with a\n        description of each.  The list is divided into \"standard commands\"\n        (listed in distutils.command.__all__) and \"extra commands\"\n        (mentioned in self.cmdclass, but not a standard command).  The\n        descriptions come from the command class attribute\n        'description'.\n        ");
      var1.setline(722);
      PyObject var3 = imp.importOne("distutils.command", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(723);
      var3 = var1.getlocal(1).__getattr__("command").__getattr__("__all__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(724);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(725);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(725);
         PyObject var4 = var3.__iternext__();
         PyInteger var5;
         if (var4 == null) {
            var1.setline(728);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(5, var7);
            var3 = null;
            var1.setline(729);
            var3 = var1.getlocal(0).__getattr__("cmdclass").__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(729);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(733);
                  PyInteger var9 = Py.newInteger(0);
                  var1.setlocal(6, var9);
                  var3 = null;
                  var1.setline(734);
                  var3 = var1.getlocal(2)._add(var1.getlocal(5)).__iter__();

                  while(true) {
                     var1.setline(734);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(738);
                        var1.getlocal(0).__getattr__("print_command_list").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("Standard commands"), (PyObject)var1.getlocal(6));
                        var1.setline(741);
                        if (var1.getlocal(5).__nonzero__()) {
                           var1.setline(742);
                           Py.println();
                           var1.setline(743);
                           var1.getlocal(0).__getattr__("print_command_list").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("Extra commands"), (PyObject)var1.getlocal(6));
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(4, var4);
                     var1.setline(735);
                     PyObject var8 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                     PyObject var10000 = var8._gt(var1.getlocal(6));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(736);
                        var8 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                        var1.setlocal(6, var8);
                        var5 = null;
                     }
                  }
               }

               var1.setlocal(4, var4);
               var1.setline(730);
               if (var1.getlocal(3).__getattr__("get").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  var1.setline(731);
                  var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(726);
         var5 = Py.newInteger(1);
         var1.getlocal(3).__setitem__((PyObject)var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject get_command_list$16(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyString.fromInterned("Get a list of (command, description) tuples.\n        The list is divided into \"standard commands\" (listed in\n        distutils.command.__all__) and \"extra commands\" (mentioned in\n        self.cmdclass, but not a standard command).  The descriptions come\n        from the command class attribute 'description'.\n        ");
      var1.setline(757);
      PyObject var3 = imp.importOne("distutils.command", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(758);
      var3 = var1.getlocal(1).__getattr__("command").__getattr__("__all__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(759);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(760);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(760);
         PyObject var4 = var3.__iternext__();
         PyInteger var5;
         if (var4 == null) {
            var1.setline(763);
            PyList var9 = new PyList(Py.EmptyObjects);
            var1.setlocal(5, var9);
            var3 = null;
            var1.setline(764);
            var3 = var1.getlocal(0).__getattr__("cmdclass").__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(764);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(768);
                  var9 = new PyList(Py.EmptyObjects);
                  var1.setlocal(6, var9);
                  var3 = null;
                  var1.setline(769);
                  var3 = var1.getlocal(2)._add(var1.getlocal(5)).__iter__();

                  while(true) {
                     var1.setline(769);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(778);
                        var3 = var1.getlocal(6);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(4, var4);
                     var1.setline(770);
                     PyObject var10 = var1.getlocal(0).__getattr__("cmdclass").__getattr__("get").__call__(var2, var1.getlocal(4));
                     var1.setlocal(7, var10);
                     var5 = null;
                     var1.setline(771);
                     if (var1.getlocal(7).__not__().__nonzero__()) {
                        var1.setline(772);
                        var10 = var1.getlocal(0).__getattr__("get_command_class").__call__(var2, var1.getlocal(4));
                        var1.setlocal(7, var10);
                        var5 = null;
                     }

                     try {
                        var1.setline(774);
                        var10 = var1.getlocal(7).__getattr__("description");
                        var1.setlocal(8, var10);
                        var5 = null;
                     } catch (Throwable var7) {
                        PyException var11 = Py.setException(var7, var1);
                        if (!var11.match(var1.getglobal("AttributeError"))) {
                           throw var11;
                        }

                        var1.setline(776);
                        PyString var6 = PyString.fromInterned("(no description available)");
                        var1.setlocal(8, var6);
                        var6 = null;
                     }

                     var1.setline(777);
                     var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(8)})));
                  }
               }

               var1.setlocal(4, var4);
               var1.setline(765);
               if (var1.getlocal(3).__getattr__("get").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  var1.setline(766);
                  var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(761);
         var5 = Py.newInteger(1);
         var1.getlocal(3).__setitem__((PyObject)var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject get_command_packages$17(PyFrame var1, ThreadState var2) {
      var1.setline(783);
      PyString.fromInterned("Return a list of packages from which commands are loaded.");
      var1.setline(784);
      PyObject var3 = var1.getlocal(0).__getattr__("command_packages");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(785);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(786);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyString var6;
         if (var10000.__nonzero__()) {
            var1.setline(787);
            var6 = PyString.fromInterned("");
            var1.setlocal(1, var6);
            var3 = null;
         }

         var1.setline(788);
         PyList var8 = new PyList();
         var3 = var8.__getattr__("append");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(788);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

         while(true) {
            var1.setline(788);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(788);
               var1.dellocal(2);
               PyList var7 = var8;
               var1.setlocal(1, var7);
               var3 = null;
               var1.setline(789);
               var6 = PyString.fromInterned("distutils.command");
               var10000 = var6._notin(var1.getlocal(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(790);
                  var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("distutils.command"));
               }

               var1.setline(791);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("command_packages", var3);
               var3 = null;
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(788);
            PyObject var5 = var1.getlocal(3);
            PyObject var10001 = var5._ne(PyString.fromInterned(""));
            var5 = null;
            if (var10001.__nonzero__()) {
               var1.setline(788);
               var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("strip").__call__(var2));
            }
         }
      }

      var1.setline(792);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_command_class$18(PyFrame var1, ThreadState var2) {
      var1.setline(805);
      PyString.fromInterned("Return the class that implements the Distutils command named by\n        'command'.  First we check the 'cmdclass' dictionary; if the\n        command is mentioned there, we fetch the class object from the\n        dictionary and return it.  Otherwise we load the command module\n        (\"distutils.command.\" + command) and fetch the command class from\n        the module.  The loaded class is also stored in 'cmdclass'\n        to speed future calls to 'get_command_class()'.\n\n        Raises DistutilsModuleError if the expected module could not be\n        found, or if that module does not define the expected class.\n        ");
      var1.setline(806);
      PyObject var3 = var1.getlocal(0).__getattr__("cmdclass").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(807);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(808);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(810);
         PyObject var4 = var1.getlocal(0).__getattr__("get_command_packages").__call__(var2).__iter__();

         while(true) {
            var1.setline(810);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(830);
               throw Py.makeException(var1.getglobal("DistutilsModuleError").__call__(var2, PyString.fromInterned("invalid command '%s'")._mod(var1.getlocal(1))));
            }

            var1.setlocal(3, var5);
            var1.setline(811);
            PyObject var6 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)}));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(812);
            var6 = var1.getlocal(1);
            var1.setlocal(5, var6);
            var6 = null;

            PyException var9;
            try {
               var1.setline(815);
               var1.getglobal("__import__").__call__(var2, var1.getlocal(4));
               var1.setline(816);
               var6 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(4));
               var1.setlocal(6, var6);
               var6 = null;
            } catch (Throwable var8) {
               var9 = Py.setException(var8, var1);
               if (var9.match(var1.getglobal("ImportError"))) {
                  continue;
               }

               throw var9;
            }

            try {
               var1.setline(821);
               var6 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(5));
               var1.setlocal(2, var6);
               var6 = null;
            } catch (Throwable var7) {
               var9 = Py.setException(var7, var1);
               if (var9.match(var1.getglobal("AttributeError"))) {
                  var1.setline(823);
                  throw Py.makeException(var1.getglobal("DistutilsModuleError"), PyString.fromInterned("invalid command '%s' (no class '%s' in module '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(4)})));
               }

               throw var9;
            }

            var1.setline(827);
            var6 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("cmdclass").__setitem__(var1.getlocal(1), var6);
            var6 = null;
            var1.setline(828);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_command_obj$19(PyFrame var1, ThreadState var2) {
      var1.setline(838);
      PyString.fromInterned("Return the command object for 'command'.  Normally this object\n        is cached on a previous call to 'get_command_obj()'; if no command\n        object for 'command' is in the cache, then we either create and\n        return it (if 'create' is true) or return None.\n        ");
      var1.setline(839);
      PyObject var3 = var1.getlocal(0).__getattr__("command_obj").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(840);
      PyObject var10000 = var1.getlocal(3).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(841);
         if (var1.getglobal("DEBUG").__nonzero__()) {
            var1.setline(842);
            var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Distribution.get_command_obj(): creating '%s' command object")._mod(var1.getlocal(1)));
         }

         var1.setline(845);
         var3 = var1.getlocal(0).__getattr__("get_command_class").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(846);
         var3 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var1.getlocal(0).__getattr__("command_obj").__setitem__(var1.getlocal(1), var3);
         var1.setline(847);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__getattr__("have_run").__setitem__((PyObject)var1.getlocal(1), var4);
         var3 = null;
         var1.setline(854);
         var3 = var1.getlocal(0).__getattr__("command_options").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(855);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(856);
            var1.getlocal(0).__getattr__("_set_command_options").__call__(var2, var1.getlocal(3), var1.getlocal(5));
         }
      }

      var1.setline(858);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_command_options$20(PyFrame var1, ThreadState var2) {
      var1.setline(868);
      PyString.fromInterned("Set the options for 'command_obj' from 'option_dict'.  Basically\n        this means copying elements of a dictionary ('option_dict') to\n        attributes of an instance ('command').\n\n        'command_obj' must be a Command instance.  If 'option_dict' is not\n        supplied, uses the standard option dictionary for this command\n        (from 'self.command_options').\n        ");
      var1.setline(869);
      PyObject var3 = var1.getlocal(1).__getattr__("get_command_name").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(870);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(871);
         var3 = var1.getlocal(0).__getattr__("get_option_dict").__call__(var2, var1.getlocal(3));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(873);
      if (var1.getglobal("DEBUG").__nonzero__()) {
         var1.setline(874);
         var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("  setting options for '%s' command:")._mod(var1.getlocal(3)));
      }

      var1.setline(875);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(875);
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
         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(5, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(6, var8);
         var8 = null;
         var6 = null;
         var1.setline(876);
         if (var1.getglobal("DEBUG").__nonzero__()) {
            var1.setline(877);
            var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("    %s = %s (from %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6), var1.getlocal(5)})));
         }

         PyException var12;
         PyObject var13;
         try {
            var1.setline(880);
            var13 = var1.getglobal("map").__call__(var2, var1.getglobal("translate_longopt"), var1.getlocal(1).__getattr__("boolean_options"));
            var1.setlocal(7, var13);
            var5 = null;
         } catch (Throwable var10) {
            var12 = Py.setException(var10, var1);
            if (!var12.match(var1.getglobal("AttributeError"))) {
               throw var12;
            }

            var1.setline(882);
            PyList var14 = new PyList(Py.EmptyObjects);
            var1.setlocal(7, var14);
            var6 = null;
         }

         try {
            var1.setline(884);
            var13 = var1.getlocal(1).__getattr__("negative_opt");
            var1.setlocal(8, var13);
            var5 = null;
         } catch (Throwable var11) {
            var12 = Py.setException(var11, var1);
            if (!var12.match(var1.getglobal("AttributeError"))) {
               throw var12;
            }

            var1.setline(886);
            PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var15);
            var6 = null;
         }

         try {
            var1.setline(889);
            var13 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("str"));
            var1.setlocal(9, var13);
            var5 = null;
            var1.setline(890);
            var13 = var1.getlocal(4);
            var10000 = var13._in(var1.getlocal(8));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(9);
            }

            if (var10000.__nonzero__()) {
               var1.setline(891);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(8).__getitem__(var1.getlocal(4)), var1.getglobal("strtobool").__call__(var2, var1.getlocal(6)).__not__());
            } else {
               var1.setline(892);
               var13 = var1.getlocal(4);
               var10000 = var13._in(var1.getlocal(7));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(9);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(893);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getglobal("strtobool").__call__(var2, var1.getlocal(6)));
               } else {
                  var1.setline(894);
                  if (!var1.getglobal("hasattr").__call__(var2, var1.getlocal(1), var1.getlocal(4)).__nonzero__()) {
                     var1.setline(897);
                     throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("error in %s: command '%s' has no such option '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(3), var1.getlocal(4)})));
                  }

                  var1.setline(895);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(6));
               }
            }
         } catch (Throwable var9) {
            var12 = Py.setException(var9, var1);
            if (var12.match(var1.getglobal("ValueError"))) {
               var6 = var12.value;
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(901);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), var1.getlocal(10));
            }

            throw var12;
         }
      }
   }

   public PyObject reinitialize_command$21(PyFrame var1, ThreadState var2) {
      var1.setline(921);
      PyString.fromInterned("Reinitializes a command to the state it was in when first\n        returned by 'get_command_obj()': ie., initialized but not yet\n        finalized.  This provides the opportunity to sneak option\n        values in programmatically, overriding or supplementing\n        user-supplied values from the config files and command line.\n        You'll have to re-finalize the command object (by calling\n        'finalize_options()' or 'ensure_finalized()') before using it for\n        real.\n\n        'command' should be a command name (string) or command object.  If\n        'reinit_subcommands' is true, also reinitializes the command's\n        sub-commands, as declared by the 'sub_commands' class attribute (if\n        it has one).  See the \"install\" command for an example.  Only\n        reinitializes the sub-commands that actually matter, ie. those\n        whose test predicates return true.\n\n        Returns the reinitialized command object.\n        ");
      var1.setline(922);
      String[] var3 = new String[]{"Command"};
      PyObject[] var6 = imp.importFrom("distutils.cmd", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(923);
      PyObject var7;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(3)).__not__().__nonzero__()) {
         var1.setline(924);
         var7 = var1.getlocal(1);
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(925);
         var7 = var1.getlocal(0).__getattr__("get_command_obj").__call__(var2, var1.getlocal(4));
         var1.setlocal(1, var7);
         var3 = null;
      } else {
         var1.setline(927);
         var7 = var1.getlocal(1).__getattr__("get_command_name").__call__(var2);
         var1.setlocal(4, var7);
         var3 = null;
      }

      var1.setline(929);
      if (var1.getlocal(1).__getattr__("finalized").__not__().__nonzero__()) {
         var1.setline(930);
         var7 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(931);
         var1.getlocal(1).__getattr__("initialize_options").__call__(var2);
         var1.setline(932);
         PyInteger var8 = Py.newInteger(0);
         var1.getlocal(1).__setattr__((String)"finalized", var8);
         var4 = null;
         var1.setline(933);
         var8 = Py.newInteger(0);
         var1.getlocal(0).__getattr__("have_run").__setitem__((PyObject)var1.getlocal(4), var8);
         var4 = null;
         var1.setline(934);
         var1.getlocal(0).__getattr__("_set_command_options").__call__(var2, var1.getlocal(1));
         var1.setline(936);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(937);
            var4 = var1.getlocal(1).__getattr__("get_sub_commands").__call__(var2).__iter__();

            while(true) {
               var1.setline(937);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(5, var5);
               var1.setline(938);
               var1.getlocal(0).__getattr__("reinitialize_command").__call__(var2, var1.getlocal(5), var1.getlocal(2));
            }
         }

         var1.setline(940);
         var7 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject announce$22(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      var1.getglobal("log").__getattr__("log").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run_commands$23(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      PyString.fromInterned("Run each command that was seen on the setup script command line.\n        Uses the list of commands found and cache of command objects\n        created by 'get_command_obj()'.\n        ");
      var1.setline(952);
      PyObject var3 = var1.getlocal(0).__getattr__("commands").__iter__();

      while(true) {
         var1.setline(952);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(953);
         var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject run_command$24(PyFrame var1, ThreadState var2) {
      var1.setline(964);
      PyString.fromInterned("Do whatever it takes to run a command (including nothing at all,\n        if the command has already been run).  Specifically: if we have\n        already created and run the command named by 'command', return\n        silently without doing anything.  If the command named by 'command'\n        doesn't even have a command object yet, create one.  Then invoke\n        'run()' on that command object (or an existing one).\n        ");
      var1.setline(966);
      if (var1.getlocal(0).__getattr__("have_run").__getattr__("get").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(967);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(969);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("running %s"), (PyObject)var1.getlocal(1));
         var1.setline(970);
         PyObject var3 = var1.getlocal(0).__getattr__("get_command_obj").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(971);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(972);
         var1.getlocal(2).__getattr__("run").__call__(var2);
         var1.setline(973);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("have_run").__setitem__((PyObject)var1.getlocal(1), var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject has_pure_modules$25(PyFrame var1, ThreadState var2) {
      var1.setline(979);
      PyObject var10000 = var1.getglobal("len");
      Object var10002 = var1.getlocal(0).__getattr__("packages");
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = var1.getlocal(0).__getattr__("py_modules");
         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = new PyList(Py.EmptyObjects);
         }
      }

      PyObject var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_ext_modules$26(PyFrame var1, ThreadState var2) {
      var1.setline(982);
      PyObject var10000 = var1.getlocal(0).__getattr__("ext_modules");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("ext_modules"));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_c_libraries$27(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      PyObject var10000 = var1.getlocal(0).__getattr__("libraries");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_modules$28(PyFrame var1, ThreadState var2) {
      var1.setline(988);
      PyObject var10000 = var1.getlocal(0).__getattr__("has_pure_modules").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("has_ext_modules").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_headers$29(PyFrame var1, ThreadState var2) {
      var1.setline(991);
      PyObject var10000 = var1.getlocal(0).__getattr__("headers");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("headers"));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_scripts$30(PyFrame var1, ThreadState var2) {
      var1.setline(994);
      PyObject var10000 = var1.getlocal(0).__getattr__("scripts");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("scripts"));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_data_files$31(PyFrame var1, ThreadState var2) {
      var1.setline(997);
      PyObject var10000 = var1.getlocal(0).__getattr__("data_files");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data_files"));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_pure$32(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyObject var10000 = var1.getlocal(0).__getattr__("has_pure_modules").__call__(var2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("has_ext_modules").__call__(var2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("has_c_libraries").__call__(var2).__not__();
         }
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DistributionMetadata$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dummy class to hold the distribution meta-data: name, version,\n    author, and so forth.\n    "));
      var1.setline(1014);
      PyString.fromInterned("Dummy class to hold the distribution meta-data: name, version,\n    author, and so forth.\n    ");
      var1.setline(1016);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("version"), PyString.fromInterned("author"), PyString.fromInterned("author_email"), PyString.fromInterned("maintainer"), PyString.fromInterned("maintainer_email"), PyString.fromInterned("url"), PyString.fromInterned("license"), PyString.fromInterned("description"), PyString.fromInterned("long_description"), PyString.fromInterned("keywords"), PyString.fromInterned("platforms"), PyString.fromInterned("fullname"), PyString.fromInterned("contact"), PyString.fromInterned("contact_email"), PyString.fromInterned("license"), PyString.fromInterned("classifiers"), PyString.fromInterned("download_url"), PyString.fromInterned("provides"), PyString.fromInterned("requires"), PyString.fromInterned("obsoletes")});
      var1.setlocal("_METHOD_BASENAMES", var3);
      var3 = null;
      var1.setline(1026);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$34, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1049);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read_pkg_file$35, PyString.fromInterned("Reads the metadata values from a file object."));
      var1.setlocal("read_pkg_file", var5);
      var3 = null;
      var1.setline(1101);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write_pkg_info$38, PyString.fromInterned("Write the PKG-INFO file into the release tree.\n        "));
      var1.setlocal("write_pkg_info", var5);
      var3 = null;
      var1.setline(1110);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write_pkg_file$39, PyString.fromInterned("Write the PKG-INFO format data to a file object.\n        "));
      var1.setlocal("write_pkg_file", var5);
      var3 = null;
      var1.setline(1144);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _write_field$40, (PyObject)null);
      var1.setlocal("_write_field", var5);
      var3 = null;
      var1.setline(1147);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _write_list$41, (PyObject)null);
      var1.setlocal("_write_list", var5);
      var3 = null;
      var1.setline(1151);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _encode_field$42, (PyObject)null);
      var1.setlocal("_encode_field", var5);
      var3 = null;
      var1.setline(1160);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_name$43, (PyObject)null);
      var1.setlocal("get_name", var5);
      var3 = null;
      var1.setline(1163);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_version$44, (PyObject)null);
      var1.setlocal("get_version", var5);
      var3 = null;
      var1.setline(1166);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_fullname$45, (PyObject)null);
      var1.setlocal("get_fullname", var5);
      var3 = null;
      var1.setline(1169);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_author$46, (PyObject)null);
      var1.setlocal("get_author", var5);
      var3 = null;
      var1.setline(1172);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_author_email$47, (PyObject)null);
      var1.setlocal("get_author_email", var5);
      var3 = null;
      var1.setline(1175);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_maintainer$48, (PyObject)null);
      var1.setlocal("get_maintainer", var5);
      var3 = null;
      var1.setline(1178);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_maintainer_email$49, (PyObject)null);
      var1.setlocal("get_maintainer_email", var5);
      var3 = null;
      var1.setline(1181);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_contact$50, (PyObject)null);
      var1.setlocal("get_contact", var5);
      var3 = null;
      var1.setline(1185);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_contact_email$51, (PyObject)null);
      var1.setlocal("get_contact_email", var5);
      var3 = null;
      var1.setline(1188);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_url$52, (PyObject)null);
      var1.setlocal("get_url", var5);
      var3 = null;
      var1.setline(1191);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_license$53, (PyObject)null);
      var1.setlocal("get_license", var5);
      var3 = null;
      var1.setline(1193);
      PyObject var6 = var1.getname("get_license");
      var1.setlocal("get_licence", var6);
      var3 = null;
      var1.setline(1195);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_description$54, (PyObject)null);
      var1.setlocal("get_description", var5);
      var3 = null;
      var1.setline(1198);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_long_description$55, (PyObject)null);
      var1.setlocal("get_long_description", var5);
      var3 = null;
      var1.setline(1201);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_keywords$56, (PyObject)null);
      var1.setlocal("get_keywords", var5);
      var3 = null;
      var1.setline(1204);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_platforms$57, (PyObject)null);
      var1.setlocal("get_platforms", var5);
      var3 = null;
      var1.setline(1207);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_classifiers$58, (PyObject)null);
      var1.setlocal("get_classifiers", var5);
      var3 = null;
      var1.setline(1210);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_download_url$59, (PyObject)null);
      var1.setlocal("get_download_url", var5);
      var3 = null;
      var1.setline(1214);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_requires$60, (PyObject)null);
      var1.setlocal("get_requires", var5);
      var3 = null;
      var1.setline(1217);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_requires$61, (PyObject)null);
      var1.setlocal("set_requires", var5);
      var3 = null;
      var1.setline(1223);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_provides$62, (PyObject)null);
      var1.setlocal("get_provides", var5);
      var3 = null;
      var1.setline(1226);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_provides$63, (PyObject)null);
      var1.setlocal("set_provides", var5);
      var3 = null;
      var1.setline(1233);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_obsoletes$64, (PyObject)null);
      var1.setlocal("get_obsoletes", var5);
      var3 = null;
      var1.setline(1236);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_obsoletes$65, (PyObject)null);
      var1.setlocal("set_obsoletes", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(1027);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1028);
         var1.getlocal(0).__getattr__("read_pkg_file").__call__(var2, var1.getglobal("open").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(1030);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(1031);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("version", var3);
         var3 = null;
         var1.setline(1032);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("author", var3);
         var3 = null;
         var1.setline(1033);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("author_email", var3);
         var3 = null;
         var1.setline(1034);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("maintainer", var3);
         var3 = null;
         var1.setline(1035);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("maintainer_email", var3);
         var3 = null;
         var1.setline(1036);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("url", var3);
         var3 = null;
         var1.setline(1037);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("license", var3);
         var3 = null;
         var1.setline(1038);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("description", var3);
         var3 = null;
         var1.setline(1039);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("long_description", var3);
         var3 = null;
         var1.setline(1040);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("keywords", var3);
         var3 = null;
         var1.setline(1041);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("platforms", var3);
         var3 = null;
         var1.setline(1042);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("classifiers", var3);
         var3 = null;
         var1.setline(1043);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("download_url", var3);
         var3 = null;
         var1.setline(1045);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("provides", var3);
         var3 = null;
         var1.setline(1046);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("requires", var3);
         var3 = null;
         var1.setline(1047);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("obsoletes", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_pkg_file$35(PyFrame var1, ThreadState var2) {
      var1.setline(1050);
      PyString.fromInterned("Reads the metadata values from a file object.");
      var1.setline(1051);
      PyObject var3 = var1.getglobal("message_from_file").__call__(var2, var1.getlocal(1));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(1053);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = _read_field$36;
      var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1059);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = _read_list$37;
      var4 = new PyObject[]{var1.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1065);
      var3 = var1.getderef(0).__getitem__(PyString.fromInterned("metadata-version"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1066);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"));
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(1067);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("version"));
      var1.getlocal(0).__setattr__("version", var3);
      var3 = null;
      var1.setline(1068);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("summary"));
      var1.getlocal(0).__setattr__("description", var3);
      var3 = null;
      var1.setline(1070);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("author"));
      var1.getlocal(0).__setattr__("author", var3);
      var3 = null;
      var1.setline(1071);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("maintainer", var3);
      var3 = null;
      var1.setline(1072);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("author-email"));
      var1.getlocal(0).__setattr__("author_email", var3);
      var3 = null;
      var1.setline(1073);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("maintainer_email", var3);
      var3 = null;
      var1.setline(1074);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("home-page"));
      var1.getlocal(0).__setattr__("url", var3);
      var3 = null;
      var1.setline(1075);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("license"));
      var1.getlocal(0).__setattr__("license", var3);
      var3 = null;
      var1.setline(1077);
      PyString var6 = PyString.fromInterned("download-url");
      PyObject var10000 = var6._in(var1.getderef(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1078);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("download-url"));
         var1.getlocal(0).__setattr__("download_url", var3);
         var3 = null;
      } else {
         var1.setline(1080);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("download_url", var3);
         var3 = null;
      }

      var1.setline(1082);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("description"));
      var1.getlocal(0).__setattr__("long_description", var3);
      var3 = null;
      var1.setline(1083);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("summary"));
      var1.getlocal(0).__setattr__("description", var3);
      var3 = null;
      var1.setline(1085);
      var6 = PyString.fromInterned("keywords");
      var10000 = var6._in(var1.getderef(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1086);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("keywords")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.getlocal(0).__setattr__("keywords", var3);
         var3 = null;
      }

      var1.setline(1088);
      var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("platform"));
      var1.getlocal(0).__setattr__("platforms", var3);
      var3 = null;
      var1.setline(1089);
      var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("classifier"));
      var1.getlocal(0).__setattr__("classifiers", var3);
      var3 = null;
      var1.setline(1092);
      var3 = var1.getlocal(4);
      var10000 = var3._eq(PyString.fromInterned("1.1"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1093);
         var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("requires"));
         var1.getlocal(0).__setattr__("requires", var3);
         var3 = null;
         var1.setline(1094);
         var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("provides"));
         var1.getlocal(0).__setattr__("provides", var3);
         var3 = null;
         var1.setline(1095);
         var3 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("obsoletes"));
         var1.getlocal(0).__setattr__("obsoletes", var3);
         var3 = null;
      } else {
         var1.setline(1097);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("requires", var3);
         var3 = null;
         var1.setline(1098);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("provides", var3);
         var3 = null;
         var1.setline(1099);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("obsoletes", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read_field$36(PyFrame var1, ThreadState var2) {
      var1.setline(1054);
      PyObject var3 = var1.getderef(0).__getitem__(var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1055);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("UNKNOWN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1056);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1057);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _read_list$37(PyFrame var1, ThreadState var2) {
      var1.setline(1060);
      PyObject var3 = var1.getderef(0).__getattr__("get_all").__call__(var2, var1.getlocal(0), var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1061);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(new PyList(Py.EmptyObjects));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1062);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1063);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject write_pkg_info$38(PyFrame var1, ThreadState var2) {
      var1.setline(1103);
      PyString.fromInterned("Write the PKG-INFO file into the release tree.\n        ");
      var1.setline(1104);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("PKG-INFO")), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1106);
         var1.getlocal(0).__getattr__("write_pkg_file").__call__(var2, var1.getlocal(2));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(1108);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(1108);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_pkg_file$39(PyFrame var1, ThreadState var2) {
      var1.setline(1112);
      PyString.fromInterned("Write the PKG-INFO format data to a file object.\n        ");
      var1.setline(1113);
      PyString var3 = PyString.fromInterned("1.0");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1114);
      PyObject var10000 = var1.getlocal(0).__getattr__("provides");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("requires");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("obsoletes");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("classifiers");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("download_url");
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1116);
         var3 = PyString.fromInterned("1.1");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1118);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Metadata-Version"), (PyObject)var1.getlocal(2));
      var1.setline(1119);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Name"), (PyObject)var1.getlocal(0).__getattr__("get_name").__call__(var2));
      var1.setline(1120);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Version"), (PyObject)var1.getlocal(0).__getattr__("get_version").__call__(var2));
      var1.setline(1121);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Summary"), (PyObject)var1.getlocal(0).__getattr__("get_description").__call__(var2));
      var1.setline(1122);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Home-page"), (PyObject)var1.getlocal(0).__getattr__("get_url").__call__(var2));
      var1.setline(1123);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Author"), (PyObject)var1.getlocal(0).__getattr__("get_contact").__call__(var2));
      var1.setline(1124);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Author-email"), (PyObject)var1.getlocal(0).__getattr__("get_contact_email").__call__(var2));
      var1.setline(1125);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("License"), (PyObject)var1.getlocal(0).__getattr__("get_license").__call__(var2));
      var1.setline(1126);
      if (var1.getlocal(0).__getattr__("download_url").__nonzero__()) {
         var1.setline(1127);
         var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Download-URL"), (PyObject)var1.getlocal(0).__getattr__("download_url"));
      }

      var1.setline(1129);
      PyObject var4 = var1.getglobal("rfc822_escape").__call__(var2, var1.getlocal(0).__getattr__("get_long_description").__call__(var2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1130);
      var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Description"), (PyObject)var1.getlocal(3));
      var1.setline(1132);
      var4 = PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("get_keywords").__call__(var2));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(1133);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1134);
         var1.getlocal(0).__getattr__("_write_field").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Keywords"), (PyObject)var1.getlocal(4));
      }

      var1.setline(1136);
      var1.getlocal(0).__getattr__("_write_list").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Platform"), (PyObject)var1.getlocal(0).__getattr__("get_platforms").__call__(var2));
      var1.setline(1137);
      var1.getlocal(0).__getattr__("_write_list").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Classifier"), (PyObject)var1.getlocal(0).__getattr__("get_classifiers").__call__(var2));
      var1.setline(1140);
      var1.getlocal(0).__getattr__("_write_list").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Requires"), (PyObject)var1.getlocal(0).__getattr__("get_requires").__call__(var2));
      var1.setline(1141);
      var1.getlocal(0).__getattr__("_write_list").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Provides"), (PyObject)var1.getlocal(0).__getattr__("get_provides").__call__(var2));
      var1.setline(1142);
      var1.getlocal(0).__getattr__("_write_list").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Obsoletes"), (PyObject)var1.getlocal(0).__getattr__("get_obsoletes").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_field$40(PyFrame var1, ThreadState var2) {
      var1.setline(1145);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(3))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_list$41(PyFrame var1, ThreadState var2) {
      var1.setline(1148);
      PyObject var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(1148);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1149);
         var1.getlocal(0).__getattr__("_write_field").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
      }
   }

   public PyObject _encode_field$42(PyFrame var1, ThreadState var2) {
      var1.setline(1152);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1153);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1154);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(1155);
            var3 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getglobal("PKG_INFO_ENCODING"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1156);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_name$43(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      Object var10000 = var1.getlocal(0).__getattr__("name");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_version$44(PyFrame var1, ThreadState var2) {
      var1.setline(1164);
      Object var10000 = var1.getlocal(0).__getattr__("version");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("0.0.0");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_fullname$45(PyFrame var1, ThreadState var2) {
      var1.setline(1167);
      PyObject var3 = PyString.fromInterned("%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("get_name").__call__(var2), var1.getlocal(0).__getattr__("get_version").__call__(var2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_author$46(PyFrame var1, ThreadState var2) {
      var1.setline(1170);
      Object var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("author"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_author_email$47(PyFrame var1, ThreadState var2) {
      var1.setline(1173);
      Object var10000 = var1.getlocal(0).__getattr__("author_email");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_maintainer$48(PyFrame var1, ThreadState var2) {
      var1.setline(1176);
      Object var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("maintainer"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_maintainer_email$49(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      Object var10000 = var1.getlocal(0).__getattr__("maintainer_email");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_contact$50(PyFrame var1, ThreadState var2) {
      var1.setline(1182);
      Object var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("maintainer"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("author"));
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("UNKNOWN");
         }
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_contact_email$51(PyFrame var1, ThreadState var2) {
      var1.setline(1186);
      Object var10000 = var1.getlocal(0).__getattr__("maintainer_email");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("author_email");
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("UNKNOWN");
         }
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_url$52(PyFrame var1, ThreadState var2) {
      var1.setline(1189);
      Object var10000 = var1.getlocal(0).__getattr__("url");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_license$53(PyFrame var1, ThreadState var2) {
      var1.setline(1192);
      Object var10000 = var1.getlocal(0).__getattr__("license");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_description$54(PyFrame var1, ThreadState var2) {
      var1.setline(1196);
      Object var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("description"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_long_description$55(PyFrame var1, ThreadState var2) {
      var1.setline(1199);
      Object var10000 = var1.getlocal(0).__getattr__("_encode_field").__call__(var2, var1.getlocal(0).__getattr__("long_description"));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_keywords$56(PyFrame var1, ThreadState var2) {
      var1.setline(1202);
      Object var10000 = var1.getlocal(0).__getattr__("keywords");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_platforms$57(PyFrame var1, ThreadState var2) {
      var1.setline(1205);
      Object var10000 = var1.getlocal(0).__getattr__("platforms");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(new PyObject[]{PyString.fromInterned("UNKNOWN")});
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_classifiers$58(PyFrame var1, ThreadState var2) {
      var1.setline(1208);
      Object var10000 = var1.getlocal(0).__getattr__("classifiers");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_download_url$59(PyFrame var1, ThreadState var2) {
      var1.setline(1211);
      Object var10000 = var1.getlocal(0).__getattr__("download_url");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("UNKNOWN");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_requires$60(PyFrame var1, ThreadState var2) {
      var1.setline(1215);
      Object var10000 = var1.getlocal(0).__getattr__("requires");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject set_requires$61(PyFrame var1, ThreadState var2) {
      var1.setline(1218);
      PyObject var3 = imp.importOne("distutils.versionpredicate", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1219);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1219);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1221);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("requires", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1220);
         var1.getlocal(2).__getattr__("versionpredicate").__getattr__("VersionPredicate").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject get_provides$62(PyFrame var1, ThreadState var2) {
      var1.setline(1224);
      Object var10000 = var1.getlocal(0).__getattr__("provides");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject set_provides$63(PyFrame var1, ThreadState var2) {
      var1.setline(1227);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1227);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1227);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1227);
            var1.dellocal(2);
            PyList var6 = var10000;
            var1.setlocal(1, var6);
            var3 = null;
            var1.setline(1228);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(1228);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1231);
                  var3 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("provides", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(1229);
               PyObject var5 = imp.importOne("distutils.versionpredicate", var1, -1);
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(1230);
               var1.getlocal(4).__getattr__("versionpredicate").__getattr__("split_provision").__call__(var2, var1.getlocal(3));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(1227);
         var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject get_obsoletes$64(PyFrame var1, ThreadState var2) {
      var1.setline(1234);
      Object var10000 = var1.getlocal(0).__getattr__("obsoletes");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject set_obsoletes$65(PyFrame var1, ThreadState var2) {
      var1.setline(1237);
      PyObject var3 = imp.importOne("distutils.versionpredicate", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1238);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1238);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1240);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("obsoletes", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1239);
         var1.getlocal(2).__getattr__("versionpredicate").__getattr__("VersionPredicate").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject fix_help_options$66(PyFrame var1, ThreadState var2) {
      var1.setline(1245);
      PyString.fromInterned("Convert a 4-tuple 'help_options' list as found in various command\n    classes to the 3-tuple form required by FancyGetopt.\n    ");
      var1.setline(1246);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1247);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1247);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1249);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1248);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null));
      }
   }

   public dist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Distribution$1 = Py.newCode(0, var2, var1, "Distribution", 34, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x"};
      f$2 = Py.newCode(1, var2, var1, "<lambda>", 119, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "attr", "basename", "method_name", "options", "command", "cmd_options", "opt_dict", "opt", "val", "msg", "key", "arg"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 128, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "dict"};
      get_option_dict$4 = Py.newCode(2, var2, var1, "get_option_dict", 289, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "commands", "indent", "pformat", "cmd_name", "opt_dict", "out", "line"};
      dump_option_dicts$5 = Py.newCode(4, var2, var1, "dump_option_dicts", 300, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "files", "sys_dir", "sys_file", "user_filename", "user_file", "local_file"};
      find_config_files$6 = Py.newCode(1, var2, var1, "find_config_files", 329, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filenames", "ConfigParser", "parser", "filename", "section", "options", "opt_dict", "opt", "val", "src", "alias", "msg"};
      parse_config_files$7 = Py.newCode(2, var2, var1, "parse_config_files", 377, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "toplevel_options", "parser", "args", "option_order"};
      parse_command_line$8 = Py.newCode(1, var2, var1, "parse_command_line", 423, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_toplevel_options$9 = Py.newCode(1, var2, var1, "_get_toplevel_options", 490, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "args", "Command", "command", "cmd_class", "msg", "negative_opt", "help_options", "opts", "help_option_found", "help_option", "short", "desc", "func", "opt_dict", "name", "value"};
      _parse_command_opts$10 = Py.newCode(3, var2, var1, "_parse_command_opts", 501, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "value", "_[604_25]", "elm"};
      finalize_options$11 = Py.newCode(1, var2, var1, "finalize_options", 594, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "global_options", "display_options", "commands", "gen_usage", "Command", "options", "command", "klass"};
      _show_help$12 = Py.newCode(5, var2, var1, "_show_help", 607, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_order", "gen_usage", "any_display_options", "is_display_option", "option", "opt", "val", "value"};
      handle_display_options$13 = Py.newCode(2, var2, var1, "handle_display_options", 657, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "commands", "header", "max_length", "cmd", "klass", "description"};
      print_command_list$14 = Py.newCode(4, var2, var1, "print_command_list", 697, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "distutils", "std_commands", "is_std", "cmd", "extra_commands", "max_length"};
      print_commands$15 = Py.newCode(1, var2, var1, "print_commands", 714, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "distutils", "std_commands", "is_std", "cmd", "extra_commands", "rv", "klass", "description"};
      get_command_list$16 = Py.newCode(1, var2, var1, "get_command_list", 747, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkgs", "_[788_20]", "pkg"};
      get_command_packages$17 = Py.newCode(1, var2, var1, "get_command_packages", 782, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "klass", "pkgname", "module_name", "klass_name", "module"};
      get_command_class$18 = Py.newCode(2, var2, var1, "get_command_class", 794, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "create", "cmd_obj", "klass", "options"};
      get_command_obj$19 = Py.newCode(3, var2, var1, "get_command_obj", 833, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command_obj", "option_dict", "command_name", "option", "source", "value", "bool_opts", "neg_opt", "is_string", "msg"};
      _set_command_options$20 = Py.newCode(3, var2, var1, "_set_command_options", 860, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "reinit_subcommands", "Command", "command_name", "sub"};
      reinitialize_command$21 = Py.newCode(3, var2, var1, "reinitialize_command", 903, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "level"};
      announce$22 = Py.newCode(3, var2, var1, "announce", 944, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      run_commands$23 = Py.newCode(1, var2, var1, "run_commands", 947, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "cmd_obj"};
      run_command$24 = Py.newCode(2, var2, var1, "run_command", 957, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_pure_modules$25 = Py.newCode(1, var2, var1, "has_pure_modules", 978, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_ext_modules$26 = Py.newCode(1, var2, var1, "has_ext_modules", 981, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_c_libraries$27 = Py.newCode(1, var2, var1, "has_c_libraries", 984, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_modules$28 = Py.newCode(1, var2, var1, "has_modules", 987, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_headers$29 = Py.newCode(1, var2, var1, "has_headers", 990, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_scripts$30 = Py.newCode(1, var2, var1, "has_scripts", 993, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_data_files$31 = Py.newCode(1, var2, var1, "has_data_files", 996, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_pure$32 = Py.newCode(1, var2, var1, "is_pure", 999, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DistributionMetadata$33 = Py.newCode(0, var2, var1, "DistributionMetadata", 1011, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path"};
      __init__$34 = Py.newCode(2, var2, var1, "__init__", 1026, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "_read_field", "_read_list", "metadata_version", "msg"};
      String[] var10001 = var2;
      dist$py var10007 = self;
      var2 = new String[]{"msg"};
      read_pkg_file$35 = Py.newCode(2, var10001, var1, "read_pkg_file", 1049, false, false, var10007, 35, var2, (String[])null, 1, 4097);
      var2 = new String[]{"name", "value"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"msg"};
      _read_field$36 = Py.newCode(1, var10001, var1, "_read_field", 1053, false, false, var10007, 36, (String[])null, var2, 0, 4097);
      var2 = new String[]{"name", "values"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"msg"};
      _read_list$37 = Py.newCode(1, var10001, var1, "_read_list", 1059, false, false, var10007, 37, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "base_dir", "pkg_info"};
      write_pkg_info$38 = Py.newCode(2, var2, var1, "write_pkg_info", 1101, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "version", "long_desc", "keywords"};
      write_pkg_file$39 = Py.newCode(2, var2, var1, "write_pkg_file", 1110, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "name", "value"};
      _write_field$40 = Py.newCode(4, var2, var1, "_write_field", 1144, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "name", "values", "value"};
      _write_list$41 = Py.newCode(4, var2, var1, "_write_list", 1147, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _encode_field$42 = Py.newCode(2, var2, var1, "_encode_field", 1151, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_name$43 = Py.newCode(1, var2, var1, "get_name", 1160, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_version$44 = Py.newCode(1, var2, var1, "get_version", 1163, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_fullname$45 = Py.newCode(1, var2, var1, "get_fullname", 1166, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_author$46 = Py.newCode(1, var2, var1, "get_author", 1169, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_author_email$47 = Py.newCode(1, var2, var1, "get_author_email", 1172, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_maintainer$48 = Py.newCode(1, var2, var1, "get_maintainer", 1175, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_maintainer_email$49 = Py.newCode(1, var2, var1, "get_maintainer_email", 1178, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_contact$50 = Py.newCode(1, var2, var1, "get_contact", 1181, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_contact_email$51 = Py.newCode(1, var2, var1, "get_contact_email", 1185, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_url$52 = Py.newCode(1, var2, var1, "get_url", 1188, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_license$53 = Py.newCode(1, var2, var1, "get_license", 1191, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_description$54 = Py.newCode(1, var2, var1, "get_description", 1195, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_long_description$55 = Py.newCode(1, var2, var1, "get_long_description", 1198, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_keywords$56 = Py.newCode(1, var2, var1, "get_keywords", 1201, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_platforms$57 = Py.newCode(1, var2, var1, "get_platforms", 1204, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_classifiers$58 = Py.newCode(1, var2, var1, "get_classifiers", 1207, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_download_url$59 = Py.newCode(1, var2, var1, "get_download_url", 1210, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_requires$60 = Py.newCode(1, var2, var1, "get_requires", 1214, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "distutils", "v"};
      set_requires$61 = Py.newCode(2, var2, var1, "set_requires", 1217, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_provides$62 = Py.newCode(1, var2, var1, "get_provides", 1223, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "_[1227_17]", "v", "distutils"};
      set_provides$63 = Py.newCode(2, var2, var1, "set_provides", 1226, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_obsoletes$64 = Py.newCode(1, var2, var1, "get_obsoletes", 1233, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "distutils", "v"};
      set_obsoletes$65 = Py.newCode(2, var2, var1, "set_obsoletes", 1236, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"options", "new_options", "help_tuple"};
      fix_help_options$66 = Py.newCode(1, var2, var1, "fix_help_options", 1242, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dist$py("distutils/dist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Distribution$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.get_option_dict$4(var2, var3);
         case 5:
            return this.dump_option_dicts$5(var2, var3);
         case 6:
            return this.find_config_files$6(var2, var3);
         case 7:
            return this.parse_config_files$7(var2, var3);
         case 8:
            return this.parse_command_line$8(var2, var3);
         case 9:
            return this._get_toplevel_options$9(var2, var3);
         case 10:
            return this._parse_command_opts$10(var2, var3);
         case 11:
            return this.finalize_options$11(var2, var3);
         case 12:
            return this._show_help$12(var2, var3);
         case 13:
            return this.handle_display_options$13(var2, var3);
         case 14:
            return this.print_command_list$14(var2, var3);
         case 15:
            return this.print_commands$15(var2, var3);
         case 16:
            return this.get_command_list$16(var2, var3);
         case 17:
            return this.get_command_packages$17(var2, var3);
         case 18:
            return this.get_command_class$18(var2, var3);
         case 19:
            return this.get_command_obj$19(var2, var3);
         case 20:
            return this._set_command_options$20(var2, var3);
         case 21:
            return this.reinitialize_command$21(var2, var3);
         case 22:
            return this.announce$22(var2, var3);
         case 23:
            return this.run_commands$23(var2, var3);
         case 24:
            return this.run_command$24(var2, var3);
         case 25:
            return this.has_pure_modules$25(var2, var3);
         case 26:
            return this.has_ext_modules$26(var2, var3);
         case 27:
            return this.has_c_libraries$27(var2, var3);
         case 28:
            return this.has_modules$28(var2, var3);
         case 29:
            return this.has_headers$29(var2, var3);
         case 30:
            return this.has_scripts$30(var2, var3);
         case 31:
            return this.has_data_files$31(var2, var3);
         case 32:
            return this.is_pure$32(var2, var3);
         case 33:
            return this.DistributionMetadata$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.read_pkg_file$35(var2, var3);
         case 36:
            return this._read_field$36(var2, var3);
         case 37:
            return this._read_list$37(var2, var3);
         case 38:
            return this.write_pkg_info$38(var2, var3);
         case 39:
            return this.write_pkg_file$39(var2, var3);
         case 40:
            return this._write_field$40(var2, var3);
         case 41:
            return this._write_list$41(var2, var3);
         case 42:
            return this._encode_field$42(var2, var3);
         case 43:
            return this.get_name$43(var2, var3);
         case 44:
            return this.get_version$44(var2, var3);
         case 45:
            return this.get_fullname$45(var2, var3);
         case 46:
            return this.get_author$46(var2, var3);
         case 47:
            return this.get_author_email$47(var2, var3);
         case 48:
            return this.get_maintainer$48(var2, var3);
         case 49:
            return this.get_maintainer_email$49(var2, var3);
         case 50:
            return this.get_contact$50(var2, var3);
         case 51:
            return this.get_contact_email$51(var2, var3);
         case 52:
            return this.get_url$52(var2, var3);
         case 53:
            return this.get_license$53(var2, var3);
         case 54:
            return this.get_description$54(var2, var3);
         case 55:
            return this.get_long_description$55(var2, var3);
         case 56:
            return this.get_keywords$56(var2, var3);
         case 57:
            return this.get_platforms$57(var2, var3);
         case 58:
            return this.get_classifiers$58(var2, var3);
         case 59:
            return this.get_download_url$59(var2, var3);
         case 60:
            return this.get_requires$60(var2, var3);
         case 61:
            return this.set_requires$61(var2, var3);
         case 62:
            return this.get_provides$62(var2, var3);
         case 63:
            return this.set_provides$63(var2, var3);
         case 64:
            return this.get_obsoletes$64(var2, var3);
         case 65:
            return this.set_obsoletes$65(var2, var3);
         case 66:
            return this.fix_help_options$66(var2, var3);
         default:
            return null;
      }
   }
}
