package com.oracle.weblogic.lifecycle.config.database;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.configuration.api.Configured;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.jvnet.hk2.annotations.Service;

@Service
@ConfiguredBy("/lifecycle-config/environments/environment/partition-ref")
public class PartitionRefConfigService extends LifecycleConfigService {
   static final String PATH_KEY = "/lifecycle-config/environments/environment/partition-ref";
   static final String ID = "id";
   static final String RUNTIME_REF = "runtimeRef";
   @Configured
   private String id;
   @Configured
   private String runtimeRef;
   @Inject
   private RuntimeConfigManager runtimeManager;

   public String getId() {
      return this.id;
   }

   public String getRuntimeRef() {
      return this.runtimeRef;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setRuntimeRef(String runtimeRef) {
      this.runtimeRef = runtimeRef;
   }

   public static String getInstanceId(EnvironmentConfigService environmentConfigService, Map map) {
      return ConfigUtil.addWithSeparator(environmentConfigService.getInstanceId(), (String)map.get("id"));
   }

   public EnvironmentConfigService getEnvironment() {
      return (EnvironmentConfigService)this.getServiceLocator().getService(EnvironmentConfigService.class, this.getParentInstanceId(), new Annotation[0]);
   }

   public RuntimeConfigService getRuntime() {
      return this.runtimeManager.getRuntimeByName(this.getRuntimeRef());
   }
}
