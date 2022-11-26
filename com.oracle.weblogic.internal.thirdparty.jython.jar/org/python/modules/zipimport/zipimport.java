package org.python.modules.zipimport;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;

public class zipimport implements ClassDictInit {
   public static final PyString __doc__ = new PyString("zipimport provides support for importing Python modules from Zip archives.\n\nThis module exports three objects:\n- zipimporter: a class; its constructor takes a path to a Zip archive.\n- ZipImportError: exception raised by zipimporter objects. It's a\nsubclass of ImportError, so it can be caught as ImportError, too.\n- _zip_directory_cache: a dict, mapping archive paths to zip directory\ninfo dicts, as used in zipimporter._files.\n\nIt is usually not needed to use the zipimport module explicitly; it is\nused by the builtin import mechanism for sys.path items that are paths\nto Zip archives.");
   public static PyObject ZipImportError;
   public static PyDictionary _zip_directory_cache = new PyDictionary();

   public static PyException ZipImportError(String message) {
      return new PyException(ZipImportError, message);
   }

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("zipimport"));
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"zipimporter", zipimporter.TYPE);
      dict.__setitem__((String)"_zip_directory_cache", _zip_directory_cache);
      dict.__setitem__("ZipImportError", ZipImportError);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"initClassExceptions", (PyObject)null);
   }

   public static void initClassExceptions(PyObject exceptions) {
      PyObject ImportError = exceptions.__finditem__("ImportError");
      ZipImportError = Py.makeClass("zipimport.ZipImportError", (PyObject)ImportError, (PyObject)(new PyStringMap() {
         {
            this.__setitem__("__module__", Py.newString("zipimport"));
         }
      }));
   }
}
