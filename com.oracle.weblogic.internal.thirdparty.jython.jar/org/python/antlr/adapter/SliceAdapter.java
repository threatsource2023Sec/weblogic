package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.base.slice;
import org.python.core.Py;
import org.python.core.PyObject;

public class SliceAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      return o instanceof slice ? o : null;
   }

   public PyObject ast2py(Object o) {
      return o == null ? Py.None : (PyObject)o;
   }

   public List iter2ast(PyObject iter) {
      List slices = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            slices.add((slice)this.py2ast((PyObject)o));
         }
      }

      return slices;
   }
}
