package org.python.modules._collections;

import org.python.core.ClassDictInit;
import org.python.core.PyObject;

public class Collections implements ClassDictInit {
   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"deque", PyDeque.TYPE);
      dict.__setitem__((String)"defaultdict", PyDefaultDict.TYPE);
   }
}
