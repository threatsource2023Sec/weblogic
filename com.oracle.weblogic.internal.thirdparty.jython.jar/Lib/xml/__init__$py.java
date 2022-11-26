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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/__init__.py")
public class xml$py extends PyFunctionTable implements PyRunnable {
   static xml$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Core XML support for Jython.\n\nThis package contains two sub-packages:\n\ndom -- The W3C Document Object Model.  This supports DOM Level 1 +\n       Namespaces.\n\nsax -- The Simple API for XML, developed by XML-Dev, led by David\n       Megginson and ported to Python by Lars Marius Garshol.  This\n       supports the SAX 2 API.\n\n"));
      var1.setline(12);
      PyString.fromInterned("Core XML support for Jython.\n\nThis package contains two sub-packages:\n\ndom -- The W3C Document Object Model.  This supports DOM Level 1 +\n       Namespaces.\n\nsax -- The Simple API for XML, developed by XML-Dev, led by David\n       Megginson and ported to Python by Lars Marius Garshol.  This\n       supports the SAX 2 API.\n\n");
      var1.setline(14);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("dom"), PyString.fromInterned("sax")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(17);
      PyTuple var8 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(8), Py.newInteger(5)});
      var1.setlocal("_MINIMUM_XMLPLUS_VERSION", var8);
      var3 = null;

      label37: {
         try {
            var1.setline(21);
            PyObject var11 = imp.importOne("_xmlplus", var1, -1);
            var1.setlocal("_xmlplus", var11);
            var3 = null;
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (var9.match(var1.getname("ImportError"))) {
               var1.setline(23);
               break label37;
            }

            throw var9;
         }

         PyException var4;
         try {
            var1.setline(26);
            PyObject var10 = var1.getname("_xmlplus").__getattr__("version_info");
            var1.setlocal("v", var10);
            var4 = null;
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (var4.match(var1.getname("AttributeError"))) {
               var1.setline(29);
               break label37;
            }

            throw var4;
         }

         var1.setline(31);
         PyObject var5 = var1.getname("v");
         PyObject var10000 = var5._ge(var1.getname("_MINIMUM_XMLPLUS_VERSION"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(32);
            var5 = imp.importOne("sys", var1, -1);
            var1.setlocal("sys", var5);
            var5 = null;
            var1.setline(33);
            var1.getname("_xmlplus").__getattr__("__path__").__getattr__("extend").__call__(var2, var1.getname("__path__"));
            var1.setline(34);
            var5 = var1.getname("_xmlplus");
            var1.getname("sys").__getattr__("modules").__setitem__(var1.getname("__name__"), var5);
            var5 = null;
         } else {
            var1.setline(36);
            var1.dellocal("v");
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public xml$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new xml$py("xml$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(xml$py.class);
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
