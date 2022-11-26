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
@MTime(1498849384000L)
@Filename("os.py")
public class os$py extends PyFunctionTable implements PyRunnable {
   static os$py self;
   static final PyCode f$0;
   static final PyCode _get_exports_list$1;
   static final PyCode makedirs$2;
   static final PyCode removedirs$3;
   static final PyCode renames$4;
   static final PyCode walk$5;
   static final PyCode _exists$6;
   static final PyCode execl$7;
   static final PyCode execle$8;
   static final PyCode execlp$9;
   static final PyCode execlpe$10;
   static final PyCode execvp$11;
   static final PyCode execvpe$12;
   static final PyCode _execvpe$13;
   static final PyCode unsetenv$14;
   static final PyCode _Environ$15;
   static final PyCode __init__$16;
   static final PyCode __setitem__$17;
   static final PyCode __getitem__$18;
   static final PyCode __delitem__$19;
   static final PyCode has_key$20;
   static final PyCode __contains__$21;
   static final PyCode get$22;
   static final PyCode update$23;
   static final PyCode copy$24;
   static final PyCode getenv$25;
   static final PyCode _spawnvef$26;
   static final PyCode spawnv$27;
   static final PyCode spawnve$28;
   static final PyCode spawnvp$29;
   static final PyCode spawnvpe$30;
   static final PyCode spawnl$31;
   static final PyCode spawnle$32;
   static final PyCode spawnlp$33;
   static final PyCode spawnlpe$34;
   static final PyCode popen2$35;
   static final PyCode popen3$36;
   static final PyCode popen4$37;
   static final PyCode urandom$38;
   static final PyCode popen$39;
   static final PyCode _wrap_close$40;
   static final PyCode __init__$41;
   static final PyCode close$42;
   static final PyCode __getattr__$43;
   static final PyCode __iter__$44;
   static final PyCode system$45;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("OS routines for Mac, NT, or Posix depending on what system we're on.\n\nThis exports:\n  - all functions from posix, nt, os2, or ce, e.g. unlink, stat, etc.\n  - os.path is one of the modules posixpath, or ntpath\n  - os.name is 'posix', 'nt', 'os2', 'ce' or 'riscos'\n  - os.curdir is a string representing the current directory ('.' or ':')\n  - os.pardir is a string representing the parent directory ('..' or '::')\n  - os.sep is the (or a most common) pathname separator ('/' or ':' or '\\\\')\n  - os.extsep is the extension separator ('.' or '/')\n  - os.altsep is the alternate pathname separator (None or '/')\n  - os.pathsep is the component separator used in $PATH etc\n  - os.linesep is the line separator in text files ('\\r' or '\\n' or '\\r\\n')\n  - os.defpath is the default search path for executables\n  - os.devnull is the file path of the null device ('/dev/null', etc.)\n\nPrograms that import and use 'os' stand a better chance of being\nportable between different platforms.  Of course, they must then\nonly use functions that are defined by all platforms (e.g., unlink\nand opendir), and leave all pathname manipulation to os.path\n(e.g., split and join).\n"));
      var1.setline(22);
      PyString.fromInterned("OS routines for Mac, NT, or Posix depending on what system we're on.\n\nThis exports:\n  - all functions from posix, nt, os2, or ce, e.g. unlink, stat, etc.\n  - os.path is one of the modules posixpath, or ntpath\n  - os.name is 'posix', 'nt', 'os2', 'ce' or 'riscos'\n  - os.curdir is a string representing the current directory ('.' or ':')\n  - os.pardir is a string representing the parent directory ('..' or '::')\n  - os.sep is the (or a most common) pathname separator ('/' or ':' or '\\\\')\n  - os.extsep is the extension separator ('.' or '/')\n  - os.altsep is the alternate pathname separator (None or '/')\n  - os.pathsep is the component separator used in $PATH etc\n  - os.linesep is the line separator in text files ('\\r' or '\\n' or '\\r\\n')\n  - os.defpath is the default search path for executables\n  - os.devnull is the file path of the null device ('/dev/null', etc.)\n\nPrograms that import and use 'os' stand a better chance of being\nportable between different platforms.  Of course, they must then\nonly use functions that are defined by all platforms (e.g., unlink\nand opendir), and leave all pathname manipulation to os.path\n(e.g., split and join).\n");
      var1.setline(26);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(27);
      String[] var14 = new String[]{"PyShadowString"};
      PyObject[] var15 = imp.importFrom("org.python.core", var14, var1, -1);
      PyObject var4 = var15[0];
      var1.setlocal("PyShadowString", var4);
      var4 = null;
      var1.setline(29);
      var3 = var1.getname("sys").__getattr__("builtin_module_names");
      var1.setlocal("_names", var3);
      var3 = null;
      var1.setline(32);
      PyList var16 = new PyList(new PyObject[]{PyString.fromInterned("altsep"), PyString.fromInterned("curdir"), PyString.fromInterned("pardir"), PyString.fromInterned("sep"), PyString.fromInterned("extsep"), PyString.fromInterned("pathsep"), PyString.fromInterned("linesep"), PyString.fromInterned("defpath"), PyString.fromInterned("name"), PyString.fromInterned("path"), PyString.fromInterned("devnull"), PyString.fromInterned("SEEK_SET"), PyString.fromInterned("SEEK_CUR"), PyString.fromInterned("SEEK_END")});
      var1.setlocal("__all__", var16);
      var3 = null;
      var1.setline(36);
      var15 = Py.EmptyObjects;
      PyFunction var17 = new PyFunction(var1.f_globals, var15, _get_exports_list$1, (PyObject)null);
      var1.setlocal("_get_exports_list", var17);
      var3 = null;
      var1.setline(42);
      PyString var18 = PyString.fromInterned("posix");
      PyObject var10000 = var18._in(var1.getname("_names"));
      var3 = null;
      PyException var19;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         var18 = PyString.fromInterned("posix");
         var1.setlocal("_name", var18);
         var3 = null;
         var1.setline(44);
         var18 = PyString.fromInterned("\n");
         var1.setlocal("linesep", var18);
         var3 = null;
         var1.setline(45);
         imp.importAll("posix", var1, -1);

         try {
            var1.setline(47);
            var14 = new String[]{"_exit"};
            var15 = imp.importFrom("posix", var14, var1, -1);
            var4 = var15[0];
            var1.setlocal("_exit", var4);
            var4 = null;
         } catch (Throwable var10) {
            var19 = Py.setException(var10, var1);
            if (!var19.match(var1.getname("ImportError"))) {
               throw var19;
            }

            var1.setline(49);
         }

         var1.setline(50);
         var3 = imp.importOneAs("posixpath", var1, -1);
         var1.setlocal("path", var3);
         var3 = null;
         var1.setline(52);
         var3 = imp.importOne("posix", var1, -1);
         var1.setlocal("posix", var3);
         var3 = null;
         var1.setline(53);
         var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("posix")));
         var1.setline(54);
         var1.dellocal("posix");
      } else {
         var1.setline(56);
         var18 = PyString.fromInterned("nt");
         var10000 = var18._in(var1.getname("_names"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(57);
            var18 = PyString.fromInterned("nt");
            var1.setlocal("_name", var18);
            var3 = null;
            var1.setline(58);
            var18 = PyString.fromInterned("\r\n");
            var1.setlocal("linesep", var18);
            var3 = null;
            var1.setline(59);
            imp.importAll("nt", var1, -1);

            try {
               var1.setline(61);
               var14 = new String[]{"_exit"};
               var15 = imp.importFrom("nt", var14, var1, -1);
               var4 = var15[0];
               var1.setlocal("_exit", var4);
               var4 = null;
            } catch (Throwable var11) {
               var19 = Py.setException(var11, var1);
               if (!var19.match(var1.getname("ImportError"))) {
                  throw var19;
               }

               var1.setline(63);
            }

            var1.setline(64);
            var3 = imp.importOneAs("ntpath", var1, -1);
            var1.setlocal("path", var3);
            var3 = null;
            var1.setline(66);
            var3 = imp.importOne("nt", var1, -1);
            var1.setlocal("nt", var3);
            var3 = null;
            var1.setline(67);
            var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("nt")));
            var1.setline(68);
            var1.dellocal("nt");
         } else {
            var1.setline(70);
            var18 = PyString.fromInterned("os2");
            var10000 = var18._in(var1.getname("_names"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(71);
               var18 = PyString.fromInterned("os2");
               var1.setlocal("_name", var18);
               var3 = null;
               var1.setline(72);
               var18 = PyString.fromInterned("\r\n");
               var1.setlocal("linesep", var18);
               var3 = null;
               var1.setline(73);
               imp.importAll("os2", var1, -1);

               try {
                  var1.setline(75);
                  var14 = new String[]{"_exit"};
                  var15 = imp.importFrom("os2", var14, var1, -1);
                  var4 = var15[0];
                  var1.setlocal("_exit", var4);
                  var4 = null;
               } catch (Throwable var12) {
                  var19 = Py.setException(var12, var1);
                  if (!var19.match(var1.getname("ImportError"))) {
                     throw var19;
                  }

                  var1.setline(77);
               }

               var1.setline(78);
               var3 = var1.getname("sys").__getattr__("version").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EMX GCC"));
               var10000 = var3._eq(Py.newInteger(-1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(79);
                  var3 = imp.importOneAs("ntpath", var1, -1);
                  var1.setlocal("path", var3);
                  var3 = null;
               } else {
                  var1.setline(81);
                  var3 = imp.importOneAs("os2emxpath", var1, -1);
                  var1.setlocal("path", var3);
                  var3 = null;
                  var1.setline(82);
                  var14 = new String[]{"link"};
                  var15 = imp.importFrom("_emx_link", var14, var1, -1);
                  var4 = var15[0];
                  var1.setlocal("link", var4);
                  var4 = null;
               }

               var1.setline(84);
               var3 = imp.importOne("os2", var1, -1);
               var1.setlocal("os2", var3);
               var3 = null;
               var1.setline(85);
               var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("os2")));
               var1.setline(86);
               var1.dellocal("os2");
            } else {
               var1.setline(88);
               var18 = PyString.fromInterned("ce");
               var10000 = var18._in(var1.getname("_names"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(89);
                  var18 = PyString.fromInterned("ce");
                  var1.setlocal("_name", var18);
                  var3 = null;
                  var1.setline(90);
                  var18 = PyString.fromInterned("\r\n");
                  var1.setlocal("linesep", var18);
                  var3 = null;
                  var1.setline(91);
                  imp.importAll("ce", var1, -1);

                  try {
                     var1.setline(93);
                     var14 = new String[]{"_exit"};
                     var15 = imp.importFrom("ce", var14, var1, -1);
                     var4 = var15[0];
                     var1.setlocal("_exit", var4);
                     var4 = null;
                  } catch (Throwable var8) {
                     var19 = Py.setException(var8, var1);
                     if (!var19.match(var1.getname("ImportError"))) {
                        throw var19;
                     }

                     var1.setline(95);
                  }

                  var1.setline(97);
                  var3 = imp.importOneAs("ntpath", var1, -1);
                  var1.setlocal("path", var3);
                  var3 = null;
                  var1.setline(99);
                  var3 = imp.importOne("ce", var1, -1);
                  var1.setlocal("ce", var3);
                  var3 = null;
                  var1.setline(100);
                  var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("ce")));
                  var1.setline(101);
                  var1.dellocal("ce");
               } else {
                  var1.setline(103);
                  var18 = PyString.fromInterned("riscos");
                  var10000 = var18._in(var1.getname("_names"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(104);
                     var18 = PyString.fromInterned("riscos");
                     var1.setlocal("_name", var18);
                     var3 = null;
                     var1.setline(105);
                     var18 = PyString.fromInterned("\n");
                     var1.setlocal("linesep", var18);
                     var3 = null;
                     var1.setline(106);
                     imp.importAll("riscos", var1, -1);

                     try {
                        var1.setline(108);
                        var14 = new String[]{"_exit"};
                        var15 = imp.importFrom("riscos", var14, var1, -1);
                        var4 = var15[0];
                        var1.setlocal("_exit", var4);
                        var4 = null;
                     } catch (Throwable var13) {
                        var19 = Py.setException(var13, var1);
                        if (!var19.match(var1.getname("ImportError"))) {
                           throw var19;
                        }

                        var1.setline(110);
                     }

                     var1.setline(111);
                     var3 = imp.importOneAs("riscospath", var1, -1);
                     var1.setlocal("path", var3);
                     var3 = null;
                     var1.setline(113);
                     var3 = imp.importOne("riscos", var1, -1);
                     var1.setlocal("riscos", var3);
                     var3 = null;
                     var1.setline(114);
                     var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("riscos")));
                     var1.setline(115);
                     var1.dellocal("riscos");
                  } else {
                     var1.setline(117);
                     var18 = PyString.fromInterned("ibmi");
                     var10000 = var18._in(var1.getname("_names"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(132);
                        throw Py.makeException(var1.getname("ImportError"), PyString.fromInterned("no os specific module found"));
                     }

                     var1.setline(118);
                     var18 = PyString.fromInterned("ibmi");
                     var1.setlocal("_name", var18);
                     var3 = null;
                     var1.setline(119);
                     var18 = PyString.fromInterned("\n");
                     var1.setlocal("linesep", var18);
                     var3 = null;
                     var1.setline(120);
                     imp.importAll("ibmi", var1, -1);

                     try {
                        var1.setline(122);
                        var14 = new String[]{"_exit"};
                        var15 = imp.importFrom("ibmi", var14, var1, -1);
                        var4 = var15[0];
                        var1.setlocal("_exit", var4);
                        var4 = null;
                     } catch (Throwable var9) {
                        var19 = Py.setException(var9, var1);
                        if (!var19.match(var1.getname("ImportError"))) {
                           throw var19;
                        }

                        var1.setline(124);
                     }

                     var1.setline(125);
                     var3 = imp.importOneAs("posixpath", var1, -1);
                     var1.setlocal("path", var3);
                     var3 = null;
                     var1.setline(127);
                     var3 = imp.importOne("ibmi", var1, -1);
                     var1.setlocal("ibmi", var3);
                     var3 = null;
                     var1.setline(128);
                     var1.getname("__all__").__getattr__("extend").__call__(var2, var1.getname("_get_exports_list").__call__(var2, var1.getname("ibmi")));
                     var1.setline(129);
                     var1.dellocal("ibmi");
                  }
               }
            }
         }
      }

      var1.setline(134);
      var3 = var1.getname("PyShadowString").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"), (PyObject)var1.getname("_name"));
      var1.setlocal("name", var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getname("path");
      var1.getname("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("os.path"), var3);
      var3 = null;
      var1.setline(137);
      var14 = new String[]{"curdir", "pardir", "sep", "pathsep", "defpath", "extsep", "altsep", "devnull"};
      var15 = imp.importFrom("os.path", var14, var1, -1);
      var4 = var15[0];
      var1.setlocal("curdir", var4);
      var4 = null;
      var4 = var15[1];
      var1.setlocal("pardir", var4);
      var4 = null;
      var4 = var15[2];
      var1.setlocal("sep", var4);
      var4 = null;
      var4 = var15[3];
      var1.setlocal("pathsep", var4);
      var4 = null;
      var4 = var15[4];
      var1.setlocal("defpath", var4);
      var4 = null;
      var4 = var15[5];
      var1.setlocal("extsep", var4);
      var4 = null;
      var4 = var15[6];
      var1.setlocal("altsep", var4);
      var4 = null;
      var4 = var15[7];
      var1.setlocal("devnull", var4);
      var4 = null;
      var1.setline(140);
      var1.dellocal("_names");
      var1.setline(144);
      PyInteger var24 = Py.newInteger(0);
      var1.setlocal("SEEK_SET", var24);
      var3 = null;
      var1.setline(145);
      var24 = Py.newInteger(1);
      var1.setlocal("SEEK_CUR", var24);
      var3 = null;
      var1.setline(146);
      var24 = Py.newInteger(2);
      var1.setlocal("SEEK_END", var24);
      var3 = null;
      var1.setline(153);
      var15 = new PyObject[]{Py.newInteger(511)};
      var17 = new PyFunction(var1.f_globals, var15, makedirs$2, PyString.fromInterned("makedirs(path [, mode=0777])\n\n    Super-mkdir; create a leaf directory and all intermediate ones.\n    Works like mkdir, except that any intermediate path segment (not\n    just the rightmost) will be created if it does not exist.  This is\n    recursive.\n\n    "));
      var1.setlocal("makedirs", var17);
      var3 = null;
      var1.setline(176);
      var15 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var15, removedirs$3, PyString.fromInterned("removedirs(path)\n\n    Super-rmdir; remove a leaf directory and all empty intermediate\n    ones.  Works like rmdir except that, if the leaf directory is\n    successfully removed, directories corresponding to rightmost path\n    segments will be pruned away until either the whole path is\n    consumed or an error occurs.  Errors during this latter phase are\n    ignored -- they generally mean that a directory was not empty.\n\n    "));
      var1.setlocal("removedirs", var17);
      var3 = null;
      var1.setline(198);
      var15 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var15, renames$4, PyString.fromInterned("renames(old, new)\n\n    Super-rename; create directories as necessary and delete any left\n    empty.  Works like rename, except creation of any intermediate\n    directories needed to make the new pathname good is attempted\n    first.  After the rename, directories corresponding to rightmost\n    path segments of the old name will be pruned way until either the\n    whole path is consumed or a nonempty directory is found.\n\n    Note: this function can fail with the new directory structure made\n    if you lack permissions needed to unlink the leaf directory or\n    file.\n\n    "));
      var1.setlocal("renames", var17);
      var3 = null;
      var1.setline(224);
      var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("makedirs"), PyString.fromInterned("removedirs"), PyString.fromInterned("renames"), PyString.fromInterned("system")})));
      var1.setline(226);
      var15 = new PyObject[]{var1.getname("True"), var1.getname("None"), var1.getname("False")};
      var17 = new PyFunction(var1.f_globals, var15, walk$5, PyString.fromInterned("Directory tree generator.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), yields a 3-tuple\n\n        dirpath, dirnames, filenames\n\n    dirpath is a string, the path to the directory.  dirnames is a list of\n    the names of the subdirectories in dirpath (excluding '.' and '..').\n    filenames is a list of the names of the non-directory files in dirpath.\n    Note that the names in the lists are just names, with no path components.\n    To get a full path (which begins with top) to a file or directory in\n    dirpath, do os.path.join(dirpath, name).\n\n    If optional arg 'topdown' is true or not specified, the triple for a\n    directory is generated before the triples for any of its subdirectories\n    (directories are generated top down).  If topdown is false, the triple\n    for a directory is generated after the triples for all of its\n    subdirectories (directories are generated bottom up).\n\n    When topdown is true, the caller can modify the dirnames list in-place\n    (e.g., via del or slice assignment), and walk will only recurse into the\n    subdirectories whose names remain in dirnames; this can be used to prune\n    the search, or to impose a specific order of visiting.  Modifying\n    dirnames when topdown is false is ineffective, since the directories in\n    dirnames have already been generated by the time dirnames itself is\n    generated.\n\n    By default errors from the os.listdir() call are ignored.  If\n    optional arg 'onerror' is specified, it should be a function; it\n    will be called with one argument, an os.error instance.  It can\n    report the error to continue with the walk, or raise the exception\n    to abort the walk.  Note that the filename is available as the\n    filename attribute of the exception object.\n\n    By default, os.walk does not follow symbolic links to subdirectories on\n    systems that support them.  In order to get this functionality, set the\n    optional argument 'followlinks' to true.\n\n    Caution:  if you pass a relative pathname for top, don't change the\n    current working directory between resumptions of walk.  walk never\n    changes the current directory, and assumes that the client doesn't\n    either.\n\n    Example:\n\n    import os\n    from os.path import join, getsize\n    for root, dirs, files in os.walk('python/Lib/email'):\n        print root, \"consumes\",\n        print sum([getsize(join(root, name)) for name in files]),\n        print \"bytes in\", len(files), \"non-directory files\"\n        if 'CVS' in dirs:\n            dirs.remove('CVS')  # don't visit CVS directories\n    "));
      var1.setlocal("walk", var17);
      var3 = null;
      var1.setline(316);
      var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("walk"));

      try {
         var1.setline(320);
         var1.getname("environ");
      } catch (Throwable var7) {
         var19 = Py.setException(var7, var1);
         if (!var19.match(var1.getname("NameError"))) {
            throw var19;
         }

         var1.setline(322);
         PyDictionary var20 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal("environ", var20);
         var4 = null;
      }

      var1.setline(324);
      var15 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var15, _exists$6, (PyObject)null);
      var1.setlocal("_exists", var17);
      var3 = null;
      var1.setline(329);
      if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("execv")).__nonzero__()) {
         var1.setline(331);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execl$7, PyString.fromInterned("execl(file, *args)\n\n        Execute the executable file with argument list args, replacing the\n        current process. "));
         var1.setlocal("execl", var17);
         var3 = null;
         var1.setline(338);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execle$8, PyString.fromInterned("execle(file, *args, env)\n\n        Execute the executable file with argument list args and\n        environment env, replacing the current process. "));
         var1.setlocal("execle", var17);
         var3 = null;
         var1.setline(346);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execlp$9, PyString.fromInterned("execlp(file, *args)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args, replacing the current process. "));
         var1.setlocal("execlp", var17);
         var3 = null;
         var1.setline(353);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execlpe$10, PyString.fromInterned("execlpe(file, *args, env)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args and environment env, replacing the current\n        process. "));
         var1.setlocal("execlpe", var17);
         var3 = null;
         var1.setline(362);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execvp$11, PyString.fromInterned("execp(file, args)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args, replacing the current process.\n        args may be a list or tuple of strings. "));
         var1.setlocal("execvp", var17);
         var3 = null;
         var1.setline(370);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, execvpe$12, PyString.fromInterned("execvpe(file, args, env)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args and environment env , replacing the\n        current process.\n        args may be a list or tuple of strings. "));
         var1.setlocal("execvpe", var17);
         var3 = null;
         var1.setline(379);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("execl"), PyString.fromInterned("execle"), PyString.fromInterned("execlp"), PyString.fromInterned("execlpe"), PyString.fromInterned("execvp"), PyString.fromInterned("execvpe")})));
         var1.setline(381);
         var15 = new PyObject[]{var1.getname("None")};
         var17 = new PyFunction(var1.f_globals, var15, _execvpe$13, (PyObject)null);
         var1.setlocal("_execvpe", var17);
         var3 = null;
      }

      label212: {
         try {
            var1.setline(418);
            var1.getname("putenv");
         } catch (Throwable var6) {
            var19 = Py.setException(var6, var1);
            if (var19.match(var1.getname("NameError"))) {
               var1.setline(420);
               break label212;
            }

            throw var19;
         }

         var1.setline(426);
         var4 = var1.getname("name");
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("os2"), PyString.fromInterned("nt")}));
         var4 = null;
         PyObject[] var21;
         if (var10000.__nonzero__()) {
            var1.setline(427);
            var21 = Py.EmptyObjects;
            PyFunction var22 = new PyFunction(var1.f_globals, var21, unsetenv$14, (PyObject)null);
            var1.setlocal("unsetenv", var22);
            var4 = null;
         }

         var1.setline(430);
         var4 = var1.getname("_name");
         var10000 = var4._eq(PyString.fromInterned("riscos"));
         var4 = null;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(432);
            String[] var23 = new String[]{"_Environ"};
            var21 = imp.importFrom("riscosenviron", var23, var1, -1);
            var5 = var21[0];
            var1.setlocal("_Environ", var5);
            var5 = null;
         } else {
            var1.setline(433);
            var4 = var1.getname("_name");
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("os2"), PyString.fromInterned("nt")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(434);
               var4 = imp.importOne("UserDict", var1, -1);
               var1.setlocal("UserDict", var4);
               var4 = null;
               var1.setline(437);
               var21 = new PyObject[]{var1.getname("UserDict").__getattr__("IterableUserDict")};
               var5 = Py.makeClass("_Environ", var21, _Environ$15);
               var1.setlocal("_Environ", var5);
               var5 = null;
               Arrays.fill(var21, (Object)null);
               var1.setline(474);
               var4 = var1.getname("_Environ").__call__(var2, var1.getname("environ"));
               var1.setlocal("environ", var4);
               var4 = null;
            }
         }
      }

      var1.setline(476);
      var15 = new PyObject[]{var1.getname("None")};
      var17 = new PyFunction(var1.f_globals, var15, getenv$25, PyString.fromInterned("Get an environment variable, return None if it doesn't exist.\n    The optional second argument can specify an alternate default."));
      var1.setlocal("getenv", var17);
      var3 = null;
      var1.setline(480);
      var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getenv"));
      var1.setline(483);
      var10000 = var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fork"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("spawnv")).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("execv"));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(485);
         var24 = Py.newInteger(0);
         var1.setlocal("P_WAIT", var24);
         var3 = null;
         var1.setline(486);
         var24 = Py.newInteger(1);
         var1.setlocal("P_NOWAIT", var24);
         var1.setlocal("P_NOWAITO", var24);
         var1.setline(492);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, _spawnvef$26, (PyObject)null);
         var1.setlocal("_spawnvef", var17);
         var3 = null;
         var1.setline(519);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnv$27, PyString.fromInterned("spawnv(mode, file, args) -> integer\n\nExecute file with arguments from args in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnv", var17);
         var3 = null;
         var1.setline(528);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnve$28, PyString.fromInterned("spawnve(mode, file, args, env) -> integer\n\nExecute file with arguments from args in a subprocess with the\nspecified environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnve", var17);
         var3 = null;
         var1.setline(540);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnvp$29, PyString.fromInterned("spawnvp(mode, file, args) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnvp", var17);
         var3 = null;
         var1.setline(550);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnvpe$30, PyString.fromInterned("spawnvpe(mode, file, args, env) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnvpe", var17);
         var3 = null;
      }

      var1.setline(560);
      if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("spawnv")).__nonzero__()) {
         var1.setline(564);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnl$31, PyString.fromInterned("spawnl(mode, file, *args) -> integer\n\nExecute file with arguments from args in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnl", var17);
         var3 = null;
         var1.setline(573);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnle$32, PyString.fromInterned("spawnle(mode, file, *args, env) -> integer\n\nExecute file with arguments from args in a subprocess with the\nsupplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnle", var17);
         var3 = null;
         var1.setline(585);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("spawnv"), PyString.fromInterned("spawnve"), PyString.fromInterned("spawnl"), PyString.fromInterned("spawnle")})));
      }

      var1.setline(588);
      if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("spawnvp")).__nonzero__()) {
         var1.setline(591);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnlp$33, PyString.fromInterned("spawnlp(mode, file, *args) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnlp", var17);
         var3 = null;
         var1.setline(601);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, spawnlpe$34, PyString.fromInterned("spawnlpe(mode, file, *args, env) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. "));
         var1.setlocal("spawnlpe", var17);
         var3 = null;
         var1.setline(613);
         var1.getname("__all__").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("spawnvp"), PyString.fromInterned("spawnvpe"), PyString.fromInterned("spawnlp"), PyString.fromInterned("spawnlpe")})));
      }

      var1.setline(617);
      var10000 = var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fork"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(618);
         if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen2")).__not__().__nonzero__()) {
            var1.setline(619);
            var15 = new PyObject[]{PyString.fromInterned("t"), Py.newInteger(-1)};
            var17 = new PyFunction(var1.f_globals, var15, popen2$35, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout) are returned."));
            var1.setlocal("popen2", var17);
            var3 = null;
            var1.setline(632);
            var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen2"));
         }

         var1.setline(634);
         if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen3")).__not__().__nonzero__()) {
            var1.setline(635);
            var15 = new PyObject[]{PyString.fromInterned("t"), Py.newInteger(-1)};
            var17 = new PyFunction(var1.f_globals, var15, popen3$36, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout, child_stderr) are returned."));
            var1.setlocal("popen3", var17);
            var3 = null;
            var1.setline(648);
            var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen3"));
         }

         var1.setline(650);
         if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen4")).__not__().__nonzero__()) {
            var1.setline(651);
            var15 = new PyObject[]{PyString.fromInterned("t"), Py.newInteger(-1)};
            var17 = new PyFunction(var1.f_globals, var15, popen4$37, PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout_stderr) are returned."));
            var1.setlocal("popen4", var17);
            var3 = null;
            var1.setline(664);
            var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("popen4"));
         }
      }

      var1.setline(666);
      if (var1.getname("_exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("urandom")).__not__().__nonzero__()) {
         var1.setline(667);
         var15 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var15, urandom$38, PyString.fromInterned("urandom(n) -> str\n\n        Return a string of n random bytes suitable for cryptographic use.\n\n        "));
         var1.setlocal("urandom", var17);
         var3 = null;
      }

      var1.setline(684);
      var15 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      var17 = new PyFunction(var1.f_globals, var15, popen$39, PyString.fromInterned("popen(command [, mode='r' [, bufsize]]) -> pipe\n\n    Open a pipe to/from a command returning a file object.\n    "));
      var1.setlocal("popen", var17);
      var3 = null;
      var1.setline(707);
      var15 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_wrap_close", var15, _wrap_close$40);
      var1.setlocal("_wrap_close", var4);
      var4 = null;
      Arrays.fill(var15, (Object)null);
      var1.setline(726);
      var15 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var15, system$45, PyString.fromInterned("system(command) -> exit_status\n\n    Execute the command (a string) in a subshell."));
      var1.setlocal("system", var17);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_exports_list$1(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(38);
         PyObject var9 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("__all__"));
         var1.f_lasti = -1;
         return var9;
      } catch (Throwable var8) {
         PyException var4 = Py.setException(var8, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(40);
            PyList var10000 = new PyList();
            PyObject var5 = var10000.__getattr__("append");
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(40);
            var5 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

            while(true) {
               var1.setline(40);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(40);
                  var1.dellocal(1);
                  PyList var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(2, var6);
               var1.setline(40);
               PyObject var7 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               PyObject var10001 = var7._ne(PyString.fromInterned("_"));
               var7 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(40);
                  var1.getlocal(1).__call__(var2, var1.getlocal(2));
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject makedirs$2(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("makedirs(path [, mode=0777])\n\n    Super-mkdir; create a leaf directory and all intermediate ones.\n    Works like mkdir, except that any intermediate path segment (not\n    just the rightmost) will be created if it does not exist.  This is\n    recursive.\n\n    ");
      var1.setline(162);
      PyObject var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(163);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(164);
         var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(2));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(165);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__not__();
         }
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(167);
            var1.getglobal("makedirs").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("OSError"))) {
               throw var7;
            }

            PyObject var8 = var7.value;
            var1.setlocal(4, var8);
            var4 = null;
            var1.setline(170);
            var8 = var1.getlocal(4).__getattr__("errno");
            var10000 = var8._ne(var1.getglobal("errno").__getattr__("EEXIST"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(171);
               throw Py.makeException();
            }
         }

         var1.setline(172);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getglobal("curdir"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(173);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(174);
      var1.getglobal("mkdir").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removedirs$3(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("removedirs(path)\n\n    Super-rmdir; remove a leaf directory and all empty intermediate\n    ones.  Works like rmdir except that, if the leaf directory is\n    successfully removed, directories corresponding to rightmost path\n    segments will be pruned away until either the whole path is\n    consumed or an error occurs.  Errors during this latter phase are\n    ignored -- they generally mean that a directory was not empty.\n\n    ");
      var1.setline(187);
      var1.getglobal("rmdir").__call__(var2, var1.getlocal(0));
      var1.setline(188);
      PyObject var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(189);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(190);
         var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      while(true) {
         var1.setline(191);
         PyObject var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         if (!var10000.__nonzero__()) {
            break;
         }

         try {
            var1.setline(193);
            var1.getglobal("rmdir").__call__(var2, var1.getlocal(1));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("error"))) {
               break;
            }

            throw var7;
         }

         var1.setline(196);
         var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject renames$4(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("renames(old, new)\n\n    Super-rename; create directories as necessary and delete any left\n    empty.  Works like rename, except creation of any intermediate\n    directories needed to make the new pathname good is attempted\n    first.  After the rename, directories corresponding to rightmost\n    path segments of the old name will be pruned way until either the\n    whole path is consumed or a nonempty directory is found.\n\n    Note: this function can fail with the new directory structure made\n    if you lack permissions needed to unlink the leaf directory or\n    file.\n\n    ");
      var1.setline(213);
      PyObject var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(214);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__not__();
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(215);
         var1.getglobal("makedirs").__call__(var2, var1.getlocal(2));
      }

      var1.setline(216);
      var1.getglobal("rename").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(217);
      var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(218);
      var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(220);
            var1.getglobal("removedirs").__call__(var2, var1.getlocal(2));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("error"))) {
               throw var7;
            }

            var1.setline(222);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject walk$5(PyFrame var1, ThreadState var2) {
      label103: {
         Object[] var3;
         PyObject var4;
         PyObject var5;
         PyObject var6;
         Object[] var7;
         PyObject var9;
         PyObject[] var11;
         PyObject var15;
         PyTuple var17;
         Object var10000;
         label91:
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(281);
               PyString.fromInterned("Directory tree generator.\n\n    For each directory in the directory tree rooted at top (including top\n    itself, but excluding '.' and '..'), yields a 3-tuple\n\n        dirpath, dirnames, filenames\n\n    dirpath is a string, the path to the directory.  dirnames is a list of\n    the names of the subdirectories in dirpath (excluding '.' and '..').\n    filenames is a list of the names of the non-directory files in dirpath.\n    Note that the names in the lists are just names, with no path components.\n    To get a full path (which begins with top) to a file or directory in\n    dirpath, do os.path.join(dirpath, name).\n\n    If optional arg 'topdown' is true or not specified, the triple for a\n    directory is generated before the triples for any of its subdirectories\n    (directories are generated top down).  If topdown is false, the triple\n    for a directory is generated after the triples for all of its\n    subdirectories (directories are generated bottom up).\n\n    When topdown is true, the caller can modify the dirnames list in-place\n    (e.g., via del or slice assignment), and walk will only recurse into the\n    subdirectories whose names remain in dirnames; this can be used to prune\n    the search, or to impose a specific order of visiting.  Modifying\n    dirnames when topdown is false is ineffective, since the directories in\n    dirnames have already been generated by the time dirnames itself is\n    generated.\n\n    By default errors from the os.listdir() call are ignored.  If\n    optional arg 'onerror' is specified, it should be a function; it\n    will be called with one argument, an os.error instance.  It can\n    report the error to continue with the walk, or raise the exception\n    to abort the walk.  Note that the filename is available as the\n    filename attribute of the exception object.\n\n    By default, os.walk does not follow symbolic links to subdirectories on\n    systems that support them.  In order to get this functionality, set the\n    optional argument 'followlinks' to true.\n\n    Caution:  if you pass a relative pathname for top, don't change the\n    current working directory between resumptions of walk.  walk never\n    changes the current directory, and assumes that the client doesn't\n    either.\n\n    Example:\n\n    import os\n    from os.path import join, getsize\n    for root, dirs, files in os.walk('python/Lib/email'):\n        print root, \"consumes\",\n        print sum([getsize(join(root, name)) for name in files]),\n        print \"bytes in\", len(files), \"non-directory files\"\n        if 'CVS' in dirs:\n            dirs.remove('CVS')  # don't visit CVS directories\n    ");
               var1.setline(283);
               String[] var10 = new String[]{"join", "isdir", "islink"};
               var11 = imp.importFrom("os.path", var10, var1, -1);
               var4 = var11[0];
               var1.setlocal(4, var4);
               var4 = null;
               var4 = var11[1];
               var1.setlocal(5, var4);
               var4 = null;
               var4 = var11[2];
               var1.setlocal(6, var4);
               var4 = null;

               try {
                  var1.setline(293);
                  var9 = var1.getglobal("listdir").__call__(var2, var1.getlocal(0));
                  var1.setlocal(7, var9);
                  var3 = null;
               } catch (Throwable var8) {
                  PyException var12 = Py.setException(var8, var1);
                  if (var12.match(var1.getglobal("error"))) {
                     var4 = var12.value;
                     var1.setlocal(8, var4);
                     var4 = null;
                     var1.setline(295);
                     var4 = var1.getlocal(2);
                     var15 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     if (var15.__nonzero__()) {
                        var1.setline(296);
                        var1.getlocal(2).__call__(var2, var1.getlocal(8));
                     }

                     var1.setline(297);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  throw var12;
               }

               var1.setline(299);
               var11 = new PyObject[2];
               PyObject[] var14 = Py.EmptyObjects;
               PyList var16 = new PyList(var14);
               Arrays.fill(var14, (Object)null);
               var11[0] = var16;
               var14 = Py.EmptyObjects;
               var16 = new PyList(var14);
               Arrays.fill(var14, (Object)null);
               var11[1] = var16;
               var17 = new PyTuple(var11);
               Arrays.fill(var11, (Object)null);
               PyTuple var13 = var17;
               var14 = Py.unpackSequence(var13, 2);
               var5 = var14[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var14[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(300);
               var9 = var1.getlocal(7).__iter__();

               while(true) {
                  var1.setline(300);
                  var4 = var9.__iternext__();
                  if (var4 == null) {
                     var1.setline(306);
                     if (var1.getlocal(1).__nonzero__()) {
                        var1.setline(307);
                        var1.setline(307);
                        var11 = new PyObject[]{var1.getlocal(0), var1.getlocal(9), var1.getlocal(10)};
                        var17 = new PyTuple(var11);
                        Arrays.fill(var11, (Object)null);
                        var1.f_lasti = 1;
                        var3 = new Object[6];
                        var1.f_savedlocals = var3;
                        return var17;
                     }

                     var1.setline(308);
                     var9 = var1.getlocal(9).__iter__();
                     break label91;
                  }

                  var1.setlocal(11, var4);
                  var1.setline(301);
                  if (var1.getlocal(5).__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(11))).__nonzero__()) {
                     var1.setline(302);
                     var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(11));
                  } else {
                     var1.setline(304);
                     var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(11));
                  }
               }
            case 1:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var15 = (PyObject)var10000;
               var1.setline(308);
               var9 = var1.getlocal(9).__iter__();
               break;
            case 2:
               var7 = var1.f_savedlocals;
               var9 = (PyObject)var7[3];
               var4 = (PyObject)var7[4];
               var5 = (PyObject)var7[5];
               var6 = (PyObject)var7[6];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var15 = (PyObject)var10000;
               var1.setline(311);
               var6 = var5.__iternext__();
               if (var6 != null) {
                  var1.setlocal(13, var6);
                  var1.setline(312);
                  var1.setline(312);
                  var15 = var1.getlocal(13);
                  var1.f_lasti = 2;
                  var7 = new Object[]{null, null, null, var9, var4, var5, var6};
                  var1.f_savedlocals = var7;
                  return var15;
               }
               break;
            case 3:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var15 = (PyObject)var10000;
               break label103;
         }

         do {
            do {
               var1.setline(308);
               var4 = var9.__iternext__();
               if (var4 == null) {
                  var1.setline(313);
                  if (var1.getlocal(1).__not__().__nonzero__()) {
                     var1.setline(314);
                     var1.setline(314);
                     var11 = new PyObject[]{var1.getlocal(0), var1.getlocal(9), var1.getlocal(10)};
                     var17 = new PyTuple(var11);
                     Arrays.fill(var11, (Object)null);
                     var1.f_lasti = 3;
                     var3 = new Object[8];
                     var1.f_savedlocals = var3;
                     return var17;
                  }
                  break label103;
               }

               var1.setlocal(11, var4);
               var1.setline(309);
               var5 = var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(11));
               var1.setlocal(12, var5);
               var5 = null;
               var1.setline(310);
               var15 = var1.getlocal(3);
               if (!var15.__nonzero__()) {
                  var15 = var1.getlocal(6).__call__(var2, var1.getlocal(12)).__not__();
               }
            } while(!var15.__nonzero__());

            var1.setline(311);
            var5 = var1.getglobal("walk").__call__(var2, var1.getlocal(12), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)).__iter__();
            var1.setline(311);
            var6 = var5.__iternext__();
         } while(var6 == null);

         var1.setlocal(13, var6);
         var1.setline(312);
         var1.setline(312);
         var15 = var1.getlocal(13);
         var1.f_lasti = 2;
         var7 = new Object[]{null, null, null, var9, var4, var5, var6};
         var1.f_savedlocals = var7;
         return var15;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _exists$6(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("__all__"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject execl$7(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyString.fromInterned("execl(file, *args)\n\n        Execute the executable file with argument list args, replacing the\n        current process. ");
      var1.setline(336);
      var1.getglobal("execv").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execle$8(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("execle(file, *args, env)\n\n        Execute the executable file with argument list args and\n        environment env, replacing the current process. ");
      var1.setline(343);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(344);
      var1.getglobal("execve").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execlp$9(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyString.fromInterned("execlp(file, *args)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args, replacing the current process. ");
      var1.setline(351);
      var1.getglobal("execvp").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execlpe$10(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned("execlpe(file, *args, env)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args and environment env, replacing the current\n        process. ");
      var1.setline(359);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(360);
      var1.getglobal("execvpe").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execvp$11(PyFrame var1, ThreadState var2) {
      var1.setline(367);
      PyString.fromInterned("execp(file, args)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args, replacing the current process.\n        args may be a list or tuple of strings. ");
      var1.setline(368);
      var1.getglobal("_execvpe").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execvpe$12(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyString.fromInterned("execvpe(file, args, env)\n\n        Execute the executable file (which is searched for along $PATH)\n        with argument list args and environment env , replacing the\n        current process.\n        args may be a list or tuple of strings. ");
      var1.setline(377);
      var1.getglobal("_execvpe").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _execvpe$13(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyTuple var8;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var3 = var1.getglobal("execve");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(384);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.setlocal(4, var8);
         var3 = null;
      } else {
         var1.setline(386);
         var3 = var1.getglobal("execv");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(387);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(388);
         var3 = var1.getglobal("environ");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(390);
      var3 = var1.getglobal("path").__getattr__("split").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(391);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(392);
         var10000 = var1.getlocal(3);
         PyObject[] var15 = new PyObject[]{var1.getlocal(0)};
         String[] var10 = new String[0];
         var10000._callextra(var15, var10, var1.getlocal(4), (PyObject)null);
         var3 = null;
         var1.setline(393);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(394);
         PyString var13 = PyString.fromInterned("PATH");
         var10000 = var13._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(395);
            var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("PATH"));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(397);
            var3 = var1.getglobal("defpath");
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(398);
         var3 = var1.getlocal(7).__getattr__("split").__call__(var2, var1.getglobal("pathsep"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(399);
         var3 = var1.getglobal("None");
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(400);
         var3 = var1.getglobal("None");
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(401);
         var3 = var1.getlocal(8).__iter__();

         while(true) {
            var1.setline(401);
            PyObject var9 = var3.__iternext__();
            if (var9 == null) {
               var1.setline(411);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(412);
                  throw Py.makeException(var1.getglobal("error"), var1.getlocal(9), var1.getlocal(10));
               } else {
                  var1.setline(413);
                  throw Py.makeException(var1.getglobal("error"), var1.getlocal(13), var1.getlocal(14));
               }
            }

            var1.setlocal(11, var9);
            var1.setline(402);
            var5 = var1.getglobal("path").__getattr__("join").__call__(var2, var1.getlocal(11), var1.getlocal(0));
            var1.setlocal(12, var5);
            var5 = null;

            try {
               var1.setline(404);
               var10000 = var1.getlocal(3);
               PyObject[] var12 = new PyObject[]{var1.getlocal(12)};
               String[] var14 = new String[0];
               var10000._callextra(var12, var14, var1.getlocal(4), (PyObject)null);
               var5 = null;
            } catch (Throwable var7) {
               PyException var11 = Py.setException(var7, var1);
               if (!var11.match(var1.getglobal("error"))) {
                  throw var11;
               }

               PyObject var6 = var11.value;
               var1.setlocal(13, var6);
               var6 = null;
               var1.setline(406);
               var6 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2));
               var1.setlocal(14, var6);
               var6 = null;
               var1.setline(407);
               var6 = var1.getlocal(13).__getattr__("errno");
               var10000 = var6._ne(var1.getglobal("errno").__getattr__("ENOENT"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(13).__getattr__("errno");
                  var10000 = var6._ne(var1.getglobal("errno").__getattr__("ENOTDIR"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(9);
                     var10000 = var6._is(var1.getglobal("None"));
                     var6 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(409);
                  var6 = var1.getlocal(13);
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(410);
                  var6 = var1.getlocal(14);
                  var1.setlocal(10, var6);
                  var6 = null;
               }
            }
         }
      }
   }

   public PyObject unsetenv$14(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      var1.getglobal("putenv").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Environ$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(438);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(443);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$17, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(445);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$18, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(447);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$19, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(449);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$20, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$21, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(453);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$22, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(455);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, update$23, (PyObject)null);
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$24, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      var1.getglobal("UserDict").__getattr__("UserDict").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(440);
      PyObject var3 = var1.getlocal(0).__getattr__("data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(441);
      var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(441);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(442);
         PyObject var7 = var1.getlocal(4);
         var1.getlocal(2).__setitem__(var1.getlocal(3).__getattr__("upper").__call__(var2), var7);
         var5 = null;
      }
   }

   public PyObject __setitem__$17(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("data").__setitem__(var1.getlocal(1).__getattr__("upper").__call__(var2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$18(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getitem__(var1.getlocal(1).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __delitem__$19(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      var1.getlocal(0).__getattr__("data").__delitem__(var1.getlocal(1).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject has_key$20(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$21(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("data"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$22(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyObject var3 = var1.getlocal(0).__getattr__("data").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("upper").__call__(var2), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject update$23(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      if (var1.getlocal(1).__nonzero__()) {
         label45: {
            PyException var3;
            PyObject var4;
            PyObject var5;
            PyObject[] var6;
            PyObject var10;
            try {
               var1.setline(458);
               PyObject var9 = var1.getlocal(1).__getattr__("keys").__call__(var2);
               var1.setlocal(3, var9);
               var3 = null;
            } catch (Throwable var8) {
               var3 = Py.setException(var8, var1);
               if (var3.match(var1.getglobal("AttributeError"))) {
                  var1.setline(461);
                  var4 = var1.getlocal(1).__iter__();

                  while(true) {
                     var1.setline(461);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        break label45;
                     }

                     var6 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var6[0];
                     var1.setlocal(4, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(5, var7);
                     var7 = null;
                     var1.setline(462);
                     var10 = var1.getlocal(5);
                     var1.getlocal(0).__setitem__(var1.getlocal(4), var10);
                     var6 = null;
                  }
               }

               throw var3;
            }

            var1.setline(467);
            var4 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(467);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(4, var5);
               var1.setline(468);
               var10 = var1.getlocal(1).__getitem__(var1.getlocal(4));
               var1.getlocal(0).__setitem__(var1.getlocal(4), var10);
               var6 = null;
            }
         }
      }

      var1.setline(469);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(470);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$24(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyObject var3 = var1.getglobal("dict").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getenv$25(PyFrame var1, ThreadState var2) {
      var1.setline(478);
      PyString.fromInterned("Get an environment variable, return None if it doesn't exist.\n    The optional second argument can specify an alternate default.");
      var1.setline(479);
      PyObject var3 = var1.getglobal("environ").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _spawnvef$26(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyObject var3 = var1.getglobal("fork").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(495);
      PyObject var10000;
      if (var1.getlocal(5).__not__().__nonzero__()) {
         try {
            var1.setline(498);
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(499);
               var1.getlocal(4).__call__(var2, var1.getlocal(1), var1.getlocal(2));
            } else {
               var1.setline(501);
               var1.getlocal(4).__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            }
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(503);
            var1.getglobal("_exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(127));
         }
      } else {
         var1.setline(506);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getglobal("P_NOWAIT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(507);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         }

         while(true) {
            var1.setline(508);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(509);
            PyObject var4 = var1.getglobal("waitpid").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(0));
            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
            var1.setline(510);
            if (!var1.getglobal("WIFSTOPPED").__call__(var2, var1.getlocal(7)).__nonzero__()) {
               var1.setline(512);
               if (var1.getglobal("WIFSIGNALED").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                  var1.setline(513);
                  var3 = var1.getglobal("WTERMSIG").__call__(var2, var1.getlocal(7)).__neg__();
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(514);
               if (var1.getglobal("WIFEXITED").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                  var1.setline(515);
                  var3 = var1.getglobal("WEXITSTATUS").__call__(var2, var1.getlocal(7));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(517);
               throw Py.makeException(var1.getglobal("error"), PyString.fromInterned("Not stopped, signaled or exited???"));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject spawnv$27(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyString.fromInterned("spawnv(mode, file, args) -> integer\n\nExecute file with arguments from args in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(526);
      PyObject var10000 = var1.getglobal("_spawnvef");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("None"), var1.getglobal("execv")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject spawnve$28(PyFrame var1, ThreadState var2) {
      var1.setline(535);
      PyString.fromInterned("spawnve(mode, file, args, env) -> integer\n\nExecute file with arguments from args in a subprocess with the\nspecified environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(536);
      PyObject var10000 = var1.getglobal("_spawnvef");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("execve")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject spawnvp$29(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyString.fromInterned("spawnvp(mode, file, args) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(548);
      PyObject var10000 = var1.getglobal("_spawnvef");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("None"), var1.getglobal("execvp")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject spawnvpe$30(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      PyString.fromInterned("spawnvpe(mode, file, args, env) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(558);
      PyObject var10000 = var1.getglobal("_spawnvef");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("execvpe")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject spawnl$31(PyFrame var1, ThreadState var2) {
      var1.setline(570);
      PyString.fromInterned("spawnl(mode, file, *args) -> integer\n\nExecute file with arguments from args in a subprocess.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(571);
      PyObject var3 = var1.getglobal("spawnv").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spawnle$32(PyFrame var1, ThreadState var2) {
      var1.setline(580);
      PyString.fromInterned("spawnle(mode, file, *args, env) -> integer\n\nExecute file with arguments from args in a subprocess with the\nsupplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(581);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(582);
      var3 = var1.getglobal("spawnve").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spawnlp$33(PyFrame var1, ThreadState var2) {
      var1.setline(598);
      PyString.fromInterned("spawnlp(mode, file, *args) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(599);
      PyObject var3 = var1.getglobal("spawnvp").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject spawnlpe$34(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyString.fromInterned("spawnlpe(mode, file, *args, env) -> integer\n\nExecute file (which is looked for along $PATH) with arguments from\nargs in a subprocess with the supplied environment.\nIf mode == P_NOWAIT return the pid of the process.\nIf mode == P_WAIT return the process's exit code if it exits normally;\notherwise return -SIG, where SIG is the signal that killed it. ");
      var1.setline(609);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(610);
      var3 = var1.getglobal("spawnvpe").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject popen2$35(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout) are returned.");
      var1.setline(626);
      PyObject var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(627);
      var3 = var1.getlocal(3).__getattr__("PIPE");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(628);
      PyObject var10000 = var1.getlocal(3).__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")), var1.getlocal(2), var1.getlocal(4), var1.getlocal(4), var1.getglobal("True")};
      String[] var4 = new String[]{"shell", "bufsize", "stdin", "stdout", "close_fds"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(631);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("stdin"), var1.getlocal(5).__getattr__("stdout")});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject popen3$36(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout, child_stderr) are returned.");
      var1.setline(642);
      PyObject var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(643);
      var3 = var1.getlocal(3).__getattr__("PIPE");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(644);
      PyObject var10000 = var1.getlocal(3).__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")), var1.getlocal(2), var1.getlocal(4), var1.getlocal(4), var1.getlocal(4), var1.getglobal("True")};
      String[] var4 = new String[]{"shell", "bufsize", "stdin", "stdout", "stderr", "close_fds"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(647);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("stdin"), var1.getlocal(5).__getattr__("stdout"), var1.getlocal(5).__getattr__("stderr")});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject popen4$37(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyString.fromInterned("Execute the shell command 'cmd' in a sub-process.  On UNIX, 'cmd'\n            may be a sequence, in which case arguments will be passed directly to\n            the program without shell intervention (as with os.spawnv()).  If 'cmd'\n            is a string it will be passed to the shell (as with os.system()). If\n            'bufsize' is specified, it sets the buffer size for the I/O pipes.  The\n            file objects (child_stdin, child_stdout_stderr) are returned.");
      var1.setline(658);
      PyObject var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(659);
      var3 = var1.getlocal(3).__getattr__("PIPE");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(660);
      PyObject var10000 = var1.getlocal(3).__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")), var1.getlocal(2), var1.getlocal(4), var1.getlocal(4), var1.getlocal(3).__getattr__("STDOUT"), var1.getglobal("True")};
      String[] var4 = new String[]{"shell", "bufsize", "stdin", "stdout", "stderr", "close_fds"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(663);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("stdin"), var1.getlocal(5).__getattr__("stdout")});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject urandom$38(PyFrame var1, ThreadState var2) {
      var1.setline(672);
      PyString.fromInterned("urandom(n) -> str\n\n        Return a string of n random bytes suitable for cryptographic use.\n\n        ");

      PyException var3;
      PyObject var5;
      try {
         var1.setline(674);
         var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/dev/urandom"), (PyObject)var1.getglobal("O_RDONLY"));
         var1.setlocal(1, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("OSError"), var1.getglobal("IOError")}))) {
            var1.setline(676);
            throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/dev/urandom (or equivalent) not found")));
         }

         throw var3;
      }

      var1.setline(677);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;

      while(true) {
         var1.setline(678);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._lt(var1.getlocal(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(680);
            var1.getglobal("close").__call__(var2, var1.getlocal(1));
            var1.setline(681);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(679);
         var5 = var1.getlocal(2);
         var5 = var5._iadd(var1.getglobal("read").__call__(var2, var1.getlocal(1), var1.getlocal(0)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)))));
         var1.setlocal(2, var5);
      }
   }

   public PyObject popen$39(PyFrame var1, ThreadState var2) {
      var1.setline(688);
      PyString.fromInterned("popen(command [, mode='r' [, bufsize]]) -> pipe\n\n    Open a pipe to/from a command returning a file object.\n    ");
      var1.setline(689);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__not__().__nonzero__()) {
         var1.setline(690);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("invalid cmd type (%s, expected string)")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(691);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("w")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(692);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid mode %r")._mod(var1.getlocal(1))));
         } else {
            var1.setline(693);
            var3 = imp.importOne("subprocess", var1, -1);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(694);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned("r"));
            var3 = null;
            String[] var4;
            PyObject[] var5;
            if (var10000.__nonzero__()) {
               var1.setline(695);
               var10000 = var1.getlocal(3).__getattr__("Popen");
               var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getglobal("True"), var1.getlocal(3).__getattr__("PIPE")};
               var4 = new String[]{"bufsize", "shell", "stdout"};
               var10000 = var10000.__call__(var2, var5, var4);
               var3 = null;
               var3 = var10000;
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(697);
               var3 = var1.getlocal(4).__getattr__("stdout");
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(698);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(PyString.fromInterned("w"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(699);
                  var10000 = var1.getlocal(3).__getattr__("Popen");
                  var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getglobal("True"), var1.getlocal(3).__getattr__("PIPE")};
                  var4 = new String[]{"bufsize", "shell", "stdin"};
                  var10000 = var10000.__call__(var2, var5, var4);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(701);
                  var3 = var1.getlocal(4).__getattr__("stdin");
                  var1.setlocal(5, var3);
                  var3 = null;
               }
            }

            var1.setline(703);
            var3 = var1.getglobal("fdopen").__call__(var2, var1.getlocal(5).__getattr__("fileno").__call__(var2), var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(704);
            var3 = var1.getglobal("_wrap_close").__call__(var2, var1.getlocal(5), var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _wrap_close$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(708);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$41, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(711);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$42, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(720);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$43, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(722);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$44, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(709);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_stream", var3);
      var3 = null;
      var1.setline(710);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_proc", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$42(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      var1.getlocal(0).__getattr__("_stream").__getattr__("close").__call__(var2);
      var1.setline(713);
      PyObject var3 = var1.getlocal(0).__getattr__("_proc").__getattr__("wait").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(714);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(715);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(716);
         PyObject var4 = var1.getglobal("_name");
         var10000 = var4._eq(PyString.fromInterned("nt"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(717);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(719);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __getattr__$43(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("_stream"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$44(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("_stream"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject system$45(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyString.fromInterned("system(command) -> exit_status\n\n    Execute the command (a string) in a subshell.");
      var1.setline(735);
      String[] var3 = new String[]{"_os_system"};
      PyObject[] var5 = imp.importFrom("subprocess", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(736);
      PyObject var6 = var1.getlocal(1);
      var1.setglobal("system", var6);
      var3 = null;
      var1.setline(737);
      var6 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var6;
   }

   public os$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"module", "_[40_16]", "n"};
      _get_exports_list$1 = Py.newCode(1, var2, var1, "_get_exports_list", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "mode", "head", "tail", "e"};
      makedirs$2 = Py.newCode(2, var2, var1, "makedirs", 153, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "head", "tail"};
      removedirs$3 = Py.newCode(1, var2, var1, "removedirs", 176, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"old", "new", "head", "tail"};
      renames$4 = Py.newCode(2, var2, var1, "renames", 198, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"top", "topdown", "onerror", "followlinks", "join", "isdir", "islink", "names", "err", "dirs", "nondirs", "name", "path", "x"};
      walk$5 = Py.newCode(4, var2, var1, "walk", 226, false, false, self, 5, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"name"};
      _exists$6 = Py.newCode(1, var2, var1, "_exists", 324, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args"};
      execl$7 = Py.newCode(2, var2, var1, "execl", 331, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args", "env"};
      execle$8 = Py.newCode(2, var2, var1, "execle", 338, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args"};
      execlp$9 = Py.newCode(2, var2, var1, "execlp", 346, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args", "env"};
      execlpe$10 = Py.newCode(2, var2, var1, "execlpe", 353, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args"};
      execvp$11 = Py.newCode(2, var2, var1, "execvp", 362, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args", "env"};
      execvpe$12 = Py.newCode(3, var2, var1, "execvpe", 370, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "args", "env", "func", "argrest", "head", "tail", "envpath", "PATH", "saved_exc", "saved_tb", "dir", "fullname", "e", "tb"};
      _execvpe$13 = Py.newCode(3, var2, var1, "_execvpe", 381, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key"};
      unsetenv$14 = Py.newCode(1, var2, var1, "unsetenv", 427, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Environ$15 = Py.newCode(0, var2, var1, "_Environ", 437, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "environ", "data", "k", "v"};
      __init__$16 = Py.newCode(2, var2, var1, "__init__", 438, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "item"};
      __setitem__$17 = Py.newCode(3, var2, var1, "__setitem__", 443, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$18 = Py.newCode(2, var2, var1, "__getitem__", 445, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$19 = Py.newCode(2, var2, var1, "__delitem__", 447, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$20 = Py.newCode(2, var2, var1, "has_key", 449, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$21 = Py.newCode(2, var2, var1, "__contains__", 451, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "failobj"};
      get$22 = Py.newCode(3, var2, var1, "get", 453, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "kwargs", "keys", "k", "v"};
      update$23 = Py.newCode(3, var2, var1, "update", 455, false, true, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$24 = Py.newCode(1, var2, var1, "copy", 471, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "default"};
      getenv$25 = Py.newCode(2, var2, var1, "getenv", 476, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args", "env", "func", "pid", "wpid", "sts"};
      _spawnvef$26 = Py.newCode(5, var2, var1, "_spawnvef", 492, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args"};
      spawnv$27 = Py.newCode(3, var2, var1, "spawnv", 519, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args", "env"};
      spawnve$28 = Py.newCode(4, var2, var1, "spawnve", 528, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args"};
      spawnvp$29 = Py.newCode(3, var2, var1, "spawnvp", 540, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args", "env"};
      spawnvpe$30 = Py.newCode(4, var2, var1, "spawnvpe", 550, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args"};
      spawnl$31 = Py.newCode(3, var2, var1, "spawnl", 564, true, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args", "env"};
      spawnle$32 = Py.newCode(3, var2, var1, "spawnle", 573, true, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args"};
      spawnlp$33 = Py.newCode(3, var2, var1, "spawnlp", 591, true, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "file", "args", "env"};
      spawnlpe$34 = Py.newCode(3, var2, var1, "spawnlpe", 601, true, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "mode", "bufsize", "subprocess", "PIPE", "p"};
      popen2$35 = Py.newCode(3, var2, var1, "popen2", 619, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "mode", "bufsize", "subprocess", "PIPE", "p"};
      popen3$36 = Py.newCode(3, var2, var1, "popen3", 635, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "mode", "bufsize", "subprocess", "PIPE", "p"};
      popen4$37 = Py.newCode(3, var2, var1, "popen4", 651, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "_urandomfd", "bytes"};
      urandom$38 = Py.newCode(1, var2, var1, "urandom", 667, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "mode", "bufsize", "subprocess", "proc", "fp"};
      popen$39 = Py.newCode(3, var2, var1, "popen", 684, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _wrap_close$40 = Py.newCode(0, var2, var1, "_wrap_close", 707, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "proc"};
      __init__$41 = Py.newCode(3, var2, var1, "__init__", 708, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "returncode"};
      close$42 = Py.newCode(1, var2, var1, "close", 711, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$43 = Py.newCode(2, var2, var1, "__getattr__", 720, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$44 = Py.newCode(1, var2, var1, "__iter__", 722, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"command", "_os_system"};
      system$45 = Py.newCode(1, var2, var1, "system", 726, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new os$py("os$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(os$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._get_exports_list$1(var2, var3);
         case 2:
            return this.makedirs$2(var2, var3);
         case 3:
            return this.removedirs$3(var2, var3);
         case 4:
            return this.renames$4(var2, var3);
         case 5:
            return this.walk$5(var2, var3);
         case 6:
            return this._exists$6(var2, var3);
         case 7:
            return this.execl$7(var2, var3);
         case 8:
            return this.execle$8(var2, var3);
         case 9:
            return this.execlp$9(var2, var3);
         case 10:
            return this.execlpe$10(var2, var3);
         case 11:
            return this.execvp$11(var2, var3);
         case 12:
            return this.execvpe$12(var2, var3);
         case 13:
            return this._execvpe$13(var2, var3);
         case 14:
            return this.unsetenv$14(var2, var3);
         case 15:
            return this._Environ$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.__setitem__$17(var2, var3);
         case 18:
            return this.__getitem__$18(var2, var3);
         case 19:
            return this.__delitem__$19(var2, var3);
         case 20:
            return this.has_key$20(var2, var3);
         case 21:
            return this.__contains__$21(var2, var3);
         case 22:
            return this.get$22(var2, var3);
         case 23:
            return this.update$23(var2, var3);
         case 24:
            return this.copy$24(var2, var3);
         case 25:
            return this.getenv$25(var2, var3);
         case 26:
            return this._spawnvef$26(var2, var3);
         case 27:
            return this.spawnv$27(var2, var3);
         case 28:
            return this.spawnve$28(var2, var3);
         case 29:
            return this.spawnvp$29(var2, var3);
         case 30:
            return this.spawnvpe$30(var2, var3);
         case 31:
            return this.spawnl$31(var2, var3);
         case 32:
            return this.spawnle$32(var2, var3);
         case 33:
            return this.spawnlp$33(var2, var3);
         case 34:
            return this.spawnlpe$34(var2, var3);
         case 35:
            return this.popen2$35(var2, var3);
         case 36:
            return this.popen3$36(var2, var3);
         case 37:
            return this.popen4$37(var2, var3);
         case 38:
            return this.urandom$38(var2, var3);
         case 39:
            return this.popen$39(var2, var3);
         case 40:
            return this._wrap_close$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.close$42(var2, var3);
         case 43:
            return this.__getattr__$43(var2, var3);
         case 44:
            return this.__iter__$44(var2, var3);
         case 45:
            return this.system$45(var2, var3);
         default:
            return null;
      }
   }
}
