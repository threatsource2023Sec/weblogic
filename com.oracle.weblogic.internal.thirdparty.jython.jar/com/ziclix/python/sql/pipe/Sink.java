package com.ziclix.python.sql.pipe;

import org.python.core.PyObject;

public interface Sink {
   void start();

   void row(PyObject var1);

   void end();
}
