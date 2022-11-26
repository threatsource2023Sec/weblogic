package org.python.core.finalization;

import org.python.core.PyObject;

public interface FinalizeTriggerFactory {
   FinalizeTrigger makeTrigger(PyObject var1);
}
