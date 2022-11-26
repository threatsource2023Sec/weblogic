package org.python.core;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class BaseSet extends PyObject implements Set, Traverseproc {
   protected Set _set;

   protected BaseSet(PyType type, Set set) {
      super(type);
      this._set = set;
   }

   public Set getSet() {
      return this._set;
   }

   protected void _update(PyObject data) {
      _update(this._set, data);
   }

   protected void _update(PyObject[] args) {
      _update(this._set, args);
   }

   protected static Set _update(Set set, PyObject data) {
      if (data == null) {
         return set;
      } else {
         if (data instanceof BaseSet) {
            set.addAll(((BaseSet)data)._set);
         } else {
            Iterator var2 = data.asIterable().iterator();

            while(var2.hasNext()) {
               PyObject item = (PyObject)var2.next();
               set.add(item);
            }
         }

         return set;
      }
   }

   protected static Set _update(Set set, PyObject[] data) {
      if (data == null) {
         return set;
      } else {
         PyObject[] var2 = data;
         int var3 = data.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject item = var2[var4];
            set.add(item);
         }

         return set;
      }
   }

   public PyObject __or__(PyObject other) {
      return this.baseset___or__(other);
   }

   final PyObject baseset___or__(PyObject other) {
      return !(other instanceof BaseSet) ? null : this.baseset_union(other);
   }

   public PyObject __and__(PyObject other) {
      return this.baseset___and__(other);
   }

   final PyObject baseset___and__(PyObject other) {
      return !(other instanceof BaseSet) ? null : this.baseset_intersection(other);
   }

   public PyObject __sub__(PyObject other) {
      return this.baseset___sub__(other);
   }

   final PyObject baseset___sub__(PyObject other) {
      return !(other instanceof BaseSet) ? null : this.baseset_difference(other);
   }

   public PyObject difference(PyObject other) {
      return this.baseset_difference(other);
   }

   final PyObject baseset_difference(PyObject other) {
      return this.baseset_difference(new PyObject[]{other});
   }

   final PyObject baseset_difference(PyObject[] args) {
      if (args.length == 0) {
         return makeNewSet(this.getType(), this);
      } else {
         BaseSet o = makeNewSet(this.getType(), this);
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject item = var3[var5];
            BaseSet bs = args[0] instanceof BaseSet ? (BaseSet)item : new PySet(item);
            Set set = ((BaseSet)bs)._set;
            Iterator var9 = set.iterator();

            while(var9.hasNext()) {
               PyObject p = (PyObject)var9.next();
               if (this._set.contains(p)) {
                  o._set.remove(p);
               }
            }
         }

         return o;
      }
   }

   public PyObject __xor__(PyObject other) {
      return this.baseset___xor__(other);
   }

   final PyObject baseset___xor__(PyObject other) {
      return !(other instanceof BaseSet) ? null : this.baseset_symmetric_difference(other);
   }

   public PyObject symmetric_difference(PyObject other) {
      return this.baseset_symmetric_difference(other);
   }

   final PyObject baseset_symmetric_difference(PyObject other) {
      BaseSet bs = other instanceof BaseSet ? (BaseSet)other : new PySet(other);
      BaseSet o = makeNewSet(this.getType());
      Iterator var4 = this._set.iterator();

      PyObject p;
      while(var4.hasNext()) {
         p = (PyObject)var4.next();
         if (!((BaseSet)bs)._set.contains(p)) {
            o._set.add(p);
         }
      }

      var4 = ((BaseSet)bs)._set.iterator();

      while(var4.hasNext()) {
         p = (PyObject)var4.next();
         if (!this._set.contains(p)) {
            o._set.add(p);
         }
      }

      return o;
   }

   public abstract int hashCode();

   public int __len__() {
      return this.baseset___len__();
   }

   final int baseset___len__() {
      return this._set.size();
   }

   public boolean __nonzero__() {
      return !this._set.isEmpty();
   }

   public PyObject __iter__() {
      return this.baseset___iter__();
   }

   final PyObject baseset___iter__() {
      return new PyIterator() {
         private int size = BaseSet.this.size();
         private Iterator iterator;

         {
            this.iterator = BaseSet.this._set.iterator();
         }

         public PyObject __iternext__() {
            return this.iterator.hasNext() ? (PyObject)this.iterator.next() : null;
         }
      };
   }

   public boolean __contains__(PyObject other) {
      return this.baseset___contains__(other);
   }

   final boolean baseset___contains__(PyObject other) {
      try {
         return this._set.contains(other);
      } catch (PyException var4) {
         PyFrozenSet frozen = this.asFrozen(var4, other);
         return this._set.contains(frozen);
      }
   }

   public int __cmp__(PyObject other) {
      return this.baseset___cmp__(other);
   }

   final int baseset___cmp__(PyObject other) {
      throw Py.TypeError("cannot compare sets using cmp()");
   }

   public PyObject __eq__(PyObject other) {
      return this.baseset___eq__(other);
   }

   final PyObject baseset___eq__(PyObject other) {
      return other instanceof BaseSet ? Py.newBoolean(this._set.equals(((BaseSet)other)._set)) : Py.False;
   }

   public PyObject __ne__(PyObject other) {
      return this.baseset___ne__(other);
   }

   final PyObject baseset___ne__(PyObject other) {
      return other instanceof BaseSet ? Py.newBoolean(!this._set.equals(((BaseSet)other)._set)) : Py.True;
   }

   public PyObject __le__(PyObject other) {
      return this.baseset___le__(other);
   }

   final PyObject baseset___le__(PyObject other) {
      return this.baseset_issubset(this.asBaseSet(other));
   }

   public PyObject __ge__(PyObject other) {
      return this.baseset___ge__(other);
   }

   final PyObject baseset___ge__(PyObject other) {
      return this.baseset_issuperset(this.asBaseSet(other));
   }

   public PyObject __lt__(PyObject other) {
      return this.baseset___lt__(other);
   }

   final PyObject baseset___lt__(PyObject other) {
      BaseSet bs = this.asBaseSet(other);
      return Py.newBoolean(this.size() < bs.size() && this.baseset_issubset(other).__nonzero__());
   }

   public PyObject __gt__(PyObject other) {
      return this.baseset___gt__(other);
   }

   final PyObject baseset___gt__(PyObject other) {
      BaseSet bs = this.asBaseSet(other);
      return Py.newBoolean(this.size() > bs.size() && this.baseset_issuperset(other).__nonzero__());
   }

   public PyObject __reduce__() {
      return this.baseset___reduce__();
   }

   final PyObject baseset___reduce__() {
      PyObject args = new PyTuple(new PyObject[]{new PyList(this)});
      PyObject dict = this.__findattr__("__dict__");
      if (dict == null) {
         dict = Py.None;
      }

      return new PyTuple(new PyObject[]{this.getType(), args, dict});
   }

   final PyObject baseset_union(PyObject other) {
      BaseSet result = makeNewSet(this.getType(), this);
      result._update(other);
      return result;
   }

   final PyObject baseset_union(PyObject[] args) {
      BaseSet result = makeNewSet(this.getType(), this);
      PyObject[] var3 = args;
      int var4 = args.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject item = var3[var5];
         result._update(item);
      }

      return result;
   }

   final PyObject baseset_intersection(PyObject other) {
      if (!(other instanceof BaseSet)) {
         other = new PySet((PyObject)other);
      }

      Object little;
      Object big;
      if (this.size() <= __builtin__.len((PyObject)other)) {
         little = this;
         big = other;
      } else {
         little = other;
         big = this;
      }

      PyObject common = __builtin__.filter(((PyObject)big).__getattr__("__contains__"), (PyObject)little);
      return makeNewSet(this.getType(), common);
   }

   final PyObject baseset_intersection(PyObject[] args) {
      BaseSet result = makeNewSet(this.getType(), this);
      if (args.length == 0) {
         return result;
      } else {
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject other = var3[var5];
            result = (BaseSet)result.baseset_intersection(other);
         }

         return result;
      }
   }

   final PyObject baseset_copy() {
      BaseSet copy = makeNewSet(this.getType(), this);
      return copy;
   }

   final PyObject baseset_issubset(PyObject other) {
      BaseSet bs = other instanceof BaseSet ? (BaseSet)other : new PySet(other);
      if (this.size() > ((BaseSet)bs).size()) {
         return Py.False;
      } else {
         Iterator var3 = this._set.iterator();

         Object p;
         do {
            if (!var3.hasNext()) {
               return Py.True;
            }

            p = var3.next();
         } while(((BaseSet)bs)._set.contains(p));

         return Py.False;
      }
   }

   final PyObject baseset_issuperset(PyObject other) {
      BaseSet bs = other instanceof BaseSet ? (BaseSet)other : new PySet(other);
      return ((BaseSet)bs).baseset_issubset(this);
   }

   final PyObject baseset_isdisjoint(PyObject other) {
      BaseSet bs = other instanceof BaseSet ? (BaseSet)other : new PySet(other);
      return Collections.disjoint(this._set, ((BaseSet)bs)._set) ? Py.True : Py.False;
   }

   public String toString() {
      return this.baseset_toString();
   }

   final String baseset_toString() {
      String name = this.getType().fastGetName();
      ThreadState ts = Py.getThreadState();
      if (!ts.enterRepr(this)) {
         return name + "(...)";
      } else {
         StringBuilder buf = (new StringBuilder(name)).append("([");
         Iterator i = this._set.iterator();

         while(i.hasNext()) {
            buf.append(((PyObject)i.next()).__repr__().toString());
            if (i.hasNext()) {
               buf.append(", ");
            }
         }

         buf.append("])");
         ts.exitRepr(this);
         return buf.toString();
      }
   }

   protected final BaseSet asBaseSet(PyObject other) {
      if (other instanceof BaseSet) {
         return (BaseSet)other;
      } else {
         throw Py.TypeError("can only compare to a set");
      }
   }

   protected final PyFrozenSet asFrozen(PyException pye, PyObject value) {
      if (value instanceof BaseSet && pye.match(Py.TypeError)) {
         PyFrozenSet tmp = new PyFrozenSet();
         tmp._set = ((BaseSet)value)._set;
         return tmp;
      } else {
         throw pye;
      }
   }

   protected static BaseSet makeNewSet(PyType type) {
      return makeNewSet(type, (PyObject)null);
   }

   protected static BaseSet makeNewSet(PyType type, PyObject iterable) {
      Object so;
      if (type == PySet.TYPE) {
         so = new PySet(iterable);
      } else if (type == PyFrozenSet.TYPE) {
         so = new PyFrozenSet(iterable);
      } else if (Py.isSubClass(type, PySet.TYPE)) {
         so = (BaseSet)((BaseSet)type.__call__((PyObject)(iterable == null ? Py.EmptyTuple : iterable)));
      } else {
         so = new PyFrozenSetDerived(type, iterable);
      }

      return (BaseSet)so;
   }

   public int size() {
      return this._set.size();
   }

   public void clear() {
      this._set.clear();
   }

   public boolean isEmpty() {
      return this._set.isEmpty();
   }

   public boolean add(Object o) {
      return this._set.add(Py.java2py(o));
   }

   public boolean contains(Object o) {
      return this._set.contains(Py.java2py(o));
   }

   public boolean remove(Object o) {
      return this._set.remove(Py.java2py(o));
   }

   public boolean addAll(Collection c) {
      boolean added = false;

      Object object;
      for(Iterator var3 = c.iterator(); var3.hasNext(); added |= this.add(object)) {
         object = var3.next();
      }

      return added;
   }

   public boolean containsAll(Collection c) {
      Iterator var2 = c.iterator();

      Object object;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         object = var2.next();
      } while(this._set.contains(Py.java2py(object)));

      return false;
   }

   public boolean removeAll(Collection c) {
      boolean removed = false;

      Object object;
      for(Iterator var3 = c.iterator(); var3.hasNext(); removed |= this._set.remove(Py.java2py(object))) {
         object = var3.next();
      }

      return removed;
   }

   public boolean retainAll(Collection c) {
      boolean modified = false;
      Iterator e = this.iterator();

      while(e.hasNext()) {
         if (!c.contains(e.next())) {
            e.remove();
            modified = true;
         }
      }

      return modified;
   }

   public Iterator iterator() {
      return new Iterator() {
         Iterator real;

         {
            this.real = BaseSet.this._set.iterator();
         }

         public boolean hasNext() {
            return this.real.hasNext();
         }

         public Object next() {
            return Py.tojava((PyObject)this.real.next(), Object.class);
         }

         public void remove() {
            this.real.remove();
         }
      };
   }

   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   public Object[] toArray(Object[] a) {
      int size = this.size();
      if (a.length < size) {
         a = (Object[])((Object[])Array.newInstance(a.getClass().getComponentType(), size));
      }

      Iterator it = this.iterator();

      for(int i = 0; i < size; ++i) {
         a[i] = it.next();
      }

      if (a.length > size) {
         a[size] = null;
      }

      return a;
   }

   public int traverse(Visitproc visit, Object arg) {
      Iterator var3 = this._set.iterator();

      while(var3.hasNext()) {
         PyObject ob = (PyObject)var3.next();
         if (ob != null) {
            int retValue = visit.visit(ob, arg);
            if (retValue != 0) {
               return retValue;
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this._set.contains(ob);
   }
}
