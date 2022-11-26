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
@Filename("lib2to3/fixes/fix_raise.py")
public class fix_raise$py extends PyFunctionTable implements PyRunnable {
   static fix_raise$py self;
   static final PyCode f$0;
   static final PyCode FixRaise$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for 'raise E, V, T'\n\nraise         -> raise\nraise E       -> raise E\nraise E, V    -> raise E(V)\nraise E, V, T -> raise E(V).with_traceback(T)\nraise E, None, T -> raise E.with_traceback(T)\n\nraise (((E, E'), E''), E'''), V -> raise E(V)\nraise \"foo\", V, T               -> warns about string exceptions\n\n\nCAVEATS:\n1) \"raise E, V\" will be incorrectly translated if V is an exception\n   instance. The correct Python 3 idiom is\n\n        raise E from V\n\n   but since we can't detect instance-hood by syntax alone and since\n   any client code would have to be changed as well, we don't automate\n   this.\n"));
      var1.setline(22);
      PyString.fromInterned("Fixer for 'raise E, V, T'\n\nraise         -> raise\nraise E       -> raise E\nraise E, V    -> raise E(V)\nraise E, V, T -> raise E(V).with_traceback(T)\nraise E, None, T -> raise E.with_traceback(T)\n\nraise (((E, E'), E''), E'''), V -> raise E(V)\nraise \"foo\", V, T               -> warns about string exceptions\n\n\nCAVEATS:\n1) \"raise E, V\" will be incorrectly translated if V is an exception\n   instance. The correct Python 3 idiom is\n\n        raise E from V\n\n   but since we can't detect instance-hood by syntax alone and since\n   any client code would have to be changed as well, we don't automate\n   this.\n");
      var1.setline(26);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(27);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(28);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(29);
      var3 = new String[]{"Name", "Call", "Attr", "ArgList", "is_tuple"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("ArgList", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("is_tuple", var4);
      var4 = null;
      var1.setline(31);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixRaise", var5, FixRaise$1);
      var1.setlocal("FixRaise", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixRaise$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(33);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(34);
      PyString var4 = PyString.fromInterned("\n    raise_stmt< 'raise' exc=any [',' val=any [',' tb=any]] >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(38);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("exc")).__getattr__("clone").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getlocal(4).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("STRING"));
      var3 = null;
      PyString var9;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         var9 = PyString.fromInterned("Python 3 does not support string exceptions");
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(44);
         var1.getlocal(0).__getattr__("cannot_convert").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         var1.setline(45);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(53);
         if (var1.getglobal("is_tuple").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            while(true) {
               var1.setline(54);
               if (!var1.getglobal("is_tuple").__call__(var2, var1.getlocal(4)).__nonzero__()) {
                  var1.setline(58);
                  PyUnicode var7 = PyUnicode.fromInterned(" ");
                  var1.getlocal(4).__setattr__((String)"prefix", var7);
                  var3 = null;
                  break;
               }

               var1.setline(57);
               var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(1)).__getattr__("children").__getitem__(Py.newInteger(0)).__getattr__("clone").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
            }
         }

         var1.setline(60);
         var9 = PyString.fromInterned("val");
         var10000 = var9._notin(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var3 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("raise_stmt"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raise")), var1.getlocal(4)})));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(63);
            var3 = var1.getlocal(1).__getattr__("prefix");
            var1.getlocal(6).__setattr__("prefix", var3);
            var3 = null;
            var1.setline(64);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(66);
            PyObject var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("val")).__getattr__("clone").__call__(var2);
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(67);
            PyList var8;
            PyUnicode var10;
            if (var1.getglobal("is_tuple").__call__(var2, var1.getlocal(7)).__nonzero__()) {
               var1.setline(68);
               PyList var13 = new PyList();
               var4 = var13.__getattr__("append");
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(68);
               var4 = var1.getlocal(7).__getattr__("children").__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__iter__();

               while(true) {
                  var1.setline(68);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(68);
                     var1.dellocal(9);
                     var8 = var13;
                     var1.setlocal(8, var8);
                     var4 = null;
                     break;
                  }

                  var1.setlocal(10, var5);
                  var1.setline(68);
                  var1.getlocal(9).__call__(var2, var1.getlocal(10).__getattr__("clone").__call__(var2));
               }
            } else {
               var1.setline(70);
               var10 = PyUnicode.fromInterned("");
               var1.getlocal(7).__setattr__((String)"prefix", var10);
               var4 = null;
               var1.setline(71);
               var8 = new PyList(new PyObject[]{var1.getlocal(7)});
               var1.setlocal(8, var8);
               var4 = null;
            }

            var1.setline(73);
            PyString var11 = PyString.fromInterned("tb");
            var10000 = var11._in(var1.getlocal(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(74);
               var4 = var1.getlocal(2).__getitem__(PyString.fromInterned("tb")).__getattr__("clone").__call__(var2);
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(75);
               var10 = PyUnicode.fromInterned("");
               var1.getlocal(11).__setattr__((String)"prefix", var10);
               var4 = null;
               var1.setline(77);
               var4 = var1.getlocal(4);
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(81);
               var4 = var1.getlocal(7).__getattr__("type");
               var10000 = var4._ne(var1.getglobal("token").__getattr__("NAME"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(7).__getattr__("value");
                  var10000 = var4._ne(PyUnicode.fromInterned("None"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(82);
                  var4 = var1.getglobal("Call").__call__(var2, var1.getlocal(4), var1.getlocal(8));
                  var1.setlocal(12, var4);
                  var4 = null;
               }

               var1.setline(83);
               var4 = var1.getglobal("Attr").__call__(var2, var1.getlocal(12), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("with_traceback")))._add(new PyList(new PyObject[]{var1.getglobal("ArgList").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(11)})))}));
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(84);
               var4 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(3).__getattr__("simple_stmt"), (new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raise"))}))._add(var1.getlocal(13)));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(85);
               var4 = var1.getlocal(1).__getattr__("prefix");
               var1.getlocal(6).__setattr__("prefix", var4);
               var4 = null;
               var1.setline(86);
               var3 = var1.getlocal(6);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(88);
               var10000 = var1.getglobal("pytree").__getattr__("Node");
               PyObject[] var12 = new PyObject[]{var1.getlocal(3).__getattr__("raise_stmt"), new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("raise")), var1.getglobal("Call").__call__(var2, var1.getlocal(4), var1.getlocal(8))}), var1.getlocal(1).__getattr__("prefix")};
               String[] var6 = new String[]{"prefix"};
               var10000 = var10000.__call__(var2, var12, var6);
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public fix_raise$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixRaise$1 = Py.newCode(0, var2, var1, "FixRaise", 31, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "exc", "msg", "new", "val", "args", "_[68_20]", "c", "tb", "e", "with_tb"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_raise$py("lib2to3/fixes/fix_raise$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_raise$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixRaise$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
