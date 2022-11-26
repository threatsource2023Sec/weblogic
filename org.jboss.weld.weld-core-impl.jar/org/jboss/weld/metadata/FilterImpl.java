package org.jboss.weld.metadata;

import java.util.Collection;
import org.jboss.weld.bootstrap.spi.Filter;

public class FilterImpl implements Filter {
   private final String name;
   private final Collection systemPropertyActivation;
   private final Collection classAvailableActivation;

   public FilterImpl(String name, Collection systemPropertyActivation, Collection classAvailableActivation) {
      this.name = name;
      this.systemPropertyActivation = systemPropertyActivation;
      this.classAvailableActivation = classAvailableActivation;
   }

   public String getName() {
      return this.name;
   }

   public Collection getSystemPropertyActivations() {
      return this.systemPropertyActivation;
   }

   public Collection getClassAvailableActivations() {
      return this.classAvailableActivation;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      if (this.getName() != null) {
         builder.append("name: ").append(this.getName());
      }

      if (this.classAvailableActivation != null) {
         builder.append(this.classAvailableActivation);
      }

      if (this.systemPropertyActivation != null) {
         builder.append(this.systemPropertyActivation);
      }

      return builder.toString();
   }
}
