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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("ntpath.py")
public class ntpath$py extends PyFunctionTable implements PyRunnable {
   static ntpath$py self;
   static final PyCode f$0;
   static final PyCode normcase$1;
   static final PyCode isabs$2;
   static final PyCode join$3;
   static final PyCode splitdrive$4;
   static final PyCode splitunc$5;
   static final PyCode split$6;
   static final PyCode splitext$7;
   static final PyCode basename$8;
   static final PyCode dirname$9;
   static final PyCode islink$10;
   static final PyCode ismount$11;
   static final PyCode walk$12;
   static final PyCode expanduser$13;
   static final PyCode expandvars$14;
   static final PyCode normpath$15;
   static final PyCode abspath$16;
   static final PyCode abspath$17;
   static final PyCode _abspath_split$18;
   static final PyCode relpath$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Common pathname manipulations, WindowsNT/95 version.\n\nInstead of importing this module directly, import os and refer to this\nmodule as os.path.\n"));
      var1.setline(6);
      PyString.fromInterned("Common pathname manipulations, WindowsNT/95 version.\n\nInstead of importing this module directly, import os and refer to this\nmodule as os.path.\n");
      var1.setline(8);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(11);
      var3 = imp.importOne("genericpath", var1, -1);
      var1.setlocal("genericpath", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(14);
      imp.importAll("genericpath", var1, -1);
      var1.setline(16);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("normcase"), PyString.fromInterned("isabs"), PyString.fromInterned("join"), PyString.fromInterned("splitdrive"), PyString.fromInterned("split"), PyString.fromInterned("splitext"), PyString.fromInterned("basename"), PyString.fromInterned("dirname"), PyString.fromInterned("commonprefix"), PyString.fromInterned("getsize"), PyString.fromInterned("getmtime"), PyString.fromInterned("getatime"), PyString.fromInterned("getctime"), PyString.fromInterned("islink"), PyString.fromInterned("exists"), PyString.fromInterned("lexists"), PyString.fromInterned("isdir"), PyString.fromInterned("isfile"), PyString.fromInterned("ismount"), PyString.fromInterned("walk"), PyString.fromInterned("expanduser"), PyString.fromInterned("expandvars"), PyString.fromInterned("normpath"), PyString.fromInterned("abspath"), PyString.fromInterned("splitunc"), PyString.fromInterned("curdir"), PyString.fromInterned("pardir"), PyString.fromInterned("sep"), PyString.fromInterned("pathsep"), PyString.fromInterned("defpath"), PyString.fromInterned("altsep"), PyString.fromInterned("extsep"), PyString.fromInterned("devnull"), PyString.fromInterned("realpath"), PyString.fromInterned("supports_unicode_filenames"), PyString.fromInterned("relpath")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(24);
      PyString var10 = PyString.fromInterned(".");
      var1.setlocal("curdir", var10);
      var3 = null;
      var1.setline(25);
      var10 = PyString.fromInterned("..");
      var1.setlocal("pardir", var10);
      var3 = null;
      var1.setline(26);
      var10 = PyString.fromInterned(".");
      var1.setlocal("extsep", var10);
      var3 = null;
      var1.setline(27);
      var10 = PyString.fromInterned("\\");
      var1.setlocal("sep", var10);
      var3 = null;
      var1.setline(28);
      var10 = PyString.fromInterned(";");
      var1.setlocal("pathsep", var10);
      var3 = null;
      var1.setline(29);
      var10 = PyString.fromInterned("/");
      var1.setlocal("altsep", var10);
      var3 = null;
      var1.setline(30);
      var10 = PyString.fromInterned(".;C:\\bin");
      var1.setlocal("defpath", var10);
      var3 = null;
      var1.setline(31);
      var10 = PyString.fromInterned("ce");
      PyObject var10000 = var10._in(var1.getname("sys").__getattr__("builtin_module_names"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(32);
         var10 = PyString.fromInterned("\\Windows");
         var1.setlocal("defpath", var10);
         var3 = null;
      } else {
         var1.setline(33);
         var10 = PyString.fromInterned("os2");
         var10000 = var10._in(var1.getname("sys").__getattr__("builtin_module_names"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(35);
            var10 = PyString.fromInterned("/");
            var1.setlocal("altsep", var10);
            var3 = null;
         }
      }

      var1.setline(36);
      var10 = PyString.fromInterned("nul");
      var1.setlocal("devnull", var10);
      var3 = null;
      var1.setline(42);
      PyObject[] var11 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var11, normcase$1, PyString.fromInterned("Normalize case of pathname.\n\n    Makes all characters lowercase and all slashes into backslashes."));
      var1.setlocal("normcase", var12);
      var3 = null;
      var1.setline(55);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, isabs$2, PyString.fromInterned("Test whether a path is absolute"));
      var1.setlocal("isabs", var12);
      var3 = null;
      var1.setline(63);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, join$3, PyString.fromInterned("Join two or more pathname components, inserting \"\\\" as needed.\n    If any component is an absolute path, all previous path components\n    will be discarded."));
      var1.setlocal("join", var12);
      var3 = null;
      var1.setline(122);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, splitdrive$4, PyString.fromInterned("Split a pathname into drive and path specifiers. Returns a 2-tuple\n\"(drive,path)\";  either part may be empty"));
      var1.setlocal("splitdrive", var12);
      var3 = null;
      var1.setline(131);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, splitunc$5, PyString.fromInterned("Split a pathname into UNC mount point and relative path specifiers.\n\n    Return a 2-tuple (unc, rest); either part may be empty.\n    If unc is not empty, it has the form '//host/mount' (or similar\n    using backslashes).  unc+rest is always the input path.\n    Paths containing drive letters never have an UNC part.\n    "));
      var1.setlocal("splitunc", var12);
      var3 = null;
      var1.setline(164);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, split$6, PyString.fromInterned("Split a pathname.\n\n    Return tuple (head, tail) where tail is everything after the final slash.\n    Either part may be empty."));
      var1.setlocal("split", var12);
      var3 = null;
      var1.setline(189);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, splitext$7, (PyObject)null);
      var1.setlocal("splitext", var12);
      var3 = null;
      var1.setline(191);
      var3 = var1.getname("genericpath").__getattr__("_splitext").__getattr__("__doc__");
      var1.getname("splitext").__setattr__("__doc__", var3);
      var3 = null;
      var1.setline(196);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, basename$8, PyString.fromInterned("Returns the final component of a pathname"));
      var1.setlocal("basename", var12);
      var3 = null;
      var1.setline(203);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, dirname$9, PyString.fromInterned("Returns the directory component of a pathname"));
      var1.setlocal("dirname", var12);
      var3 = null;
      var1.setline(210);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, islink$10, PyString.fromInterned("Test for symbolic link.\n    On WindowsNT/95 and OS/2 always returns false\n    "));
      var1.setlocal("islink", var12);
      var3 = null;
      var1.setline(217);
      var3 = var1.getname("exists");
      var1.setlocal("lexists", var3);
      var3 = null;
      var1.setline(222);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, ismount$11, PyString.fromInterned("Test whether a path is a mount point (defined as root of drive)"));
      var1.setlocal("ismount", var12);
      var3 = null;
      var1.setline(239);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, walk$12, PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common."));
      var1.setlocal("walk", var12);
      var3 = null;
      var1.setline(275);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, expanduser$13, PyString.fromInterned("Expand ~ and ~user constructs.\n\n    If user or $HOME is unknown, do nothing."));
      var1.setlocal("expanduser", var12);
      var3 = null;
      var1.setline(317);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, expandvars$14, PyString.fromInterned("Expand shell variables of the forms $var, ${var} and %var%.\n\n    Unknown variables are left unchanged."));
      var1.setlocal("expandvars", var12);
      var3 = null;
      var1.setline(398);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, normpath$15, PyString.fromInterned("Normalize path, eliminating double slashes, etc."));
      var1.setlocal("normpath", var12);
      var3 = null;

      PyObject[] var4;
      PyObject var8;
      PyException var13;
      String[] var14;
      label44: {
         PyFunction var7;
         try {
            var1.setline(452);
            var14 = new String[]{"_getfullpathname"};
            var11 = imp.importFrom("nt", var14, var1, -1);
            var8 = var11[0];
            var1.setlocal("_getfullpathname", var8);
            var4 = null;
         } catch (Throwable var6) {
            var13 = Py.setException(var6, var1);
            if (var13.match(var1.getname("ImportError"))) {
               var1.setline(455);
               var4 = Py.EmptyObjects;
               var7 = new PyFunction(var1.f_globals, var4, abspath$16, PyString.fromInterned("Return the absolute version of a path."));
               var1.setlocal("abspath", var7);
               var4 = null;
               break label44;
            }

            throw var13;
         }

         var1.setline(466);
         var4 = Py.EmptyObjects;
         var7 = new PyFunction(var1.f_globals, var4, abspath$17, PyString.fromInterned("Return the absolute version of a path."));
         var1.setlocal("abspath", var7);
         var4 = null;
      }

      var1.setline(481);
      var3 = var1.getname("abspath");
      var1.setlocal("realpath", var3);
      var3 = null;
      var1.setline(483);
      var10000 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("sys"), (PyObject)PyString.fromInterned("getwindowsversion"));
      if (var10000.__nonzero__()) {
         var3 = var1.getname("sys").__getattr__("getwindowsversion").__call__(var2).__getitem__(Py.newInteger(3));
         var10000 = var3._ge(Py.newInteger(2));
         var3 = null;
      }

      var3 = var10000;
      var1.setlocal("supports_unicode_filenames", var3);
      var3 = null;
      var1.setline(486);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _abspath_split$18, (PyObject)null);
      var1.setlocal("_abspath_split", var12);
      var3 = null;
      var1.setline(494);
      var11 = new PyObject[]{var1.getname("curdir")};
      var12 = new PyFunction(var1.f_globals, var11, relpath$19, PyString.fromInterned("Return a relative version of a path"));
      var1.setlocal("relpath", var12);
      var3 = null;

      try {
         var1.setline(530);
         var14 = new String[]{"_isdir"};
         var11 = imp.importFrom("nt", var14, var1, -1);
         var8 = var11[0];
         var1.setlocal("isdir", var8);
         var4 = null;
      } catch (Throwable var5) {
         var13 = Py.setException(var5, var1);
         if (!var13.match(var1.getname("ImportError"))) {
            throw var13;
         }

         var1.setline(533);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject normcase$1(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyString.fromInterned("Normalize case of pathname.\n\n    Makes all characters lowercase and all slashes into backslashes.");
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("\\")).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isabs$2(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString.fromInterned("Test whether a path is absolute");
      var1.setline(57);
      PyObject var3 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var3._in(PyString.fromInterned("/\\"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject join$3(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString.fromInterned("Join two or more pathname components, inserting \"\\\" as needed.\n    If any component is an absolute path, all previous path components\n    will be discarded.");
      var1.setline(67);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(68);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(116);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(69);
         PyInteger var5 = Py.newInteger(0);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(70);
         PyObject var6 = var1.getlocal(2);
         PyObject var10000 = var6._eq(PyString.fromInterned(""));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(71);
            var5 = Py.newInteger(1);
            var1.setlocal(4, var5);
            var5 = null;
         } else {
            var1.setline(73);
            if (var1.getglobal("isabs").__call__(var2, var1.getlocal(3)).__nonzero__()) {
               var1.setline(82);
               var6 = var1.getlocal(2).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
               var10000 = var6._ne(PyString.fromInterned(":"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var6 = var1.getlocal(3).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
                  var10000 = var6._eq(PyString.fromInterned(":"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(84);
                  var5 = Py.newInteger(1);
                  var1.setlocal(4, var5);
                  var5 = null;
               } else {
                  var1.setline(87);
                  var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                  var10000 = var6._gt(Py.newInteger(3));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                     var10000 = var6._eq(Py.newInteger(3));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var6 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
                        var10000 = var6._notin(PyString.fromInterned("/\\"));
                        var5 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(90);
                     var5 = Py.newInteger(1);
                     var1.setlocal(4, var5);
                     var5 = null;
                  }
               }
            }
         }

         var1.setline(92);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(93);
            var6 = var1.getlocal(3);
            var1.setlocal(2, var6);
            var5 = null;
         } else {
            var1.setline(96);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var6._gt(Py.newInteger(0));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(97);
            var6 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
            var10000 = var6._in(PyString.fromInterned("/\\"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(98);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                  var10000 = var6._in(PyString.fromInterned("/\\"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(99);
                  var6 = var1.getlocal(2);
                  var6 = var6._iadd(var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  var1.setlocal(2, var6);
               } else {
                  var1.setline(101);
                  var6 = var1.getlocal(2);
                  var6 = var6._iadd(var1.getlocal(3));
                  var1.setlocal(2, var6);
               }
            } else {
               var1.setline(102);
               var6 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
               var10000 = var6._eq(PyString.fromInterned(":"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(103);
                  var6 = var1.getlocal(2);
                  var6 = var6._iadd(var1.getlocal(3));
                  var1.setlocal(2, var6);
               } else {
                  var1.setline(104);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(105);
                     var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                     var10000 = var6._in(PyString.fromInterned("/\\"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(106);
                        var6 = var1.getlocal(2);
                        var6 = var6._iadd(var1.getlocal(3));
                        var1.setlocal(2, var6);
                     } else {
                        var1.setline(108);
                        var6 = var1.getlocal(2);
                        var6 = var6._iadd(PyString.fromInterned("\\")._add(var1.getlocal(3)));
                        var1.setlocal(2, var6);
                     }
                  } else {
                     var1.setline(114);
                     var6 = var1.getlocal(2);
                     var6 = var6._iadd(PyString.fromInterned("\\"));
                     var1.setlocal(2, var6);
                  }
               }
            }
         }
      }
   }

   public PyObject splitdrive$4(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Split a pathname into drive and path specifiers. Returns a 2-tuple\n\"(drive,path)\";  either part may be empty");
      var1.setline(125);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(":"));
      var3 = null;
      PyTuple var4;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null), var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(127);
         var4 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject splitunc$5(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Split a pathname into UNC mount point and relative path specifiers.\n\n    Return a 2-tuple (unc, rest); either part may be empty.\n    If unc is not empty, it has the form '//host/mount' (or similar\n    using backslashes).  unc+rest is always the input path.\n    Paths containing drive letters never have an UNC part.\n    ");
      var1.setline(139);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(":"));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(140);
         var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(141);
         PyObject var4 = var1.getlocal(0).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(142);
         var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("//"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(1);
            var10000 = var4._eq(PyString.fromInterned("\\\\"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(147);
            var4 = var1.getglobal("normcase").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(148);
            var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)Py.newInteger(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(149);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(-1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(151);
               var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(152);
               var4 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)var1.getlocal(3)._add(Py.newInteger(1)));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(153);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(Py.newInteger(-1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(154);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                  var1.setlocal(3, var4);
                  var4 = null;
               }

               var1.setline(155);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null)});
               var1.f_lasti = -1;
               return var5;
            }
         } else {
            var1.setline(156);
            var5 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject split$6(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("Split a pathname.\n\n    Return tuple (head, tail) where tail is everything after the final slash.\n    Either part may be empty.");
      var1.setline(170);
      PyObject var3 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(172);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(173);
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
            var10000 = var3._notin(PyString.fromInterned("/\\"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(175);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
            var1.setline(177);
            var3 = var1.getlocal(3);
            var1.setlocal(5, var3);
            var3 = null;

            while(true) {
               var1.setline(178);
               var10000 = var1.getlocal(5);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(5).__getitem__(Py.newInteger(-1));
                  var10000 = var3._in(PyString.fromInterned("/\\"));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(180);
                  var10000 = var1.getlocal(5);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(3);
                  }

                  var3 = var10000;
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(181);
                  var6 = new PyTuple(new PyObject[]{var1.getlocal(1)._add(var1.getlocal(3)), var1.getlocal(4)});
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setline(179);
               var3 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(5, var3);
               var3 = null;
            }
         }

         var1.setline(174);
         var3 = var1.getlocal(2)._sub(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
      }
   }

   public PyObject splitext$7(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyObject var3 = var1.getglobal("genericpath").__getattr__("_splitext").__call__(var2, var1.getlocal(0), var1.getglobal("sep"), var1.getglobal("altsep"), var1.getglobal("extsep"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject basename$8(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Returns the final component of a pathname");
      var1.setline(198);
      PyObject var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dirname$9(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyString.fromInterned("Returns the directory component of a pathname");
      var1.setline(205);
      PyObject var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject islink$10(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyString.fromInterned("Test for symbolic link.\n    On WindowsNT/95 and OS/2 always returns false\n    ");
      var1.setline(214);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismount$11(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Test whether a path is a mount point (defined as root of drive)");
      var1.setline(224);
      PyObject var3 = var1.getglobal("splitunc").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(225);
      PyObject var10000;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(226);
         var3 = var1.getlocal(2);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("/"), PyString.fromInterned("\\")}));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(227);
         PyObject var6 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(228);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var6._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var6._in(PyString.fromInterned("/\\"));
            var4 = null;
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject walk$12(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common.");
      var1.setline(253);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warnpy3k");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("In 3.x, os.path.walk is removed in favor of os.walk."), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;

      PyObject var8;
      try {
         var1.setline(256);
         var8 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var8);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(258);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var7;
      }

      var1.setline(259);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(3));
      var1.setline(260);
      var8 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(260);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var9);
         var1.setline(261);
         PyObject var5 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(262);
         if (var1.getglobal("isdir").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(263);
            var1.getglobal("walk").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
         }
      }
   }

   public PyObject expanduser$13(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyString.fromInterned("Expand ~ and ~user constructs.\n\n    If user or $HOME is unknown, do nothing.");
      var1.setline(279);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("~"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(280);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(281);
         PyTuple var4 = new PyTuple(new PyObject[]{Py.newInteger(1), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;

         while(true) {
            var1.setline(282);
            PyObject var8 = var1.getlocal(1);
            var10000 = var8._lt(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var8 = var1.getlocal(0).__getitem__(var1.getlocal(1));
               var10000 = var8._notin(PyString.fromInterned("/\\"));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(285);
               PyString var10 = PyString.fromInterned("HOME");
               var10000 = var10._in(var1.getglobal("os").__getattr__("environ"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(286);
                  var8 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME"));
                  var1.setlocal(3, var8);
                  var4 = null;
               } else {
                  var1.setline(287);
                  var10 = PyString.fromInterned("USERPROFILE");
                  var10000 = var10._in(var1.getglobal("os").__getattr__("environ"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(288);
                     var8 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("USERPROFILE"));
                     var1.setlocal(3, var8);
                     var4 = null;
                  } else {
                     var1.setline(289);
                     var10 = PyString.fromInterned("HOMEPATH");
                     var10000 = var10._in(var1.getglobal("os").__getattr__("environ"));
                     var4 = null;
                     if (var10000.__not__().__nonzero__()) {
                        var1.setline(290);
                        var3 = var1.getlocal(0);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     try {
                        var1.setline(293);
                        var8 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOMEDRIVE"));
                        var1.setlocal(4, var8);
                        var4 = null;
                     } catch (Throwable var7) {
                        PyException var11 = Py.setException(var7, var1);
                        if (!var11.match(var1.getglobal("KeyError"))) {
                           throw var11;
                        }

                        var1.setline(295);
                        PyString var9 = PyString.fromInterned("");
                        var1.setlocal(4, var9);
                        var5 = null;
                     }

                     var1.setline(296);
                     var8 = var1.getglobal("join").__call__(var2, var1.getlocal(4), var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOMEPATH")));
                     var1.setlocal(3, var8);
                     var4 = null;
                  }
               }

               var1.setline(298);
               var8 = var1.getlocal(1);
               var10000 = var8._ne(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(299);
                  var8 = var1.getglobal("join").__call__(var2, var1.getglobal("dirname").__call__(var2, var1.getlocal(3)), var1.getlocal(0).__getslice__(Py.newInteger(1), var1.getlocal(1), (PyObject)null));
                  var1.setlocal(3, var8);
                  var4 = null;
               }

               var1.setline(301);
               var3 = var1.getlocal(3)._add(var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(283);
            var8 = var1.getlocal(1)._add(Py.newInteger(1));
            var1.setlocal(1, var8);
            var4 = null;
         }
      }
   }

   public PyObject expandvars$14(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyString.fromInterned("Expand shell variables of the forms $var, ${var} and %var%.\n\n    Unknown variables are left unchanged.");
      var1.setline(321);
      PyString var3 = PyString.fromInterned("$");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = PyString.fromInterned("%");
         var10000 = var3._notin(var1.getlocal(0));
         var3 = null;
      }

      PyObject var9;
      if (var10000.__nonzero__()) {
         var1.setline(322);
         var9 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(323);
         PyObject var4 = imp.importOne("string", var1, -1);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(324);
         var4 = var1.getlocal(1).__getattr__("ascii_letters")._add(var1.getlocal(1).__getattr__("digits"))._add(PyString.fromInterned("_-"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(325);
         PyString var10 = PyString.fromInterned("");
         var1.setlocal(3, var10);
         var4 = null;
         var1.setline(326);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(4, var11);
         var4 = null;
         var1.setline(327);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var4);
         var4 = null;

         while(true) {
            var1.setline(328);
            var4 = var1.getlocal(4);
            var10000 = var4._lt(var1.getlocal(5));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(391);
               var9 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var9;
            }

            var1.setline(329);
            var4 = var1.getlocal(0).__getitem__(var1.getlocal(4));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(330);
            var4 = var1.getlocal(6);
            var10000 = var4._eq(PyString.fromInterned("'"));
            var4 = null;
            PyObject var5;
            PyException var12;
            if (var10000.__nonzero__()) {
               var1.setline(331);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(332);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var1.setlocal(5, var4);
               var4 = null;

               try {
                  var1.setline(334);
                  var4 = var1.getlocal(0).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(335);
                  var4 = var1.getlocal(3)._add(PyString.fromInterned("'"))._add(var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null));
                  var1.setlocal(3, var4);
                  var4 = null;
               } catch (Throwable var6) {
                  var12 = Py.setException(var6, var1);
                  if (!var12.match(var1.getglobal("ValueError"))) {
                     throw var12;
                  }

                  var1.setline(337);
                  var5 = var1.getlocal(3)._add(var1.getlocal(0));
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(338);
                  var5 = var1.getlocal(5)._sub(Py.newInteger(1));
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            } else {
               var1.setline(339);
               var4 = var1.getlocal(6);
               var10000 = var4._eq(PyString.fromInterned("%"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(357);
                  var4 = var1.getlocal(6);
                  var10000 = var4._eq(PyString.fromInterned("$"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(358);
                     var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("$"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(359);
                        var4 = var1.getlocal(3)._add(var1.getlocal(6));
                        var1.setlocal(3, var4);
                        var4 = null;
                        var1.setline(360);
                        var4 = var1.getlocal(4)._add(Py.newInteger(1));
                        var1.setlocal(4, var4);
                        var4 = null;
                     } else {
                        var1.setline(361);
                        var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null);
                        var10000 = var4._eq(PyString.fromInterned("{"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(362);
                           var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null, (PyObject)null);
                           var1.setlocal(0, var4);
                           var4 = null;
                           var1.setline(363);
                           var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                           var1.setlocal(5, var4);
                           var4 = null;

                           try {
                              var1.setline(365);
                              var4 = var1.getlocal(0).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"));
                              var1.setlocal(4, var4);
                              var4 = null;
                              var1.setline(366);
                              var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
                              var1.setlocal(7, var4);
                              var4 = null;
                              var1.setline(367);
                              var4 = var1.getlocal(7);
                              var10000 = var4._in(var1.getglobal("os").__getattr__("environ"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(368);
                                 var4 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(7)));
                                 var1.setlocal(3, var4);
                                 var4 = null;
                              } else {
                                 var1.setline(370);
                                 var4 = var1.getlocal(3)._add(PyString.fromInterned("${"))._add(var1.getlocal(7))._add(PyString.fromInterned("}"));
                                 var1.setlocal(3, var4);
                                 var4 = null;
                              }
                           } catch (Throwable var7) {
                              var12 = Py.setException(var7, var1);
                              if (!var12.match(var1.getglobal("ValueError"))) {
                                 throw var12;
                              }

                              var1.setline(372);
                              var5 = var1.getlocal(3)._add(PyString.fromInterned("${"))._add(var1.getlocal(0));
                              var1.setlocal(3, var5);
                              var5 = null;
                              var1.setline(373);
                              var5 = var1.getlocal(5)._sub(Py.newInteger(1));
                              var1.setlocal(4, var5);
                              var5 = null;
                           }
                        } else {
                           var1.setline(375);
                           var10 = PyString.fromInterned("");
                           var1.setlocal(7, var10);
                           var4 = null;
                           var1.setline(376);
                           var4 = var1.getlocal(4)._add(Py.newInteger(1));
                           var1.setlocal(4, var4);
                           var4 = null;
                           var1.setline(377);
                           var4 = var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
                           var1.setlocal(6, var4);
                           var4 = null;

                           while(true) {
                              var1.setline(378);
                              var4 = var1.getlocal(6);
                              var10000 = var4._ne(PyString.fromInterned(""));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var4 = var1.getlocal(6);
                                 var10000 = var4._in(var1.getlocal(2));
                                 var4 = null;
                              }

                              if (!var10000.__nonzero__()) {
                                 var1.setline(382);
                                 var4 = var1.getlocal(7);
                                 var10000 = var4._in(var1.getglobal("os").__getattr__("environ"));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(383);
                                    var4 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(7)));
                                    var1.setlocal(3, var4);
                                    var4 = null;
                                 } else {
                                    var1.setline(385);
                                    var4 = var1.getlocal(3)._add(PyString.fromInterned("$"))._add(var1.getlocal(7));
                                    var1.setlocal(3, var4);
                                    var4 = null;
                                 }

                                 var1.setline(386);
                                 var4 = var1.getlocal(6);
                                 var10000 = var4._ne(PyString.fromInterned(""));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(387);
                                    var4 = var1.getlocal(4)._sub(Py.newInteger(1));
                                    var1.setlocal(4, var4);
                                    var4 = null;
                                 }
                                 break;
                              }

                              var1.setline(379);
                              var4 = var1.getlocal(7)._add(var1.getlocal(6));
                              var1.setlocal(7, var4);
                              var4 = null;
                              var1.setline(380);
                              var4 = var1.getlocal(4)._add(Py.newInteger(1));
                              var1.setlocal(4, var4);
                              var4 = null;
                              var1.setline(381);
                              var4 = var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
                              var1.setlocal(6, var4);
                              var4 = null;
                           }
                        }
                     }
                  } else {
                     var1.setline(389);
                     var4 = var1.getlocal(3)._add(var1.getlocal(6));
                     var1.setlocal(3, var4);
                     var4 = null;
                  }
               } else {
                  var1.setline(340);
                  var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("%"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(341);
                     var4 = var1.getlocal(3)._add(var1.getlocal(6));
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(342);
                     var4 = var1.getlocal(4)._add(Py.newInteger(1));
                     var1.setlocal(4, var4);
                     var4 = null;
                  } else {
                     label120: {
                        var1.setline(344);
                        var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
                        var1.setlocal(0, var4);
                        var4 = null;
                        var1.setline(345);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                        var1.setlocal(5, var4);
                        var4 = null;

                        try {
                           var1.setline(347);
                           var4 = var1.getlocal(0).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
                           var1.setlocal(4, var4);
                           var4 = null;
                        } catch (Throwable var8) {
                           var12 = Py.setException(var8, var1);
                           if (var12.match(var1.getglobal("ValueError"))) {
                              var1.setline(349);
                              var5 = var1.getlocal(3)._add(PyString.fromInterned("%"))._add(var1.getlocal(0));
                              var1.setlocal(3, var5);
                              var5 = null;
                              var1.setline(350);
                              var5 = var1.getlocal(5)._sub(Py.newInteger(1));
                              var1.setlocal(4, var5);
                              var5 = null;
                              break label120;
                           }

                           throw var12;
                        }

                        var1.setline(352);
                        var5 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
                        var1.setlocal(7, var5);
                        var5 = null;
                        var1.setline(353);
                        var5 = var1.getlocal(7);
                        var10000 = var5._in(var1.getglobal("os").__getattr__("environ"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(354);
                           var5 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(7)));
                           var1.setlocal(3, var5);
                           var5 = null;
                        } else {
                           var1.setline(356);
                           var5 = var1.getlocal(3)._add(PyString.fromInterned("%"))._add(var1.getlocal(7))._add(PyString.fromInterned("%"));
                           var1.setlocal(3, var5);
                           var5 = null;
                        }
                     }
                  }
               }
            }

            var1.setline(390);
            var4 = var1.getlocal(4)._add(Py.newInteger(1));
            var1.setlocal(4, var4);
            var4 = null;
         }
      }
   }

   public PyObject normpath$15(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyString.fromInterned("Normalize path, eliminating double slashes, etc.");
      var1.setline(401);
      var1.setline(401);
      PyTuple var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__() ? new PyTuple(new PyObject[]{PyUnicode.fromInterned("\\"), PyUnicode.fromInterned(".")}) : new PyTuple(new PyObject[]{PyString.fromInterned("\\"), PyString.fromInterned(".")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(402);
      PyObject var7;
      if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("\\\\.\\"), PyString.fromInterned("\\\\?\\")}))).__nonzero__()) {
         var1.setline(407);
         var7 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(408);
         PyObject var8 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("\\"));
         var1.setlocal(0, var8);
         var4 = null;
         var1.setline(409);
         var8 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(0));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var6 = var9[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(0, var6);
         var6 = null;
         var4 = null;
         var1.setline(419);
         var8 = var1.getlocal(3);
         PyObject var10000 = var8._eq(PyString.fromInterned(""));
         var4 = null;
         if (var10000.__nonzero__()) {
            while(true) {
               var1.setline(421);
               var8 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10000 = var8._eq(PyString.fromInterned("\\"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(422);
               var8 = var1.getlocal(3)._add(var1.getlocal(1));
               var1.setlocal(3, var8);
               var4 = null;
               var1.setline(423);
               var8 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var8);
               var4 = null;
            }
         } else {
            var1.setline(426);
            if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\")).__nonzero__()) {
               var1.setline(427);
               var8 = var1.getlocal(3)._add(var1.getlocal(1));
               var1.setlocal(3, var8);
               var4 = null;
               var1.setline(428);
               var8 = var1.getlocal(0).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"));
               var1.setlocal(0, var8);
               var4 = null;
            }
         }

         var1.setline(429);
         var8 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"));
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(430);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(5, var10);
         var4 = null;

         while(true) {
            var1.setline(431);
            var8 = var1.getlocal(5);
            var10000 = var8._lt(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(445);
               var10000 = var1.getlocal(3).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(446);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
               }

               var1.setline(447);
               var7 = var1.getlocal(3)._add(var1.getlocal(1).__getattr__("join").__call__(var2, var1.getlocal(4)));
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(432);
            var8 = var1.getlocal(4).__getitem__(var1.getlocal(5));
            var10000 = var8._in(new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(433);
               var1.getlocal(4).__delitem__(var1.getlocal(5));
            } else {
               var1.setline(434);
               var8 = var1.getlocal(4).__getitem__(var1.getlocal(5));
               var10000 = var8._eq(PyString.fromInterned(".."));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(435);
                  var8 = var1.getlocal(5);
                  var10000 = var8._gt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(4).__getitem__(var1.getlocal(5)._sub(Py.newInteger(1)));
                     var10000 = var8._ne(PyString.fromInterned(".."));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(436);
                     var1.getlocal(4).__delslice__(var1.getlocal(5)._sub(Py.newInteger(1)), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
                     var1.setline(437);
                     var8 = var1.getlocal(5);
                     var8 = var8._isub(Py.newInteger(1));
                     var1.setlocal(5, var8);
                  } else {
                     var1.setline(438);
                     var8 = var1.getlocal(5);
                     var10000 = var8._eq(Py.newInteger(0));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(439);
                        var1.getlocal(4).__delitem__(var1.getlocal(5));
                     } else {
                        var1.setline(441);
                        var8 = var1.getlocal(5);
                        var8 = var8._iadd(Py.newInteger(1));
                        var1.setlocal(5, var8);
                     }
                  }
               } else {
                  var1.setline(443);
                  var8 = var1.getlocal(5);
                  var8 = var8._iadd(Py.newInteger(1));
                  var1.setlocal(5, var8);
               }
            }
         }
      }
   }

   public PyObject abspath$16(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyString.fromInterned("Return the absolute version of a path.");
      var1.setline(457);
      PyObject var3;
      if (var1.getglobal("isabs").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(458);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(459);
            var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(461);
            var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(462);
         var3 = var1.getglobal("join").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(463);
      var3 = var1.getglobal("normpath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject abspath$17(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyString.fromInterned("Return the absolute version of a path.");
      var1.setline(469);
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         try {
            var1.setline(471);
            var3 = var1.getglobal("_getfullpathname").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("WindowsError"))) {
               throw var5;
            }

            var1.setline(473);
         }
      } else {
         var1.setline(474);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(475);
            var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(477);
            var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(478);
      var3 = var1.getglobal("normpath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _abspath_split$18(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyObject var3 = var1.getglobal("abspath").__call__(var2, var1.getglobal("normpath").__call__(var2, var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(488);
      var3 = var1.getglobal("splitunc").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(489);
      var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(490);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(491);
         var3 = var1.getglobal("splitdrive").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(492);
      PyTuple var10000 = new PyTuple;
      PyObject[] var10002 = new PyObject[]{var1.getlocal(4), var1.getlocal(2), null};
      PyList var10005 = new PyList();
      var3 = var10005.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(492);
      var3 = var1.getlocal(3).__getattr__("split").__call__(var2, var1.getglobal("sep")).__iter__();

      while(true) {
         var1.setline(492);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(492);
            var1.dellocal(5);
            var10002[2] = var10005;
            var10000.<init>(var10002);
            PyTuple var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(6, var6);
         var1.setline(492);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(492);
            var1.getlocal(5).__call__(var2, var1.getlocal(6));
         }
      }
   }

   public PyObject relpath$19(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("Return a relative version of a path");
      var1.setline(497);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(498);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no path specified")));
      } else {
         var1.setline(500);
         PyObject var3 = var1.getglobal("_abspath_split").__call__(var2, var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(501);
         var3 = var1.getglobal("_abspath_split").__call__(var2, var1.getlocal(0));
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(503);
         if (var1.getlocal(5)._xor(var1.getlocal(2)).__nonzero__()) {
            var1.setline(504);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Cannot mix UNC and non-UNC paths (%s and %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
         } else {
            var1.setline(506);
            var3 = var1.getlocal(6).__getattr__("lower").__call__(var2);
            PyObject var10000 = var3._ne(var1.getlocal(3).__getattr__("lower").__call__(var2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(507);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(508);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("path is on UNC root %s, start on UNC root %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(3)}))));
               } else {
                  var1.setline(511);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("path is on drive %s, start on drive %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(3)}))));
               }
            } else {
               var1.setline(514);
               PyInteger var8 = Py.newInteger(0);
               var1.setlocal(8, var8);
               var3 = null;
               var1.setline(515);
               var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(4), var1.getlocal(7)).__iter__();

               while(true) {
                  var1.setline(515);
                  PyObject var7 = var3.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  PyObject[] var9 = Py.unpackSequence(var7, 2);
                  PyObject var6 = var9[0];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var9[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var1.setline(516);
                  var5 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                  var10000 = var5._ne(var1.getlocal(10).__getattr__("lower").__call__(var2));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(518);
                  var5 = var1.getlocal(8);
                  var5 = var5._iadd(Py.newInteger(1));
                  var1.setlocal(8, var5);
               }

               var1.setline(520);
               var3 = (new PyList(new PyObject[]{var1.getglobal("pardir")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(4))._sub(var1.getlocal(8)))._add(var1.getlocal(7).__getslice__(var1.getlocal(8), (PyObject)null, (PyObject)null));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(521);
               if (var1.getlocal(11).__not__().__nonzero__()) {
                  var1.setline(522);
                  var3 = var1.getglobal("curdir");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(523);
                  var10000 = var1.getglobal("join");
                  var4 = Py.EmptyObjects;
                  String[] var10 = new String[0];
                  var10000 = var10000._callextra(var4, var10, var1.getlocal(11), (PyObject)null);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public ntpath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      normcase$1 = Py.newCode(1, var2, var1, "normcase", 42, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      isabs$2 = Py.newCode(1, var2, var1, "isabs", 55, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "p", "path", "b", "b_wins"};
      join$3 = Py.newCode(2, var2, var1, "join", 63, true, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitdrive$4 = Py.newCode(1, var2, var1, "splitdrive", 122, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "firstTwo", "normp", "index"};
      splitunc$5 = Py.newCode(1, var2, var1, "splitunc", 131, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "d", "i", "head", "tail", "head2"};
      split$6 = Py.newCode(1, var2, var1, "split", 164, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitext$7 = Py.newCode(1, var2, var1, "splitext", 189, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      basename$8 = Py.newCode(1, var2, var1, "basename", 196, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      dirname$9 = Py.newCode(1, var2, var1, "dirname", 203, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      islink$10 = Py.newCode(1, var2, var1, "islink", 210, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "unc", "rest", "p"};
      ismount$11 = Py.newCode(1, var2, var1, "ismount", 222, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"top", "func", "arg", "names", "name"};
      walk$12 = Py.newCode(3, var2, var1, "walk", 239, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "i", "n", "userhome", "drive"};
      expanduser$13 = Py.newCode(1, var2, var1, "expanduser", 275, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "string", "varchars", "res", "index", "pathlen", "c", "var"};
      expandvars$14 = Py.newCode(1, var2, var1, "expandvars", 317, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "backslash", "dot", "prefix", "comps", "i"};
      normpath$15 = Py.newCode(1, var2, var1, "normpath", 398, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "cwd"};
      abspath$16 = Py.newCode(1, var2, var1, "abspath", 455, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      abspath$17 = Py.newCode(1, var2, var1, "abspath", 466, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "abs", "prefix", "rest", "is_unc", "_[492_28]", "x"};
      _abspath_split$18 = Py.newCode(1, var2, var1, "_abspath_split", 486, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "start", "start_is_unc", "start_prefix", "start_list", "path_is_unc", "path_prefix", "path_list", "i", "e1", "e2", "rel_list"};
      relpath$19 = Py.newCode(2, var2, var1, "relpath", 494, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ntpath$py("ntpath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ntpath$py.class);
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
            return this.splitdrive$4(var2, var3);
         case 5:
            return this.splitunc$5(var2, var3);
         case 6:
            return this.split$6(var2, var3);
         case 7:
            return this.splitext$7(var2, var3);
         case 8:
            return this.basename$8(var2, var3);
         case 9:
            return this.dirname$9(var2, var3);
         case 10:
            return this.islink$10(var2, var3);
         case 11:
            return this.ismount$11(var2, var3);
         case 12:
            return this.walk$12(var2, var3);
         case 13:
            return this.expanduser$13(var2, var3);
         case 14:
            return this.expandvars$14(var2, var3);
         case 15:
            return this.normpath$15(var2, var3);
         case 16:
            return this.abspath$16(var2, var3);
         case 17:
            return this.abspath$17(var2, var3);
         case 18:
            return this._abspath_split$18(var2, var3);
         case 19:
            return this.relpath$19(var2, var3);
         default:
            return null;
      }
   }
}
