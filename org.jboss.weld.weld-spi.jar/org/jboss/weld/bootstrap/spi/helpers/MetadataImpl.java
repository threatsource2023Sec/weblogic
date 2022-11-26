package org.jboss.weld.bootstrap.spi.helpers;

import org.jboss.weld.bootstrap.spi.Metadata;

public class MetadataImpl implements Metadata {
   public static final String LOCATION_NOT_AVAILABLE = "n/a";
   private final String location;
   private final Object value;

   public static MetadataImpl from(Object value) {
      return new MetadataImpl(value);
   }

   public MetadataImpl(Object value) {
      this(value, "n/a");
   }

   public MetadataImpl(Object value, String location) {
      this.location = location;
      this.value = value;
   }

   public String getLocation() {
      return this.location;
   }

   public Object getValue() {
      return this.value;
   }

   public String toString() {
      return this.getLocation();
   }
}
