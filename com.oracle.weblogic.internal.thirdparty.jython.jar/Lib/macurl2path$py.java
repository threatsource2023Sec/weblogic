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
@Filename("macurl2path.py")
public class macurl2path$py extends PyFunctionTable implements PyRunnable {
   static macurl2path$py self;
   static final PyCode f$0;
   static final PyCode url2pathname$1;
   static final PyCode pathname2url$2;
   static final PyCode _pncomp2url$3;
   static final PyCode test$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Macintosh-specific module for conversion between pathnames and URLs.\n\nDo not import directly; use urllib instead."));
      var1.setline(3);
      PyString.fromInterned("Macintosh-specific module for conversion between pathnames and URLs.\n\nDo not import directly; use urllib instead.");
      var1.setline(5);
      PyObject var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(8);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("url2pathname"), PyString.fromInterned("pathname2url")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(10);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, url2pathname$1, PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n    to a file system path; not recommended for general use."));
      var1.setlocal("url2pathname", var6);
      var3 = null;
      var1.setline(52);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, pathname2url$2, PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n    of the 'file' scheme; not recommended for general use."));
      var1.setlocal("pathname2url", var6);
      var3 = null;
      var1.setline(75);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _pncomp2url$3, (PyObject)null);
      var1.setlocal("_pncomp2url", var6);
      var3 = null;
      var1.setline(79);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test$4, (PyObject)null);
      var1.setlocal("test", var6);
      var3 = null;
      var1.setline(96);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(97);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject url2pathname$1(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n    to a file system path; not recommended for general use.");
      var1.setline(16);
      PyObject var3 = var1.getglobal("urllib").__getattr__("splittype").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(17);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(PyString.fromInterned("file"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(18);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Cannot convert non-local URL to pathname"));
      } else {
         var1.setline(20);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("///"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(21);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(22);
            var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("//"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(23);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Cannot convert non-local URL to pathname"));
            }
         }

         var1.setline(24);
         var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(26);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(3, var4);
         var3 = null;

         while(true) {
            var1.setline(27);
            var3 = var1.getlocal(3);
            var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(38);
               if (var1.getlocal(2).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
                  var1.setline(40);
                  var3 = PyString.fromInterned(":").__getattr__("join").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  var1.setlocal(4, var3);
                  var3 = null;
               } else {
                  var1.setline(44);
                  var4 = Py.newInteger(0);
                  var1.setlocal(3, var4);
                  var3 = null;

                  while(true) {
                     var1.setline(45);
                     var3 = var1.getlocal(3);
                     var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                        var10000 = var3._eq(PyString.fromInterned(".."));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(48);
                        var3 = PyString.fromInterned(":")._add(PyString.fromInterned(":").__getattr__("join").__call__(var2, var1.getlocal(2)));
                        var1.setlocal(4, var3);
                        var3 = null;
                        break;
                     }

                     var1.setline(46);
                     PyString var5 = PyString.fromInterned("");
                     var1.getlocal(2).__setitem__((PyObject)var1.getlocal(3), var5);
                     var3 = null;
                     var1.setline(47);
                     var3 = var1.getlocal(3)._add(Py.newInteger(1));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
               }

               var1.setline(50);
               var3 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(28);
            var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
            var10000 = var3._eq(PyString.fromInterned("."));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(29);
               var1.getlocal(2).__delitem__(var1.getlocal(3));
            } else {
               var1.setline(30);
               var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
               var10000 = var3._eq(PyString.fromInterned(".."));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(3);
                  var10000 = var3._gt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                     var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("..")}));
                     var3 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(32);
                  var1.getlocal(2).__delslice__(var1.getlocal(3)._sub(Py.newInteger(1)), var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null);
                  var1.setline(33);
                  var3 = var1.getlocal(3)._sub(Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(34);
                  var3 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._gt(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(2).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1)));
                        var10000 = var3._ne(PyString.fromInterned(""));
                        var3 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(35);
                     var1.getlocal(2).__delitem__(var1.getlocal(3));
                  } else {
                     var1.setline(37);
                     var3 = var1.getlocal(3)._add(Py.newInteger(1));
                     var1.setlocal(3, var3);
                     var3 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject pathname2url$2(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n    of the 'file' scheme; not recommended for general use.");
      var1.setline(55);
      PyString var3 = PyString.fromInterned("/");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(56);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Cannot convert pathname containing slashes"));
      } else {
         var1.setline(57);
         PyObject var6 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(59);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(60);
            var1.getlocal(1).__delitem__((PyObject)Py.newInteger(0));
         }

         var1.setline(61);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
         var10000 = var6._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var1.getlocal(1).__delitem__((PyObject)Py.newInteger(-1));
         }

         var1.setline(64);
         var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

         while(true) {
            var1.setline(64);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(68);
               var6 = var1.getglobal("map").__call__(var2, var1.getglobal("_pncomp2url"), var1.getlocal(1));
               var1.setlocal(1, var6);
               var3 = null;
               var1.setline(70);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                  var1.setline(71);
                  var6 = PyString.fromInterned("/")._add(PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(1)));
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setline(73);
               var6 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var6;
            }

            var1.setlocal(2, var4);
            var1.setline(65);
            PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(2));
            var10000 = var5._eq(PyString.fromInterned(""));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(66);
               PyString var7 = PyString.fromInterned("..");
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var7);
               var5 = null;
            }
         }
      }
   }

   public PyObject _pncomp2url$3(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var10000 = var1.getglobal("urllib").__getattr__("quote");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(31), (PyObject)null), PyString.fromInterned("")};
      String[] var4 = new String[]{"safe"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(0, var5);
      var3 = null;
      var1.setline(77);
      var5 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject test$4(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = (new PyList(new PyObject[]{PyString.fromInterned("index.html"), PyString.fromInterned("bar/index.html"), PyString.fromInterned("/foo/bar/index.html"), PyString.fromInterned("/foo/bar/"), PyString.fromInterned("/")})).__iter__();

      while(true) {
         var1.setline(80);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(86);
            var3 = (new PyList(new PyObject[]{PyString.fromInterned("drive:"), PyString.fromInterned("drive:dir:"), PyString.fromInterned("drive:dir:file"), PyString.fromInterned("drive:file"), PyString.fromInterned("file"), PyString.fromInterned(":file"), PyString.fromInterned(":dir:"), PyString.fromInterned(":dir:file")})).__iter__();

            while(true) {
               var1.setline(86);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(94);
               Py.println(PyString.fromInterned("%r -> %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("pathname2url").__call__(var2, var1.getlocal(1))})));
            }
         }

         var1.setlocal(0, var4);
         var1.setline(85);
         Py.println(PyString.fromInterned("%r -> %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("url2pathname").__call__(var2, var1.getlocal(0))})));
      }
   }

   public macurl2path$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pathname", "tp", "components", "i", "rv"};
      url2pathname$1 = Py.newCode(1, var2, var1, "url2pathname", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pathname", "components", "i"};
      pathname2url$2 = Py.newCode(1, var2, var1, "pathname2url", 52, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"component"};
      _pncomp2url$3 = Py.newCode(1, var2, var1, "_pncomp2url", 75, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"url", "path"};
      test$4 = Py.newCode(0, var2, var1, "test", 79, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new macurl2path$py("macurl2path$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(macurl2path$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.url2pathname$1(var2, var3);
         case 2:
            return this.pathname2url$2(var2, var3);
         case 3:
            return this._pncomp2url$3(var2, var3);
         case 4:
            return this.test$4(var2, var3);
         default:
            return null;
      }
   }
}
