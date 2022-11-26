package org.python.antlr.adapter;

import java.util.List;
import org.python.core.PyObject;

public interface AstAdapter {
   PyObject ast2py(Object var1);

   Object py2ast(PyObject var1);

   List iter2ast(PyObject var1);
}
