package org.jboss.weld.metadata;

import java.util.Collection;
import org.jboss.weld.bootstrap.spi.Scanning;

public class ScanningImpl implements Scanning {
   private final Collection includes;
   private final Collection excludes;

   public ScanningImpl(Collection includes, Collection excludes) {
      this.includes = includes;
      this.excludes = excludes;
   }

   public Collection getExcludes() {
      return this.excludes;
   }

   public Collection getIncludes() {
      return this.includes;
   }

   public String toString() {
      return "Includes: " + this.includes + "; Excludes: " + this.excludes;
   }
}
