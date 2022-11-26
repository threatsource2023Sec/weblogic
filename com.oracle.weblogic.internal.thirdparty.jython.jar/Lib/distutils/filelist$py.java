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
@Filename("distutils/filelist.py")
public class filelist$py extends PyFunctionTable implements PyRunnable {
   static filelist$py self;
   static final PyCode f$0;
   static final PyCode FileList$1;
   static final PyCode __init__$2;
   static final PyCode set_allfiles$3;
   static final PyCode findall$4;
   static final PyCode debug_print$5;
   static final PyCode append$6;
   static final PyCode extend$7;
   static final PyCode sort$8;
   static final PyCode remove_duplicates$9;
   static final PyCode _parse_template_line$10;
   static final PyCode process_template_line$11;
   static final PyCode include_pattern$12;
   static final PyCode exclude_pattern$13;
   static final PyCode findall$14;
   static final PyCode glob_to_re$15;
   static final PyCode translate_pattern$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.filelist\n\nProvides the FileList class, used for poking about the filesystem\nand building lists of files.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.filelist\n\nProvides the FileList class, used for poking about the filesystem\nand building lists of files.\n");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(10);
      var5 = imp.importOne("fnmatch", var1, -1);
      var1.setlocal("fnmatch", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"convert_path"};
      PyObject[] var7 = imp.importFrom("distutils.util", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"DistutilsTemplateError", "DistutilsInternalError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsTemplateError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsInternalError", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(15);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("FileList", var7, FileList$1);
      var1.setlocal("FileList", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(256);
      var7 = new PyObject[]{var1.getname("os").__getattr__("curdir")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, findall$14, PyString.fromInterned("Find all files under 'dir' and return the list of full filenames\n    (relative to 'dir').\n    "));
      var1.setlocal("findall", var8);
      var3 = null;
      var1.setline(288);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, glob_to_re$15, PyString.fromInterned("Translate a shell-like glob pattern to a regular expression.\n\n    Return a string containing the regex.  Differs from\n    'fnmatch.translate()' in that '*' does not match \"special characters\"\n    (which are platform-specific).\n    "));
      var1.setlocal("glob_to_re", var8);
      var3 = null;
      var1.setline(312);
      var7 = new PyObject[]{Py.newInteger(1), var1.getname("None"), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, translate_pattern$16, PyString.fromInterned("Translate a shell-like wildcard pattern to a compiled regular\n    expression.\n\n    Return the compiled regex.  If 'is_regex' true,\n    then 'pattern' is directly compiled to a regex (if it's a string)\n    or just returned as-is (assumes it's a regex object).\n    "));
      var1.setlocal("translate_pattern", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FileList$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A list of files built by on exploring the filesystem and filtered by\n    applying various patterns to what we find there.\n\n    Instance attributes:\n      dir\n        directory from which files will be taken -- only used if\n        'allfiles' not supplied to constructor\n      files\n        list of filenames currently being built/filtered/manipulated\n      allfiles\n        complete list of files under consideration (ie. without any\n        filtering applied)\n    "));
      var1.setline(28);
      PyString.fromInterned("A list of files built by on exploring the filesystem and filtered by\n    applying various patterns to what we find there.\n\n    Instance attributes:\n      dir\n        directory from which files will be taken -- only used if\n        'allfiles' not supplied to constructor\n      files\n        list of filenames currently being built/filtered/manipulated\n      allfiles\n        complete list of files under consideration (ie. without any\n        filtering applied)\n    ");
      var1.setline(30);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_allfiles$3, (PyObject)null);
      var1.setlocal("set_allfiles", var4);
      var3 = null;
      var1.setline(39);
      var3 = new PyObject[]{var1.getname("os").__getattr__("curdir")};
      var4 = new PyFunction(var1.f_globals, var3, findall$4, (PyObject)null);
      var1.setlocal("findall", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug_print$5, PyString.fromInterned("Print 'msg' to stdout if the global DEBUG (taken from the\n        DISTUTILS_DEBUG environment variable) flag is true.\n        "));
      var1.setlocal("debug_print", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$6, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, extend$7, (PyObject)null);
      var1.setlocal("extend", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sort$8, (PyObject)null);
      var1.setlocal("sort", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_duplicates$9, (PyObject)null);
      var1.setlocal("remove_duplicates", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_template_line$10, (PyObject)null);
      var1.setlocal("_parse_template_line", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process_template_line$11, (PyObject)null);
      var1.setlocal("process_template_line", var4);
      var3 = null;
      var1.setline(187);
      var3 = new PyObject[]{Py.newInteger(1), var1.getname("None"), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, include_pattern$12, PyString.fromInterned("Select strings (presumably filenames) from 'self.files' that\n        match 'pattern', a Unix-style wildcard (glob) pattern.\n\n        Patterns are not quite the same as implemented by the 'fnmatch'\n        module: '*' and '?'  match non-special characters, where \"special\"\n        is platform-dependent: slash on Unix; colon, slash, and backslash on\n        DOS/Windows; and colon on Mac OS.\n\n        If 'anchor' is true (the default), then the pattern match is more\n        stringent: \"*.py\" will match \"foo.py\" but not \"foo/bar.py\".  If\n        'anchor' is false, both of these will match.\n\n        If 'prefix' is supplied, then only filenames starting with 'prefix'\n        (itself a pattern) and ending with 'pattern', with anything in between\n        them, will match.  'anchor' is ignored in this case.\n\n        If 'is_regex' is true, 'anchor' and 'prefix' are ignored, and\n        'pattern' is assumed to be either a string containing a regex or a\n        regex object -- no translation is done, the regex is just compiled\n        and used as-is.\n\n        Selected strings will be added to self.files.\n\n        Return 1 if files are found.\n        "));
      var1.setlocal("include_pattern", var4);
      var3 = null;
      var1.setline(232);
      var3 = new PyObject[]{Py.newInteger(1), var1.getname("None"), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, exclude_pattern$13, PyString.fromInterned("Remove strings (presumably filenames) from 'files' that match\n        'pattern'.\n\n        Other parameters are the same as for 'include_pattern()', above.\n        The list 'self.files' is modified in place. Return 1 if files are\n        found.\n        "));
      var1.setlocal("exclude_pattern", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("allfiles", var3);
      var3 = null;
      var1.setline(34);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"files", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_allfiles$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("allfiles", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject findall$4(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getglobal("findall").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("allfiles", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug_print$5(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Print 'msg' to stdout if the global DEBUG (taken from the\n        DISTUTILS_DEBUG environment variable) flag is true.\n        ");
      var1.setline(46);
      String[] var3 = new String[]{"DEBUG"};
      PyObject[] var5 = imp.importFrom("distutils.debug", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(47);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(48);
         Py.println(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$6(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      var1.getlocal(0).__getattr__("files").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extend$7(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      var1.getlocal(0).__getattr__("files").__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sort$8(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = var1.getglobal("map").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("split"), var1.getlocal(0).__getattr__("files"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(61);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(62);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"files", var7);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(63);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(64);
         PyObject var10000 = var1.getlocal(0).__getattr__("files").__getattr__("append");
         PyObject var10002 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         PyObject[] var5 = Py.EmptyObjects;
         String[] var6 = new String[0];
         var10002 = var10002._callextra(var5, var6, var1.getlocal(2), (PyObject)null);
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject remove_duplicates$9(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getglobal("range").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("files"))._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)).__iter__();

      while(true) {
         var1.setline(71);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(72);
         PyObject var5 = var1.getlocal(0).__getattr__("files").__getitem__(var1.getlocal(1));
         PyObject var10000 = var5._eq(var1.getlocal(0).__getattr__("files").__getitem__(var1.getlocal(1)._sub(Py.newInteger(1))));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(73);
            var1.getlocal(0).__getattr__("files").__delitem__(var1.getlocal(1));
         }
      }
   }

   public PyObject _parse_template_line$10(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var1.setlocal(5, var3);
      var1.setlocal(6, var3);
      var1.setline(84);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("include"), PyString.fromInterned("exclude"), PyString.fromInterned("global-include"), PyString.fromInterned("global-exclude")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._lt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(87);
            throw Py.makeException(var1.getglobal("DistutilsTemplateError"), PyString.fromInterned("'%s' expects <pattern1> <pattern2> ...")._mod(var1.getlocal(3)));
         }

         var1.setline(90);
         var3 = var1.getglobal("map").__call__(var2, var1.getglobal("convert_path"), var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(92);
         var3 = var1.getlocal(3);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("recursive-include"), PyString.fromInterned("recursive-exclude")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(93);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._lt(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(94);
               throw Py.makeException(var1.getglobal("DistutilsTemplateError"), PyString.fromInterned("'%s' expects <dir> <pattern1> <pattern2> ...")._mod(var1.getlocal(3)));
            }

            var1.setline(97);
            var3 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(98);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("convert_path"), var1.getlocal(2).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(100);
            var3 = var1.getlocal(3);
            var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("graft"), PyString.fromInterned("prune")}));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(108);
               throw Py.makeException(var1.getglobal("DistutilsTemplateError"), PyString.fromInterned("unknown action '%s'")._mod(var1.getlocal(3)));
            }

            var1.setline(101);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._ne(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(102);
               throw Py.makeException(var1.getglobal("DistutilsTemplateError"), PyString.fromInterned("'%s' expects a single <dir_pattern>")._mod(var1.getlocal(3)));
            }

            var1.setline(105);
            var3 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)));
            var1.setlocal(6, var3);
            var3 = null;
         }
      }

      var1.setline(110);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject process_template_line$11(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getattr__("_parse_template_line").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(123);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("include"));
      var3 = null;
      String[] var6;
      PyObject var7;
      PyObject[] var9;
      if (var10000.__nonzero__()) {
         var1.setline(124);
         var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("include ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))));
         var1.setline(125);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(125);
            var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(6, var7);
            var1.setline(126);
            var10000 = var1.getlocal(0).__getattr__("include_pattern");
            var9 = new PyObject[]{var1.getlocal(6), Py.newInteger(1)};
            var6 = new String[]{"anchor"};
            var10000 = var10000.__call__(var2, var9, var6);
            var5 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(127);
               var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: no files found matching '%s'"), (PyObject)var1.getlocal(6));
            }
         }
      } else {
         var1.setline(130);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("exclude"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("exclude ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))));
            var1.setline(132);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(132);
               var7 = var3.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(6, var7);
               var1.setline(133);
               var10000 = var1.getlocal(0).__getattr__("exclude_pattern");
               var9 = new PyObject[]{var1.getlocal(6), Py.newInteger(1)};
               var6 = new String[]{"anchor"};
               var10000 = var10000.__call__(var2, var9, var6);
               var5 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(134);
                  var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: no previously-included files found matching '%s'"), (PyObject)var1.getlocal(6));
               }
            }
         } else {
            var1.setline(137);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("global-include"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(138);
               var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("global-include ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))));
               var1.setline(139);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(139);
                  var7 = var3.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  var1.setlocal(6, var7);
                  var1.setline(140);
                  var10000 = var1.getlocal(0).__getattr__("include_pattern");
                  var9 = new PyObject[]{var1.getlocal(6), Py.newInteger(0)};
                  var6 = new String[]{"anchor"};
                  var10000 = var10000.__call__(var2, var9, var6);
                  var5 = null;
                  if (var10000.__not__().__nonzero__()) {
                     var1.setline(141);
                     var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("warning: no files found matching '%s' ")._add(PyString.fromInterned("anywhere in distribution")), var1.getlocal(6));
                  }
               }
            } else {
               var1.setline(144);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(PyString.fromInterned("global-exclude"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(145);
                  var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("global-exclude ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))));
                  var1.setline(146);
                  var3 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(146);
                     var7 = var3.__iternext__();
                     if (var7 == null) {
                        break;
                     }

                     var1.setlocal(6, var7);
                     var1.setline(147);
                     var10000 = var1.getlocal(0).__getattr__("exclude_pattern");
                     var9 = new PyObject[]{var1.getlocal(6), Py.newInteger(0)};
                     var6 = new String[]{"anchor"};
                     var10000 = var10000.__call__(var2, var9, var6);
                     var5 = null;
                     if (var10000.__not__().__nonzero__()) {
                        var1.setline(148);
                        var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: no previously-included files matching '%s' found anywhere in distribution"), (PyObject)var1.getlocal(6));
                     }
                  }
               } else {
                  var1.setline(152);
                  var3 = var1.getlocal(2);
                  var10000 = var3._eq(PyString.fromInterned("recursive-include"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(153);
                     var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("recursive-include %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))})));
                     var1.setline(155);
                     var3 = var1.getlocal(3).__iter__();

                     while(true) {
                        var1.setline(155);
                        var7 = var3.__iternext__();
                        if (var7 == null) {
                           break;
                        }

                        var1.setlocal(6, var7);
                        var1.setline(156);
                        var10000 = var1.getlocal(0).__getattr__("include_pattern");
                        var9 = new PyObject[]{var1.getlocal(6), var1.getlocal(4)};
                        var6 = new String[]{"prefix"};
                        var10000 = var10000.__call__(var2, var9, var6);
                        var5 = null;
                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(157);
                           var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("warning: no files found matching '%s' ")._add(PyString.fromInterned("under directory '%s'")), var1.getlocal(6), var1.getlocal(4));
                        }
                     }
                  } else {
                     var1.setline(161);
                     var3 = var1.getlocal(2);
                     var10000 = var3._eq(PyString.fromInterned("recursive-exclude"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(162);
                        var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("recursive-exclude %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))})));
                        var1.setline(164);
                        var3 = var1.getlocal(3).__iter__();

                        while(true) {
                           var1.setline(164);
                           var7 = var3.__iternext__();
                           if (var7 == null) {
                              break;
                           }

                           var1.setlocal(6, var7);
                           var1.setline(165);
                           var10000 = var1.getlocal(0).__getattr__("exclude_pattern");
                           var9 = new PyObject[]{var1.getlocal(6), var1.getlocal(4)};
                           var6 = new String[]{"prefix"};
                           var10000 = var10000.__call__(var2, var9, var6);
                           var5 = null;
                           if (var10000.__not__().__nonzero__()) {
                              var1.setline(166);
                              var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("warning: no previously-included files matching '%s' found under directory '%s'"), (PyObject)var1.getlocal(6), (PyObject)var1.getlocal(4));
                           }
                        }
                     } else {
                        var1.setline(170);
                        var3 = var1.getlocal(2);
                        var10000 = var3._eq(PyString.fromInterned("graft"));
                        var3 = null;
                        String[] var8;
                        PyObject[] var10;
                        if (var10000.__nonzero__()) {
                           var1.setline(171);
                           var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("graft ")._add(var1.getlocal(5)));
                           var1.setline(172);
                           var10000 = var1.getlocal(0).__getattr__("include_pattern");
                           var10 = new PyObject[]{var1.getglobal("None"), var1.getlocal(5)};
                           var8 = new String[]{"prefix"};
                           var10000 = var10000.__call__(var2, var10, var8);
                           var3 = null;
                           if (var10000.__not__().__nonzero__()) {
                              var1.setline(173);
                              var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("warning: no directories found matching '%s'"), (PyObject)var1.getlocal(5));
                           }
                        } else {
                           var1.setline(176);
                           var3 = var1.getlocal(2);
                           var10000 = var3._eq(PyString.fromInterned("prune"));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(182);
                              throw Py.makeException(var1.getglobal("DistutilsInternalError"), PyString.fromInterned("this cannot happen: invalid action '%s'")._mod(var1.getlocal(2)));
                           }

                           var1.setline(177);
                           var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("prune ")._add(var1.getlocal(5)));
                           var1.setline(178);
                           var10000 = var1.getlocal(0).__getattr__("exclude_pattern");
                           var10 = new PyObject[]{var1.getglobal("None"), var1.getlocal(5)};
                           var8 = new String[]{"prefix"};
                           var10000 = var10000.__call__(var2, var10, var8);
                           var3 = null;
                           if (var10000.__not__().__nonzero__()) {
                              var1.setline(179);
                              var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("no previously-included directories found ")._add(PyString.fromInterned("matching '%s'")), var1.getlocal(5));
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject include_pattern$12(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("Select strings (presumably filenames) from 'self.files' that\n        match 'pattern', a Unix-style wildcard (glob) pattern.\n\n        Patterns are not quite the same as implemented by the 'fnmatch'\n        module: '*' and '?'  match non-special characters, where \"special\"\n        is platform-dependent: slash on Unix; colon, slash, and backslash on\n        DOS/Windows; and colon on Mac OS.\n\n        If 'anchor' is true (the default), then the pattern match is more\n        stringent: \"*.py\" will match \"foo.py\" but not \"foo/bar.py\".  If\n        'anchor' is false, both of these will match.\n\n        If 'prefix' is supplied, then only filenames starting with 'prefix'\n        (itself a pattern) and ending with 'pattern', with anything in between\n        them, will match.  'anchor' is ignored in this case.\n\n        If 'is_regex' is true, 'anchor' and 'prefix' are ignored, and\n        'pattern' is assumed to be either a string containing a regex or a\n        regex object -- no translation is done, the regex is just compiled\n        and used as-is.\n\n        Selected strings will be added to self.files.\n\n        Return 1 if files are found.\n        ");
      var1.setline(214);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(215);
      PyObject var6 = var1.getglobal("translate_pattern").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(216);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("include_pattern: applying regex r'%s'")._mod(var1.getlocal(6).__getattr__("pattern")));
      var1.setline(220);
      var6 = var1.getlocal(0).__getattr__("allfiles");
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(221);
         var1.getlocal(0).__getattr__("findall").__call__(var2);
      }

      var1.setline(223);
      var6 = var1.getlocal(0).__getattr__("allfiles").__iter__();

      while(true) {
         var1.setline(223);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(229);
            var6 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(7, var4);
         var1.setline(224);
         if (var1.getlocal(6).__getattr__("search").__call__(var2, var1.getlocal(7)).__nonzero__()) {
            var1.setline(225);
            var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned(" adding ")._add(var1.getlocal(7)));
            var1.setline(226);
            var1.getlocal(0).__getattr__("files").__getattr__("append").__call__(var2, var1.getlocal(7));
            var1.setline(227);
            PyInteger var5 = Py.newInteger(1);
            var1.setlocal(5, var5);
            var5 = null;
         }
      }
   }

   public PyObject exclude_pattern$13(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Remove strings (presumably filenames) from 'files' that match\n        'pattern'.\n\n        Other parameters are the same as for 'include_pattern()', above.\n        The list 'self.files' is modified in place. Return 1 if files are\n        found.\n        ");
      var1.setline(240);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(241);
      PyObject var6 = var1.getglobal("translate_pattern").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(242);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("exclude_pattern: applying regex r'%s'")._mod(var1.getlocal(6).__getattr__("pattern")));
      var1.setline(244);
      var6 = var1.getglobal("range").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("files"))._sub(Py.newInteger(1)), (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(-1)).__iter__();

      while(true) {
         var1.setline(244);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(250);
            var6 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(7, var4);
         var1.setline(245);
         if (var1.getlocal(6).__getattr__("search").__call__(var2, var1.getlocal(0).__getattr__("files").__getitem__(var1.getlocal(7))).__nonzero__()) {
            var1.setline(246);
            var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned(" removing ")._add(var1.getlocal(0).__getattr__("files").__getitem__(var1.getlocal(7))));
            var1.setline(247);
            var1.getlocal(0).__getattr__("files").__delitem__(var1.getlocal(7));
            var1.setline(248);
            PyInteger var5 = Py.newInteger(1);
            var1.setlocal(5, var5);
            var5 = null;
         }
      }
   }

   public PyObject findall$14(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyString.fromInterned("Find all files under 'dir' and return the list of full filenames\n    (relative to 'dir').\n    ");
      var1.setline(260);
      String[] var3 = new String[]{"ST_MODE", "S_ISREG", "S_ISDIR", "S_ISLNK"};
      PyObject[] var6 = imp.importFrom("stat", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal(2, var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal(3, var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(262);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(263);
      var7 = new PyList(new PyObject[]{var1.getlocal(0)});
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(264);
      PyObject var8 = var1.getlocal(6).__getattr__("pop");
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(265);
      var8 = var1.getlocal(6).__getattr__("append");
      var1.setlocal(8, var8);
      var3 = null;

      while(true) {
         var1.setline(267);
         if (!var1.getlocal(6).__nonzero__()) {
            var1.setline(285);
            var8 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setline(268);
         var8 = var1.getlocal(7).__call__(var2);
         var1.setlocal(0, var8);
         var3 = null;
         var1.setline(269);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(9, var8);
         var3 = null;
         var1.setline(271);
         var8 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(271);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(10, var4);
            var1.setline(272);
            PyObject var5 = var1.getlocal(0);
            PyObject var10000 = var5._ne(var1.getglobal("os").__getattr__("curdir"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(273);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(10));
               var1.setlocal(11, var5);
               var5 = null;
            } else {
               var1.setline(275);
               var5 = var1.getlocal(10);
               var1.setlocal(11, var5);
               var5 = null;
            }

            var1.setline(278);
            var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(11));
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(279);
            var5 = var1.getlocal(12).__getitem__(var1.getlocal(1));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(280);
            if (var1.getlocal(2).__call__(var2, var1.getlocal(13)).__nonzero__()) {
               var1.setline(281);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(11));
            } else {
               var1.setline(282);
               var10000 = var1.getlocal(3).__call__(var2, var1.getlocal(13));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4).__call__(var2, var1.getlocal(13)).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(283);
                  var1.getlocal(8).__call__(var2, var1.getlocal(11));
               }
            }
         }
      }
   }

   public PyObject glob_to_re$15(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyString.fromInterned("Translate a shell-like glob pattern to a regular expression.\n\n    Return a string containing the regex.  Differs from\n    'fnmatch.translate()' in that '*' does not match \"special characters\"\n    (which are platform-specific).\n    ");
      var1.setline(295);
      PyObject var3 = var1.getglobal("fnmatch").__getattr__("translate").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getglobal("os").__getattr__("sep");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(303);
      var3 = var1.getglobal("os").__getattr__("sep");
      PyObject var10000 = var3._eq(PyString.fromInterned("\\"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(306);
         PyString var4 = PyString.fromInterned("\\\\\\\\");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(307);
      var3 = PyString.fromInterned("\\1[^%s]")._mod(var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(308);
      var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("((?<!\\\\)(\\\\\\\\)*)\\."), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(309);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject translate_pattern$16(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("Translate a shell-like wildcard pattern to a compiled regular\n    expression.\n\n    Return the compiled regex.  If 'is_regex' true,\n    then 'pattern' is directly compiled to a regex (if it's a string)\n    or just returned as-is (assumes it's a regex object).\n    ");
      var1.setline(320);
      PyObject var3;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(321);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
            var1.setline(322);
            var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(324);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(326);
         PyObject var4;
         PyString var5;
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(327);
            var4 = var1.getglobal("glob_to_re").__call__(var2, var1.getlocal(0));
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            var1.setline(329);
            var5 = PyString.fromInterned("");
            var1.setlocal(4, var5);
            var4 = null;
         }

         var1.setline(331);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(333);
            var4 = var1.getglobal("glob_to_re").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(334);
            var4 = var1.getglobal("glob_to_re").__call__(var2, var1.getlocal(2)).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(5)).__neg__(), (PyObject)null);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(335);
            var4 = var1.getglobal("os").__getattr__("sep");
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(336);
            var4 = var1.getglobal("os").__getattr__("sep");
            var10000 = var4._eq(PyString.fromInterned("\\"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(337);
               var5 = PyString.fromInterned("\\\\");
               var1.setlocal(7, var5);
               var4 = null;
            }

            var1.setline(338);
            var4 = PyString.fromInterned("^")._add(var1.getlocal(7).__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned(".*")._add(var1.getlocal(4))}))));
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            var1.setline(340);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(341);
               var4 = PyString.fromInterned("^")._add(var1.getlocal(4));
               var1.setlocal(4, var4);
               var4 = null;
            }
         }

         var1.setline(343);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public filelist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FileList$1 = Py.newCode(0, var2, var1, "FileList", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "warn", "debug_print"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "allfiles"};
      set_allfiles$3 = Py.newCode(2, var2, var1, "set_allfiles", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      findall$4 = Py.newCode(2, var2, var1, "findall", 39, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "DEBUG"};
      debug_print$5 = Py.newCode(2, var2, var1, "debug_print", 42, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "item"};
      append$6 = Py.newCode(2, var2, var1, "append", 52, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items"};
      extend$7 = Py.newCode(2, var2, var1, "extend", 55, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sortable_files", "sort_tuple"};
      sort$8 = Py.newCode(1, var2, var1, "sort", 58, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      remove_duplicates$9 = Py.newCode(1, var2, var1, "remove_duplicates", 69, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "words", "action", "patterns", "dir", "dir_pattern"};
      _parse_template_line$10 = Py.newCode(2, var2, var1, "_parse_template_line", 78, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "action", "patterns", "dir", "dir_pattern", "pattern"};
      process_template_line$11 = Py.newCode(2, var2, var1, "process_template_line", 112, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pattern", "anchor", "prefix", "is_regex", "files_found", "pattern_re", "name"};
      include_pattern$12 = Py.newCode(5, var2, var1, "include_pattern", 187, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pattern", "anchor", "prefix", "is_regex", "files_found", "pattern_re", "i"};
      exclude_pattern$13 = Py.newCode(5, var2, var1, "exclude_pattern", 232, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dir", "ST_MODE", "S_ISREG", "S_ISDIR", "S_ISLNK", "list", "stack", "pop", "push", "names", "name", "fullname", "stat", "mode"};
      findall$14 = Py.newCode(1, var2, var1, "findall", 256, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "pattern_re", "sep", "escaped"};
      glob_to_re$15 = Py.newCode(1, var2, var1, "glob_to_re", 288, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "anchor", "prefix", "is_regex", "pattern_re", "empty_pattern", "prefix_re", "sep"};
      translate_pattern$16 = Py.newCode(4, var2, var1, "translate_pattern", 312, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new filelist$py("distutils/filelist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(filelist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FileList$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.set_allfiles$3(var2, var3);
         case 4:
            return this.findall$4(var2, var3);
         case 5:
            return this.debug_print$5(var2, var3);
         case 6:
            return this.append$6(var2, var3);
         case 7:
            return this.extend$7(var2, var3);
         case 8:
            return this.sort$8(var2, var3);
         case 9:
            return this.remove_duplicates$9(var2, var3);
         case 10:
            return this._parse_template_line$10(var2, var3);
         case 11:
            return this.process_template_line$11(var2, var3);
         case 12:
            return this.include_pattern$12(var2, var3);
         case 13:
            return this.exclude_pattern$13(var2, var3);
         case 14:
            return this.findall$14(var2, var3);
         case 15:
            return this.glob_to_re$15(var2, var3);
         case 16:
            return this.translate_pattern$16(var2, var3);
         default:
            return null;
      }
   }
}
