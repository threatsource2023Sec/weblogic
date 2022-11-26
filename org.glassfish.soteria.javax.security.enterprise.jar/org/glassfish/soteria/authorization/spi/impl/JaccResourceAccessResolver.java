package org.glassfish.soteria.authorization.spi.impl;

import org.glassfish.soteria.authorization.JACC;
import org.glassfish.soteria.authorization.spi.ResourceAccessResolver;

public class JaccResourceAccessResolver implements ResourceAccessResolver {
   public boolean hasAccessToWebResource(String resource, String... methods) {
      return JACC.hasAccessToWebResource(resource, methods);
   }
}
