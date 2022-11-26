import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@MTime(1498849383000L)
@Filename("copy_reg.py")
public class copy_reg$py extends PyFunctionTable implements PyRunnable {
   static copy_reg$py self;
   static final PyCode f$0;
   static final PyCode pickle$1;
   static final PyCode constructor$2;
   static final PyCode pickle_complex$3;
   static final PyCode _reconstructor$4;
   static final PyCode _reduce_ex$5;
   static final PyCode __newobj__$6;
   static final PyCode _slotnames$7;
   static final PyCode add_extension$8;
   static final PyCode remove_extension$9;
   static final PyCode clear_extension_cache$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Helper to provide extensibility for pickle/cPickle.\n\nThis is only useful to add pickle support for extension types defined in\nC, not for instances of user-defined classes.\n"));
      var1.setline(5);
      PyString.fromInterned("Helper to provide extensibility for pickle/cPickle.\n\nThis is only useful to add pickle support for extension types defined in\nC, not for instances of user-defined classes.\n");
      var1.setline(7);
      String[] var3 = new String[]{"ClassType"};
      PyObject[] var6 = imp.importFrom("types", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_ClassType", var4);
      var4 = null;
      var1.setline(9);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("pickle"), PyString.fromInterned("constructor"), PyString.fromInterned("add_extension"), PyString.fromInterned("remove_extension"), PyString.fromInterned("clear_extension_cache")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(12);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("dispatch_table", var8);
      var3 = null;
      var1.setline(14);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var11 = new PyFunction(var1.f_globals, var6, pickle$1, (PyObject)null);
      var1.setlocal("pickle", var11);
      var3 = null;
      var1.setline(27);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, constructor$2, (PyObject)null);
      var1.setlocal("constructor", var11);
      var3 = null;

      label19: {
         try {
            var1.setline(34);
            var1.getname("complex");
         } catch (Throwable var5) {
            PyException var12 = Py.setException(var5, var1);
            if (var12.match(var1.getname("NameError"))) {
               var1.setline(36);
               break label19;
            }

            throw var12;
         }

         var1.setline(39);
         PyObject[] var9 = Py.EmptyObjects;
         PyFunction var10 = new PyFunction(var1.f_globals, var9, pickle_complex$3, (PyObject)null);
         var1.setlocal("pickle_complex", var10);
         var4 = null;
         var1.setline(42);
         var1.getname("pickle").__call__(var2, var1.getname("complex"), var1.getname("pickle_complex"), var1.getname("complex"));
      }

      var1.setline(46);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, _reconstructor$4, (PyObject)null);
      var1.setlocal("_reconstructor", var11);
      var3 = null;
      var1.setline(55);
      PyObject var13 = Py.newInteger(1)._lshift(Py.newInteger(9));
      var1.setlocal("_HEAPTYPE", var13);
      var3 = null;
      var1.setline(59);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, _reduce_ex$5, (PyObject)null);
      var1.setlocal("_reduce_ex", var11);
      var3 = null;
      var1.setline(92);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, __newobj__$6, (PyObject)null);
      var1.setlocal("__newobj__", var11);
      var3 = null;
      var1.setline(95);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, _slotnames$7, PyString.fromInterned("Return a list of slot names for a given class.\n\n    This needs to find slots defined by the class and its bases, so we\n    can't simply return the __slots__ attribute.  We must walk down\n    the Method Resolution Order and concatenate the __slots__ of each\n    class found there.  (This assumes classes don't modify their\n    __slots__ attribute to misrepresent their slots after the class is\n    defined.)\n    "));
      var1.setlocal("_slotnames", var11);
      var3 = null;
      var1.setline(151);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_extension_registry", var8);
      var3 = null;
      var1.setline(152);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_inverted_registry", var8);
      var3 = null;
      var1.setline(153);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_extension_cache", var8);
      var3 = null;
      var1.setline(157);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, add_extension$8, PyString.fromInterned("Register an extension code."));
      var1.setlocal("add_extension", var11);
      var3 = null;
      var1.setline(175);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, remove_extension$9, PyString.fromInterned("Unregister an extension code.  For testing only."));
      var1.setlocal("remove_extension", var11);
      var3 = null;
      var1.setline(187);
      var6 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var6, clear_extension_cache$10, (PyObject)null);
      var1.setlocal("clear_extension_cache", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pickle$1(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._is(var1.getglobal("_ClassType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(16);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("copy_reg is not intended for use with classes")));
      } else {
         var1.setline(18);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
            var1.setline(19);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("reduction functions must be callable")));
         } else {
            var1.setline(20);
            var3 = var1.getlocal(1);
            var1.getglobal("dispatch_table").__setitem__(var1.getlocal(0), var3);
            var3 = null;
            var1.setline(24);
            var3 = var1.getlocal(2);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(25);
               var1.getglobal("constructor").__call__(var2, var1.getlocal(2));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject constructor$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
         var1.setline(29);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("constructors must be callable")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject pickle_complex$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("complex"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("real"), var1.getlocal(0).__getattr__("imag")})});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _reconstructor$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("object"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(50);
         var3 = var1.getlocal(1).__getattr__("__new__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(51);
         var3 = var1.getlocal(1).__getattr__("__init__");
         var10000 = var3._ne(var1.getglobal("object").__getattr__("__init__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(52);
            var1.getlocal(1).__getattr__("__init__").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         }
      }

      var1.setline(53);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _reduce_ex$5(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(61);
      var3 = var1.getlocal(0).__getattr__("__class__").__getattr__("__mro__").__iter__();

      PyObject var4;
      PyObject var5;
      do {
         var1.setline(61);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(65);
            var5 = var1.getglobal("object");
            var1.setlocal(2, var5);
            var5 = null;
            break;
         }

         var1.setlocal(2, var4);
         var1.setline(62);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__flags__"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getattr__("__flags__")._and(var1.getglobal("_HEAPTYPE")).__not__();
         }
      } while(!var10000.__nonzero__());

      var1.setline(66);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("object"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(67);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(69);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getlocal(0).__getattr__("__class__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(70);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("can't pickle %s objects")._mod(var1.getlocal(2).__getattr__("__name__")));
         }

         var1.setline(71);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(72);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(2), var1.getlocal(3)});
      var1.setlocal(4, var9);
      var3 = null;

      label61: {
         try {
            var1.setline(74);
            var3 = var1.getlocal(0).__getattr__("__getstate__");
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var7) {
            PyException var10 = Py.setException(var7, var1);
            if (var10.match(var1.getglobal("AttributeError"))) {
               var1.setline(76);
               if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__slots__"), (PyObject)var1.getglobal("None")).__nonzero__()) {
                  var1.setline(77);
                  throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a class that defines __slots__ without defining __getstate__ cannot be pickled")));
               }

               try {
                  var1.setline(80);
                  var4 = var1.getlocal(0).__getattr__("__dict__");
                  var1.setlocal(6, var4);
                  var4 = null;
                  break label61;
               } catch (Throwable var6) {
                  PyException var8 = Py.setException(var6, var1);
                  if (var8.match(var1.getglobal("AttributeError"))) {
                     var1.setline(82);
                     var5 = var1.getglobal("None");
                     var1.setlocal(6, var5);
                     var5 = null;
                     break label61;
                  }

                  throw var8;
               }
            }

            throw var10;
         }

         var1.setline(84);
         var4 = var1.getlocal(5).__call__(var2);
         var1.setlocal(6, var4);
         var4 = null;
      }

      var1.setline(85);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(86);
         var9 = new PyTuple(new PyObject[]{var1.getglobal("_reconstructor"), var1.getlocal(4), var1.getlocal(6)});
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(88);
         var9 = new PyTuple(new PyObject[]{var1.getglobal("_reconstructor"), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject __newobj__$6(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var10000 = var1.getlocal(0).__getattr__("__new__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _slotnames$7(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Return a list of slot names for a given class.\n\n    This needs to find slots defined by the class and its bases, so we\n    can't simply return the __slots__ attribute.  We must walk down\n    the Method Resolution Order and concatenate the __slots__ of each\n    class found there.  (This assumes classes don't modify their\n    __slots__ attribute to misrepresent their slots after the class is\n    defined.)\n    ");
      var1.setline(107);
      PyObject var3 = var1.getlocal(0).__getattr__("__dict__").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__slotnames__"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(109);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(112);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(113);
         PyObject var10;
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__slots__")).__not__().__nonzero__()) {
            var1.setline(115);
         } else {
            var1.setline(118);
            var10 = var1.getlocal(0).__getattr__("__mro__").__iter__();

            label51:
            while(true) {
               PyString var6;
               do {
                  var1.setline(118);
                  PyObject var5 = var10.__iternext__();
                  if (var5 == null) {
                     break label51;
                  }

                  var1.setlocal(2, var5);
                  var1.setline(119);
                  var6 = PyString.fromInterned("__slots__");
                  var10000 = var6._in(var1.getlocal(2).__getattr__("__dict__"));
                  var6 = null;
               } while(!var10000.__nonzero__());

               var1.setline(120);
               PyObject var11 = var1.getlocal(2).__getattr__("__dict__").__getitem__(PyString.fromInterned("__slots__"));
               var1.setlocal(3, var11);
               var6 = null;
               var1.setline(122);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
                  var1.setline(123);
                  PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(3)});
                  var1.setlocal(3, var12);
                  var6 = null;
               }

               var1.setline(124);
               var11 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(124);
                  PyObject var7 = var11.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  var1.setlocal(4, var7);
                  var1.setline(126);
                  PyObject var8 = var1.getlocal(4);
                  var10000 = var8._in(new PyTuple(new PyObject[]{PyString.fromInterned("__dict__"), PyString.fromInterned("__weakref__")}));
                  var8 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(129);
                     var10000 = var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__"));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(4).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__")).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(130);
                        var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("_%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("__name__"), var1.getlocal(4)})));
                     } else {
                        var1.setline(132);
                        var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
                     }
                  }
               }
            }
         }

         try {
            var1.setline(136);
            var10 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("__slotnames__", var10);
            var4 = null;
         } catch (Throwable var9) {
            Py.setException(var9, var1);
            var1.setline(138);
         }

         var1.setline(140);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject add_extension$8(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyString.fromInterned("Register an extension code.");
      var1.setline(159);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(160);
      PyInteger var5 = Py.newInteger(1);
      PyObject var10001 = var1.getlocal(2);
      PyInteger var10000 = var5;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(Py.newInteger(Integer.MAX_VALUE));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(161);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("code out of range"));
      } else {
         var1.setline(162);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(163);
         var3 = var1.getglobal("_extension_registry").__getattr__("get").__call__(var2, var1.getlocal(3));
         PyObject var7 = var3._eq(var1.getlocal(2));
         var3 = null;
         if (var7.__nonzero__()) {
            var3 = var1.getglobal("_inverted_registry").__getattr__("get").__call__(var2, var1.getlocal(2));
            var7 = var3._eq(var1.getlocal(3));
            var3 = null;
         }

         if (var7.__nonzero__()) {
            var1.setline(165);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(166);
            var3 = var1.getlocal(3);
            var7 = var3._in(var1.getglobal("_extension_registry"));
            var3 = null;
            if (var7.__nonzero__()) {
               var1.setline(167);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("key %s is already registered with code %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("_extension_registry").__getitem__(var1.getlocal(3))}))));
            } else {
               var1.setline(169);
               var3 = var1.getlocal(2);
               var7 = var3._in(var1.getglobal("_inverted_registry"));
               var3 = null;
               if (var7.__nonzero__()) {
                  var1.setline(170);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("code %s is already in use for key %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("_inverted_registry").__getitem__(var1.getlocal(2))}))));
               } else {
                  var1.setline(172);
                  var3 = var1.getlocal(2);
                  var1.getglobal("_extension_registry").__setitem__(var1.getlocal(3), var3);
                  var3 = null;
                  var1.setline(173);
                  var3 = var1.getlocal(3);
                  var1.getglobal("_inverted_registry").__setitem__(var1.getlocal(2), var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject remove_extension$9(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("Unregister an extension code.  For testing only.");
      var1.setline(177);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(178);
      PyObject var4 = var1.getglobal("_extension_registry").__getattr__("get").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var4._ne(var1.getlocal(2));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var4 = var1.getglobal("_inverted_registry").__getattr__("get").__call__(var2, var1.getlocal(2));
         var10000 = var4._ne(var1.getlocal(3));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(180);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("key %s is not registered with code %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)}))));
      } else {
         var1.setline(182);
         var1.getglobal("_extension_registry").__delitem__(var1.getlocal(3));
         var1.setline(183);
         var1.getglobal("_inverted_registry").__delitem__(var1.getlocal(2));
         var1.setline(184);
         var4 = var1.getlocal(2);
         var10000 = var4._in(var1.getglobal("_extension_cache"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(185);
            var1.getglobal("_extension_cache").__delitem__(var1.getlocal(2));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject clear_extension_cache$10(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      var1.getglobal("_extension_cache").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public copy_reg$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"ob_type", "pickle_function", "constructor_ob"};
      pickle$1 = Py.newCode(3, var2, var1, "pickle", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      constructor$2 = Py.newCode(1, var2, var1, "constructor", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"c"};
      pickle_complex$3 = Py.newCode(1, var2, var1, "pickle_complex", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "base", "state", "obj"};
      _reconstructor$4 = Py.newCode(3, var2, var1, "_reconstructor", 46, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "proto", "base", "state", "args", "getstate", "dict"};
      _reduce_ex$5 = Py.newCode(2, var2, var1, "_reduce_ex", 59, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "args"};
      __newobj__$6 = Py.newCode(2, var2, var1, "__newobj__", 92, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "names", "c", "slots", "name"};
      _slotnames$7 = Py.newCode(1, var2, var1, "_slotnames", 95, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "name", "code", "key"};
      add_extension$8 = Py.newCode(3, var2, var1, "add_extension", 157, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "name", "code", "key"};
      remove_extension$9 = Py.newCode(3, var2, var1, "remove_extension", 175, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      clear_extension_cache$10 = Py.newCode(0, var2, var1, "clear_extension_cache", 187, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new copy_reg$py("copy_reg$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(copy_reg$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.pickle$1(var2, var3);
         case 2:
            return this.constructor$2(var2, var3);
         case 3:
            return this.pickle_complex$3(var2, var3);
         case 4:
            return this._reconstructor$4(var2, var3);
         case 5:
            return this._reduce_ex$5(var2, var3);
         case 6:
            return this.__newobj__$6(var2, var3);
         case 7:
            return this._slotnames$7(var2, var3);
         case 8:
            return this.add_extension$8(var2, var3);
         case 9:
            return this.remove_extension$9(var2, var3);
         case 10:
            return this.clear_extension_cache$10(var2, var3);
         default:
            return null;
      }
   }
}
