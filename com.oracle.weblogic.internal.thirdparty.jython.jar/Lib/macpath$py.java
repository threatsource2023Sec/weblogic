import java.util.Arrays;
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
@Filename("macpath.py")
public class macpath$py extends PyFunctionTable implements PyRunnable {
   static macpath$py self;
   static final PyCode f$0;
   static final PyCode normcase$1;
   static final PyCode isabs$2;
   static final PyCode join$3;
   static final PyCode split$4;
   static final PyCode splitext$5;
   static final PyCode splitdrive$6;
   static final PyCode dirname$7;
   static final PyCode basename$8;
   static final PyCode ismount$9;
   static final PyCode islink$10;
   static final PyCode lexists$11;
   static final PyCode expandvars$12;
   static final PyCode expanduser$13;
   static final PyCode norm_error$14;
   static final PyCode normpath$15;
   static final PyCode walk$16;
   static final PyCode abspath$17;
   static final PyCode realpath$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Pathname and path-related operations for the Macintosh."));
      var1.setline(1);
      PyString.fromInterned("Pathname and path-related operations for the Macintosh.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(5);
      imp.importAll("stat", var1, -1);
      var1.setline(6);
      var3 = imp.importOne("genericpath", var1, -1);
      var1.setlocal("genericpath", var3);
      var3 = null;
      var1.setline(7);
      imp.importAll("genericpath", var1, -1);
      var1.setline(9);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("normcase"), PyString.fromInterned("isabs"), PyString.fromInterned("join"), PyString.fromInterned("splitdrive"), PyString.fromInterned("split"), PyString.fromInterned("splitext"), PyString.fromInterned("basename"), PyString.fromInterned("dirname"), PyString.fromInterned("commonprefix"), PyString.fromInterned("getsize"), PyString.fromInterned("getmtime"), PyString.fromInterned("getatime"), PyString.fromInterned("getctime"), PyString.fromInterned("islink"), PyString.fromInterned("exists"), PyString.fromInterned("lexists"), PyString.fromInterned("isdir"), PyString.fromInterned("isfile"), PyString.fromInterned("walk"), PyString.fromInterned("expanduser"), PyString.fromInterned("expandvars"), PyString.fromInterned("normpath"), PyString.fromInterned("abspath"), PyString.fromInterned("curdir"), PyString.fromInterned("pardir"), PyString.fromInterned("sep"), PyString.fromInterned("pathsep"), PyString.fromInterned("defpath"), PyString.fromInterned("altsep"), PyString.fromInterned("extsep"), PyString.fromInterned("devnull"), PyString.fromInterned("realpath"), PyString.fromInterned("supports_unicode_filenames")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(17);
      PyString var6 = PyString.fromInterned(":");
      var1.setlocal("curdir", var6);
      var3 = null;
      var1.setline(18);
      var6 = PyString.fromInterned("::");
      var1.setlocal("pardir", var6);
      var3 = null;
      var1.setline(19);
      var6 = PyString.fromInterned(".");
      var1.setlocal("extsep", var6);
      var3 = null;
      var1.setline(20);
      var6 = PyString.fromInterned(":");
      var1.setlocal("sep", var6);
      var3 = null;
      var1.setline(21);
      var6 = PyString.fromInterned("\n");
      var1.setlocal("pathsep", var6);
      var3 = null;
      var1.setline(22);
      var6 = PyString.fromInterned(":");
      var1.setlocal("defpath", var6);
      var3 = null;
      var1.setline(23);
      var3 = var1.getname("None");
      var1.setlocal("altsep", var3);
      var3 = null;
      var1.setline(24);
      var6 = PyString.fromInterned("Dev:Null");
      var1.setlocal("devnull", var6);
      var3 = null;
      var1.setline(28);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, normcase$1, (PyObject)null);
      var1.setlocal("normcase", var8);
      var3 = null;
      var1.setline(32);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, isabs$2, PyString.fromInterned("Return true if a path is absolute.\n    On the Mac, relative paths begin with a colon,\n    but as a special case, paths with no colons at all are also relative.\n    Anything else is absolute (the string up to the first colon is the\n    volume name)."));
      var1.setlocal("isabs", var8);
      var3 = null;
      var1.setline(42);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, join$3, (PyObject)null);
      var1.setlocal("join", var8);
      var3 = null;
      var1.setline(58);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, split$4, PyString.fromInterned("Split a pathname into two parts: the directory leading up to the final\n    bit, and the basename (the filename, without colons, in that directory).\n    The result (s, t) is such that join(s, t) yields the original argument."));
      var1.setlocal("split", var8);
      var3 = null;
      var1.setline(73);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, splitext$5, (PyObject)null);
      var1.setlocal("splitext", var8);
      var3 = null;
      var1.setline(75);
      var3 = var1.getname("genericpath").__getattr__("_splitext").__getattr__("__doc__");
      var1.getname("splitext").__setattr__("__doc__", var3);
      var3 = null;
      var1.setline(77);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, splitdrive$6, PyString.fromInterned("Split a pathname into a drive specification and the rest of the\n    path.  Useful on DOS/Windows/NT; on the Mac, the drive is always\n    empty (don't use the volume name -- it doesn't have the same\n    syntactic and semantic oddities as DOS drive letters, such as there\n    being a separate current directory per drive)."));
      var1.setlocal("splitdrive", var8);
      var3 = null;
      var1.setline(89);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, dirname$7, (PyObject)null);
      var1.setlocal("dirname", var8);
      var3 = null;
      var1.setline(90);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, basename$8, (PyObject)null);
      var1.setlocal("basename", var8);
      var3 = null;
      var1.setline(92);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, ismount$9, (PyObject)null);
      var1.setlocal("ismount", var8);
      var3 = null;
      var1.setline(98);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, islink$10, PyString.fromInterned("Return true if the pathname refers to a symbolic link."));
      var1.setlocal("islink", var8);
      var3 = null;
      var1.setline(110);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, lexists$11, PyString.fromInterned("Test whether a path exists.  Returns True for broken symbolic links"));
      var1.setlocal("lexists", var8);
      var3 = null;
      var1.setline(119);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, expandvars$12, PyString.fromInterned("Dummy to retain interface-compatibility with other operating systems."));
      var1.setlocal("expandvars", var8);
      var3 = null;
      var1.setline(124);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, expanduser$13, PyString.fromInterned("Dummy to retain interface-compatibility with other operating systems."));
      var1.setlocal("expanduser", var8);
      var3 = null;
      var1.setline(128);
      var7 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("norm_error", var7, norm_error$14);
      var1.setlocal("norm_error", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(131);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, normpath$15, PyString.fromInterned("Normalize a pathname.  Will return the same result for\n    equivalent paths."));
      var1.setlocal("normpath", var8);
      var3 = null;
      var1.setline(159);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, walk$16, PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common."));
      var1.setlocal("walk", var8);
      var3 = null;
      var1.setline(186);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, abspath$17, PyString.fromInterned("Return an absolute path."));
      var1.setlocal("abspath", var8);
      var3 = null;
      var1.setline(197);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, realpath$18, (PyObject)null);
      var1.setlocal("realpath", var8);
      var3 = null;
      var1.setline(215);
      var3 = var1.getname("True");
      var1.setlocal("supports_unicode_filenames", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject normcase$1(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isabs$2(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Return true if a path is absolute.\n    On the Mac, relative paths begin with a colon,\n    but as a special case, paths with no colons at all are also relative.\n    Anything else is absolute (the string up to the first colon is the\n    volume name).");
      var1.setline(39);
      PyString var3 = PyString.fromInterned(":");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var4._ne(PyString.fromInterned(":"));
         var3 = null;
      }

      var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject join$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(44);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(55);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(45);
         PyObject var10000 = var1.getlocal(0).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isabs").__call__(var2, var1.getlocal(3));
         }

         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(46);
            var5 = var1.getlocal(3);
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(48);
            var5 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var5._eq(PyString.fromInterned(":"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(49);
               var5 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(50);
            PyString var6 = PyString.fromInterned(":");
            var10000 = var6._notin(var1.getlocal(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(51);
               var5 = PyString.fromInterned(":")._add(var1.getlocal(2));
               var1.setlocal(2, var5);
               var5 = null;
            }

            var1.setline(52);
            var5 = var1.getlocal(2).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
            var10000 = var5._ne(PyString.fromInterned(":"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(53);
               var5 = var1.getlocal(2)._add(PyString.fromInterned(":"));
               var1.setlocal(2, var5);
               var5 = null;
            }

            var1.setline(54);
            var5 = var1.getlocal(2)._add(var1.getlocal(3));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject split$4(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("Split a pathname into two parts: the directory leading up to the final\n    bit, and the basename (the filename, without colons, in that directory).\n    The result (s, t) is such that join(s, t) yields the original argument.");
      var1.setline(63);
      PyString var3 = PyString.fromInterned(":");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(63);
         var7 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(64);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(65);
         PyObject var8 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

         while(true) {
            var1.setline(65);
            PyObject var5 = var8.__iternext__();
            PyObject var6;
            if (var5 == null) {
               var1.setline(67);
               PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1)._sub(Py.newInteger(1)), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null)});
               PyObject[] var10 = Py.unpackSequence(var9, 2);
               var6 = var10[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(68);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  PyString var11 = PyString.fromInterned(":");
                  var10000 = var11._in(var1.getlocal(3));
                  var4 = null;
                  var10000 = var10000.__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(69);
                  var8 = var1.getlocal(3)._add(PyString.fromInterned(":"));
                  var1.setlocal(3, var8);
                  var4 = null;
               }

               var1.setline(70);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(2, var5);
            var1.setline(66);
            var6 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var10000 = var6._eq(PyString.fromInterned(":"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(66);
               var6 = var1.getlocal(2)._add(Py.newInteger(1));
               var1.setlocal(1, var6);
               var6 = null;
            }
         }
      }
   }

   public PyObject splitext$5(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("genericpath").__getattr__("_splitext").__call__(var2, var1.getlocal(0), var1.getglobal("sep"), var1.getglobal("altsep"), var1.getglobal("extsep"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject splitdrive$6(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Split a pathname into a drive specification and the rest of the\n    path.  Useful on DOS/Windows/NT; on the Mac, the drive is always\n    empty (don't use the volume name -- it doesn't have the same\n    syntactic and semantic oddities as DOS drive letters, such as there\n    being a separate current directory per drive).");
      var1.setline(84);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dirname$7(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject basename$8(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismount$9(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3;
      if (var1.getglobal("isabs").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(94);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(95);
         PyObject var4 = var1.getglobal("split").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(96);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var4._eq(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var10000 = var4._eq(PyString.fromInterned(""));
            var4 = null;
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject islink$10(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Return true if the pathname refers to a symbolic link.");

      PyObject var3;
      try {
         var1.setline(102);
         var3 = imp.importOne("Carbon.File", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(103);
         var3 = var1.getlocal(1).__getattr__("File").__getattr__("ResolveAliasFile").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(105);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lexists$11(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyString.fromInterned("Test whether a path exists.  Returns True for broken symbolic links");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(114);
         PyObject var6 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(116);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(117);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject expandvars$12(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("Dummy to retain interface-compatibility with other operating systems.");
      var1.setline(121);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expanduser$13(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyString.fromInterned("Dummy to retain interface-compatibility with other operating systems.");
      var1.setline(126);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject norm_error$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Path cannot be normalized"));
      var1.setline(129);
      PyString.fromInterned("Path cannot be normalized");
      return var1.getf_locals();
   }

   public PyObject normpath$15(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Normalize a pathname.  Will return the same result for\n    equivalent paths.");
      var1.setline(135);
      PyString var3 = PyString.fromInterned(":");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         var5 = PyString.fromInterned(":")._add(var1.getlocal(0));
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(138);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(139);
         PyInteger var6 = Py.newInteger(1);
         var1.setlocal(2, var6);
         var4 = null;

         while(true) {
            var1.setline(140);
            var4 = var1.getlocal(2);
            var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1)));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(151);
               var4 = PyString.fromInterned(":").__getattr__("join").__call__(var2, var1.getlocal(1));
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(154);
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
               var10000 = var4._eq(PyString.fromInterned(":"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
                  var10000 = var4._gt(Py.newInteger(2));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(0);
                     var10000 = var4._ne(PyString.fromInterned(":")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(0))));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(155);
                  var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(0, var4);
                  var4 = null;
               }

               var1.setline(156);
               var5 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(141);
            var4 = var1.getlocal(1).__getitem__(var1.getlocal(2));
            var10000 = var4._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(1).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
               var10000 = var4._ne(PyString.fromInterned(""));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(142);
               var4 = var1.getlocal(2);
               var10000 = var4._gt(Py.newInteger(1));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(147);
                  throw Py.makeException(var1.getglobal("norm_error"), PyString.fromInterned("Cannot use :: immediately after volume name"));
               }

               var1.setline(143);
               var1.getlocal(1).__delslice__(var1.getlocal(2)._sub(Py.newInteger(1)), var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null);
               var1.setline(144);
               var4 = var1.getlocal(2)._sub(Py.newInteger(1));
               var1.setlocal(2, var4);
               var4 = null;
            } else {
               var1.setline(149);
               var4 = var1.getlocal(2)._add(Py.newInteger(1));
               var1.setlocal(2, var4);
               var4 = null;
            }
         }
      }
   }

   public PyObject walk$16(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common.");
      var1.setline(173);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warnpy3k");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("In 3.x, os.path.walk is removed in favor of os.walk."), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;

      PyObject var8;
      try {
         var1.setline(176);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var8);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(178);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var7;
      }

      var1.setline(179);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(3));
      var1.setline(180);
      var8 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(180);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var9);
         var1.setline(181);
         PyObject var5 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(182);
         var10000 = var1.getglobal("isdir").__call__(var2, var1.getlocal(4));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("islink").__call__(var2, var1.getlocal(4)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(183);
            var1.getglobal("walk").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
         }
      }
   }

   public PyObject abspath$17(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Return an absolute path.");
      var1.setline(188);
      PyObject var3;
      if (var1.getglobal("isabs").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(189);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(190);
            var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(192);
            var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(193);
         var3 = var1.getglobal("join").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(194);
      var3 = var1.getglobal("normpath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject realpath$18(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(200);
         var3 = imp.importOne("Carbon.File", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("ImportError"))) {
            var1.setline(202);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var9;
      }

      var1.setline(203);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(204);
         var4 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(205);
         var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(206);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0))._add(PyString.fromInterned(":"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(207);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(207);
            PyObject var5 = var3.__iternext__();
            if (var5 == null) {
               var1.setline(213);
               var4 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var4;
            }

            var1.setlocal(3, var5);
            var1.setline(208);
            PyObject var6 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(0, var6);
            var6 = null;

            try {
               var1.setline(210);
               var6 = var1.getlocal(1).__getattr__("File").__getattr__("FSResolveAliasFile").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0)).__getattr__("as_pathname").__call__(var2);
               var1.setlocal(0, var6);
               var6 = null;
            } catch (Throwable var8) {
               PyException var10 = Py.setException(var8, var1);
               if (!var10.match(var1.getlocal(1).__getattr__("File").__getattr__("Error"))) {
                  throw var10;
               }

               var1.setline(212);
            }
         }
      }
   }

   public macpath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"path"};
      normcase$1 = Py.newCode(1, var2, var1, "normcase", 28, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      isabs$2 = Py.newCode(1, var2, var1, "isabs", 32, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "p", "path", "t"};
      join$3 = Py.newCode(2, var2, var1, "join", 42, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "colon", "i", "path", "file"};
      split$4 = Py.newCode(1, var2, var1, "split", 58, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitext$5 = Py.newCode(1, var2, var1, "splitext", 73, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitdrive$6 = Py.newCode(1, var2, var1, "splitdrive", 77, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      dirname$7 = Py.newCode(1, var2, var1, "dirname", 89, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      basename$8 = Py.newCode(1, var2, var1, "basename", 90, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "components"};
      ismount$9 = Py.newCode(1, var2, var1, "ismount", 92, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "Carbon"};
      islink$10 = Py.newCode(1, var2, var1, "islink", 98, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "st"};
      lexists$11 = Py.newCode(1, var2, var1, "lexists", 110, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      expandvars$12 = Py.newCode(1, var2, var1, "expandvars", 119, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      expanduser$13 = Py.newCode(1, var2, var1, "expanduser", 124, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      norm_error$14 = Py.newCode(0, var2, var1, "norm_error", 128, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "comps", "i"};
      normpath$15 = Py.newCode(1, var2, var1, "normpath", 131, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"top", "func", "arg", "names", "name"};
      walk$16 = Py.newCode(3, var2, var1, "walk", 159, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "cwd"};
      abspath$17 = Py.newCode(1, var2, var1, "abspath", 186, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "Carbon", "components", "c"};
      realpath$18 = Py.newCode(1, var2, var1, "realpath", 197, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new macpath$py("macpath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(macpath$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.normcase$1(var2, var3);
         case 2:
            return this.isabs$2(var2, var3);
         case 3:
            return this.join$3(var2, var3);
         case 4:
            return this.split$4(var2, var3);
         case 5:
            return this.splitext$5(var2, var3);
         case 6:
            return this.splitdrive$6(var2, var3);
         case 7:
            return this.dirname$7(var2, var3);
         case 8:
            return this.basename$8(var2, var3);
         case 9:
            return this.ismount$9(var2, var3);
         case 10:
            return this.islink$10(var2, var3);
         case 11:
            return this.lexists$11(var2, var3);
         case 12:
            return this.expandvars$12(var2, var3);
         case 13:
            return this.expanduser$13(var2, var3);
         case 14:
            return this.norm_error$14(var2, var3);
         case 15:
            return this.normpath$15(var2, var3);
         case 16:
            return this.walk$16(var2, var3);
         case 17:
            return this.abspath$17(var2, var3);
         case 18:
            return this.realpath$18(var2, var3);
         default:
            return null;
      }
   }
}
