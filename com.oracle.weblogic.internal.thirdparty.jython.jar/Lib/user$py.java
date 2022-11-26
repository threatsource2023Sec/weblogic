import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("user.py")
public class user$py extends PyFunctionTable implements PyRunnable {
   static user$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Hook to allow user-specified customization code to run.\n\nAs a policy, Python doesn't run user-specified code on startup of\nPython programs (interactive sessions execute the script specified in\nthe PYTHONSTARTUP environment variable if it exists).\n\nHowever, some programs or sites may find it convenient to allow users\nto have a standard customization file, which gets run when a program\nrequests it.  This module implements such a mechanism.  A program\nthat wishes to use the mechanism must execute the statement\n\n    import user\n\nThe user module looks for a file .pythonrc.py in the user's home\ndirectory and if it can be opened, execfile()s it in its own global\nnamespace.  Errors during this phase are not caught; that's up to the\nprogram that imports the user module, if it wishes.\n\nThe user's .pythonrc.py could conceivably test for sys.version if it\nwishes to do different things depending on the Python version.\n\n"));
      var1.setline(22);
      PyString.fromInterned("Hook to allow user-specified customization code to run.\n\nAs a policy, Python doesn't run user-specified code on startup of\nPython programs (interactive sessions execute the script specified in\nthe PYTHONSTARTUP environment variable if it exists).\n\nHowever, some programs or sites may find it convenient to allow users\nto have a standard customization file, which gets run when a program\nrequests it.  This module implements such a mechanism.  A program\nthat wishes to use the mechanism must execute the statement\n\n    import user\n\nThe user module looks for a file .pythonrc.py in the user's home\ndirectory and if it can be opened, execfile()s it in its own global\nnamespace.  Errors during this phase are not caught; that's up to the\nprogram that imports the user module, if it wishes.\n\nThe user's .pythonrc.py could conceivably test for sys.version if it\nwishes to do different things depending on the Python version.\n\n");
      var1.setline(23);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var6 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(24);
      PyObject var10000 = var1.getname("warnpy3k");
      var6 = new PyObject[]{PyString.fromInterned("the user module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var8 = new String[]{"stacklevel"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(25);
      var1.dellocal("warnpy3k");
      var1.setline(27);
      PyObject var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var1.setline(29);
      var7 = var1.getname("os").__getattr__("curdir");
      var1.setlocal("home", var7);
      var3 = null;
      var1.setline(30);
      PyString var9 = PyString.fromInterned("HOME");
      var10000 = var9._in(var1.getname("os").__getattr__("environ"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         var7 = var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME"));
         var1.setlocal("home", var7);
         var3 = null;
      } else {
         var1.setline(32);
         var7 = var1.getname("os").__getattr__("name");
         var10000 = var7._eq(PyString.fromInterned("posix"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(33);
            var7 = var1.getname("os").__getattr__("path").__getattr__("expanduser").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~/"));
            var1.setlocal("home", var7);
            var3 = null;
         } else {
            var1.setline(34);
            var7 = var1.getname("os").__getattr__("name");
            var10000 = var7._eq(PyString.fromInterned("nt"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(35);
               var9 = PyString.fromInterned("HOMEPATH");
               var10000 = var9._in(var1.getname("os").__getattr__("environ"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(36);
                  var9 = PyString.fromInterned("HOMEDRIVE");
                  var10000 = var9._in(var1.getname("os").__getattr__("environ"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(37);
                     var7 = var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOMEDRIVE"))._add(var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOMEPATH")));
                     var1.setlocal("home", var7);
                     var3 = null;
                  } else {
                     var1.setline(39);
                     var7 = var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOMEPATH"));
                     var1.setlocal("home", var7);
                     var3 = null;
                  }
               }
            }
         }
      }

      var1.setline(41);
      var7 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getname("home"), (PyObject)PyString.fromInterned(".pythonrc.py"));
      var1.setlocal("pythonrc", var7);
      var3 = null;

      label36: {
         try {
            var1.setline(43);
            var7 = var1.getname("open").__call__(var2, var1.getname("pythonrc"));
            var1.setlocal("f", var7);
            var3 = null;
         } catch (Throwable var5) {
            PyException var10 = Py.setException(var5, var1);
            if (var10.match(var1.getname("IOError"))) {
               var1.setline(45);
               break label36;
            }

            throw var10;
         }

         var1.setline(47);
         var1.getname("f").__getattr__("close").__call__(var2);
         var1.setline(48);
         var1.getname("execfile").__call__(var2, var1.getname("pythonrc"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public user$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new user$py("user$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(user$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
