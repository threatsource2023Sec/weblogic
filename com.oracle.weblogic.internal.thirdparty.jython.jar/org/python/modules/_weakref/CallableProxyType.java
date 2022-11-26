package org.python.modules._weakref;

import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "weakcallableproxy",
   isBaseType = false
)
public class CallableProxyType extends ProxyType {
   public static final PyType TYPE;

   public CallableProxyType(ReferenceBackend ref, PyObject callback) {
      super(TYPE, ref, callback);
   }

   public PyObject __call__(PyObject[] args, String[] kws) {
      return this.weakcallableproxy___call__(args, kws);
   }

   final PyObject weakcallableproxy___call__(PyObject[] args, String[] kws) {
      return this.py().__call__(args, kws);
   }

   static {
      PyType.addBuilder(CallableProxyType.class, new PyExposer());
      TYPE = PyType.fromClass(CallableProxyType.class);
   }

   private static class weakcallableproxy___call___exposer extends PyBuiltinMethod {
      public weakcallableproxy___call___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public weakcallableproxy___call___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new weakcallableproxy___call___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((CallableProxyType)this.self).weakcallableproxy___call__(var1, var2);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new weakcallableproxy___call___exposer("__call__")};
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("weakcallableproxy", CallableProxyType.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
