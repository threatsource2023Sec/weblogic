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
@Filename("compileall.py")
public class compileall$py extends PyFunctionTable implements PyRunnable {
   static compileall$py self;
   static final PyCode f$0;
   static final PyCode compile_dir$1;
   static final PyCode compile_file$2;
   static final PyCode compile_path$3;
   static final PyCode expand_args$4;
   static final PyCode main$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Module/script to byte-compile all .py files to .pyc (or .pyo) files.\n\nWhen called as a script with arguments, this compiles the directories\ngiven as arguments recursively; the -l option prevents it from\nrecursing into directories.\n\nWithout arguments, if compiles all modules on sys.path, without\nrecursing into subdirectories.  (Even though it should do so for\npackages -- for now, you'll have to deal with packages separately.)\n\nSee module py_compile for details of the actual byte-compilation.\n"));
      var1.setline(12);
      PyString.fromInterned("Module/script to byte-compile all .py files to .pyc (or .pyo) files.\n\nWhen called as a script with arguments, this compiles the directories\ngiven as arguments recursively; the -l option prevents it from\nrecursing into directories.\n\nWithout arguments, if compiles all modules on sys.path, without\nrecursing into subdirectories.  (Even though it should do so for\npackages -- for now, you'll have to deal with packages separately.)\n\nSee module py_compile for details of the actual byte-compilation.\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("py_compile", var1, -1);
      var1.setlocal("py_compile", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var3);
      var3 = null;
      var1.setline(19);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("compile_dir"), PyString.fromInterned("compile_file"), PyString.fromInterned("compile_path")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(21);
      PyObject[] var5 = new PyObject[]{Py.newInteger(10), var1.getname("None"), Py.newInteger(0), var1.getname("None"), Py.newInteger(0)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, compile_dir$1, PyString.fromInterned("Byte-compile all modules in the given directory tree.\n\n    Arguments (only dir is required):\n\n    dir:       the directory to byte-compile\n    maxlevels: maximum recursion level (default 10)\n    ddir:      the directory that will be prepended to the path to the\n               file as it is compiled into each byte-code file.\n    force:     if 1, force compilation, even if timestamps are up-to-date\n    quiet:     if 1, be quiet during compilation\n    "));
      var1.setlocal("compile_dir", var6);
      var3 = null;
      var1.setline(61);
      var5 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None"), Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, compile_file$2, PyString.fromInterned("Byte-compile one file.\n\n    Arguments (only fullname is required):\n\n    fullname:  the file to byte-compile\n    ddir:      if given, the directory name compiled in to the\n               byte-code file.\n    force:     if 1, force compilation, even if timestamps are up-to-date\n    quiet:     if 1, be quiet during compilation\n    "));
      var1.setlocal("compile_file", var6);
      var3 = null;
      var1.setline(113);
      var5 = new PyObject[]{Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, compile_path$3, PyString.fromInterned("Byte-compile all module on sys.path.\n\n    Arguments (all optional):\n\n    skip_curdir: if true, skip current directory (default true)\n    maxlevels:   max recursion level (default 0)\n    force: as for compile_dir() (default 0)\n    quiet: as for compile_dir() (default 0)\n    "));
      var1.setlocal("compile_path", var6);
      var3 = null;
      var1.setline(132);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, expand_args$4, PyString.fromInterned("read names in flist and append to args"));
      var1.setlocal("expand_args", var6);
      var3 = null;
      var1.setline(151);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, main$5, PyString.fromInterned("Script main program."));
      var1.setlocal("main", var6);
      var3 = null;
      var1.setline(225);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(226);
         var3 = var1.getname("int").__call__(var2, var1.getname("main").__call__(var2).__not__());
         var1.setlocal("exit_status", var3);
         var3 = null;
         var1.setline(227);
         var1.getname("sys").__getattr__("exit").__call__(var2, var1.getname("exit_status"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile_dir$1(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyString.fromInterned("Byte-compile all modules in the given directory tree.\n\n    Arguments (only dir is required):\n\n    dir:       the directory to byte-compile\n    maxlevels: maximum recursion level (default 10)\n    ddir:      the directory that will be prepended to the path to the\n               file as it is compiled into each byte-code file.\n    force:     if 1, force compilation, even if timestamps are up-to-date\n    quiet:     if 1, be quiet during compilation\n    ");
      var1.setline(34);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(35);
         Py.printComma(PyString.fromInterned("Listing"));
         Py.printComma(var1.getlocal(0));
         Py.println(PyString.fromInterned("..."));
      }

      PyException var3;
      PyObject var7;
      try {
         var1.setline(37);
         var7 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(6, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("os").__getattr__("error"))) {
            throw var3;
         }

         var1.setline(39);
         Py.printComma(PyString.fromInterned("Can't list"));
         Py.println(var1.getlocal(0));
         var1.setline(40);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var4);
         var4 = null;
      }

      var1.setline(41);
      var1.getlocal(6).__getattr__("sort").__call__(var2);
      var1.setline(42);
      PyInteger var8 = Py.newInteger(1);
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(43);
      var7 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(43);
         PyObject var9 = var7.__iternext__();
         if (var9 == null) {
            var1.setline(59);
            var7 = var1.getlocal(7);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(8, var9);
         var1.setline(44);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(8));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(45);
         var5 = var1.getlocal(2);
         PyObject var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(46);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(8));
            var1.setlocal(10, var5);
            var5 = null;
         } else {
            var1.setline(48);
            var5 = var1.getglobal("None");
            var1.setlocal(10, var5);
            var5 = null;
         }

         var1.setline(49);
         PyObject[] var10;
         PyInteger var11;
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(9)).__not__().__nonzero__()) {
            var1.setline(50);
            var10000 = var1.getglobal("compile_file");
            var10 = new PyObject[]{var1.getlocal(9), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
            if (var10000.__call__(var2, var10).__not__().__nonzero__()) {
               var1.setline(51);
               var11 = Py.newInteger(0);
               var1.setlocal(7, var11);
               var5 = null;
            }
         } else {
            var1.setline(52);
            var5 = var1.getlocal(1);
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(8);
               var10000 = var5._ne(var1.getglobal("os").__getattr__("curdir"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(8);
                  var10000 = var5._ne(var1.getglobal("os").__getattr__("pardir"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(9));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(9)).__not__();
                     }
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(56);
               var10000 = var1.getglobal("compile_dir");
               var10 = new PyObject[]{var1.getlocal(9), var1.getlocal(1)._sub(Py.newInteger(1)), var1.getlocal(10), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
               if (var10000.__call__(var2, var10).__not__().__nonzero__()) {
                  var1.setline(58);
                  var11 = Py.newInteger(0);
                  var1.setlocal(7, var11);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject compile_file$2(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(71);
      PyString.fromInterned("Byte-compile one file.\n\n    Arguments (only fullname is required):\n\n    fullname:  the file to byte-compile\n    ddir:      if given, the directory name compiled in to the\n               byte-code file.\n    force:     if 1, force compilation, even if timestamps are up-to-date\n    quiet:     if 1, be quiet during compilation\n    ");
      var1.setline(72);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(73);
      PyObject var10 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var10);
      var3 = null;
      var1.setline(74);
      var10 = var1.getlocal(1);
      PyObject var10000 = var10._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(75);
         var10 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(6));
         var1.setlocal(7, var10);
         var3 = null;
      } else {
         var1.setline(77);
         var10 = var1.getglobal("None");
         var1.setlocal(7, var10);
         var3 = null;
      }

      var1.setline(78);
      var10 = var1.getlocal(3);
      var10000 = var10._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(79);
         var10 = var1.getlocal(3).__getattr__("search").__call__(var2, var1.getlocal(0));
         var1.setlocal(8, var10);
         var3 = null;
         var1.setline(80);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(81);
            var10 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var10;
         }
      }

      var1.setline(82);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(83);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null), var1.getlocal(6).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null)});
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(10, var6);
         var6 = null;
         var4 = null;
         var1.setline(84);
         PyObject var11 = var1.getlocal(10);
         var10000 = var11._eq(PyString.fromInterned(".py"));
         var4 = null;
         if (var10000.__nonzero__()) {
            label97: {
               var1.setline(85);
               PyObject var12;
               PyException var13;
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  try {
                     var1.setline(87);
                     var11 = var1.getglobal("int").__call__(var2, var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0)).__getattr__("st_mtime"));
                     var1.setlocal(11, var11);
                     var4 = null;
                     var1.setline(88);
                     var11 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, PyString.fromInterned("<4sl"), (PyObject)var1.getglobal("imp").__getattr__("get_magic").__call__(var2), (PyObject)var1.getlocal(11));
                     var1.setlocal(12, var11);
                     var4 = null;
                     var1.setline(89);
                     var11 = var1.getglobal("imp").__getattr__("_makeCompiledFilename").__call__(var2, var1.getlocal(0));
                     var1.setlocal(13, var11);
                     var4 = null;
                     ContextManager var15;
                     var12 = (var15 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(13), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

                     label80: {
                        try {
                           var1.setlocal(14, var12);
                           var1.setline(91);
                           var12 = var1.getlocal(14).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
                           var1.setlocal(15, var12);
                           var5 = null;
                        } catch (Throwable var8) {
                           if (var15.__exit__(var2, Py.setException(var8, var1))) {
                              break label80;
                           }

                           throw (Throwable)Py.makeException();
                        }

                        var15.__exit__(var2, (PyException)null);
                     }

                     var1.setline(92);
                     var11 = var1.getlocal(12);
                     var10000 = var11._eq(var1.getlocal(15));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(93);
                        var10 = var1.getlocal(5);
                        var1.f_lasti = -1;
                        return var10;
                     }
                  } catch (Throwable var9) {
                     var13 = Py.setException(var9, var1);
                     if (!var13.match(var1.getglobal("IOError"))) {
                        throw var13;
                     }

                     var1.setline(95);
                  }
               }

               var1.setline(96);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(97);
                  Py.printComma(PyString.fromInterned("Compiling"));
                  Py.printComma(var1.getlocal(0));
                  Py.println(PyString.fromInterned("..."));
               }

               PyInteger var14;
               try {
                  var1.setline(99);
                  var11 = var1.getglobal("py_compile").__getattr__("compile").__call__(var2, var1.getlocal(0), var1.getglobal("None"), var1.getlocal(7), var1.getglobal("True"));
                  var1.setlocal(16, var11);
                  var4 = null;
               } catch (Throwable var7) {
                  var13 = Py.setException(var7, var1);
                  if (var13.match(var1.getglobal("py_compile").__getattr__("PyCompileError"))) {
                     var12 = var13.value;
                     var1.setlocal(17, var12);
                     var5 = null;
                     var1.setline(101);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(102);
                        Py.printComma(PyString.fromInterned("Compiling"));
                        Py.printComma(var1.getlocal(0));
                        Py.println(PyString.fromInterned("..."));
                     }

                     var1.setline(103);
                     Py.println(var1.getlocal(17).__getattr__("msg"));
                     var1.setline(104);
                     var14 = Py.newInteger(0);
                     var1.setlocal(5, var14);
                     var5 = null;
                  } else {
                     if (!var13.match(var1.getglobal("IOError"))) {
                        throw var13;
                     }

                     var12 = var13.value;
                     var1.setlocal(18, var12);
                     var5 = null;
                     var1.setline(106);
                     Py.printComma(PyString.fromInterned("Sorry"));
                     Py.println(var1.getlocal(18));
                     var1.setline(107);
                     var14 = Py.newInteger(0);
                     var1.setlocal(5, var14);
                     var5 = null;
                  }
                  break label97;
               }

               var1.setline(109);
               var12 = var1.getlocal(16);
               var10000 = var12._eq(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(110);
                  var14 = Py.newInteger(0);
                  var1.setlocal(5, var14);
                  var5 = null;
               }
            }
         }
      }

      var1.setline(111);
      var10 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject compile_path$3(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Byte-compile all module on sys.path.\n\n    Arguments (all optional):\n\n    skip_curdir: if true, skip current directory (default true)\n    maxlevels:   max recursion level (default 0)\n    force: as for compile_dir() (default 0)\n    quiet: as for compile_dir() (default 0)\n    ");
      var1.setline(123);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(124);
      PyObject var7 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(124);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(130);
            var7 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(5, var4);
         var1.setline(125);
         PyObject var10000 = var1.getlocal(5).__not__();
         PyObject var5;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(5);
            var10000 = var5._eq(var1.getglobal("os").__getattr__("curdir"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0);
         }

         if (var10000.__nonzero__()) {
            var1.setline(126);
            Py.println(PyString.fromInterned("Skipping current directory"));
         } else {
            var1.setline(128);
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("compile_dir");
               PyObject[] var8 = new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getglobal("None"), var1.getlocal(2), var1.getlocal(3)};
               String[] var6 = new String[]{"quiet"};
               var10000 = var10000.__call__(var2, var8, var6);
               var5 = null;
            }

            var5 = var10000;
            var1.setlocal(4, var5);
            var5 = null;
         }
      }
   }

   public PyObject expand_args$4(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("read names in flist and append to args");
      var1.setline(134);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(135);
      if (var1.getlocal(1).__nonzero__()) {
         try {
            var1.setline(137);
            var3 = var1.getlocal(1);
            PyObject var10000 = var3._eq(PyString.fromInterned("-"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(138);
               var3 = var1.getglobal("sys").__getattr__("stdin");
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(140);
               var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var3);
               var3 = null;
            }

            while(true) {
               var1.setline(141);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(142);
               var3 = var1.getlocal(3).__getattr__("readline").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(143);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  break;
               }

               var1.setline(145);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
            }
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("IOError"))) {
               var1.setline(147);
               Py.println(PyString.fromInterned("Error reading file list %s")._mod(var1.getlocal(1)));
               var1.setline(148);
               throw Py.makeException();
            }

            throw var5;
         }
      }

      var1.setline(149);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject main$5(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("Script main program.");
      var1.setline(153);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      PyException var10;
      try {
         var1.setline(155);
         var3 = var1.getlocal(0).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("lfqd:x:i:"));
         PyObject[] var11 = Py.unpackSequence(var3, 2);
         var5 = var11[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var9) {
         var10 = Py.setException(var9, var1);
         if (!var10.match(var1.getlocal(0).__getattr__("error"))) {
            throw var10;
         }

         var4 = var10.value;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(157);
         Py.println(var1.getlocal(3));
         var1.setline(158);
         Py.println(PyString.fromInterned("usage: python compileall.py [-l] [-f] [-q] [-d destdir] [-x regexp] [-i list] [directory|file ...]"));
         var1.setline(160);
         Py.println();
         var1.setline(161);
         Py.println(PyString.fromInterned("arguments: zero or more file and directory names to compile; if no arguments given, "));
         var1.setline(163);
         Py.println(PyString.fromInterned("           defaults to the equivalent of -l sys.path"));
         var1.setline(164);
         Py.println();
         var1.setline(165);
         Py.println(PyString.fromInterned("options:"));
         var1.setline(166);
         Py.println(PyString.fromInterned("-l: don't recurse into subdirectories"));
         var1.setline(167);
         Py.println(PyString.fromInterned("-f: force rebuild even if timestamps are up-to-date"));
         var1.setline(168);
         Py.println(PyString.fromInterned("-q: output only error messages"));
         var1.setline(169);
         Py.println(PyString.fromInterned("-d destdir: directory to prepend to file paths for use in compile-time tracebacks and in"));
         var1.setline(171);
         Py.println(PyString.fromInterned("            runtime tracebacks in cases where the source file is unavailable"));
         var1.setline(173);
         Py.println(PyString.fromInterned("-x regexp: skip files matching the regular expression regexp; the regexp is searched for"));
         var1.setline(175);
         Py.println(PyString.fromInterned("           in the full path of each file considered for compilation"));
         var1.setline(177);
         Py.println(PyString.fromInterned("-i file: add all the files and directories listed in file to the list considered for"));
         var1.setline(179);
         Py.println(PyString.fromInterned("         compilation; if \"-\", names are read from stdin"));
         var1.setline(181);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      }

      var1.setline(182);
      PyInteger var12 = Py.newInteger(10);
      var1.setlocal(4, var12);
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("None");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(184);
      var12 = Py.newInteger(0);
      var1.setlocal(6, var12);
      var3 = null;
      var1.setline(185);
      var12 = Py.newInteger(0);
      var1.setlocal(7, var12);
      var3 = null;
      var1.setline(186);
      var3 = var1.getglobal("None");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("None");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(188);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(188);
         var4 = var3.__iternext__();
         PyObject[] var14;
         PyInteger var15;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(197);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(198);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._ne(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0))).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(199);
                  Py.println(PyString.fromInterned("-d destdir require exactly one directory argument"));
                  var1.setline(200);
                  var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
               }
            }

            var1.setline(201);
            var12 = Py.newInteger(1);
            var1.setlocal(13, var12);
            var3 = null;

            PyInteger var13;
            try {
               var1.setline(203);
               var10000 = var1.getlocal(2);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(9);
               }

               if (var10000.__nonzero__()) {
                  try {
                     var1.setline(205);
                     if (var1.getlocal(9).__nonzero__()) {
                        var1.setline(206);
                        var3 = var1.getglobal("expand_args").__call__(var2, var1.getlocal(2), var1.getlocal(9));
                        var1.setlocal(2, var3);
                        var3 = null;
                     }
                  } catch (Throwable var7) {
                     var10 = Py.setException(var7, var1);
                     if (!var10.match(var1.getglobal("IOError"))) {
                        throw var10;
                     }

                     var1.setline(208);
                     var13 = Py.newInteger(0);
                     var1.setlocal(13, var13);
                     var4 = null;
                  }

                  var1.setline(209);
                  if (var1.getlocal(13).__nonzero__()) {
                     var1.setline(210);
                     var3 = var1.getlocal(2).__iter__();

                     while(true) {
                        var1.setline(210);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(14, var4);
                        var1.setline(211);
                        if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(14)).__nonzero__()) {
                           var1.setline(212);
                           var10000 = var1.getglobal("compile_dir");
                           var14 = new PyObject[]{var1.getlocal(14), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(8), var1.getlocal(7)};
                           if (var10000.__call__(var2, var14).__not__().__nonzero__()) {
                              var1.setline(214);
                              var15 = Py.newInteger(0);
                              var1.setlocal(13, var15);
                              var5 = null;
                           }
                        } else {
                           var1.setline(216);
                           var10000 = var1.getglobal("compile_file");
                           var14 = new PyObject[]{var1.getlocal(14), var1.getlocal(5), var1.getlocal(6), var1.getlocal(8), var1.getlocal(7)};
                           if (var10000.__call__(var2, var14).__not__().__nonzero__()) {
                              var1.setline(217);
                              var15 = Py.newInteger(0);
                              var1.setlocal(13, var15);
                              var5 = null;
                           }
                        }
                     }
                  }
               } else {
                  var1.setline(219);
                  var3 = var1.getglobal("compile_path").__call__(var2);
                  var1.setlocal(13, var3);
                  var3 = null;
               }
            } catch (Throwable var8) {
               var10 = Py.setException(var8, var1);
               if (!var10.match(var1.getglobal("KeyboardInterrupt"))) {
                  throw var10;
               }

               var1.setline(221);
               Py.println(PyString.fromInterned("\n[interrupted]"));
               var1.setline(222);
               var13 = Py.newInteger(0);
               var1.setlocal(13, var13);
               var4 = null;
            }

            var1.setline(223);
            var3 = var1.getlocal(13);
            var1.f_lasti = -1;
            return var3;
         }

         var14 = Py.unpackSequence(var4, 2);
         PyObject var6 = var14[0];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var14[1];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(189);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-l"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(189);
            var15 = Py.newInteger(0);
            var1.setlocal(4, var15);
            var5 = null;
         }

         var1.setline(190);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-d"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var5 = var1.getlocal(11);
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(191);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-f"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(191);
            var15 = Py.newInteger(1);
            var1.setlocal(6, var15);
            var5 = null;
         }

         var1.setline(192);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-q"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(192);
            var15 = Py.newInteger(1);
            var1.setlocal(7, var15);
            var5 = null;
         }

         var1.setline(193);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-x"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(194);
            var5 = imp.importOne("re", var1, -1);
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(195);
            var5 = var1.getlocal(12).__getattr__("compile").__call__(var2, var1.getlocal(11));
            var1.setlocal(8, var5);
            var5 = null;
         }

         var1.setline(196);
         var5 = var1.getlocal(10);
         var10000 = var5._eq(PyString.fromInterned("-i"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(196);
            var5 = var1.getlocal(11);
            var1.setlocal(9, var5);
            var5 = null;
         }
      }
   }

   public compileall$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"dir", "maxlevels", "ddir", "force", "rx", "quiet", "names", "success", "name", "fullname", "dfile"};
      compile_dir$1 = Py.newCode(6, var2, var1, "compile_dir", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fullname", "ddir", "force", "rx", "quiet", "success", "name", "dfile", "mo", "head", "tail", "mtime", "expect", "cfile", "chandle", "actual", "ok", "err", "e"};
      compile_file$2 = Py.newCode(5, var2, var1, "compile_file", 61, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"skip_curdir", "maxlevels", "force", "quiet", "success", "dir"};
      compile_path$3 = Py.newCode(4, var2, var1, "compile_path", 113, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "flist", "expanded", "fd", "line"};
      expand_args$4 = Py.newCode(2, var2, var1, "expand_args", 132, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"getopt", "opts", "args", "msg", "maxlevels", "ddir", "force", "quiet", "rx", "flist", "o", "a", "re", "success", "arg"};
      main$5 = Py.newCode(0, var2, var1, "main", 151, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new compileall$py("compileall$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(compileall$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.compile_dir$1(var2, var3);
         case 2:
            return this.compile_file$2(var2, var3);
         case 3:
            return this.compile_path$3(var2, var3);
         case 4:
            return this.expand_args$4(var2, var3);
         case 5:
            return this.main$5(var2, var3);
         default:
            return null;
      }
   }
}
