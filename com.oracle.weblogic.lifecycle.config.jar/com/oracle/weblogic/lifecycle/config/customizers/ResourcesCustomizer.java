package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.PropertyBean;
import com.oracle.weblogic.lifecycle.config.Resource;
import com.oracle.weblogic.lifecycle.config.ResourceHandler;
import com.oracle.weblogic.lifecycle.config.Resources;
import java.beans.PropertyVetoException;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class ResourcesCustomizer {
   @Inject
   private XmlService xmlService;

   public Resource createResource(Resources resources, String resourceName, String resourceType, Map properties, ServiceLocator locator) {
      Resource resource = (Resource)this.xmlService.createBean(Resource.class);

      try {
         resource.setName(resourceName);
      } catch (PropertyVetoException var10) {
         throw new RuntimeException(var10);
      }

      resource.setType(resourceType);
      Iterator var7 = properties.keySet().iterator();

      while(var7.hasNext()) {
         String propertyName = (String)var7.next();
         PropertyBean property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);
         property.setName(propertyName);
         property.setValue((String)properties.get(propertyName));
         resource.addProperty(property);
      }

      this.validateResource(resources, resource, locator);
      return resources.addResource(resource);
   }

   private Resource internalUpdateResource(Resources resources, String resourceName, Map properties, ServiceLocator locator) {
      Resource resource = resources.lookupResource(resourceName);
      Iterator var6 = properties.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         String propertyName = (String)entry.getKey();
         String propertyValue = (String)entry.getValue();
         PropertyBean foundProperty = resource.lookupProperty(propertyName);
         if (foundProperty != null) {
            if (propertyValue == null) {
               resource.removeProperty(foundProperty.getName());
            } else {
               foundProperty.setValue(propertyValue);
            }
         } else {
            if (propertyValue == null) {
               throw new IllegalArgumentException("cannot set property [" + propertyName + "] for resource [" + resource.getName() + "] without any value");
            }

            PropertyBean property = (PropertyBean)this.xmlService.createBean(PropertyBean.class);
            property.setName(propertyName);
            property.setValue(propertyValue);
            resource.addProperty(property);
         }
      }

      this.validateResource(resources, resource, locator);
      return resource;
   }

   public Resource updateResource(Resources resources, String resourceName, Map properties, ServiceLocator locator) {
      XmlHk2ConfigurationBean xmlBean = (XmlHk2ConfigurationBean)resources;
      XmlRootHandle rootHandle = xmlBean._getRoot();
      boolean success = false;
      XmlHandleTransaction transaction = rootHandle.lockForTransaction();

      Resource var10;
      try {
         Resource retVal = this.internalUpdateResource(resources, resourceName, properties, locator);
         success = true;
         var10 = retVal;
      } finally {
         if (success) {
            transaction.commit();
         } else {
            transaction.abandon();
         }

      }

      return var10;
   }

   public Resource deleteResource(Resources resources, String resourceName, ServiceLocator locator) {
      return resources.removeResource(resourceName);
   }

   public Resource getResource(Resources resources, String resourceName, ServiceLocator locator) {
      return resources.lookupResource(resourceName);
   }

   public ResourceHandler getResourceHandler(Resources resources, Resource resource, ServiceLocator locator) {
      ResourceHandler handler = null;
      Collection resourceHandlers = locator.getAllServices(ResourceHandler.class, new Annotation[0]);
      Iterator var6 = resourceHandlers.iterator();

      while(var6.hasNext()) {
         ResourceHandler resourceHandler = (ResourceHandler)var6.next();
         if (resourceHandler.handles(resource)) {
            handler = resourceHandler;
            break;
         }
      }

      return handler;
   }

   private void validateResource(Resources resources, Resource resource, ServiceLocator locator) {
      ResourceHandler handler = this.getResourceHandler(resources, resource, locator);
      if (handler == null) {
         throw new RuntimeException("No resource handler for resource type [" + resource.getType() + "] found");
      } else {
         handler.validate(resource);
      }
   }
}
