package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@ExposedType(
   name = "dict",
   base = PyObject.class,
   doc = "dict() -> new empty dictionary\ndict(mapping) -> new dictionary initialized from a mapping object's\n    (key, value) pairs\ndict(iterable) -> new dictionary initialized as if via:\n    d = {}\n    for k, v in iterable:\n        d[k] = v\ndict(**kwargs) -> new dictionary initialized with the name=value pairs\n    in the keyword argument list.  For example:  dict(one=1, two=2)"
)
public class PyDictionary extends AbstractDict implements ConcurrentMap, Traverseproc {
   public static final PyType TYPE;
   private final ConcurrentMap internalMap;

   public ConcurrentMap getMap() {
      return this.internalMap;
   }

   public PyDictionary() {
      this(TYPE);
   }

   public PyDictionary(PyType type, int capacity) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.internalMap = new ConcurrentHashMap(capacity, 0.75F, 2);
   }

   public PyDictionary(PyType type) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.internalMap = Generic.concurrentMap();
   }

   public PyDictionary(Map map) {
      this(TYPE, map);
   }

   public PyDictionary(ConcurrentMap backingMap, boolean useBackingMap) {
      super(TYPE);
      TYPE.object___setattr__("__hash__", Py.None);
      this.internalMap = backingMap;
   }

   public PyDictionary(PyType type, ConcurrentMap backingMap, boolean useBackingMap) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.internalMap = backingMap;
   }

   public PyDictionary(PyType type, Map map) {
      this(type, Math.max((int)((float)map.size() / 0.75F) + 1, 16));
      this.getMap().putAll(map);
   }

   protected PyDictionary(PyType type, boolean initializeBacking) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      if (initializeBacking) {
         this.internalMap = Generic.concurrentMap();
      } else {
         this.internalMap = null;
      }

   }

   public PyDictionary(PyObject[] elements) {
      this();
      ConcurrentMap map = this.getMap();

      for(int i = 0; i < elements.length; i += 2) {
         map.put(elements[i], elements[i + 1]);
      }

   }

   @ExposedNew
   protected final void dict___init__(PyObject[] args, String[] keywords) {
      this.updateCommon(args, keywords, "dict");
   }

   public static PyObject fromkeys(PyObject keys) {
      return fromkeys(keys, Py.None);
   }

   public static PyObject fromkeys(PyObject keys, PyObject value) {
      return dict_fromkeys(TYPE, keys, value);
   }

   static PyObject dict_fromkeys(PyType type, PyObject keys, PyObject value) {
      PyObject d = type.__call__();
      Iterator var4 = keys.asIterable().iterator();

      while(var4.hasNext()) {
         PyObject o = (PyObject)var4.next();
         d.__setitem__(o, value);
      }

      return d;
   }

   public int __len__() {
      return this.dict___len__();
   }

   final int dict___len__() {
      return this.getMap().size();
   }

   public boolean __nonzero__() {
      return this.getMap().size() != 0;
   }

   public PyObject __finditem__(int index) {
      throw Py.TypeError("loop over non-sequence");
   }

   public PyObject __finditem__(PyObject key) {
      return (PyObject)this.getMap().get(key);
   }

   protected final PyObject dict___getitem__(PyObject key) {
      PyObject result = (PyObject)this.getMap().get(key);
      if (result != null) {
         return result;
      } else {
         PyType type = this.getType();
         if (type != TYPE) {
            PyObject missing = type.lookup("__missing__");
            if (missing != null) {
               return missing.__get__(this, type).__call__(key);
            }
         }

         throw Py.KeyError(key);
      }
   }

   public void __setitem__(PyObject key, PyObject value) {
      this.dict___setitem__(key, value);
   }

   final void dict___setitem__(PyObject key, PyObject value) {
      this.getMap().put(key, value);
   }

   public void __delitem__(PyObject key) {
      this.dict___delitem__(key);
   }

   final void dict___delitem__(PyObject key) {
      Object ret = this.getMap().remove(key);
      if (ret == null) {
         throw Py.KeyError(key.toString());
      }
   }

   public PyObject __iter__() {
      return this.dict___iter__();
   }

   final PyObject dict___iter__() {
      return this.iterkeys();
   }

   public String toString() {
      return this.dict_toString();
   }

   final String dict_toString() {
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return "{...}";
      } else {
         StringBuilder buf = new StringBuilder("{");
         Iterator var3 = this.getMap().entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            buf.append(((PyObject)entry.getKey()).__repr__().toString());
            buf.append(": ");
            buf.append(((PyObject)entry.getValue()).__repr__().toString());
            buf.append(", ");
         }

         if (buf.length() > 1) {
            buf.delete(buf.length() - 2, buf.length());
         }

         buf.append("}");
         ts.exitRepr(this);
         return buf.toString();
      }
   }

   public PyObject __eq__(PyObject otherObj) {
      return this.dict___eq__(otherObj);
   }

   final PyObject dict___eq__(PyObject otherObj) {
      PyType thisType = this.getType();
      PyType otherType = otherObj.getType();
      if ((otherType == thisType || thisType.isSubType(otherType) || otherType.isSubType(thisType)) && otherType != PyObject.TYPE) {
         PyDictionary other = (PyDictionary)otherObj;
         int an = this.getMap().size();
         int bn = other.getMap().size();
         if (an != bn) {
            return Py.False;
         } else {
            PyList akeys = this.keys();

            for(int i = 0; i < an; ++i) {
               PyObject akey = akeys.pyget(i);
               PyObject bvalue = other.__finditem__(akey);
               if (bvalue == null) {
                  return Py.False;
               }

               PyObject avalue = this.__finditem__(akey);
               if (!avalue._eq(bvalue).__nonzero__()) {
                  return Py.False;
               }
            }

            return Py.True;
         }
      } else {
         return null;
      }
   }

   public PyObject __ne__(PyObject otherObj) {
      return this.dict___ne__(otherObj);
   }

   final PyObject dict___ne__(PyObject otherObj) {
      PyObject eq_result = this.__eq__(otherObj);
      return eq_result == null ? null : eq_result.__not__();
   }

   final PyObject dict___lt__(PyObject otherObj) {
      int result = this.__cmp__(otherObj);
      if (result == -2) {
         return null;
      } else {
         return result < 0 ? Py.True : Py.False;
      }
   }

   final PyObject dict___gt__(PyObject otherObj) {
      int result = this.__cmp__(otherObj);
      if (result == -2) {
         return null;
      } else {
         return result > 0 ? Py.True : Py.False;
      }
   }

   final PyObject dict___le__(PyObject otherObj) {
      int result = this.__cmp__(otherObj);
      if (result == -2) {
         return null;
      } else {
         return result <= 0 ? Py.True : Py.False;
      }
   }

   final PyObject dict___ge__(PyObject otherObj) {
      int result = this.__cmp__(otherObj);
      if (result == -2) {
         return null;
      } else {
         return result >= 0 ? Py.True : Py.False;
      }
   }

   public int __cmp__(PyObject otherObj) {
      return this.dict___cmp__(otherObj);
   }

   final int dict___cmp__(PyObject otherObj) {
      PyType thisType = this.getType();
      PyType otherType = otherObj.getType();
      if ((otherType == thisType || thisType.isSubType(otherType) || otherType.isSubType(thisType)) && otherType != PyObject.TYPE) {
         PyDictionary other = (PyDictionary)otherObj;
         int an = this.getMap().size();
         int bn = other.getMap().size();
         if (an < bn) {
            return -1;
         } else if (an > bn) {
            return 1;
         } else {
            PyList akeys = this.keys();
            PyList bkeys = other.keys();
            akeys.sort();
            bkeys.sort();

            for(int i = 0; i < bn; ++i) {
               PyObject akey = akeys.pyget(i);
               PyObject bkey = bkeys.pyget(i);
               int c = akey._cmp(bkey);
               if (c != 0) {
                  return c;
               }

               PyObject avalue = this.__finditem__(akey);
               PyObject bvalue = other.__finditem__(bkey);
               if (avalue == null) {
                  if (bvalue != null) {
                     return -3;
                  }
               } else {
                  if (bvalue == null) {
                     return -3;
                  }

                  c = avalue._cmp(bvalue);
                  if (c != 0) {
                     return c;
                  }
               }
            }

            return 0;
         }
      } else {
         return -2;
      }
   }

   public boolean has_key(PyObject key) {
      return this.dict_has_key(key);
   }

   final boolean dict_has_key(PyObject key) {
      return this.getMap().containsKey(key);
   }

   public boolean __contains__(PyObject o) {
      return this.dict___contains__(o);
   }

   final boolean dict___contains__(PyObject o) {
      return this.dict_has_key(o);
   }

   public PyObject get(PyObject key, PyObject defaultObj) {
      return this.dict_get(key, defaultObj);
   }

   final PyObject dict_get(PyObject key, PyObject defaultObj) {
      PyObject o = (PyObject)this.getMap().get(key);
      return o == null ? defaultObj : o;
   }

   public PyObject get(PyObject key) {
      return this.dict_get(key, Py.None);
   }

   public PyDictionary copy() {
      return this.dict_copy();
   }

   final PyDictionary dict_copy() {
      return new PyDictionary(this.getMap());
   }

   public void clear() {
      this.dict_clear();
   }

   final void dict_clear() {
      this.getMap().clear();
   }

   public void update(PyObject other) {
      this.dict_update(new PyObject[]{other}, Py.NoKeywords);
   }

   final void dict_update(PyObject[] args, String[] keywords) {
      this.updateCommon(args, keywords, "update");
   }

   public void updateCommon(PyObject[] args, String[] keywords, String methName) {
      int nargs = args.length - keywords.length;
      if (nargs > 1) {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(nargs, false, methName, 0, 1);
      } else {
         if (nargs == 1) {
            PyObject arg = args[0];
            Object proxy = arg.getJavaProxy();
            if (proxy instanceof Map) {
               this.merge((Map)proxy);
            } else if (arg.__findattr__("keys") != null) {
               this.merge(arg);
            } else {
               this.mergeFromSeq(arg);
            }
         }

         for(int i = 0; i < keywords.length; ++i) {
            this.dict___setitem__(Py.newString(keywords[i]), args[nargs + i]);
         }

      }
   }

   private void merge(Map other) {
      Iterator var2 = other.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.dict___setitem__(Py.java2py(entry.getKey()), Py.java2py(entry.getValue()));
      }

   }

   private void merge(PyObject other) {
      if (other instanceof PyDictionary) {
         this.getMap().putAll(((PyDictionary)other).getMap());
      } else if (other instanceof PyStringMap) {
         this.mergeFromKeys(other, ((PyStringMap)other).keys());
      } else {
         this.mergeFromKeys(other, other.invoke("keys"));
      }

   }

   public void merge(PyObject other, boolean override) {
      synchronized(this.internalMap) {
         if (override) {
            this.merge(other);
         } else if (other instanceof PyDictionary) {
            Set entrySet = ((PyDictionary)other).internalMap.entrySet();
            Iterator var5 = entrySet.iterator();

            while(var5.hasNext()) {
               Map.Entry ent = (Map.Entry)var5.next();
               if (!this.internalMap.containsKey(ent.getKey())) {
                  this.internalMap.put(ent.getKey(), ent.getValue());
               }
            }
         } else if (other instanceof PyStringMap) {
            this.mergeFromKeys(other, ((PyStringMap)other).keys(), override);
         } else {
            this.mergeFromKeys(other, other.invoke("keys"), override);
         }

      }
   }

   private void mergeFromKeys(PyObject other, PyObject keys) {
      Iterator var3 = keys.asIterable().iterator();

      while(var3.hasNext()) {
         PyObject key = (PyObject)var3.next();
         this.dict___setitem__(key, other.__getitem__(key));
      }

   }

   public void mergeFromKeys(PyObject other, PyObject keys, boolean override) {
      synchronized(this.internalMap) {
         if (override) {
            this.mergeFromKeys(other, keys);
         } else {
            Iterator var5 = keys.asIterable().iterator();

            while(var5.hasNext()) {
               PyObject key = (PyObject)var5.next();
               if (!this.dict___contains__(key)) {
                  this.dict___setitem__(key, other.__getitem__(key));
               }
            }
         }

      }
   }

   private void mergeFromSeq(PyObject other) {
      PyObject pairs = other.__iter__();

      PyObject pair;
      for(int i = 0; (pair = pairs.__iternext__()) != null; ++i) {
         PySequence pair;
         try {
            pair = PySequence.fastSequence(pair, "");
         } catch (PyException var6) {
            if (var6.match(Py.TypeError)) {
               throw Py.TypeError(String.format("cannot convert dictionary update sequence element #%d to a sequence", i));
            }

            throw var6;
         }

         int n;
         if ((n = pair.__len__()) != 2) {
            throw Py.ValueError(String.format("dictionary update sequence element #%d has length %d; 2 is required", i, n));
         }

         this.dict___setitem__(pair.__getitem__(0), pair.__getitem__(1));
      }

   }

   public void mergeFromSeq(PyObject other, boolean override) {
      synchronized(this.internalMap) {
         if (override) {
            this.mergeFromSeq(other);
         } else {
            PyObject pairs = other.__iter__();

            PyObject pair;
            for(int i = 0; (pair = pairs.__iternext__()) != null; ++i) {
               PySequence pair;
               try {
                  pair = PySequence.fastSequence(pair, "");
               } catch (PyException var9) {
                  if (var9.match(Py.TypeError)) {
                     throw Py.TypeError(String.format("cannot convert dictionary update sequence element #%d to a sequence", i));
                  }

                  throw var9;
               }

               int n;
               if ((n = pair.__len__()) != 2) {
                  throw Py.ValueError(String.format("dictionary update sequence element #%d has length %d; 2 is required", i, n));
               }

               if (!this.dict___contains__(pair.__getitem__(0))) {
                  this.dict___setitem__(pair.__getitem__(0), pair.__getitem__(1));
               }
            }
         }

      }
   }

   public PyObject setdefault(PyObject key) {
      return this.dict_setdefault(key, Py.None);
   }

   public PyObject setdefault(PyObject key, PyObject failobj) {
      return this.dict_setdefault(key, failobj);
   }

   final PyObject dict_setdefault(PyObject key, PyObject failobj) {
      PyObject oldValue = (PyObject)this.getMap().putIfAbsent(key, failobj);
      return oldValue == null ? failobj : oldValue;
   }

   final PyObject dict_setifabsent(PyObject key, PyObject failobj) {
      PyObject oldValue = (PyObject)this.getMap().putIfAbsent(key, failobj);
      return oldValue == null ? Py.None : oldValue;
   }

   public PyObject pop(PyObject key) {
      return this.dict_pop(key, (PyObject)null);
   }

   public PyObject pop(PyObject key, PyObject defaultValue) {
      return this.dict_pop(key, defaultValue);
   }

   final PyObject dict_pop(PyObject key, PyObject defaultValue) {
      if (!this.getMap().containsKey(key)) {
         if (defaultValue == null) {
            throw Py.KeyError(key);
         } else {
            return defaultValue;
         }
      } else {
         return (PyObject)this.getMap().remove(key);
      }
   }

   public PyObject popitem() {
      return this.dict_popitem();
   }

   final PyObject dict_popitem() {
      Iterator it = this.getMap().entrySet().iterator();
      if (!it.hasNext()) {
         throw Py.KeyError("popitem(): dictionary is empty");
      } else {
         Map.Entry entry = (Map.Entry)it.next();
         PyTuple tuple = new PyTuple(new PyObject[]{(PyObject)entry.getKey(), (PyObject)entry.getValue()});
         it.remove();
         return tuple;
      }
   }

   public PyList items() {
      return this.dict_items();
   }

   final PyList dict_items() {
      List list = new ArrayList(this.getMap().size());
      Iterator var2 = this.getMap().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         list.add(new PyTuple(new PyObject[]{(PyObject)entry.getKey(), (PyObject)entry.getValue()}));
      }

      return PyList.fromList(list);
   }

   public PyList keys() {
      return this.dict_keys();
   }

   final PyList dict_keys() {
      return PyList.fromList(new ArrayList(this.getMap().keySet()));
   }

   final PyList dict_values() {
      return PyList.fromList(new ArrayList(this.getMap().values()));
   }

   public PyObject iteritems() {
      return this.dict_iteritems();
   }

   final PyObject dict_iteritems() {
      return new ItemsIter(this.getMap().entrySet());
   }

   public PyObject iterkeys() {
      return this.dict_iterkeys();
   }

   final PyObject dict_iterkeys() {
      return new AbstractDict.ValuesIter(this.getMap().keySet());
   }

   public PyObject itervalues() {
      return this.dict_itervalues();
   }

   final PyObject dict_itervalues() {
      return new AbstractDict.ValuesIter(this.getMap().values());
   }

   public int hashCode() {
      return this.dict___hash__();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof PyDictionary) {
         return ((PyDictionary)obj).getMap().equals(this.getMap());
      } else {
         return obj instanceof Map ? this.getMap().equals((Map)obj) : false;
      }
   }

   final int dict___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   public boolean isMappingType() {
      return true;
   }

   public boolean isSequenceType() {
      return false;
   }

   public PyObject viewkeys() {
      return super.viewkeys();
   }

   public PyObject viewitems() {
      return super.viewitems();
   }

   public PyObject viewvalues() {
      return super.viewvalues();
   }

   public Set pyKeySet() {
      return this.internalMap.keySet();
   }

   public Set entrySet() {
      return new PyMapEntrySet(this.getMap().entrySet());
   }

   public Set keySet() {
      return new PyMapKeyValSet(this.getMap().keySet());
   }

   public Collection values() {
      return new PyMapKeyValSet(this.getMap().values());
   }

   public void putAll(Map map) {
      Iterator var2 = map.entrySet().iterator();

      while(var2.hasNext()) {
         Object o = var2.next();
         Map.Entry entry = (Map.Entry)o;
         this.getMap().put(Py.java2py(entry.getKey()), Py.java2py(entry.getValue()));
      }

   }

   public Object remove(Object key) {
      return tojava(this.getMap().remove(Py.java2py(key)));
   }

   public Object put(Object key, Object value) {
      return tojava(this.getMap().put(Py.java2py(key), Py.java2py(value)));
   }

   public Object get(Object key) {
      return tojava(this.getMap().get(Py.java2py(key)));
   }

   public boolean containsValue(Object value) {
      return this.getMap().containsValue(Py.java2py(value));
   }

   public boolean containsKey(Object key) {
      return this.getMap().containsKey(Py.java2py(key));
   }

   public boolean isEmpty() {
      return this.getMap().isEmpty();
   }

   public int size() {
      return this.getMap().size();
   }

   public Object putIfAbsent(Object key, Object value) {
      return tojava(this.getMap().putIfAbsent(Py.java2py(key), Py.java2py(value)));
   }

   public boolean remove(Object key, Object value) {
      return this.getMap().remove(Py.java2py(key), Py.java2py(value));
   }

   public boolean replace(Object key, Object oldValue, Object newValue) {
      return this.getMap().replace(Py.java2py(key), Py.java2py(oldValue), Py.java2py(newValue));
   }

   public Object replace(Object key, Object value) {
      return tojava(this.getMap().replace(Py.java2py(key), Py.java2py(value)));
   }

   public int traverse(Visitproc visit, Object arg) {
      Iterator var3 = this.internalMap.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry ent = (Map.Entry)var3.next();
         int retVal = visit.visit((PyObject)ent.getKey(), arg);
         if (retVal != 0) {
            return retVal;
         }

         if (ent.getValue() != null) {
            retVal = visit.visit((PyObject)ent.getValue(), arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.internalMap.containsKey(ob) || this.internalMap.containsValue(ob));
   }

   static {
      PyType.addBuilder(PyDictionary.class, new PyExposer());
      TYPE = PyType.fromClass(PyDictionary.class);
   }

   @ExposedType(
      name = "dict_items",
      base = PyObject.class
   )
   static class PyDictionaryViewItems extends BaseDictionaryView {
      public final PyType TYPE = PyType.fromClass(PyDictionaryViewItems.class);

      public PyDictionaryViewItems(AbstractDict dvDict) {
         super(dvDict);
      }

      public PyObject __iter__() {
         return this.dict_items___iter__();
      }

      final PyObject dict_items___iter__() {
         return this.dvDict.iteritems();
      }

      final PyObject dict_items___ne__(PyObject otherObj) {
         return this.dict_view___ne__(otherObj);
      }

      final PyObject dict_items___eq__(PyObject otherObj) {
         return this.dict_view___eq__(otherObj);
      }

      final PyObject dict_items___lt__(PyObject otherObj) {
         return this.dict_view___lt__(otherObj);
      }

      final PyObject dict_items___gt__(PyObject otherObj) {
         return this.dict_view___gt__(otherObj);
      }

      final PyObject dict_items___ge__(PyObject otherObj) {
         return this.dict_view___ge__(otherObj);
      }

      final PyObject dict_items___le__(PyObject otherObj) {
         return this.dict_view___le__(otherObj);
      }

      public PyObject __or__(PyObject otherObj) {
         return this.dict_items___or__(otherObj);
      }

      final PyObject dict_items___or__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict.iteritems());
         result.set_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public PyObject __xor__(PyObject otherObj) {
         return this.dict_items___xor__(otherObj);
      }

      final PyObject dict_items___xor__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict.iteritems());
         result.set_symmetric_difference_update(otherObj);
         return result;
      }

      public PyObject __sub__(PyObject otherObj) {
         return this.dict_items___sub__(otherObj);
      }

      final PyObject dict_items___sub__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict.iteritems());
         result.set_difference_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public PyObject __and__(PyObject otherObj) {
         return this.dict_items___and__(otherObj);
      }

      final PyObject dict_items___and__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict.iteritems());
         result.set_intersection_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public boolean __contains__(PyObject otherObj) {
         return this.dict_items___contains__(otherObj);
      }

      final boolean dict_items___contains__(PyObject item) {
         if (item instanceof PyTuple) {
            PyTuple tupleItem = (PyTuple)item;
            if (tupleItem.size() == 2) {
               SimpleEntry entry = new SimpleEntry(tupleItem.get(0), tupleItem.get(1));
               return this.dvDict.entrySet().contains(entry);
            }
         }

         return false;
      }

      final String dict_keys_toString() {
         return this.dict_view_toString();
      }

      static {
         PyType.addBuilder(PyDictionaryViewItems.class, new PyExposer());
      }

      private static class dict_items___iter___exposer extends PyBuiltinMethodNarrow {
         public dict_items___iter___exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public dict_items___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___iter___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((PyDictionaryViewItems)this.self).dict_items___iter__();
         }
      }

      private static class dict_items___ne___exposer extends PyBuiltinMethodNarrow {
         public dict_items___ne___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__ne__(y) <==> x!=y";
         }

         public dict_items___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__ne__(y) <==> x!=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___ne___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___ne__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___eq___exposer extends PyBuiltinMethodNarrow {
         public dict_items___eq___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__eq__(y) <==> x==y";
         }

         public dict_items___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__eq__(y) <==> x==y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___eq___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___eq__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___lt___exposer extends PyBuiltinMethodNarrow {
         public dict_items___lt___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__lt__(y) <==> x<y";
         }

         public dict_items___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__lt__(y) <==> x<y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___lt___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___lt__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___gt___exposer extends PyBuiltinMethodNarrow {
         public dict_items___gt___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__gt__(y) <==> x>y";
         }

         public dict_items___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__gt__(y) <==> x>y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___gt___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___gt__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___ge___exposer extends PyBuiltinMethodNarrow {
         public dict_items___ge___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__ge__(y) <==> x>=y";
         }

         public dict_items___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__ge__(y) <==> x>=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___ge___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___ge__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___le___exposer extends PyBuiltinMethodNarrow {
         public dict_items___le___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__le__(y) <==> x<=y";
         }

         public dict_items___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__le__(y) <==> x<=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___le___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___le__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___or___exposer extends PyBuiltinMethodNarrow {
         public dict_items___or___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__or__(y) <==> x|y";
         }

         public dict_items___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__or__(y) <==> x|y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___or___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___or__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___xor___exposer extends PyBuiltinMethodNarrow {
         public dict_items___xor___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__xor__(y) <==> x^y";
         }

         public dict_items___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__xor__(y) <==> x^y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___xor___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___xor__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___sub___exposer extends PyBuiltinMethodNarrow {
         public dict_items___sub___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__sub__(y) <==> x-y";
         }

         public dict_items___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__sub__(y) <==> x-y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___sub___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___sub__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___and___exposer extends PyBuiltinMethodNarrow {
         public dict_items___and___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__and__(y) <==> x&y";
         }

         public dict_items___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__and__(y) <==> x&y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___and___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewItems)this.self).dict_items___and__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_items___contains___exposer extends PyBuiltinMethodNarrow {
         public dict_items___contains___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__contains__(y) <==> y in x.";
         }

         public dict_items___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__contains__(y) <==> y in x.";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_items___contains___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            return Py.newBoolean(((PyDictionaryViewItems)this.self).dict_items___contains__(var1));
         }
      }

      private static class dict_keys_toString_exposer extends PyBuiltinMethodNarrow {
         public dict_keys_toString_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__repr__() <==> repr(x)";
         }

         public dict_keys_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__repr__() <==> repr(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys_toString_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            String var10000 = ((PyDictionaryViewItems)this.self).dict_keys_toString();
            return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new dict_items___iter___exposer("__iter__"), new dict_items___ne___exposer("__ne__"), new dict_items___eq___exposer("__eq__"), new dict_items___lt___exposer("__lt__"), new dict_items___gt___exposer("__gt__"), new dict_items___ge___exposer("__ge__"), new dict_items___le___exposer("__le__"), new dict_items___or___exposer("__or__"), new dict_items___xor___exposer("__xor__"), new dict_items___sub___exposer("__sub__"), new dict_items___and___exposer("__and__"), new dict_items___contains___exposer("__contains__"), new dict_keys_toString_exposer("__repr__")};
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("dict_items", PyDictionaryViewItems.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }

   @ExposedType(
      name = "dict_keys",
      base = PyObject.class
   )
   static class PyDictionaryViewKeys extends BaseDictionaryView {
      public final PyType TYPE = PyType.fromClass(PyDictionaryViewKeys.class);

      public PyDictionaryViewKeys(AbstractDict dvDict) {
         super(dvDict);
      }

      public PyObject __iter__() {
         return this.dict_keys___iter__();
      }

      final PyObject dict_keys___iter__() {
         return new AbstractDict.ValuesIter(this.dvDict.pyKeySet());
      }

      final PyObject dict_keys___ne__(PyObject otherObj) {
         return this.dict_view___ne__(otherObj);
      }

      final PyObject dict_keys___eq__(PyObject otherObj) {
         return this.dict_view___eq__(otherObj);
      }

      final PyObject dict_keys___lt__(PyObject otherObj) {
         return this.dict_view___lt__(otherObj);
      }

      final PyObject dict_keys___gt__(PyObject otherObj) {
         return this.dict_view___gt__(otherObj);
      }

      final PyObject dict_keys___ge__(PyObject otherObj) {
         return this.dict_view___ge__(otherObj);
      }

      final PyObject dict_keys___le__(PyObject otherObj) {
         return this.dict_view___le__(otherObj);
      }

      public PyObject __or__(PyObject otherObj) {
         return this.dict_keys___or__(otherObj);
      }

      final PyObject dict_keys___or__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict);
         result.set_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public PyObject __xor__(PyObject otherObj) {
         return this.dict_keys___xor__(otherObj);
      }

      final PyObject dict_keys___xor__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict);
         result.set_symmetric_difference_update(otherObj);
         return result;
      }

      public PyObject __sub__(PyObject otherObj) {
         return this.dict_keys___sub__(otherObj);
      }

      final PyObject dict_keys___sub__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict);
         result.set_difference_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public PyObject __and__(PyObject otherObj) {
         return this.dict_keys___and__(otherObj);
      }

      final PyObject dict_keys___and__(PyObject otherObj) {
         PySet result = new PySet(this.dvDict);
         result.set_intersection_update(new PyObject[]{otherObj}, new String[0]);
         return result;
      }

      public boolean __contains__(PyObject otherObj) {
         return this.dict_keys___contains__(otherObj);
      }

      final boolean dict_keys___contains__(PyObject item) {
         return this.dvDict.__contains__(item);
      }

      final String dict_keys_toString() {
         return this.dict_view_toString();
      }

      static {
         PyType.addBuilder(PyDictionaryViewKeys.class, new PyExposer());
      }

      private static class dict_keys___iter___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___iter___exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public dict_keys___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___iter___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((PyDictionaryViewKeys)this.self).dict_keys___iter__();
         }
      }

      private static class dict_keys___ne___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___ne___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__ne__(y) <==> x!=y";
         }

         public dict_keys___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__ne__(y) <==> x!=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___ne___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___ne__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___eq___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___eq___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__eq__(y) <==> x==y";
         }

         public dict_keys___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__eq__(y) <==> x==y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___eq___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___eq__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___lt___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___lt___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__lt__(y) <==> x<y";
         }

         public dict_keys___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__lt__(y) <==> x<y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___lt___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___lt__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___gt___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___gt___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__gt__(y) <==> x>y";
         }

         public dict_keys___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__gt__(y) <==> x>y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___gt___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___gt__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___ge___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___ge___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__ge__(y) <==> x>=y";
         }

         public dict_keys___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__ge__(y) <==> x>=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___ge___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___ge__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___le___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___le___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__le__(y) <==> x<=y";
         }

         public dict_keys___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__le__(y) <==> x<=y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___le___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___le__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___or___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___or___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__or__(y) <==> x|y";
         }

         public dict_keys___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__or__(y) <==> x|y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___or___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___or__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___xor___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___xor___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__xor__(y) <==> x^y";
         }

         public dict_keys___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__xor__(y) <==> x^y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___xor___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___xor__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___sub___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___sub___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__sub__(y) <==> x-y";
         }

         public dict_keys___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__sub__(y) <==> x-y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___sub___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___sub__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___and___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___and___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__and__(y) <==> x&y";
         }

         public dict_keys___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__and__(y) <==> x&y";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___and___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            PyObject var10000 = ((PyDictionaryViewKeys)this.self).dict_keys___and__(var1);
            return var10000 == null ? Py.NotImplemented : var10000;
         }
      }

      private static class dict_keys___contains___exposer extends PyBuiltinMethodNarrow {
         public dict_keys___contains___exposer(String var1) {
            super(var1, 2, 2);
            super.doc = "x.__contains__(y) <==> y in x.";
         }

         public dict_keys___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__contains__(y) <==> y in x.";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys___contains___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__(PyObject var1) {
            return Py.newBoolean(((PyDictionaryViewKeys)this.self).dict_keys___contains__(var1));
         }
      }

      private static class dict_keys_toString_exposer extends PyBuiltinMethodNarrow {
         public dict_keys_toString_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__repr__() <==> repr(x)";
         }

         public dict_keys_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__repr__() <==> repr(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_keys_toString_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            String var10000 = ((PyDictionaryViewKeys)this.self).dict_keys_toString();
            return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new dict_keys___iter___exposer("__iter__"), new dict_keys___ne___exposer("__ne__"), new dict_keys___eq___exposer("__eq__"), new dict_keys___lt___exposer("__lt__"), new dict_keys___gt___exposer("__gt__"), new dict_keys___ge___exposer("__ge__"), new dict_keys___le___exposer("__le__"), new dict_keys___or___exposer("__or__"), new dict_keys___xor___exposer("__xor__"), new dict_keys___sub___exposer("__sub__"), new dict_keys___and___exposer("__and__"), new dict_keys___contains___exposer("__contains__"), new dict_keys_toString_exposer("__repr__")};
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("dict_keys", PyDictionaryViewKeys.class, PyObject.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
         }
      }
   }

   @ExposedType(
      name = "dict_values",
      base = PyObject.class,
      doc = ""
   )
   static class PyDictionaryViewValues extends BaseDictionaryView {
      public final PyType TYPE = PyType.fromClass(PyDictionaryViewValues.class);

      public PyDictionaryViewValues(AbstractDict dvDict) {
         super(dvDict);
      }

      public PyObject __iter__() {
         return this.dict_values___iter__();
      }

      final PyObject dict_values___iter__() {
         return new AbstractDict.ValuesIter(this.dvDict.getMap().values());
      }

      final int dict_values___len__() {
         return this.dict_view___len__();
      }

      final String dict_values_toString() {
         return this.dict_view_toString();
      }

      static {
         PyType.addBuilder(PyDictionaryViewValues.class, new PyExposer());
      }

      private static class dict_values___iter___exposer extends PyBuiltinMethodNarrow {
         public dict_values___iter___exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public dict_values___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__iter__() <==> iter(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_values___iter___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return ((PyDictionaryViewValues)this.self).dict_values___iter__();
         }
      }

      private static class dict_values___len___exposer extends PyBuiltinMethodNarrow {
         public dict_values___len___exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__len__() <==> len(x)";
         }

         public dict_values___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__len__() <==> len(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_values___len___exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            return Py.newInteger(((PyDictionaryViewValues)this.self).dict_values___len__());
         }
      }

      private static class dict_values_toString_exposer extends PyBuiltinMethodNarrow {
         public dict_values_toString_exposer(String var1) {
            super(var1, 1, 1);
            super.doc = "x.__str__() <==> str(x)";
         }

         public dict_values_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
            super(var1, var2, var3);
            super.doc = "x.__str__() <==> str(x)";
         }

         public PyBuiltinCallable bind(PyObject var1) {
            return new dict_values_toString_exposer(this.getType(), var1, this.info);
         }

         public PyObject __call__() {
            String var10000 = ((PyDictionaryViewValues)this.self).dict_values_toString();
            return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
         }
      }

      private static class PyExposer extends BaseTypeBuilder {
         public PyExposer() {
            PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new dict_values___iter___exposer("__iter__"), new dict_values___len___exposer("__len__"), new dict_values_toString_exposer("__repr__"), new dict_values_toString_exposer("__str__")};
            PyDataDescr[] var2 = new PyDataDescr[0];
            super("dict_values", PyDictionaryViewValues.class, PyObject.class, (boolean)1, "", var1, var2, (PyNewWrapper)null);
         }
      }
   }

   class ItemsIter extends PyIterator {
      private final Iterator iterator;
      private final int size;

      public ItemsIter(Set items) {
         this.iterator = items.iterator();
         this.size = items.size();
      }

      public PyObject __iternext__() {
         if (!this.iterator.hasNext()) {
            return null;
         } else {
            Map.Entry entry = (Map.Entry)this.iterator.next();
            return new PyTuple(new PyObject[]{(PyObject)entry.getKey(), (PyObject)entry.getValue()});
         }
      }
   }

   private static class dict___init___exposer extends PyBuiltinMethod {
      public dict___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public dict___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyDictionary)this.self).dict___init__(var1, var2);
         return Py.None;
      }
   }

   private static class dict_fromkeys_exposer extends PyBuiltinClassMethodNarrow {
      public dict_fromkeys_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "dict.fromkeys(S[,v]) -> New dict with keys from S and values equal to v.\nv defaults to None.";
      }

      public dict_fromkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "dict.fromkeys(S[,v]) -> New dict with keys from S and values equal to v.\nv defaults to None.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_fromkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return PyDictionary.dict_fromkeys((PyType)this.self, var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return PyDictionary.dict_fromkeys((PyType)this.self, var1, Py.None);
      }
   }

   private static class dict___len___exposer extends PyBuiltinMethodNarrow {
      public dict___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public dict___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyDictionary)this.self).dict___len__());
      }
   }

   private static class dict___getitem___exposer extends PyBuiltinMethodNarrow {
      public dict___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public dict___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictionary)this.self).dict___getitem__(var1);
      }
   }

   private static class dict___setitem___exposer extends PyBuiltinMethodNarrow {
      public dict___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public dict___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyDictionary)this.self).dict___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class dict___delitem___exposer extends PyBuiltinMethodNarrow {
      public dict___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public dict___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyDictionary)this.self).dict___delitem__(var1);
         return Py.None;
      }
   }

   private static class dict___iter___exposer extends PyBuiltinMethodNarrow {
      public dict___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public dict___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict___iter__();
      }
   }

   private static class dict_toString_exposer extends PyBuiltinMethodNarrow {
      public dict_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public dict_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyDictionary)this.self).dict_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class dict___eq___exposer extends PyBuiltinMethodNarrow {
      public dict___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public dict___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___ne___exposer extends PyBuiltinMethodNarrow {
      public dict___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public dict___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___lt___exposer extends PyBuiltinMethodNarrow {
      public dict___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public dict___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___gt___exposer extends PyBuiltinMethodNarrow {
      public dict___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public dict___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___le___exposer extends PyBuiltinMethodNarrow {
      public dict___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public dict___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___ge___exposer extends PyBuiltinMethodNarrow {
      public dict___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public dict___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyDictionary)this.self).dict___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class dict___cmp___exposer extends PyBuiltinMethodNarrow {
      public dict___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public dict___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyDictionary)this.self).dict___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("dict.__cmp__(x,y) requires y to be 'dict', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class dict_has_key_exposer extends PyBuiltinMethodNarrow {
      public dict_has_key_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "D.has_key(k) -> True if D has a key k, else False";
      }

      public dict_has_key_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.has_key(k) -> True if D has a key k, else False";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_has_key_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyDictionary)this.self).dict_has_key(var1));
      }
   }

   private static class dict___contains___exposer extends PyBuiltinMethodNarrow {
      public dict___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "D.__contains__(k) -> True if D has a key k, else False";
      }

      public dict___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.__contains__(k) -> True if D has a key k, else False";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyDictionary)this.self).dict___contains__(var1));
      }
   }

   private static class dict_get_exposer extends PyBuiltinMethodNarrow {
      public dict_get_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.get(k[,d]) -> D[k] if k in D, else d.  d defaults to None.";
      }

      public dict_get_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.get(k[,d]) -> D[k] if k in D, else d.  d defaults to None.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_get_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDictionary)this.self).dict_get(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictionary)this.self).dict_get(var1, Py.None);
      }
   }

   private static class dict_copy_exposer extends PyBuiltinMethodNarrow {
      public dict_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.copy() -> a shallow copy of D";
      }

      public dict_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.copy() -> a shallow copy of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_copy();
      }
   }

   private static class dict_clear_exposer extends PyBuiltinMethodNarrow {
      public dict_clear_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.clear() -> None.  Remove all items from D.";
      }

      public dict_clear_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.clear() -> None.  Remove all items from D.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_clear_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyDictionary)this.self).dict_clear();
         return Py.None;
      }
   }

   private static class dict_update_exposer extends PyBuiltinMethod {
      public dict_update_exposer(String var1) {
         super(var1);
         super.doc = "D.update(E, **F) -> None.  Update D from dict/iterable E and F.\nIf E has a .keys() method, does:     for k in E: D[k] = E[k]\nIf E lacks .keys() method, does:     for (k, v) in E: D[k] = v\nIn either case, this is followed by: for k in F: D[k] = F[k]";
      }

      public dict_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.update(E, **F) -> None.  Update D from dict/iterable E and F.\nIf E has a .keys() method, does:     for k in E: D[k] = E[k]\nIf E lacks .keys() method, does:     for (k, v) in E: D[k] = v\nIn either case, this is followed by: for k in F: D[k] = F[k]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyDictionary)this.self).dict_update(var1, var2);
         return Py.None;
      }
   }

   private static class dict_setdefault_exposer extends PyBuiltinMethodNarrow {
      public dict_setdefault_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.setdefault(k[,d]) -> D.get(k,d), also set D[k]=d if k not in D";
      }

      public dict_setdefault_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.setdefault(k[,d]) -> D.get(k,d), also set D[k]=d if k not in D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_setdefault_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDictionary)this.self).dict_setdefault(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictionary)this.self).dict_setdefault(var1, Py.None);
      }
   }

   private static class dict_setifabsent_exposer extends PyBuiltinMethodNarrow {
      public dict_setifabsent_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "";
      }

      public dict_setifabsent_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_setifabsent_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDictionary)this.self).dict_setifabsent(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictionary)this.self).dict_setifabsent(var1, Py.None);
      }
   }

   private static class dict_pop_exposer extends PyBuiltinMethodNarrow {
      public dict_pop_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.pop(k[,d]) -> v, remove specified key and return the corresponding value.\nIf key is not found, d is returned if given, otherwise KeyError is raised";
      }

      public dict_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.pop(k[,d]) -> v, remove specified key and return the corresponding value.\nIf key is not found, d is returned if given, otherwise KeyError is raised";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyDictionary)this.self).dict_pop(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDictionary)this.self).dict_pop(var1, (PyObject)null);
      }
   }

   private static class dict_popitem_exposer extends PyBuiltinMethodNarrow {
      public dict_popitem_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.popitem() -> (k, v), remove and return some (key, value) pair as a\n2-tuple; but raise KeyError if D is empty.";
      }

      public dict_popitem_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.popitem() -> (k, v), remove and return some (key, value) pair as a\n2-tuple; but raise KeyError if D is empty.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_popitem_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_popitem();
      }
   }

   private static class dict_items_exposer extends PyBuiltinMethodNarrow {
      public dict_items_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.items() -> list of D's (key, value) pairs, as 2-tuples";
      }

      public dict_items_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.items() -> list of D's (key, value) pairs, as 2-tuples";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_items_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_items();
      }
   }

   private static class dict_keys_exposer extends PyBuiltinMethodNarrow {
      public dict_keys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.keys() -> list of D's keys";
      }

      public dict_keys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.keys() -> list of D's keys";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_keys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_keys();
      }
   }

   private static class dict_values_exposer extends PyBuiltinMethodNarrow {
      public dict_values_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.values() -> list of D's values";
      }

      public dict_values_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.values() -> list of D's values";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_values_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_values();
      }
   }

   private static class dict_iteritems_exposer extends PyBuiltinMethodNarrow {
      public dict_iteritems_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.iteritems() -> an iterator over the (key, value) items of D";
      }

      public dict_iteritems_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.iteritems() -> an iterator over the (key, value) items of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_iteritems_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_iteritems();
      }
   }

   private static class dict_iterkeys_exposer extends PyBuiltinMethodNarrow {
      public dict_iterkeys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.iterkeys() -> an iterator over the keys of D";
      }

      public dict_iterkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.iterkeys() -> an iterator over the keys of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_iterkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_iterkeys();
      }
   }

   private static class dict_itervalues_exposer extends PyBuiltinMethodNarrow {
      public dict_itervalues_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.itervalues() -> an iterator over the values of D";
      }

      public dict_itervalues_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.itervalues() -> an iterator over the values of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict_itervalues_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).dict_itervalues();
      }
   }

   private static class dict___hash___exposer extends PyBuiltinMethodNarrow {
      public dict___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public dict___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new dict___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyDictionary)this.self).dict___hash__());
      }
   }

   private static class viewkeys_exposer extends PyBuiltinMethodNarrow {
      public viewkeys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.viewkeys() -> a set-like object providing a view on D's keys";
      }

      public viewkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.viewkeys() -> a set-like object providing a view on D's keys";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new viewkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).viewkeys();
      }
   }

   private static class viewitems_exposer extends PyBuiltinMethodNarrow {
      public viewitems_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.viewitems() -> a set-like object providing a view on D's items";
      }

      public viewitems_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.viewitems() -> a set-like object providing a view on D's items";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new viewitems_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).viewitems();
      }
   }

   private static class viewvalues_exposer extends PyBuiltinMethodNarrow {
      public viewvalues_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.viewvalues() -> an object providing a view on D's values";
      }

      public viewvalues_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.viewvalues() -> an object providing a view on D's values";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new viewvalues_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDictionary)this.self).viewvalues();
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyDictionary var4 = new PyDictionary(this.for_type);
         if (var1) {
            var4.dict___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyDictionaryDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new dict___init___exposer("__init__"), new dict_fromkeys_exposer("fromkeys"), new dict___len___exposer("__len__"), new dict___getitem___exposer("__getitem__"), new dict___setitem___exposer("__setitem__"), new dict___delitem___exposer("__delitem__"), new dict___iter___exposer("__iter__"), new dict_toString_exposer("__repr__"), new dict_toString_exposer("__str__"), new dict___eq___exposer("__eq__"), new dict___ne___exposer("__ne__"), new dict___lt___exposer("__lt__"), new dict___gt___exposer("__gt__"), new dict___le___exposer("__le__"), new dict___ge___exposer("__ge__"), new dict___cmp___exposer("__cmp__"), new dict_has_key_exposer("has_key"), new dict___contains___exposer("__contains__"), new dict_get_exposer("get"), new dict_copy_exposer("copy"), new dict_clear_exposer("clear"), new dict_update_exposer("update"), new dict_setdefault_exposer("setdefault"), new dict_setifabsent_exposer("setifabsent"), new dict_pop_exposer("pop"), new dict_popitem_exposer("popitem"), new dict_items_exposer("items"), new dict_keys_exposer("keys"), new dict_values_exposer("values"), new dict_iteritems_exposer("iteritems"), new dict_iterkeys_exposer("iterkeys"), new dict_itervalues_exposer("itervalues"), new dict___hash___exposer("__hash__"), new viewkeys_exposer("viewkeys"), new viewitems_exposer("viewitems"), new viewvalues_exposer("viewvalues")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("dict", PyDictionary.class, PyObject.class, (boolean)1, "dict() -> new empty dictionary\ndict(mapping) -> new dictionary initialized from a mapping object's\n    (key, value) pairs\ndict(iterable) -> new dictionary initialized as if via:\n    d = {}\n    for k, v in iterable:\n        d[k] = v\ndict(**kwargs) -> new dictionary initialized with the name=value pairs\n    in the keyword argument list.  For example:  dict(one=1, two=2)", var1, var2, new exposed___new__());
      }
   }
}
