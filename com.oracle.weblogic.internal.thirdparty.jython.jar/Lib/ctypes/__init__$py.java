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
@Filename("ctypes/__init__.py")
public class ctypes$py extends PyFunctionTable implements PyRunnable {
   static ctypes$py self;
   static final PyCode f$0;
   static final PyCode _CTypeMetaClass$1;
   static final PyCode __new__$2;
   static final PyCode __mul__$3;
   static final PyCode _CData$4;
   static final PyCode in_dll$5;
   static final PyCode size$6;
   static final PyCode _ScalarCData$7;
   static final PyCode _ArrayCData$8;
   static final PyCode __len__$9;
   static final PyCode _StructLayoutBuilder$10;
   static final PyCode __init__$11;
   static final PyCode align$12;
   static final PyCode add_fields$13;
   static final PyCode add_field$14;
   static final PyCode build$15;
   static final PyCode _AggregateMetaClass$16;
   static final PyCode __new_aggregate__$17;
   static final PyCode get_fields$18;
   static final PyCode set_fields$19;
   static final PyCode _StructMetaClass$20;
   static final PyCode __new__$21;
   static final PyCode _UnionMetaClass$22;
   static final PyCode __new__$23;
   static final PyCode Structure$24;
   static final PyCode Union$25;
   static final PyCode sizeof$26;
   static final PyCode alignment$27;
   static final PyCode addressof$28;
   static final PyCode byref$29;
   static final PyCode pointer$30;
   static final PyCode POINTER$31;
   static final PyCode c_bool$32;
   static final PyCode c_byte$33;
   static final PyCode c_ubyte$34;
   static final PyCode c_short$35;
   static final PyCode c_ushort$36;
   static final PyCode c_int$37;
   static final PyCode c_uint$38;
   static final PyCode c_longlong$39;
   static final PyCode c_ulonglong$40;
   static final PyCode c_long$41;
   static final PyCode c_ulong$42;
   static final PyCode c_float$43;
   static final PyCode c_double$44;
   static final PyCode c_char_p$45;
   static final PyCode c_void_p$46;
   static final PyCode _Function$47;
   static final PyCode CDLL$48;
   static final PyCode __init__$49;
   static final PyCode __getattr__$50;
   static final PyCode __getitem__$51;
   static final PyCode LibraryLoader$52;
   static final PyCode __init__$53;
   static final PyCode __getattr__$54;
   static final PyCode __getitem__$55;
   static final PyCode LoadLibrary$56;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("jffi", var1, -1);
      var1.setlocal("jffi", var3);
      var3 = null;
      var1.setline(3);
      PyString var5 = PyString.fromInterned("0.0.1");
      var1.setlocal("__version__", var5);
      var3 = null;
      var1.setline(5);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("b"), var1.getname("jffi").__getattr__("Type").__getattr__("BYTE"), PyString.fromInterned("B"), var1.getname("jffi").__getattr__("Type").__getattr__("UBYTE"), PyString.fromInterned("h"), var1.getname("jffi").__getattr__("Type").__getattr__("SHORT"), PyString.fromInterned("H"), var1.getname("jffi").__getattr__("Type").__getattr__("USHORT"), PyString.fromInterned("i"), var1.getname("jffi").__getattr__("Type").__getattr__("INT"), PyString.fromInterned("I"), var1.getname("jffi").__getattr__("Type").__getattr__("UINT"), PyString.fromInterned("l"), var1.getname("jffi").__getattr__("Type").__getattr__("LONG"), PyString.fromInterned("L"), var1.getname("jffi").__getattr__("Type").__getattr__("ULONG"), PyString.fromInterned("q"), var1.getname("jffi").__getattr__("Type").__getattr__("LONGLONG"), PyString.fromInterned("Q"), var1.getname("jffi").__getattr__("Type").__getattr__("ULONGLONG"), PyString.fromInterned("f"), var1.getname("jffi").__getattr__("Type").__getattr__("FLOAT"), PyString.fromInterned("d"), var1.getname("jffi").__getattr__("Type").__getattr__("DOUBLE"), PyString.fromInterned("?"), var1.getname("jffi").__getattr__("Type").__getattr__("BOOL"), PyString.fromInterned("z"), var1.getname("jffi").__getattr__("Type").__getattr__("STRING"), PyString.fromInterned("P"), var1.getname("jffi").__getattr__("Type").__getattr__("POINTER")});
      var1.setlocal("_TypeMap", var6);
      var3 = null;
      var1.setline(23);
      PyObject[] var7 = new PyObject[]{var1.getname("type")};
      PyObject var4 = Py.makeClass("_CTypeMetaClass", var7, _CTypeMetaClass$1);
      var1.setlocal("_CTypeMetaClass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(41);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_CData", var7, _CData$4);
      var1.setlocal("_CData", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(50);
      var7 = new PyObject[]{var1.getname("jffi").__getattr__("ScalarCData"), var1.getname("_CData")};
      var4 = Py.makeClass("_ScalarCData", var7, _ScalarCData$7);
      var1.setlocal("_ScalarCData", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(54);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_ArrayCData", var7, _ArrayCData$8);
      var1.setlocal("_ArrayCData", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(58);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_StructLayoutBuilder", var7, _StructLayoutBuilder$10);
      var1.setlocal("_StructLayoutBuilder", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(91);
      var7 = new PyObject[]{var1.getname("type")};
      var4 = Py.makeClass("_AggregateMetaClass", var7, _AggregateMetaClass$16);
      var1.setlocal("_AggregateMetaClass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(125);
      var7 = new PyObject[]{var1.getname("_AggregateMetaClass")};
      var4 = Py.makeClass("_StructMetaClass", var7, _StructMetaClass$20);
      var1.setlocal("_StructMetaClass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(129);
      var7 = new PyObject[]{var1.getname("_AggregateMetaClass")};
      var4 = Py.makeClass("_UnionMetaClass", var7, _UnionMetaClass$22);
      var1.setlocal("_UnionMetaClass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(133);
      var7 = new PyObject[]{var1.getname("jffi").__getattr__("Structure"), var1.getname("_CData")};
      var4 = Py.makeClass("Structure", var7, Structure$24);
      var1.setlocal("Structure", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(136);
      var7 = new PyObject[]{var1.getname("jffi").__getattr__("Structure"), var1.getname("_CData")};
      var4 = Py.makeClass("Union", var7, Union$25);
      var1.setlocal("Union", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(139);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, sizeof$26, (PyObject)null);
      var1.setlocal("sizeof", var8);
      var3 = null;
      var1.setline(145);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, alignment$27, (PyObject)null);
      var1.setlocal("alignment", var8);
      var3 = null;
      var1.setline(148);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, addressof$28, (PyObject)null);
      var1.setlocal("addressof", var8);
      var3 = null;
      var1.setline(151);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, byref$29, (PyObject)null);
      var1.setlocal("byref", var8);
      var3 = null;
      var1.setline(154);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, pointer$30, (PyObject)null);
      var1.setlocal("pointer", var8);
      var3 = null;
      var1.setline(157);
      var3 = var1.getname("jffi").__getattr__("memmove");
      var1.setlocal("memmove", var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getname("jffi").__getattr__("memset");
      var1.setlocal("memset", var3);
      var3 = null;
      var1.setline(160);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_pointer_type_cache", var6);
      var3 = null;
      var1.setline(161);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, POINTER$31, (PyObject)null);
      var1.setlocal("POINTER", var8);
      var3 = null;
      var1.setline(181);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_bool", var7, c_bool$32);
      var1.setlocal("c_bool", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(185);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_byte", var7, c_byte$33);
      var1.setlocal("c_byte", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(189);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_ubyte", var7, c_ubyte$34);
      var1.setlocal("c_ubyte", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(193);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_short", var7, c_short$35);
      var1.setlocal("c_short", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(197);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_ushort", var7, c_ushort$36);
      var1.setlocal("c_ushort", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(201);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_int", var7, c_int$37);
      var1.setlocal("c_int", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(205);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_uint", var7, c_uint$38);
      var1.setlocal("c_uint", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(209);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_longlong", var7, c_longlong$39);
      var1.setlocal("c_longlong", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(213);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_ulonglong", var7, c_ulonglong$40);
      var1.setlocal("c_ulonglong", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(217);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_long", var7, c_long$41);
      var1.setlocal("c_long", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(221);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_ulong", var7, c_ulong$42);
      var1.setlocal("c_ulong", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(225);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_float", var7, c_float$43);
      var1.setlocal("c_float", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(229);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_double", var7, c_double$44);
      var1.setlocal("c_double", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(233);
      var3 = var1.getname("c_byte");
      var1.setlocal("c_int8", var3);
      var3 = null;
      var1.setline(234);
      var3 = var1.getname("c_ubyte");
      var1.setlocal("c_uint8", var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getname("c_short");
      var1.setlocal("c_int16", var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getname("c_ushort");
      var1.setlocal("c_uint16", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getname("c_int");
      var1.setlocal("c_int32", var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getname("c_uint");
      var1.setlocal("c_uint32", var3);
      var3 = null;
      var1.setline(239);
      var3 = var1.getname("c_longlong");
      var1.setlocal("c_int64", var3);
      var3 = null;
      var1.setline(240);
      var3 = var1.getname("c_ulonglong");
      var1.setlocal("c_uint64", var3);
      var3 = null;
      var1.setline(242);
      var3 = var1.getname("c_ulong");
      var1.setlocal("c_size_t", var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getname("c_long");
      var1.setlocal("c_ssize_t", var3);
      var3 = null;
      var1.setline(245);
      var7 = new PyObject[]{var1.getname("jffi").__getattr__("StringCData"), var1.getname("_CData")};
      var4 = Py.makeClass("c_char_p", var7, c_char_p$45);
      var1.setlocal("c_char_p", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(249);
      var7 = new PyObject[]{var1.getname("_ScalarCData")};
      var4 = Py.makeClass("c_void_p", var7, c_void_p$46);
      var1.setlocal("c_void_p", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(253);
      var7 = new PyObject[]{var1.getname("jffi").__getattr__("Function")};
      var4 = Py.makeClass("_Function", var7, _Function$47);
      var1.setlocal("_Function", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(258);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("CDLL", var7, CDLL$48);
      var1.setlocal("CDLL", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(274);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LibraryLoader", var7, LibraryLoader$52);
      var1.setlocal("LibraryLoader", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(291);
      var3 = var1.getname("LibraryLoader").__call__(var2, var1.getname("CDLL"));
      var1.setlocal("cdll", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _CTypeMetaClass$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __mul__$3, (PyObject)null);
      var1.setlocal("__mul__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("type").__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __mul__$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("_jffi_type"), var1.getglobal("jffi").__getattr__("Type").__getattr__("Array").__call__(var2, var1.getlocal(0), var1.getlocal(1))});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(32);
      PyObject var4 = imp.importOne("inspect", var1, -1);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(33);
      var4 = var1.getlocal(3).__getattr__("getmodule").__call__(var2, var1.getlocal(3).__getattr__("stack").__call__(var2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0)));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(34);
      var4 = var1.getlocal(4);
      PyObject var10000 = var4._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(35);
         PyString var5 = PyString.fromInterned("__main__");
         var1.setlocal(5, var5);
         var3 = null;
      } else {
         var1.setline(37);
         var4 = var1.getlocal(4).__getattr__("__name__");
         var1.setlocal(5, var4);
         var3 = null;
      }

      var1.setline(38);
      var4 = var1.getlocal(5);
      var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("__module__"), var4);
      var3 = null;
      var1.setline(39);
      var4 = var1.getglobal("type").__call__((ThreadState)var2, PyString.fromInterned("%s_Array_%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__name__"), var1.getlocal(1)})), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("jffi").__getattr__("ArrayCData"), var1.getglobal("_ArrayCData"), var1.getglobal("_CData")})), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _CData$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, in_dll$5, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("in_dll", var5);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, size$6, (PyObject)null);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("size", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject in_dll$5(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(0).__getattr__("from_address").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject size$6(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(0).__getattr__("_jffi_type").__getattr__("size").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ScalarCData$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(51);
      PyObject var3 = var1.getname("_CTypeMetaClass");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _ArrayCData$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(55);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __len__$9, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __len__$9(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0).__getattr__("_jffi_type").__getattr__("length");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _StructLayoutBuilder$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, align$12, (PyObject)null);
      var1.setlocal("align", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_fields$13, (PyObject)null);
      var1.setlocal("add_fields", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_field$14, (PyObject)null);
      var1.setlocal("add_field", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, build$15, (PyObject)null);
      var1.setlocal("build", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"size", var3);
      var3 = null;
      var1.setline(61);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"offset", var3);
      var3 = null;
      var1.setline(62);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"fields", var4);
      var3 = null;
      var1.setline(63);
      PyObject var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("union", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject align$12(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var3 = var1.getlocal(2)._add(var1.getlocal(1)._sub(Py.newInteger(1))._and(var1.getlocal(2)._sub(Py.newInteger(1)).__invert__()));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_fields$13(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(69);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(71);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(70);
         var1.getlocal(0).__getattr__("add_field").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject add_field$14(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getglobal("_ScalarCData")).__not__().__nonzero__()) {
         var1.setline(75);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("non-scalar fields not supported")));
      } else {
         var1.setline(77);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(78);
            throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("structs with bitfields not supported")));
         } else {
            var1.setline(80);
            var3 = var1.getlocal(0).__getattr__("align").__call__(var2, var1.getlocal(0).__getattr__("offset"), var1.getglobal("alignment").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
            var1.getlocal(0).__setattr__("offset", var3);
            var3 = null;
            var1.setline(81);
            var1.getlocal(0).__getattr__("fields").__getattr__("append").__call__(var2, var1.getglobal("jffi").__getattr__("StructLayout").__getattr__("ScalarField").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(0).__getattr__("offset")));
            var1.setline(82);
            if (var1.getlocal(0).__getattr__("union").__not__().__nonzero__()) {
               var1.setline(83);
               var10000 = var1.getlocal(0);
               String var6 = "offset";
               PyObject var4 = var10000;
               PyObject var5 = var4.__getattr__(var6);
               var5 = var5._iadd(var1.getglobal("sizeof").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
               var4.__setattr__(var6, var5);
            }

            var1.setline(84);
            var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("offset"), var1.getglobal("sizeof").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))));
            var1.getlocal(0).__setattr__("size", var3);
            var3 = null;
            var1.setline(86);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject build$15(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var10000 = var1.getglobal("jffi").__getattr__("StructLayout");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("fields"), var1.getlocal(0).__getattr__("union")};
      String[] var4 = new String[]{"fields", "union"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _AggregateMetaClass$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(92);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new_aggregate__$17, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("__new_aggregate__", var5);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_fields$18, (PyObject)null);
      var1.setlocal("get_fields", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_fields$19, (PyObject)null);
      var1.setlocal("set_fields", var4);
      var3 = null;
      var1.setline(120);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_fields"), var1.getname("set_fields"));
      var1.setlocal("_fields_", var5);
      var3 = null;
      var1.setline(122);
      var5 = var1.getname("property").__call__(var2, var1.getname("None"));
      var1.setlocal("_pack_", var5);
      var3 = null;
      var1.setline(123);
      var5 = var1.getname("property").__call__(var2, var1.getname("None"));
      var1.setlocal("_anonymous_", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new_aggregate__$17(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3;
      if (var1.getlocal(3).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_fields_")).__nonzero__()) {
         var1.setline(95);
         var3 = var1.getglobal("_StructLayoutBuilder").__call__(var2, var1.getlocal(4)).__getattr__("add_fields").__call__(var2, var1.getlocal(3).__getitem__(PyString.fromInterned("_fields_"))).__getattr__("build").__call__(var2);
         var1.setlocal(5, var3);
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("_jffi_type"), var3);
         var1.setline(97);
         var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("_fields_")).__iter__();

         while(true) {
            var1.setline(97);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(99);
               var3 = var1.getlocal(3).__getitem__(PyString.fromInterned("_fields_"));
               var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("__fields_"), var3);
               var3 = null;
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(98);
            PyObject var5 = var1.getlocal(5).__getitem__(var1.getlocal(6).__getitem__(Py.newInteger(0)));
            var1.getlocal(3).__setitem__(var1.getlocal(6).__getitem__(Py.newInteger(0)), var5);
            var5 = null;
         }
      } else {
         var1.setline(101);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("__fields_"), var6);
         var3 = null;
      }

      var1.setline(102);
      if (var1.getlocal(3).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_pack_")).__nonzero__()) {
         var1.setline(103);
         throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("struct packing not implemented")));
      } else {
         var1.setline(104);
         if (var1.getlocal(3).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_anonymous_")).__nonzero__()) {
            var1.setline(105);
            throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("anonymous fields not implemented")));
         } else {
            var1.setline(107);
            var3 = var1.getglobal("type").__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_fields$18(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getlocal(0).__getattr__("_AggregateMetaClass__fields_");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_fields$19(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var10000 = var1.getglobal("_StructLayoutBuilder");
      PyObject[] var3 = new PyObject[]{var1.getglobal("issubclass").__call__(var2, var1.getglobal("Union"), var1.getlocal(0))};
      String[] var4 = new String[]{"union"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000.__getattr__("add_fields").__call__(var2, var1.getlocal(1)).__getattr__("build").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(114);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_AggregateMetaClass__fields_", var5);
      var3 = null;
      var1.setline(115);
      var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_jffi_type", var5);
      var3 = null;
      var1.setline(117);
      var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(117);
         PyObject var6 = var5.__iternext__();
         if (var6 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var6);
         var1.setline(118);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(var1.getlocal(3).__getitem__(Py.newInteger(0))));
      }
   }

   public PyObject _StructMetaClass$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(126);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$21, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$21(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var10000 = var1.getglobal("_AggregateMetaClass").__getattr__("__new_aggregate__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("False")};
      String[] var4 = new String[]{"union"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _UnionMetaClass$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(130);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$23, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$23(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var10000 = var1.getglobal("_AggregateMetaClass").__getattr__("__new_aggregate__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("True")};
      String[] var4 = new String[]{"union"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject Structure$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(134);
      PyObject var3 = var1.getname("_StructMetaClass");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Union$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(137);
      PyObject var3 = var1.getname("_UnionMetaClass");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject sizeof$26(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_jffi_type")).__nonzero__()) {
         var1.setline(141);
         PyObject var3 = var1.getlocal(0).__getattr__("_jffi_type").__getattr__("size").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(143);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("this type has no size")));
      }
   }

   public PyObject alignment$27(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyObject var3 = var1.getlocal(0).__getattr__("_jffi_type").__getattr__("alignment").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject addressof$28(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(0).__getattr__("address").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject byref$29(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getlocal(0).__getattr__("byref").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pointer$30(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getlocal(0).__getattr__("pointer").__call__(var2, var1.getglobal("POINTER").__call__(var2, var1.getlocal(0).__getattr__("__class__")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject POINTER$31(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var3;
      if (var1.getglobal("_pointer_type_cache").__getattr__("has_key").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(164);
         var3 = var1.getglobal("_pointer_type_cache").__getitem__(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(167);
         PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("_jffi_type"), var1.getglobal("jffi").__getattr__("Type").__getattr__("Pointer").__call__(var2, var1.getlocal(0))});
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(169);
         PyObject var5 = imp.importOne("inspect", var1, -1);
         var1.setlocal(2, var5);
         var4 = null;
         var1.setline(170);
         var5 = var1.getlocal(2).__getattr__("getmodule").__call__(var2, var1.getlocal(2).__getattr__("stack").__call__(var2).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0)));
         var1.setlocal(3, var5);
         var4 = null;
         var1.setline(171);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            PyString var6 = PyString.fromInterned("__main__");
            var1.setlocal(4, var6);
            var4 = null;
         } else {
            var1.setline(174);
            var5 = var1.getlocal(3).__getattr__("__name__");
            var1.setlocal(4, var5);
            var4 = null;
         }

         var1.setline(175);
         var5 = var1.getlocal(4);
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("__module__"), var5);
         var4 = null;
         var1.setline(177);
         var5 = var1.getglobal("type").__call__((ThreadState)var2, PyString.fromInterned("LP_%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__name__")})), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("jffi").__getattr__("PointerCData"), var1.getglobal("_CData")})), (PyObject)var1.getlocal(1));
         var1.setlocal(5, var5);
         var4 = null;
         var1.setline(178);
         var5 = var1.getlocal(5);
         var1.getglobal("_pointer_type_cache").__setitem__(var1.getlocal(0), var5);
         var4 = null;
         var1.setline(179);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject c_bool$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(182);
      PyString var3 = PyString.fromInterned("?");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(183);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("BOOL");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_byte$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(186);
      PyString var3 = PyString.fromInterned("b");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(187);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("BYTE");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_ubyte$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(190);
      PyString var3 = PyString.fromInterned("B");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(191);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("UBYTE");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_short$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(194);
      PyString var3 = PyString.fromInterned("h");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(195);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("SHORT");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_ushort$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(198);
      PyString var3 = PyString.fromInterned("H");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(199);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("USHORT");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_int$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(202);
      PyString var3 = PyString.fromInterned("i");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(203);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("INT");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_uint$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(206);
      PyString var3 = PyString.fromInterned("I");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(207);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("UINT");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_longlong$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(210);
      PyString var3 = PyString.fromInterned("q");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(211);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("LONGLONG");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_ulonglong$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(214);
      PyString var3 = PyString.fromInterned("Q");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(215);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("ULONGLONG");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_long$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(218);
      PyString var3 = PyString.fromInterned("l");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(219);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("LONG");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_ulong$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(222);
      PyString var3 = PyString.fromInterned("L");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(223);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("ULONG");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_float$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(226);
      PyString var3 = PyString.fromInterned("f");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(227);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("FLOAT");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_double$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(230);
      PyString var3 = PyString.fromInterned("d");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(231);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("DOUBLE");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_char_p$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(246);
      PyString var3 = PyString.fromInterned("z");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(247);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("STRING");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject c_void_p$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(250);
      PyString var3 = PyString.fromInterned("P");
      var1.setlocal("_type_", var3);
      var3 = null;
      var1.setline(251);
      PyObject var4 = var1.getname("jffi").__getattr__("Type").__getattr__("POINTER");
      var1.setlocal("_jffi_type", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _Function$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(254);
      PyObject var3 = var1.getname("c_int");
      var1.setlocal("_restype", var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getname("None");
      var1.setlocal("_argtypes", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject CDLL$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(259);
      PyObject var3 = var1.getname("jffi").__getattr__("RTLD_GLOBAL")._or(var1.getname("jffi").__getattr__("RTLD_LAZY"));
      var1.setlocal("DEFAULT_MODE", var3);
      var3 = null;
      var1.setline(261);
      PyObject[] var4 = new PyObject[]{var1.getname("DEFAULT_MODE"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$49, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(264);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$50, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(271);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getitem__$51, (PyObject)null);
      var1.setlocal("__getitem__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyObject var3 = var1.getglobal("jffi").__getattr__("dlopen").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.getlocal(0).__setattr__("_handle", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$50(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(266);
         throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
      } else {
         var1.setline(267);
         PyObject var3 = var1.getlocal(0).__getattr__("__getitem__").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(268);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.setline(269);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __getitem__$51(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getglobal("_Function").__call__(var2, var1.getlocal(0).__getattr__("_handle").__getattr__("find_symbol").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LibraryLoader$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(275);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$53, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(278);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$54, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(285);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$55, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, LoadLibrary$56, (PyObject)null);
      var1.setlocal("LoadLibrary", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$53(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_dlltype", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$54(PyFrame var1, ThreadState var2) {
      var1.setline(279);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._eq(PyString.fromInterned("_"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(280);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("_dlltype").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(282);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.setline(283);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __getitem__$55(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LoadLibrary$56(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyObject var3 = var1.getlocal(0).__getattr__("_dlltype").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public ctypes$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _CTypeMetaClass$1 = Py.newCode(0, var2, var1, "_CTypeMetaClass", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "name", "bases", "dict"};
      __new__$2 = Py.newCode(4, var2, var1, "__new__", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len", "dict", "inspect", "mod", "name"};
      __mul__$3 = Py.newCode(2, var2, var1, "__mul__", 28, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _CData$4 = Py.newCode(0, var2, var1, "_CData", 41, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lib", "name"};
      in_dll$5 = Py.newCode(3, var2, var1, "in_dll", 42, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      size$6 = Py.newCode(1, var2, var1, "size", 46, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ScalarCData$7 = Py.newCode(0, var2, var1, "_ScalarCData", 50, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ArrayCData$8 = Py.newCode(0, var2, var1, "_ArrayCData", 54, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __len__$9 = Py.newCode(1, var2, var1, "__len__", 55, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StructLayoutBuilder$10 = Py.newCode(0, var2, var1, "_StructLayoutBuilder", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "union"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 59, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "align"};
      align$12 = Py.newCode(3, var2, var1, "align", 65, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fields", "f"};
      add_fields$13 = Py.newCode(2, var2, var1, "add_fields", 68, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      add_field$14 = Py.newCode(2, var2, var1, "add_field", 73, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      build$15 = Py.newCode(1, var2, var1, "build", 88, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _AggregateMetaClass$16 = Py.newCode(0, var2, var1, "_AggregateMetaClass", 91, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "name", "bases", "dict", "union", "layout", "f"};
      __new_aggregate__$17 = Py.newCode(5, var2, var1, "__new_aggregate__", 92, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_fields$18 = Py.newCode(1, var2, var1, "get_fields", 109, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fields", "layout", "f"};
      set_fields$19 = Py.newCode(2, var2, var1, "set_fields", 112, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StructMetaClass$20 = Py.newCode(0, var2, var1, "_StructMetaClass", 125, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "name", "bases", "dict"};
      __new__$21 = Py.newCode(4, var2, var1, "__new__", 126, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _UnionMetaClass$22 = Py.newCode(0, var2, var1, "_UnionMetaClass", 129, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "name", "bases", "dict"};
      __new__$23 = Py.newCode(4, var2, var1, "__new__", 130, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Structure$24 = Py.newCode(0, var2, var1, "Structure", 133, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Union$25 = Py.newCode(0, var2, var1, "Union", 136, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"type"};
      sizeof$26 = Py.newCode(1, var2, var1, "sizeof", 139, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"type"};
      alignment$27 = Py.newCode(1, var2, var1, "alignment", 145, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cdata"};
      addressof$28 = Py.newCode(1, var2, var1, "addressof", 148, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cdata", "offset"};
      byref$29 = Py.newCode(2, var2, var1, "byref", 151, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cdata"};
      pointer$30 = Py.newCode(1, var2, var1, "pointer", 154, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ctype", "dict", "inspect", "mod", "name", "ptype"};
      POINTER$31 = Py.newCode(1, var2, var1, "POINTER", 161, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      c_bool$32 = Py.newCode(0, var2, var1, "c_bool", 181, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_byte$33 = Py.newCode(0, var2, var1, "c_byte", 185, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_ubyte$34 = Py.newCode(0, var2, var1, "c_ubyte", 189, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_short$35 = Py.newCode(0, var2, var1, "c_short", 193, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_ushort$36 = Py.newCode(0, var2, var1, "c_ushort", 197, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_int$37 = Py.newCode(0, var2, var1, "c_int", 201, false, false, self, 37, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_uint$38 = Py.newCode(0, var2, var1, "c_uint", 205, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_longlong$39 = Py.newCode(0, var2, var1, "c_longlong", 209, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_ulonglong$40 = Py.newCode(0, var2, var1, "c_ulonglong", 213, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_long$41 = Py.newCode(0, var2, var1, "c_long", 217, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_ulong$42 = Py.newCode(0, var2, var1, "c_ulong", 221, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_float$43 = Py.newCode(0, var2, var1, "c_float", 225, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_double$44 = Py.newCode(0, var2, var1, "c_double", 229, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_char_p$45 = Py.newCode(0, var2, var1, "c_char_p", 245, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      c_void_p$46 = Py.newCode(0, var2, var1, "c_void_p", 249, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Function$47 = Py.newCode(0, var2, var1, "_Function", 253, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CDLL$48 = Py.newCode(0, var2, var1, "CDLL", 258, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "mode", "handle"};
      __init__$49 = Py.newCode(4, var2, var1, "__init__", 261, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "func"};
      __getattr__$50 = Py.newCode(2, var2, var1, "__getattr__", 264, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$51 = Py.newCode(2, var2, var1, "__getitem__", 271, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LibraryLoader$52 = Py.newCode(0, var2, var1, "LibraryLoader", 274, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dlltype"};
      __init__$53 = Py.newCode(2, var2, var1, "__init__", 275, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "dll"};
      __getattr__$54 = Py.newCode(2, var2, var1, "__getattr__", 278, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$55 = Py.newCode(2, var2, var1, "__getitem__", 285, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      LoadLibrary$56 = Py.newCode(2, var2, var1, "LoadLibrary", 288, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ctypes$py("ctypes$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ctypes$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._CTypeMetaClass$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this.__mul__$3(var2, var3);
         case 4:
            return this._CData$4(var2, var3);
         case 5:
            return this.in_dll$5(var2, var3);
         case 6:
            return this.size$6(var2, var3);
         case 7:
            return this._ScalarCData$7(var2, var3);
         case 8:
            return this._ArrayCData$8(var2, var3);
         case 9:
            return this.__len__$9(var2, var3);
         case 10:
            return this._StructLayoutBuilder$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.align$12(var2, var3);
         case 13:
            return this.add_fields$13(var2, var3);
         case 14:
            return this.add_field$14(var2, var3);
         case 15:
            return this.build$15(var2, var3);
         case 16:
            return this._AggregateMetaClass$16(var2, var3);
         case 17:
            return this.__new_aggregate__$17(var2, var3);
         case 18:
            return this.get_fields$18(var2, var3);
         case 19:
            return this.set_fields$19(var2, var3);
         case 20:
            return this._StructMetaClass$20(var2, var3);
         case 21:
            return this.__new__$21(var2, var3);
         case 22:
            return this._UnionMetaClass$22(var2, var3);
         case 23:
            return this.__new__$23(var2, var3);
         case 24:
            return this.Structure$24(var2, var3);
         case 25:
            return this.Union$25(var2, var3);
         case 26:
            return this.sizeof$26(var2, var3);
         case 27:
            return this.alignment$27(var2, var3);
         case 28:
            return this.addressof$28(var2, var3);
         case 29:
            return this.byref$29(var2, var3);
         case 30:
            return this.pointer$30(var2, var3);
         case 31:
            return this.POINTER$31(var2, var3);
         case 32:
            return this.c_bool$32(var2, var3);
         case 33:
            return this.c_byte$33(var2, var3);
         case 34:
            return this.c_ubyte$34(var2, var3);
         case 35:
            return this.c_short$35(var2, var3);
         case 36:
            return this.c_ushort$36(var2, var3);
         case 37:
            return this.c_int$37(var2, var3);
         case 38:
            return this.c_uint$38(var2, var3);
         case 39:
            return this.c_longlong$39(var2, var3);
         case 40:
            return this.c_ulonglong$40(var2, var3);
         case 41:
            return this.c_long$41(var2, var3);
         case 42:
            return this.c_ulong$42(var2, var3);
         case 43:
            return this.c_float$43(var2, var3);
         case 44:
            return this.c_double$44(var2, var3);
         case 45:
            return this.c_char_p$45(var2, var3);
         case 46:
            return this.c_void_p$46(var2, var3);
         case 47:
            return this._Function$47(var2, var3);
         case 48:
            return this.CDLL$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this.__getattr__$50(var2, var3);
         case 51:
            return this.__getitem__$51(var2, var3);
         case 52:
            return this.LibraryLoader$52(var2, var3);
         case 53:
            return this.__init__$53(var2, var3);
         case 54:
            return this.__getattr__$54(var2, var3);
         case 55:
            return this.__getitem__$55(var2, var3);
         case 56:
            return this.LoadLibrary$56(var2, var3);
         default:
            return null;
      }
   }
}
