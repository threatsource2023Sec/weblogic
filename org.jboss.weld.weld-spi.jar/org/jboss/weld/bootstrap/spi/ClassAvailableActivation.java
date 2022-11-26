package org.jboss.weld.bootstrap.spi;

public interface ClassAvailableActivation {
   String getClassName();

   boolean isInverted();
}
