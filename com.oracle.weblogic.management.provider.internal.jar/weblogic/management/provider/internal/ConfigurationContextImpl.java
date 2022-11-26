package weblogic.management.provider.internal;

import java.util.HashMap;
import java.util.Map;
import weblogic.deploy.service.ConfigurationContext;
import weblogic.deploy.service.DeploymentRequest;
import weblogic.diagnostics.debug.DebugLogger;

public class ConfigurationContextImpl implements ConfigurationContext {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private DeploymentRequest deploymentRequest;
   private HashMap contextComponents = new HashMap();

   ConfigurationContextImpl(DeploymentRequest deployRequest) {
      this.deploymentRequest = deployRequest;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created configuration context impl " + this);
      }

   }

   public DeploymentRequest getDeploymentRequest() {
      return this.deploymentRequest;
   }

   public void addContextComponent(String contextId, Object contextObject) {
      this.contextComponents.put(contextId, contextObject);
   }

   public Object getContextComponent(String contextId) {
      return this.contextComponents.get(contextId);
   }

   public Map getContextComponents() {
      return this.contextComponents;
   }
}
