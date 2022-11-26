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
@Filename("distutils/tests/test_filelist.py")
public class test_filelist$py extends PyFunctionTable implements PyRunnable {
   static test_filelist$py self;
   static final PyCode f$0;
   static final PyCode make_local_path$1;
   static final PyCode FileListTestCase$2;
   static final PyCode assertNoWarnings$3;
   static final PyCode assertWarnings$4;
   static final PyCode test_glob_to_re$5;
   static final PyCode test_process_template_line$6;
   static final PyCode test_debug_print$7;
   static final PyCode test_set_allfiles$8;
   static final PyCode test_remove_duplicates$9;
   static final PyCode test_translate_pattern$10;
   static final PyCode test_exclude_pattern$11;
   static final PyCode test_include_pattern$12;
   static final PyCode test_process_template$13;
   static final PyCode test_suite$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.filelist."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.filelist.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"debug"};
      PyObject[] var6 = imp.importFrom("distutils", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("debug", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"WARN"};
      var6 = imp.importFrom("distutils.log", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("WARN", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"DistutilsTemplateError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsTemplateError", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"glob_to_re", "translate_pattern", "FileList"};
      var6 = imp.importFrom("distutils.filelist", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("glob_to_re", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("translate_pattern", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("FileList", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"captured_stdout", "run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(13);
      PyString var7 = PyString.fromInterned("include ok\ninclude xo\nexclude xo\ninclude foo.tmp\ninclude buildout.cfg\nglobal-include *.x\nglobal-include *.txt\nglobal-exclude *.tmp\nrecursive-include f *.oo\nrecursive-exclude global *.x\ngraft dir\nprune dir3\n");
      var1.setlocal("MANIFEST_IN", var7);
      var3 = null;
      var1.setline(29);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, make_local_path$1, PyString.fromInterned("Converts '/' in a string to os.sep"));
      var1.setlocal("make_local_path", var8);
      var3 = null;
      var1.setline(34);
      var6 = new PyObject[]{var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("FileListTestCase", var6, FileListTestCase$2);
      var1.setlocal("FileListTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(295);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, test_suite$14, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(298);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(299);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_local_path$1(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyString.fromInterned("Converts '/' in a string to os.sep");
      var1.setline(31);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getglobal("os").__getattr__("sep"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FileListTestCase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, assertNoWarnings$3, (PyObject)null);
      var1.setlocal("assertNoWarnings", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, assertWarnings$4, (PyObject)null);
      var1.setlocal("assertWarnings", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_glob_to_re$5, (PyObject)null);
      var1.setlocal("test_glob_to_re", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_process_template_line$6, (PyObject)null);
      var1.setlocal("test_process_template_line", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_print$7, (PyObject)null);
      var1.setlocal("test_debug_print", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_set_allfiles$8, (PyObject)null);
      var1.setlocal("test_set_allfiles", var4);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_remove_duplicates$9, (PyObject)null);
      var1.setlocal("test_remove_duplicates", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_translate_pattern$10, (PyObject)null);
      var1.setlocal("test_translate_pattern", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_exclude_pattern$11, (PyObject)null);
      var1.setlocal("test_exclude_pattern", var4);
      var3 = null;
      var1.setline(168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_include_pattern$12, (PyObject)null);
      var1.setlocal("test_include_pattern", var4);
      var3 = null;
      var1.setline(186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_process_template$13, (PyObject)null);
      var1.setlocal("test_process_template", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject assertNoWarnings$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get_logs").__call__(var2, var1.getglobal("WARN")), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(39);
      var1.getlocal(0).__getattr__("clear_logs").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertWarnings$4(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("get_logs").__call__(var2, var1.getglobal("WARN"))), (PyObject)Py.newInteger(0));
      var1.setline(43);
      var1.getlocal(0).__getattr__("clear_logs").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_glob_to_re$5(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("os").__getattr__("sep");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("os").__getattr__("sep");
      PyObject var10000 = var3._eq(PyString.fromInterned("\\"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         var3 = var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getglobal("os").__getattr__("sep"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(50);
      var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo*"), PyString.fromInterned("foo[^%(sep)s]*\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo?"), PyString.fromInterned("foo[^%(sep)s]\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo??"), PyString.fromInterned("foo[^%(sep)s][^%(sep)s]\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo\\\\*"), PyString.fromInterned("foo\\\\\\\\[^%(sep)s]*\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo\\\\\\*"), PyString.fromInterned("foo\\\\\\\\\\\\[^%(sep)s]*\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo????"), PyString.fromInterned("foo[^%(sep)s][^%(sep)s][^%(sep)s][^%(sep)s]\\Z(?ms)")}), new PyTuple(new PyObject[]{PyString.fromInterned("foo\\\\??"), PyString.fromInterned("foo\\\\\\\\[^%(sep)s][^%(sep)s]\\Z(?ms)")})})).__iter__();

      while(true) {
         var1.setline(50);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(60);
         PyObject var7 = var1.getlocal(3)._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("sep"), var1.getlocal(1)}));
         var1.setlocal(3, var7);
         var5 = null;
         var1.setline(61);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("glob_to_re").__call__(var2, var1.getlocal(2)), var1.getlocal(3));
      }
   }

   public PyObject test_process_template_line$6(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getglobal("make_local_path");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(69);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("foo.tmp"), PyString.fromInterned("ok"), PyString.fromInterned("xo"), PyString.fromInterned("four.txt"), PyString.fromInterned("buildout.cfg"), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".hg/last-message.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/one.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/two.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/files.x")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/here.tmp")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/o/f.oo")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir/graft-one")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir/dir2/graft2")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir3/ok")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir3/sub/ok.txt"))});
      var1.getlocal(1).__setattr__((String)"allfiles", var6);
      var3 = null;
      var1.setline(85);
      var3 = var1.getglobal("MANIFEST_IN").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(85);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(90);
            var6 = new PyList(new PyObject[]{PyString.fromInterned("ok"), PyString.fromInterned("buildout.cfg"), PyString.fromInterned("four.txt"), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".hg/last-message.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/one.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global/two.txt")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/o/f.oo")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir/graft-one")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir/dir2/graft2"))});
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(101);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("files"), var1.getlocal(4));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(86);
         PyObject var5 = var1.getlocal(3).__getattr__("strip").__call__(var2);
         PyObject var10000 = var5._eq(PyString.fromInterned(""));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(88);
            var1.getlocal(1).__getattr__("process_template_line").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject test_debug_print$7(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(104);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var9;
      PyObject var4 = (var9 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label38: {
         try {
            var1.setlocal(2, var4);
            var1.setline(106);
            var1.getlocal(1).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
         } catch (Throwable var8) {
            if (var9.__exit__(var2, Py.setException(var8, var1))) {
               break label38;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.setline(107);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(109);
      var3 = var1.getglobal("True");
      var1.getglobal("debug").__setattr__("DEBUG", var3);
      var3 = null;
      var3 = null;

      try {
         ContextManager var10;
         PyObject var5 = (var10 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

         label28: {
            try {
               var1.setlocal(2, var5);
               var1.setline(112);
               var1.getlocal(1).__getattr__("debug_print").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"));
            } catch (Throwable var6) {
               if (var10.__exit__(var2, Py.setException(var6, var1))) {
                  break label28;
               }

               throw (Throwable)Py.makeException();
            }

            var10.__exit__(var2, (PyException)null);
         }

         var1.setline(113);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("xxx\n"));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(115);
         var4 = var1.getglobal("False");
         var1.getglobal("debug").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(115);
      var4 = var1.getglobal("False");
      var1.getglobal("debug").__setattr__("DEBUG", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_set_allfiles$8(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(119);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c")});
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(120);
      var1.getlocal(1).__getattr__("set_allfiles").__call__(var2, var1.getlocal(2));
      var1.setline(121);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("allfiles"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_remove_duplicates$9(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("a"), PyString.fromInterned("g"), PyString.fromInterned("c"), PyString.fromInterned("g")});
      var1.getlocal(1).__setattr__((String)"files", var4);
      var3 = null;
      var1.setline(127);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(128);
      var1.getlocal(1).__getattr__("remove_duplicates").__call__(var2);
      var1.setline(129);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c"), PyString.fromInterned("g")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_translate_pattern$10(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyObject var10002 = var1.getglobal("hasattr");
      PyObject var10004 = var1.getglobal("translate_pattern");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("a"), var1.getglobal("True"), var1.getglobal("False")};
      String[] var4 = new String[]{"anchor", "is_regex"};
      var10004 = var10004.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("search")));
      var1.setline(138);
      PyObject var5 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a"));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(139);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getglobal("translate_pattern");
      var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("True"), var1.getglobal("True")};
      var4 = new String[]{"anchor", "is_regex"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(1));
      var1.setline(144);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var10002 = var1.getglobal("hasattr");
      var10004 = var1.getglobal("translate_pattern");
      var3 = new PyObject[]{PyString.fromInterned("a"), var1.getglobal("True"), var1.getglobal("True")};
      var4 = new String[]{"anchor", "is_regex"};
      var10004 = var10004.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("search")));
      var1.setline(149);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var10002 = var1.getglobal("translate_pattern");
      var3 = new PyObject[]{PyString.fromInterned("*.py"), var1.getglobal("True"), var1.getglobal("False")};
      var4 = new String[]{"anchor", "is_regex"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002.__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filelist.py")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_exclude_pattern$11(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(155);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("exclude_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*.py")));
      var1.setline(158);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(159);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.py")});
      var1.getlocal(1).__setattr__((String)"files", var4);
      var3 = null;
      var1.setline(160);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("exclude_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*.py")));
      var1.setline(163);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(164);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("a.txt")});
      var1.getlocal(1).__setattr__((String)"files", var4);
      var3 = null;
      var1.setline(165);
      var1.getlocal(1).__getattr__("exclude_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*.py"));
      var1.setline(166);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.txt")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_include_pattern$12(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(171);
      var1.getlocal(1).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(172);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("include_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*.py")));
      var1.setline(175);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(176);
      var1.getlocal(1).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt")})));
      var1.setline(177);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("include_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*.py")));
      var1.setline(180);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(181);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getlocal(1).__getattr__("allfiles"));
      var1.setline(182);
      var1.getlocal(1).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt")})));
      var1.setline(183);
      var1.getlocal(1).__getattr__("include_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*"));
      var1.setline(184);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("allfiles"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_process_template$13(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyObject var3 = var1.getglobal("make_local_path");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getglobal("FileList").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(190);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("include"), PyString.fromInterned("exclude"), PyString.fromInterned("global-include"), PyString.fromInterned("global-exclude"), PyString.fromInterned("recursive-include"), PyString.fromInterned("recursive-exclude"), PyString.fromInterned("graft"), PyString.fromInterned("prune"), PyString.fromInterned("blarg")})).__iter__();

      while(true) {
         var1.setline(190);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(197);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(198);
            var1.getlocal(2).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(200);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include *.py"));
            var1.setline(201);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py")})));
            var1.setline(202);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(204);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include *.rb"));
            var1.setline(205);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py")})));
            var1.setline(206);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(209);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(210);
            PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))});
            var1.getlocal(2).__setattr__((String)"files", var5);
            var3 = null;
            var1.setline(212);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exclude *.py"));
            var1.setline(213);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(214);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(216);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exclude *.rb"));
            var1.setline(217);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(218);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(221);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(222);
            var1.getlocal(2).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(224);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global-include *.py"));
            var1.setline(225);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(226);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(228);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global-include *.rb"));
            var1.setline(229);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))})));
            var1.setline(230);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(233);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(234);
            var5 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), PyString.fromInterned("b.txt"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.py"))});
            var1.getlocal(2).__setattr__((String)"files", var5);
            var3 = null;
            var1.setline(236);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global-exclude *.py"));
            var1.setline(237);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("b.txt")})));
            var1.setline(238);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(240);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("global-exclude *.rb"));
            var1.setline(241);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("b.txt")})));
            var1.setline(242);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(245);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(246);
            var1.getlocal(2).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.txt")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))})));
            var1.setline(249);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("recursive-include d *.py"));
            var1.setline(250);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))})));
            var1.setline(251);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(253);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("recursive-include e *.py"));
            var1.setline(254);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))})));
            var1.setline(255);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(258);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(259);
            var5 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.txt")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))});
            var1.getlocal(2).__setattr__((String)"files", var5);
            var3 = null;
            var1.setline(261);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("recursive-exclude d *.py"));
            var1.setline(262);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.txt"))})));
            var1.setline(263);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(265);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("recursive-exclude e *.py"));
            var1.setline(266);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/c.txt"))})));
            var1.setline(267);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(270);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(271);
            var1.getlocal(2).__getattr__("set_allfiles").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/f.py"))})));
            var1.setline(274);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("graft d"));
            var1.setline(275);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))})));
            var1.setline(276);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(278);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("graft e"));
            var1.setline(279);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py"))})));
            var1.setline(280);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.setline(283);
            var3 = var1.getglobal("FileList").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(284);
            var5 = new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/b.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d/d/e.py")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/f.py"))});
            var1.getlocal(2).__setattr__((String)"files", var5);
            var3 = null;
            var1.setline(286);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prune d"));
            var1.setline(287);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/f.py"))})));
            var1.setline(288);
            var1.getlocal(0).__getattr__("assertNoWarnings").__call__(var2);
            var1.setline(290);
            var1.getlocal(2).__getattr__("process_template_line").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prune e"));
            var1.setline(291);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("files"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a.py"), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f/f.py"))})));
            var1.setline(292);
            var1.getlocal(0).__getattr__("assertWarnings").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(193);
         var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsTemplateError"), var1.getlocal(2).__getattr__("process_template_line"), var1.getlocal(3));
      }
   }

   public PyObject test_suite$14(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("FileListTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_filelist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      make_local_path$1 = Py.newCode(1, var2, var1, "make_local_path", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FileListTestCase$2 = Py.newCode(0, var2, var1, "FileListTestCase", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      assertNoWarnings$3 = Py.newCode(1, var2, var1, "assertNoWarnings", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      assertWarnings$4 = Py.newCode(1, var2, var1, "assertWarnings", 41, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep", "glob", "regex"};
      test_glob_to_re$5 = Py.newCode(1, var2, var1, "test_glob_to_re", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list", "l", "line", "wanted"};
      test_process_template_line$6 = Py.newCode(1, var2, var1, "test_process_template_line", 63, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list", "stdout"};
      test_debug_print$7 = Py.newCode(1, var2, var1, "test_debug_print", 103, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list", "files"};
      test_set_allfiles$8 = Py.newCode(1, var2, var1, "test_set_allfiles", 117, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list"};
      test_remove_duplicates$9 = Py.newCode(1, var2, var1, "test_remove_duplicates", 123, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "regex"};
      test_translate_pattern$10 = Py.newCode(1, var2, var1, "test_translate_pattern", 131, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list"};
      test_exclude_pattern$11 = Py.newCode(1, var2, var1, "test_exclude_pattern", 152, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file_list"};
      test_include_pattern$12 = Py.newCode(1, var2, var1, "test_include_pattern", 168, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "file_list", "action"};
      test_process_template$13 = Py.newCode(1, var2, var1, "test_process_template", 186, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$14 = Py.newCode(0, var2, var1, "test_suite", 295, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_filelist$py("distutils/tests/test_filelist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_filelist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.make_local_path$1(var2, var3);
         case 2:
            return this.FileListTestCase$2(var2, var3);
         case 3:
            return this.assertNoWarnings$3(var2, var3);
         case 4:
            return this.assertWarnings$4(var2, var3);
         case 5:
            return this.test_glob_to_re$5(var2, var3);
         case 6:
            return this.test_process_template_line$6(var2, var3);
         case 7:
            return this.test_debug_print$7(var2, var3);
         case 8:
            return this.test_set_allfiles$8(var2, var3);
         case 9:
            return this.test_remove_duplicates$9(var2, var3);
         case 10:
            return this.test_translate_pattern$10(var2, var3);
         case 11:
            return this.test_exclude_pattern$11(var2, var3);
         case 12:
            return this.test_include_pattern$12(var2, var3);
         case 13:
            return this.test_process_template$13(var2, var3);
         case 14:
            return this.test_suite$14(var2, var3);
         default:
            return null;
      }
   }
}
