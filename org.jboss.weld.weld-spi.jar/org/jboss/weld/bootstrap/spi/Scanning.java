package org.jboss.weld.bootstrap.spi;

import java.util.Collection;
import java.util.Collections;

public interface Scanning {
   Scanning EMPTY_SCANNING = new Scanning() {
      public Collection getIncludes() {
         return Collections.emptyList();
      }

      public Collection getExcludes() {
         return Collections.emptyList();
      }
   };

   Collection getIncludes();

   Collection getExcludes();
}
