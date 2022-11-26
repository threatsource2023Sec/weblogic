package org.python.expose;

import org.python.core.PyObject;
import org.python.core.PyType;

public interface TypeBuilder {
   String getName();

   PyObject getDict(PyType var1);

   Class getTypeClass();

   Class getBase();

   boolean getIsBaseType();

   String getDoc();
}
