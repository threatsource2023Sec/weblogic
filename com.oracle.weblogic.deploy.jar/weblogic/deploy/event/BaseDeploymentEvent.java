package weblogic.deploy.event;

import java.security.AccessController;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class BaseDeploymentEvent extends EventObject {
   private static final long serialVersionUID = -7782817013969844896L;
   private EventType type;
   private BasicDeploymentMBean deployMBean;
   private boolean isStaticAppDeployment;
   private String[] modules;
   private String[] targets;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   protected BaseDeploymentEvent(Object source, EventType type, BasicDeploymentMBean deployMBean, boolean isStaticAppDeployment, String[] modules, String[] targets) {
      super(source);
      this.type = type;
      this.deployMBean = deployMBean;
      this.isStaticAppDeployment = isStaticAppDeployment;
      if (modules != null && modules.length > 0) {
         this.modules = modules;
      }

      if (targets != null && targets.length > 0) {
         this.targets = targets;
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getClass().getName()).append("{").append(this.toStringContent()).append("}");
      return sb.toString();
   }

   protected String toStringContent() {
      StringBuffer sb = new StringBuffer();
      sb.append("EventType=").append(this.type).append(",").append("BasicDeployment=").append(this.deployMBean).append(",").append("isStaticAppDeploy=").append(this.isStaticAppDeployment).append(",").append("Modules=").append(Arrays.toString(this.modules)).append(",").append("Targets=").append(Arrays.toString(this.targets));
      return sb.toString();
   }

   public AppDeploymentMBean getAppDeployment() {
      return !(this.deployMBean instanceof AppDeploymentMBean) ? null : (AppDeploymentMBean)this.deployMBean;
   }

   public SystemResourceMBean getSystemResource() {
      return !(this.deployMBean instanceof SystemResourceMBean) ? null : (SystemResourceMBean)this.deployMBean;
   }

   public String[] getModules() {
      return this.modules;
   }

   public String[] getTargets() {
      return this.targets;
   }

   public boolean isAdminServer() {
      return ManagementService.getRuntimeAccess(kernelId).isAdminServer();
   }

   public boolean isStaticAppDeployment() {
      return this.isStaticAppDeployment;
   }

   public EventType getType() {
      return this.type;
   }

   ListenerAdapter getListenerAdapter() {
      return this.type.getListenerAdapter();
   }

   interface ListenerAdapter {
      void notifyListener(EventListener var1, BaseDeploymentEvent var2) throws DeploymentVetoException;
   }

   public static class EventType {
      private String name;
      private ListenerAdapter adapter;

      protected EventType(String name, ListenerAdapter adapter) {
         this.name = name;
         this.adapter = adapter;
      }

      public String toString() {
         return this.name;
      }

      public String getName() {
         return this.name;
      }

      private ListenerAdapter getListenerAdapter() {
         return this.adapter;
      }
   }
}
