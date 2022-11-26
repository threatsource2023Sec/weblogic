package lib2to3.fixes;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_types.py")
public class fix_types$py extends PyFunctionTable implements PyRunnable {
   static fix_types$py self;
   static final PyCode f$0;
   static final PyCode FixTypes$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for removing uses of the types module.\n\nThese work for only the known names in the types module.  The forms above\ncan include types. or not.  ie, It is assumed the module is imported either as:\n\n    import types\n    from types import ... # either * or specific types\n\nThe import statements are not modified.\n\nThere should be another fixer that handles at least the following constants:\n\n   type([]) -> list\n   type(()) -> tuple\n   type('') -> str\n\n"));
      var1.setline(20);
      PyString.fromInterned("Fixer for removing uses of the types module.\n\nThese work for only the known names in the types module.  The forms above\ncan include types. or not.  ie, It is assumed the module is imported either as:\n\n    import types\n    from types import ... # either * or specific types\n\nThe import statements are not modified.\n\nThere should be another fixer that handles at least the following constants:\n\n   type([]) -> list\n   type(()) -> tuple\n   type('') -> str\n\n");
      var1.setline(23);
      String[] var3 = new String[]{"token"};
      PyObject[] var5 = imp.importFrom("pgen2", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(24);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(25);
      var3 = new String[]{"Name"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var1.setline(27);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("BooleanType"), PyString.fromInterned("bool"), PyString.fromInterned("BufferType"), PyString.fromInterned("memoryview"), PyString.fromInterned("ClassType"), PyString.fromInterned("type"), PyString.fromInterned("ComplexType"), PyString.fromInterned("complex"), PyString.fromInterned("DictType"), PyString.fromInterned("dict"), PyString.fromInterned("DictionaryType"), PyString.fromInterned("dict"), PyString.fromInterned("EllipsisType"), PyString.fromInterned("type(Ellipsis)"), PyString.fromInterned("FloatType"), PyString.fromInterned("float"), PyString.fromInterned("IntType"), PyString.fromInterned("int"), PyString.fromInterned("ListType"), PyString.fromInterned("list"), PyString.fromInterned("LongType"), PyString.fromInterned("int"), PyString.fromInterned("ObjectType"), PyString.fromInterned("object"), PyString.fromInterned("NoneType"), PyString.fromInterned("type(None)"), PyString.fromInterned("NotImplementedType"), PyString.fromInterned("type(NotImplemented)"), PyString.fromInterned("SliceType"), PyString.fromInterned("slice"), PyString.fromInterned("StringType"), PyString.fromInterned("bytes"), PyString.fromInterned("StringTypes"), PyString.fromInterned("str"), PyString.fromInterned("TupleType"), PyString.fromInterned("tuple"), PyString.fromInterned("TypeType"), PyString.fromInterned("type"), PyString.fromInterned("UnicodeType"), PyString.fromInterned("str"), PyString.fromInterned("XRangeType"), PyString.fromInterned("range")});
      var1.setlocal("_TYPE_MAPPING", var6);
      var3 = null;
      var1.setline(52);
      PyList var10000 = new PyList();
      PyObject var7 = var10000.__getattr__("append");
      var1.setlocal("_[52_9]", var7);
      var3 = null;
      var1.setline(52);
      var7 = var1.getname("_TYPE_MAPPING").__iter__();

      while(true) {
         var1.setline(52);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(52);
            var1.dellocal("_[52_9]");
            PyList var8 = var10000;
            var1.setlocal("_pats", var8);
            var3 = null;
            var1.setline(54);
            var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
            var4 = Py.makeClass("FixTypes", var5, FixTypes$1);
            var1.setlocal("FixTypes", var4);
            var4 = null;
            Arrays.fill(var5, (Object)null);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("t", var4);
         var1.setline(52);
         var1.getname("_[52_9]").__call__(var2, PyString.fromInterned("power< 'types' trailer< '.' name='%s' > >")._mod(var1.getname("t")));
      }
   }

   public PyObject FixTypes$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(55);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(56);
      var3 = PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getname("_pats"));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(58);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, transform$2, (PyObject)null);
      var1.setlocal("transform", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("unicode").__call__(var2, var1.getglobal("_TYPE_MAPPING").__getattr__("get").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("name")).__getattr__("value")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(60);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(61);
         PyObject var10000 = var1.getglobal("Name");
         PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(1).__getattr__("prefix")};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(62);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_types$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixTypes$1 = Py.newCode(0, var2, var1, "FixTypes", 54, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new_value"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 58, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_types$py("lib2to3/fixes/fix_types$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_types$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixTypes$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
