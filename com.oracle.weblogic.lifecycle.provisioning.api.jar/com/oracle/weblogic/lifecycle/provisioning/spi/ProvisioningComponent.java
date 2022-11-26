package com.oracle.weblogic.lifecycle.provisioning.spi;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public class ProvisioningComponent {
   private final ProvisioningComponentIdentifier id;
   private final String description;
   private final boolean selectable;
   private final Set provisioningResources;
   private final Set affiliateIds;

   public ProvisioningComponent(ProvisioningComponent prototype) {
      Objects.requireNonNull(prototype);
      ProvisioningComponentIdentifier id = prototype.getId();
      Objects.requireNonNull(id);
      if (id.getName() == null) {
         throw new IllegalArgumentException("prototype", new IllegalStateException("prototype.getId().getName() == null"));
      } else {
         this.id = id;
         this.description = prototype.getDescription();
         this.selectable = prototype.isSelectable();
         Set provisioningResources = prototype.getProvisioningResources();
         Set myProvisioningResources = new HashSet();
         if (provisioningResources != null && !provisioningResources.isEmpty()) {
            Iterator var5 = provisioningResources.iterator();

            while(var5.hasNext()) {
               ProvisioningResource provisioningResource = (ProvisioningResource)var5.next();
               if (provisioningResource != null) {
                  URI uri = provisioningResource.getResource();
                  if (uri == null) {
                     throw new IllegalArgumentException("provisioningResources", new IllegalStateException("The following ProvisioningResource returned null from its getResource() method: " + provisioningResource));
                  }

                  if (!uri.isAbsolute()) {
                     throw new IllegalArgumentException("provisioningResources", new IllegalStateException("The following ProvisioningResource returned a non-absolute URI from its getResource() method: " + provisioningResource));
                  }

                  myProvisioningResources.add(provisioningResource);
               }
            }
         }

         this.provisioningResources = Collections.unmodifiableSet(myProvisioningResources);
         Set myAffiliateIds = new HashSet();
         Set affiliateIds = prototype.getAffiliatedComponentIds();
         if (affiliateIds != null && !affiliateIds.isEmpty()) {
            Iterator var11 = affiliateIds.iterator();

            while(var11.hasNext()) {
               ProvisioningComponentIdentifier affiliateId = (ProvisioningComponentIdentifier)var11.next();
               if (affiliateId != null) {
                  myAffiliateIds.add(affiliateId);
               }
            }
         }

         this.affiliateIds = Collections.unmodifiableSet(myAffiliateIds);
      }
   }

   public ProvisioningComponent(ProvisioningComponentIdentifier id, String description, boolean selectable, Set provisioningResources, Set affiliateIds) {
      Objects.requireNonNull(id);
      if (id.getName() == null) {
         throw new IllegalArgumentException("id", new IllegalStateException("id.getName() == null"));
      } else {
         this.id = id;
         this.description = description;
         this.selectable = selectable;
         Set myProvisioningResources = new HashSet();
         if (provisioningResources != null && !provisioningResources.isEmpty()) {
            Iterator var7 = provisioningResources.iterator();

            while(var7.hasNext()) {
               ProvisioningResource provisioningResource = (ProvisioningResource)var7.next();
               if (provisioningResource != null) {
                  URI uri = provisioningResource.getResource();
                  if (uri == null) {
                     throw new IllegalArgumentException("provisioningResources", new IllegalStateException("The following ProvisioningResource returned null from its getResource() method: " + provisioningResource));
                  }

                  if (!uri.isAbsolute()) {
                     throw new IllegalArgumentException("provisioningResources", new IllegalStateException("The following ProvisioningResource returned a non-absolute URI from its getResource() method: " + provisioningResource));
                  }

                  myProvisioningResources.add(provisioningResource);
               }
            }
         }

         this.provisioningResources = Collections.unmodifiableSet(myProvisioningResources);
         Set myAffiliateIds = new HashSet();
         if (affiliateIds != null && !affiliateIds.isEmpty()) {
            Iterator var11 = affiliateIds.iterator();

            while(var11.hasNext()) {
               ProvisioningComponentIdentifier affiliateId = (ProvisioningComponentIdentifier)var11.next();
               if (affiliateId != null) {
                  myAffiliateIds.add(affiliateId);
               }
            }
         }

         this.affiliateIds = Collections.unmodifiableSet(myAffiliateIds);
      }
   }

   public final ProvisioningComponentIdentifier getId() {
      return this.id;
   }

   public final String getDescription() {
      return this.description;
   }

   public final boolean isSelectable() {
      return this.selectable;
   }

   public final Set getProvisioningResources() {
      return this.provisioningResources;
   }

   public final Set getAffiliatedComponentIds() {
      return this.affiliateIds;
   }

   public int hashCode() {
      int hashCode = 17;
      Object id = this.getId();
      int c = id == null ? 0 : id.hashCode();
      hashCode = hashCode * 37 + c;
      Object description = this.getDescription();
      c = description == null ? 0 : description.hashCode();
      hashCode = hashCode * 37 + c;
      boolean selectable = this.isSelectable();
      c = selectable ? 1 : 0;
      hashCode = hashCode * 37 + c;
      Object provisioningResources = this.getProvisioningResources();
      c = provisioningResources == null ? 0 : provisioningResources.hashCode();
      hashCode = hashCode * 37 + c;
      return hashCode;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ProvisioningComponent) {
         ProvisioningComponent him = (ProvisioningComponent)other;
         Object myId = this.getId();
         if (myId == null) {
            if (him.getId() != null) {
               return false;
            }
         } else if (!myId.equals(him.getId())) {
            return false;
         }

         Object description = this.getDescription();
         if (description == null) {
            if (him.getDescription() != null) {
               return false;
            }
         } else if (!description.equals(him.getDescription())) {
            return false;
         }

         boolean isSelectable = this.isSelectable();
         if (this.isSelectable()) {
            if (!him.isSelectable()) {
               return false;
            }
         } else if (him.isSelectable()) {
            return false;
         }

         Object provisioningResources = this.getProvisioningResources();
         if (provisioningResources == null) {
            if (him.getProvisioningResources() != null) {
               return false;
            }
         } else if (!provisioningResources.equals(him.getProvisioningResources())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Object id = this.getId();
      if (id == null) {
         sb.append("Unidentified ProvisioningComponent:null");
      } else {
         sb.append(id);
      }

      sb.append(" {");
      sb.append("description: ").append(String.valueOf(this.getDescription()));
      sb.append("; selectable: ").append(Boolean.toString(this.isSelectable()));
      sb.append("; provisioningResources: ").append(this.getProvisioningResources());
      sb.append("; affiliatedComponentIds: ").append(this.getAffiliatedComponentIds());
      sb.append("}");
      return sb.toString();
   }
}
