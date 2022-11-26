package com.oracle.weblogic.lifecycle.provisioning.api;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;

public class ProvisioningResource implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Properties properties;
   private final URI resource;

   public ProvisioningResource(URI resource) {
      this((Properties)null, resource);
   }

   public ProvisioningResource(Properties properties, URI resource) {
      Objects.requireNonNull(resource);
      this.resource = resource;
      if (properties == null) {
         this.properties = new Properties();
      } else {
         this.properties = properties;
      }

   }

   public final Properties getProperties() {
      return this.properties;
   }

   public final URI getResource() {
      return this.resource;
   }

   public int hashCode() {
      int hashCode = 17;
      Object resource = this.getResource();
      int c = resource == null ? 0 : resource.hashCode();
      hashCode = 37 * hashCode + c;
      Object properties = this.getProperties();
      c = properties == null ? 0 : properties.hashCode();
      hashCode = 37 * hashCode + c;
      return hashCode;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ProvisioningResource) {
         ProvisioningResource him = (ProvisioningResource)other;
         Object resource = this.getResource();
         if (resource == null) {
            if (him.getResource() != null) {
               return false;
            }
         } else if (!resource.equals(him.getResource())) {
            return false;
         }

         Object properties = this.getProperties();
         if (properties == null) {
            if (him.getProperties() != null) {
               return false;
            }
         } else if (!properties.equals(him.getProperties())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      URI uri = this.getResource();
      if (uri == null) {
         sb.append("null");
      } else {
         sb.append(uri.toString());
      }

      Properties p = this.getProperties();
      if (p != null && !p.isEmpty()) {
         sb.append(" ").append(p);
      }

      return sb.toString();
   }
}
