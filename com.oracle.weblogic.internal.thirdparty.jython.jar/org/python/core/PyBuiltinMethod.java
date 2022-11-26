package org.python.core;

import org.python.expose.ExposeAsSuperclass;

public abstract class PyBuiltinMethod extends PyBuiltinCallable implements ExposeAsSuperclass, Cloneable, Traverseproc {
   protected PyObject self;

   protected PyBuiltinMethod(PyType type, PyObject self, PyBuiltinCallable.Info info) {
      super(type, info);
      this.self = self;
   }

   protected PyBuiltinMethod(PyObject self, PyBuiltinCallable.Info info) {
      super(info);
      this.self = self;
   }

   protected PyBuiltinMethod(String name) {
      this((PyObject)null, new PyBuiltinCallable.DefaultInfo(name));
   }

   public PyBuiltinCallable bind(PyObject bindTo) {
      if (this.self == null) {
         PyBuiltinMethod bindable;
         try {
            bindable = (PyBuiltinMethod)this.clone();
         } catch (CloneNotSupportedException var4) {
            throw new RuntimeException("Didn't expect PyBuiltinMethodto throw CloneNotSupported since it implements Cloneable", var4);
         }

         bindable.self = bindTo;
         return bindable;
      } else {
         return this;
      }
   }

   public PyObject getSelf() {
      return this.self;
   }

   public PyMethodDescr makeDescriptor(PyType t) {
      return new PyMethodDescr(t, this);
   }

   public int hashCode() {
      int hashCode = this.self == null ? 0 : this.self.hashCode();
      return hashCode ^ this.getClass().hashCode();
   }

   public int __cmp__(PyObject other) {
      if (!(other instanceof PyBuiltinMethod)) {
         return -2;
      } else {
         PyBuiltinMethod otherMethod = (PyBuiltinMethod)other;
         if (this.self != otherMethod.self) {
            if (this.self == null) {
               return -1;
            } else {
               return otherMethod.self == null ? 1 : this.self._cmp(otherMethod.self);
            }
         } else if (this.getClass() == otherMethod.getClass()) {
            return 0;
         } else {
            int compareTo = this.info.getName().compareTo(otherMethod.info.getName());
            return compareTo < 0 ? -1 : (compareTo > 0 ? 1 : 0);
         }
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.self != null ? visit.visit(this.self, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.self;
   }
}
