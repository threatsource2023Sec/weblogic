package org.python.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;

class JavaProxySet {
   private static final SetMethod cmpProxy = new SetMethod("__cmp__", 1) {
      public PyObject __call__(PyObject value) {
         throw Py.TypeError("cannot compare sets using cmp()");
      }
   };
   private static final SetMethod eqProxy = new SetMethod("__eq__", 1) {
      public PyObject __call__(PyObject other) {
         return Py.newBoolean(this.isEqual(other));
      }
   };
   private static final SetMethod ltProxy = new SetMethod("__lt__", 1) {
      public PyObject __call__(PyObject other) {
         return Py.newBoolean(!this.isEqual(other) && this.isSubset(other));
      }
   };
   private static final SetMethod gtProxy = new SetMethod("__gt__", 1) {
      public PyObject __call__(PyObject other) {
         return Py.newBoolean(!this.isEqual(other) && this.isSuperset(other));
      }
   };
   private static final SetMethod isDisjointProxy = new SetMethod("isdisjoint", 1) {
      public PyObject __call__(PyObject other) {
         return Py.newBoolean(this.intersect(new Collection[]{JavaProxySet.getJavaCollection(other)}).size() == 0);
      }
   };
   private static final SetMethod differenceProxy = new SetMethodVarargs("difference") {
      public PyObject __call__(PyObject[] others) {
         return this.makePySet(this.difference(JavaProxySet.getCombinedJavaCollections(others)));
      }
   };
   private static final SetMethod differenceUpdateProxy = new SetMethodVarargs("difference_update") {
      public PyObject __call__(PyObject[] others) {
         this.differenceUpdate(JavaProxySet.getCombinedJavaCollections(others));
         return Py.None;
      }
   };
   private static final SetMethod subProxy = new SetMethod("__sub__", 1) {
      public PyObject __call__(PyObject other) {
         return this.makePySet(this.difference(JavaProxySet.getJavaSet(this.self, "-", other)));
      }
   };
   private static final SetMethod isubProxy = new SetMethod("__isub__", 1) {
      public PyObject __call__(PyObject other) {
         this.differenceUpdate(JavaProxySet.getJavaSet(this.self, "-=", other));
         return this.self;
      }
   };
   private static final SetMethod intersectionProxy = new SetMethodVarargs("intersection") {
      public PyObject __call__(PyObject[] others) {
         return this.makePySet(this.intersect(JavaProxySet.getJavaCollections(others)));
      }
   };
   private static final SetMethod intersectionUpdateProxy = new SetMethodVarargs("intersection_update") {
      public PyObject __call__(PyObject[] others) {
         this.intersectUpdate(JavaProxySet.getJavaCollections(others));
         return Py.None;
      }
   };
   private static final SetMethod andProxy = new SetMethod("__and__", 1) {
      public PyObject __call__(PyObject other) {
         return this.makePySet(this.intersect(new Collection[]{JavaProxySet.getJavaSet(this.self, "&", other)}));
      }
   };
   private static final SetMethod iandProxy = new SetMethod("__iand__", 1) {
      public PyObject __call__(PyObject other) {
         this.intersectUpdate(new Collection[]{JavaProxySet.getJavaSet(this.self, "&=", other)});
         return this.self;
      }
   };
   private static final SetMethod symDiffProxy = new SetMethod("symmetric_difference", 1) {
      public PyObject __call__(PyObject other) {
         return this.makePySet(this.symDiff(JavaProxySet.getJavaCollection(other)));
      }
   };
   private static final SetMethod symDiffUpdateProxy = new SetMethod("symmetric_difference_update", 1) {
      public PyObject __call__(PyObject other) {
         this.symDiffUpdate(JavaProxySet.getJavaCollection(other));
         return Py.None;
      }
   };
   private static final SetMethod xorProxy = new SetMethod("__xor__", 1) {
      public PyObject __call__(PyObject other) {
         return this.makePySet(this.symDiff(JavaProxySet.getJavaSet(this.self, "^", other)));
      }
   };
   private static final SetMethod ixorProxy = new SetMethod("__ixor__", 1) {
      public PyObject __call__(PyObject other) {
         this.symDiffUpdate(JavaProxySet.getJavaSet(this.self, "^=", other));
         return this.self;
      }
   };
   private static final SetMethod unionProxy = new SetMethodVarargs("union") {
      public PyObject __call__(PyObject[] others) {
         return this.makePySet(this.union(JavaProxySet.getCombinedJavaCollections(others)));
      }
   };
   private static final SetMethod updateProxy = new SetMethodVarargs("update") {
      public PyObject __call__(PyObject[] others) {
         this.update(JavaProxySet.getCombinedJavaCollections(others));
         return Py.None;
      }
   };
   private static final SetMethod orProxy = new SetMethod("__or__", 1) {
      public PyObject __call__(PyObject other) {
         return this.makePySet(this.union(JavaProxySet.getJavaSet(this.self, "|", other)));
      }
   };
   private static final SetMethod iorProxy = new SetMethod("__ior__", 1) {
      public PyObject __call__(PyObject other) {
         this.update(JavaProxySet.getJavaSet(this.self, "|=", other));
         return this.self;
      }
   };
   private static final SetMethod deepcopyOverrideProxy = new SetMethod("__deepcopy__", 1) {
      public PyObject __call__(PyObject memo) {
         Set newSet = new HashSet();
         Iterator var3 = this.asSet().iterator();

         while(var3.hasNext()) {
            Object obj = var3.next();
            PyObject pyobj = Py.java2py(obj);
            PyObject newobj = pyobj.invoke("__deepcopy__", memo);
            newSet.add(newobj.__tojava__(Object.class));
         }

         return this.makePySet(newSet);
      }
   };
   private static final SetMethod reduceProxy = new SetMethod("__reduce__", 0) {
      public PyObject __call__() {
         PyObject args = new PyTuple(new PyObject[]{new PyList(new JavaIterator(this.asSet()))});
         PyObject dict = this.__findattr__("__dict__");
         if (dict == null) {
            dict = Py.None;
         }

         return new PyTuple(new PyObject[]{this.self.getType(), args, dict});
      }
   };
   private static final SetMethod containsProxy = new SetMethod("__contains__", 1) {
      public PyObject __call__(PyObject value) {
         return Py.newBoolean(this.asSet().contains(value.__tojava__(Object.class)));
      }
   };
   private static final SetMethod hashProxy = new SetMethod("__hash__", 0) {
      public PyObject __call__(PyObject value) {
         throw Py.TypeError(String.format("unhashable type: '%.200s'", this.self.getType().fastGetName()));
      }
   };
   private static final SetMethod discardProxy = new SetMethod("discard", 1) {
      public PyObject __call__(PyObject value) {
         this.asSet().remove(value.__tojava__(Object.class));
         return Py.None;
      }
   };
   private static final SetMethod popProxy = new SetMethod("pop", 0) {
      public PyObject __call__() {
         Set selfSet = this.asSet();
         Iterator it;
         if (selfSet instanceof NavigableSet) {
            it = ((NavigableSet)selfSet).descendingIterator();
         } else {
            it = selfSet.iterator();
         }

         try {
            PyObject value = Py.java2py(it.next());
            it.remove();
            return value;
         } catch (NoSuchElementException var4) {
            throw Py.KeyError("pop from an empty set");
         }
      }
   };
   private static final SetMethod removeOverrideProxy = new SetMethod("remove", 1) {
      public PyObject __call__(PyObject value) {
         boolean removed = this.asSet().remove(value.__tojava__(Object.class));
         if (!removed) {
            throw Py.KeyError(value);
         } else {
            return Py.None;
         }
      }
   };

   private static boolean isPySet(PyObject obj) {
      PyType type = obj.getType();
      return type.isSubType(PySet.TYPE) || type.isSubType(PyFrozenSet.TYPE);
   }

   private static Collection getJavaSet(PyObject self, String op, PyObject obj) {
      Object items;
      if (isPySet(obj)) {
         Set otherPySet = ((BaseSet)obj).getSet();
         items = new ArrayList(otherPySet.size());
         Iterator var5 = otherPySet.iterator();

         while(var5.hasNext()) {
            PyObject pyobj = (PyObject)var5.next();
            ((Collection)items).add(pyobj.__tojava__(Object.class));
         }
      } else {
         Object oj = obj.getJavaProxy();
         if (!(oj instanceof Set)) {
            throw Py.TypeError(String.format("unsupported operand type(s) for %s: '%.200s' and '%.200s'", op, self.getType().fastGetName(), obj.getType().fastGetName()));
         }

         Set jSet = (Set)oj;
         items = jSet;
      }

      return (Collection)items;
   }

   private static Collection getJavaCollection(PyObject obj) {
      Object oj = obj.getJavaProxy();
      Object items;
      Iterator var5;
      if (oj != null) {
         if (oj instanceof Collection) {
            Collection jCollection = (Collection)oj;
            items = jCollection;
         } else {
            if (!(oj instanceof Iterable)) {
               throw Py.TypeError(String.format("unsupported operand type(s): '%.200s'", obj.getType().fastGetName()));
            }

            items = new HashSet();
            var5 = ((Iterable)oj).iterator();

            while(var5.hasNext()) {
               Object item = var5.next();
               ((Collection)items).add(item);
            }
         }
      } else {
         items = new HashSet();
         var5 = obj.asIterable().iterator();

         while(var5.hasNext()) {
            PyObject pyobj = (PyObject)var5.next();
            ((Collection)items).add(pyobj.__tojava__(Object.class));
         }
      }

      return (Collection)items;
   }

   private static Collection[] getJavaCollections(PyObject[] objs) {
      Collection[] collections = new Collection[objs.length];
      int i = 0;
      PyObject[] var3 = objs;
      int var4 = objs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PyObject obj = var3[var5];
         collections[i++] = getJavaCollection(obj);
      }

      return collections;
   }

   private static Collection getCombinedJavaCollections(PyObject[] objs) {
      if (objs.length == 0) {
         return Collections.emptyList();
      } else if (objs.length == 1) {
         return getJavaCollection(objs[0]);
      } else {
         Set items = new HashSet();
         PyObject[] var2 = objs;
         int var3 = objs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject obj = var2[var4];
            Object oj = obj.getJavaProxy();
            Iterator var7;
            if (oj != null) {
               if (!(oj instanceof Iterable)) {
                  throw Py.TypeError(String.format("unsupported operand type(s): '%.200s'", obj.getType().fastGetName()));
               }

               var7 = ((Iterable)oj).iterator();

               while(var7.hasNext()) {
                  Object item = var7.next();
                  items.add(item);
               }
            } else {
               var7 = obj.asIterable().iterator();

               while(var7.hasNext()) {
                  PyObject pyobj = (PyObject)var7.next();
                  items.add(pyobj.__tojava__(Object.class));
               }
            }
         }

         return items;
      }
   }

   static PyBuiltinMethod[] getProxyMethods() {
      return new PyBuiltinMethod[]{cmpProxy, eqProxy, ltProxy, new IsSubsetMethod("__le__"), new IsSubsetMethod("issubset"), new IsSupersetMethod("__ge__"), new IsSupersetMethod("issuperset"), gtProxy, isDisjointProxy, differenceProxy, differenceUpdateProxy, subProxy, isubProxy, intersectionProxy, intersectionUpdateProxy, andProxy, iandProxy, symDiffProxy, symDiffUpdateProxy, xorProxy, ixorProxy, unionProxy, updateProxy, orProxy, iorProxy, new CopyMethod("copy"), new CopyMethod("__copy__"), reduceProxy, containsProxy, hashProxy, discardProxy, popProxy};
   }

   static PyBuiltinMethod[] getPostProxyMethods() {
      return new PyBuiltinMethod[]{deepcopyOverrideProxy, removeOverrideProxy};
   }

   @Untraversable
   private static class CopyMethod extends SetMethod {
      protected CopyMethod(String name) {
         super(name, 0);
      }

      public PyObject __call__() {
         return this.makePySet(this.asSet());
      }
   }

   @Untraversable
   private static class IsSupersetMethod extends SetMethod {
      protected IsSupersetMethod(String name) {
         super(name, 1);
      }

      public PyObject __call__(PyObject other) {
         return Py.newBoolean(this.isSuperset(other));
      }
   }

   @Untraversable
   private static class IsSubsetMethod extends SetMethod {
      protected IsSubsetMethod(String name) {
         super(name, 1);
      }

      public PyObject __call__(PyObject other) {
         return Py.newBoolean(this.isSubset(other));
      }
   }

   @Untraversable
   private static class SetMethodVarargs extends SetMethod {
      protected SetMethodVarargs(String name) {
         super(name, 0, -1);
      }

      public PyObject __call__() {
         return this.__call__(Py.EmptyObjects);
      }

      public PyObject __call__(PyObject obj) {
         return this.__call__(new PyObject[]{obj});
      }

      public PyObject __call__(PyObject obj1, PyObject obj2) {
         return this.__call__(new PyObject[]{obj1, obj2});
      }

      public PyObject __call__(PyObject obj1, PyObject obj2, PyObject obj3) {
         return this.__call__(new PyObject[]{obj1, obj2, obj3});
      }

      public PyObject __call__(PyObject obj1, PyObject obj2, PyObject obj3, PyObject obj4) {
         return this.__call__(new PyObject[]{obj1, obj2, obj3, obj4});
      }
   }

   @Untraversable
   private static class SetMethod extends PyBuiltinMethodNarrow {
      protected SetMethod(String name, int numArgs) {
         super(name, numArgs);
      }

      protected SetMethod(String name, int minArgs, int maxArgs) {
         super(name, minArgs, maxArgs);
      }

      protected Set asSet() {
         return (Set)this.self.getJavaProxy();
      }

      protected PyObject makePySet(Set newSet) {
         PyObject newPySet = this.self.getType().__call__();
         Set jSet = (Set)newPySet.getJavaProxy();
         jSet.addAll(newSet);
         return newPySet;
      }

      public boolean isEqual(PyObject other) {
         Set selfSet = this.asSet();
         Object oj = other.getJavaProxy();
         Set otherPySet;
         if (oj != null && oj instanceof Set) {
            otherPySet = (Set)oj;
            return selfSet.size() != otherPySet.size() ? false : selfSet.containsAll(otherPySet);
         } else if (JavaProxySet.isPySet(other)) {
            otherPySet = ((BaseSet)other).getSet();
            if (selfSet.size() != otherPySet.size()) {
               return false;
            } else {
               Iterator var5 = otherPySet.iterator();

               PyObject pyobj;
               do {
                  if (!var5.hasNext()) {
                     return true;
                  }

                  pyobj = (PyObject)var5.next();
               } while(selfSet.contains(pyobj.__tojava__(Object.class)));

               return false;
            }
         } else {
            return false;
         }
      }

      public boolean isSuperset(PyObject other) {
         Set selfSet = this.asSet();
         Object oj = other.getJavaProxy();
         Set otherPySet;
         if (oj != null && oj instanceof Set) {
            otherPySet = (Set)oj;
            return selfSet.containsAll(otherPySet);
         } else if (JavaProxySet.isPySet(other)) {
            otherPySet = ((BaseSet)other).getSet();
            Iterator var5 = otherPySet.iterator();

            PyObject pyobj;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               pyobj = (PyObject)var5.next();
            } while(selfSet.contains(pyobj.__tojava__(Object.class)));

            return false;
         } else {
            return false;
         }
      }

      public boolean isSubset(PyObject other) {
         Set selfSet = this.asSet();
         Object oj = other.getJavaProxy();
         Set otherPySet;
         if (oj != null && oj instanceof Set) {
            otherPySet = (Set)oj;
            return otherPySet.containsAll(selfSet);
         } else if (JavaProxySet.isPySet(other)) {
            otherPySet = ((BaseSet)other).getSet();
            Iterator var5 = selfSet.iterator();

            Object obj;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               obj = var5.next();
            } while(otherPySet.contains(Py.java2py(obj)));

            return false;
         } else {
            return false;
         }
      }

      protected Set difference(Collection other) {
         Set selfSet = this.asSet();
         Set diff = new HashSet(selfSet);
         diff.removeAll(other);
         return diff;
      }

      protected void differenceUpdate(Collection other) {
         this.asSet().removeAll(other);
      }

      protected Set intersect(Collection[] others) {
         Set selfSet = this.asSet();
         Set intersection = new HashSet(selfSet);
         Collection[] var4 = others;
         int var5 = others.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Collection other = var4[var6];
            intersection.retainAll(other);
         }

         return intersection;
      }

      protected void intersectUpdate(Collection[] others) {
         Set selfSet = this.asSet();
         Collection[] var3 = others;
         int var4 = others.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Collection other = var3[var5];
            selfSet.retainAll(other);
         }

      }

      protected Set union(Collection other) {
         Set selfSet = this.asSet();
         Set u = new HashSet(selfSet);
         u.addAll(other);
         return u;
      }

      protected void update(Collection other) {
         this.asSet().addAll(other);
      }

      protected Set symDiff(Collection other) {
         Set selfSet = this.asSet();
         Set symDiff = new HashSet(selfSet);
         symDiff.addAll(other);
         Set intersection = new HashSet(selfSet);
         intersection.retainAll(other);
         symDiff.removeAll(intersection);
         return symDiff;
      }

      protected void symDiffUpdate(Collection other) {
         Set selfSet = this.asSet();
         Set intersection = new HashSet(selfSet);
         intersection.retainAll(other);
         selfSet.addAll(other);
         selfSet.removeAll(intersection);
      }
   }
}
