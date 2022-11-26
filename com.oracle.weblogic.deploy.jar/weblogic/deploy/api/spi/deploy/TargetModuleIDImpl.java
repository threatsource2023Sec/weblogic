package weblogic.deploy.api.spi.deploy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;

public class TargetModuleIDImpl extends WebLogicTargetModuleID {
   private static final long serialVersionUID = 1L;
   private static final String NAME_PROP = "Name";
   private static final String TARGET_PROP = "Target";
   private static final String TARGET_TYPE_PROP = "WebLogicTargetType";
   private static final String PARENT_PROP = "Application";
   private static final boolean debug = Debug.isDebug("deploy");
   private String name;
   private Target target;
   private WebLogicTargetModuleID parentTID;
   private List childTID;
   private transient WebLogicDeploymentManager manager;
   private Set servers;
   private boolean targeted;
   private Hashtable objectName;
   private String webURL;

   public TargetModuleIDImpl(String name, Target target, TargetModuleID parent, int type, DeploymentManager manager) throws IllegalArgumentException {
      super(type);
      this.parentTID = null;
      this.childTID = null;
      this.servers = null;
      this.targeted = true;
      this.name = name;
      if (name == null) {
         throw new AssertionError("No name for TargetModuleID");
      } else {
         this.target = target;
         this.parentTID = (WebLogicTargetModuleID)parent;
         this.manager = (WebLogicDeploymentManager)manager;
         this.objectName = this.setObjectName();
         if (this.parentTID != null) {
            ((TargetModuleIDImpl)this.parentTID).addChildTargetModuleID(this);
         }

      }
   }

   public TargetModuleIDImpl(String name, Target target, TargetModuleID parent, ModuleType type, DeploymentManager manager) throws IllegalArgumentException {
      this(name, target, parent, type.getValue(), manager);
   }

   public TargetModuleID[] getChildTargetModuleID() {
      return this.childTID != null && this.childTID.size() != 0 ? (TargetModuleID[])((TargetModuleID[])this.childTID.toArray(new TargetModuleID[this.childTID.size()])) : null;
   }

   public Target getTarget() {
      return this.target;
   }

   public String getModuleID() {
      return this.name;
   }

   public String getWebURL() {
      if (this.webURL != null && this.webURL.contains(":")) {
         return this.webURL;
      } else {
         if (this.manager != null && this.manager.getServerConnection() != null) {
            this.manager.getServerConnection().populateWarUrlInChildren(this);
         }

         return this.webURL;
      }
   }

   public void setWebURL(String argWebUrl) {
      this.webURL = argWebUrl;
   }

   public String toString() {
      return this.objectName.toString();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o == null) {
         return false;
      } else {
         return o instanceof TargetModuleID ? this.hashCode() == o.hashCode() : false;
      }
   }

   public int hashCode() {
      return this.objectName.hashCode();
   }

   public TargetModuleID getParentTargetModuleID() {
      return this.parentTID;
   }

   public String getApplicationName() {
      return this.parentTID != null ? this.parentTID.getApplicationName() : ApplicationVersionUtils.getApplicationName(this.name);
   }

   public String getVersion() {
      return this.parentTID != null ? this.parentTID.getVersion() : ApplicationVersionUtils.getVersionId(this.name);
   }

   public String getArchiveVersion() {
      return this.parentTID != null ? this.parentTID.getVersion() : ApplicationVersionUtils.getArchiveVersion(this.getVersion());
   }

   public String getPlanVersion() {
      return this.parentTID != null ? this.parentTID.getVersion() : ApplicationVersionUtils.getPlanVersion(this.getVersion());
   }

   public Target[] getServers() throws IllegalStateException {
      try {
         Set servers = this.getServersForTarget();
         return (Target[])((Target[])servers.toArray(new Target[servers.size()]));
      } catch (ServerConnectionException var3) {
         if (debug) {
            var3.printStackTrace();
         }

         IllegalStateException e = new IllegalStateException(SPIDeployerLogger.connectionError());
         e.initCause(var3.getRootCause());
         throw e;
      }
   }

   public boolean isOnVirtualHost() {
      return ((TargetImpl)this.getTarget()).isVirtualHost();
   }

   public boolean isOnVirtualTarget() {
      return ((TargetImpl)this.getTarget()).isVirtualTarget();
   }

   public boolean isOnServer() {
      return ((TargetImpl)this.getTarget()).isServer();
   }

   public boolean isOnCluster() {
      return ((TargetImpl)this.getTarget()).isCluster();
   }

   public boolean isOnJMSServer() {
      return ((TargetImpl)this.getTarget()).isJMSServer();
   }

   public boolean isOnSAFAgent() {
      return ((TargetImpl)this.getTarget()).isSAFAgent();
   }

   public Set getServersForTarget() throws ServerConnectionException {
      if (this.servers == null) {
         this.setServersForTarget();
      }

      return this.servers;
   }

   void addChildTargetModuleID(TargetModuleID child) {
      if (child != null) {
         if (this.childTID == null) {
            this.childTID = new ArrayList();
         }

         this.childTID.add(child);
      }

   }

   public WebLogicDeploymentManager getManager() {
      return this.manager;
   }

   public boolean isTargeted() {
      return this.targeted;
   }

   public void setTargeted(boolean targeted) {
      this.targeted = targeted;
   }

   private Hashtable setObjectName() {
      Hashtable props = new Hashtable(5);
      if (this.name != null && this.name.length() > 0) {
         props.put("Name", this.name);
      }

      props.put("Target", this.target.getName());
      props.put("WebLogicTargetType", this.target.getDescription());
      if (this.parentTID != null) {
         props.put("Application", this.parentTID.getModuleID());
      }

      return props;
   }

   private void setServersForTarget() throws ServerConnectionException {
      this.servers = new HashSet();
      ServerConnection serverConnection = this.manager.getServerConnection();
      if (this.manager.isConnected()) {
         if (this.isOnServer()) {
            this.servers.add((TargetImpl)this.getTarget());
         } else if (this.isOnCluster()) {
            this.servers.addAll(serverConnection.getServersForCluster((TargetImpl)this.getTarget()));
         } else if (this.isOnVirtualHost()) {
            this.servers.addAll(serverConnection.getServersForHost((TargetImpl)this.getTarget()));
         } else if (this.isOnVirtualTarget()) {
            this.servers.addAll(serverConnection.getServersForHost((TargetImpl)this.getTarget()));
         } else if (this.isOnJMSServer()) {
            this.servers.addAll(serverConnection.getServersForJmsServer((TargetImpl)this.getTarget()));
         } else if (this.isOnSAFAgent()) {
            this.servers.addAll(serverConnection.getServersForSafAgent((TargetImpl)this.getTarget()));
         }
      }

      if (debug) {
         this.dumpServers();
      }

   }

   private void dumpServers() {
      try {
         Debug.say("Servers in target " + this.getTarget().getName() + ":");
         Iterator s = this.getServersForTarget().iterator();

         while(s.hasNext()) {
            Debug.say("   " + ((TargetImpl)s.next()).getName());
         }
      } catch (ServerConnectionException var2) {
         var2.printStackTrace();
      }

   }
}
