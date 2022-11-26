package org.python.netty.util;

public interface Attribute {
   AttributeKey key();

   Object get();

   void set(Object var1);

   Object getAndSet(Object var1);

   Object setIfAbsent(Object var1);

   /** @deprecated */
   @Deprecated
   Object getAndRemove();

   boolean compareAndSet(Object var1, Object var2);

   /** @deprecated */
   @Deprecated
   void remove();
}
