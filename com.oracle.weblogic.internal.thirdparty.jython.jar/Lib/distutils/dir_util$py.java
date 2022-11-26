package distutils;

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
@Filename("distutils/dir_util.py")
public class dir_util$py extends PyFunctionTable implements PyRunnable {
   static dir_util$py self;
   static final PyCode f$0;
   static final PyCode mkpath$1;
   static final PyCode create_tree$2;
   static final PyCode copy_tree$3;
   static final PyCode _build_cmdtuple$4;
   static final PyCode remove_tree$5;
   static final PyCode ensure_relative$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.dir_util\n\nUtility functions for manipulating directories and directory trees."));
      var1.setline(3);
      PyString.fromInterned("distutils.dir_util\n\nUtility functions for manipulating directories and directory trees.");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(8);
      var5 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var5);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"DistutilsFileError", "DistutilsInternalError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsInternalError", var4);
      var4 = null;
      var1.setline(10);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(14);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_path_created", var8);
      var3 = null;
      var1.setline(19);
      var7 = new PyObject[]{Py.newInteger(511), Py.newInteger(1), Py.newInteger(0)};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, mkpath$1, PyString.fromInterned("Create a directory and any missing ancestor directories.\n\n    If the directory already exists (or if 'name' is the empty string, which\n    means the current directory, which of course exists), then do nothing.\n    Raise DistutilsFileError if unable to create some directory along the way\n    (eg. some sub-path exists, but is a file rather than a directory).\n    If 'verbose' is true, print a one-line summary of each mkdir to stdout.\n    Return the list of directories actually created.\n    "));
      var1.setlocal("mkpath", var9);
      var3 = null;
      var1.setline(82);
      var7 = new PyObject[]{Py.newInteger(511), Py.newInteger(1), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, create_tree$2, PyString.fromInterned("Create all the empty directories under 'base_dir' needed to put 'files'\n    there.\n\n    'base_dir' is just the name of a directory which doesn't necessarily\n    exist yet; 'files' is a list of filenames to be interpreted relative to\n    'base_dir'.  'base_dir' + the directory portion of every file in 'files'\n    will be created if it doesn't already exist.  'mode', 'verbose' and\n    'dry_run' flags are as for 'mkpath()'.\n    "));
      var1.setlocal("create_tree", var9);
      var3 = null;
      var1.setline(103);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, copy_tree$3, PyString.fromInterned("Copy an entire directory tree 'src' to a new location 'dst'.\n\n    Both 'src' and 'dst' must be directory names.  If 'src' is not a\n    directory, raise DistutilsFileError.  If 'dst' does not exist, it is\n    created with 'mkpath()'.  The end result of the copy is that every\n    file in 'src' is copied to 'dst', and directories under 'src' are\n    recursively copied to 'dst'.  Return the list of files that were\n    copied or might have been copied, using their output name.  The\n    return value is unaffected by 'update' or 'dry_run': it is simply\n    the list of all files under 'src', with the names changed to be\n    under 'dst'.\n\n    'preserve_mode' and 'preserve_times' are the same as for\n    'copy_file'; note that they only apply to regular files, not to\n    directories.  If 'preserve_symlinks' is true, symlinks will be\n    copied as symlinks (on platforms that support them!); otherwise\n    (the default), the destination of the symlink will be copied.\n    'update' and 'verbose' are the same as for 'copy_file'.\n    "));
      var1.setlocal("copy_tree", var9);
      var3 = null;
      var1.setline(172);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, _build_cmdtuple$4, PyString.fromInterned("Helper for remove_tree()."));
      var1.setlocal("_build_cmdtuple", var9);
      var3 = null;
      var1.setline(182);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, remove_tree$5, PyString.fromInterned("Recursively remove an entire directory tree.\n\n    Any errors are ignored (apart from being reported to stdout if 'verbose'\n    is true).\n    "));
      var1.setlocal("remove_tree", var9);
      var3 = null;
      var1.setline(206);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, ensure_relative$6, PyString.fromInterned("Take the full path 'path', and make it a relative path.\n\n    This is useful to make 'path' the second argument to os.path.join().\n    "));
      var1.setlocal("ensure_relative", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mkpath$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Create a directory and any missing ancestor directories.\n\n    If the directory already exists (or if 'name' is the empty string, which\n    means the current directory, which of course exists), then do nothing.\n    Raise DistutilsFileError if unable to create some directory along the way\n    (eg. some sub-path exists, but is a file rather than a directory).\n    If 'verbose' is true, print a one-line summary of each mkdir to stdout.\n    Return the list of directories actually created.\n    ");
      var1.setline(33);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var1.setline(34);
         throw Py.makeException(var1.getglobal("DistutilsInternalError"), PyString.fromInterned("mkpath: 'name' must be a string (got %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
      } else {
         var1.setline(42);
         PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(43);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var9);
         var3 = null;
         var1.setline(44);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(45);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(46);
            if (var1.getglobal("_path_created").__getattr__("get").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0))).__nonzero__()) {
               var1.setline(47);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(49);
               PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(0));
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var4 = null;
               var1.setline(50);
               PyList var10 = new PyList(new PyObject[]{var1.getlocal(6)});
               var1.setlocal(7, var10);
               var4 = null;

               while(true) {
                  var1.setline(52);
                  var10000 = var1.getlocal(5);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(6);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5)).__not__();
                     }
                  }

                  if (!var10000.__nonzero__()) {
                     var1.setline(59);
                     var4 = var1.getlocal(7).__iter__();

                     while(true) {
                        do {
                           var1.setline(59);
                           PyObject var11 = var4.__iternext__();
                           if (var11 == null) {
                              var1.setline(80);
                              var3 = var1.getlocal(4);
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setlocal(8, var11);
                           var1.setline(61);
                           var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(8));
                           var1.setlocal(5, var6);
                           var6 = null;
                           var1.setline(62);
                           var6 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(5));
                           var1.setlocal(9, var6);
                           var6 = null;
                           var1.setline(64);
                        } while(var1.getglobal("_path_created").__getattr__("get").__call__(var2, var1.getlocal(9)).__nonzero__());

                        var1.setline(67);
                        var6 = var1.getlocal(2);
                        var10000 = var6._ge(Py.newInteger(1));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(68);
                           var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("creating %s"), (PyObject)var1.getlocal(5));
                        }

                        var1.setline(70);
                        if (var1.getlocal(3).__not__().__nonzero__()) {
                           try {
                              var1.setline(72);
                              var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(5), var1.getlocal(1));
                           } catch (Throwable var8) {
                              PyException var12 = Py.setException(var8, var1);
                              if (!var12.match(var1.getglobal("OSError"))) {
                                 throw var12;
                              }

                              PyObject var7 = var12.value;
                              var1.setlocal(10, var7);
                              var7 = null;
                              var1.setline(74);
                              var7 = var1.getlocal(10).__getattr__("errno");
                              var10000 = var7._eq(var1.getglobal("errno").__getattr__("EEXIST"));
                              var7 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5));
                              }

                              if (var10000.__not__().__nonzero__()) {
                                 var1.setline(75);
                                 throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not create '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(10).__getattr__("args").__getitem__(Py.newInteger(-1))}))));
                              }
                           }

                           var1.setline(77);
                           var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
                        }

                        var1.setline(79);
                        PyInteger var13 = Py.newInteger(1);
                        var1.getglobal("_path_created").__setitem__((PyObject)var1.getlocal(9), var13);
                        var6 = null;
                     }
                  }

                  var1.setline(53);
                  var4 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(5));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(54);
                  var1.getlocal(7).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(6));
               }
            }
         }
      }
   }

   public PyObject create_tree$2(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Create all the empty directories under 'base_dir' needed to put 'files'\n    there.\n\n    'base_dir' is just the name of a directory which doesn't necessarily\n    exist yet; 'files' is a list of filenames to be interpreted relative to\n    'base_dir'.  'base_dir' + the directory portion of every file in 'files'\n    will be created if it doesn't already exist.  'mode', 'verbose' and\n    'dry_run' flags are as for 'mkpath()'.\n    ");
      var1.setline(93);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(94);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(94);
         PyObject var4 = var7.__iternext__();
         PyInteger var5;
         if (var4 == null) {
            var1.setline(96);
            var7 = var1.getlocal(5).__getattr__("keys").__call__(var2);
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(97);
            var1.getlocal(7).__getattr__("sort").__call__(var2);
            var1.setline(100);
            var7 = var1.getlocal(7).__iter__();

            while(true) {
               var1.setline(100);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(8, var4);
               var1.setline(101);
               PyObject var10000 = var1.getglobal("mkpath");
               PyObject[] var8 = new PyObject[]{var1.getlocal(8), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
               String[] var6 = new String[]{"verbose", "dry_run"};
               var10000.__call__(var2, var8, var6);
               var5 = null;
            }
         }

         var1.setlocal(6, var4);
         var1.setline(95);
         var5 = Py.newInteger(1);
         var1.getlocal(5).__setitem__((PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(6))), var5);
         var5 = null;
      }
   }

   public PyObject copy_tree$3(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Copy an entire directory tree 'src' to a new location 'dst'.\n\n    Both 'src' and 'dst' must be directory names.  If 'src' is not a\n    directory, raise DistutilsFileError.  If 'dst' does not exist, it is\n    created with 'mkpath()'.  The end result of the copy is that every\n    file in 'src' is copied to 'dst', and directories under 'src' are\n    recursively copied to 'dst'.  Return the list of files that were\n    copied or might have been copied, using their output name.  The\n    return value is unaffected by 'update' or 'dry_run': it is simply\n    the list of all files under 'src', with the names changed to be\n    under 'dst'.\n\n    'preserve_mode' and 'preserve_times' are the same as for\n    'copy_file'; note that they only apply to regular files, not to\n    directories.  If 'preserve_symlinks' is true, symlinks will be\n    copied as symlinks (on platforms that support them!); otherwise\n    (the default), the destination of the symlink will be copied.\n    'update' and 'verbose' are the same as for 'copy_file'.\n    ");
      var1.setline(124);
      String[] var3 = new String[]{"copy_file"};
      PyObject[] var8 = imp.importFrom("distutils.file_util", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(126);
      PyObject var10000 = var1.getlocal(7).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(127);
         throw Py.makeException(var1.getglobal("DistutilsFileError"), PyString.fromInterned("cannot copy tree '%s': not a directory")._mod(var1.getlocal(0)));
      } else {
         PyObject[] var5;
         PyObject var10;
         try {
            var1.setline(130);
            var10 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
            var1.setlocal(9, var10);
            var3 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (!var9.match(var1.getglobal("os").__getattr__("error"))) {
               throw var9;
            }

            var4 = var9.value;
            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(11, var6);
            var6 = null;
            var4 = null;
            var1.setline(132);
            if (!var1.getlocal(7).__nonzero__()) {
               var1.setline(135);
               throw Py.makeException(var1.getglobal("DistutilsFileError"), PyString.fromInterned("error listing files in '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(11)})));
            }

            var1.setline(133);
            PyList var12 = new PyList(Py.EmptyObjects);
            var1.setlocal(9, var12);
            var4 = null;
         }

         var1.setline(138);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            var1.setline(139);
            var10000 = var1.getglobal("mkpath");
            var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(6)};
            String[] var14 = new String[]{"verbose"};
            var10000.__call__(var2, var8, var14);
            var3 = null;
         }

         var1.setline(141);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(12, var11);
         var3 = null;
         var1.setline(143);
         var10 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(143);
            var4 = var10.__iternext__();
            if (var4 == null) {
               var1.setline(170);
               var10 = var1.getlocal(12);
               var1.f_lasti = -1;
               return var10;
            }

            var1.setlocal(13, var4);
            var1.setline(144);
            PyObject var13 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(13));
            var1.setlocal(14, var13);
            var5 = null;
            var1.setline(145);
            var13 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(13));
            var1.setlocal(15, var13);
            var5 = null;
            var1.setline(147);
            if (!var1.getlocal(13).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".nfs")).__nonzero__()) {
               var1.setline(151);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(14));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(152);
                  var13 = var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(14));
                  var1.setlocal(16, var13);
                  var5 = null;
                  var1.setline(153);
                  var13 = var1.getlocal(6);
                  var10000 = var13._ge(Py.newInteger(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(154);
                     var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("linking %s -> %s"), (PyObject)var1.getlocal(15), (PyObject)var1.getlocal(16));
                  }

                  var1.setline(155);
                  if (var1.getlocal(7).__not__().__nonzero__()) {
                     var1.setline(156);
                     var1.getglobal("os").__getattr__("symlink").__call__(var2, var1.getlocal(16), var1.getlocal(15));
                  }

                  var1.setline(157);
                  var1.getlocal(12).__getattr__("append").__call__(var2, var1.getlocal(15));
               } else {
                  var1.setline(159);
                  String[] var15;
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                     var1.setline(160);
                     var10000 = var1.getlocal(12).__getattr__("extend");
                     PyObject var10002 = var1.getglobal("copy_tree");
                     var5 = new PyObject[]{var1.getlocal(14), var1.getlocal(15), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
                     var15 = new String[]{"verbose", "dry_run"};
                     var10002 = var10002.__call__(var2, var5, var15);
                     var5 = null;
                     var10000.__call__(var2, var10002);
                  } else {
                     var1.setline(165);
                     var10000 = var1.getlocal(8);
                     var5 = new PyObject[]{var1.getlocal(14), var1.getlocal(15), var1.getlocal(2), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
                     var15 = new String[]{"verbose", "dry_run"};
                     var10000.__call__(var2, var5, var15);
                     var5 = null;
                     var1.setline(168);
                     var1.getlocal(12).__getattr__("append").__call__(var2, var1.getlocal(15));
                  }
               }
            }
         }
      }
   }

   public PyObject _build_cmdtuple$4(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("Helper for remove_tree().");
      var1.setline(174);
      PyObject var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(174);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(180);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("rmdir"), var1.getlocal(0)})));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(175);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(176);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(3));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(3)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(177);
            var1.getglobal("_build_cmdtuple").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         } else {
            var1.setline(179);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("remove"), var1.getlocal(3)})));
         }
      }
   }

   public PyObject remove_tree$5(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Recursively remove an entire directory tree.\n\n    Any errors are ignored (apart from being reported to stdout if 'verbose'\n    is true).\n    ");
      var1.setline(190);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(191);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("removing '%s' (and everything under it)"), (PyObject)var1.getlocal(0));
      }

      var1.setline(192);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(193);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(194);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(195);
         var1.getglobal("_build_cmdtuple").__call__(var2, var1.getlocal(0), var1.getlocal(3));
         var1.setline(196);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(196);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);

            PyException var5;
            try {
               var1.setline(198);
               var1.getlocal(4).__getitem__(Py.newInteger(0)).__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1)));
               var1.setline(200);
               PyObject var9 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1)));
               var1.setlocal(5, var9);
               var5 = null;
               var1.setline(201);
               var9 = var1.getlocal(5);
               var10000 = var9._in(var1.getglobal("_path_created"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(202);
                  var1.getglobal("_path_created").__delitem__(var1.getlocal(5));
               }
            } catch (Throwable var7) {
               var5 = Py.setException(var7, var1);
               if (!var5.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("OSError")}))) {
                  throw var5;
               }

               PyObject var6 = var5.value;
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(204);
               var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("error removing %s: %s"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(6));
            }
         }
      }
   }

   public PyObject ensure_relative$6(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyString.fromInterned("Take the full path 'path', and make it a relative path.\n\n    This is useful to make 'path' the second argument to os.path.join().\n    ");
      var1.setline(211);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(212);
      var3 = var1.getlocal(0).__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._eq(var1.getglobal("os").__getattr__("sep"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(213);
         var3 = var1.getlocal(1)._add(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(214);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public dir_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "mode", "verbose", "dry_run", "created_dirs", "head", "tail", "tails", "d", "abs_head", "exc"};
      mkpath$1 = Py.newCode(4, var2, var1, "mkpath", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"base_dir", "files", "mode", "verbose", "dry_run", "need_dir", "file", "need_dirs", "dir"};
      create_tree$2 = Py.newCode(5, var2, var1, "create_tree", 82, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "preserve_mode", "preserve_times", "preserve_symlinks", "update", "verbose", "dry_run", "copy_file", "names", "errno", "errstr", "outputs", "n", "src_name", "dst_name", "link_dest"};
      copy_tree$3 = Py.newCode(8, var2, var1, "copy_tree", 103, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "cmdtuples", "f", "real_f"};
      _build_cmdtuple$4 = Py.newCode(2, var2, var1, "_build_cmdtuple", 172, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"directory", "verbose", "dry_run", "cmdtuples", "cmd", "abspath", "exc"};
      remove_tree$5 = Py.newCode(3, var2, var1, "remove_tree", 182, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "drive"};
      ensure_relative$6 = Py.newCode(1, var2, var1, "ensure_relative", 206, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dir_util$py("distutils/dir_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dir_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.mkpath$1(var2, var3);
         case 2:
            return this.create_tree$2(var2, var3);
         case 3:
            return this.copy_tree$3(var2, var3);
         case 4:
            return this._build_cmdtuple$4(var2, var3);
         case 5:
            return this.remove_tree$5(var2, var3);
         case 6:
            return this.ensure_relative$6(var2, var3);
         default:
            return null;
      }
   }
}
