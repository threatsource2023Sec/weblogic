package org.python.tests;

import java.util.Map;
import org.python.core.Py;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyBuiltinMethodNarrow;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.util.Generic;

public class CustomizableMapHolder {
   public Map held = Generic.map();

   public CustomizableMapHolder() {
      this.held.put("initial", 7);
   }

   public static void clearAdditions() {
      PyObject dict = PyType.fromClass(CustomizableMapHolder.class).fastGetDict();
      String[] var1 = new String[]{"__getitem__", "__setitem__", "__getattribute__"};
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String name = var1[var3];
         if (dict.__finditem__(name) != null) {
            dict.__delitem__(name);
         }
      }

   }

   public static void addGetitem() {
      PyBuiltinMethod meth = new PyBuiltinMethodNarrow("__getitem__", 1) {
         public PyObject __call__(PyObject arg) {
            CustomizableMapHolder inst = (CustomizableMapHolder)Py.tojava(this.self, CustomizableMapHolder.class);
            String key = (String)Py.tojava(arg, String.class);
            return Py.java2py(inst.held.get(key));
         }
      };
      PyType.fromClass(CustomizableMapHolder.class).addMethod(meth);
   }

   public static void addSetitem() {
      PyBuiltinMethod meth = new PyBuiltinMethodNarrow("__setitem__", 2) {
         public PyObject __call__(PyObject arg1, PyObject arg2) {
            CustomizableMapHolder inst = (CustomizableMapHolder)Py.tojava(this.self, CustomizableMapHolder.class);
            String key = (String)Py.tojava(arg1, String.class);
            Integer val = (Integer)Py.tojava(arg2, Integer.class);
            inst.held.put(key, val);
            return Py.None;
         }
      };
      PyType.fromClass(CustomizableMapHolder.class).addMethod(meth);
   }

   public static void addGetattribute() {
      final PyObject objectGetattribute = PyObject.TYPE.__getattr__("__getattribute__");
      PyBuiltinMethod meth = new PyBuiltinMethodNarrow("__getattribute__", 1) {
         public PyObject __call__(PyObject name) {
            try {
               return objectGetattribute.__call__(this.self, name);
            } catch (PyException var3) {
               if (!var3.match(Py.AttributeError)) {
                  throw var3;
               } else {
                  CustomizableMapHolder inst = (CustomizableMapHolder)Py.tojava(this.self, CustomizableMapHolder.class);
                  return Py.java2py(inst.held.get(Py.tojava(name, String.class)));
               }
            }
         }
      };
      PyType.fromClass(CustomizableMapHolder.class).addMethod(meth);
   }
}
