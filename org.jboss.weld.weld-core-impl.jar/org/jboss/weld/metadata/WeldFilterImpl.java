package org.jboss.weld.metadata;

import java.util.Collection;
import org.jboss.weld.bootstrap.spi.WeldFilter;

public class WeldFilterImpl extends FilterImpl implements WeldFilter {
   private final String pattern;

   public WeldFilterImpl(String name, Collection systemPropertyActivation, Collection classAvailableActivation, String pattern) {
      super(name, systemPropertyActivation, classAvailableActivation);
      this.pattern = pattern;
   }

   public String getPattern() {
      return this.pattern;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      if (this.getName() != null) {
         builder.append("name: ").append(this.getName());
      }

      if (this.getPattern() != null) {
         builder.append("pattern: ").append(this.pattern);
      }

      if (this.getClassAvailableActivations() != null) {
         builder.append(this.getClassAvailableActivations());
      }

      if (this.getSystemPropertyActivations() != null) {
         builder.append(this.getSystemPropertyActivations());
      }

      return builder.toString();
   }
}
