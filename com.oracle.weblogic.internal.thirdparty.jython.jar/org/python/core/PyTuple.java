package org.python.core;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "tuple",
   base = PyObject.class,
   doc = "tuple() -> empty tuple\ntuple(iterable) -> tuple initialized from iterable's items\n\nIf the argument is a tuple, the return value is the same object."
)
public class PyTuple extends PySequenceList {
   public static final PyType TYPE;
   private final PyObject[] array;
   private volatile List cachedList;

   public PyTuple() {
      this(TYPE, Py.EmptyObjects);
   }

   public PyTuple(PyObject... elements) {
      this(TYPE, elements);
   }

   public PyTuple(PyType subtype, PyObject[] elements) {
      super(subtype);
      this.cachedList = null;
      if (elements == null) {
         this.array = new PyObject[0];
      } else {
         this.array = new PyObject[elements.length];
         System.arraycopy(elements, 0, this.array, 0, elements.length);
      }

   }

   public PyTuple(PyObject[] elements, boolean copy) {
      this(TYPE, elements, copy);
   }

   public PyTuple(PyType subtype, PyObject[] elements, boolean copy) {
      super(subtype);
      this.cachedList = null;
      if (copy) {
         this.array = new PyObject[elements.length];
         System.arraycopy(elements, 0, this.array, 0, elements.length);
      } else {
         this.array = elements;
      }

   }

   private static PyTuple fromArrayNoCopy(PyObject[] elements) {
      return new PyTuple(elements, false);
   }

   List getList() {
      if (this.cachedList == null) {
         this.cachedList = Arrays.asList(this.array);
      }

      return this.cachedList;
   }

   @ExposedNew
   static final PyObject tuple_new(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("tuple", args, keywords, new String[]{"sequence"}, 0);
      PyObject S = ap.getPyObject(0, (PyObject)null);
      if (new_.for_type == subtype) {
         if (S == null) {
            return Py.EmptyTuple;
         } else if (S.getType() == TYPE) {
            return S;
         } else {
            return S instanceof PyTupleDerived ? new PyTuple(((PyTuple)S).getArray()) : fromArrayNoCopy(Py.make_array(S));
         }
      } else {
         return S == null ? new PyTupleDerived(subtype, Py.EmptyObjects) : new PyTupleDerived(subtype, Py.make_array(S));
      }
   }

   public static PyTuple fromIterable(PyObject iterable) {
      return fromArrayNoCopy(Py.make_array(iterable));
   }

   protected PyObject getslice(int start, int stop, int step) {
      if (step > 0 && stop < start) {
         stop = start;
      }

      int n = sliceLength(start, stop, (long)step);
      PyObject[] newArray = new PyObject[n];
      if (step == 1) {
         System.arraycopy(this.array, start, newArray, 0, stop - start);
         return fromArrayNoCopy(newArray);
      } else {
         int i = start;

         for(int j = 0; j < n; ++j) {
            newArray[j] = this.array[i];
            i += step;
         }

         return fromArrayNoCopy(newArray);
      }
   }

   protected PyObject repeat(int count) {
      if (count < 0) {
         count = 0;
      }

      int size = this.size();
      if (size == 0 || count == 1) {
         if (this.getType() == TYPE) {
            return this;
         }

         if (size == 0) {
            return Py.EmptyTuple;
         }
      }

      int newSize = size * count;
      if (newSize / size != count) {
         throw Py.MemoryError("");
      } else {
         PyObject[] newArray = new PyObject[newSize];

         for(int i = 0; i < count; ++i) {
            System.arraycopy(this.array, 0, newArray, i * size, size);
         }

         return fromArrayNoCopy(newArray);
      }
   }

   public int __len__() {
      return this.tuple___len__();
   }

   final int tuple___len__() {
      return this.size();
   }

   final boolean tuple___contains__(PyObject o) {
      return super.__contains__(o);
   }

   final PyObject tuple___ne__(PyObject o) {
      return super.__ne__(o);
   }

   final PyObject tuple___eq__(PyObject o) {
      return super.__eq__(o);
   }

   final PyObject tuple___gt__(PyObject o) {
      return super.__gt__(o);
   }

   final PyObject tuple___ge__(PyObject o) {
      return super.__ge__(o);
   }

   final PyObject tuple___lt__(PyObject o) {
      return super.__lt__(o);
   }

   final PyObject tuple___le__(PyObject o) {
      return super.__le__(o);
   }

   public PyObject __add__(PyObject generic_other) {
      return this.tuple___add__(generic_other);
   }

   final PyObject tuple___add__(PyObject generic_other) {
      PyTuple sum = null;
      if (generic_other instanceof PyTuple) {
         PyTuple other = (PyTuple)generic_other;
         PyObject[] newArray = new PyObject[this.array.length + other.array.length];
         System.arraycopy(this.array, 0, newArray, 0, this.array.length);
         System.arraycopy(other.array, 0, newArray, this.array.length, other.array.length);
         sum = fromArrayNoCopy(newArray);
      }

      return sum;
   }

   public PyObject __mul__(PyObject o) {
      return this.tuple___mul__(o);
   }

   final PyObject tuple___mul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __rmul__(PyObject o) {
      return this.tuple___rmul__(o);
   }

   final PyObject tuple___rmul__(PyObject o) {
      return !o.isIndex() ? null : this.repeat(o.asIndex(Py.OverflowError));
   }

   public PyObject __iter__() {
      return this.tuple___iter__();
   }

   public PyObject tuple___iter__() {
      return new PyTupleIterator(this);
   }

   final PyObject tuple___getslice__(PyObject s_start, PyObject s_stop, PyObject s_step) {
      return this.seq___getslice__(s_start, s_stop, s_step);
   }

   final PyObject tuple___getitem__(PyObject index) {
      PyObject ret = this.seq___finditem__(index);
      if (ret == null) {
         throw Py.IndexError("index out of range: " + index);
      } else {
         return ret;
      }
   }

   final PyTuple tuple___getnewargs__() {
      return new PyTuple(new PyObject[]{new PyTuple(this.getArray())});
   }

   public PyTuple __getnewargs__() {
      return this.tuple___getnewargs__();
   }

   public int hashCode() {
      return this.tuple___hash__();
   }

   final int tuple___hash__() {
      int len = this.size();
      int mult = 1000003;
      int x = 3430008;

      while(true) {
         --len;
         if (len < 0) {
            return x + 97531;
         }

         int y = this.array[len].hashCode();
         x = (x ^ y) * mult;
         mult += 82520 + len + len;
      }
   }

   private String subobjRepr(PyObject o) {
      return o == null ? "null" : o.__repr__().toString();
   }

   public String toString() {
      return this.tuple___repr__();
   }

   final String tuple___repr__() {
      StringBuilder buf = new StringBuilder("(");

      for(int i = 0; i < this.array.length - 1; ++i) {
         buf.append(this.subobjRepr(this.array[i]));
         buf.append(", ");
      }

      if (this.array.length > 0) {
         buf.append(this.subobjRepr(this.array[this.array.length - 1]));
      }

      if (this.array.length == 1) {
         buf.append(",");
      }

      buf.append(")");
      return buf.toString();
   }

   public List subList(int fromIndex, int toIndex) {
      if (fromIndex >= 0 && toIndex <= this.size()) {
         if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
         } else {
            PyObject[] elements = new PyObject[toIndex - fromIndex];
            int i = 0;

            for(int j = fromIndex; i < elements.length; ++j) {
               elements[i] = this.array[j];
               ++i;
            }

            return new PyTuple(elements);
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public Iterator iterator() {
      return new Iterator() {
         private final Iterator iter = PyTuple.this.getList().iterator();

         public void remove() {
            throw new UnsupportedOperationException();
         }

         public boolean hasNext() {
            return this.iter.hasNext();
         }

         public Object next() {
            return ((PyObject)this.iter.next()).__tojava__(Object.class);
         }
      };
   }

   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection coll) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public Object set(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   public void add(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   public Object remove(int index) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(int index, Collection c) {
      throw new UnsupportedOperationException();
   }

   public ListIterator listIterator() {
      return this.listIterator(0);
   }

   public ListIterator listIterator(final int index) {
      return new ListIterator() {
         private final ListIterator iter = PyTuple.this.getList().listIterator(index);

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
            throw new UnsupportedOperationException();
         }

         public void set(Object o) {
            throw new UnsupportedOperationException();
         }

         public void add(Object o) {
            throw new UnsupportedOperationException();
         }
      };
   }

   protected String unsupportedopMessage(String op, PyObject o2) {
      return op.equals("+") ? "can only concatenate tuple (not \"{2}\") to tuple" : super.unsupportedopMessage(op, o2);
   }

   public void pyset(int index, PyObject value) {
      throw Py.TypeError("'tuple' object does not support item assignment");
   }

   public boolean contains(Object o) {
      return this.getList().contains(Py.java2py(o));
   }

   public boolean containsAll(Collection c) {
      if (c instanceof PyList) {
         return this.getList().containsAll(((PyList)c).getList());
      } else {
         return c instanceof PyTuple ? this.getList().containsAll(((PyTuple)c).getList()) : this.getList().containsAll(new PyList(c));
      }
   }

   public int count(PyObject value) {
      return this.tuple_count(value);
   }

   final int tuple_count(PyObject value) {
      int count = 0;
      PyObject[] var3 = this.array;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject item = var3[var5];
         if (item.equals(value)) {
            ++count;
         }
      }

      return count;
   }

   public int index(PyObject value) {
      return this.index(value, 0);
   }

   public int index(PyObject value, int start) {
      return this.index(value, start, this.size());
   }

   public int index(PyObject value, int start, int stop) {
      return this.tuple_index(value, start, stop);
   }

   final int tuple_index(PyObject value, PyObject start, PyObject stop) {
      int startInt = start == null ? 0 : PySlice.calculateSliceIndex(start);
      int stopInt = stop == null ? this.size() : PySlice.calculateSliceIndex(stop);
      return this.tuple_index(value, startInt, stopInt);
   }

   final int tuple_index(PyObject value, int start, int stop) {
      int validStart = this.boundToSequence(start);
      int validStop = this.boundToSequence(stop);

      for(int i = validStart; i < validStop; ++i) {
         if (this.array[i].equals(value)) {
            return i;
         }
      }

      throw Py.ValueError("tuple.index(x): x not in list");
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other instanceof PyObject) {
         return this._eq((PyObject)other).__nonzero__();
      } else {
         return other instanceof List ? other.equals(this) : false;
      }
   }

   public Object get(int index) {
      return this.array[index].__tojava__(Object.class);
   }

   public PyObject[] getArray() {
      return this.array;
   }

   public int indexOf(Object o) {
      return this.getList().indexOf(Py.java2py(o));
   }

   public boolean isEmpty() {
      return this.array.length == 0;
   }

   public int lastIndexOf(Object o) {
      return this.getList().lastIndexOf(Py.java2py(o));
   }

   public void pyadd(int index, PyObject element) {
      throw new UnsupportedOperationException();
   }

   public boolean pyadd(PyObject o) {
      throw new UnsupportedOperationException();
   }

   public PyObject pyget(int index) {
      return this.array[index];
   }

   public void remove(int start, int stop) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.array.length;
   }

   public Object[] toArray() {
      Object[] converted = new Object[this.array.length];

      for(int i = 0; i < this.array.length; ++i) {
         converted[i] = this.array[i].__tojava__(Object.class);
      }

      return converted;
   }

   public Object[] toArray(Object[] converted) {
      Class type = converted.getClass().getComponentType();
      if (converted.length < this.array.length) {
         converted = (Object[])((Object[])Array.newInstance(type, this.array.length));
      }

      int i;
      for(i = 0; i < this.array.length; ++i) {
         converted[i] = type.cast(this.array[i].__tojava__(type));
      }

      if (this.array.length < converted.length) {
         for(i = this.array.length; i < converted.length; ++i) {
            converted[i] = null;
         }
      }

      return converted;
   }

   public int traverse(Visitproc visit, Object arg) {
      PyObject[] var3 = this.array;
      int var4 = var3.length;

      int retVal;
      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject ob = var3[var5];
         if (ob != null) {
            retVal = visit.visit(ob, arg);
            if (retVal != 0) {
               return retVal;
            }
         }
      }

      if (this.cachedList != null) {
         Iterator var8 = this.cachedList.iterator();

         while(var8.hasNext()) {
            PyObject ob = (PyObject)var8.next();
            if (ob != null) {
               retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else {
         PyObject[] var2 = this.array;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject obj = var2[var4];
            if (obj == ob) {
               return true;
            }
         }

         if (this.cachedList != null) {
            Iterator var6 = this.cachedList.iterator();

            while(var6.hasNext()) {
               PyObject obj = (PyObject)var6.next();
               if (obj == ob) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   static {
      PyType.addBuilder(PyTuple.class, new PyExposer());
      TYPE = PyType.fromClass(PyTuple.class);
   }

   private static class tuple___len___exposer extends PyBuiltinMethodNarrow {
      public tuple___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public tuple___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyTuple)this.self).tuple___len__());
      }
   }

   private static class tuple___contains___exposer extends PyBuiltinMethodNarrow {
      public tuple___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public tuple___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyTuple)this.self).tuple___contains__(var1));
      }
   }

   private static class tuple___ne___exposer extends PyBuiltinMethodNarrow {
      public tuple___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public tuple___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___eq___exposer extends PyBuiltinMethodNarrow {
      public tuple___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public tuple___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___gt___exposer extends PyBuiltinMethodNarrow {
      public tuple___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public tuple___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___ge___exposer extends PyBuiltinMethodNarrow {
      public tuple___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public tuple___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___lt___exposer extends PyBuiltinMethodNarrow {
      public tuple___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public tuple___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___le___exposer extends PyBuiltinMethodNarrow {
      public tuple___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public tuple___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___add___exposer extends PyBuiltinMethodNarrow {
      public tuple___add___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public tuple___add___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__add__(y) <==> x+y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___add___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___add__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___mul___exposer extends PyBuiltinMethodNarrow {
      public tuple___mul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public tuple___mul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__mul__(n) <==> x*n";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___mul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___mul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___rmul___exposer extends PyBuiltinMethodNarrow {
      public tuple___rmul___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public tuple___rmul___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__rmul__(n) <==> n*x";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___rmul___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyTuple)this.self).tuple___rmul__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class tuple___iter___exposer extends PyBuiltinMethodNarrow {
      public tuple___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public tuple___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyTuple)this.self).tuple___iter__();
      }
   }

   private static class tuple___getslice___exposer extends PyBuiltinMethodNarrow {
      public tuple___getslice___exposer(String var1) {
         super(var1, 3, 4);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public tuple___getslice___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getslice__(i, j) <==> x[i:j]\n           \n           Use of negative indices is not supported.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___getslice___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return ((PyTuple)this.self).tuple___getslice__(var1, var2, var3);
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return ((PyTuple)this.self).tuple___getslice__(var1, var2, (PyObject)null);
      }
   }

   private static class tuple___getitem___exposer extends PyBuiltinMethodNarrow {
      public tuple___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public tuple___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyTuple)this.self).tuple___getitem__(var1);
      }
   }

   private static class tuple___getnewargs___exposer extends PyBuiltinMethodNarrow {
      public tuple___getnewargs___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public tuple___getnewargs___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___getnewargs___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyTuple)this.self).tuple___getnewargs__();
      }
   }

   private static class tuple___hash___exposer extends PyBuiltinMethodNarrow {
      public tuple___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public tuple___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyTuple)this.self).tuple___hash__());
      }
   }

   private static class tuple___repr___exposer extends PyBuiltinMethodNarrow {
      public tuple___repr___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public tuple___repr___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple___repr___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyTuple)this.self).tuple___repr__();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class tuple_count_exposer extends PyBuiltinMethodNarrow {
      public tuple_count_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "T.count(value) -> integer -- return number of occurrences of value";
      }

      public tuple_count_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "T.count(value) -> integer -- return number of occurrences of value";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple_count_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyTuple)this.self).tuple_count(var1));
      }
   }

   private static class tuple_index_exposer extends PyBuiltinMethodNarrow {
      public tuple_index_exposer(String var1) {
         super(var1, 2, 4);
         super.doc = "T.index(value, [start, [stop]]) -> integer -- return first index of value.\nRaises ValueError if the value is not present.";
      }

      public tuple_index_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "T.index(value, [start, [stop]]) -> integer -- return first index of value.\nRaises ValueError if the value is not present.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new tuple_index_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1, PyObject var2, PyObject var3) {
         return Py.newInteger(((PyTuple)this.self).tuple_index(var1, var2, var3));
      }

      public PyObject __call__(PyObject var1, PyObject var2) {
         return Py.newInteger(((PyTuple)this.self).tuple_index(var1, var2, (PyObject)null));
      }

      public PyObject __call__(PyObject var1) {
         return Py.newInteger(((PyTuple)this.self).tuple_index(var1, (PyObject)null, (PyObject)null));
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyTuple.tuple_new(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new tuple___len___exposer("__len__"), new tuple___contains___exposer("__contains__"), new tuple___ne___exposer("__ne__"), new tuple___eq___exposer("__eq__"), new tuple___gt___exposer("__gt__"), new tuple___ge___exposer("__ge__"), new tuple___lt___exposer("__lt__"), new tuple___le___exposer("__le__"), new tuple___add___exposer("__add__"), new tuple___mul___exposer("__mul__"), new tuple___rmul___exposer("__rmul__"), new tuple___iter___exposer("__iter__"), new tuple___getslice___exposer("__getslice__"), new tuple___getitem___exposer("__getitem__"), new tuple___getnewargs___exposer("__getnewargs__"), new tuple___hash___exposer("__hash__"), new tuple___repr___exposer("__repr__"), new tuple_count_exposer("count"), new tuple_index_exposer("index")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("tuple", PyTuple.class, PyObject.class, (boolean)1, "tuple() -> empty tuple\ntuple(iterable) -> tuple initialized from iterable's items\n\nIf the argument is a tuple, the return value is the same object.", var1, var2, new exposed___new__());
      }
   }
}
