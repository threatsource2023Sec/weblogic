package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import weblogic.management.workflow.command.WorkflowContext;

public final class DeprovisionComponentCommand extends ProvisioningBaseCommand {
   public final void initialize(WorkflowContext wc) {
      String className = DeprovisionComponentCommand.class.getName();
      String methodName = "initialize";
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "initialize" + " ENTRY " + wc);
      }

      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "initialize" + " RETURN");
      }

   }

   public final boolean execute() throws LifecycleException {
      String className = DeprovisionComponentCommand.class.getName();
      String methodName = "execute";
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "execute" + " ENTRY");
      }

      RuntimeManager runtimeManager = this.runtimeManager;
      if (runtimeManager == null) {
         throw new IllegalStateException("runtimeManager is null and has not been initialized");
      } else {
         LifecycleRuntime lifecycleRuntime = runtimeManager.getRuntime("TemplateRuntime");
         if (lifecycleRuntime == null) {
            if (logger != null && logger.isLoggable(Level.SEVERE)) {
               logger.logp(Level.SEVERE, className, "execute", "LifecycleRuntime {0} does not exist", lifecycleRuntime);
            }

            throw new LifecycleException("LifecycleRuntime TemplateRuntime does not exist");
         } else {
            if (this.partitionName != null && !this.partitionName.isEmpty()) {
               List runtimeList = runtimeManager.getRuntimes("wls");
               if (runtimeList == null || runtimeList.isEmpty()) {
                  throw new IllegalStateException("No runtimes of type 'wls' exist.");
               }

               LifecyclePartition wlsPartition = null;
               Iterator var8 = runtimeList.iterator();
               if (var8.hasNext()) {
                  LifecycleRuntime lr = (LifecycleRuntime)var8.next();
                  wlsPartition = lr.getPartition(this.partitionName);
               }

               if (wlsPartition == null) {
                  throw new IllegalStateException("WLS Partition does not exist: " + this.partitionName);
               }
            }

            Map partitionProperties = new HashMap();
            if (this.componentName != null) {
               partitionProperties.put("componentName", PropertyValueFactory.getStringPropertyValue(this.componentName));
            }

            Map configurableAttributeValues = ProvisioningBaseCommand.convert(this.configurableAttributes);
            if (configurableAttributeValues != null && !configurableAttributeValues.isEmpty()) {
               partitionProperties.put("configurableAttributes", PropertyValueFactory.getPropertiesPropertyValue(configurableAttributeValues));
            }

            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug("Invoking lifecycleRuntime.deletePartition(String, Map) with " + this.partitionName + " and " + partitionProperties);
            }

            lifecycleRuntime.deletePartition(this.partitionName, partitionProperties);
            boolean returnValue = true;
            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug(className + " " + "execute" + " RETURN " + returnValue);
            }

            return returnValue;
         }
      }
   }
}
