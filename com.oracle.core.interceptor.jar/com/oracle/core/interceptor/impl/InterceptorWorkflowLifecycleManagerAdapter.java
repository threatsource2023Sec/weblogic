package com.oracle.core.interceptor.impl;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.InterceptorMBean;
import weblogic.management.configuration.InterceptorsMBean;
import weblogic.management.workflow.WorkflowLifecycleManager;

@Service
public class InterceptorWorkflowLifecycleManagerAdapter {
   @Inject
   ServiceLocator serviceLocator;
   private InterceptorsMBean interceptorsMBean;
   private WorkflowLifecycleManager workflowLifecycleManager;
   private AtomicLong lastInterceptorsMBeanChangeNumber = new AtomicLong(0L);
   private InterceptorsMBeanChangeListener interceptorsMBeanChangeListener;

   public InterceptorsMBean getInterceptorsMBean() {
      return this.interceptorsMBean;
   }

   public void setInterceptorsMBean(InterceptorsMBean interceptorsMBean) {
      this.interceptorsMBean = interceptorsMBean;
      this.interceptorsMBeanChangeListener = new InterceptorsMBeanChangeListener((RankBasedOrderingService)this.serviceLocator.getService(RankBasedOrderingService.class, new Annotation[0]), interceptorsMBean);
      this.lastInterceptorsMBeanChangeNumber.incrementAndGet();
   }

   public void setWorkflowLifecycleManager(WorkflowLifecycleManager workflowLifecycleManager) {
      this.workflowLifecycleManager = workflowLifecycleManager;
   }

   public long getLastInterceptorsMBeanChangeNumber() {
      return this.lastInterceptorsMBeanChangeNumber.get();
   }

   WorkflowLifecycleManager getWorkflowLifecycleManager() {
      try {
         if (this.workflowLifecycleManager == null) {
            File dir = Files.createTempDirectory("interceptor-workflow").toFile();
            this.workflowLifecycleManager = new WorkflowLifecycleManagerForInterceptors(dir);
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

      return this.workflowLifecycleManager;
   }

   private class InterceptorsMBeanChangeListener implements BeanUpdateListener {
      private RankBasedOrderingService rankBasedOrderingService;
      private InterceptorsMBean interceptorsMBean;

      public InterceptorsMBeanChangeListener(RankBasedOrderingService rankBasedOrderingService, InterceptorsMBean interceptorsMBean) {
         this.rankBasedOrderingService = rankBasedOrderingService;
         this.interceptorsMBean = interceptorsMBean;
         interceptorsMBean.addBeanUpdateListener(this);
         InterceptorMBean[] var4 = interceptorsMBean.getInterceptors();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            InterceptorMBean interceptorMBean = var4[var6];
            interceptorMBean.addBeanUpdateListener(this);
         }

      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         DescriptorBean sourceBean = event.getSourceBean();
         if (sourceBean instanceof InterceptorsMBean) {
            InterceptorsMBean interceptorsMBean = (InterceptorsMBean)sourceBean;
            InterceptorMBean[] var4 = interceptorsMBean.getInterceptors();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               InterceptorMBean mBean = var4[var6];
               mBean.addBeanUpdateListener(this);
            }

            InterceptorWorkflowLifecycleManagerAdapter.this.lastInterceptorsMBeanChangeNumber.incrementAndGet();
         } else if (sourceBean instanceof InterceptorMBean) {
            InterceptorMBean mBeanx = (InterceptorMBean)sourceBean;
            InterceptorWorkflowLifecycleManagerAdapter.this.lastInterceptorsMBeanChangeNumber.incrementAndGet();
         }

      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }
   }

   private static class WorkflowLifecycleManagerForInterceptors extends WorkflowLifecycleManager {
      private WorkflowLifecycleManagerForInterceptors(File baseSaveDir) {
         super(baseSaveDir);
      }

      // $FF: synthetic method
      WorkflowLifecycleManagerForInterceptors(File x0, Object x1) {
         this(x0);
      }
   }
}
