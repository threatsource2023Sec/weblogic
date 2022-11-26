package org.glassfish.hk2.configuration.hub.api;

import java.util.Map;

public interface Type {
   String getName();

   Map getInstances();

   Instance getInstance(String var1);

   Object getMetadata();

   void setMetadata(Object var1);
}
