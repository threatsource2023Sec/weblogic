package com.oracle.weblogic.lifecycle.provisioning.core.util;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.core.ComponentMetadata;
import java.net.URI;
import java.util.Iterator;
import java.util.Set;

/** @deprecated */
@Deprecated
public final class TemplateBasedComponentMetadata extends ComponentMetadata {
   /** @deprecated */
   @Deprecated
   public TemplateBasedComponentMetadata(String name, boolean selectable, String description, String version, Set affiliates, Iterable documentURIs) {
      super(name, version, description, selectable, documentURIs, affiliates);
   }

   public final String toString() {
      StringBuilder sb = new StringBuilder("TemplateBasedComponentMetadata {");
      sb.append("\n\t name=").append(this.getName()).append(";\t selectable=").append(this.isSelectable()).append(";\t version=").append(this.getVersion()).append(";\t description=").append(this.getDescription());
      Iterable provisioningResources = this.getProvisioningResources();
      if (provisioningResources != null) {
         Iterator var3 = provisioningResources.iterator();

         while(var3.hasNext()) {
            ProvisioningResource provisioningResource = (ProvisioningResource)var3.next();
            if (provisioningResource != null) {
               sb.append("\n\t\tURI: ");
               URI uri = provisioningResource.getResource();
               if (uri == null) {
                  sb.append("null");
               } else {
                  sb.append(uri.toString());
               }
            }
         }
      }

      sb.append("\n\tDependency => ");
      String delim = " ";

      for(Iterator var8 = this.getDependentComponentNames().iterator(); var8.hasNext(); delim = ", ") {
         String dep = (String)var8.next();
         sb.append(delim).append(dep);
      }

      sb.append("\n}");
      return sb.toString();
   }
}
