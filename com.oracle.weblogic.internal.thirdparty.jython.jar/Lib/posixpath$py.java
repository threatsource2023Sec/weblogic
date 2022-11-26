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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("posixpath.py")
public class posixpath$py extends PyFunctionTable implements PyRunnable {
   static posixpath$py self;
   static final PyCode f$0;
   static final PyCode _unicode$1;
   static final PyCode normcase$2;
   static final PyCode isabs$3;
   static final PyCode join$4;
   static final PyCode split$5;
   static final PyCode splitext$6;
   static final PyCode splitdrive$7;
   static final PyCode basename$8;
   static final PyCode dirname$9;
   static final PyCode islink$10;
   static final PyCode lexists$11;
   static final PyCode samefile$12;
   static final PyCode sameopenfile$13;
   static final PyCode samestat$14;
   static final PyCode ismount$15;
   static final PyCode walk$16;
   static final PyCode expanduser$17;
   static final PyCode expandvars$18;
   static final PyCode normpath$19;
   static final PyCode abspath$20;
   static final PyCode realpath$21;
   static final PyCode _joinrealpath$22;
   static final PyCode relpath$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Common operations on Posix pathnames.\n\nInstead of importing this module directly, import os and refer to\nthis module as os.path.  The \"os.path\" name is an alias for this\nmodule on Posix systems; on other systems (e.g. Mac, Windows),\nos.path provides the same operations in a manner specific to that\nplatform, and is an alias to another module (e.g. macpath, ntpath).\n\nSome of this can actually be useful on non-Posix systems too, e.g.\nfor manipulation of the pathname component of URLs.\n"));
      var1.setline(11);
      PyString.fromInterned("Common operations on Posix pathnames.\n\nInstead of importing this module directly, import os and refer to\nthis module as os.path.  The \"os.path\" name is an alias for this\nmodule on Posix systems; on other systems (e.g. Mac, Windows),\nos.path provides the same operations in a manner specific to that\nplatform, and is an alias to another module (e.g. macpath, ntpath).\n\nSome of this can actually be useful on non-Posix systems too, e.g.\nfor manipulation of the pathname component of URLs.\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("genericpath", var1, -1);
      var1.setlocal("genericpath", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(18);
      imp.importAll("genericpath", var1, -1);

      try {
         var1.setline(21);
         var3 = var1.getname("unicode");
         var1.setlocal("_unicode", var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("NameError"))) {
            throw var7;
         }

         var1.setline(25);
         PyObject[] var4 = new PyObject[]{var1.getname("object")};
         PyObject var5 = Py.makeClass("_unicode", var4, _unicode$1);
         var1.setlocal("_unicode", var5);
         var5 = null;
         Arrays.fill(var4, (Object)null);
      }

      var1.setline(28);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("normcase"), PyString.fromInterned("isabs"), PyString.fromInterned("join"), PyString.fromInterned("splitdrive"), PyString.fromInterned("split"), PyString.fromInterned("splitext"), PyString.fromInterned("basename"), PyString.fromInterned("dirname"), PyString.fromInterned("commonprefix"), PyString.fromInterned("getsize"), PyString.fromInterned("getmtime"), PyString.fromInterned("getatime"), PyString.fromInterned("getctime"), PyString.fromInterned("islink"), PyString.fromInterned("exists"), PyString.fromInterned("lexists"), PyString.fromInterned("isdir"), PyString.fromInterned("isfile"), PyString.fromInterned("ismount"), PyString.fromInterned("walk"), PyString.fromInterned("expanduser"), PyString.fromInterned("expandvars"), PyString.fromInterned("normpath"), PyString.fromInterned("abspath"), PyString.fromInterned("samefile"), PyString.fromInterned("sameopenfile"), PyString.fromInterned("samestat"), PyString.fromInterned("curdir"), PyString.fromInterned("pardir"), PyString.fromInterned("sep"), PyString.fromInterned("pathsep"), PyString.fromInterned("defpath"), PyString.fromInterned("altsep"), PyString.fromInterned("extsep"), PyString.fromInterned("devnull"), PyString.fromInterned("realpath"), PyString.fromInterned("supports_unicode_filenames"), PyString.fromInterned("relpath")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(37);
      PyString var9 = PyString.fromInterned(".");
      var1.setlocal("curdir", var9);
      var3 = null;
      var1.setline(38);
      var9 = PyString.fromInterned("..");
      var1.setlocal("pardir", var9);
      var3 = null;
      var1.setline(39);
      var9 = PyString.fromInterned(".");
      var1.setlocal("extsep", var9);
      var3 = null;
      var1.setline(40);
      var9 = PyString.fromInterned("/");
      var1.setlocal("sep", var9);
      var3 = null;
      var1.setline(41);
      var9 = PyString.fromInterned(":");
      var1.setlocal("pathsep", var9);
      var3 = null;
      var1.setline(42);
      var9 = PyString.fromInterned(":/bin:/usr/bin");
      var1.setlocal("defpath", var9);
      var3 = null;
      var1.setline(43);
      var3 = var1.getname("None");
      var1.setlocal("altsep", var3);
      var3 = null;
      var1.setline(44);
      var9 = PyString.fromInterned("/dev/null");
      var1.setlocal("devnull", var9);
      var3 = null;
      var1.setline(51);
      PyObject[] var10 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var10, normcase$2, PyString.fromInterned("Normalize case of pathname.  Has no effect under Posix"));
      var1.setlocal("normcase", var11);
      var3 = null;
      var1.setline(59);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, isabs$3, PyString.fromInterned("Test whether a path is absolute"));
      var1.setlocal("isabs", var11);
      var3 = null;
      var1.setline(68);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, join$4, PyString.fromInterned("Join two or more pathname components, inserting '/' as needed.\n    If any component is an absolute path, all previous path components\n    will be discarded.  An empty last part will result in a path that\n    ends with a separator."));
      var1.setlocal("join", var11);
      var3 = null;
      var1.setline(89);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, split$5, PyString.fromInterned("Split a pathname.  Returns tuple \"(head, tail)\" where \"tail\" is\n    everything after the final slash.  Either part may be empty."));
      var1.setlocal("split", var11);
      var3 = null;
      var1.setline(104);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, splitext$6, (PyObject)null);
      var1.setlocal("splitext", var11);
      var3 = null;
      var1.setline(106);
      var3 = var1.getname("genericpath").__getattr__("_splitext").__getattr__("__doc__");
      var1.getname("splitext").__setattr__("__doc__", var3);
      var3 = null;
      var1.setline(111);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, splitdrive$7, PyString.fromInterned("Split a pathname into drive and path. On Posix, drive is always\n    empty."));
      var1.setlocal("splitdrive", var11);
      var3 = null;
      var1.setline(119);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, basename$8, PyString.fromInterned("Returns the final component of a pathname"));
      var1.setlocal("basename", var11);
      var3 = null;
      var1.setline(127);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, dirname$9, PyString.fromInterned("Returns the directory component of a pathname"));
      var1.setlocal("dirname", var11);
      var3 = null;
      var1.setline(139);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, islink$10, PyString.fromInterned("Test whether a path is a symbolic link"));
      var1.setlocal("islink", var11);
      var3 = null;
      var1.setline(149);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, lexists$11, PyString.fromInterned("Test whether a path exists.  Returns True for broken symbolic links"));
      var1.setlocal("lexists", var11);
      var3 = null;
      var1.setline(160);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, samefile$12, PyString.fromInterned("Test whether two pathnames reference the same actual file"));
      var1.setlocal("samefile", var11);
      var3 = null;
      var1.setline(170);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, sameopenfile$13, PyString.fromInterned("Test whether two open file objects reference the same file"));
      var1.setlocal("sameopenfile", var11);
      var3 = null;
      var1.setline(180);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, samestat$14, PyString.fromInterned("Test whether two stat buffers reference the same file"));
      var1.setlocal("samestat", var11);
      var3 = null;
      var1.setline(189);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, ismount$15, PyString.fromInterned("Test whether a path is a mount point"));
      var1.setlocal("ismount", var11);
      var3 = null;
      var1.setline(218);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, walk$16, PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common."));
      var1.setlocal("walk", var11);
      var3 = null;
      var1.setline(258);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, expanduser$17, PyString.fromInterned("Expand ~ and ~user constructions.  If user or $HOME is unknown,\n    do nothing."));
      var1.setlocal("expanduser", var11);
      var3 = null;
      var1.setline(287);
      var3 = var1.getname("None");
      var1.setlocal("_varprog", var3);
      var3 = null;
      var1.setline(289);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, expandvars$18, PyString.fromInterned("Expand shell variables of form $var and ${var}.  Unknown variables\n    are left unchanged."));
      var1.setlocal("expandvars", var11);
      var3 = null;
      var1.setline(321);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, normpath$19, PyString.fromInterned("Normalize path, eliminating double slashes, etc."));
      var1.setlocal("normpath", var11);
      var3 = null;
      var1.setline(350);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, abspath$20, PyString.fromInterned("Return an absolute path."));
      var1.setlocal("abspath", var11);
      var3 = null;
      var1.setline(364);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, realpath$21, PyString.fromInterned("Return the canonical path of the specified filename, eliminating any\nsymbolic links encountered in the path."));
      var1.setlocal("realpath", var11);
      var3 = null;
      var1.setline(372);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _joinrealpath$22, (PyObject)null);
      var1.setlocal("_joinrealpath", var11);
      var3 = null;
      var1.setline(414);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("darwin"));
      var3 = null;
      var3 = var10000;
      var1.setlocal("supports_unicode_filenames", var3);
      var3 = null;
      var1.setline(416);
      var10 = new PyObject[]{var1.getname("curdir")};
      var11 = new PyFunction(var1.f_globals, var10, relpath$23, PyString.fromInterned("Return a relative version of a path"));
      var1.setlocal("relpath", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(26);
      return var1.getf_locals();
   }

   public PyObject normcase$2(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString.fromInterned("Normalize case of pathname.  Has no effect under Posix");
      var1.setline(53);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isabs$3(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Test whether a path is absolute");
      var1.setline(61);
      PyObject var3 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject join$4(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Join two or more pathname components, inserting '/' as needed.\n    If any component is an absolute path, all previous path components\n    will be discarded.  An empty last part will result in a path that\n    ends with a separator.");
      var1.setline(73);
      PyObject var3 = var1.getlocal(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(74);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(81);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(75);
         PyObject var5;
         if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__nonzero__()) {
            var1.setline(76);
            var5 = var1.getlocal(3);
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(77);
            var5 = var1.getlocal(2);
            PyObject var10000 = var5._eq(PyString.fromInterned(""));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(78);
               var5 = var1.getlocal(2);
               var5 = var5._iadd(var1.getlocal(3));
               var1.setlocal(2, var5);
            } else {
               var1.setline(80);
               var5 = var1.getlocal(2);
               var5 = var5._iadd(PyString.fromInterned("/")._add(var1.getlocal(3)));
               var1.setlocal(2, var5);
            }
         }
      }
   }

   public PyObject split$5(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Split a pathname.  Returns tuple \"(head, tail)\" where \"tail\" is\n    everything after the final slash.  Either part may be empty.");
      var1.setline(92);
      PyObject var3 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"))._add(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(93);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null)});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(94);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._ne(PyString.fromInterned("/")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(95);
         var3 = var1.getlocal(2).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(96);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject splitext$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getglobal("genericpath").__getattr__("_splitext").__call__(var2, var1.getlocal(0), var1.getglobal("sep"), var1.getglobal("altsep"), var1.getglobal("extsep"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject splitdrive$7(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyString.fromInterned("Split a pathname into drive and path. On Posix, drive is always\n    empty.");
      var1.setline(114);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject basename$8(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("Returns the final component of a pathname");
      var1.setline(121);
      PyObject var3 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"))._add(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dirname$9(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString.fromInterned("Returns the directory component of a pathname");
      var1.setline(129);
      PyObject var3 = var1.getlocal(0).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"))._add(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(131);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._ne(PyString.fromInterned("/")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(132);
         var3 = var1.getlocal(2).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(133);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject islink$10(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyString.fromInterned("Test whether a path is a symbolic link");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(142);
         PyObject var6 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("error"), var1.getglobal("AttributeError")}))) {
            var1.setline(144);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(145);
      var4 = var1.getglobal("stat").__getattr__("S_ISLNK").__call__(var2, var1.getlocal(1).__getattr__("st_mode"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject lexists$11(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Test whether a path exists.  Returns True for broken symbolic links");

      PyObject var4;
      try {
         var1.setline(152);
         var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(154);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(155);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject samefile$12(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("Test whether two pathnames reference the same actual file");
      var1.setline(162);
      PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(163);
      var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(164);
      var3 = var1.getglobal("samestat").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sameopenfile$13(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyString.fromInterned("Test whether two open file objects reference the same file");
      var1.setline(172);
      PyObject var3 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getglobal("samestat").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject samestat$14(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("Test whether two stat buffers reference the same file");
      var1.setline(182);
      PyObject var3 = var1.getlocal(0).__getattr__("st_ino");
      PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("st_ino"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("st_dev");
         var10000 = var3._eq(var1.getlocal(1).__getattr__("st_dev"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismount$15(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyString.fromInterned("Test whether a path is a mount point");
      var1.setline(191);
      PyObject var3;
      if (var1.getglobal("islink").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(193);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var6;
         try {
            var1.setline(195);
            var6 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var6);
            var4 = null;
            var1.setline(196);
            var6 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getglobal("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("..")));
            var1.setlocal(2, var6);
            var4 = null;
         } catch (Throwable var5) {
            var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("os").__getattr__("error"))) {
               var1.setline(198);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(199);
         var6 = var1.getlocal(1).__getattr__("st_dev");
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(200);
         var6 = var1.getlocal(2).__getattr__("st_dev");
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(201);
         var6 = var1.getlocal(3);
         PyObject var10000 = var6._ne(var1.getlocal(4));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(203);
            var6 = var1.getlocal(1).__getattr__("st_ino");
            var1.setlocal(5, var6);
            var4 = null;
            var1.setline(204);
            var6 = var1.getlocal(2).__getattr__("st_ino");
            var1.setlocal(6, var6);
            var4 = null;
            var1.setline(205);
            var6 = var1.getlocal(5);
            var10000 = var6._eq(var1.getlocal(6));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(206);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(207);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject walk$16(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyString.fromInterned("Directory tree walk with callback function.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), call func(arg, dirname, fnames).\n    dirname is the name of the directory, and fnames a list of the names of\n    the files and subdirectories in dirname (excluding '.' and '..').  func\n    may modify the fnames list in-place (e.g. via del or slice assignment),\n    and walk will only recurse into the subdirectories whose names remain in\n    fnames; this can be used to implement a filter, or to impose a specific\n    order of visiting.  No semantics are defined for, or required of, arg,\n    beyond that arg is always passed to func.  It can be used, e.g., to pass\n    a filename pattern, or a mutable object designed to accumulate\n    statistics.  Passing None for arg is common.");
      var1.setline(232);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warnpy3k");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("In 3.x, os.path.walk is removed in favor of os.walk."), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;

      PyObject var9;
      try {
         var1.setline(235);
         var9 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var9);
         var3 = null;
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(237);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      var1.setline(238);
      var1.getlocal(1).__call__(var2, var1.getlocal(2), var1.getlocal(0), var1.getlocal(3));
      var1.setline(239);
      var9 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(239);
         PyObject var10 = var9.__iternext__();
         if (var10 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var10);
         var1.setline(240);
         PyObject var5 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(4, var5);
         var5 = null;

         try {
            var1.setline(242);
            var5 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("os").__getattr__("error"))) {
               continue;
            }

            throw var11;
         }

         var1.setline(245);
         if (var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(5).__getattr__("st_mode")).__nonzero__()) {
            var1.setline(246);
            var1.getglobal("walk").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2));
         }
      }
   }

   public PyObject expanduser$17(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Expand ~ and ~user constructions.  If user or $HOME is unknown,\n    do nothing.");
      var1.setline(261);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~")).__not__().__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(263);
         PyObject var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)Py.newInteger(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(264);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._lt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(265);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(266);
         var4 = var1.getlocal(1);
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(267);
            PyString var7 = PyString.fromInterned("HOME");
            var10000 = var7._notin(var1.getglobal("os").__getattr__("environ"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(268);
               var4 = imp.importOne("pwd", var1, -1);
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(269);
               var4 = var1.getlocal(2).__getattr__("getpwuid").__call__(var2, var1.getglobal("os").__getattr__("getuid").__call__(var2)).__getattr__("pw_dir");
               var1.setlocal(3, var4);
               var4 = null;
            } else {
               var1.setline(271);
               var4 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME"));
               var1.setlocal(3, var4);
               var4 = null;
            }
         } else {
            var1.setline(273);
            var4 = imp.importOne("pwd", var1, -1);
            var1.setlocal(2, var4);
            var4 = null;

            try {
               var1.setline(275);
               var4 = var1.getlocal(2).__getattr__("getpwnam").__call__(var2, var1.getlocal(0).__getslice__(Py.newInteger(1), var1.getlocal(1), (PyObject)null));
               var1.setlocal(4, var4);
               var4 = null;
            } catch (Throwable var5) {
               PyException var8 = Py.setException(var5, var1);
               if (var8.match(var1.getglobal("KeyError"))) {
                  var1.setline(277);
                  var3 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var3;
               }

               throw var8;
            }

            var1.setline(278);
            var4 = var1.getlocal(4).__getattr__("pw_dir");
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(279);
         var4 = var1.getlocal(3).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(280);
         Object var9 = var1.getlocal(3)._add(var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null));
         if (!((PyObject)var9).__nonzero__()) {
            var9 = PyString.fromInterned("/");
         }

         Object var6 = var9;
         var1.f_lasti = -1;
         return (PyObject)var6;
      }
   }

   public PyObject expandvars$18(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("Expand shell variables of form $var and ${var}.  Unknown variables\n    are left unchanged.");
      var1.setline(293);
      PyString var3 = PyString.fromInterned("$");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(294);
         var7 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(295);
         PyObject var4;
         if (var1.getglobal("_varprog").__not__().__nonzero__()) {
            var1.setline(296);
            var4 = imp.importOne("re", var1, -1);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(297);
            var4 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$(\\w+|\\{[^}]*\\})"));
            var1.setglobal("_varprog", var4);
            var4 = null;
         }

         var1.setline(298);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(2, var8);
         var4 = null;

         while(true) {
            var1.setline(299);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(300);
            var4 = var1.getglobal("_varprog").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(301);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               break;
            }

            var1.setline(303);
            var4 = var1.getlocal(3).__getattr__("span").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(304);
            var4 = var1.getlocal(3).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(305);
            var10000 = var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(306);
               var4 = var1.getlocal(5).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(307);
            var4 = var1.getlocal(5);
            var10000 = var4._in(var1.getglobal("os").__getattr__("environ"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(308);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(309);
               var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)._add(var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(5)));
               var1.setlocal(0, var4);
               var4 = null;
               var1.setline(310);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(311);
               var4 = var1.getlocal(0);
               var4 = var4._iadd(var1.getlocal(6));
               var1.setlocal(0, var4);
            } else {
               var1.setline(313);
               var4 = var1.getlocal(4);
               var1.setlocal(2, var4);
               var4 = null;
            }
         }

         var1.setline(314);
         var7 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject normpath$19(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Normalize path, eliminating double slashes, etc.");
      var1.setline(324);
      var1.setline(324);
      PyTuple var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_unicode")).__nonzero__() ? new PyTuple(new PyObject[]{PyUnicode.fromInterned("/"), PyUnicode.fromInterned(".")}) : new PyTuple(new PyObject[]{PyString.fromInterned("/"), PyString.fromInterned(".")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(325);
      PyObject var7 = var1.getlocal(0);
      PyObject var10000 = var7._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(326);
         var7 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(327);
         PyObject var8 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(3, var8);
         var4 = null;
         var1.setline(330);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("//"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("///")).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(332);
            PyInteger var9 = Py.newInteger(2);
            var1.setlocal(3, var9);
            var4 = null;
         }

         var1.setline(333);
         var8 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(4, var8);
         var4 = null;
         var1.setline(334);
         PyList var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var10);
         var4 = null;
         var1.setline(335);
         var8 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(335);
            var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(343);
               var8 = var1.getlocal(5);
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(344);
               var8 = var1.getlocal(1).__getattr__("join").__call__(var2, var1.getlocal(4));
               var1.setlocal(0, var8);
               var4 = null;
               var1.setline(345);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(346);
                  var8 = var1.getlocal(1)._mul(var1.getlocal(3))._add(var1.getlocal(0));
                  var1.setlocal(0, var8);
                  var4 = null;
               }

               var1.setline(347);
               var10000 = var1.getlocal(0);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }

               var7 = var10000;
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(6, var5);
            var1.setline(336);
            PyObject var6 = var1.getlocal(6);
            var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(".")}));
            var6 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(338);
               var6 = var1.getlocal(6);
               var10000 = var6._ne(PyString.fromInterned(".."));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(3).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(5).__not__();
                  }

                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(5);
                     if (var10000.__nonzero__()) {
                        var6 = var1.getlocal(5).__getitem__(Py.newInteger(-1));
                        var10000 = var6._eq(PyString.fromInterned(".."));
                        var6 = null;
                     }
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(340);
                  var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6));
               } else {
                  var1.setline(341);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(342);
                     var1.getlocal(5).__getattr__("pop").__call__(var2);
                  }
               }
            }
         }
      }
   }

   public PyObject abspath$20(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      PyString.fromInterned("Return an absolute path.");
      var1.setline(352);
      PyObject var3;
      if (var1.getglobal("isabs").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(353);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_unicode")).__nonzero__()) {
            var1.setline(354);
            var3 = var1.getglobal("os").__getattr__("getcwdu").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(356);
            var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(357);
         var3 = var1.getglobal("join").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(358);
      var3 = var1.getglobal("normpath").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject realpath$21(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyString.fromInterned("Return the canonical path of the specified filename, eliminating any\nsymbolic links encountered in the path.");
      var1.setline(367);
      PyObject var3 = var1.getglobal("_joinrealpath").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(0), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(368);
      var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _joinrealpath$22(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyObject var3;
      if (var1.getglobal("isabs").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(374);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(375);
         var3 = var1.getglobal("sep");
         var1.setlocal(0, var3);
         var3 = null;
      }

      while(true) {
         var1.setline(377);
         PyTuple var9;
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(411);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("True")});
            var1.f_lasti = -1;
            return var9;
         }

         var1.setline(378);
         var3 = var1.getlocal(1).__getattr__("partition").__call__(var2, var1.getglobal("sep"));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
         var1.setline(379);
         PyObject var10000 = var1.getlocal(3).__not__();
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._eq(var1.getglobal("curdir"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(382);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(var1.getglobal("pardir"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(384);
               if (var1.getlocal(0).__nonzero__()) {
                  var1.setline(385);
                  var3 = var1.getglobal("split").__call__(var2, var1.getlocal(0));
                  var4 = Py.unpackSequence(var3, 2);
                  var5 = var4[0];
                  var1.setlocal(0, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(3, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(386);
                  var3 = var1.getlocal(3);
                  var10000 = var3._eq(var1.getglobal("pardir"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(387);
                     var3 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getglobal("pardir"), var1.getglobal("pardir"));
                     var1.setlocal(0, var3);
                     var3 = null;
                  }
               } else {
                  var1.setline(389);
                  var3 = var1.getglobal("pardir");
                  var1.setlocal(0, var3);
                  var3 = null;
               }
            } else {
               var1.setline(391);
               var3 = var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(3));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(392);
               if (var1.getglobal("islink").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
                  var1.setline(393);
                  var3 = var1.getlocal(5);
                  var1.setlocal(0, var3);
                  var3 = null;
               } else {
                  var1.setline(396);
                  var3 = var1.getlocal(5);
                  var10000 = var3._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(398);
                     var3 = var1.getlocal(2).__getitem__(var1.getlocal(5));
                     var1.setlocal(0, var3);
                     var3 = null;
                     var1.setline(399);
                     var3 = var1.getlocal(0);
                     var10000 = var3._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(404);
                        var9 = new PyTuple(new PyObject[]{var1.getglobal("join").__call__(var2, var1.getlocal(5), var1.getlocal(1)), var1.getglobal("False")});
                        var1.f_lasti = -1;
                        return var9;
                     }
                  } else {
                     var1.setline(405);
                     PyObject var7 = var1.getglobal("None");
                     var1.getlocal(2).__setitem__(var1.getlocal(5), var7);
                     var4 = null;
                     var1.setline(406);
                     var7 = var1.getglobal("_joinrealpath").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(5)), var1.getlocal(2));
                     PyObject[] var8 = Py.unpackSequence(var7, 2);
                     PyObject var6 = var8[0];
                     var1.setlocal(0, var6);
                     var6 = null;
                     var6 = var8[1];
                     var1.setlocal(6, var6);
                     var6 = null;
                     var4 = null;
                     var1.setline(407);
                     if (var1.getlocal(6).__not__().__nonzero__()) {
                        var1.setline(408);
                        var9 = new PyTuple(new PyObject[]{var1.getglobal("join").__call__(var2, var1.getlocal(0), var1.getlocal(1)), var1.getglobal("False")});
                        var1.f_lasti = -1;
                        return var9;
                     }

                     var1.setline(409);
                     var7 = var1.getlocal(0);
                     var1.getlocal(2).__setitem__(var1.getlocal(5), var7);
                     var4 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject relpath$23(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyString.fromInterned("Return a relative version of a path");
      var1.setline(419);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(420);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no path specified")));
      } else {
         var1.setline(422);
         PyList var10000 = new PyList();
         PyObject var3 = var10000.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(422);
         var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(1)).__getattr__("split").__call__(var2, var1.getglobal("sep")).__iter__();

         while(true) {
            var1.setline(422);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(422);
               var1.dellocal(3);
               PyList var6 = var10000;
               var1.setlocal(2, var6);
               var3 = null;
               var1.setline(423);
               var10000 = new PyList();
               var3 = var10000.__getattr__("append");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(423);
               var3 = var1.getglobal("abspath").__call__(var2, var1.getlocal(0)).__getattr__("split").__call__(var2, var1.getglobal("sep")).__iter__();

               while(true) {
                  var1.setline(423);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(423);
                     var1.dellocal(6);
                     var6 = var10000;
                     var1.setlocal(5, var6);
                     var3 = null;
                     var1.setline(426);
                     var3 = var1.getglobal("len").__call__(var2, var1.getglobal("commonprefix").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2), var1.getlocal(5)}))));
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(428);
                     var3 = (new PyList(new PyObject[]{var1.getglobal("pardir")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(7)))._add(var1.getlocal(5).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(429);
                     if (var1.getlocal(8).__not__().__nonzero__()) {
                        var1.setline(430);
                        var3 = var1.getglobal("curdir");
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(431);
                     PyObject var8 = var1.getglobal("join");
                     PyObject[] var7 = Py.EmptyObjects;
                     String[] var5 = new String[0];
                     var8 = var8._callextra(var7, var5, var1.getlocal(8), (PyObject)null);
                     var4 = null;
                     var3 = var8;
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(423);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(423);
                     var1.getlocal(6).__call__(var2, var1.getlocal(4));
                  }
               }
            }

            var1.setlocal(4, var4);
            var1.setline(422);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(422);
               var1.getlocal(3).__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public posixpath$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _unicode$1 = Py.newCode(0, var2, var1, "_unicode", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      normcase$2 = Py.newCode(1, var2, var1, "normcase", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      isabs$3 = Py.newCode(1, var2, var1, "isabs", 59, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "p", "path", "b"};
      join$4 = Py.newCode(2, var2, var1, "join", 68, true, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "i", "head", "tail"};
      split$5 = Py.newCode(1, var2, var1, "split", 89, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitext$6 = Py.newCode(1, var2, var1, "splitext", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      splitdrive$7 = Py.newCode(1, var2, var1, "splitdrive", 111, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "i"};
      basename$8 = Py.newCode(1, var2, var1, "basename", 119, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "i", "head"};
      dirname$9 = Py.newCode(1, var2, var1, "dirname", 127, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "st"};
      islink$10 = Py.newCode(1, var2, var1, "islink", 139, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      lexists$11 = Py.newCode(1, var2, var1, "lexists", 149, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f1", "f2", "s1", "s2"};
      samefile$12 = Py.newCode(2, var2, var1, "samefile", 160, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp1", "fp2", "s1", "s2"};
      sameopenfile$13 = Py.newCode(2, var2, var1, "sameopenfile", 170, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s1", "s2"};
      samestat$14 = Py.newCode(2, var2, var1, "samestat", 180, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "s1", "s2", "dev1", "dev2", "ino1", "ino2"};
      ismount$15 = Py.newCode(1, var2, var1, "ismount", 189, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"top", "func", "arg", "names", "name", "st"};
      walk$16 = Py.newCode(3, var2, var1, "walk", 218, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "i", "pwd", "userhome", "pwent"};
      expanduser$17 = Py.newCode(1, var2, var1, "expanduser", 258, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "re", "i", "m", "j", "name", "tail"};
      expandvars$18 = Py.newCode(1, var2, var1, "expandvars", 289, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "slash", "dot", "initial_slashes", "comps", "new_comps", "comp"};
      normpath$19 = Py.newCode(1, var2, var1, "normpath", 321, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "cwd"};
      abspath$20 = Py.newCode(1, var2, var1, "abspath", 350, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "path", "ok"};
      realpath$21 = Py.newCode(1, var2, var1, "realpath", 364, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "rest", "seen", "name", "_", "newpath", "ok"};
      _joinrealpath$22 = Py.newCode(3, var2, var1, "_joinrealpath", 372, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "start", "start_list", "_[422_18]", "x", "path_list", "_[423_17]", "i", "rel_list"};
      relpath$23 = Py.newCode(2, var2, var1, "relpath", 416, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new posixpath$py("posixpath$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(posixpath$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._unicode$1(var2, var3);
         case 2:
            return this.normcase$2(var2, var3);
         case 3:
            return this.isabs$3(var2, var3);
         case 4:
            return this.join$4(var2, var3);
         case 5:
            return this.split$5(var2, var3);
         case 6:
            return this.splitext$6(var2, var3);
         case 7:
            return this.splitdrive$7(var2, var3);
         case 8:
            return this.basename$8(var2, var3);
         case 9:
            return this.dirname$9(var2, var3);
         case 10:
            return this.islink$10(var2, var3);
         case 11:
            return this.lexists$11(var2, var3);
         case 12:
            return this.samefile$12(var2, var3);
         case 13:
            return this.sameopenfile$13(var2, var3);
         case 14:
            return this.samestat$14(var2, var3);
         case 15:
            return this.ismount$15(var2, var3);
         case 16:
            return this.walk$16(var2, var3);
         case 17:
            return this.expanduser$17(var2, var3);
         case 18:
            return this.expandvars$18(var2, var3);
         case 19:
            return this.normpath$19(var2, var3);
         case 20:
            return this.abspath$20(var2, var3);
         case 21:
            return this.realpath$21(var2, var3);
         case 22:
            return this._joinrealpath$22(var2, var3);
         case 23:
            return this.relpath$23(var2, var3);
         default:
            return null;
      }
   }
}
