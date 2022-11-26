package org.jboss.weld.injection.spi;

public interface ResourceReference {
   Object getInstance();

   void release();
}
