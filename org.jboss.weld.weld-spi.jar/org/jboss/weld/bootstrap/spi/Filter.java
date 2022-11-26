package org.jboss.weld.bootstrap.spi;

import java.util.Collection;

public interface Filter {
   String getName();

   Collection getSystemPropertyActivations();

   Collection getClassAvailableActivations();
}
