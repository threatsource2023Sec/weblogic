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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_weakrefset.py")
public class _weakrefset$py extends PyFunctionTable implements PyRunnable {
   static _weakrefset$py self;
   static final PyCode f$0;
   static final PyCode WeakSet$1;
   static final PyCode __new__$2;
   static final PyCode _build$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      String[] var3 = new String[]{"WeakHashMap"};
      PyObject[] var5 = imp.importFrom("java.util", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("WeakHashMap", var4);
      var4 = null;
      var1.setline(6);
      var3 = new String[]{"newSetFromMap", "synchronizedMap"};
      var5 = imp.importFrom("java.util.Collections", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("newSetFromMap", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("synchronizedMap", var4);
      var4 = null;
      var1.setline(7);
      var3 = new String[]{"set_builder", "MapMaker"};
      var5 = imp.importFrom("jythonlib", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("set_builder", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("MapMaker", var4);
      var4 = null;
      var1.setline(9);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("WeakSet")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("set")};
      var4 = Py.makeClass("WeakSet", var5, WeakSet$1);
      var1.setlocal("WeakSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WeakSet$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _build$3, (PyObject)null);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(21);
      PyObject var5 = var1.getglobal("set_builder").__call__(var2, var1.getlocal(2), var1.getlocal(0)).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _build$3(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getglobal("newSetFromMap").__call__(var2, var1.getglobal("synchronizedMap").__call__(var2, var1.getglobal("WeakHashMap").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public _weakrefset$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WeakSet$1 = Py.newCode(0, var2, var1, "WeakSet", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "data", "_build"};
      __new__$2 = Py.newCode(2, var2, var1, "__new__", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _build$3 = Py.newCode(0, var2, var1, "_build", 15, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _weakrefset$py("_weakrefset$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_weakrefset$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.WeakSet$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this._build$3(var2, var3);
         default:
            return null;
      }
   }
}
