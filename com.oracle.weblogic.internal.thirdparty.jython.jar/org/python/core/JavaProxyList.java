package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.python.util.Generic;

class JavaProxyList {
   private static final PyBuiltinMethodNarrow listGetProxy = new ListMethod("__getitem__", 1) {
      public PyObject __call__(PyObject key) {
         return (new ListIndexDelegate(this.asList())).checkIdxAndGetItem(key);
      }
   };
   private static final PyBuiltinMethodNarrow listSetProxy = new ListMethod("__setitem__", 2) {
      public PyObject __call__(PyObject key, PyObject value) {
         (new ListIndexDelegate(this.asList())).checkIdxAndSetItem(key, value);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listRemoveProxy = new ListMethod("__delitem__", 1) {
      public PyObject __call__(PyObject key) {
         (new ListIndexDelegate(this.asList())).checkIdxAndDelItem(key);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listEqProxy = new ListMethod("__eq__", 1) {
      public PyObject __call__(PyObject other) {
         List jList = this.asList();
         if (other.getType().isSubType(PyList.TYPE)) {
            PyList oList = (PyList)other;
            if (jList.size() != oList.size()) {
               return Py.False;
            } else {
               for(int i = 0; i < jList.size(); ++i) {
                  if (!Py.java2py(jList.get(i))._eq(oList.pyget(i)).__nonzero__()) {
                     return Py.False;
                  }
               }

               return Py.True;
            }
         } else {
            Object oj = other.getJavaProxy();
            if (oj instanceof List) {
               List oListx = (List)oj;
               if (jList.size() != oListx.size()) {
                  return Py.False;
               } else {
                  for(int ix = 0; ix < jList.size(); ++ix) {
                     if (!Py.java2py(jList.get(ix))._eq(Py.java2py(oListx.get(ix))).__nonzero__()) {
                        return Py.False;
                     }
                  }

                  return Py.True;
               }
            } else {
               return null;
            }
         }
      }
   };
   private static final PyBuiltinMethodNarrow listAppendProxy = new ListMethod("append", 1) {
      public PyObject __call__(PyObject value) {
         this.asList().add(value);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listExtendProxy = new ListMethod("extend", 1) {
      public PyObject __call__(PyObject obj) {
         List jList = this.asList();
         List extension = new ArrayList();
         Iterator var4 = obj.asIterable().iterator();

         while(var4.hasNext()) {
            PyObject item = (PyObject)var4.next();
            extension.add(item);
         }

         jList.addAll(extension);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listInsertProxy = new ListMethod("insert", 2) {
      public PyObject __call__(PyObject index, PyObject object) {
         List jlist = this.asList();
         ListIndexDelegate lid = new ListIndexDelegate(jlist);
         int idx = lid.fixBoundIndex(index);
         jlist.add(idx, object);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listPopProxy = new ListMethod("pop", 0, 1) {
      public PyObject __call__() {
         return this.__call__(Py.newInteger(-1));
      }

      public PyObject __call__(PyObject index) {
         List jlist = this.asList();
         if (jlist.isEmpty()) {
            throw Py.IndexError("pop from empty list");
         } else {
            ListIndexDelegate ldel = new ListIndexDelegate(jlist);
            PyObject item = ldel.checkIdxAndFindItem(index.asInt());
            if (item == null) {
               throw Py.IndexError("pop index out of range");
            } else {
               ldel.checkIdxAndDelItem(index);
               return item;
            }
         }
      }
   };
   private static final PyBuiltinMethodNarrow listIndexProxy = new ListMethod("index", 1, 3) {
      public PyObject __call__(PyObject object) {
         return this.__call__(object, Py.newInteger(0), Py.newInteger(this.asList().size()));
      }

      public PyObject __call__(PyObject object, PyObject start) {
         return this.__call__(object, start, Py.newInteger(this.asList().size()));
      }

      public PyObject __call__(PyObject object, PyObject start, PyObject end) {
         List jlist = this.asList();
         ListIndexDelegate lid = new ListIndexDelegate(jlist);
         int start_index = lid.fixBoundIndex(start);
         int end_index = lid.fixBoundIndex(end);
         int i = start_index;

         try {
            for(ListIterator it = jlist.listIterator(start_index); it.hasNext() && i != end_index; ++i) {
               Object jobj = it.next();
               if (Py.java2py(jobj)._eq(object).__nonzero__()) {
                  return Py.newInteger(i);
               }
            }
         } catch (ConcurrentModificationException var11) {
            throw Py.ValueError(object.toString() + " is not in list");
         }

         throw Py.ValueError(object.toString() + " is not in list");
      }
   };
   private static final PyBuiltinMethodNarrow listCountProxy = new ListMethod("count", 1) {
      public PyObject __call__(PyObject object) {
         int count = 0;
         List jlist = this.asList();

         for(int i = 0; i < jlist.size(); ++i) {
            Object jobj = jlist.get(i);
            if (Py.java2py(jobj)._eq(object).__nonzero__()) {
               ++count;
            }
         }

         return Py.newInteger(count);
      }
   };
   private static final PyBuiltinMethodNarrow listReverseProxy = new ListMethod("reverse", 0) {
      public PyObject __call__() {
         List jlist = this.asList();
         Collections.reverse(jlist);
         return Py.None;
      }
   };
   private static final PyBuiltinMethodNarrow listRemoveOverrideProxy = new ListMethod("remove", 1) {
      public PyObject __call__(PyObject object) {
         List jlist = this.asList();

         for(int i = 0; i < jlist.size(); ++i) {
            Object jobj = jlist.get(i);
            if (Py.java2py(jobj)._eq(object).__nonzero__()) {
               jlist.remove(i);
               return Py.None;
            }
         }

         if (object.isIndex()) {
            ListIndexDelegate ldel = new ListIndexDelegate(jlist);
            ldel.checkIdxAndDelItem(object);
            return Py.None;
         } else {
            throw Py.ValueError(object.toString() + " is not in list");
         }
      }
   };
   private static final PyBuiltinMethodNarrow listRAddProxy = new ListMethod("__radd__", 1) {
      public PyObject __call__(PyObject obj) {
         List jList = this.asList();

         List jClone;
         try {
            jClone = (List)jList.getClass().newInstance();
         } catch (IllegalAccessException var7) {
            throw Py.JavaError(var7);
         } catch (InstantiationException var8) {
            throw Py.JavaError(var8);
         }

         Iterator var4 = jList.iterator();

         while(var4.hasNext()) {
            Object entry = var4.next();
            jClone.add(entry);
         }

         if (obj instanceof Collection) {
            jClone.addAll(0, (Collection)obj);
         } else {
            int i = 0;

            for(Iterator var10 = obj.asIterable().iterator(); var10.hasNext(); ++i) {
               PyObject item = (PyObject)var10.next();
               jClone.add(i, item);
            }
         }

         return Py.java2py(jClone);
      }
   };
   private static final PyBuiltinMethodNarrow listIAddProxy = new ListMethod("__iadd__", 1) {
      public PyObject __call__(PyObject obj) {
         List jList = this.asList();
         if (obj instanceof Collection) {
            jList.addAll((Collection)obj);
         } else {
            Iterator var3 = obj.asIterable().iterator();

            while(var3.hasNext()) {
               PyObject item = (PyObject)var3.next();
               jList.add(item);
            }
         }

         return this.self;
      }
   };
   private static final PyBuiltinMethodNarrow listIMulProxy = new ListMethod("__imul__", 1) {
      public PyObject __call__(PyObject obj) {
         List jList = this.asList();
         int mult = obj.asInt();
         if (mult <= 0) {
            jList.clear();
         } else {
            try {
               if (jList instanceof ArrayList) {
                  ((ArrayList)jList).ensureCapacity(jList.size() * (mult - 1));
               }

               int originalSize = jList.size();
               --mult;

               while(mult > 0) {
                  for(int i = 0; i < originalSize; ++i) {
                     jList.add(jList.get(i));
                  }

                  --mult;
               }
            } catch (OutOfMemoryError var6) {
               throw Py.MemoryError("");
            }
         }

         return this.self;
      }
   };
   private static final PyBuiltinMethodNarrow listSortProxy = new ListMethod("sort", 0, 3) {
      public PyObject __call__() {
         JavaProxyList.list_sort(this.asList(), Py.None, Py.None, false);
         return Py.None;
      }

      public PyObject __call__(PyObject cmp) {
         JavaProxyList.list_sort(this.asList(), cmp, Py.None, false);
         return Py.None;
      }

      public PyObject __call__(PyObject cmp, PyObject key) {
         JavaProxyList.list_sort(this.asList(), cmp, key, false);
         return Py.None;
      }

      public PyObject __call__(PyObject cmp, PyObject key, PyObject reverse) {
         JavaProxyList.list_sort(this.asList(), cmp, key, reverse.__nonzero__());
         return Py.None;
      }

      public PyObject __call__(PyObject[] args, String[] kwds) {
         ArgParser ap = new ArgParser("list", args, kwds, new String[]{"cmp", "key", "reverse"}, 0);
         PyObject cmp = ap.getPyObject(0, Py.None);
         PyObject key = ap.getPyObject(1, Py.None);
         PyObject reverse = ap.getPyObject(2, Py.False);
         JavaProxyList.list_sort(this.asList(), cmp, key, reverse.__nonzero__());
         return Py.None;
      }
   };

   private static synchronized void list_sort(List list, PyObject cmp, PyObject key, boolean reverse) {
      int size = list.size();
      ArrayList decorated = new ArrayList(size);
      Iterator var6 = list.iterator();

      while(true) {
         while(var6.hasNext()) {
            Object value = var6.next();
            PyObject pyvalue = Py.java2py(value);
            if (key != null && key != Py.None) {
               decorated.add(new KV(key.__call__(pyvalue), value));
            } else {
               decorated.add(new KV(pyvalue, value));
            }
         }

         list.clear();
         KVComparator c = new KVComparator(cmp);
         if (reverse) {
            Collections.reverse(decorated);
         }

         Collections.sort(decorated, c);
         if (reverse) {
            Collections.reverse(decorated);
         }

         boolean modified = list.size() > 0;
         Iterator var12 = decorated.iterator();

         while(var12.hasNext()) {
            KV kv = (KV)var12.next();
            list.add(kv.value);
         }

         if (modified) {
            throw Py.ValueError("list modified during sort");
         }

         return;
      }
   }

   static PyBuiltinMethod[] getProxyMethods() {
      return new PyBuiltinMethod[]{listGetProxy, listSetProxy, listEqProxy, listRemoveProxy, listAppendProxy, listExtendProxy, listInsertProxy, listPopProxy, listIndexProxy, listCountProxy, listReverseProxy, listRAddProxy, listIAddProxy, new ListMulProxyClass("__mul__", 1), new ListMulProxyClass("__rmul__", 1), listIMulProxy};
   }

   static PyBuiltinMethod[] getPostProxyMethods() {
      return new PyBuiltinMethod[]{listSortProxy, listRemoveOverrideProxy};
   }

   private static class KVComparator implements Comparator {
      private final PyObject cmp;

      KVComparator(PyObject cmp) {
         this.cmp = cmp;
      }

      public int compare(KV o1, KV o2) {
         if (this.cmp != null && this.cmp != Py.None) {
            PyObject pyresult = this.cmp.__call__(o1.key, o2.key);
            if (!(pyresult instanceof PyInteger) && !(pyresult instanceof PyLong)) {
               throw Py.TypeError(String.format("comparison function must return int, not %.200s", pyresult.getType().fastGetName()));
            } else {
               return pyresult.asInt();
            }
         } else {
            int result = o1.key._cmp(o2.key);
            return result;
         }
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return o instanceof KVComparator ? this.cmp.equals(((KVComparator)o).cmp) : false;
         }
      }
   }

   private static class KV {
      private final PyObject key;
      private final Object value;

      KV(PyObject key, Object value) {
         this.key = key;
         this.value = value;
      }
   }

   @Untraversable
   private static class ListMulProxyClass extends ListMethod {
      protected ListMulProxyClass(String name, int numArgs) {
         super(name, numArgs);
      }

      public PyObject __call__(PyObject obj) {
         List jList = this.asList();
         int mult = obj.asInt();
         List newList = null;
         if (mult > 0) {
            try {
               for(newList = new ArrayList(jList.size() * mult); mult > 0; --mult) {
                  Iterator var5 = jList.iterator();

                  while(var5.hasNext()) {
                     Object entry = var5.next();
                     ((List)newList).add(entry);
                  }
               }
            } catch (OutOfMemoryError var7) {
               throw Py.MemoryError("");
            }
         } else {
            newList = Collections.EMPTY_LIST;
         }

         return Py.java2py(newList);
      }
   }

   protected static class ListIndexDelegate extends SequenceIndexDelegate {
      private final List list;

      public ListIndexDelegate(List list) {
         this.list = list;
      }

      public void delItem(int idx) {
         this.list.remove(idx);
      }

      public PyObject getItem(int idx) {
         return Py.java2py(this.list.get(idx));
      }

      public PyObject getSlice(int start, int stop, int step) {
         if (step > 0 && stop < start) {
            stop = start;
         }

         int n = PySequence.sliceLength(start, stop, (long)step);

         List newList;
         try {
            newList = (List)this.list.getClass().newInstance();
         } catch (Exception var8) {
            throw Py.JavaError(var8);
         }

         int j = 0;

         for(int i = start; j < n; i += step) {
            newList.add(this.list.get(i));
            ++j;
         }

         return Py.java2py(newList);
      }

      public String getTypeName() {
         return this.list.getClass().getName();
      }

      public int len() {
         return this.list.size();
      }

      protected int fixBoundIndex(PyObject index) {
         PyInteger length = Py.newInteger(this.len());
         if (((PyObject)index)._lt(Py.Zero).__nonzero__()) {
            index = ((PyObject)index)._add(length);
            if (((PyObject)index)._lt(Py.Zero).__nonzero__()) {
               index = Py.Zero;
            }
         } else if (((PyObject)index)._gt(length).__nonzero__()) {
            index = length;
         }

         int i = ((PyObject)index).asIndex();

         assert i >= 0;

         return i;
      }

      public void setItem(int idx, PyObject value) {
         this.list.set(idx, value.__tojava__(Object.class));
      }

      public void setSlice(int start, int stop, int step, PyObject value) {
         if (stop < start) {
            stop = start;
         }

         if (JyAttribute.getAttr(value, (byte)-128) == this.list) {
            List xs = Generic.list();
            xs.addAll(this.list);
            this.setsliceList(start, stop, step, xs);
         } else if (value instanceof PyList) {
            this.setslicePyList(start, stop, step, (PyList)value);
         } else {
            Object valueList = value.__tojava__(List.class);
            if (valueList != null && valueList != Py.NoConversion) {
               this.setsliceList(start, stop, step, (List)valueList);
            } else {
               this.setsliceIterator(start, stop, step, value.asIterable().iterator());
            }
         }

      }

      private final void setsliceList(int start, int stop, int step, List value) {
         if (step == 1) {
            this.list.subList(start, stop).clear();
            this.list.addAll(start, value);
         } else {
            int size = this.list.size();
            Iterator iter = value.listIterator();

            for(int j = start; iter.hasNext(); j += step) {
               Object item = iter.next();
               if (j >= size) {
                  this.list.add(item);
               } else {
                  this.list.set(j, item);
               }
            }
         }

      }

      private final void setsliceIterator(int start, int stop, int step, Iterator iter) {
         if (step == 1) {
            List insertion = new ArrayList();
            if (iter != null) {
               while(iter.hasNext()) {
                  insertion.add(((PyObject)iter.next()).__tojava__(Object.class));
               }
            }

            this.list.subList(start, stop).clear();
            this.list.addAll(start, insertion);
         } else {
            int size = this.list.size();

            for(int j = start; iter.hasNext(); j += step) {
               Object item = ((PyObject)iter.next()).__tojava__(Object.class);
               if (j >= size) {
                  this.list.add(item);
               } else {
                  this.list.set(j, item);
               }
            }
         }

      }

      private final void setslicePyList(int start, int stop, int step, PyList value) {
         int size;
         int j;
         Object item;
         if (step == 1) {
            this.list.subList(start, stop).clear();
            size = value.getList().size();
            int i = 0;

            for(j = start; i < size; ++j) {
               item = ((PyObject)value.getList().get(i)).__tojava__(Object.class);
               this.list.add(j, item);
               ++i;
            }
         } else {
            size = this.list.size();
            Iterator iter = value.getList().listIterator();

            for(j = start; iter.hasNext(); j += step) {
               item = ((PyObject)iter.next()).__tojava__(Object.class);
               if (j >= size) {
                  this.list.add(item);
               } else {
                  this.list.set(j, item);
               }
            }
         }

      }

      public void delItems(int start, int stop) {
         int n = stop - start;

         while(n-- > 0) {
            this.delItem(start);
         }

      }
   }

   @Untraversable
   private static class ListMethod extends PyBuiltinMethodNarrow {
      protected ListMethod(String name, int numArgs) {
         super(name, numArgs);
      }

      protected ListMethod(String name, int minArgs, int maxArgs) {
         super(name, minArgs, maxArgs);
      }

      protected List asList() {
         return (List)this.self.getJavaProxy();
      }

      protected List newList() {
         try {
            return (List)this.asList().getClass().newInstance();
         } catch (IllegalAccessException var2) {
            throw Py.JavaError(var2);
         } catch (InstantiationException var3) {
            throw Py.JavaError(var3);
         }
      }
   }
}
