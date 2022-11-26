package com.oracle.weblogic.lifecycle.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;

public class VetoableLifecycleConfigChangeListener implements VetoableChangeListener {
   private final ServiceLocator locator;

   public VetoableLifecycleConfigChangeListener(ServiceLocator locator) {
      this.locator = locator;
   }

   public VetoableLifecycleConfigChangeListener() {
      this((ServiceLocator)null);
   }

   private static LifecycleConfig getRoot(Object child) {
      if (child == null) {
         return null;
      } else if (!(child instanceof XmlHk2ConfigurationBean)) {
         return null;
      } else {
         XmlHk2ConfigurationBean asBean = (XmlHk2ConfigurationBean)child;
         XmlRootHandle handle = asBean._getRoot();
         return handle == null ? null : (LifecycleConfig)handle.getRoot();
      }
   }

   private void hk2VetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
      Object oldValue = event.getOldValue();
      Object newValue = event.getNewValue();
      PartitionRef other;
      Environment environment;
      Partition partition;
      if (newValue == null && oldValue != null) {
         if (oldValue instanceof Partition) {
            partition = (Partition)oldValue;
            Environments environments = (Environments)this.locator.getService(Environments.class, new Annotation[0]);
            other = environments.getPartitionRef(partition);
            if (other != null) {
               environment = other.getEnvironment();
               throw new PropertyVetoException("Partition " + partition.getName() + " is referenced by environment " + environment.getName(), event);
            }
         } else if (oldValue instanceof Environment) {
            Environment environment = (Environment)oldValue;
            List services = this.locator.getAllServices(Service.class, new Annotation[0]);
            String environmentName = environment.getName();
            Iterator var27 = services.iterator();

            while(var27.hasNext()) {
               Service service = (Service)var27.next();
               if (equals(environmentName, service.getEnvironmentRef())) {
                  throw new PropertyVetoException("Environemnt " + environmentName + " is referenced by service  " + service.getName(), event);
               }
            }
         }
      } else if (newValue != null && oldValue == null) {
         String id;
         if (newValue instanceof PartitionRef) {
            PartitionRef partitionRef = (PartitionRef)newValue;
            id = partitionRef.getId();
            other = (PartitionRef)this.locator.getService(PartitionRef.class, id, new Annotation[0]);
            if (other != null) {
               environment = other.getEnvironment();
               throw new PropertyVetoException("Partition " + id + " is already referenced by " + environment.getName(), event);
            }
         } else {
            String name;
            if (newValue instanceof Partition) {
               partition = (Partition)newValue;
               id = partition.getId();
               Partition other = (Partition)this.locator.getService(Partition.class, id, new Annotation[0]);
               if (other != null) {
                  Runtime runtime = other.getRuntime();
                  throw new PropertyVetoException("Partition " + id + " already exists in " + runtime.getName(), event);
               }

               name = partition.getName();
               Runtime runtime = partition.getRuntime();
               other = runtime.getPartitionByName(name);
               if (other != null) {
                  throw new PropertyVetoException("Name " + name + " is not unique", event);
               }
            } else if (newValue instanceof Service) {
               Service service = (Service)newValue;
               id = service.getId();
               Service other = (Service)this.locator.getService(Service.class, id, new Annotation[0]);
               if (other != null) {
                  Tenant tenant = other.getTenant();
                  throw new PropertyVetoException("Service " + id + " already exists in " + tenant.getName(), event);
               }

               name = service.getName();
               Tenant tenant = service.getTenant();
               other = tenant.getServiceByName(name);
               if (other != null) {
                  throw new PropertyVetoException("Name " + name + " is not unique", event);
               }

               String environmentName = service.getEnvironmentRef();
               List services = this.locator.getAllServices(Service.class, new Annotation[0]);
               Iterator var11 = services.iterator();

               while(var11.hasNext()) {
                  Service otherService = (Service)var11.next();
                  if (equals(environmentName, otherService.getEnvironmentRef())) {
                     throw new PropertyVetoException("Environemnt " + environmentName + " already has service  " + otherService.getName(), event);
                  }
               }

               if (environmentName != null) {
                  LifecycleConfig root = getRoot(service);
                  Environments environments = root.getEnvironments();
                  Environment found = environments.lookupEnvironment(environmentName);
                  if (found == null) {
                     throw new PropertyVetoException("There is no environment with name " + environmentName + " while adding service " + service.getId() + " and name " + service.getName(), event);
                  }
               }
            }
         }
      }

   }

   public void vetoableChange(PropertyChangeEvent event) throws PropertyVetoException {
      this.hk2VetoableChange(event);
   }

   public static boolean equals(Object object1, Object object2) {
      if (object1 == object2) {
         return true;
      } else {
         return object1 != null && object2 != null ? object1.equals(object2) : false;
      }
   }
}
