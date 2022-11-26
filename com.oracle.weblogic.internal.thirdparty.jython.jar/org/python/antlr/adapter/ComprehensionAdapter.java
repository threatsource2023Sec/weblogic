package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.ast.comprehension;
import org.python.core.Py;
import org.python.core.PyObject;

public class ComprehensionAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      return o instanceof comprehension ? o : null;
   }

   public PyObject ast2py(Object o) {
      return o == null ? Py.None : (PyObject)o;
   }

   public List iter2ast(PyObject iter) {
      List comprehensions = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            comprehensions.add((comprehension)this.py2ast((PyObject)o));
         }
      }

      return comprehensions;
   }
}
