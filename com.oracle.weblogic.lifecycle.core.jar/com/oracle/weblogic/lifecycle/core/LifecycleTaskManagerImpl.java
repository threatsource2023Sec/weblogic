package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.LifecycleTask;
import com.oracle.weblogic.lifecycle.LifecycleTaskManager;
import com.oracle.weblogic.lifecycle.LifecycleTaskPlugin;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import java.util.Map;
import java.util.Properties;
import javax.inject.Inject;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;

@Service
public class LifecycleTaskManagerImpl implements LifecycleTaskManager {
   @Inject
   private LifecyclePluginFactory lifecyclePluginFactory;
   @Inject
   private ServiceLocator locator;
   @Inject
   private RuntimeManager runtimeManager;
   @Inject
   private LifecycleConfigFactory lifecycleConfigFactory;

   public String getTaskStatus(String runtimeName, String taskType, Map properties) throws LifecycleException {
      LifecycleTaskPlugin plugin = this.lifecyclePluginFactory.getTaskPlugin(taskType);
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(runtimeName);
      if (plugin != null) {
         String taskStatus = plugin.getTaskStatus(runtime, properties);
         return taskStatus;
      } else {
         return null;
      }
   }

   public String getTaskProgress(String runtimeName, String taskType, Map properties) throws LifecycleException {
      LifecycleTaskPlugin plugin = this.lifecyclePluginFactory.getTaskPlugin(taskType);
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(runtimeName);
      if (plugin != null) {
         String taskStatus = plugin.getTaskProgress(runtime, properties);
         return taskStatus;
      } else {
         return null;
      }
   }

   public LifecycleTask getLifecycleTask(String runtimeName, String taskType, Map properties) throws LifecycleException {
      LifecycleTaskPlugin plugin = this.lifecyclePluginFactory.getTaskPlugin(taskType);
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(runtimeName);
      return plugin != null ? plugin.getLifecycleTask(runtime, properties) : null;
   }

   private LifecycleRuntime createRuntime(String runtimeType, String runtimeName, Properties properties) {
      LifecycleRuntime runtimeObject = new LifecycleRuntimeImpl(runtimeType, runtimeName, properties);
      this.locator.inject(runtimeObject);
      return runtimeObject;
   }

   public void cancelTask(String runtimeName, String taskType, Map properties) throws LifecycleException {
      LifecycleTaskPlugin plugin = this.lifecyclePluginFactory.getTaskPlugin(taskType);
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(runtimeName);
      if (plugin != null) {
         plugin.cancelTask(runtime, properties);
      }

   }
}
