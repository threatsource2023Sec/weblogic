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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("distutils/file_util.py")
public class file_util$py extends PyFunctionTable implements PyRunnable {
   static file_util$py self;
   static final PyCode f$0;
   static final PyCode _copy_file_contents$1;
   static final PyCode copy_file$2;
   static final PyCode move_file$3;
   static final PyCode write_file$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.file_util\n\nUtility functions for operating on single files.\n"));
      var1.setline(4);
      PyString.fromInterned("distutils.file_util\n\nUtility functions for operating on single files.\n");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"DistutilsFileError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var1.setline(10);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(13);
      PyDictionary var8 = new PyDictionary(new PyObject[]{var1.getname("None"), PyString.fromInterned("copying"), PyString.fromInterned("hard"), PyString.fromInterned("hard linking"), PyString.fromInterned("sym"), PyString.fromInterned("symbolically linking")});
      var1.setlocal("_copy_action", var8);
      var3 = null;
      var1.setline(18);
      var7 = new PyObject[]{Py.newInteger(16)._mul(Py.newInteger(1024))};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, _copy_file_contents$1, PyString.fromInterned("Copy the file 'src' to 'dst'.\n\n    Both must be filenames. Any error opening either file, reading from\n    'src', or writing to 'dst', raises DistutilsFileError.  Data is\n    read/written in chunks of 'buffer_size' bytes (default 16k).  No attempt\n    is made to handle anything apart from regular files.\n    "));
      var1.setlocal("_copy_file_contents", var9);
      var3 = null;
      var1.setline(71);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), var1.getname("None"), Py.newInteger(1), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, copy_file$2, PyString.fromInterned("Copy a file 'src' to 'dst'.\n\n    If 'dst' is a directory, then 'src' is copied there with the same name;\n    otherwise, it must be a filename.  (If the file exists, it will be\n    ruthlessly clobbered.)  If 'preserve_mode' is true (the default),\n    the file's mode (type and permission bits, or whatever is analogous on\n    the current platform) is copied.  If 'preserve_times' is true (the\n    default), the last-modified and last-access times are copied as well.\n    If 'update' is true, 'src' will only be copied if 'dst' does not exist,\n    or if 'dst' does exist but is older than 'src'.\n\n    'link' allows you to make hard links (os.link) or symbolic links\n    (os.symlink) instead of copying: set it to \"hard\" or \"sym\"; if it is\n    None (the default), files are copied.  Don't set 'link' on systems that\n    don't support it: 'copy_file()' doesn't check if hard or symbolic\n    linking is available. If hardlink fails, falls back to\n    _copy_file_contents().\n\n    Under Mac OS, uses the native file copy function in macostools; on\n    other systems, uses '_copy_file_contents()' to copy file contents.\n\n    Return a tuple (dest_name, copied): 'dest_name' is the actual name of\n    the output file, and 'copied' is true if the file was copied (or would\n    have been copied, if 'dry_run' true).\n    "));
      var1.setlocal("copy_file", var9);
      var3 = null;
      var1.setline(170);
      var7 = new PyObject[]{Py.newInteger(1), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, move_file$3, PyString.fromInterned("Move a file 'src' to 'dst'.\n\n    If 'dst' is a directory, the file will be moved into it with the same\n    name; otherwise, 'src' is just renamed to 'dst'.  Return the new\n    full name of the file.\n\n    Handles cross-device moves on Unix using 'copy_file()'.  What about\n    other systems???\n    "));
      var1.setlocal("move_file", var9);
      var3 = null;
      var1.setline(230);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, write_file$4, PyString.fromInterned("Create a file with the specified name and write 'contents' (a\n    sequence of strings without line terminators) to it.\n    "));
      var1.setlocal("write_file", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _copy_file_contents$1(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyString.fromInterned("Copy the file 'src' to 'dst'.\n\n    Both must be filenames. Any error opening either file, reading from\n    'src', or writing to 'dst', raises DistutilsFileError.  Data is\n    read/written in chunks of 'buffer_size' bytes (default 16k).  No attempt\n    is made to handle anything apart from regular files.\n    ");
      var1.setline(28);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         PyException var4;
         PyObject var5;
         PyObject[] var6;
         PyObject var7;
         PyObject var14;
         try {
            var1.setline(32);
            var14 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(3, var14);
            var4 = null;
         } catch (Throwable var10) {
            var4 = Py.setException(var10, var1);
            if (var4.match(var1.getglobal("os").__getattr__("error"))) {
               var5 = var4.value;
               var6 = Py.unpackSequence(var5, 2);
               var7 = var6[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;
               var1.setline(34);
               throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not open '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(6)}))));
            }

            throw var4;
         }

         var1.setline(36);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            try {
               var1.setline(38);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(1));
            } catch (Throwable var11) {
               var4 = Py.setException(var11, var1);
               if (var4.match(var1.getglobal("os").__getattr__("error"))) {
                  var5 = var4.value;
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(5, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(40);
                  throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not delete '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)}))));
               }

               throw var4;
            }
         }

         try {
            var1.setline(44);
            var14 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
            var1.setlocal(4, var14);
            var4 = null;
         } catch (Throwable var12) {
            var4 = Py.setException(var12, var1);
            if (var4.match(var1.getglobal("os").__getattr__("error"))) {
               var5 = var4.value;
               var6 = Py.unpackSequence(var5, 2);
               var7 = var6[0];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;
               var1.setline(46);
               throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not create '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)}))));
            }

            throw var4;
         }

         while(true) {
            var1.setline(49);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            try {
               var1.setline(51);
               var14 = var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(2));
               var1.setlocal(7, var14);
               var4 = null;
            } catch (Throwable var9) {
               var4 = Py.setException(var9, var1);
               if (var4.match(var1.getglobal("os").__getattr__("error"))) {
                  var5 = var4.value;
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(5, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(53);
                  throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not read from '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(6)}))));
               }

               throw var4;
            }

            var1.setline(56);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               break;
            }

            try {
               var1.setline(60);
               var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(7));
            } catch (Throwable var8) {
               var4 = Py.setException(var8, var1);
               if (var4.match(var1.getglobal("os").__getattr__("error"))) {
                  var5 = var4.value;
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(5, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(62);
                  throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("could not write to '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)}))));
               }

               throw var4;
            }
         }
      } catch (Throwable var13) {
         Py.addTraceback(var13, var1);
         var1.setline(66);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(67);
            var1.getlocal(4).__getattr__("close").__call__(var2);
         }

         var1.setline(68);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(69);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         }

         throw (Throwable)var13;
      }

      var1.setline(66);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(67);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      }

      var1.setline(68);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(69);
         var1.getlocal(3).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy_file$2(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Copy a file 'src' to 'dst'.\n\n    If 'dst' is a directory, then 'src' is copied there with the same name;\n    otherwise, it must be a filename.  (If the file exists, it will be\n    ruthlessly clobbered.)  If 'preserve_mode' is true (the default),\n    the file's mode (type and permission bits, or whatever is analogous on\n    the current platform) is copied.  If 'preserve_times' is true (the\n    default), the last-modified and last-access times are copied as well.\n    If 'update' is true, 'src' will only be copied if 'dst' does not exist,\n    or if 'dst' does exist but is older than 'src'.\n\n    'link' allows you to make hard links (os.link) or symbolic links\n    (os.symlink) instead of copying: set it to \"hard\" or \"sym\"; if it is\n    None (the default), files are copied.  Don't set 'link' on systems that\n    don't support it: 'copy_file()' doesn't check if hard or symbolic\n    linking is available. If hardlink fails, falls back to\n    _copy_file_contents().\n\n    Under Mac OS, uses the native file copy function in macostools; on\n    other systems, uses '_copy_file_contents()' to copy file contents.\n\n    Return a tuple (dest_name, copied): 'dest_name' is the actual name of\n    the output file, and 'copied' is true if the file was copied (or would\n    have been copied, if 'dry_run' true).\n    ");
      var1.setline(105);
      String[] var3 = new String[]{"newer"};
      PyObject[] var7 = imp.importFrom("distutils.dep_util", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(106);
      var3 = new String[]{"ST_ATIME", "ST_MTIME", "ST_MODE", "S_IMODE"};
      var7 = imp.importFrom("stat", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal(9, var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal(10, var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal(11, var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal(12, var4);
      var4 = null;
      var1.setline(108);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(109);
         throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("can't copy '%s': doesn't exist or not a regular file")._mod(var1.getlocal(0))));
      } else {
         var1.setline(112);
         PyObject var8;
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(113);
            var8 = var1.getlocal(1);
            var1.setlocal(13, var8);
            var3 = null;
            var1.setline(114);
            var8 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0)));
            var1.setlocal(1, var8);
            var3 = null;
         } else {
            var1.setline(116);
            var8 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(1));
            var1.setlocal(13, var8);
            var3 = null;
         }

         var1.setline(118);
         PyObject var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(8).__call__(var2, var1.getlocal(0), var1.getlocal(1)).__not__();
         }

         PyTuple var9;
         if (var10000.__nonzero__()) {
            var1.setline(119);
            var8 = var1.getlocal(6);
            var10000 = var8._ge(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(120);
               var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not copying %s (output up-to-date)"), (PyObject)var1.getlocal(0));
            }

            var1.setline(121);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var9;
         } else {
            PyException var10;
            try {
               var1.setline(124);
               var4 = var1.getglobal("_copy_action").__getitem__(var1.getlocal(5));
               var1.setlocal(14, var4);
               var4 = null;
            } catch (Throwable var5) {
               var10 = Py.setException(var5, var1);
               if (var10.match(var1.getglobal("KeyError"))) {
                  var1.setline(126);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid value '%s' for 'link' argument")._mod(var1.getlocal(5))));
               }

               throw var10;
            }

            var1.setline(128);
            var4 = var1.getlocal(6);
            var10000 = var4._ge(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(129);
               var4 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
               var10000 = var4._eq(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0)));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(130);
                  var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("%s %s -> %s"), var1.getlocal(14), var1.getlocal(0), var1.getlocal(13));
               } else {
                  var1.setline(132);
                  var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("%s %s -> %s"), var1.getlocal(14), var1.getlocal(0), var1.getlocal(1));
               }
            }

            var1.setline(134);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(135);
               var9 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(1)});
               var1.f_lasti = -1;
               return var9;
            } else {
               var1.setline(139);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(PyString.fromInterned("hard"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(140);
                  var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("os").__getattr__("path").__getattr__("samefile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                  }

                  if (var10000.__not__().__nonzero__()) {
                     try {
                        var1.setline(142);
                        var1.getglobal("os").__getattr__("link").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                        var1.setline(143);
                        var9 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(1)});
                        var1.f_lasti = -1;
                        return var9;
                     } catch (Throwable var6) {
                        var10 = Py.setException(var6, var1);
                        if (!var10.match(var1.getglobal("OSError"))) {
                           throw var10;
                        }
                     }

                     var1.setline(148);
                  }
               } else {
                  var1.setline(149);
                  var4 = var1.getlocal(5);
                  var10000 = var4._eq(PyString.fromInterned("sym"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(150);
                     var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("samefile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                     }

                     if (var10000.__not__().__nonzero__()) {
                        var1.setline(151);
                        var1.getglobal("os").__getattr__("symlink").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                        var1.setline(152);
                        var9 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(1)});
                        var1.f_lasti = -1;
                        return var9;
                     }
                  }
               }

               var1.setline(156);
               var1.getglobal("_copy_file_contents").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setline(157);
               var10000 = var1.getlocal(2);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(3);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(158);
                  var4 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0));
                  var1.setlocal(15, var4);
                  var4 = null;
                  var1.setline(162);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(163);
                     var1.getglobal("os").__getattr__("utime").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(15).__getitem__(var1.getlocal(9)), var1.getlocal(15).__getitem__(var1.getlocal(10))})));
                  }

                  var1.setline(164);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(165);
                     var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(12).__call__(var2, var1.getlocal(15).__getitem__(var1.getlocal(11))));
                  }
               }

               var1.setline(167);
               var9 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(1)});
               var1.f_lasti = -1;
               return var9;
            }
         }
      }
   }

   public PyObject move_file$3(PyFrame var1, ThreadState var2) {
      var1.setline(179);
      PyString.fromInterned("Move a file 'src' to 'dst'.\n\n    If 'dst' is a directory, the file will be moved into it with the same\n    name; otherwise, 'src' is just renamed to 'dst'.  Return the new\n    full name of the file.\n\n    Handles cross-device moves on Unix using 'copy_file()'.  What about\n    other systems???\n    ");
      var1.setline(180);
      String[] var3 = new String[]{"exists", "isfile", "isdir", "basename", "dirname"};
      PyObject[] var11 = imp.importFrom("os.path", var3, var1, -1);
      PyObject var4 = var11[0];
      var1.setlocal(4, var4);
      var4 = null;
      var4 = var11[1];
      var1.setlocal(5, var4);
      var4 = null;
      var4 = var11[2];
      var1.setlocal(6, var4);
      var4 = null;
      var4 = var11[3];
      var1.setlocal(7, var4);
      var4 = null;
      var4 = var11[4];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(181);
      PyObject var12 = imp.importOne("errno", var1, -1);
      var1.setlocal(9, var12);
      var3 = null;
      var1.setline(183);
      var12 = var1.getlocal(2);
      PyObject var10000 = var12._ge(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(184);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("moving %s -> %s"), (PyObject)var1.getlocal(0), (PyObject)var1.getlocal(1));
      }

      var1.setline(186);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(187);
         var12 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var12;
      } else {
         var1.setline(189);
         if (var1.getlocal(5).__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
            var1.setline(190);
            throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("can't move '%s': not a regular file")._mod(var1.getlocal(0))));
         } else {
            var1.setline(192);
            if (var1.getlocal(6).__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(193);
               var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(7).__call__(var2, var1.getlocal(0)));
               var1.setlocal(1, var4);
               var4 = null;
            } else {
               var1.setline(194);
               if (var1.getlocal(4).__call__(var2, var1.getlocal(1)).__nonzero__()) {
                  var1.setline(195);
                  throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("can't move '%s': destination '%s' already exists")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
               }
            }

            var1.setline(199);
            if (var1.getlocal(6).__call__(var2, var1.getlocal(8).__call__(var2, var1.getlocal(1))).__not__().__nonzero__()) {
               var1.setline(200);
               throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("can't move '%s': destination '%s' not a valid path")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
            } else {
               var1.setline(204);
               PyInteger var15 = Py.newInteger(0);
               var1.setlocal(10, var15);
               var4 = null;

               PyObject var5;
               PyObject[] var6;
               PyObject var7;
               PyException var17;
               try {
                  var1.setline(206);
                  var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               } catch (Throwable var10) {
                  var17 = Py.setException(var10, var1);
                  if (!var17.match(var1.getglobal("os").__getattr__("error"))) {
                     throw var17;
                  }

                  var5 = var17.value;
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(11, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(12, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(208);
                  var5 = var1.getlocal(11);
                  var10000 = var5._eq(var1.getlocal(9).__getattr__("EXDEV"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(211);
                     throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("couldn't move '%s' to '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(12)}))));
                  }

                  var1.setline(209);
                  PyInteger var13 = Py.newInteger(1);
                  var1.setlocal(10, var13);
                  var5 = null;
               }

               var1.setline(214);
               if (var1.getlocal(10).__nonzero__()) {
                  var1.setline(215);
                  var10000 = var1.getglobal("copy_file");
                  PyObject[] var18 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)};
                  String[] var14 = new String[]{"verbose"};
                  var10000.__call__(var2, var18, var14);
                  var4 = null;

                  try {
                     var1.setline(217);
                     var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(0));
                  } catch (Throwable var9) {
                     var17 = Py.setException(var9, var1);
                     if (var17.match(var1.getglobal("os").__getattr__("error"))) {
                        var5 = var17.value;
                        var6 = Py.unpackSequence(var5, 2);
                        var7 = var6[0];
                        var1.setlocal(11, var7);
                        var7 = null;
                        var7 = var6[1];
                        var1.setlocal(12, var7);
                        var7 = null;
                        var5 = null;

                        try {
                           var1.setline(220);
                           var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(1));
                        } catch (Throwable var8) {
                           PyException var16 = Py.setException(var8, var1);
                           if (!var16.match(var1.getglobal("os").__getattr__("error"))) {
                              throw var16;
                           }

                           var1.setline(222);
                        }

                        var1.setline(223);
                        throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("couldn't move '%s' to '%s' by copy/delete: ")._add(PyString.fromInterned("delete '%s' failed: %s"))._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(0), var1.getlocal(12)}))));
                     }

                     throw var17;
                  }
               }

               var1.setline(227);
               var12 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var12;
            }
         }
      }
   }

   public PyObject write_file$4(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      PyString.fromInterned("Create a file with the specified name and write 'contents' (a\n    sequence of strings without line terminators) to it.\n    ");
      var1.setline(234);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(236);
         PyObject var4 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(236);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(3, var5);
            var1.setline(237);
            var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("\n")));
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(239);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(239);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public file_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"src", "dst", "buffer_size", "fsrc", "fdst", "errno", "errstr", "buf"};
      _copy_file_contents$1 = Py.newCode(3, var2, var1, "_copy_file_contents", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "preserve_mode", "preserve_times", "update", "link", "verbose", "dry_run", "newer", "ST_ATIME", "ST_MTIME", "ST_MODE", "S_IMODE", "dir", "action", "st"};
      copy_file$2 = Py.newCode(8, var2, var1, "copy_file", 71, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "verbose", "dry_run", "exists", "isfile", "isdir", "basename", "dirname", "errno", "copy_it", "num", "msg"};
      move_file$3 = Py.newCode(4, var2, var1, "move_file", 170, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "contents", "f", "line"};
      write_file$4 = Py.newCode(2, var2, var1, "write_file", 230, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new file_util$py("distutils/file_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(file_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._copy_file_contents$1(var2, var3);
         case 2:
            return this.copy_file$2(var2, var3);
         case 3:
            return this.move_file$3(var2, var3);
         case 4:
            return this.write_file$4(var2, var3);
         default:
            return null;
      }
   }
}
