package org.python.core;

import java.io.OutputStream;

public interface PythonCodeBundle {
   PyCode loadCode() throws Exception;

   void writeTo(OutputStream var1) throws Exception;
}
