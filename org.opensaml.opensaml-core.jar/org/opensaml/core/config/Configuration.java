package org.opensaml.core.config;

public interface Configuration {
   Object get(Class var1, String var2);

   void register(Class var1, Object var2, String var3);

   Object deregister(Class var1, String var2);
}
