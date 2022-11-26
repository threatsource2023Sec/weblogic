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
@Filename("filecmp.py")
public class filecmp$py extends PyFunctionTable implements PyRunnable {
   static filecmp$py self;
   static final PyCode f$0;
   static final PyCode cmp$1;
   static final PyCode _sig$2;
   static final PyCode _do_cmp$3;
   static final PyCode dircmp$4;
   static final PyCode __init__$5;
   static final PyCode phase0$6;
   static final PyCode phase1$7;
   static final PyCode phase2$8;
   static final PyCode phase3$9;
   static final PyCode phase4$10;
   static final PyCode phase4_closure$11;
   static final PyCode report$12;
   static final PyCode report_partial_closure$13;
   static final PyCode report_full_closure$14;
   static final PyCode __getattr__$15;
   static final PyCode cmpfiles$16;
   static final PyCode _cmp$17;
   static final PyCode _filter$18;
   static final PyCode demo$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Utilities for comparing files and directories.\n\nClasses:\n    dircmp\n\nFunctions:\n    cmp(f1, f2, shallow=1) -> int\n    cmpfiles(a, b, common) -> ([], [], [])\n\n"));
      var1.setline(10);
      PyString.fromInterned("Utilities for comparing files and directories.\n\nClasses:\n    dircmp\n\nFunctions:\n    cmp(f1, f2, shallow=1) -> int\n    cmpfiles(a, b, common) -> ([], [], [])\n\n");
      var1.setline(12);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(14);
      String[] var5 = new String[]{"ifilter", "ifilterfalse", "imap", "izip"};
      PyObject[] var6 = imp.importFrom("itertools", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("ifilter", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("ifilterfalse", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("imap", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("izip", var4);
      var4 = null;
      var1.setline(16);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("cmp"), PyString.fromInterned("dircmp"), PyString.fromInterned("cmpfiles")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(18);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache", var8);
      var3 = null;
      var1.setline(19);
      var3 = Py.newInteger(8)._mul(Py.newInteger(1024));
      var1.setlocal("BUFSIZE", var3);
      var3 = null;
      var1.setline(21);
      var6 = new PyObject[]{Py.newInteger(1)};
      PyFunction var9 = new PyFunction(var1.f_globals, var6, cmp$1, PyString.fromInterned("Compare two files.\n\n    Arguments:\n\n    f1 -- First file name\n\n    f2 -- Second file name\n\n    shallow -- Just check stat signature (do not read the files).\n               defaults to 1.\n\n    Return value:\n\n    True if the files are the same, False otherwise.\n\n    This function uses a cache for past comparisons and the results,\n    with a cache invalidation mechanism relying on stale signatures.\n\n    "));
      var1.setlocal("cmp", var9);
      var3 = null;
      var1.setline(59);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _sig$2, (PyObject)null);
      var1.setlocal("_sig", var9);
      var3 = null;
      var1.setline(64);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _do_cmp$3, (PyObject)null);
      var1.setlocal("_do_cmp", var9);
      var3 = null;
      var1.setline(77);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("dircmp", var6, dircmp$4);
      var1.setlocal("dircmp", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(241);
      var6 = new PyObject[]{Py.newInteger(1)};
      var9 = new PyFunction(var1.f_globals, var6, cmpfiles$16, PyString.fromInterned("Compare common files in two directories.\n\n    a, b -- directory names\n    common -- list of file names found in both directories\n    shallow -- if true, do comparison based solely on stat() information\n\n    Returns a tuple of three lists:\n      files that compare equal\n      files that are different\n      filenames that aren't regular files.\n\n    "));
      var1.setlocal("cmpfiles", var9);
      var3 = null;
      var1.setline(268);
      var6 = new PyObject[]{var1.getname("abs"), var1.getname("cmp")};
      var9 = new PyFunction(var1.f_globals, var6, _cmp$17, (PyObject)null);
      var1.setlocal("_cmp", var9);
      var3 = null;
      var1.setline(277);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, _filter$18, (PyObject)null);
      var1.setlocal("_filter", var9);
      var3 = null;
      var1.setline(283);
      var6 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var6, demo$19, (PyObject)null);
      var1.setlocal("demo", var9);
      var3 = null;
      var1.setline(295);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(296);
         var1.getname("demo").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cmp$1(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("Compare two files.\n\n    Arguments:\n\n    f1 -- First file name\n\n    f2 -- Second file name\n\n    shallow -- Just check stat signature (do not read the files).\n               defaults to 1.\n\n    Return value:\n\n    True if the files are the same, False otherwise.\n\n    This function uses a cache for past comparisons and the results,\n    with a cache invalidation mechanism relying on stale signatures.\n\n    ");
      var1.setline(42);
      PyObject var3 = var1.getglobal("_sig").__call__(var2, var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getglobal("_sig").__call__(var2, var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(var1.getglobal("stat").__getattr__("S_IFREG"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var10000 = var3._ne(var1.getglobal("stat").__getattr__("S_IFREG"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(45);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(46);
         var10000 = var1.getlocal(2);
         PyObject var4;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(3);
            var10000 = var4._eq(var1.getlocal(4));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(47);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(48);
            var4 = var1.getlocal(3).__getitem__(Py.newInteger(1));
            var10000 = var4._ne(var1.getlocal(4).__getitem__(Py.newInteger(1)));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(49);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(51);
               var4 = var1.getglobal("_cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)})));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(52);
               var4 = var1.getlocal(5);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(53);
                  var4 = var1.getglobal("_do_cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(54);
                  var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache"));
                  var10000 = var4._gt(Py.newInteger(100));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(55);
                     var1.getglobal("_cache").__getattr__("clear").__call__(var2);
                  }

                  var1.setline(56);
                  var4 = var1.getlocal(5);
                  var1.getglobal("_cache").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)})), var4);
                  var4 = null;
               }

               var1.setline(57);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _sig$2(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("stat").__getattr__("S_IFMT").__call__(var2, var1.getlocal(0).__getattr__("st_mode")), var1.getlocal(0).__getattr__("st_size"), var1.getlocal(0).__getattr__("st_mtime")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _do_cmp$3(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject dircmp$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A class that manages the comparison of 2 directories.\n\n    dircmp(a,b,ignore=None,hide=None)\n      A and B are directories.\n      IGNORE is a list of names to ignore,\n        defaults to ['RCS', 'CVS', 'tags'].\n      HIDE is a list of names to hide,\n        defaults to [os.curdir, os.pardir].\n\n    High level usage:\n      x = dircmp(dir1, dir2)\n      x.report() -> prints a report on the differences between dir1 and dir2\n       or\n      x.report_partial_closure() -> prints report on differences between dir1\n            and dir2, and reports on common immediate subdirectories.\n      x.report_full_closure() -> like report_partial_closure,\n            but fully recursive.\n\n    Attributes:\n     left_list, right_list: The files in dir1 and dir2,\n        filtered by hide and ignore.\n     common: a list of names in both dir1 and dir2.\n     left_only, right_only: names only in dir1, dir2.\n     common_dirs: subdirectories in both dir1 and dir2.\n     common_files: files in both dir1 and dir2.\n     common_funny: names in both dir1 and dir2 where the type differs between\n        dir1 and dir2, or the name is not stat-able.\n     same_files: list of identical files.\n     diff_files: list of filenames which differ.\n     funny_files: list of files which could not be compared.\n     subdirs: a dictionary of dircmp objects, keyed by names in common_dirs.\n     "));
      var1.setline(109);
      PyString.fromInterned("A class that manages the comparison of 2 directories.\n\n    dircmp(a,b,ignore=None,hide=None)\n      A and B are directories.\n      IGNORE is a list of names to ignore,\n        defaults to ['RCS', 'CVS', 'tags'].\n      HIDE is a list of names to hide,\n        defaults to [os.curdir, os.pardir].\n\n    High level usage:\n      x = dircmp(dir1, dir2)\n      x.report() -> prints a report on the differences between dir1 and dir2\n       or\n      x.report_partial_closure() -> prints report on differences between dir1\n            and dir2, and reports on common immediate subdirectories.\n      x.report_full_closure() -> like report_partial_closure,\n            but fully recursive.\n\n    Attributes:\n     left_list, right_list: The files in dir1 and dir2,\n        filtered by hide and ignore.\n     common: a list of names in both dir1 and dir2.\n     left_only, right_only: names only in dir1, dir2.\n     common_dirs: subdirectories in both dir1 and dir2.\n     common_files: files in both dir1 and dir2.\n     common_funny: names in both dir1 and dir2 where the type differs between\n        dir1 and dir2, or the name is not stat-able.\n     same_files: list of identical files.\n     diff_files: list of filenames which differ.\n     funny_files: list of files which could not be compared.\n     subdirs: a dictionary of dircmp objects, keyed by names in common_dirs.\n     ");
      var1.setline(111);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase0$6, (PyObject)null);
      var1.setlocal("phase0", var5);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase1$7, (PyObject)null);
      var1.setlocal("phase1", var5);
      var3 = null;
      var1.setline(138);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase2$8, (PyObject)null);
      var1.setlocal("phase2", var5);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase3$9, (PyObject)null);
      var1.setlocal("phase3", var5);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase4$10, (PyObject)null);
      var1.setlocal("phase4", var5);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, phase4_closure$11, (PyObject)null);
      var1.setlocal("phase4_closure", var5);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, report$12, (PyObject)null);
      var1.setlocal("report", var5);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, report_partial_closure$13, (PyObject)null);
      var1.setlocal("report_partial_closure", var5);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, report_full_closure$14, (PyObject)null);
      var1.setlocal("report_full_closure", var5);
      var3 = null;
      var1.setline(229);
      PyObject var10000 = var1.getname("dict");
      var3 = new PyObject[]{var1.getname("phase4"), var1.getname("phase3"), var1.getname("phase3"), var1.getname("phase3"), var1.getname("phase2"), var1.getname("phase2"), var1.getname("phase2"), var1.getname("phase1"), var1.getname("phase1"), var1.getname("phase1"), var1.getname("phase0"), var1.getname("phase0")};
      String[] var4 = new String[]{"subdirs", "same_files", "diff_files", "funny_files", "common_dirs", "common_files", "common_funny", "common", "left_only", "right_only", "left_list", "right_list"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal("methodmap", var6);
      var3 = null;
      var1.setline(235);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __getattr__$15, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("left", var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("right", var3);
      var3 = null;
      var1.setline(114);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyList var4;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var4 = new PyList(new PyObject[]{var1.getglobal("os").__getattr__("curdir"), var1.getglobal("os").__getattr__("pardir")});
         var1.getlocal(0).__setattr__((String)"hide", var4);
         var3 = null;
      } else {
         var1.setline(117);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("hide", var3);
         var3 = null;
      }

      var1.setline(118);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(119);
         var4 = new PyList(new PyObject[]{PyString.fromInterned("RCS"), PyString.fromInterned("CVS"), PyString.fromInterned("tags")});
         var1.getlocal(0).__setattr__((String)"ignore", var4);
         var3 = null;
      } else {
         var1.setline(121);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("ignore", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject phase0$6(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("_filter").__call__(var2, var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("left")), var1.getlocal(0).__getattr__("hide")._add(var1.getlocal(0).__getattr__("ignore")));
      var1.getlocal(0).__setattr__("left_list", var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("_filter").__call__(var2, var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("right")), var1.getlocal(0).__getattr__("hide")._add(var1.getlocal(0).__getattr__("ignore")));
      var1.getlocal(0).__setattr__("right_list", var3);
      var3 = null;
      var1.setline(128);
      var1.getlocal(0).__getattr__("left_list").__getattr__("sort").__call__(var2);
      var1.setline(129);
      var1.getlocal(0).__getattr__("right_list").__getattr__("sort").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject phase1$7(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getglobal("dict").__call__(var2, var1.getglobal("izip").__call__(var2, var1.getglobal("imap").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase"), var1.getlocal(0).__getattr__("left_list")), var1.getlocal(0).__getattr__("left_list")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getglobal("dict").__call__(var2, var1.getglobal("izip").__call__(var2, var1.getglobal("imap").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase"), var1.getlocal(0).__getattr__("right_list")), var1.getlocal(0).__getattr__("right_list")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getglobal("map").__call__(var2, var1.getlocal(1).__getattr__("__getitem__"), var1.getglobal("ifilter").__call__(var2, var1.getlocal(2).__getattr__("__contains__"), var1.getlocal(1)));
      var1.getlocal(0).__setattr__("common", var3);
      var3 = null;
      var1.setline(135);
      var3 = var1.getglobal("map").__call__(var2, var1.getlocal(1).__getattr__("__getitem__"), var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(2).__getattr__("__contains__"), var1.getlocal(1)));
      var1.getlocal(0).__setattr__("left_only", var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getglobal("map").__call__(var2, var1.getlocal(2).__getattr__("__getitem__"), var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(1).__getattr__("__contains__"), var1.getlocal(2)));
      var1.getlocal(0).__setattr__("right_only", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject phase2$8(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"common_dirs", var3);
      var3 = null;
      var1.setline(140);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"common_files", var3);
      var3 = null;
      var1.setline(141);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"common_funny", var3);
      var3 = null;
      var1.setline(143);
      PyObject var9 = var1.getlocal(0).__getattr__("common").__iter__();

      while(true) {
         var1.setline(143);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(144);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("left"), var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(145);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("right"), var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(147);
         PyInteger var10 = Py.newInteger(1);
         var1.setlocal(4, var10);
         var5 = null;

         PyObject var6;
         PyInteger var11;
         PyException var12;
         try {
            var1.setline(149);
            var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2));
            var1.setlocal(5, var5);
            var5 = null;
         } catch (Throwable var8) {
            var12 = Py.setException(var8, var1);
            if (!var12.match(var1.getglobal("os").__getattr__("error"))) {
               throw var12;
            }

            var6 = var12.value;
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(152);
            var11 = Py.newInteger(0);
            var1.setlocal(4, var11);
            var6 = null;
         }

         try {
            var1.setline(154);
            var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(3));
            var1.setlocal(7, var5);
            var5 = null;
         } catch (Throwable var7) {
            var12 = Py.setException(var7, var1);
            if (!var12.match(var1.getglobal("os").__getattr__("error"))) {
               throw var12;
            }

            var6 = var12.value;
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(157);
            var11 = Py.newInteger(0);
            var1.setlocal(4, var11);
            var6 = null;
         }

         var1.setline(159);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(160);
            var5 = var1.getglobal("stat").__getattr__("S_IFMT").__call__(var2, var1.getlocal(5).__getattr__("st_mode"));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(161);
            var5 = var1.getglobal("stat").__getattr__("S_IFMT").__call__(var2, var1.getlocal(7).__getattr__("st_mode"));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(162);
            var5 = var1.getlocal(8);
            PyObject var10000 = var5._ne(var1.getlocal(9));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(163);
               var1.getlocal(0).__getattr__("common_funny").__getattr__("append").__call__(var2, var1.getlocal(1));
            } else {
               var1.setline(164);
               if (var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                  var1.setline(165);
                  var1.getlocal(0).__getattr__("common_dirs").__getattr__("append").__call__(var2, var1.getlocal(1));
               } else {
                  var1.setline(166);
                  if (var1.getglobal("stat").__getattr__("S_ISREG").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                     var1.setline(167);
                     var1.getlocal(0).__getattr__("common_files").__getattr__("append").__call__(var2, var1.getlocal(1));
                  } else {
                     var1.setline(169);
                     var1.getlocal(0).__getattr__("common_funny").__getattr__("append").__call__(var2, var1.getlocal(1));
                  }
               }
            }
         } else {
            var1.setline(171);
            var1.getlocal(0).__getattr__("common_funny").__getattr__("append").__call__(var2, var1.getlocal(1));
         }
      }
   }

   public PyObject phase3$9(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getglobal("cmpfiles").__call__(var2, var1.getlocal(0).__getattr__("left"), var1.getlocal(0).__getattr__("right"), var1.getlocal(0).__getattr__("common_files"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(175);
      var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("same_files", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("diff_files", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("funny_files", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject phase4$10(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"subdirs", var3);
      var3 = null;
      var1.setline(182);
      PyObject var6 = var1.getlocal(0).__getattr__("common_dirs").__iter__();

      while(true) {
         var1.setline(182);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(183);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("left"), var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(184);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("right"), var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(185);
         var5 = var1.getglobal("dircmp").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("ignore"), var1.getlocal(0).__getattr__("hide"));
         var1.getlocal(0).__getattr__("subdirs").__setitem__(var1.getlocal(1), var5);
         var5 = null;
      }
   }

   public PyObject phase4_closure$11(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      var1.getlocal(0).__getattr__("phase4").__call__(var2);
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("subdirs").__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(189);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(190);
         var1.getlocal(1).__getattr__("phase4_closure").__call__(var2);
      }
   }

   public PyObject report$12(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      Py.printComma(PyString.fromInterned("diff"));
      Py.printComma(var1.getlocal(0).__getattr__("left"));
      Py.println(var1.getlocal(0).__getattr__("right"));
      var1.setline(195);
      if (var1.getlocal(0).__getattr__("left_only").__nonzero__()) {
         var1.setline(196);
         var1.getlocal(0).__getattr__("left_only").__getattr__("sort").__call__(var2);
         var1.setline(197);
         Py.printComma(PyString.fromInterned("Only in"));
         Py.printComma(var1.getlocal(0).__getattr__("left"));
         Py.printComma(PyString.fromInterned(":"));
         Py.println(var1.getlocal(0).__getattr__("left_only"));
      }

      var1.setline(198);
      if (var1.getlocal(0).__getattr__("right_only").__nonzero__()) {
         var1.setline(199);
         var1.getlocal(0).__getattr__("right_only").__getattr__("sort").__call__(var2);
         var1.setline(200);
         Py.printComma(PyString.fromInterned("Only in"));
         Py.printComma(var1.getlocal(0).__getattr__("right"));
         Py.printComma(PyString.fromInterned(":"));
         Py.println(var1.getlocal(0).__getattr__("right_only"));
      }

      var1.setline(201);
      if (var1.getlocal(0).__getattr__("same_files").__nonzero__()) {
         var1.setline(202);
         var1.getlocal(0).__getattr__("same_files").__getattr__("sort").__call__(var2);
         var1.setline(203);
         Py.printComma(PyString.fromInterned("Identical files :"));
         Py.println(var1.getlocal(0).__getattr__("same_files"));
      }

      var1.setline(204);
      if (var1.getlocal(0).__getattr__("diff_files").__nonzero__()) {
         var1.setline(205);
         var1.getlocal(0).__getattr__("diff_files").__getattr__("sort").__call__(var2);
         var1.setline(206);
         Py.printComma(PyString.fromInterned("Differing files :"));
         Py.println(var1.getlocal(0).__getattr__("diff_files"));
      }

      var1.setline(207);
      if (var1.getlocal(0).__getattr__("funny_files").__nonzero__()) {
         var1.setline(208);
         var1.getlocal(0).__getattr__("funny_files").__getattr__("sort").__call__(var2);
         var1.setline(209);
         Py.printComma(PyString.fromInterned("Trouble with common files :"));
         Py.println(var1.getlocal(0).__getattr__("funny_files"));
      }

      var1.setline(210);
      if (var1.getlocal(0).__getattr__("common_dirs").__nonzero__()) {
         var1.setline(211);
         var1.getlocal(0).__getattr__("common_dirs").__getattr__("sort").__call__(var2);
         var1.setline(212);
         Py.printComma(PyString.fromInterned("Common subdirectories :"));
         Py.println(var1.getlocal(0).__getattr__("common_dirs"));
      }

      var1.setline(213);
      if (var1.getlocal(0).__getattr__("common_funny").__nonzero__()) {
         var1.setline(214);
         var1.getlocal(0).__getattr__("common_funny").__getattr__("sort").__call__(var2);
         var1.setline(215);
         Py.printComma(PyString.fromInterned("Common funny cases :"));
         Py.println(var1.getlocal(0).__getattr__("common_funny"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject report_partial_closure$13(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      var1.getlocal(0).__getattr__("report").__call__(var2);
      var1.setline(219);
      PyObject var3 = var1.getlocal(0).__getattr__("subdirs").__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(219);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(220);
         Py.println();
         var1.setline(221);
         var1.getlocal(1).__getattr__("report").__call__(var2);
      }
   }

   public PyObject report_full_closure$14(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      var1.getlocal(0).__getattr__("report").__call__(var2);
      var1.setline(225);
      PyObject var3 = var1.getlocal(0).__getattr__("subdirs").__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(225);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(226);
         Py.println();
         var1.setline(227);
         var1.getlocal(1).__getattr__("report_full_closure").__call__(var2);
      }
   }

   public PyObject __getattr__$15(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("methodmap"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(237);
         throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
      } else {
         var1.setline(238);
         var1.getlocal(0).__getattr__("methodmap").__getitem__(var1.getlocal(1)).__call__(var2, var1.getlocal(0));
         var1.setline(239);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject cmpfiles$16(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Compare common files in two directories.\n\n    a, b -- directory names\n    common -- list of file names found in both directories\n    shallow -- if true, do comparison based solely on stat() information\n\n    Returns a tuple of three lists:\n      files that compare equal\n      files that are different\n      filenames that aren't regular files.\n\n    ");
      var1.setline(254);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(255);
      PyObject var6 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(255);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(259);
            var6 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(5, var4);
         var1.setline(256);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(257);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(258);
         var1.getlocal(4).__getitem__(var1.getglobal("_cmp").__call__(var2, var1.getlocal(6), var1.getlocal(7), var1.getlocal(3))).__getattr__("append").__call__(var2, var1.getlocal(5));
      }
   }

   public PyObject _cmp$17(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(270);
         PyObject var6 = var1.getlocal(3).__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2))).__not__();
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(272);
            PyInteger var3 = Py.newInteger(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _filter$18(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getglobal("ifilterfalse").__call__(var2, var1.getlocal(1).__getattr__("__contains__"), var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject demo$19(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(285);
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(286);
      var3 = var1.getlocal(1).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("r"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(287);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._ne(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(288);
         throw Py.makeException(var1.getlocal(1).__getattr__("GetoptError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("need exactly two args"), (PyObject)var1.getglobal("None")));
      } else {
         var1.setline(289);
         var3 = var1.getglobal("dircmp").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(290);
         PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("-r"), PyString.fromInterned("")});
         var10000 = var6._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(291);
            var1.getlocal(4).__getattr__("report_full_closure").__call__(var2);
         } else {
            var1.setline(293);
            var1.getlocal(4).__getattr__("report").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public filecmp$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"f1", "f2", "shallow", "s1", "s2", "outcome"};
      cmp$1 = Py.newCode(3, var2, var1, "cmp", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"st"};
      _sig$2 = Py.newCode(1, var2, var1, "_sig", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f1", "f2", "bufsize", "fp1", "fp2", "b1", "b2"};
      _do_cmp$3 = Py.newCode(2, var2, var1, "_do_cmp", 64, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dircmp$4 = Py.newCode(0, var2, var1, "dircmp", 77, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "a", "b", "ignore", "hide"};
      __init__$5 = Py.newCode(5, var2, var1, "__init__", 111, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      phase0$6 = Py.newCode(1, var2, var1, "phase0", 123, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      phase1$7 = Py.newCode(1, var2, var1, "phase1", 131, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "a_path", "b_path", "ok", "a_stat", "why", "b_stat", "a_type", "b_type"};
      phase2$8 = Py.newCode(1, var2, var1, "phase2", 138, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "xx"};
      phase3$9 = Py.newCode(1, var2, var1, "phase3", 173, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "a_x", "b_x"};
      phase4$10 = Py.newCode(1, var2, var1, "phase4", 177, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sd"};
      phase4_closure$11 = Py.newCode(1, var2, var1, "phase4_closure", 187, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      report$12 = Py.newCode(1, var2, var1, "report", 192, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sd"};
      report_partial_closure$13 = Py.newCode(1, var2, var1, "report_partial_closure", 217, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sd"};
      report_full_closure$14 = Py.newCode(1, var2, var1, "report_full_closure", 223, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$15 = Py.newCode(2, var2, var1, "__getattr__", 235, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "common", "shallow", "res", "x", "ax", "bx"};
      cmpfiles$16 = Py.newCode(4, var2, var1, "cmpfiles", 241, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "sh", "abs", "cmp"};
      _cmp$17 = Py.newCode(5, var2, var1, "_cmp", 268, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"flist", "skip"};
      _filter$18 = Py.newCode(2, var2, var1, "_filter", 277, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "getopt", "options", "args", "dd"};
      demo$19 = Py.newCode(0, var2, var1, "demo", 283, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new filecmp$py("filecmp$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(filecmp$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.cmp$1(var2, var3);
         case 2:
            return this._sig$2(var2, var3);
         case 3:
            return this._do_cmp$3(var2, var3);
         case 4:
            return this.dircmp$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.phase0$6(var2, var3);
         case 7:
            return this.phase1$7(var2, var3);
         case 8:
            return this.phase2$8(var2, var3);
         case 9:
            return this.phase3$9(var2, var3);
         case 10:
            return this.phase4$10(var2, var3);
         case 11:
            return this.phase4_closure$11(var2, var3);
         case 12:
            return this.report$12(var2, var3);
         case 13:
            return this.report_partial_closure$13(var2, var3);
         case 14:
            return this.report_full_closure$14(var2, var3);
         case 15:
            return this.__getattr__$15(var2, var3);
         case 16:
            return this.cmpfiles$16(var2, var3);
         case 17:
            return this._cmp$17(var2, var3);
         case 18:
            return this._filter$18(var2, var3);
         case 19:
            return this.demo$19(var2, var3);
         default:
            return null;
      }
   }
}
