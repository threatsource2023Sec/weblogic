package org.python.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.python.core.finalization.FinalizablePyObject;
import org.python.core.finalization.FinalizeTrigger;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "instance",
   isBaseType = false
)
public class PyInstance extends PyObject implements FinalizablePyObject, Traverseproc {
   public static final PyType TYPE;
   private static JavaFunc __ensure_finalizer__Function;
   public transient PyClass instclass;
   public PyObject __dict__;

   public PyInstance() {
      super(TYPE);
   }

   public PyInstance(PyClass iclass, PyObject dict) {
      super(TYPE);
      this.instclass = iclass;
      if (dict == null) {
         dict = new PyStringMap();
      }

      this.__dict__ = (PyObject)dict;
   }

   public PyInstance(PyClass iclass) {
      this(iclass, (PyObject)null);
   }

   @ExposedNew
   public static PyObject instance___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("instance", args, keywords, "name", "bases", "dict");
      PyClass klass = (PyClass)ap.getPyObjectByType(0, PyClass.TYPE);
      PyObject dict = ap.getPyObject(1, Py.None);
      if (dict == Py.None) {
         dict = null;
      } else if (!(dict instanceof AbstractDict)) {
         throw Py.TypeError("instance() second arg must be dictionary or None");
      }

      return new PyInstance(klass, dict);
   }

   public PyClass fastGetClass() {
      return this.instclass;
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      String module = in.readUTF();
      String name = in.readUTF();
      PyObject mod = imp.importName(module.intern(), false);
      PyClass pyc = (PyClass)mod.__getattr__(name.intern());
      this.instclass = pyc;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      PyObject name = this.instclass.__findattr__("__module__");
      if (name instanceof PyString && name != Py.None) {
         out.writeUTF(name.toString());
         name = this.instclass.__findattr__("__name__");
         if (name instanceof PyString && name != Py.None) {
            out.writeUTF(name.toString());
         } else {
            throw Py.ValueError("Can't find module for class with no name");
         }
      } else {
         throw Py.ValueError("Can't find module for class: " + this.instclass.__name__);
      }
   }

   public Object __tojava__(Class c) {
      if (c.isInstance(this)) {
         return this;
      } else {
         if (this.instclass.__tojava__ != null) {
            PyObject ret = this.instclass.__tojava__.__call__((PyObject)this, (PyObject)PyType.fromClass(c));
            if (ret == Py.None) {
               return Py.NoConversion;
            }

            if (ret != this) {
               return ret.__tojava__(c);
            }
         }

         return Py.NoConversion;
      }
   }

   public void __init__(PyObject[] args, String[] keywords) {
      PyObject init = this.instclass.lookup("__init__");
      PyObject ret = null;
      if (init != null) {
         ret = init.__call__((PyObject)this, (PyObject[])args, (String[])keywords);
      }

      if (ret == null) {
         if (args.length != 0) {
            init = this.instclass.lookup("__init__");
            if (init == null) {
               throw Py.TypeError("this constructor takes no arguments");
            }

            init.__call__((PyObject)this, (PyObject[])args, (String[])keywords);
         }
      } else if (ret != Py.None) {
         throw Py.TypeError("__init__() should return None");
      }

   }

   public PyObject __findattr_ex__(String name) {
      PyObject result = this.ifindlocal(name);
      if (result != null) {
         return result;
      } else {
         result = this.instclass.lookup(name);
         return result != null ? result.__get__(this, this.instclass) : this.ifindfunction(name);
      }
   }

   public static void ensureFinalizer(PyObject[] args, String[] kws) {
      FinalizeTrigger.ensureFinalizer((PyInstance)args[0]);
   }

   private static JavaFunc makeFunction__ensure_finalizer__() {
      try {
         return new JavaFunc(PyInstance.class.getMethod("ensureFinalizer", PyObject[].class, String[].class));
      } catch (Exception var1) {
         return null;
      }
   }

   protected PyObject ifindlocal(String name) {
      if (name == "__dict__") {
         return this.__dict__;
      } else if (name == "__class__") {
         return this.instclass;
      } else if (name == "__ensure_finalizer__") {
         if (__ensure_finalizer__Function == null) {
            __ensure_finalizer__Function = makeFunction__ensure_finalizer__();
         }

         return new PyMethod(__ensure_finalizer__Function, this, this.instclass);
      } else {
         return this.__dict__ == null ? null : this.__dict__.__finditem__(name);
      }
   }

   protected PyObject ifindclass(String name) {
      return this.instclass.lookup(name);
   }

   protected PyObject ifindfunction(String name) {
      PyObject getter = this.instclass.__getattr__;
      return getter == null ? null : getter.__call__((PyObject)this, (PyObject)(new PyString(name)));
   }

   public boolean isCallable() {
      return this.__findattr__("__call__") != null;
   }

   public boolean isNumberType() {
      return this.__findattr__("__int__") != null || this.__findattr__("__float__") != null;
   }

   public boolean isMappingType() {
      return this.__findattr__("__getitem__") != null;
   }

   public boolean isSequenceType() {
      return this.__findattr__("__getitem__") != null;
   }

   public boolean isIndex() {
      return this.__findattr__("__index__") != null;
   }

   public PyObject invoke(String name) {
      PyObject f = this.ifindlocal(name);
      if (f == null) {
         f = this.ifindclass(name);
         if (f != null) {
            if (f instanceof PyFunction) {
               return f.__call__((PyObject)this);
            }

            f = f.__get__(this, this.instclass);
         }
      }

      if (f == null) {
         f = this.ifindfunction(name);
      }

      if (f == null) {
         this.noAttributeError(name);
      }

      return f.__call__();
   }

   public PyObject invoke(String name, PyObject arg1) {
      PyObject f = this.ifindlocal(name);
      if (f == null) {
         f = this.ifindclass(name);
         if (f != null) {
            if (f instanceof PyFunction) {
               return f.__call__((PyObject)this, (PyObject)arg1);
            }

            f = f.__get__(this, this.instclass);
         }
      }

      if (f == null) {
         f = this.ifindfunction(name);
      }

      if (f == null) {
         this.noAttributeError(name);
      }

      return f.__call__(arg1);
   }

   public PyObject invoke(String name, PyObject arg1, PyObject arg2) {
      PyObject f = this.ifindlocal(name);
      if (f == null) {
         f = this.ifindclass(name);
         if (f != null) {
            if (f instanceof PyFunction) {
               return f.__call__((PyObject)this, (PyObject)arg1, (PyObject)arg2);
            }

            f = f.__get__(this, this.instclass);
         }
      }

      if (f == null) {
         f = this.ifindfunction(name);
      }

      if (f == null) {
         this.noAttributeError(name);
      }

      return f.__call__(arg1, arg2);
   }

   public void noAttributeError(String name) {
      throw Py.AttributeError(String.format("%.50s instance has no attribute '%.400s'", this.instclass.__name__, name));
   }

   public void __setattr__(String name, PyObject value) {
      this.instance___setattr__(name, value);
   }

   final void instance___setattr__(String name, PyObject value) {
      if (name == "__class__") {
         if (value instanceof PyClass) {
            this.instclass = (PyClass)value;
            if (this.instclass.__del__ != null && !JyAttribute.hasAttr(this, (byte)127)) {
               FinalizeTrigger.ensureFinalizer(this);
            }

         } else {
            throw Py.TypeError("__class__ must be set to a class");
         }
      } else if (name == "__dict__") {
         this.__dict__ = value;
      } else {
         PyObject setter = this.instclass.__setattr__;
         if (setter != null) {
            setter.__call__((PyObject)this, (PyObject)(new PyString(name)), (PyObject)value);
         } else {
            this.__dict__.__setitem__(name, value);
         }

         if (name == "__del__" && !JyAttribute.hasAttr(this, (byte)127)) {
            FinalizeTrigger.ensureFinalizer(this);
         }

      }
   }

   protected void noField(String name, PyObject value) {
      this.__dict__.__setitem__(name, value);
   }

   protected void unassignableField(String name, PyObject value) {
      this.__dict__.__setitem__(name, value);
   }

   public void __delattr__(String name) {
      this.instance___delattr__(name);
   }

   final void instance___delattr__(String name) {
      PyObject deller = this.instclass.__delattr__;
      if (deller != null) {
         deller.__call__((PyObject)this, (PyObject)(new PyString(name)));
      } else {
         try {
            this.__dict__.__delitem__(name);
         } catch (PyException var4) {
            if (var4.match(Py.KeyError)) {
               throw Py.AttributeError("class " + this.instclass.__name__ + " has no attribute '" + name + "'");
            }
         }
      }

   }

   public PyObject invoke_ex(String name, PyObject[] args, String[] keywords) {
      PyObject meth = this.__findattr__(name);
      return meth == null ? null : meth.__call__(args, keywords);
   }

   public PyObject invoke_ex(String name) {
      PyObject meth = this.__findattr__(name);
      return meth == null ? null : meth.__call__();
   }

   public PyObject invoke_ex(String name, PyObject arg1) {
      PyObject meth = this.__findattr__(name);
      return meth == null ? null : meth.__call__(arg1);
   }

   public PyObject invoke_ex(String name, PyObject arg1, PyObject arg2) {
      PyObject meth = this.__findattr__(name);
      return meth == null ? null : meth.__call__(arg1, arg2);
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      return this.instance___call__(args, keywords);
   }

   final PyObject instance___call__(PyObject[] args, String[] keywords) {
      return this.invoke("__call__", args, keywords);
   }

   public String toString() {
      return this.__repr__().toString();
   }

   public PyString __repr__() {
      return this.instance___repr__();
   }

   final PyString instance___repr__() {
      PyObject ret = this.invoke_ex("__repr__");
      if (ret == null) {
         return this.makeDefaultRepr();
      } else if (!(ret instanceof PyString)) {
         throw Py.TypeError("__repr__ method must return a string");
      } else {
         return (PyString)ret;
      }
   }

   protected PyString makeDefaultRepr() {
      PyObject mod = this.instclass.__dict__.__finditem__("__module__");
      String modStr = mod != null && Py.isInstance(mod, PyString.TYPE) ? mod.toString() : "?";
      return new PyString(String.format("<%s.%s instance at %s>", modStr, this.instclass.__name__, Py.idstr(this)));
   }

   public PyString __str__() {
      return this.instance___str__();
   }

   final PyString instance___str__() {
      PyObject ret = this.invoke_ex("__str__");
      if (ret == null) {
         return this.__repr__();
      } else if (!(ret instanceof PyString)) {
         throw Py.TypeError("__str__ method must return a string");
      } else {
         return (PyString)ret;
      }
   }

   public PyUnicode __unicode__() {
      return this.instance___unicode__();
   }

   final PyUnicode instance___unicode__() {
      PyObject ret = this.invoke_ex("__unicode__");
      if (ret == null) {
         return super.__unicode__();
      } else if (ret instanceof PyUnicode) {
         return (PyUnicode)ret;
      } else if (ret instanceof PyString) {
         return new PyUnicode((PyString)ret);
      } else {
         throw Py.TypeError("__unicode__ must return unicode or str");
      }
   }

   public int hashCode() {
      PyObject ret = this.invoke_ex("__hash__");
      if (ret == null) {
         if (this.__findattr__("__eq__") == null && this.__findattr__("__cmp__") == null) {
            return super.hashCode();
         } else {
            throw Py.TypeError("unhashable instance");
         }
      } else if (ret instanceof PyInteger) {
         return ((PyInteger)ret).getValue();
      } else if (ret instanceof PyLong) {
         return ((PyLong)ret).hashCode();
      } else {
         throw Py.TypeError("__hash__() must really return int" + ret.getType());
      }
   }

   public int __cmp__(PyObject other) {
      return this.instance___cmp__(other);
   }

   final int instance___cmp__(PyObject other) {
      PyObject[] coerced = this._coerce(other);
      PyObject ret = null;
      Object v;
      PyObject w;
      if (coerced != null) {
         v = coerced[0];
         w = coerced[1];
         if (!(v instanceof PyInstance) && !(w instanceof PyInstance)) {
            return ((PyObject)v)._cmp(w);
         }
      } else {
         v = this;
         w = other;
      }

      int result;
      if (v instanceof PyInstance) {
         ret = ((PyInstance)v).invoke_ex("__cmp__", w);
         if (ret != null) {
            if (ret instanceof PyInteger) {
               result = ((PyInteger)ret).getValue();
               return result < 0 ? -1 : (result > 0 ? 1 : 0);
            }

            throw Py.TypeError("__cmp__() must return int");
         }
      }

      if (w instanceof PyInstance) {
         ret = ((PyInstance)w).invoke_ex("__cmp__", (PyObject)v);
         if (ret != null) {
            if (ret instanceof PyInteger) {
               result = ((PyInteger)ret).getValue();
               return -(result < 0 ? -1 : (result > 0 ? 1 : 0));
            }

            throw Py.TypeError("__cmp__() must return int");
         }
      }

      return -2;
   }

   private PyObject invoke_ex_richcmp(String name, PyObject o) {
      PyObject ret = this.invoke_ex(name, o);
      return ret == Py.NotImplemented ? null : ret;
   }

   public PyObject __lt__(PyObject o) {
      return this.instance___lt__(o);
   }

   final PyObject instance___lt__(PyObject o) {
      return this.invoke_ex_richcmp("__lt__", o);
   }

   public PyObject __le__(PyObject o) {
      return this.instance___le__(o);
   }

   final PyObject instance___le__(PyObject o) {
      return this.invoke_ex_richcmp("__le__", o);
   }

   public PyObject __gt__(PyObject o) {
      return this.instance___gt__(o);
   }

   final PyObject instance___gt__(PyObject o) {
      return this.invoke_ex_richcmp("__gt__", o);
   }

   public PyObject __ge__(PyObject o) {
      return this.instance___ge__(o);
   }

   final PyObject instance___ge__(PyObject o) {
      return this.invoke_ex_richcmp("__ge__", o);
   }

   public PyObject __eq__(PyObject o) {
      return this.instance___eq__(o);
   }

   final PyObject instance___eq__(PyObject o) {
      return this.invoke_ex_richcmp("__eq__", o);
   }

   public PyObject __ne__(PyObject o) {
      return this.instance___ne__(o);
   }

   final PyObject instance___ne__(PyObject o) {
      return this.invoke_ex_richcmp("__ne__", o);
   }

   public boolean __nonzero__() {
      return this.instance___nonzero__();
   }

   final boolean instance___nonzero__() {
      PyObject meth = null;

      try {
         meth = this.__findattr__("__nonzero__");
      } catch (PyException var4) {
      }

      if (meth == null) {
         try {
            meth = this.__findattr__("__len__");
         } catch (PyException var3) {
         }

         if (meth == null) {
            return true;
         }
      }

      PyObject ret = meth.__call__();
      return ret.__nonzero__();
   }

   public int __len__() {
      return this.instance___len__();
   }

   final int instance___len__() {
      PyObject ret = this.invoke("__len__");
      if (ret instanceof PyInteger) {
         return ((PyInteger)ret).getValue();
      } else {
         throw Py.TypeError("__len__() should return an int");
      }
   }

   public PyObject __finditem__(int key) {
      return this.__finditem__(new PyInteger(key));
   }

   private PyObject trySlice(String name, PyObject start, PyObject stop) {
      return this.trySlice(name, start, stop, (PyObject)null);
   }

   private PyObject trySlice(String name, PyObject start, PyObject stop, PyObject extraArg) {
      PyObject func = this.__findattr__(name);
      if (func == null) {
         return null;
      } else {
         PyObject[] indices = PySlice.indices2(this, start, stop);
         start = indices[0];
         stop = indices[1];
         return extraArg == null ? func.__call__(start, stop) : func.__call__(start, stop, extraArg);
      }
   }

   public PyObject __finditem__(PyObject key) {
      try {
         return this.invoke("__getitem__", key);
      } catch (PyException var3) {
         if (var3.match(Py.IndexError)) {
            return null;
         } else if (var3.match(Py.KeyError)) {
            return null;
         } else {
            throw var3;
         }
      }
   }

   public PyObject __getitem__(PyObject key) {
      return this.instance___getitem__(key);
   }

   final PyObject instance___getitem__(PyObject key) {
      return this.invoke("__getitem__", key);
   }

   public void __setitem__(PyObject key, PyObject value) {
      this.instance___setitem__(key, value);
   }

   final void instance___setitem__(PyObject key, PyObject value) {
      this.invoke("__setitem__", key, value);
   }

   public void __delitem__(PyObject key) {
      this.instance___delitem__(key);
   }

   final void instance___delitem__(PyObject key) {
      this.invoke("__delitem__", key);
   }

   public PyObject __getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.instance___getslice__(start, stop, step);
   }

   final PyObject instance___getslice__(PyObject start, PyObject stop, PyObject step) {
      if (step != null) {
         return this.__getitem__(new PySlice(start, stop, step));
      } else {
         PyObject ret = this.trySlice("__getslice__", start, stop);
         return ret != null ? ret : super.__getslice__(start, stop, step);
      }
   }

   public void __setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      this.instance___setslice__(start, stop, step, value);
   }

   final void instance___setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      if (step != null) {
         this.__setitem__(new PySlice(start, stop, step), value);
      } else if (this.trySlice("__setslice__", start, stop, value) == null) {
         super.__setslice__(start, stop, step, value);
      }

   }

   public void __delslice__(PyObject start, PyObject stop, PyObject step) {
      this.instance___delslice__(start, stop, step);
   }

   final void instance___delslice__(PyObject start, PyObject stop, PyObject step) {
      if (step != null) {
         this.__delitem__(new PySlice(start, stop, step));
      } else if (this.trySlice("__delslice__", start, stop) == null) {
         super.__delslice__(start, stop, step);
      }

   }

   public PyObject __iter__() {
      return this.instance___iter__();
   }

   final PyObject instance___iter__() {
      PyObject func = this.__findattr__("__iter__");
      if (func != null) {
         return func.__call__();
      } else {
         func = this.__findattr__("__getitem__");
         return (PyObject)(func == null ? super.__iter__() : new PySequenceIter(this));
      }
   }

   public PyObject __iternext__() {
      PyObject func = this.__findattr__("next");
      if (func != null) {
         try {
            return func.__call__();
         } catch (PyException var3) {
            if (var3.match(Py.StopIteration)) {
               return null;
            } else {
               throw var3;
            }
         }
      } else {
         throw Py.TypeError("instance has no next() method");
      }
   }

   public boolean __contains__(PyObject o) {
      return this.instance___contains__(o);
   }

   final boolean instance___contains__(PyObject o) {
      PyObject func = this.__findattr__("__contains__");
      if (func == null) {
         return super.__contains__(o);
      } else {
         PyObject ret = func.__call__(o);
         return ret.__nonzero__();
      }
   }

   public Object __coerce_ex__(PyObject o) {
      PyObject ret = this.invoke_ex("__coerce__", o);
      if (ret != null && ret != Py.None) {
         if (!(ret instanceof PyTuple)) {
            throw Py.TypeError("coercion should return None or 2-tuple");
         } else {
            return ((PyTuple)ret).getArray();
         }
      } else {
         return ret;
      }
   }

   public PyObject __index__() {
      return this.instance___index__();
   }

   final PyObject instance___index__() {
      PyObject ret;
      try {
         ret = this.invoke("__index__");
      } catch (PyException var3) {
         if (!var3.match(Py.AttributeError)) {
            throw var3;
         }

         throw Py.TypeError("object cannot be interpreted as an index");
      }

      if (!(ret instanceof PyInteger) && !(ret instanceof PyLong)) {
         throw Py.TypeError(String.format("__index__ returned non-(int,long) (type %s)", ret.getType().fastGetName()));
      } else {
         return ret;
      }
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.instance___format__(formatSpec);
   }

   final PyObject instance___format__(PyObject formatSpec) {
      PyObject func = this.__findattr__("__format__");
      return func == null ? super.__format__(formatSpec) : func.__call__(formatSpec);
   }

   public PyString __hex__() {
      return this.instance___hex__();
   }

   final PyString instance___hex__() {
      PyObject ret = this.invoke("__hex__");
      if (ret instanceof PyString) {
         return (PyString)ret;
      } else {
         throw Py.TypeError("__hex__() should return a string");
      }
   }

   public PyString __oct__() {
      return this.instance___oct__();
   }

   final PyString instance___oct__() {
      PyObject ret = this.invoke("__oct__");
      if (ret instanceof PyString) {
         return (PyString)ret;
      } else {
         throw Py.TypeError("__oct__() should return a string");
      }
   }

   public PyObject __int__() {
      return this.instance___int__();
   }

   final PyObject instance___int__() {
      PyObject ret = this.invoke("__int__");
      if (!(ret instanceof PyLong) && !(ret instanceof PyInteger)) {
         throw Py.TypeError("__int__() should return a int");
      } else {
         return ret;
      }
   }

   public PyFloat __float__() {
      return this.instance___float__();
   }

   final PyFloat instance___float__() {
      PyObject ret = this.invoke("__float__");
      if (ret instanceof PyFloat) {
         return (PyFloat)ret;
      } else {
         throw Py.TypeError("__float__() should return a float");
      }
   }

   public PyObject __long__() {
      return this.instance___long__();
   }

   final PyObject instance___long__() {
      PyObject ret = this.invoke("__long__");
      if (!(ret instanceof PyLong) && !(ret instanceof PyInteger)) {
         throw Py.TypeError("__long__() should return a long");
      } else {
         return ret;
      }
   }

   public PyComplex __complex__() {
      return this.instance___complex__();
   }

   final PyComplex instance___complex__() {
      PyObject ret = this.invoke("__complex__");
      if (ret instanceof PyComplex) {
         return (PyComplex)ret;
      } else {
         throw Py.TypeError("__complex__() should return a complex");
      }
   }

   public PyObject __pos__() {
      return this.instance___pos__();
   }

   public PyObject instance___pos__() {
      return this.invoke("__pos__");
   }

   public PyObject __neg__() {
      return this.instance___neg__();
   }

   public PyObject instance___neg__() {
      return this.invoke("__neg__");
   }

   public PyObject __abs__() {
      return this.instance___abs__();
   }

   public PyObject instance___abs__() {
      return this.invoke("__abs__");
   }

   public PyObject __invert__() {
      return this.instance___invert__();
   }

   public PyObject instance___invert__() {
      return this.invoke("__invert__");
   }

   public PyObject __add__(PyObject o) {
      return this.instance___add__(o);
   }

   public PyObject instance___add__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__add__", o2) : o1._add(o2);
      } else {
         return this.invoke_ex("__add__", o);
      }
   }

   public PyObject __radd__(PyObject o) {
      return this.instance___radd__(o);
   }

   public PyObject instance___radd__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__radd__", o2) : o2._add(o1);
      } else {
         return this.invoke_ex("__radd__", o);
      }
   }

   public PyObject __iadd__(PyObject o) {
      return this.instance___iadd__(o);
   }

   public PyObject instance___iadd__(PyObject o) {
      PyObject ret = this.invoke_ex("__iadd__", o);
      return ret != null ? ret : super.__iadd__(o);
   }

   public PyObject __sub__(PyObject o) {
      return this.instance___sub__(o);
   }

   public PyObject instance___sub__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__sub__", o2) : o1._sub(o2);
      } else {
         return this.invoke_ex("__sub__", o);
      }
   }

   public PyObject __rsub__(PyObject o) {
      return this.instance___rsub__(o);
   }

   public PyObject instance___rsub__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rsub__", o2) : o2._sub(o1);
      } else {
         return this.invoke_ex("__rsub__", o);
      }
   }

   public PyObject __isub__(PyObject o) {
      return this.instance___isub__(o);
   }

   public PyObject instance___isub__(PyObject o) {
      PyObject ret = this.invoke_ex("__isub__", o);
      return ret != null ? ret : super.__isub__(o);
   }

   public PyObject __mul__(PyObject o) {
      return this.instance___mul__(o);
   }

   public PyObject instance___mul__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__mul__", o2) : o1._mul(o2);
      } else {
         return this.invoke_ex("__mul__", o);
      }
   }

   public PyObject __rmul__(PyObject o) {
      return this.instance___rmul__(o);
   }

   public PyObject instance___rmul__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rmul__", o2) : o2._mul(o1);
      } else {
         return this.invoke_ex("__rmul__", o);
      }
   }

   public PyObject __imul__(PyObject o) {
      return this.instance___imul__(o);
   }

   public PyObject instance___imul__(PyObject o) {
      PyObject ret = this.invoke_ex("__imul__", o);
      return ret != null ? ret : super.__imul__(o);
   }

   public PyObject __div__(PyObject o) {
      return this.instance___div__(o);
   }

   public PyObject instance___div__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__div__", o2) : o1._div(o2);
      } else {
         return this.invoke_ex("__div__", o);
      }
   }

   public PyObject __rdiv__(PyObject o) {
      return this.instance___rdiv__(o);
   }

   public PyObject instance___rdiv__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rdiv__", o2) : o2._div(o1);
      } else {
         return this.invoke_ex("__rdiv__", o);
      }
   }

   public PyObject __idiv__(PyObject o) {
      return this.instance___idiv__(o);
   }

   public PyObject instance___idiv__(PyObject o) {
      PyObject ret = this.invoke_ex("__idiv__", o);
      return ret != null ? ret : super.__idiv__(o);
   }

   public PyObject __floordiv__(PyObject o) {
      return this.instance___floordiv__(o);
   }

   public PyObject instance___floordiv__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__floordiv__", o2) : o1._floordiv(o2);
      } else {
         return this.invoke_ex("__floordiv__", o);
      }
   }

   public PyObject __rfloordiv__(PyObject o) {
      return this.instance___rfloordiv__(o);
   }

   public PyObject instance___rfloordiv__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rfloordiv__", o2) : o2._floordiv(o1);
      } else {
         return this.invoke_ex("__rfloordiv__", o);
      }
   }

   public PyObject __ifloordiv__(PyObject o) {
      return this.instance___ifloordiv__(o);
   }

   public PyObject instance___ifloordiv__(PyObject o) {
      PyObject ret = this.invoke_ex("__ifloordiv__", o);
      return ret != null ? ret : super.__ifloordiv__(o);
   }

   public PyObject __truediv__(PyObject o) {
      return this.instance___truediv__(o);
   }

   public PyObject instance___truediv__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__truediv__", o2) : o1._truediv(o2);
      } else {
         return this.invoke_ex("__truediv__", o);
      }
   }

   public PyObject __rtruediv__(PyObject o) {
      return this.instance___rtruediv__(o);
   }

   public PyObject instance___rtruediv__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rtruediv__", o2) : o2._truediv(o1);
      } else {
         return this.invoke_ex("__rtruediv__", o);
      }
   }

   public PyObject __itruediv__(PyObject o) {
      return this.instance___itruediv__(o);
   }

   public PyObject instance___itruediv__(PyObject o) {
      PyObject ret = this.invoke_ex("__itruediv__", o);
      return ret != null ? ret : super.__itruediv__(o);
   }

   public PyObject __mod__(PyObject o) {
      return this.instance___mod__(o);
   }

   public PyObject instance___mod__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__mod__", o2) : o1._mod(o2);
      } else {
         return this.invoke_ex("__mod__", o);
      }
   }

   public PyObject __rmod__(PyObject o) {
      return this.instance___rmod__(o);
   }

   public PyObject instance___rmod__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rmod__", o2) : o2._mod(o1);
      } else {
         return this.invoke_ex("__rmod__", o);
      }
   }

   public PyObject __imod__(PyObject o) {
      return this.instance___imod__(o);
   }

   public PyObject instance___imod__(PyObject o) {
      PyObject ret = this.invoke_ex("__imod__", o);
      return ret != null ? ret : super.__imod__(o);
   }

   public PyObject __divmod__(PyObject o) {
      return this.instance___divmod__(o);
   }

   public PyObject instance___divmod__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__divmod__", o2) : o1._divmod(o2);
      } else {
         return this.invoke_ex("__divmod__", o);
      }
   }

   public PyObject __rdivmod__(PyObject o) {
      return this.instance___rdivmod__(o);
   }

   public PyObject instance___rdivmod__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rdivmod__", o2) : o2._divmod(o1);
      } else {
         return this.invoke_ex("__rdivmod__", o);
      }
   }

   public PyObject __pow__(PyObject o) {
      return this.instance___pow__(o);
   }

   public PyObject instance___pow__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__pow__", o2) : o1._pow(o2);
      } else {
         return this.invoke_ex("__pow__", o);
      }
   }

   public PyObject __rpow__(PyObject o) {
      return this.instance___rpow__(o);
   }

   public PyObject instance___rpow__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rpow__", o2) : o2._pow(o1);
      } else {
         return this.invoke_ex("__rpow__", o);
      }
   }

   public PyObject __ipow__(PyObject o) {
      return this.instance___ipow__(o);
   }

   public PyObject instance___ipow__(PyObject o) {
      PyObject ret = this.invoke_ex("__ipow__", o);
      return ret != null ? ret : super.__ipow__(o);
   }

   public PyObject __lshift__(PyObject o) {
      return this.instance___lshift__(o);
   }

   public PyObject instance___lshift__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__lshift__", o2) : o1._lshift(o2);
      } else {
         return this.invoke_ex("__lshift__", o);
      }
   }

   public PyObject __rlshift__(PyObject o) {
      return this.instance___rlshift__(o);
   }

   public PyObject instance___rlshift__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rlshift__", o2) : o2._lshift(o1);
      } else {
         return this.invoke_ex("__rlshift__", o);
      }
   }

   public PyObject __ilshift__(PyObject o) {
      return this.instance___ilshift__(o);
   }

   public PyObject instance___ilshift__(PyObject o) {
      PyObject ret = this.invoke_ex("__ilshift__", o);
      return ret != null ? ret : super.__ilshift__(o);
   }

   public PyObject __rshift__(PyObject o) {
      return this.instance___rshift__(o);
   }

   public PyObject instance___rshift__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rshift__", o2) : o1._rshift(o2);
      } else {
         return this.invoke_ex("__rshift__", o);
      }
   }

   public PyObject __rrshift__(PyObject o) {
      return this.instance___rrshift__(o);
   }

   public PyObject instance___rrshift__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rrshift__", o2) : o2._rshift(o1);
      } else {
         return this.invoke_ex("__rrshift__", o);
      }
   }

   public PyObject __irshift__(PyObject o) {
      return this.instance___irshift__(o);
   }

   public PyObject instance___irshift__(PyObject o) {
      PyObject ret = this.invoke_ex("__irshift__", o);
      return ret != null ? ret : super.__irshift__(o);
   }

   public PyObject __and__(PyObject o) {
      return this.instance___and__(o);
   }

   public PyObject instance___and__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__and__", o2) : o1._and(o2);
      } else {
         return this.invoke_ex("__and__", o);
      }
   }

   public PyObject __rand__(PyObject o) {
      return this.instance___rand__(o);
   }

   public PyObject instance___rand__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rand__", o2) : o2._and(o1);
      } else {
         return this.invoke_ex("__rand__", o);
      }
   }

   public PyObject __iand__(PyObject o) {
      return this.instance___iand__(o);
   }

   public PyObject instance___iand__(PyObject o) {
      PyObject ret = this.invoke_ex("__iand__", o);
      return ret != null ? ret : super.__iand__(o);
   }

   public PyObject __or__(PyObject o) {
      return this.instance___or__(o);
   }

   public PyObject instance___or__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__or__", o2) : o1._or(o2);
      } else {
         return this.invoke_ex("__or__", o);
      }
   }

   public PyObject __ror__(PyObject o) {
      return this.instance___ror__(o);
   }

   public PyObject instance___ror__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__ror__", o2) : o2._or(o1);
      } else {
         return this.invoke_ex("__ror__", o);
      }
   }

   public PyObject __ior__(PyObject o) {
      return this.instance___ior__(o);
   }

   public PyObject instance___ior__(PyObject o) {
      PyObject ret = this.invoke_ex("__ior__", o);
      return ret != null ? ret : super.__ior__(o);
   }

   public PyObject __xor__(PyObject o) {
      return this.instance___xor__(o);
   }

   public PyObject instance___xor__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__xor__", o2) : o1._xor(o2);
      } else {
         return this.invoke_ex("__xor__", o);
      }
   }

   public PyObject __rxor__(PyObject o) {
      return this.instance___rxor__(o);
   }

   public PyObject instance___rxor__(PyObject o) {
      Object ctmp = this.__coerce_ex__(o);
      if (ctmp != null && ctmp != Py.None) {
         PyObject o1 = ((PyObject[])((PyObject[])ctmp))[0];
         PyObject o2 = ((PyObject[])((PyObject[])ctmp))[1];
         return this == o1 ? this.invoke_ex("__rxor__", o2) : o2._xor(o1);
      } else {
         return this.invoke_ex("__rxor__", o);
      }
   }

   public PyObject __ixor__(PyObject o) {
      return this.instance___ixor__(o);
   }

   public PyObject instance___ixor__(PyObject o) {
      PyObject ret = this.invoke_ex("__ixor__", o);
      return ret != null ? ret : super.__ixor__(o);
   }

   public void __del__() {
      try {
         PyObject method = this.__findattr__("__del__");
         if (method != null) {
            method.__call__();
         } else if (this.instclass.__del__ != null) {
            this.instclass.__del__.__call__((PyObject)this);
         }
      } catch (PyException var5) {
         PyObject method = this.instclass.__del__;

         try {
            method = this.__findattr__("__del__");
         } catch (PyException var4) {
         }

         Py.writeUnraisable(var5, method);
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.instclass != null) {
         int retVal = visit.visit(this.instclass, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return visit.visit(this.__dict__, arg);
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.instclass || ob == this.__dict__);
   }

   static {
      PyType.addBuilder(PyInstance.class, new PyExposer());
      TYPE = PyType.fromClass(PyInstance.class);
   }

   private static class instance___setattr___exposer extends PyBuiltinMethodNarrow {
      public instance___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public instance___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyInstance)this.self).instance___setattr__(var1.asString(), var2);
         return Py.None;
      }
   }

   private static class instance___delattr___exposer extends PyBuiltinMethodNarrow {
      public instance___delattr___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___delattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___delattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyInstance)this.self).instance___delattr__(var1.asString());
         return Py.None;
      }
   }

   private static class instance___call___exposer extends PyBuiltinMethod {
      public instance___call___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public instance___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyInstance)this.self).instance___call__(var1, var2);
      }
   }

   private static class instance___repr___exposer extends PyBuiltinMethodNarrow {
      public instance___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___repr__();
      }
   }

   private static class instance___str___exposer extends PyBuiltinMethodNarrow {
      public instance___str___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___str___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___str___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___str__();
      }
   }

   private static class instance___unicode___exposer extends PyBuiltinMethodNarrow {
      public instance___unicode___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___unicode___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___unicode___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___unicode__();
      }
   }

   private static class instance___cmp___exposer extends PyBuiltinMethodNarrow {
      public instance___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyInstance)this.self).instance___cmp__(var1));
      }
   }

   private static class instance___lt___exposer extends PyBuiltinMethodNarrow {
      public instance___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___lt__(var1);
      }
   }

   private static class instance___le___exposer extends PyBuiltinMethodNarrow {
      public instance___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___le__(var1);
      }
   }

   private static class instance___gt___exposer extends PyBuiltinMethodNarrow {
      public instance___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___gt__(var1);
      }
   }

   private static class instance___ge___exposer extends PyBuiltinMethodNarrow {
      public instance___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___ge__(var1);
      }
   }

   private static class instance___eq___exposer extends PyBuiltinMethodNarrow {
      public instance___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___eq__(var1);
      }
   }

   private static class instance___ne___exposer extends PyBuiltinMethodNarrow {
      public instance___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___ne__(var1);
      }
   }

   private static class instance___nonzero___exposer extends PyBuiltinMethodNarrow {
      public instance___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((PyInstance)this.self).instance___nonzero__());
      }
   }

   private static class instance___len___exposer extends PyBuiltinMethodNarrow {
      public instance___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyInstance)this.self).instance___len__());
      }
   }

   private static class instance___getitem___exposer extends PyBuiltinMethodNarrow {
      public instance___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___getitem__(var1);
      }
   }

   private static class instance___setitem___exposer extends PyBuiltinMethodNarrow {
      public instance___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public instance___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyInstance)this.self).instance___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class instance___delitem___exposer extends PyBuiltinMethodNarrow {
      public instance___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyInstance)this.self).instance___delitem__(var1);
         return Py.None;
      }
   }

   private static class instance___getslice___exposer extends PyBuiltinMethodNarrow {
      public instance___getslice___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public instance___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyInstance)this.self).instance___getslice__(var1, var2, var3);
      }
   }

   private static class instance___setslice___exposer extends PyBuiltinMethodNarrow {
      public instance___setslice___exposer(String var1) {
         super(var1, 5, 5);
         super.doc = "";
      }

      public instance___setslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___setslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3, PyObject var4) {
         ((PyInstance)this.self).instance___setslice__(var1, var2, var3, var4);
         return Py.None;
      }
   }

   private static class instance___delslice___exposer extends PyBuiltinMethodNarrow {
      public instance___delslice___exposer(String var1) {
         super(var1, 4, 4);
         super.doc = "";
      }

      public instance___delslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___delslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyInstance)this.self).instance___delslice__(var1, var2, var3);
         return Py.None;
      }
   }

   private static class instance___iter___exposer extends PyBuiltinMethodNarrow {
      public instance___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___iter__();
      }
   }

   private static class instance___contains___exposer extends PyBuiltinMethodNarrow {
      public instance___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyInstance)this.self).instance___contains__(var1));
      }
   }

   private static class instance___index___exposer extends PyBuiltinMethodNarrow {
      public instance___index___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___index___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___index___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___index__();
      }
   }

   private static class instance___format___exposer extends PyBuiltinMethodNarrow {
      public instance___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyInstance)this.self).instance___format__(var1);
      }
   }

   private static class instance___hex___exposer extends PyBuiltinMethodNarrow {
      public instance___hex___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___hex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___hex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___hex__();
      }
   }

   private static class instance___oct___exposer extends PyBuiltinMethodNarrow {
      public instance___oct___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___oct___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___oct___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___oct__();
      }
   }

   private static class instance___int___exposer extends PyBuiltinMethodNarrow {
      public instance___int___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___int___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___int___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___int__();
      }
   }

   private static class instance___float___exposer extends PyBuiltinMethodNarrow {
      public instance___float___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___float___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___float___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___float__();
      }
   }

   private static class instance___long___exposer extends PyBuiltinMethodNarrow {
      public instance___long___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___long___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___long___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___long__();
      }
   }

   private static class instance___complex___exposer extends PyBuiltinMethodNarrow {
      public instance___complex___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___complex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___complex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___complex__();
      }
   }

   private static class instance___pos___exposer extends PyBuiltinMethodNarrow {
      public instance___pos___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___pos___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___pos___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___pos__();
      }
   }

   private static class instance___neg___exposer extends PyBuiltinMethodNarrow {
      public instance___neg___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___neg___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___neg___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___neg__();
      }
   }

   private static class instance___abs___exposer extends PyBuiltinMethodNarrow {
      public instance___abs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___abs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___abs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___abs__();
      }
   }

   private static class instance___invert___exposer extends PyBuiltinMethodNarrow {
      public instance___invert___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public instance___invert___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___invert___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyInstance)this.self).instance___invert__();
      }
   }

   private static class instance___add___exposer extends PyBuiltinMethodNarrow {
      public instance___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___radd___exposer extends PyBuiltinMethodNarrow {
      public instance___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___iadd___exposer extends PyBuiltinMethodNarrow {
      public instance___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___iadd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___sub___exposer extends PyBuiltinMethodNarrow {
      public instance___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rsub___exposer extends PyBuiltinMethodNarrow {
      public instance___rsub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rsub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rsub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rsub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___isub___exposer extends PyBuiltinMethodNarrow {
      public instance___isub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___isub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___isub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___isub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___mul___exposer extends PyBuiltinMethodNarrow {
      public instance___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rmul___exposer extends PyBuiltinMethodNarrow {
      public instance___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___imul___exposer extends PyBuiltinMethodNarrow {
      public instance___imul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___imul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___imul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___imul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___div___exposer extends PyBuiltinMethodNarrow {
      public instance___div___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___div___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___div___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___div__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rdiv___exposer extends PyBuiltinMethodNarrow {
      public instance___rdiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rdiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rdiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rdiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___idiv___exposer extends PyBuiltinMethodNarrow {
      public instance___idiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___idiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___idiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___idiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___floordiv___exposer extends PyBuiltinMethodNarrow {
      public instance___floordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___floordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___floordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___floordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rfloordiv___exposer extends PyBuiltinMethodNarrow {
      public instance___rfloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rfloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rfloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rfloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ifloordiv___exposer extends PyBuiltinMethodNarrow {
      public instance___ifloordiv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ifloordiv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ifloordiv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ifloordiv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___truediv___exposer extends PyBuiltinMethodNarrow {
      public instance___truediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___truediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___truediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___truediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rtruediv___exposer extends PyBuiltinMethodNarrow {
      public instance___rtruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rtruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rtruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rtruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___itruediv___exposer extends PyBuiltinMethodNarrow {
      public instance___itruediv___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___itruediv___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___itruediv___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___itruediv__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___mod___exposer extends PyBuiltinMethodNarrow {
      public instance___mod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___mod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___mod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___mod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rmod___exposer extends PyBuiltinMethodNarrow {
      public instance___rmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___imod___exposer extends PyBuiltinMethodNarrow {
      public instance___imod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___imod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___imod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___imod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___divmod___exposer extends PyBuiltinMethodNarrow {
      public instance___divmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___divmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___divmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___divmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rdivmod___exposer extends PyBuiltinMethodNarrow {
      public instance___rdivmod___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rdivmod___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rdivmod___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rdivmod__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___pow___exposer extends PyBuiltinMethodNarrow {
      public instance___pow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___pow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___pow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___pow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rpow___exposer extends PyBuiltinMethodNarrow {
      public instance___rpow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rpow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rpow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rpow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ipow___exposer extends PyBuiltinMethodNarrow {
      public instance___ipow___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ipow___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ipow___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ipow__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___lshift___exposer extends PyBuiltinMethodNarrow {
      public instance___lshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___lshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___lshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___lshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rlshift___exposer extends PyBuiltinMethodNarrow {
      public instance___rlshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rlshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rlshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rlshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ilshift___exposer extends PyBuiltinMethodNarrow {
      public instance___ilshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ilshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ilshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ilshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rshift___exposer extends PyBuiltinMethodNarrow {
      public instance___rshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rrshift___exposer extends PyBuiltinMethodNarrow {
      public instance___rrshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rrshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rrshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rrshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___irshift___exposer extends PyBuiltinMethodNarrow {
      public instance___irshift___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___irshift___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___irshift___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___irshift__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___and___exposer extends PyBuiltinMethodNarrow {
      public instance___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rand___exposer extends PyBuiltinMethodNarrow {
      public instance___rand___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rand___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rand___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rand__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___iand___exposer extends PyBuiltinMethodNarrow {
      public instance___iand___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___iand___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___iand___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___iand__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___or___exposer extends PyBuiltinMethodNarrow {
      public instance___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ror___exposer extends PyBuiltinMethodNarrow {
      public instance___ror___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ror___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ror___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ror__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ior___exposer extends PyBuiltinMethodNarrow {
      public instance___ior___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ior___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ior___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ior__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___xor___exposer extends PyBuiltinMethodNarrow {
      public instance___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___rxor___exposer extends PyBuiltinMethodNarrow {
      public instance___rxor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___rxor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___rxor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___rxor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class instance___ixor___exposer extends PyBuiltinMethodNarrow {
      public instance___ixor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public instance___ixor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new instance___ixor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyInstance)this.self).instance___ixor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyInstance.instance___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new instance___setattr___exposer("__setattr__"), new instance___delattr___exposer("__delattr__"), new instance___call___exposer("__call__"), new instance___repr___exposer("__repr__"), new instance___str___exposer("__str__"), new instance___unicode___exposer("__unicode__"), new instance___cmp___exposer("__cmp__"), new instance___lt___exposer("__lt__"), new instance___le___exposer("__le__"), new instance___gt___exposer("__gt__"), new instance___ge___exposer("__ge__"), new instance___eq___exposer("__eq__"), new instance___ne___exposer("__ne__"), new instance___nonzero___exposer("__nonzero__"), new instance___len___exposer("__len__"), new instance___getitem___exposer("__getitem__"), new instance___setitem___exposer("__setitem__"), new instance___delitem___exposer("__delitem__"), new instance___getslice___exposer("__getslice__"), new instance___setslice___exposer("__setslice__"), new instance___delslice___exposer("__delslice__"), new instance___iter___exposer("__iter__"), new instance___contains___exposer("__contains__"), new instance___index___exposer("__index__"), new instance___format___exposer("__format__"), new instance___hex___exposer("__hex__"), new instance___oct___exposer("__oct__"), new instance___int___exposer("__int__"), new instance___float___exposer("__float__"), new instance___long___exposer("__long__"), new instance___complex___exposer("__complex__"), new instance___pos___exposer("__pos__"), new instance___neg___exposer("__neg__"), new instance___abs___exposer("__abs__"), new instance___invert___exposer("__invert__"), new instance___add___exposer("__add__"), new instance___radd___exposer("__radd__"), new instance___iadd___exposer("__iadd__"), new instance___sub___exposer("__sub__"), new instance___rsub___exposer("__rsub__"), new instance___isub___exposer("__isub__"), new instance___mul___exposer("__mul__"), new instance___rmul___exposer("__rmul__"), new instance___imul___exposer("__imul__"), new instance___div___exposer("__div__"), new instance___rdiv___exposer("__rdiv__"), new instance___idiv___exposer("__idiv__"), new instance___floordiv___exposer("__floordiv__"), new instance___rfloordiv___exposer("__rfloordiv__"), new instance___ifloordiv___exposer("__ifloordiv__"), new instance___truediv___exposer("__truediv__"), new instance___rtruediv___exposer("__rtruediv__"), new instance___itruediv___exposer("__itruediv__"), new instance___mod___exposer("__mod__"), new instance___rmod___exposer("__rmod__"), new instance___imod___exposer("__imod__"), new instance___divmod___exposer("__divmod__"), new instance___rdivmod___exposer("__rdivmod__"), new instance___pow___exposer("__pow__"), new instance___rpow___exposer("__rpow__"), new instance___ipow___exposer("__ipow__"), new instance___lshift___exposer("__lshift__"), new instance___rlshift___exposer("__rlshift__"), new instance___ilshift___exposer("__ilshift__"), new instance___rshift___exposer("__rshift__"), new instance___rrshift___exposer("__rrshift__"), new instance___irshift___exposer("__irshift__"), new instance___and___exposer("__and__"), new instance___rand___exposer("__rand__"), new instance___iand___exposer("__iand__"), new instance___or___exposer("__or__"), new instance___ror___exposer("__ror__"), new instance___ior___exposer("__ior__"), new instance___xor___exposer("__xor__"), new instance___rxor___exposer("__rxor__"), new instance___ixor___exposer("__ixor__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("instance", PyInstance.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
