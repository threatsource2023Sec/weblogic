package org.python.modules._jythonlib;

import java.util.Set;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PySet;
import org.python.core.PySetDerived;
import org.python.core.PyType;

public class set_builder extends PyObject {
   public static final PyType TYPE = PyType.fromClass(set_builder.class);
   private final PyObject factory;
   private final PyType set_type;

   public set_builder(PyObject factory) {
      this.factory = factory;
      this.set_type = null;
   }

   public set_builder(PyObject factory, PyType set_type) {
      this.factory = factory;
      this.set_type = set_type;
   }

   public PyObject __call__(PyObject iterable) {
      Set backing_set = (Set)((Set)this.factory.__call__().__tojava__(Set.class));
      return (PyObject)(this.set_type == null ? new PySet(backing_set, iterable == Py.None ? null : iterable) : new PySetDerived(this.set_type, backing_set, iterable == Py.None ? null : iterable));
   }
}
