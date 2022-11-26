import java.util.Arrays;
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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("webbrowser.py")
public class webbrowser$py extends PyFunctionTable implements PyRunnable {
   static webbrowser$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode AWTBrowser$2;
   static final PyCode open$3;
   static final PyCode open_new$4;
   static final PyCode open_new_tab$5;
   static final PyCode get$6;
   static final PyCode register$7;
   static final PyCode main$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Interfaces for launching and remotely controlling Web browsers."));
      var1.setline(2);
      PyString.fromInterned("Interfaces for launching and remotely controlling Web browsers.");
      var1.setline(5);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal("getopt", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"Desktop"};
      PyObject[] var6 = imp.importFrom("java.awt", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Desktop", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"URI"};
      var6 = imp.importFrom("java.net", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("URI", var4);
      var4 = null;
      var1.setline(10);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Error"), PyString.fromInterned("open"), PyString.fromInterned("open_new"), PyString.fromInterned("open_new_tab"), PyString.fromInterned("get"), PyString.fromInterned("register")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var6, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("AWTBrowser", var6, AWTBrowser$2);
      var1.setlocal("AWTBrowser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(34);
      var3 = var1.getname("AWTBrowser").__call__(var2);
      var1.setlocal("AWTBrowser", var3);
      var3 = null;
      var1.setline(37);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, get$6, PyString.fromInterned("Return a browser launcher instance appropriate for the environment."));
      var1.setlocal("get", var8);
      var3 = null;
      var1.setline(42);
      var6 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      var8 = new PyFunction(var1.f_globals, var6, register$7, PyString.fromInterned("Register a browser connector and, optionally, connection."));
      var1.setlocal("register", var8);
      var3 = null;
      var1.setline(46);
      var3 = var1.getname("AWTBrowser").__getattr__("open");
      var1.setlocal("open", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getname("AWTBrowser").__getattr__("open_new");
      var1.setlocal("open_new", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getname("AWTBrowser").__getattr__("open_new_tab");
      var1.setlocal("open_new_tab", var3);
      var3 = null;
      var1.setline(51);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, main$8, (PyObject)null);
      var1.setlocal("main", var8);
      var3 = null;
      var1.setline(77);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      return var1.getf_locals();
   }

   public PyObject AWTBrowser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, open$3, (PyObject)null);
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open_new$4, (PyObject)null);
      var1.setlocal("open_new", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open_new_tab$5, (PyObject)null);
      var1.setlocal("open_new_tab", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject open$3(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      if (var1.getglobal("Desktop").__getattr__("isDesktopSupported").__call__(var2).__not__().__nonzero__()) {
         var1.setline(18);
         throw Py.makeException(var1.getglobal("Error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("webbrowswer.py not supported in your environment")));
      } else {
         try {
            var1.setline(20);
            var1.getglobal("Desktop").__getattr__("getDesktop").__call__(var2).__getattr__("browse").__call__(var2, var1.getglobal("URI").__call__(var2, var1.getlocal(1)));
            var1.setline(21);
            PyObject var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            PyException var4 = Py.setException(var6, var1);
            if (var4.match(var1.getglobal("IOError"))) {
               PyObject var5 = var4.value;
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(23);
               throw Py.makeException(var1.getglobal("Error").__call__(var2, var1.getlocal(4)));
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject open_new$4(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getlocal(0).__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open_new_tab$5(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$6(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("Return a browser launcher instance appropriate for the environment.");
      var1.setline(39);
      PyObject var3 = var1.getglobal("AWTBrowser");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject register$7(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Register a browser connector and, optionally, connection.");
      var1.setline(44);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$8(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(53);
      var3 = PyString.fromInterned("Usage: %s [-n | -t] url\n    -n: open new window\n    -t: open new tab")._mod(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         var1.setline(57);
         var3 = var1.getlocal(0).__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("ntd"));
         PyObject[] var9 = Py.unpackSequence(var3, 2);
         var5 = var9[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var9[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getlocal(0).__getattr__("error"))) {
            throw var8;
         }

         var4 = var8.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(59);
         var4 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var4, var1.getlocal(4));
         var1.setline(60);
         var4 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var4, var1.getlocal(1));
         var1.setline(61);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(62);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(63);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(63);
         var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(66);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._ne(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(67);
               var3 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var3, var1.getlocal(1));
               var1.setline(68);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var1.setline(70);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(72);
            var1.getglobal("open").__call__(var2, var1.getlocal(8), var1.getlocal(5));
            var1.setline(73);
            Py.println(PyString.fromInterned("opened"));
            var1.setline(75);
            Py.println(PyString.fromInterned("\u0007"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var11 = Py.unpackSequence(var4, 2);
         PyObject var6 = var11[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(64);
         var5 = var1.getlocal(6);
         var10000 = var5._eq(PyString.fromInterned("-n"));
         var5 = null;
         PyInteger var12;
         if (var10000.__nonzero__()) {
            var1.setline(64);
            var12 = Py.newInteger(1);
            var1.setlocal(5, var12);
            var5 = null;
         } else {
            var1.setline(65);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("-t"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(65);
               var12 = Py.newInteger(2);
               var1.setlocal(5, var12);
               var5 = null;
            }
         }
      }
   }

   public webbrowser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      AWTBrowser$2 = Py.newCode(0, var2, var1, "AWTBrowser", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url", "new", "autoraise", "e"};
      open$3 = Py.newCode(4, var2, var1, "open", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url"};
      open_new$4 = Py.newCode(2, var2, var1, "open_new", 25, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url"};
      open_new_tab$5 = Py.newCode(2, var2, var1, "open_new_tab", 28, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"using"};
      get$6 = Py.newCode(1, var2, var1, "get", 37, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "klass", "instance", "update_tryorder"};
      register$7 = Py.newCode(4, var2, var1, "register", 42, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"getopt", "usage", "opts", "args", "msg", "new_win", "o", "a", "url"};
      main$8 = Py.newCode(0, var2, var1, "main", 51, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new webbrowser$py("webbrowser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(webbrowser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.AWTBrowser$2(var2, var3);
         case 3:
            return this.open$3(var2, var3);
         case 4:
            return this.open_new$4(var2, var3);
         case 5:
            return this.open_new_tab$5(var2, var3);
         case 6:
            return this.get$6(var2, var3);
         case 7:
            return this.register$7(var2, var3);
         case 8:
            return this.main$8(var2, var3);
         default:
            return null;
      }
   }
}
