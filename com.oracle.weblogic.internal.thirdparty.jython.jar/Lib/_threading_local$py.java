import java.util.Arrays;
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
@Filename("_threading_local.py")
public class _threading_local$py extends PyFunctionTable implements PyRunnable {
   static _threading_local$py self;
   static final PyCode f$0;
   static final PyCode _localbase$1;
   static final PyCode __new__$2;
   static final PyCode _patch$3;
   static final PyCode local$4;
   static final PyCode __getattribute__$5;
   static final PyCode __setattr__$6;
   static final PyCode __delattr__$7;
   static final PyCode __del__$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Thread-local objects.\n\n(Note that this module provides a Python version of the threading.local\n class.  Depending on the version of Python you're using, there may be a\n faster one available.  You should always import the `local` class from\n `threading`.)\n\nThread-local objects support the management of thread-local data.\nIf you have data that you want to be local to a thread, simply create\na thread-local object and use its attributes:\n\n  >>> mydata = local()\n  >>> mydata.number = 42\n  >>> mydata.number\n  42\n\nYou can also access the local-object's dictionary:\n\n  >>> mydata.__dict__\n  {'number': 42}\n  >>> mydata.__dict__.setdefault('widgets', [])\n  []\n  >>> mydata.widgets\n  []\n\nWhat's important about thread-local objects is that their data are\nlocal to a thread. If we access the data in a different thread:\n\n  >>> log = []\n  >>> def f():\n  ...     items = mydata.__dict__.items()\n  ...     items.sort()\n  ...     log.append(items)\n  ...     mydata.number = 11\n  ...     log.append(mydata.number)\n\n  >>> import threading\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n  >>> log\n  [[], 11]\n\nwe get different data.  Furthermore, changes made in the other thread\ndon't affect data seen in this thread:\n\n  >>> mydata.number\n  42\n\nOf course, values you get from a local object, including a __dict__\nattribute, are for whatever thread was current at the time the\nattribute was read.  For that reason, you generally don't want to save\nthese values across threads, as they apply only to the thread they\ncame from.\n\nYou can create custom local objects by subclassing the local class:\n\n  >>> class MyLocal(local):\n  ...     number = 2\n  ...     initialized = False\n  ...     def __init__(self, **kw):\n  ...         if self.initialized:\n  ...             raise SystemError('__init__ called too many times')\n  ...         self.initialized = True\n  ...         self.__dict__.update(kw)\n  ...     def squared(self):\n  ...         return self.number ** 2\n\nThis can be useful to support default values, methods and\ninitialization.  Note that if you define an __init__ method, it will be\ncalled each time the local object is used in a separate thread.  This\nis necessary to initialize each thread's dictionary.\n\nNow if we create a local object:\n\n  >>> mydata = MyLocal(color='red')\n\nNow we have a default number:\n\n  >>> mydata.number\n  2\n\nan initial color:\n\n  >>> mydata.color\n  'red'\n  >>> del mydata.color\n\nAnd a method that operates on the data:\n\n  >>> mydata.squared()\n  4\n\nAs before, we can access the data in a separate thread:\n\n  >>> log = []\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n  >>> log\n  [[('color', 'red'), ('initialized', True)], 11]\n\nwithout affecting this thread's data:\n\n  >>> mydata.number\n  2\n  >>> mydata.color\n  Traceback (most recent call last):\n  ...\n  AttributeError: 'MyLocal' object has no attribute 'color'\n\nNote that subclasses can define slots, but they are not thread\nlocal. They are shared across threads:\n\n  >>> class MyLocal(local):\n  ...     __slots__ = 'number'\n\n  >>> mydata = MyLocal()\n  >>> mydata.number = 42\n  >>> mydata.color = 'red'\n\nSo, the separate thread:\n\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n\naffects what we see:\n\n  >>> mydata.number\n  11\n\n>>> del mydata\n"));
      var1.setline(134);
      PyString.fromInterned("Thread-local objects.\n\n(Note that this module provides a Python version of the threading.local\n class.  Depending on the version of Python you're using, there may be a\n faster one available.  You should always import the `local` class from\n `threading`.)\n\nThread-local objects support the management of thread-local data.\nIf you have data that you want to be local to a thread, simply create\na thread-local object and use its attributes:\n\n  >>> mydata = local()\n  >>> mydata.number = 42\n  >>> mydata.number\n  42\n\nYou can also access the local-object's dictionary:\n\n  >>> mydata.__dict__\n  {'number': 42}\n  >>> mydata.__dict__.setdefault('widgets', [])\n  []\n  >>> mydata.widgets\n  []\n\nWhat's important about thread-local objects is that their data are\nlocal to a thread. If we access the data in a different thread:\n\n  >>> log = []\n  >>> def f():\n  ...     items = mydata.__dict__.items()\n  ...     items.sort()\n  ...     log.append(items)\n  ...     mydata.number = 11\n  ...     log.append(mydata.number)\n\n  >>> import threading\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n  >>> log\n  [[], 11]\n\nwe get different data.  Furthermore, changes made in the other thread\ndon't affect data seen in this thread:\n\n  >>> mydata.number\n  42\n\nOf course, values you get from a local object, including a __dict__\nattribute, are for whatever thread was current at the time the\nattribute was read.  For that reason, you generally don't want to save\nthese values across threads, as they apply only to the thread they\ncame from.\n\nYou can create custom local objects by subclassing the local class:\n\n  >>> class MyLocal(local):\n  ...     number = 2\n  ...     initialized = False\n  ...     def __init__(self, **kw):\n  ...         if self.initialized:\n  ...             raise SystemError('__init__ called too many times')\n  ...         self.initialized = True\n  ...         self.__dict__.update(kw)\n  ...     def squared(self):\n  ...         return self.number ** 2\n\nThis can be useful to support default values, methods and\ninitialization.  Note that if you define an __init__ method, it will be\ncalled each time the local object is used in a separate thread.  This\nis necessary to initialize each thread's dictionary.\n\nNow if we create a local object:\n\n  >>> mydata = MyLocal(color='red')\n\nNow we have a default number:\n\n  >>> mydata.number\n  2\n\nan initial color:\n\n  >>> mydata.color\n  'red'\n  >>> del mydata.color\n\nAnd a method that operates on the data:\n\n  >>> mydata.squared()\n  4\n\nAs before, we can access the data in a separate thread:\n\n  >>> log = []\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n  >>> log\n  [[('color', 'red'), ('initialized', True)], 11]\n\nwithout affecting this thread's data:\n\n  >>> mydata.number\n  2\n  >>> mydata.color\n  Traceback (most recent call last):\n  ...\n  AttributeError: 'MyLocal' object has no attribute 'color'\n\nNote that subclasses can define slots, but they are not thread\nlocal. They are shared across threads:\n\n  >>> class MyLocal(local):\n  ...     __slots__ = 'number'\n\n  >>> mydata = MyLocal()\n  >>> mydata.number = 42\n  >>> mydata.color = 'red'\n\nSo, the separate thread:\n\n  >>> thread = threading.Thread(target=f)\n  >>> thread.start()\n  >>> thread.join()\n\naffects what we see:\n\n  >>> mydata.number\n  11\n\n>>> del mydata\n");
      var1.setline(136);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("local")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(148);
      PyObject[] var5 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("_localbase", var5, _localbase$1);
      var1.setlocal("_localbase", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(169);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, _patch$3, (PyObject)null);
      var1.setlocal("_patch", var6);
      var3 = null;
      var1.setline(186);
      var5 = new PyObject[]{var1.getname("_localbase")};
      var4 = Py.makeClass("local", var5, local$4);
      var1.setlocal("local", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(251);
      String[] var7 = new String[]{"current_thread", "RLock"};
      var5 = imp.importFrom("threading", var7, var1, -1);
      var4 = var5[0];
      var1.setlocal("current_thread", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("RLock", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _localbase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(149);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_local__key"), PyString.fromInterned("_local__args"), PyString.fromInterned("_local__lock")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(151);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(153);
      PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("_local__key"), PyString.fromInterned("thread.local.")._add(var1.getglobal("str").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(3))))});
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(154);
      var1.getglobal("object").__getattr__("__setattr__").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("_local__key"), (PyObject)var1.getlocal(4));
      var1.setline(155);
      var1.getglobal("object").__getattr__("__setattr__").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("_local__args"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(156);
      var1.getglobal("object").__getattr__("__setattr__").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("_local__lock"), (PyObject)var1.getglobal("RLock").__call__(var2));
      var1.setline(158);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("__init__");
         var10000 = var3._is(var1.getglobal("object").__getattr__("__init__"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(159);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Initialization arguments are not supported")));
      } else {
         var1.setline(164);
         var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("__dict__"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(165);
         var3 = var1.getlocal(5);
         var1.getglobal("current_thread").__call__(var2).__getattr__("__dict__").__setitem__(var1.getlocal(4), var3);
         var3 = null;
         var1.setline(167);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _patch$3(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__key"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getglobal("current_thread").__call__(var2).__getattr__("__dict__").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(174);
         var3 = var1.getlocal(2);
         var1.getglobal("current_thread").__call__(var2).__getattr__("__dict__").__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.setline(175);
         var1.getglobal("object").__getattr__("__setattr__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__dict__"), (PyObject)var1.getlocal(2));
         var1.setline(179);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(180);
         var3 = var1.getlocal(3).__getattr__("__init__");
         var10000 = var3._isnot(var1.getglobal("object").__getattr__("__init__"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(181);
            var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__args"));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(182);
            var10000 = var1.getlocal(3).__getattr__("__init__");
            PyObject[] var8 = new PyObject[]{var1.getlocal(0)};
            String[] var6 = new String[0];
            var10000._callextra(var8, var6, var1.getlocal(4), var1.getlocal(5));
            var3 = null;
         }
      } else {
         var1.setline(184);
         var1.getglobal("object").__getattr__("__setattr__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__dict__"), (PyObject)var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject local$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(188);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getattribute__$5, (PyObject)null);
      var1.setlocal("__getattribute__", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setattr__$6, (PyObject)null);
      var1.setlocal("__setattr__", var4);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delattr__$7, (PyObject)null);
      var1.setlocal("__delattr__", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$8, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getattribute__$5(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__lock"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(190);
      var1.getlocal(2).__getattr__("acquire").__call__(var2);
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(192);
            var1.getglobal("_patch").__call__(var2, var1.getlocal(0));
            var1.setline(193);
            var4 = var1.getglobal("object").__getattr__("__getattribute__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(195);
         var1.getlocal(2).__getattr__("release").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(195);
      var1.getlocal(2).__getattr__("release").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject __setattr__$6(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("__dict__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(199);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("%r object attribute '__dict__' is read-only")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"))));
      } else {
         var1.setline(202);
         var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__lock"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(203);
         var1.getlocal(3).__getattr__("acquire").__call__(var2);
         var3 = null;

         Throwable var8;
         label29: {
            boolean var10001;
            PyObject var4;
            try {
               var1.setline(205);
               var1.getglobal("_patch").__call__(var2, var1.getlocal(0));
               var1.setline(206);
               var4 = var1.getglobal("object").__getattr__("__setattr__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
            } catch (Throwable var6) {
               var8 = var6;
               var10001 = false;
               break label29;
            }

            var1.setline(208);
            var1.getlocal(3).__getattr__("release").__call__(var2);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var8 = var5;
               var10001 = false;
            }
         }

         Throwable var7 = var8;
         Py.addTraceback(var7, var1);
         var1.setline(208);
         var1.getlocal(3).__getattr__("release").__call__(var2);
         throw (Throwable)var7;
      }
   }

   public PyObject __delattr__$7(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("__dict__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("%r object attribute '__dict__' is read-only")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"))));
      } else {
         var1.setline(215);
         var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__lock"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(216);
         var1.getlocal(2).__getattr__("acquire").__call__(var2);
         var3 = null;

         Throwable var8;
         label29: {
            boolean var10001;
            PyObject var4;
            try {
               var1.setline(218);
               var1.getglobal("_patch").__call__(var2, var1.getlocal(0));
               var1.setline(219);
               var4 = var1.getglobal("object").__getattr__("__delattr__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            } catch (Throwable var6) {
               var8 = var6;
               var10001 = false;
               break label29;
            }

            var1.setline(221);
            var1.getlocal(2).__getattr__("release").__call__(var2);

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var5) {
               var8 = var5;
               var10001 = false;
            }
         }

         Throwable var7 = var8;
         Py.addTraceback(var7, var1);
         var1.setline(221);
         var1.getlocal(2).__getattr__("release").__call__(var2);
         throw (Throwable)var7;
      }
   }

   public PyObject __del__$8(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject var3 = imp.importOne("threading", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getglobal("object").__getattr__("__getattribute__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_local__key"));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(231);
         var3 = var1.getlocal(1).__getattr__("_enumerate").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(236);
         var1.f_lasti = -1;
         return Py.None;
      }

      var1.setline(238);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(238);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);

         PyException var5;
         PyObject var9;
         try {
            var1.setline(240);
            var9 = var1.getlocal(4).__getattr__("__dict__");
            var1.setlocal(5, var9);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(245);
         var9 = var1.getlocal(2);
         PyObject var10000 = var9._in(var1.getlocal(5));
         var5 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(247);
               var1.getlocal(5).__delitem__(var1.getlocal(2));
            } catch (Throwable var8) {
               var5 = Py.setException(var8, var1);
               if (!var5.match(var1.getglobal("KeyError"))) {
                  throw var5;
               }

               var1.setline(249);
            }
         }
      }
   }

   public _threading_local$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _localbase$1 = Py.newCode(0, var2, var1, "_localbase", 148, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "args", "kw", "self", "key", "dict"};
      __new__$2 = Py.newCode(3, var2, var1, "__new__", 151, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "d", "cls", "args", "kw"};
      _patch$3 = Py.newCode(1, var2, var1, "_patch", 169, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      local$4 = Py.newCode(0, var2, var1, "local", 186, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "lock"};
      __getattribute__$5 = Py.newCode(2, var2, var1, "__getattribute__", 188, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "lock"};
      __setattr__$6 = Py.newCode(3, var2, var1, "__setattr__", 197, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "lock"};
      __delattr__$7 = Py.newCode(2, var2, var1, "__delattr__", 210, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "threading", "key", "threads", "thread", "__dict__"};
      __del__$8 = Py.newCode(1, var2, var1, "__del__", 223, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _threading_local$py("_threading_local$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_threading_local$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._localbase$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this._patch$3(var2, var3);
         case 4:
            return this.local$4(var2, var3);
         case 5:
            return this.__getattribute__$5(var2, var3);
         case 6:
            return this.__setattr__$6(var2, var3);
         case 7:
            return this.__delattr__$7(var2, var3);
         case 8:
            return this.__del__$8(var2, var3);
         default:
            return null;
      }
   }
}
