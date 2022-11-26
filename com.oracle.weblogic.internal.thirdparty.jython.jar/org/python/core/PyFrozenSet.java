package org.python.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "frozenset",
   base = PyObject.class,
   doc = "frozenset() -> empty frozenset object\nfrozenset(iterable) -> frozenset object\n\nBuild an immutable unordered collection of unique elements."
)
public class PyFrozenSet extends BaseSet {
   public static final PyType TYPE;

   public PyFrozenSet() {
      super(TYPE, new HashSet());
   }

   public PyFrozenSet(PyObject data) {
      this(TYPE, data);
   }

   public PyFrozenSet(PyType type, PyObject data) {
      super(type, _update(new HashSet(), data));
   }

   @ExposedNew
   public static PyObject frozenset___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("frozenset", args, keywords, new String[]{"iterable"}, 0);
      PyObject iterable = ap.getPyObject(0, (PyObject)null);
      PyFrozenSet fset = null;
      if (new_.for_type == subtype) {
         if (iterable == null) {
            fset = Py.EmptyFrozenSet;
         } else if (iterable.getType() == TYPE) {
            fset = (PyFrozenSet)iterable;
         } else {
            fset = new PyFrozenSet(iterable);
            if (((PyFrozenSet)fset).__len__() == 0) {
               fset = Py.EmptyFrozenSet;
            }
         }
      } else {
         fset = new PyFrozenSetDerived(subtype, iterable);
      }

      return (PyObject)fset;
   }

   final PyObject frozenset___cmp__(PyObject o) {
      return new PyInteger(this.baseset___cmp__(o));
   }

   final PyObject frozenset___ne__(PyObject o) {
      return this.baseset___ne__(o);
   }

   final PyObject frozenset___eq__(PyObject o) {
      return this.baseset___eq__(o);
   }

   final PyObject frozenset___or__(PyObject o) {
      return this.baseset___or__(o);
   }

   final PyObject frozenset___xor__(PyObject o) {
      return this.baseset___xor__(o);
   }

   final PyObject frozenset___sub__(PyObject o) {
      return this.baseset___sub__(o);
   }

   final PyObject frozenset___and__(PyObject o) {
      return this.baseset___and__(o);
   }

   final PyObject frozenset___lt__(PyObject o) {
      return this.baseset___lt__(o);
   }

   final PyObject frozenset___gt__(PyObject o) {
      return this.baseset___gt__(o);
   }

   final PyObject frozenset___ge__(PyObject o) {
      return this.baseset___ge__(o);
   }

   final PyObject frozenset___le__(PyObject o) {
      return this.baseset___le__(o);
   }

   final PyObject frozenset___iter__() {
      return this.baseset___iter__();
   }

   final boolean frozenset___contains__(PyObject item) {
      return this.baseset___contains__(item);
   }

   final PyObject frozenset_copy() {
      return (PyObject)(this.getClass() == PyFrozenSet.class ? this : this.baseset_copy());
   }

   final PyObject frozenset_union(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("difference() takes no keyword arguments");
      } else {
         return this.baseset_union(args);
      }
   }

   final PyObject frozenset_difference(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("difference() takes no keyword arguments");
      } else {
         return this.baseset_difference(args);
      }
   }

   final PyObject frozenset_symmetric_difference(PyObject set) {
      return this.baseset_symmetric_difference(set);
   }

   final PyObject frozenset_intersection(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("intersection() takes no keyword arguments");
      } else {
         return this.baseset_intersection(args);
      }
   }

   final PyObject frozenset_issubset(PyObject set) {
      return this.baseset_issubset(set);
   }

   final PyObject frozenset_issuperset(PyObject set) {
      return this.baseset_issuperset(set);
   }

   final PyObject frozenset_isdisjoint(PyObject set) {
      return this.baseset_isdisjoint(set);
   }

   final int frozenset___len__() {
      return this.baseset___len__();
   }

   final PyObject frozenset___reduce__() {
      return this.baseset___reduce__();
   }

   final int frozenset___hash__() {
      return this._set.hashCode();
   }

   final String frozenset_toString() {
      return this.baseset_toString();
   }

   public int hashCode() {
      return this.frozenset___hash__();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public Iterator iterator() {
      final Iterator i = super.iterator();
      return new Iterator() {
         public boolean hasNext() {
            return i.hasNext();
         }

         public Object next() {
            return i.next();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   static {
      PyType.addBuilder(PyFrozenSet.class, new PyExposer());
      TYPE = PyType.fromClass(PyFrozenSet.class);
   }

   private static class frozenset___cmp___exposer extends PyBuiltinMethodNarrow {
      public frozenset___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public frozenset___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___cmp__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___ne___exposer extends PyBuiltinMethodNarrow {
      public frozenset___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public frozenset___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___eq___exposer extends PyBuiltinMethodNarrow {
      public frozenset___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public frozenset___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___or___exposer extends PyBuiltinMethodNarrow {
      public frozenset___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public frozenset___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___xor___exposer extends PyBuiltinMethodNarrow {
      public frozenset___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public frozenset___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___sub___exposer extends PyBuiltinMethodNarrow {
      public frozenset___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public frozenset___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___and___exposer extends PyBuiltinMethodNarrow {
      public frozenset___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public frozenset___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___lt___exposer extends PyBuiltinMethodNarrow {
      public frozenset___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public frozenset___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___gt___exposer extends PyBuiltinMethodNarrow {
      public frozenset___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public frozenset___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___ge___exposer extends PyBuiltinMethodNarrow {
      public frozenset___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public frozenset___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___le___exposer extends PyBuiltinMethodNarrow {
      public frozenset___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public frozenset___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PyFrozenSet)this.self).frozenset___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class frozenset___iter___exposer extends PyBuiltinMethodNarrow {
      public frozenset___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public frozenset___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFrozenSet)this.self).frozenset___iter__();
      }
   }

   private static class frozenset___contains___exposer extends PyBuiltinMethodNarrow {
      public frozenset___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x.";
      }

      public frozenset___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PyFrozenSet)this.self).frozenset___contains__(var1));
      }
   }

   private static class frozenset_copy_exposer extends PyBuiltinMethodNarrow {
      public frozenset_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return a shallow copy of a set.";
      }

      public frozenset_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return a shallow copy of a set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFrozenSet)this.self).frozenset_copy();
      }
   }

   private static class frozenset_union_exposer extends PyBuiltinMethod {
      public frozenset_union_exposer(String var1) {
         super(var1);
         super.doc = "Return the union of sets as a new set.\n\n(i.e. all elements that are in either set.)";
      }

      public frozenset_union_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the union of sets as a new set.\n\n(i.e. all elements that are in either set.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_union_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyFrozenSet)this.self).frozenset_union(var1, var2);
      }
   }

   private static class frozenset_difference_exposer extends PyBuiltinMethod {
      public frozenset_difference_exposer(String var1) {
         super(var1);
         super.doc = "Return the difference of two or more sets as a new set.\n\n(i.e. all elements that are in this set but not the others.)";
      }

      public frozenset_difference_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the difference of two or more sets as a new set.\n\n(i.e. all elements that are in this set but not the others.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_difference_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyFrozenSet)this.self).frozenset_difference(var1, var2);
      }
   }

   private static class frozenset_symmetric_difference_exposer extends PyBuiltinMethodNarrow {
      public frozenset_symmetric_difference_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Return the symmetric difference of two sets as a new set.\n\n(i.e. all elements that are in exactly one of the sets.)";
      }

      public frozenset_symmetric_difference_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the symmetric difference of two sets as a new set.\n\n(i.e. all elements that are in exactly one of the sets.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_symmetric_difference_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFrozenSet)this.self).frozenset_symmetric_difference(var1);
      }
   }

   private static class frozenset_intersection_exposer extends PyBuiltinMethod {
      public frozenset_intersection_exposer(String var1) {
         super(var1);
         super.doc = "Return the intersection of two or more sets as a new set.\n\n(i.e. elements that are common to all of the sets.)";
      }

      public frozenset_intersection_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the intersection of two or more sets as a new set.\n\n(i.e. elements that are common to all of the sets.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_intersection_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PyFrozenSet)this.self).frozenset_intersection(var1, var2);
      }
   }

   private static class frozenset_issubset_exposer extends PyBuiltinMethodNarrow {
      public frozenset_issubset_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Report whether another set contains this set.";
      }

      public frozenset_issubset_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Report whether another set contains this set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_issubset_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFrozenSet)this.self).frozenset_issubset(var1);
      }
   }

   private static class frozenset_issuperset_exposer extends PyBuiltinMethodNarrow {
      public frozenset_issuperset_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Report whether this set contains another set.";
      }

      public frozenset_issuperset_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Report whether this set contains another set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_issuperset_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFrozenSet)this.self).frozenset_issuperset(var1);
      }
   }

   private static class frozenset_isdisjoint_exposer extends PyBuiltinMethodNarrow {
      public frozenset_isdisjoint_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Return True if two sets have a null intersection.";
      }

      public frozenset_isdisjoint_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return True if two sets have a null intersection.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_isdisjoint_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyFrozenSet)this.self).frozenset_isdisjoint(var1);
      }
   }

   private static class frozenset___len___exposer extends PyBuiltinMethodNarrow {
      public frozenset___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public frozenset___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyFrozenSet)this.self).frozenset___len__());
      }
   }

   private static class frozenset___reduce___exposer extends PyBuiltinMethodNarrow {
      public frozenset___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return state information for pickling.";
      }

      public frozenset___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return state information for pickling.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyFrozenSet)this.self).frozenset___reduce__();
      }
   }

   private static class frozenset___hash___exposer extends PyBuiltinMethodNarrow {
      public frozenset___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public frozenset___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__hash__() <==> hash(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PyFrozenSet)this.self).frozenset___hash__());
      }
   }

   private static class frozenset_toString_exposer extends PyBuiltinMethodNarrow {
      public frozenset_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public frozenset_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new frozenset_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyFrozenSet)this.self).frozenset_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyFrozenSet.frozenset___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new frozenset___cmp___exposer("__cmp__"), new frozenset___ne___exposer("__ne__"), new frozenset___eq___exposer("__eq__"), new frozenset___or___exposer("__or__"), new frozenset___xor___exposer("__xor__"), new frozenset___sub___exposer("__sub__"), new frozenset___and___exposer("__and__"), new frozenset___lt___exposer("__lt__"), new frozenset___gt___exposer("__gt__"), new frozenset___ge___exposer("__ge__"), new frozenset___le___exposer("__le__"), new frozenset___iter___exposer("__iter__"), new frozenset___contains___exposer("__contains__"), new frozenset_copy_exposer("copy"), new frozenset_union_exposer("union"), new frozenset_difference_exposer("difference"), new frozenset_symmetric_difference_exposer("symmetric_difference"), new frozenset_intersection_exposer("intersection"), new frozenset_issubset_exposer("issubset"), new frozenset_issuperset_exposer("issuperset"), new frozenset_isdisjoint_exposer("isdisjoint"), new frozenset___len___exposer("__len__"), new frozenset___reduce___exposer("__reduce__"), new frozenset___hash___exposer("__hash__"), new frozenset_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("frozenset", PyFrozenSet.class, PyObject.class, (boolean)1, "frozenset() -> empty frozenset object\nfrozenset(iterable) -> frozenset object\n\nBuild an immutable unordered collection of unique elements.", var1, var2, new exposed___new__());
      }
   }
}
