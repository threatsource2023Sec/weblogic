package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleTaskPlugin;
import com.oracle.weblogic.lifecycle.PartitionPlugin;
import com.oracle.weblogic.lifecycle.RuntimePlugin;
import java.lang.annotation.Annotation;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
public class LifecyclePluginFactory {
   @Inject
   private ServiceLocator serviceLocator;

   public PartitionPlugin getService(String type) {
      if (this.serviceLocator == null) {
         return null;
      } else {
         PartitionPlugin infraService = (PartitionPlugin)this.serviceLocator.getService(PartitionPlugin.class, type, new Annotation[0]);
         return infraService;
      }
   }

   public RuntimePlugin getRuntimePlugin(String type) {
      if (this.serviceLocator == null) {
         return null;
      } else {
         RuntimePlugin infraService = (RuntimePlugin)this.serviceLocator.getService(RuntimePlugin.class, type, new Annotation[0]);
         return infraService;
      }
   }

   public LifecycleTaskPlugin getTaskPlugin(String type) {
      if (this.serviceLocator == null) {
         return null;
      } else {
         LifecycleTaskPlugin infraService = (LifecycleTaskPlugin)this.serviceLocator.getService(LifecycleTaskPlugin.class, type, new Annotation[0]);
         return infraService;
      }
   }
}
