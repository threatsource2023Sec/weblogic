package org.python.core;

import java.util.Iterator;

public class JavaIterator extends PyIterator {
   private final Iterator proxy;

   public JavaIterator(Iterable proxy) {
      this(proxy.iterator());
   }

   public JavaIterator(Iterator proxy) {
      this.proxy = proxy;
   }

   public PyObject __iternext__() {
      return this.proxy.hasNext() ? Py.java2py(this.proxy.next()) : null;
   }
}
