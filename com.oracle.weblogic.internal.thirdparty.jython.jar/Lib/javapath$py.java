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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("javapath.py")
public class javapath$py extends PyFunctionTable implements PyRunnable {
   static javapath$py self;
   static final PyCode f$0;
   static final PyCode _tostr$1;
   static final PyCode _type_name$2;
   static final PyCode dirname$3;
   static final PyCode basename$4;
   static final PyCode split$5;
   static final PyCode splitext$6;
   static final PyCode splitdrive$7;
   static final PyCode exists$8;
   static final PyCode isabs$9;
   static final PyCode isfile$10;
   static final PyCode isdir$11;
   static final PyCode join$12;
   static final PyCode normcase$13;
   static final PyCode commonprefix$14;
   static final PyCode islink$15;
   static final PyCode samefile$16;
   static final PyCode ismount$17;
   static final PyCode walk$18;
   static final PyCode expanduser$19;
   static final PyCode getuser$20;
   static final PyCode gethome$21;
   static final PyCode normpath$22;
   static final PyCode abspath$23;
   static final PyCode _abspath$24;
   static final PyCode realpath$25;
   static final PyCode _realpath$26;
   static final PyCode getsize$27;
   static final PyCode getmtime$28;
   static final PyCode getatime$29;
   static final PyCode expandvars$30;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Common pathname manipulations, JDK version.\n\nInstead of importing this module directly, import os and refer to this\nmodule as os.path.\n\n"));
      var1.setline(6);
      PyString.fromInterned("Common pathname manipulations, JDK version.\n\nInstead of importing this module directly, import os and refer to this\nmodule as os.path.\n\n");
      var1.setline(16);
      PyObject var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(18);
      String[] var5 = new String[]{"File"};
      PyObject[] var6 = imp.importFrom("java.io", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("File", var4);
      var4 = null;
      var1.setline(19);
      var3 = imp.importOne("java.io.IOException", var1, -1);
      var1.setlocal("java", var3);
      var3 = null;
      var1.setline(20);
      var5 = new String[]{"System"};
      var6 = imp.importFrom("java.lang", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("System", var4);
      var4 = null;
      var1.setline(21);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(23);
      var5 = new String[]{"newString"};
      var6 = imp.importFrom("org.python.core.Py", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("asPyString", var4);
      var4 = null;
      var1.setline(24);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(25);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("The javapath module is deprecated. Use the os.path module."), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(29);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _tostr$1, (PyObject)null);
      var1.setlocal("_tostr", var7);
      var3 = null;
      var1.setline(35);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _type_name$2, (PyObject)null);
      var1.setlocal("_type_name", var7);
      var3 = null;
      var1.setline(45);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, dirname$3, PyString.fromInterned("Return the directory component of a pathname"));
      var1.setlocal("dirname", var7);
      var3 = null;
      var1.setline(56);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, basename$4, PyString.fromInterned("Return the final component of a pathname"));
      var1.setlocal("basename", var7);
      var3 = null;
      var1.setline(61);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, split$5, PyString.fromInterned("Split a pathname.\n\n    Return tuple \"(head, tail)\" where \"tail\" is everything after the\n    final slash.  Either part may be empty.\n\n    "));
      var1.setlocal("split", var7);
      var3 = null;
      var1.setline(71);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, splitext$6, PyString.fromInterned("Split the extension from a pathname.\n\n    Extension is everything from the last dot to the end.  Return\n    \"(root, ext)\", either part may be empty.\n\n    "));
      var1.setlocal("splitext", var7);
      var3 = null;
      var1.setline(88);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, splitdrive$7, PyString.fromInterned("Split a pathname into drive and path specifiers.\n\n    Returns a 2-tuple \"(drive,path)\"; either part may be empty.\n    "));
      var1.setlocal("splitdrive", var7);
      var3 = null;
      var1.setline(99);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, exists$8, PyString.fromInterned("Test whether a path exists.\n\n    Returns false for broken symbolic links.\n\n    "));
      var1.setlocal("exists", var7);
      var3 = null;
      var1.setline(108);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isabs$9, PyString.fromInterned("Test whether a path is absolute"));
      var1.setlocal("isabs", var7);
      var3 = null;
      var1.setline(113);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isfile$10, PyString.fromInterned("Test whether a path is a regular file"));
      var1.setlocal("isfile", var7);
      var3 = null;
      var1.setline(118);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, isdir$11, PyString.fromInterned("Test whether a path is a directory"));
      var1.setlocal("isdir", var7);
      var3 = null;
      var1.setline(123);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, join$12, PyString.fromInterned("Join two or more pathname components, inserting os.sep as needed"));
      var1.setlocal("join", var7);
      var3 = null;
      var1.setline(138);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, normcase$13, PyString.fromInterned("Normalize case of pathname.\n\n    XXX Not done right under JDK.\n\n    "));
      var1.setlocal("normcase", var7);
      var3 = null;
      var1.setline(147);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, commonprefix$14, PyString.fromInterned("Given a list of pathnames, return the longest common leading component"));
      var1.setlocal("commonprefix", var7);
      var3 = null;
      var1.setline(159);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, islink$15, PyString.fromInterned("Test whether a path is a symbolic link"));
      var1.setlocal("islink", var7);
      var3 = null;
      var1.setline(167);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, samefile$16, PyString.fromInterned("Test whether two pathnames reference the same actual file"));
      var1.setlocal("samefile", var7);
      var3 = null;
      var1.setline(173);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, ismount$17, PyString.fromInterned("Test whether a path is a mount point.\n\n    XXX This incorrectly always returns false under JDK.\n\n    "));
      var1.setlocal("ismount", var7);
      var3 = null;
      var1.setline(181);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, walk$18, PyString.fromInterned("Walk a directory tree.\n\n    walk(top,func,args) calls func(arg, d, files) for each directory\n    \"d\" in the tree rooted at \"top\" (including \"top\" itself).  \"files\"\n    is a list of all the files and subdirs in directory \"d\".\n\n    "));
      var1.setlocal("walk", var7);
      var3 = null;
      var1.setline(199);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, expanduser$19, (PyObject)null);
      var1.setlocal("expanduser", var7);
      var3 = null;
      var1.setline(208);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getuser$20, (PyObject)null);
      var1.setlocal("getuser", var7);
      var3 = null;
      var1.setline(211);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, gethome$21, (PyObject)null);
      var1.setlocal("gethome", var7);
      var3 = null;
      var1.setline(220);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, normpath$22, PyString.fromInterned("Normalize path, eliminating double slashes, etc."));
      var1.setlocal("normpath", var7);
      var3 = null;
      var1.setline(252);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, abspath$23, PyString.fromInterned("Return an absolute path normalized but symbolic links not eliminated"));
      var1.setlocal("abspath", var7);
      var3 = null;
      var1.setline(257);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _abspath$24, (PyObject)null);
      var1.setlocal("_abspath", var7);
      var3 = null;
      var1.setline(262);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, realpath$25, PyString.fromInterned("Return an absolute path normalized and symbolic links eliminated"));
      var1.setlocal("realpath", var7);
      var3 = null;
      var1.setline(267);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _realpath$26, (PyObject)null);
      var1.setlocal("_realpath", var7);
      var3 = null;
      var1.setline(273);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getsize$27, (PyObject)null);
      var1.setlocal("getsize", var7);
      var3 = null;
      var1.setline(283);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getmtime$28, (PyObject)null);
      var1.setlocal("getmtime", var7);
      var3 = null;
      var1.setline(290);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getatime$29, (PyObject)null);
      var1.setlocal("getatime", var7);
      var3 = null;
      var1.setline(311);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, expandvars$30, PyString.fromInterned("Expand shell variables of form $var and ${var}.\n\n    Unknown variables are left unchanged."));
      var1.setlocal("expandvars", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _tostr$1(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(31);
         PyObject var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(32);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("%s() argument must be a str or unicode object, not %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("_type_name").__call__(var2, var1.getlocal(0))})));
      }
   }

   public PyObject _type_name$2(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = Py.newInteger(1)._lshift(Py.newInteger(9));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(37);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(3).__getattr__("__flags__")._and(var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(40);
      var10000 = var1.getlocal(4).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3).__getattr__("__module__");
         var10000 = var3._ne(PyString.fromInterned("__builtin__"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(41);
         var3 = PyString.fromInterned("%s.")._mod(var1.getlocal(3).__getattr__("__module__"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(42);
      var3 = var1.getlocal(2);
      var3 = var3._iadd(var1.getlocal(3).__getattr__("__name__"));
      var1.setlocal(2, var3);
      var1.setline(43);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dirname$3(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyString.fromInterned("Return the directory component of a pathname");
      var1.setline(47);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("dirname"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getlocal(0)).__getattr__("getParent").__call__(var2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(49);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(50);
         if (var1.getglobal("isabs").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(51);
            var3 = var1.getlocal(0);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(53);
            PyString var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var3 = null;
         }
      }

      var1.setline(54);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject basename$4(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Return the final component of a pathname");
      var1.setline(58);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("basename"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getlocal(0)).__getattr__("getName").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject split$5(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Split a pathname.\n\n    Return tuple \"(head, tail)\" where \"tail\" is everything after the\n    final slash.  Either part may be empty.\n\n    ");
      var1.setline(68);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("split"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(69);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("dirname").__call__(var2, var1.getlocal(0)), var1.getglobal("basename").__call__(var2, var1.getlocal(0))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject splitext$6(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Split the extension from a pathname.\n\n    Extension is everything from the last dot to the end.  Return\n    \"(root, ext)\", either part may be empty.\n\n    ");
      var1.setline(78);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(79);
      var3 = Py.newInteger(-1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(80);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(80);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(83);
            var6 = var1.getlocal(2);
            var10000 = var6._lt(Py.newInteger(0));
            var3 = null;
            PyTuple var7;
            if (var10000.__nonzero__()) {
               var1.setline(84);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(0), PyString.fromInterned("")});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(86);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(3, var4);
         var1.setline(81);
         PyObject var5 = var1.getlocal(3);
         var10000 = var5._eq(PyString.fromInterned("."));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(81);
            var5 = var1.getlocal(1);
            var1.setlocal(2, var5);
            var5 = null;
         }

         var1.setline(82);
         var5 = var1.getlocal(1)._add(Py.newInteger(1));
         var1.setlocal(1, var5);
         var5 = null;
      }
   }

   public PyObject splitdrive$7(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Split a pathname into drive and path specifiers.\n\n    Returns a 2-tuple \"(drive,path)\"; either part may be empty.\n    ");
      var1.setline(94);
      PyObject var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned(":"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
         var10000 = var3._in(PyString.fromInterned("abcdefghijklmnopqrstuvwxyz"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
               var10000 = var3._in(PyString.fromInterned("/\\"));
               var3 = null;
            }
         }
      }

      PyTuple var4;
      if (var10000.__nonzero__()) {
         var1.setline(96);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null), var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(97);
         var4 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject exists$8(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Test whether a path exists.\n\n    Returns false for broken symbolic links.\n\n    ");
      var1.setline(105);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("exists"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0))).__getattr__("exists").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isabs$9(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("Test whether a path is absolute");
      var1.setline(110);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("isabs"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(111);
      var3 = var1.getglobal("File").__call__(var2, var1.getlocal(0)).__getattr__("isAbsolute").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfile$10(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyString.fromInterned("Test whether a path is a regular file");
      var1.setline(115);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("isfile"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0))).__getattr__("isFile").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdir$11(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyString.fromInterned("Test whether a path is a directory");
      var1.setline(120);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("isdir"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0))).__getattr__("isDirectory").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject join$12(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Join two or more pathname components, inserting os.sep as needed");
      var1.setline(125);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("join"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("File").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(127);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(136);
            var3 = var1.getglobal("asPyString").__call__(var2, var1.getlocal(2).__getattr__("getPath").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(128);
         PyObject var5 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("join"));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(129);
         var5 = var1.getglobal("File").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(130);
         PyObject var10000 = var1.getlocal(4).__getattr__("isAbsolute").__call__(var2);
         if (!var10000.__nonzero__()) {
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("getPath").__call__(var2));
            var10000 = var5._eq(Py.newInteger(0));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(131);
            var5 = var1.getlocal(4);
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(133);
            var5 = var1.getlocal(3);
            var10000 = var5._eq(PyString.fromInterned(""));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(134);
               var5 = var1.getglobal("os").__getattr__("sep");
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(135);
            var5 = var1.getglobal("File").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject normcase$13(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Normalize case of pathname.\n\n    XXX Not done right under JDK.\n\n    ");
      var1.setline(144);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("normcase"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getlocal(0)).__getattr__("getPath").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject commonprefix$14(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("Given a list of pathnames, return the longest common leading component");
      var1.setline(149);
      PyString var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(149);
         var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(150);
         PyObject var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(151);
         var4 = var1.getlocal(0).__iter__();

         PyObject var10000;
         do {
            label27:
            while(true) {
               var1.setline(151);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(157);
                  PyObject var9 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var9;
               }

               var1.setlocal(2, var5);
               var1.setline(152);
               PyObject var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

               PyObject var8;
               do {
                  var1.setline(152);
                  PyObject var7 = var6.__iternext__();
                  if (var7 == null) {
                     continue label27;
                  }

                  var1.setlocal(3, var7);
                  var1.setline(153);
                  var8 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null);
                  var10000 = var8._ne(var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null));
                  var8 = null;
               } while(!var10000.__nonzero__());

               var1.setline(154);
               var8 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
               var1.setlocal(1, var8);
               var8 = null;
               var1.setline(155);
               var8 = var1.getlocal(3);
               var10000 = var8._eq(Py.newInteger(0));
               var8 = null;
               break;
            }
         } while(!var10000.__nonzero__());

         var1.setline(155);
         var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject islink$15(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned("Test whether a path is a symbolic link");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(162);
         PyObject var6 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("error"), var1.getglobal("AttributeError")}))) {
            var1.setline(164);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(165);
      var4 = var1.getglobal("stat").__getattr__("S_ISLNK").__call__(var2, var1.getlocal(1).__getattr__("st_mode"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject samefile$16(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("Test whether two pathnames reference the same actual file");
      var1.setline(169);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("samefile"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("samefile"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getglobal("_realpath").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("_realpath").__call__(var2, var1.getlocal(1)));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismount$17(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyString.fromInterned("Test whether a path is a mount point.\n\n    XXX This incorrectly always returns false under JDK.\n\n    ");
      var1.setline(179);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject walk$18(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("Walk a directory tree.\n\n    walk(top,func,args) calls func(arg, d, files) for each directory\n    \"d\" in the tree rooted at \"top\" (including \"top\" itself).  \"files\"\n    is a list of all the files and subdirs in directory \"d\".\n\n    ");

      PyException var3;
      PyObject var7;
      try {
         var1.setline(190);
         var7 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(192);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(193);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(3));
      var1.setline(194);
      var7 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(194);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(195);
         PyObject var5 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(196);
         PyObject var10000 = var1.getglobal("isdir").__call__(var2, var1.getlocal(4));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("islink").__call__(var2, var1.getlocal(4)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(197);
            var1.getglobal("walk").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
         }
      }
   }

   public PyObject expanduser$19(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("~"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(201);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(202);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(203);
            var3 = var1.getglobal("gethome").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(204);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(var1.getglobal("os").__getattr__("sep"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(205);
            var3 = var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getglobal("gethome").__call__(var2), var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)).__getattr__("getPath").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(206);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getuser$20(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getglobal("System").__getattr__("getProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("user.name"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gethome$21(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3 = var1.getglobal("System").__getattr__("getProperty").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("user.home"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject normpath$22(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Normalize path, eliminating double slashes, etc.");
      var1.setline(222);
      PyObject var3 = var1.getglobal("os").__getattr__("sep");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("\\"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(224);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getlocal(1));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(225);
      var3 = var1.getglobal("os").__getattr__("curdir");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getglobal("os").__getattr__("pardir");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      var3 = imp.importOne("string", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(229);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(5, var4);
      var3 = null;

      while(true) {
         var1.setline(230);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(233);
            var3 = var1.getlocal(4).__getattr__("splitfields").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(234);
            PyInteger var5 = Py.newInteger(0);
            var1.setlocal(7, var5);
            var3 = null;

            while(true) {
               while(true) {
                  var1.setline(235);
                  var3 = var1.getlocal(7);
                  var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(248);
                     var10000 = var1.getlocal(6).__not__();
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(5).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(249);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2));
                     }

                     var1.setline(250);
                     var3 = var1.getlocal(5)._add(var1.getlocal(4).__getattr__("joinfields").__call__(var2, var1.getlocal(6), var1.getlocal(1)));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(236);
                  var3 = var1.getlocal(6).__getitem__(var1.getlocal(7));
                  var10000 = var3._eq(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(237);
                     var1.getlocal(6).__delitem__(var1.getlocal(7));

                     while(true) {
                        var1.setline(238);
                        var3 = var1.getlocal(7);
                        var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(6).__getitem__(var1.getlocal(7));
                           var10000 = var3._eq(PyString.fromInterned(""));
                           var3 = null;
                        }

                        if (!var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(239);
                        var1.getlocal(6).__delitem__(var1.getlocal(7));
                     }
                  } else {
                     var1.setline(240);
                     var3 = var1.getlocal(6).__getitem__(var1.getlocal(7));
                     var10000 = var3._eq(var1.getlocal(3));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(7);
                        var10000 = var3._gt(Py.newInteger(0));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(6).__getitem__(var1.getlocal(7)._sub(Py.newInteger(1)));
                           var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(3)}));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(241);
                        var1.getlocal(6).__delslice__(var1.getlocal(7)._sub(Py.newInteger(1)), var1.getlocal(7)._add(Py.newInteger(1)), (PyObject)null);
                        var1.setline(242);
                        var3 = var1.getlocal(7)._sub(Py.newInteger(1));
                        var1.setlocal(7, var3);
                        var3 = null;
                     } else {
                        var1.setline(243);
                        var3 = var1.getlocal(6).__getitem__(var1.getlocal(7));
                        var10000 = var3._eq(PyString.fromInterned(""));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(7);
                           var10000 = var3._gt(Py.newInteger(0));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var3 = var1.getlocal(6).__getitem__(var1.getlocal(7)._sub(Py.newInteger(1)));
                              var10000 = var3._ne(PyString.fromInterned(""));
                              var3 = null;
                           }
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(244);
                           var1.getlocal(6).__delitem__(var1.getlocal(7));
                        } else {
                           var1.setline(246);
                           var3 = var1.getlocal(7)._add(Py.newInteger(1));
                           var1.setlocal(7, var3);
                           var3 = null;
                        }
                     }
                  }
               }
            }
         }

         var1.setline(231);
         var3 = var1.getlocal(5)._add(var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(232);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }
   }

   public PyObject abspath$23(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Return an absolute path normalized but symbolic links not eliminated");
      var1.setline(254);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("abspath"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getglobal("_abspath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _abspath$24(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyObject var3 = var1.getglobal("normpath").__call__(var2, var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0))).__getattr__("getAbsolutePath").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject realpath$25(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyString.fromInterned("Return an absolute path normalized and symbolic links eliminated");
      var1.setline(264);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("realpath"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getglobal("_realpath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _realpath$26(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(269);
         var3 = var1.getglobal("asPyString").__call__(var2, var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0))).__getattr__("getCanonicalPath").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("java").__getattr__("io").__getattr__("IOException"))) {
            var1.setline(271);
            var3 = var1.getglobal("_abspath").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject getsize$27(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("getsize"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(276);
      var3 = var1.getlocal(1).__getattr__("length").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(279);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("exists").__call__(var2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(280);
         throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("No such file or directory"), (PyObject)var1.getlocal(0)));
      } else {
         var1.setline(281);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getmtime$28(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("getmtime"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(285);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(286);
      if (var1.getlocal(1).__getattr__("exists").__call__(var2).__not__().__nonzero__()) {
         var1.setline(287);
         throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("No such file or directory"), (PyObject)var1.getlocal(0)));
      } else {
         var1.setline(288);
         var3 = var1.getlocal(1).__getattr__("lastModified").__call__(var2)._div(Py.newFloat(1000.0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getatime$29(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyObject var3 = var1.getglobal("_tostr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("getatime"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getglobal("File").__call__(var2, var1.getglobal("sys").__getattr__("getPath").__call__(var2, var1.getlocal(0)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(295);
      if (var1.getlocal(1).__getattr__("exists").__call__(var2).__not__().__nonzero__()) {
         var1.setline(296);
         throw Py.makeException(var1.getglobal("OSError").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("No such file or directory"), (PyObject)var1.getlocal(0)));
      } else {
         var1.setline(297);
         var3 = var1.getlocal(1).__getattr__("lastModified").__call__(var2)._div(Py.newFloat(1000.0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject expandvars$30(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyString.fromInterned("Expand shell variables of form $var and ${var}.\n\n    Unknown variables are left unchanged.");
      var1.setline(315);
      PyString var3 = PyString.fromInterned("$");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      PyObject var8;
      if (var10000.__nonzero__()) {
         var1.setline(316);
         var8 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(317);
         PyObject var4 = imp.importOne("string", var1, -1);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(318);
         var4 = var1.getlocal(1).__getattr__("letters")._add(var1.getlocal(1).__getattr__("digits"))._add(PyString.fromInterned("_-"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(319);
         PyString var9 = PyString.fromInterned("");
         var1.setlocal(3, var9);
         var4 = null;
         var1.setline(320);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(4, var10);
         var4 = null;
         var1.setline(321);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var4);
         var4 = null;

         while(true) {
            var1.setline(322);
            var4 = var1.getlocal(4);
            var10000 = var4._lt(var1.getlocal(5));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(363);
               var8 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(323);
            var4 = var1.getlocal(0).__getitem__(var1.getlocal(4));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(324);
            var4 = var1.getlocal(6);
            var10000 = var4._eq(PyString.fromInterned("'"));
            var4 = null;
            PyObject var5;
            PyException var11;
            if (var10000.__nonzero__()) {
               var1.setline(325);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(326);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var1.setlocal(5, var4);
               var4 = null;

               try {
                  var1.setline(328);
                  var4 = var1.getlocal(0).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(329);
                  var4 = var1.getlocal(3)._add(PyString.fromInterned("'"))._add(var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null));
                  var1.setlocal(3, var4);
                  var4 = null;
               } catch (Throwable var7) {
                  var11 = Py.setException(var7, var1);
                  if (!var11.match(var1.getglobal("ValueError"))) {
                     throw var11;
                  }

                  var1.setline(331);
                  var5 = var1.getlocal(3)._add(var1.getlocal(0));
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(332);
                  var5 = var1.getlocal(5)._sub(Py.newInteger(1));
                  var1.setlocal(4, var5);
                  var5 = null;
               }
            } else {
               var1.setline(333);
               var4 = var1.getlocal(6);
               var10000 = var4._eq(PyString.fromInterned("$"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(334);
                  var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("$"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(335);
                     var4 = var1.getlocal(3)._add(var1.getlocal(6));
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(336);
                     var4 = var1.getlocal(4)._add(Py.newInteger(1));
                     var1.setlocal(4, var4);
                     var4 = null;
                  } else {
                     var1.setline(337);
                     var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("{"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(338);
                        var4 = var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(2)), (PyObject)null, (PyObject)null);
                        var1.setlocal(0, var4);
                        var4 = null;
                        var1.setline(339);
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                        var1.setlocal(5, var4);
                        var4 = null;

                        try {
                           var1.setline(341);
                           var4 = var1.getlocal(0).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"));
                           var1.setlocal(4, var4);
                           var4 = null;
                           var1.setline(342);
                           var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
                           var1.setlocal(7, var4);
                           var4 = null;
                           var1.setline(343);
                           if (var1.getglobal("os").__getattr__("environ").__getattr__("has_key").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                              var1.setline(344);
                              var4 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(7)));
                              var1.setlocal(3, var4);
                              var4 = null;
                           }
                        } catch (Throwable var6) {
                           var11 = Py.setException(var6, var1);
                           if (!var11.match(var1.getglobal("ValueError"))) {
                              throw var11;
                           }

                           var1.setline(346);
                           var5 = var1.getlocal(3)._add(var1.getlocal(0));
                           var1.setlocal(3, var5);
                           var5 = null;
                           var1.setline(347);
                           var5 = var1.getlocal(5)._sub(Py.newInteger(1));
                           var1.setlocal(4, var5);
                           var5 = null;
                        }
                     } else {
                        var1.setline(349);
                        var9 = PyString.fromInterned("");
                        var1.setlocal(7, var9);
                        var4 = null;
                        var1.setline(350);
                        var4 = var1.getlocal(4)._add(Py.newInteger(1));
                        var1.setlocal(4, var4);
                        var4 = null;
                        var1.setline(351);
                        var4 = var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
                        var1.setlocal(6, var4);
                        var4 = null;

                        while(true) {
                           var1.setline(352);
                           var4 = var1.getlocal(6);
                           var10000 = var4._ne(PyString.fromInterned(""));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var4 = var1.getlocal(6);
                              var10000 = var4._in(var1.getlocal(2));
                              var4 = null;
                           }

                           if (!var10000.__nonzero__()) {
                              var1.setline(356);
                              if (var1.getglobal("os").__getattr__("environ").__getattr__("has_key").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                                 var1.setline(357);
                                 var4 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(7)));
                                 var1.setlocal(3, var4);
                                 var4 = null;
                              }

                              var1.setline(358);
                              var4 = var1.getlocal(6);
                              var10000 = var4._ne(PyString.fromInterned(""));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(359);
                                 var4 = var1.getlocal(3)._add(var1.getlocal(6));
                                 var1.setlocal(3, var4);
                                 var4 = null;
                              }
                              break;
                           }

                           var1.setline(353);
                           var4 = var1.getlocal(7)._add(var1.getlocal(6));
                           var1.setlocal(7, var4);
                           var4 = null;
                           var1.setline(354);
                           var4 = var1.getlocal(4)._add(Py.newInteger(1));
                           var1.setlocal(4, var4);
                           var4 = null;
                           var1.setline(355);
                           var4 = var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
                           var1.setlocal(6, var4);
                           var4 = null;
                        }
                     }
                  }
               } else {
                  var1.setline(361);
                  var4 = var1.getlocal(3)._add(var1.getlocal(6));
                  var1.setlocal(3, var4);
                  var4 = null;
               }
            }

            var1.setline(362);
            var4 = var1.getlocal(4)._add(Py.newInteger(1));
            var1.setlocal(4, var4);
            var4 = null;
         }
      }
   }

   public javapath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "method"};
      _tostr$1 = Py.newCode(2, var2, var1, "_tostr", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "TPFLAGS_HEAPTYPE", "type_name", "obj_type", "is_heap"};
      _type_name$2 = Py.newCode(1, var2, var1, "_type_name", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "result"};
      dirname$3 = Py.newCode(1, var2, var1, "dirname", 45, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      basename$4 = Py.newCode(1, var2, var1, "basename", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      split$5 = Py.newCode(1, var2, var1, "split", 61, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "i", "n", "c"};
      splitext$6 = Py.newCode(1, var2, var1, "splitext", 71, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      splitdrive$7 = Py.newCode(1, var2, var1, "splitdrive", 88, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      exists$8 = Py.newCode(1, var2, var1, "exists", 99, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      isabs$9 = Py.newCode(1, var2, var1, "isabs", 108, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      isfile$10 = Py.newCode(1, var2, var1, "isfile", 113, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      isdir$11 = Py.newCode(1, var2, var1, "isdir", 118, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "args", "f", "a", "g"};
      join$12 = Py.newCode(2, var2, var1, "join", 123, true, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      normcase$13 = Py.newCode(1, var2, var1, "normcase", 138, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m", "prefix", "item", "i"};
      commonprefix$14 = Py.newCode(1, var2, var1, "commonprefix", 147, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "st"};
      islink$15 = Py.newCode(1, var2, var1, "islink", 159, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "path2"};
      samefile$16 = Py.newCode(2, var2, var1, "samefile", 167, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      ismount$17 = Py.newCode(1, var2, var1, "ismount", 173, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"top", "func", "arg", "names", "name"};
      walk$18 = Py.newCode(3, var2, var1, "walk", 181, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "c"};
      expanduser$19 = Py.newCode(1, var2, var1, "expanduser", 199, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      getuser$20 = Py.newCode(0, var2, var1, "getuser", 208, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      gethome$21 = Py.newCode(0, var2, var1, "gethome", 211, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "sep", "curdir", "pardir", "string", "slashes", "comps", "i"};
      normpath$22 = Py.newCode(1, var2, var1, "normpath", 220, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      abspath$23 = Py.newCode(1, var2, var1, "abspath", 252, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      _abspath$24 = Py.newCode(1, var2, var1, "_abspath", 257, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      realpath$25 = Py.newCode(1, var2, var1, "realpath", 262, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      _realpath$26 = Py.newCode(1, var2, var1, "_realpath", 267, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "f", "size"};
      getsize$27 = Py.newCode(1, var2, var1, "getsize", 273, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "f"};
      getmtime$28 = Py.newCode(1, var2, var1, "getmtime", 283, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "f"};
      getatime$29 = Py.newCode(1, var2, var1, "getatime", 290, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "string", "varchars", "res", "index", "pathlen", "c", "var"};
      expandvars$30 = Py.newCode(1, var2, var1, "expandvars", 311, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new javapath$py("javapath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(javapath$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._tostr$1(var2, var3);
         case 2:
            return this._type_name$2(var2, var3);
         case 3:
            return this.dirname$3(var2, var3);
         case 4:
            return this.basename$4(var2, var3);
         case 5:
            return this.split$5(var2, var3);
         case 6:
            return this.splitext$6(var2, var3);
         case 7:
            return this.splitdrive$7(var2, var3);
         case 8:
            return this.exists$8(var2, var3);
         case 9:
            return this.isabs$9(var2, var3);
         case 10:
            return this.isfile$10(var2, var3);
         case 11:
            return this.isdir$11(var2, var3);
         case 12:
            return this.join$12(var2, var3);
         case 13:
            return this.normcase$13(var2, var3);
         case 14:
            return this.commonprefix$14(var2, var3);
         case 15:
            return this.islink$15(var2, var3);
         case 16:
            return this.samefile$16(var2, var3);
         case 17:
            return this.ismount$17(var2, var3);
         case 18:
            return this.walk$18(var2, var3);
         case 19:
            return this.expanduser$19(var2, var3);
         case 20:
            return this.getuser$20(var2, var3);
         case 21:
            return this.gethome$21(var2, var3);
         case 22:
            return this.normpath$22(var2, var3);
         case 23:
            return this.abspath$23(var2, var3);
         case 24:
            return this._abspath$24(var2, var3);
         case 25:
            return this.realpath$25(var2, var3);
         case 26:
            return this._realpath$26(var2, var3);
         case 27:
            return this.getsize$27(var2, var3);
         case 28:
            return this.getmtime$28(var2, var3);
         case 29:
            return this.getatime$29(var2, var3);
         case 30:
            return this.expandvars$30(var2, var3);
         default:
            return null;
      }
   }
}
