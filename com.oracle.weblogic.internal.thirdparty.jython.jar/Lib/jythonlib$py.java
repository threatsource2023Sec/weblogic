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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("jythonlib.py")
public class jythonlib$py extends PyFunctionTable implements PyRunnable {
   static jythonlib$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      imp.importAll("_jythonlib", var1, -1);
      var1.setline(2);
      PyObject var3 = imp.importOneAs("_bytecodetools", var1, -1);
      var1.setlocal("bytecodetools", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(9);
         var3 = imp.importOneAs("org.python.google.common", var1, -1);
         var1.setlocal("guava", var3);
         var3 = null;
         var1.setline(10);
         String[] var8 = new String[]{"MapMaker"};
         PyObject[] var10 = imp.importFrom("org.python.google.common.collect", var8, var1, -1);
         var4 = var10[0];
         var1.setlocal("MapMaker", var4);
         var4 = null;
         var1.setline(11);
         var8 = new String[]{"CacheBuilder", "CacheLoader"};
         var10 = imp.importFrom("org.python.google.common.cache", var8, var1, -1);
         var4 = var10[0];
         var1.setlocal("CacheBuilder", var4);
         var4 = null;
         var4 = var10[1];
         var1.setlocal("CacheLoader", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(14);
         var4 = imp.importOneAs("com.google.common", var1, -1);
         var1.setlocal("guava", var4);
         var4 = null;
         var1.setline(15);
         String[] var9 = new String[]{"MapMaker"};
         PyObject[] var11 = imp.importFrom("com.google.common.collect", var9, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("MapMaker", var5);
         var5 = null;
         var1.setline(16);
         var9 = new String[]{"CacheBuilder", "CacheLoader"};
         var11 = imp.importFrom("com.google.common.cache", var9, var1, -1);
         var5 = var11[0];
         var1.setlocal("CacheBuilder", var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal("CacheLoader", var5);
         var5 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public jythonlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new jythonlib$py("jythonlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(jythonlib$py.class);
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
