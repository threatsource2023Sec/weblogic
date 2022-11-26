package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class RuntimeCustomizer {
   @Inject
   private XmlService xmlService;

   private void internalUpdate(Runtime runtime, Map properties) {
      Iterator var3 = properties.keySet().iterator();

      while(true) {
         while(true) {
            String propertyName;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               propertyName = (String)var3.next();
            } while(propertyName == null);

            PropertyValue value = (PropertyValue)properties.get(propertyName);
            String propertyValue = getString(value);
            if (propertyName.equalsIgnoreCase("type")) {
               runtime.setType(propertyValue);
            } else {
               if (propertyName.equalsIgnoreCase("name")) {
                  throw new IllegalArgumentException("May not change the name of an existing bean from " + runtime.getName() + " to " + propertyValue);
               }

               if (propertyName.equalsIgnoreCase("hostname")) {
                  runtime.setHostname(propertyValue);
               } else if (propertyName.equalsIgnoreCase("port")) {
                  runtime.setPort(propertyValue);
               } else if (propertyName.equalsIgnoreCase("protocol")) {
                  runtime.setProtocol(propertyValue);
               } else {
                  PropertyBean property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);

                  try {
                     if (propertyName.equalsIgnoreCase("password") && value instanceof StringPropertyValue) {
                        ConfidentialPropertyValue cp = PropertyValueFactory.getConfidentialPropertyValue(propertyValue);
                        propertyValue = cp.getEncryptedValue();
                     }

                     property.setName(propertyName);
                     property.setValue(propertyValue);
                  } catch (Exception var9) {
                     throw new RuntimeException(var9);
                  }

                  runtime.removeProperty(propertyName);
                  runtime.addProperty(property);
               }
            }
         }
      }
   }

   public void update(Runtime runtime, Map properties) {
      Objects.requireNonNull(properties);
      XmlHk2ConfigurationBean xmlBean = (XmlHk2ConfigurationBean)runtime;
      XmlRootHandle rootHandle = xmlBean._getRoot();
      boolean success = false;
      XmlHandleTransaction transaction = rootHandle.lockForTransaction();

      try {
         this.internalUpdate(runtime, properties);
         success = true;
      } finally {
         if (success) {
            transaction.commit();
         } else {
            transaction.abandon();
         }

      }

   }

   public Partition getPartitionById(Runtime runtime, String id) {
      return id == null ? null : runtime.lookupPartition(id);
   }

   public Partition getPartitionByName(Runtime runtime, String name) {
      return null;
   }

   public Partition createPartition(Runtime runtime, Map properties) {
      Partition partition = (Partition)this.xmlService.createBean(Partition.class);
      String partitionId = getString(removeProperty(properties, "id"));
      if (partitionId != null) {
         partition.setId(partitionId);
      } else {
         partitionId = getString(removeProperty(properties, "uuid"));
         if (partitionId == null) {
            throw new RuntimeException("Create Partition config failed: No 'id' in properties map");
         }

         partition.setId(partitionId);
      }

      String name = getString(removeProperty(properties, "name"));
      if (name != null) {
         partition.setName(name);
      } else {
         partition.setName("runtimePartition" + partitionId);
      }

      PropertyBean property;
      for(Iterator var6 = properties.keySet().iterator(); var6.hasNext(); partition.addProperty(property)) {
         String propertyName = (String)var6.next();
         property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);

         try {
            property.setName(propertyName);
            property.setValue(getString((PropertyValue)properties.get(propertyName)));
         } catch (Exception var10) {
            throw new RuntimeException(var10);
         }
      }

      return runtime.addPartition(partition);
   }

   public Partition deletePartition(Runtime runtime, Partition partition) {
      return partition == null ? null : runtime.removePartition(partition);
   }

   private static String getString(PropertyValue value) {
      Objects.requireNonNull(value);
      String propertyValue;
      if (value instanceof StringPropertyValue) {
         propertyValue = ((StringPropertyValue)value).getValue();
      } else if (value instanceof ConfidentialPropertyValue) {
         propertyValue = ((ConfidentialPropertyValue)value).getEncryptedValue();
      } else {
         propertyValue = value.toString();
      }

      return propertyValue;
   }

   private static PropertyValue removeProperty(Map properties, String propertyName) {
      if (properties != null && properties.containsKey(propertyName)) {
         PropertyValue propertyValue = (PropertyValue)properties.get(propertyName);
         properties.remove(propertyName);
         return propertyValue;
      } else {
         return null;
      }
   }
}
