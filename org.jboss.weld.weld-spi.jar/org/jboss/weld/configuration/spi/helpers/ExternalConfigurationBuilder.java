package org.jboss.weld.configuration.spi.helpers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jboss.weld.configuration.spi.ExternalConfiguration;

public final class ExternalConfigurationBuilder {
   private final Map builder = new HashMap();

   public ExternalConfigurationBuilder add(String key, Object value) {
      this.builder.put(key, value);
      return this;
   }

   public ExternalConfiguration build() {
      return new ExternalConfigurationImpl(Collections.unmodifiableMap(this.builder));
   }

   private static class ExternalConfigurationImpl implements ExternalConfiguration {
      private final Map properties;

      private ExternalConfigurationImpl(Map properties) {
         this.properties = properties;
      }

      public Map getConfigurationProperties() {
         return this.properties;
      }

      public void cleanup() {
      }

      public String toString() {
         return "ExternalConfigurationImpl [properties=" + this.properties + "]";
      }

      // $FF: synthetic method
      ExternalConfigurationImpl(Map x0, Object x1) {
         this(x0);
      }
   }
}
