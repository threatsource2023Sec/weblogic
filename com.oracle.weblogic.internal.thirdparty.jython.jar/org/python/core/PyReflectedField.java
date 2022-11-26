package org.python.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Untraversable
public class PyReflectedField extends PyObject {
   public Field field;

   public PyReflectedField() {
   }

   public PyReflectedField(Field field) {
      this.field = field;
   }

   public PyObject _doget(PyObject self) {
      Object iself = null;
      if (!Modifier.isStatic(this.field.getModifiers())) {
         if (self == null) {
            return this;
         }

         iself = self.getJavaProxy();
         if (iself == null) {
            iself = self;
         }
      }

      Object value;
      try {
         value = this.field.get(iself);
      } catch (IllegalAccessException var5) {
         throw Py.JavaError(var5);
      }

      return Py.java2py(value);
   }

   public boolean _doset(PyObject self, PyObject value) {
      Object iself = null;
      if (!Modifier.isStatic(this.field.getModifiers())) {
         if (self == null) {
            throw Py.AttributeError("set instance variable as static: " + this.field.toString());
         }

         iself = self.getJavaProxy();
         if (iself == null) {
            iself = self;
         }
      }

      Object fvalue = Py.tojava(value, this.field.getType());

      try {
         this.field.set(iself, fvalue);
         return true;
      } catch (IllegalAccessException var6) {
         throw Py.JavaError(var6);
      }
   }

   public String toString() {
      return String.format("<reflected field %s at %s>", this.field, Py.idstr(this));
   }
}
