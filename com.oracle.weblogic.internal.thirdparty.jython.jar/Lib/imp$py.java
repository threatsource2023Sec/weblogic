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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("imp.py")
public class imp$py extends PyFunctionTable implements PyRunnable {
   static imp$py self;
   static final PyCode f$0;
   static final PyCode NullImporter$1;
   static final PyCode __init__$2;
   static final PyCode find_module$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("os.path", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"C_BUILTIN", "C_EXTENSION", "IMP_HOOK", "PKG_DIRECTORY", "PY_COMPILED", "PY_FROZEN", "PY_SOURCE", "__doc__", "acquire_lock", "find_module", "getClass", "get_magic", "get_suffixes", "is_builtin", "is_frozen", "load_compiled", "load_dynamic", "load_module", "load_source", "lock_held", "new_module", "release_lock", "reload", "makeCompiledFilename"};
      PyObject[] var6 = imp.importFrom("_imp", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("C_BUILTIN", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("C_EXTENSION", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("IMP_HOOK", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("PKG_DIRECTORY", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("PY_COMPILED", var4);
      var4 = null;
      var4 = var6[5];
      var1.setlocal("PY_FROZEN", var4);
      var4 = null;
      var4 = var6[6];
      var1.setlocal("PY_SOURCE", var4);
      var4 = null;
      var4 = var6[7];
      var1.setlocal("__doc__", var4);
      var4 = null;
      var4 = var6[8];
      var1.setlocal("acquire_lock", var4);
      var4 = null;
      var4 = var6[9];
      var1.setlocal("find_module", var4);
      var4 = null;
      var4 = var6[10];
      var1.setlocal("getClass", var4);
      var4 = null;
      var4 = var6[11];
      var1.setlocal("get_magic", var4);
      var4 = null;
      var4 = var6[12];
      var1.setlocal("get_suffixes", var4);
      var4 = null;
      var4 = var6[13];
      var1.setlocal("is_builtin", var4);
      var4 = null;
      var4 = var6[14];
      var1.setlocal("is_frozen", var4);
      var4 = null;
      var4 = var6[15];
      var1.setlocal("load_compiled", var4);
      var4 = null;
      var4 = var6[16];
      var1.setlocal("load_dynamic", var4);
      var4 = null;
      var4 = var6[17];
      var1.setlocal("load_module", var4);
      var4 = null;
      var4 = var6[18];
      var1.setlocal("load_source", var4);
      var4 = null;
      var4 = var6[19];
      var1.setlocal("lock_held", var4);
      var4 = null;
      var4 = var6[20];
      var1.setlocal("new_module", var4);
      var4 = null;
      var4 = var6[21];
      var1.setlocal("release_lock", var4);
      var4 = null;
      var4 = var6[22];
      var1.setlocal("reload", var4);
      var4 = null;
      var4 = var6[23];
      var1.setlocal("_makeCompiledFilename", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("NullImporter", var6, NullImporter$1);
      var1.setlocal("NullImporter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NullImporter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(17);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, find_module$3, (PyObject)null);
      var1.setlocal("find_module", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(15);
         throw Py.makeException(var1.getglobal("ImportError").__call__(var2));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject find_module$3(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public imp$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NullImporter$1 = Py.newCode(0, var2, var1, "NullImporter", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "path"};
      find_module$3 = Py.newCode(3, var2, var1, "find_module", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new imp$py("imp$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(imp$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NullImporter$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.find_module$3(var2, var3);
         default:
            return null;
      }
   }
}
