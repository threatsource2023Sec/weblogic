package weblogic.deploy.event;

import java.util.EventListener;
import weblogic.deploy.common.Debug;
import weblogic.management.configuration.BasicDeploymentMBean;

public class VetoableDeploymentEvent extends BaseDeploymentEvent {
   private static final long serialVersionUID = 2451377168814537861L;
   public static final BaseDeploymentEvent.EventType APP_ACTIVATE = new BaseDeploymentEvent.EventType("AppActivated", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (VetoableDeploymentEvent.isDebugEnabled()) {
            VetoableDeploymentEvent.debug("EventType('AppActivated'): vetoableApplicationActivate() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         VetoableDeploymentListener vetoableDeploymentListener = (VetoableDeploymentListener)listener;
         vetoableDeploymentListener.vetoableApplicationDeploy((VetoableDeploymentEvent)evt);
      }
   });
   public static final BaseDeploymentEvent.EventType APP_DEPLOY = new BaseDeploymentEvent.EventType("AppDeployed", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (VetoableDeploymentEvent.isDebugEnabled()) {
            VetoableDeploymentEvent.debug("EventType('AppDeployed'): vetoableApplicationDeploy() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         ((VetoableDeploymentListener)listener).vetoableApplicationDeploy((VetoableDeploymentEvent)evt);
      }
   });
   public static final BaseDeploymentEvent.EventType APP_START = new BaseDeploymentEvent.EventType("AppStarted", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (VetoableDeploymentEvent.isDebugEnabled()) {
            VetoableDeploymentEvent.debug("EventType('AppStarted'): vetoableApplicationStart() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         VetoableDeploymentListener vetoableDeploymentListener = (VetoableDeploymentListener)listener;
         if (!(vetoableDeploymentListener instanceof ApplicationVersionLifecycleListenerAdapter)) {
            vetoableDeploymentListener.vetoableApplicationDeploy((VetoableDeploymentEvent)evt);
         }

      }
   });
   public static final BaseDeploymentEvent.EventType APP_UNDEPLOY = new BaseDeploymentEvent.EventType("AppDeleted", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (VetoableDeploymentEvent.isDebugEnabled()) {
            VetoableDeploymentEvent.debug("EventType('AppDeleted'): vetoableApplicationUndeploy() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         ((VetoableDeploymentListener)listener).vetoableApplicationUndeploy((VetoableDeploymentEvent)evt);
      }
   });
   private final boolean isNewAppDeployment;
   private final SecurityInfo securityInfo = new SecurityInfo(this);

   private VetoableDeploymentEvent(Object source, BaseDeploymentEvent.EventType type, BasicDeploymentMBean deployMBean, boolean isStaticAppDeployment, boolean isNewAppDeployment, String[] modules, String[] targets) {
      super(source, type, deployMBean, isStaticAppDeployment, modules, targets);
      this.isNewAppDeployment = isNewAppDeployment;
   }

   protected String toStringContent() {
      StringBuffer sb = new StringBuffer();
      sb.append(super.toStringContent()).append(",").append("isNewApp=").append(this.isNewAppDeployment).append(",").append("SecurityInfo=").append(this.securityInfo);
      return sb.toString();
   }

   public static VetoableDeploymentEvent create(Object source, BaseDeploymentEvent.EventType type, BasicDeploymentMBean deployMBean, boolean isNewDeploy, String[] modules, String[] targets) {
      return new VetoableDeploymentEvent(source, type, deployMBean, false, isNewDeploy, modules, targets);
   }

   public static VetoableDeploymentEvent create(Object source, BaseDeploymentEvent.EventType type, BasicDeploymentMBean deployMBean, boolean isStaticDeploy, boolean isNewDeploy, String[] modules, String[] targets) {
      return new VetoableDeploymentEvent(source, type, deployMBean, isStaticDeploy, isNewDeploy, modules, targets);
   }

   public boolean isNewAppDeployment() {
      return this.isNewAppDeployment;
   }

   public SecurityInfo getSecurityInfo() {
      return this.securityInfo;
   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   private static void debug(String msg) {
      Debug.deploymentDebug("<VetoableDeploymentEvent>: " + msg);
   }
}
