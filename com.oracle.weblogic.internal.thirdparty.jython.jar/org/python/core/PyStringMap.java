package org.python.core;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "stringmap",
   base = PyObject.class,
   isBaseType = false
)
public class PyStringMap extends AbstractDict implements Traverseproc {
   private static PyType lazyType;
   private final ConcurrentMap table;

   public ConcurrentMap getMap() {
      return this.table;
   }

   public PyStringMap() {
      this(4);
   }

   public PyStringMap(int capacity) {
      super(getLazyType());
      this.table = new ConcurrentHashMap(capacity, 0.75F, 2);
   }

   public PyStringMap(Map map) {
      this(Math.max((int)((float)map.size() / 0.75F) + 1, 16));
      this.table.putAll(map);
   }

   public PyStringMap(PyObject[] elements) {
      this(elements.length);

      for(int i = 0; i < elements.length; i += 2) {
         this.__setitem__(elements[i], elements[i + 1]);
      }

   }

   private static PyType getLazyType() {
      if (lazyType == null) {
         lazyType = PyType.fromClass(PyStringMap.class);
      }

      return lazyType;
   }

   @ExposedNew
   static final PyObject stringmap_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      PyStringMap map = new PyStringMap();
      map.stringmap_update(args, keywords);
      return map;
   }

   public static PyObject fromkeys(PyObject keys) {
      return fromkeys(keys, Py.None);
   }

   public static PyObject fromkeys(PyObject keys, PyObject value) {
      return stringmap_fromkeys(TYPE, keys, value);
   }

   static PyObject stringmap_fromkeys(PyType type, PyObject keys, PyObject value) {
      PyObject d = type.__call__();
      Iterator var4 = keys.asIterable().iterator();

      while(var4.hasNext()) {
         PyObject o = (PyObject)var4.next();
         d.__setitem__(o, value);
      }

      return d;
   }

   public int __len__() {
      return this.stringmap___len__();
   }

   final int stringmap___len__() {
      return this.table.size();
   }

   public boolean __nonzero__() {
      return this.table.size() != 0;
   }

   public PyObject __finditem__(String key) {
      return key == null ? null : (PyObject)this.table.get(key);
   }

   public PyObject __finditem__(PyObject key) {
      return key instanceof PyString ? this.__finditem__(((PyString)key).internedString()) : (PyObject)this.table.get(key);
   }

   public PyObject __getitem__(String key) {
      PyObject o = this.__finditem__(key);
      if (null == o) {
         throw Py.KeyError(key);
      } else {
         return o;
      }
   }

   public PyObject __getitem__(PyObject key) {
      return this.stringmap___getitem__(key);
   }

   final PyObject stringmap___getitem__(PyObject key) {
      if (key instanceof PyString) {
         return this.__getitem__(((PyString)key).internedString());
      } else {
         PyObject o = this.__finditem__(key);
         if (null == o) {
            throw Py.KeyError(key);
         } else {
            return o;
         }
      }
   }

   public PyObject __iter__() {
      return this.stringmap___iter__();
   }

   final PyObject stringmap___iter__() {
      return this.stringmap_iterkeys();
   }

   public void __setitem__(String key, PyObject value) {
      if (value == null) {
         this.table.remove(key);
      } else {
         this.table.put(key, value);
      }

   }

   public void __setitem__(PyObject key, PyObject value) {
      this.stringmap___setitem__(key, value);
   }

   final void stringmap___setitem__(PyObject key, PyObject value) {
      if (value == null) {
         this.table.remove(pyToKey(key));
      } else if (key instanceof PyString) {
         this.__setitem__(((PyString)key).internedString(), value);
      } else {
         this.table.put(key, value);
      }

   }

   public void __delitem__(String key) {
      Object ret = this.table.remove(key);
      if (ret == null) {
         throw Py.KeyError(key);
      }
   }

   public void __delitem__(PyObject key) {
      this.stringmap___delitem__(key);
   }

   final void stringmap___delitem__(PyObject key) {
      if (key instanceof PyString) {
         this.__delitem__(((PyString)key).internedString());
      } else {
         Object ret = this.table.remove(key);
         if (ret == null) {
            throw Py.KeyError(key);
         }
      }

   }

   public void clear() {
      this.stringmap_clear();
   }

   final void stringmap_clear() {
      this.table.clear();
   }

   public String toString() {
      return this.stringmap_toString();
   }

   final String stringmap_toString() {
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return "{...}";
      } else {
         StringBuilder buf = new StringBuilder("{");
         Iterator var3 = this.table.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            Object key = entry.getKey();
            if (key instanceof String) {
               buf.append((new PyString((String)key)).__repr__().toString());
            } else {
               buf.append(((PyObject)key).__repr__().toString());
            }

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

   public int __cmp__(PyObject other) {
      return this.stringmap___cmp__(other);
   }

   final int stringmap___cmp__(PyObject other) {
      if (!(other instanceof AbstractDict)) {
         return -2;
      } else {
         int an = this.__len__();
         int bn = other.__len__();
         if (an < bn) {
            return -1;
         } else if (an > bn) {
            return 1;
         } else {
            PyList akeys = this.keys();
            PyList bkeys = ((AbstractDict)other).keys();
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
               c = avalue._cmp(bvalue);
               if (c != 0) {
                  return c;
               }
            }

            return 0;
         }
      }
   }

   public boolean has_key(String key) {
      return this.table.containsKey(key);
   }

   public boolean has_key(PyObject key) {
      return this.stringmap_has_key(key);
   }

   final boolean stringmap_has_key(PyObject key) {
      return this.table.containsKey(pyToKey(key));
   }

   public boolean __contains__(PyObject o) {
      return this.stringmap___contains__(o);
   }

   final boolean stringmap___contains__(PyObject o) {
      return this.stringmap_has_key(o);
   }

   public PyObject get(PyObject key, PyObject defaultObj) {
      return this.stringmap_get(key, defaultObj);
   }

   final PyObject stringmap_get(PyObject key, PyObject defaultObj) {
      PyObject obj = this.__finditem__(key);
      return obj == null ? defaultObj : obj;
   }

   public PyObject get(PyObject key) {
      return this.stringmap_get(key, Py.None);
   }

   public PyStringMap copy() {
      return this.stringmap_copy();
   }

   final PyStringMap stringmap_copy() {
      return new PyStringMap(this.table);
   }

   public void update(PyObject other) {
      this.stringmap_update(new PyObject[]{other}, Py.NoKeywords);
   }

   final void stringmap_update(PyObject[] args, String[] keywords) {
      int nargs = args.length - keywords.length;
      if (nargs > 1) {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(nargs, false, "update", 0, 1);
      } else {
         if (nargs == 1) {
            PyObject arg = args[0];
            if (arg.__findattr__("keys") != null) {
               this.merge(arg);
            } else {
               this.mergeFromSeq(arg);
            }
         }

         for(int i = 0; i < keywords.length; ++i) {
            this.__setitem__(keywords[i], args[nargs + i]);
         }

      }
   }

   private void merge(PyObject other) {
      if (other instanceof PyStringMap) {
         this.table.putAll(((PyStringMap)other).table);
      } else if (other instanceof PyDictionary) {
         this.mergeFromKeys(other, ((PyDictionary)other).keys());
      } else {
         this.mergeFromKeys(other, other.invoke("keys"));
      }

   }

   public void merge(PyObject other, boolean override) {
      synchronized(this.table) {
         if (override) {
            this.merge(other);
         } else if (other instanceof PyStringMap) {
            Set entrySet = ((PyStringMap)other).table.entrySet();
            Iterator var5 = entrySet.iterator();

            while(var5.hasNext()) {
               Map.Entry ent = (Map.Entry)var5.next();
               if (!this.table.containsKey(ent.getKey())) {
                  this.table.put(ent.getKey(), ent.getValue());
               }
            }
         } else if (other instanceof PyDictionary) {
            this.mergeFromKeys(other, ((PyDictionary)other).keys(), override);
         } else {
            this.mergeFromKeys(other, other.invoke("keys"), override);
         }

      }
   }

   private void mergeFromKeys(PyObject other, PyObject keys) {
      Iterator var3 = keys.asIterable().iterator();

      while(var3.hasNext()) {
         PyObject key = (PyObject)var3.next();
         this.__setitem__(key, other.__getitem__(key));
      }

   }

   public void mergeFromKeys(PyObject other, PyObject keys, boolean override) {
      synchronized(this.table) {
         if (override) {
            this.mergeFromKeys(other, keys);
         } else {
            Iterator var5 = keys.asIterable().iterator();

            while(var5.hasNext()) {
               PyObject key = (PyObject)var5.next();
               if (!this.__contains__(key)) {
                  this.__setitem__(key, other.__getitem__(key));
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

         this.__setitem__(pair.__getitem__(0), pair.__getitem__(1));
      }

   }

   public void mergeFromSeq(PyObject other, boolean override) {
      synchronized(this.table) {
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

               if (!this.__contains__(pair.__getitem__(0))) {
                  this.__setitem__(pair.__getitem__(0), pair.__getitem__(1));
               }
            }
         }

      }
   }

   public PyObject setdefault(PyObject key) {
      return this.setdefault(key, Py.None);
   }

   public PyObject setdefault(PyObject key, PyObject failobj) {
      return this.stringmap_setdefault(key, failobj);
   }

   final PyObject stringmap_setdefault(PyObject key, PyObject failobj) {
      Object internedKey = key instanceof PyString ? ((PyString)key).internedString() : key;
      PyObject oldValue = (PyObject)this.table.putIfAbsent(internedKey, failobj);
      return oldValue == null ? failobj : oldValue;
   }

   public PyObject popitem() {
      return this.stringmap_popitem();
   }

   final PyObject stringmap_popitem() {
      Iterator it = this.table.entrySet().iterator();
      if (!it.hasNext()) {
         throw Py.KeyError("popitem(): dictionary is empty");
      } else {
         PyTuple tuple = this.itemTuple((Map.Entry)it.next());
         it.remove();
         return tuple;
      }
   }

   public PyObject pop(PyObject key) {
      if (this.table.size() == 0) {
         throw Py.KeyError("pop(): dictionary is empty");
      } else {
         return this.stringmap_pop(key, (PyObject)null);
      }
   }

   public PyObject pop(PyObject key, PyObject failobj) {
      return this.stringmap_pop(key, failobj);
   }

   final PyObject stringmap_pop(PyObject key, PyObject failobj) {
      PyObject value = (PyObject)this.table.remove(pyToKey(key));
      if (value == null) {
         if (failobj == null) {
            throw Py.KeyError(key);
         } else {
            return failobj;
         }
      } else {
         return value;
      }
   }

   public PyList items() {
      return this.stringmap_items();
   }

   final PyList stringmap_items() {
      return new PyList(this.stringmap_iteritems());
   }

   private PyTuple itemTuple(Map.Entry entry) {
      return new PyTuple(new PyObject[]{keyToPy(entry.getKey()), (PyObject)entry.getValue()});
   }

   public PyList keys() {
      return this.stringmap_keys();
   }

   final PyList stringmap_keys() {
      PyObject[] keyArray = new PyObject[this.table.size()];
      int i = 0;

      Object key;
      for(Iterator var3 = this.table.keySet().iterator(); var3.hasNext(); keyArray[i++] = keyToPy(key)) {
         key = var3.next();
      }

      return new PyList(keyArray);
   }

   public PyList values() {
      return this.stringmap_values();
   }

   final PyList stringmap_values() {
      return new PyList(this.table.values());
   }

   public PyObject iteritems() {
      return this.stringmap_iteritems();
   }

   final PyObject stringmap_iteritems() {
      return new ItemsIter(this.table.entrySet());
   }

   public PyObject iterkeys() {
      return this.stringmap_iterkeys();
   }

   final PyObject stringmap_iterkeys() {
      return new KeysIter(this.table.keySet());
   }

   public PyObject itervalues() {
      return this.stringmap_itervalues();
   }

   final PyObject stringmap_itervalues() {
      return new StringMapValuesIter(this.table.values());
   }

   public int hashCode() {
      return this.stringmap___hash__();
   }

   final int stringmap___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   public boolean isMappingType() {
      return true;
   }

   public boolean isSequenceType() {
      return false;
   }

   private static PyObject keyToPy(Object objKey) {
      return (PyObject)(objKey instanceof String ? PyString.fromInterned((String)objKey) : (PyObject)objKey);
   }

   private static Object pyToKey(PyObject pyKey) {
      return pyKey instanceof PyString ? ((PyString)pyKey).internedString() : pyKey;
   }

   public Set pyKeySet() {
      return new PyStringMapKeySetWrapper(this.table.keySet());
   }

   public Set entrySet() {
      return new PyMapEntrySet(this.getMap().entrySet());
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

   public int traverse(Visitproc visit, Object arg) {
      Iterator var3 = this.table.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry ent = (Map.Entry)var3.next();
         Object key = ent.getKey();
         PyObject value = (PyObject)ent.getValue();
         int retVal;
         if (key instanceof PyObject) {
            retVal = visit.visit((PyObject)key, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (value != null) {
            retVal = visit.visit(value, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.table.containsKey(ob) || this.table.containsValue(ob));
   }

   static {
      PyType.addBuilder(PyStringMap.class, new PyExposer());
   }

   private static class PyStringMapKeySetWrapper extends AbstractSet {
      Set backend;

      PyStringMapKeySetWrapper(Set backend) {
         this.backend = backend;
      }

      public Iterator iterator() {
         return new PyStringMapKeySetIter(this.backend.iterator());
      }

      public int size() {
         return this.backend.size();
      }

      class PyStringMapKeySetIter implements Iterator {
         Iterator itr;

         PyStringMapKeySetIter(Iterator itr) {
            this.itr = itr;
         }

         public boolean hasNext() {
            return this.itr.hasNext();
         }

         public PyObject next() {
            return PyStringMap.keyToPy(this.itr.next());
         }

         public void remove() {
            this.itr.remove();
         }
      }
   }

   private class ItemsIter extends StringMapIter {
      public ItemsIter(Set s) {
         super(s);
      }

      public PyObject stringMapNext() {
         return PyStringMap.this.itemTuple((Map.Entry)this.iterator.next());
      }
   }

   private class KeysIter extends StringMapIter {
      public KeysIter(Set s) {
         super(s);
      }

      protected PyObject stringMapNext() {
         return PyStringMap.keyToPy(this.iterator.next());
      }
   }

   private class StringMapValuesIter extends StringMapIter {
      public StringMapValuesIter(Collection c) {
         super(c);
      }

      public PyObject stringMapNext() {
         return (PyObject)this.iterator.next();
      }
   }

   private abstract class StringMapIter extends PyIterator {
      protected final Iterator iterator;
      private final int size;

      public StringMapIter(Collection c) {
         this.iterator = c.iterator();
         this.size = c.size();
      }

      public PyObject __iternext__() {
         if (PyStringMap.this.table.size() != this.size) {
            throw Py.RuntimeError("dictionary changed size during iteration");
         } else {
            return !this.iterator.hasNext() ? null : this.stringMapNext();
         }
      }

      protected abstract PyObject stringMapNext();
   }

   private static class stringmap_fromkeys_exposer extends PyBuiltinClassMethodNarrow {
      public stringmap_fromkeys_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "dict.fromkeys(S[,v]) -> New dict with keys from S and values equal to v.\nv defaults to None.";
      }

      public stringmap_fromkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "dict.fromkeys(S[,v]) -> New dict with keys from S and values equal to v.\nv defaults to None.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_fromkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return PyStringMap.stringmap_fromkeys((PyType)this.self, var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return PyStringMap.stringmap_fromkeys((PyType)this.self, var1, Py.None);
      }
   }

   private static class stringmap___len___exposer extends PyBuiltinMethodNarrow {
      public stringmap___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public stringmap___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyStringMap)this.self).stringmap___len__());
      }
   }

   private static class stringmap___getitem___exposer extends PyBuiltinMethodNarrow {
      public stringmap___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public stringmap___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStringMap)this.self).stringmap___getitem__(var1);
      }
   }

   private static class stringmap___iter___exposer extends PyBuiltinMethodNarrow {
      public stringmap___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public stringmap___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap___iter__();
      }
   }

   private static class stringmap___setitem___exposer extends PyBuiltinMethodNarrow {
      public stringmap___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public stringmap___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyStringMap)this.self).stringmap___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class stringmap___delitem___exposer extends PyBuiltinMethodNarrow {
      public stringmap___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public stringmap___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyStringMap)this.self).stringmap___delitem__(var1);
         return Py.None;
      }
   }

   private static class stringmap_clear_exposer extends PyBuiltinMethodNarrow {
      public stringmap_clear_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.clear() -> None.  Remove all items from D.";
      }

      public stringmap_clear_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.clear() -> None.  Remove all items from D.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_clear_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyStringMap)this.self).stringmap_clear();
         return Py.None;
      }
   }

   private static class stringmap_toString_exposer extends PyBuiltinMethodNarrow {
      public stringmap_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__str__() <==> str(x)";
      }

      public stringmap_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__str__() <==> str(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyStringMap)this.self).stringmap_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class stringmap___cmp___exposer extends PyBuiltinMethodNarrow {
      public stringmap___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public stringmap___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         int var10000 = ((PyStringMap)this.self).stringmap___cmp__(var1);
         if (var10000 == -2) {
            throw Py.TypeError("stringmap.__cmp__(x,y) requires y to be 'stringmap', not a '" + var1.getType().fastGetName() + "'");
         } else {
            return Py.newInteger(var10000);
         }
      }
   }

   private static class stringmap_has_key_exposer extends PyBuiltinMethodNarrow {
      public stringmap_has_key_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "D.has_key(k) -> True if D has a key k, else False";
      }

      public stringmap_has_key_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.has_key(k) -> True if D has a key k, else False";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_has_key_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyStringMap)this.self).stringmap_has_key(var1));
      }
   }

   private static class stringmap___contains___exposer extends PyBuiltinMethodNarrow {
      public stringmap___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "D.__contains__(k) -> True if D has a key k, else False";
      }

      public stringmap___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.__contains__(k) -> True if D has a key k, else False";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyStringMap)this.self).stringmap___contains__(var1));
      }
   }

   private static class stringmap_get_exposer extends PyBuiltinMethodNarrow {
      public stringmap_get_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.get(k[,d]) -> D[k] if k in D, else d.  d defaults to None.";
      }

      public stringmap_get_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.get(k[,d]) -> D[k] if k in D, else d.  d defaults to None.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_get_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyStringMap)this.self).stringmap_get(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStringMap)this.self).stringmap_get(var1, Py.None);
      }
   }

   private static class stringmap_copy_exposer extends PyBuiltinMethodNarrow {
      public stringmap_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.copy() -> a shallow copy of D";
      }

      public stringmap_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.copy() -> a shallow copy of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_copy();
      }
   }

   private static class stringmap_update_exposer extends PyBuiltinMethod {
      public stringmap_update_exposer(String var1) {
         super(var1);
         super.doc = "D.update(E, **F) -> None.  Update D from dict/iterable E and F.\nIf E has a .keys() method, does:     for k in E: D[k] = E[k]\nIf E lacks .keys() method, does:     for (k, v) in E: D[k] = v\nIn either case, this is followed by: for k in F: D[k] = F[k]";
      }

      public stringmap_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.update(E, **F) -> None.  Update D from dict/iterable E and F.\nIf E has a .keys() method, does:     for k in E: D[k] = E[k]\nIf E lacks .keys() method, does:     for (k, v) in E: D[k] = v\nIn either case, this is followed by: for k in F: D[k] = F[k]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyStringMap)this.self).stringmap_update(var1, var2);
         return Py.None;
      }
   }

   private static class stringmap_setdefault_exposer extends PyBuiltinMethodNarrow {
      public stringmap_setdefault_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.setdefault(k[,d]) -> D.get(k,d), also set D[k]=d if k not in D";
      }

      public stringmap_setdefault_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.setdefault(k[,d]) -> D.get(k,d), also set D[k]=d if k not in D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_setdefault_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyStringMap)this.self).stringmap_setdefault(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStringMap)this.self).stringmap_setdefault(var1, Py.None);
      }
   }

   private static class stringmap_popitem_exposer extends PyBuiltinMethodNarrow {
      public stringmap_popitem_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.popitem() -> (k, v), remove and return some (key, value) pair as a\n2-tuple; but raise KeyError if D is empty.";
      }

      public stringmap_popitem_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.popitem() -> (k, v), remove and return some (key, value) pair as a\n2-tuple; but raise KeyError if D is empty.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_popitem_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_popitem();
      }
   }

   private static class stringmap_pop_exposer extends PyBuiltinMethodNarrow {
      public stringmap_pop_exposer(String var1) {
         super(var1, 2, 3);
         super.doc = "D.pop(k[,d]) -> v, remove specified key and return the corresponding value.\nIf key is not found, d is returned if given, otherwise KeyError is raised";
      }

      public stringmap_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.pop(k[,d]) -> v, remove specified key and return the corresponding value.\nIf key is not found, d is returned if given, otherwise KeyError is raised";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyStringMap)this.self).stringmap_pop(var1, var2);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyStringMap)this.self).stringmap_pop(var1, (PyObject)null);
      }
   }

   private static class stringmap_items_exposer extends PyBuiltinMethodNarrow {
      public stringmap_items_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.items() -> list of D's (key, value) pairs, as 2-tuples";
      }

      public stringmap_items_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.items() -> list of D's (key, value) pairs, as 2-tuples";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_items_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_items();
      }
   }

   private static class stringmap_keys_exposer extends PyBuiltinMethodNarrow {
      public stringmap_keys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.keys() -> list of D's keys";
      }

      public stringmap_keys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.keys() -> list of D's keys";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_keys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_keys();
      }
   }

   private static class stringmap_values_exposer extends PyBuiltinMethodNarrow {
      public stringmap_values_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.values() -> list of D's values";
      }

      public stringmap_values_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.values() -> list of D's values";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_values_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_values();
      }
   }

   private static class stringmap_iteritems_exposer extends PyBuiltinMethodNarrow {
      public stringmap_iteritems_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.iteritems() -> an iterator over the (key, value) items of D";
      }

      public stringmap_iteritems_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.iteritems() -> an iterator over the (key, value) items of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_iteritems_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_iteritems();
      }
   }

   private static class stringmap_iterkeys_exposer extends PyBuiltinMethodNarrow {
      public stringmap_iterkeys_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.iterkeys() -> an iterator over the keys of D";
      }

      public stringmap_iterkeys_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.iterkeys() -> an iterator over the keys of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_iterkeys_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_iterkeys();
      }
   }

   private static class stringmap_itervalues_exposer extends PyBuiltinMethodNarrow {
      public stringmap_itervalues_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "D.itervalues() -> an iterator over the values of D";
      }

      public stringmap_itervalues_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "D.itervalues() -> an iterator over the values of D";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap_itervalues_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyStringMap)this.self).stringmap_itervalues();
      }
   }

   private static class stringmap___hash___exposer extends PyBuiltinMethodNarrow {
      public stringmap___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public stringmap___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new stringmap___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyStringMap)this.self).stringmap___hash__());
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
         return ((PyStringMap)this.self).viewkeys();
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
         return ((PyStringMap)this.self).viewitems();
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
         return ((PyStringMap)this.self).viewvalues();
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyStringMap.stringmap_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new stringmap_fromkeys_exposer("fromkeys"), new stringmap___len___exposer("__len__"), new stringmap___getitem___exposer("__getitem__"), new stringmap___iter___exposer("__iter__"), new stringmap___setitem___exposer("__setitem__"), new stringmap___delitem___exposer("__delitem__"), new stringmap_clear_exposer("clear"), new stringmap_toString_exposer("__repr__"), new stringmap_toString_exposer("__str__"), new stringmap___cmp___exposer("__cmp__"), new stringmap_has_key_exposer("has_key"), new stringmap___contains___exposer("__contains__"), new stringmap_get_exposer("get"), new stringmap_copy_exposer("copy"), new stringmap_update_exposer("update"), new stringmap_setdefault_exposer("setdefault"), new stringmap_popitem_exposer("popitem"), new stringmap_pop_exposer("pop"), new stringmap_items_exposer("items"), new stringmap_keys_exposer("keys"), new stringmap_values_exposer("values"), new stringmap_iteritems_exposer("iteritems"), new stringmap_iterkeys_exposer("iterkeys"), new stringmap_itervalues_exposer("itervalues"), new stringmap___hash___exposer("__hash__"), new viewkeys_exposer("viewkeys"), new viewitems_exposer("viewitems"), new viewvalues_exposer("viewvalues")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("stringmap", PyStringMap.class, PyObject.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
