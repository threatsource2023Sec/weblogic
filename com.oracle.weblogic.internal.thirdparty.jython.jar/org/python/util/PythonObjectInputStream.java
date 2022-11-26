package org.python.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.__builtin__;

public class PythonObjectInputStream extends ObjectInputStream {
   public PythonObjectInputStream(InputStream istr) throws IOException {
      super(istr);
   }

   protected Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
      String clsName = v.getName();
      if (clsName.startsWith("org.python.proxies")) {
         int idx = clsName.lastIndexOf(36);
         if (idx > 19) {
            clsName = clsName.substring(19, idx);
         }

         idx = clsName.indexOf(36);
         if (idx >= 0) {
            String mod = clsName.substring(0, idx);
            clsName = clsName.substring(idx + 1);
            PyObject module = importModule(mod);
            PyType pycls = (PyType)module.__getattr__(clsName.intern());
            return pycls.getProxyType();
         }
      }

      try {
         return super.resolveClass(v);
      } catch (ClassNotFoundException var7) {
         PyObject m = importModule(clsName);
         Object cls = m.__tojava__(Class.class);
         if (cls != null && cls != Py.NoConversion) {
            return (Class)cls;
         } else {
            throw var7;
         }
      }
   }

   private static PyObject importModule(String name) {
      PyObject fromlist = new PyTuple(new PyObject[]{Py.newString("__doc__")});
      return __builtin__.__import__(name, (PyObject)null, (PyObject)null, fromlist);
   }
}
