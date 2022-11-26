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
@MTime(1498849384000L)
@Filename("marshal.py")
public class marshal$py extends PyFunctionTable implements PyRunnable {
   static marshal$py self;
   static final PyCode f$0;
   static final PyCode dump$1;
   static final PyCode load$2;
   static final PyCode dumps$3;
   static final PyCode loads$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Marshal module written in Python.\n\nThis doesn't marshal code objects, but supports everything else.\nPerformance or careful error checking is not an issue.\n\n"));
      var1.setline(6);
      PyString.fromInterned("Marshal module written in Python.\n\nThis doesn't marshal code objects, but supports everything else.\nPerformance or careful error checking is not an issue.\n\n");
      var1.setline(8);
      PyObject var3 = imp.importOne("cStringIO", var1, -1);
      var1.setlocal("cStringIO", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"Marshaller", "Unmarshaller"};
      PyObject[] var6 = imp.importFrom("_marshal", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Marshaller", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("Unmarshaller", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{Py.newInteger(2)};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, dump$1, (PyObject)null);
      var1.setlocal("dump", var7);
      var3 = null;
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("False")};
      var7 = new PyFunction(var1.f_globals, var6, load$2, (PyObject)null);
      var1.setlocal("load", var7);
      var3 = null;
      var1.setline(21);
      var6 = new PyObject[]{Py.newInteger(2)};
      var7 = new PyFunction(var1.f_globals, var6, dumps$3, (PyObject)null);
      var1.setlocal("dumps", var7);
      var3 = null;
      var1.setline(26);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, loads$4, (PyObject)null);
      var1.setlocal("loads", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$1(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      var1.getglobal("Marshaller").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getattr__("dump").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load$2(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getglobal("Unmarshaller").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(17);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(18);
         var1.getlocal(2).__getattr__("_debug").__call__(var2);
      }

      var1.setline(19);
      var3 = var1.getlocal(2).__getattr__("load").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dumps$3(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getglobal("cStringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(23);
      var1.getglobal("dump").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1));
      var1.setline(24);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject loads$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getglobal("cStringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("load").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public marshal$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x", "f", "version"};
      dump$1 = Py.newCode(3, var2, var1, "dump", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "debug", "u"};
      load$2 = Py.newCode(2, var2, var1, "load", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "version", "f"};
      dumps$3 = Py.newCode(2, var2, var1, "dumps", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "f"};
      loads$4 = Py.newCode(1, var2, var1, "loads", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new marshal$py("marshal$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(marshal$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.dump$1(var2, var3);
         case 2:
            return this.load$2(var2, var3);
         case 3:
            return this.dumps$3(var2, var3);
         case 4:
            return this.loads$4(var2, var3);
         default:
            return null;
      }
   }
}
