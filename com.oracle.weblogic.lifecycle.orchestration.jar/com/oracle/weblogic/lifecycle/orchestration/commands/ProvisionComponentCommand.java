package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.properties.PropertyValueFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;

public final class ProvisionComponentCommand extends ProvisioningBaseCommand {
   @SharedState
   protected transient MutableString serviceUUID;

   public final void initialize(WorkflowContext wc) {
      String className = ProvisionComponentCommand.class.getName();
      String methodName = "initialize";
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "initialize" + " ENTRY " + wc);
      }

      Objects.requireNonNull(this.partitionName);
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(className + " " + "initialize" + " RETURN");
      }

   }

   public final boolean execute() throws LifecycleException {
      String className = ProvisionComponentCommand.class.getName();
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
            Map partitionProperties = new HashMap();
            if (this.componentName != null) {
               partitionProperties.put("componentName", PropertyValueFactory.getStringPropertyValue(this.componentName));
            }

            if (this.serviceUUID != null && this.serviceUUID.getValue() != null) {
               Map tenantMap = new HashMap();
               tenantMap.put("serviceUUID", PropertyValueFactory.getStringPropertyValue(this.serviceUUID.getValue()));
               partitionProperties.put("properties", PropertyValueFactory.getPropertiesPropertyValue(tenantMap));
            }

            Map configurableAttributeValues = convert(this.configurableAttributes);
            if (configurableAttributeValues != null && !configurableAttributeValues.isEmpty()) {
               partitionProperties.put("configurableAttributes", PropertyValueFactory.getPropertiesPropertyValue(configurableAttributeValues));
            }

            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug("Invoking lifecycleRuntime.createPartition(String, Map) with " + this.partitionName + " and " + partitionProperties);
            }

            lifecycleRuntime.createPartition(this.partitionName, partitionProperties);
            boolean returnValue = true;
            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug(className + " " + "execute" + " RETURN " + returnValue);
            }

            return returnValue;
         }
      }
   }
}
