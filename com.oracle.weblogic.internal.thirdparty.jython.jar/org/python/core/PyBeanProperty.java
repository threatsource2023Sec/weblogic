package org.python.core;

import java.lang.reflect.Method;

@Untraversable
public class PyBeanProperty extends PyReflectedField {
   public Method getMethod;
   public Method setMethod;
   public Class myType;
   String __name__;

   public PyBeanProperty(String name, Class myType, Method getMethod, Method setMethod) {
      this.__name__ = name;
      this.getMethod = getMethod;
      this.setMethod = setMethod;
      this.myType = myType;
   }

   public PyObject _doget(PyObject self) {
      if (self == null) {
         if (this.field != null) {
            return super._doget((PyObject)null);
         } else {
            throw Py.AttributeError("instance attr: " + this.__name__);
         }
      } else if (this.getMethod == null) {
         throw Py.AttributeError("write-only attr: " + this.__name__);
      } else {
         Object iself = Py.tojava(self, this.getMethod.getDeclaringClass());

         try {
            Object value = this.getMethod.invoke(iself, (Object[])Py.EmptyObjects);
            return Py.java2py(value);
         } catch (Exception var4) {
            throw Py.JavaError(var4);
         }
      }
   }

   public boolean _doset(PyObject self, PyObject value) {
      if (self == null) {
         if (this.field != null) {
            return super._doset((PyObject)null, value);
         } else {
            throw Py.AttributeError("instance attr: " + this.__name__);
         }
      } else if (this.setMethod == null) {
         throw Py.AttributeError("read-only attr: " + this.__name__);
      } else {
         Object iself = Py.tojava(self, this.setMethod.getDeclaringClass());
         if (value instanceof PyTuple && this.myType != PyObject.class) {
            try {
               value = Py.java2py(this.myType).__call__(((PyTuple)value).getArray());
            } catch (Throwable var7) {
               throw Py.JavaError(var7);
            }
         }

         Object jvalue = Py.tojava(value, this.myType);

         try {
            this.setMethod.invoke(iself, jvalue);
            return true;
         } catch (Exception var6) {
            throw Py.JavaError(var6);
         }
      }
   }

   public PyBeanProperty copy() {
      return new PyBeanProperty(this.__name__, this.myType, this.getMethod, this.setMethod);
   }

   public String toString() {
      String typeName = "unknown";
      if (this.myType != null) {
         typeName = this.myType.getName();
      }

      return "<beanProperty " + this.__name__ + " type: " + typeName + " " + Py.idstr(this) + ">";
   }
}
