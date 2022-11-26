package distutils.command;

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
@MTime(1498849384000L)
@Filename("distutils/command/sdist.py")
public class sdist$py extends PyFunctionTable implements PyRunnable {
   static sdist$py self;
   static final PyCode f$0;
   static final PyCode show_formats$1;
   static final PyCode sdist$2;
   static final PyCode checking_metadata$3;
   static final PyCode initialize_options$4;
   static final PyCode finalize_options$5;
   static final PyCode run$6;
   static final PyCode check_metadata$7;
   static final PyCode get_file_list$8;
   static final PyCode add_defaults$9;
   static final PyCode read_template$10;
   static final PyCode prune_file_list$11;
   static final PyCode write_manifest$12;
   static final PyCode _manifest_is_not_generated$13;
   static final PyCode read_manifest$14;
   static final PyCode make_release_tree$15;
   static final PyCode make_distribution$16;
   static final PyCode get_archive_files$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.sdist\n\nImplements the Distutils 'sdist' command (create a source distribution)."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.sdist\n\nImplements the Distutils 'sdist' command (create a source distribution).");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(8);
      var5 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var5);
      var3 = null;
      var1.setline(9);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"glob"};
      PyObject[] var7 = imp.importFrom("glob", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("glob", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"warn"};
      var7 = imp.importFrom("warnings", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"dir_util", "dep_util", "file_util", "archive_util"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("dir_util", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("dep_util", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("file_util", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("archive_util", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"TextFile"};
      var7 = imp.importFrom("distutils.text_file", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("TextFile", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"DistutilsPlatformError", "DistutilsOptionError", "DistutilsTemplateError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("DistutilsTemplateError", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"FileList"};
      var7 = imp.importFrom("distutils.filelist", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("FileList", var4);
      var4 = null;
      var1.setline(19);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"convert_path"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var1.setline(22);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, show_formats$1, PyString.fromInterned("Print all possible values for the 'formats' option (used by\n    the \"--help-formats\" command-line option).\n    "));
      var1.setlocal("show_formats", var8);
      var3 = null;
      var1.setline(36);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("sdist", var7, sdist$2);
      var1.setlocal("sdist", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject show_formats$1(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyString.fromInterned("Print all possible values for the 'formats' option (used by\n    the \"--help-formats\" command-line option).\n    ");
      var1.setline(26);
      String[] var3 = new String[]{"FancyGetopt"};
      PyObject[] var5 = imp.importFrom("distutils.fancy_getopt", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(27);
      var3 = new String[]{"ARCHIVE_FORMATS"};
      var5 = imp.importFrom("distutils.archive_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(28);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(29);
      PyObject var7 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(29);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(32);
            var1.getlocal(2).__getattr__("sort").__call__(var2);
            var1.setline(33);
            var1.getlocal(0).__call__(var2, var1.getlocal(2)).__getattr__("print_help").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("List of available source distribution formats:"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(30);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("formats=")._add(var1.getlocal(3)), var1.getglobal("None"), var1.getlocal(1).__getitem__(var1.getlocal(3)).__getitem__(Py.newInteger(2))})));
      }
   }

   public PyObject sdist$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(38);
      PyString var3 = PyString.fromInterned("create a source distribution (tarball, zip file, etc.)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(40);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, checking_metadata$3, PyString.fromInterned("Callable used for the check sub-command.\n\n        Placed here so user_options can view it"));
      var1.setlocal("checking_metadata", var5);
      var3 = null;
      var1.setline(46);
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("template="), PyString.fromInterned("t"), PyString.fromInterned("name of manifest template file [default: MANIFEST.in]")}), new PyTuple(new PyObject[]{PyString.fromInterned("manifest="), PyString.fromInterned("m"), PyString.fromInterned("name of manifest file [default: MANIFEST]")}), new PyTuple(new PyObject[]{PyString.fromInterned("use-defaults"), var1.getname("None"), PyString.fromInterned("include the default file set in the manifest [default; disable with --no-defaults]")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-defaults"), var1.getname("None"), PyString.fromInterned("don't include the default file set")}), new PyTuple(new PyObject[]{PyString.fromInterned("prune"), var1.getname("None"), PyString.fromInterned("specifically exclude files/directories that should not be distributed (build tree, RCS/CVS dirs, etc.) [default; disable with --no-prune]")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-prune"), var1.getname("None"), PyString.fromInterned("don't automatically exclude anything")}), new PyTuple(new PyObject[]{PyString.fromInterned("manifest-only"), PyString.fromInterned("o"), PyString.fromInterned("just regenerate the manifest and then stop (implies --force-manifest)")}), new PyTuple(new PyObject[]{PyString.fromInterned("force-manifest"), PyString.fromInterned("f"), PyString.fromInterned("forcibly regenerate the manifest and carry on as usual. Deprecated: now the manifest is always regenerated.")}), new PyTuple(new PyObject[]{PyString.fromInterned("formats="), var1.getname("None"), PyString.fromInterned("formats for source distribution (comma-separated list)")}), new PyTuple(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("k"), PyString.fromInterned("keep the distribution tree around after creating ")._add(PyString.fromInterned("archive file(s)"))}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put the source distribution archive(s) in [default: dist]")}), new PyTuple(new PyObject[]{PyString.fromInterned("metadata-check"), var1.getname("None"), PyString.fromInterned("Ensure that all required elements of meta-data are supplied. Warn if any missing. [default]")}), new PyTuple(new PyObject[]{PyString.fromInterned("owner="), PyString.fromInterned("u"), PyString.fromInterned("Owner name used when creating a tar file [default: current user]")}), new PyTuple(new PyObject[]{PyString.fromInterned("group="), PyString.fromInterned("g"), PyString.fromInterned("Group name used when creating a tar file [default: current group]")})});
      var1.setlocal("user_options", var6);
      var3 = null;
      var1.setline(85);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("use-defaults"), PyString.fromInterned("prune"), PyString.fromInterned("manifest-only"), PyString.fromInterned("force-manifest"), PyString.fromInterned("keep-temp"), PyString.fromInterned("metadata-check")});
      var1.setlocal("boolean_options", var6);
      var3 = null;
      var1.setline(89);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-formats"), var1.getname("None"), PyString.fromInterned("list available distribution formats"), var1.getname("show_formats")})});
      var1.setlocal("help_options", var6);
      var3 = null;
      var1.setline(94);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("no-defaults"), PyString.fromInterned("use-defaults"), PyString.fromInterned("no-prune"), PyString.fromInterned("prune")});
      var1.setlocal("negative_opt", var7);
      var3 = null;
      var1.setline(97);
      var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("posix"), PyString.fromInterned("gztar"), PyString.fromInterned("java"), PyString.fromInterned("gztar"), PyString.fromInterned("nt"), PyString.fromInterned("zip")});
      var1.setlocal("default_format", var7);
      var3 = null;
      var1.setline(101);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("check"), var1.getname("checking_metadata")})});
      var1.setlocal("sub_commands", var6);
      var3 = null;
      var1.setline(103);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, initialize_options$4, (PyObject)null);
      var1.setlocal("initialize_options", var5);
      var3 = null;
      var1.setline(126);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, finalize_options$5, (PyObject)null);
      var1.setlocal("finalize_options", var5);
      var3 = null;
      var1.setline(149);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$6, (PyObject)null);
      var1.setlocal("run", var5);
      var3 = null;
      var1.setline(171);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_metadata$7, PyString.fromInterned("Deprecated API."));
      var1.setlocal("check_metadata", var5);
      var3 = null;
      var1.setline(179);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_file_list$8, PyString.fromInterned("Figure out the list of files to include in the source\n        distribution, and put it in 'self.filelist'.  This might involve\n        reading the manifest template (and writing the manifest), or just\n        reading the manifest, or just using the default file set -- it all\n        depends on the user's options.\n        "));
      var1.setlocal("get_file_list", var5);
      var3 = null;
      var1.setline(219);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add_defaults$9, PyString.fromInterned("Add all the default files to self.filelist:\n          - README or README.txt\n          - setup.py\n          - test/test*.py\n          - all pure Python modules mentioned in setup script\n          - all files pointed by package_data (build_py)\n          - all files defined in data_files.\n          - all files defined as scripts.\n          - all C sources listed as part of extensions or C libraries\n            in the setup script (doesn't catch C headers!)\n        Warns if (README or README.txt) or setup.py are missing; everything\n        else is optional.\n        "));
      var1.setlocal("add_defaults", var5);
      var3 = null;
      var1.setline(301);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read_template$10, PyString.fromInterned("Read and parse manifest template file named by self.template.\n\n        (usually \"MANIFEST.in\") The parsing and processing is done by\n        'self.filelist', which updates itself accordingly.\n        "));
      var1.setlocal("read_template", var5);
      var3 = null;
      var1.setline(334);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, prune_file_list$11, PyString.fromInterned("Prune off branches that might slip into the file list as created\n        by 'read_template()', but really don't belong there:\n          * the build tree (typically \"build\")\n          * the release tree itself (only an issue if we ran \"sdist\"\n            previously with --keep-temp, or it aborted)\n          * any RCS, CVS, .svn, .hg, .git, .bzr, _darcs directories\n        "));
      var1.setlocal("prune_file_list", var5);
      var3 = null;
      var1.setline(360);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write_manifest$12, PyString.fromInterned("Write the file list in 'self.filelist' (presumably as filled in\n        by 'add_defaults()' and 'read_template()') to the manifest file\n        named by 'self.manifest'.\n        "));
      var1.setlocal("write_manifest", var5);
      var3 = null;
      var1.setline(375);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _manifest_is_not_generated$13, (PyObject)null);
      var1.setlocal("_manifest_is_not_generated", var5);
      var3 = null;
      var1.setline(387);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read_manifest$14, PyString.fromInterned("Read the manifest file (named by 'self.manifest') and use it to\n        fill in 'self.filelist', the list of files to include in the source\n        distribution.\n        "));
      var1.setlocal("read_manifest", var5);
      var3 = null;
      var1.setline(402);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, make_release_tree$15, PyString.fromInterned("Create the directory tree that will become the source\n        distribution archive.  All directories implied by the filenames in\n        'files' are created under 'base_dir', and then we hard link or copy\n        (if hard linking is unavailable) those files into place.\n        Essentially, this duplicates the developer's source tree, but in a\n        directory named after the distribution, containing only the files\n        to be distributed.\n        "));
      var1.setlocal("make_release_tree", var5);
      var3 = null;
      var1.setline(444);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, make_distribution$16, PyString.fromInterned("Create the source distribution(s).  First, we create the release\n        tree with 'make_release_tree()'; then, we create all required\n        archive files (according to 'self.formats') from the release tree.\n        Finally, we clean up by blowing away the release tree (unless\n        'self.keep_temp' is true).  The list of archive files created is\n        stored so it can be retrieved later by 'get_archive_files()'.\n        "));
      var1.setlocal("make_distribution", var5);
      var3 = null;
      var1.setline(474);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_archive_files$17, PyString.fromInterned("Return the list of archive files created when the command\n        was run, or None if the command hasn't run yet.\n        "));
      var1.setlocal("get_archive_files", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject checking_metadata$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Callable used for the check sub-command.\n\n        Placed here so user_options can view it");
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("metadata_check");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject initialize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("template", var3);
      var3 = null;
      var1.setline(107);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("manifest", var3);
      var3 = null;
      var1.setline(111);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"use_defaults", var4);
      var3 = null;
      var1.setline(112);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"prune", var4);
      var3 = null;
      var1.setline(114);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"manifest_only", var4);
      var3 = null;
      var1.setline(115);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force_manifest", var4);
      var3 = null;
      var1.setline(117);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("formats", var3);
      var3 = null;
      var1.setline(118);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"keep_temp", var4);
      var3 = null;
      var1.setline(119);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("archive_files", var3);
      var3 = null;
      var1.setline(122);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"metadata_check", var4);
      var3 = null;
      var1.setline(123);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("owner", var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("group", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$5(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("manifest");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(128);
         var5 = PyString.fromInterned("MANIFEST");
         var1.getlocal(0).__setattr__((String)"manifest", var5);
         var3 = null;
      }

      var1.setline(129);
      var3 = var1.getlocal(0).__getattr__("template");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(130);
         var5 = PyString.fromInterned("MANIFEST.in");
         var1.getlocal(0).__setattr__((String)"template", var5);
         var3 = null;
      }

      var1.setline(132);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("formats"));
      var1.setline(133);
      var3 = var1.getlocal(0).__getattr__("formats");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(135);
            PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("default_format").__getitem__(var1.getglobal("os").__getattr__("name"))});
            var1.getlocal(0).__setattr__((String)"formats", var7);
            var3 = null;
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (var6.match(var1.getglobal("KeyError"))) {
               var1.setline(137);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to create source distributions ")._add(PyString.fromInterned("on platform %s")._mod(var1.getglobal("os").__getattr__("name"))));
            }

            throw var6;
         }
      }

      var1.setline(141);
      var3 = var1.getglobal("archive_util").__getattr__("check_archive_formats").__call__(var2, var1.getlocal(0).__getattr__("formats"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(142);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(143);
         throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("unknown archive format '%s'")._mod(var1.getlocal(1)));
      } else {
         var1.setline(146);
         var3 = var1.getlocal(0).__getattr__("dist_dir");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(147);
            var5 = PyString.fromInterned("dist");
            var1.getlocal(0).__setattr__((String)"dist_dir", var5);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject run$6(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.getlocal(0).__setattr__("filelist", var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(155);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(161);
            var1.getlocal(0).__getattr__("get_file_list").__call__(var2);
            var1.setline(164);
            if (var1.getlocal(0).__getattr__("manifest_only").__nonzero__()) {
               var1.setline(165);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(169);
               var1.getlocal(0).__getattr__("make_distribution").__call__(var2);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setlocal(1, var4);
         var1.setline(156);
         var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject check_metadata$7(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString.fromInterned("Deprecated API.");
      var1.setline(173);
      var1.getglobal("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distutils.command.sdist.check_metadata is deprecated,               use the check command instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"));
      var1.setline(175);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("check"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(176);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(177);
      var1.getlocal(1).__getattr__("run").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_file_list$8(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyString.fromInterned("Figure out the list of files to include in the source\n        distribution, and put it in 'self.filelist'.  This might involve\n        reading the manifest template (and writing the manifest), or just\n        reading the manifest, or just using the default file set -- it all\n        depends on the user's options.\n        ");
      var1.setline(193);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0).__getattr__("template"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(194);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_manifest_is_not_generated").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(195);
         var1.getlocal(0).__getattr__("read_manifest").__call__(var2);
         var1.setline(196);
         var1.getlocal(0).__getattr__("filelist").__getattr__("sort").__call__(var2);
         var1.setline(197);
         var1.getlocal(0).__getattr__("filelist").__getattr__("remove_duplicates").__call__(var2);
         var1.setline(198);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(200);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(201);
            var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("manifest template '%s' does not exist ")._add(PyString.fromInterned("(using default file list)"))._mod(var1.getlocal(0).__getattr__("template")));
         }

         var1.setline(204);
         var1.getlocal(0).__getattr__("filelist").__getattr__("findall").__call__(var2);
         var1.setline(206);
         if (var1.getlocal(0).__getattr__("use_defaults").__nonzero__()) {
            var1.setline(207);
            var1.getlocal(0).__getattr__("add_defaults").__call__(var2);
         }

         var1.setline(209);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(210);
            var1.getlocal(0).__getattr__("read_template").__call__(var2);
         }

         var1.setline(212);
         if (var1.getlocal(0).__getattr__("prune").__nonzero__()) {
            var1.setline(213);
            var1.getlocal(0).__getattr__("prune_file_list").__call__(var2);
         }

         var1.setline(215);
         var1.getlocal(0).__getattr__("filelist").__getattr__("sort").__call__(var2);
         var1.setline(216);
         var1.getlocal(0).__getattr__("filelist").__getattr__("remove_duplicates").__call__(var2);
         var1.setline(217);
         var1.getlocal(0).__getattr__("write_manifest").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject add_defaults$9(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      PyString.fromInterned("Add all the default files to self.filelist:\n          - README or README.txt\n          - setup.py\n          - test/test*.py\n          - all pure Python modules mentioned in setup script\n          - all files pointed by package_data (build_py)\n          - all files defined in data_files.\n          - all files defined as scripts.\n          - all C sources listed as part of extensions or C libraries\n            in the setup script (doesn't catch C headers!)\n        Warns if (README or README.txt) or setup.py are missing; everything\n        else is optional.\n        ");
      var1.setline(234);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("README"), PyString.fromInterned("README.txt")}), var1.getlocal(0).__getattr__("distribution").__getattr__("script_name")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(235);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         while(true) {
            var1.setline(235);
            PyObject var4 = var8.__iternext__();
            PyObject var5;
            PyObject var6;
            PyInteger var7;
            if (var4 == null) {
               var1.setline(254);
               var3 = new PyList(new PyObject[]{PyString.fromInterned("test/test*.py"), PyString.fromInterned("setup.cfg")});
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(255);
               var8 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(255);
                  var4 = var8.__iternext__();
                  if (var4 == null) {
                     var1.setline(263);
                     var8 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
                     var1.setlocal(8, var8);
                     var3 = null;
                     var1.setline(266);
                     if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2).__nonzero__()) {
                        var1.setline(267);
                        var1.getlocal(0).__getattr__("filelist").__getattr__("extend").__call__(var2, var1.getlocal(8).__getattr__("get_source_files").__call__(var2));
                     }

                     var1.setline(271);
                     var8 = var1.getlocal(8).__getattr__("data_files").__iter__();

                     while(true) {
                        var1.setline(271);
                        var4 = var8.__iternext__();
                        if (var4 == null) {
                           var1.setline(276);
                           if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_data_files").__call__(var2).__nonzero__()) {
                              var1.setline(277);
                              var8 = var1.getlocal(0).__getattr__("distribution").__getattr__("data_files").__iter__();

                              label73:
                              while(true) {
                                 while(true) {
                                    var1.setline(277);
                                    var4 = var8.__iternext__();
                                    if (var4 == null) {
                                       break label73;
                                    }

                                    var1.setlocal(14, var4);
                                    var1.setline(278);
                                    if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(14), var1.getglobal("str")).__nonzero__()) {
                                       var1.setline(279);
                                       var5 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(14));
                                       var1.setlocal(14, var5);
                                       var5 = null;
                                       var1.setline(280);
                                       if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                                          var1.setline(281);
                                          var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(14));
                                       }
                                    } else {
                                       var1.setline(283);
                                       var5 = var1.getlocal(14);
                                       PyObject[] var12 = Py.unpackSequence(var5, 2);
                                       PyObject var11 = var12[0];
                                       var1.setlocal(15, var11);
                                       var7 = null;
                                       var11 = var12[1];
                                       var1.setlocal(12, var11);
                                       var7 = null;
                                       var5 = null;
                                       var1.setline(284);
                                       var5 = var1.getlocal(12).__iter__();

                                       while(true) {
                                          var1.setline(284);
                                          var6 = var5.__iternext__();
                                          if (var6 == null) {
                                             break;
                                          }

                                          var1.setlocal(16, var6);
                                          var1.setline(285);
                                          var11 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(16));
                                          var1.setlocal(16, var11);
                                          var7 = null;
                                          var1.setline(286);
                                          if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(16)).__nonzero__()) {
                                             var1.setline(287);
                                             var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(16));
                                          }
                                       }
                                    }
                                 }
                              }
                           }

                           var1.setline(289);
                           if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
                              var1.setline(290);
                              var8 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_ext"));
                              var1.setlocal(17, var8);
                              var3 = null;
                              var1.setline(291);
                              var1.getlocal(0).__getattr__("filelist").__getattr__("extend").__call__(var2, var1.getlocal(17).__getattr__("get_source_files").__call__(var2));
                           }

                           var1.setline(293);
                           if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_c_libraries").__call__(var2).__nonzero__()) {
                              var1.setline(294);
                              var8 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_clib"));
                              var1.setlocal(18, var8);
                              var3 = null;
                              var1.setline(295);
                              var1.getlocal(0).__getattr__("filelist").__getattr__("extend").__call__(var2, var1.getlocal(18).__getattr__("get_source_files").__call__(var2));
                           }

                           var1.setline(297);
                           if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_scripts").__call__(var2).__nonzero__()) {
                              var1.setline(298);
                              var8 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_scripts"));
                              var1.setlocal(19, var8);
                              var3 = null;
                              var1.setline(299);
                              var1.getlocal(0).__getattr__("filelist").__getattr__("extend").__call__(var2, var1.getlocal(19).__getattr__("get_source_files").__call__(var2));
                           }

                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        PyObject[] var10 = Py.unpackSequence(var4, 4);
                        var6 = var10[0];
                        var1.setlocal(9, var6);
                        var6 = null;
                        var6 = var10[1];
                        var1.setlocal(10, var6);
                        var6 = null;
                        var6 = var10[2];
                        var1.setlocal(11, var6);
                        var6 = null;
                        var6 = var10[3];
                        var1.setlocal(12, var6);
                        var6 = null;
                        var1.setline(272);
                        var5 = var1.getlocal(12).__iter__();

                        while(true) {
                           var1.setline(272);
                           var6 = var5.__iternext__();
                           if (var6 == null) {
                              break;
                           }

                           var1.setlocal(13, var6);
                           var1.setline(273);
                           var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(10), var1.getlocal(13)));
                        }
                     }
                  }

                  var1.setlocal(6, var4);
                  var1.setline(256);
                  var5 = var1.getglobal("filter").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("isfile"), var1.getglobal("glob").__call__(var2, var1.getlocal(6)));
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(257);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(258);
                     var1.getlocal(0).__getattr__("filelist").__getattr__("extend").__call__(var2, var1.getlocal(7));
                  }
               }
            }

            var1.setlocal(2, var4);
            var1.setline(236);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__nonzero__()) {
               var1.setline(237);
               var5 = var1.getlocal(2);
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(238);
               PyInteger var9 = Py.newInteger(0);
               var1.setlocal(4, var9);
               var5 = null;
               var1.setline(239);
               var5 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(239);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(2, var6);
                  var1.setline(240);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                     var1.setline(241);
                     var7 = Py.newInteger(1);
                     var1.setlocal(4, var7);
                     var7 = null;
                     var1.setline(242);
                     var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(2));
                     break;
                  }
               }

               var1.setline(245);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(246);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("standard file not found: should have one of ")._add(var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned(", "))));
               }
            } else {
               var1.setline(249);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                  var1.setline(250);
                  var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(2));
               } else {
                  var1.setline(252);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("standard file '%s' not found")._mod(var1.getlocal(2)));
               }
            }
         }
      }
   }

   public PyObject read_template$10(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Read and parse manifest template file named by self.template.\n\n        (usually \"MANIFEST.in\") The parsing and processing is done by\n        'self.filelist', which updates itself accordingly.\n        ");
      var1.setline(307);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("reading manifest template '%s'"), (PyObject)var1.getlocal(0).__getattr__("template"));
      var1.setline(308);
      PyObject var10000 = var1.getglobal("TextFile");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("template"), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1)};
      String[] var4 = new String[]{"strip_comments", "skip_blanks", "join_lines", "lstrip_ws", "rstrip_ws", "collapse_join"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(1, var8);
      var3 = null;
      var3 = null;

      try {
         while(true) {
            var1.setline(317);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(318);
            PyObject var9 = var1.getlocal(1).__getattr__("readline").__call__(var2);
            var1.setlocal(2, var9);
            var4 = null;
            var1.setline(319);
            var9 = var1.getlocal(2);
            var10000 = var9._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            try {
               var1.setline(323);
               var1.getlocal(0).__getattr__("filelist").__getattr__("process_template_line").__call__(var2, var1.getlocal(2));
            } catch (Throwable var6) {
               PyException var10 = Py.setException(var6, var1);
               if (!var10.match(new PyTuple(new PyObject[]{var1.getglobal("DistutilsTemplateError"), var1.getglobal("ValueError")}))) {
                  throw var10;
               }

               PyObject var5 = var10.value;
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(328);
               var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("%s, line %d: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(1).__getattr__("current_line"), var1.getlocal(3)})));
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(332);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(332);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject prune_file_list$11(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyString.fromInterned("Prune off branches that might slip into the file list as created\n        by 'read_template()', but really don't belong there:\n          * the build tree (typically \"build\")\n          * the release tree itself (only an issue if we ran \"sdist\"\n            previously with --keep-temp, or it aborted)\n          * any RCS, CVS, .svn, .hg, .git, .bzr, _darcs directories\n        ");
      var1.setline(342);
      PyObject var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(343);
      var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(345);
      PyObject var10000 = var1.getlocal(0).__getattr__("filelist").__getattr__("exclude_pattern");
      PyObject[] var5 = new PyObject[]{var1.getglobal("None"), var1.getlocal(1).__getattr__("build_base")};
      String[] var4 = new String[]{"prefix"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(346);
      var10000 = var1.getlocal(0).__getattr__("filelist").__getattr__("exclude_pattern");
      var5 = new PyObject[]{var1.getglobal("None"), var1.getlocal(2)};
      var4 = new String[]{"prefix"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(350);
      var3 = var1.getglobal("sys").__getattr__("platform");
      var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      PyString var6;
      if (var10000.__nonzero__()) {
         var1.setline(351);
         var6 = PyString.fromInterned("/|\\\\");
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(353);
         var6 = PyString.fromInterned("/");
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(355);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("RCS"), PyString.fromInterned("CVS"), PyString.fromInterned("\\.svn"), PyString.fromInterned("\\.hg"), PyString.fromInterned("\\.git"), PyString.fromInterned("\\.bzr"), PyString.fromInterned("_darcs")});
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(357);
      var3 = PyString.fromInterned("(^|%s)(%s)(%s).*")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getlocal(4)), var1.getlocal(3)}));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(358);
      var10000 = var1.getlocal(0).__getattr__("filelist").__getattr__("exclude_pattern");
      var5 = new PyObject[]{var1.getlocal(5), Py.newInteger(1)};
      var4 = new String[]{"is_regex"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_manifest$12(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Write the file list in 'self.filelist' (presumably as filled in\n        by 'add_defaults()' and 'read_template()') to the manifest file\n        named by 'self.manifest'.\n        ");
      var1.setline(365);
      if (var1.getlocal(0).__getattr__("_manifest_is_not_generated").__call__(var2).__nonzero__()) {
         var1.setline(366);
         var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("not writing to manually maintained manifest file '%s'")._mod(var1.getlocal(0).__getattr__("manifest")));
         var1.setline(368);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(370);
         PyObject var3 = var1.getlocal(0).__getattr__("filelist").__getattr__("files").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(371);
         var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("# file GENERATED by distutils, do NOT edit"));
         var1.setline(372);
         var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("file_util").__getattr__("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("manifest"), var1.getlocal(1)})), (PyObject)PyString.fromInterned("writing manifest file '%s'")._mod(var1.getlocal(0).__getattr__("manifest")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _manifest_is_not_generated$13(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0).__getattr__("manifest")).__not__().__nonzero__()) {
         var1.setline(378);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(380);
         PyObject var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("manifest"), (PyObject)PyString.fromInterned("rU"));
         var1.setlocal(1, var4);
         var4 = null;
         var4 = null;

         try {
            var1.setline(382);
            PyObject var5 = var1.getlocal(1).__getattr__("readline").__call__(var2);
            var1.setlocal(2, var5);
            var5 = null;
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(384);
            var1.getlocal(1).__getattr__("close").__call__(var2);
            throw (Throwable)var6;
         }

         var1.setline(384);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(385);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._ne(PyString.fromInterned("# file GENERATED by distutils, do NOT edit\n"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_manifest$14(PyFrame var1, ThreadState var2) {
      var1.setline(391);
      PyString.fromInterned("Read the manifest file (named by 'self.manifest') and use it to\n        fill in 'self.filelist', the list of files to include in the source\n        distribution.\n        ");
      var1.setline(392);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("reading manifest file '%s'"), (PyObject)var1.getlocal(0).__getattr__("manifest"));
      var1.setline(393);
      PyObject var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("manifest"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(394);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(394);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(400);
            var1.getlocal(1).__getattr__("close").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(396);
         PyObject var5 = var1.getlocal(2).__getattr__("strip").__call__(var2);
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(397);
         PyObject var10000 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(399);
            var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject make_release_tree$15(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("Create the directory tree that will become the source\n        distribution archive.  All directories implied by the filenames in\n        'files' are created under 'base_dir', and then we hard link or copy\n        (if hard linking is unavailable) those files into place.\n        Essentially, this duplicates the developer's source tree, but in a\n        directory named after the distribution, containing only the files\n        to be distributed.\n        ");
      var1.setline(414);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(1));
      var1.setline(415);
      PyObject var10000 = var1.getglobal("dir_util").__getattr__("create_tree");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(424);
      PyObject var8;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("link")).__nonzero__()) {
         var1.setline(425);
         PyString var7 = PyString.fromInterned("hard");
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(426);
         var8 = PyString.fromInterned("making hard links in %s...")._mod(var1.getlocal(1));
         var1.setlocal(4, var8);
         var3 = null;
      } else {
         var1.setline(428);
         var8 = var1.getglobal("None");
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(429);
         var8 = PyString.fromInterned("copying files to %s...")._mod(var1.getlocal(1));
         var1.setlocal(4, var8);
         var3 = null;
      }

      var1.setline(431);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(432);
         var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no files to distribute -- empty manifest?"));
      } else {
         var1.setline(434);
         var1.getglobal("log").__getattr__("info").__call__(var2, var1.getlocal(4));
      }

      var1.setline(435);
      var8 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(435);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.setline(442);
            var1.getlocal(0).__getattr__("distribution").__getattr__("metadata").__getattr__("write_pkg_info").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var9);
         var1.setline(436);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
            var1.setline(437);
            var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("'%s' not a regular file -- skipping")._mod(var1.getlocal(5)));
         } else {
            var1.setline(439);
            PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(440);
            var10000 = var1.getlocal(0).__getattr__("copy_file");
            PyObject[] var10 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(3)};
            String[] var6 = new String[]{"link"};
            var10000.__call__(var2, var10, var6);
            var5 = null;
         }
      }
   }

   public PyObject make_distribution$16(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyString.fromInterned("Create the source distribution(s).  First, we create the release\n        tree with 'make_release_tree()'; then, we create all required\n        archive files (according to 'self.formats') from the release tree.\n        Finally, we clean up by blowing away the release tree (unless\n        'self.keep_temp' is true).  The list of archive files created is\n        stored so it can be retrieved later by 'get_archive_files()'.\n        ");
      var1.setline(454);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(455);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(457);
      var1.getlocal(0).__getattr__("make_release_tree").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("filelist").__getattr__("files"));
      var1.setline(458);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(460);
      PyString var9 = PyString.fromInterned("tar");
      PyObject var10000 = var9._in(var1.getlocal(0).__getattr__("formats"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(461);
         var1.getlocal(0).__getattr__("formats").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("formats").__getattr__("pop").__call__(var2, var1.getlocal(0).__getattr__("formats").__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tar"))));
      }

      var1.setline(463);
      var3 = var1.getlocal(0).__getattr__("formats").__iter__();

      while(true) {
         var1.setline(463);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(469);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("archive_files", var3);
            var3 = null;
            var1.setline(471);
            if (var1.getlocal(0).__getattr__("keep_temp").__not__().__nonzero__()) {
               var1.setline(472);
               var10000 = var1.getglobal("dir_util").__getattr__("remove_tree");
               PyObject[] var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dry_run")};
               String[] var8 = new String[]{"dry_run"};
               var10000.__call__(var2, var11, var8);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(464);
         var10000 = var1.getlocal(0).__getattr__("make_archive");
         PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(1), var1.getlocal(0).__getattr__("owner"), var1.getlocal(0).__getattr__("group")};
         String[] var6 = new String[]{"base_dir", "owner", "group"};
         var10000 = var10000.__call__(var2, var5, var6);
         var5 = null;
         PyObject var10 = var10000;
         var1.setlocal(5, var10);
         var5 = null;
         var1.setline(466);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         var1.setline(467);
         var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("sdist"), PyString.fromInterned(""), var1.getlocal(5)})));
      }
   }

   public PyObject get_archive_files$17(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyString.fromInterned("Return the list of archive files created when the command\n        was run, or None if the command hasn't run yet.\n        ");
      var1.setline(478);
      PyObject var3 = var1.getlocal(0).__getattr__("archive_files");
      var1.f_lasti = -1;
      return var3;
   }

   public sdist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"FancyGetopt", "ARCHIVE_FORMATS", "formats", "format"};
      show_formats$1 = Py.newCode(0, var2, var1, "show_formats", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      sdist$2 = Py.newCode(0, var2, var1, "sdist", 36, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      checking_metadata$3 = Py.newCode(1, var2, var1, "checking_metadata", 40, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$4 = Py.newCode(1, var2, var1, "initialize_options", 103, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bad_format"};
      finalize_options$5 = Py.newCode(1, var2, var1, "finalize_options", 126, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd_name"};
      run$6 = Py.newCode(1, var2, var1, "run", 149, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "check"};
      check_metadata$7 = Py.newCode(1, var2, var1, "check_metadata", 171, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "template_exists"};
      get_file_list$8 = Py.newCode(1, var2, var1, "get_file_list", 179, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "standards", "fn", "alts", "got_it", "optional", "pattern", "files", "build_py", "pkg", "src_dir", "build_dir", "filenames", "filename", "item", "dirname", "f", "build_ext", "build_clib", "build_scripts"};
      add_defaults$9 = Py.newCode(1, var2, var1, "add_defaults", 219, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "template", "line", "msg"};
      read_template$10 = Py.newCode(1, var2, var1, "read_template", 301, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "build", "base_dir", "seps", "vcs_dirs", "vcs_ptrn"};
      prune_file_list$11 = Py.newCode(1, var2, var1, "prune_file_list", 334, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content"};
      write_manifest$12 = Py.newCode(1, var2, var1, "write_manifest", 360, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "first_line"};
      _manifest_is_not_generated$13 = Py.newCode(1, var2, var1, "_manifest_is_not_generated", 375, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "manifest", "line"};
      read_manifest$14 = Py.newCode(1, var2, var1, "read_manifest", 387, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "base_dir", "files", "link", "msg", "file", "dest"};
      make_release_tree$15 = Py.newCode(3, var2, var1, "make_release_tree", 402, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "base_dir", "base_name", "archive_files", "fmt", "file"};
      make_distribution$16 = Py.newCode(1, var2, var1, "make_distribution", 444, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_archive_files$17 = Py.newCode(1, var2, var1, "get_archive_files", 474, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sdist$py("distutils/command/sdist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sdist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.show_formats$1(var2, var3);
         case 2:
            return this.sdist$2(var2, var3);
         case 3:
            return this.checking_metadata$3(var2, var3);
         case 4:
            return this.initialize_options$4(var2, var3);
         case 5:
            return this.finalize_options$5(var2, var3);
         case 6:
            return this.run$6(var2, var3);
         case 7:
            return this.check_metadata$7(var2, var3);
         case 8:
            return this.get_file_list$8(var2, var3);
         case 9:
            return this.add_defaults$9(var2, var3);
         case 10:
            return this.read_template$10(var2, var3);
         case 11:
            return this.prune_file_list$11(var2, var3);
         case 12:
            return this.write_manifest$12(var2, var3);
         case 13:
            return this._manifest_is_not_generated$13(var2, var3);
         case 14:
            return this.read_manifest$14(var2, var3);
         case 15:
            return this.make_release_tree$15(var2, var3);
         case 16:
            return this.make_distribution$16(var2, var3);
         case 17:
            return this.get_archive_files$17(var2, var3);
         default:
            return null;
      }
   }
}
