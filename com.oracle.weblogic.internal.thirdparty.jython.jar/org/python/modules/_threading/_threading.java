package org.python.modules._threading;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyObject;

public class _threading implements ClassDictInit {
   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", Py.newString("_threading"));
      dict.__setitem__((String)"Lock", Lock.TYPE);
      dict.__setitem__((String)"RLock", RLock.TYPE);
      dict.__setitem__((String)"_Lock", Lock.TYPE);
      dict.__setitem__((String)"_RLock", RLock.TYPE);
      dict.__setitem__((String)"Condition", Condition.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }
}
