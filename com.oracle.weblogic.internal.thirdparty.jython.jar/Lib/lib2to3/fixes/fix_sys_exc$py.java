package lib2to3.fixes;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_sys_exc.py")
public class fix_sys_exc$py extends PyFunctionTable implements PyRunnable {
   static fix_sys_exc$py self;
   static final PyCode f$0;
   static final PyCode FixSysExc$1;
   static final PyCode f$2;
   static final PyCode transform$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for sys.exc_{type, value, traceback}\n\nsys.exc_type -> sys.exc_info()[0]\nsys.exc_value -> sys.exc_info()[1]\nsys.exc_traceback -> sys.exc_info()[2]\n"));
      var1.setline(6);
      PyString.fromInterned("Fixer for sys.exc_{type, value, traceback}\n\nsys.exc_type -> sys.exc_info()[0]\nsys.exc_value -> sys.exc_info()[1]\nsys.exc_traceback -> sys.exc_info()[2]\n");
      var1.setline(11);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"Attr", "Call", "Name", "Number", "Subscript", "Node", "syms"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Number", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("Subscript", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("Node", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixSysExc", var5, FixSysExc$1);
      var1.setlocal("FixSysExc", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixSysExc$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyList var3 = new PyList(new PyObject[]{PyUnicode.fromInterned("exc_type"), PyUnicode.fromInterned("exc_value"), PyUnicode.fromInterned("exc_traceback")});
      var1.setlocal("exc_info", var3);
      var3 = null;
      var1.setline(17);
      PyObject var5 = var1.getname("True");
      var1.setlocal("BM_compatible", var5);
      var3 = null;
      var1.setline(18);
      PyString var10000 = PyString.fromInterned("\n              power< 'sys' trailer< dot='.' attribute=(%s) > >\n              ");
      PyObject var10001 = PyString.fromInterned("|").__getattr__("join");
      var1.setline(20);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var6, f$2, (PyObject)null);
      PyObject var10003 = var4.__call__(var2, var1.getname("exc_info").__iter__());
      Arrays.fill(var6, (Object)null);
      var5 = var10000._mod(var10001.__call__(var2, var10003));
      var1.setlocal("PATTERN", var5);
      var3 = null;
      var1.setline(22);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$3, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(20);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(20);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(20);
         var1.setline(20);
         var6 = PyString.fromInterned("'%s'")._mod(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("attribute")).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getglobal("Number").__call__(var2, var1.getlocal(0).__getattr__("exc_info").__getattr__("index").__call__(var2, var1.getlocal(3).__getattr__("value")));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(26);
      PyObject var10000 = var1.getglobal("Call");
      PyObject[] var5 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("exc_info")), var1.getlocal(3).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("Attr").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("sys")), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("dot")).__getattr__("prefix");
      var1.getlocal(6).__getitem__(Py.newInteger(1)).__getattr__("children").__getitem__(Py.newInteger(0)).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(29);
      var1.getlocal(6).__getattr__("append").__call__(var2, var1.getglobal("Subscript").__call__(var2, var1.getlocal(4)));
      var1.setline(30);
      var10000 = var1.getglobal("Node");
      var5 = new PyObject[]{var1.getglobal("syms").__getattr__("power"), var1.getlocal(6), var1.getlocal(1).__getattr__("prefix")};
      var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public fix_sys_exc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixSysExc$1 = Py.newCode(0, var2, var1, "FixSysExc", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_(x)", "e"};
      f$2 = Py.newCode(1, var2, var1, "<genexpr>", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "node", "results", "sys_attr", "index", "call", "attr"};
      transform$3 = Py.newCode(3, var2, var1, "transform", 22, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_sys_exc$py("lib2to3/fixes/fix_sys_exc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_sys_exc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixSysExc$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         default:
            return null;
      }
   }
}
