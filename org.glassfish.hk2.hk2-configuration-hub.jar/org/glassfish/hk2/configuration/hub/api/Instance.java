package org.glassfish.hk2.configuration.hub.api;

public interface Instance {
   Object getBean();

   Object getMetadata();

   void setMetadata(Object var1);
}
