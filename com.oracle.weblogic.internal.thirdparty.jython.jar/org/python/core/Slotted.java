package org.python.core;

public interface Slotted {
   PyObject getSlot(int var1);

   void setSlot(int var1, PyObject var2);
}
