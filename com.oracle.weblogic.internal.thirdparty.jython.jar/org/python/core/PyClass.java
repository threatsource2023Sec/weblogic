package org.python.core;

import org.python.core.finalization.FinalizeTrigger;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "classobj",
   isBaseType = false
)
public class PyClass extends PyObject implements Traverseproc {
   public static final PyType TYPE;
   public PyObject __dict__;
   public PyTuple __bases__;
   public String __name__;
   PyObject __getattr__;
   PyObject __setattr__;
   PyObject __delattr__;
   PyObject __tojava__;
   PyObject __del__;
   PyObject __contains__;

   private PyClass() {
      super(TYPE);
   }

   @ExposedNew
   public static PyObject classobj___new__(PyNewWrapper new_, boolean init, PyType subtype, PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("function", args, keywords, "name", "bases", "dict");
      PyObject name = ap.getPyObject(0);
      PyObject bases = ap.getPyObject(1);
      PyObject dict = ap.getPyObject(2);
      return classobj___new__(name, bases, dict);
   }

   public static PyObject classobj___new__(PyObject name, PyObject bases, PyObject dict) {
      if (!name.getType().isSubType(PyString.TYPE)) {
         throw Py.TypeError("PyClass_New: name must be a string");
      } else if (!(dict instanceof AbstractDict)) {
         throw Py.TypeError("PyClass_New: dict must be a dictionary");
      } else {
         PyType.ensureDoc(dict);
         PyType.ensureModule(dict);
         if (!(bases instanceof PyTuple)) {
            throw Py.TypeError("PyClass_New: bases must be a tuple");
         } else {
            PyTuple basesTuple = (PyTuple)bases;
            PyObject[] var4 = basesTuple.getArray();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               PyObject base = var4[var6];
               if (!(base instanceof PyClass)) {
                  if (base.getType().isCallable()) {
                     return base.getType().__call__(name, bases, dict);
                  }

                  throw Py.TypeError("PyClass_New: base must be a class");
               }
            }

            PyClass klass = new PyClass();
            klass.__name__ = name.toString();
            klass.__bases__ = basesTuple;
            klass.__dict__ = dict;
            klass.cacheDescriptors();
            return klass;
         }
      }
   }

   private void cacheDescriptors() {
      this.__getattr__ = this.lookup("__getattr__");
      this.__setattr__ = this.lookup("__setattr__");
      this.__delattr__ = this.lookup("__delattr__");
      this.__tojava__ = this.lookup("__tojava__");
      this.__del__ = this.lookup("__del__");
      this.__contains__ = this.lookup("__contains__");
   }

   PyObject lookup(String name) {
      PyObject result = this.__dict__.__finditem__(name);
      if (result == null && this.__bases__ != null) {
         PyObject[] var3 = this.__bases__.getArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject base = var3[var5];
            result = ((PyClass)base).lookup(name);
            if (result != null) {
               break;
            }
         }
      }

      return result;
   }

   public PyObject fastGetDict() {
      return this.__dict__;
   }

   public PyObject __findattr_ex__(String name) {
      if (name == "__dict__") {
         return this.__dict__;
      } else if (name == "__bases__") {
         return this.__bases__;
      } else if (name == "__name__") {
         return Py.newString(this.__name__);
      } else {
         PyObject result = this.lookup(name);
         return result == null ? result : result.__get__((PyObject)null, this);
      }
   }

   public void __setattr__(String name, PyObject value) {
      if (name == "__dict__") {
         this.setDict(value);
      } else if (name == "__bases__") {
         this.setBases(value);
      } else if (name == "__name__") {
         this.setName(value);
      } else if (name == "__getattr__") {
         this.__getattr__ = value;
      } else if (name == "__setattr__") {
         this.__setattr__ = value;
      } else if (name == "__delattr__") {
         this.__delattr__ = value;
      } else if (name == "__tojava__") {
         this.__tojava__ = value;
      } else if (name == "__del__") {
         this.__del__ = value;
      } else if (name == "__contains__") {
         this.__contains__ = value;
      } else {
         if (value == null) {
            try {
               this.__dict__.__delitem__(name);
            } catch (PyException var4) {
               this.noAttributeError(name);
            }
         }

         this.__dict__.__setitem__(name, value);
      }
   }

   public void __delattr__(String name) {
      this.__setattr__(name, (PyObject)null);
   }

   public void __rawdir__(PyDictionary accum) {
      this.mergeClassDict(accum, this);
   }

   public void noAttributeError(String name) {
      throw Py.AttributeError(String.format("class %.50s has no attribute '%.400s'", this.__name__, name));
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      PyInstance inst = new PyInstance(this);
      if (this.__del__ != null) {
         FinalizeTrigger.ensureFinalizer(inst);
      }

      inst.__init__(args, keywords);
      return inst;
   }

   public boolean isCallable() {
      return true;
   }

   public int __cmp__(PyObject other) {
      if (!(other instanceof PyClass)) {
         return -2;
      } else {
         int c = this.__name__.compareTo(((PyClass)other).__name__);
         return c < 0 ? -1 : (c > 0 ? 1 : 0);
      }
   }

   public PyString __str__() {
      if (this.__dict__ == null) {
         return new PyString(this.__name__);
      } else {
         PyObject mod = this.__dict__.__finditem__("__module__");
         if (mod != null && mod instanceof PyString) {
            String smod = ((PyString)mod).toString();
            return new PyString(smod + "." + this.__name__);
         } else {
            return new PyString(this.__name__);
         }
      }
   }

   public String toString() {
      PyObject mod = this.__dict__.__finditem__("__module__");
      String modStr = mod != null && Py.isInstance(mod, PyString.TYPE) ? mod.toString() : "?";
      return String.format("<class %s.%s at %s>", modStr, this.__name__, Py.idstr(this));
   }

   public boolean isSubClass(PyClass superclass) {
      if (this == superclass) {
         return true;
      } else if (this.__bases__ != null && superclass.__bases__ != null) {
         PyObject[] var2 = this.__bases__.getArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject base = var2[var4];
            if (((PyClass)base).isSubClass(superclass)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void setDict(PyObject value) {
      if (value != null && value instanceof AbstractDict) {
         this.__dict__ = value;
      } else {
         throw Py.TypeError("__dict__ must be a dictionary object");
      }
   }

   public void setBases(PyObject value) {
      if (value != null && value instanceof PyTuple) {
         PyTuple bases = (PyTuple)value;
         PyObject[] var3 = bases.getArray();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject base = var3[var5];
            if (!(base instanceof PyClass)) {
               throw Py.TypeError("__bases__ items must be classes");
            }

            if (((PyClass)base).isSubClass(this)) {
               throw Py.TypeError("a __bases__ item causes an inheritance cycle");
            }
         }

         this.__bases__ = bases;
      } else {
         throw Py.TypeError("__bases__ must be a tuple object");
      }
   }

   public void setName(PyObject value) {
      if (value != null && Py.isInstance(value, PyString.TYPE)) {
         String name = value.toString();
         if (name.contains("\u0000")) {
            throw Py.TypeError("__name__ must not contain null bytes");
         } else {
            this.__name__ = name;
         }
      } else {
         throw Py.TypeError("__name__ must be a string object");
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.__bases__ != null) {
         retVal = visit.visit(this.__bases__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__dict__ != null) {
         retVal = visit.visit(this.__dict__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__getattr__ != null) {
         retVal = visit.visit(this.__getattr__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__setattr__ != null) {
         retVal = visit.visit(this.__setattr__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__delattr__ != null) {
         retVal = visit.visit(this.__delattr__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__tojava__ != null) {
         retVal = visit.visit(this.__tojava__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.__del__ != null) {
         retVal = visit.visit(this.__del__, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.__contains__ != null ? visit.visit(this.__contains__, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (this.__dict__ == ob || this.__bases__ == ob || this.__getattr__ == ob || this.__setattr__ == ob || this.__delattr__ == ob || this.__tojava__ == ob || this.__del__ == ob || this.__contains__ == ob);
   }

   static {
      PyType.addBuilder(PyClass.class, new PyExposer());
      TYPE = PyType.fromClass(PyClass.class);
   }

   private static class exposed___new__ extends PyNewWrapper {
      public exposed___new__() {
      }

      public PyObject new_impl(boolean var1, PyType var2, PyObject[] var3, String[] var4) {
         return PyClass.classobj___new__(this, var1, var2, var3, var4);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("classobj", PyClass.class, Object.class, (boolean)0, (String)null, var1, var2, new exposed___new__());
      }
   }
}
