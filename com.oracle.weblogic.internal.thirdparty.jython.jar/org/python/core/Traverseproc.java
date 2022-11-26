package org.python.core;

public interface Traverseproc {
   int traverse(Visitproc var1, Object var2);

   boolean refersDirectlyTo(PyObject var1) throws UnsupportedOperationException;
}
