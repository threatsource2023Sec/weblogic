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
@Filename("commands.py")
public class commands$py extends PyFunctionTable implements PyRunnable {
   static commands$py self;
   static final PyCode f$0;
   static final PyCode getstatus$1;
   static final PyCode getoutput$2;
   static final PyCode getstatusoutput$3;
   static final PyCode mk2arg$4;
   static final PyCode mkarg$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Execute shell commands via os.popen() and return status, output.\n\nInterface summary:\n\n       import commands\n\n       outtext = commands.getoutput(cmd)\n       (exitstatus, outtext) = commands.getstatusoutput(cmd)\n       outtext = commands.getstatus(file)  # returns output of \"ls -ld file\"\n\nA trailing newline is removed from the output string.\n\nEncapsulates the basic operation:\n\n      pipe = os.popen('{ ' + cmd + '; } 2>&1', 'r')\n      text = pipe.read()\n      sts = pipe.close()\n\n [Note:  it would be nice to add functions to interpret the exit status.]\n"));
      var1.setline(20);
      PyString.fromInterned("Execute shell commands via os.popen() and return status, output.\n\nInterface summary:\n\n       import commands\n\n       outtext = commands.getoutput(cmd)\n       (exitstatus, outtext) = commands.getstatusoutput(cmd)\n       outtext = commands.getstatus(file)  # returns output of \"ls -ld file\"\n\nA trailing newline is removed from the output string.\n\nEncapsulates the basic operation:\n\n      pipe = os.popen('{ ' + cmd + '; } 2>&1', 'r')\n      text = pipe.read()\n      sts = pipe.close()\n\n [Note:  it would be nice to add functions to interpret the exit status.]\n");
      var1.setline(21);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(22);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the commands module has been removed in Python 3.0; use the subprocess module instead"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(24);
      var1.dellocal("warnpy3k");
      var1.setline(26);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("getstatusoutput"), PyString.fromInterned("getoutput"), PyString.fromInterned("getstatus")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, getstatus$1, PyString.fromInterned("Return output of \"ls -ld <file>\" in a string."));
      var1.setlocal("getstatus", var8);
      var3 = null;
      var1.setline(48);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, getoutput$2, PyString.fromInterned("Return output (stdout or stderr) of executing cmd in a shell."));
      var1.setlocal("getoutput", var8);
      var3 = null;
      var1.setline(56);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, getstatusoutput$3, PyString.fromInterned("Return (status, output) of executing cmd in a shell."));
      var1.setlocal("getstatusoutput", var8);
      var3 = null;
      var1.setline(69);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, mk2arg$4, (PyObject)null);
      var1.setlocal("mk2arg", var8);
      var3 = null;
      var1.setline(81);
      var5 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var5, mkarg$5, (PyObject)null);
      var1.setlocal("mkarg", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstatus$1(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyString.fromInterned("Return output of \"ls -ld <file>\" in a string.");
      var1.setline(39);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(40);
      var1.getlocal(1).__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("commands.getstatus() is deprecated"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(41);
      var3 = var1.getglobal("getoutput").__call__(var2, PyString.fromInterned("ls -ld")._add(var1.getglobal("mkarg").__call__(var2, var1.getlocal(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getoutput$2(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Return output (stdout or stderr) of executing cmd in a shell.");
      var1.setline(50);
      PyObject var3 = var1.getglobal("getstatusoutput").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getstatusoutput$3(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Return (status, output) of executing cmd in a shell.");
      var1.setline(58);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getlocal(1).__getattr__("popen").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{ ")._add(var1.getlocal(0))._add(PyString.fromInterned("; } 2>&1")), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(2).__getattr__("read").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(62);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(63);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("\n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(63);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(64);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject mk2arg$4(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("mkarg").__call__(var2, var1.getlocal(2).__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mkarg$5(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString var3 = PyString.fromInterned("'");
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(83);
         var7 = PyString.fromInterned(" '")._add(var1.getlocal(0))._add(PyString.fromInterned("'"));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(84);
         PyString var4 = PyString.fromInterned(" \"");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(85);
         PyObject var8 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(85);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(89);
               var8 = var1.getlocal(1)._add(PyString.fromInterned("\""));
               var1.setlocal(1, var8);
               var4 = null;
               var1.setline(90);
               var7 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setlocal(2, var5);
            var1.setline(86);
            PyObject var6 = var1.getlocal(2);
            var10000 = var6._in(PyString.fromInterned("\\$\"`"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(87);
               var6 = var1.getlocal(1)._add(PyString.fromInterned("\\"));
               var1.setlocal(1, var6);
               var6 = null;
            }

            var1.setline(88);
            var6 = var1.getlocal(1)._add(var1.getlocal(2));
            var1.setlocal(1, var6);
            var6 = null;
         }
      }
   }

   public commands$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file", "warnings"};
      getstatus$1 = Py.newCode(1, var2, var1, "getstatus", 37, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd"};
      getoutput$2 = Py.newCode(1, var2, var1, "getoutput", 48, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "os", "pipe", "text", "sts"};
      getstatusoutput$3 = Py.newCode(1, var2, var1, "getstatusoutput", 56, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"head", "x", "os"};
      mk2arg$4 = Py.newCode(2, var2, var1, "mk2arg", 69, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "s", "c"};
      mkarg$5 = Py.newCode(1, var2, var1, "mkarg", 81, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new commands$py("commands$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(commands$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.getstatus$1(var2, var3);
         case 2:
            return this.getoutput$2(var2, var3);
         case 3:
            return this.getstatusoutput$3(var2, var3);
         case 4:
            return this.mk2arg$4(var2, var3);
         case 5:
            return this.mkarg$5(var2, var3);
         default:
            return null;
      }
   }
}
