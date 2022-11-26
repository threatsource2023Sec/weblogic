package weblogic.deploy.service.internal.targetserver;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.Deployment;
import weblogic.deploy.service.DeploymentContext;
import weblogic.deploy.service.DeploymentRequest;

public class DeploymentContextImpl implements DeploymentContext {
   private final TargetRequestImpl deploymentRequest;
   private HashMap contextComponents = new HashMap();
   private boolean requiresRestart;

   public DeploymentContextImpl(TargetRequestImpl request) {
      this.deploymentRequest = request;
   }

   public DeploymentRequest getDeploymentRequest() {
      return this.deploymentRequest;
   }

   public InputStream getDataAsStream(String targetPath, String sourcePath, Deployment deployment, String lockPath) throws IOException {
      return null;
   }

   public final boolean isRestartRequired() {
      return this.requiresRestart;
   }

   public final void setRestartRequired(boolean restartRequired) {
      this.requiresRestart = restartRequired;
   }

   public void addContextComponent(String contextId, Object contextObject) {
      if (this.isDebugEnabled()) {
         this.debug(" +++ adding contextId entry ('" + contextId + ",'" + contextObject + "')");
      }

      this.contextComponents.put(contextId, contextObject);
   }

   public Object getContextComponent(String contextId) {
      Object retObj = this.contextComponents.get(contextId);
      if (retObj == null) {
         if (this.isDebugEnabled()) {
            this.debug(" +++ contextObject is NULL");
         }
      } else if (this.isDebugEnabled()) {
         this.debug(" +++ contextObject of type:" + retObj.getClass().getName() + " = " + retObj);
      }

      return retObj;
   }

   public Map getContextComponents() {
      return (Map)this.contextComponents.clone();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toString()).append("(");
      sb.append("request='").append(this.deploymentRequest.toString()).append("',");
      sb.append("contextComponents='").append(this.contextComponents).append("',");
      sb.append("requiresRestart='").append(this.requiresRestart).append("')");
      return sb.toString();
   }

   private final void debug(String message) {
      Debug.serviceDebug(message);
   }

   private final boolean isDebugEnabled() {
      return Debug.isServiceDebugEnabled();
   }
}
