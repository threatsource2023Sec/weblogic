package org.python.modules;

import org.python.core.PyArray;
import org.python.core.PyObject;

public class jarray {
   public static PyArray array(PyObject seq, char typecode) {
      return PyArray.array(seq, typecode);
   }

   public static PyArray array(PyObject seq, Class type) {
      return PyArray.array(seq, type);
   }

   public static PyArray zeros(int n, char typecode) {
      return PyArray.zeros(n, typecode);
   }

   public static PyArray zeros(int n, Class type) {
      return PyArray.zeros(n, type);
   }

   public static Class array_class(PyObject type) {
      return PyArray.array_class((Class)type.__tojava__(Class.class));
   }
}
