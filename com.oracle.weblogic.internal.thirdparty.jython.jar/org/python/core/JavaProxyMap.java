package org.python.core;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class JavaProxyMap {
   private static final PyBuiltinMethodNarrow mapLenProxy = new MapMethod("__len__", 0) {
      public PyObject __call__() {
         return Py.java2py(this.asMap().size());
      }
   };
   private static final PyBuiltinMethodNarrow mapReprProxy = new MapMethod("__repr__", 0) {
      public PyObject __call__() {
         StringBuilder repr = new StringBuilder("{");
         Iterator var2 = this.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            Object jkey = entry.getKey();
            Object jval = entry.getValue();
            repr.append(jkey.toString());
            repr.append(": ");
            repr.append(jval == this.asMap() ? "{...}" : (jval == null ? "None" : jval.toString()));
            repr.append(", ");
         }

         int lastindex = repr.lastIndexOf(", ");
         if (lastindex > -1) {
            repr.delete(lastindex, lastindex + 2);
         }

         repr.append("}");
         return new PyString(repr.toString());
      }
   };
   private static final PyBuiltinMethodNarrow mapEqProxy = new MapMethod("__eq__", 1) {
      public PyObject __call__(PyObject other) {
         return JavaProxyMap.mapEq(this.self, other);
      }
   };
   private static final PyBuiltinMethodNarrow mapLeProxy = new MapMethod("__le__", 1) {
      public PyObject __call__(PyObject other) {
         return JavaProxyMap.mapLe(this.self, other);
      }
   };
   private static final PyBuiltinMethodNarrow mapGeProxy = new MapMethod("__ge__", 1) {
      public PyObject __call__(PyObject other) {
         return JavaProxyMap.mapLe(this.self, other).__not__().__or__(JavaProxyMap.mapEq(this.self, other));
      }
   };
   private static final PyBuiltinMethodNarrow mapLtProxy = new MapMethod("__lt__", 1) {
      public PyObject __call__(PyObject other) {
         return JavaProxyMap.mapLe(this.self, other).__and__(JavaProxyMap.mapEq(this.self, other).__not__());
      }
   };
   private static final PyBuiltinMethodNarrow mapGtProxy = new MapMethod("__gt__", 1) {
      public PyObject __call__(PyObject other) {
         return JavaProxyMap.mapLe(this.self, other).__not__();
      }
   };
   private static final PyBuiltinMethodNarrow mapIterProxy = new MapMethod("__iter__", 0) {
      public PyObject __call__() {
         return new JavaIterator(this.asMap().keySet());
      }
   };
   private static final PyBuiltinMethodNarrow mapContainsProxy = new MapMethod("__contains__", 1) {
      public PyObject __call__(PyObject obj) {
         Object other = obj.__tojava__(Object.class);
         return this.asMap().containsKey(other) ? Py.True : Py.False;
      }
   };
   private static final PyBuiltinMethodNarrow mapGetProxy = new MapMethod("get", 1, 2) {
      public PyObject __call__(PyObject key) {
         return this.__call__(key, Py.None);
      }

      public PyObject __call__(PyObject key, PyObject _default) {
         Object jkey = Py.tojava(key, Object.class);
         return this.asMap().containsKey(jkey) ? Py.java2py(this.asMap().get(jkey)) : _default;
      }
   };
   private static final PyBuiltinMethodNarrow mapGetItemProxy = new MapMethod("__getitem__", 1) {
      public PyObject __call__(PyObject key) {
         Object jkey = Py.tojava(key, Object.class);
         if (this.asMap().containsKey(jkey)) {
            return Py.java2py(this.asMap().get(jkey));
         } else {
            throw Py.KeyError(key);
         }
      }
   };
   private static final PyBuiltinMethodNarrow mapPutProxy = new MapMethod("__setitem__", 2) {
      public PyObject __call__(PyObject key, PyObject value) {
         this.asMap().put(Py.tojava(key, Object.class), value == Py.None ? Py.None : Py.tojava(value, Object.class));
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow mapRemoveProxy = new MapMethod("__delitem__", 1) {
      public PyObject __call__(PyObject key) {
         Object jkey = Py.tojava(key, Object.class);
         if (this.asMap().remove(jkey) == null) {
            throw Py.KeyError(key);
         } else {
            return Py.None;
         }
      }
   };
   private static final PyBuiltinMethodNarrow mapIterItemsProxy = new MapMethod("iteritems", 0) {
      public PyObject __call__() {
         final Iterator entrySetIterator = this.asMap().entrySet().iterator();
         return new PyIterator() {
            public PyObject __iternext__() {
               if (entrySetIterator.hasNext()) {
                  Map.Entry nextEntry = (Map.Entry)entrySetIterator.next();
                  return new PyTuple(new PyObject[]{Py.java2py(nextEntry.getKey()), Py.java2py(nextEntry.getValue())});
               } else {
                  return null;
               }
            }
         };
      }
   };
   private static final PyBuiltinMethodNarrow mapIterKeysProxy = new MapMethod("iterkeys", 0) {
      public PyObject __call__() {
         final Iterator keyIterator = this.asMap().keySet().iterator();
         return new PyIterator() {
            public PyObject __iternext__() {
               if (keyIterator.hasNext()) {
                  Object nextKey = keyIterator.next();
                  return Py.java2py(nextKey);
               } else {
                  return null;
               }
            }
         };
      }
   };
   private static final PyBuiltinMethodNarrow mapIterValuesProxy = new MapMethod("itervalues", 0) {
      public PyObject __call__() {
         final Iterator valueIterator = this.asMap().values().iterator();
         return new PyIterator() {
            public PyObject __iternext__() {
               if (valueIterator.hasNext()) {
                  Object nextValue = valueIterator.next();
                  return Py.java2py(nextValue);
               } else {
                  return null;
               }
            }
         };
      }
   };
   private static final PyBuiltinMethodNarrow mapHasKeyProxy = new MapMethod("has_key", 1) {
      public PyObject __call__(PyObject key) {
         return this.asMap().containsKey(Py.tojava(key, Object.class)) ? Py.True : Py.False;
      }
   };
   private static final PyBuiltinMethodNarrow mapKeysProxy = new MapMethod("keys", 0) {
      public PyObject __call__() {
         PyList keys = new PyList();
         Iterator var2 = this.asMap().keySet().iterator();

         while(var2.hasNext()) {
            Object key = var2.next();
            keys.add(Py.java2py(key));
         }

         return keys;
      }
   };
   private static final PyBuiltinMethod mapValuesProxy = new MapMethod("values", 0) {
      public PyObject __call__() {
         PyList values = new PyList();
         Iterator var2 = this.asMap().values().iterator();

         while(var2.hasNext()) {
            Object value = var2.next();
            values.add(Py.java2py(value));
         }

         return values;
      }
   };
   private static final PyBuiltinMethodNarrow mapSetDefaultProxy = new MapMethod("setdefault", 1, 2) {
      public PyObject __call__(PyObject key) {
         return this.__call__(key, Py.None);
      }

      public PyObject __call__(PyObject key, PyObject _default) {
         Object jkey = Py.tojava(key, Object.class);
         Object jval = this.asMap().get(jkey);
         if (jval == null) {
            this.asMap().put(jkey, _default == Py.None ? Py.None : Py.tojava(_default, Object.class));
            return _default;
         } else {
            return Py.java2py(jval);
         }
      }
   };
   private static final PyBuiltinMethodNarrow mapPopProxy = new MapMethod("pop", 1, 2) {
      public PyObject __call__(PyObject key) {
         return this.__call__(key, (PyObject)null);
      }

      public PyObject __call__(PyObject key, PyObject _default) {
         Object jkey = Py.tojava(key, Object.class);
         if (this.asMap().containsKey(jkey)) {
            PyObject value = Py.java2py(this.asMap().remove(jkey));

            assert value != null;

            return Py.java2py(value);
         } else if (_default == null) {
            throw Py.KeyError(key);
         } else {
            return _default;
         }
      }
   };
   private static final PyBuiltinMethodNarrow mapPopItemProxy = new MapMethod("popitem", 0) {
      public PyObject __call__() {
         if (this.asMap().size() == 0) {
            throw Py.KeyError("popitem(): map is empty");
         } else {
            Object key = this.asMap().keySet().toArray()[0];
            Object val = this.asMap().remove(key);
            return Py.java2py(val);
         }
      }
   };
   private static final PyBuiltinMethodNarrow mapItemsProxy = new MapMethod("items", 0) {
      public PyObject __call__() {
         PyList items = new PyList();
         Iterator var2 = this.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            items.add(new PyTuple(new PyObject[]{Py.java2py(entry.getKey()), Py.java2py(entry.getValue())}));
         }

         return items;
      }
   };
   private static final PyBuiltinMethodNarrow mapCopyProxy = new MapMethod("copy", 0) {
      public PyObject __call__() {
         Map jmap = this.asMap();

         Map jclone;
         try {
            jclone = (Map)jmap.getClass().newInstance();
         } catch (IllegalAccessException var5) {
            throw Py.JavaError(var5);
         } catch (InstantiationException var6) {
            throw Py.JavaError(var6);
         }

         Iterator var3 = jmap.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            jclone.put(entry.getKey(), entry.getValue());
         }

         return Py.java2py(jclone);
      }
   };
   private static final PyBuiltinMethodNarrow mapUpdateProxy = new MapMethod("update", 0, 1) {
      private Map jmap;

      public PyObject __call__() {
         return Py.None;
      }

      public PyObject __call__(PyObject other) {
         return this.__call__(new PyObject[]{other}, new String[0]);
      }

      public PyObject __call__(PyObject[] args, String[] keywords) {
         if (args.length - keywords.length != 1) {
            throw this.info.unexpectedCall(args.length, false);
         } else {
            this.jmap = this.asMap();
            PyObject other = args[0];
            Object proxy = other.getJavaProxy();
            if (proxy instanceof Map) {
               this.merge((Map)proxy);
            } else if (other.__findattr__("keys") != null) {
               this.merge(other);
            } else {
               this.mergeFromSeq(other);
            }

            for(int i = 0; i < keywords.length; ++i) {
               String jkey = keywords[i];
               PyObject value = args[1 + i];
               this.jmap.put(jkey, Py.tojava(value, Object.class));
            }

            return Py.None;
         }
      }

      private void merge(Map other) {
         Iterator var2 = other.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.jmap.put(entry.getKey(), entry.getValue());
         }

      }

      private void merge(PyObject other) {
         if (other instanceof PyDictionary) {
            this.jmap.putAll(((PyDictionary)other).getMap());
         } else if (other instanceof PyStringMap) {
            this.mergeFromKeys(other, ((PyStringMap)other).keys());
         } else {
            this.mergeFromKeys(other, other.invoke("keys"));
         }

      }

      private void mergeFromKeys(PyObject other, PyObject keys) {
         Iterator var3 = keys.asIterable().iterator();

         while(var3.hasNext()) {
            PyObject key = (PyObject)var3.next();
            this.jmap.put(Py.tojava(key, Object.class), Py.tojava(other.__getitem__(key), Object.class));
         }

      }

      private void mergeFromSeq(PyObject other) {
         PyObject pairs = other.__iter__();

         PyObject pairx;
         for(int i = 0; (pairx = pairs.__iternext__()) != null; ++i) {
            PySequence pair;
            try {
               pair = PySequence.fastSequence(pairx, "");
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

            this.jmap.put(Py.tojava(pair.__getitem__(0), Object.class), Py.tojava(pair.__getitem__(1), Object.class));
         }

      }
   };
   private static final PyBuiltinClassMethodNarrow mapFromKeysProxy = new MapClassMethod("fromkeys", 1, 2) {
      public PyObject __call__(PyObject keys) {
         return this.__call__(keys, (PyObject)null);
      }

      public PyObject __call__(PyObject keys, PyObject _default) {
         Object defobj = _default == null ? Py.None : Py.tojava(_default, Object.class);
         Class theClass = this.asClass();

         try {
            Map theMap = (Map)theClass.newInstance();
            Iterator var6 = keys.asIterable().iterator();

            while(var6.hasNext()) {
               PyObject key = (PyObject)var6.next();
               theMap.put(Py.tojava(key, Object.class), defobj);
            }

            return Py.java2py(theMap);
         } catch (InstantiationException var8) {
            throw Py.JavaError(var8);
         } catch (IllegalAccessException var9) {
            throw Py.JavaError(var9);
         }
      }
   };

   private static PyObject mapEq(PyObject self, PyObject other) {
      Map selfMap = (Map)self.getJavaProxy();
      if (other.getType().isSubType(PyDictionary.TYPE)) {
         PyDictionary oDict = (PyDictionary)other;
         if (selfMap.size() != oDict.size()) {
            return Py.False;
         } else {
            Iterator var9 = selfMap.keySet().iterator();

            Object jval;
            PyObject oVal;
            do {
               if (!var9.hasNext()) {
                  return Py.True;
               }

               Object jkey = var9.next();
               jval = selfMap.get(jkey);
               oVal = oDict.__finditem__(Py.java2py(jkey));
               if (oVal == null) {
                  return Py.False;
               }
            } while(Py.java2py(jval)._eq(oVal).__nonzero__());

            return Py.False;
         }
      } else {
         Object oj = other.getJavaProxy();
         if (oj instanceof Map) {
            Map oMap = (Map)oj;
            return Py.newBoolean(selfMap.equals(oMap));
         } else {
            return null;
         }
      }
   }

   private static PyObject mapLe(PyObject self, PyObject other) {
      Set selfKeys = ((Map)self.getJavaProxy()).keySet();
      if (other.getType().isSubType(PyDictionary.TYPE)) {
         PyDictionary oDict = (PyDictionary)other;
         Iterator var7 = selfKeys.iterator();

         Object jkey;
         do {
            if (!var7.hasNext()) {
               return Py.True;
            }

            jkey = var7.next();
         } while(oDict.__contains__(Py.java2py(jkey)));

         return Py.False;
      } else {
         Object oj = other.getJavaProxy();
         if (oj instanceof Map) {
            Map oMap = (Map)oj;
            return Py.newBoolean(oMap.keySet().containsAll(selfKeys));
         } else {
            return null;
         }
      }
   }

   static PyBuiltinMethod[] getProxyMethods() {
      return new PyBuiltinMethod[]{mapLenProxy, mapIterProxy, mapReprProxy, mapEqProxy, mapLeProxy, mapLtProxy, mapGeProxy, mapGtProxy, mapContainsProxy, mapGetItemProxy, mapPutProxy, mapRemoveProxy, mapIterItemsProxy, mapIterKeysProxy, mapIterValuesProxy, mapHasKeyProxy, mapKeysProxy, mapSetDefaultProxy, mapPopProxy, mapPopItemProxy, mapItemsProxy, mapCopyProxy, mapUpdateProxy, mapFromKeysProxy};
   }

   static PyBuiltinMethod[] getPostProxyMethods() {
      return new PyBuiltinMethod[]{mapGetProxy, mapValuesProxy};
   }

   @Untraversable
   private static class MapClassMethod extends PyBuiltinClassMethodNarrow {
      protected MapClassMethod(String name, int minArgs, int maxArgs) {
         super(name, minArgs, maxArgs);
      }

      protected Class asClass() {
         return (Class)this.self.getJavaProxy();
      }
   }

   @Untraversable
   private static class MapMethod extends PyBuiltinMethodNarrow {
      protected MapMethod(String name, int numArgs) {
         super(name, numArgs);
      }

      protected MapMethod(String name, int minArgs, int maxArgs) {
         super(name, minArgs, maxArgs);
      }

      protected Map asMap() {
         return (Map)this.self.getJavaProxy();
      }
   }
}
