package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeLiteral;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponent;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ComponentMetadata extends ProvisioningComponent {
   private final Set dependentComponentNames;
   private final ConcurrentMap configurableAttributes;

   public ComponentMetadata(String name) {
      this(new ProvisioningComponentIdentifier(name), (String)null, false, (Set)null, (Set)null, (Set)null);
   }

   public ComponentMetadata(String name, boolean selectable) {
      this(new ProvisioningComponentIdentifier(name), (String)null, selectable, (Set)null, (Set)null, (Set)null);
   }

   /** @deprecated */
   @Deprecated
   public ComponentMetadata(String name, boolean selectable, Set affiliates) {
      this(new ProvisioningComponentIdentifier(name), (String)null, selectable, (Set)null, asProvisioningComponentIdentifiers(affiliates), (Set)null);
   }

   /** @deprecated */
   @Deprecated
   public ComponentMetadata(String name, String version, String description, boolean selectable, Iterable documentURIs, Set affiliates) {
      this(new ProvisioningComponentIdentifier(name, version), description, selectable, asProvisioningResources(documentURIs), asProvisioningComponentIdentifiers(affiliates), (Set)null);
   }

   /** @deprecated */
   @Deprecated
   public ComponentMetadata(String name, String version, String description, boolean selectable, Iterable documentURIs, Set affiliates, Set dependentComponentNames) {
      this(new ProvisioningComponentIdentifier(name, version), description, selectable, asProvisioningResources(documentURIs), asProvisioningComponentIdentifiers(affiliates), dependentComponentNames);
   }

   /** @deprecated */
   @Deprecated
   public ComponentMetadata(String name, String version, String description, boolean selectable, Set provisioningResources, Set affiliates) {
      this(new ProvisioningComponentIdentifier(name, version), description, selectable, provisioningResources, asProvisioningComponentIdentifiers(affiliates), (Set)null);
   }

   public ComponentMetadata(ProvisioningComponent delegate) {
      super(delegate);
      this.dependentComponentNames = new HashSet();
      this.configurableAttributes = new ConcurrentHashMap();
   }

   /** @deprecated */
   @Deprecated
   public ComponentMetadata(String name, String version, String description, boolean selectable, Set provisioningResources, Set affiliates, Set dependentComponentNames) {
      this(new ProvisioningComponentIdentifier(name, version), description, selectable, provisioningResources, asProvisioningComponentIdentifiers(affiliates), dependentComponentNames);
   }

   public ComponentMetadata(ProvisioningComponentIdentifier id, String description, boolean selectable, Set provisioningResources, Set affiliateIds, Set dependentComponentNames) {
      super(id, description, selectable, provisioningResources, affiliateIds);
      this.dependentComponentNames = new HashSet();
      this.configurableAttributes = new ConcurrentHashMap();
      if (dependentComponentNames != null && !dependentComponentNames.isEmpty()) {
         Iterator var7 = dependentComponentNames.iterator();

         while(var7.hasNext()) {
            String dependentComponentName = (String)var7.next();
            if (dependentComponentName != null) {
               this.addDependentComponentName(dependentComponentName);
            }
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public final String getName() {
      ProvisioningComponentIdentifier id = this.getId();
      String returnValue;
      if (id == null) {
         returnValue = null;
      } else {
         returnValue = id.getName();
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public final String getVersion() {
      ProvisioningComponentIdentifier id = this.getId();
      String returnValue;
      if (id == null) {
         returnValue = null;
      } else {
         returnValue = id.getVersion();
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public final Collection getAllDocumentURIs() {
      Collection returnValue = null;
      Set provisioningResources = this.getProvisioningResources();
      if (provisioningResources != null && !provisioningResources.isEmpty()) {
         returnValue = new HashSet();
         Iterator var3 = provisioningResources.iterator();

         while(var3.hasNext()) {
            ProvisioningResource provisioningResource = (ProvisioningResource)var3.next();
            if (provisioningResource != null) {
               returnValue.add(provisioningResource.getResource());
            }
         }
      }

      Object returnValue;
      if (returnValue != null && !returnValue.isEmpty()) {
         returnValue = Collections.unmodifiableCollection(returnValue);
      } else {
         returnValue = Collections.emptySet();
      }

      return (Collection)returnValue;
   }

   /** @deprecated */
   @Deprecated
   public final boolean hasDocuments() {
      Collection provisioningResources = this.getProvisioningResources();
      return provisioningResources != null && !provisioningResources.isEmpty();
   }

   /** @deprecated */
   @Deprecated
   public final synchronized Set getAffiliatedComponentNames() {
      Set affiliatedIds = this.getAffiliatedComponentIds();
      Set returnValue = new HashSet();
      if (affiliatedIds != null && !affiliatedIds.isEmpty()) {
         Iterator var3 = affiliatedIds.iterator();

         while(var3.hasNext()) {
            ProvisioningComponentIdentifier affiliatedId = (ProvisioningComponentIdentifier)var3.next();
            if (affiliatedId != null) {
               returnValue.add(affiliatedId.getName());
            }
         }
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   protected final synchronized void addAffiliatedComponentName(String affiliate) {
   }

   public final synchronized Set getDependentComponentNames() {
      return Collections.unmodifiableSet(this.dependentComponentNames);
   }

   protected final synchronized void addDependentComponentName(String componentName) {
      if (componentName != null && !componentName.equals(this.getName())) {
         this.dependentComponentNames.add(componentName);
      }

   }

   protected final ConfigurableAttribute putConfigurableAttribute(String name, ConfigurableAttribute configurableAttribute) {
      ConfigurableAttribute returnValue;
      if (configurableAttribute == null) {
         returnValue = null;
      } else {
         if (name == null) {
            name = "";
         }

         ConfigurableAttributeLiteral ca = new ConfigurableAttributeLiteral(name, configurableAttribute.description(), configurableAttribute.defaultValue(), configurableAttribute.isSensitive());
         returnValue = (ConfigurableAttribute)this.configurableAttributes.put(name, ca);
      }

      return returnValue;
   }

   public ConfigurableAttribute getConfigurableAttribute(String name) {
      ConfigurableAttribute returnValue;
      if (name == null) {
         returnValue = null;
      } else {
         returnValue = (ConfigurableAttribute)this.configurableAttributes.get(name);
      }

      return returnValue;
   }

   public Collection getConfigurableAttributes() {
      return Collections.unmodifiableCollection(this.configurableAttributes.values());
   }

   public int hashCode() {
      int hashCode = super.hashCode();
      Object dependentComponentNames = this.getDependentComponentNames();
      int c = dependentComponentNames == null ? 0 : dependentComponentNames.hashCode();
      hashCode = hashCode * 37 + c;
      Object configurableAttributes = this.getConfigurableAttributes();
      c = configurableAttributes == null ? 0 : configurableAttributes.hashCode();
      hashCode = hashCode * 37 + c;
      return hashCode;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ComponentMetadata) {
         if (!super.equals(other)) {
            return false;
         } else {
            ComponentMetadata him = (ComponentMetadata)other;
            Object dependentComponentNames = this.getDependentComponentNames();
            if (dependentComponentNames == null) {
               if (him.getDependentComponentNames() != null) {
                  return false;
               }
            } else if (!dependentComponentNames.equals(him.getDependentComponentNames())) {
               return false;
            }

            Object configurableAttributes = this.getConfigurableAttributes();
            if (configurableAttributes == null) {
               if (him.getConfigurableAttributes() != null) {
                  return false;
               }
            } else if (!configurableAttributes.equals(him.getConfigurableAttributes())) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ComponentMetadata {id='" + this.getId() + '\'' + ", dependentComponentNames=" + this.getDependentComponentNames() + ", selectable=" + this.isSelectable() + ", configurableAttributes = " + this.getConfigurableAttributes() + '}';
   }

   /** @deprecated */
   @Deprecated
   private static final Set asProvisioningResources(Iterable uris) {
      Set returnValue = new HashSet();
      if (uris != null) {
         Iterator var2 = uris.iterator();

         while(true) {
            URI uri;
            do {
               if (!var2.hasNext()) {
                  return returnValue;
               }

               uri = (URI)var2.next();
            } while(uri == null);

            String uriString = uri.toString();
            Properties properties = null;
            if (uriString != null) {
               properties = new Properties();
               if (uriString.indexOf("!/_partition/") < 0 && uriString.indexOf("/_partition/") < 0) {
                  properties.setProperty("scope", "domain");
               } else {
                  properties.setProperty("scope", "partition");
               }
            }

            returnValue.add(new ProvisioningResource(properties, uri));
         }
      } else {
         return returnValue;
      }
   }

   /** @deprecated */
   @Deprecated
   private static final Set asProvisioningComponentIdentifiers(Iterable affiliates) {
      Set returnValue = new HashSet();
      if (affiliates != null) {
         Iterator var2 = affiliates.iterator();

         while(var2.hasNext()) {
            String affiliate = (String)var2.next();
            if (affiliate != null) {
               ProvisioningComponentIdentifier id = new ProvisioningComponentIdentifier(affiliate);
               returnValue.add(id);
            }
         }
      }

      return returnValue;
   }
}
