package org.python.antlr.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Str;
import org.python.antlr.base.expr;
import org.python.core.Py;
import org.python.core.PyComplex;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;

public class ExprAdapter implements AstAdapter {
   public Object py2ast(PyObject o) {
      if (o instanceof expr) {
         return o;
      } else if (!(o instanceof PyInteger) && !(o instanceof PyLong) && !(o instanceof PyFloat) && !(o instanceof PyComplex)) {
         return !(o instanceof PyString) && !(o instanceof PyUnicode) ? null : new Str(o);
      } else {
         return new Num(o);
      }
   }

   public PyObject ast2py(Object o) {
      return o == null ? Py.None : (PyObject)o;
   }

   public List iter2ast(PyObject iter) {
      List exprs = new ArrayList();
      if (iter != Py.None) {
         Iterator var3 = ((Iterable)iter).iterator();

         while(var3.hasNext()) {
            Object o = var3.next();
            exprs.add((expr)this.py2ast((PyObject)o));
         }
      }

      return exprs;
   }
}
