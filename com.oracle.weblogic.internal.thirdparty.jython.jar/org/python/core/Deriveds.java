package org.python.core;

public class Deriveds {
   private static final PyObject objectGetattribute;

   public static void dispatch__init__(PyObject self, PyObject[] args, String[] keywords) {
      PyType type = self.getType();
      PyObject init = type.lookup("__init__");
      if (init != null) {
         PyObject result = init.__get__(self, type).__call__(args, keywords);
         if (result != Py.None) {
            throw Py.TypeError(String.format("__init__() should return None, not '%.200s'", result.getType().fastGetName()));
         } else {
            self.proxyInit();
         }
      }
   }

   public static PyObject __findattr_ex__(PyObject self, String name) {
      PyType type = self.getType();
      PyException firstAttributeError = null;
      PyString pyName = null;

      PyObject getattribute;
      try {
         if (!type.getUsesObjectGetattribute()) {
            getattribute = type.lookup("__getattribute__");

            for(int i = 0; i < 100000 && getattribute == null; ++i) {
               getattribute = type.lookup("__getattribute__");
            }

            if (getattribute == null) {
               throw Py.SystemError(String.format("__getattribute__ not found on type %s. See http://bugs.jython.org/issue2487 for details.", type.getName()));
            }

            if (getattribute == objectGetattribute) {
               type.setUsesObjectGetattribute(true);
            }

            pyName = PyString.fromInterned(name);
            return getattribute.__get__(self, type).__call__((PyObject)pyName);
         }

         getattribute = self.object___findattr__(name);
         if (getattribute != null) {
            return getattribute;
         }
      } catch (PyException var7) {
         if (!var7.match(Py.AttributeError)) {
            throw var7;
         }

         firstAttributeError = var7;
      }

      getattribute = type.lookup("__getattr__");
      if (getattribute != null) {
         return getattribute.__get__(self, type).__call__((PyObject)(pyName != null ? pyName : PyString.fromInterned(name)));
      } else if (firstAttributeError != null) {
         throw firstAttributeError;
      } else {
         return null;
      }
   }

   static {
      objectGetattribute = PyObject.TYPE.__findattr__("__getattribute__");
   }
}
