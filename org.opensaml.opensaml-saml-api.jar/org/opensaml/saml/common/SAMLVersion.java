package org.opensaml.saml.common;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public final class SAMLVersion {
   @Nonnull
   public static final SAMLVersion VERSION_10 = new SAMLVersion(1, 0);
   @Nonnull
   public static final SAMLVersion VERSION_11 = new SAMLVersion(1, 1);
   @Nonnull
   public static final SAMLVersion VERSION_20 = new SAMLVersion(2, 0);
   private final int majorVersion;
   private final int minorVersion;
   @Nonnull
   @NotEmpty
   private final String versionString;

   private SAMLVersion(int major, int minor) {
      this.majorVersion = major;
      this.minorVersion = minor;
      this.versionString = this.majorVersion + "." + this.minorVersion;
   }

   public static final SAMLVersion valueOf(int majorVersion, int minorVersion) {
      if (majorVersion == 1) {
         if (minorVersion == 0) {
            return VERSION_10;
         }

         if (minorVersion == 1) {
            return VERSION_11;
         }
      } else if (majorVersion == 2 && minorVersion == 0) {
         return VERSION_20;
      }

      return new SAMLVersion(majorVersion, minorVersion);
   }

   public static final SAMLVersion valueOf(@Nonnull String version) {
      String[] components = version.split("\\.");
      return valueOf(Integer.valueOf(components[0]), Integer.valueOf(components[1]));
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   @Nonnull
   @NotEmpty
   public String toString() {
      return this.versionString;
   }
}
