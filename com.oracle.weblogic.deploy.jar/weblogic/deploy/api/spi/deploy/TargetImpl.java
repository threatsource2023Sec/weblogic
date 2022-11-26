package weblogic.deploy.api.spi.deploy;

import javax.enterprise.deploy.spi.DeploymentManager;
import weblogic.deploy.api.shared.WebLogicTargetType;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTarget;

public class TargetImpl extends WebLogicTarget {
   private static final long serialVersionUID = 1L;
   private String name;
   private transient WebLogicDeploymentManager manager;

   public TargetImpl() {
   }

   public TargetImpl(String name, int type, DeploymentManager manager) {
      super(type);
      this.name = name;
      this.manager = (WebLogicDeploymentManager)manager;
   }

   public TargetImpl(String name, WebLogicTargetType type, DeploymentManager manager) {
      this(name, type.getValue(), manager);
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return super.toString();
   }

   public String toString() {
      return this.getName().concat("/").concat(this.getDescription());
   }

   public WebLogicDeploymentManager getManager() {
      return this.manager;
   }

   public boolean isServer() {
      return this.getValue() == WebLogicTargetType.SERVER.getValue();
   }

   public boolean isCluster() {
      return this.getValue() == WebLogicTargetType.CLUSTER.getValue();
   }

   public boolean isVirtualHost() {
      return this.getValue() == WebLogicTargetType.VIRTUALHOST.getValue();
   }

   public boolean isVirtualTarget() {
      return this.getValue() == WebLogicTargetType.VIRTUALTARGET.getValue();
   }

   public boolean isJMSServer() {
      return this.getValue() == WebLogicTargetType.JMSSERVER.getValue();
   }

   public boolean isSAFAgent() {
      return this.getValue() == WebLogicTargetType.SAFAGENT.getValue();
   }
}
