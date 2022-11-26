package org.python.netty.util;

/** @deprecated */
@Deprecated
public final class DomainMappingBuilder {
   private final DomainNameMappingBuilder builder;

   public DomainMappingBuilder(Object defaultValue) {
      this.builder = new DomainNameMappingBuilder(defaultValue);
   }

   public DomainMappingBuilder(int initialCapacity, Object defaultValue) {
      this.builder = new DomainNameMappingBuilder(initialCapacity, defaultValue);
   }

   public DomainMappingBuilder add(String hostname, Object output) {
      this.builder.add(hostname, output);
      return this;
   }

   public DomainNameMapping build() {
      return this.builder.build();
   }
}
