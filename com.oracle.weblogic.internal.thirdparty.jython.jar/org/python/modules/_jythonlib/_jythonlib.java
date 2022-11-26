package org.python.modules._jythonlib;

import org.python.core.ClassDictInit;
import org.python.core.PyObject;
import org.python.core.PyString;

public class _jythonlib implements ClassDictInit {
   public static final PyString __doc__ = new PyString("jythonlib module");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("_jythonlib"));
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"__module__", new PyString("_jythonlib"));
      dict.__setitem__((String)"dict_builder", dict_builder.TYPE);
      dict.__setitem__((String)"set_builder", set_builder.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }
}
