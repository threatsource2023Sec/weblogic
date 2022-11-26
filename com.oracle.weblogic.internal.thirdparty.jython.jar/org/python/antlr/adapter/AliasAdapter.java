package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.ast.alias;
import org.python.core.Py;
import org.python.core.PyObject;

public class AliasAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      return o instanceof alias ? o : null;
   }

   public PyObject ast2py(Object o) {
      return o == null ? Py.None : (PyObject)o;
   }

   public List iter2ast(PyObject iter) {
      List aliases = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            aliases.add((alias)this.py2ast((PyObject)o));
         }
      }

      return aliases;
   }
}
