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
@Filename("tempfile.py")
public class tempfile$py extends PyFunctionTable implements PyRunnable {
   static tempfile$py self;
   static final PyCode f$0;
   static final PyCode _set_cloexec$1;
   static final PyCode _set_cloexec$2;
   static final PyCode _stat$3;
   static final PyCode _exists$4;
   static final PyCode _RandomNameSequence$5;
   static final PyCode __init__$6;
   static final PyCode rng$7;
   static final PyCode __iter__$8;
   static final PyCode next$9;
   static final PyCode _candidate_tempdir_list$10;
   static final PyCode _get_default_tempdir$11;
   static final PyCode _get_candidate_names$12;
   static final PyCode _mkstemp_inner$13;
   static final PyCode gettempprefix$14;
   static final PyCode gettempdir$15;
   static final PyCode mkstemp$16;
   static final PyCode mkdtemp$17;
   static final PyCode mktemp$18;
   static final PyCode _TemporaryFileWrapper$19;
   static final PyCode __init__$20;
   static final PyCode __getattr__$21;
   static final PyCode __enter__$22;
   static final PyCode close$23;
   static final PyCode __del__$24;
   static final PyCode __exit__$25;
   static final PyCode __exit__$26;
   static final PyCode NamedTemporaryFile$27;
   static final PyCode TemporaryFile$28;
   static final PyCode SpooledTemporaryFile$29;
   static final PyCode __init__$30;
   static final PyCode _check$31;
   static final PyCode rollover$32;
   static final PyCode __enter__$33;
   static final PyCode __exit__$34;
   static final PyCode __iter__$35;
   static final PyCode close$36;
   static final PyCode closed$37;
   static final PyCode encoding$38;
   static final PyCode fileno$39;
   static final PyCode flush$40;
   static final PyCode isatty$41;
   static final PyCode mode$42;
   static final PyCode name$43;
   static final PyCode newlines$44;
   static final PyCode next$45;
   static final PyCode read$46;
   static final PyCode readline$47;
   static final PyCode readlines$48;
   static final PyCode seek$49;
   static final PyCode softspace$50;
   static final PyCode tell$51;
   static final PyCode truncate$52;
   static final PyCode write$53;
   static final PyCode writelines$54;
   static final PyCode xreadlines$55;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Temporary files.\n\nThis module provides generic, low- and high-level interfaces for\ncreating temporary files and directories.  The interfaces listed\nas \"safe\" just below can be used without fear of race conditions.\nThose listed as \"unsafe\" cannot, and are provided for backward\ncompatibility only.\n\nThis module also provides some data items to the user:\n\n  TMP_MAX  - maximum number of names that will be tried before\n             giving up.\n  template - the default prefix for all temporary names.\n             You may change this to control the default prefix.\n  tempdir  - If this is set to a string before the first use of\n             any routine from this module, it will be considered as\n             another candidate location to store temporary files.\n"));
      var1.setline(18);
      PyString.fromInterned("Temporary files.\n\nThis module provides generic, low- and high-level interfaces for\ncreating temporary files and directories.  The interfaces listed\nas \"safe\" just below can be used without fear of race conditions.\nThose listed as \"unsafe\" cannot, and are provided for backward\ncompatibility only.\n\nThis module also provides some data items to the user:\n\n  TMP_MAX  - maximum number of names that will be tried before\n             giving up.\n  template - the default prefix for all temporary names.\n             You may change this to control the default prefix.\n  tempdir  - If this is set to a string before the first use of\n             any routine from this module, it will be considered as\n             another candidate location to store temporary files.\n");
      var1.setline(20);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("NamedTemporaryFile"), PyString.fromInterned("TemporaryFile"), PyString.fromInterned("SpooledTemporaryFile"), PyString.fromInterned("mkstemp"), PyString.fromInterned("mkdtemp"), PyString.fromInterned("mktemp"), PyString.fromInterned("TMP_MAX"), PyString.fromInterned("gettempprefix"), PyString.fromInterned("tempdir"), PyString.fromInterned("gettempdir")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(32);
      PyObject var9 = imp.importOneAs("io", var1, -1);
      var1.setlocal("_io", var9);
      var3 = null;
      var1.setline(33);
      var9 = imp.importOneAs("os", var1, -1);
      var1.setlocal("_os", var9);
      var3 = null;
      var1.setline(34);
      var9 = imp.importOneAs("errno", var1, -1);
      var1.setlocal("_errno", var9);
      var3 = null;
      var1.setline(35);
      String[] var12 = new String[]{"Random"};
      PyObject[] var13 = imp.importFrom("random", var12, var1, -1);
      PyObject var4 = var13[0];
      var1.setlocal("_Random", var4);
      var4 = null;

      PyObject[] var11;
      PyException var14;
      try {
         var1.setline(38);
         var12 = new String[]{"StringIO"};
         var13 = imp.importFrom("cStringIO", var12, var1, -1);
         var4 = var13[0];
         var1.setlocal("_StringIO", var4);
         var4 = null;
      } catch (Throwable var8) {
         var14 = Py.setException(var8, var1);
         if (!var14.match(var1.getname("ImportError"))) {
            throw var14;
         }

         var1.setline(40);
         String[] var10 = new String[]{"StringIO"};
         var11 = imp.importFrom("StringIO", var10, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("_StringIO", var5);
         var5 = null;
      }

      label71: {
         PyFunction var15;
         try {
            var1.setline(43);
            var9 = imp.importOneAs("fcntl", var1, -1);
            var1.setlocal("_fcntl", var9);
            var3 = null;
         } catch (Throwable var7) {
            var14 = Py.setException(var7, var1);
            if (var14.match(var1.getname("ImportError"))) {
               var1.setline(45);
               var11 = Py.EmptyObjects;
               var15 = new PyFunction(var1.f_globals, var11, _set_cloexec$1, (PyObject)null);
               var1.setlocal("_set_cloexec", var15);
               var4 = null;
               break label71;
            }

            throw var14;
         }

         var1.setline(48);
         var11 = Py.EmptyObjects;
         var15 = new PyFunction(var1.f_globals, var11, _set_cloexec$2, (PyObject)null);
         var1.setlocal("_set_cloexec", var15);
         var4 = null;
      }

      try {
         var1.setline(60);
         var9 = imp.importOneAs("thread", var1, -1);
         var1.setlocal("_thread", var9);
         var3 = null;
      } catch (Throwable var6) {
         var14 = Py.setException(var6, var1);
         if (!var14.match(var1.getname("ImportError"))) {
            throw var14;
         }

         var1.setline(62);
         var4 = imp.importOneAs("dummy_thread", var1, -1);
         var1.setlocal("_thread", var4);
         var4 = null;
      }

      var1.setline(63);
      var9 = var1.getname("_thread").__getattr__("allocate_lock");
      var1.setlocal("_allocate_lock", var9);
      var3 = null;
      var1.setline(65);
      var9 = var1.getname("_os").__getattr__("O_RDWR")._or(var1.getname("_os").__getattr__("O_CREAT"))._or(var1.getname("_os").__getattr__("O_EXCL"));
      var1.setlocal("_text_openflags", var9);
      var3 = null;
      var1.setline(66);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("O_NOINHERIT")).__nonzero__()) {
         var1.setline(67);
         var9 = var1.getname("_text_openflags");
         var9 = var9._ior(var1.getname("_os").__getattr__("O_NOINHERIT"));
         var1.setlocal("_text_openflags", var9);
      }

      var1.setline(68);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("O_NOFOLLOW")).__nonzero__()) {
         var1.setline(69);
         var9 = var1.getname("_text_openflags");
         var9 = var9._ior(var1.getname("_os").__getattr__("O_NOFOLLOW"));
         var1.setlocal("_text_openflags", var9);
      }

      var1.setline(71);
      var9 = var1.getname("_text_openflags");
      var1.setlocal("_bin_openflags", var9);
      var3 = null;
      var1.setline(72);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("O_BINARY")).__nonzero__()) {
         var1.setline(73);
         var9 = var1.getname("_bin_openflags");
         var9 = var9._ior(var1.getname("_os").__getattr__("O_BINARY"));
         var1.setlocal("_bin_openflags", var9);
      }

      var1.setline(75);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("TMP_MAX")).__nonzero__()) {
         var1.setline(76);
         var9 = var1.getname("_os").__getattr__("TMP_MAX");
         var1.setlocal("TMP_MAX", var9);
         var3 = null;
      } else {
         var1.setline(78);
         PyInteger var16 = Py.newInteger(10000);
         var1.setlocal("TMP_MAX", var16);
         var3 = null;
      }

      var1.setline(80);
      PyString var17 = PyString.fromInterned("tmp");
      var1.setlocal("template", var17);
      var3 = null;
      var1.setline(84);
      var9 = var1.getname("_allocate_lock").__call__(var2);
      var1.setlocal("_once_lock", var9);
      var3 = null;
      var1.setline(86);
      PyFunction var18;
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("lstat")).__nonzero__()) {
         var1.setline(87);
         var9 = var1.getname("_os").__getattr__("lstat");
         var1.setlocal("_stat", var9);
         var3 = null;
      } else {
         var1.setline(88);
         if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_os"), (PyObject)PyString.fromInterned("stat")).__nonzero__()) {
            var1.setline(89);
            var9 = var1.getname("_os").__getattr__("stat");
            var1.setlocal("_stat", var9);
            var3 = null;
         } else {
            var1.setline(93);
            var13 = Py.EmptyObjects;
            var18 = new PyFunction(var1.f_globals, var13, _stat$3, (PyObject)null);
            var1.setlocal("_stat", var18);
            var3 = null;
         }
      }

      var1.setline(100);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, _exists$4, (PyObject)null);
      var1.setlocal("_exists", var18);
      var3 = null;
      var1.setline(108);
      var13 = Py.EmptyObjects;
      var4 = Py.makeClass("_RandomNameSequence", var13, _RandomNameSequence$5);
      var1.setlocal("_RandomNameSequence", var4);
      var4 = null;
      Arrays.fill(var13, (Object)null);
      var1.setline(153);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, _candidate_tempdir_list$10, PyString.fromInterned("Generate a list of candidate temporary directories which\n    _get_default_tempdir will try."));
      var1.setlocal("_candidate_tempdir_list", var18);
      var3 = null;
      var1.setline(187);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, _get_default_tempdir$11, PyString.fromInterned("Calculate the default directory to use for temporary files.\n    This routine should be called exactly once.\n\n    We determine whether or not a candidate temp dir is usable by\n    trying to create and write to a file in that directory.  If this\n    is successful, the test file is deleted.  To prevent denial of\n    service, the name of the test file must be randomized."));
      var1.setlocal("_get_default_tempdir", var18);
      var3 = null;
      var1.setline(225);
      var9 = var1.getname("None");
      var1.setlocal("_name_sequence", var9);
      var3 = null;
      var1.setline(227);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, _get_candidate_names$12, PyString.fromInterned("Common setup sequence for all user-callable interfaces."));
      var1.setlocal("_get_candidate_names", var18);
      var3 = null;
      var1.setline(241);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, _mkstemp_inner$13, PyString.fromInterned("Code common to mkstemp, TemporaryFile, and NamedTemporaryFile."));
      var1.setlocal("_mkstemp_inner", var18);
      var3 = null;
      var1.setline(263);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, gettempprefix$14, PyString.fromInterned("Accessor for tempdir.template."));
      var1.setlocal("gettempprefix", var18);
      var3 = null;
      var1.setline(267);
      var9 = var1.getname("None");
      var1.setlocal("tempdir", var9);
      var3 = null;
      var1.setline(269);
      var13 = Py.EmptyObjects;
      var18 = new PyFunction(var1.f_globals, var13, gettempdir$15, PyString.fromInterned("Accessor for tempfile.tempdir."));
      var1.setlocal("gettempdir", var18);
      var3 = null;
      var1.setline(281);
      var13 = new PyObject[]{PyString.fromInterned(""), var1.getname("template"), var1.getname("None"), var1.getname("False")};
      var18 = new PyFunction(var1.f_globals, var13, mkstemp$16, PyString.fromInterned("User-callable function to create and return a unique temporary\n    file.  The return value is a pair (fd, name) where fd is the\n    file descriptor returned by os.open, and name is the filename.\n\n    If 'suffix' is specified, the file name will end with that suffix,\n    otherwise there will be no suffix.\n\n    If 'prefix' is specified, the file name will begin with that prefix,\n    otherwise a default prefix is used.\n\n    If 'dir' is specified, the file will be created in that directory,\n    otherwise a default directory is used.\n\n    If 'text' is specified and true, the file is opened in text\n    mode.  Else (the default) the file is opened in binary mode.  On\n    some operating systems, this makes no difference.\n\n    The file is readable and writable only by the creating user ID.\n    If the operating system uses permission bits to indicate whether a\n    file is executable, the file is executable by no one. The file\n    descriptor is not inherited by children of this process.\n\n    Caller is responsible for deleting the file when done with it.\n    "));
      var1.setlocal("mkstemp", var18);
      var3 = null;
      var1.setline(318);
      var13 = new PyObject[]{PyString.fromInterned(""), var1.getname("template"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var13, mkdtemp$17, PyString.fromInterned("User-callable function to create and return a unique temporary\n    directory.  The return value is the pathname of the directory.\n\n    Arguments are as for mkstemp, except that the 'text' argument is\n    not accepted.\n\n    The directory is readable, writable, and searchable only by the\n    creating user.\n\n    Caller is responsible for deleting the directory when done with it.\n    "));
      var1.setlocal("mkdtemp", var18);
      var3 = null;
      var1.setline(349);
      var13 = new PyObject[]{PyString.fromInterned(""), var1.getname("template"), var1.getname("None")};
      var18 = new PyFunction(var1.f_globals, var13, mktemp$18, PyString.fromInterned("User-callable function to return a unique temporary file name.  The\n    file is not created.\n\n    Arguments are as for mkstemp, except that the 'text' argument is\n    not accepted.\n\n    This function is unsafe and should not be used.  The file name\n    refers to a file that did not exist at some point, but by the time\n    you get around to creating it, someone else may have beaten you to\n    the punch.\n    "));
      var1.setlocal("mktemp", var18);
      var3 = null;
      var1.setline(379);
      var13 = Py.EmptyObjects;
      var4 = Py.makeClass("_TemporaryFileWrapper", var13, _TemporaryFileWrapper$19);
      var1.setlocal("_TemporaryFileWrapper", var4);
      var4 = null;
      Arrays.fill(var13, (Object)null);
      var1.setline(441);
      var13 = new PyObject[]{PyString.fromInterned("w+b"), Py.newInteger(-1), PyString.fromInterned(""), var1.getname("template"), var1.getname("None"), var1.getname("True")};
      var18 = new PyFunction(var1.f_globals, var13, NamedTemporaryFile$27, PyString.fromInterned("Create and return a temporary file.\n    Arguments:\n    'prefix', 'suffix', 'dir' -- as for mkstemp.\n    'mode' -- the mode argument to os.fdopen (default \"w+b\").\n    'bufsize' -- the buffer size argument to os.fdopen (default -1).\n    'delete' -- whether the file is deleted on close (default True).\n    The file is created as mkstemp() would do it.\n\n    Returns an object with a file-like interface; the name of the file\n    is accessible as file.name.  The file will be automatically deleted\n    when it is closed unless the 'delete' argument is set to False.\n    "));
      var1.setlocal("NamedTemporaryFile", var18);
      var3 = null;
      var1.setline(473);
      var9 = var1.getname("_os").__getattr__("name");
      PyObject var10000 = var9._ne(PyString.fromInterned("posix"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var9 = var1.getname("_os").__getattr__("sys").__getattr__("platform");
         var10000 = var9._eq(PyString.fromInterned("cygwin"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(476);
         var9 = var1.getname("NamedTemporaryFile");
         var1.setlocal("TemporaryFile", var9);
         var3 = null;
      } else {
         var1.setline(479);
         var13 = new PyObject[]{PyString.fromInterned("w+b"), Py.newInteger(-1), PyString.fromInterned(""), var1.getname("template"), var1.getname("None")};
         var18 = new PyFunction(var1.f_globals, var13, TemporaryFile$28, PyString.fromInterned("Create and return a temporary file.\n        Arguments:\n        'prefix', 'suffix', 'dir' -- as for mkstemp.\n        'mode' -- the mode argument to os.fdopen (default \"w+b\").\n        'bufsize' -- the buffer size argument to os.fdopen (default -1).\n        The file is created as mkstemp() would do it.\n\n        Returns an object with a file-like interface.  The file has no\n        name, and will cease to exist when it is closed.\n        "));
         var1.setlocal("TemporaryFile", var18);
         var3 = null;
      }

      var1.setline(508);
      var13 = Py.EmptyObjects;
      var4 = Py.makeClass("SpooledTemporaryFile", var13, SpooledTemporaryFile$29);
      var1.setlocal("SpooledTemporaryFile", var4);
      var4 = null;
      Arrays.fill(var13, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_cloexec$1(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_cloexec$2(PyFrame var1, ThreadState var2) {
      label18: {
         PyException var3;
         try {
            var1.setline(50);
            PyObject var6 = var1.getglobal("_fcntl").__getattr__("fcntl").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("_fcntl").__getattr__("F_GETFD"), (PyObject)Py.newInteger(0));
            var1.setlocal(1, var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("IOError"))) {
               var1.setline(52);
               break label18;
            }

            throw var3;
         }

         var1.setline(55);
         PyObject var4 = var1.getlocal(1);
         var4 = var4._ior(var1.getglobal("_fcntl").__getattr__("FD_CLOEXEC"));
         var1.setlocal(1, var4);
         var1.setline(56);
         var1.getglobal("_fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(0), var1.getglobal("_fcntl").__getattr__("F_SETFD"), var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _stat$3(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(95);
         PyObject var5 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(97);
            throw Py.makeException(var1.getglobal("_os").__getattr__("error"));
         }

         throw var3;
      }

      var1.setline(98);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _exists$4(PyFrame var1, ThreadState var2) {
      PyObject var4;
      try {
         var1.setline(102);
         var1.getglobal("_stat").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("_os").__getattr__("error"))) {
            var1.setline(104);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(106);
      var4 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _RandomNameSequence$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An instance of _RandomNameSequence generates an endless\n    sequence of unpredictable strings which can safely be incorporated\n    into file names.  Each string is six characters long.  Multiple\n    threads can safely use the same instance at the same time.\n\n    _RandomNameSequence is an iterator."));
      var1.setline(114);
      PyString.fromInterned("An instance of _RandomNameSequence generates an endless\n    sequence of unpredictable strings which can safely be incorporated\n    into file names.  Each string is six characters long.  Multiple\n    threads can safely use the same instance at the same time.\n\n    _RandomNameSequence is an iterator.");
      var1.setline(116);
      PyObject var3 = PyString.fromInterned("abcdefghijklmnopqrstuvwxyz")._add(PyString.fromInterned("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))._add(PyString.fromInterned("0123456789_"));
      var1.setlocal("characters", var3);
      var3 = null;
      var1.setline(120);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(124);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, rng$7, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("rng", var3);
      var3 = null;
      var1.setline(137);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$8, (PyObject)null);
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(140);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$9, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getglobal("_allocate_lock").__call__(var2);
      var1.getlocal(0).__setattr__("mutex", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getglobal("_os").__getattr__("path").__getattr__("normcase");
      var1.getlocal(0).__setattr__("normcase", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject rng$7(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyInteger var3;
      PyObject var4;
      if (var1.getglobal("_os").__getattr__("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(128);
         var3 = Py.newInteger(1);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(130);
         var4 = var1.getglobal("_os").__getattr__("getpid").__call__(var2);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(132);
      var4 = var1.getlocal(1);
      PyObject var10000 = var4._ne(var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("_rng_pid"), (PyObject)var1.getglobal("None")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(133);
         var4 = var1.getglobal("_Random").__call__(var2);
         var1.getlocal(0).__setattr__("_rng", var4);
         var3 = null;
         var1.setline(134);
         var4 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_rng_pid", var4);
         var3 = null;
      }

      var1.setline(135);
      var4 = var1.getlocal(0).__getattr__("_rng");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __iter__$8(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$9(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(0).__getattr__("mutex");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(142);
      var3 = var1.getlocal(0).__getattr__("characters");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getlocal(0).__getattr__("rng").__getattr__("choice");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(145);
      var1.getlocal(1).__getattr__("acquire").__call__(var2);
      var3 = null;

      try {
         var1.setline(147);
         PyList var10000 = new PyList();
         PyObject var4 = var10000.__getattr__("append");
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(147);
         var4 = PyString.fromInterned("123456").__iter__();

         while(true) {
            var1.setline(147);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(147);
               var1.dellocal(5);
               PyList var7 = var10000;
               var1.setlocal(4, var7);
               var4 = null;
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(147);
            var1.getlocal(5).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(2)));
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(149);
         var1.getlocal(1).__getattr__("release").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(149);
      var1.getlocal(1).__getattr__("release").__call__(var2);
      var1.setline(151);
      var3 = var1.getlocal(0).__getattr__("normcase").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _candidate_tempdir_list$10(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyString.fromInterned("Generate a list of candidate temporary directories which\n    _get_default_tempdir will try.");
      var1.setline(157);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(160);
      PyObject var7 = (new PyTuple(new PyObject[]{PyString.fromInterned("TMPDIR"), PyString.fromInterned("TEMP"), PyString.fromInterned("TMP")})).__iter__();

      while(true) {
         var1.setline(160);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(165);
            var7 = var1.getglobal("_os").__getattr__("name");
            PyObject var10000 = var7._ne(PyString.fromInterned("java"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(166);
               var7 = var1.getglobal("_os").__getattr__("name");
               var1.setlocal(3, var7);
               var3 = null;
            } else {
               var1.setline(168);
               var7 = var1.getglobal("_os").__getattr__("_name");
               var1.setlocal(3, var7);
               var3 = null;
            }

            var1.setline(171);
            var7 = var1.getlocal(3);
            var10000 = var7._eq(PyString.fromInterned("riscos"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(172);
               var7 = var1.getglobal("_os").__getattr__("getenv").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wimp$ScrapDir"));
               var1.setlocal(2, var7);
               var3 = null;
               var1.setline(173);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(173);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
               }
            } else {
               var1.setline(174);
               var7 = var1.getlocal(3);
               var10000 = var7._eq(PyString.fromInterned("nt"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(175);
                  var1.getlocal(0).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("c:\\temp"), PyString.fromInterned("c:\\tmp"), PyString.fromInterned("\\temp"), PyString.fromInterned("\\tmp")})));
               } else {
                  var1.setline(177);
                  var1.getlocal(0).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("/tmp"), PyString.fromInterned("/var/tmp"), PyString.fromInterned("/usr/tmp")})));
               }
            }

            try {
               var1.setline(181);
               var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("_os").__getattr__("getcwd").__call__(var2));
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("_os").__getattr__("error")}))) {
                  throw var8;
               }

               var1.setline(183);
               var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("_os").__getattr__("curdir"));
            }

            var1.setline(185);
            var7 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(1, var4);
         var1.setline(161);
         PyObject var5 = var1.getglobal("_os").__getattr__("getenv").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(162);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(162);
            var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject _get_default_tempdir$11(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(194);
      PyString.fromInterned("Calculate the default directory to use for temporary files.\n    This routine should be called exactly once.\n\n    We determine whether or not a candidate temp dir is usable by\n    trying to create and write to a file in that directory.  If this\n    is successful, the test file is deleted.  To prevent denial of\n    service, the name of the test file must be randomized.");
      var1.setline(196);
      PyObject var3 = var1.getglobal("_RandomNameSequence").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(197);
      var3 = var1.getglobal("_candidate_tempdir_list").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getglobal("_text_openflags");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(200);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(222);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{var1.getglobal("_errno").__getattr__("ENOENT"), PyString.fromInterned("No usable temporary directory found in %s")._mod(var1.getlocal(1))}));
         }

         var1.setlocal(3, var4);
         var1.setline(201);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._ne(var1.getglobal("_os").__getattr__("curdir"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var5 = var1.getglobal("_os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var5);
            var5 = null;
         }

         var1.setline(204);
         var5 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(100)).__iter__();

         while(true) {
            var1.setline(204);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(205);
            PyObject var7 = var1.getlocal(0).__getattr__("next").__call__(var2);
            var1.setlocal(5, var7);
            var7 = null;
            var1.setline(206);
            var7 = var1.getglobal("_os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            var1.setlocal(6, var7);
            var7 = null;

            PyException var8;
            PyObject var9;
            try {
               var1.setline(208);
               var7 = var1.getglobal("_os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(384));
               var1.setlocal(7, var7);
               var7 = null;
               var7 = null;

               try {
                  var8 = null;

                  try {
                     label73: {
                        var10000 = var1.getglobal("_io").__getattr__("open");
                        PyObject[] var15 = new PyObject[]{var1.getlocal(7), PyString.fromInterned("wb"), var1.getglobal("False")};
                        String[] var10 = new String[]{"closefd"};
                        var10000 = var10000.__call__(var2, var15, var10);
                        var9 = null;
                        ContextManager var17;
                        PyObject var16 = (var17 = ContextGuard.getManager(var10000)).__enter__(var2);

                        try {
                           var1.setlocal(8, var16);
                           var1.setline(212);
                           var1.getlocal(8).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("blat"));
                        } catch (Throwable var11) {
                           if (var17.__exit__(var2, Py.setException(var11, var1))) {
                              break label73;
                           }

                           throw (Throwable)Py.makeException();
                        }

                        var17.__exit__(var2, (PyException)null);
                     }
                  } catch (Throwable var12) {
                     Py.addTraceback(var12, var1);
                     var1.setline(214);
                     var1.getglobal("_os").__getattr__("close").__call__(var2, var1.getlocal(7));
                     throw (Throwable)var12;
                  }

                  var1.setline(214);
                  var1.getglobal("_os").__getattr__("close").__call__(var2, var1.getlocal(7));
               } catch (Throwable var13) {
                  Py.addTraceback(var13, var1);
                  var1.setline(216);
                  var1.getglobal("_os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
                  throw (Throwable)var13;
               }

               var1.setline(216);
               var1.getglobal("_os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
               var1.setline(217);
               var7 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var7;
            } catch (Throwable var14) {
               var8 = Py.setException(var14, var1);
               if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("OSError"), var1.getglobal("IOError")}))) {
                  throw var8;
               }

               var9 = var8.value;
               var1.setlocal(9, var9);
               var9 = null;
               var1.setline(219);
               var9 = var1.getlocal(9).__getattr__("args").__getitem__(Py.newInteger(0));
               var10000 = var9._ne(var1.getglobal("_errno").__getattr__("EEXIST"));
               var9 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(221);
            }
         }
      }
   }

   public PyObject _get_candidate_names$12(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("Common setup sequence for all user-callable interfaces.");
      var1.setline(231);
      PyObject var3 = var1.getglobal("_name_sequence");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(232);
         var1.getglobal("_once_lock").__getattr__("acquire").__call__(var2);
         var3 = null;

         try {
            var1.setline(234);
            PyObject var4 = var1.getglobal("_name_sequence");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(235);
               var4 = var1.getglobal("_RandomNameSequence").__call__(var2);
               var1.setglobal("_name_sequence", var4);
               var4 = null;
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(237);
            var1.getglobal("_once_lock").__getattr__("release").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(237);
         var1.getglobal("_once_lock").__getattr__("release").__call__(var2);
      }

      var1.setline(238);
      var3 = var1.getglobal("_name_sequence");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _mkstemp_inner$13(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyString.fromInterned("Code common to mkstemp, TemporaryFile, and NamedTemporaryFile.");
      var1.setline(244);
      PyObject var3 = var1.getglobal("_get_candidate_names").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(246);
      var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("TMP_MAX")).__iter__();

      while(true) {
         var1.setline(246);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(258);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{var1.getglobal("_errno").__getattr__("EEXIST"), PyString.fromInterned("No usable temporary file name found")}));
         }

         var1.setlocal(5, var4);
         var1.setline(247);
         PyObject var5 = var1.getlocal(4).__getattr__("next").__call__(var2);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(248);
         var5 = var1.getglobal("_os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1)._add(var1.getlocal(6))._add(var1.getlocal(2)));
         var1.setlocal(7, var5);
         var5 = null;

         try {
            var1.setline(250);
            var5 = var1.getglobal("_os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(384));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(251);
            var1.getglobal("_set_cloexec").__call__(var2, var1.getlocal(8));
            var1.setline(252);
            PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getglobal("_os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(7))});
            var1.f_lasti = -1;
            return var9;
         } catch (Throwable var8) {
            PyException var6 = Py.setException(var8, var1);
            if (!var6.match(var1.getglobal("OSError"))) {
               throw var6;
            }

            PyObject var7 = var6.value;
            var1.setlocal(9, var7);
            var7 = null;
            var1.setline(254);
            var7 = var1.getlocal(9).__getattr__("errno");
            PyObject var10000 = var7._eq(var1.getglobal("_errno").__getattr__("EEXIST"));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(256);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject gettempprefix$14(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyString.fromInterned("Accessor for tempdir.template.");
      var1.setline(265);
      PyObject var3 = var1.getglobal("template");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gettempdir$15(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyString.fromInterned("Accessor for tempfile.tempdir.");
      var1.setline(272);
      PyObject var3 = var1.getglobal("tempdir");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(273);
         var1.getglobal("_once_lock").__getattr__("acquire").__call__(var2);
         var3 = null;

         try {
            var1.setline(275);
            PyObject var4 = var1.getglobal("tempdir");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(276);
               var4 = var1.getglobal("_get_default_tempdir").__call__(var2);
               var1.setglobal("tempdir", var4);
               var4 = null;
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(278);
            var1.getglobal("_once_lock").__getattr__("release").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(278);
         var1.getglobal("_once_lock").__getattr__("release").__call__(var2);
      }

      var1.setline(279);
      var3 = var1.getglobal("tempdir");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mkstemp$16(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyString.fromInterned("User-callable function to create and return a unique temporary\n    file.  The return value is a pair (fd, name) where fd is the\n    file descriptor returned by os.open, and name is the filename.\n\n    If 'suffix' is specified, the file name will end with that suffix,\n    otherwise there will be no suffix.\n\n    If 'prefix' is specified, the file name will begin with that prefix,\n    otherwise a default prefix is used.\n\n    If 'dir' is specified, the file will be created in that directory,\n    otherwise a default directory is used.\n\n    If 'text' is specified and true, the file is opened in text\n    mode.  Else (the default) the file is opened in binary mode.  On\n    some operating systems, this makes no difference.\n\n    The file is readable and writable only by the creating user ID.\n    If the operating system uses permission bits to indicate whether a\n    file is executable, the file is executable by no one. The file\n    descriptor is not inherited by children of this process.\n\n    Caller is responsible for deleting the file when done with it.\n    ");
      var1.setline(307);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(308);
         var3 = var1.getglobal("gettempdir").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(310);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(311);
         var3 = var1.getglobal("_text_openflags");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(313);
         var3 = var1.getglobal("_bin_openflags");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(315);
      var3 = var1.getglobal("_mkstemp_inner").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(0), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mkdtemp$17(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyString.fromInterned("User-callable function to create and return a unique temporary\n    directory.  The return value is the pathname of the directory.\n\n    Arguments are as for mkstemp, except that the 'text' argument is\n    not accepted.\n\n    The directory is readable, writable, and searchable only by the\n    creating user.\n\n    Caller is responsible for deleting the directory when done with it.\n    ");
      var1.setline(331);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(332);
         var3 = var1.getglobal("gettempdir").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(334);
      var3 = var1.getglobal("_get_candidate_names").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("TMP_MAX")).__iter__();

      while(true) {
         var1.setline(336);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(347);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{var1.getglobal("_errno").__getattr__("EEXIST"), PyString.fromInterned("No usable temporary directory name found")}));
         }

         var1.setlocal(4, var4);
         var1.setline(337);
         PyObject var5 = var1.getlocal(3).__getattr__("next").__call__(var2);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(338);
         var5 = var1.getglobal("_os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(var1.getlocal(5))._add(var1.getlocal(0)));
         var1.setlocal(6, var5);
         var5 = null;

         try {
            var1.setline(340);
            var1.getglobal("_os").__getattr__("mkdir").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(448));
            var1.setline(341);
            var5 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var5;
         } catch (Throwable var8) {
            PyException var6 = Py.setException(var8, var1);
            if (!var6.match(var1.getglobal("OSError"))) {
               throw var6;
            }

            PyObject var7 = var6.value;
            var1.setlocal(7, var7);
            var7 = null;
            var1.setline(343);
            var7 = var1.getlocal(7).__getattr__("errno");
            var10000 = var7._eq(var1.getglobal("_errno").__getattr__("EEXIST"));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(345);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject mktemp$18(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyString.fromInterned("User-callable function to return a unique temporary file name.  The\n    file is not created.\n\n    Arguments are as for mkstemp, except that the 'text' argument is\n    not accepted.\n\n    This function is unsafe and should not be used.  The file name\n    refers to a file that did not exist at some point, but by the time\n    you get around to creating it, someone else may have beaten you to\n    the punch.\n    ");
      var1.setline(366);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(367);
         var3 = var1.getglobal("gettempdir").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(369);
      var3 = var1.getglobal("_get_candidate_names").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("TMP_MAX")).__iter__();

      PyObject var5;
      do {
         var1.setline(370);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(376);
            throw Py.makeException(var1.getglobal("IOError"), new PyTuple(new PyObject[]{var1.getglobal("_errno").__getattr__("EEXIST"), PyString.fromInterned("No usable temporary filename found")}));
         }

         var1.setlocal(4, var4);
         var1.setline(371);
         var5 = var1.getlocal(3).__getattr__("next").__call__(var2);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(372);
         var5 = var1.getglobal("_os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(1)._add(var1.getlocal(5))._add(var1.getlocal(0)));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(373);
      } while(!var1.getglobal("_exists").__call__(var2, var1.getlocal(6)).__not__().__nonzero__());

      var1.setline(374);
      var5 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _TemporaryFileWrapper$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Temporary file wrapper\n\n    This class provides a wrapper around files opened for\n    temporary use.  In particular, it seeks to automatically\n    remove the file when it is no longer needed.\n    "));
      var1.setline(385);
      PyString.fromInterned("Temporary file wrapper\n\n    This class provides a wrapper around files opened for\n    temporary use.  In particular, it seeks to automatically\n    remove the file when it is no longer needed.\n    ");
      var1.setline(387);
      PyObject[] var3 = new PyObject[]{var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(393);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$21, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(405);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$22, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(412);
      PyObject var5 = var1.getname("_os").__getattr__("name");
      PyObject var10000 = var5._ne(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(418);
         var5 = var1.getname("_os").__getattr__("unlink");
         var1.setlocal("unlink", var5);
         var3 = null;
         var1.setline(420);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, close$23, (PyObject)null);
         var1.setlocal("close", var4);
         var3 = null;
         var1.setline(427);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, __del__$24, (PyObject)null);
         var1.setlocal("__del__", var4);
         var3 = null;
         var1.setline(432);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, __exit__$25, (PyObject)null);
         var1.setlocal("__exit__", var4);
         var3 = null;
      } else {
         var1.setline(437);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, __exit__$26, (PyObject)null);
         var1.setlocal("__exit__", var4);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(389);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("close_called", var3);
      var3 = null;
      var1.setline(391);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("delete", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$21(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__").__getitem__(PyString.fromInterned("file"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(399);
      if (var1.getglobal("issubclass").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3)), var1.getglobal("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))).__not__().__nonzero__()) {
         var1.setline(400);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(3));
      }

      var1.setline(401);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __enter__$22(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      var1.getlocal(0).__getattr__("file").__getattr__("__enter__").__call__(var2);
      var1.setline(407);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$23(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      if (var1.getlocal(0).__getattr__("close_called").__not__().__nonzero__()) {
         var1.setline(422);
         PyObject var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("close_called", var3);
         var3 = null;
         var1.setline(423);
         var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
         var1.setline(424);
         if (var1.getlocal(0).__getattr__("delete").__nonzero__()) {
            var1.setline(425);
            var1.getlocal(0).__getattr__("unlink").__call__(var2, var1.getlocal(0).__getattr__("name"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$24(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __exit__$25(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("__exit__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(434);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.setline(435);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$26(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      var1.getlocal(0).__getattr__("file").__getattr__("__exit__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NamedTemporaryFile$27(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyString.fromInterned("Create and return a temporary file.\n    Arguments:\n    'prefix', 'suffix', 'dir' -- as for mkstemp.\n    'mode' -- the mode argument to os.fdopen (default \"w+b\").\n    'bufsize' -- the buffer size argument to os.fdopen (default -1).\n    'delete' -- whether the file is deleted on close (default True).\n    The file is created as mkstemp() would do it.\n\n    Returns an object with a file-like interface; the name of the file\n    is accessible as file.name.  The file will be automatically deleted\n    when it is closed unless the 'delete' argument is set to False.\n    ");
      var1.setline(456);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(457);
         var3 = var1.getglobal("gettempdir").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(459);
      PyString var6 = PyString.fromInterned("b");
      var10000 = var6._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(460);
         var3 = var1.getglobal("_bin_openflags");
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(462);
         var3 = var1.getglobal("_text_openflags");
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(466);
      var3 = var1.getglobal("_os").__getattr__("name");
      var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5);
      }

      if (var10000.__nonzero__()) {
         var1.setline(467);
         var3 = var1.getlocal(6);
         var3 = var3._ior(var1.getglobal("_os").__getattr__("O_TEMPORARY"));
         var1.setlocal(6, var3);
      }

      var1.setline(469);
      var3 = var1.getglobal("_mkstemp_inner").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(6));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(470);
      var3 = var1.getglobal("_os").__getattr__("fdopen").__call__(var2, var1.getlocal(7), var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(471);
      var3 = var1.getglobal("_TemporaryFileWrapper").__call__(var2, var1.getlocal(9), var1.getlocal(8), var1.getlocal(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TemporaryFile$28(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyString.fromInterned("Create and return a temporary file.\n        Arguments:\n        'prefix', 'suffix', 'dir' -- as for mkstemp.\n        'mode' -- the mode argument to os.fdopen (default \"w+b\").\n        'bufsize' -- the buffer size argument to os.fdopen (default -1).\n        The file is created as mkstemp() would do it.\n\n        Returns an object with a file-like interface.  The file has no\n        name, and will cease to exist when it is closed.\n        ");
      var1.setline(492);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(493);
         var3 = var1.getglobal("gettempdir").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(495);
      PyString var7 = PyString.fromInterned("b");
      var10000 = var7._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(496);
         var3 = var1.getglobal("_bin_openflags");
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(498);
         var3 = var1.getglobal("_text_openflags");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(500);
      var3 = var1.getglobal("_mkstemp_inner").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(2), var1.getlocal(5));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;

      try {
         var1.setline(502);
         var1.getglobal("_os").__getattr__("unlink").__call__(var2, var1.getlocal(7));
         var1.setline(503);
         var3 = var1.getglobal("_os").__getattr__("fdopen").__call__(var2, var1.getlocal(6), var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(505);
         var1.getglobal("_os").__getattr__("close").__call__(var2, var1.getlocal(6));
         var1.setline(506);
         throw Py.makeException();
      }
   }

   public PyObject SpooledTemporaryFile$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Temporary file wrapper, specialized to switch from\n    StringIO to a real file when it exceeds a certain size or\n    when a fileno is needed.\n    "));
      var1.setline(512);
      PyString.fromInterned("Temporary file wrapper, specialized to switch from\n    StringIO to a real file when it exceeds a certain size or\n    when a fileno is needed.\n    ");
      var1.setline(513);
      PyObject var3 = var1.getname("False");
      var1.setlocal("_rolled", var3);
      var3 = null;
      var1.setline(515);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("w+b"), Py.newInteger(-1), PyString.fromInterned(""), var1.getname("template"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(522);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _check$31, (PyObject)null);
      var1.setlocal("_check", var5);
      var3 = null;
      var1.setline(528);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, rollover$32, (PyObject)null);
      var1.setlocal("rollover", var5);
      var3 = null;
      var1.setline(545);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$33, (PyObject)null);
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(550);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __exit__$34, (PyObject)null);
      var1.setlocal("__exit__", var5);
      var3 = null;
      var1.setline(554);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$35, (PyObject)null);
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(557);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$36, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(560);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, closed$37, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("closed", var3);
      var3 = null;
      var1.setline(564);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, encoding$38, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("encoding", var3);
      var3 = null;
      var1.setline(568);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fileno$39, (PyObject)null);
      var1.setlocal("fileno", var5);
      var3 = null;
      var1.setline(572);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$40, (PyObject)null);
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(575);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isatty$41, (PyObject)null);
      var1.setlocal("isatty", var5);
      var3 = null;
      var1.setline(578);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, mode$42, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(585);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, name$43, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("name", var3);
      var3 = null;
      var1.setline(592);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, newlines$44, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("newlines", var3);
      var3 = null;
      var1.setline(596);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$45, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(599);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read$46, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(602);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readline$47, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(605);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readlines$48, (PyObject)null);
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(608);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seek$49, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(611);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, softspace$50, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("softspace", var3);
      var3 = null;
      var1.setline(615);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$51, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(618);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, truncate$52, (PyObject)null);
      var1.setlocal("truncate", var5);
      var3 = null;
      var1.setline(621);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$53, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(627);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writelines$54, (PyObject)null);
      var1.setlocal("writelines", var5);
      var3 = null;
      var1.setline(633);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, xreadlines$55, (PyObject)null);
      var1.setlocal("xreadlines", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(517);
      PyObject var3 = var1.getglobal("_StringIO").__call__(var2);
      var1.getlocal(0).__setattr__("_file", var3);
      var3 = null;
      var1.setline(518);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_max_size", var3);
      var3 = null;
      var1.setline(519);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_rolled", var3);
      var3 = null;
      var1.setline(520);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)});
      var1.getlocal(0).__setattr__((String)"_TemporaryFileArgs", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check$31(PyFrame var1, ThreadState var2) {
      var1.setline(523);
      if (var1.getlocal(0).__getattr__("_rolled").__nonzero__()) {
         var1.setline(523);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(524);
         PyObject var3 = var1.getlocal(0).__getattr__("_max_size");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(525);
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("tell").__call__(var2);
            var10000 = var3._gt(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(526);
            var1.getlocal(0).__getattr__("rollover").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject rollover$32(PyFrame var1, ThreadState var2) {
      var1.setline(529);
      if (var1.getlocal(0).__getattr__("_rolled").__nonzero__()) {
         var1.setline(529);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(530);
         PyObject var3 = var1.getlocal(0).__getattr__("_file");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(531);
         PyObject var10000 = var1.getglobal("TemporaryFile");
         PyObject[] var5 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(0).__getattr__("_TemporaryFileArgs"), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var1.getlocal(0).__setattr__("_file", var3);
         var1.setline(532);
         var1.getlocal(0).__delattr__("_TemporaryFileArgs");
         var1.setline(534);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1).__getattr__("getvalue").__call__(var2));
         var1.setline(535);
         var1.getlocal(2).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("tell").__call__(var2), (PyObject)Py.newInteger(0));
         var1.setline(537);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_rolled", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __enter__$33(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      if (var1.getlocal(0).__getattr__("_file").__getattr__("closed").__nonzero__()) {
         var1.setline(547);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot enter context with closed file")));
      } else {
         var1.setline(548);
         PyObject var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __exit__$34(PyFrame var1, ThreadState var2) {
      var1.setline(551);
      var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$35(PyFrame var1, ThreadState var2) {
      var1.setline(555);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("__iter__").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$36(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject closed$37(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encoding$38(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("encoding");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fileno$39(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      var1.getlocal(0).__getattr__("rollover").__call__(var2);
      var1.setline(570);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("fileno").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$40(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      var1.getlocal(0).__getattr__("_file").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isatty$41(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("isatty").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mode$42(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(581);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("mode");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(583);
            var3 = var1.getlocal(0).__getattr__("_TemporaryFileArgs").__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject name$43(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(588);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("name");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(590);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject newlines$44(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("newlines");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$45(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("next");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$46(PyFrame var1, ThreadState var2) {
      var1.setline(600);
      PyObject var10000 = var1.getlocal(0).__getattr__("_file").__getattr__("read");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject readline$47(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyObject var10000 = var1.getlocal(0).__getattr__("_file").__getattr__("readline");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject readlines$48(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyObject var10000 = var1.getlocal(0).__getattr__("_file").__getattr__("readlines");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject seek$49(PyFrame var1, ThreadState var2) {
      var1.setline(609);
      PyObject var10000 = var1.getlocal(0).__getattr__("_file").__getattr__("seek");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject softspace$50(PyFrame var1, ThreadState var2) {
      var1.setline(613);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("softspace");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tell$51(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject truncate$52(PyFrame var1, ThreadState var2) {
      var1.setline(619);
      var1.getlocal(0).__getattr__("_file").__getattr__("truncate").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$53(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyObject var3 = var1.getlocal(0).__getattr__("_file");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(623);
      var3 = var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(624);
      var1.getlocal(0).__getattr__("_check").__call__(var2, var1.getlocal(2));
      var1.setline(625);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writelines$54(PyFrame var1, ThreadState var2) {
      var1.setline(628);
      PyObject var3 = var1.getlocal(0).__getattr__("_file");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(629);
      var3 = var1.getlocal(2).__getattr__("writelines").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(630);
      var1.getlocal(0).__getattr__("_check").__call__(var2, var1.getlocal(2));
      var1.setline(631);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject xreadlines$55(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      PyObject var3;
      try {
         var1.setline(635);
         var10000 = var1.getlocal(0).__getattr__("_file").__getattr__("xreadlines");
         PyObject[] var8 = Py.EmptyObjects;
         String[] var9 = new String[0];
         var10000 = var10000._callextra(var8, var9, var1.getlocal(1), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var7) {
         PyException var4 = Py.setException(var7, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(637);
            var10000 = var1.getglobal("iter");
            PyObject var10002 = var1.getlocal(0).__getattr__("_file").__getattr__("readlines");
            PyObject[] var5 = Py.EmptyObjects;
            String[] var6 = new String[0];
            var10002 = var10002._callextra(var5, var6, var1.getlocal(1), (PyObject)null);
            var5 = null;
            var3 = var10000.__call__(var2, var10002);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public tempfile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fd"};
      _set_cloexec$1 = Py.newCode(1, var2, var1, "_set_cloexec", 45, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fd", "flags"};
      _set_cloexec$2 = Py.newCode(1, var2, var1, "_set_cloexec", 48, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn", "f"};
      _stat$3 = Py.newCode(1, var2, var1, "_stat", 93, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn"};
      _exists$4 = Py.newCode(1, var2, var1, "_exists", 100, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _RandomNameSequence$5 = Py.newCode(0, var2, var1, "_RandomNameSequence", 108, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$6 = Py.newCode(1, var2, var1, "__init__", 120, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cur_pid"};
      rng$7 = Py.newCode(1, var2, var1, "rng", 124, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$8 = Py.newCode(1, var2, var1, "__iter__", 137, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "c", "choose", "letters", "_[147_23]", "dummy"};
      next$9 = Py.newCode(1, var2, var1, "next", 140, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dirlist", "envname", "dirname", "os_name"};
      _candidate_tempdir_list$10 = Py.newCode(0, var2, var1, "_candidate_tempdir_list", 153, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"namer", "dirlist", "flags", "dir", "seq", "name", "filename", "fd", "fp", "e"};
      _get_default_tempdir$11 = Py.newCode(0, var2, var1, "_get_default_tempdir", 187, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _get_candidate_names$12 = Py.newCode(0, var2, var1, "_get_candidate_names", 227, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dir", "pre", "suf", "flags", "names", "seq", "name", "file", "fd", "e"};
      _mkstemp_inner$13 = Py.newCode(4, var2, var1, "_mkstemp_inner", 241, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      gettempprefix$14 = Py.newCode(0, var2, var1, "gettempprefix", 263, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      gettempdir$15 = Py.newCode(0, var2, var1, "gettempdir", 269, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suffix", "prefix", "dir", "text", "flags"};
      mkstemp$16 = Py.newCode(4, var2, var1, "mkstemp", 281, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suffix", "prefix", "dir", "names", "seq", "name", "file", "e"};
      mkdtemp$17 = Py.newCode(3, var2, var1, "mkdtemp", 318, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suffix", "prefix", "dir", "names", "seq", "name", "file"};
      mktemp$18 = Py.newCode(3, var2, var1, "mktemp", 349, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _TemporaryFileWrapper$19 = Py.newCode(0, var2, var1, "_TemporaryFileWrapper", 379, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "name", "delete"};
      __init__$20 = Py.newCode(4, var2, var1, "__init__", 387, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "file", "a"};
      __getattr__$21 = Py.newCode(2, var2, var1, "__getattr__", 393, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$22 = Py.newCode(1, var2, var1, "__enter__", 405, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$23 = Py.newCode(1, var2, var1, "close", 420, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$24 = Py.newCode(1, var2, var1, "__del__", 427, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc", "value", "tb", "result"};
      __exit__$25 = Py.newCode(4, var2, var1, "__exit__", 432, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc", "value", "tb"};
      __exit__$26 = Py.newCode(4, var2, var1, "__exit__", 437, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "bufsize", "suffix", "prefix", "dir", "delete", "flags", "fd", "name", "file"};
      NamedTemporaryFile$27 = Py.newCode(6, var2, var1, "NamedTemporaryFile", 441, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "bufsize", "suffix", "prefix", "dir", "flags", "fd", "name"};
      TemporaryFile$28 = Py.newCode(5, var2, var1, "TemporaryFile", 479, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SpooledTemporaryFile$29 = Py.newCode(0, var2, var1, "SpooledTemporaryFile", 508, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "max_size", "mode", "bufsize", "suffix", "prefix", "dir"};
      __init__$30 = Py.newCode(7, var2, var1, "__init__", 515, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "max_size"};
      _check$31 = Py.newCode(2, var2, var1, "_check", 522, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "newfile"};
      rollover$32 = Py.newCode(1, var2, var1, "rollover", 528, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$33 = Py.newCode(1, var2, var1, "__enter__", 545, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc", "value", "tb"};
      __exit__$34 = Py.newCode(4, var2, var1, "__exit__", 550, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$35 = Py.newCode(1, var2, var1, "__iter__", 554, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$36 = Py.newCode(1, var2, var1, "close", 557, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      closed$37 = Py.newCode(1, var2, var1, "closed", 560, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      encoding$38 = Py.newCode(1, var2, var1, "encoding", 564, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$39 = Py.newCode(1, var2, var1, "fileno", 568, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$40 = Py.newCode(1, var2, var1, "flush", 572, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isatty$41 = Py.newCode(1, var2, var1, "isatty", 575, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      mode$42 = Py.newCode(1, var2, var1, "mode", 578, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      name$43 = Py.newCode(1, var2, var1, "name", 585, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      newlines$44 = Py.newCode(1, var2, var1, "newlines", 592, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      next$45 = Py.newCode(1, var2, var1, "next", 596, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      read$46 = Py.newCode(2, var2, var1, "read", 599, true, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      readline$47 = Py.newCode(2, var2, var1, "readline", 602, true, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      readlines$48 = Py.newCode(2, var2, var1, "readlines", 605, true, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      seek$49 = Py.newCode(2, var2, var1, "seek", 608, true, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      softspace$50 = Py.newCode(1, var2, var1, "softspace", 611, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$51 = Py.newCode(1, var2, var1, "tell", 615, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      truncate$52 = Py.newCode(1, var2, var1, "truncate", 618, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "file", "rv"};
      write$53 = Py.newCode(2, var2, var1, "write", 621, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "file", "rv"};
      writelines$54 = Py.newCode(2, var2, var1, "writelines", 627, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      xreadlines$55 = Py.newCode(2, var2, var1, "xreadlines", 633, true, false, self, 55, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tempfile$py("tempfile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tempfile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._set_cloexec$1(var2, var3);
         case 2:
            return this._set_cloexec$2(var2, var3);
         case 3:
            return this._stat$3(var2, var3);
         case 4:
            return this._exists$4(var2, var3);
         case 5:
            return this._RandomNameSequence$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.rng$7(var2, var3);
         case 8:
            return this.__iter__$8(var2, var3);
         case 9:
            return this.next$9(var2, var3);
         case 10:
            return this._candidate_tempdir_list$10(var2, var3);
         case 11:
            return this._get_default_tempdir$11(var2, var3);
         case 12:
            return this._get_candidate_names$12(var2, var3);
         case 13:
            return this._mkstemp_inner$13(var2, var3);
         case 14:
            return this.gettempprefix$14(var2, var3);
         case 15:
            return this.gettempdir$15(var2, var3);
         case 16:
            return this.mkstemp$16(var2, var3);
         case 17:
            return this.mkdtemp$17(var2, var3);
         case 18:
            return this.mktemp$18(var2, var3);
         case 19:
            return this._TemporaryFileWrapper$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__getattr__$21(var2, var3);
         case 22:
            return this.__enter__$22(var2, var3);
         case 23:
            return this.close$23(var2, var3);
         case 24:
            return this.__del__$24(var2, var3);
         case 25:
            return this.__exit__$25(var2, var3);
         case 26:
            return this.__exit__$26(var2, var3);
         case 27:
            return this.NamedTemporaryFile$27(var2, var3);
         case 28:
            return this.TemporaryFile$28(var2, var3);
         case 29:
            return this.SpooledTemporaryFile$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this._check$31(var2, var3);
         case 32:
            return this.rollover$32(var2, var3);
         case 33:
            return this.__enter__$33(var2, var3);
         case 34:
            return this.__exit__$34(var2, var3);
         case 35:
            return this.__iter__$35(var2, var3);
         case 36:
            return this.close$36(var2, var3);
         case 37:
            return this.closed$37(var2, var3);
         case 38:
            return this.encoding$38(var2, var3);
         case 39:
            return this.fileno$39(var2, var3);
         case 40:
            return this.flush$40(var2, var3);
         case 41:
            return this.isatty$41(var2, var3);
         case 42:
            return this.mode$42(var2, var3);
         case 43:
            return this.name$43(var2, var3);
         case 44:
            return this.newlines$44(var2, var3);
         case 45:
            return this.next$45(var2, var3);
         case 46:
            return this.read$46(var2, var3);
         case 47:
            return this.readline$47(var2, var3);
         case 48:
            return this.readlines$48(var2, var3);
         case 49:
            return this.seek$49(var2, var3);
         case 50:
            return this.softspace$50(var2, var3);
         case 51:
            return this.tell$51(var2, var3);
         case 52:
            return this.truncate$52(var2, var3);
         case 53:
            return this.write$53(var2, var3);
         case 54:
            return this.writelines$54(var2, var3);
         case 55:
            return this.xreadlines$55(var2, var3);
         default:
            return null;
      }
   }
}
