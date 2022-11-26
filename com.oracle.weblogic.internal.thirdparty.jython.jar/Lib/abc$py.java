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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("abc.py")
public class abc$py extends PyFunctionTable implements PyRunnable {
   static abc$py self;
   static final PyCode f$0;
   static final PyCode _C$1;
   static final PyCode abstractmethod$2;
   static final PyCode abstractproperty$3;
   static final PyCode ABCMeta$4;
   static final PyCode __new__$5;
   static final PyCode f$6;
   static final PyCode register$7;
   static final PyCode _dump_registry$8;
   static final PyCode __instancecheck__$9;
   static final PyCode __subclasscheck__$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Abstract Base Classes (ABCs) according to PEP 3119."));
      var1.setline(4);
      PyString.fromInterned("Abstract Base Classes (ABCs) according to PEP 3119.");
      var1.setline(6);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"WeakSet"};
      PyObject[] var6 = imp.importFrom("_weakrefset", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("WeakSet", var4);
      var4 = null;
      var1.setline(11);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("_C", var6, _C$1);
      var1.setlocal("_C", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(12);
      var3 = var1.getname("type").__call__(var2, var1.getname("_C").__call__(var2));
      var1.setlocal("_InstanceType", var3);
      var3 = null;
      var1.setline(15);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, abstractmethod$2, PyString.fromInterned("A decorator indicating abstract methods.\n\n    Requires that the metaclass is ABCMeta or derived from it.  A\n    class that has a metaclass derived from ABCMeta cannot be\n    instantiated unless all of its abstract methods are overridden.\n    The abstract methods can be called using any of the normal\n    'super' call mechanisms.\n\n    Usage:\n\n        class C:\n            __metaclass__ = ABCMeta\n            @abstractmethod\n            def my_abstract_method(self, ...):\n                ...\n    "));
      var1.setlocal("abstractmethod", var7);
      var3 = null;
      var1.setline(36);
      var6 = new PyObject[]{var1.getname("property")};
      var4 = Py.makeClass("abstractproperty", var6, abstractproperty$3);
      var1.setlocal("abstractproperty", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(65);
      var6 = new PyObject[]{var1.getname("type")};
      var4 = Py.makeClass("ABCMeta", var6, ABCMeta$4);
      var1.setlocal("ABCMeta", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _C$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      return var1.getf_locals();
   }

   public PyObject abstractmethod$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyString.fromInterned("A decorator indicating abstract methods.\n\n    Requires that the metaclass is ABCMeta or derived from it.  A\n    class that has a metaclass derived from ABCMeta cannot be\n    instantiated unless all of its abstract methods are overridden.\n    The abstract methods can be called using any of the normal\n    'super' call mechanisms.\n\n    Usage:\n\n        class C:\n            __metaclass__ = ABCMeta\n            @abstractmethod\n            def my_abstract_method(self, ...):\n                ...\n    ");
      var1.setline(32);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("__isabstractmethod__", var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject abstractproperty$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A decorator indicating abstract properties.\n\n    Requires that the metaclass is ABCMeta or derived from it.  A\n    class that has a metaclass derived from ABCMeta cannot be\n    instantiated unless all of its abstract properties are overridden.\n    The abstract properties can be called using any of the normal\n    'super' call mechanisms.\n\n    Usage:\n\n        class C:\n            __metaclass__ = ABCMeta\n            @abstractproperty\n            def my_abstract_property(self):\n                ...\n\n    This defines a read-only property; you can also define a read-write\n    abstract property using the 'long' form of property declaration:\n\n        class C:\n            __metaclass__ = ABCMeta\n            def getx(self): ...\n            def setx(self, value): ...\n            x = abstractproperty(getx, setx)\n    "));
      var1.setline(61);
      PyString.fromInterned("A decorator indicating abstract properties.\n\n    Requires that the metaclass is ABCMeta or derived from it.  A\n    class that has a metaclass derived from ABCMeta cannot be\n    instantiated unless all of its abstract properties are overridden.\n    The abstract properties can be called using any of the normal\n    'super' call mechanisms.\n\n    Usage:\n\n        class C:\n            __metaclass__ = ABCMeta\n            @abstractproperty\n            def my_abstract_property(self):\n                ...\n\n    This defines a read-only property; you can also define a read-write\n    abstract property using the 'long' form of property declaration:\n\n        class C:\n            __metaclass__ = ABCMeta\n            def getx(self): ...\n            def setx(self, value): ...\n            x = abstractproperty(getx, setx)\n    ");
      var1.setline(62);
      PyObject var3 = var1.getname("True");
      var1.setlocal("__isabstractmethod__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject ABCMeta$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Metaclass for defining Abstract Base Classes (ABCs).\n\n    Use this metaclass to create an ABC.  An ABC can be subclassed\n    directly, and then acts as a mix-in class.  You can also register\n    unrelated concrete classes (even built-in classes) and unrelated\n    ABCs as 'virtual subclasses' -- these and their descendants will\n    be considered subclasses of the registering ABC by the built-in\n    issubclass() function, but the registering ABC won't show up in\n    their MRO (Method Resolution Order) nor will method\n    implementations defined by the registering ABC be callable (not\n    even via super()).\n\n    "));
      var1.setline(79);
      PyString.fromInterned("Metaclass for defining Abstract Base Classes (ABCs).\n\n    Use this metaclass to create an ABC.  An ABC can be subclassed\n    directly, and then acts as a mix-in class.  You can also register\n    unrelated concrete classes (even built-in classes) and unrelated\n    ABCs as 'virtual subclasses' -- these and their descendants will\n    be considered subclasses of the registering ABC by the built-in\n    issubclass() function, but the registering ABC won't show up in\n    their MRO (Method Resolution Order) nor will method\n    implementations defined by the registering ABC be callable (not\n    even via super()).\n\n    ");
      var1.setline(84);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("_abc_invalidation_counter", var3);
      var3 = null;
      var1.setline(86);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$5, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, register$7, PyString.fromInterned("Register a virtual subclass of an ABC."));
      var1.setlocal("register", var5);
      var3 = null;
      var1.setline(119);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _dump_registry$8, PyString.fromInterned("Debug helper to print the ABC registry."));
      var1.setlocal("_dump_registry", var5);
      var3 = null;
      var1.setline(128);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __instancecheck__$9, PyString.fromInterned("Override for isinstance(instance, cls)."));
      var1.setlocal("__instancecheck__", var5);
      var3 = null;
      var1.setline(148);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __subclasscheck__$10, PyString.fromInterned("Override for issubclass(subclass, cls)."));
      var1.setlocal("__subclasscheck__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$5(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ABCMeta"), var1.getlocal(0)).__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(89);
      PyObject var10000 = var1.getglobal("set");
      var1.setline(89);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var8, f$6, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(3).__getattr__("items").__call__(var2).__iter__());
      Arrays.fill(var8, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(92);
         PyObject var9 = var3.__iternext__();
         if (var9 == null) {
            var1.setline(97);
            var3 = var1.getglobal("frozenset").__call__(var2, var1.getlocal(5));
            var1.getlocal(4).__setattr__("__abstractmethods__", var3);
            var3 = null;
            var1.setline(99);
            var3 = var1.getglobal("WeakSet").__call__(var2);
            var1.getlocal(4).__setattr__("_abc_registry", var3);
            var3 = null;
            var1.setline(100);
            var3 = var1.getglobal("WeakSet").__call__(var2);
            var1.getlocal(4).__setattr__("_abc_cache", var3);
            var3 = null;
            var1.setline(101);
            var3 = var1.getglobal("WeakSet").__call__(var2);
            var1.getlocal(4).__setattr__("_abc_negative_cache", var3);
            var3 = null;
            var1.setline(102);
            var3 = var1.getglobal("ABCMeta").__getattr__("_abc_invalidation_counter");
            var1.getlocal(4).__setattr__("_abc_negative_cache_version", var3);
            var3 = null;
            var1.setline(103);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(7, var9);
         var1.setline(93);
         PyObject var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("__abstractmethods__"), (PyObject)var1.getglobal("set").__call__(var2)).__iter__();

         while(true) {
            var1.setline(93);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(1, var6);
            var1.setline(94);
            PyObject var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getglobal("None"));
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(95);
            if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyString.fromInterned("__isabstractmethod__"), (PyObject)var1.getglobal("False")).__nonzero__()) {
               var1.setline(96);
               var1.getlocal(5).__getattr__("add").__call__(var2, var1.getlocal(1));
            }
         }
      }
   }

   public PyObject f$6(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(90);
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

            var8 = (PyObject)var10000;
      }

      do {
         var1.setline(90);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(91);
      } while(!var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("__isabstractmethod__"), (PyObject)var1.getglobal("False")).__nonzero__());

      var1.setline(89);
      var1.setline(89);
      var8 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var8;
   }

   public PyObject register$7(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyString.fromInterned("Register a virtual subclass of an ABC.");
      var1.setline(107);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type"), var1.getglobal("types").__getattr__("ClassType")}))).__not__().__nonzero__()) {
         var1.setline(108);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can only register classes")));
      } else {
         var1.setline(109);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getlocal(0)).__nonzero__()) {
            var1.setline(110);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(113);
            if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__nonzero__()) {
               var1.setline(115);
               throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Refusing to create an inheritance cycle")));
            } else {
               var1.setline(116);
               var1.getlocal(0).__getattr__("_abc_registry").__getattr__("add").__call__(var2, var1.getlocal(1));
               var1.setline(117);
               PyObject var10000 = var1.getglobal("ABCMeta");
               String var3 = "_abc_invalidation_counter";
               PyObject var4 = var10000;
               PyObject var5 = var4.__getattr__(var3);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var3, var5);
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject _dump_registry$8(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("Debug helper to print the ABC registry.");
      var1.setline(121);
      PyObject var3 = var1.getlocal(1);
      Py.println(var3, PyString.fromInterned("Class: %s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__module__"), var1.getlocal(0).__getattr__("__name__")})));
      var1.setline(122);
      var3 = var1.getlocal(1);
      Py.println(var3, PyString.fromInterned("Inv.counter: %s")._mod(var1.getglobal("ABCMeta").__getattr__("_abc_invalidation_counter")));
      var1.setline(123);
      var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0).__getattr__("__dict__").__getattr__("keys").__call__(var2)).__iter__();

      while(true) {
         var1.setline(123);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(124);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_abc_")).__nonzero__()) {
            var1.setline(125);
            PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(126);
            var5 = var1.getlocal(1);
            Py.println(var5, PyString.fromInterned("%s: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject __instancecheck__$9(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyString.fromInterned("Override for isinstance(instance, cls).");
      var1.setline(131);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__class__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_abc_cache"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(133);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(134);
         PyObject var4 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(136);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getglobal("_InstanceType"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(137);
            var4 = var1.getlocal(2);
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(138);
         var4 = var1.getlocal(3);
         var10000 = var4._is(var1.getlocal(2));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(139);
            var4 = var1.getlocal(0).__getattr__("_abc_negative_cache_version");
            var10000 = var4._eq(var1.getglobal("ABCMeta").__getattr__("_abc_invalidation_counter"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(3);
               var10000 = var4._in(var1.getlocal(0).__getattr__("_abc_negative_cache"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(142);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(144);
               var3 = var1.getlocal(0).__getattr__("__subclasscheck__").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(145);
            var10000 = var1.getlocal(0).__getattr__("__subclasscheck__").__call__(var2, var1.getlocal(2));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("__subclasscheck__").__call__(var2, var1.getlocal(3));
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __subclasscheck__$10(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyString.fromInterned("Override for issubclass(subclass, cls).");
      var1.setline(151);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_abc_cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(154);
         PyObject var4 = var1.getlocal(0).__getattr__("_abc_negative_cache_version");
         var10000 = var4._lt(var1.getglobal("ABCMeta").__getattr__("_abc_invalidation_counter"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(156);
            var4 = var1.getglobal("WeakSet").__call__(var2);
            var1.getlocal(0).__setattr__("_abc_negative_cache", var4);
            var4 = null;
            var1.setline(157);
            var4 = var1.getglobal("ABCMeta").__getattr__("_abc_invalidation_counter");
            var1.getlocal(0).__setattr__("_abc_negative_cache_version", var4);
            var4 = null;
         } else {
            var1.setline(158);
            var4 = var1.getlocal(1);
            var10000 = var4._in(var1.getlocal(0).__getattr__("_abc_negative_cache"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(159);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(161);
         var4 = var1.getlocal(0).__getattr__("__subclasshook__").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(162);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("NotImplemented"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(163);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("bool")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            } else {
               var1.setline(164);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(165);
                  var1.getlocal(0).__getattr__("_abc_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
               } else {
                  var1.setline(167);
                  var1.getlocal(0).__getattr__("_abc_negative_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
               }

               var1.setline(168);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(170);
            var4 = var1.getlocal(0);
            var10000 = var4._in(var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__mro__"), (PyObject)(new PyTuple(Py.EmptyObjects))));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(171);
               var1.getlocal(0).__getattr__("_abc_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
               var1.setline(172);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(174);
               var4 = var1.getlocal(0).__getattr__("_abc_registry").__iter__();

               do {
                  var1.setline(174);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(179);
                     var4 = var1.getlocal(0).__getattr__("__subclasses__").__call__(var2).__iter__();

                     do {
                        var1.setline(179);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(184);
                           var1.getlocal(0).__getattr__("_abc_negative_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
                           var1.setline(185);
                           var3 = var1.getglobal("False");
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setlocal(4, var5);
                        var1.setline(180);
                     } while(!var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getlocal(4)).__nonzero__());

                     var1.setline(181);
                     var1.getlocal(0).__getattr__("_abc_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
                     var1.setline(182);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(3, var5);
                  var1.setline(175);
               } while(!var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getlocal(3)).__nonzero__());

               var1.setline(176);
               var1.getlocal(0).__getattr__("_abc_cache").__getattr__("add").__call__(var2, var1.getlocal(1));
               var1.setline(177);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public abc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _C$1 = Py.newCode(0, var2, var1, "_C", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"funcobj"};
      abstractmethod$2 = Py.newCode(1, var2, var1, "abstractmethod", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      abstractproperty$3 = Py.newCode(0, var2, var1, "abstractproperty", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ABCMeta$4 = Py.newCode(0, var2, var1, "ABCMeta", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"mcls", "name", "bases", "namespace", "cls", "abstracts", "_(89_24)", "base", "value"};
      __new__$5 = Py.newCode(4, var2, var1, "__new__", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "name", "value"};
      f$6 = Py.newCode(1, var2, var1, "<genexpr>", 89, false, false, self, 6, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"cls", "subclass"};
      register$7 = Py.newCode(2, var2, var1, "register", 105, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "file", "name", "value"};
      _dump_registry$8 = Py.newCode(2, var2, var1, "_dump_registry", 119, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "instance", "subclass", "subtype"};
      __instancecheck__$9 = Py.newCode(2, var2, var1, "__instancecheck__", 128, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "subclass", "ok", "rcls", "scls"};
      __subclasscheck__$10 = Py.newCode(2, var2, var1, "__subclasscheck__", 148, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new abc$py("abc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(abc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._C$1(var2, var3);
         case 2:
            return this.abstractmethod$2(var2, var3);
         case 3:
            return this.abstractproperty$3(var2, var3);
         case 4:
            return this.ABCMeta$4(var2, var3);
         case 5:
            return this.__new__$5(var2, var3);
         case 6:
            return this.f$6(var2, var3);
         case 7:
            return this.register$7(var2, var3);
         case 8:
            return this._dump_registry$8(var2, var3);
         case 9:
            return this.__instancecheck__$9(var2, var3);
         case 10:
            return this.__subclasscheck__$10(var2, var3);
         default:
            return null;
      }
   }
}
