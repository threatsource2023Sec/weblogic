package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.ast.ExceptHandler;
import org.python.core.Py;
import org.python.core.PyObject;

public class ExcepthandlerAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      return o instanceof ExceptHandler ? o : null;
   }

   public PyObject ast2py(Object o) {
      return o == null ? Py.None : (PyObject)o;
   }

   public List iter2ast(PyObject iter) {
      List excepthandlers = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            excepthandlers.add((ExceptHandler)this.py2ast((PyObject)o));
         }
      }

      return excepthandlers;
   }
}
