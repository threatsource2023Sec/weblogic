package lib2to3.fixes;

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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_itertools.py")
public class fix_itertools$py extends PyFunctionTable implements PyRunnable {
   static fix_itertools$py self;
   static final PyCode f$0;
   static final PyCode FixItertools$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Fixer for itertools.(imap|ifilter|izip) --> (map|filter|zip) and\n    itertools.ifilterfalse --> itertools.filterfalse (bugs 2360-2363)\n\n    imports from itertools are fixed in fix_itertools_import.py\n\n    If itertools is imported as something else (ie: import itertools as it;\n    it.izip(spam, eggs)) method calls will not get fixed.\n    "));
      var1.setline(8);
      PyString.fromInterned(" Fixer for itertools.(imap|ifilter|izip) --> (map|filter|zip) and\n    itertools.ifilterfalse --> itertools.filterfalse (bugs 2360-2363)\n\n    imports from itertools are fixed in fix_itertools_import.py\n\n    If itertools is imported as something else (ie: import itertools as it;\n    it.izip(spam, eggs)) method calls will not get fixed.\n    ");
      var1.setline(11);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"Name"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixItertools", var5, FixItertools$1);
      var1.setlocal("FixItertools", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixItertools$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(16);
      PyString var4 = PyString.fromInterned("('imap'|'ifilter'|'izip'|'izip_longest'|'ifilterfalse')");
      var1.setlocal("it_funcs", var4);
      var3 = null;
      var1.setline(17);
      var3 = PyString.fromInterned("\n              power< it='itertools'\n                  trailer<\n                     dot='.' func=%(it_funcs)s > trailer< '(' [any] ')' > >\n              |\n              power< func=%(it_funcs)s trailer< '(' [any] ')' > >\n              ")._mod(var1.getname("locals").__call__(var2));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(26);
      PyInteger var5 = Py.newInteger(6);
      var1.setlocal("run_order", var5);
      var3 = null;
      var1.setline(28);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$2, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("func")).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(31);
      PyString var6 = PyString.fromInterned("it");
      PyObject var10000 = var6._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(4).__getattr__("value");
         var10000 = var3._notin(new PyTuple(new PyObject[]{PyUnicode.fromInterned("ifilterfalse"), PyUnicode.fromInterned("izip_longest")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(33);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(PyString.fromInterned("dot")), var1.getlocal(2).__getitem__(PyString.fromInterned("it"))});
         PyObject[] var4 = Py.unpackSequence(var8, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(35);
         var3 = var1.getlocal(6).__getattr__("prefix");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(36);
         var1.getlocal(6).__getattr__("remove").__call__(var2);
         var1.setline(39);
         var1.getlocal(5).__getattr__("remove").__call__(var2);
         var1.setline(40);
         var1.getlocal(4).__getattr__("parent").__getattr__("replace").__call__(var2, var1.getlocal(4));
      }

      var1.setline(42);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4).__getattr__("prefix");
      }

      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(43);
      var10000 = var1.getlocal(4).__getattr__("replace");
      PyObject var10002 = var1.getglobal("Name");
      PyObject[] var9 = new PyObject[]{var1.getlocal(4).__getattr__("value").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(3)};
      String[] var7 = new String[]{"prefix"};
      var10002 = var10002.__call__(var2, var9, var7);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_itertools$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixItertools$1 = Py.newCode(0, var2, var1, "FixItertools", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "prefix", "func", "dot", "it"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_itertools$py("lib2to3/fixes/fix_itertools$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_itertools$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixItertools$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
