package org.jboss.weld.bootstrap.api;

public interface Singleton {
   Object get(String var1);

   boolean isSet(String var1);

   void set(String var1, Object var2);

   void clear(String var1);
}
