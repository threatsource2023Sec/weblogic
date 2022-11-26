package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.python.antlr.adapter.AstAdapter;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.astlist",
   base = PyList.class
)
public class AstList extends PySequence implements Cloneable, List, Traverseproc {
   public static final PyType TYPE;
   private static final PyString[] fields;
   private List data;
   private AstAdapter adapter;

   public PyString[] get_fields() {
      return fields;
   }

   public AstList() {
      this(TYPE, new ArrayList(), (AstAdapter)null);
   }

   public AstList(List data) {
      this(TYPE, data, (AstAdapter)null);
   }

   public AstList(List data, AstAdapter adapter) {
      this(TYPE, data, adapter);
   }

   public AstList(PyType type, List data, AstAdapter adapter) {
      super(TYPE);
      if (data == null) {
         data = new ArrayList();
      }

      this.data = (List)data;
      this.adapter = adapter;
   }

   final PyObject astlist___ne__(PyObject o) {
      return this.seq___ne__(o);
   }

   final PyObject astlist___eq__(PyObject o) {
      return this.seq___eq__(o);
   }

   final PyObject astlist___lt__(PyObject o) {
      return this.seq___lt__(o);
   }

   final PyObject astlist___le__(PyObject o) {
      return this.seq___le__(o);
   }

   final PyObject astlist___gt__(PyObject o) {
      return this.seq___gt__(o);
   }

   final PyObject astlist___ge__(PyObject o) {
      return this.seq___ge__(o);
   }

   final boolean astlist___contains__(PyObject o) {
      return this.object___contains__(o);
   }

   final void astlist___delitem__(PyObject index) {
      this.seq___delitem__(index);
   }

   final void astlist___setitem__(PyObject o, PyObject def) {
      this.seq___setitem__(o, def);
   }

   final PyObject astlist___getitem__(PyObject o) {
      PyObject ret = this.seq___finditem__(o);
      if (ret == null) {
         throw Py.IndexError("index out of range: " + o);
      } else {
         return ret;
      }
   }

   final boolean astlist___nonzero__() {
      return this.seq___nonzero__();
   }

   public PyObject astlist___iter__() {
      return this.seq___iter__();
   }

   final PyObject astlist___getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.seq___getslice__(start, stop, step);
   }

   final void astlist___setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      if (value == null) {
         value = step;
         step = null;
      }

      this.seq___setslice__(start, stop, step, value);
   }

   final void astlist___delslice__(PyObject start, PyObject stop, PyObject step) {
      this.seq___delslice__(start, stop, step);
   }

   public PyObject __imul__(PyObject o) {
      return this.astlist___imul__(o);
   }

   final PyObject astlist___imul__(PyObject o) {
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
               int oldsize = this.data.size();

               for(int i = 1; i < count; ++i) {
                  this.data.addAll(this.data.subList(0, oldsize));
               }

               return this;
            }
         } else {
            return this;
         }
      }
   }

   public PyObject __mul__(PyObject o) {
      return this.astlist___mul__(o);
   }

   final PyObject astlist___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.astlist___rmul__(o);
   }

   final PyObject astlist___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __iadd__(PyObject other) {
      return this.astlist___iadd__(other);
   }

   final PyObject astlist___iadd__(PyObject o) {
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

   public PyObject __add__(PyObject other) {
      return this.astlist___add__(other);
   }

   final PyObject astlist___add__(PyObject o) {
      AstList sum = null;
      Object oList = o.__tojava__(List.class);
      if (oList != Py.NoConversion && oList != null) {
         List otherList = (List)oList;
         sum = new AstList();
         sum.extend(this);
         Iterator i = otherList.iterator();

         while(i.hasNext()) {
            sum.add(i.next());
         }
      }

      return sum;
   }

   public PyObject __radd__(PyObject o) {
      return this.astlist___radd__(o);
   }

   final PyObject astlist___radd__(PyObject o) {
      PyList sum = null;
      Object oList = o.__tojava__(List.class);
      if (oList != Py.NoConversion && oList != null) {
         sum = new PyList();
         sum.addAll((List)oList);
         sum.extend(this);
      }

      return sum;
   }

   public int __len__() {
      return this.data.size();
   }

   public String toString() {
      return this.astlist_toString();
   }

   final String astlist_toString() {
      return this.data.toString();
   }

   public void append(PyObject o) {
      this.astlist_append(o);
   }

   final void astlist_append(PyObject o) {
      this.data.add(o);
   }

   public Object clone() {
      return new AstList(this);
   }

   final int astlist_count(PyObject value) {
      int count = 0;
      Iterator var3 = this.data.iterator();

      while(var3.hasNext()) {
         Object o = var3.next();
         if (o.equals(value)) {
            ++count;
         }
      }

      return count;
   }

   public int count(PyObject value) {
      return this.astlist_count(value);
   }

   public int index(PyObject o) {
      return this.astlist_index(o, 0, this.size());
   }

   public int index(PyObject o, int start) {
      return this.astlist_index(o, start, this.size());
   }

   public int index(PyObject o, int start, int stop) {
      return this.astlist_index(o, start, stop);
   }

   final int astlist_index(PyObject o, PyObject start, PyObject stop) {
      int startInt = start == null ? 0 : PySlice.calculateSliceIndex(start);
      int stopInt = stop == null ? this.size() : PySlice.calculateSliceIndex(stop);
      return this.astlist_index(o, startInt, stopInt);
   }

   final int astlist_index(PyObject o, int start, int stop) {
      return this._index(o, "astlist.index(x): x not in list", start, stop);
   }

   final int astlist_index(PyObject o, int start) {
      return this._index(o, "astlist.index(x): x not in list", start, this.size());
   }

   final int astlist_index(PyObject o) {
      return this._index(o, "astlist.index(x): x not in list", 0, this.size());
   }

   private int _index(PyObject o, String message, int start, int stop) {
      int validStop = this.boundToSequence(stop);
      int validStart = this.boundToSequence(start);

      for(int i = validStart; i < validStop && i < this.size(); ++i) {
         if (this.data.get(i).equals(o)) {
            return i;
         }
      }

      throw Py.ValueError(message);
   }

   protected void del(int i) {
      this.data.remove(i);
   }

   protected void delRange(int start, int stop, int step) {
      int i;
      if (step >= 1) {
         for(i = start; i < stop; i += step) {
            this.remove(i);
            --i;
            --stop;
         }
      } else if (step < 0) {
         for(i = start; i >= 0 && i >= stop; i += step) {
            this.remove(i);
         }
      }

   }

   final void astlist_extend(PyObject iterable) {
      int length = this.size();
      this.setslice(length, length, 1, iterable);
   }

   public void extend(PyObject iterable) {
      this.astlist_extend(iterable);
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      int n = sliceLength(start, stop, (long)step);
      List newList = this.data.subList(start, stop);
      if (step == 1) {
         newList = this.data.subList(start, stop);
         return new AstList(newList, this.adapter);
      } else {
         int j = 0;

         for(int i = start; j < n; i += step) {
            newList.set(j, this.data.get(i));
            ++j;
         }

         return new AstList(newList, this.adapter);
      }
   }

   public void insert(int index, PyObject o) {
      this.astlist_insert(index, o);
   }

   final void astlist_insert(int index, PyObject o) {
      if (index < 0) {
         index = Math.max(0, this.size() + index);
      }

      if (index > this.size()) {
         index = this.size();
      }

      this.data.add(index, o);
   }

   final void astlist_remove(PyObject value) {
      this.del(this._index(value, "astlist.remove(x): x not in list", 0, this.size()));
   }

   public void remove(PyObject value) {
      this.astlist_remove(value);
   }

   public void reverse() {
      this.astlist_reverse();
   }

   final void astlist_reverse() {
      Collections.reverse(this.data);
   }

   public PyObject pop() {
      return this.pop(-1);
   }

   public PyObject pop(int n) {
      return this.astlist_pop(n);
   }

   final PyObject astlist_pop(int n) {
      if (this.adapter == null) {
         return (PyObject)this.data.remove(n);
      } else {
         Object element = this.data.remove(n);
         return this.adapter.ast2py(element);
      }
   }

   protected PyObject repeat(int count) {
      if (count < 0) {
         count = 0;
      }

      int size = this.size();
      int newSize = size * count;
      if (count != 0 && newSize / count != size) {
         throw Py.MemoryError("");
      } else {
         List newList = new ArrayList();

         for(int i = 0; i < count; ++i) {
            newList.addAll(this.data);
         }

         return new AstList(newList);
      }
   }

   protected void setslice(int start, int stop, int step, PyObject value) {
      if (stop < start) {
         stop = start;
      }

      if (value instanceof PySequence) {
         PySequence sequence = (PySequence)value;
         this.setslicePySequence(start, stop, step, sequence);
      } else if (value instanceof List) {
         List list = (List)value.__tojava__(List.class);
         if (list != null && list != Py.NoConversion) {
            this.setsliceList(start, stop, step, list);
         }
      } else {
         this.setsliceIterable(start, stop, step, value);
      }

   }

   protected void setslicePySequence(int start, int stop, int step, PySequence value) {
      if (step != 0) {
         if (value == this) {
            PyList newseq = new PyList();
            PyObject iter = ((PySequence)value).__iter__();
            PyObject item = null;

            while((item = iter.__iternext__()) != null) {
               newseq.append(item);
            }

            value = newseq;
         }

         int n = ((PySequence)value).__len__();
         int i = 0;

         for(int j = start; i < n; j += step) {
            this.pyset(j, ((PySequence)value).pyget(i));
            ++i;
         }
      }

   }

   protected void setsliceList(int start, int stop, int step, List value) {
      if (step != 1) {
         throw Py.TypeError("setslice with java.util.List and step != 1 not supported yet");
      } else {
         int n = value.size();

         for(int i = 0; i < n; ++i) {
            this.data.add(i + start, value.get(i));
         }

      }
   }

   protected void setsliceIterable(int start, int stop, int step, PyObject value) {
      PyObject[] seq;
      try {
         seq = Py.make_array(value);
      } catch (PyException var7) {
         if (var7.match(Py.TypeError)) {
            throw Py.TypeError("can only assign an iterable");
         }

         throw var7;
      }

      this.setslicePySequence(start, stop, step, new PyList(seq));
   }

   public void add(int index, Object element) {
      this.data.add(index, element);
   }

   public boolean add(Object o) {
      return this.data.add(o);
   }

   public boolean addAll(int index, Collection c) {
      return this.data.addAll(index, c);
   }

   public boolean addAll(Collection c) {
      return this.data.addAll(c);
   }

   public void clear() {
      this.data.clear();
   }

   public boolean contains(Object o) {
      return this.data.contains(o);
   }

   public boolean containsAll(Collection c) {
      return this.data.containsAll(c);
   }

   public Object get(int index) {
      return this.data.get(index);
   }

   public int indexOf(Object o) {
      return this.data.indexOf(o);
   }

   public boolean isEmpty() {
      return this.data.isEmpty();
   }

   public Iterator iterator() {
      return this.data.iterator();
   }

   public int lastIndexOf(Object o) {
      return this.data.lastIndexOf(o);
   }

   public ListIterator listIterator() {
      return this.data.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.data.listIterator(index);
   }

   public boolean pyadd(PyObject o) {
      this.data.add(o);
      return true;
   }

   public void pyadd(int index, PyObject element) {
      this.data.add(index, element);
   }

   public PyObject pyget(int index) {
      return this.adapter == null ? (PyObject)this.data.get(index) : this.adapter.ast2py(this.data.get(index));
   }

   public void pyset(int index, PyObject element) {
      if (this.adapter == null) {
         this.data.set(index, element);
      } else {
         Object o = this.adapter.py2ast(element);
         this.data.set(index, o);
      }

   }

   public Object remove(int index) {
      return this.data.remove(index);
   }

   public boolean remove(Object o) {
      return this.data.remove(o);
   }

   public boolean removeAll(Collection c) {
      return this.data.removeAll(c);
   }

   public boolean retainAll(Collection c) {
      return this.data.retainAll(c);
   }

   public Object set(int index, Object element) {
      return this.data.set(index, element);
   }

   public int size() {
      return this.data.size();
   }

   public List subList(int fromIndex, int toIndex) {
      return this.data.subList(fromIndex, toIndex);
   }

   public Object[] toArray() {
      return this.data.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.data.toArray(a);
   }

   public Object __tojava__(Class c) {
      return c.isInstance(this) ? this : Py.NoConversion;
   }

   public int traverse(Visitproc visit, Object arg) {
      Iterator var3 = this.data.iterator();

      while(var3.hasNext()) {
         Object ob = var3.next();
         if (ob instanceof PyObject) {
            int retVal = visit.visit((PyObject)ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return this.data.contains(ob);
   }

   static {
      PyType.addBuilder(AstList.class, new PyExposer());
      TYPE = PyType.fromClass(AstList.class);
      fields = new PyString[0];
   }

   private static class astlist___ne___exposer extends PyBuiltinMethodNarrow {
      public astlist___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___eq___exposer extends PyBuiltinMethodNarrow {
      public astlist___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___lt___exposer extends PyBuiltinMethodNarrow {
      public astlist___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___le___exposer extends PyBuiltinMethodNarrow {
      public astlist___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___gt___exposer extends PyBuiltinMethodNarrow {
      public astlist___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___ge___exposer extends PyBuiltinMethodNarrow {
      public astlist___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___contains___exposer extends PyBuiltinMethodNarrow {
      public astlist___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((AstList)this.self).astlist___contains__(var1));
      }
   }

   private static class astlist___delitem___exposer extends PyBuiltinMethodNarrow {
      public astlist___delitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___delitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___delitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((AstList)this.self).astlist___delitem__(var1);
         return Py.None;
      }
   }

   private static class astlist___setitem___exposer extends PyBuiltinMethodNarrow {
      public astlist___setitem___exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public astlist___setitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___setitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((AstList)this.self).astlist___setitem__(var1, var2);
         return Py.None;
      }
   }

   private static class astlist___getitem___exposer extends PyBuiltinMethodNarrow {
      public astlist___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((AstList)this.self).astlist___getitem__(var1);
      }
   }

   private static class astlist___nonzero___exposer extends PyBuiltinMethodNarrow {
      public astlist___nonzero___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public astlist___nonzero___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___nonzero___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newBoolean(((AstList)this.self).astlist___nonzero__());
      }
   }

   private static class astlist___iter___exposer extends PyBuiltinMethodNarrow {
      public astlist___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public astlist___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((AstList)this.self).astlist___iter__();
      }
   }

   private static class astlist___getslice___exposer extends PyBuiltinMethodNarrow {
      public astlist___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "";
      }

      public astlist___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((AstList)this.self).astlist___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((AstList)this.self).astlist___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class astlist___setslice___exposer extends PyBuiltinMethodNarrow {
      public astlist___setslice___exposer(String var1) {
         super(var1, 4, 5);
         super.doc = "";
      }

      public astlist___setslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___setslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3, PyObject var4) {
         ((AstList)this.self).astlist___setslice__(var1, var2, var3, var4);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((AstList)this.self).astlist___setslice__(var1, var2, var3, (PyObject)null);
         return Py.None;
      }
   }

   private static class astlist___delslice___exposer extends PyBuiltinMethodNarrow {
      public astlist___delslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "";
      }

      public astlist___delslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___delslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         ((AstList)this.self).astlist___delslice__(var1, var2, var3);
         return Py.None;
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((AstList)this.self).astlist___delslice__(var1, var2, (PyObject)null);
         return Py.None;
      }
   }

   private static class astlist___imul___exposer extends PyBuiltinMethodNarrow {
      public astlist___imul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___imul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___imul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___imul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___mul___exposer extends PyBuiltinMethodNarrow {
      public astlist___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___rmul___exposer extends PyBuiltinMethodNarrow {
      public astlist___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___iadd___exposer extends PyBuiltinMethodNarrow {
      public astlist___iadd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___iadd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___iadd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((AstList)this.self).astlist___iadd__(var1);
      }
   }

   private static class astlist___add___exposer extends PyBuiltinMethodNarrow {
      public astlist___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist___radd___exposer extends PyBuiltinMethodNarrow {
      public astlist___radd___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist___radd___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist___radd___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((AstList)this.self).astlist___radd__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class astlist_toString_exposer extends PyBuiltinMethodNarrow {
      public astlist_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public astlist_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((AstList)this.self).astlist_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class astlist_append_exposer extends PyBuiltinMethodNarrow {
      public astlist_append_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist_append_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_append_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((AstList)this.self).astlist_append(var1);
         return Py.None;
      }
   }

   private static class astlist_count_exposer extends PyBuiltinMethodNarrow {
      public astlist_count_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((AstList)this.self).astlist_count(var1));
      }
   }

   private static class astlist_index_exposer extends PyBuiltinMethodNarrow {
      public astlist_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "";
      }

      public astlist_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((AstList)this.self).astlist_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((AstList)this.self).astlist_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((AstList)this.self).astlist_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class astlist_extend_exposer extends PyBuiltinMethodNarrow {
      public astlist_extend_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist_extend_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_extend_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((AstList)this.self).astlist_extend(var1);
         return Py.None;
      }
   }

   private static class astlist_insert_exposer extends PyBuiltinMethodNarrow {
      public astlist_insert_exposer(String var1) {
         super(var1, 3, 3);
         super.doc = "";
      }

      public astlist_insert_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_insert_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         ((AstList)this.self).astlist_insert(Py.py2int(var1), var2);
         return Py.None;
      }
   }

   private static class astlist_remove_exposer extends PyBuiltinMethodNarrow {
      public astlist_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public astlist_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((AstList)this.self).astlist_remove(var1);
         return Py.None;
      }
   }

   private static class astlist_reverse_exposer extends PyBuiltinMethodNarrow {
      public astlist_reverse_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public astlist_reverse_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_reverse_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((AstList)this.self).astlist_reverse();
         return Py.None;
      }
   }

   private static class astlist_pop_exposer extends PyBuiltinMethodNarrow {
      public astlist_pop_exposer(String var1) {
         super(var1, 1, 2);
         super.doc = "";
      }

      public astlist_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new astlist_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((AstList)this.self).astlist_pop(Py.py2int(var1));
      }

      public PyObject __call__() {
         return ((AstList)this.self).astlist_pop(-1);
      }
   }

   private static class _fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _fields_descriptor() {
         super("_fields", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((AstList)var1).get_fields();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new astlist___ne___exposer("__ne__"), new astlist___eq___exposer("__eq__"), new astlist___lt___exposer("__lt__"), new astlist___le___exposer("__le__"), new astlist___gt___exposer("__gt__"), new astlist___ge___exposer("__ge__"), new astlist___contains___exposer("__contains__"), new astlist___delitem___exposer("__delitem__"), new astlist___setitem___exposer("__setitem__"), new astlist___getitem___exposer("__getitem__"), new astlist___nonzero___exposer("__nonzero__"), new astlist___iter___exposer("__iter__"), new astlist___getslice___exposer("__getslice__"), new astlist___setslice___exposer("__setslice__"), new astlist___delslice___exposer("__delslice__"), new astlist___imul___exposer("__imul__"), new astlist___mul___exposer("__mul__"), new astlist___rmul___exposer("__rmul__"), new astlist___iadd___exposer("__iadd__"), new astlist___add___exposer("__add__"), new astlist___radd___exposer("__radd__"), new astlist_toString_exposer("__repr__"), new astlist_append_exposer("append"), new astlist_count_exposer("count"), new astlist_index_exposer("index"), new astlist_extend_exposer("extend"), new astlist_insert_exposer("insert"), new astlist_remove_exposer("remove"), new astlist_reverse_exposer("reverse"), new astlist_pop_exposer("pop")};
         PyDataDescr[] var2 = new PyDataDescr[]{new _fields_descriptor()};
         super("_ast.astlist", AstList.class, PyList.class, (boolean)1, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
