package weblogic.deploy.event;

import java.util.EventListener;
import weblogic.deploy.common.Debug;
import weblogic.management.configuration.AppDeploymentMBean;

public class DeploymentEvent extends BaseDeploymentEvent {
   private static final long serialVersionUID = 5996987879320598338L;
   public static final BaseDeploymentEvent.EventType APP_ACTIVATED = new BaseDeploymentEvent.EventType("AppActivated", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (DeploymentEvent.isDebugEnabled()) {
            DeploymentEvent.debug("EventType('AppActivated'): applicationActivated() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         DeploymentEventListener deploymentEventListener = (DeploymentEventListener)listener;
         deploymentEventListener.applicationDeployed((DeploymentEvent)evt);
      }
   });
   public static final BaseDeploymentEvent.EventType APP_DEPLOYED = new BaseDeploymentEvent.EventType("AppDeployed", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (DeploymentEvent.isDebugEnabled()) {
            DeploymentEvent.debug("EventType('AppDeployed'): applicationDeployed() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         ((DeploymentEventListener)listener).applicationDeployed((DeploymentEvent)evt);
      }
   });
   public static final BaseDeploymentEvent.EventType APP_REDEPLOYED = new BaseDeploymentEvent.EventType("AppRedeployed", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (DeploymentEvent.isDebugEnabled()) {
            DeploymentEvent.debug("EventType('AppRedeployed'): applicationRedeployed() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         ((DeploymentEventListener)listener).applicationRedeployed((DeploymentEvent)evt);
      }
   });
   public static final BaseDeploymentEvent.EventType APP_STARTED = new BaseDeploymentEvent.EventType("AppStarted", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (DeploymentEvent.isDebugEnabled()) {
            DeploymentEvent.debug("EventType('AppStarted'): applicationStarted() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         DeploymentEventListener deploymentEventListener = (DeploymentEventListener)listener;
         if (!(deploymentEventListener instanceof ApplicationVersionLifecycleListenerAdapter)) {
            deploymentEventListener.applicationDeployed((DeploymentEvent)evt);
         }

      }
   });
   public static final BaseDeploymentEvent.EventType APP_DELETED = new BaseDeploymentEvent.EventType("AppDeleted", new BaseDeploymentEvent.ListenerAdapter() {
      public void notifyListener(EventListener listener, BaseDeploymentEvent evt) throws DeploymentVetoException {
         if (DeploymentEvent.isDebugEnabled()) {
            DeploymentEvent.debug("EventType('AppDeleted'): applicationDeleted() fired on : '" + listener + "' for the event : '" + evt + "'");
         }

         ((DeploymentEventListener)listener).applicationDeleted((DeploymentEvent)evt);
      }
   });

   private DeploymentEvent(Object source, BaseDeploymentEvent.EventType type, AppDeploymentMBean deployMBean, boolean isStaticDeployment, String[] modules, String[] targets) {
      super(source, type, deployMBean, isStaticDeployment, modules, targets);
   }

   public static DeploymentEvent create(Object source, BaseDeploymentEvent.EventType type, AppDeploymentMBean deployMBean, String[] modules, String[] targets) {
      return new DeploymentEvent(source, type, deployMBean, false, modules, targets);
   }

   public static DeploymentEvent create(Object source, BaseDeploymentEvent.EventType type, AppDeploymentMBean deployMBean, boolean isStaticDeployment, String[] modules, String[] targets) {
      return new DeploymentEvent(source, type, deployMBean, isStaticDeployment, modules, targets);
   }

   private static boolean isDebugEnabled() {
      return Debug.isDeploymentDebugEnabled();
   }

   private static void debug(String msg) {
      Debug.deploymentDebug("<DeploymentEvent>: " + msg);
   }
}
