package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.properties.ConfidentialPropertyValue;
import com.oracle.weblogic.lifecycle.properties.PropertyValue;
import com.oracle.weblogic.lifecycle.properties.StringPropertyValue;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class PartitionCustomizer {
   @Inject
   private XmlService xmlService;

   public Runtime getRuntime(Partition partition) {
      XmlHk2ConfigurationBean config = (XmlHk2ConfigurationBean)partition;
      return (Runtime)config._getParent();
   }

   public Partition update(Partition partition, Map properties) {
      XmlHk2ConfigurationBean xmlBean = (XmlHk2ConfigurationBean)partition;
      XmlRootHandle rootHandle = xmlBean._getRoot();
      boolean success = false;
      XmlHandleTransaction transaction = rootHandle.lockForTransaction();

      try {
         Iterator var7 = properties.keySet().iterator();

         while(var7.hasNext()) {
            String propertyName = (String)var7.next();
            if (!propertyName.equalsIgnoreCase("id") && !propertyName.equalsIgnoreCase("name")) {
               PropertyBean property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);
               String propertyValue = getString((PropertyValue)properties.get(propertyName));

               try {
                  property.setName(propertyName);
                  property.setValue(propertyValue);
               } catch (Exception var15) {
                  throw new RuntimeException(var15);
               }

               partition.removeProperty(propertyName);
               partition.addProperty(property);
            }
         }

         success = true;
         return partition;
      } finally {
         if (success) {
            transaction.commit();
         } else {
            transaction.abandon();
         }

      }
   }

   private static String getString(PropertyValue value) {
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
}
