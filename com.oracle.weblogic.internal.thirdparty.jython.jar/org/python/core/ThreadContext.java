package org.python.core;

class ThreadContext {
   static ThreadLocal initializingProxy = new ThreadLocal() {
      protected Object[] initialValue() {
         return new Object[1];
      }
   };
}
