package org.python.modules._collections;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyDataDescr;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;
import org.python.google.common.cache.CacheBuilder;
import org.python.google.common.cache.CacheLoader;
import org.python.google.common.cache.LoadingCache;

@ExposedType(
   name = "collections.defaultdict"
)
public class PyDefaultDict extends PyDictionary implements Traverseproc {
   public static final PyType TYPE;
   private PyObject defaultFactory;
   private final LoadingCache backingMap;

   public ConcurrentMap getMap() {
      return this.backingMap.asMap();
   }

   public PyDefaultDict() {
      this(TYPE);
   }

   public PyDefaultDict(PyType subtype) {
      super(subtype, false);
      this.defaultFactory = Py.None;
      this.backingMap = CacheBuilder.newBuilder().build(new CacheLoader() {
         public PyObject load(PyObject key) {
            try {
               return PyDefaultDict.this.__missing__(key);
            } catch (RuntimeException var3) {
               throw new MissingThrownException(var3);
            }
         }
      });
   }

   public PyDefaultDict(PyType subtype, Map map) {
      this(subtype);
      this.getMap().putAll(map);
   }

   @ExposedNew
   final void defaultdict___init__(PyObject[] args, String[] kwds) {
      int nargs = args.length - kwds.length;
      if (nargs != 0) {
         this.defaultFactory = args[0];
         if (this.defaultFactory != Py.None && !this.defaultFactory.isCallable()) {
            throw Py.TypeError("first argument must be callable");
         }

         PyObject[] newargs = new PyObject[args.length - 1];
         System.arraycopy(args, 1, newargs, 0, newargs.length);
         this.dict___init__(newargs, kwds);
      }

   }

   public PyObject __missing__(PyObject key) {
      return this.defaultdict___missing__(key);
   }

   final PyObject defaultdict___missing__(PyObject key) {
      if (this.defaultFactory == Py.None) {
         throw Py.KeyError(key);
      } else {
         return this.defaultFactory.__call__();
      }
   }

   public PyObject __reduce__() {
      return this.defaultdict___reduce__();
   }

   final PyObject defaultdict___reduce__() {
      PyTuple args = null;
      if (this.defaultFactory == Py.None) {
         args = new PyTuple();
      } else {
         PyObject[] ob = new PyObject[]{this.defaultFactory};
         args = new PyTuple(ob);
      }

      return new PyTuple(new PyObject[]{this.getType(), args, Py.None, Py.None, this.iteritems()});
   }

   public PyDictionary copy() {
      return this.defaultdict_copy();
   }

   final PyDefaultDict defaultdict_copy() {
      PyDefaultDict ob = new PyDefaultDict(TYPE, this.getMap());
      ob.defaultFactory = this.defaultFactory;
      return ob;
   }

   public String toString() {
      return this.defaultdict_toString();
   }

   final String defaultdict_toString() {
      return String.format("defaultdict(%s, %s)", this.defaultFactory, super.toString());
   }

   public PyObject getDefaultFactory() {
      return this.defaultFactory;
   }

   public void setDefaultFactory(PyObject value) {
      this.defaultFactory = value;
   }

   public void delDefaultFactory() {
      this.defaultFactory = Py.None;
   }

   public PyObject __finditem__(PyObject key) {
      return this.defaultdict___getitem__(key);
   }

   protected final PyObject defaultdict___getitem__(PyObject key) {
      try {
         return (PyObject)this.backingMap.get(key);
      } catch (PyException var4) {
         throw var4;
      } catch (Exception var5) {
         Throwable cause = var5.getCause();
         if (cause != null && cause instanceof MissingThrownException) {
            throw ((MissingThrownException)cause).thrownByMissing;
         } else {
            throw Py.KeyError(key);
         }
      }
   }

   public PyObject get(PyObject key, PyObject defaultObj) {
      PyObject value = (PyObject)this.getMap().get(key);
      return value != null ? value : defaultObj;
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         retVal = visit.visit(this.defaultFactory, arg);
         if (retVal != 0) {
            return retVal;
         } else {
            if (this.backingMap != null) {
               Iterator var4 = this.backingMap.asMap().entrySet().iterator();

               while(var4.hasNext()) {
                  Map.Entry ent = (Map.Entry)var4.next();
                  retVal = visit.visit((PyObject)ent.getKey(), arg);
                  if (retVal != 0) {
                     return retVal;
                  }

                  if (ent.getValue() != null) {
                     retVal = visit.visit((PyObject)ent.getValue(), arg);
                     if (retVal != 0) {
                        return retVal;
                     }
                  }
               }
            }

            return 0;
         }
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (super.refersDirectlyTo(ob)) {
         return true;
      } else if (this.backingMap == null) {
         return false;
      } else {
         return this.backingMap.asMap().containsKey(ob) || this.backingMap.asMap().containsValue(ob);
      }
   }

   static {
      PyType.addBuilder(PyDefaultDict.class, new PyExposer());
      TYPE = PyType.fromClass(PyDefaultDict.class);
   }

   private static class MissingThrownException extends RuntimeException {
      final RuntimeException thrownByMissing;

      MissingThrownException(RuntimeException thrownByMissing) {
         super(thrownByMissing);
         this.thrownByMissing = thrownByMissing;
      }
   }

   private static class defaultdict___init___exposer extends PyBuiltinMethod {
      public defaultdict___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public defaultdict___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((PyDefaultDict)this.self).defaultdict___init__(var1, var2);
         return Py.None;
      }
   }

   private static class defaultdict___missing___exposer extends PyBuiltinMethodNarrow {
      public defaultdict___missing___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "";
      }

      public defaultdict___missing___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict___missing___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDefaultDict)this.self).defaultdict___missing__(var1);
      }
   }

   private static class defaultdict___reduce___exposer extends PyBuiltinMethodNarrow {
      public defaultdict___reduce___exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public defaultdict___reduce___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict___reduce___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDefaultDict)this.self).defaultdict___reduce__();
      }
   }

   private static class defaultdict_copy_exposer extends PyBuiltinMethodNarrow {
      public defaultdict_copy_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public defaultdict_copy_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict_copy_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         return ((PyDefaultDict)this.self).defaultdict_copy();
      }
   }

   private static class defaultdict_toString_exposer extends PyBuiltinMethodNarrow {
      public defaultdict_toString_exposer(String var1) {
         super(var1, 1, 1);
         super.doc = "";
      }

      public defaultdict_toString_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict_toString_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__() {
         String var10000 = ((PyDefaultDict)this.self).defaultdict_toString();
         return (PyObject)(var10000 == null ? Py.None : Py.newString(var10000));
      }
   }

   private static class defaultdict___getitem___exposer extends PyBuiltinMethodNarrow {
      public defaultdict___getitem___exposer(String var1) {
         super(var1, 2, 2);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public defaultdict___getitem___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "x.__getitem__(y) <==> x[y]";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new defaultdict___getitem___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject var1) {
         return ((PyDefaultDict)this.self).defaultdict___getitem__(var1);
      }
   }

   private static class default_factory_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public default_factory_descriptor() {
         super("default_factory", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PyDefaultDict)var1).getDefaultFactory();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((PyDefaultDict)var1).setDefaultFactory((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public void invokeDelete(PyObject var1) {
         ((PyDefaultDict)var1).delDefaultFactory();
      }

      public boolean implementsDescrDelete() {
         return true;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         PyDefaultDict var4 = new PyDefaultDict(this.for_type);
         if (var1) {
            var4.defaultdict___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PyDefaultDictDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new defaultdict___init___exposer("__init__"), new defaultdict___missing___exposer("__missing__"), new defaultdict___reduce___exposer("__reduce__"), new defaultdict_copy_exposer("copy"), new defaultdict_copy_exposer("__copy__"), new defaultdict_toString_exposer("__repr__"), new defaultdict___getitem___exposer("__getitem__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new default_factory_descriptor()};
         super("collections.defaultdict", PyDefaultDict.class, Object.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
