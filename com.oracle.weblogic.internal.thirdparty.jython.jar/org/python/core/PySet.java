package org.python.core;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.util.Generic;

@ExposedType(
   name = "set",
   base = PyObject.class,
   doc = "set() -> new empty set object\nset(iterable) -> new set object\n\nBuild an unordered collection of unique elements."
)
public class PySet extends BaseSet {
   public static final PyType TYPE;

   public PySet() {
      this(TYPE);
   }

   public PySet(PyType type) {
      super(type, Generic.concurrentSet());
      TYPE.object___setattr__("__hash__", Py.None);
   }

   public PySet(PyObject data) {
      super(TYPE, _update(Generic.concurrentSet(), data));
      TYPE.object___setattr__("__hash__", Py.None);
   }

   public PySet(PyObject[] data) {
      super(TYPE, _update(Generic.concurrentSet(), data));
      TYPE.object___setattr__("__hash__", Py.None);
   }

   public PySet(Set backing_set, PyObject data) {
      super(TYPE, _update(backing_set, data));
      TYPE.object___setattr__("__hash__", Py.None);
   }

   public PySet(PyType type, Set backing_set, PyObject data) {
      super(type, _update(backing_set, data));
      TYPE.object___setattr__("__hash__", Py.None);
   }

   @ExposedNew
   final void set___init__(PyObject[] args, String[] kwds) {
      if (args.length <= 1 && kwds.length == 0) {
         if (args.length != 0) {
            this._set.clear();
            this._update(args[0]);
         }
      } else {
         throw PyBuiltinCallable.DefaultInfo.unexpectedCall(args.length, false, "Set", 0, 1);
      }
   }

   final PyObject set___cmp__(PyObject o) {
      return new PyInteger(this.baseset___cmp__(o));
   }

   final PyObject set___ne__(PyObject o) {
      return this.baseset___ne__(o);
   }

   final PyObject set___eq__(PyObject o) {
      return this.baseset___eq__(o);
   }

   final PyObject set___or__(PyObject o) {
      return this.baseset___or__(o);
   }

   final PyObject set___xor__(PyObject o) {
      return this.baseset___xor__(o);
   }

   final PyObject set___sub__(PyObject o) {
      return this.baseset___sub__(o);
   }

   final PyObject set___and__(PyObject o) {
      return this.baseset___and__(o);
   }

   final PyObject set___lt__(PyObject o) {
      return this.baseset___lt__(o);
   }

   final PyObject set___gt__(PyObject o) {
      return this.baseset___gt__(o);
   }

   final PyObject set___ge__(PyObject o) {
      return this.baseset___ge__(o);
   }

   final PyObject set___le__(PyObject o) {
      return this.baseset___le__(o);
   }

   final PyObject set___iter__() {
      return this.baseset___iter__();
   }

   final boolean set___contains__(PyObject item) {
      return this.baseset___contains__(item);
   }

   final PyObject set_copy() {
      return this.baseset_copy();
   }

   final PyObject set_union(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("union() takes no keyword arguments");
      } else {
         return this.baseset_union(args);
      }
   }

   final PyObject set_difference(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("difference() takes no keyword arguments");
      } else {
         return this.baseset_difference(args);
      }
   }

   final PyObject set_symmetric_difference(PyObject set) {
      return this.baseset_symmetric_difference(set);
   }

   final PyObject set_intersection(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("intersection() takes no keyword arguments");
      } else {
         return this.baseset_intersection(args);
      }
   }

   final PyObject set_issubset(PyObject set) {
      return this.baseset_issubset(set);
   }

   final PyObject set_issuperset(PyObject set) {
      return this.baseset_issuperset(set);
   }

   final PyObject set_isdisjoint(PyObject set) {
      return this.baseset_isdisjoint(set);
   }

   final int set___len__() {
      return this.baseset___len__();
   }

   final PyObject set___reduce__() {
      return this.baseset___reduce__();
   }

   public PyObject __ior__(PyObject other) {
      return this.set___ior__(other);
   }

   final PyObject set___ior__(PyObject other) {
      if (!(other instanceof BaseSet)) {
         return null;
      } else {
         this._set.addAll(((BaseSet)other)._set);
         return this;
      }
   }

   public PyObject __ixor__(PyObject other) {
      return this.set___ixor__(other);
   }

   final PyObject set___ixor__(PyObject other) {
      if (!(other instanceof BaseSet)) {
         return null;
      } else {
         this.set_symmetric_difference_update(other);
         return this;
      }
   }

   public PyObject __iand__(PyObject other) {
      return this.set___iand__(other);
   }

   final PyObject set___iand__(PyObject other) {
      if (!(other instanceof BaseSet)) {
         return null;
      } else {
         this._set = ((BaseSet)this.__and__(other))._set;
         return this;
      }
   }

   public PyObject __isub__(PyObject other) {
      return this.set___isub__(other);
   }

   final PyObject set___isub__(PyObject other) {
      if (!(other instanceof BaseSet)) {
         return null;
      } else {
         this._set.removeAll(((BaseSet)other)._set);
         return this;
      }
   }

   public int hashCode() {
      return this.set___hash__();
   }

   final int set___hash__() {
      throw Py.TypeError("set objects are unhashable");
   }

   final void set_add(PyObject o) {
      this._set.add(o);
   }

   final void set_remove(PyObject o) {
      boolean b = false;

      try {
         b = this._set.remove(o);
      } catch (PyException var5) {
         PyObject frozen = this.asFrozen(var5, o);
         b = this._set.remove(frozen);
      }

      if (!b) {
         throw new PyException(Py.KeyError, o);
      }
   }

   final void set_discard(PyObject o) {
      try {
         this._set.remove(o);
      } catch (PyException var4) {
         PyObject frozen = this.asFrozen(var4, o);
         this._set.remove(frozen);
      }

   }

   final synchronized PyObject set_pop() {
      Iterator iterator = this._set.iterator();

      try {
         Object first = iterator.next();
         this._set.remove(first);
         return (PyObject)first;
      } catch (NoSuchElementException var3) {
         throw new PyException(Py.KeyError, "pop from an empty set");
      }
   }

   final void set_clear() {
      this._set.clear();
   }

   final void set_update(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("update() takes no keyword arguments");
      } else {
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject item = var3[var5];
            this._update(item);
         }

      }
   }

   final void set_intersection_update(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("intersection_update() takes no keyword arguments");
      } else {
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject other = var3[var5];
            if (other instanceof BaseSet) {
               this.set___iand__(other);
            } else {
               BaseSet set = (BaseSet)this.baseset_intersection(other);
               this._set = set._set;
            }
         }

      }
   }

   final void set_symmetric_difference_update(PyObject other) {
      if (this == other) {
         this.set_clear();
      } else {
         BaseSet bs = other instanceof BaseSet ? (BaseSet)other : new PySet(other);
         Iterator var3 = ((BaseSet)bs)._set.iterator();

         while(var3.hasNext()) {
            PyObject o = (PyObject)var3.next();
            if (this._set.contains(o)) {
               this._set.remove(o);
            } else {
               this._set.add(o);
            }
         }

      }
   }

   final void set_difference_update(PyObject[] args, String[] kws) {
      if (kws.length > 0) {
         throw Py.TypeError("difference_update() takes no keyword arguments");
      } else if (args.length != 0) {
         PyObject[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject other = var3[var5];
            if (other instanceof BaseSet) {
               this.set___isub__(other);
            }

            Iterator var7 = other.asIterable().iterator();

            while(var7.hasNext()) {
               PyObject o = (PyObject)var7.next();
               if (this.__contains__(o)) {
                  this._set.remove(o);
               }
            }
         }

      }
   }

   final String set_toString() {
      return this.baseset_toString();
   }

   static {
      PyType.addBuilder(PySet.class, new PyExposer());
      TYPE = PyType.fromClass(PySet.class);
   }

   private static class set___init___exposer extends PyBuiltinMethod {
      public set___init___exposer(String var1) {
         super(var1);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public set___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__init__(...) initializes x; see help(type(x)) for signature";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PySet)this.self).set___init__(var1, var2);
         return Py.None;
      }
   }

   private static class set___cmp___exposer extends PyBuiltinMethodNarrow {
      public set___cmp___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public set___cmp___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__cmp__(y) <==> cmp(x,y)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___cmp___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___cmp__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___ne___exposer extends PyBuiltinMethodNarrow {
      public set___ne___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public set___ne___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ne__(y) <==> x!=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___ne___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___ne__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___eq___exposer extends PyBuiltinMethodNarrow {
      public set___eq___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public set___eq___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__eq__(y) <==> x==y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___eq___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___eq__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___or___exposer extends PyBuiltinMethodNarrow {
      public set___or___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public set___or___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__or__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___or___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___or__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___xor___exposer extends PyBuiltinMethodNarrow {
      public set___xor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public set___xor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__xor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___xor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___xor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___sub___exposer extends PyBuiltinMethodNarrow {
      public set___sub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public set___sub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__sub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___sub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___sub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___and___exposer extends PyBuiltinMethodNarrow {
      public set___and___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public set___and___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__and__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___and___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___and__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___lt___exposer extends PyBuiltinMethodNarrow {
      public set___lt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public set___lt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__lt__(y) <==> x<y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___lt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___lt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___gt___exposer extends PyBuiltinMethodNarrow {
      public set___gt___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public set___gt___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__gt__(y) <==> x>y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___gt___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___gt__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___ge___exposer extends PyBuiltinMethodNarrow {
      public set___ge___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public set___ge___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ge__(y) <==> x>=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___ge___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___ge__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___le___exposer extends PyBuiltinMethodNarrow {
      public set___le___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public set___le___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__le__(y) <==> x<=y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___le___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___le__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___iter___exposer extends PyBuiltinMethodNarrow {
      public set___iter___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public set___iter___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iter__() <==> iter(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___iter___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PySet)this.self).set___iter__();
      }
   }

   private static class set___contains___exposer extends PyBuiltinMethodNarrow {
      public set___contains___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__contains__(y) <==> y in x.";
      }

      public set___contains___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__contains__(y) <==> y in x.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___contains___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return Py.newBoolean(((PySet)this.self).set___contains__(var1));
      }
   }

   private static class set_copy_exposer extends PyBuiltinMethodNarrow {
      public set_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return a shallow copy of a set.";
      }

      public set_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return a shallow copy of a set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PySet)this.self).set_copy();
      }
   }

   private static class set_union_exposer extends PyBuiltinMethod {
      public set_union_exposer(String var1) {
         super(var1);
         super.doc = "Return the union of sets as a new set.\n\n(i.e. all elements that are in either set.)";
      }

      public set_union_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the union of sets as a new set.\n\n(i.e. all elements that are in either set.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_union_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PySet)this.self).set_union(var1, var2);
      }
   }

   private static class set_difference_exposer extends PyBuiltinMethod {
      public set_difference_exposer(String var1) {
         super(var1);
         super.doc = "Return the difference of two or more sets as a new set.\n\n(i.e. all elements that are in this set but not the others.)";
      }

      public set_difference_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the difference of two or more sets as a new set.\n\n(i.e. all elements that are in this set but not the others.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_difference_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PySet)this.self).set_difference(var1, var2);
      }
   }

   private static class set_symmetric_difference_exposer extends PyBuiltinMethodNarrow {
      public set_symmetric_difference_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Return the symmetric difference of two sets as a new set.\n\n(i.e. all elements that are in exactly one of the sets.)";
      }

      public set_symmetric_difference_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the symmetric difference of two sets as a new set.\n\n(i.e. all elements that are in exactly one of the sets.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_symmetric_difference_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySet)this.self).set_symmetric_difference(var1);
      }
   }

   private static class set_intersection_exposer extends PyBuiltinMethod {
      public set_intersection_exposer(String var1) {
         super(var1);
         super.doc = "Return the intersection of two or more sets as a new set.\n\n(i.e. elements that are common to all of the sets.)";
      }

      public set_intersection_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return the intersection of two or more sets as a new set.\n\n(i.e. elements that are common to all of the sets.)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_intersection_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PySet)this.self).set_intersection(var1, var2);
      }
   }

   private static class set_issubset_exposer extends PyBuiltinMethodNarrow {
      public set_issubset_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Report whether another set contains this set.";
      }

      public set_issubset_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Report whether another set contains this set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_issubset_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySet)this.self).set_issubset(var1);
      }
   }

   private static class set_issuperset_exposer extends PyBuiltinMethodNarrow {
      public set_issuperset_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Report whether this set contains another set.";
      }

      public set_issuperset_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Report whether this set contains another set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_issuperset_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySet)this.self).set_issuperset(var1);
      }
   }

   private static class set_isdisjoint_exposer extends PyBuiltinMethodNarrow {
      public set_isdisjoint_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Return True if two sets have a null intersection.";
      }

      public set_isdisjoint_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return True if two sets have a null intersection.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_isdisjoint_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PySet)this.self).set_isdisjoint(var1);
      }
   }

   private static class set___len___exposer extends PyBuiltinMethodNarrow {
      public set___len___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__len__() <==> len(x)";
      }

      public set___len___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__len__() <==> len(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___len___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PySet)this.self).set___len__());
      }
   }

   private static class set___reduce___exposer extends PyBuiltinMethodNarrow {
      public set___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Return state information for pickling.";
      }

      public set___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Return state information for pickling.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PySet)this.self).set___reduce__();
      }
   }

   private static class set___ior___exposer extends PyBuiltinMethodNarrow {
      public set___ior___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ior__(y) <==> x|y";
      }

      public set___ior___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ior__(y) <==> x|y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___ior___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___ior__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___ixor___exposer extends PyBuiltinMethodNarrow {
      public set___ixor___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__ixor__(y) <==> x^y";
      }

      public set___ixor___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__ixor__(y) <==> x^y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___ixor___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___ixor__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___iand___exposer extends PyBuiltinMethodNarrow {
      public set___iand___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__iand__(y) <==> x&y";
      }

      public set___iand___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__iand__(y) <==> x&y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___iand___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___iand__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___isub___exposer extends PyBuiltinMethodNarrow {
      public set___isub___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__isub__(y) <==> x-y";
      }

      public set___isub___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__isub__(y) <==> x-y";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___isub___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         PyObject var10000 = ((PySet)this.self).set___isub__(var1);
         return var10000 == null ? Py.NotImplemented : var10000;
      }
   }

   private static class set___hash___exposer extends PyBuiltinMethodNarrow {
      public set___hash___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public set___hash___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set___hash___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return Py.newInteger(((PySet)this.self).set___hash__());
      }
   }

   private static class set_add_exposer extends PyBuiltinMethodNarrow {
      public set_add_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Add an element to a set.\n\nThis has no effect if the element is already present.";
      }

      public set_add_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Add an element to a set.\n\nThis has no effect if the element is already present.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_add_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PySet)this.self).set_add(var1);
         return Py.None;
      }
   }

   private static class set_remove_exposer extends PyBuiltinMethodNarrow {
      public set_remove_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Remove an element from a set; it must be a member.\n\nIf the element is not a member, raise a KeyError.";
      }

      public set_remove_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Remove an element from a set; it must be a member.\n\nIf the element is not a member, raise a KeyError.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_remove_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PySet)this.self).set_remove(var1);
         return Py.None;
      }
   }

   private static class set_discard_exposer extends PyBuiltinMethodNarrow {
      public set_discard_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Remove an element from a set if it is a member.\n\nIf the element is not a member, do nothing.";
      }

      public set_discard_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Remove an element from a set if it is a member.\n\nIf the element is not a member, do nothing.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_discard_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PySet)this.self).set_discard(var1);
         return Py.None;
      }
   }

   private static class set_pop_exposer extends PyBuiltinMethodNarrow {
      public set_pop_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Remove and return an arbitrary set element.\nRaises KeyError if the set is empty.";
      }

      public set_pop_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Remove and return an arbitrary set element.\nRaises KeyError if the set is empty.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_pop_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PySet)this.self).set_pop();
      }
   }

   private static class set_clear_exposer extends PyBuiltinMethodNarrow {
      public set_clear_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "Remove all elements from this set.";
      }

      public set_clear_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Remove all elements from this set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_clear_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         ((PySet)this.self).set_clear();
         return Py.None;
      }
   }

   private static class set_update_exposer extends PyBuiltinMethod {
      public set_update_exposer(String var1) {
         super(var1);
         super.doc = "Update a set with the union of itself and others.";
      }

      public set_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Update a set with the union of itself and others.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PySet)this.self).set_update(var1, var2);
         return Py.None;
      }
   }

   private static class set_intersection_update_exposer extends PyBuiltinMethod {
      public set_intersection_update_exposer(String var1) {
         super(var1);
         super.doc = "Update a set with the intersection of itself and another.";
      }

      public set_intersection_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Update a set with the intersection of itself and another.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_intersection_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PySet)this.self).set_intersection_update(var1, var2);
         return Py.None;
      }
   }

   private static class set_symmetric_difference_update_exposer extends PyBuiltinMethodNarrow {
      public set_symmetric_difference_update_exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "Update a set with the symmetric difference of itself and another.";
      }

      public set_symmetric_difference_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Update a set with the symmetric difference of itself and another.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_symmetric_difference_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         ((PySet)this.self).set_symmetric_difference_update(var1);
         return Py.None;
      }
   }

   private static class set_difference_update_exposer extends PyBuiltinMethod {
      public set_difference_update_exposer(String var1) {
         super(var1);
         super.doc = "Remove all elements of another set from this set.";
      }

      public set_difference_update_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "Remove all elements of another set from this set.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_difference_update_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PySet)this.self).set_difference_update(var1, var2);
         return Py.None;
      }
   }

   private static class set_toString_exposer extends PyBuiltinMethodNarrow {
      public set_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public set_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__repr__() <==> repr(x)";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new set_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PySet)this.self).set_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PySet var4 = new PySet(this.for_type);
         if (var1) {
            var4.set___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PySetDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new set___init___exposer("__init__"), new set___cmp___exposer("__cmp__"), new set___ne___exposer("__ne__"), new set___eq___exposer("__eq__"), new set___or___exposer("__or__"), new set___xor___exposer("__xor__"), new set___sub___exposer("__sub__"), new set___and___exposer("__and__"), new set___lt___exposer("__lt__"), new set___gt___exposer("__gt__"), new set___ge___exposer("__ge__"), new set___le___exposer("__le__"), new set___iter___exposer("__iter__"), new set___contains___exposer("__contains__"), new set_copy_exposer("copy"), new set_union_exposer("union"), new set_difference_exposer("difference"), new set_symmetric_difference_exposer("symmetric_difference"), new set_intersection_exposer("intersection"), new set_issubset_exposer("issubset"), new set_issuperset_exposer("issuperset"), new set_isdisjoint_exposer("isdisjoint"), new set___len___exposer("__len__"), new set___reduce___exposer("__reduce__"), new set___ior___exposer("__ior__"), new set___ixor___exposer("__ixor__"), new set___iand___exposer("__iand__"), new set___isub___exposer("__isub__"), new set___hash___exposer("__hash__"), new set_add_exposer("add"), new set_remove_exposer("remove"), new set_discard_exposer("discard"), new set_pop_exposer("pop"), new set_clear_exposer("clear"), new set_update_exposer("update"), new set_intersection_update_exposer("intersection_update"), new set_symmetric_difference_update_exposer("symmetric_difference_update"), new set_difference_update_exposer("difference_update"), new set_toString_exposer("__repr__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("set", PySet.class, PyObject.class, (boolean)1, "set() -> new empty set object\nset(iterable) -> new set object\n\nBuild an unordered collection of unique elements.", var1, var2, new exposed___new__());
      }
   }
}
