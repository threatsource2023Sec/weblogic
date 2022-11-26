package org.python.core;

import java.util.Set;
import org.python.util.Generic;

class BootstrapTypesSingleton {
   private final Set BOOTSTRAP_TYPES;

   private BootstrapTypesSingleton() {
      this.BOOTSTRAP_TYPES = Generic.set();
      this.BOOTSTRAP_TYPES.add(PyObject.class);
      this.BOOTSTRAP_TYPES.add(PyType.class);
      this.BOOTSTRAP_TYPES.add(PyBuiltinCallable.class);
      this.BOOTSTRAP_TYPES.add(PyDataDescr.class);
   }

   public static Set getInstance() {
      return BootstrapTypesSingleton.LazyHolder.INSTANCE.BOOTSTRAP_TYPES;
   }

   // $FF: synthetic method
   BootstrapTypesSingleton(Object x0) {
      this();
   }

   private static class LazyHolder {
      private static final BootstrapTypesSingleton INSTANCE = new BootstrapTypesSingleton();
   }
}
