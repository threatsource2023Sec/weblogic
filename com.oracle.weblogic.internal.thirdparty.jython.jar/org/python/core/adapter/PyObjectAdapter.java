package org.python.core.adapter;

import org.python.core.PyObject;

public interface PyObjectAdapter {
   boolean canAdapt(Object var1);

   PyObject adapt(Object var1);
}
