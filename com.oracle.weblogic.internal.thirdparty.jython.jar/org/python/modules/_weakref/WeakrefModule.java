package org.python.modules._weakref;

import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

public class WeakrefModule implements ClassDictInit {
   public static final PyString __doc__ = new PyString("Weak-reference support module.");

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__doc__", __doc__);
      dict.__setitem__((String)"__name__", Py.newString("_weakref"));
      dict.__setitem__((String)"ref", ReferenceType.TYPE);
      dict.__setitem__((String)"ReferenceType", ReferenceType.TYPE);
      dict.__setitem__((String)"ProxyType", ProxyType.TYPE);
      dict.__setitem__((String)"CallableProxyType", CallableProxyType.TYPE);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
   }

   public static ProxyType proxy(PyObject object) {
      ReferenceBackend gref = GlobalRef.newInstance(object);
      boolean callable = object.isCallable();
      ProxyType ret = (ProxyType)gref.find(callable ? CallableProxyType.class : ProxyType.class);
      if (ret != null) {
         return ret;
      } else {
         return (ProxyType)(callable ? new CallableProxyType(GlobalRef.newInstance(object), (PyObject)null) : new ProxyType(GlobalRef.newInstance(object), (PyObject)null));
      }
   }

   public static ProxyType proxy(PyObject object, PyObject callback) {
      if (callback == Py.None) {
         return proxy(object);
      } else {
         return (ProxyType)(object.isCallable() ? new CallableProxyType(GlobalRef.newInstance(object), callback) : new ProxyType(GlobalRef.newInstance(object), callback));
      }
   }

   public static int getweakrefcount(PyObject object) {
      return GlobalRef.getCount(object);
   }

   public static PyList getweakrefs(PyObject object) {
      return GlobalRef.getRefs(object);
   }
}
