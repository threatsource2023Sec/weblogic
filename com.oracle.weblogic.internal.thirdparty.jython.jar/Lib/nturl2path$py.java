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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("nturl2path.py")
public class nturl2path$py extends PyFunctionTable implements PyRunnable {
   static nturl2path$py self;
   static final PyCode f$0;
   static final PyCode url2pathname$1;
   static final PyCode pathname2url$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Convert a NT pathname to a file URL and vice versa."));
      var1.setline(1);
      PyString.fromInterned("Convert a NT pathname to a file URL and vice versa.");
      var1.setline(3);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, url2pathname$1, PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n    to a file system path; not recommended for general use."));
      var1.setlocal("url2pathname", var4);
      var3 = null;
      var1.setline(38);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pathname2url$2, PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n    of the 'file' scheme; not recommended for general use."));
      var1.setlocal("pathname2url", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject url2pathname$1(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyString.fromInterned("OS-specific conversion from a relative URL of the 'file' scheme\n    to a file system path; not recommended for general use.");
      var1.setline(10);
      PyObject var3 = imp.importOne("string", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(12);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("|"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(13);
      PyString var7 = PyString.fromInterned("|");
      PyObject var10000 = var7._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(15);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("////"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(19);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(20);
         var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(22);
         var3 = var1.getlocal(2).__getattr__("unquote").__call__(var2, PyString.fromInterned("\\").__getattr__("join").__call__(var2, var1.getlocal(3)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(23);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(24);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var4._ne(Py.newInteger(2));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(-1));
            var10000 = var4._notin(var1.getlocal(1).__getattr__("ascii_letters"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(25);
            var4 = PyString.fromInterned("Bad URL: ")._add(var1.getlocal(0));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(26);
            throw Py.makeException(var1.getglobal("IOError"), var1.getlocal(5));
         } else {
            var1.setline(27);
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(-1)).__getattr__("upper").__call__(var2);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(28);
            var4 = var1.getlocal(6)._add(PyString.fromInterned(":"));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(29);
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(30);
            var4 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(30);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(34);
                  var10000 = var1.getlocal(7).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(35);
                     var4 = var1.getlocal(7);
                     var4 = var4._iadd(PyString.fromInterned("\\"));
                     var1.setlocal(7, var4);
                  }

                  var1.setline(36);
                  var3 = var1.getlocal(7);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(31);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(32);
                  PyObject var6 = var1.getlocal(7)._add(PyString.fromInterned("\\"))._add(var1.getlocal(2).__getattr__("unquote").__call__(var2, var1.getlocal(4)));
                  var1.setlocal(7, var6);
                  var6 = null;
               }
            }
         }
      }
   }

   public PyObject pathname2url$2(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("OS-specific conversion from a file system path to a relative URL\n    of the 'file' scheme; not recommended for general use.");
      var1.setline(45);
      PyObject var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(46);
      PyString var7 = PyString.fromInterned(":");
      PyObject var10000 = var7._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(48);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("\\\\"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(52);
            var3 = PyString.fromInterned("\\\\")._add(var1.getlocal(0));
            var1.setlocal(0, var3);
            var3 = null;
         }

         var1.setline(53);
         var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(54);
         var3 = var1.getlocal(1).__getattr__("quote").__call__(var2, PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(2)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(55);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(56);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var4._ne(Py.newInteger(2));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
            var10000 = var4._gt(Py.newInteger(1));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(57);
            var4 = PyString.fromInterned("Bad path: ")._add(var1.getlocal(0));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(58);
            throw Py.makeException(var1.getglobal("IOError"), var1.getlocal(4));
         } else {
            var1.setline(60);
            var4 = var1.getlocal(1).__getattr__("quote").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("upper").__call__(var2));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(61);
            var4 = var1.getlocal(3).__getitem__(Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(62);
            var4 = PyString.fromInterned("///")._add(var1.getlocal(5))._add(PyString.fromInterned(":"));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(63);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(63);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(66);
                  var3 = var1.getlocal(6);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(3, var5);
               var1.setline(64);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(65);
                  PyObject var6 = var1.getlocal(6)._add(PyString.fromInterned("/"))._add(var1.getlocal(1).__getattr__("quote").__call__(var2, var1.getlocal(3)));
                  var1.setlocal(6, var6);
                  var6 = null;
               }
            }
         }
      }
   }

   public nturl2path$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"url", "string", "urllib", "components", "comp", "error", "drive", "path"};
      url2pathname$1 = Py.newCode(1, var2, var1, "url2pathname", 3, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "urllib", "components", "comp", "error", "drive", "path"};
      pathname2url$2 = Py.newCode(1, var2, var1, "pathname2url", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new nturl2path$py("nturl2path$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(nturl2path$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.url2pathname$1(var2, var3);
         case 2:
            return this.pathname2url$2(var2, var3);
         default:
            return null;
      }
   }
}
