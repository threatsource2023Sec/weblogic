package org.python.tests.constructor_kwargs;

import java.util.HashMap;
import org.python.core.PyObject;

public class KWArgsObject {
   private HashMap data = new HashMap();

   public KWArgsObject(PyObject[] values, String[] names) {
      int offset = values.length - names.length;

      for(int i = 0; i < names.length; ++i) {
         this.data.put(names[i], values[offset + i]);
      }

   }

   public PyObject getValue(String key) {
      return (PyObject)this.data.get(key);
   }
}
