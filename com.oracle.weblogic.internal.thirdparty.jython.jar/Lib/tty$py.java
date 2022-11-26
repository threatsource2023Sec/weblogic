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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("tty.py")
public class tty$py extends PyFunctionTable implements PyRunnable {
   static tty$py self;
   static final PyCode f$0;
   static final PyCode setraw$1;
   static final PyCode setcbreak$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Terminal utilities."));
      var1.setline(1);
      PyString.fromInterned("Terminal utilities.");
      var1.setline(5);
      imp.importAll("termios", var1, -1);
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("setraw"), PyString.fromInterned("setcbreak")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(10);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("IFLAG", var4);
      var3 = null;
      var1.setline(11);
      var4 = Py.newInteger(1);
      var1.setlocal("OFLAG", var4);
      var3 = null;
      var1.setline(12);
      var4 = Py.newInteger(2);
      var1.setlocal("CFLAG", var4);
      var3 = null;
      var1.setline(13);
      var4 = Py.newInteger(3);
      var1.setlocal("LFLAG", var4);
      var3 = null;
      var1.setline(14);
      var4 = Py.newInteger(4);
      var1.setlocal("ISPEED", var4);
      var3 = null;
      var1.setline(15);
      var4 = Py.newInteger(5);
      var1.setlocal("OSPEED", var4);
      var3 = null;
      var1.setline(16);
      var4 = Py.newInteger(6);
      var1.setlocal("CC", var4);
      var3 = null;
      var1.setline(18);
      PyObject[] var5 = new PyObject[]{var1.getname("TCSAFLUSH")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, setraw$1, PyString.fromInterned("Put terminal into a raw mode."));
      var1.setlocal("setraw", var6);
      var3 = null;
      var1.setline(30);
      var5 = new PyObject[]{var1.getname("TCSAFLUSH")};
      var6 = new PyFunction(var1.f_globals, var5, setcbreak$2, PyString.fromInterned("Put terminal into a cbreak mode."));
      var1.setlocal("setcbreak", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setraw$1(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyString.fromInterned("Put terminal into a raw mode.");
      var1.setline(20);
      PyObject var3 = var1.getglobal("tcgetattr").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("IFLAG"))._and(var1.getglobal("BRKINT")._or(var1.getglobal("ICRNL"))._or(var1.getglobal("INPCK"))._or(var1.getglobal("ISTRIP"))._or(var1.getglobal("IXON")).__invert__());
      var1.getlocal(2).__setitem__(var1.getglobal("IFLAG"), var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("OFLAG"))._and(var1.getglobal("OPOST").__invert__());
      var1.getlocal(2).__setitem__(var1.getglobal("OFLAG"), var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("CFLAG"))._and(var1.getglobal("CSIZE")._or(var1.getglobal("PARENB")).__invert__());
      var1.getlocal(2).__setitem__(var1.getglobal("CFLAG"), var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("CFLAG"))._or(var1.getglobal("CS8"));
      var1.getlocal(2).__setitem__(var1.getglobal("CFLAG"), var3);
      var3 = null;
      var1.setline(25);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("LFLAG"))._and(var1.getglobal("ECHO")._or(var1.getglobal("ICANON"))._or(var1.getglobal("IEXTEN"))._or(var1.getglobal("ISIG")).__invert__());
      var1.getlocal(2).__setitem__(var1.getglobal("LFLAG"), var3);
      var3 = null;
      var1.setline(26);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(2).__getitem__(var1.getglobal("CC")).__setitem__((PyObject)var1.getglobal("VMIN"), var4);
      var3 = null;
      var1.setline(27);
      var4 = Py.newInteger(0);
      var1.getlocal(2).__getitem__(var1.getglobal("CC")).__setitem__((PyObject)var1.getglobal("VTIME"), var4);
      var3 = null;
      var1.setline(28);
      var1.getglobal("tcsetattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setcbreak$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("Put terminal into a cbreak mode.");
      var1.setline(32);
      PyObject var3 = var1.getglobal("tcgetattr").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(2).__getitem__(var1.getglobal("LFLAG"))._and(var1.getglobal("ECHO")._or(var1.getglobal("ICANON")).__invert__());
      var1.getlocal(2).__setitem__(var1.getglobal("LFLAG"), var3);
      var3 = null;
      var1.setline(34);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(2).__getitem__(var1.getglobal("CC")).__setitem__((PyObject)var1.getglobal("VMIN"), var4);
      var3 = null;
      var1.setline(35);
      var4 = Py.newInteger(0);
      var1.getlocal(2).__getitem__(var1.getglobal("CC")).__setitem__((PyObject)var1.getglobal("VTIME"), var4);
      var3 = null;
      var1.setline(36);
      var1.getglobal("tcsetattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public tty$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fd", "when", "mode"};
      setraw$1 = Py.newCode(2, var2, var1, "setraw", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fd", "when", "mode"};
      setcbreak$2 = Py.newCode(2, var2, var1, "setcbreak", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tty$py("tty$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tty$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.setraw$1(var2, var3);
         case 2:
            return this.setcbreak$2(var2, var3);
         default:
            return null;
      }
   }
}
