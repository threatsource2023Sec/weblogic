package org.python.core.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.core.PyObject;

public class ExtensiblePyObjectAdapter implements PyObjectAdapter {
   private List preClassAdapters = new ArrayList();
   private List postClassAdapters = new ArrayList();
   private Map classAdapters = new HashMap();

   public boolean canAdapt(Object o) {
      return findAdapter(this.preClassAdapters, o) != null || this.classAdapters.containsKey(o.getClass()) || findAdapter(this.postClassAdapters, o) != null;
   }

   public PyObject adapt(Object o) {
      PyObjectAdapter adapter = findAdapter(this.preClassAdapters, o);
      if (adapter != null) {
         return adapter.adapt(o);
      } else {
         adapter = (PyObjectAdapter)this.classAdapters.get(o.getClass());
         if (adapter != null) {
            return adapter.adapt(o);
         } else {
            adapter = findAdapter(this.postClassAdapters, o);
            return adapter != null ? adapter.adapt(o) : null;
         }
      }
   }

   public void addPreClass(PyObjectAdapter adapter) {
      this.preClassAdapters.add(adapter);
   }

   public void add(ClassAdapter adapter) {
      this.classAdapters.put(adapter.getAdaptedClass(), adapter);
   }

   public void addPostClass(PyObjectAdapter converter) {
      this.postClassAdapters.add(converter);
   }

   private static PyObjectAdapter findAdapter(List l, Object o) {
      Iterator iter = l.iterator();

      PyObjectAdapter adapter;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         adapter = (PyObjectAdapter)iter.next();
      } while(!adapter.canAdapt(o));

      return adapter;
   }
}
