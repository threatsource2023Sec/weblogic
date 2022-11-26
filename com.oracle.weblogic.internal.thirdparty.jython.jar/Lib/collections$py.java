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
@Filename("collections.py")
public class collections$py extends PyFunctionTable implements PyRunnable {
   static collections$py self;
   static final PyCode f$0;
   static final PyCode OrderedDict$1;
   static final PyCode __init__$2;
   static final PyCode __setitem__$3;
   static final PyCode __delitem__$4;
   static final PyCode __iter__$5;
   static final PyCode __reversed__$6;
   static final PyCode clear$7;
   static final PyCode keys$8;
   static final PyCode values$9;
   static final PyCode items$10;
   static final PyCode iterkeys$11;
   static final PyCode itervalues$12;
   static final PyCode iteritems$13;
   static final PyCode pop$14;
   static final PyCode setdefault$15;
   static final PyCode popitem$16;
   static final PyCode __repr__$17;
   static final PyCode __reduce__$18;
   static final PyCode copy$19;
   static final PyCode fromkeys$20;
   static final PyCode __eq__$21;
   static final PyCode __ne__$22;
   static final PyCode viewkeys$23;
   static final PyCode viewvalues$24;
   static final PyCode viewitems$25;
   static final PyCode namedtuple$26;
   static final PyCode f$27;
   static final PyCode f$28;
   static final PyCode f$29;
   static final PyCode f$30;
   static final PyCode Counter$31;
   static final PyCode __init__$32;
   static final PyCode __missing__$33;
   static final PyCode most_common$34;
   static final PyCode elements$35;
   static final PyCode fromkeys$36;
   static final PyCode update$37;
   static final PyCode subtract$38;
   static final PyCode copy$39;
   static final PyCode __reduce__$40;
   static final PyCode __delitem__$41;
   static final PyCode __repr__$42;
   static final PyCode __add__$43;
   static final PyCode __sub__$44;
   static final PyCode __or__$45;
   static final PyCode __and__$46;
   static final PyCode Point$47;
   static final PyCode hypot$48;
   static final PyCode __str__$49;
   static final PyCode Point$50;
   static final PyCode _replace$51;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Counter"), PyString.fromInterned("deque"), PyString.fromInterned("defaultdict"), PyString.fromInterned("namedtuple"), PyString.fromInterned("OrderedDict")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(4);
      imp.importAll("_abcoll", var1, -1);
      var1.setline(5);
      PyObject var7 = imp.importOne("_abcoll", var1, -1);
      var1.setlocal("_abcoll", var7);
      var3 = null;
      var1.setline(6);
      var7 = var1.getname("__all__");
      var7 = var7._iadd(var1.getname("_abcoll").__getattr__("__all__"));
      var1.setlocal("__all__", var7);
      var1.setline(8);
      String[] var8 = new String[]{"deque", "defaultdict"};
      PyObject[] var9 = imp.importFrom("_collections", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("deque", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("defaultdict", var4);
      var4 = null;
      var1.setline(9);
      var8 = new String[]{"itemgetter", "eq"};
      var9 = imp.importFrom("operator", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_itemgetter", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("_eq", var4);
      var4 = null;
      var1.setline(10);
      var8 = new String[]{"iskeyword"};
      var9 = imp.importFrom("keyword", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_iskeyword", var4);
      var4 = null;
      var1.setline(11);
      var7 = imp.importOneAs("sys", var1, -1);
      var1.setlocal("_sys", var7);
      var3 = null;
      var1.setline(12);
      var7 = imp.importOneAs("heapq", var1, -1);
      var1.setlocal("_heapq", var7);
      var3 = null;
      var1.setline(13);
      var8 = new String[]{"repeat", "chain", "starmap"};
      var9 = imp.importFrom("itertools", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_repeat", var4);
      var4 = null;
      var4 = var9[1];
      var1.setlocal("_chain", var4);
      var4 = null;
      var4 = var9[2];
      var1.setlocal("_starmap", var4);
      var4 = null;
      var1.setline(14);
      var8 = new String[]{"imap"};
      var9 = imp.importFrom("itertools", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("_imap", var4);
      var4 = null;

      String[] var11;
      try {
         var1.setline(17);
         var8 = new String[]{"get_ident"};
         var9 = imp.importFrom("thread", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("_get_ident", var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(19);
         var11 = new String[]{"get_ident"};
         PyObject[] var12 = imp.importFrom("dummy_thread", var11, var1, -1);
         PyObject var5 = var12[0];
         var1.setlocal("_get_ident", var5);
         var5 = null;
      }

      var1.setline(26);
      var9 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("OrderedDict", var9, OrderedDict$1);
      var1.setlocal("OrderedDict", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(234);
      PyString var13 = PyString.fromInterned("class {typename}(tuple):\n    '{typename}({arg_list})'\n\n    __slots__ = ()\n\n    _fields = {field_names!r}\n\n    def __new__(_cls, {arg_list}):\n        'Create new instance of {typename}({arg_list})'\n        return _tuple.__new__(_cls, ({arg_list}))\n\n    @classmethod\n    def _make(cls, iterable, new=tuple.__new__, len=len):\n        'Make a new {typename} object from a sequence or iterable'\n        result = new(cls, iterable)\n        if len(result) != {num_fields:d}:\n            raise TypeError('Expected {num_fields:d} arguments, got %d' % len(result))\n        return result\n\n    def __repr__(self):\n        'Return a nicely formatted representation string'\n        return '{typename}({repr_fmt})' % self\n\n    def _asdict(self):\n        'Return a new OrderedDict which maps field names to their values'\n        return OrderedDict(zip(self._fields, self))\n\n    __dict__ = property(_asdict)\n\n    def _replace(_self, **kwds):\n        'Return a new {typename} object replacing specified fields with new values'\n        result = _self._make(map(kwds.pop, {field_names!r}, _self))\n        if kwds:\n            raise ValueError('Got unexpected field names: %r' % kwds.keys())\n        return result\n\n    def __getnewargs__(self):\n        'Return self as a plain tuple.  Used by copy and pickle.'\n        return tuple(self)\n\n{field_defs}\n");
      var1.setlocal("_class_template", var13);
      var3 = null;
      var1.setline(278);
      var13 = PyString.fromInterned("{name}=%r");
      var1.setlocal("_repr_template", var13);
      var3 = null;
      var1.setline(280);
      var13 = PyString.fromInterned("    {name} = _property(_itemgetter({index:d}), doc='Alias for field number {index:d}')\n");
      var1.setlocal("_field_template", var13);
      var3 = null;
      var1.setline(284);
      var9 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      PyFunction var14 = new PyFunction(var1.f_globals, var9, namedtuple$26, PyString.fromInterned("Returns a new subclass of tuple with named fields.\n\n    >>> Point = namedtuple('Point', ['x', 'y'])\n    >>> Point.__doc__                   # docstring for the new class\n    'Point(x, y)'\n    >>> p = Point(11, y=22)             # instantiate with positional args or keywords\n    >>> p[0] + p[1]                     # indexable like a plain tuple\n    33\n    >>> x, y = p                        # unpack like a regular tuple\n    >>> x, y\n    (11, 22)\n    >>> p.x + p.y                       # fields also accessable by name\n    33\n    >>> d = p._asdict()                 # convert to a dictionary\n    >>> d['x']\n    11\n    >>> Point(**d)                      # convert from a dictionary\n    Point(x=11, y=22)\n    >>> p._replace(x=100)               # _replace() is like str.replace() but targets named fields\n    Point(x=100, y=22)\n\n    "));
      var1.setlocal("namedtuple", var14);
      var3 = null;
      var1.setline(383);
      var9 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("Counter", var9, Counter$31);
      var1.setlocal("Counter", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(662);
      var7 = var1.getname("__name__");
      PyObject var10000 = var7._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(664);
         var8 = new String[]{"loads", "dumps"};
         var9 = imp.importFrom("cPickle", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("loads", var4);
         var4 = null;
         var4 = var9[1];
         var1.setlocal("dumps", var4);
         var4 = null;
         var1.setline(665);
         var7 = var1.getname("namedtuple").__call__((ThreadState)var2, PyString.fromInterned("Point"), (PyObject)PyString.fromInterned("x, y"), (PyObject)var1.getname("True"));
         var1.setlocal("Point", var7);
         var3 = null;
         var1.setline(666);
         var10000 = var1.getname("Point");
         var9 = new PyObject[]{Py.newInteger(10), Py.newInteger(20)};
         var11 = new String[]{"x", "y"};
         var10000 = var10000.__call__(var2, var9, var11);
         var3 = null;
         var7 = var10000;
         var1.setlocal("p", var7);
         var3 = null;
         var1.setline(667);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var7 = var1.getname("p");
            var10000 = var7._eq(var1.getname("loads").__call__(var2, var1.getname("dumps").__call__(var2, var1.getname("p"))));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(670);
         var9 = new PyObject[]{var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Point"), (PyObject)PyString.fromInterned("x y"))};
         var4 = Py.makeClass("Point", var9, Point$47);
         var1.setlocal("Point", var4);
         var4 = null;
         Arrays.fill(var9, (Object)null);
         var1.setline(678);
         var7 = (new PyTuple(new PyObject[]{var1.getname("Point").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)Py.newInteger(4)), var1.getname("Point").__call__((ThreadState)var2, (PyObject)Py.newInteger(14), (PyObject)Py.newInteger(5)._div(Py.newFloat(7.0)))})).__iter__();

         while(true) {
            var1.setline(678);
            var4 = var7.__iternext__();
            if (var4 == null) {
               var1.setline(681);
               var9 = new PyObject[]{var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Point"), (PyObject)PyString.fromInterned("x y"))};
               var4 = Py.makeClass("Point", var9, Point$50);
               var1.setlocal("Point", var4);
               var4 = null;
               Arrays.fill(var9, (Object)null);
               var1.setline(688);
               var10000 = var1.getname("Point").__call__((ThreadState)var2, (PyObject)Py.newInteger(11), (PyObject)Py.newInteger(22)).__getattr__("_replace");
               var9 = new PyObject[]{Py.newInteger(100)};
               var11 = new String[]{"x"};
               var10000 = var10000.__call__(var2, var9, var11);
               var3 = null;
               Py.println(var10000);
               var1.setline(690);
               var7 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Point3D"), (PyObject)var1.getname("Point").__getattr__("_fields")._add(new PyTuple(new PyObject[]{PyString.fromInterned("z")})));
               var1.setlocal("Point3D", var7);
               var3 = null;
               var1.setline(691);
               Py.println(var1.getname("Point3D").__getattr__("__doc__"));
               var1.setline(693);
               var7 = imp.importOne("doctest", var1, -1);
               var1.setlocal("doctest", var7);
               var3 = null;
               var1.setline(694);
               var7 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestResults"), (PyObject)PyString.fromInterned("failed attempted"));
               var1.setlocal("TestResults", var7);
               var3 = null;
               var1.setline(695);
               var10000 = var1.getname("TestResults");
               var9 = Py.EmptyObjects;
               var11 = new String[0];
               var10000 = var10000._callextra(var9, var11, var1.getname("doctest").__getattr__("testmod").__call__(var2), (PyObject)null);
               var3 = null;
               Py.println(var10000);
               break;
            }

            var1.setlocal("p", var4);
            var1.setline(679);
            Py.println(var1.getname("p"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject OrderedDict$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dictionary that remembers insertion order"));
      var1.setline(27);
      PyString.fromInterned("Dictionary that remembers insertion order");
      var1.setline(38);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Initialize an ordered dictionary.  The signature is the same as\n        regular dictionaries, but keyword arguments are not recommended because\n        their insertion order is arbitrary.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(54);
      var3 = new PyObject[]{var1.getname("dict").__getattr__("__setitem__")};
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$3, PyString.fromInterned("od.__setitem__(i, y) <==> od[i]=y"));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(64);
      var3 = new PyObject[]{var1.getname("dict").__getattr__("__delitem__")};
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$4, PyString.fromInterned("od.__delitem__(y) <==> del od[y]"));
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$5, PyString.fromInterned("od.__iter__() <==> iter(od)"));
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __reversed__$6, PyString.fromInterned("od.__reversed__() <==> reversed(od)"));
      var1.setlocal("__reversed__", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$7, PyString.fromInterned("od.clear() -> None.  Remove all items from od."));
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$8, PyString.fromInterned("od.keys() -> list of keys in od"));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$9, PyString.fromInterned("od.values() -> list of values in od"));
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$10, PyString.fromInterned("od.items() -> list of (key, value) pairs in od"));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$11, PyString.fromInterned("od.iterkeys() -> an iterator over the keys in od"));
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(116);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervalues$12, PyString.fromInterned("od.itervalues -> an iterator over the values in od"));
      var1.setlocal("itervalues", var4);
      var3 = null;
      var1.setline(121);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iteritems$13, PyString.fromInterned("od.iteritems -> an iterator over the (key, value) pairs in od"));
      var1.setlocal("iteritems", var4);
      var3 = null;
      var1.setline(126);
      PyObject var5 = var1.getname("MutableMapping").__getattr__("update");
      var1.setlocal("update", var5);
      var3 = null;
      var1.setline(128);
      var5 = var1.getname("update");
      var1.setlocal("_OrderedDict__update", var5);
      var3 = null;
      var1.setline(130);
      var5 = var1.getname("object").__call__(var2);
      var1.setlocal("_OrderedDict__marker", var5);
      var3 = null;
      var1.setline(132);
      var3 = new PyObject[]{var1.getname("_OrderedDict__marker")};
      var4 = new PyFunction(var1.f_globals, var3, pop$14, PyString.fromInterned("od.pop(k[,d]) -> v, remove specified key and return the corresponding\n        value.  If key is not found, d is returned if given, otherwise KeyError\n        is raised.\n\n        "));
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(146);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$15, PyString.fromInterned("od.setdefault(k[,d]) -> od.get(k,d), also set od[k]=d if k not in od"));
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(153);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, popitem$16, PyString.fromInterned("od.popitem() -> (k, v), return and remove a (key, value) pair.\n        Pairs are returned in LIFO order if last is true or FIFO order if false.\n\n        "));
      var1.setlocal("popitem", var4);
      var3 = null;
      var1.setline(164);
      var3 = new PyObject[]{new PyDictionary(Py.EmptyObjects)};
      var4 = new PyFunction(var1.f_globals, var3, __repr__$17, PyString.fromInterned("od.__repr__() <==> repr(od)"));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __reduce__$18, PyString.fromInterned("Return state information for pickling"));
      var1.setlocal("__reduce__", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$19, PyString.fromInterned("od.copy() -> a shallow copy of od"));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(191);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fromkeys$20, PyString.fromInterned("OD.fromkeys(S[, v]) -> New ordered dictionary with keys from S.\n        If not specified, the value defaults to None.\n\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fromkeys", var5);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$21, PyString.fromInterned("od.__eq__(y) <==> od==y.  Comparison to another OD is order-sensitive\n        while comparison to a regular mapping is order-insensitive.\n\n        "));
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$22, PyString.fromInterned("od.__ne__(y) <==> od!=y"));
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, viewkeys$23, PyString.fromInterned("od.viewkeys() -> a set-like object providing a view on od's keys"));
      var1.setlocal("viewkeys", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, viewvalues$24, PyString.fromInterned("od.viewvalues() -> an object providing a view on od's values"));
      var1.setlocal("viewvalues", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, viewitems$25, PyString.fromInterned("od.viewitems() -> a set-like object providing a view on od's items"));
      var1.setlocal("viewitems", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyString.fromInterned("Initialize an ordered dictionary.  The signature is the same as\n        regular dictionaries, but keyword arguments are not recommended because\n        their insertion order is arbitrary.\n\n        ");
      var1.setline(44);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(45);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("expected at most 1 arguments, got %d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
      } else {
         try {
            var1.setline(47);
            var1.getlocal(0).__getattr__("_OrderedDict__root");
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("AttributeError"))) {
               throw var6;
            }

            var1.setline(49);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"_OrderedDict__root", var4);
            var1.setlocal(3, var4);
            var1.setline(50);
            var4 = new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(3), var1.getglobal("None")});
            var1.getlocal(3).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var4);
            var4 = null;
            var1.setline(51);
            PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"_OrderedDict__map", var8);
            var4 = null;
         }

         var1.setline(52);
         var10000 = var1.getlocal(0).__getattr__("_OrderedDict__update");
         PyObject[] var7 = Py.EmptyObjects;
         String[] var9 = new String[0];
         var10000._callextra(var7, var9, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __setitem__$3(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("od.__setitem__(i, y) <==> od[i]=y");
      var1.setline(58);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(59);
         var3 = var1.getlocal(0).__getattr__("_OrderedDict__root");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(60);
         var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(61);
         PyList var4 = new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(4), var1.getlocal(1)});
         var1.getlocal(5).__setitem__((PyObject)Py.newInteger(1), var4);
         var1.getlocal(4).__setitem__((PyObject)Py.newInteger(0), var4);
         var1.getlocal(0).__getattr__("_OrderedDict__map").__setitem__((PyObject)var1.getlocal(1), var4);
      }

      var1.setline(62);
      var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __delitem__$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyString.fromInterned("od.__delitem__(y) <==> del od[y]");
      var1.setline(68);
      var1.getlocal(2).__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("_OrderedDict__map").__getattr__("pop").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(70);
      var3 = var1.getlocal(4);
      var1.getlocal(3).__setitem__((PyObject)Py.newInteger(1), var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setitem__((PyObject)Py.newInteger(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$5(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      PyObject var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(74);
            PyString.fromInterned("od.__iter__() <==> iter(od)");
            var1.setline(76);
            var4 = var1.getlocal(0).__getattr__("_OrderedDict__root");
            var1.setlocal(1, var4);
            var3 = null;
            var1.setline(77);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var1.setlocal(2, var4);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var5 = (PyObject)var10000;
            var1.setline(80);
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(2, var4);
            var3 = null;
      }

      var1.setline(78);
      var4 = var1.getlocal(2);
      var5 = var4._isnot(var1.getlocal(1));
      var3 = null;
      if (!var5.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(79);
         var1.setline(79);
         var5 = var1.getlocal(2).__getitem__(Py.newInteger(2));
         var1.f_lasti = 1;
         var3 = new Object[4];
         var1.f_savedlocals = var3;
         return var5;
      }
   }

   public PyObject __reversed__$6(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var4;
      PyObject var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(83);
            PyString.fromInterned("od.__reversed__() <==> reversed(od)");
            var1.setline(85);
            var4 = var1.getlocal(0).__getattr__("_OrderedDict__root");
            var1.setlocal(1, var4);
            var3 = null;
            var1.setline(86);
            var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var4);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var5 = (PyObject)var10000;
            var1.setline(89);
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var4);
            var3 = null;
      }

      var1.setline(87);
      var4 = var1.getlocal(2);
      var5 = var4._isnot(var1.getlocal(1));
      var3 = null;
      if (!var5.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(88);
         var1.setline(88);
         var5 = var1.getlocal(2).__getitem__(Py.newInteger(2));
         var1.f_lasti = 1;
         var3 = new Object[4];
         var1.f_savedlocals = var3;
         return var5;
      }
   }

   public PyObject clear$7(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("od.clear() -> None.  Remove all items from od.");
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__getattr__("_OrderedDict__root");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(94);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(1), var1.getlocal(1), var1.getglobal("None")});
      var1.getlocal(1).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var4);
      var3 = null;
      var1.setline(95);
      var1.getlocal(0).__getattr__("_OrderedDict__map").__getattr__("clear").__call__(var2);
      var1.setline(96);
      var1.getglobal("dict").__getattr__("clear").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject keys$8(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("od.keys() -> list of keys in od");
      var1.setline(102);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$9(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("od.values() -> list of values in od");
      var1.setline(106);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(106);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(106);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(106);
         var1.getlocal(1).__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)));
      }
   }

   public PyObject items$10(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("od.items() -> list of (key, value) pairs in od");
      var1.setline(110);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(110);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(110);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(110);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getitem__(var1.getlocal(2))})));
      }
   }

   public PyObject iterkeys$11(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyString.fromInterned("od.iterkeys() -> an iterator over the keys in od");
      var1.setline(114);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itervalues$12(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(117);
            PyString.fromInterned("od.itervalues -> an iterator over the values in od");
            var1.setline(118);
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

            var6 = (PyObject)var10000;
      }

      var1.setline(118);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(119);
         var1.setline(119);
         var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject iteritems$13(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(122);
            PyString.fromInterned("od.iteritems -> an iterator over the (key, value) pairs in od");
            var1.setline(123);
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

            PyObject var7 = (PyObject)var10000;
      }

      var1.setline(123);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(124);
         var1.setline(124);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getitem__(var1.getlocal(1))};
         PyTuple var8 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject pop$14(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("od.pop(k[,d]) -> v, remove specified key and return the corresponding\n        value.  If key is not found, d is returned if given, otherwise KeyError\n        is raised.\n\n        ");
      var1.setline(138);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(140);
         var1.getlocal(0).__delitem__(var1.getlocal(1));
         var1.setline(141);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(142);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getlocal(0).__getattr__("_OrderedDict__marker"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(143);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(144);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject setdefault$15(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyString.fromInterned("od.setdefault(k[,d]) -> od.get(k,d), also set od[k]=d if k not in od");
      var1.setline(148);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(149);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(150);
         PyObject var4 = var1.getlocal(2);
         var1.getlocal(0).__setitem__(var1.getlocal(1), var4);
         var4 = null;
         var1.setline(151);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject popitem$16(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyString.fromInterned("od.popitem() -> (k, v), return and remove a (key, value) pair.\n        Pairs are returned in LIFO order if last is true or FIFO order if false.\n\n        ");
      var1.setline(158);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(159);
         throw Py.makeException(var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dictionary is empty")));
      } else {
         var1.setline(160);
         PyObject var10000 = var1.getglobal("next");
         var1.setline(160);
         PyObject var3 = var10000.__call__(var2, var1.getlocal(1).__nonzero__() ? var1.getglobal("reversed").__call__(var2, var1.getlocal(0)) : var1.getglobal("iter").__call__(var2, var1.getlocal(0)));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(161);
         var3 = var1.getlocal(0).__getattr__("pop").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(162);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject __repr__$17(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyString.fromInterned("od.__repr__() <==> repr(od)");
      var1.setline(166);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getglobal("_get_ident").__call__(var2)});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(167);
      PyObject var9 = var1.getlocal(2);
      PyObject var10000 = var9._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         PyString var10 = PyString.fromInterned("...");
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(169);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var4);
         var4 = null;
         var4 = null;

         Throwable var12;
         label46: {
            boolean var10001;
            label47: {
               try {
                  var1.setline(171);
                  if (!var1.getlocal(0).__not__().__nonzero__()) {
                     break label47;
                  }

                  var1.setline(172);
                  var9 = PyString.fromInterned("%s()")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")}));
               } catch (Throwable var8) {
                  var12 = var8;
                  var10001 = false;
                  break label46;
               }

               var1.setline(175);
               var1.getlocal(1).__delitem__(var1.getlocal(2));

               try {
                  var1.f_lasti = -1;
                  return var9;
               } catch (Throwable var5) {
                  var12 = var5;
                  var10001 = false;
                  break label46;
               }
            }

            try {
               var1.setline(173);
               var9 = PyString.fromInterned("%s(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("items").__call__(var2)}));
            } catch (Throwable var7) {
               var12 = var7;
               var10001 = false;
               break label46;
            }

            var1.setline(175);
            var1.getlocal(1).__delitem__(var1.getlocal(2));

            try {
               var1.f_lasti = -1;
               return var9;
            } catch (Throwable var6) {
               var12 = var6;
               var10001 = false;
            }
         }

         Throwable var11 = var12;
         Py.addTraceback(var11, var1);
         var1.setline(175);
         var1.getlocal(1).__delitem__(var1.getlocal(2));
         throw (Throwable)var11;
      }
   }

   public PyObject __reduce__$18(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyString.fromInterned("Return state information for pickling");
      var1.setline(179);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(179);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(179);
            var1.dellocal(2);
            PyList var5 = var10000;
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(180);
            var3 = var1.getglobal("vars").__call__(var2, var1.getlocal(0)).__getattr__("copy").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(181);
            var3 = var1.getglobal("vars").__call__(var2, var1.getglobal("OrderedDict").__call__(var2)).__iter__();

            while(true) {
               var1.setline(181);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(183);
                  PyTuple var6;
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(184);
                     var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(new PyObject[]{var1.getlocal(1)}), var1.getlocal(4)});
                     var1.f_lasti = -1;
                     return var6;
                  } else {
                     var1.setline(185);
                     var6 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(new PyObject[]{var1.getlocal(1)})});
                     var1.f_lasti = -1;
                     return var6;
                  }
               }

               var1.setlocal(3, var4);
               var1.setline(182);
               var1.getlocal(4).__getattr__("pop").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(179);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getitem__(var1.getlocal(3))})));
      }
   }

   public PyObject copy$19(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("od.copy() -> a shallow copy of od");
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fromkeys$20(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyString.fromInterned("OD.fromkeys(S[, v]) -> New ordered dictionary with keys from S.\n        If not specified, the value defaults to None.\n\n        ");
      var1.setline(197);
      PyObject var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(198);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(200);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(199);
         PyObject var5 = var1.getlocal(2);
         var1.getlocal(3).__setitem__(var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject __eq__$21(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("od.__eq__(y) <==> od==y.  Comparison to another OD is order-sensitive\n        while comparison to a regular mapping is order-insensitive.\n\n        ");
      var1.setline(207);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("OrderedDict")).__nonzero__()) {
         var1.setline(208);
         PyObject var10000 = var1.getglobal("dict").__getattr__("__eq__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("all").__call__(var2, var1.getglobal("_imap").__call__(var2, var1.getglobal("_eq"), var1.getlocal(0), var1.getlocal(1)));
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(209);
         var3 = var1.getglobal("dict").__getattr__("__eq__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$22(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("od.__ne__(y) <==> od!=y");
      var1.setline(213);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject viewkeys$23(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyString.fromInterned("od.viewkeys() -> a set-like object providing a view on od's keys");
      var1.setline(219);
      PyObject var3 = var1.getglobal("KeysView").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject viewvalues$24(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyString.fromInterned("od.viewvalues() -> an object providing a view on od's values");
      var1.setline(223);
      PyObject var3 = var1.getglobal("ValuesView").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject viewitems$25(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyString.fromInterned("od.viewitems() -> a set-like object providing a view on od's items");
      var1.setline(227);
      PyObject var3 = var1.getglobal("ItemsView").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject namedtuple$26(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Returns a new subclass of tuple with named fields.\n\n    >>> Point = namedtuple('Point', ['x', 'y'])\n    >>> Point.__doc__                   # docstring for the new class\n    'Point(x, y)'\n    >>> p = Point(11, y=22)             # instantiate with positional args or keywords\n    >>> p[0] + p[1]                     # indexable like a plain tuple\n    33\n    >>> x, y = p                        # unpack like a regular tuple\n    >>> x, y\n    (11, 22)\n    >>> p.x + p.y                       # fields also accessable by name\n    33\n    >>> d = p._asdict()                 # convert to a dictionary\n    >>> d['x']\n    11\n    >>> Point(**d)                      # convert from a dictionary\n    Point(x=11, y=22)\n    >>> p._replace(x=100)               # _replace() is like str.replace() but targets named fields\n    Point(x=100, y=22)\n\n    ");
      var1.setline(310);
      PyObject var3;
      if (var1.getname("isinstance").__call__(var2, var1.getlocal(1), var1.getname("basestring")).__nonzero__()) {
         var1.setline(311);
         var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","), (PyObject)PyString.fromInterned(" ")).__getattr__("split").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(312);
      var3 = var1.getname("map").__call__(var2, var1.getname("str"), var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(313);
      PyObject var4;
      PyObject[] var5;
      PyObject var11;
      PyFunction var13;
      PyObject var10000;
      PyObject var10002;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(314);
         var3 = var1.getname("set").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(315);
         var3 = var1.getname("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(315);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(316);
            var10000 = var1.getname("all");
            var1.setline(316);
            var5 = Py.EmptyObjects;
            var13 = new PyFunction(var1.f_globals, var5, f$27, (PyObject)null);
            var10002 = var13.__call__(var2, var1.getlocal(6).__iter__());
            Arrays.fill(var5, (Object)null);
            var10000 = var10000.__call__(var2, var10002).__not__();
            if (!var10000.__nonzero__()) {
               var10000 = var1.getname("_iskeyword").__call__(var2, var1.getlocal(6));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6).__not__();
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("isdigit").__call__(var2);
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getlocal(6).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
                        if (!var10000.__nonzero__()) {
                           var11 = var1.getlocal(6);
                           var10000 = var11._in(var1.getlocal(4));
                           var5 = null;
                        }
                     }
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(322);
               var11 = PyString.fromInterned("_%d")._mod(var1.getlocal(5));
               var1.getlocal(1).__setitem__(var1.getlocal(5), var11);
               var5 = null;
            }

            var1.setline(323);
            var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(6));
         }
      }

      var1.setline(324);
      var3 = (new PyList(new PyObject[]{var1.getlocal(0)}))._add(var1.getlocal(1)).__iter__();

      do {
         var1.setline(324);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(334);
            var3 = var1.getname("set").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(335);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(335);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(344);
                  var10000 = var1.getname("_class_template").__getattr__("format");
                  PyObject[] var12 = new PyObject[]{var1.getlocal(0), var1.getname("tuple").__call__(var2, var1.getlocal(1)), var1.getname("len").__call__(var2, var1.getlocal(1)), var1.getname("repr").__call__(var2, var1.getname("tuple").__call__(var2, var1.getlocal(1))).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"), (PyObject)PyString.fromInterned("")).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null), null, null};
                  var10002 = PyString.fromInterned(", ").__getattr__("join");
                  var1.setline(349);
                  PyObject[] var9 = Py.EmptyObjects;
                  PyFunction var14 = new PyFunction(var1.f_globals, var9, f$29, (PyObject)null);
                  PyObject var10004 = var14.__call__(var2, var1.getlocal(1).__iter__());
                  Arrays.fill(var9, (Object)null);
                  var12[4] = var10002.__call__(var2, var10004);
                  var10002 = PyString.fromInterned("\n").__getattr__("join");
                  var1.setline(351);
                  var9 = Py.EmptyObjects;
                  var14 = new PyFunction(var1.f_globals, var9, f$30, (PyObject)null);
                  var10004 = var14.__call__(var2, var1.getname("enumerate").__call__(var2, var1.getlocal(1)).__iter__());
                  Arrays.fill(var9, (Object)null);
                  var12[5] = var10002.__call__(var2, var10004);
                  String[] var10 = new String[]{"typename", "field_names", "num_fields", "arg_list", "repr_fmt", "field_defs"};
                  var10000 = var10000.__call__(var2, var12, var10);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(354);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(355);
                     Py.println(var1.getlocal(9));
                  }

                  var1.setline(359);
                  var10000 = var1.getname("dict");
                  var12 = new PyObject[]{var1.getname("_itemgetter"), PyString.fromInterned("namedtuple_%s")._mod(var1.getlocal(0)), var1.getname("OrderedDict"), var1.getname("property"), var1.getname("tuple")};
                  var10 = new String[]{"_itemgetter", "__name__", "OrderedDict", "_property", "_tuple"};
                  var10000 = var10000.__call__(var2, var12, var10);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(12, var3);
                  var3 = null;

                  PyException var15;
                  try {
                     var1.setline(362);
                     Py.exec(var1.getlocal(9), var1.getlocal(12), (PyObject)null);
                  } catch (Throwable var7) {
                     var15 = Py.setException(var7, var1);
                     if (var15.match(var1.getname("SyntaxError"))) {
                        var4 = var15.value;
                        var1.setlocal(13, var4);
                        var4 = null;
                        var1.setline(364);
                        throw Py.makeException(var1.getname("SyntaxError").__call__(var2, var1.getlocal(13).__getattr__("message")._add(PyString.fromInterned(":\n"))._add(var1.getlocal(9))));
                     }

                     throw var15;
                  }

                  var1.setline(365);
                  var3 = var1.getlocal(12).__getitem__(var1.getlocal(0));
                  var1.setlocal(14, var3);
                  var3 = null;

                  try {
                     var1.setline(372);
                     var3 = var1.getname("_sys").__getattr__("_getframe").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("f_globals").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__name__"), (PyObject)PyString.fromInterned("__main__"));
                     var1.getlocal(14).__setattr__("__module__", var3);
                     var3 = null;
                  } catch (Throwable var8) {
                     var15 = Py.setException(var8, var1);
                     if (!var15.match(new PyTuple(new PyObject[]{var1.getname("AttributeError"), var1.getname("ValueError")}))) {
                        throw var15;
                     }

                     var1.setline(374);
                  }

                  var1.setline(376);
                  var3 = var1.getlocal(14);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(6, var4);
               var1.setline(336);
               var10000 = var1.getlocal(6).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(3).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(337);
                  throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("Field names cannot start with an underscore: %r")._mod(var1.getlocal(6))));
               }

               var1.setline(339);
               var11 = var1.getlocal(6);
               var10000 = var11._in(var1.getlocal(4));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(340);
                  throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("Encountered duplicate field name: %r")._mod(var1.getlocal(6))));
               }

               var1.setline(341);
               var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(6));
            }
         }

         var1.setlocal(6, var4);
         var1.setline(325);
         var10000 = var1.getname("all");
         var1.setline(325);
         var5 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var5, f$28, (PyObject)null);
         var10002 = var13.__call__(var2, var1.getlocal(6).__iter__());
         Arrays.fill(var5, (Object)null);
         if (var10000.__call__(var2, var10002).__not__().__nonzero__()) {
            var1.setline(326);
            throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("Type names and field names can only contain alphanumeric characters and underscores: %r")._mod(var1.getlocal(6))));
         }

         var1.setline(328);
         if (var1.getname("_iskeyword").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(329);
            throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("Type names and field names cannot be a keyword: %r")._mod(var1.getlocal(6))));
         }

         var1.setline(331);
      } while(!var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("isdigit").__call__(var2).__nonzero__());

      var1.setline(332);
      throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("Type names and field names cannot start with a number: %r")._mod(var1.getlocal(6))));
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(316);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(316);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(316);
         var1.setline(316);
         var7 = var1.getlocal(1).__getattr__("isalnum").__call__(var2);
         if (!var7.__nonzero__()) {
            PyObject var6 = var1.getlocal(1);
            var7 = var6._eq(PyString.fromInterned("_"));
            var5 = null;
         }

         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(325);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(325);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(325);
         var1.setline(325);
         var7 = var1.getlocal(1).__getattr__("isalnum").__call__(var2);
         if (!var7.__nonzero__()) {
            PyObject var6 = var1.getlocal(1);
            var7 = var6._eq(PyString.fromInterned("_"));
            var5 = null;
         }

         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject f$29(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(350);
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

      var1.setline(350);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(349);
         var1.setline(349);
         var8 = var1.getglobal("_repr_template").__getattr__("format");
         PyObject[] var7 = new PyObject[]{var1.getlocal(1)};
         String[] var6 = new String[]{"name"};
         var8 = var8.__call__(var2, var7, var6);
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject f$30(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(352);
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

            var9 = (PyObject)var10000;
      }

      var1.setline(352);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(351);
         var1.setline(351);
         var9 = var1.getglobal("_field_template").__getattr__("format");
         var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         String[] var8 = new String[]{"index", "name"};
         var9 = var9.__call__(var2, var7, var8);
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject Counter$31(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dict subclass for counting hashable items.  Sometimes called a bag\n    or multiset.  Elements are stored as dictionary keys and their counts\n    are stored as dictionary values.\n\n    >>> c = Counter('abcdeabcdabcaba')  # count elements from a string\n\n    >>> c.most_common(3)                # three most common elements\n    [('a', 5), ('b', 4), ('c', 3)]\n    >>> sorted(c)                       # list all unique elements\n    ['a', 'b', 'c', 'd', 'e']\n    >>> ''.join(sorted(c.elements()))   # list elements with repetitions\n    'aaaaabbbbcccdde'\n    >>> sum(c.values())                 # total of all counts\n    15\n\n    >>> c['a']                          # count of letter 'a'\n    5\n    >>> for elem in 'shazam':           # update counts from an iterable\n    ...     c[elem] += 1                # by adding 1 to each element's count\n    >>> c['a']                          # now there are seven 'a'\n    7\n    >>> del c['b']                      # remove all 'b'\n    >>> c['b']                          # now there are zero 'b'\n    0\n\n    >>> d = Counter('simsalabim')       # make another counter\n    >>> c.update(d)                     # add in the second counter\n    >>> c['a']                          # now there are nine 'a'\n    9\n\n    >>> c.clear()                       # empty the counter\n    >>> c\n    Counter()\n\n    Note:  If a count is set to zero or reduced to zero, it will remain\n    in the counter until the entry is deleted or the counter is cleared:\n\n    >>> c = Counter('aaabbc')\n    >>> c['b'] -= 2                     # reduce the count of 'b' by two\n    >>> c.most_common()                 # 'b' is still in, but its count is zero\n    [('a', 3), ('c', 1), ('b', 0)]\n\n    "));
      var1.setline(426);
      PyString.fromInterned("Dict subclass for counting hashable items.  Sometimes called a bag\n    or multiset.  Elements are stored as dictionary keys and their counts\n    are stored as dictionary values.\n\n    >>> c = Counter('abcdeabcdabcaba')  # count elements from a string\n\n    >>> c.most_common(3)                # three most common elements\n    [('a', 5), ('b', 4), ('c', 3)]\n    >>> sorted(c)                       # list all unique elements\n    ['a', 'b', 'c', 'd', 'e']\n    >>> ''.join(sorted(c.elements()))   # list elements with repetitions\n    'aaaaabbbbcccdde'\n    >>> sum(c.values())                 # total of all counts\n    15\n\n    >>> c['a']                          # count of letter 'a'\n    5\n    >>> for elem in 'shazam':           # update counts from an iterable\n    ...     c[elem] += 1                # by adding 1 to each element's count\n    >>> c['a']                          # now there are seven 'a'\n    7\n    >>> del c['b']                      # remove all 'b'\n    >>> c['b']                          # now there are zero 'b'\n    0\n\n    >>> d = Counter('simsalabim')       # make another counter\n    >>> c.update(d)                     # add in the second counter\n    >>> c['a']                          # now there are nine 'a'\n    9\n\n    >>> c.clear()                       # empty the counter\n    >>> c\n    Counter()\n\n    Note:  If a count is set to zero or reduced to zero, it will remain\n    in the counter until the entry is deleted or the counter is cleared:\n\n    >>> c = Counter('aaabbc')\n    >>> c['b'] -= 2                     # reduce the count of 'b' by two\n    >>> c.most_common()                 # 'b' is still in, but its count is zero\n    [('a', 3), ('c', 1), ('b', 0)]\n\n    ");
      var1.setline(434);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$32, PyString.fromInterned("Create a new, empty Counter object.  And if given, count elements\n        from an input iterable.  Or, initialize the count from another mapping\n        of elements to their counts.\n\n        >>> c = Counter()                           # a new, empty counter\n        >>> c = Counter('gallahad')                 # a new counter from an iterable\n        >>> c = Counter({'a': 4, 'b': 2})           # a new counter from a mapping\n        >>> c = Counter(a=4, b=2)                   # a new counter from keyword args\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __missing__$33, PyString.fromInterned("The count of elements not in the Counter is zero."));
      var1.setlocal("__missing__", var4);
      var3 = null;
      var1.setline(453);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, most_common$34, PyString.fromInterned("List the n most common elements and their counts from the most\n        common to the least.  If n is None, then list all element counts.\n\n        >>> Counter('abcdeabcdabcaba').most_common(3)\n        [('a', 5), ('b', 4), ('c', 3)]\n\n        "));
      var1.setlocal("most_common", var4);
      var3 = null;
      var1.setline(466);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, elements$35, PyString.fromInterned("Iterator over elements repeating each as many times as its count.\n\n        >>> c = Counter('ABCABC')\n        >>> sorted(c.elements())\n        ['A', 'A', 'B', 'B', 'C', 'C']\n\n        # Knuth's example for prime factors of 1836:  2**2 * 3**3 * 17**1\n        >>> prime_factors = Counter({2: 2, 3: 3, 17: 1})\n        >>> product = 1\n        >>> for factor in prime_factors.elements():     # loop over factors\n        ...     product *= factor                       # and multiply them\n        >>> product\n        1836\n\n        Note, if an element's count has been set to zero or is a negative\n        number, elements() will ignore it.\n\n        "));
      var1.setlocal("elements", var4);
      var3 = null;
      var1.setline(490);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fromkeys$36, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fromkeys", var5);
      var3 = null;
      var1.setline(497);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, update$37, PyString.fromInterned("Like dict.update() but add counts instead of replacing them.\n\n        Source can be an iterable, a dictionary, or another Counter instance.\n\n        >>> c = Counter('which')\n        >>> c.update('witch')           # add elements from another iterable\n        >>> d = Counter('watch')\n        >>> c.update(d)                 # add elements from another counter\n        >>> c['h']                      # four 'h' in which, witch, and watch\n        4\n\n        "));
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(532);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, subtract$38, PyString.fromInterned("Like dict.update() but subtracts counts instead of replacing them.\n        Counts can be reduced below zero.  Both the inputs and outputs are\n        allowed to contain zero and negative counts.\n\n        Source can be an iterable, a dictionary, or another Counter instance.\n\n        >>> c = Counter('which')\n        >>> c.subtract('witch')             # subtract elements from another iterable\n        >>> c.subtract(Counter('watch'))    # subtract elements from another counter\n        >>> c['h']                          # 2 in which, minus 1 in witch, minus 1 in watch\n        0\n        >>> c['w']                          # 1 in which, minus 1 in witch, minus 1 in watch\n        -1\n\n        "));
      var1.setlocal("subtract", var4);
      var3 = null;
      var1.setline(559);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$39, PyString.fromInterned("Return a shallow copy."));
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(563);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __reduce__$40, (PyObject)null);
      var1.setlocal("__reduce__", var4);
      var3 = null;
      var1.setline(566);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$41, PyString.fromInterned("Like dict.__delitem__() but does not raise KeyError for missing values."));
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(571);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$42, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(586);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$43, PyString.fromInterned("Add counts from two counters.\n\n        >>> Counter('abbb') + Counter('bcc')\n        Counter({'b': 4, 'c': 2, 'a': 1})\n\n        "));
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __sub__$44, PyString.fromInterned(" Subtract count, but keep only results with positive counts.\n\n        >>> Counter('abbbc') - Counter('bccd')\n        Counter({'b': 2, 'a': 1})\n\n        "));
      var1.setlocal("__sub__", var4);
      var3 = null;
      var1.setline(624);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __or__$45, PyString.fromInterned("Union is the maximum of value in either of the input counters.\n\n        >>> Counter('abbb') | Counter('bcc')\n        Counter({'b': 3, 'c': 2, 'a': 1})\n\n        "));
      var1.setlocal("__or__", var4);
      var3 = null;
      var1.setline(644);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __and__$46, PyString.fromInterned(" Intersection is the minimum of corresponding counts.\n\n        >>> Counter('abbb') & Counter('bcc')\n        Counter({'b': 1})\n\n        "));
      var1.setlocal("__and__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$32(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      PyString.fromInterned("Create a new, empty Counter object.  And if given, count elements\n        from an input iterable.  Or, initialize the count from another mapping\n        of elements to their counts.\n\n        >>> c = Counter()                           # a new, empty counter\n        >>> c = Counter('gallahad')                 # a new counter from an iterable\n        >>> c = Counter({'a': 4, 'b': 2})           # a new counter from a mapping\n        >>> c = Counter(a=4, b=2)                   # a new counter from keyword args\n\n        ");
      var1.setline(445);
      var1.getglobal("super").__call__(var2, var1.getglobal("Counter"), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.setline(446);
      PyObject var10000 = var1.getlocal(0).__getattr__("update");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __missing__$33(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyString.fromInterned("The count of elements not in the Counter is zero.");
      var1.setline(451);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject most_common$34(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      PyString.fromInterned("List the n most common elements and their counts from the most\n        common to the least.  If n is None, then list all element counts.\n\n        >>> Counter('abcdeabcdabcaba').most_common(3)\n        [('a', 5), ('b', 4), ('c', 3)]\n\n        ");
      var1.setline(462);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(463);
         var10000 = var1.getglobal("sorted");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("iteritems").__call__(var2), var1.getglobal("_itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getglobal("True")};
         String[] var7 = new String[]{"key", "reverse"};
         var10000 = var10000.__call__(var2, var6, var7);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(464);
         var10000 = var1.getglobal("_heapq").__getattr__("nlargest");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("iteritems").__call__(var2), var1.getglobal("_itemgetter").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))};
         String[] var5 = new String[]{"key"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject elements$35(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyString.fromInterned("Iterator over elements repeating each as many times as its count.\n\n        >>> c = Counter('ABCABC')\n        >>> sorted(c.elements())\n        ['A', 'A', 'B', 'B', 'C', 'C']\n\n        # Knuth's example for prime factors of 1836:  2**2 * 3**3 * 17**1\n        >>> prime_factors = Counter({2: 2, 3: 3, 17: 1})\n        >>> product = 1\n        >>> for factor in prime_factors.elements():     # loop over factors\n        ...     product *= factor                       # and multiply them\n        >>> product\n        1836\n\n        Note, if an element's count has been set to zero or is a negative\n        number, elements() will ignore it.\n\n        ");
      var1.setline(486);
      PyObject var3 = var1.getglobal("_chain").__getattr__("from_iterable").__call__(var2, var1.getglobal("_starmap").__call__(var2, var1.getglobal("_repeat"), var1.getlocal(0).__getattr__("iteritems").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fromkeys$36(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Counter.fromkeys() is undefined.  Use Counter(iterable) instead.")));
   }

   public PyObject update$37(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyString.fromInterned("Like dict.update() but add counts instead of replacing them.\n\n        Source can be an iterable, a dictionary, or another Counter instance.\n\n        >>> c = Counter('which')\n        >>> c.update('witch')           # add elements from another iterable\n        >>> d = Counter('watch')\n        >>> c.update(d)                 # add elements from another counter\n        >>> c['h']                      # four 'h' in which, witch, and watch\n        4\n\n        ");
      var1.setline(517);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(518);
         PyObject var4;
         PyObject[] var5;
         PyObject var7;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Mapping")).__nonzero__()) {
            var1.setline(519);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(520);
               var3 = var1.getlocal(0).__getattr__("get");
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(521);
               var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

               while(true) {
                  var1.setline(521);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var1.setline(522);
                  var7 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0))._add(var1.getlocal(5));
                  var1.getlocal(0).__setitem__(var1.getlocal(4), var7);
                  var5 = null;
               }
            } else {
               var1.setline(524);
               var1.getglobal("super").__call__(var2, var1.getglobal("Counter"), var1.getlocal(0)).__getattr__("update").__call__(var2, var1.getlocal(1));
            }
         } else {
            var1.setline(526);
            var3 = var1.getlocal(0).__getattr__("get");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(527);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(527);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(528);
               var7 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
               var1.getlocal(0).__setitem__(var1.getlocal(4), var7);
               var5 = null;
            }
         }
      }

      var1.setline(529);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(530);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject subtract$38(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyString.fromInterned("Like dict.update() but subtracts counts instead of replacing them.\n        Counts can be reduced below zero.  Both the inputs and outputs are\n        allowed to contain zero and negative counts.\n\n        Source can be an iterable, a dictionary, or another Counter instance.\n\n        >>> c = Counter('which')\n        >>> c.subtract('witch')             # subtract elements from another iterable\n        >>> c.subtract(Counter('watch'))    # subtract elements from another counter\n        >>> c['h']                          # 2 in which, minus 1 in witch, minus 1 in watch\n        0\n        >>> c['w']                          # 1 in which, minus 1 in witch, minus 1 in watch\n        -1\n\n        ");
      var1.setline(548);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(549);
         var3 = var1.getlocal(0).__getattr__("get");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(550);
         PyObject var4;
         PyObject[] var5;
         PyObject var7;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Mapping")).__nonzero__()) {
            var1.setline(551);
            var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(551);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(552);
               var7 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0))._sub(var1.getlocal(5));
               var1.getlocal(0).__setitem__(var1.getlocal(4), var7);
               var5 = null;
            }
         } else {
            var1.setline(554);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(554);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(555);
               var7 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0))._sub(Py.newInteger(1));
               var1.getlocal(0).__setitem__(var1.getlocal(4), var7);
               var5 = null;
            }
         }
      }

      var1.setline(556);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(557);
         var1.getlocal(0).__getattr__("subtract").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$39(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyString.fromInterned("Return a shallow copy.");
      var1.setline(561);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __reduce__$40(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), new PyTuple(new PyObject[]{var1.getglobal("dict").__call__(var2, var1.getlocal(0))})});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __delitem__$41(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("Like dict.__delitem__() but does not raise KeyError for missing values.");
      var1.setline(568);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(569);
         var1.getglobal("super").__call__(var2, var1.getglobal("Counter"), var1.getlocal(0)).__getattr__("__delitem__").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$42(PyFrame var1, ThreadState var2) {
      var1.setline(572);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(573);
         var3 = PyString.fromInterned("%s()")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(574);
         PyObject var4 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, PyString.fromInterned("%r: %r").__getattr__("__mod__"), var1.getlocal(0).__getattr__("most_common").__call__(var2)));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(575);
         var3 = PyString.fromInterned("%s({%s})")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(1)}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __add__$43(PyFrame var1, ThreadState var2) {
      var1.setline(592);
      PyString.fromInterned("Add counts from two counters.\n\n        >>> Counter('abbb') + Counter('bcc')\n        Counter({'b': 4, 'c': 2, 'a': 1})\n\n        ");
      var1.setline(593);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Counter")).__not__().__nonzero__()) {
         var1.setline(594);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(595);
         PyObject var4 = var1.getglobal("Counter").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(596);
         var4 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(596);
            PyObject var5 = var4.__iternext__();
            PyObject var10000;
            PyObject[] var6;
            PyObject var7;
            PyObject var8;
            if (var5 == null) {
               var1.setline(600);
               var4 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(600);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(603);
                     var3 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(601);
                  var8 = var1.getlocal(3);
                  var10000 = var8._notin(var1.getlocal(0));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(4);
                     var10000 = var8._gt(Py.newInteger(0));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(602);
                     var8 = var1.getlocal(4);
                     var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
                     var6 = null;
                  }
               }
            }

            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(597);
            var8 = var1.getlocal(4)._add(var1.getlocal(1).__getitem__(var1.getlocal(3)));
            var1.setlocal(5, var8);
            var6 = null;
            var1.setline(598);
            var8 = var1.getlocal(5);
            var10000 = var8._gt(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(599);
               var8 = var1.getlocal(5);
               var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
               var6 = null;
            }
         }
      }
   }

   public PyObject __sub__$44(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyString.fromInterned(" Subtract count, but keep only results with positive counts.\n\n        >>> Counter('abbbc') - Counter('bccd')\n        Counter({'b': 2, 'a': 1})\n\n        ");
      var1.setline(612);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Counter")).__not__().__nonzero__()) {
         var1.setline(613);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(614);
         PyObject var4 = var1.getglobal("Counter").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(615);
         var4 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(615);
            PyObject var5 = var4.__iternext__();
            PyObject var10000;
            PyObject[] var6;
            PyObject var7;
            PyObject var8;
            if (var5 == null) {
               var1.setline(619);
               var4 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(619);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(622);
                     var3 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(620);
                  var8 = var1.getlocal(3);
                  var10000 = var8._notin(var1.getlocal(0));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(4);
                     var10000 = var8._lt(Py.newInteger(0));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(621);
                     var8 = Py.newInteger(0)._sub(var1.getlocal(4));
                     var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
                     var6 = null;
                  }
               }
            }

            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(616);
            var8 = var1.getlocal(4)._sub(var1.getlocal(1).__getitem__(var1.getlocal(3)));
            var1.setlocal(5, var8);
            var6 = null;
            var1.setline(617);
            var8 = var1.getlocal(5);
            var10000 = var8._gt(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(618);
               var8 = var1.getlocal(5);
               var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
               var6 = null;
            }
         }
      }
   }

   public PyObject __or__$45(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyString.fromInterned("Union is the maximum of value in either of the input counters.\n\n        >>> Counter('abbb') | Counter('bcc')\n        Counter({'b': 3, 'c': 2, 'a': 1})\n\n        ");
      var1.setline(631);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Counter")).__not__().__nonzero__()) {
         var1.setline(632);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(633);
         PyObject var4 = var1.getglobal("Counter").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(634);
         var4 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(634);
            PyObject var5 = var4.__iternext__();
            PyObject var10000;
            PyObject[] var6;
            PyObject var7;
            PyObject var8;
            if (var5 == null) {
               var1.setline(639);
               var4 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(639);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(642);
                     var3 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(640);
                  var8 = var1.getlocal(3);
                  var10000 = var8._notin(var1.getlocal(0));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getlocal(4);
                     var10000 = var8._gt(Py.newInteger(0));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(641);
                     var8 = var1.getlocal(4);
                     var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
                     var6 = null;
                  }
               }
            }

            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(635);
            var8 = var1.getlocal(1).__getitem__(var1.getlocal(3));
            var1.setlocal(5, var8);
            var6 = null;
            var1.setline(636);
            var1.setline(636);
            var8 = var1.getlocal(4);
            var10000 = var8._lt(var1.getlocal(5));
            var6 = null;
            var8 = var10000.__nonzero__() ? var1.getlocal(5) : var1.getlocal(4);
            var1.setlocal(6, var8);
            var6 = null;
            var1.setline(637);
            var8 = var1.getlocal(6);
            var10000 = var8._gt(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(638);
               var8 = var1.getlocal(6);
               var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
               var6 = null;
            }
         }
      }
   }

   public PyObject __and__$46(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyString.fromInterned(" Intersection is the minimum of corresponding counts.\n\n        >>> Counter('abbb') & Counter('bcc')\n        Counter({'b': 1})\n\n        ");
      var1.setline(651);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Counter")).__not__().__nonzero__()) {
         var1.setline(652);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(653);
         PyObject var4 = var1.getglobal("Counter").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(654);
         var4 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(654);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(659);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(655);
            PyObject var8 = var1.getlocal(1).__getitem__(var1.getlocal(3));
            var1.setlocal(5, var8);
            var6 = null;
            var1.setline(656);
            var1.setline(656);
            var8 = var1.getlocal(4);
            PyObject var10000 = var8._lt(var1.getlocal(5));
            var6 = null;
            var8 = var10000.__nonzero__() ? var1.getlocal(4) : var1.getlocal(5);
            var1.setlocal(6, var8);
            var6 = null;
            var1.setline(657);
            var8 = var1.getlocal(6);
            var10000 = var8._gt(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(658);
               var8 = var1.getlocal(6);
               var1.getlocal(2).__setitem__(var1.getlocal(3), var8);
               var6 = null;
            }
         }
      }
   }

   public PyObject Point$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(671);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(672);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, hypot$48, (PyObject)null);
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("hypot", var6);
      var3 = null;
      var1.setline(675);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$49, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject hypot$48(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyObject var3 = var1.getlocal(0).__getattr__("x")._pow(Py.newInteger(2))._add(var1.getlocal(0).__getattr__("y")._pow(Py.newInteger(2)))._pow(Py.newFloat(0.5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$49(PyFrame var1, ThreadState var2) {
      var1.setline(676);
      PyObject var3 = PyString.fromInterned("Point: x=%6.3f  y=%6.3f  hypot=%6.3f")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("x"), var1.getlocal(0).__getattr__("y"), var1.getlocal(0).__getattr__("hypot")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Point$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Point class with optimized _make() and _replace() without error-checking"));
      var1.setline(682);
      PyString.fromInterned("Point class with optimized _make() and _replace() without error-checking");
      var1.setline(683);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(684);
      PyObject var4 = var1.getname("classmethod").__call__(var2, var1.getname("tuple").__getattr__("__new__"));
      var1.setlocal("_make", var4);
      var3 = null;
      var1.setline(685);
      PyObject[] var5 = new PyObject[]{var1.getname("map")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, _replace$51, (PyObject)null);
      var1.setlocal("_replace", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _replace$51(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyObject var3 = var1.getlocal(0).__getattr__("_make").__call__(var2, var1.getlocal(1).__call__((ThreadState)var2, var1.getlocal(2).__getattr__("get"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("x"), PyString.fromInterned("y")})), (PyObject)var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public collections$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      OrderedDict$1 = Py.newCode(0, var2, var1, "OrderedDict", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwds", "root"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 38, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "dict_setitem", "root", "last"};
      __setitem__$3 = Py.newCode(4, var2, var1, "__setitem__", 54, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "dict_delitem", "link_prev", "link_next"};
      __delitem__$4 = Py.newCode(3, var2, var1, "__delitem__", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "root", "curr"};
      __iter__$5 = Py.newCode(1, var2, var1, "__iter__", 73, false, false, self, 5, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "root", "curr"};
      __reversed__$6 = Py.newCode(1, var2, var1, "__reversed__", 82, false, false, self, 6, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "root"};
      clear$7 = Py.newCode(1, var2, var1, "clear", 91, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$8 = Py.newCode(1, var2, var1, "keys", 100, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[106_16]", "key"};
      values$9 = Py.newCode(1, var2, var1, "values", 104, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[110_16]", "key"};
      items$10 = Py.newCode(1, var2, var1, "items", 108, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iterkeys$11 = Py.newCode(1, var2, var1, "iterkeys", 112, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k"};
      itervalues$12 = Py.newCode(1, var2, var1, "itervalues", 116, false, false, self, 12, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "k"};
      iteritems$13 = Py.newCode(1, var2, var1, "iteritems", 121, false, false, self, 13, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key", "default", "result"};
      pop$14 = Py.newCode(3, var2, var1, "pop", 132, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      setdefault$15 = Py.newCode(3, var2, var1, "setdefault", 146, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "last", "key", "value"};
      popitem$16 = Py.newCode(2, var2, var1, "popitem", 153, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_repr_running", "call_key"};
      __repr__$17 = Py.newCode(2, var2, var1, "__repr__", 164, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "_[179_17]", "k", "inst_dict"};
      __reduce__$18 = Py.newCode(1, var2, var1, "__reduce__", 177, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$19 = Py.newCode(1, var2, var1, "copy", 187, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "iterable", "value", "self", "key"};
      fromkeys$20 = Py.newCode(3, var2, var1, "fromkeys", 191, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$21 = Py.newCode(2, var2, var1, "__eq__", 202, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$22 = Py.newCode(2, var2, var1, "__ne__", 211, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      viewkeys$23 = Py.newCode(1, var2, var1, "viewkeys", 217, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      viewvalues$24 = Py.newCode(1, var2, var1, "viewvalues", 221, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      viewitems$25 = Py.newCode(1, var2, var1, "viewitems", 225, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"typename", "field_names", "verbose", "rename", "seen", "index", "name", "_(316_24)", "_(325_19)", "class_definition", "_(349_29)", "_(351_31)", "namespace", "e", "result"};
      namedtuple$26 = Py.newCode(4, var2, var1, "namedtuple", 284, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_(x)", "c"};
      f$27 = Py.newCode(1, var2, var1, "<genexpr>", 316, false, false, self, 27, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "c"};
      f$28 = Py.newCode(1, var2, var1, "<genexpr>", 325, false, false, self, 28, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "name"};
      f$29 = Py.newCode(1, var2, var1, "<genexpr>", 349, false, false, self, 29, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "index", "name"};
      f$30 = Py.newCode(1, var2, var1, "<genexpr>", 351, false, false, self, 30, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      Counter$31 = Py.newCode(0, var2, var1, "Counter", 383, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "iterable", "kwds"};
      __init__$32 = Py.newCode(3, var2, var1, "__init__", 434, false, true, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __missing__$33 = Py.newCode(2, var2, var1, "__missing__", 448, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      most_common$34 = Py.newCode(2, var2, var1, "most_common", 453, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      elements$35 = Py.newCode(1, var2, var1, "elements", 466, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "iterable", "v"};
      fromkeys$36 = Py.newCode(3, var2, var1, "fromkeys", 490, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "kwds", "self_get", "elem", "count"};
      update$37 = Py.newCode(3, var2, var1, "update", 497, false, true, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "iterable", "kwds", "self_get", "elem", "count"};
      subtract$38 = Py.newCode(3, var2, var1, "subtract", 532, false, true, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      copy$39 = Py.newCode(1, var2, var1, "copy", 559, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __reduce__$40 = Py.newCode(1, var2, var1, "__reduce__", 563, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "elem"};
      __delitem__$41 = Py.newCode(2, var2, var1, "__delitem__", 566, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items"};
      __repr__$42 = Py.newCode(1, var2, var1, "__repr__", 571, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "elem", "count", "newcount"};
      __add__$43 = Py.newCode(2, var2, var1, "__add__", 586, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "elem", "count", "newcount"};
      __sub__$44 = Py.newCode(2, var2, var1, "__sub__", 605, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "elem", "count", "other_count", "newcount"};
      __or__$45 = Py.newCode(2, var2, var1, "__or__", 624, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "result", "elem", "count", "other_count", "newcount"};
      __and__$46 = Py.newCode(2, var2, var1, "__and__", 644, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Point$47 = Py.newCode(0, var2, var1, "Point", 670, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      hypot$48 = Py.newCode(1, var2, var1, "hypot", 672, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$49 = Py.newCode(1, var2, var1, "__str__", 675, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Point$50 = Py.newCode(0, var2, var1, "Point", 681, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_map", "kwds"};
      _replace$51 = Py.newCode(3, var2, var1, "_replace", 685, false, true, self, 51, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new collections$py("collections$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(collections$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.OrderedDict$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__setitem__$3(var2, var3);
         case 4:
            return this.__delitem__$4(var2, var3);
         case 5:
            return this.__iter__$5(var2, var3);
         case 6:
            return this.__reversed__$6(var2, var3);
         case 7:
            return this.clear$7(var2, var3);
         case 8:
            return this.keys$8(var2, var3);
         case 9:
            return this.values$9(var2, var3);
         case 10:
            return this.items$10(var2, var3);
         case 11:
            return this.iterkeys$11(var2, var3);
         case 12:
            return this.itervalues$12(var2, var3);
         case 13:
            return this.iteritems$13(var2, var3);
         case 14:
            return this.pop$14(var2, var3);
         case 15:
            return this.setdefault$15(var2, var3);
         case 16:
            return this.popitem$16(var2, var3);
         case 17:
            return this.__repr__$17(var2, var3);
         case 18:
            return this.__reduce__$18(var2, var3);
         case 19:
            return this.copy$19(var2, var3);
         case 20:
            return this.fromkeys$20(var2, var3);
         case 21:
            return this.__eq__$21(var2, var3);
         case 22:
            return this.__ne__$22(var2, var3);
         case 23:
            return this.viewkeys$23(var2, var3);
         case 24:
            return this.viewvalues$24(var2, var3);
         case 25:
            return this.viewitems$25(var2, var3);
         case 26:
            return this.namedtuple$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this.f$29(var2, var3);
         case 30:
            return this.f$30(var2, var3);
         case 31:
            return this.Counter$31(var2, var3);
         case 32:
            return this.__init__$32(var2, var3);
         case 33:
            return this.__missing__$33(var2, var3);
         case 34:
            return this.most_common$34(var2, var3);
         case 35:
            return this.elements$35(var2, var3);
         case 36:
            return this.fromkeys$36(var2, var3);
         case 37:
            return this.update$37(var2, var3);
         case 38:
            return this.subtract$38(var2, var3);
         case 39:
            return this.copy$39(var2, var3);
         case 40:
            return this.__reduce__$40(var2, var3);
         case 41:
            return this.__delitem__$41(var2, var3);
         case 42:
            return this.__repr__$42(var2, var3);
         case 43:
            return this.__add__$43(var2, var3);
         case 44:
            return this.__sub__$44(var2, var3);
         case 45:
            return this.__or__$45(var2, var3);
         case 46:
            return this.__and__$46(var2, var3);
         case 47:
            return this.Point$47(var2, var3);
         case 48:
            return this.hypot$48(var2, var3);
         case 49:
            return this.__str__$49(var2, var3);
         case 50:
            return this.Point$50(var2, var3);
         case 51:
            return this._replace$51(var2, var3);
         default:
            return null;
      }
   }
}
