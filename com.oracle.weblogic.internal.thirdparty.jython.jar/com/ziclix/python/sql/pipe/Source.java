package com.ziclix.python.sql.pipe;

import org.python.core.PyObject;

public interface Source {
   void start();

   PyObject next();

   void end();
}
