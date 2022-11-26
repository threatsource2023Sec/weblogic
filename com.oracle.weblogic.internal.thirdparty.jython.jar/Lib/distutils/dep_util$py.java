package distutils;

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
@Filename("distutils/dep_util.py")
public class dep_util$py extends PyFunctionTable implements PyRunnable {
   static dep_util$py self;
   static final PyCode f$0;
   static final PyCode newer$1;
   static final PyCode newer_pairwise$2;
   static final PyCode newer_group$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.dep_util\n\nUtility functions for simple, timestamp-based dependency of files\nand groups of files; also, function based entirely on such\ntimestamp dependency analysis."));
      var1.setline(5);
      PyString.fromInterned("distutils.dep_util\n\nUtility functions for simple, timestamp-based dependency of files\nand groups of files; also, function based entirely on such\ntimestamp dependency analysis.");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"ST_MTIME"};
      PyObject[] var7 = imp.importFrom("stat", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("ST_MTIME", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"DistutilsFileError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var1.setline(13);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, newer$1, PyString.fromInterned("Tells if the target is newer than the source.\n\n    Return true if 'source' exists and is more recently modified than\n    'target', or if 'source' exists and 'target' doesn't.\n\n    Return false if both exist and 'target' is the same age or younger\n    than 'source'. Raise DistutilsFileError if 'source' does not exist.\n\n    Note that this test is not very accurate: files created in the same second\n    will have the same \"age\".\n    "));
      var1.setlocal("newer", var8);
      var3 = null;
      var1.setline(33);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, newer_pairwise$2, PyString.fromInterned("Walk two filename lists in parallel, testing if each source is newer\n    than its corresponding target.  Return a pair of lists (sources,\n    targets) where source is newer than target, according to the semantics\n    of 'newer()'.\n    "));
      var1.setlocal("newer_pairwise", var8);
      var3 = null;
      var1.setline(52);
      var7 = new PyObject[]{PyString.fromInterned("error")};
      var8 = new PyFunction(var1.f_globals, var7, newer_group$3, PyString.fromInterned("Return true if 'target' is out-of-date with respect to any file\n    listed in 'sources'.\n\n    In other words, if 'target' exists and is newer\n    than every file in 'sources', return false; otherwise return true.\n    'missing' controls what we do when a source file is missing; the\n    default (\"error\") is to blow up with an OSError from inside 'stat()';\n    if it is \"ignore\", we silently drop any missing source files; if it is\n    \"newer\", any missing source files make us assume that 'target' is\n    out-of-date (this is handy in \"dry-run\" mode: it'll make you pretend to\n    carry out commands that wouldn't work because inputs are missing, but\n    that doesn't matter because you're not actually going to run the\n    commands).\n    "));
      var1.setlocal("newer_group", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject newer$1(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyString.fromInterned("Tells if the target is newer than the source.\n\n    Return true if 'source' exists and is more recently modified than\n    'target', or if 'source' exists and 'target' doesn't.\n\n    Return false if both exist and 'target' is the same age or younger\n    than 'source'. Raise DistutilsFileError if 'source' does not exist.\n\n    Note that this test is not very accurate: files created in the same second\n    will have the same \"age\".\n    ");
      var1.setline(25);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(26);
         throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("file '%s' does not exist")._mod(var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(28);
         PyObject var3;
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(29);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(31);
            PyObject var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getitem__(var1.getglobal("ST_MTIME"));
            PyObject var10000 = var4._gt(var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1)).__getitem__(var1.getglobal("ST_MTIME")));
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject newer_pairwise$2(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("Walk two filename lists in parallel, testing if each source is newer\n    than its corresponding target.  Return a pair of lists (sources,\n    targets) where source is newer than target, according to the semantics\n    of 'newer()'.\n    ");
      var1.setline(39);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("'sources' and 'targets' must be same length"));
      } else {
         var1.setline(43);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(44);
         var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(45);
         var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(45);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(50);
               PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var8;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(46);
            if (var1.getglobal("newer").__call__(var2, var1.getlocal(4), var1.getlocal(5)).__nonzero__()) {
               var1.setline(47);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
               var1.setline(48);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject newer_group$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Return true if 'target' is out-of-date with respect to any file\n    listed in 'sources'.\n\n    In other words, if 'target' exists and is newer\n    than every file in 'sources', return false; otherwise return true.\n    'missing' controls what we do when a source file is missing; the\n    default (\"error\") is to blow up with an OSError from inside 'stat()';\n    if it is \"ignore\", we silently drop any missing source files; if it is\n    \"newer\", any missing source files make us assume that 'target' is\n    out-of-date (this is handy in \"dry-run\" mode: it'll make you pretend to\n    carry out commands that wouldn't work because inputs are missing, but\n    that doesn't matter because you're not actually going to run the\n    commands).\n    ");
      var1.setline(68);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(69);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(75);
         PyObject var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1)).__getitem__(var1.getglobal("ST_MTIME"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(77);
         var4 = var1.getlocal(0).__iter__();

         PyObject var10000;
         do {
            PyObject var6;
            while(true) {
               var1.setline(77);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(89);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(78);
               if (!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(79);
               var6 = var1.getlocal(2);
               var10000 = var6._eq(PyString.fromInterned("error"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(80);
                  break;
               }

               var1.setline(81);
               var6 = var1.getlocal(2);
               var10000 = var6._eq(PyString.fromInterned("ignore"));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(83);
                  var6 = var1.getlocal(2);
                  var10000 = var6._eq(PyString.fromInterned("newer"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(84);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }
                  break;
               }
            }

            var1.setline(86);
            var6 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(4)).__getitem__(var1.getglobal("ST_MTIME"));
            var10000 = var6._gt(var1.getlocal(3));
            var6 = null;
         } while(!var10000.__nonzero__());

         var1.setline(87);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public dep_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"source", "target"};
      newer$1 = Py.newCode(2, var2, var1, "newer", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sources", "targets", "n_sources", "n_targets", "source", "target"};
      newer_pairwise$2 = Py.newCode(2, var2, var1, "newer_pairwise", 33, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sources", "target", "missing", "target_mtime", "source"};
      newer_group$3 = Py.newCode(3, var2, var1, "newer_group", 52, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dep_util$py("distutils/dep_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dep_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.newer$1(var2, var3);
         case 2:
            return this.newer_pairwise$2(var2, var3);
         case 3:
            return this.newer_group$3(var2, var3);
         default:
            return null;
      }
   }
}
