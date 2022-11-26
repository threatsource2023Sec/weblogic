package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_sdist.py")
public class test_sdist$py extends PyFunctionTable implements PyRunnable {
   static test_sdist$py self;
   static final PyCode f$0;
   static final PyCode SDistTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode get_cmd$4;
   static final PyCode test_prune_file_list$5;
   static final PyCode test_make_distribution$6;
   static final PyCode test_unicode_metadata_tgz$7;
   static final PyCode test_add_defaults$8;
   static final PyCode test_metadata_check_option$9;
   static final PyCode test_check_metadata_deprecated$10;
   static final PyCode test_show_formats$11;
   static final PyCode test_finalize_options$12;
   static final PyCode test_make_distribution_owner_group$13;
   static final PyCode _check_template$14;
   static final PyCode test_invalid_template_unknown_command$15;
   static final PyCode test_invalid_template_wrong_arguments$16;
   static final PyCode test_invalid_template_wrong_path$17;
   static final PyCode test_get_file_list$18;
   static final PyCode test_manifest_marker$19;
   static final PyCode test_manifest_comments$20;
   static final PyCode test_manual_manifest$21;
   static final PyCode test_suite$22;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.sdist."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.sdist.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("tarfile", var1, -1);
      var1.setlocal("tarfile", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("zipfile", var1, -1);
      var1.setlocal("zipfile", var3);
      var3 = null;
      var1.setline(7);
      String[] var7 = new String[]{"join"};
      PyObject[] var8 = imp.importFrom("os.path", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("join", var4);
      var4 = null;
      var1.setline(8);
      var7 = new String[]{"dedent"};
      var8 = imp.importFrom("textwrap", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("dedent", var4);
      var4 = null;
      var1.setline(9);
      var7 = new String[]{"captured_stdout", "check_warnings", "run_unittest"};
      var8 = imp.importFrom("test.test_support", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("check_warnings", var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal("run_unittest", var4);
      var4 = null;

      PyException var9;
      try {
         var1.setline(14);
         var3 = imp.importOne("zlib", var1, -1);
         var1.setlocal("zlib", var3);
         var3 = null;
      } catch (Throwable var6) {
         var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(16);
         var4 = var1.getname("None");
         var1.setlocal("zlib", var4);
         var4 = null;
      }

      try {
         var1.setline(19);
         var3 = imp.importOne("grp", var1, -1);
         var1.setlocal("grp", var3);
         var3 = null;
         var1.setline(20);
         var3 = imp.importOne("pwd", var1, -1);
         var1.setlocal("pwd", var3);
         var3 = null;
         var1.setline(21);
         var3 = var1.getname("True");
         var1.setlocal("UID_GID_SUPPORT", var3);
         var3 = null;
      } catch (Throwable var5) {
         var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(23);
         var4 = var1.getname("False");
         var1.setlocal("UID_GID_SUPPORT", var4);
         var4 = null;
      }

      var1.setline(26);
      var7 = new String[]{"sdist", "show_formats"};
      var8 = imp.importFrom("distutils.command.sdist", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("sdist", var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal("show_formats", var4);
      var4 = null;
      var1.setline(27);
      var7 = new String[]{"Distribution"};
      var8 = imp.importFrom("distutils.core", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(28);
      var7 = new String[]{"PyPIRCCommandTestCase"};
      var8 = imp.importFrom("distutils.tests.test_config", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("PyPIRCCommandTestCase", var4);
      var4 = null;
      var1.setline(29);
      var7 = new String[]{"DistutilsOptionError"};
      var8 = imp.importFrom("distutils.errors", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(30);
      var7 = new String[]{"find_executable"};
      var8 = imp.importFrom("distutils.spawn", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("find_executable", var4);
      var4 = null;
      var1.setline(31);
      var7 = new String[]{"WARN"};
      var8 = imp.importFrom("distutils.log", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("WARN", var4);
      var4 = null;
      var1.setline(32);
      var7 = new String[]{"FileList"};
      var8 = imp.importFrom("distutils.filelist", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("FileList", var4);
      var4 = null;
      var1.setline(33);
      var7 = new String[]{"ARCHIVE_FORMATS"};
      var8 = imp.importFrom("distutils.archive_util", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("ARCHIVE_FORMATS", var4);
      var4 = null;
      var1.setline(35);
      PyString var10 = PyString.fromInterned("\nfrom distutils.core import setup\nimport somecode\n\nsetup(name='fake')\n");
      var1.setlocal("SETUP_PY", var10);
      var3 = null;
      var1.setline(42);
      var10 = PyString.fromInterned("# file GENERATED by distutils, do NOT edit\nREADME\nbuildout.cfg\ninroot.txt\nsetup.py\ndata%(sep)sdata.dt\nscripts%(sep)sscript.py\nsome%(sep)sfile.txt\nsome%(sep)sother_file.txt\nsomecode%(sep)s__init__.py\nsomecode%(sep)sdoc.dat\nsomecode%(sep)sdoc.txt\n");
      var1.setlocal("MANIFEST", var10);
      var3 = null;
      var1.setline(57);
      var8 = new PyObject[]{var1.getname("PyPIRCCommandTestCase")};
      var4 = Py.makeClass("SDistTestCase", var8, SDistTestCase$1);
      var1.setlocal("SDistTestCase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(508);
      var8 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var8, test_suite$22, (PyObject)null);
      var1.setlocal("test_suite", var11);
      var3 = null;
      var1.setline(511);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(512);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SDistTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var5);
      var3 = null;
      var1.setline(78);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_cmd$4, PyString.fromInterned("Returns a cmd"));
      var1.setlocal("get_cmd", var5);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_prune_file_list$5, (PyObject)null);
      PyObject var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_prune_file_list", var6);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_make_distribution$6, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_make_distribution", var6);
      var3 = null;
      var1.setline(170);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_unicode_metadata_tgz$7, PyString.fromInterned("\n        Unicode name or version should not break building to tar.gz format.\n        Reference issue #11638.\n        "));
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_unicode_metadata_tgz", var6);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_add_defaults$8, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_add_defaults", var6);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_metadata_check_option$9, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_metadata_check_option", var6);
      var3 = null;
      var1.setline(288);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_check_metadata_deprecated$10, (PyObject)null);
      var1.setlocal("test_check_metadata_deprecated", var5);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_show_formats$11, (PyObject)null);
      var1.setlocal("test_show_formats", var5);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_finalize_options$12, (PyObject)null);
      var1.setlocal("test_finalize_options", var5);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_make_distribution_owner_group$13, (PyObject)null);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib"));
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("UID_GID_SUPPORT"), (PyObject)PyString.fromInterned("Requires grp and pwd support")).__call__((ThreadState)var2, (PyObject)var5);
      var6 = var10000.__call__(var2, var6);
      var1.setlocal("test_make_distribution_owner_group", var6);
      var3 = null;
      var1.setline(379);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _check_template$14, (PyObject)null);
      var1.setlocal("_check_template", var5);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_invalid_template_unknown_command$15, (PyObject)null);
      var1.setlocal("test_invalid_template_unknown_command", var5);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_invalid_template_wrong_arguments$16, (PyObject)null);
      var1.setlocal("test_invalid_template_wrong_arguments", var5);
      var3 = null;
      var1.setline(396);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_invalid_template_wrong_path$17, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipIf");
      PyObject var4 = var1.getname("os").__getattr__("name");
      PyObject var10002 = var4._ne(PyString.fromInterned("nt"));
      var4 = null;
      var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("test relevant for Windows only")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_invalid_template_wrong_path", var6);
      var3 = null;
      var1.setline(402);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_get_file_list$18, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_get_file_list", var6);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_manifest_marker$19, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_manifest_marker", var6);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_manifest_comments$20, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_manifest_comments", var6);
      var3 = null;
      var1.setline(478);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_manual_manifest$21, (PyObject)null);
      var6 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_manual_manifest", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      var1.getglobal("super").__call__(var2, var1.getglobal("SDistTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(64);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.getlocal(0).__setattr__("old_path", var3);
      var3 = null;
      var1.setline(65);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("somecode")));
      var1.setline(66);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist")));
      var1.setline(68);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("README")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(69);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned("__init__.py")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(70);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("setup.py")})), (PyObject)var1.getglobal("SETUP_PY"));
      var1.setline(71);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("tmp_dir"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("old_path"));
      var1.setline(76);
      var1.getglobal("super").__call__(var2, var1.getglobal("SDistTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_cmd$4(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyString.fromInterned("Returns a cmd");
      var1.setline(80);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("fake"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx")});
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(84);
      var3 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(85);
      PyString var5 = PyString.fromInterned("setup.py");
      var1.getlocal(2).__setattr__((String)"script_name", var5);
      var3 = null;
      var1.setline(86);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("somecode")});
      var1.getlocal(2).__setattr__((String)"packages", var6);
      var3 = null;
      var1.setline(87);
      var3 = var1.getglobal("True");
      var1.getlocal(2).__setattr__("include_package_data", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getglobal("sdist").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(89);
      var5 = PyString.fromInterned("dist");
      var1.getlocal(3).__setattr__((String)"dist_dir", var5);
      var3 = null;
      var1.setline(90);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject test_prune_file_list$5(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("somecode"), (PyObject)PyString.fromInterned(".svn")));
      var1.setline(99);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned(".svn"), PyString.fromInterned("ok.py")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(101);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("somecode"), (PyObject)PyString.fromInterned(".hg")));
      var1.setline(102);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned(".hg"), PyString.fromInterned("ok")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(105);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("somecode"), (PyObject)PyString.fromInterned(".git")));
      var1.setline(106);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned(".git"), PyString.fromInterned("ok")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(109);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned(".nfs0001")})), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(112);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(116);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("zip")});
      var1.getlocal(2).__setattr__((String)"formats", var7);
      var3 = null;
      var1.setline(118);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(119);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(122);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(124);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0.zip")})));
      var1.setline(126);
      var3 = var1.getglobal("zipfile").__getattr__("ZipFile").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("fake-1.0.zip")));
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(128);
         PyObject var8 = var1.getlocal(5).__getattr__("namelist").__call__(var2);
         var1.setlocal(6, var8);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(130);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(130);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(133);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(6)), (PyObject)Py.newInteger(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_distribution$6(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyObject var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tar"));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gzip"));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(141);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(144);
         var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(147);
         PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("gztar"), PyString.fromInterned("tar")});
         var1.getlocal(2).__setattr__((String)"formats", var6);
         var3 = null;
         var1.setline(148);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(149);
         var1.getlocal(2).__getattr__("run").__call__(var2);
         var1.setline(152);
         var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(153);
         var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(154);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(155);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0.tar"), PyString.fromInterned("fake-1.0.tar.gz")})));
         var1.setline(157);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("fake-1.0.tar")));
         var1.setline(158);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("fake-1.0.tar.gz")));
         var1.setline(161);
         var6 = new PyList(new PyObject[]{PyString.fromInterned("tar"), PyString.fromInterned("gztar")});
         var1.getlocal(2).__setattr__((String)"formats", var6);
         var3 = null;
         var1.setline(163);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(164);
         var1.getlocal(2).__getattr__("run").__call__(var2);
         var1.setline(166);
         var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(167);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(168);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0.tar"), PyString.fromInterned("fake-1.0.tar.gz")})));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_unicode_metadata_tgz$7(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyString.fromInterned("\n        Unicode name or version should not break building to tar.gz format.\n        Reference issue #11638.\n        ");
      var1.setline(178);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyUnicode.fromInterned("fake"), PyString.fromInterned("version"), PyUnicode.fromInterned("1.0")})));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(181);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
      var1.getlocal(2).__setattr__((String)"formats", var6);
      var3 = null;
      var1.setline(182);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(183);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(186);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(188);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0.tar.gz")})));
      var1.setline(190);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("fake-1.0.tar.gz")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_add_defaults$8(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(203);
      PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), new PyList(new PyObject[]{PyString.fromInterned("*.cfg"), PyString.fromInterned("*.dat")}), PyString.fromInterned("somecode"), new PyList(new PyObject[]{PyString.fromInterned("*.txt")})});
      var1.getlocal(1).__setattr__((String)"package_data", var8);
      var3 = null;
      var1.setline(205);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned("doc.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(206);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned("doc.dat")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(209);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("data"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(210);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(3));
      var1.setline(211);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("data.dt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(212);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("some"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(213);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(4));
      var1.setline(215);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned(".hg"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(216);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(5));
      var1.setline(217);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("last-message.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(219);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("buildout.cfg")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(220);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("inroot.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(221);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("file.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(222);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("other_file.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(224);
      PyList var10 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("data"), new PyList(new PyObject[]{PyString.fromInterned("data/data.dt"), PyString.fromInterned("buildout.cfg"), PyString.fromInterned("inroot.txt"), PyString.fromInterned("notexisting")})}), PyString.fromInterned("some/file.txt"), PyString.fromInterned("some/other_file.txt")});
      var1.getlocal(1).__setattr__((String)"data_files", var10);
      var3 = null;
      var1.setline(232);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("scripts"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(233);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(6));
      var1.setline(234);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned("script.py")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(235);
      var10 = new PyList(new PyObject[]{var1.getglobal("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("scripts"), (PyObject)PyString.fromInterned("script.py"))});
      var1.getlocal(1).__setattr__((String)"scripts", var10);
      var3 = null;
      var1.setline(237);
      var10 = new PyList(new PyObject[]{PyString.fromInterned("zip")});
      var1.getlocal(2).__setattr__((String)"formats", var10);
      var3 = null;
      var1.setline(238);
      var3 = var1.getglobal("True");
      var1.getlocal(2).__setattr__("use_defaults", var3);
      var3 = null;
      var1.setline(240);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(241);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(244);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(245);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(246);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0.zip")})));
      var1.setline(248);
      var3 = var1.getglobal("zipfile").__getattr__("ZipFile").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("fake-1.0.zip")));
      var1.setlocal(9, var3);
      var3 = null;
      var3 = null;

      PyObject var9;
      try {
         var1.setline(250);
         var9 = var1.getlocal(9).__getattr__("namelist").__call__(var2);
         var1.setlocal(10, var9);
         var4 = null;
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(252);
         var1.getlocal(9).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(252);
      var1.getlocal(9).__getattr__("close").__call__(var2);
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(10)), (PyObject)Py.newInteger(12));
      var1.setline(258);
      var3 = var1.getglobal("open").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("MANIFEST")));
      var1.setlocal(11, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(260);
         var9 = var1.getlocal(11).__getattr__("read").__call__(var2);
         var1.setlocal(12, var9);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(262);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(262);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(263);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(12), var1.getglobal("MANIFEST")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("sep"), var1.getglobal("os").__getattr__("sep")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_metadata_check_option$9(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var10000 = var1.getlocal(0).__getattr__("get_cmd");
      PyObject[] var3 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      String[] var4 = new String[]{"metadata"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(272);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(273);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(274);
      PyList var11 = new PyList();
      var6 = var11.__getattr__("append");
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(274);
      var6 = var1.getlocal(0).__getattr__("get_logs").__call__(var2, var1.getglobal("WARN")).__iter__();

      while(true) {
         var1.setline(274);
         PyObject var8 = var6.__iternext__();
         if (var8 == null) {
            var1.setline(274);
            var1.dellocal(4);
            PyList var9 = var11;
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(276);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(2));
            var1.setline(279);
            var1.getlocal(0).__getattr__("clear_logs").__call__(var2);
            var1.setline(280);
            var6 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
            var7 = Py.unpackSequence(var6, 2);
            var5 = var7[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
            var1.setline(281);
            var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
            var1.setline(282);
            PyInteger var10 = Py.newInteger(0);
            var1.getlocal(2).__setattr__((String)"metadata_check", var10);
            var3 = null;
            var1.setline(283);
            var1.getlocal(2).__getattr__("run").__call__(var2);
            var1.setline(284);
            var11 = new PyList();
            var6 = var11.__getattr__("append");
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(284);
            var6 = var1.getlocal(0).__getattr__("get_logs").__call__(var2, var1.getglobal("WARN")).__iter__();

            while(true) {
               var1.setline(284);
               var8 = var6.__iternext__();
               if (var8 == null) {
                  var1.setline(284);
                  var1.dellocal(6);
                  var9 = var11;
                  var1.setlocal(3, var9);
                  var3 = null;
                  var1.setline(286);
                  var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(0));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(5, var8);
               var1.setline(285);
               if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: check:")).__nonzero__()) {
                  var1.setline(284);
                  var1.getlocal(6).__call__(var2, var1.getlocal(5));
               }
            }
         }

         var1.setlocal(5, var8);
         var1.setline(275);
         if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: check:")).__nonzero__()) {
            var1.setline(274);
            var1.getlocal(4).__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject test_check_metadata_deprecated$10(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(290);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      ContextManager var7;
      PyObject var8 = (var7 = ContextGuard.getManager(var1.getglobal("check_warnings").__call__(var2))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(3, var8);
            var1.setline(292);
            var1.getglobal("warnings").__getattr__("simplefilter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("always"));
            var1.setline(293);
            var1.getlocal(2).__getattr__("check_metadata").__call__(var2);
            var1.setline(294);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("warnings")), (PyObject)Py.newInteger(1));
         } catch (Throwable var6) {
            if (var7.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var7.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_show_formats$11(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label25: {
         try {
            var1.setlocal(1, var4);
            var1.setline(298);
            var1.getglobal("show_formats").__call__(var2);
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label25;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(301);
      PyObject var6 = var1.getglobal("len").__call__(var2, var1.getglobal("ARCHIVE_FORMATS").__getattr__("keys").__call__(var2));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(302);
      PyList var10000 = new PyList();
      var6 = var10000.__getattr__("append");
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(302);
      var6 = var1.getlocal(1).__getattr__("getvalue").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(302);
         var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(302);
            var1.dellocal(4);
            PyList var7 = var10000;
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(304);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3)), var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(303);
         if (var1.getlocal(5).__getattr__("strip").__call__(var2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--formats=")).__nonzero__()) {
            var1.setline(302);
            var1.getlocal(4).__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject test_finalize_options$12(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(308);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(311);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("manifest"), (PyObject)PyString.fromInterned("MANIFEST"));
      var1.setline(312);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("template"), (PyObject)PyString.fromInterned("MANIFEST.in"));
      var1.setline(313);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("dist_dir"), (PyObject)PyString.fromInterned("dist"));
      var1.setline(317);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(2).__setattr__((String)"formats", var6);
      var3 = null;
      var1.setline(318);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("finalize_options"));
      var1.setline(319);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("zip")});
      var1.getlocal(2).__setattr__((String)"formats", var7);
      var3 = null;
      var1.setline(320);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(323);
      PyString var8 = PyString.fromInterned("supazipa");
      var1.getlocal(2).__setattr__((String)"formats", var8);
      var3 = null;
      var1.setline(324);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("finalize_options"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_distribution_owner_group$13(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyObject var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tar"));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gzip"));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(333);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(336);
         var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(339);
         PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
         var1.getlocal(2).__setattr__((String)"formats", var9);
         var3 = null;
         var1.setline(340);
         var3 = var1.getglobal("pwd").__getattr__("getpwuid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var1.getlocal(2).__setattr__("owner", var3);
         var3 = null;
         var1.setline(341);
         var3 = var1.getglobal("grp").__getattr__("getgrgid").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0));
         var1.getlocal(2).__setattr__("group", var3);
         var3 = null;
         var1.setline(342);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(343);
         var1.getlocal(2).__getattr__("run").__call__(var2);
         var1.setline(346);
         var3 = var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"), (PyObject)PyString.fromInterned("fake-1.0.tar.gz"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(347);
         var3 = var1.getglobal("tarfile").__getattr__("open").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var3 = null;

         PyObject var8;
         try {
            var1.setline(349);
            var8 = var1.getlocal(4).__getattr__("getmembers").__call__(var2).__iter__();

            while(true) {
               var1.setline(349);
               var5 = var8.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(5, var5);
               var1.setline(350);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("uid"), (PyObject)Py.newInteger(0));
               var1.setline(351);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("gid"), (PyObject)Py.newInteger(0));
            }
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(353);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            throw (Throwable)var7;
         }

         var1.setline(353);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(356);
         var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(359);
         var9 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
         var1.getlocal(2).__setattr__((String)"formats", var9);
         var3 = null;
         var1.setline(360);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(361);
         var1.getlocal(2).__getattr__("run").__call__(var2);
         var1.setline(364);
         var3 = var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"), (PyObject)PyString.fromInterned("fake-1.0.tar.gz"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(365);
         var3 = var1.getglobal("tarfile").__getattr__("open").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(371);
            var8 = var1.getlocal(4).__getattr__("getmembers").__call__(var2).__iter__();

            while(true) {
               var1.setline(371);
               var5 = var8.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(5, var5);
               var1.setline(372);
               var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("uid"), var1.getglobal("os").__getattr__("getuid").__call__(var2));
            }
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(374);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            throw (Throwable)var6;
         }

         var1.setline(374);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _check_template$14(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(381);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("tmp_dir"));
      var1.setline(382);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MANIFEST.in"), (PyObject)var1.getlocal(1));
      var1.setline(383);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(384);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.getlocal(3).__setattr__("filelist", var3);
      var3 = null;
      var1.setline(385);
      var1.getlocal(3).__getattr__("read_template").__call__(var2);
      var1.setline(386);
      var3 = var1.getlocal(0).__getattr__("get_logs").__call__(var2, var1.getglobal("WARN"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(387);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_template_unknown_command$15(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      var1.getlocal(0).__getattr__("_check_template").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("taunt knights *"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_template_wrong_arguments$16(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      var1.getlocal(0).__getattr__("_check_template").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prune"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_template_wrong_path$17(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      var1.getlocal(0).__getattr__("_check_template").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include examples/"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_file_list$18(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(408);
      PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("somecode"), new PyList(new PyObject[]{PyString.fromInterned("*.txt")})});
      var1.getlocal(1).__setattr__((String)"package_data", var9);
      var3 = null;
      var1.setline(409);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned("doc.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(410);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
      var1.getlocal(2).__setattr__((String)"formats", var10);
      var3 = null;
      var1.setline(411);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(412);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(414);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2).__getattr__("manifest"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      PyObject var6;
      PyObject var11;
      PyList var12;
      PyList var10000;
      PyObject var10001;
      try {
         var1.setline(416);
         var10000 = new PyList();
         var11 = var10000.__getattr__("append");
         var1.setlocal(5, var11);
         var4 = null;
         var1.setline(416);
         var11 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(416);
            var5 = var11.__iternext__();
            if (var5 == null) {
               var1.setline(416);
               var1.dellocal(5);
               var12 = var10000;
               var1.setlocal(4, var12);
               var4 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(417);
            var6 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            var10001 = var6._ne(PyString.fromInterned(""));
            var6 = null;
            if (var10001.__nonzero__()) {
               var1.setline(416);
               var1.getlocal(5).__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
            }
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(419);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var8;
      }

      var1.setline(419);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(421);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4)), (PyObject)Py.newInteger(5));
      var1.setline(424);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("somecode"), PyString.fromInterned("doc2.txt")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(427);
      var3 = var1.getlocal(1).__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(428);
      var3 = var1.getglobal("False");
      var1.getlocal(7).__setattr__("finalized", var3);
      var3 = null;
      var1.setline(429);
      var1.getlocal(7).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(431);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(433);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2).__getattr__("manifest"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(435);
         var10000 = new PyList();
         var11 = var10000.__getattr__("append");
         var1.setlocal(9, var11);
         var4 = null;
         var1.setline(435);
         var11 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(435);
            var5 = var11.__iternext__();
            if (var5 == null) {
               var1.setline(435);
               var1.dellocal(9);
               var12 = var10000;
               var1.setlocal(8, var12);
               var4 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(436);
            var6 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            var10001 = var6._ne(PyString.fromInterned(""));
            var6 = null;
            if (var10001.__nonzero__()) {
               var1.setline(435);
               var1.getlocal(9).__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(438);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(438);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(441);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(8)), (PyObject)Py.newInteger(6));
      var1.setline(442);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("doc2.txt"), (PyObject)var1.getlocal(8).__getitem__(Py.newInteger(-1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_manifest_marker$19(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(448);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(449);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(451);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2).__getattr__("manifest"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(453);
         PyList var10000 = new PyList();
         PyObject var8 = var10000.__getattr__("append");
         var1.setlocal(5, var8);
         var4 = null;
         var1.setline(453);
         var8 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(453);
            var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(453);
               var1.dellocal(5);
               PyList var9 = var10000;
               var1.setlocal(4, var9);
               var4 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(454);
            PyObject var6 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            PyObject var10001 = var6._ne(PyString.fromInterned(""));
            var6 = null;
            if (var10001.__nonzero__()) {
               var1.setline(453);
               var1.getlocal(5).__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(456);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(456);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(458);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("# file GENERATED by distutils, do NOT edit"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_manifest_comments$20(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyObject var3 = var1.getglobal("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("            # bad.py\n            #bad.py\n            good.py\n            "));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(469);
      var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(470);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(471);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), var1.getlocal(3).__getattr__("manifest")})), (PyObject)var1.getlocal(1));
      var1.setline(472);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("good.py")})), (PyObject)PyString.fromInterned("# pick me!"));
      var1.setline(473);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("bad.py")})), (PyObject)PyString.fromInterned("# don't pick me!"));
      var1.setline(474);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("#bad.py")})), (PyObject)PyString.fromInterned("# don't pick me!"));
      var1.setline(475);
      var1.getlocal(3).__getattr__("run").__call__(var2);
      var1.setline(476);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("filelist").__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("good.py")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_manual_manifest$21(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      PyObject var3 = var1.getlocal(0).__getattr__("get_cmd").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(482);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
      var1.getlocal(2).__setattr__((String)"formats", var9);
      var3 = null;
      var1.setline(483);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(484);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), var1.getlocal(2).__getattr__("manifest")})), (PyObject)PyString.fromInterned("README.manual"));
      var1.setline(485);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tmp_dir"), PyString.fromInterned("README.manual")})), (PyObject)PyString.fromInterned("This project maintains its MANIFEST file itself."));
      var1.setline(487);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(488);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("filelist").__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("README.manual")})));
      var1.setline(490);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2).__getattr__("manifest"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      PyObject var10;
      PyList var11;
      PyList var10000;
      try {
         var1.setline(492);
         var10000 = new PyList();
         var10 = var10000.__getattr__("append");
         var1.setlocal(5, var10);
         var4 = null;
         var1.setline(492);
         var10 = var1.getlocal(3).__getattr__("read").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(492);
            var5 = var10.__iternext__();
            if (var5 == null) {
               var1.setline(492);
               var1.dellocal(5);
               var11 = var10000;
               var1.setlocal(4, var11);
               var4 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(493);
            PyObject var6 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            PyObject var10001 = var6._ne(PyString.fromInterned(""));
            var6 = null;
            if (var10001.__nonzero__()) {
               var1.setline(492);
               var1.getlocal(5).__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
            }
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(495);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var8;
      }

      var1.setline(495);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(497);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("README.manual")})));
      var1.setline(499);
      var3 = var1.getglobal("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("dist"), (PyObject)PyString.fromInterned("fake-1.0.tar.gz"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(500);
      var3 = var1.getglobal("tarfile").__getattr__("open").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(502);
         var10000 = new PyList();
         var10 = var10000.__getattr__("append");
         var1.setlocal(10, var10);
         var4 = null;
         var1.setline(502);
         var10 = var1.getlocal(8).__iter__();

         while(true) {
            var1.setline(502);
            var5 = var10.__iternext__();
            if (var5 == null) {
               var1.setline(502);
               var1.dellocal(10);
               var11 = var10000;
               var1.setlocal(9, var11);
               var4 = null;
               break;
            }

            var1.setlocal(11, var5);
            var1.setline(502);
            var1.getlocal(10).__call__(var2, var1.getlocal(11).__getattr__("name"));
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(504);
         var1.getlocal(8).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(504);
      var1.getlocal(8).__getattr__("close").__call__(var2);
      var1.setline(505);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("sorted").__call__(var2, var1.getlocal(9)), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("fake-1.0"), PyString.fromInterned("fake-1.0/PKG-INFO"), PyString.fromInterned("fake-1.0/README.manual")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$22(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("SDistTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_sdist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SDistTestCase$1 = Py.newCode(0, var2, var1, "SDistTestCase", 57, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 73, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "metadata", "dist", "cmd"};
      get_cmd$4 = Py.newCode(2, var2, var1, "get_cmd", 78, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "dist_folder", "files", "zip_file", "content"};
      test_prune_file_list$5 = Py.newCode(1, var2, var1, "test_prune_file_list", 92, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "dist_folder", "result"};
      test_make_distribution$6 = Py.newCode(1, var2, var1, "test_make_distribution", 135, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "dist_folder", "result"};
      test_unicode_metadata_tgz$7 = Py.newCode(1, var2, var1, "test_unicode_metadata_tgz", 170, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "data_dir", "some_dir", "hg_dir", "script_dir", "dist_folder", "files", "zip_file", "content", "f", "manifest"};
      test_add_defaults$8 = Py.newCode(1, var2, var1, "test_add_defaults", 192, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "warnings", "_[274_20]", "msg", "_[284_20]"};
      test_metadata_check_option$9 = Py.newCode(1, var2, var1, "test_metadata_check_option", 265, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "w"};
      test_check_metadata_deprecated$10 = Py.newCode(1, var2, var1, "test_check_metadata_deprecated", 288, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stdout", "num_formats", "output", "_[302_18]", "line"};
      test_show_formats$11 = Py.newCode(1, var2, var1, "test_show_formats", 296, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd"};
      test_finalize_options$12 = Py.newCode(1, var2, var1, "test_finalize_options", 306, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "archive_name", "archive", "member"};
      test_make_distribution_owner_group$13 = Py.newCode(1, var2, var1, "test_make_distribution_owner_group", 326, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "content", "dist", "cmd", "warnings"};
      _check_template$14 = Py.newCode(2, var2, var1, "_check_template", 379, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_invalid_template_unknown_command$15 = Py.newCode(1, var2, var1, "test_invalid_template_unknown_command", 389, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_invalid_template_wrong_arguments$16 = Py.newCode(1, var2, var1, "test_invalid_template_wrong_arguments", 392, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_invalid_template_wrong_path$17 = Py.newCode(1, var2, var1, "test_invalid_template_wrong_path", 396, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "f", "manifest", "_[416_24]", "line", "build_py", "manifest2", "_[435_25]"};
      test_get_file_list$18 = Py.newCode(1, var2, var1, "test_get_file_list", 402, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "f", "manifest", "_[453_24]", "line"};
      test_manifest_marker$19 = Py.newCode(1, var2, var1, "test_manifest_marker", 444, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "contents", "dist", "cmd"};
      test_manifest_comments$20 = Py.newCode(1, var2, var1, "test_manifest_comments", 461, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "f", "manifest", "_[492_24]", "line", "archive_name", "archive", "filenames", "_[502_25]", "tarinfo"};
      test_manual_manifest$21 = Py.newCode(1, var2, var1, "test_manual_manifest", 478, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$22 = Py.newCode(0, var2, var1, "test_suite", 508, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_sdist$py("distutils/tests/test_sdist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_sdist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SDistTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.get_cmd$4(var2, var3);
         case 5:
            return this.test_prune_file_list$5(var2, var3);
         case 6:
            return this.test_make_distribution$6(var2, var3);
         case 7:
            return this.test_unicode_metadata_tgz$7(var2, var3);
         case 8:
            return this.test_add_defaults$8(var2, var3);
         case 9:
            return this.test_metadata_check_option$9(var2, var3);
         case 10:
            return this.test_check_metadata_deprecated$10(var2, var3);
         case 11:
            return this.test_show_formats$11(var2, var3);
         case 12:
            return this.test_finalize_options$12(var2, var3);
         case 13:
            return this.test_make_distribution_owner_group$13(var2, var3);
         case 14:
            return this._check_template$14(var2, var3);
         case 15:
            return this.test_invalid_template_unknown_command$15(var2, var3);
         case 16:
            return this.test_invalid_template_wrong_arguments$16(var2, var3);
         case 17:
            return this.test_invalid_template_wrong_path$17(var2, var3);
         case 18:
            return this.test_get_file_list$18(var2, var3);
         case 19:
            return this.test_manifest_marker$19(var2, var3);
         case 20:
            return this.test_manifest_comments$20(var2, var3);
         case 21:
            return this.test_manual_manifest$21(var2, var3);
         case 22:
            return this.test_suite$22(var2, var3);
         default:
            return null;
      }
   }
}
