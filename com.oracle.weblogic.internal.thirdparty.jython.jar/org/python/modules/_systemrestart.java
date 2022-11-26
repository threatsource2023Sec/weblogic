package org.python.modules;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyStringMap;

public class _systemrestart implements ClassDictInit {
   public static PyObject SystemRestart;

   public static void classDictInit(PyObject dict) {
      SystemRestart = Py.makeClass("_systemrestart.SystemRestart", (PyObject)Py.BaseException, (PyObject)(new PyStringMap() {
         {
            this.__setitem__("__doc__", Py.newString("Request to restart the interpreter. (Jython-specific)"));
         }
      }));
      dict.__delitem__("classDictInit");
   }
}
