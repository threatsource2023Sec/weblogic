package org.python.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.modules.gc;
import org.python.util.Generic;

@ExposedType(
   name = "object",
   doc = "The most base type"
)
public class PyObject implements Serializable {
   public static final PyType TYPE;
   public static boolean gcMonitorGlobal;
   protected PyType objtype;
   protected Object attributes;
   private static final Map primitiveMap;

   public PyObject(PyType objtype) {
      this.objtype = objtype;
      if (gcMonitorGlobal) {
         gc.monitorObject(this);
      }

   }

   public PyObject() {
      this.objtype = PyType.fromClass(this.getClass(), false);
      if (gcMonitorGlobal) {
         gc.monitorObject(this);
      }

   }

   PyObject(boolean ignored) {
      this.objtype = (PyType)this;
      if (gcMonitorGlobal) {
         gc.monitorObject(this);
      }

   }

   @ExposedNew
   static final PyObject object___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      PyObject[] where = new PyObject[1];
      subtype.lookup_where("__init__", where);
      if (where[0] == TYPE && args.length > 0) {
         throw Py.TypeError("object.__new__() takes no parameters");
      } else if (subtype.isAbstract()) {
         PyObject sorted = Py.getSystemState().getBuiltins().__getitem__(Py.newString("sorted"));
         PyString methods = Py.newString(", ").join(sorted.__call__(subtype.getAbstractmethods()));
         throw Py.TypeError(String.format("Can't instantiate abstract class %s with abstract methods %s", subtype.fastGetName(), methods));
      } else {
         return (PyObject)(new_.for_type == subtype ? new PyObject() : new PyObjectDerived(subtype));
      }
   }

   protected final void finalize() throws Throwable {
   }

   final void object___init__(PyObject[] args, String[] keywords) {
   }

   public PyType getType() {
      return this.objtype;
   }

   public void setType(PyType type) {
      if (!type.builtin && !this.getType().builtin) {
         type.compatibleForAssignment(this.getType(), "__class__");
         this.objtype = type;
      } else {
         throw Py.TypeError("__class__ assignment: only for heap types");
      }
   }

   public void delType() {
      throw Py.TypeError("can't delete __class__ attribute");
   }

   public PyObject fastGetClass() {
      return this.objtype;
   }

   public void dispatch__init__(PyObject[] args, String[] keywords) {
   }

   void proxyInit() {
      Class c = this.getType().getProxyType();
      Object javaProxy = JyAttribute.getAttr(this, (byte)-128);
      if (javaProxy == null && c != null) {
         if (!PyProxy.class.isAssignableFrom(c)) {
            throw Py.SystemError("Automatic proxy initialization should only occur on proxy classes");
         } else {
            Object[] previous = (Object[])ThreadContext.initializingProxy.get();
            ThreadContext.initializingProxy.set(new Object[]{this});

            PyProxy proxy;
            try {
               proxy = (PyProxy)c.newInstance();
            } catch (InstantiationException var13) {
               Class sup = c.getSuperclass();
               String msg = "Default constructor failed for Java superclass";
               if (sup != null) {
                  msg = msg + " " + sup.getName();
               }

               throw Py.TypeError(msg);
            } catch (NoSuchMethodError var14) {
               throw Py.TypeError("constructor requires arguments");
            } catch (Exception var15) {
               throw Py.JavaError(var15);
            } finally {
               ThreadContext.initializingProxy.set(previous);
            }

            javaProxy = JyAttribute.getAttr(this, (byte)-128);
            if (javaProxy != null && javaProxy != proxy) {
               throw Py.TypeError("Proxy instance already initialized");
            } else {
               PyObject proxyInstance = proxy._getPyInstance();
               if (proxyInstance != null && proxyInstance != this) {
                  throw Py.TypeError("Proxy initialized with another instance");
               } else {
                  JyAttribute.setAttr(this, (byte)-128, proxy);
               }
            }
         }
      }
   }

   public PyString __repr__() {
      return new PyString(this.toString());
   }

   public String toString() {
      return this.object_toString();
   }

   final String object_toString() {
      if (this.getType() == null) {
         return "unknown object";
      } else {
         String name = this.getType().getName();
         if (name == null) {
            return "unknown object";
         } else {
            PyObject module = this.getType().getModule();
            return module instanceof PyString && !module.toString().equals("__builtin__") ? String.format("<%s.%s object at %s>", module.toString(), name, Py.idstr(this)) : String.format("<%s object at %s>", name, Py.idstr(this));
         }
      }
   }

   public PyString __str__() {
      return this.__repr__();
   }

   public void __ensure_finalizer__() {
   }

   public PyUnicode __unicode__() {
      return new PyUnicode(this.__str__());
   }

   public final PyInteger __hash__() {
      return new PyInteger(this.hashCode());
   }

   public int hashCode() {
      return this.object___hash__();
   }

   final int object___hash__() {
      return System.identityHashCode(this);
   }

   public boolean equals(Object ob_other) {
      if (ob_other == this) {
         return true;
      } else {
         return ob_other instanceof PyObject && this._eq((PyObject)ob_other).__nonzero__();
      }
   }

   public boolean __nonzero__() {
      return true;
   }

   public Object __tojava__(Class c) {
      Object proxy = this.getJavaProxy();
      if ((c == Object.class || c == Serializable.class) && proxy != null) {
         return proxy;
      } else if (c.isInstance(this)) {
         return this;
      } else {
         Class component;
         if (c.isPrimitive()) {
            component = (Class)primitiveMap.get(c);
            if (component != null) {
               c = component;
            }
         }

         if (c.isInstance(proxy)) {
            return proxy;
         } else {
            if (c == Double.class || c == Float.class) {
               try {
                  return this.__float__().asDouble();
               } catch (PyException var9) {
                  if (!var9.match(Py.AttributeError)) {
                     throw var9;
                  }
               }
            }

            if (c.isArray()) {
               component = c.getComponentType();

               try {
                  int n = this.__len__();
                  PyArray array = new PyArray(component, n);

                  for(int i = 0; i < n; ++i) {
                     PyObject o = this.__getitem__(i);
                     array.set(i, o);
                  }

                  return array.getArray();
               } catch (Throwable var8) {
               }
            }

            return Py.NoConversion;
         }
      }
   }

   protected Object getJavaProxy() {
      Object ob = JyAttribute.getAttr(this, (byte)-128);
      if (ob == null) {
         synchronized(this) {
            this.proxyInit();
            ob = JyAttribute.getAttr(this, (byte)-128);
         }
      }

      return ob;
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      throw Py.TypeError(String.format("'%s' object is not callable", this.getType().fastGetName()));
   }

   public PyObject __call__(ThreadState state, PyObject[] args, String[] keywords) {
      return this.__call__(args, keywords);
   }

   public PyObject __call__(PyObject arg1, PyObject[] args, String[] keywords) {
      PyObject[] newArgs = new PyObject[args.length + 1];
      System.arraycopy(args, 0, newArgs, 1, args.length);
      newArgs[0] = arg1;
      return this.__call__(newArgs, keywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg1, PyObject[] args, String[] keywords) {
      return this.__call__(arg1, args, keywords);
   }

   public PyObject __call__(PyObject[] args) {
      return this.__call__(args, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state, PyObject[] args) {
      return this.__call__(args);
   }

   public PyObject __call__() {
      return this.__call__(Py.EmptyObjects, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state) {
      return this.__call__();
   }

   public PyObject __call__(PyObject arg0) {
      return this.__call__(new PyObject[]{arg0}, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg0) {
      return this.__call__(arg0);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1) {
      return this.__call__(new PyObject[]{arg0, arg1}, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1) {
      return this.__call__(arg0, arg1);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2) {
      return this.__call__(new PyObject[]{arg0, arg1, arg2}, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2) {
      return this.__call__(arg0, arg1, arg2);
   }

   public PyObject __call__(PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__call__(new PyObject[]{arg0, arg1, arg2, arg3}, Py.NoKeywords);
   }

   public PyObject __call__(ThreadState state, PyObject arg0, PyObject arg1, PyObject arg2, PyObject arg3) {
      return this.__call__(arg0, arg1, arg2, arg3);
   }

   public PyObject _callextra(PyObject[] args, String[] keywords, PyObject starargs, PyObject kwargs) {
      int argslen = args.length;
      String name;
      if (this instanceof PyFunction) {
         name = ((PyFunction)this).__name__ + "() ";
      } else if (this instanceof PyBuiltinCallable) {
         name = ((PyBuiltinCallable)this).fastGetName().toString() + "() ";
      } else {
         name = this.getType().fastGetName() + " ";
      }

      int argidx;
      if (kwargs != null) {
         PyObject keys = kwargs.__findattr__("keys");
         if (keys == null) {
            throw Py.TypeError(name + "argument after ** must be a mapping");
         }

         String[] var8 = keywords;
         argidx = keywords.length;

         for(int var10 = 0; var10 < argidx; ++var10) {
            String keyword = var8[var10];
            if (kwargs.__finditem__(keyword) != null) {
               throw Py.TypeError(name + "got multiple values for " + "keyword argument '" + keyword + "'");
            }
         }

         argslen += kwargs.__len__();
      }

      List starObjs = null;
      if (starargs != null) {
         starObjs = new ArrayList();
         PyObject iter = Py.iter(starargs, name + "argument after * must be a sequence");
         PyObject cur = null;

         while((cur = iter.__iternext__()) != null) {
            starObjs.add(cur);
         }

         argslen += starObjs.size();
      }

      PyObject[] newargs = new PyObject[argslen];
      argidx = args.length - keywords.length;
      System.arraycopy(args, 0, newargs, 0, argidx);
      if (starObjs != null) {
         for(Iterator it = starObjs.iterator(); it.hasNext(); newargs[argidx++] = (PyObject)it.next()) {
         }
      }

      System.arraycopy(args, args.length - keywords.length, newargs, argidx, keywords.length);
      argidx += keywords.length;
      if (kwargs != null) {
         String[] newkeywords = new String[keywords.length + kwargs.__len__()];
         System.arraycopy(keywords, 0, newkeywords, 0, keywords.length);
         PyObject keys = kwargs.invoke("keys");

         PyObject key;
         for(int i = 0; (key = keys.__finditem__(i)) != null; ++i) {
            if (!(key instanceof PyString)) {
               throw Py.TypeError(name + "keywords must be strings");
            }

            newkeywords[keywords.length + i] = ((PyString)key).internedString();
            newargs[argidx++] = kwargs.__finditem__(key);
         }

         keywords = newkeywords;
      }

      if (newargs.length != argidx) {
         args = new PyObject[argidx];
         System.arraycopy(newargs, 0, args, 0, argidx);
      } else {
         args = newargs;
      }

      return this.__call__(args, keywords);
   }

   public boolean isCallable() {
      return this.getType().lookup("__call__") != null;
   }

   public boolean isNumberType() {
      PyType type = this.getType();
      return type.lookup("__int__") != null || type.lookup("__float__") != null;
   }

   public boolean isMappingType() {
      PyType type = this.getType();
      return type.lookup("__getitem__") != null && (!this.isSequenceType() || type.lookup("__getslice__") == null);
   }

   public boolean isSequenceType() {
      return this.getType().lookup("__getitem__") != null;
   }

   public boolean isInteger() {
      return this.getType().lookup("__int__") != null;
   }

   public boolean isIndex() {
      return this.getType().lookup("__index__") != null;
   }

   public int __len__() {
      throw Py.TypeError(String.format("object of type '%.200s' has no len()", this.getType().fastGetName()));
   }

   public PyObject __finditem__(PyObject key) {
      throw Py.TypeError(String.format("'%.200s' object is unsubscriptable", this.getType().fastGetName()));
   }

   public PyObject __finditem__(int key) {
      return this.__finditem__((PyObject)(new PyInteger(key)));
   }

   public PyObject __finditem__(String key) {
      return this.__finditem__((PyObject)(new PyString(key)));
   }

   public PyObject __getitem__(int key) {
      PyObject ret = this.__finditem__(key);
      if (ret == null) {
         throw Py.KeyError("" + key);
      } else {
         return ret;
      }
   }

   public PyObject __getitem__(PyObject key) {
      PyObject ret = this.__finditem__(key);
      if (ret == null) {
         throw Py.KeyError(key);
      } else {
         return ret;
      }
   }

   public void __setitem__(PyObject key, PyObject value) {
      throw Py.TypeError(String.format("'%.200s' object does not support item assignment", this.getType().fastGetName()));
   }

   public void __setitem__(String key, PyObject value) {
      this.__setitem__((PyObject)(new PyString(key)), value);
   }

   public void __setitem__(int key, PyObject value) {
      this.__setitem__((PyObject)(new PyInteger(key)), value);
   }

   public void __delitem__(PyObject key) {
      throw Py.TypeError(String.format("'%.200s' object doesn't support item deletion", this.getType().fastGetName()));
   }

   public void __delitem__(String key) {
      this.__delitem__((PyObject)(new PyString(key)));
   }

   public PyObject __getslice__(PyObject s_start, PyObject s_stop, PyObject s_step) {
      PySlice s = new PySlice(s_start, s_stop, s_step);
      return this.__getitem__(s);
   }

   public void __setslice__(PyObject s_start, PyObject s_stop, PyObject s_step, PyObject value) {
      PySlice s = new PySlice(s_start, s_stop, s_step);
      this.__setitem__((PyObject)s, value);
   }

   public void __delslice__(PyObject s_start, PyObject s_stop, PyObject s_step) {
      PySlice s = new PySlice(s_start, s_stop, s_step);
      this.__delitem__((PyObject)s);
   }

   public PyObject __getslice__(PyObject start, PyObject stop) {
      return this.__getslice__(start, stop, (PyObject)null);
   }

   public void __setslice__(PyObject start, PyObject stop, PyObject value) {
      this.__setslice__(start, stop, (PyObject)null, value);
   }

   public void __delslice__(PyObject start, PyObject stop) {
      this.__delslice__(start, stop, (PyObject)null);
   }

   public PyObject __iter__() {
      throw Py.TypeError(String.format("'%.200s' object is not iterable", this.getType().fastGetName()));
   }

   public Iterable asIterable() {
      return new Iterable() {
         public Iterator iterator() {
            return new WrappedIterIterator(PyObject.this.__iter__()) {
               public PyObject next() {
                  return this.getNext();
               }
            };
         }
      };
   }

   public PyObject __iternext__() {
      return null;
   }

   public final PyObject __findattr__(PyString name) {
      return name == null ? null : this.__findattr__(name.internedString());
   }

   public final PyObject __findattr__(String name) {
      try {
         return this.__findattr_ex__(name);
      } catch (PyException var3) {
         if (var3.match(Py.AttributeError)) {
            return null;
         } else {
            throw var3;
         }
      }
   }

   public PyObject __findattr_ex__(String name) {
      return this.object___findattr__(name);
   }

   public final PyObject __getattr__(PyString name) {
      return this.__getattr__(name.internedString());
   }

   public final PyObject __getattr__(String name) {
      PyObject ret = this.__findattr_ex__(name);
      if (ret == null) {
         this.noAttributeError(name);
      }

      return ret;
   }

   public void noAttributeError(String name) {
      throw Py.AttributeError(String.format("'%.50s' object has no attribute '%.400s'", this.getType().fastGetName(), name));
   }

   public void readonlyAttributeError(String name) {
      throw Py.TypeError("readonly attribute");
   }

   public final void __setattr__(PyString name, PyObject value) {
      this.__setattr__(name.internedString(), value);
   }

   public void __setattr__(String name, PyObject value) {
      this.object___setattr__(name, value);
   }

   public final void __delattr__(PyString name) {
      this.__delattr__(name.internedString());
   }

   public void __delattr__(String name) {
      this.object___delattr__(name);
   }

   protected PyObject impAttr(String name) {
      return this.__findattr__(name);
   }

   protected void mergeListAttr(PyDictionary accum, String attr) {
      PyObject obj = this.__findattr__(attr);
      if (obj != null) {
         if (obj instanceof PyList) {
            Iterator var4 = obj.asIterable().iterator();

            while(var4.hasNext()) {
               PyObject name = (PyObject)var4.next();
               accum.__setitem__(name, Py.None);
            }
         }

      }
   }

   protected void mergeDictAttr(PyDictionary accum, String attr) {
      PyObject obj = this.__findattr__(attr);
      if (obj != null) {
         if (obj instanceof AbstractDict || obj instanceof PyDictProxy) {
            accum.update(obj);
         }

      }
   }

   protected void mergeClassDict(PyDictionary accum, PyObject aClass) {
      aClass.mergeDictAttr(accum, "__dict__");
      PyObject bases = aClass.__findattr__("__bases__");
      if (bases != null) {
         int len = bases.__len__();

         for(int i = 0; i < len; ++i) {
            this.mergeClassDict(accum, bases.__getitem__(i));
         }

      }
   }

   protected void __rawdir__(PyDictionary accum) {
      this.mergeDictAttr(accum, "__dict__");
      this.mergeListAttr(accum, "__methods__");
      this.mergeListAttr(accum, "__members__");
      PyObject itsClass = this.__findattr__("__class__");
      if (itsClass != null) {
         this.mergeClassDict(accum, itsClass);
      }

   }

   public PyObject __dir__() {
      PyDictionary accum = new PyDictionary();
      this.__rawdir__(accum);
      PyList ret = accum.keys();
      ret.sort();
      return ret;
   }

   public PyObject _doget(PyObject container) {
      return this;
   }

   public PyObject _doget(PyObject container, PyObject wherefound) {
      return this._doget(container);
   }

   public boolean _doset(PyObject container, PyObject value) {
      return false;
   }

   boolean jdontdel() {
      return false;
   }

   public Object __coerce_ex__(PyObject o) {
      return null;
   }

   PyObject[] _coerce(PyObject other) {
      if (this.getType() == other.getType() && !(this instanceof PyInstance)) {
         return new PyObject[]{this, other};
      } else {
         Object result = this.__coerce_ex__(other);
         if (result != null && result != Py.None) {
            return result instanceof PyObject[] ? (PyObject[])((PyObject[])result) : new PyObject[]{this, (PyObject)result};
         } else {
            result = other.__coerce_ex__(this);
            if (result != null && result != Py.None) {
               return result instanceof PyObject[] ? (PyObject[])((PyObject[])result) : new PyObject[]{(PyObject)result, other};
            } else {
               return null;
            }
         }
      }
   }

   public final PyObject __coerce__(PyObject pyo) {
      Object o = this.__coerce_ex__(pyo);
      if (o == null) {
         throw Py.AttributeError("__coerce__");
      } else {
         return this.adaptToCoerceTuple(o);
      }
   }

   protected final PyObject adaptToCoerceTuple(Object o) {
      if (o == Py.None) {
         return Py.NotImplemented;
      } else {
         return o instanceof PyObject[] ? new PyTuple((PyObject[])((PyObject[])o)) : new PyTuple(new PyObject[]{this, (PyObject)o});
      }
   }

   public int __cmp__(PyObject other) {
      return -2;
   }

   public PyObject __eq__(PyObject other) {
      return null;
   }

   public PyObject __ne__(PyObject other) {
      return null;
   }

   public PyObject __le__(PyObject other) {
      return null;
   }

   public PyObject __lt__(PyObject other) {
      return null;
   }

   public PyObject __ge__(PyObject other) {
      return null;
   }

   public PyObject __gt__(PyObject other) {
      return null;
   }

   public final int _cmp(PyObject o) {
      if (this == o) {
         return 0;
      } else {
         PyObject token = null;
         ThreadState ts = Py.getThreadState();

         int var5;
         try {
            if (++ts.compareStateNesting > 500 && (token = check_recursion(ts, this, o)) == null) {
               byte var11 = 0;
               return var11;
            }

            PyObject result = this.__eq__(o);
            if (result == null) {
               result = o.__eq__(this);
            }

            if (result == null || !result.__nonzero__()) {
               result = this.__lt__(o);
               if (result == null) {
                  result = o.__gt__(this);
               }

               if (result != null && result.__nonzero__()) {
                  byte var10 = -1;
                  return var10;
               }

               result = this.__gt__(o);
               if (result == null) {
                  result = o.__lt__(this);
               }

               if (result != null && result.__nonzero__()) {
                  byte var9 = 1;
                  return var9;
               }

               var5 = this._cmp_unsafe(o);
               return var5;
            }

            var5 = 0;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var5;
      }
   }

   private PyObject make_pair(PyObject o) {
      return System.identityHashCode(this) < System.identityHashCode(o) ? new PyIdentityTuple(new PyObject[]{this, o}) : new PyIdentityTuple(new PyObject[]{o, this});
   }

   private final int _default_cmp(PyObject other) {
      if (this._is(other).__nonzero__()) {
         return 0;
      } else if (this == Py.None) {
         return -1;
      } else if (other == Py.None) {
         return 1;
      } else {
         PyType type = this.getType();
         PyType otherType = other.getType();
         if (type == otherType) {
            return Py.id(this) < Py.id(other) ? -1 : 1;
         } else {
            String typeName = this.isNumberType() ? "" : type.fastGetName();
            String otherTypeName = other.isNumberType() ? "" : otherType.fastGetName();
            int result = typeName.compareTo(otherTypeName);
            if (result == 0) {
               return Py.id(type) < Py.id(otherType) ? -1 : 1;
            } else {
               return result < 0 ? -1 : 1;
            }
         }
      }
   }

   private final int _cmp_unsafe(PyObject other) {
      int result = this._try__cmp__(other);
      return result != -2 ? result : this._default_cmp(other);
   }

   private final int _cmpeq_unsafe(PyObject other) {
      int result = this._try__cmp__(other);
      if (result != -2) {
         return result;
      } else {
         return this._is(other).__nonzero__() ? 0 : 1;
      }
   }

   private int _try__cmp__(PyObject other) {
      int result = this.__cmp__(other);
      if (result != -2) {
         return result;
      } else {
         if (!(this instanceof PyInstance)) {
            result = other.__cmp__(this);
            if (result != -2) {
               return -result;
            }
         }

         PyObject[] coerced = this._coerce(other);
         if (coerced != null) {
            result = coerced[0].__cmp__(coerced[1]);
            if (result != -2) {
               return result;
            }
         }

         return -2;
      }
   }

   private static final PyObject check_recursion(ThreadState ts, PyObject o1, PyObject o2) {
      PyDictionary stateDict = ts.getCompareStateDict();
      PyObject pair = o1.make_pair(o2);
      if (stateDict.__finditem__(pair) != null) {
         return null;
      } else {
         stateDict.__setitem__(pair, pair);
         return pair;
      }
   }

   private static final void delete_token(ThreadState ts, PyObject token) {
      if (token != null) {
         PyDictionary stateDict = ts.getCompareStateDict();
         stateDict.__delitem__(token);
      }
   }

   public final PyObject _eq(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._eq(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyObject var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               PyBoolean var11 = Py.True;
               return var11;
            }

            PyObject res = this.__eq__(o);
            if (res != null) {
               var7 = res;
               return var7;
            }

            res = o.__eq__(this);
            if (res == null) {
               PyBoolean var12 = this._cmpeq_unsafe(o) == 0 ? Py.True : Py.False;
               return var12;
            }

            var7 = res;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public final PyObject _ne(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._ne(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyObject var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               PyBoolean var11 = Py.False;
               return var11;
            }

            PyObject res = this.__ne__(o);
            if (res != null) {
               var7 = res;
               return var7;
            }

            res = o.__ne__(this);
            if (res == null) {
               PyBoolean var12 = this._cmpeq_unsafe(o) != 0 ? Py.True : Py.False;
               return var12;
            }

            var7 = res;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public final PyObject _le(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._ge(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyObject var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               throw Py.ValueError("can't order recursive values");
            }

            PyObject res = this.__le__(o);
            if (res == null) {
               res = o.__ge__(this);
               if (res != null) {
                  var7 = res;
                  return var7;
               }

               PyBoolean var11 = this._cmp_unsafe(o) <= 0 ? Py.True : Py.False;
               return var11;
            }

            var7 = res;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public final PyObject _lt(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._gt(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyBoolean var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               throw Py.ValueError("can't order recursive values");
            }

            PyObject res = this.__lt__(o);
            PyObject var11;
            if (res != null) {
               var11 = res;
               return var11;
            }

            res = o.__gt__(this);
            if (res != null) {
               var11 = res;
               return var11;
            }

            var7 = this._cmp_unsafe(o) < 0 ? Py.True : Py.False;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public final PyObject _ge(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._le(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyBoolean var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               throw Py.ValueError("can't order recursive values");
            }

            PyObject res = this.__ge__(o);
            PyObject var11;
            if (res != null) {
               var11 = res;
               return var11;
            }

            res = o.__le__(this);
            if (res != null) {
               var11 = res;
               return var11;
            }

            var7 = this._cmp_unsafe(o) >= 0 ? Py.True : Py.False;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public final PyObject _gt(PyObject o) {
      PyObject token = null;
      PyType t1 = this.getType();
      PyType t2 = o.getType();
      if (t1 != t2 && t2.isSubType(t1)) {
         return o._lt(this);
      } else {
         ThreadState ts = Py.getThreadState();

         PyBoolean var7;
         try {
            if (++ts.compareStateNesting > 10 && (token = check_recursion(ts, this, o)) == null) {
               throw Py.ValueError("can't order recursive values");
            }

            PyObject res = this.__gt__(o);
            PyObject var11;
            if (res != null) {
               var11 = res;
               return var11;
            }

            res = o.__lt__(this);
            if (res != null) {
               var11 = res;
               return var11;
            }

            var7 = this._cmp_unsafe(o) > 0 ? Py.True : Py.False;
         } finally {
            delete_token(ts, token);
            --ts.compareStateNesting;
         }

         return var7;
      }
   }

   public PyObject _is(PyObject o) {
      return this != o && (!JyAttribute.hasAttr(this, (byte)-128) || JyAttribute.getAttr(this, (byte)-128) != JyAttribute.getAttr(o, (byte)-128)) ? Py.False : Py.True;
   }

   public PyObject _isnot(PyObject o) {
      return this == o || JyAttribute.hasAttr(this, (byte)-128) && JyAttribute.getAttr(this, (byte)-128) == JyAttribute.getAttr(o, (byte)-128) ? Py.False : Py.True;
   }

   public final PyObject _in(PyObject o) {
      return Py.newBoolean(o.__contains__(this));
   }

   public final PyObject _notin(PyObject o) {
      return Py.newBoolean(!o.__contains__(this));
   }

   public boolean __contains__(PyObject o) {
      return this.object___contains__(o);
   }

   final boolean object___contains__(PyObject o) {
      Iterator var2 = this.asIterable().iterator();

      PyObject item;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         item = (PyObject)var2.next();
      } while(!o.equals(item));

      return true;
   }

   public PyObject __format__(PyObject formatSpec) {
      return this.object___format__(formatSpec);
   }

   final PyObject object___format__(PyObject formatSpec) {
      if (formatSpec != null && formatSpec instanceof PyString && !((PyString)formatSpec).getString().isEmpty()) {
         Py.warning(Py.PendingDeprecationWarning, "object.__format__ with a non-empty format string is deprecated");
      }

      return this.__str__().__format__(formatSpec);
   }

   public PyObject __not__() {
      return this.__nonzero__() ? Py.False : Py.True;
   }

   public PyString __hex__() {
      throw Py.TypeError("hex() argument can't be converted to hex");
   }

   public PyString __oct__() {
      throw Py.TypeError("oct() argument can't be converted to oct");
   }

   public PyObject __int__() {
      throw Py.AttributeError("__int__");
   }

   public PyObject __long__() {
      throw Py.AttributeError("__long__");
   }

   public PyFloat __float__() {
      throw Py.AttributeError("__float__");
   }

   public PyComplex __complex__() {
      throw Py.AttributeError("__complex__");
   }

   public PyObject __trunc__() {
      throw Py.AttributeError("__trunc__");
   }

   public PyObject conjugate() {
      throw Py.AttributeError("conjugate");
   }

   public int bit_length() {
      throw Py.AttributeError("bit_length");
   }

   public PyObject __pos__() {
      throw Py.TypeError(String.format("bad operand type for unary +: '%.200s'", this.getType().fastGetName()));
   }

   public PyObject __neg__() {
      throw Py.TypeError(String.format("bad operand type for unary -: '%.200s'", this.getType().fastGetName()));
   }

   public PyObject __abs__() {
      throw Py.TypeError(String.format("bad operand type for abs(): '%.200s'", this.getType().fastGetName()));
   }

   public PyObject __invert__() {
      throw Py.TypeError(String.format("bad operand type for unary ~: '%.200s'", this.getType().fastGetName()));
   }

   public PyObject __index__() {
      throw Py.TypeError(String.format("'%.200s' object cannot be interpreted as an index", this.getType().fastGetName()));
   }

   protected final String _unsupportedop(String op, PyObject o2) {
      Object[] args = new Object[]{op, this.getType().fastGetName(), o2.getType().fastGetName()};
      String msg = this.unsupportedopMessage(op, o2);
      if (msg == null) {
         msg = o2.runsupportedopMessage(op, o2);
      }

      if (msg == null) {
         msg = "unsupported operand type(s) for {0}: ''{1}'' and ''{2}''";
      }

      return MessageFormat.format(msg, args);
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return null;
   }

   protected String runsupportedopMessage(String op, PyObject o2) {
      return null;
   }

   public PyObject __pow__(PyObject o2, PyObject o3) {
      return null;
   }

   private boolean isStrUnicodeSpecialCase(PyType t1, PyType t2, String op) {
      return op == "+" && (t1 == PyString.TYPE || t1 == PyUnicode.TYPE) && (t2.isSubType(PyString.TYPE) || t2.isSubType(PyUnicode.TYPE));
   }

   private PyObject _binop_rule(PyType t1, PyObject o2, PyType t2, String left, String right, String op) {
      PyObject o1 = this;
      PyObject[] where = new PyObject[1];
      PyObject where1 = null;
      PyObject where2 = null;
      PyObject impl1 = t1.lookup_where(left, where);
      where1 = where[0];
      PyObject impl2 = t2.lookup_where(right, where);
      where2 = where[0];
      PyObject res;
      if (impl2 != null && impl1 != null && where1 != where2 && (t2.isSubType(t1) && !Py.isSubClass(where1, where2) && !Py.isSubClass(t1, where2) || this.isStrUnicodeSpecialCase(t1, t2, op))) {
         o1 = o2;
         o2 = this;
         res = impl1;
         impl1 = impl2;
         impl2 = res;
         PyType ttmp = t1;
         t1 = t2;
         t2 = ttmp;
      }

      res = null;
      if (impl1 != null) {
         res = impl1.__get__(o1, t1).__call__(o2);
         if (res != Py.NotImplemented) {
            return res;
         }
      }

      if (impl2 != null) {
         res = impl2.__get__(o2, t2).__call__(o1);
         if (res != Py.NotImplemented) {
            return res;
         }
      }

      throw Py.TypeError(this._unsupportedop(op, o2));
   }

   public PyObject __add__(PyObject other) {
      return null;
   }

   public PyObject __radd__(PyObject other) {
      return null;
   }

   public PyObject __iadd__(PyObject other) {
      return null;
   }

   public final PyObject _add(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__add__", "__radd__", "+") : this._basic_add(o2);
   }

   final PyObject _basic_add(PyObject o2) {
      PyObject x = this.__add__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__radd__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("+", o2));
         }
      }
   }

   public final PyObject _iadd(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__iadd__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__add__", "__radd__", "+");
      } else {
         return this._basic_iadd(o2);
      }
   }

   final PyObject _basic_iadd(PyObject o2) {
      PyObject x = this.__iadd__(o2);
      return x != null ? x : this._basic_add(o2);
   }

   public PyObject __sub__(PyObject other) {
      return null;
   }

   public PyObject __rsub__(PyObject other) {
      return null;
   }

   public PyObject __isub__(PyObject other) {
      return null;
   }

   public final PyObject _sub(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__sub__", "__rsub__", "-") : this._basic_sub(o2);
   }

   final PyObject _basic_sub(PyObject o2) {
      PyObject x = this.__sub__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rsub__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("-", o2));
         }
      }
   }

   public final PyObject _isub(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__isub__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__sub__", "__rsub__", "-");
      } else {
         return this._basic_isub(o2);
      }
   }

   final PyObject _basic_isub(PyObject o2) {
      PyObject x = this.__isub__(o2);
      return x != null ? x : this._basic_sub(o2);
   }

   public PyObject __mul__(PyObject other) {
      return null;
   }

   public PyObject __rmul__(PyObject other) {
      return null;
   }

   public PyObject __imul__(PyObject other) {
      return null;
   }

   public final PyObject _mul(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__mul__", "__rmul__", "*") : this._basic_mul(o2);
   }

   final PyObject _basic_mul(PyObject o2) {
      PyObject x = this.__mul__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rmul__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("*", o2));
         }
      }
   }

   public final PyObject _imul(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__imul__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__mul__", "__rmul__", "*");
      } else {
         return this._basic_imul(o2);
      }
   }

   final PyObject _basic_imul(PyObject o2) {
      PyObject x = this.__imul__(o2);
      return x != null ? x : this._basic_mul(o2);
   }

   public PyObject __div__(PyObject other) {
      return null;
   }

   public PyObject __rdiv__(PyObject other) {
      return null;
   }

   public PyObject __idiv__(PyObject other) {
      return null;
   }

   public final PyObject _div(PyObject o2) {
      if (Options.Qnew) {
         return this._truediv(o2);
      } else {
         PyType t1 = this.getType();
         PyType t2 = o2.getType();
         return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__div__", "__rdiv__", "/") : this._basic_div(o2);
      }
   }

   final PyObject _basic_div(PyObject o2) {
      PyObject x = this.__div__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rdiv__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("/", o2));
         }
      }
   }

   public final PyObject _idiv(PyObject o2) {
      if (Options.Qnew) {
         return this._itruediv(o2);
      } else {
         PyType t1 = this.getType();
         PyType t2 = o2.getType();
         if (t1 == t2 || t1.builtin && t2.builtin) {
            return this._basic_idiv(o2);
         } else {
            PyObject impl = t1.lookup("__idiv__");
            if (impl != null) {
               PyObject res = impl.__get__(this, t1).__call__(o2);
               if (res != Py.NotImplemented) {
                  return res;
               }
            }

            return this._binop_rule(t1, o2, t2, "__div__", "__rdiv__", "/");
         }
      }
   }

   final PyObject _basic_idiv(PyObject o2) {
      PyObject x = this.__idiv__(o2);
      return x != null ? x : this._basic_div(o2);
   }

   public PyObject __floordiv__(PyObject other) {
      return null;
   }

   public PyObject __rfloordiv__(PyObject other) {
      return null;
   }

   public PyObject __ifloordiv__(PyObject other) {
      return null;
   }

   public final PyObject _floordiv(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__floordiv__", "__rfloordiv__", "//") : this._basic_floordiv(o2);
   }

   final PyObject _basic_floordiv(PyObject o2) {
      PyObject x = this.__floordiv__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rfloordiv__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("//", o2));
         }
      }
   }

   public final PyObject _ifloordiv(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__ifloordiv__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__floordiv__", "__rfloordiv__", "//");
      } else {
         return this._basic_ifloordiv(o2);
      }
   }

   final PyObject _basic_ifloordiv(PyObject o2) {
      PyObject x = this.__ifloordiv__(o2);
      return x != null ? x : this._basic_floordiv(o2);
   }

   public PyObject __truediv__(PyObject other) {
      return null;
   }

   public PyObject __rtruediv__(PyObject other) {
      return null;
   }

   public PyObject __itruediv__(PyObject other) {
      return null;
   }

   public final PyObject _truediv(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__truediv__", "__rtruediv__", "/") : this._basic_truediv(o2);
   }

   final PyObject _basic_truediv(PyObject o2) {
      PyObject x = this.__truediv__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rtruediv__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("/", o2));
         }
      }
   }

   public final PyObject _itruediv(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__itruediv__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__truediv__", "__rtruediv__", "/");
      } else {
         return this._basic_itruediv(o2);
      }
   }

   final PyObject _basic_itruediv(PyObject o2) {
      PyObject x = this.__itruediv__(o2);
      return x != null ? x : this._basic_truediv(o2);
   }

   public PyObject __mod__(PyObject other) {
      return null;
   }

   public PyObject __rmod__(PyObject other) {
      return null;
   }

   public PyObject __imod__(PyObject other) {
      return null;
   }

   public final PyObject _mod(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__mod__", "__rmod__", "%") : this._basic_mod(o2);
   }

   final PyObject _basic_mod(PyObject o2) {
      PyObject x = this.__mod__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rmod__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("%", o2));
         }
      }
   }

   public final PyObject _imod(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__imod__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__mod__", "__rmod__", "%");
      } else {
         return this._basic_imod(o2);
      }
   }

   final PyObject _basic_imod(PyObject o2) {
      PyObject x = this.__imod__(o2);
      return x != null ? x : this._basic_mod(o2);
   }

   public PyObject __divmod__(PyObject other) {
      return null;
   }

   public PyObject __rdivmod__(PyObject other) {
      return null;
   }

   public PyObject __idivmod__(PyObject other) {
      return null;
   }

   public final PyObject _divmod(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__divmod__", "__rdivmod__", "divmod") : this._basic_divmod(o2);
   }

   final PyObject _basic_divmod(PyObject o2) {
      PyObject x = this.__divmod__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rdivmod__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("divmod", o2));
         }
      }
   }

   public final PyObject _idivmod(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__idivmod__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__divmod__", "__rdivmod__", "divmod");
      } else {
         return this._basic_idivmod(o2);
      }
   }

   final PyObject _basic_idivmod(PyObject o2) {
      PyObject x = this.__idivmod__(o2);
      return x != null ? x : this._basic_divmod(o2);
   }

   public PyObject __pow__(PyObject other) {
      return this.__pow__(other, (PyObject)null);
   }

   public PyObject __rpow__(PyObject other) {
      return null;
   }

   public PyObject __ipow__(PyObject other) {
      return null;
   }

   public final PyObject _pow(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__pow__", "__rpow__", "**") : this._basic_pow(o2);
   }

   final PyObject _basic_pow(PyObject o2) {
      PyObject x = this.__pow__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rpow__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("**", o2));
         }
      }
   }

   public final PyObject _ipow(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__ipow__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__pow__", "__rpow__", "**");
      } else {
         return this._basic_ipow(o2);
      }
   }

   final PyObject _basic_ipow(PyObject o2) {
      PyObject x = this.__ipow__(o2);
      return x != null ? x : this._basic_pow(o2);
   }

   public PyObject __lshift__(PyObject other) {
      return null;
   }

   public PyObject __rlshift__(PyObject other) {
      return null;
   }

   public PyObject __ilshift__(PyObject other) {
      return null;
   }

   public final PyObject _lshift(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__lshift__", "__rlshift__", "<<") : this._basic_lshift(o2);
   }

   final PyObject _basic_lshift(PyObject o2) {
      PyObject x = this.__lshift__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rlshift__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("<<", o2));
         }
      }
   }

   public final PyObject _ilshift(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__ilshift__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__lshift__", "__rlshift__", "<<");
      } else {
         return this._basic_ilshift(o2);
      }
   }

   final PyObject _basic_ilshift(PyObject o2) {
      PyObject x = this.__ilshift__(o2);
      return x != null ? x : this._basic_lshift(o2);
   }

   public PyObject __rshift__(PyObject other) {
      return null;
   }

   public PyObject __rrshift__(PyObject other) {
      return null;
   }

   public PyObject __irshift__(PyObject other) {
      return null;
   }

   public final PyObject _rshift(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__rshift__", "__rrshift__", ">>") : this._basic_rshift(o2);
   }

   final PyObject _basic_rshift(PyObject o2) {
      PyObject x = this.__rshift__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rrshift__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop(">>", o2));
         }
      }
   }

   public final PyObject _irshift(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__irshift__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__rshift__", "__rrshift__", ">>");
      } else {
         return this._basic_irshift(o2);
      }
   }

   final PyObject _basic_irshift(PyObject o2) {
      PyObject x = this.__irshift__(o2);
      return x != null ? x : this._basic_rshift(o2);
   }

   public PyObject __and__(PyObject other) {
      return null;
   }

   public PyObject __rand__(PyObject other) {
      return null;
   }

   public PyObject __iand__(PyObject other) {
      return null;
   }

   public final PyObject _and(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__and__", "__rand__", "&") : this._basic_and(o2);
   }

   final PyObject _basic_and(PyObject o2) {
      PyObject x = this.__and__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rand__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("&", o2));
         }
      }
   }

   public final PyObject _iand(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__iand__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__and__", "__rand__", "&");
      } else {
         return this._basic_iand(o2);
      }
   }

   final PyObject _basic_iand(PyObject o2) {
      PyObject x = this.__iand__(o2);
      return x != null ? x : this._basic_and(o2);
   }

   public PyObject __or__(PyObject other) {
      return null;
   }

   public PyObject __ror__(PyObject other) {
      return null;
   }

   public PyObject __ior__(PyObject other) {
      return null;
   }

   public final PyObject _or(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__or__", "__ror__", "|") : this._basic_or(o2);
   }

   final PyObject _basic_or(PyObject o2) {
      PyObject x = this.__or__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__ror__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("|", o2));
         }
      }
   }

   public final PyObject _ior(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__ior__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__or__", "__ror__", "|");
      } else {
         return this._basic_ior(o2);
      }
   }

   final PyObject _basic_ior(PyObject o2) {
      PyObject x = this.__ior__(o2);
      return x != null ? x : this._basic_or(o2);
   }

   public PyObject __xor__(PyObject other) {
      return null;
   }

   public PyObject __rxor__(PyObject other) {
      return null;
   }

   public PyObject __ixor__(PyObject other) {
      return null;
   }

   public final PyObject _xor(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      return t1 != t2 && (!t1.builtin || !t2.builtin) ? this._binop_rule(t1, o2, t2, "__xor__", "__rxor__", "^") : this._basic_xor(o2);
   }

   final PyObject _basic_xor(PyObject o2) {
      PyObject x = this.__xor__(o2);
      if (x != null) {
         return x;
      } else {
         x = o2.__rxor__(this);
         if (x != null) {
            return x;
         } else {
            throw Py.TypeError(this._unsupportedop("^", o2));
         }
      }
   }

   public final PyObject _ixor(PyObject o2) {
      PyType t1 = this.getType();
      PyType t2 = o2.getType();
      if (t1 != t2 && (!t1.builtin || !t2.builtin)) {
         PyObject impl = t1.lookup("__ixor__");
         if (impl != null) {
            PyObject res = impl.__get__(this, t1).__call__(o2);
            if (res != Py.NotImplemented) {
               return res;
            }
         }

         return this._binop_rule(t1, o2, t2, "__xor__", "__rxor__", "^");
      } else {
         return this._basic_ixor(o2);
      }
   }

   final PyObject _basic_ixor(PyObject o2) {
      PyObject x = this.__ixor__(o2);
      return x != null ? x : this._basic_xor(o2);
   }

   public PyObject _jcallexc(Object[] args) throws Throwable {
      try {
         return this.__call__(Py.javas2pys(args));
      } catch (PyException var4) {
         if (var4.value.getJavaProxy() != null) {
            Object t = var4.value.__tojava__(Throwable.class);
            if (t != null && t != Py.NoConversion) {
               throw (Throwable)t;
            }
         } else {
            ThreadState ts = Py.getThreadState();
            if (ts.frame == null) {
               Py.maybeSystemExit(var4);
            }

            if (Options.showPythonProxyExceptions) {
               Py.stderr.println("Exception in Python proxy returning to Java:");
               Py.printException(var4);
            }
         }

         throw var4;
      }
   }

   public void _jthrow(Throwable t) {
      if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw Py.JavaError(t);
      }
   }

   public PyObject _jcall(Object[] args) {
      try {
         return this._jcallexc(args);
      } catch (Throwable var3) {
         this._jthrow(var3);
         return null;
      }
   }

   public PyObject invoke(String name, PyObject[] args, String[] keywords) {
      PyObject f = this.__getattr__(name);
      return f.__call__(args, keywords);
   }

   public PyObject invoke(String name, PyObject[] args) {
      PyObject f = this.__getattr__(name);
      return f.__call__(args);
   }

   public PyObject invoke(String name) {
      PyObject f = this.__getattr__(name);
      return f.__call__();
   }

   public PyObject invoke(String name, PyObject arg1) {
      PyObject f = this.__getattr__(name);
      return f.__call__(arg1);
   }

   public PyObject invoke(String name, PyObject arg1, PyObject arg2) {
      PyObject f = this.__getattr__(name);
      return f.__call__(arg1, arg2);
   }

   public PyObject invoke(String name, PyObject arg1, PyObject[] args, String[] keywords) {
      PyObject f = this.__getattr__(name);
      return f.__call__(arg1, args, keywords);
   }

   public PyObject fastGetDict() {
      return null;
   }

   public PyObject getDict() {
      return null;
   }

   public void setDict(PyObject newDict) {
      throw Py.TypeError("can't set attribute '__dict__' of instance of " + this.getType().fastGetName());
   }

   public void delDict() {
      throw Py.TypeError("can't delete attribute '__dict__' of instance of '" + this.getType().fastGetName() + "'");
   }

   public boolean implementsDescrGet() {
      return this.objtype.hasGet;
   }

   public boolean implementsDescrSet() {
      return this.objtype.hasSet;
   }

   public boolean implementsDescrDelete() {
      return this.objtype.hasDelete;
   }

   public boolean isDataDescr() {
      return this.objtype.hasSet || this.objtype.hasDelete;
   }

   public PyObject __get__(PyObject obj, PyObject type) {
      return this._doget(obj, type);
   }

   public void __set__(PyObject obj, PyObject value) {
      if (!this._doset(obj, value)) {
         throw Py.AttributeError("object internal __set__ impl is abstract");
      }
   }

   public void __delete__(PyObject obj) {
      throw Py.AttributeError("object internal __delete__ impl is abstract");
   }

   final PyObject object___getattribute__(PyObject arg0) {
      String name = asName(arg0);
      PyObject ret = this.object___findattr__(name);
      if (ret == null) {
         this.noAttributeError(name);
      }

      return ret;
   }

   final PyObject object___findattr__(String name) {
      PyObject descr = this.objtype.lookup(name);
      boolean get = false;
      if (descr != null) {
         get = descr.implementsDescrGet();
         if (get && descr.isDataDescr()) {
            return descr.__get__(this, this.objtype);
         }
      }

      PyObject obj_dict = this.fastGetDict();
      if (obj_dict != null) {
         PyObject res = obj_dict.__finditem__(name);
         if (res != null) {
            return res;
         }
      }

      if (get) {
         return descr.__get__(this, this.objtype);
      } else {
         return descr != null ? descr : null;
      }
   }

   final void object___setattr__(PyObject name, PyObject value) {
      this.hackCheck("__setattr__");
      this.object___setattr__(asName(name), value);
   }

   final void object___setattr__(String name, PyObject value) {
      PyObject descr = this.objtype.lookup(name);
      boolean set = false;
      if (descr != null) {
         set = descr.implementsDescrSet();
         if (set && descr.isDataDescr()) {
            descr.__set__(this, value);
            return;
         }
      }

      PyObject obj_dict = this.fastGetDict();
      if (obj_dict != null) {
         obj_dict.__setitem__(name, value);
      } else {
         if (set) {
            descr.__set__(this, value);
         }

         if (descr != null) {
            this.readonlyAttributeError(name);
         }

         this.noAttributeError(name);
      }
   }

   final void object___delattr__(PyObject name) {
      this.hackCheck("__delattr__");
      this.object___delattr__(asName(name));
   }

   public static final String asName(PyObject obj) {
      try {
         return obj.asName(0);
      } catch (ConversionException var2) {
         throw Py.TypeError("attribute name must be a string");
      }
   }

   final void object___delattr__(String name) {
      PyObject descr = this.objtype.lookup(name);
      boolean delete = false;
      if (descr != null) {
         delete = descr.implementsDescrDelete();
         if (delete && descr.isDataDescr()) {
            descr.__delete__(this);
            return;
         }
      }

      PyObject obj_dict = this.fastGetDict();
      if (obj_dict != null) {
         try {
            obj_dict.__delitem__(name);
         } catch (PyException var6) {
            if (!var6.match(Py.KeyError)) {
               throw var6;
            }

            this.noAttributeError(name);
         }

      } else {
         if (delete) {
            descr.__delete__(this);
         }

         if (descr != null) {
            this.readonlyAttributeError(name);
         }

         this.noAttributeError(name);
      }
   }

   private void hackCheck(String what) {
      if (this instanceof PyType && ((PyType)this).builtin) {
         throw Py.TypeError(String.format("can't apply this %s to %s object", what, this.objtype.fastGetName()));
      }
   }

   private PyObject commonReduce(int proto) {
      PyObject res;
      if (proto >= 2) {
         res = this.reduce_2();
      } else {
         PyObject copyreg = __builtin__.__import__("copy_reg", (PyObject)null, (PyObject)null, Py.EmptyTuple);
         PyObject copyreg_reduce = copyreg.__findattr__("_reduce_ex");
         res = copyreg_reduce.__call__((PyObject)this, (PyObject)(new PyInteger(proto)));
      }

      return res;
   }

   public PyObject __reduce__() {
      return this.object___reduce__();
   }

   final PyObject object___reduce__() {
      return this.commonReduce(0);
   }

   public PyObject __reduce_ex__(int arg) {
      return this.object___reduce_ex__(arg);
   }

   public PyObject __reduce_ex__() {
      return this.object___reduce_ex__(0);
   }

   final PyObject object___reduce_ex__(int arg) {
      PyObject clsreduce = this.getType().__findattr__("__reduce__");
      PyObject objreduce = (new PyObject()).getType().__findattr__("__reduce__");
      PyObject res;
      if (clsreduce != objreduce) {
         res = this.__reduce__();
      } else {
         res = this.commonReduce(arg);
      }

      return res;
   }

   private static PyObject slotnames(PyObject cls) {
      PyObject slotnames = cls.fastGetDict().__finditem__("__slotnames__");
      if (null != slotnames) {
         return slotnames;
      } else {
         PyObject copyreg = __builtin__.__import__("copy_reg", (PyObject)null, (PyObject)null, Py.EmptyTuple);
         PyObject copyreg_slotnames = copyreg.__findattr__("_slotnames");
         slotnames = copyreg_slotnames.__call__(cls);
         if (null != slotnames && Py.None != slotnames && !(slotnames instanceof PyList)) {
            throw Py.TypeError("copy_reg._slotnames didn't return a list or None");
         } else {
            return slotnames;
         }
      }
   }

   private PyObject reduce_2() {
      PyObject res = null;
      PyObject cls = this.__findattr__("__class__");
      PyObject getnewargs = this.__findattr__("__getnewargs__");
      Object args;
      if (null != getnewargs) {
         args = getnewargs.__call__();
         if (null != args && !(args instanceof PyTuple)) {
            throw Py.TypeError("__getnewargs__ should return a tuple");
         }
      } else {
         args = Py.EmptyTuple;
      }

      PyObject getstate = this.__findattr__("__getstate__");
      Object state;
      PyObject listitems;
      int n;
      int i;
      PyObject name;
      PyObject value;
      if (null != getstate) {
         state = getstate.__call__();
         if (null == state) {
            return (PyObject)res;
         }
      } else {
         state = this.__findattr__("__dict__");
         if (null == state) {
            state = Py.None;
         }

         listitems = slotnames(cls);
         if (null == listitems) {
            return (PyObject)res;
         }

         if (listitems != Py.None) {
            if (!(listitems instanceof PyList)) {
               throw Py.AssertionError("slots not a list");
            }

            PyObject slots = new PyDictionary();
            n = 0;

            for(i = 0; i < ((PyList)listitems).size(); ++i) {
               name = ((PyList)listitems).pyget(i);
               value = this.__findattr__(name.toString());
               if (null != value) {
                  slots.__setitem__(name, value);
                  ++n;
               }
            }

            if (n > 0) {
               state = new PyTuple(new PyObject[]{(PyObject)state, slots});
            }
         }
      }

      if (!(this instanceof PyList)) {
         listitems = Py.None;
      } else {
         listitems = ((PyList)this).__iter__();
      }

      PyObject dictitems;
      if (!(this instanceof PyDictionary)) {
         dictitems = Py.None;
      } else {
         dictitems = this.invoke("iteritems");
      }

      name = __builtin__.__import__("copy_reg", (PyObject)null, (PyObject)null, Py.EmptyTuple);
      value = name.__findattr__("__newobj__");
      n = ((PyTuple)args).size();
      PyObject[] args2 = new PyObject[n + 1];
      args2[0] = cls;

      for(i = 0; i < n; ++i) {
         args2[i + 1] = ((PyTuple)args).pyget(i);
      }

      return new PyTuple(new PyObject[]{value, new PyTuple(args2), (PyObject)state, listitems, dictitems});
   }

   public PyTuple __getnewargs__() {
      return new PyTuple();
   }

   public static PyObject object___subclasshook__(PyType type, PyObject subclass) {
      return Py.NotImplemented;
   }

   public String asString(int index) throws ConversionException {
      throw new ConversionException(index);
   }

   public String asString() {
      throw Py.TypeError("expected a str");
   }

   public String asStringOrNull(int index) throws ConversionException {
      return this.asString(index);
   }

   public String asStringOrNull() {
      return this.asString();
   }

   public String asName(int index) throws ConversionException {
      throw new ConversionException(index);
   }

   public int asInt(int index) throws ConversionException {
      throw new ConversionException(index);
   }

   public int asInt() {
      PyObject intObj;
      try {
         intObj = this.__int__();
      } catch (PyException var3) {
         if (var3.match(Py.AttributeError)) {
            throw Py.TypeError("an integer is required");
         }

         throw var3;
      }

      if (!(intObj instanceof PyInteger) && !(intObj instanceof PyLong)) {
         throw Py.TypeError("nb_int should return int object");
      } else {
         return intObj.asInt();
      }
   }

   public long asLong(int index) throws ConversionException {
      throw new ConversionException(index);
   }

   public long asLong() {
      PyObject longObj;
      try {
         longObj = this.__long__();
      } catch (PyException var3) {
         if (var3.match(Py.AttributeError)) {
            throw Py.TypeError("an integer is required");
         }

         throw var3;
      }

      if (!(longObj instanceof PyLong) && !(longObj instanceof PyInteger)) {
         throw Py.TypeError("integer conversion failed");
      } else {
         return longObj.asLong();
      }
   }

   public double asDouble() {
      PyFloat floatObj;
      try {
         floatObj = this.__float__();
      } catch (PyException var3) {
         if (var3.match(Py.AttributeError)) {
            throw Py.TypeError("a float is required");
         }

         throw var3;
      }

      return floatObj.asDouble();
   }

   public int asIndex() {
      return this.asIndex((PyObject)null);
   }

   public int asIndex(PyObject err) {
      return this.__index__().asInt();
   }

   static {
      PyType.addBuilder(PyObject.class, new PyExposer());
      TYPE = PyType.fromClass(PyObject.class);
      gcMonitorGlobal = false;
      primitiveMap = Generic.map();
      primitiveMap.put(Character.TYPE, Character.class);
      primitiveMap.put(Boolean.TYPE, Boolean.class);
      primitiveMap.put(Byte.TYPE, Byte.class);
      primitiveMap.put(Short.TYPE, Short.class);
      primitiveMap.put(Integer.TYPE, Integer.class);
      primitiveMap.put(Long.TYPE, Long.class);
      primitiveMap.put(Float.TYPE, Float.class);
      primitiveMap.put(Double.TYPE, Double.class);
      if (BootstrapTypesSingleton.getInstance().size() > 0) {
         Py.writeWarning("init", "Bootstrap types weren't encountered in bootstrapping: " + BootstrapTypesSingleton.getInstance());
      }

   }

   public static class ConversionException extends Exception {
      public int index;

      public ConversionException(int index) {
         this.index = index;
      }
   }

   private static class object___init___exposer extends PyBuiltinMethod {
      public object___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public object___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyObject)this.self).object___init__(var1, var2);
         return Py.None;
      }
   }

   private static class __repr___exposer extends PyBuiltinMethodNarrow {
      public __repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public __repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyObject)this.self).__repr__();
      }
   }

   private static class object_toString_exposer extends PyBuiltinMethodNarrow {
      public object_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public object_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyObject)this.self).object_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class __ensure_finalizer___exposer extends PyBuiltinMethodNarrow {
      public __ensure_finalizer___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public __ensure_finalizer___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new __ensure_finalizer___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyObject)this.self).__ensure_finalizer__();
         return Py.None;
      }
   }

   private static class object___hash___exposer extends PyBuiltinMethodNarrow {
      public object___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public object___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyObject)this.self).object___hash__());
      }
   }

   private static class object___format___exposer extends PyBuiltinMethodNarrow {
      public object___format___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "default object formatter";
      }

      public object___format___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "default object formatter";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___format___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyObject)this.self).object___format__(var1);
      }
   }

   private static class object___getattribute___exposer extends PyBuiltinMethodNarrow {
      public object___getattribute___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public object___getattribute___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getattribute__('name') <==> x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___getattribute___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyObject)this.self).object___getattribute__(var1);
      }
   }

   private static class object___setattr___exposer extends PyBuiltinMethodNarrow {
      public object___setattr___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public object___setattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setattr__('name', value) <==> x.name = value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___setattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyObject)this.self).object___setattr__(var1, var2);
         return Py.None;
      }
   }

   private static class object___delattr___exposer extends PyBuiltinMethodNarrow {
      public object___delattr___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__delattr__('name') <==> del x.name";
      }

      public object___delattr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delattr__('name') <==> del x.name";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___delattr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyObject)this.self).object___delattr__(var1);
         return Py.None;
      }
   }

   private static class object___reduce___exposer extends PyBuiltinMethodNarrow {
      public object___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "helper for pickle";
      }

      public object___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "helper for pickle";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyObject)this.self).object___reduce__();
      }
   }

   private static class object___reduce_ex___exposer extends PyBuiltinMethodNarrow {
      public object___reduce_ex___exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "helper for pickle";
      }

      public object___reduce_ex___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "helper for pickle";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___reduce_ex___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyObject)this.self).object___reduce_ex__(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyObject)this.self).object___reduce_ex__(0);
      }
   }

   private static class object___subclasshook___exposer extends PyBuiltinClassMethodNarrow {
      public object___subclasshook___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Abstract classes can override this to customize issubclass().\n\nThis is invoked early on by abc.ABCMeta.__subclasscheck__().\nIt should return True, False or NotImplemented.  If it returns\nNotImplemented, the normal algorithm is used.  Otherwise, it\noverrides the normal algorithm (and the outcome is cached).\n";
      }

      public object___subclasshook___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Abstract classes can override this to customize issubclass().\n\nThis is invoked early on by abc.ABCMeta.__subclasscheck__().\nIt should return True, False or NotImplemented.  If it returns\nNotImplemented, the normal algorithm is used.  Otherwise, it\noverrides the normal algorithm (and the outcome is cached).\n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new object___subclasshook___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return PyObject.object___subclasshook__((PyType)this.self, var1);
      }
   }

   private static class __class___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __class___descriptor() {
         super("__class__", PyType.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyObject)var1).getType();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyObject)var1).setType((PyType)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyObject)var1).delType();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyObject.object___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new object___init___exposer("__init__"), new __repr___exposer("__str__"), new object_toString_exposer("__repr__"), new __ensure_finalizer___exposer("__ensure_finalizer__"), new object___hash___exposer("__hash__"), new object___format___exposer("__format__"), new object___getattribute___exposer("__getattribute__"), new object___setattr___exposer("__setattr__"), new object___delattr___exposer("__delattr__"), new object___reduce___exposer("__reduce__"), new object___reduce_ex___exposer("__reduce_ex__"), new object___subclasshook___exposer("__subclasshook__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new __class___descriptor()};
         super("object", PyObject.class, Object.class, (boolean)1, "The most base type", var1, var2, new exposed___new__());
      }
   }
}
