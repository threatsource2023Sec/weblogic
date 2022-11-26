package org.apache.velocity.context;

public interface Context {
   Object put(String var1, Object var2);

   Object get(String var1);

   boolean containsKey(Object var1);

   Object[] getKeys();

   Object remove(Object var1);
}
