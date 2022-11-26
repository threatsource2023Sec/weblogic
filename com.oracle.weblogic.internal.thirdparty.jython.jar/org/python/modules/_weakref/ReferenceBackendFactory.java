package org.python.modules._weakref;

import org.python.core.PyObject;

public interface ReferenceBackendFactory {
   ReferenceBackend makeBackend(GlobalRef var1, PyObject var2);

   void notifyClear(ReferenceBackend var1, GlobalRef var2);

   void updateBackend(ReferenceBackend var1, GlobalRef var2);
}
