package org.python.core;

import java.lang.reflect.Method;

@Untraversable
public class PyBeanEvent extends PyObject {
   public Method addMethod;
   public Class eventClass;
   public String __name__;

   public PyBeanEvent(String name, Class eventClass, Method addMethod) {
      this.__name__ = name.intern();
      this.addMethod = addMethod;
      this.eventClass = eventClass;
   }

   public PyObject _doget(PyObject container) {
      throw Py.TypeError("write only attribute");
   }

   boolean jdontdel() {
      throw Py.TypeError("can't delete this attribute");
   }

   public boolean _doset(PyObject self, PyObject value) {
      Object jself = Py.tojava(self, this.addMethod.getDeclaringClass());
      Object jvalue = Py.tojava(value, this.eventClass);

      try {
         this.addMethod.invoke(jself, jvalue);
         return true;
      } catch (Exception var6) {
         throw Py.JavaError(var6);
      }
   }

   public String toString() {
      return "<beanEvent " + this.__name__ + " for event " + this.eventClass.toString() + " " + Py.idstr(this) + ">";
   }
}
