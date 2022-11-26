package weblogic.management.scripting;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.UndeclaredThrowableException;
import java.rmi.ConnectException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.management.InstanceNotFoundException;
import javax.management.RuntimeOperationsException;
import javax.naming.CommunicationException;
import javax.naming.InitialContext;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SystemComponentConfigurationMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.jmx.RemoteRuntimeException;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.mbeanservers.edit.ValidationException;
import weblogic.management.runtime.ElasticServiceManagerRuntimeMBean;
import weblogic.management.runtime.ScalingTaskRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleRuntimeMBean;
import weblogic.management.runtime.SystemComponentLifeCycleTaskRuntimeMBean;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;
import weblogic.rjvm.PeerGoneException;
import weblogic.server.ServerLifecycleException;
import weblogic.utils.FileUtils;
import weblogic.utils.JavaExec;
import weblogic.utils.StringUtils;

public class LifeCycleHandler implements Serializable {
   WLScriptContext ctx = null;
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   static final int ATTEMPT_SUCCESS = 0;
   static final int ATTEMPT_FAILED = 1;
   static final int ATTEMPT_UNKNOWN = 2;

   public LifeCycleHandler(WLScriptContext ctx) {
      this.ctx = ctx;
   }

   private boolean getBooleanFromString(String name, String s, boolean defaultValue) {
      if (s.toLowerCase(Locale.US).equals("true")) {
         return true;
      } else if (s.toLowerCase(Locale.US).equals("false")) {
         return false;
      } else {
         if (defaultValue) {
            this.ctx.println(txtFmt.getValueMustBeTrueOrFalseDefaultTrue(name, s));
         } else {
            this.ctx.println(txtFmt.getValueMustBeTrueOrFalse(name, s));
         }

         return defaultValue;
      }
   }

   public boolean shutdown(String name, String entityType, String ignoreSessions, int timeOut, String force, String block, Properties props, String waitForAllSessions) throws ScriptException {
      boolean sdCurrentServer = false;
      ServerLifeCycleTaskRuntimeMBean taskBean = null;
      entityType = this.determineType(name, entityType);
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      boolean ignoreSes = this.getBooleanFromString("ignoreSessions", ignoreSessions, false);
      boolean blockCall = this.getBooleanFromString("block", block, true);
      boolean waitAllSessions = this.getBooleanFromString("waitForAllSessions", waitForAllSessions, false);

      Throwable cause;
      try {
         ServerLifeCycleRuntimeMBean slrbean = null;
         if (!entityType.equals("Server")) {
            if (entityType.equals("Cluster")) {
               this.shutdownCluster(name, force, timeOut, ignoreSes);
            } else if (entityType.equals("SystemComponent")) {
               this.shutdownComponent(name, blockCall, props);
            } else {
               if (!entityType.equals("SystemComponentConfiguration")) {
                  this.setShutdownFailure(txtFmt.getInvalidShutdownEntity(entityType), (Throwable)null);
                  return sdCurrentServer;
               }

               this.shutdownComponentConfiguration(name, blockCall, props);
            }
         } else if (this.ctx.isAdminServer) {
            this.ctx.println(txtFmt.getShutdownServer(this.ctx.serverName, name, force));
            if (this.ctx.serverName.equals(name)) {
               sdCurrentServer = true;
            }

            try {
               slrbean = this.getServerLifecycleRuntimeMBean(name);
               if (slrbean == null) {
                  this.setShutdownFailure(txtFmt.getNoServerWithName(name), (Throwable)null);
                  return sdCurrentServer;
               }

               if (force.equals("true")) {
                  taskBean = slrbean.forceShutdown();
               } else if (waitAllSessions) {
                  taskBean = slrbean.shutdown(timeOut, ignoreSes, true);
               } else {
                  taskBean = slrbean.shutdown(timeOut, ignoreSes);
               }
            } catch (InstanceNotFoundException var17) {
               this.setShutdownFailure(txtFmt.getCanNotFindServerInstance(name), var17);
               return sdCurrentServer;
            }

            try {
               this.handleShutdownTask(taskBean, name, blockCall);
               if (taskBean.getStatus().equals("FAILED")) {
                  this.setShutdownFailure((String)null, taskBean.getError());
               }
            } catch (RemoteRuntimeException var16) {
            }
         } else if (this.ctx.serverName.equals(name)) {
            sdCurrentServer = true;
            this.ctx.println(txtFmt.getShutdownServer(this.ctx.serverName, name, force));
            if (force.equals("true")) {
               this.ctx.runtimeServiceMBean.getServerRuntime().forceShutdown();
            } else {
               this.ctx.runtimeServiceMBean.getServerRuntime().shutdown(timeOut, ignoreSes);
            }

            this.ctx.dc("true");
         } else {
            this.ctx.println(txtFmt.getCannotShutdownFromManaged());
         }

         this.setShutdownSuccess(sdCurrentServer);
      } catch (ServerLifecycleException var18) {
         this.setShutdownFailure(txtFmt.getServerLifeCycledException(), var18);
      } catch (InstanceNotFoundException var19) {
         this.setShutdownFailure(txtFmt.getCanNotFindClusterInstance(), var19);
      } catch (NoSuchObjectException var20) {
         this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var20);
      } catch (weblogic.rmi.extensions.RemoteRuntimeException var21) {
         this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var21);
      } catch (RemoteRuntimeException var22) {
         this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var22);
      } catch (PeerGoneException var23) {
         this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var23);
      } catch (ConnectException var24) {
         this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var24);
      } catch (UndeclaredThrowableException var25) {
         if (var25.getCause() instanceof PeerGoneException) {
            this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var25);
         } else {
            this.setShutdownFailure(txtFmt.getCanNotFindClusterInstance(), var25);
         }
      } catch (IllegalStateException var26) {
         cause = var26.getCause();
         if (cause instanceof NoSuchObjectException) {
            this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var26);
         }
      } catch (RuntimeException var27) {
         cause = var27.getCause();
         if (cause instanceof NoSuchObjectException) {
            this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var27);
         } else if (cause instanceof PeerGoneException) {
            this.setShutdownSuccessIfCurrent(sdCurrentServer, (String)null, var27);
         } else {
            this.setShutdownFailure((String)null, var27);
         }
      } catch (ScriptException var28) {
         throw var28;
      } catch (Throwable var29) {
         this.setShutdownFailure((String)null, var29);
      }

      return sdCurrentServer;
   }

   private ServerLifeCycleRuntimeMBean getServerLifecycleRuntimeMBean(String serverName) throws InstanceNotFoundException {
      return this.ctx.isDomainRuntimeServerEnabled ? this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(serverName) : null;
   }

   private SystemComponentLifeCycleRuntimeMBean getSystemComponentLifecycleRuntimeMBean(String componentName) throws InstanceNotFoundException {
      return this.ctx.isDomainRuntimeServerEnabled ? this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupSystemComponentLifeCycleRuntime(componentName) : null;
   }

   private void handleShutdownTask(ServerLifeCycleTaskRuntimeMBean taskBean, String name, boolean blockCall) throws RemoteRuntimeException {
      if (taskBean != null && taskBean.isRunning()) {
         if (!blockCall) {
            String taskName = name + "Task";
            this.ctx.getWLSTInterpreter().set(taskName, taskBean);
            this.ctx.println(txtFmt.getServerShutdownTaskAvailable(name, taskName));
         } else {
            if (!WLSTUtil.runningWLSTAsModule()) {
               this.ctx.getWLSTInterpreter().set(name + "Task", taskBean);
            }

            this.blockOnTask(taskBean);
         }
      }

   }

   private void blockOnTask(ServerLifeCycleTaskRuntimeMBean taskBean) throws RemoteRuntimeException {
      while(true) {
         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
         }

         if (!taskBean.isRunning()) {
            System.out.println("");
            return;
         }

         System.out.print(".");
      }
   }

   private void setShutdownFailure(String msg, Throwable e) throws ScriptException {
      if (msg == null) {
         msg = txtFmt.getErrorShuttingDownServer();
      }

      this.ctx.shutdownSuccessful = false;
      if (e != null) {
         this.ctx.throwWLSTException(msg, e);
      } else {
         this.ctx.throwWLSTException(msg);
      }

   }

   private void setShutdownSuccessIfCurrent(boolean sdCurrentServer, String msg, Throwable e) throws ScriptException {
      if (sdCurrentServer) {
         this.setShutdownSuccess(sdCurrentServer);
      } else {
         this.setShutdownFailure(msg, e);
      }

   }

   private void setShutdownSuccess(boolean sdCurrentServer) throws ScriptException {
      this.ctx.shutdownSuccessful = true;
      if (sdCurrentServer) {
         try {
            this.ctx.dc("true");
         } catch (Throwable var3) {
            this.ctx.throwWLSTException(txtFmt.getErrorShuttingDownConnection(), var3);
         }
      }

   }

   public void shutdownCluster(String clusterName, String force, int timeOut, boolean ignoreSes) throws Throwable {
      try {
         if (clusterName == null || "".equals(clusterName)) {
            this.ctx.println(txtFmt.getShutdownClusterRequiresName());
         }

         this.ctx.println(txtFmt.getShutdownCluster(clusterName));
         ClusterMBean cMBean = null;
         if (this.ctx.isAdminServer) {
            boolean clusterExists = false;
            if (clusterName != null) {
               cMBean = this.ctx.runtimeDomainMBean.lookupCluster(clusterName);
               if (cMBean != null) {
                  clusterExists = true;
               }
            }

            if (!clusterExists) {
               this.ctx.throwWLSTException(txtFmt.getThereIsNoClusterDefined(clusterName));
            }

            HashMap results = new HashMap();
            ServerMBean[] count;
            if (clusterExists) {
               count = cMBean.getServers();

               try {
                  for(int i = 0; i < count.length; ++i) {
                     ServerMBean server = count[i];
                     results.put(server.getName(), force.equals("true") ? this.getServerLifecycleRuntimeMBean(server.getName()).forceShutdown() : this.getServerLifecycleRuntimeMBean(server.getName()).shutdown(timeOut, ignoreSes));

                     try {
                        Thread.currentThread();
                        Thread.sleep(1000L);
                     } catch (Exception var12) {
                     }
                  }
               } catch (Exception var13) {
                  RuntimeException re = new RuntimeException(var13);
                  throw new RuntimeOperationsException(re);
               }
            }

            count = cMBean.getServers();
            String[] serverNames = new String[count.length];

            int i;
            for(i = 0; i < count.length; ++i) {
               serverNames[i] = count[i].getName();
            }

            for(i = 0; i < count.length; ++i) {
               ServerLifeCycleTaskRuntimeMBean bean = (ServerLifeCycleTaskRuntimeMBean)results.get(count[i].getName());
               if (bean != null && bean.isRunning()) {
                  do {
                     Thread.sleep(1000L);
                  } while(bean.isRunning());
               }
            }

            this.ctx.println(txtFmt.getClusterShutdownIssued(clusterName));
         }
      } catch (Throwable var14) {
         this.ctx.throwWLSTException(txtFmt.getErrorShuttingDownCluster(), var14);
      }

   }

   public Object startCluster(String clusterName, String block) throws Throwable {
      ServerLifeCycleTaskRuntimeMBean[] tbeans = null;
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      try {
         ClusterMBean cMBean = null;
         if (!this.ctx.isAdminServer) {
            this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminStart());
            return tbeans;
         }

         boolean clusterExists = false;
         if (clusterName != null) {
            cMBean = this.ctx.runtimeDomainMBean.lookupCluster(clusterName);
            if (cMBean != null) {
               clusterExists = true;
            }
         }

         if (!clusterExists) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoClusterDefined(clusterName));
         }

         HashMap results = new HashMap();
         ServerMBean[] count = cMBean.getServers();
         String[] serverNames = new String[count.length];

         for(int i = 0; i < count.length; ++i) {
            serverNames[i] = count[i].getName();
         }

         int j;
         if (clusterExists) {
            this.ctx.println(txtFmt.getStartingServersInCluster(clusterName, StringUtils.join(serverNames, ",")));
            ServerMBean[] _servers = cMBean.getServers();
            tbeans = new ServerLifeCycleTaskRuntimeMBean[count.length];

            try {
               for(j = 0; j < _servers.length; ++j) {
                  ServerMBean server = _servers[j];
                  tbeans[j] = this.getServerLifecycleRuntimeMBean(server.getName()).start();
                  results.put(server.getName(), tbeans[j]);

                  try {
                     Thread.currentThread();
                     Thread.sleep(1000L);
                  } catch (Exception var15) {
                  }
               }
            } catch (Exception var17) {
               this.ctx.throwWLSTException(txtFmt.getProblemStartingCluster(clusterName), var17);
            }
         }

         ServerLifeCycleTaskRuntimeMBean bean;
         if (block.equals("true")) {
            boolean success = false;

            for(j = 0; j < count.length; ++j) {
               bean = (ServerLifeCycleTaskRuntimeMBean)results.get(count[j].getName());
               if (bean != null && bean.getStatus().equals("TASK IN PROGRESS") || bean.getStatus().equals("TASK COMPLETED")) {
                  try {
                     Integer in = new Integer(180000);
                     long quitTime = System.currentTimeMillis() + in.longValue();

                     do {
                        this.ctx.print(".");
                        Thread.sleep(1000L);
                     } while(bean.getStatus().equals("TASK IN PROGRESS"));

                     success = true;
                  } catch (Exception var16) {
                     this.ctx.throwWLSTException(txtFmt.getErrorGettingStatusFromLifecycle(), var16);
                  }
               }
            }

            if (success) {
               boolean suc = true;

               for(int k = 0; k < count.length; ++k) {
                  ServerLifeCycleRuntimeMBean urlServerRuntimeMBean1 = this.ctx.getDomainRuntimeDomainRuntimeMBean().lookupServerLifeCycleRuntime(serverNames[k]);
                  if (!urlServerRuntimeMBean1.getState().equals("RUNNING")) {
                     this.ctx.throwWLSTException(txtFmt.getCouldNotStartServerName(serverNames[k]));
                     suc = false;
                  }
               }

               if (suc) {
                  this.ctx.println(txtFmt.getAllServersStartedSuccessfully(clusterName));
               } else {
                  this.ctx.throwWLSTException(txtFmt.getUnableStartSomeServers(clusterName));
               }
            } else {
               this.ctx.throwWLSTException(txtFmt.getNoServersInClusterStarted(clusterName));
            }
         } else {
            Iterator resIter = results.keySet().iterator();

            while(resIter.hasNext()) {
               String svrnm = (String)resIter.next();
               bean = (ServerLifeCycleTaskRuntimeMBean)results.get(svrnm);
               this.ctx.getWLSTInterpreter().set(svrnm + "Task", bean);
               this.ctx.println(txtFmt.getServerStartStatusTask(svrnm, svrnm + "Task"));
            }

            this.ctx.println(txtFmt.getClusterStartStatus());
         }
      } catch (Throwable var18) {
         this.ctx.throwWLSTException(txtFmt.getProblemStartingCluster(clusterName), var18);
      }

      return tbeans;
   }

   public Object startServer(String targetServerName, String listenAddress, int listenPort, String block, String disableMsiMode) throws Throwable {
      ServerLifeCycleTaskRuntimeMBean taskMBean = null;

      try {
         if (WLSTUtil.runningWLSTAsModule()) {
            block = "true";
         }

         try {
            if (!this.ctx.isAdminServer || !this.ctx.isConnected()) {
               if (this.ctx.nmService.isConnectedToNM()) {
                  this.ctx.nmService.nmStart(targetServerName, (String)null, (Properties)null, (Writer)null);
                  return taskMBean;
               }

               this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedAdminOrNM());
            }

            if (this.ctx.serverName.equals(targetServerName)) {
               this.ctx.println(txtFmt.getServerIsAlreadyRunning(targetServerName));
               return taskMBean;
            }

            boolean found = false;
            ServerMBean targetServerMBean = this.ctx.runtimeDomainMBean.lookupServer(targetServerName);
            if (targetServerMBean == null) {
               this.ctx.throwWLSTException(txtFmt.getNoServerWithName(targetServerName));
            }

            this.ctx.print(txtFmt.getStartingServer(targetServerName));
            ServerLifeCycleRuntimeMBean targetServerLifeCycleRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(targetServerName);
            if (targetServerLifeCycleRuntimeMBean != null) {
               if ("true".equals(disableMsiMode)) {
                  taskMBean = targetServerLifeCycleRuntimeMBean.start(true);
               } else {
                  taskMBean = targetServerLifeCycleRuntimeMBean.start();
               }
            } else {
               ServerLifeCycleRuntimeMBean targetServerLifeCycleRuntimeMBean1 = null;
               if (this.ctx.compatDomainRuntimeMBean != null) {
                  targetServerLifeCycleRuntimeMBean1 = this.ctx.compatDomainRuntimeMBean.lookupServerLifeCycleRuntime(targetServerMBean.getName());
               }

               if (targetServerLifeCycleRuntimeMBean1 == null) {
                  this.ctx.throwWLSTException(txtFmt.getUnableToLookupServerLifeCycle(targetServerName));
               }

               if ("true".equals(disableMsiMode)) {
                  taskMBean = targetServerLifeCycleRuntimeMBean1.start(true);
               } else {
                  taskMBean = targetServerLifeCycleRuntimeMBean1.start();
               }
            }

            if (block.equals("true")) {
               if (taskMBean != null && taskMBean.isRunning()) {
                  do {
                     this.ctx.print(".");
                     Thread.sleep(1000L);
                  } while(taskMBean.isRunning());
               }

               if (taskMBean.getStatus().equals("TASK COMPLETED")) {
                  this.ctx.println(txtFmt.getServerNameStarted(targetServerName));
                  if (!WLSTUtil.runningWLSTAsModule()) {
                     this.ctx.getWLSTInterpreter().set(targetServerName + "Task", taskMBean);
                  }
               } else {
                  if (taskMBean.getError() != null) {
                     System.err.println(taskMBean.getError().getMessage());
                  }

                  this.ctx.errorMsg = txtFmt.getServerFailedtoStart(targetServerName);
                  this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
                  this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
               }
            } else {
               this.ctx.getWLSTInterpreter().set(targetServerName + "Task", taskMBean);
               this.ctx.println(txtFmt.getServerStartStatusTask(targetServerName, targetServerName + "Task"));
               this.ctx.println(txtFmt.getServerStartStatus());
            }
         } catch (SecurityException var11) {
            this.ctx.throwWLSTException(txtFmt.getSecurityExceptionOccurred(), var11);
         } catch (ServerLifecycleException var12) {
            this.ctx.throwWLSTException(txtFmt.getServerLifecycleException(), var12);
         }
      } catch (Throwable var13) {
         this.ctx.throwWLSTException(txtFmt.getErrorStartingServerPlain(), var13);
      }

      return taskMBean;
   }

   public HashMap state(String name, String type) throws Throwable {
      type = this.determineType(name, type);
      if (type.equals("Server")) {
         return this.serverState(name);
      } else if (type.equals("Cluster")) {
         return this.clusterState(name);
      } else if (type.equals("SystemComponent")) {
         return this.componentState(name);
      } else if (type.equals("SystemComponentConfiguration")) {
         return this.componentConfigurationState(name);
      } else {
         this.ctx.throwWLSTException(txtFmt.getSpecifyCorrectEntityType());
         return null;
      }
   }

   public String progress(String urlServerName) throws Throwable {
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminPerform());
      }

      String retVal = null;

      try {
         ServerLifeCycleRuntimeMBean slcBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(urlServerName);
         if (slcBean != null) {
            retVal = slcBean.getProgressAsXml();
            return retVal;
         }
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(txtFmt.getErrorGettingServerState(), var4);
      }

      this.ctx.throwWLSTException(txtFmt.getNoServerWithName(urlServerName));
      return null;
   }

   public HashMap serverState(String urlServerName) throws Throwable {
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminPerform());
      }

      HashMap hmap = new HashMap();

      try {
         ServerLifeCycleRuntimeMBean slcBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(urlServerName);
         if (slcBean != null) {
            hmap.put(slcBean.getName(), slcBean.getState());
            this.ctx.println(txtFmt.getCurrentStateOfServer(urlServerName, slcBean.getState()));
            return hmap;
         }
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(txtFmt.getErrorGettingServerState(), var4);
      }

      this.ctx.throwWLSTException(txtFmt.getNoServerWithName(urlServerName));
      return null;
   }

   public HashMap componentState(String urlComponentName) throws Throwable {
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminPerform());
      }

      HashMap hmap = new HashMap();

      try {
         SystemComponentLifeCycleRuntimeMBean sclcBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupSystemComponentLifeCycleRuntime(urlComponentName);
         if (sclcBean != null) {
            hmap.put(sclcBean.getName(), sclcBean.getState());
            this.ctx.println(txtFmt.getCurrentStateOfComponent(urlComponentName, sclcBean.getState()));
            return hmap;
         }
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(txtFmt.getErrorGettingComponentState(), var4);
      }

      this.ctx.throwWLSTException(txtFmt.getNoComponentWithName(urlComponentName));
      return null;
   }

   private HashMap clusterState(String name) throws Throwable {
      ClusterMBean cMBean = null;
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminServer());
         return null;
      } else {
         cMBean = this.ctx.runtimeDomainMBean.lookupCluster(name);
         if (cMBean == null) {
            this.ctx.throwWLSTException(txtFmt.getNoClusterConfigured(name));
            return null;
         } else {
            ServerMBean[] servers = cMBean.getServers();
            String[] serverNames = new String[servers.length];
            List allServers = new ArrayList();
            List activeServers = new ArrayList();
            HashMap map = new HashMap();
            this.ctx.println(txtFmt.getServersInCluster("" + cMBean.getServers().length, name));
            ServerLifeCycleRuntimeMBean[] sruntimes = this.ctx.getDomainRuntimeDomainRuntimeMBean().getServerLifeCycleRuntimes();

            int i;
            for(int j = 0; j < servers.length; ++j) {
               serverNames[j] = servers[j].getName();
               allServers.add(servers[j].getName());

               for(i = 0; i < sruntimes.length; ++i) {
                  ServerLifeCycleRuntimeMBean bean = sruntimes[i];
                  String srname = bean.getName();
                  String state = "";
                  if (srname.equals(serverNames[j])) {
                     state = bean.getState();
                     map.put(serverNames[j], state);
                     activeServers.add(serverNames[j]);
                     allServers.remove(serverNames[j]);
                  }
               }
            }

            String[] stt = new String[activeServers.size()];

            for(i = 0; i < activeServers.size(); ++i) {
               stt[i] = (String)activeServers.get(i);
            }

            int k;
            if (stt.length != 0) {
               this.ctx.println(txtFmt.getStateOfServers());
               String[] stt1 = new String[activeServers.size()];

               for(k = 0; k < activeServers.size(); ++k) {
                  stt1[k] = (String)activeServers.get(k);
                  this.ctx.println(stt1[k] + "---" + map.get(stt1[k]));
               }
            }

            if (!allServers.isEmpty()) {
               String names = "";

               for(k = 0; k < allServers.size(); ++k) {
                  names = names + "\n" + (String)allServers.get(k);
               }

               this.ctx.println(txtFmt.getNotActiveServers(names));
            }

            return map;
         }
      }
   }

   private HashMap componentConfigurationState(String name) throws Throwable {
      SystemComponentConfigurationMBean sccMBean = null;
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminServer());
         return null;
      } else {
         sccMBean = this.ctx.runtimeDomainMBean.lookupSystemComponentConfiguration(name);
         if (sccMBean == null) {
            this.ctx.throwWLSTException(txtFmt.getNoComponentConfigurationConfigured(name));
            return null;
         } else {
            SystemComponentMBean[] comps = sccMBean.getSystemComponents();
            String[] compNames = new String[comps.length];
            List allComps = new ArrayList();
            List activeComps = new ArrayList();
            HashMap map = new HashMap();
            this.ctx.println(txtFmt.getCompsInConfiguration("" + sccMBean.getSystemComponents().length, name));
            SystemComponentLifeCycleRuntimeMBean[] scruntimes = this.ctx.getDomainRuntimeDomainRuntimeMBean().getSystemComponentLifeCycleRuntimes();

            int i;
            for(int j = 0; j < comps.length; ++j) {
               compNames[j] = comps[j].getName();
               allComps.add(comps[j].getName());

               for(i = 0; i < scruntimes.length; ++i) {
                  SystemComponentLifeCycleRuntimeMBean bean = scruntimes[i];
                  String scname = scruntimes[i].getName();
                  String state = "";
                  if (scname.equals(compNames[j])) {
                     state = bean.getState();
                     map.put(compNames[j], state);
                     activeComps.add(compNames[j]);
                     allComps.remove(compNames[j]);
                  }
               }
            }

            String[] stt = new String[activeComps.size()];

            for(i = 0; i < activeComps.size(); ++i) {
               stt[i] = (String)activeComps.get(i);
            }

            int k;
            if (stt.length != 0) {
               this.ctx.println(txtFmt.getStateOfComponents());
               String[] stt1 = new String[activeComps.size()];

               for(k = 0; k < activeComps.size(); ++k) {
                  stt1[k] = (String)activeComps.get(k);
                  this.ctx.println(stt1[k] + "---" + map.get(stt1[k]));
               }
            }

            if (!allComps.isEmpty()) {
               String names = "";

               for(k = 0; k < allComps.size(); ++k) {
                  names = names + "\n" + (String)allComps.get(k);
               }

               this.ctx.println(txtFmt.getNotActiveServers(names));
            }

            return map;
         }
      }
   }

   public Object resume(String serverName, String block) throws ScriptException {
      ServerLifeCycleTaskRuntimeMBean taskBean = null;

      try {
         if (serverName == null) {
            serverName = this.ctx.serverName;
         }

         if (this.ctx.domainRuntimeServiceMBean == null) {
            if (serverName.equals(this.ctx.serverName)) {
               this.ctx.printDebug("Trying to resume the managed server you are currently connected to");
               this.ctx.getServerRuntimeServerRuntimeMBean().resume();
               return taskBean;
            }

            this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminResume());
            return taskBean;
         }

         if (WLSTUtil.runningWLSTAsModule()) {
            block = "true";
         }

         ServerLifeCycleRuntimeMBean srBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
         taskBean = srBean.resume();
         if (this.ctx.getBoolean(block)) {
            if (taskBean != null && taskBean.isRunning()) {
               while(true) {
                  try {
                     Thread.sleep(1000L);
                  } catch (InterruptedException var6) {
                  }

                  if (!taskBean.isRunning()) {
                     break;
                  }

                  this.ctx.print(".");
               }
            }

            if (!taskBean.getStatus().equals("FAILED")) {
               this.ctx.println(txtFmt.getServerResumedSuccessfully(serverName));
            } else {
               this.ctx.println(txtFmt.getFailedToResume(serverName, "" + taskBean.getError()));
            }

            if (!WLSTUtil.runningWLSTAsModule()) {
               this.ctx.getWLSTInterpreter().set(serverName + "_resume_Task", taskBean);
            }
         } else {
            this.ctx.getWLSTInterpreter().set(serverName + "_resume_Task", taskBean);
            this.ctx.println(txtFmt.getServerResumeTask(serverName, serverName + "_resume_Task"));
            this.ctx.println(txtFmt.getCallResumeStatus());
         }
      } catch (ServerLifecycleException var7) {
         this.ctx.throwWLSTException(txtFmt.getServerLifecycleExceptionResume(serverName), var7);
      }

      return taskBean;
   }

   public Object suspend(String serverName, String ignoreSessions, int timeOut, String force, String block) throws ScriptException {
      ServerLifeCycleTaskRuntimeMBean taskBean = null;

      try {
         if (serverName == null) {
            serverName = this.ctx.serverName;
         }

         if (this.ctx.domainRuntimeServiceMBean == null) {
            if (serverName.equals(this.ctx.serverName)) {
               this.ctx.printDebug("Trying to suspend the managed server you are currently connected to");
               this.ctx.getServerRuntimeServerRuntimeMBean().suspend(timeOut, this.ctx.getBoolean(ignoreSessions));
               return taskBean;
            } else {
               this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminSuspend());
               return taskBean;
            }
         } else {
            if (WLSTUtil.runningWLSTAsModule()) {
               block = "true";
            }

            ServerLifeCycleRuntimeMBean srBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
            if (this.ctx.getBoolean(force)) {
               taskBean = srBean.forceSuspend();
            } else {
               taskBean = srBean.suspend(timeOut, this.ctx.getBoolean(ignoreSessions));
            }

            if (this.ctx.getBoolean(block)) {
               if (taskBean != null && taskBean.isRunning()) {
                  while(true) {
                     try {
                        Thread.sleep(1000L);
                     } catch (InterruptedException var9) {
                     }

                     if (!taskBean.isRunning()) {
                        break;
                     }

                     this.ctx.print(".");
                  }
               }

               if (!taskBean.getStatus().equals("FAILED")) {
                  this.ctx.println(txtFmt.getServerSuspendedSuccessfully(serverName));
               } else {
                  this.ctx.println(txtFmt.getFailedToSuspendServer(serverName, "" + taskBean.getError()));
               }

               if (!WLSTUtil.runningWLSTAsModule()) {
                  this.ctx.getWLSTInterpreter().set(serverName + "_suspend_Task", taskBean);
               }
            } else {
               this.ctx.getWLSTInterpreter().set(serverName + "_suspend_Task", taskBean);
               this.ctx.println(txtFmt.getServerSuspendTask(serverName, serverName + "_suspend_Task"));
               this.ctx.println(txtFmt.getCallSuspendStatus());
            }

            return taskBean;
         }
      } catch (ServerLifecycleException var10) {
         this.ctx.throwWLSTException(txtFmt.getServerLifecycleExceptionSuspend(serverName), var10);
         return taskBean;
      }
   }

   public String startSvr(String serverName, String domainName, String url, String username, String password, String rootDirectory, String generateDefaultConfig, String overWriteDomainDir, String block, int timeout, String useNM, String serverLog, String sysProps, String jvmArgs, String spaceAsJvmArgsDelimiter) throws Throwable {
      Properties props = System.getProperties();
      if (props == null) {
         props = new Properties();
      }

      boolean usingDefaults = false;
      if (username != null && password != null && username.length() > 0 && password.length() > 0) {
         usingDefaults = true;
      }

      File domDir = new File(rootDirectory);
      if (!domDir.exists()) {
         domDir.mkdirs();
      }

      File configFile = new File(domDir.getAbsolutePath() + "/config/config.xml");
      if (!configFile.exists()) {
         props.setProperty("weblogic.Domain", domainName);
         props.setProperty("weblogic.Name", serverName);
      }

      String listenAddress = "localhost";
      String listenPort = "7001";
      String protocol = "t3";
      if (url == null) {
         url = "t3://localhost:7001";
      }

      if (!url.equals("t3://localhost:7001")) {
         listenPort = this.getListenPort(this.getURL(url));
         listenAddress = this.getListenAddress(this.getURL(url));
         protocol = this.ctx.getProtocol(url);
         if (props.getProperty("weblogic.EnableListenPortOverride") != null) {
            props.setProperty("weblogic.ListenPort", listenPort);
            props.setProperty("weblogic.ListenAddress", listenAddress);
            props.setProperty("weblogic.Protocol", protocol);
         }
      }

      if (!configFile.exists()) {
         props.setProperty("weblogic.ListenPort", listenPort);
         props.setProperty("weblogic.ListenAddress", listenAddress);
         props.setProperty("weblogic.Protocol", protocol);
      }

      File bootProps = new File(domDir.getAbsolutePath() + File.separator + "boot.properties");
      if (!bootProps.exists()) {
         bootProps = new File(domDir.getAbsolutePath() + File.separator + "servers" + File.separator + serverName + File.separator + "security" + File.separator + "boot.properties");
      }

      if (!bootProps.exists() && !configFile.exists() && !usingDefaults) {
         if (username == null || username.length() == 0) {
            this.ctx.throwWLSTException(txtFmt.getWLSTUserNotSpecified());
         }

         if (password == null || password.length() == 0) {
            this.ctx.throwWLSTException(txtFmt.getWLSTPasswordNotSpecified());
         }

         usingDefaults = true;
      }

      if (usingDefaults) {
         props.setProperty("weblogic.management.username", username);
         props.setProperty("weblogic.management.password", password);
      }

      String[] _props;
      if (generateDefaultConfig.equals("true")) {
         if (overWriteDomainDir.equals("true")) {
            if (domDir.exists()) {
               FileUtils.remove(domDir);
               if (username == null || username.length() == 0) {
                  this.ctx.throwWLSTException(txtFmt.getWLSTUserNotSpecified());
               }

               if (password == null || password.length() == 0) {
                  this.ctx.throwWLSTException(txtFmt.getWLSTPasswordNotSpecified());
               }

               props.setProperty("weblogic.management.username", username);
               props.setProperty("weblogic.management.password", password);
            }

            domDir.mkdirs();
         } else if (domDir.isDirectory() || domDir.exists()) {
            _props = domDir.list();
            if (_props.length > 0) {
               this.ctx.throwWLSTException(txtFmt.getRootDirectoryNotEmpty("" + domDir));
               return null;
            }

            props.setProperty("weblogic.RootDirectory", domDir.getAbsolutePath());
            props.setProperty("weblogic.management.GenerateDefaultConfig", generateDefaultConfig);
            if (this.ctx.nmService.isConnectedToNM() && useNM.toLowerCase(Locale.US).equals("true")) {
               this.ctx.nmService.nmStart(serverName, rootDirectory, this.processNodeManagerProps(sysProps), (Writer)null);
               return null;
            }

            boolean spaceAsDelimiter = false;
            if ("true".equalsIgnoreCase(spaceAsJvmArgsDelimiter)) {
               spaceAsDelimiter = true;
            }

            return this.nowStartServer(props, block, timeout, serverName, serverLog, usingDefaults, jvmArgs, spaceAsDelimiter, url);
         }
      }

      props.setProperty("weblogic.RootDirectory", domDir.getAbsolutePath());
      props.setProperty("weblogic.management.GenerateDefaultConfig", generateDefaultConfig);
      if (sysProps != null) {
         _props = StringUtils.splitCompletely(sysProps);

         for(int i = 0; i < _props.length; ++i) {
            String s = _props[i];
            String[] _s = StringUtils.splitCompletely(s, "=");
            if (_s.length == 2) {
               props.setProperty(_s[0], _s[1]);
            }
         }
      }

      if (this.ctx.nmService.isConnectedToNM() && useNM.toLowerCase(Locale.US).equals("true")) {
         this.ctx.nmService.nmStart(serverName, rootDirectory, this.processNodeManagerProps(sysProps), (Writer)null);
         return null;
      } else {
         boolean spaceAsDelimiter = false;
         if ("true".equalsIgnoreCase(spaceAsJvmArgsDelimiter)) {
            spaceAsDelimiter = true;
         }

         return this.nowStartServer(props, block, timeout, serverName, serverLog, usingDefaults, jvmArgs, spaceAsDelimiter, url);
      }
   }

   String nowStartServer(Properties props, String block, int timeout, String serverName, String serverLog, boolean usingDefaults, String jvmArgs, boolean spaceAsDelimiter, String jndiUrl) throws ScriptException {
      Process myChild = null;
      String processName = null;

      try {
         JavaExec jx = JavaExec.createCommand("weblogic.Server");
         if (jvmArgs != null) {
            String delim = spaceAsDelimiter ? " " : ",";
            if (!spaceAsDelimiter && jvmArgs.indexOf(",") == -1 && jvmArgs.indexOf(" ") != -1) {
               delim = " ";
            }

            String[] _jargs = StringUtils.splitCompletely(jvmArgs, delim);

            for(int i = 0; i < _jargs.length; ++i) {
               jx.addJvmArg(_jargs[i].trim());
            }
         }

         Enumeration e = props.keys();

         while(true) {
            String k;
            do {
               do {
                  do {
                     do {
                        if (!e.hasMoreElements()) {
                           jx.addDefaultClassPath();
                           this.ctx.printDebug("Command being used to start the server is ");
                           this.ctx.printDebug(jx.getCommand());
                           myChild = jx.getProcess();
                           this.ctx.println(txtFmt.getStartingWLSServer());
                           processName = this.calculateProcessName();
                           WLSTUtil.startProcess(myChild, processName, true, serverLog);

                           int attemptStatus;
                           try {
                              this.ctx.printDebug("Sleeping for 3 seconds for the new JVM to start");
                              Thread.sleep(3000L);
                              attemptStatus = myChild.exitValue();
                              if (attemptStatus == 1) {
                                 this.ctx.printDebug("An exit value 1 reported which indicates that WLST was not able to create a new JVM.");
                              }

                              this.ctx.throwWLSTException(txtFmt.getErrorStartingServerJVM());
                           } catch (IllegalThreadStateException var19) {
                              this.ctx.printDebug("Child process is running");
                           } catch (InterruptedException var20) {
                           }

                           if ("true".equals(block)) {
                              attemptStatus = this.attemptConnection(props, jndiUrl, timeout, serverName, usingDefaults);
                              if (attemptStatus == 0) {
                                 this.ctx.println(txtFmt.getServerStartedSuccessfully());
                                 return processName;
                              }

                              if (attemptStatus == 1) {
                                 myChild.destroy();
                                 this.ctx.throwWLSTException(txtFmt.getCouldNotStartServerTimeout());
                                 return processName;
                              }

                              this.ctx.println(txtFmt.getCheckServerOutput());
                              return processName;
                           }

                           return processName;
                        }

                        k = (String)e.nextElement();
                     } while(k != null && k.startsWith("sun."));
                  } while(k != null && k.startsWith("user."));
               } while(k != null && k.startsWith("file."));
            } while("sun.boot.class.path".equals(k));

            String v = (String)props.get(k);
            if ("java.endorsed.dirs".equals(k) && System.getProperty("java.version").startsWith("1.")) {
               String MW_Endorsed = props.getProperty("MW_HOME") + File.separator + "oracle_common" + File.separator + "modules" + File.separator + "endorsed";
               String WL_Endorsed = props.getProperty("WL_HOME") + File.separator + "modules" + File.separator + "endorsed";
               File f = new File(MW_Endorsed);
               if (!f.isDirectory() || !f.exists()) {
                  this.ctx.println(txtFmt.getFolderNotFound(f.getAbsolutePath()));
               }

               f = new File(WL_Endorsed);
               if (!f.isDirectory() || !f.exists()) {
                  this.ctx.println(txtFmt.getFolderNotFound(f.getAbsolutePath()));
               }

               v = v + File.pathSeparator + MW_Endorsed + File.pathSeparator + WL_Endorsed;
            }

            jx.addSystemProp(k, v);
         }
      } catch (IOException var21) {
         this.ctx.throwWLSTException(txtFmt.getCouldNotStartServer(), var21);
         myChild.destroy();
         return processName;
      }
   }

   private Properties processNodeManagerProps(String sysProps) {
      if (sysProps == null) {
         return null;
      } else {
         Properties props = new Properties();
         String[] _props = StringUtils.splitCompletely(sysProps, ",");

         for(int i = 0; i < _props.length; ++i) {
            String s = _props[i];
            String[] _s = StringUtils.splitPartially(s, "=", 2);
            if (_s.length == 2) {
               props.setProperty(_s[0], _s[1]);
            }
         }

         return props;
      }
   }

   private String calculateProcessName() {
      StringBuffer buf = new StringBuffer();
      Date dte = new Date();
      buf.append("WLST-WLS-");
      buf.append(Long.toString(dte.getTime()));
      return buf.toString();
   }

   private int attemptConnection(Properties props, String jndiUrl, int timeout, String serverName, boolean usingDefaults) {
      long waitTime;
      if (timeout < 0) {
         waitTime = 1L;
      } else if (timeout == 0) {
         waitTime = Long.MAX_VALUE;
      } else {
         waitTime = (long)timeout;
      }

      String username = null;
      String pwd = null;
      String idd = null;
      long elapsedTime;
      if (usingDefaults) {
         username = props.getProperty("weblogic.management.username");
         pwd = props.getProperty("weblogic.management.password");
         idd = props.getProperty("weblogic.management.IdentityDomain");
      } else {
         String domainDir = props.getProperty("weblogic.RootDirectory");
         File bootProps = new File(domainDir + File.separator + "boot.properties");
         if (!bootProps.exists()) {
            bootProps = new File(domainDir + File.separator + "servers" + File.separator + serverName + File.separator + "security" + File.separator + "boot.properties");
         }

         if (bootProps.exists()) {
            HashMap map = WLSTHelper.loadUsernameAndPasswordFromBootProperties(bootProps, domainDir);
            if (map != null) {
               username = (String)map.get("username");
               pwd = (String)map.get("password");
               idd = (String)map.get("idd");
            }
         }

         if (username == null || pwd == null) {
            this.ctx.println(txtFmt.getNoUserOrPasswordBlocking("" + timeout / '\uea60'));

            while(waitTime > 0L) {
               elapsedTime = System.currentTimeMillis();

               try {
                  this.ctx.print(".");
                  Thread.sleep(2000L);
               } catch (InterruptedException var18) {
                  throw new RuntimeException(var18);
               }

               elapsedTime = System.currentTimeMillis() - elapsedTime;
               waitTime -= elapsedTime;
            }

            this.ctx.println("");
            return 2;
         }
      }

      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      env.put("java.naming.provider.url", jndiUrl);
      if (username != null) {
         env.put("java.naming.security.principal", username);
      }

      if (pwd != null) {
         env.put("java.naming.security.credentials", pwd);
      }

      if (idd != null) {
         env.put("weblogic.jndi.identityDomain", idd);
      }

      Exception lastException = null;

      while(waitTime > 0L) {
         elapsedTime = System.currentTimeMillis();

         try {
            new InitialContext(env);
            return 0;
         } catch (Exception var19) {
            if (!(var19 instanceof CommunicationException)) {
               System.out.println(txtFmt.getUnexpectedExceptionRetrying());
               var19.printStackTrace();
            }

            try {
               this.ctx.print(".");
               Thread.sleep(2000L);
            } catch (InterruptedException var17) {
               throw new RuntimeException(var17);
            }

            lastException = var19;
            elapsedTime = System.currentTimeMillis() - elapsedTime;
            waitTime -= elapsedTime;
         }
      }

      this.ctx.println(txtFmt.getCouldNotConnectToServer("" + lastException));
      return 1;
   }

   private String getListenAddress(String url) {
      int i = url.indexOf("//");
      int j = url.lastIndexOf(":");
      String addr = url.substring(i + 2, j);
      return addr;
   }

   private String getListenPort(String url) {
      int j = url.lastIndexOf(":");
      String port = url.substring(j + 1, url.length());
      return port;
   }

   private String getURL(String url) {
      int i = url.indexOf("//");
      if (i > 0 && url.charAt(i - 1) == ':') {
         return url;
      } else {
         return i == 0 ? "t3:" + url : "t3://" + url;
      }
   }

   public boolean startServerNM(String serverName, String domainName, String domainDir, String url, String username, String password, String NMType, String NMHost, String NMPort, String NMUsername, String NMPassword) throws Throwable {
      String nmArgs = "";
      return false;
   }

   public Object shutdownComponent(String componentName, boolean blockCall, Properties props) throws Throwable {
      SystemComponentLifeCycleTaskRuntimeMBean taskBean = null;

      try {
         if (componentName == null || "".equals(componentName)) {
            this.ctx.throwWLSTException(txtFmt.getShutdownComponentRequiresName());
         }

         SystemComponentMBean cMBean = null;
         if (this.ctx.isAdminServer) {
            boolean componentExists = false;
            if (componentName != null) {
               cMBean = this.ctx.runtimeDomainMBean.lookupSystemComponent(componentName);
               if (cMBean != null) {
                  componentExists = true;
               }
            }

            if (!componentExists) {
               this.ctx.throwWLSTException(txtFmt.getThereIsNoComponentDefined(componentName));
            }

            this.ctx.println(txtFmt.getShutdownComponent(componentName));

            try {
               SystemComponentLifeCycleRuntimeMBean sclrbean = this.getSystemComponentLifecycleRuntimeMBean(componentName);
               if (sclrbean == null) {
                  this.ctx.println("");
                  this.setShutdownFailure(txtFmt.getComponentNotRunning(componentName), (Throwable)null);
                  return taskBean;
               }

               taskBean = sclrbean.shutdown(props);
            } catch (InstanceNotFoundException var9) {
               this.setShutdownFailure(txtFmt.getCanNotFindComponentInstance(componentName), var9);
               return taskBean;
            }

            try {
               if (taskBean != null && taskBean.isRunning()) {
                  if (!blockCall) {
                     String taskName = componentName + "Task";
                     this.ctx.getWLSTInterpreter().set(taskName, taskBean);
                     this.ctx.println(txtFmt.getComponentShutdownTaskAvailable(componentName, taskName));
                  } else {
                     if (!WLSTUtil.runningWLSTAsModule()) {
                        this.ctx.getWLSTInterpreter().set(componentName + "Task", taskBean);
                     }

                     this.blockOnTask(taskBean);
                  }
               }

               if (taskBean.getStatus().equals("FAILED")) {
                  this.setShutdownFailure((String)null, taskBean.getError());
               } else if (blockCall) {
                  this.ctx.println(txtFmt.getComponentShutdownSuccess(componentName));
               }
            } catch (RemoteRuntimeException var8) {
            }
         } else {
            this.ctx.println(txtFmt.getCannotShutdownFromManaged());
         }
      } catch (ScriptException var10) {
         throw var10;
      } catch (Throwable var11) {
         this.ctx.throwWLSTException(txtFmt.getErrorShuttingDownComponent(), var11);
      }

      return taskBean;
   }

   public Object startComponent(String targetComponentName, String block, Properties props) throws Throwable {
      SystemComponentLifeCycleTaskRuntimeMBean taskMBean = null;

      try {
         if (WLSTUtil.runningWLSTAsModule()) {
            block = "true";
         }

         try {
            if (!this.ctx.isAdminServer) {
               this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminStart());
            }

            boolean found = false;
            SystemComponentMBean targetComponentMBean = this.ctx.runtimeDomainMBean.lookupSystemComponent(targetComponentName);
            if (targetComponentMBean == null) {
               this.ctx.throwWLSTException(txtFmt.getThereIsNoComponentDefined(targetComponentName));
            }

            this.ctx.println(txtFmt.getStartingComponent(targetComponentName));
            SystemComponentLifeCycleRuntimeMBean targetComponentLifeCycleRuntimeMBean = this.ctx.domainRuntimeServiceMBean.getDomainRuntime().lookupSystemComponentLifeCycleRuntime(targetComponentName);
            if (targetComponentLifeCycleRuntimeMBean != null) {
               taskMBean = targetComponentLifeCycleRuntimeMBean.start(props);
            } else {
               this.ctx.throwWLSTException(txtFmt.getUnableToLookupComponentLifeCycle(targetComponentName));
            }

            boolean blockCall = this.getBooleanFromString("block", block, true);
            if (blockCall) {
               if (taskMBean != null && taskMBean.isRunning()) {
                  do {
                     this.ctx.print(".");
                     Thread.sleep(1000L);
                  } while(taskMBean.isRunning());
               }

               this.ctx.println("");
               if (taskMBean.getStatus().equals("TASK COMPLETED")) {
                  this.ctx.println(txtFmt.getComponentNameStarted(targetComponentName));
                  if (!WLSTUtil.runningWLSTAsModule()) {
                     this.ctx.getWLSTInterpreter().set(targetComponentName + "Task", taskMBean);
                  }
               } else {
                  String err = "";
                  if (taskMBean.getError() != null) {
                     err = taskMBean.getError().getMessage();
                     System.err.println(err);
                  }

                  this.ctx.errorMsg = txtFmt.getComponentFailedtoStart(targetComponentName, err);
                  this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
                  this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
               }
            } else {
               this.ctx.getWLSTInterpreter().set(targetComponentName + "Task", taskMBean);
               this.ctx.println(txtFmt.getComponentStartStatusTask(targetComponentName, targetComponentName + "Task"));
               this.ctx.println(txtFmt.getComponentStartStatus());
            }
         } catch (SecurityException var10) {
            this.ctx.throwWLSTException(txtFmt.getSecurityExceptionOccurred(), var10);
         } catch (ServerLifecycleException var11) {
            this.ctx.throwWLSTException(txtFmt.getComponentLifecycleException(), var11);
         }
      } catch (ScriptException var12) {
         throw var12;
      } catch (Throwable var13) {
         this.ctx.throwWLSTException(txtFmt.getErrorStartingComponentPlain(), var13);
      }

      return taskMBean;
   }

   public Object startComponentConfiguration(String compConfName, String block, Properties props) throws Throwable {
      SystemComponentLifeCycleTaskRuntimeMBean[] tbeans = null;
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      try {
         SystemComponentConfigurationMBean sccMBean = null;
         if (!this.ctx.isAdminServer) {
            this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminStart());
            return tbeans;
         }

         boolean compConfExists = false;
         if (compConfName != null) {
            sccMBean = this.ctx.runtimeDomainMBean.lookupSystemComponentConfiguration(compConfName);
            if (sccMBean != null) {
               compConfExists = true;
            }
         }

         if (!compConfExists) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoCompConfDefined(compConfName));
            return tbeans;
         }

         HashMap results = new HashMap();
         SystemComponentMBean[] count = sccMBean.getSystemComponents();
         String[] compNames = new String[count.length];

         for(int i = 0; i < count.length; ++i) {
            compNames[i] = count[i].getName();
         }

         int j;
         if (compConfExists) {
            this.ctx.println(txtFmt.getStartingComponentInCompConf(compConfName, StringUtils.join(compNames, ",")));
            this.ctx.println("");
            SystemComponentMBean[] comps = sccMBean.getSystemComponents();
            tbeans = new SystemComponentLifeCycleTaskRuntimeMBean[count.length];

            try {
               for(j = 0; j < comps.length; ++j) {
                  SystemComponentMBean comp = comps[j];
                  tbeans[j] = (SystemComponentLifeCycleTaskRuntimeMBean)this.startComponent(comp.getName(), block, props);
                  results.put(comp.getName(), tbeans[j]);

                  try {
                     Thread.currentThread();
                     Thread.sleep(1000L);
                  } catch (Exception var16) {
                  }
               }
            } catch (Exception var18) {
               this.ctx.throwWLSTException(txtFmt.getProblemStartingComponentConfiguration(compConfName), var18);
            }
         }

         SystemComponentLifeCycleTaskRuntimeMBean bean;
         if (block.equals("true")) {
            boolean success = false;

            for(j = 0; j < count.length; ++j) {
               bean = (SystemComponentLifeCycleTaskRuntimeMBean)results.get(count[j].getName());
               if (bean != null && bean.getStatus().equals("TASK IN PROGRESS")) {
                  try {
                     Integer in = new Integer(180000);
                     long quitTime = System.currentTimeMillis() + in.longValue();

                     do {
                        this.ctx.print(".");
                        Thread.sleep(1000L);
                     } while(bean.getStatus().equals("TASK IN PROGRESS"));

                     success = true;
                  } catch (Exception var17) {
                     this.ctx.throwWLSTException(txtFmt.getErrorGettingStatusFromLifecycle(), var17);
                  }
               } else {
                  success = true;
               }
            }

            if (success) {
               boolean suc = true;

               for(int k = 0; k < count.length; ++k) {
                  SystemComponentLifeCycleRuntimeMBean sysCompRuntimeMBean1 = this.ctx.getDomainRuntimeDomainRuntimeMBean().lookupSystemComponentLifeCycleRuntime(compNames[k]);
                  if (!sysCompRuntimeMBean1.getState().equals("RUNNING")) {
                     this.ctx.throwWLSTException(txtFmt.getCouldNotStartSystemComponent(compNames[k]));
                     suc = false;
                  }
               }

               if (suc) {
                  this.ctx.println(txtFmt.getAllCompsStartedSuccessfully(compConfName));
               } else {
                  this.ctx.throwWLSTException(txtFmt.getUnableStartSomeComps(compConfName));
               }
            } else {
               this.ctx.throwWLSTException(txtFmt.getNoComponentInComponentConfigurationStarted(compConfName));
            }
         } else {
            Iterator resIter = results.keySet().iterator();

            while(resIter.hasNext()) {
               String svrnm = (String)resIter.next();
               bean = (SystemComponentLifeCycleTaskRuntimeMBean)results.get(svrnm);
               this.ctx.getWLSTInterpreter().set(svrnm + "Task", bean);
               this.ctx.println(txtFmt.getComponentStartStatusTask(svrnm, svrnm + "Task"));
            }

            this.ctx.println(txtFmt.getComponentConfigurationStartStatus());
         }
      } catch (Throwable var19) {
         this.ctx.throwWLSTException(txtFmt.getProblemStartingComponentConfiguration(compConfName), var19);
      }

      return tbeans;
   }

   public void shutdownComponentConfiguration(String compConfName, boolean blockCall, Properties props) throws Throwable {
      try {
         if (compConfName == null || "".equals(compConfName)) {
            this.ctx.throwWLSTException(txtFmt.getShutdownCompConfRequiresName());
         }

         this.ctx.println(txtFmt.getShutdownComponentConfiguration(compConfName));
         this.ctx.println("");
         SystemComponentConfigurationMBean sccMBean = null;
         if (!this.ctx.isAdminServer) {
            this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminStart());
         }

         boolean compConfExists = false;
         if (compConfName != null) {
            sccMBean = this.ctx.runtimeDomainMBean.lookupSystemComponentConfiguration(compConfName);
            if (sccMBean != null) {
               compConfExists = true;
            }
         }

         if (!compConfExists) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoCompConfDefined(compConfName));
         }

         HashMap results = new HashMap();
         SystemComponentMBean[] count;
         if (compConfExists) {
            count = sccMBean.getSystemComponents();

            try {
               for(int i = 0; i < count.length; ++i) {
                  SystemComponentMBean comp = count[i];
                  results.put(comp.getName(), this.shutdownComponent(comp.getName(), blockCall, props));

                  try {
                     Thread.currentThread();
                     Thread.sleep(1000L);
                  } catch (Exception var11) {
                  }
               }
            } catch (Exception var12) {
               RuntimeException re = new RuntimeException(var12);
               throw new RuntimeOperationsException(re);
            }
         }

         count = sccMBean.getSystemComponents();
         String[] compConfNames = new String[count.length];

         int i;
         for(i = 0; i < count.length; ++i) {
            compConfNames[i] = count[i].getName();
         }

         for(i = 0; i < count.length; ++i) {
            SystemComponentLifeCycleTaskRuntimeMBean bean = (SystemComponentLifeCycleTaskRuntimeMBean)results.get(count[i].getName());
            if (bean != null && bean.isRunning()) {
               do {
                  Thread.sleep(1000L);
               } while(bean.isRunning());
            }
         }

         this.ctx.println(txtFmt.getCompConfShutdownIssued(compConfName));
      } catch (Throwable var13) {
         this.ctx.throwWLSTException(txtFmt.getErrorShuttingDownCompConf(), var13);
      }

   }

   public String determineType(String entity, String entityType) throws ScriptException {
      if (entityType != null) {
         return entityType;
      } else {
         entityType = "Server";

         try {
            if (this.ctx.runtimeDomainMBean.lookupServer(entity) != null) {
               return entityType;
            }

            if (this.ctx.runtimeDomainMBean.lookupCluster(entity) != null) {
               return "Cluster";
            }

            if (this.ctx.runtimeDomainMBean.lookupSystemComponent(entity) != null) {
               return "SystemComponent";
            }

            if (this.ctx.runtimeDomainMBean.lookupSystemComponentConfiguration(entity) != null) {
               return "SystemComponentConfiguration";
            }
         } catch (Exception var4) {
         }

         this.ctx.throwWLSTException(txtFmt.getNoElementWithName(entity));
         return entityType;
      }
   }

   public Object softRestart(String name, String block, Properties props, String type) throws Throwable {
      type = this.determineType(name, type);
      if (type.equals("SystemComponent")) {
         return this.softRestartComponent(name, block, props);
      } else if (type.equals("SystemComponentConfiguration")) {
         return this.softRestartComponentConfiguration(name, block, props);
      } else {
         this.ctx.throwWLSTException(txtFmt.getSpecifyCorrectEntityType());
         return null;
      }
   }

   public Object softRestartComponent(String componentName, String block, Properties props) throws ScriptException {
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      boolean blockCall = this.getBooleanFromString("block", block, true);
      SystemComponentLifeCycleTaskRuntimeMBean taskBean = null;

      try {
         if (componentName == null || "".equals(componentName)) {
            this.ctx.throwWLSTException(txtFmt.getSoftRestartComponentRequiresName());
         }

         SystemComponentMBean cMBean = null;
         if (!this.ctx.isAdminServer) {
            this.ctx.println(txtFmt.getCannotSoftRestartFromManaged());
            return taskBean;
         }

         boolean componentExists = false;
         if (componentName != null) {
            cMBean = this.ctx.runtimeDomainMBean.lookupSystemComponent(componentName);
            if (cMBean != null) {
               componentExists = true;
            }
         }

         if (!componentExists) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoComponentDefined(componentName));
         }

         this.ctx.println(txtFmt.getSoftRestartComponent(componentName));

         try {
            SystemComponentLifeCycleRuntimeMBean sclrbean = this.getSystemComponentLifecycleRuntimeMBean(componentName);
            if (sclrbean == null) {
               this.ctx.println("");
               this.setSoftRestartFailure(txtFmt.getComponentNotRunning(componentName), (Throwable)null);
               return taskBean;
            }

            taskBean = sclrbean.softRestart(props);
         } catch (InstanceNotFoundException var10) {
            this.setSoftRestartFailure(txtFmt.getCanNotFindComponentInstance(componentName), var10);
            return taskBean;
         }

         try {
            if (taskBean != null && taskBean.isRunning()) {
               if (!blockCall) {
                  String taskName = componentName + "Task";
                  this.ctx.getWLSTInterpreter().set(taskName, taskBean);
                  this.ctx.println(txtFmt.getComponentSoftRestartTaskAvailable(componentName, taskName));
               } else {
                  if (!WLSTUtil.runningWLSTAsModule()) {
                     this.ctx.getWLSTInterpreter().set(componentName + "Task", taskBean);
                  }

                  this.blockOnTask(taskBean);
               }
            }

            if (taskBean.getStatus().equals("FAILED")) {
               this.setSoftRestartFailure((String)null, taskBean.getError());
            } else if (blockCall) {
               this.ctx.println(txtFmt.getComponentSoftRestartSuccess(componentName));
            }
         } catch (RemoteRuntimeException var9) {
         }
      } catch (ScriptException var11) {
         throw var11;
      } catch (Throwable var12) {
         this.ctx.throwWLSTException(txtFmt.getErrorSoftRestartingComponent(), var12);
      }

      return taskBean;
   }

   public Object softRestartComponentConfiguration(String compConfName, String block, Properties props) throws Throwable {
      SystemComponentLifeCycleTaskRuntimeMBean[] tbeans = null;
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      try {
         SystemComponentConfigurationMBean sccMBean = null;
         if (!this.ctx.isAdminServer) {
            this.ctx.throwWLSTException(txtFmt.getShouldBeConnectedToAdminStart());
            return tbeans;
         }

         boolean compConfExists = false;
         if (compConfName != null) {
            sccMBean = this.ctx.runtimeDomainMBean.lookupSystemComponentConfiguration(compConfName);
            if (sccMBean != null) {
               compConfExists = true;
            }
         }

         if (!compConfExists) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoCompConfDefined(compConfName));
            return tbeans;
         }

         HashMap results = new HashMap();
         SystemComponentMBean[] count = sccMBean.getSystemComponents();
         String[] compNames = new String[count.length];

         for(int i = 0; i < count.length; ++i) {
            compNames[i] = count[i].getName();
         }

         int j;
         if (compConfExists) {
            this.ctx.println(txtFmt.getSoftRestartingComponentInCompConf(compConfName, StringUtils.join(compNames, ",")));
            this.ctx.println("");
            SystemComponentMBean[] comps = sccMBean.getSystemComponents();
            tbeans = new SystemComponentLifeCycleTaskRuntimeMBean[count.length];

            try {
               for(j = 0; j < comps.length; ++j) {
                  SystemComponentMBean comp = comps[j];
                  tbeans[j] = (SystemComponentLifeCycleTaskRuntimeMBean)this.softRestartComponent(comp.getName(), block, props);
                  results.put(comp.getName(), tbeans[j]);

                  try {
                     Thread.currentThread();
                     Thread.sleep(1000L);
                  } catch (Exception var14) {
                  }
               }
            } catch (Exception var16) {
               this.ctx.throwWLSTException(txtFmt.getProblemSoftRestartingComponentConfiguration(compConfName), var16);
            }
         }

         SystemComponentLifeCycleTaskRuntimeMBean bean;
         if (block.equals("true")) {
            boolean success = false;

            for(j = 0; j < count.length; ++j) {
               bean = (SystemComponentLifeCycleTaskRuntimeMBean)results.get(count[j].getName());
               if (bean != null && bean.getStatus().equals("TASK IN PROGRESS")) {
                  try {
                     new Integer(180000);

                     do {
                        this.ctx.print(".");
                        Thread.sleep(1000L);
                     } while(bean.getStatus().equals("TASK IN PROGRESS"));

                     success = true;
                  } catch (Exception var15) {
                     this.ctx.throwWLSTException(txtFmt.getErrorGettingStatusFromLifecycle(), var15);
                  }
               } else if (bean != null && !bean.getStatus().equals("TASK IN PROGRESS")) {
                  success = true;
               }
            }

            if (success) {
               boolean suc = true;

               for(int k = 0; k < count.length; ++k) {
                  SystemComponentLifeCycleRuntimeMBean sysCompRuntimeMBean1 = this.ctx.getDomainRuntimeDomainRuntimeMBean().lookupSystemComponentLifeCycleRuntime(compNames[k]);
                  if (!sysCompRuntimeMBean1.getState().equals("RUNNING")) {
                     this.ctx.throwWLSTException(txtFmt.getCouldNotSoftRestartSystemComponent(compNames[k]));
                     suc = false;
                  }
               }

               if (suc) {
                  this.ctx.println(txtFmt.getAllCompsSoftRestartedSuccessfully(compConfName));
               } else {
                  this.ctx.throwWLSTException(txtFmt.getUnableSoftRestartSomeComps(compConfName));
               }
            } else {
               this.ctx.throwWLSTException(txtFmt.getNoComponentInComponentConfigurationSoftRestarted(compConfName));
            }
         } else {
            Iterator resIter = results.keySet().iterator();

            while(resIter.hasNext()) {
               String svrnm = (String)resIter.next();
               bean = (SystemComponentLifeCycleTaskRuntimeMBean)results.get(svrnm);
               this.ctx.getWLSTInterpreter().set(svrnm + "Task", bean);
               this.ctx.println(txtFmt.getComponentStartStatusTask(svrnm, svrnm + "Task"));
            }

            this.ctx.println(txtFmt.getComponentConfigurationStartStatus());
         }
      } catch (Throwable var17) {
         this.ctx.throwWLSTException(txtFmt.getProblemSoftRestartingComponentConfiguration(compConfName), var17);
      }

      return tbeans;
   }

   private void setSoftRestartFailure(String msg, Throwable e) throws ScriptException {
      if (msg == null) {
         msg = txtFmt.getErrorSoftRestartingComponent();
      }

      if (e != null) {
         this.ctx.throwWLSTException(msg, e);
      } else {
         this.ctx.throwWLSTException(msg);
      }

   }

   private void checkScaleArgs(int numServers, int timeoutSeconds) throws ScriptException {
      if (numServers < 0) {
         this.ctx.throwWLSTException("The number of servers to scale can't be a negative number.");
      } else if (numServers == 0) {
         this.ctx.throwWLSTException("The number of servers to scale can't be zero.");
      }

      if (timeoutSeconds < 0) {
         this.ctx.throwWLSTException("The timeout in seconds can't be a negative integer.");
      }

   }

   public ScalingTaskRuntimeMBean scaleUp(String dynamicClusterName, int numServers, boolean updateConfiguration, boolean block, int timeoutSeconds, String type) throws Throwable {
      try {
         this.checkScaleArgs(numServers, timeoutSeconds);
         ServerLifeCycleRuntimeMBean[] infos = this.getServerInfos(dynamicClusterName);
         this.ctx.printDebug("SCALEUP: name=" + dynamicClusterName + ", numServers=" + numServers + ", block=" + block + ", updateConfiguration=" + updateConfiguration + ", type=" + type);
         int numToAdd = this.howManyServersToAdd(numServers, this.numShutdown(infos));
         int numAvailable = this.numShutdown(infos);
         int numRunningBeforeScaling = this.numRunning(infos);
         this.ctx.printDebug("Number of available servers: " + numAvailable + ", number of servers to add to cluster: " + numToAdd + ", number of servers running before this scaling call: " + numRunningBeforeScaling);
         if (numToAdd > 0) {
            if (!updateConfiguration) {
               this.ctx.throwWLSTException("There are not enough shutdown servers available in the cluster (" + numAvailable + ") to scaleUp by " + numServers + " -- rerun the command with the updateConfiguration flag set to true.");
            }

            this.changeMaxInConfig(dynamicClusterName, numToAdd + infos.length);
            infos = this.getServerInfos(dynamicClusterName);
         }

         ElasticServiceManagerRuntimeMBean esm = this.getElasticServiceManagerRuntimeMBean();
         ScalingTaskRuntimeMBean task = esm.scaleUp(dynamicClusterName, numServers);
         this.waitForJob(task, timeoutSeconds);
         if (block) {
            this.waitForAllServersToStart(infos, numRunningBeforeScaling + numServers, numServers, timeoutSeconds);
         }

         return task;
      } catch (ScriptException var13) {
         throw var13;
      } catch (Throwable var14) {
         this.ctx.throwWLSTException("", var14);
         return null;
      }
   }

   public ScalingTaskRuntimeMBean scaleDown(String dynamicClusterName, int numServers, boolean updateConfiguration, boolean block, int timeoutSeconds, String type) throws Throwable {
      try {
         this.checkScaleArgs(numServers, timeoutSeconds);
         ServerLifeCycleRuntimeMBean[] infos = this.getServerInfos(dynamicClusterName);
         this.ctx.printDebug("SCALEDOWN: name=" + dynamicClusterName + ", numServers=" + numServers + ", block=" + block + ", updateConfiguration=" + updateConfiguration + ", type=" + type);
         int numRunningBeforeScaling = this.numRunning(infos);
         if (numServers > numRunningBeforeScaling) {
            this.ctx.throwWLSTException("Can not scale-down by " + numServers + " servers because there are only " + numRunningBeforeScaling + " servers currently running.");
         }

         int min = this.getMinServers(dynamicClusterName);
         if (numServers > numRunningBeforeScaling - min) {
            this.ctx.throwWLSTException("Can not scale-down by " + numServers + " servers because there are only " + numRunningBeforeScaling + " servers currently running and we must leave the minimum number, " + min + ", running.");
         }

         int numStoppedBeforeScaling = this.numShutdown(infos);
         this.ctx.printDebug("The number of servers that were stopped before this scaling call: " + numStoppedBeforeScaling);
         ElasticServiceManagerRuntimeMBean esm = this.getElasticServiceManagerRuntimeMBean();
         ScalingTaskRuntimeMBean task = esm.scaleDown(dynamicClusterName, numServers);
         this.waitForJob(task, timeoutSeconds);
         if (block) {
            this.waitForAllServersToStop(infos, numServers + numStoppedBeforeScaling, numServers, timeoutSeconds);
         }

         if (updateConfiguration) {
            this.changeMaxInConfig(dynamicClusterName, infos.length - numServers);
         }

         return task;
      } catch (ScriptException var13) {
         throw var13;
      } catch (Throwable var14) {
         this.ctx.throwWLSTException("", var14);
         return null;
      }
   }

   private int getMinServers(String clusterName) {
      try {
         DomainMBean dmb = this.ctx.getServerRuntimeDomainMBean();
         ClusterMBean cmb = dmb.lookupCluster(clusterName);
         if (cmb != null) {
            DynamicServersMBean dsmb = cmb.getDynamicServers();
            return dsmb.getMinDynamicClusterSize();
         }
      } catch (Exception var5) {
      }

      return 1;
   }

   ServerLifeCycleRuntimeMBean[] getServerInfos(String clusterName) throws ScriptException {
      ClusterMBean clusterMBean = this.ctx.runtimeDomainMBean.lookupCluster(clusterName);
      ServerLifeCycleRuntimeMBean[] infos = new ServerLifeCycleRuntimeMBean[0];

      try {
         if (clusterMBean == null) {
            this.ctx.throwWLSTException(txtFmt.getThereIsNoClusterDefined(clusterName));
         }

         ServerMBean[] serverMBeans = clusterMBean.getServers();
         int numServers = serverMBeans.length;
         String prefix = clusterMBean.getDynamicServers().getServerNamePrefix();
         ArrayList slcList = new ArrayList();

         for(int i = 0; i < numServers; ++i) {
            ServerMBean smb = serverMBeans[i];
            String name = smb.getName();
            ServerLifeCycleRuntimeMBean serverLifeCycleRuntimeMBean = this.ctx.getDomainRuntimeDomainRuntimeMBean().lookupServerLifeCycleRuntime(name);
            if (name.startsWith(prefix)) {
               slcList.add(serverLifeCycleRuntimeMBean);
            }
         }

         infos = new ServerLifeCycleRuntimeMBean[slcList.size()];
         infos = (ServerLifeCycleRuntimeMBean[])slcList.toArray(infos);
      } catch (ScriptException var12) {
         throw var12;
      } catch (Throwable var13) {
         this.ctx.throwWLSTException("Error: " + var13, var13);
      }

      return infos;
   }

   public void changeMaxInConfig(String clusterName, int newMaximum) throws ScriptException, ValidationException, NotEditorException, EditTimedOutException {
      EditServiceMBean editServiceMBean = this.ctx.getEditServiceMBean();
      ConfigurationManagerMBean configurationManagerMBean = editServiceMBean.getConfigurationManager();
      DomainMBean domainMBean = configurationManagerMBean.startEdit(-1, -1);
      ClusterMBean clusterMBean = domainMBean.lookupCluster(clusterName);
      clusterMBean.getDynamicServers().setMaximumDynamicServerCount(newMaximum);
      configurationManagerMBean.activate(60000L);
   }

   ElasticServiceManagerRuntimeMBean getElasticServiceManagerRuntimeMBean() {
      return this.ctx.getDomainRuntimeDomainRuntimeMBean().getElasticServiceManagerRuntime();
   }

   private int minutesToSeconds(int minutes) {
      return 60 * minutes;
   }

   int howManyServersToAdd(int numServersWanted, int numServersAvailable) {
      int numToAdd = numServersWanted - numServersAvailable;
      return numToAdd <= 0 ? 0 : numToAdd;
   }

   private void waitForJob(ScalingTaskRuntimeMBean task, int maxWaitSeconds) {
      for(int waitCount = 0; waitCount < maxWaitSeconds; ++waitCount) {
         String progress = task.getProgress();
         if ("success".equals(progress)) {
            this.ctx.print("Remote " + task.getScalingType() + " started successfully after " + waitCount + " seconds.");
            return;
         }

         if ("failed".equals(progress)) {
            throw new RuntimeException(task.getError());
         }

         this.ctx.printDebug(" :::: GET PROGRESS SAYS: " + progress);
         this.sleep(1000);
      }

      throw new RuntimeException("The Scaling Task did not start in " + maxWaitSeconds + " seconds.");
   }

   void waitForAllServersToStart(ServerLifeCycleRuntimeMBean[] infos, int finalTotal, int totalToStart, int timeoutSeconds) throws ScriptException {
      this.ctx.println("Waiting for " + totalToStart + " servers to reach the running state.  The timeout is " + timeoutSeconds + " seconds.");
      int numRunning = this.numRunning(infos);
      int prevRunning = numRunning;

      for(int numSeconds = 0; numSeconds < timeoutSeconds; ++numSeconds) {
         numRunning = this.numRunning(infos);
         if (numRunning >= finalTotal) {
            this.ctx.println("\nAll servers are now running.");
            return;
         }

         if (numRunning > prevRunning) {
            this.ctx.println("\n" + (numRunning - prevRunning) + " server(s) transitioned to running.  Waiting for " + (finalTotal - numRunning) + " more servers.");
         }

         prevRunning = numRunning;
         this.sleepOneSecond();
      }

      this.ctx.throwWLSTException("Timed out waiting for servers to start.  A total of " + finalTotal + " running servers were desired. There were only " + numRunning + " servers running after " + timeoutSeconds + " seconds.");
   }

   void waitForAllServersToStop(ServerLifeCycleRuntimeMBean[] infos, int finalNumStopped, int totalToStop, int timeoutSeconds) throws ScriptException {
      int numStopped = this.numShutdown(infos);
      int prevStopped = numStopped;
      if (numStopped < finalNumStopped) {
         this.ctx.println("Waiting for " + totalToStop + " servers to stop.  The timeout is " + timeoutSeconds + " seconds.");
      }

      for(int numSeconds = 0; numSeconds < timeoutSeconds; ++numSeconds) {
         numStopped = this.numShutdown(infos);
         if (numStopped >= finalNumStopped) {
            this.ctx.println("\nThe servers were stopped successfully.");
            return;
         }

         if (numStopped > prevStopped) {
            this.ctx.println("\n" + (numStopped - prevStopped) + " server(s) stopped.  Waiting for " + (finalNumStopped - numStopped) + " more servers to stop.");
         }

         prevStopped = numStopped;
         this.sleepOneSecond();
      }

      this.ctx.throwWLSTException("Timed out waiting for servers to stop.  A total of " + finalNumStopped + " stopped servers was desired. There were only " + numStopped + " servers stopped after " + timeoutSeconds + " seconds.");
   }

   private void sleepOneSecond() {
      this.sleep(1000);
      this.ctx.print(".");
   }

   private void sleep(int msec) {
      try {
         Thread.sleep(1000L);
      } catch (InterruptedException var3) {
      }

   }

   int numShutdown(ServerLifeCycleRuntimeMBean[] infos) {
      int numShutdown = 0;
      ServerLifeCycleRuntimeMBean[] var3 = infos;
      int var4 = infos.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerLifeCycleRuntimeMBean info = var3[var5];
         if (this.isShutdown(info)) {
            ++numShutdown;
         }
      }

      return numShutdown;
   }

   int numRunning(ServerLifeCycleRuntimeMBean[] infos) {
      int numRunning = 0;
      ServerLifeCycleRuntimeMBean[] var3 = infos;
      int var4 = infos.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServerLifeCycleRuntimeMBean info = var3[var5];
         if (this.isRunning(info)) {
            ++numRunning;
         }
      }

      return numRunning;
   }

   boolean isRunning(ServerLifeCycleRuntimeMBean mBean) {
      return "RUNNING".equalsIgnoreCase(mBean.getState());
   }

   boolean isShutdown(ServerLifeCycleRuntimeMBean mBean) {
      return "SHUTDOWN".equalsIgnoreCase(mBean.getState());
   }
}
