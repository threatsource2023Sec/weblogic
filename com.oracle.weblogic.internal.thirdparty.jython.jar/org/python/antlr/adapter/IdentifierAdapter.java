package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

public class IdentifierAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      return o != null && o != Py.None ? o.toString() : null;
   }

   public PyObject ast2py(Object o) {
      return (PyObject)(o == null ? Py.None : new PyString(o.toString()));
   }

   public List iter2ast(PyObject iter) {
      List identifiers = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            identifiers.add((String)this.py2ast((PyObject)o));
         }
      }

      return identifiers;
   }
}
