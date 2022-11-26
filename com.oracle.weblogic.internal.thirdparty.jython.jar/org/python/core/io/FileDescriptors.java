package org.python.core.io;

import org.python.core.Py;
import org.python.core.PyObject;

public class FileDescriptors {
   public static RawIOBase get(PyObject fd) {
      return (RawIOBase)Py.tojava(fd, RawIOBase.class);
   }
}
