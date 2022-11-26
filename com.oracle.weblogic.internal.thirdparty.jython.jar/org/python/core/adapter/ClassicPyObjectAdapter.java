package org.python.core.adapter;

import java.math.BigInteger;
import org.python.core.Py;
import org.python.core.PyArray;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyJavaType;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyProxy;
import org.python.core.PyType;
import org.python.core.PyUnicode;

public class ClassicPyObjectAdapter extends ExtensiblePyObjectAdapter {
   public ClassicPyObjectAdapter() {
      this.addPreClass(new PyObjectAdapter() {
         public PyObject adapt(Object o) {
            return (PyObject)o;
         }

         public boolean canAdapt(Object o) {
            return o instanceof PyObject;
         }
      });
      this.addPreClass(new PyObjectAdapter() {
         public PyObject adapt(Object o) {
            return ((PyProxy)o)._getPyInstance();
         }

         public boolean canAdapt(Object o) {
            return o instanceof PyProxy;
         }
      });
      this.addPreClass(new PyObjectAdapter() {
         public boolean canAdapt(Object o) {
            return o == null;
         }

         public PyObject adapt(Object o) {
            return Py.None;
         }
      });
      this.add(new ClassAdapter(String.class) {
         public PyObject adapt(Object o) {
            return new PyUnicode((String)o);
         }
      });
      this.add(new ClassAdapter(Character.class) {
         public PyObject adapt(Object o) {
            return Py.makeCharacter((Character)o);
         }
      });
      this.add(new ClassAdapter(Class.class) {
         public PyObject adapt(Object o) {
            return PyType.fromClass((Class)o, false);
         }
      });
      this.add(new NumberToPyFloat(Double.class));
      this.add(new NumberToPyFloat(Float.class));
      this.add(new NumberToPyInteger(Integer.class));
      this.add(new NumberToPyInteger(Byte.class));
      this.add(new NumberToPyInteger(Short.class));
      this.add(new ClassAdapter(Long.class) {
         public PyObject adapt(Object o) {
            return new PyLong(((Number)o).longValue());
         }
      });
      this.add(new ClassAdapter(BigInteger.class) {
         public PyObject adapt(Object o) {
            return new PyLong((BigInteger)o);
         }
      });
      this.add(new ClassAdapter(Boolean.class) {
         public PyObject adapt(Object o) {
            return (Boolean)o ? Py.True : Py.False;
         }
      });
      this.addPostClass(new PyObjectAdapter() {
         public PyObject adapt(Object o) {
            return new PyArray(o.getClass().getComponentType(), o);
         }

         public boolean canAdapt(Object o) {
            return o.getClass().isArray();
         }
      });
   }

   public boolean canAdapt(Object o) {
      return true;
   }

   public PyObject adapt(Object o) {
      PyObject result = super.adapt(o);
      return result != null ? result : PyJavaType.wrapJavaObject(o);
   }

   private static class NumberToPyFloat extends ClassAdapter {
      public NumberToPyFloat(Class c) {
         super(c);
      }

      public PyObject adapt(Object o) {
         return new PyFloat(((Number)o).doubleValue());
      }
   }

   private static class NumberToPyInteger extends ClassAdapter {
      public NumberToPyInteger(Class c) {
         super(c);
      }

      public PyObject adapt(Object o) {
         return new PyInteger(((Number)o).intValue());
      }
   }
}
