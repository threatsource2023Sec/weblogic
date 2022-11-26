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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("weakref.py")
public class weakref$py extends PyFunctionTable implements PyRunnable {
   static weakref$py self;
   static final PyCode f$0;
   static final PyCode WeakValueDictionary$1;
   static final PyCode __new__$2;
   static final PyCode itervaluerefs$3;
   static final PyCode valuerefs$4;
   static final PyCode WeakKeyDictionary$5;
   static final PyCode __new__$6;
   static final PyCode iterkeyrefs$7;
   static final PyCode keyrefs$8;
   static final PyCode KeyedRef$9;
   static final PyCode __new__$10;
   static final PyCode __init__$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Weak reference support for Python.\n\nThis module is an implementation of PEP 205:\n\nhttp://www.python.org/dev/peps/pep-0205/\n"));
      var1.setline(6);
      PyString.fromInterned("Weak reference support for Python.\n\nThis module is an implementation of PEP 205:\n\nhttp://www.python.org/dev/peps/pep-0205/\n");
      var1.setline(14);
      String[] var3 = new String[]{"getweakrefcount", "getweakrefs", "ref", "proxy", "CallableProxyType", "ProxyType", "ReferenceType"};
      PyObject[] var5 = imp.importFrom("_weakref", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("getweakrefcount", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("getweakrefs", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("ref", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("proxy", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("CallableProxyType", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("ProxyType", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("ReferenceType", var4);
      var4 = null;
      var1.setline(23);
      var3 = new String[]{"WeakSet"};
      var5 = imp.importFrom("_weakrefset", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("WeakSet", var4);
      var4 = null;
      var1.setline(25);
      var3 = new String[]{"ReferenceError"};
      var5 = imp.importFrom("exceptions", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("ReferenceError", var4);
      var4 = null;
      var1.setline(26);
      var3 = new String[]{"MapMaker", "dict_builder"};
      var5 = imp.importFrom("jythonlib", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("MapMaker", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("dict_builder", var4);
      var4 = null;
      var1.setline(29);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getname("ProxyType"), var1.getname("CallableProxyType")});
      var1.setlocal("ProxyTypes", var6);
      var3 = null;
      var1.setline(31);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("ref"), PyString.fromInterned("proxy"), PyString.fromInterned("getweakrefcount"), PyString.fromInterned("getweakrefs"), PyString.fromInterned("WeakKeyDictionary"), PyString.fromInterned("ReferenceError"), PyString.fromInterned("ReferenceType"), PyString.fromInterned("ProxyType"), PyString.fromInterned("CallableProxyType"), PyString.fromInterned("ProxyTypes"), PyString.fromInterned("WeakValueDictionary"), PyString.fromInterned("WeakSet")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(36);
      var5 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("WeakValueDictionary", var5, WeakValueDictionary$1);
      var1.setlocal("WeakValueDictionary", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(71);
      PyObject var8 = var1.getname("dict_builder").__call__(var2, var1.getname("MapMaker").__call__(var2).__getattr__("weakValues").__call__(var2).__getattr__("makeMap"), var1.getname("WeakValueDictionary"));
      var1.setlocal("WeakValueDictionaryBuilder", var8);
      var3 = null;
      var1.setline(74);
      var5 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("WeakKeyDictionary", var5, WeakKeyDictionary$5);
      var1.setlocal("WeakKeyDictionary", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(113);
      var8 = var1.getname("dict_builder").__call__(var2, var1.getname("MapMaker").__call__(var2).__getattr__("weakKeys").__call__(var2).__getattr__("makeMap"), var1.getname("WeakKeyDictionary"));
      var1.setlocal("WeakKeyDictionaryBuilder", var8);
      var3 = null;
      var1.setline(119);
      var5 = new PyObject[]{var1.getname("ref")};
      var4 = Py.makeClass("KeyedRef", var5, KeyedRef$9);
      var1.setlocal("KeyedRef", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject WeakValueDictionary$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mapping class that references values weakly.\n\n    Entries in the dictionary will be discarded when no strong\n    reference to the value exists anymore\n    "));
      var1.setline(41);
      PyString.fromInterned("Mapping class that references values weakly.\n\n    Entries in the dictionary will be discarded when no strong\n    reference to the value exists anymore\n    ");
      var1.setline(43);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervaluerefs$3, PyString.fromInterned("Return an iterator that yields the weak references to the values.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the values around longer than needed.\n\n        "));
      var1.setlocal("itervaluerefs", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, valuerefs$4, PyString.fromInterned("Return a list of weak references to the values.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the values around longer than needed.\n\n        "));
      var1.setlocal("valuerefs", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var10000 = var1.getglobal("WeakValueDictionaryBuilder");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject itervaluerefs$3(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(55);
            PyString.fromInterned("Return an iterator that yields the weak references to the values.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the values around longer than needed.\n\n        ");
            var1.setline(56);
            var3 = var1.getlocal(0).__getattr__("itervalues").__call__(var2).__iter__();
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

      var1.setline(56);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(57);
         var1.setline(57);
         var6 = var1.getglobal("ref").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject valuerefs$4(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Return a list of weak references to the values.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the values around longer than needed.\n\n        ");
      var1.setline(69);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getlocal(0).__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(69);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(69);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(69);
         var1.getlocal(1).__call__(var2, var1.getglobal("ref").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject WeakKeyDictionary$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Mapping class that references keys weakly.\n\n    Entries in the dictionary will be discarded when there is no\n    longer a strong reference to the key. This can be used to\n    associate additional data with an object owned by other parts of\n    an application without adding attributes to those objects. This\n    can be especially useful with objects that override attribute\n    accesses.\n    "));
      var1.setline(83);
      PyString.fromInterned(" Mapping class that references keys weakly.\n\n    Entries in the dictionary will be discarded when there is no\n    longer a strong reference to the key. This can be used to\n    associate additional data with an object owned by other parts of\n    an application without adding attributes to those objects. This\n    can be especially useful with objects that override attribute\n    accesses.\n    ");
      var1.setline(85);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$6, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeyrefs$7, PyString.fromInterned("Return an iterator that yields the weak references to the keys.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the keys around longer than needed.\n\n        "));
      var1.setlocal("iterkeyrefs", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keyrefs$8, PyString.fromInterned("Return a list of weak references to the keys.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the keys around longer than needed.\n\n        "));
      var1.setlocal("keyrefs", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$6(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var10000 = var1.getglobal("WeakKeyDictionaryBuilder");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject iterkeyrefs$7(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(97);
            PyString.fromInterned("Return an iterator that yields the weak references to the keys.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the keys around longer than needed.\n\n        ");
            var1.setline(98);
            var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();
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

      var1.setline(98);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(99);
         var1.setline(99);
         var6 = var1.getglobal("ref").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject keyrefs$8(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Return a list of weak references to the keys.\n\n        The references are not guaranteed to be 'live' at the time\n        they are used, so the result of calling the references needs\n        to be checked before being used.  This can be used to avoid\n        creating references that will cause the garbage collector to\n        keep the keys around longer than needed.\n\n        ");
      var1.setline(111);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(111);
      var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();

      while(true) {
         var1.setline(111);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(111);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(111);
         var1.getlocal(1).__call__(var2, var1.getglobal("ref").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject KeyedRef$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Specialized reference that includes a key corresponding to the value.\n\n    This is used in the WeakValueDictionary to avoid having to create\n    a function object for each key stored in the mapping.  A shared\n    callback object can use the 'key' attribute of a KeyedRef instead\n    of getting a reference to the key from an enclosing scope.\n\n    "));
      var1.setline(127);
      PyString.fromInterned("Specialized reference that includes a key corresponding to the value.\n\n    This is used in the WeakValueDictionary to avoid having to create\n    a function object for each key stored in the mapping.  A shared\n    callback object can use the 'key' attribute of a KeyedRef instead\n    of getting a reference to the key from an enclosing scope.\n\n    ");
      var1.setline(129);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("key")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(131);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$10, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(136);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$10(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getglobal("ref").__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("key", var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      var1.getglobal("super").__call__(var2, var1.getglobal("KeyedRef"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public weakref$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      WeakValueDictionary$1 = Py.newCode(0, var2, var1, "WeakValueDictionary", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kw"};
      __new__$2 = Py.newCode(3, var2, var1, "__new__", 43, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      itervaluerefs$3 = Py.newCode(1, var2, var1, "itervaluerefs", 46, false, false, self, 3, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "_[69_16]", "value"};
      valuerefs$4 = Py.newCode(1, var2, var1, "valuerefs", 59, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      WeakKeyDictionary$5 = Py.newCode(0, var2, var1, "WeakKeyDictionary", 74, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kw"};
      __new__$6 = Py.newCode(3, var2, var1, "__new__", 85, true, true, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      iterkeyrefs$7 = Py.newCode(1, var2, var1, "iterkeyrefs", 88, false, false, self, 7, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "_[111_16]", "key"};
      keyrefs$8 = Py.newCode(1, var2, var1, "keyrefs", 101, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      KeyedRef$9 = Py.newCode(0, var2, var1, "KeyedRef", 119, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"type", "ob", "callback", "key", "self"};
      __new__$10 = Py.newCode(4, var2, var1, "__new__", 131, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ob", "callback", "key"};
      __init__$11 = Py.newCode(4, var2, var1, "__init__", 136, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new weakref$py("weakref$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(weakref$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.WeakValueDictionary$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this.itervaluerefs$3(var2, var3);
         case 4:
            return this.valuerefs$4(var2, var3);
         case 5:
            return this.WeakKeyDictionary$5(var2, var3);
         case 6:
            return this.__new__$6(var2, var3);
         case 7:
            return this.iterkeyrefs$7(var2, var3);
         case 8:
            return this.keyrefs$8(var2, var3);
         case 9:
            return this.KeyedRef$9(var2, var3);
         case 10:
            return this.__new__$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         default:
            return null;
      }
   }
}
