package org.python.modules._jythonlib;

import java.util.concurrent.ConcurrentMap;
import org.python.core.PyDictionary;
import org.python.core.PyDictionaryDerived;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class dict_builder extends PyObject implements Traverseproc {
   public static final PyType TYPE = PyType.fromClass(dict_builder.class);
   private final PyObject factory;
   private final PyType dict_type;

   public dict_builder(PyObject factory) {
      this.factory = factory;
      this.dict_type = null;
   }

   public dict_builder(PyObject factory, PyType dict_type) {
      this.factory = factory;
      this.dict_type = dict_type;
   }

   public PyObject __call__(PyObject[] args, String[] keywords) {
      ConcurrentMap map = (ConcurrentMap)((ConcurrentMap)this.factory.__call__().__tojava__(ConcurrentMap.class));
      Object dict;
      if (this.dict_type == null) {
         dict = new PyDictionary(map, true);
      } else {
         dict = new PyDictionaryDerived(this.dict_type, map, true);
      }

      ((PyDictionary)dict).updateCommon(args, keywords, "dict");
      return (PyObject)dict;
   }

   public int traverse(Visitproc visit, Object arg) {
      return this.factory != null ? visit.visit(this.factory, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && this.factory == ob;
   }
}
