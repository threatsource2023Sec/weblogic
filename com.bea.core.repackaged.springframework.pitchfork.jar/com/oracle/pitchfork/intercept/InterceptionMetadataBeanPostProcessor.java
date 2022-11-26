package com.oracle.pitchfork.intercept;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.oracle.pitchfork.inject.Jsr250MetadataBeanPostProcessor;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import java.util.HashMap;

public class InterceptionMetadataBeanPostProcessor extends Jsr250MetadataBeanPostProcessor {
   public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      Object o = this.getMetadata(beanName);
      if (o != null && o instanceof InterceptionMetadata) {
         InterceptionMetadata im = (InterceptionMetadata)this.getMetadata(beanName);
         Object maybeProxy = im.createProxyIfNecessary(bean, new HashMap());
         if (im.getDeploymentUnitMetadata().isInvokeLifecycleMethod()) {
            im.invokeLifecycleMethods(maybeProxy, LifecycleEvent.POST_CONSTRUCT);
         }

         return maybeProxy;
      } else {
         return bean;
      }
   }
}
