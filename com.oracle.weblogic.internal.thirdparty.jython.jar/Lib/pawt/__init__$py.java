import java.util.Arrays;
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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("pawt/__init__.py")
public class pawt$py extends PyFunctionTable implements PyRunnable {
   static pawt$py self;
   static final PyCode f$0;
   static final PyCode test$1;
   static final PyCode f$2;
   static final PyCode GridBag$3;
   static final PyCode __init__$4;
   static final PyCode addRow$5;
   static final PyCode add$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(2);
      String[] var5 = new String[]{"awt"};
      PyObject[] var6 = imp.importFrom("java", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("awt", var4);
      var4 = null;
      var1.setline(4);
      var6 = new PyObject[]{var1.getname("None"), PyString.fromInterned("AWT Tester")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test$1, (PyObject)null);
      var1.setlocal("test", var7);
      var3 = null;
      var1.setline(16);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("GridBag", var6, GridBag$3);
      var1.setlocal("GridBag", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$1(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var10000 = var1.getglobal("awt").__getattr__("Frame");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), null};
      var1.setline(5);
      PyObject[] var4 = Py.EmptyObjects;
      var3[1] = new PyFunction(var1.f_globals, var4, f$2);
      String[] var6 = new String[]{"windowClosing"};
      var10000 = var10000.__call__(var2, var3, var6);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(6);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("init")).__nonzero__()) {
         var1.setline(7);
         var1.getlocal(0).__getattr__("init").__call__(var2);
      }

      var1.setline(9);
      var1.getlocal(3).__getattr__("add").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Center"), (PyObject)var1.getlocal(0));
      var1.setline(10);
      var1.getlocal(3).__getattr__("pack").__call__(var2);
      var1.setline(11);
      var5 = var1.getlocal(1);
      var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(12);
         var1.getlocal(3).__getattr__("setSize").__call__(var2, var1.getglobal("apply").__call__(var2, var1.getglobal("awt").__getattr__("Dimension"), var1.getlocal(1)));
      }

      var1.setline(13);
      var1.getlocal(3).__getattr__("setVisible").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(14);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var3 = var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject GridBag$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addRow$5, (PyObject)null);
      var1.setlocal("addRow", var4);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$6, (PyObject)null);
      var1.setlocal("add", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("frame", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("awt").__getattr__("GridBagLayout").__call__(var2);
      var1.getlocal(0).__setattr__("gridbag", var3);
      var3 = null;
      var1.setline(20);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("defaults", var3);
      var3 = null;
      var1.setline(21);
      var1.getlocal(1).__getattr__("setLayout").__call__(var2, var1.getlocal(0).__getattr__("gridbag"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addRow$5(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyString var3 = PyString.fromInterned("REMAINDER");
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("gridwidth"), var3);
      var3 = null;
      var1.setline(25);
      var1.getglobal("apply").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("add"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1)})), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$6(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("awt").__getattr__("GridBagConstraints").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(0).__getattr__("defaults").__getattr__("items").__call__(var2)._add(var1.getlocal(2).__getattr__("items").__call__(var2)).__iter__();

      while(true) {
         var1.setline(30);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(34);
            var1.getlocal(0).__getattr__("gridbag").__getattr__("setConstraints").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setline(35);
            var1.getlocal(0).__getattr__("frame").__getattr__("add").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(31);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))).__nonzero__()) {
            var1.setline(32);
            PyObject var7 = var1.getglobal("getattr").__call__(var2, var1.getglobal("awt").__getattr__("GridBagConstraints"), var1.getlocal(5));
            var1.setlocal(5, var7);
            var5 = null;
         }

         var1.setline(33);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
      }
   }

   public pawt$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"panel", "size", "name", "f"};
      test$1 = Py.newCode(3, var2, var1, "test", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"event"};
      f$2 = Py.newCode(1, var2, var1, "<lambda>", 5, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GridBag$3 = Py.newCode(0, var2, var1, "GridBag", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "frame", "defaults"};
      __init__$4 = Py.newCode(3, var2, var1, "__init__", 17, false, true, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "widget", "kw"};
      addRow$5 = Py.newCode(3, var2, var1, "addRow", 23, false, true, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "widget", "kw", "constraints", "key", "value"};
      add$6 = Py.newCode(3, var2, var1, "add", 27, false, true, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pawt$py("pawt$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pawt$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.GridBag$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.addRow$5(var2, var3);
         case 6:
            return this.add$6(var2, var3);
         default:
            return null;
      }
   }
}
