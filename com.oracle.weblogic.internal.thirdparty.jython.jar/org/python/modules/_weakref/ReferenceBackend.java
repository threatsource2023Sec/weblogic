package org.python.modules._weakref;

import org.python.core.PyList;
import org.python.core.PyObject;

public interface ReferenceBackend {
   PyObject get();

   void add(AbstractReference var1);

   boolean isCleared();

   AbstractReference find(Class var1);

   int pythonHashCode();

   PyList refs();

   void restore(PyObject var1);

   int count();
}
