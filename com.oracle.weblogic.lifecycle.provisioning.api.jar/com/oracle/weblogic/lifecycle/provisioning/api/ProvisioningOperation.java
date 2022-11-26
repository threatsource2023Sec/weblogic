package com.oracle.weblogic.lifecycle.provisioning.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ProvisioningOperation implements Serializable {
   private static final long serialVersionUID = 1L;
   private final ProvisioningOperationDescriptor type;
   private final Set ids;
   private final Map properties;
   private final Map configurableAttributeValues;

   /** @deprecated */
   @Deprecated
   public ProvisioningOperation(ProvisioningOperationDescriptor type, Set provisioningComponentNames) {
      this((Set)asProvisioningComponentIdentifiers(provisioningComponentNames), (ProvisioningOperationDescriptor)type, (Map)null, (Map)null);
   }

   public ProvisioningOperation(ProvisioningComponentIdentifier id, ProvisioningOperationDescriptor type) {
      this((Set)(id == null ? Collections.emptySet() : Collections.singleton(id)), (ProvisioningOperationDescriptor)type, (Map)null, (Map)null);
   }

   /** @deprecated */
   @Deprecated
   public ProvisioningOperation(ProvisioningOperationDescriptor type, Set provisioningComponentNames, Map configurableAttributeValues, Map properties) {
      this(asProvisioningComponentIdentifiers(provisioningComponentNames), type, configurableAttributeValues, properties);
   }

   public ProvisioningOperation(Set ids, ProvisioningOperationDescriptor type, Map configurableAttributeValues, Map properties) {
      Objects.requireNonNull(type);
      this.type = type;
      if (ids != null && !ids.isEmpty()) {
         this.ids = Collections.unmodifiableSet(new HashSet(ids));
      } else {
         this.ids = Collections.emptySet();
      }

      if (configurableAttributeValues != null && !configurableAttributeValues.isEmpty()) {
         Map newValues = new HashMap();
         Collection entries = configurableAttributeValues.entrySet();
         if (entries != null && !entries.isEmpty()) {
            Iterator var7 = entries.iterator();

            label49:
            while(true) {
               while(true) {
                  Map.Entry entry;
                  do {
                     if (!var7.hasNext()) {
                        break label49;
                     }

                     entry = (Map.Entry)var7.next();
                  } while(entry == null);

                  Map provisioningComponentValues = (Map)entry.getValue();
                  if (provisioningComponentValues != null && !provisioningComponentValues.isEmpty()) {
                     newValues.put(entry.getKey(), Collections.unmodifiableMap(new HashMap(provisioningComponentValues)));
                  } else {
                     newValues.put(entry.getKey(), Collections.emptyMap());
                  }
               }
            }
         }

         this.configurableAttributeValues = Collections.unmodifiableMap(newValues);
      } else {
         this.configurableAttributeValues = Collections.emptyMap();
      }

      if (properties != null && !properties.isEmpty()) {
         this.properties = Collections.unmodifiableMap(new HashMap(properties));
      } else {
         this.properties = Collections.emptyMap();
      }

   }

   public final ProvisioningOperationDescriptor getProvisioningOperationDescriptor() {
      return this.type;
   }

   /** @deprecated */
   @Deprecated
   public final Set getProvisioningComponentNames() {
      return asProvisioningComponentNames(this.ids);
   }

   public final Set getProvisioningComponentIds() {
      return this.ids;
   }

   public final Set getProvisioningSequence() throws ProvisioningException {
      assert this.type != null;

      return this.type.getProvisioningSequence(asProvisioningComponentNames(this.getProvisioningComponentIds()));
   }

   public final Map getProperties() {
      return this.properties;
   }

   public final String getProperty(String name) {
      Map properties = this.getProperties();
      String returnValue;
      if (properties == null) {
         returnValue = null;
      } else {
         returnValue = (String)properties.get(name);
      }

      return returnValue;
   }

   public final Map getConfigurableAttributeValues() {
      return this.configurableAttributeValues;
   }

   public final boolean containsConfigurableAttributeValue(String namespace, String attributeName) {
      boolean returnValue = false;
      Map allValues = this.getConfigurableAttributeValues();
      if (allValues != null) {
         Map values = (Map)allValues.get(namespace);
         returnValue = values != null && values.containsKey(attributeName);
      }

      return returnValue;
   }

   public final String getConfigurableAttributeValue(String namespace, String attributeName) {
      String returnValue = null;
      Map allValues = this.getConfigurableAttributeValues();
      if (allValues != null && !allValues.isEmpty()) {
         Map values = (Map)allValues.get(namespace);
         if (values != null) {
            returnValue = (String)values.get(attributeName);
         }
      }

      return returnValue;
   }

   public final int hashCode() {
      int hashCode = 17;
      Object type = this.getProvisioningOperationDescriptor();
      int c = type == null ? 0 : type.hashCode();
      hashCode = hashCode * 37 + c;
      Object ids = this.getProvisioningComponentIds();
      c = ids == null ? 0 : ids.hashCode();
      hashCode = hashCode * 37 + c;
      Object properties = this.getProperties();
      c = properties == null ? 0 : properties.hashCode();
      hashCode = hashCode * 37 + c;
      Object values = this.getConfigurableAttributeValues();
      c = values == null ? 0 : values.hashCode();
      hashCode = hashCode * 37 + c;
      return hashCode;
   }

   public final boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ProvisioningOperation) {
         ProvisioningOperation her = (ProvisioningOperation)other;
         Object type = this.getProvisioningOperationDescriptor();
         if (type == null) {
            if (her.getProvisioningOperationDescriptor() != null) {
               return false;
            }
         } else if (!type.equals(her.getProvisioningOperationDescriptor())) {
            return false;
         }

         Object ids = this.getProvisioningComponentIds();
         if (ids == null) {
            if (her.getProvisioningComponentIds() != null) {
               return false;
            }
         } else if (!ids.equals(her.getProvisioningComponentIds())) {
            return false;
         }

         Object properties = this.getProperties();
         if (properties == null) {
            if (her.getProperties() != null) {
               return false;
            }
         } else if (!properties.equals(her.getProperties())) {
            return false;
         }

         Object values = this.getConfigurableAttributeValues();
         if (values == null) {
            if (her.getConfigurableAttributeValues() != null) {
               return false;
            }
         } else if (!values.equals(her.getConfigurableAttributeValues())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   private static final Set asProvisioningComponentIdentifiers(Set names) {
      Set returnValue = new HashSet();
      if (names != null && !names.isEmpty()) {
         Iterator var2 = names.iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            if (name != null) {
               returnValue.add(new ProvisioningComponentIdentifier(name));
            }
         }
      }

      return Collections.unmodifiableSet(returnValue);
   }

   private static final Set asProvisioningComponentNames(Collection ids) {
      Set returnValue = new HashSet();
      if (ids != null) {
         Iterator var2 = ids.iterator();

         while(var2.hasNext()) {
            ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var2.next();
            if (id != null) {
               returnValue.add(id.getName());
            }
         }
      }

      return Collections.unmodifiableSet(returnValue);
   }
}
