package com.oracle.weblogic.lifecycle.provisioning.api;

import java.io.Serializable;
import java.util.Objects;

public final class ProvisioningComponentIdentifier implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String name;
   private final String version;
   private final String stringRepresentation;

   public ProvisioningComponentIdentifier(String name) {
      this(name, (String)null);
   }

   public ProvisioningComponentIdentifier(String name, String version) {
      Objects.requireNonNull(name);
      this.name = name;
      this.version = version;
      StringBuilder sb = (new StringBuilder(name)).append(":");
      if (version == null) {
         sb.append("null");
      } else {
         sb.append(version);
      }

      this.stringRepresentation = sb.toString();
   }

   public final String getName() {
      return this.name;
   }

   public final String getVersion() {
      return this.version;
   }

   public final boolean implies(ProvisioningComponentIdentifier other) {
      if (other == this) {
         return true;
      } else if (other == null) {
         return false;
      } else {
         Object name = this.getName();
         if (name == null) {
            if (other.getName() != null) {
               return false;
            }
         } else if (!name.equals(other.getName())) {
            return false;
         }

         Object version = this.getVersion();
         return version == null || version.equals(other.getVersion());
      }
   }

   public final int hashCode() {
      int hashCode = 17;
      Object name = this.getName();
      int c = name == null ? 0 : name.hashCode();
      hashCode = 37 * hashCode + c;
      Object version = this.getVersion();
      c = version == null ? 0 : version.hashCode();
      hashCode = 37 * hashCode + c;
      return hashCode;
   }

   public final boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ProvisioningComponentIdentifier) {
         ProvisioningComponentIdentifier her = (ProvisioningComponentIdentifier)other;
         Object name = this.getName();
         if (name == null) {
            if (her.getName() != null) {
               return false;
            }
         } else if (!name.equals(her.getName())) {
            return false;
         }

         Object version = this.getVersion();
         if (version == null) {
            if (her.getVersion() != null) {
               return false;
            }
         } else if (!version.equals(her.getVersion())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public final String toString() {
      return this.stringRepresentation;
   }
}
