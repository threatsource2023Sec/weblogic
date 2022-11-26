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
import org.python.core.PyInteger;
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
@Filename("types.py")
public class types$py extends PyFunctionTable implements PyRunnable {
   static types$py self;
   static final PyCode f$0;
   static final PyCode _f$1;
   static final PyCode f$2;
   static final PyCode _g$3;
   static final PyCode _C$4;
   static final PyCode _m$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Define names for all type symbols known in the standard interpreter.\n\nTypes that are part of optional modules (e.g. array) are not listed.\n"));
      var1.setline(4);
      PyString.fromInterned("Define names for all type symbols known in the standard interpreter.\n\nTypes that are part of optional modules (e.g. array) are not listed.\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(12);
      var3 = var1.getname("type").__call__(var2, var1.getname("None"));
      var1.setlocal("NoneType", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getname("type");
      var1.setlocal("TypeType", var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getname("object");
      var1.setlocal("ObjectType", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("int");
      var1.setlocal("IntType", var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getname("long");
      var1.setlocal("LongType", var3);
      var3 = null;
      var1.setline(18);
      var3 = var1.getname("float");
      var1.setlocal("FloatType", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getname("bool");
      var1.setlocal("BooleanType", var3);
      var3 = null;

      PyException var9;
      try {
         var1.setline(21);
         var3 = var1.getname("complex");
         var1.setlocal("ComplexType", var3);
         var3 = null;
      } catch (Throwable var7) {
         var9 = Py.setException(var7, var1);
         if (!var9.match(var1.getname("NameError"))) {
            throw var9;
         }

         var1.setline(23);
      }

      var1.setline(25);
      var3 = var1.getname("str");
      var1.setlocal("StringType", var3);
      var3 = null;

      PyTuple var4;
      try {
         var1.setline(31);
         var3 = var1.getname("unicode");
         var1.setlocal("UnicodeType", var3);
         var3 = null;
         var1.setline(32);
         PyTuple var10 = new PyTuple(new PyObject[]{var1.getname("StringType"), var1.getname("UnicodeType")});
         var1.setlocal("StringTypes", var10);
         var3 = null;
      } catch (Throwable var6) {
         var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("NameError"))) {
            throw var9;
         }

         var1.setline(34);
         var4 = new PyTuple(new PyObject[]{var1.getname("StringType")});
         var1.setlocal("StringTypes", var4);
         var4 = null;
      }

      var1.setline(39);
      var3 = var1.getname("tuple");
      var1.setlocal("TupleType", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getname("list");
      var1.setlocal("ListType", var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getname("dict");
      var1.setlocal("DictType", var3);
      var1.setlocal("DictionaryType", var3);
      var1.setline(43);
      PyObject[] var11 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var11, _f$1, (PyObject)null);
      var1.setlocal("_f", var12);
      var3 = null;
      var1.setline(44);
      var3 = var1.getname("type").__call__(var2, var1.getname("_f"));
      var1.setlocal("FunctionType", var3);
      var3 = null;
      var1.setline(45);
      PyObject var10000 = var1.getname("type");
      var1.setline(45);
      var11 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var11, f$2)));
      var1.setlocal("LambdaType", var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getname("type").__call__(var2, var1.getname("_f").__getattr__("func_code"));
      var1.setlocal("CodeType", var3);
      var3 = null;
      var1.setline(48);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _g$3, (PyObject)null);
      var1.setlocal("_g", var12);
      var3 = null;
      var1.setline(50);
      var3 = var1.getname("type").__call__(var2, var1.getname("_g").__call__(var2));
      var1.setlocal("GeneratorType", var3);
      var3 = null;
      var1.setline(52);
      var11 = Py.EmptyObjects;
      PyObject var8 = Py.makeClass("_C", var11, _C$4);
      var1.setlocal("_C", var8);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(54);
      var3 = var1.getname("type").__call__(var2, var1.getname("_C"));
      var1.setlocal("ClassType", var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getname("type").__call__(var2, var1.getname("_C").__getattr__("_m"));
      var1.setlocal("UnboundMethodType", var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getname("_C").__call__(var2);
      var1.setlocal("_x", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getname("type").__call__(var2, var1.getname("_x"));
      var1.setlocal("InstanceType", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getname("type").__call__(var2, var1.getname("_x").__getattr__("_m"));
      var1.setlocal("MethodType", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getname("type").__call__(var2, var1.getname("len"));
      var1.setlocal("BuiltinFunctionType", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getname("type").__call__(var2, (new PyList(Py.EmptyObjects)).__getattr__("append"));
      var1.setlocal("BuiltinMethodType", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getname("type").__call__(var2, var1.getname("sys").__getattr__("modules").__getitem__(var1.getname("__name__")));
      var1.setlocal("ModuleType", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getname("file");
      var1.setlocal("FileType", var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getname("xrange");
      var1.setlocal("XRangeType", var3);
      var3 = null;

      try {
         var1.setline(70);
         throw Py.makeException(var1.getname("TypeError"));
      } catch (Throwable var5) {
         var9 = Py.setException(var5, var1);
         if (var9.match(var1.getname("TypeError"))) {
            var1.setline(72);
            var8 = var1.getname("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2));
            var1.setlocal("tb", var8);
            var4 = null;
            var1.setline(73);
            var8 = var1.getname("type").__call__(var2, var1.getname("tb"));
            var1.setlocal("TracebackType", var8);
            var4 = null;
            var1.setline(74);
            var8 = var1.getname("type").__call__(var2, var1.getname("tb").__getattr__("tb_frame"));
            var1.setlocal("FrameType", var8);
            var4 = null;
            var1.setline(75);
            var1.dellocal("tb");
            var1.setline(77);
            var3 = var1.getname("slice");
            var1.setlocal("SliceType", var3);
            var3 = null;
            var1.setline(78);
            var3 = var1.getname("type").__call__(var2, var1.getname("Ellipsis"));
            var1.setlocal("EllipsisType", var3);
            var3 = null;
            var1.setline(80);
            var3 = var1.getname("type").__call__(var2, var1.getname("TypeType").__getattr__("__dict__"));
            var1.setlocal("DictProxyType", var3);
            var3 = null;
            var1.setline(81);
            var3 = var1.getname("type").__call__(var2, var1.getname("NotImplemented"));
            var1.setlocal("NotImplementedType", var3);
            var3 = null;
            var1.setline(84);
            var3 = var1.getname("type").__call__(var2, var1.getname("FunctionType").__getattr__("func_code"));
            var1.setlocal("GetSetDescriptorType", var3);
            var3 = null;
            var1.setline(85);
            var3 = var1.getname("type").__call__(var2, var1.getname("FunctionType").__getattr__("func_globals"));
            var1.setlocal("MemberDescriptorType", var3);
            var3 = null;
            var1.setline(87);
            var1.dellocal("sys");
            var1.dellocal("_f");
            var1.dellocal("_g");
            var1.dellocal("_C");
            var1.dellocal("_x");
            var1.f_lasti = -1;
            return Py.None;
         } else {
            throw var9;
         }
      }
   }

   public PyObject _f$1(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _g$3(PyFrame var1, ThreadState var2) {
      Object[] var3;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(49);
            var1.setline(49);
            PyInteger var5 = Py.newInteger(1);
            var1.f_lasti = 1;
            var3 = new Object[3];
            var1.f_savedlocals = var3;
            return var5;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               PyObject var4 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject _C$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(53);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _m$5, (PyObject)null);
      var1.setlocal("_m", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _m$5(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      var1.f_lasti = -1;
      return Py.None;
   }

   public types$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _f$1 = Py.newCode(0, var2, var1, "_f", 43, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$2 = Py.newCode(0, var2, var1, "<lambda>", 45, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _g$3 = Py.newCode(0, var2, var1, "_g", 48, false, false, self, 3, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      _C$4 = Py.newCode(0, var2, var1, "_C", 52, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _m$5 = Py.newCode(1, var2, var1, "_m", 53, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new types$py("types$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(types$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._f$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this._g$3(var2, var3);
         case 4:
            return this._C$4(var2, var3);
         case 5:
            return this._m$5(var2, var3);
         default:
            return null;
      }
   }
}
