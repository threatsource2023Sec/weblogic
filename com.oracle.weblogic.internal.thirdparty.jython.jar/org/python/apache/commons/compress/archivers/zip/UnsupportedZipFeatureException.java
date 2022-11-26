package org.python.apache.commons.compress.archivers.zip;

import java.io.Serializable;
import java.util.zip.ZipException;

public class UnsupportedZipFeatureException extends ZipException {
   private final Feature reason;
   private final transient ZipArchiveEntry entry;
   private static final long serialVersionUID = 20161219L;

   public UnsupportedZipFeatureException(Feature reason, ZipArchiveEntry entry) {
      super("unsupported feature " + reason + " used in entry " + entry.getName());
      this.reason = reason;
      this.entry = entry;
   }

   public UnsupportedZipFeatureException(ZipMethod method, ZipArchiveEntry entry) {
      super("unsupported feature method '" + method.name() + "' used in entry " + entry.getName());
      this.reason = UnsupportedZipFeatureException.Feature.METHOD;
      this.entry = entry;
   }

   public UnsupportedZipFeatureException(Feature reason) {
      super("unsupported feature " + reason + " used in archive.");
      this.reason = reason;
      this.entry = null;
   }

   public Feature getFeature() {
      return this.reason;
   }

   public ZipArchiveEntry getEntry() {
      return this.entry;
   }

   public static class Feature implements Serializable {
      private static final long serialVersionUID = 4112582948775420359L;
      public static final Feature ENCRYPTION = new Feature("encryption");
      public static final Feature METHOD = new Feature("compression method");
      public static final Feature DATA_DESCRIPTOR = new Feature("data descriptor");
      public static final Feature SPLITTING = new Feature("splitting");
      private final String name;

      private Feature(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }
   }
}
