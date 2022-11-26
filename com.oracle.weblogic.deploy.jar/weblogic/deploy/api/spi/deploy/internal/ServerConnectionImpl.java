package weblogic.deploy.api.spi.deploy.internal;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.deploy.shared.ActionType;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.shared.StateType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.OperationUnsupportedException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.DeployerHelperException;
import weblogic.deploy.api.internal.utils.InstallDir;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.shared.WebLogicTargetType;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.deploy.ServerConnection;
import weblogic.deploy.api.spi.deploy.TargetImpl;
import weblogic.deploy.api.spi.deploy.TargetModuleIDImpl;
import weblogic.deploy.api.spi.deploy.mbeans.ModuleCache;
import weblogic.deploy.api.spi.deploy.mbeans.TargetCache;
import weblogic.deploy.api.spi.exceptions.ServerConnectionException;
import weblogic.deploy.api.spi.status.ProgressObjectImpl;
import weblogic.deploy.utils.ApplicationUtils;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.jndi.Environment;
import weblogic.management.DeploymentNotification;
import weblogic.management.DomainDir;
import weblogic.management.RemoteNotificationListener;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.remote.common.WLSJMXConnector;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;

public class ServerConnectionImpl implements ServerConnection, Serializable {
   private static final long serialVersionUID = 1L;
   private static final boolean debug = Debug.isDebug("deploy");
   private static final boolean ddebug = Debug.isDebug("internal");
   private static final String IIOP = "iiop";
   private static final String POLLER_NAME = "J2EE-Deployment-task-poller";
   private static final int RUNTIME = 2;
   private static final int DOMAIN_RUNTIME = 3;
   private boolean isRemote = false;
   private transient String adminUrl;
   private transient URI adminURI = null;
   private transient String auth1 = null;
   private transient String auth2 = null;
   private transient MBeanServerConnection mbs = null;
   private transient MBeanServerConnection runtimeMBS = null;
   private transient JMXDeployerHelper helper = null;
   private transient Map listeners = Collections.synchronizedMap(new HashMap());
   private transient Thread poller = null;
   private boolean forceStop = false;
   private transient String domain = null;
   private transient WebLogicDeploymentManager dm;
   private transient DeployerRuntimeMBean deployer;
   private transient TargetCache targetCache;
   private transient ModuleCache moduleCache;
   private Context ctx;
   private transient JMXConnector jmx = null;
   private transient JMXConnector runtimeJmx = null;
   private transient JMXConnector editJmx = null;
   private File delApp = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ConfigurationManagerMBean configMgr;
   private transient DomainManager domainManager;
   private boolean closed = false;
   private static final Logger REMOTE_MISC_LOGGER = Logger.getLogger("javax.management.remote.misc");
   private static final Logger REMOTE_RMI_LOGGER = Logger.getLogger("javax.management.remote.misc");

   protected void finalize() throws Throwable {
      if (!this.closed) {
         this.close(true);
      }

   }

   public void init(URI uri, String user, String pword, WebLogicDeploymentManager dm) throws ServerConnectionException {
      this.dm = dm;
      this.adminURI = uri;
      this.auth1 = user;
      this.auth2 = pword;

      try {
         this.ctx = this.getEnvironment(uri, user, pword);
      } catch (NamingException var7) {
         throw new ServerConnectionException(var7.getMessage(), var7);
      } catch (URISyntaxException var8) {
         throw new ServerConnectionException(var8.getMessage(), var8);
      }

      this.mbs = this.getMBeanServerForType(3);
      this.runtimeMBS = this.getMBeanServerForType(2);

      try {
         this.helper = new JMXDeployerHelper(this.jmx);
         this.helper.setConfigMgr(this.configMgr);
      } catch (DeployerHelperException var6) {
         throw new ServerConnectionException(var6.getMessage(), var6);
      }

      this.initialize();
      if (!dm.isLocal()) {
         this.setRemote();
      }

      if (!debug && !dm.isAuthenticated() && this.adminURI != null) {
         REMOTE_MISC_LOGGER.setLevel(Level.OFF);
         REMOTE_RMI_LOGGER.setLevel(Level.OFF);
      }

      if (debug) {
         Debug.say("Initializing ServerConnection : " + this);
      }

   }

   private void initialize() throws ServerConnectionException {
      this.domainManager = new DomainManager(this);
      DomainMBean currDomain = this.domainManager.getDomain();
      this.domain = currDomain.getName();
      this.dm.setDomain(this.domain);
      if (debug) {
         Debug.say("Connected to WLS domain: " + this.domain);
      }

      try {
         this.deployer = this.helper.getDeployer();
      } catch (Throwable var3) {
         throw new ServerConnectionException(SPIDeployerLogger.connectionError(), var3);
      }

      if (this.deployer == null) {
         throw new ServerConnectionException(SPIDeployerLogger.connectionError());
      }
   }

   private void initCaches(DomainMBean domain) {
      if (this.targetCache != null) {
         this.targetCache.reset();
      }

      this.targetCache = new TargetCache(domain, this.dm);
      if (this.moduleCache != null) {
         this.moduleCache.reset();
      }

      this.moduleCache = new ModuleCache(domain, this.dm);
   }

   private MBeanServerConnection getMBeanServerForType(int type) throws ServerConnectionException {
      if (this.adminURI == null) {
         throw new ServerConnectionException("Admin URI cannot be null");
      } else {
         return this.getMBeanServer(this.adminURI, this.auth1, this.auth2, type);
      }
   }

   private MBeanServerConnection getMBeanServer(URI uri, String uid, String pwd, int type) throws ServerConnectionException {
      String jndiName = "";
      if (type == 3) {
         jndiName = "weblogic.management.mbeanservers.domainruntime";
      } else {
         jndiName = "weblogic.management.mbeanservers.runtime";
      }

      String host = "localhost";
      int portVal = 7001;
      String protocol = "t3";
      String path = "";

      try {
         Hashtable h = new Hashtable();
         if (uid != null) {
            h.put("java.naming.security.principal", uid);
            h.put("java.naming.security.credentials", pwd);
         }

         if (uri == null || uid == null) {
            RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
            if (rt != null && rt.isAdminServer()) {
               return null;
            }

            String urls = ManagementService.getPropertyService(kernelId).getAdminBinaryURL();
            this.adminUrl = urls;
            if (urls != null) {
               uri = new URI(urls);
            }
         }

         if (uri != null) {
            this.adminUrl = uri.toString();
            host = uri.getHost();
            portVal = uri.getPort();
            protocol = this.extractProtocol(uri.getScheme());
            if (uri.getPath() != null) {
               path = uri.getPath();
            }

            String query = uri.getQuery();
            if (query != null && query.trim().startsWith("partitionName=")) {
               path = path + "?" + query;
            }
         }

         h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         JMXServiceURL jmxs = new JMXServiceURL(protocol, host, portVal, path + "/jndi/" + jndiName);
         if (debug) {
            Debug.say("Connecting to MBeanServer at " + jmxs.toString());
         }

         JMXConnector theJmx = JMXConnectorFactory.connect(jmxs, h);
         if (type == 3) {
            this.jmx = theJmx;
            this.getConfigurationManager(protocol, host, portVal, path, h);
         } else {
            this.runtimeJmx = theJmx;
         }

         return theJmx.getMBeanServerConnection();
      } catch (Exception var13) {
         if (debug) {
            var13.printStackTrace();
         }

         throw new ServerConnectionException(SPIDeployerLogger.failedMBeanConnection(protocol + "://" + host + ":" + portVal, uid, var13.getMessage()), var13);
      }
   }

   private void getConfigurationManager(String protocol, String host, int portVal, String path, Hashtable h) throws IOException, MalformedObjectNameException {
      if (this.configMgr == null) {
         if (this.editJmx == null) {
            JMXServiceURL jmxs = new JMXServiceURL(protocol, host, portVal, path + "/jndi/weblogic.management.mbeanservers.edit");
            this.editJmx = JMXConnectorFactory.connect(jmxs, h);
         }

         EditServiceMBean editServiceMBean = (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.editJmx, new ObjectName(EditServiceMBean.OBJECT_NAME));
         this.configMgr = editServiceMBean.getConfigurationManager();
      }
   }

   private String extractProtocol(String scheme) {
      return scheme == null ? "t3" : scheme;
   }

   private Context getEnvironment(URI uri, String uid, String pwd) throws NamingException, URISyntaxException {
      if (this.dm.isAuthenticated()) {
         return null;
      } else {
         if (debug) {
            Debug.say("setting environment");
         }

         if (uid != null && uri != null) {
            String uRLString = this.getUriAsString(uri);
            if (debug) {
               Debug.say("getting context using " + uRLString);
            }

            Context ctx;
            if (uRLString.startsWith("iiop")) {
               if (System.getProperty("weblogic.system.iiop.enableClient") == null) {
                  System.setProperty("weblogic.system.iiop.enableClient", "false");
               }

               ctx = this.getIIOPContext(uRLString, uid, pwd);
            } else {
               ctx = this.getContext(uRLString, uid, pwd);
            }

            return ctx;
         } else {
            return null;
         }
      }
   }

   private String getUriAsString(URI uri) throws URISyntaxException {
      String s = this.extractProtocol(uri.getScheme());
      String h = uri.getHost();
      int p = uri.getPort();
      String path = uri.getPath();
      String query = uri.getQuery();
      return query != null && query.trim().startsWith("partitionName=") ? (new URI(s, (String)null, h, p, path, query, (String)null)).toString() : (new URI(s, (String)null, h, p, path, (String)null, (String)null)).toString();
   }

   private Context getIIOPContext(String url, String username, String password) throws NamingException {
      Hashtable h = new Hashtable();
      h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      h.put("java.naming.provider.url", url);
      h.put("java.naming.security.principal", username);
      h.put("java.naming.security.credentials", password);
      return new InitialContext(h);
   }

   private Context getContext(String url, String username, String password) throws NamingException {
      Environment env = new Environment();
      env.setProviderUrl(url);
      env.setSecurityPrincipal(username);
      env.setSecurityCredentials(password);
      return env.getInitialContext();
   }

   public synchronized void close(boolean force) {
      if (debug) {
         Debug.say("Closing DM connection");
      }

      synchronized(this.listeners) {
         Iterator var3 = this.listeners.keySet().iterator();

         while(true) {
            if (!var3.hasNext()) {
               this.listeners.clear();
               this.forceStop = true;
               if (this.poller != null) {
                  this.poller.interrupt();
               }
               break;
            }

            ProgressObjectImpl po = (ProgressObjectImpl)var3.next();
            if (force) {
               try {
                  po.cancel();
                  po.setAction(ActionType.CANCEL);
               } catch (OperationUnsupportedException var8) {
               }
            }

            po.setState(StateType.RELEASED);
            po.reportEvent();
         }
      }

      if (debug) {
         Debug.say("Unregistered all listeners");
      }

      this.waitForPollerToStop();
      if (this.moduleCache != null) {
         this.moduleCache.close();
      }

      if (this.targetCache != null) {
         this.targetCache.close();
      }

      if (this.domainManager != null) {
         this.domainManager.close();
      }

      this.closeJMX();

      try {
         if (this.ctx != null) {
            this.ctx.close();
         }
      } catch (NamingException var7) {
      }

      this.mbs = null;
      this.closed = true;
   }

   private void closeJMX() {
      try {
         if (this.jmx != null) {
            this.jmx.close();
            if (debug) {
               Debug.say("Closed JMX connection");
            }
         }
      } catch (IOException var4) {
         if (debug) {
            Debug.say("Failed to close JMX connection");
            var4.printStackTrace();
         }
      }

      try {
         if (this.runtimeJmx != null) {
            this.runtimeJmx.close();
            if (debug) {
               Debug.say("Closed Runtime JMX connection");
            }
         }
      } catch (IOException var3) {
         if (debug) {
            Debug.say("Failed to close Runtime JMX connection");
            var3.printStackTrace();
         }
      }

      try {
         if (this.editJmx != null) {
            this.editJmx.close();
            if (debug) {
               Debug.say("Closed Edit JMX connection");
            }
         }
      } catch (IOException var2) {
         if (debug) {
            Debug.say("Failed to close Edit JMX connection");
            var2.printStackTrace();
         }
      }

      this.jmx = null;
      this.runtimeJmx = null;
      this.editJmx = null;
   }

   private void waitForPollerToStop() {
      while(this.poller != null) {
         try {
            Thread.sleep(10L);
         } catch (InterruptedException var2) {
         }
      }

   }

   public void registerListener(ProgressObjectImpl po) throws ServerConnectionException {
      try {
         String t = po.getTask();
         if (ddebug) {
            Debug.say("Register listener for task " + t);
         }

         if (t == null) {
            po.setMessage(SPIDeployerLogger.lostTask());
            po.reportEvent();
         } else {
            DeploymentTaskRuntimeMBean task = this.helper.getTaskMBean(t);
            if (task != null) {
               if (ddebug) {
                  Debug.say("Adding app listener for task " + t);
               }

               AppListener appListener = new AppListener(po, task);
               synchronized(this.listeners) {
                  this.listeners.put(po, appListener);
               }
            }

            if (ddebug) {
               Debug.say("Starting poller as nec for  task " + t);
            }

            synchronized(this.listeners) {
               if (this.poller == null) {
                  this.poller = new TaskPoller("J2EE-Deployment-task-poller");
                  this.poller.start();
               }

            }
         }
      } catch (ServerConnectionException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new ServerConnectionException(SPIDeployerLogger.noSuchApp(po.getTask()), var11);
      }
   }

   public void deregisterListener(ProgressObjectImpl po) {
      synchronized(this.listeners) {
         AppListener appListener = (AppListener)this.listeners.get(po);
         if (appListener != null) {
            if (ddebug) {
               Debug.say("removing listener: " + appListener);
            }

            this.listeners.remove(po);
         }

      }
   }

   public JMXDeployerHelper getHelper() {
      return this.helper;
   }

   public MBeanServerConnection getMBeanServerConnection() {
      return this.mbs;
   }

   public MBeanServerConnection getRuntimeServerConnection() {
      return this.runtimeMBS;
   }

   public void setRemote() {
      if (debug) {
         Debug.say("Running in remote mode");
      }

      this.isRemote = true;
   }

   public boolean isUploadEnabled() {
      return this.isRemote;
   }

   public List getTargets() throws ServerConnectionException {
      return this.getTargets((DeploymentOptions)null);
   }

   private List getBaseTargets(DeploymentOptions options) throws ServerConnectionException {
      return this.getBaseTargets(options, false);
   }

   private List getBaseTargets(DeploymentOptions options, boolean internalUse) throws ServerConnectionException {
      List t = this.getServers(options);
      t.addAll(this.getClusters(options));
      t.addAll(this.getHosts(options));
      t.addAll(this.getVirtualTargets(options));
      t.addAll(this.getJmsServers(options));
      t.addAll(this.getSafAgents(options));
      return internalUse ? t : this.excludeDynamicClusterTargets(t, options);
   }

   private List excludeDynamicClusterTargets(List origTargets, DeploymentOptions options) {
      List targetsToAdd = new ArrayList();
      DomainMBean runtimeDomain = null;
      if (options == null || !options.isRGOrRGTOperation()) {
         runtimeDomain = this.dm.getHelper().getRuntimeDomain();
      }

      if (runtimeDomain == null) {
         RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
         if (rt != null) {
            runtimeDomain = rt.getDomain();
         }
      }

      Iterator var7 = origTargets.iterator();

      while(var7.hasNext()) {
         Target target = (Target)var7.next();
         if (!ApplicationUtils.isDynamicClusterServer(target.getName(), runtimeDomain)) {
            targetsToAdd.add(target);
         }
      }

      return targetsToAdd;
   }

   public List getTargets(DeploymentOptions options) throws ServerConnectionException {
      return this.getTargets(options, false);
   }

   public List getTargets(DeploymentOptions options, boolean internalUse) throws ServerConnectionException {
      if (options == null) {
         return this.getBaseTargets(options, internalUse);
      } else {
         List t = new ArrayList();
         t.addAll(this.getBaseTargets(options, internalUse));
         if (options.getPartition() == null) {
            if (options.getResourceGroupTemplate() != null) {
               t.addAll(this.getResourceGroupTemplateTargets(options.getResourceGroupTemplate()));
            } else if (options.getResourceGroup() != null) {
               t.addAll(this.getResourceGroupTargets(options.getResourceGroup()));
            }
         } else if (options.getResourceGroup() != null) {
            t.addAll(this.getResourceGroupTargets(options.getPartition(), options.getResourceGroup()));
         }

         return t;
      }
   }

   private synchronized List getResourceGroupTemplateTargets(String templateName) {
      List targets = new ArrayList();
      DomainMBean currDomain = this.domainManager.getDomain();
      ResourceGroupTemplateMBean template = currDomain.lookupResourceGroupTemplate(templateName);
      if (template != null) {
         JMSServerMBean[] jmsServers = template.getJMSServers();
         if (jmsServers != null) {
            for(int i = 0; i < jmsServers.length; ++i) {
               targets.add(new TargetImpl(jmsServers[i].getObjectName().getName(), WebLogicTargetType.JMSSERVER, this.dm));
            }
         }

         SAFAgentMBean[] safAgents = template.getSAFAgents();
         if (safAgents != null) {
            for(int i = 0; i < safAgents.length; ++i) {
               targets.add(new TargetImpl(safAgents[i].getObjectName().getName(), WebLogicTargetType.SAFAGENT, this.dm));
            }
         }
      }

      return targets;
   }

   private synchronized List getResourceGroupTargets(String groupName) {
      List targets = new ArrayList();
      DomainMBean currDomain = this.domainManager.getDomain();
      ResourceGroupMBean group = currDomain.lookupResourceGroup(groupName);
      if (group != null) {
         JMSServerMBean[] jmsServers = group.getJMSServers();
         if (jmsServers != null) {
            for(int i = 0; i < jmsServers.length; ++i) {
               targets.add(new TargetImpl(jmsServers[i].getObjectName().getName(), WebLogicTargetType.JMSSERVER, this.dm));
            }
         }

         SAFAgentMBean[] safAgents = group.getSAFAgents();
         if (safAgents != null) {
            for(int i = 0; i < safAgents.length; ++i) {
               targets.add(new TargetImpl(safAgents[i].getObjectName().getName(), WebLogicTargetType.SAFAGENT, this.dm));
            }
         }
      }

      return targets;
   }

   private synchronized List getResourceGroupTargets(String partitionName, String groupName) {
      List targets = new ArrayList();
      DomainMBean currDomain = this.domainManager.getDomain();
      PartitionMBean partition = currDomain.lookupPartition(partitionName);
      if (partition != null) {
         ResourceGroupMBean group = partition.lookupResourceGroup(groupName);
         if (group != null) {
            JMSServerMBean[] jmsServers = group.getJMSServers();
            if (jmsServers != null) {
               for(int i = 0; i < jmsServers.length; ++i) {
                  targets.add(new TargetImpl(jmsServers[i].getObjectName().getName(), WebLogicTargetType.JMSSERVER, this.dm));
               }
            }

            SAFAgentMBean[] safAgents = group.getSAFAgents();
            if (safAgents != null) {
               for(int i = 0; i < safAgents.length; ++i) {
                  targets.add(new TargetImpl(safAgents[i].getObjectName().getName(), WebLogicTargetType.SAFAGENT, this.dm));
               }
            }
         }
      }

      return targets;
   }

   public List getServers() throws ServerConnectionException {
      return this.getServers((DeploymentOptions)null);
   }

   public List getServers(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.SERVER);
   }

   public List getClusters() throws ServerConnectionException {
      return this.getClusters((DeploymentOptions)null);
   }

   public List getClusters(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.CLUSTER);
   }

   public List getHosts() throws ServerConnectionException {
      return this.getHosts((DeploymentOptions)null);
   }

   public List getHosts(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.VIRTUALHOST);
   }

   public List getVirtualTargets() throws ServerConnectionException {
      return this.getVirtualTargets((DeploymentOptions)null);
   }

   public List getVirtualTargets(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.VIRTUALTARGET);
   }

   public List getJmsServers() throws ServerConnectionException {
      return this.getJmsServers((DeploymentOptions)null);
   }

   public List getJmsServers(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.JMSSERVER);
   }

   public List getSafAgents() throws ServerConnectionException {
      return this.getSafAgents((DeploymentOptions)null);
   }

   public List getSafAgents(DeploymentOptions options) throws ServerConnectionException {
      return this.targetCache.getTargets(options, WebLogicTargetType.SAFAGENT);
   }

   public boolean isRunning(TargetModuleID tmid) throws ServerConnectionException {
      return this.isRunning((DeploymentOptions)null, tmid);
   }

   public boolean isRunning(DeploymentOptions options, TargetModuleID tmid) throws ServerConnectionException {
      try {
         if (!this.isTargetAlive(options, tmid.getTarget())) {
            return false;
         } else if (((TargetModuleIDImpl)tmid).getValue() == WebLogicModuleType.SUBMODULE.getValue()) {
            return true;
         } else {
            AppRuntimeStateRuntimeMBean rt = this.helper.getAppRuntimeStateMBean();
            if (rt == null) {
               return false;
            } else {
               String state;
               if (tmid.getParentTargetModuleID() == null) {
                  state = rt.getCurrentState(tmid.getModuleID(), tmid.getTarget().getName());
               } else {
                  state = rt.getCurrentState(tmid.getParentTargetModuleID().getModuleID(), tmid.getModuleID(), tmid.getTarget().getName());
               }

               return "STATE_ACTIVE".equals(state);
            }
         }
      } catch (Exception var5) {
         throw new ServerConnectionException(var5.getMessage(), var5);
      }
   }

   private boolean isTargetAlive(DeploymentOptions options, Target t) {
      List s = this.getServersForTarget(options, (TargetImpl)t);

      for(int i = 0; i < s.size(); ++i) {
         Target srvr = (Target)s.get(i);
         if (this.helper.isServerAlive(srvr.getName())) {
            return true;
         }
      }

      return false;
   }

   public void validateTargets(Target[] targets) throws TargetException, ServerConnectionException {
      this.validateTargets(targets, (DeploymentOptions)null);
   }

   public void validateTargets(Target[] targets, DeploymentOptions options) throws TargetException, ServerConnectionException {
      if (debug) {
         Debug.say("Validating targets");
      }

      if (targets != null && targets.length != 0) {
         List configuredTargets = this.getTargets(options);

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] == null) {
               throw new TargetException(SPIDeployerLogger.nullTarget());
            }

            if (!configuredTargets.contains(targets[i])) {
               throw new TargetException(SPIDeployerLogger.noSuchTarget(targets[i].getName(), targets[i].getDescription()));
            }
         }

      } else {
         throw new TargetException(SPIDeployerLogger.nullTargetArray());
      }
   }

   public List getModules(ConfigurationMBean mbean) throws ServerConnectionException {
      return this.getModules(mbean, createDeploymentOptions(mbean));
   }

   public List getModules(ConfigurationMBean mbean, DeploymentOptions options) throws ServerConnectionException {
      boolean isRoot = mbean instanceof AppDeploymentMBean;
      if (!(mbean instanceof BasicDeploymentMBean) && !(mbean instanceof SubDeploymentMBean)) {
         throw new IllegalArgumentException(SPIDeployerLogger.invalidMBean(mbean.getObjectName().toString()));
      } else {
         List tmidSet = this.getModules(mbean.getName(), options);
         Iterator mods = tmidSet.iterator();

         while(true) {
            while(mods.hasNext()) {
               TargetModuleIDImpl module = (TargetModuleIDImpl)mods.next();
               if (!module.getModuleID().equals(mbean.getName())) {
                  mods.remove();
               } else if (isRoot && module.getParentTargetModuleID() != null) {
                  mods.remove();
               } else if (!isRoot && !mbean.getParent().getName().equals(module.getParentTargetModuleID().getModuleID())) {
                  mods.remove();
               }
            }

            if (tmidSet.isEmpty()) {
               TargetModuleID tmid = this.createTMIDsForRGOrRGTMBeanIfApplicable(mbean);
               if (tmid != null) {
                  tmidSet.add(tmid);
               }
            }

            return tmidSet;
         }
      }
   }

   public List getModulesForTarget(ModuleType type, Target target) throws TargetException, ServerConnectionException {
      return this.getModulesForTarget(type, target, (DeploymentOptions)null);
   }

   public List getModulesForTarget(ModuleType type, Target target, DeploymentOptions options) throws TargetException, ServerConnectionException {
      Target[] targets = new Target[]{target};
      this.validateTargets(targets);
      List modSet = this.getModules(Arrays.asList(targets).iterator(), options);
      Iterator mods = modSet.iterator();

      while(mods.hasNext()) {
         TargetModuleIDImpl module = (TargetModuleIDImpl)mods.next();
         if (debug) {
            Debug.say("checking tmid, " + module.getModuleID() + ", " + Integer.toString(type.getValue()));
         }

         if (module.getValue() != type.getValue()) {
            mods.remove();
         }

         if (!module.getTarget().getName().equals(target.getName())) {
            mods.remove();
         }
      }

      return modSet;
   }

   public List getModulesForTargets(ModuleType type, Target[] targets) throws TargetException, ServerConnectionException {
      return this.getModulesForTargets(type, targets, (DeploymentOptions)null);
   }

   public List getModulesForTargets(ModuleType type, Target[] targets, DeploymentOptions options) throws TargetException, ServerConnectionException {
      this.validateTargets(targets, options);
      List modSet = this.getModules(Arrays.asList(targets).iterator(), options);
      Iterator mods = modSet.iterator();

      while(true) {
         while(mods.hasNext()) {
            TargetModuleIDImpl module = (TargetModuleIDImpl)mods.next();
            if (type.getValue() == ModuleType.WAR.getValue() && module.getValue() == ModuleType.WAR.getValue()) {
               this.populateWarUrlIfNecessary(module);
            }

            if (debug) {
               Debug.say("checking tmid, " + module.getModuleID() + ", " + Integer.toString(type.getValue()));
            }

            if (module.getValue() != type.getValue()) {
               mods.remove();
            } else {
               boolean fnd = false;

               for(int i = 0; i < targets.length; ++i) {
                  if (module.getTarget().getName().equals(targets[i].getName())) {
                     fnd = true;
                     break;
                  }
               }

               if (!fnd) {
                  mods.remove();
               }
            }
         }

         return modSet;
      }
   }

   private void populateWarUrlIfNecessary(TargetModuleIDImpl targetModuleIDInterface) {
      if (targetModuleIDInterface != null) {
         TargetModuleIDImpl targetModuleID = targetModuleIDInterface;
         String contextRoot = null;
         if (targetModuleIDInterface.getValue() == ModuleType.WAR.getValue()) {
            try {
               String serverName = this.getServerName(targetModuleID);
               DomainRuntimeServiceMBean domainRuntimeServiceMBean = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(this.getMBeanServerConnection(), new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME));
               if (domainRuntimeServiceMBean != null) {
                  ServerRuntimeMBean serverRuntimeMBean = domainRuntimeServiceMBean.lookupServerRuntime(serverName);
                  if (serverRuntimeMBean != null) {
                     String serverUrl = serverRuntimeMBean.getURL("http");
                     ApplicationRuntimeMBean applicationRuntimeMBean = serverRuntimeMBean.lookupApplicationRuntime(this.getApplicationRuntimeName(targetModuleID));
                     if (applicationRuntimeMBean != null) {
                        ComponentRuntimeMBean[] componentRuntimeMBeans = applicationRuntimeMBean.getComponentRuntimes();
                        if (componentRuntimeMBeans != null) {
                           ComponentRuntimeMBean[] var10 = componentRuntimeMBeans;
                           int var11 = componentRuntimeMBeans.length;

                           for(int var12 = 0; var12 < var11; ++var12) {
                              ComponentRuntimeMBean componentRuntimeMBean = var10[var12];
                              if (componentRuntimeMBean instanceof WebAppComponentRuntimeMBean) {
                                 WebAppComponentRuntimeMBean webAppComponentMBean = (WebAppComponentRuntimeMBean)componentRuntimeMBean;
                                 if (this.isMatchingTmidAndComponentMBean(targetModuleID, webAppComponentMBean)) {
                                    contextRoot = webAppComponentMBean.getContextRoot();
                                    break;
                                 }
                              }
                           }
                        }
                     }

                     if (serverUrl != null && contextRoot != null) {
                        targetModuleID.setWebURL(serverUrl + contextRoot);
                     }
                  }
               }
            } catch (Exception var15) {
               if (debug) {
                  Debug.say("Unable to lookup the WAR's URL: " + var15.getMessage());
               }
            }
         }

      }
   }

   private String getApplicationRuntimeName(TargetModuleIDImpl targetModuleID) {
      String appName = targetModuleID.getApplicationName();
      String versionId = targetModuleID.getVersion();
      if (appName != null && versionId != null) {
         return appName + "_" + versionId;
      } else {
         return appName != null ? appName : null;
      }
   }

   private boolean isMatchingTmidAndComponentMBean(TargetModuleIDImpl targetModuleID, WebAppComponentRuntimeMBean webAppComponentMBean) {
      String tmidApplicationName;
      String webAppComponentApplicationName;
      if (targetModuleID.getParentTargetModuleID() != null) {
         tmidApplicationName = targetModuleID.getModuleID();
         webAppComponentApplicationName = webAppComponentMBean.getModuleId();
         if (webAppComponentApplicationName.charAt(0) == '/') {
            webAppComponentApplicationName = webAppComponentApplicationName.substring(1);
         }

         return tmidApplicationName.equals(webAppComponentApplicationName);
      } else {
         tmidApplicationName = targetModuleID.getApplicationName();
         webAppComponentApplicationName = webAppComponentMBean.getApplicationIdentifier();
         int versionIndex = webAppComponentApplicationName.indexOf(35);
         if (versionIndex != -1) {
            webAppComponentApplicationName = webAppComponentApplicationName.substring(0, versionIndex);
         }

         return tmidApplicationName.equals(webAppComponentApplicationName);
      }
   }

   public void populateWarUrlInChildren(TargetModuleID targetModuleID) {
      if (targetModuleID instanceof TargetModuleIDImpl) {
         TargetModuleIDImpl targetModuleIDImpl = (TargetModuleIDImpl)targetModuleID;
         if (targetModuleIDImpl.getValue() == ModuleType.WAR.getValue()) {
            this.populateWarUrlIfNecessary(targetModuleIDImpl);
         } else {
            TargetModuleID[] childModules = targetModuleID.getChildTargetModuleID();
            if (childModules != null) {
               TargetModuleID[] var4 = childModules;
               int var5 = childModules.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  TargetModuleID childModule = var4[var6];
                  this.populateWarUrlInChildren(childModule);
               }
            }
         }

      }
   }

   private String getServerName(TargetModuleIDImpl targetModuleID) {
      String serverName = null;
      TargetImpl target = (TargetImpl)targetModuleID.getTarget();
      if (target.isCluster()) {
         DomainMBean domainMBean = this.domainManager.getDomain();
         ClusterMBean[] clusters = domainMBean.getClusters();
         ClusterMBean[] var6 = clusters;
         int var7 = clusters.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ClusterMBean cluster = var6[var8];
            if (cluster.getName().equals(target.getName())) {
               ServerMBean[] serverMBeans = cluster.getServers();
               if (serverMBeans != null && serverMBeans.length > 0) {
                  serverName = serverMBeans[0].getName();
                  break;
               }
            }
         }
      } else {
         serverName = target.getName();
      }

      return serverName;
   }

   public List getModules(Iterator targets) throws ServerConnectionException {
      return this.getModules(targets, (DeploymentOptions)null);
   }

   public List getModules(Iterator targets, DeploymentOptions options) throws ServerConnectionException {
      return this.getModules(targets, (String)null, options);
   }

   public List getModules(Iterator targets, String appName, DeploymentOptions options) throws ServerConnectionException {
      List tmidSet = new ArrayList();

      while(targets.hasNext()) {
         Target target = (Target)targets.next();
         tmidSet.addAll(this.moduleCache.getModules(target, appName, options));
      }

      return tmidSet;
   }

   public List getModules() throws ServerConnectionException {
      return this.getModules((DeploymentOptions)null);
   }

   public List getModules(DeploymentOptions options) throws ServerConnectionException {
      return this.getModules((String)null, options);
   }

   public List getModules(String appName, DeploymentOptions options) throws ServerConnectionException {
      Iterator targets = this.getTargets(options).iterator();
      return this.getModules(targets, appName, options);
   }

   public InstallDir upload(InstallDir paths, String appId, String[] delta) throws ServerConnectionException, IOException {
      return this.upload(paths, appId, delta, new DeploymentOptions());
   }

   public InstallDir upload(InstallDir paths, String appId, String[] delta, DeploymentOptions options) throws ServerConnectionException, IOException {
      InstallDir newPaths = paths;
      boolean cleanup = false;

      try {
         if (this.isRemote) {
            if (!paths.isProper()) {
               cleanup = true;
               File inst = new File(DomainDir.getTempDirForServer(this.helper.getAdminServerName()), paths.getArchive().getName());
               if (!inst.exists()) {
                  String tmp = System.getProperty("java.io.tmpdir");
                  if (tmp == null) {
                     tmp = "/tmp";
                  }

                  inst = new File(tmp, paths.getArchive().getName() + Long.toString(System.nanoTime()));
                  if (inst.exists()) {
                     inst = new File(tmp, paths.getArchive().getName() + Long.toString(System.nanoTime()));
                  }
               }

               newPaths = new InstallDir(paths.getArchive().getName(), inst);
               newPaths.getAppDir().mkdir();
               FileUtils.copyPreservePermissions(paths.getArchive().getCanonicalFile(), newPaths.getArchive());
               newPaths.getConfigDir().mkdir();
               if (paths.getConfigDir().exists()) {
                  FileUtils.copyPreservePermissions(paths.getConfigDir(), newPaths.getConfigDir());
               }

               if (paths.getPlan() != null) {
                  newPaths.setPlan(new File(newPaths.getConfigDir(), paths.getPlan().getName()));
                  FileUtils.copyPreservePermissions(paths.getPlan().getCanonicalFile(), newPaths.getPlan());
               }

               if (paths.getAltAppDD() != null) {
                  newPaths.setAltAppDD(new File(newPaths.getAltDDDir(), paths.getAltAppDD().getName()));
                  FileUtils.copyPreservePermissions(paths.getAltAppDD().getCanonicalFile(), newPaths.getAltAppDD());
               }
            }

            String p = this.helper.uploadSource(this.adminUrl, this.auth1, this.auth2, newPaths.getInstallDir().getPath(), delta, appId, options);
            File tmp = new File(p);
            paths.resetInstallDir(tmp);
            paths.setArchive(new File(paths.getAppDir(), newPaths.getArchive().getName()));
            if (newPaths.getPlan() != null) {
               paths.setPlan(new File(paths.getConfigDir(), newPaths.getPlan().getName()));
            }

            if (newPaths.getAltAppDD() != null) {
               paths.setAltAppDD(new File(paths.getAltDDDir(), newPaths.getAltAppDD().getName()));
            }

            if (debug) {
               Debug.say("Uploaded app to " + p);
            }
         }
      } catch (DeployerHelperException var12) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, paths.getArchive().getPath()), var12);
      } finally {
         if (cleanup) {
            FileUtils.remove(newPaths.getInstallDir());
         }

      }

      return paths;
   }

   public InstallDir upload2(InstallDir paths, String appId, String[] delta, DeploymentOptions options) throws ServerConnectionException, IOException {
      String appPath = paths.getArchive() == null ? null : paths.getArchive().getCanonicalPath();
      String planPath = null;
      String altAppDDPath = null;
      if (paths.getPlan() != null) {
         planPath = paths.getPlan().getPath();
      }

      if (paths.getAltAppDD() != null) {
         altAppDDPath = paths.getAltAppDD().getPath();
      }

      try {
         if (this.isRemote) {
            File planf = null;
            File appf = null;
            File altAppDDf = null;
            File appdir;
            File newAltAppDD;
            if (appPath != null) {
               appf = new File(appPath);
               if (planPath != null) {
                  planf = new File(planPath);
               }

               if (altAppDDPath != null) {
                  altAppDDf = new File(altAppDDPath);
               }

               if (!paths.isInAppDir(appf)) {
                  appdir = paths.getAppDir();
                  if (appdir.exists()) {
                     FileUtils.remove(appdir);
                  }

                  appdir.mkdirs();
                  newAltAppDD = new File(appdir, appf.getName());
                  if (debug) {
                     Debug.say("Copying " + appf.getPath() + " to app area, " + newAltAppDD.getPath());
                  }

                  HashSet exclude = new HashSet();
                  exclude.add(appdir);
                  FileUtils.copyPreservePermissions(appf, newAltAppDD, exclude);
                  this.delApp = newAltAppDD;
                  paths.setArchive(newAltAppDD.getCanonicalFile());
               } else {
                  paths.setArchive(appf.getCanonicalFile());
               }
            }

            appdir = paths.getConfigDir().getCanonicalFile().getParentFile();
            if (!paths.getInstallDir().equals(appdir)) {
               appdir = new File(paths.getInstallDir(), "plan");
               appdir.mkdirs();
               if (debug) {
                  Debug.say("Copying plan dir at " + paths.getConfigDir() + " to " + appdir);
               }

               FileUtils.copyPreservePermissions(paths.getConfigDir(), appdir);
               paths.setConfigDir(appdir);
            }

            if (planf != null) {
               if (!paths.isInConfigDir(planf)) {
                  newAltAppDD = new File(paths.getConfigDir(), planf.getName());
                  if (debug) {
                     Debug.say("Copying plan at " + planf.getPath() + " to config area, " + newAltAppDD.getPath());
                  }

                  FileUtils.copyPreservePermissions(planf, newAltAppDD);
                  paths.setPlan(newAltAppDD.getCanonicalFile());
               } else {
                  paths.setPlan(planf.getCanonicalFile());
               }
            }

            if (altAppDDf != null) {
               if (!paths.isInConfigDir(altAppDDf)) {
                  newAltAppDD = new File(paths.getConfigDir(), altAppDDf.getName());
                  if (debug) {
                     Debug.say("Copying altAppDD at " + altAppDDf.getPath() + " to config area, " + newAltAppDD.getPath());
                  }

                  FileUtils.copyPreservePermissions(altAppDDf, newAltAppDD);
                  paths.setAltAppDD(newAltAppDD.getCanonicalFile());
               } else {
                  paths.setAltAppDD(altAppDDf.getCanonicalFile());
               }
            }

            String p = this.helper.uploadSource(this.adminUrl, this.auth1, this.auth2, paths.getInstallDir().getPath(), delta, appId, options);
            File tmp = new File(p);

            try {
               tmp = new File(new URI(p));
            } catch (Exception var15) {
               Debug.say("Caught: " + var15);
            }

            paths.resetInstallDir(tmp);
            if (appf != null) {
               paths.setArchive(new File(paths.getAppDir(), appf.getName()));
            }

            if (planf != null) {
               paths.setPlan(new File(paths.getConfigDir(), planf.getName()));
            }

            if (altAppDDf != null) {
               paths.setAltAppDD(new File(paths.getAltDDDir(), altAppDDf.getName()));
            }

            if (debug) {
               Debug.say("Uploaded app to " + p);
            }

            if (this.delApp != null) {
               FileUtils.remove(this.delApp);
               this.delApp = null;
            }
         }

         return paths;
      } catch (DeployerHelperException var16) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, appPath), var16);
      }
   }

   public String uploadApp(String inPath, String appId, String[] delta) throws ServerConnectionException {
      return this.uploadApp(inPath, appId, delta, new DeploymentOptions());
   }

   public String uploadApp(String inPath, String appId, String[] delta, DeploymentOptions options) throws ServerConnectionException {
      String outPath = inPath;

      try {
         if (this.isRemote) {
            outPath = this.helper.uploadSource(this.adminUrl, this.auth1, this.auth2, inPath, delta, appId, options);
            if (debug) {
               Debug.say("Uploaded file, " + inPath + " to " + this.adminUrl + ": " + outPath);
            }
         }

         return outPath;
      } catch (DeployerHelperException var7) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, inPath), var7);
      }
   }

   public String uploadConfig(String planPath, DeploymentPlanBean plan, String appId) throws ServerConnectionException {
      return this.uploadConfig(planPath, plan, appId, new DeploymentOptions());
   }

   public String uploadConfig(String planPath, DeploymentPlanBean plan, String appId, DeploymentOptions options) throws ServerConnectionException {
      String configPath = plan.getConfigRoot();
      String cfp = null;
      if (configPath != null) {
         cfp = (new File(configPath)).getParent();
      }

      try {
         InstallDir paths = new InstallDir(appId, cfp);
         String inPath = (new File(planPath)).getAbsoluteFile().getPath();
         String outPath = inPath;
         if (this.isRemote) {
            File planf = new File(inPath);
            if (!paths.isInConfigDir(planf)) {
               File newPlan = new File(paths.getConfigDir(), planf.getName());
               if (debug) {
                  Debug.say("Copying " + planf.getPath() + " to config area, " + newPlan.getPath());
               }

               FileUtils.copyPreservePermissions(planf, newPlan);
               paths.setPlan(newPlan);
               inPath = paths.getConfigDir().getPath();
            }

            outPath = this.helper.uploadPlan(this.adminUrl, this.auth1, this.auth2, inPath, appId, options);
            if (debug) {
               Debug.say("Uploaded file, " + inPath + " to " + this.adminUrl + ": " + outPath);
            }
         }

         return outPath;
      } catch (DeployerHelperException var12) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, configPath), var12);
      } catch (IOException var13) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, configPath), var13);
      }
   }

   public TargetModuleID[] getResultTmids(AppDeploymentMBean appMBean, Target[] targetList) throws TargetException, ServerConnectionException {
      if (appMBean == null) {
         return new TargetModuleID[0];
      } else {
         if (ddebug) {
            Debug.say("Getting tmids for app: " + appMBean.getName());
         }

         List distTmids = new ArrayList();

         for(int i = 0; i < targetList.length; ++i) {
            TargetModuleID tmid = this.moduleCache.getTMID(appMBean, targetList[i]);
            if (tmid != null) {
               distTmids.add(tmid);
            }
         }

         return (TargetModuleID[])distTmids.toArray(new TargetModuleID[distTmids.size()]);
      }
   }

   public ModuleCache getModuleCache() {
      return this.moduleCache;
   }

   public String uploadPlan(String inPath, String appId) throws ServerConnectionException {
      return this.uploadPlan(inPath, appId, new DeploymentOptions());
   }

   public String uploadPlan(String inPath, String appId, DeploymentOptions options) throws ServerConnectionException {
      try {
         String outPath = this.helper.uploadPlan(this.adminUrl, this.auth1, this.auth2, inPath, appId, options);
         if (debug) {
            Debug.say("Uploaded file, " + inPath + " to " + this.adminUrl + ": " + outPath);
         }

         return outPath;
      } catch (DeployerHelperException var5) {
         throw new ServerConnectionException(SPIDeployerLogger.uploadFailure(this.adminUrl, inPath), var5);
      }
   }

   public void test() throws Throwable {
      this.helper.getTaskByID("23");
   }

   public void resetDomain(DomainMBean domain) {
      this.initCaches(domain);
   }

   public List getServersForCluster(TargetImpl cluster) throws ServerConnectionException {
      return this.getServersForCluster((DeploymentOptions)null, cluster);
   }

   public List getServersForCluster(DeploymentOptions options, TargetImpl cluster) throws ServerConnectionException {
      List servers = new ArrayList();
      List mbeans = this.targetCache.getMBeans(options);
      Iterator var5 = mbeans.iterator();

      while(var5.hasNext()) {
         ConfigurationMBean cbean = (ConfigurationMBean)var5.next();
         if (cbean instanceof ServerMBean) {
            ClusterMBean cmb = ((ServerMBean)cbean).getCluster();
            if (cmb != null && cmb.getName().equals(cluster.getName())) {
               servers.add(this.targetCache.getTarget(options, ((ServerMBean)cbean).getName()));
            }
         }
      }

      return servers;
   }

   public List getServersForJmsServer(TargetImpl jms) throws ServerConnectionException {
      return this.getServersForJmsServer((DeploymentOptions)null, jms);
   }

   public List getServersForJmsServer(DeploymentOptions options, TargetImpl jms) throws ServerConnectionException {
      return this.getServersForDeployableTarget(options, jms);
   }

   public List getServersForSafAgent(TargetImpl saf) throws ServerConnectionException {
      return this.getServersForSafAgent((DeploymentOptions)null, saf);
   }

   public List getServersForSafAgent(DeploymentOptions options, TargetImpl saf) throws ServerConnectionException {
      return this.getServersForTarget(options, saf);
   }

   public List getServersForHost(TargetImpl host) throws ServerConnectionException {
      return this.getServersForHost((DeploymentOptions)null, host);
   }

   public List getServersForHost(DeploymentOptions options, TargetImpl host) throws ServerConnectionException {
      return this.getServersForDeployableTarget(options, host);
   }

   private List getServersForDeployableTarget(DeploymentOptions options, TargetImpl host) throws ServerConnectionException {
      Set servers = new HashSet();
      ConfigurationMBean o = this.targetCache.getMBean(options, host.getName());
      if (o instanceof DeploymentMBean) {
         DeploymentMBean mbean = (DeploymentMBean)o;
         TargetMBean[] targets = mbean.getTargets();

         for(int i = 0; i < targets.length; ++i) {
            TargetMBean target = targets[i];
            if (debug) {
               Debug.say("ServerConnectionImpl.getServersForDeployableTarget:  " + target);
            }

            if (target instanceof ServerMBean) {
               servers.add(this.targetCache.getTarget(options, target.getName()));
            } else if (target instanceof ClusterMBean) {
               servers.addAll(this.getServersForCluster(this.targetCache.getTarget(options, target.getName())));
            }
         }
      }

      return new ArrayList(servers);
   }

   private List getServersForTarget(DeploymentOptions options, TargetImpl target) throws ServerConnectionException {
      if (debug) {
         Debug.say("ServerConnectionImpl.getServersForTarget:  " + target);
      }

      List servers = new ArrayList();
      if (target.isServer()) {
         servers.add(target);
      } else if (target.isCluster()) {
         servers.addAll(this.getServersForCluster(options, target));
      } else if (target.isVirtualHost()) {
         servers.addAll(this.getServersForHost(options, target));
      } else if (target.isVirtualTarget()) {
         servers.addAll(this.getServersForHost(options, target));
      } else if (target.isJMSServer()) {
         servers.addAll(this.getServersForJmsServer(options, target));
      }

      return servers;
   }

   public TargetImpl getTarget(String name) throws ServerConnectionException {
      return this.targetCache.getTarget((DeploymentOptions)null, name);
   }

   public TargetImpl getTarget(DeploymentOptions options, String name) throws ServerConnectionException {
      return this.targetCache.getTarget(options, name);
   }

   public void setLocale(Locale locale) throws IOException {
      this.setLocale(locale, this.jmx);
      this.setLocale(locale, this.runtimeJmx);
      this.setLocale(locale, this.editJmx);
   }

   private void setLocale(Locale locale, JMXConnector connector) throws IOException {
      if (connector != null) {
         if (connector instanceof WLSJMXConnector) {
            ((WLSJMXConnector)connector).getMBeanServerConnection(locale);
         }

      }
   }

   public AppRuntimeStateRuntimeMBean getAppRuntimeStateRuntimeMBean() {
      try {
         return this.helper.getAppRuntimeStateMBean();
      } catch (Exception var2) {
         return null;
      }
   }

   public DomainMBean getDomainMBean() {
      return this.domainManager.getDomain();
   }

   private static DeploymentOptions createDeploymentOptions(ConfigurationMBean configMBean) {
      DeploymentOptions options = new DeploymentOptions();
      WebLogicMBean parent = configMBean.getParent();
      if (parent instanceof ResourceGroupMBean) {
         options.setResourceGroup(parent.getName());
         WebLogicMBean grandParent = parent.getParent();
         if (grandParent instanceof PartitionMBean) {
            options.setPartition(grandParent.getName());
         }
      } else if (parent instanceof ResourceGroupTemplateMBean) {
         options.setResourceGroupTemplate(parent.getName());
      }

      return options;
   }

   private TargetModuleID createTMIDsForRGOrRGTMBeanIfApplicable(ConfigurationMBean configMBean) {
      WebLogicMBean parent = configMBean.getParent();
      TargetModuleID tmid = null;
      if (parent instanceof ResourceGroupTemplateMBean) {
         TargetImpl rgTarget;
         if (parent instanceof ResourceGroupMBean) {
            rgTarget = this.targetCache.createAndCacheRGOrRGTTarget("resourceGroup");
            tmid = this.dm.createTargetModuleID((String)configMBean.getName(), (ModuleType)WebLogicModuleType.UNKNOWN, (Target)rgTarget);
         } else {
            rgTarget = this.targetCache.createAndCacheRGOrRGTTarget("resourceGroupTemplate");
            tmid = this.dm.createTargetModuleID((String)configMBean.getName(), (ModuleType)WebLogicModuleType.UNKNOWN, (Target)rgTarget);
         }
      }

      return tmid;
   }

   public class TaskPoller extends Thread {
      private Map msgMap = new HashMap();

      public TaskPoller(String name) {
         super(name);
      }

      public void run() {
         try {
            if (ServerConnectionImpl.ddebug) {
               Debug.say("Poller starting up");
            }

            boolean polling = true;

            while(polling && (!interrupted() || !ServerConnectionImpl.this.forceStop)) {
               polling = this.poll();

               try {
                  if (polling) {
                     Thread.sleep(100L);
                  }
               } catch (InterruptedException var5) {
                  if (ServerConnectionImpl.this.forceStop) {
                     break;
                  }
               }
            }

            synchronized(ServerConnectionImpl.this.listeners) {
               ServerConnectionImpl.this.poller = null;
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("Poller shut down");
               }
            }
         } catch (Throwable var6) {
            SPIDeployerLogger.logPollerError(var6);
            ServerConnectionImpl.this.dm.release();
         }

      }

      private boolean poll() {
         Set done = new HashSet();
         synchronized(ServerConnectionImpl.this.listeners) {
            Iterator var5 = ServerConnectionImpl.this.listeners.keySet().iterator();

            ProgressObjectImpl po;
            while(var5.hasNext()) {
               po = (ProgressObjectImpl)var5.next();
               int msgIndex = this.getMessageIndex(po);

               try {
                  DeploymentTaskRuntimeMBean task = po.getDtrm();
                  if (task != null) {
                     this.report(task, msgIndex, po);
                     int state = task.getState();
                     if (state != 1 && state != 0) {
                        this.completeTask(state, po, task);
                        po.reportEvent();
                        done.add(po);
                     }
                  } else {
                     if (po.getDeploymentStatus().getState() == StateType.RUNNING) {
                        po.setMessage(SPIDeployerLogger.lostTask());
                        po.setState(StateType.RELEASED);
                        po.reportEvent();
                     }

                     done.add(po);
                  }
               } catch (Throwable var10) {
                  SPIDeployerLogger.logConnectionError(var10.getMessage(), var10);
                  po.setState(StateType.RELEASED);
                  po.setError(var10);
                  po.reportEvent();
                  done.add(po);
               }
            }

            var5 = done.iterator();

            while(var5.hasNext()) {
               po = (ProgressObjectImpl)var5.next();
               ServerConnectionImpl.this.deregisterListener(po);
            }

            return ServerConnectionImpl.this.listeners.size() > 0;
         }
      }

      private void completeTask(int state, ProgressObjectImpl po, DeploymentTaskRuntimeMBean task) {
         switch (state) {
            case 2:
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("task state is complete");
               }

               po.setState(StateType.COMPLETED);
               break;
            case 3:
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("task state is failed");
               }

               Exception e = null;

               try {
                  e = task.getError();
               } catch (Throwable var10) {
                  try {
                     List ms = task.getTaskMessages();
                     if (ms != null) {
                        for(int i = 0; i < ms.size(); ++i) {
                           String o = (String)ms.get(i);
                           po.setMessage(o);
                        }
                     }
                  } catch (Throwable var9) {
                     po.setMessage(StackTraceUtils.throwable2StackTrace(var9));
                  }
               }

               if (e != null) {
                  po.setError(e);
               } else if (po.getDeploymentStatus().getMessage() == null) {
                  po.setMessage(SPIDeployerLogger.unknownError(task.getDescription()));
               }

               po.setState(StateType.FAILED);
               break;
            case 4:
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("task state is deferred");
               }

               po.setState(StateType.COMPLETED);
         }

      }

      private int getMessageIndex(ProgressObjectImpl po) {
         Integer index = (Integer)this.msgMap.get(po);
         if (index == null) {
            index = new Integer(0);
            this.msgMap.put(po, index);
         }

         return index;
      }

      private void updateMessageIndex(ProgressObjectImpl po, int i) {
         this.msgMap.put(po, new Integer(i));
      }

      private void report(DeploymentTaskRuntimeMBean task, int msgIndex, ProgressObjectImpl po) {
         List msgs = task.getTaskMessages();
         if (msgs != null && msgs.size() > msgIndex) {
            for(int i = msgIndex; i < msgs.size(); ++i) {
               String o = (String)msgs.get(i);
               po.reportEvent(o);
            }

            this.updateMessageIndex(po, msgs.size());
         }

      }
   }

   public class AppFilter implements NotificationFilter, Serializable {
      private static final long serialVersionUID = 1L;

      public boolean isNotificationEnabled(Notification n) {
         return n instanceof DeploymentNotification;
      }
   }

   public class AppListener implements RemoteNotificationListener, Serializable {
      transient String tid;
      transient DeploymentTaskRuntimeMBean task;
      transient ProgressObjectImpl po;
      private static final long serialVersionUID = 1L;

      AppListener(ProgressObjectImpl po, DeploymentTaskRuntimeMBean task) {
         this.task = task;
         this.tid = task.getId();
         this.po = po;
      }

      public void handleNotification(Notification notification, Object handback) {
         if (this.tid.equals(handback)) {
            DeploymentNotification n = (DeploymentNotification)notification;
            if (ServerConnectionImpl.debug) {
               Debug.say("Received notification: " + n.getMessage());
            }

            String appName = n.getAppName();
            String server = n.getServerName();
            String msg = null;
            String moduleName = null;
            String currState;
            if (n.isModuleNotification()) {
               moduleName = n.getModuleName();
               currState = n.getCurrentState();
               String targetState = n.getTargetState();
               String trans = n.getTransition();
               if (trans.equals("end")) {
                  msg = SPIDeployerLogger.successfulTransition(appName, moduleName, currState, targetState, server);
               } else if (trans.equals("failed")) {
                  msg = SPIDeployerLogger.failedTransition(appName, moduleName, currState, targetState, server);
                  this.po.setError(this.task.getError());
               }

               if (msg != null) {
                  this.po.setMessage(msg);
               }
            } else {
               currState = n.getPhase();
               msg = SPIDeployerLogger.appNotification(appName, server, currState);
               this.po.setMessage(msg);
            }

            if (msg != null) {
               this.updateProgress();

               try {
                  this.po.reportEvent(appName, moduleName, server, msg);
               } catch (ServerConnectionException var11) {
                  this.po.reportEvent(appName, moduleName, server, msg, var11);
               }
            }
         }

      }

      private void updateProgress() {
         int state = this.task.getState();
         int cancelState = this.task.getCancelState();
         switch (state) {
            case 1:
               this.po.setState(StateType.RUNNING);
               if (cancelState == 2) {
                  this.po.setAction(ActionType.CANCEL);
               } else {
                  this.po.setAction(ActionType.EXECUTE);
               }
               break;
            case 2:
            case 4:
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("completing task " + this.tid);
               }

               this.po.setState(StateType.COMPLETED);
               this.po.setAction(ActionType.EXECUTE);
               ServerConnectionImpl.this.deregisterListener(this.po);
               break;
            case 3:
               if (ServerConnectionImpl.ddebug) {
                  Debug.say("failing task " + this.tid);
               }

               this.po.setState(StateType.FAILED);
               this.po.setAction(ActionType.EXECUTE);
               ServerConnectionImpl.this.deregisterListener(this.po);
         }

      }

      public String toString() {
         return "Listener on task " + this.tid;
      }
   }
}
