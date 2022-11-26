package com.bea.core.repackaged.springframework.jca.support;

import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.resource.ResourceException;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;

public class ResourceAdapterFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
   @Nullable
   private ResourceAdapter resourceAdapter;
   @Nullable
   private BootstrapContext bootstrapContext;
   @Nullable
   private WorkManager workManager;
   @Nullable
   private XATerminator xaTerminator;

   public void setResourceAdapterClass(Class resourceAdapterClass) {
      this.resourceAdapter = (ResourceAdapter)BeanUtils.instantiateClass(resourceAdapterClass);
   }

   public void setResourceAdapter(ResourceAdapter resourceAdapter) {
      this.resourceAdapter = resourceAdapter;
   }

   public void setBootstrapContext(BootstrapContext bootstrapContext) {
      this.bootstrapContext = bootstrapContext;
   }

   public void setWorkManager(WorkManager workManager) {
      this.workManager = workManager;
   }

   public void setXaTerminator(XATerminator xaTerminator) {
      this.xaTerminator = xaTerminator;
   }

   public void afterPropertiesSet() throws ResourceException {
      if (this.resourceAdapter == null) {
         throw new IllegalArgumentException("'resourceAdapter' or 'resourceAdapterClass' is required");
      } else {
         if (this.bootstrapContext == null) {
            this.bootstrapContext = new SimpleBootstrapContext(this.workManager, this.xaTerminator);
         }

         this.resourceAdapter.start(this.bootstrapContext);
      }
   }

   @Nullable
   public ResourceAdapter getObject() {
      return this.resourceAdapter;
   }

   public Class getObjectType() {
      return this.resourceAdapter != null ? this.resourceAdapter.getClass() : ResourceAdapter.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() {
      if (this.resourceAdapter != null) {
         this.resourceAdapter.stop();
      }

   }
}
