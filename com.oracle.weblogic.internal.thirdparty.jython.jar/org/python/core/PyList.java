package org.python.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@ExposedType(
   name = "list",
   base = PyObject.class,
   doc = "list() -> new empty list\nlist(iterable) -> new list initialized from iterable's items"
)
public class PyList extends PySequenceList {
   public static final PyType TYPE;
   private final List list;
   public volatile int gListAllocatedStatus;

   public PyList() {
      this(TYPE);
   }

   public PyList(PyType type) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.gListAllocatedStatus = -1;
      this.list = Generic.list();
   }

   private PyList(List list, boolean convert) {
      super(TYPE);
      TYPE.object___setattr__("__hash__", Py.None);
      this.gListAllocatedStatus = -1;
      if (!convert) {
         this.list = list;
      } else {
         this.list = Generic.list();
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            this.add(o);
         }
      }

   }

   public PyList(PyType type, PyObject[] elements) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.gListAllocatedStatus = -1;
      this.list = new ArrayList(Arrays.asList(elements));
   }

   public PyList(PyType type, Collection c) {
      super(type);
      TYPE.object___setattr__("__hash__", Py.None);
      this.gListAllocatedStatus = -1;
      this.list = new ArrayList(c.size());
      Iterator var3 = c.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         this.add(o);
      }

   }

   public PyList(PyObject[] elements) {
      this(TYPE, elements);
   }

   public PyList(Collection c) {
      this(TYPE, c);
   }

   public PyList(PyObject o) {
      this(TYPE);
      Iterator var2 = o.asIterable().iterator();

      while(var2.hasNext()) {
         PyObject item = (PyObject)var2.next();
         this.list.add(item);
      }

   }

   public static PyList fromList(List list) {
      return new PyList(list, false);
   }

   List getList() {
      return Collections.unmodifiableList(this.list);
   }

   private static List listify(Iterator iter) {
      List list = Generic.list();

      while(iter.hasNext()) {
         list.add(iter.next());
      }

      return list;
   }

   public PyList(Iterator iter) {
      this(TYPE, (Collection)listify(iter));
   }

   private static void addCollection(List list, Collection seq) {
      Map seen = new HashMap();

      PyObject seen_obj;
      for(Iterator var3 = seq.iterator(); var3.hasNext(); list.add(seen_obj)) {
         Object item = var3.next();
         long id = Py.java_obj_id(item);
         seen_obj = (PyObject)seen.get(id);
         if (seen_obj != null) {
            seen_obj = Py.java2py(item);
            seen.put(id, seen_obj);
         }
      }

   }

   @ExposedNew
   final void list___init__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("list", args, kwds, new String[]{"sequence"}, 0);
      PyObject seq = ap.getPyObject(0, (PyObject)null);
      this.clear();
      if (seq != null) {
         Iterator var5;
         PyObject item;
         if (seq instanceof PyListDerived) {
            var5 = seq.asIterable().iterator();

            while(var5.hasNext()) {
               item = (PyObject)var5.next();
               this.append(item);
            }
         } else if (seq instanceof PyList) {
            this.list.addAll(((PyList)seq).list);
         } else if (seq instanceof PyTuple) {
            this.list.addAll(((PyTuple)seq).getList());
         } else if (seq.getClass().isAssignableFrom(Collection.class)) {
            System.err.println("Adding from collection");
            addCollection(this.list, (Collection)seq);
         } else {
            var5 = seq.asIterable().iterator();

            while(var5.hasNext()) {
               item = (PyObject)var5.next();
               this.append(item);
            }
         }

      }
   }

   public int __len__() {
      return this.list___len__();
   }

   final synchronized int list___len__() {
      return this.size();
   }

   protected void del(int i) {
      this.remove(i);
   }

   protected void delRange(int start, int stop) {
      this.remove(start, stop);
   }

   protected void setslice(int start, int stop, int step, PyObject value) {
      if (stop < start) {
         stop = start;
      }

      if (value instanceof PyList) {
         if (value == this) {
            value = new PyList((PySequence)value);
         }

         this.setslicePyList(start, stop, step, (PyList)value);
      } else if (value instanceof PySequence) {
         this.setsliceIterator(start, stop, step, ((PyObject)value).asIterable().iterator());
      } else if (value instanceof List) {
         this.setsliceList(start, stop, step, (List)value);
      } else {
         Object valueList = ((PyObject)value).__tojava__(List.class);
         if (valueList != null && valueList != Py.NoConversion) {
            this.setsliceList(start, stop, step, (List)valueList);
         } else {
            PyObject value = new PyList((PyObject)value);
            this.setsliceIterator(start, stop, step, value.asIterable().iterator());
         }
      }

   }

   private final void setsliceList(int start, int stop, int step, List value) {
      int size;
      int j;
      if (step == 1) {
         this.list.subList(start, stop).clear();
         size = value.size();
         int i = 0;

         for(j = start; i < size; ++j) {
            this.list.add(j, Py.java2py(value.get(i)));
            ++i;
         }
      } else {
         size = this.list.size();
         Iterator iter = value.listIterator();

         for(j = start; iter.hasNext(); j += step) {
            PyObject item = Py.java2py(iter.next());
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
               insertion.add(iter.next());
            }
         }

         this.list.subList(start, stop).clear();
         this.list.addAll(start, insertion);
      } else {
         int size = this.list.size();

         for(int j = start; iter.hasNext(); j += step) {
            PyObject item = (PyObject)iter.next();
            if (j >= size) {
               this.list.add(item);
            } else {
               this.list.set(j, item);
            }
         }
      }

   }

   private final void setslicePyList(int start, int stop, int step, PyList other) {
      if (step == 1) {
         this.list.subList(start, stop).clear();
         this.list.addAll(start, other.list);
      } else {
         int size = this.list.size();
         Iterator iter = other.list.listIterator();

         for(int j = start; iter.hasNext(); j += step) {
            PyObject item = (PyObject)iter.next();
            if (j >= size) {
               this.list.add(item);
            } else {
               this.list.set(j, item);
            }
         }
      }

   }

   protected synchronized PyObject repeat(int count) {
      if (count < 0) {
         count = 0;
      }

      int size = this.size();
      int newSize = size * count;
      if (count != 0 && newSize / count != size) {
         throw Py.MemoryError("");
      } else {
         PyObject[] elements = (PyObject[])this.list.toArray(new PyObject[size]);
         PyObject[] newList = new PyObject[newSize];

         for(int i = 0; i < count; ++i) {
            System.arraycopy(elements, 0, newList, i * size, size);
         }

         return new PyList(newList);
      }
   }

   final synchronized PyObject list___ne__(PyObject o) {
      return this.seq___ne__(o);
   }

   final synchronized PyObject list___eq__(PyObject o) {
      return this.seq___eq__(o);
   }

   final synchronized PyObject list___lt__(PyObject o) {
      return this.seq___lt__(o);
   }

   final synchronized PyObject list___le__(PyObject o) {
      return this.seq___le__(o);
   }

   final synchronized PyObject list___gt__(PyObject o) {
      return this.seq___gt__(o);
   }

   final synchronized PyObject list___ge__(PyObject o) {
      return this.seq___ge__(o);
   }

   public PyObject __imul__(PyObject o) {
      return this.list___imul__(o);
   }

   final synchronized PyObject list___imul__(PyObject o) {
      if (!o.isIndex()) {
         return null;
      } else {
         int count = o.asIndex(Py.OverflowError);
         int size = this.size();
         if (size != 0 && count != 1) {
            if (count < 1) {
               this.clear();
               return this;
            } else if (size > Integer.MAX_VALUE / count) {
               throw Py.MemoryError("");
            } else {
               int newSize = size * count;
               if (this.list instanceof ArrayList) {
                  ((ArrayList)this.list).ensureCapacity(newSize);
               }

               List oldList = new ArrayList(this.list);

               for(int i = 1; i < count; ++i) {
                  this.list.addAll(oldList);
               }

               this.gListAllocatedStatus = this.list.size();
               return this;
            }
         } else {
            return this;
         }
      }
   }

   public PyObject __mul__(PyObject o) {
      return this.list___mul__(o);
   }

   final synchronized PyObject list___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.list___rmul__(o);
   }

   final synchronized PyObject list___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __add__(PyObject o) {
      return this.list___add__(o);
   }

   final synchronized PyObject list___add__(PyObject o) {
      PyList sum = null;
      if (o instanceof PySequenceList && !(o instanceof PyTuple)) {
         if (o instanceof PyList) {
            List oList = ((PyList)o).list;
            ArrayList newList = new ArrayList(this.list.size() + oList.size());
            newList.addAll(this.list);
            newList.addAll(oList);
            sum = fromList(newList);
         }
      } else if (!(o instanceof PySequenceList)) {
         Object oList = o.__tojava__(List.class);
         if (oList != Py.NoConversion && oList != null) {
            List otherList = (List)oList;
            sum = new PyList();
            sum.list_extend(this);
            Iterator var5 = otherList.iterator();

            while(var5.hasNext()) {
               PyObject ob = (PyObject)var5.next();
               sum.add(ob);
            }
         }
      }

      return sum;
   }

   public PyObject __radd__(PyObject o) {
      return this.list___radd__(o);
   }

   final synchronized PyObject list___radd__(PyObject o) {
      PyList sum = null;
      if (o instanceof PySequence) {
         return null;
      } else {
         Object oList = o.__tojava__(List.class);
         if (oList != Py.NoConversion && oList != null) {
            sum = new PyList();
            sum.addAll((List)oList);
            sum.extend(this);
         }

         return sum;
      }
   }

   final synchronized boolean list___contains__(PyObject o) {
      return this.object___contains__(o);
   }

   final synchronized void list___delitem__(PyObject index) {
      this.seq___delitem__(index);
   }

   final synchronized void list___setitem__(PyObject o, PyObject def) {
      this.seq___setitem__(o, def);
   }

   final synchronized PyObject list___getitem__(PyObject o) {
      PyObject ret = this.seq___finditem__(o);
      if (ret == null) {
         throw Py.IndexError("index out of range: " + o);
      } else {
         return ret;
      }
   }

   public PyObject __iter__() {
      return this.list___iter__();
   }

   final PyObject list___iter__() {
      return new PyListIterator(this);
   }

   public PyIterator __reversed__() {
      return this.list___reversed__();
   }

   final synchronized PyIterator list___reversed__() {
      return new PyReversedIterator(this);
   }

   final synchronized PyObject list___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   final synchronized void list___setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      this.seq___setslice__(start, stop, step, value);
   }

   final synchronized void list___delslice__(PyObject start, PyObject stop, PyObject step) {
      this.seq___delslice__(start, stop, step);
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return op.equals("+") ? "can only concatenate list (not \"{2}\") to list" : super.unsupportedopMessage(op, o2);
   }

   public String toString() {
      return this.list_toString();
   }

   final synchronized String list_toString() {
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return "[...]";
      } else {
         StringBuilder buf = new StringBuilder("[");
         int length = this.size();
         int i = 0;

         for(Iterator var5 = this.list.iterator(); var5.hasNext(); ++i) {
            PyObject item = (PyObject)var5.next();
            buf.append(item.__repr__().toString());
            if (i < length - 1) {
               buf.append(", ");
            }
         }

         buf.append("]");
         ts.exitRepr(this);
         return buf.toString();
      }
   }

   public void append(PyObject o) {
      this.list_append(o);
   }

   final synchronized void list_append(PyObject o) {
      this.pyadd(o);
      this.gListAllocatedStatus = this.list.size();
   }

   public int count(PyObject o) {
      return this.list_count(o);
   }

   final synchronized int list_count(PyObject o) {
      int count = 0;
      Iterator var3 = this.list.iterator();

      while(var3.hasNext()) {
         PyObject item = (PyObject)var3.next();
         if (item.equals(o)) {
            ++count;
         }
      }

      return count;
   }

   public int index(PyObject o) {
      return this.index(o, 0);
   }

   public int index(PyObject o, int start) {
      return this.list_index(o, start, this.size());
   }

   public int index(PyObject o, int start, int stop) {
      return this.list_index(o, start, stop);
   }

   final synchronized int list_index(PyObject o, PyObject start, PyObject stop) {
      int startInt = start == null ? 0 : PySlice.calculateSliceIndex(start);
      int stopInt = stop == null ? this.size() : PySlice.calculateSliceIndex(stop);
      return this.list_index(o, startInt, stopInt);
   }

   final synchronized int list_index(PyObject o, int start, int stop) {
      return this._index(o, "list.index(x): x not in list", start, stop);
   }

   final synchronized int list_index(PyObject o, int start) {
      return this._index(o, "list.index(x): x not in list", start, this.size());
   }

   final synchronized int list_index(PyObject o) {
      return this._index(o, "list.index(x): x not in list", 0, this.size());
   }

   private int _index(PyObject o, String message, int start, int stop) {
      int validStop = this.boundToSequence(stop);
      int validStart = this.boundToSequence(start);
      int i = validStart;
      if (validStart <= validStop) {
         try {
            for(Iterator var8 = this.list.subList(validStart, validStop).iterator(); var8.hasNext(); ++i) {
               PyObject item = (PyObject)var8.next();
               if (item.equals(o)) {
                  return i;
               }
            }
         } catch (ConcurrentModificationException var10) {
            throw Py.ValueError(message);
         }
      }

      throw Py.ValueError(message);
   }

   public void insert(int index, PyObject o) {
      this.list_insert(index, o);
   }

   final synchronized void list_insert(int index, PyObject o) {
      if (index < 0) {
         index = Math.max(0, this.size() + index);
      }

      if (index > this.size()) {
         index = this.size();
      }

      this.pyadd(index, o);
      this.gListAllocatedStatus = this.list.size();
   }

   public void remove(PyObject o) {
      this.list_remove(o);
   }

   final synchronized void list_remove(PyObject o) {
      this.del(this._index(o, "list.remove(x): x not in list", 0, this.size()));
      this.gListAllocatedStatus = this.list.size();
   }

   public void reverse() {
      this.list_reverse();
   }

   final synchronized void list_reverse() {
      Collections.reverse(this.list);
      this.gListAllocatedStatus = this.list.size();
   }

   public PyObject pop() {
      return this.pop(-1);
   }

   public PyObject pop(int n) {
      return this.list_pop(n);
   }

   final synchronized PyObject list_pop(int n) {
      int length = this.size();
      if (length == 0) {
         throw Py.IndexError("pop from empty list");
      } else {
         if (n < 0) {
            n += length;
         }

         if (n >= 0 && n < length) {
            PyObject v = (PyObject)this.list.remove(n);
            return v;
         } else {
            throw Py.IndexError("pop index out of range");
         }
      }
   }

   public void extend(PyObject o) {
      this.list_extend(o);
   }

   final synchronized void list_extend(PyObject o) {
      if (o instanceof PyList) {
         this.list.addAll(((PyList)o).list);
      } else {
         Iterator var2 = o.asIterable().iterator();

         while(var2.hasNext()) {
            PyObject item = (PyObject)var2.next();
            this.list.add(item);
         }
      }

      this.gListAllocatedStatus = this.list.size();
   }

   public PyObject __iadd__(PyObject o) {
      return this.list___iadd__(o);
   }

   final synchronized PyObject list___iadd__(PyObject o) {
      PyType oType = o.getType();
      if (oType != TYPE && oType != PyTuple.TYPE && this != o) {
         PyObject it;
         try {
            it = o.__iter__();
         } catch (PyException var5) {
            if (!var5.match(Py.TypeError)) {
               throw var5;
            }

            return null;
         }

         this.extend(it);
         return this;
      } else {
         this.extend(fastSequence(o, "argument must be iterable"));
         return this;
      }
   }

   final synchronized void list_sort(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("list", args, kwds, new String[]{"cmp", "key", "reverse"}, 0);
      PyObject cmp = ap.getPyObject(0, Py.None);
      PyObject key = ap.getPyObject(1, Py.None);
      PyObject reverse = ap.getPyObject(2, Py.False);
      this.sort(cmp, key, reverse);
   }

   public void sort(PyObject cmp, PyObject key, PyObject reverse) {
      boolean bReverse = reverse.__nonzero__();
      if (key != Py.None && key != null) {
         this.sort(cmp, key, bReverse);
      } else if (cmp != Py.None && cmp != null) {
         this.sort(cmp, bReverse);
      } else {
         this.sort(bReverse);
      }

   }

   public void sort() {
      this.sort(false);
   }

   private synchronized void sort(boolean reverse) {
      this.gListAllocatedStatus = -1;
      if (reverse) {
         Collections.reverse(this.list);
      }

      PyObjectDefaultComparator comparator = new PyObjectDefaultComparator(this);
      Collections.sort(this.list, comparator);
      if (comparator.raisedException()) {
         throw comparator.getRaisedException();
      } else {
         if (reverse) {
            Collections.reverse(this.list);
         }

         this.gListAllocatedStatus = this.list.size();
      }
   }

   public void sort(PyObject compare) {
      this.sort(compare, false);
   }

   private synchronized void sort(PyObject compare, boolean reverse) {
      this.gListAllocatedStatus = -1;
      if (reverse) {
         Collections.reverse(this.list);
      }

      PyObjectComparator comparator = new PyObjectComparator(this, compare);
      Collections.sort(this.list, comparator);
      if (comparator.raisedException()) {
         throw comparator.getRaisedException();
      } else {
         if (reverse) {
            Collections.reverse(this.list);
         }

         this.gListAllocatedStatus = this.list.size();
      }
   }

   private synchronized void sort(PyObject cmp, PyObject key, boolean reverse) {
      this.gListAllocatedStatus = -1;
      int size = this.list.size();
      ArrayList decorated = new ArrayList(size);
      Iterator var6 = this.list.iterator();

      while(var6.hasNext()) {
         PyObject value = (PyObject)var6.next();
         decorated.add(new KV(key.__call__(value), value));
      }

      this.list.clear();
      KVComparator c = new KVComparator(this, cmp);
      if (reverse) {
         Collections.reverse(decorated);
      }

      Collections.sort(decorated, c);
      if (reverse) {
         Collections.reverse(decorated);
      }

      if (this.list instanceof ArrayList) {
         ((ArrayList)this.list).ensureCapacity(size);
      }

      Iterator var10 = decorated.iterator();

      while(var10.hasNext()) {
         KV kv = (KV)var10.next();
         this.list.add(kv.value);
      }

      this.gListAllocatedStatus = this.list.size();
   }

   public int hashCode() {
      return this.list___hash__();
   }

   final synchronized int list___hash__() {
      throw Py.TypeError(String.format("unhashable type: '%.200s'", this.getType().fastGetName()));
   }

   public PyTuple __getnewargs__() {
      return new PyTuple(new PyObject[]{new PyTuple(this.getArray())});
   }

   public void add(int index, Object element) {
      this.pyadd(index, Py.java2py(element));
   }

   public boolean add(Object o) {
      this.pyadd(Py.java2py(o));
      return true;
   }

   public synchronized boolean addAll(int index, Collection c) {
      PyList elements = new PyList(c);
      return this.list.addAll(index, elements.list);
   }

   public boolean addAll(Collection c) {
      return this.addAll(0, c);
   }

   public synchronized void clear() {
      this.list.clear();
   }

   public synchronized boolean contains(Object o) {
      return this.list.contains(Py.java2py(o));
   }

   public synchronized boolean containsAll(Collection c) {
      if (c instanceof PyList) {
         return this.list.containsAll(((PyList)c).list);
      } else {
         return c instanceof PyTuple ? this.list.containsAll(((PyTuple)c).getList()) : this.list.containsAll(new PyList(c));
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof PyObject) {
         synchronized(this) {
            return this._eq((PyObject)other).__nonzero__();
         }
      } else if (other instanceof List) {
         synchronized(this) {
            return this.list.equals(other);
         }
      } else {
         return false;
      }
   }

   public synchronized Object get(int index) {
      return ((PyObject)this.list.get(index)).__tojava__(Object.class);
   }

   public synchronized PyObject[] getArray() {
      return (PyObject[])this.list.toArray(Py.EmptyObjects);
   }

   public synchronized int indexOf(Object o) {
      return this.list.indexOf(Py.java2py(o));
   }

   public synchronized boolean isEmpty() {
      return this.list.isEmpty();
   }

   public Iterator iterator() {
      return new Iterator() {
         private final Iterator iter;

         {
            this.iter = PyList.this.list.iterator();
         }

         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public Object next() {
            return ((PyObject)this.iter.next()).__tojava__(Object.class);
         }

         public void remove() {
            this.iter.remove();
         }
      };
   }

   public synchronized int lastIndexOf(Object o) {
      return this.list.lastIndexOf(Py.java2py(o));
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(final int index) {
      return new ListIterator() {
         private final ListIterator iter;

         {
            this.iter = PyList.this.list.listIterator(index);
         }

         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public Object next() {
            return ((PyObject)this.iter.next()).__tojava__(Object.class);
         }

         public boolean hasPrevious() {
            return this.iter.hasPrevious();
         }

         public Object previous() {
            return ((PyObject)this.iter.previous()).__tojava__(Object.class);
         }

         public int nextIndex() {
            return this.iter.nextIndex();
         }

         public int previousIndex() {
            return this.iter.previousIndex();
         }

         public void remove() {
            this.iter.remove();
         }

         public void set(Object o) {
            this.iter.set(Py.java2py(o));
         }

         public void add(Object o) {
            this.iter.add(Py.java2py(o));
         }
      };
   }

   public synchronized void pyadd(int index, PyObject element) {
      this.list.add(index, element);
   }

   public synchronized boolean pyadd(PyObject o) {
      this.list.add(o);
      return true;
   }

   public synchronized PyObject pyget(int index) {
      return (PyObject)this.list.get(index);
   }

   public synchronized void pyset(int index, PyObject element) {
      this.list.set(index, element);
   }

   public synchronized Object remove(int index) {
      return this.list.remove(index);
   }

   public synchronized void remove(int start, int stop) {
      this.list.subList(start, stop).clear();
   }

   public synchronized boolean removeAll(Collection c) {
      return c instanceof PySequenceList ? this.list.removeAll(c) : this.list.removeAll(new PyList(c));
   }

   public synchronized boolean retainAll(Collection c) {
      return c instanceof PySequenceList ? this.list.retainAll(c) : this.list.retainAll(new PyList(c));
   }

   public synchronized Object set(int index, Object element) {
      return ((PyObject)this.list.set(index, Py.java2py(element))).__tojava__(Object.class);
   }

   public synchronized int size() {
      return this.list.size();
   }

   public synchronized List subList(int fromIndex, int toIndex) {
      return fromList(this.list.subList(fromIndex, toIndex));
   }

   public synchronized Object[] toArray() {
      Object[] copy = this.list.toArray();

      for(int i = 0; i < copy.length; ++i) {
         copy[i] = ((PyObject)copy[i]).__tojava__(Object.class);
      }

      return copy;
   }

   public synchronized Object[] toArray(Object[] a) {
      int size = this.size();
      Class type = a.getClass().getComponentType();
      if (a.length < size) {
         a = (Object[])((Object[])Array.newInstance(type, size));
      }

      int i;
      for(i = 0; i < size; ++i) {
         a[i] = ((PyObject)this.list.get(i)).__tojava__(type);
      }

      if (a.length > size) {
         for(i = size; i < a.length; ++i) {
            a[i] = null;
         }
      }

      return a;
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      int n = sliceLength(start, stop, (long)step);
      ArrayList newList;
      if (step == 1) {
         newList = new ArrayList(this.list.subList(start, stop));
      } else {
         newList = new ArrayList(n);
         int i = start;

         for(int j = 0; j < n; ++j) {
            newList.add(this.list.get(i));
            i += step;
         }
      }

      return fromList(newList);
   }

   public synchronized boolean remove(Object o) {
      return this.list.remove(Py.java2py(o));
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.list != null) {
         Iterator var3 = this.list.iterator();

         while(var3.hasNext()) {
            PyObject ob = (PyObject)var3.next();
            if (ob != null) {
               int retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return this.list == null ? false : this.list.contains(ob);
   }

   static {
      PyType.addBuilder(PyList.class, new PyExposer());
      TYPE = PyType.fromClass(PyList.class);
   }

   private static class KVComparator implements Comparator {
      private final PyList list;
      private final PyObject cmp;

      KVComparator(PyList list, PyObject cmp) {
         this.list = list;
         this.cmp = cmp;
      }

      public int compare(KV o1, KV o2) {
         int result;
         if (this.cmp != null && this.cmp != Py.None) {
            result = this.cmp.__call__(o1.key, o2.key).asInt();
         } else if (o1.key._lt(o2.key).__nonzero__()) {
            result = -1;
         } else if (o2.key._lt(o1.key).__nonzero__()) {
            result = 1;
         } else {
            result = 0;
         }

         if (this.list.gListAllocatedStatus >= 0) {
            throw Py.ValueError("list modified during sort");
         } else {
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
      private final PyObject value;

      KV(PyObject key, PyObject value) {
         this.key = key;
         this.value = value;
      }
   }

   private static class PyObjectComparator implements Comparator {
      private final PyList list;
      private final PyObject cmp;
      private PyException comparatorException;

      PyObjectComparator(PyList list, PyObject cmp) {
         this.list = list;
         this.cmp = cmp;
      }

      public PyException getRaisedException() {
         return this.comparatorException;
      }

      public boolean raisedException() {
         return this.comparatorException != null;
      }

      public int compare(PyObject o1, PyObject o2) {
         int result = 0;

         try {
            result = this.cmp.__call__(o1, o2).asInt();
         } catch (PyException var5) {
            this.comparatorException = var5;
         }

         if (this.list.gListAllocatedStatus >= 0) {
            throw Py.ValueError("list modified during sort");
         } else {
            return result;
         }
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return o instanceof PyObjectComparator ? this.cmp.equals(((PyObjectComparator)o).cmp) : false;
         }
      }
   }

   private static class PyObjectDefaultComparator implements Comparator {
      private final PyList list;
      private PyException comparatorException;

      PyObjectDefaultComparator(PyList list) {
         this.list = list;
      }

      public PyException getRaisedException() {
         return this.comparatorException;
      }

      public boolean raisedException() {
         return this.comparatorException != null;
      }

      public int compare(PyObject o1, PyObject o2) {
         int result = 0;

         try {
            if (o1._lt(o2).__nonzero__()) {
               result = -1;
            } else if (o2._lt(o1).__nonzero__()) {
               result = 1;
            }
         } catch (PyException var5) {
            this.comparatorException = var5;
         }

         if (this.list.gListAllocatedStatus >= 0) {
            throw Py.ValueError("list modified during sort");
         } else {
            return result;
         }
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else {
            return o instanceof PyObjectDefaultComparator;
         }
      }
   }

   private static class list___init___exposer extends PyBuiltinMethod {
      public list___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public list___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyList)this.self).list___init__(var1, var2);
         return Py.None;
      }
   }

   private static class list___len___exposer extends PyBuiltinMethodNarrow {
      public list___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public list___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyList)this.self).list___len__());
      }
   }

   private static class list___ne___exposer extends PyBuiltinMethodNarrow {
      public list___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public list___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___eq___exposer extends PyBuiltinMethodNarrow {
      public list___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public list___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___lt___exposer extends PyBuiltinMethodNarrow {
      public list___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public list___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___le___exposer extends PyBuiltinMethodNarrow {
      public list___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public list___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___gt___exposer extends PyBuiltinMethodNarrow {
      public list___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public list___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___ge___exposer extends PyBuiltinMethodNarrow {
      public list___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public list___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___imul___exposer extends PyBuiltinMethodNarrow {
      public list___imul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__imul__(y) <==> x*=y";
      }

      public list___imul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__imul__(y) <==> x*=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___imul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___imul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___mul___exposer extends PyBuiltinMethodNarrow {
      public list___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public list___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___rmul___exposer extends PyBuiltinMethodNarrow {
      public list___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public list___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___add___exposer extends PyBuiltinMethodNarrow {
      public list___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public list___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___radd___exposer extends PyBuiltinMethodNarrow {
      public list___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public list___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list___contains___exposer extends PyBuiltinMethodNarrow {
      public list___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public list___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyList)this.self).list___contains__(var1));
      }
   }

   private static class list___delitem___exposer extends PyBuiltinMethodNarrow {
      public list___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public list___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delitem__(y) <==> del x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyList)this.self).list___delitem__(var1);
         return Py.None;
      }
   }

   private static class list___setitem___exposer extends PyBuiltinMethodNarrow {
      public list___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public list___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setitem__(i, y) <==> x[i]=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyList)this.self).list___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class list___getitem___exposer extends PyBuiltinMethodNarrow {
      public list___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public list___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyList)this.self).list___getitem__(var1);
      }
   }

   private static class list___iter___exposer extends PyBuiltinMethodNarrow {
      public list___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public list___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyList)this.self).list___iter__();
      }
   }

   private static class list___reversed___exposer extends PyBuiltinMethodNarrow {
      public list___reversed___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "L.__reversed__() -- return a reverse iterator over the list";
      }

      public list___reversed___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.__reversed__() -- return a reverse iterator over the list";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___reversed___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyList)this.self).list___reversed__();
      }
   }

   private static class list___getslice___exposer extends PyBuiltinMethodNarrow {
      public list___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public list___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyList)this.self).list___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyList)this.self).list___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class list___setslice___exposer extends PyBuiltinMethodNarrow {
      public list___setslice___exposer(String var1) {
         super(var1, 4, 5);
         super.doc = "x.__setslice__(i, j, y) <==> x[i:j]=y\n           \n           Use  of negative indices is not supported.";
      }

      public list___setslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__setslice__(i, j, y) <==> x[i:j]=y\n           \n           Use  of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___setslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3, PyObject var4) {
         ((PyList)this.self).list___setslice__(var1, var2, var3, var4);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyList)this.self).list___setslice__(var1, var2, var3, (PyObject)null);
         return Py.None;
      }
   }

   private static class list___delslice___exposer extends PyBuiltinMethodNarrow {
      public list___delslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "x.__delslice__(i, j) <==> del x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public list___delslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__delslice__(i, j) <==> del x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___delslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((PyList)this.self).list___delslice__(var1, var2, var3);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyList)this.self).list___delslice__(var1, var2, (PyObject)null);
         return Py.None;
      }
   }

   private static class list_toString_exposer extends PyBuiltinMethodNarrow {
      public list_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public list_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyList)this.self).list_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class list_append_exposer extends PyBuiltinMethodNarrow {
      public list_append_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "L.append(object) -- append object to end";
      }

      public list_append_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.append(object) -- append object to end";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_append_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyList)this.self).list_append(var1);
         return Py.None;
      }
   }

   private static class list_count_exposer extends PyBuiltinMethodNarrow {
      public list_count_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "L.count(value) -> integer -- return number of occurrences of value";
      }

      public list_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.count(value) -> integer -- return number of occurrences of value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyList)this.self).list_count(var1));
      }
   }

   private static class list_index_exposer extends PyBuiltinMethodNarrow {
      public list_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "L.index(value, [start, [stop]]) -> integer -- return first index of value.\nRaises ValueError if the value is not present.";
      }

      public list_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.index(value, [start, [stop]]) -> integer -- return first index of value.\nRaises ValueError if the value is not present.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyList)this.self).list_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyList)this.self).list_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyList)this.self).list_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class list_insert_exposer extends PyBuiltinMethodNarrow {
      public list_insert_exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "L.insert(index, object) -- insert object before index";
      }

      public list_insert_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.insert(index, object) -- insert object before index";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_insert_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((PyList)this.self).list_insert(Py.py2int(var1), var2);
         return Py.None;
      }
   }

   private static class list_remove_exposer extends PyBuiltinMethodNarrow {
      public list_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "L.remove(value) -- remove first occurrence of value.\nRaises ValueError if the value is not present.";
      }

      public list_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.remove(value) -- remove first occurrence of value.\nRaises ValueError if the value is not present.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyList)this.self).list_remove(var1);
         return Py.None;
      }
   }

   private static class list_reverse_exposer extends PyBuiltinMethodNarrow {
      public list_reverse_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "L.reverse() -- reverse *IN PLACE*";
      }

      public list_reverse_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.reverse() -- reverse *IN PLACE*";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_reverse_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PyList)this.self).list_reverse();
         return Py.None;
      }
   }

   private static class list_pop_exposer extends PyBuiltinMethodNarrow {
      public list_pop_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "L.pop([index]) -> item -- remove and return item at index (default last).\nRaises IndexError if list is empty or index is out of range.";
      }

      public list_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.pop([index]) -> item -- remove and return item at index (default last).\nRaises IndexError if list is empty or index is out of range.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyList)this.self).list_pop(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((PyList)this.self).list_pop(-1);
      }
   }

   private static class list_extend_exposer extends PyBuiltinMethodNarrow {
      public list_extend_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "L.extend(iterable) -- extend list by appending elements from the iterable";
      }

      public list_extend_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.extend(iterable) -- extend list by appending elements from the iterable";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_extend_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PyList)this.self).list_extend(var1);
         return Py.None;
      }
   }

   private static class list___iadd___exposer extends PyBuiltinMethodNarrow {
      public list___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__iadd__(y) <==> x+=y";
      }

      public list___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iadd__(y) <==> x+=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyList)this.self).list___iadd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class list_sort_exposer extends PyBuiltinMethod {
      public list_sort_exposer(String var1) {
         super(var1);
         super.doc = "L.sort(cmp=None, key=None, reverse=False) -- stable sort *IN PLACE*;\ncmp(x, y) -> -1, 0, 1";
      }

      public list_sort_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "L.sort(cmp=None, key=None, reverse=False) -- stable sort *IN PLACE*;\ncmp(x, y) -> -1, 0, 1";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list_sort_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyList)this.self).list_sort(var1, var2);
         return Py.None;
      }
   }

   private static class list___hash___exposer extends PyBuiltinMethodNarrow {
      public list___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public list___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new list___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyList)this.self).list___hash__());
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyList var4 = new PyList(this.for_type);
         if (var1) {
            var4.list___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyListDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new list___init___exposer("__init__"), new list___len___exposer("__len__"), new list___ne___exposer("__ne__"), new list___eq___exposer("__eq__"), new list___lt___exposer("__lt__"), new list___le___exposer("__le__"), new list___gt___exposer("__gt__"), new list___ge___exposer("__ge__"), new list___imul___exposer("__imul__"), new list___mul___exposer("__mul__"), new list___rmul___exposer("__rmul__"), new list___add___exposer("__add__"), new list___radd___exposer("__radd__"), new list___contains___exposer("__contains__"), new list___delitem___exposer("__delitem__"), new list___setitem___exposer("__setitem__"), new list___getitem___exposer("__getitem__"), new list___iter___exposer("__iter__"), new list___reversed___exposer("__reversed__"), new list___getslice___exposer("__getslice__"), new list___setslice___exposer("__setslice__"), new list___delslice___exposer("__delslice__"), new list_toString_exposer("__repr__"), new list_append_exposer("append"), new list_count_exposer("count"), new list_index_exposer("index"), new list_insert_exposer("insert"), new list_remove_exposer("remove"), new list_reverse_exposer("reverse"), new list_pop_exposer("pop"), new list_extend_exposer("extend"), new list___iadd___exposer("__iadd__"), new list_sort_exposer("sort"), new list___hash___exposer("__hash__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("list", PyList.class, PyObject.class, (boolean)1, "list() -> new empty list\nlist(iterable) -> new list initialized from iterable's items", var1, var2, new exposed___new__());
      }
   }
}
