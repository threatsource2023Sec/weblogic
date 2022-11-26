package org.glassfish.hk2.extras.hk2bridge.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;

public class CrossOverDescriptor extends AbstractActiveDescriptor {
   private final ServiceLocator remoteLocator;
   private final ActiveDescriptor remote;
   private boolean remoteReified;

   public CrossOverDescriptor(ServiceLocator local, ActiveDescriptor localService) {
      super(localService);
      this.remoteLocator = local;
      this.remote = localService;
      this.remoteReified = localService.isReified();
      if (this.remoteReified) {
         this.setScopeAsAnnotation(this.remote.getScopeAsAnnotation());
      } else {
         this.setScope(this.remote.getScope());
      }

      this.addMetadata("org.jvnet.hk2.hk2bridge.locator.id", Long.toString(local.getLocatorId()));
      this.addMetadata("org.jvnet.hk2.hk2bridge.service.id", Long.toString(localService.getServiceId()));
   }

   public boolean isReified() {
      return true;
   }

   private synchronized void checkState() {
      if (!this.remoteReified) {
         this.remoteReified = true;
         if (!this.remote.isReified()) {
            this.remoteLocator.reifyDescriptor(this.remote);
         }
      }
   }

   public Class getImplementationClass() {
      this.checkState();
      return this.remote.getImplementationClass();
   }

   public Type getImplementationType() {
      this.checkState();
      return this.remote.getImplementationType();
   }

   public void setImplementationType(Type t) {
      this.checkState();
      throw new AssertionError("Can not set type of remove descriptor");
   }

   public Set getContractTypes() {
      this.checkState();
      return this.remote.getContractTypes();
   }

   public Annotation getScopeAsAnnotation() {
      this.checkState();
      return this.remote.getScopeAsAnnotation();
   }

   public Class getScopeAnnotation() {
      this.checkState();
      return this.remote.getScopeAnnotation();
   }

   public Set getQualifierAnnotations() {
      this.checkState();
      return this.remote.getQualifierAnnotations();
   }

   public List getInjectees() {
      this.checkState();
      return this.remote.getInjectees();
   }

   public Long getFactoryServiceId() {
      this.checkState();
      return this.remote.getFactoryServiceId();
   }

   public Long getFactoryLocatorId() {
      this.checkState();
      return this.remote.getFactoryLocatorId();
   }

   public Object create(ServiceHandle root) {
      this.checkState();
      return this.remoteLocator.getService(this.remote, root);
   }

   public void dispose(Object instance) {
      this.checkState();
      this.remote.dispose(instance);
   }
}
