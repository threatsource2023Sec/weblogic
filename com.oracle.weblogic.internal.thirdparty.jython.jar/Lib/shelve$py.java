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
@Filename("shelve.py")
public class shelve$py extends PyFunctionTable implements PyRunnable {
   static shelve$py self;
   static final PyCode f$0;
   static final PyCode _ClosedDict$1;
   static final PyCode closed$2;
   static final PyCode __repr__$3;
   static final PyCode Shelf$4;
   static final PyCode __init__$5;
   static final PyCode keys$6;
   static final PyCode __len__$7;
   static final PyCode has_key$8;
   static final PyCode __contains__$9;
   static final PyCode get$10;
   static final PyCode __getitem__$11;
   static final PyCode __setitem__$12;
   static final PyCode __delitem__$13;
   static final PyCode close$14;
   static final PyCode __del__$15;
   static final PyCode sync$16;
   static final PyCode BsdDbShelf$17;
   static final PyCode __init__$18;
   static final PyCode set_location$19;
   static final PyCode next$20;
   static final PyCode previous$21;
   static final PyCode first$22;
   static final PyCode last$23;
   static final PyCode DbfilenameShelf$24;
   static final PyCode __init__$25;
   static final PyCode open$26;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Manage shelves of pickled objects.\n\nA \"shelf\" is a persistent, dictionary-like object.  The difference\nwith dbm databases is that the values (not the keys!) in a shelf can\nbe essentially arbitrary Python objects -- anything that the \"pickle\"\nmodule can handle.  This includes most class instances, recursive data\ntypes, and objects containing lots of shared sub-objects.  The keys\nare ordinary strings.\n\nTo summarize the interface (key is a string, data is an arbitrary\nobject):\n\n        import shelve\n        d = shelve.open(filename) # open, with (g)dbm filename -- no suffix\n\n        d[key] = data   # store data at key (overwrites old data if\n                        # using an existing key)\n        data = d[key]   # retrieve a COPY of the data at key (raise\n                        # KeyError if no such key) -- NOTE that this\n                        # access returns a *copy* of the entry!\n        del d[key]      # delete data stored at key (raises KeyError\n                        # if no such key)\n        flag = d.has_key(key)   # true if the key exists; same as \"key in d\"\n        list = d.keys() # a list of all existing keys (slow!)\n\n        d.close()       # close it\n\nDependent on the implementation, closing a persistent dictionary may\nor may not be necessary to flush changes to disk.\n\nNormally, d[key] returns a COPY of the entry.  This needs care when\nmutable entries are mutated: for example, if d[key] is a list,\n        d[key].append(anitem)\ndoes NOT modify the entry d[key] itself, as stored in the persistent\nmapping -- it only modifies the copy, which is then immediately\ndiscarded, so that the append has NO effect whatsoever.  To append an\nitem to d[key] in a way that will affect the persistent mapping, use:\n        data = d[key]\n        data.append(anitem)\n        d[key] = data\n\nTo avoid the problem with mutable entries, you may pass the keyword\nargument writeback=True in the call to shelve.open.  When you use:\n        d = shelve.open(filename, writeback=True)\nthen d keeps a cache of all entries you access, and writes them all back\nto the persistent mapping when you call d.close().  This ensures that\nsuch usage as d[key].append(anitem) works as intended.\n\nHowever, using keyword argument writeback=True may consume vast amount\nof memory for the cache, and it may make d.close() very slow, if you\naccess many of d's entries after opening it in this way: d has no way to\ncheck which of the entries you access are mutable and/or which ones you\nactually mutate, so it must cache, and write back at close, all of the\nentries that you access.  You can call d.sync() to write back all the\nentries in the cache, and empty the cache (d.sync() also synchronizes\nthe persistent dictionary on disk, if feasible).\n"));
      var1.setline(57);
      PyString.fromInterned("Manage shelves of pickled objects.\n\nA \"shelf\" is a persistent, dictionary-like object.  The difference\nwith dbm databases is that the values (not the keys!) in a shelf can\nbe essentially arbitrary Python objects -- anything that the \"pickle\"\nmodule can handle.  This includes most class instances, recursive data\ntypes, and objects containing lots of shared sub-objects.  The keys\nare ordinary strings.\n\nTo summarize the interface (key is a string, data is an arbitrary\nobject):\n\n        import shelve\n        d = shelve.open(filename) # open, with (g)dbm filename -- no suffix\n\n        d[key] = data   # store data at key (overwrites old data if\n                        # using an existing key)\n        data = d[key]   # retrieve a COPY of the data at key (raise\n                        # KeyError if no such key) -- NOTE that this\n                        # access returns a *copy* of the entry!\n        del d[key]      # delete data stored at key (raises KeyError\n                        # if no such key)\n        flag = d.has_key(key)   # true if the key exists; same as \"key in d\"\n        list = d.keys() # a list of all existing keys (slow!)\n\n        d.close()       # close it\n\nDependent on the implementation, closing a persistent dictionary may\nor may not be necessary to flush changes to disk.\n\nNormally, d[key] returns a COPY of the entry.  This needs care when\nmutable entries are mutated: for example, if d[key] is a list,\n        d[key].append(anitem)\ndoes NOT modify the entry d[key] itself, as stored in the persistent\nmapping -- it only modifies the copy, which is then immediately\ndiscarded, so that the append has NO effect whatsoever.  To append an\nitem to d[key] in a way that will affect the persistent mapping, use:\n        data = d[key]\n        data.append(anitem)\n        d[key] = data\n\nTo avoid the problem with mutable entries, you may pass the keyword\nargument writeback=True in the call to shelve.open.  When you use:\n        d = shelve.open(filename, writeback=True)\nthen d keeps a cache of all entries you access, and writes them all back\nto the persistent mapping when you call d.close().  This ensures that\nsuch usage as d[key].append(anitem) works as intended.\n\nHowever, using keyword argument writeback=True may consume vast amount\nof memory for the cache, and it may make d.close() very slow, if you\naccess many of d's entries after opening it in this way: d has no way to\ncheck which of the entries you access are mutable and/or which ones you\nactually mutate, so it must cache, and write back at close, all of the\nentries that you access.  You can call d.sync() to write back all the\nentries in the cache, and empty the cache (d.sync() also synchronizes\nthe persistent dictionary on disk, if feasible).\n");

      PyException var3;
      String[] var4;
      PyObject var5;
      String[] var8;
      PyObject[] var9;
      PyObject[] var10;
      PyObject var11;
      try {
         var1.setline(62);
         var8 = new String[]{"Pickler", "Unpickler"};
         var9 = imp.importFrom("cPickle", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("Pickler", var11);
         var4 = null;
         var11 = var9[1];
         var1.setlocal("Unpickler", var11);
         var4 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (!var3.match(var1.getname("ImportError"))) {
            throw var3;
         }

         var1.setline(64);
         var4 = new String[]{"Pickler", "Unpickler"};
         var10 = imp.importFrom("pickle", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("Pickler", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("Unpickler", var5);
         var5 = null;
      }

      try {
         var1.setline(67);
         var8 = new String[]{"StringIO"};
         var9 = imp.importFrom("cStringIO", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("StringIO", var11);
         var4 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getname("ImportError"))) {
            throw var3;
         }

         var1.setline(69);
         var4 = new String[]{"StringIO"};
         var10 = imp.importFrom("StringIO", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("StringIO", var5);
         var5 = null;
      }

      var1.setline(71);
      PyObject var12 = imp.importOne("UserDict", var1, -1);
      var1.setlocal("UserDict", var12);
      var3 = null;
      var1.setline(73);
      PyList var13 = new PyList(new PyObject[]{PyString.fromInterned("Shelf"), PyString.fromInterned("BsdDbShelf"), PyString.fromInterned("DbfilenameShelf"), PyString.fromInterned("open")});
      var1.setlocal("__all__", var13);
      var3 = null;
      var1.setline(75);
      var9 = new PyObject[]{var1.getname("UserDict").__getattr__("DictMixin")};
      var11 = Py.makeClass("_ClosedDict", var9, _ClosedDict$1);
      var1.setlocal("_ClosedDict", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(85);
      var9 = new PyObject[]{var1.getname("UserDict").__getattr__("DictMixin")};
      var11 = Py.makeClass("Shelf", var9, Shelf$4);
      var1.setlocal("Shelf", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(172);
      var9 = new PyObject[]{var1.getname("Shelf")};
      var11 = Py.makeClass("BsdDbShelf", var9, BsdDbShelf$17);
      var1.setlocal("BsdDbShelf", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(214);
      var9 = new PyObject[]{var1.getname("Shelf")};
      var11 = Py.makeClass("DbfilenameShelf", var9, DbfilenameShelf$24);
      var1.setlocal("DbfilenameShelf", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(226);
      var9 = new PyObject[]{PyString.fromInterned("c"), var1.getname("None"), var1.getname("False")};
      PyFunction var14 = new PyFunction(var1.f_globals, var9, open$26, PyString.fromInterned("Open a persistent dictionary for reading and writing.\n\n    The filename parameter is the base filename for the underlying\n    database.  As a side-effect, an extension may be added to the\n    filename and more than one file may be created.  The optional flag\n    parameter has the same interpretation as the flag parameter of\n    anydbm.open(). The optional protocol parameter specifies the\n    version of the pickle protocol (0, 1, or 2).\n\n    See the module's __doc__ string for an overview of the interface.\n    "));
      var1.setlocal("open", var14);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ClosedDict$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Marker for a closed dict.  Access attempts raise a ValueError."));
      var1.setline(76);
      PyString.fromInterned("Marker for a closed dict.  Access attempts raise a ValueError.");
      var1.setline(78);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, closed$2, (PyObject)null);
      var1.setlocal("closed", var4);
      var3 = null;
      var1.setline(80);
      PyObject var5 = var1.getname("closed");
      var1.setlocal("__getitem__", var5);
      var1.setlocal("__setitem__", var5);
      var1.setlocal("__delitem__", var5);
      var1.setlocal("keys", var5);
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject closed$2(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid operation on closed shelf")));
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyString var3 = PyString.fromInterned("<Closed Dictionary>");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Shelf$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for shelf implementations.\n\n    This is initialized with a dictionary-like object.\n    See the module's __doc__ string for an overview of the interface.\n    "));
      var1.setline(90);
      PyString.fromInterned("Base class for shelf implementations.\n\n    This is initialized with a dictionary-like object.\n    See the module's __doc__ string for an overview of the interface.\n    ");
      var1.setline(92);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$6, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$7, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$8, (PyObject)null);
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$9, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(112);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$10, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$11, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$12, (PyObject)null);
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$13, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$14, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$15, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(161);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sync$16, (PyObject)null);
      var1.setlocal("sync", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("dict", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(95);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(96);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_protocol", var3);
      var3 = null;
      var1.setline(97);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("writeback", var3);
      var3 = null;
      var1.setline(98);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"cache", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject keys$6(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$7(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dict"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$8(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$9(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$10(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(115);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __getitem__$11(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(119);
         var6 = var1.getlocal(0).__getattr__("cache").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(121);
         PyObject var4 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1)));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(122);
         var4 = var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(123);
         if (var1.getlocal(0).__getattr__("writeback").__nonzero__()) {
            var1.setline(124);
            var4 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("cache").__setitem__(var1.getlocal(1), var4);
            var4 = null;
         }
      }

      var1.setline(125);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject __setitem__$12(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("writeback").__nonzero__()) {
         var1.setline(129);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__getattr__("cache").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.setline(130);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getglobal("Pickler").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("_protocol"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(132);
      var1.getlocal(4).__getattr__("dump").__call__(var2, var1.getlocal(2));
      var1.setline(133);
      var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$13(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      var1.getlocal(0).__getattr__("dict").__delitem__(var1.getlocal(1));

      try {
         var1.setline(138);
         var1.getlocal(0).__getattr__("cache").__delitem__(var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(140);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$14(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      var1.getlocal(0).__getattr__("sync").__call__(var2);

      PyException var3;
      try {
         var1.setline(145);
         var1.getlocal(0).__getattr__("dict").__getattr__("close").__call__(var2);
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(147);
      }

      try {
         var1.setline(151);
         PyObject var7 = var1.getglobal("_ClosedDict").__call__(var2);
         var1.getlocal(0).__setattr__("dict", var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(new PyTuple(new PyObject[]{var1.getglobal("NameError"), var1.getglobal("TypeError")}))) {
            throw var3;
         }

         var1.setline(153);
         PyObject var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("dict", var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$15(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("writeback")).__not__().__nonzero__()) {
         var1.setline(158);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(159);
         var1.getlocal(0).__getattr__("close").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject sync$16(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var10000 = var1.getlocal(0).__getattr__("writeback");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("cache");
      }

      if (var10000.__nonzero__()) {
         var1.setline(163);
         PyObject var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("writeback", var3);
         var3 = null;
         var1.setline(164);
         var3 = var1.getlocal(0).__getattr__("cache").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(164);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(166);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("writeback", var3);
               var3 = null;
               var1.setline(167);
               PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"cache", var7);
               var3 = null;
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(165);
            PyObject var8 = var1.getlocal(2);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var8);
            var5 = null;
         }
      }

      var1.setline(168);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("dict"), (PyObject)PyString.fromInterned("sync")).__nonzero__()) {
         var1.setline(169);
         var1.getlocal(0).__getattr__("dict").__getattr__("sync").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BsdDbShelf$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Shelf implementation using the \"BSD\" db interface.\n\n    This adds methods first(), next(), previous(), last() and\n    set_location() that have no counterpart in [g]dbm databases.\n\n    The actual database must be opened using one of the \"bsddb\"\n    modules \"open\" routines (i.e. bsddb.hashopen, bsddb.btopen or\n    bsddb.rnopen) and passed to the constructor.\n\n    See the module's __doc__ string for an overview of the interface.\n    "));
      var1.setline(183);
      PyString.fromInterned("Shelf implementation using the \"BSD\" db interface.\n\n    This adds methods first(), next(), previous(), last() and\n    set_location() that have no counterpart in [g]dbm databases.\n\n    The actual database must be opened using one of the \"bsddb\"\n    modules \"open\" routines (i.e. bsddb.hashopen, bsddb.btopen or\n    bsddb.rnopen) and passed to the constructor.\n\n    See the module's __doc__ string for an overview of the interface.\n    ");
      var1.setline(185);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_location$19, (PyObject)null);
      var1.setlocal("set_location", var4);
      var3 = null;
      var1.setline(193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$20, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, previous$21, (PyObject)null);
      var1.setlocal("previous", var4);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, first$22, (PyObject)null);
      var1.setlocal("first", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, last$23, (PyObject)null);
      var1.setlocal("last", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      var1.getglobal("Shelf").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_location$19(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("set_location").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(190);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(191);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject next$20(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("next").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(195);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(196);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject previous$21(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("previous").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(200);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(201);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject first$22(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("first").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(205);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(206);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject last$23(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("last").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(210);
      var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(211);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("Unpickler").__call__(var2, var1.getlocal(3)).__getattr__("load").__call__(var2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject DbfilenameShelf$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Shelf implementation using the \"anydbm\" generic dbm interface.\n\n    This is initialized with the filename for the dbm database.\n    See the module's __doc__ string for an overview of the interface.\n    "));
      var1.setline(219);
      PyString.fromInterned("Shelf implementation using the \"anydbm\" generic dbm interface.\n\n    This is initialized with the filename for the dbm database.\n    See the module's __doc__ string for an overview of the interface.\n    ");
      var1.setline(221);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("c"), var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$25, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = imp.importOne("anydbm", var1, -1);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(223);
      var1.getglobal("Shelf").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(5).__getattr__("open").__call__(var2, var1.getlocal(1), var1.getlocal(2)), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$26(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Open a persistent dictionary for reading and writing.\n\n    The filename parameter is the base filename for the underlying\n    database.  As a side-effect, an extension may be added to the\n    filename and more than one file may be created.  The optional flag\n    parameter has the same interpretation as the flag parameter of\n    anydbm.open(). The optional protocol parameter specifies the\n    version of the pickle protocol (0, 1, or 2).\n\n    See the module's __doc__ string for an overview of the interface.\n    ");
      var1.setline(239);
      PyObject var3 = var1.getglobal("DbfilenameShelf").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public shelve$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ClosedDict$1 = Py.newCode(0, var2, var1, "_ClosedDict", 75, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      closed$2 = Py.newCode(2, var2, var1, "closed", 78, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 82, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Shelf$4 = Py.newCode(0, var2, var1, "Shelf", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dict", "protocol", "writeback"};
      __init__$5 = Py.newCode(4, var2, var1, "__init__", 92, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$6 = Py.newCode(1, var2, var1, "keys", 100, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$7 = Py.newCode(1, var2, var1, "__len__", 103, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$8 = Py.newCode(2, var2, var1, "has_key", 106, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$9 = Py.newCode(2, var2, var1, "__contains__", 109, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$10 = Py.newCode(3, var2, var1, "get", 112, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      __getitem__$11 = Py.newCode(2, var2, var1, "__getitem__", 117, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f", "p"};
      __setitem__$12 = Py.newCode(3, var2, var1, "__setitem__", 127, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$13 = Py.newCode(2, var2, var1, "__delitem__", 135, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$14 = Py.newCode(1, var2, var1, "close", 142, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$15 = Py.newCode(1, var2, var1, "__del__", 155, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "entry"};
      sync$16 = Py.newCode(1, var2, var1, "sync", 161, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BsdDbShelf$17 = Py.newCode(0, var2, var1, "BsdDbShelf", 172, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dict", "protocol", "writeback"};
      __init__$18 = Py.newCode(4, var2, var1, "__init__", 185, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      set_location$19 = Py.newCode(2, var2, var1, "set_location", 188, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      next$20 = Py.newCode(1, var2, var1, "next", 193, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      previous$21 = Py.newCode(1, var2, var1, "previous", 198, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      first$22 = Py.newCode(1, var2, var1, "first", 203, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "f"};
      last$23 = Py.newCode(1, var2, var1, "last", 208, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DbfilenameShelf$24 = Py.newCode(0, var2, var1, "DbfilenameShelf", 214, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "flag", "protocol", "writeback", "anydbm"};
      __init__$25 = Py.newCode(5, var2, var1, "__init__", 221, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "flag", "protocol", "writeback"};
      open$26 = Py.newCode(4, var2, var1, "open", 226, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new shelve$py("shelve$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(shelve$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._ClosedDict$1(var2, var3);
         case 2:
            return this.closed$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.Shelf$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.keys$6(var2, var3);
         case 7:
            return this.__len__$7(var2, var3);
         case 8:
            return this.has_key$8(var2, var3);
         case 9:
            return this.__contains__$9(var2, var3);
         case 10:
            return this.get$10(var2, var3);
         case 11:
            return this.__getitem__$11(var2, var3);
         case 12:
            return this.__setitem__$12(var2, var3);
         case 13:
            return this.__delitem__$13(var2, var3);
         case 14:
            return this.close$14(var2, var3);
         case 15:
            return this.__del__$15(var2, var3);
         case 16:
            return this.sync$16(var2, var3);
         case 17:
            return this.BsdDbShelf$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.set_location$19(var2, var3);
         case 20:
            return this.next$20(var2, var3);
         case 21:
            return this.previous$21(var2, var3);
         case 22:
            return this.first$22(var2, var3);
         case 23:
            return this.last$23(var2, var3);
         case 24:
            return this.DbfilenameShelf$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this.open$26(var2, var3);
         default:
            return null;
      }
   }
}
