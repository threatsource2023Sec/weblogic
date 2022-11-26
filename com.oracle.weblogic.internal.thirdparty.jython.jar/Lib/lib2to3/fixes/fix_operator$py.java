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
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_operator.py")
public class fix_operator$py extends PyFunctionTable implements PyRunnable {
   static fix_operator$py self;
   static final PyCode f$0;
   static final PyCode invocation$1;
   static final PyCode dec$2;
   static final PyCode FixOperator$3;
   static final PyCode transform$4;
   static final PyCode _sequenceIncludes$5;
   static final PyCode _isCallable$6;
   static final PyCode _repeat$7;
   static final PyCode _irepeat$8;
   static final PyCode _isSequenceType$9;
   static final PyCode _isMappingType$10;
   static final PyCode _isNumberType$11;
   static final PyCode _handle_rename$12;
   static final PyCode _handle_type2abc$13;
   static final PyCode _check_method$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for operator functions.\n\noperator.isCallable(obj)       -> hasattr(obj, '__call__')\noperator.sequenceIncludes(obj) -> operator.contains(obj)\noperator.isSequenceType(obj)   -> isinstance(obj, collections.Sequence)\noperator.isMappingType(obj)    -> isinstance(obj, collections.Mapping)\noperator.isNumberType(obj)     -> isinstance(obj, numbers.Number)\noperator.repeat(obj, n)        -> operator.mul(obj, n)\noperator.irepeat(obj, n)       -> operator.imul(obj, n)\n"));
      var1.setline(10);
      PyString.fromInterned("Fixer for operator functions.\n\noperator.isCallable(obj)       -> hasattr(obj, '__call__')\noperator.sequenceIncludes(obj) -> operator.contains(obj)\noperator.isSequenceType(obj)   -> isinstance(obj, collections.Sequence)\noperator.isMappingType(obj)    -> isinstance(obj, collections.Mapping)\noperator.isNumberType(obj)     -> isinstance(obj, numbers.Number)\noperator.repeat(obj, n)        -> operator.mul(obj, n)\noperator.irepeat(obj, n)       -> operator.imul(obj, n)\n");
      var1.setline(13);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("lib2to3", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"Call", "Name", "String", "touch_import"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("String", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("touch_import", var4);
      var4 = null;
      var1.setline(17);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, invocation$1, (PyObject)null);
      var1.setlocal("invocation", var6);
      var3 = null;
      var1.setline(24);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixOperator", var5, FixOperator$3);
      var1.setlocal("FixOperator", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject invocation$1(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(18);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = dec$2;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(21);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject dec$2(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getderef(0);
      var1.getlocal(0).__setattr__("invocation", var3);
      var3 = null;
      var1.setline(20);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject FixOperator$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(26);
      PyString var5 = PyString.fromInterned("pre");
      var1.setlocal("order", var5);
      var3 = null;
      var1.setline(28);
      var5 = PyString.fromInterned("\n              method=('isCallable'|'sequenceIncludes'\n                     |'isSequenceType'|'isMappingType'|'isNumberType'\n                     |'repeat'|'irepeat')\n              ");
      var1.setlocal("methods", var5);
      var3 = null;
      var1.setline(33);
      var5 = PyString.fromInterned("'(' obj=any ')'");
      var1.setlocal("obj", var5);
      var3 = null;
      var1.setline(34);
      PyString var10000 = PyString.fromInterned("\n              power< module='operator'\n                trailer< '.' %(methods)s > trailer< %(obj)s > >\n              |\n              power< %(methods)s trailer< %(obj)s > >\n              ");
      PyObject var10001 = var1.getname("dict");
      PyObject[] var6 = new PyObject[]{var1.getname("methods"), var1.getname("obj")};
      String[] var4 = new String[]{"methods", "obj"};
      var10001 = var10001.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000._mod(var10001);
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(41);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$4, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      var1.setline(46);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _sequenceIncludes$5, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("operator.contains(%s)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_sequenceIncludes", var3);
      var3 = null;
      var1.setline(50);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _isCallable$6, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hasattr(%s, '__call__')")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_isCallable", var3);
      var3 = null;
      var1.setline(56);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _repeat$7, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("operator.mul(%s)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_repeat", var3);
      var3 = null;
      var1.setline(60);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _irepeat$8, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("operator.imul(%s)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_irepeat", var3);
      var3 = null;
      var1.setline(64);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _isSequenceType$9, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("isinstance(%s, collections.Sequence)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_isSequenceType", var3);
      var3 = null;
      var1.setline(68);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _isMappingType$10, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("isinstance(%s, collections.Mapping)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_isMappingType", var3);
      var3 = null;
      var1.setline(72);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _isNumberType$11, (PyObject)null);
      var3 = var1.getname("invocation").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("isinstance(%s, numbers.Number)")).__call__((ThreadState)var2, (PyObject)var7);
      var1.setlocal("_isNumberType", var3);
      var3 = null;
      var1.setline(76);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _handle_rename$12, (PyObject)null);
      var1.setlocal("_handle_rename", var7);
      var3 = null;
      var1.setline(81);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _handle_type2abc$13, (PyObject)null);
      var1.setlocal("_handle_type2abc", var7);
      var3 = null;
      var1.setline(87);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _check_method$14, (PyObject)null);
      var1.setlocal("_check_method", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$4(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(0).__getattr__("_check_method").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(44);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _sequenceIncludes$5(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_rename").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)PyUnicode.fromInterned("contains"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _isCallable$6(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("obj"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(53);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(3).__getattr__("clone").__call__(var2), var1.getglobal("String").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(", ")), var1.getglobal("String").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("'__call__'"))});
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(54);
      PyObject var10000 = var1.getglobal("Call");
      PyObject[] var6 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("hasattr")), var1.getlocal(4), var1.getlocal(1).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _repeat$7(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_rename").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)PyUnicode.fromInterned("mul"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _irepeat$8(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_rename").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)PyUnicode.fromInterned("imul"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _isSequenceType$9(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_type2abc").__call__(var2, var1.getlocal(1), var1.getlocal(2), PyUnicode.fromInterned("collections"), PyUnicode.fromInterned("Sequence"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _isMappingType$10(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_type2abc").__call__(var2, var1.getlocal(1), var1.getlocal(2), PyUnicode.fromInterned("collections"), PyUnicode.fromInterned("Mapping"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _isNumberType$11(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getlocal(0).__getattr__("_handle_type2abc").__call__(var2, var1.getlocal(1), var1.getlocal(2), PyUnicode.fromInterned("numbers"), PyUnicode.fromInterned("Number"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _handle_rename$12(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("method")).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("value", var3);
      var3 = null;
      var1.setline(79);
      var1.getlocal(4).__getattr__("changed").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handle_type2abc$13(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      var1.getglobal("touch_import").__call__(var2, var1.getglobal("None"), var1.getlocal(3), var1.getlocal(1));
      var1.setline(83);
      PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("obj"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(84);
      PyList var5 = new PyList(new PyObject[]{var1.getlocal(5).__getattr__("clone").__call__(var2), var1.getglobal("String").__call__(var2, PyUnicode.fromInterned(", ")._add(PyUnicode.fromInterned(".").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})))))});
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(85);
      PyObject var10000 = var1.getglobal("Call");
      PyObject[] var6 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("isinstance")), var1.getlocal(6), var1.getlocal(1).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _check_method$14(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_")._add(var1.getlocal(2).__getitem__(PyString.fromInterned("method")).__getitem__(Py.newInteger(0)).__getattr__("value").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"))));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(89);
      if (var1.getglobal("callable").__call__(var2, var1.getlocal(3)).__nonzero__()) {
         var1.setline(90);
         PyString var5 = PyString.fromInterned("module");
         PyObject var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(91);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(93);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("unicode").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("obj")))});
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(94);
         PyObject var6 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3).__getattr__("invocation"))._mod(var1.getlocal(4));
         var1.setlocal(5, var6);
         var4 = null;
         var1.setline(95);
         var1.getlocal(0).__getattr__("warning").__call__(var2, var1.getlocal(1), PyUnicode.fromInterned("You should use '%s' here.")._mod(var1.getlocal(5)));
      }

      var1.setline(96);
      var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public fix_operator$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "dec"};
      String[] var10001 = var2;
      fix_operator$py var10007 = self;
      var2 = new String[]{"s"};
      invocation$1 = Py.newCode(1, var10001, var1, "invocation", 17, false, false, var10007, 1, var2, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"s"};
      dec$2 = Py.newCode(1, var10001, var1, "dec", 18, false, false, var10007, 2, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      FixOperator$3 = Py.newCode(0, var2, var1, "FixOperator", 24, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "method"};
      transform$4 = Py.newCode(3, var2, var1, "transform", 41, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _sequenceIncludes$5 = Py.newCode(3, var2, var1, "_sequenceIncludes", 46, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "obj", "args"};
      _isCallable$6 = Py.newCode(3, var2, var1, "_isCallable", 50, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _repeat$7 = Py.newCode(3, var2, var1, "_repeat", 56, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _irepeat$8 = Py.newCode(3, var2, var1, "_irepeat", 60, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _isSequenceType$9 = Py.newCode(3, var2, var1, "_isSequenceType", 64, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _isMappingType$10 = Py.newCode(3, var2, var1, "_isMappingType", 68, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results"};
      _isNumberType$11 = Py.newCode(3, var2, var1, "_isNumberType", 72, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "name", "method"};
      _handle_rename$12 = Py.newCode(4, var2, var1, "_handle_rename", 76, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "module", "abc", "obj", "args"};
      _handle_type2abc$13 = Py.newCode(5, var2, var1, "_handle_type2abc", 81, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "method", "sub", "invocation_str"};
      _check_method$14 = Py.newCode(3, var2, var1, "_check_method", 87, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_operator$py("lib2to3/fixes/fix_operator$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_operator$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.invocation$1(var2, var3);
         case 2:
            return this.dec$2(var2, var3);
         case 3:
            return this.FixOperator$3(var2, var3);
         case 4:
            return this.transform$4(var2, var3);
         case 5:
            return this._sequenceIncludes$5(var2, var3);
         case 6:
            return this._isCallable$6(var2, var3);
         case 7:
            return this._repeat$7(var2, var3);
         case 8:
            return this._irepeat$8(var2, var3);
         case 9:
            return this._isSequenceType$9(var2, var3);
         case 10:
            return this._isMappingType$10(var2, var3);
         case 11:
            return this._isNumberType$11(var2, var3);
         case 12:
            return this._handle_rename$12(var2, var3);
         case 13:
            return this._handle_type2abc$13(var2, var3);
         case 14:
            return this._check_method$14(var2, var3);
         default:
            return null;
      }
   }
}
