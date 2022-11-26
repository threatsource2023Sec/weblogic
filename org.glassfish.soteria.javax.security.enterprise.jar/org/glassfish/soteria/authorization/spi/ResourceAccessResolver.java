package org.glassfish.soteria.authorization.spi;

public interface ResourceAccessResolver {
   boolean hasAccessToWebResource(String var1, String... var2);
}
