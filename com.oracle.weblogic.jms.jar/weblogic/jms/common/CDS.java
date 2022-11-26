package weblogic.jms.common;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.deployment.jms.ForeignOpaqueTag;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class CDS {
   private static HashMap cdsServices = new HashMap();
   private final List dd2Listeners = Collections.synchronizedList(new LinkedList());
   private final HashMap unsuccessfulDDLookup = new HashMap();
   private final HashMap pendingRegistration = new HashMap();
   private static CDSListProvider localCDSServer;
   private static CDSListProvider localCDSProxy = CDSLocalProxy.getSingleton();
   private static final int JNDI_CONNECT_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.connectTimeout", "60000"));
   private static final int JNDI_RESPONSE_READ_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.responseReadTimeout", "60000"));
   private TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.common.DistributedDestinationManager", (WorkManager)null);
   private Timer ddPoller;
   private final Object pollerListenerLock = new Object();
   private boolean ddPollerLookupIsRunning;
   private boolean ddRegistrationLookupIsRunning;
   private final Object propertyLock = new Object();
   private TimerManager timerManagerForRegistration = null;
   private Timer listenerRegistrar;
   private static final String DDM_NAME = "weblogic.jms.common.DistributedDestinationManager";
   private static final String DDM_REGISTRATION_MANAGER_NAME = "weblogic.jms.common.DistributedDestinationRegistrationManager";
   private static final String CDS_ASYNC_REGISTRATION_WM_NAME = "CdsAsyncRegistration";
   private WorkManager cdsAsyncRegistrationWorkManager;
   private boolean postDeploymentsStart = false;
   private static final long JMS_DD_JNDI_LOOKUP_INTERVAL = 10000L;
   private static final long JMS_DD_JNDI_LOOKUP_INITIAL_DELAY = 10000L;
   private static final long JMS_DD_LISTENER_REGISTRATION_INTERVAL = 500L;
   private static final long JMS_DD_LISTENER_REGISTRATION_DELAY = 500L;
   private static final int INITIAL_CONTEXT_SUCCEEDED = 4;
   private static final int INITIAL_JNDI_LOOKUP_SUCCEEDED = 1;
   private static final int POLLER_JNDI_LOOKUP_SUCCEEDED = 2;
   private static final int NUMBER_OF_RETRIES_BEFORE_ON_FAILURE_CALLBACK = 10;
   private static final int DEFAULT_CDS_ASYNC_REGISTRATION_THREAD_COUNT;

   public static synchronized CDS getCDS() {
      String partitionName = PartitionUtils.getPartitionName();
      CDS cds = (CDS)cdsServices.get(partitionName);
      if (cds == null) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Creating CDS for partition: " + partitionName);
         }

         cds = new CDS();
         cdsServices.put(partitionName, cds);
      }

      return cds;
   }

   public static synchronized void removeCDS(String partitionName) {
      if (!PartitionUtils.isDomain(partitionName)) {
         CDS cds = (CDS)cdsServices.remove(partitionName);
         if (cds != null) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("Cleanup CDS@" + cds.hashCode() + " for partition: " + partitionName);
            }

            cds.cleanupDDMembershipChangeListeners();
         }

      }
   }

   private void initializeWorkManager() {
      this.cdsAsyncRegistrationWorkManager = WorkManagerFactory.getInstance().findOrCreate("CdsAsyncRegistration", 100, 1, DEFAULT_CDS_ASYNC_REGISTRATION_THREAD_COUNT);
   }

   private void startPolling(DD2Listener ddl) {
      synchronized(this.dd2Listeners) {
         Iterator it = this.dd2Listeners.listIterator();

         while(it.hasNext()) {
            if (ddl == it.next()) {
               it.remove();
               break;
            }
         }
      }

      ddl.setIsSecurityHandleReady(false);
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("The DD " + ddl.getDestinationName() + " is not up, starting the poller...");
      }

      synchronized(this.pollerListenerLock) {
         this.unsuccessfulDDLookup.put(ddl.getListener(), ddl);
         if (this.ddPoller == null) {
            this.ddPoller = this.timerManager.schedule(new DDLookupTimerListener(), 10000L, 10000L);
         }

      }
   }

   public CDSSecurityHandle registerForDDMembershipInformation(DDMembershipChangeListener listener) {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("CDS registration: current CIC  = " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
      }

      if (listener == null) {
         throw new AssertionError("Listener cannot be null");
      } else {
         synchronized(this.propertyLock) {
            if (this.cdsAsyncRegistrationWorkManager == null) {
               this.initializeWorkManager();
            }

            this.timerManagerForRegistration = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.common.DistributedDestinationRegistrationManager", this.cdsAsyncRegistrationWorkManager);
         }

         DD2Listener ddl = new DD2Listener(listener);
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Scheduling initial registration for DD JNDI Name " + listener.getDestinationName() + " providerIRL = " + ddl.getProviderURL() + " isLocal =  " + ddl.isLocal());
         }

         synchronized(this.pollerListenerLock) {
            this.pendingRegistration.put(ddl.getListener(), ddl);
            if (this.listenerRegistrar == null) {
               this.listenerRegistrar = this.timerManagerForRegistration.schedule(new DDListenerRegistrationTimerListener(), 500L, 500L);
            }

            return ddl;
         }
      }
   }

   public DDMemberInformation[] getDDMembershipInformation(DDMembershipChangeListener listener) {
      if (listener == null) {
         throw new AssertionError("Listener cannot be null");
      } else {
         DD2Listener ddl = new DD2Listener(listener);
         String destinationName = listener.getDestinationName();
         Context ctx = createInitialContext(ddl, false);
         if (ctx == null) {
            this.startPolling(ddl);
            return null;
         } else {
            Object destination = this.lookupDestination(ctx, ddl, false);
            if (destination == null) {
               this.startPolling(ddl);
               return null;
            } else {
               DDMemberInformation[] ret;
               try {
                  ret = this.processDD(ctx, ddl, destination);
               } catch (javax.jms.JMSException var8) {
                  this.startPolling(ddl);
                  return null;
               }

               ddl.setCurrentMemberList(ret);
               return ret;
            }
         }
      }
   }

   private void moveListenerToPoller(HashMap listeners, Object key) {
      DD2Listener ddl = null;
      synchronized(this.pollerListenerLock) {
         ddl = (DD2Listener)listeners.remove(key);
         if (listeners.size() == 0) {
            this.clearListenerRegistrarTimer();
         }
      }

      if (ddl != null) {
         this.startPolling(ddl);
      }

   }

   private synchronized void lookupDDAndCalloutListener(HashMap listeners, boolean isPoller) {
      Iterator it = null;
      synchronized(this.pollerListenerLock) {
         it = ((HashMap)listeners.clone()).keySet().iterator();
      }

      while(it != null && it.hasNext()) {
         Object key = it.next();
         DD2Listener ddl = null;
         synchronized(this.pollerListenerLock) {
            ddl = (DD2Listener)listeners.get(key);
         }

         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("lookupDDAndCalloutListener ddl= " + ddl + " isPoller=" + isPoller);
         }

         if (ddl != null) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDS look up: current CIC = " + ddl.getCIC());
            }

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (ddl.getCCL() != null) {
               Thread.currentThread().setContextClassLoader(ddl.getCCL());
            }

            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDS look up: current CIC (re-obtained) = " + ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext());
            }

            try {
               this.lookupDDAndCalloutListenerSingle(listeners, key, ddl, isPoller);
            } finally {
               if (ddl.getCCL() != null) {
                  Thread.currentThread().setContextClassLoader(cl);
               }

            }
         }
      }

   }

   private void lookupDDAndCalloutListenerSingle(HashMap listeners, Object key, DD2Listener ddl, boolean isPoller) {
      Context ctx = createInitialContext(ddl, isPoller);
      if (ctx == null) {
         if (!isPoller) {
            this.moveListenerToPoller(listeners, key);
         }

      } else {
         Object destination = this.lookupDestination(ctx, ddl, isPoller);
         if (destination == null) {
            if (!isPoller) {
               this.moveListenerToPoller(listeners, key);
            }

         } else {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("lookupDDAndCalloutListener has successfully looked up the destination  with JNDI name " + ddl.getDestinationName() + ", going to process the membership information");
            }

            DDMemberInformation[] newMemberInformation;
            try {
               newMemberInformation = this.processDD(ctx, ddl, destination);
            } catch (javax.jms.JMSException var12) {
               ddl.reportException(var12);
               if (!isPoller) {
                  this.moveListenerToPoller(listeners, key);
               }

               return;
            }

            if (!ddl.isDD()) {
               synchronized(this.dd2Listeners) {
                  this.dd2Listeners.remove(ddl);
               }
            }

            synchronized(this.pollerListenerLock) {
               listeners.remove(key);
               if (listeners.size() == 0) {
                  if (isPoller) {
                     this.clearDDPollerTimer();
                  } else {
                     this.clearListenerRegistrarTimer();
                  }
               }
            }

            ddl.listChange(newMemberInformation);
         }
      }
   }

   private static Context createInitialContext(DD2Listener dd2Listener, boolean isPoller) {
      Exception e = null;
      final DD2Listener ddl = dd2Listener;
      Context context = null;

      try {
         Context ctx = (Context)CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(ddl), new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return ddl.getInitialContext();
            }
         });
         ddl.setState(4);
         context = ctx;
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Successfully created the initial context for the JNDIName " + ddl.getDestinationName());
         }
      } catch (PrivilegedActionException var6) {
         e = var6.getException();
      } catch (NamingException var7) {
         e = var7;
      } catch (IOException var8) {
         e = var8;
      } catch (SecurityException var9) {
         e = var9;
      }

      if (e != null) {
         if (isPoller) {
            dd2Listener.incrementPollerRetryCount();
            if (dd2Listener.getPollerRetryCount() == 10) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("From ddPoller: The initial context creation for the JNDI name " + dd2Listener.getDestinationName() + " has failed, and the poller has reached the retry limit for reporting error, calling out listener's onFailure ...");
               }

               if ((dd2Listener.getState() & 4) != 4) {
                  dd2Listener.reportException((Exception)e);
               }
            }
         } else {
            if ((dd2Listener.getState() & 4) != 4) {
               dd2Listener.reportException((Exception)e);
            }

            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("The initial context creation for JNDIName " + dd2Listener.getDestinationName() + "has failed, going to start polling", (Throwable)e);
            }
         }
      }

      return context;
   }

   private Object lookupDestination(Context context, DD2Listener ddl, boolean isPoller) {
      Object destination = null;
      Exception e = null;
      boolean lookupException = false;

      try {
         destination = this.getDestinationFromContext(ddl, context, ddl);
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("The destination JNDI lookup succeeded for " + ddl.getDestinationName() + " destination = " + destination + ", going to process the membership information");
         }

         if (!isPoller) {
            ddl.setState(1);
         } else {
            ddl.setState(2);
            ddl.resetPollerRetryCount();
         }
      } catch (Exception var15) {
         e = var15;
         if (var15 instanceof NamingException) {
            lookupException = true;
         }
      }

      if (KernelStatus.isServer() && lookupException && ddl.envContext != null) {
         e = null;
         JMSServerUtilities.pushLocalJNDIContext(ddl.envContext);

         try {
            destination = this.getDestinationFromContext(ddl, context, ddl);
         } catch (Exception var13) {
            e = var13;
         } finally {
            JMSServerUtilities.popLocalJNDIContext();
         }
      }

      if (e != null) {
         if (isPoller) {
            ddl.incrementPollerRetryCount();
            if (ddl.getPollerRetryCount() == 10) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("From ddPoller: The destination with JNDI name " + ddl.getDestinationName() + " is not up and poller has reached the retry limit for reporting error, calling out listener's onFailure ...");
               }

               if ((ddl.getState() & 2) != 2) {
                  ddl.reportException(e);
               }
            }
         } else {
            if ((ddl.getState() & 1) != 1) {
               ddl.reportException(e);
            }

            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("Initial destination lookup for the JNDI name " + ddl.getDestinationName() + "has failed, going to start polling", e);
            }
         }
      }

      return destination;
   }

   private Object getDestinationFromContext(DD2Listener ddl, final Context ctx, final DD2Listener dd2Listener) throws Exception {
      Object destination = null;
      Exception e = null;

      try {
         destination = CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(ddl), new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               return ctx.lookup(dd2Listener.getDestinationName());
            }
         });
         return destination;
      } catch (PrivilegedActionException var7) {
         e = var7.getException();
         throw e;
      }
   }

   private void clearDDPollerTimer() {
      if (this.ddPoller != null) {
         this.ddPoller.cancel();
         this.ddPoller = null;
      }

   }

   private void clearListenerRegistrarTimer() {
      if (this.listenerRegistrar != null) {
         this.listenerRegistrar.cancel();
         this.listenerRegistrar = null;
      }

   }

   private void cleanupDDMembershipChangeListeners() {
      synchronized(this.pollerListenerLock) {
         this.pendingRegistration.clear();
         this.clearListenerRegistrarTimer();
         this.clearDDPollerTimer();
      }

      this.unregisterDD2Listener((DDMembershipChangeListener)null);
   }

   public void unregisterDDMembershipChangeListener(DDMembershipChangeListener listener) {
      if (listener == null) {
         throw new AssertionError("Listener cannot be null");
      } else {
         String destinationJndiName = listener.getDestinationName();
         DD2Listener ddl = null;
         synchronized(this.pollerListenerLock) {
            ddl = (DD2Listener)this.pendingRegistration.remove(listener);
            if (ddl != null) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("unregisterDDMembershipChangeListener: " + listener + ". Removing the corresponding DD (JNDI name: " + destinationJndiName + ") which is currently  kept in pendingRegistration map");
               }

               if (this.pendingRegistration.size() == 0) {
                  this.clearListenerRegistrarTimer();
               }

               return;
            }

            ddl = (DD2Listener)this.unsuccessfulDDLookup.remove(listener);
            if (ddl != null) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("unregisterDDMembershipChangeListener: " + listener + ". Removing the corresponding DD (JNDI name: " + destinationJndiName + ") which is currently  kept in unsuccessfulDDLookup map");
               }

               this.unregisterFromCDSListProvider(ddl);
               if (this.unsuccessfulDDLookup.size() == 0) {
                  this.clearDDPollerTimer();
               }

               return;
            }
         }

         this.unregisterDD2Listener(listener);
      }
   }

   private void unregisterDD2Listener(DDMembershipChangeListener listener) {
      DD2Listener ddl = null;
      synchronized(this.dd2Listeners) {
         Iterator it = this.dd2Listeners.listIterator();

         while(true) {
            do {
               if (!it.hasNext()) {
                  return;
               }

               ddl = (DD2Listener)it.next();
            } while(listener != null && ddl.getListener() != listener);

            String destinationJndiName = ddl.getListener().getDestinationName();
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("unregisterDDMembershipChangeListener: " + listener + ". Removing the correponding DD (JNDI name: " + destinationJndiName + ") which is currently  kept in ddl map");
            }

            it.remove();
            this.unregisterFromCDSListProvider(ddl);
         }
      }
   }

   private void unregisterFromCDSListProvider(DD2Listener ddl) {
      if (ddl.isLocal()) {
         if (localCDSServer == null) {
            throw new AssertionError("MDB says that it is local, but the CDS is not able to find server symbols");
         }

         localCDSServer.unregisterListener(ddl);
      } else {
         localCDSProxy.unregisterListener(ddl);
      }

   }

   private static DDMemberInformation[] processForeign(DD2Listener ddl, Object destination) {
      boolean isQueue = false;
      if (destination instanceof Queue) {
         isQueue = true;
         if (destination instanceof Topic) {
            try {
               isQueue = ((Queue)destination).getQueueName() != null;
            } catch (Throwable var6) {
               isQueue = false;
            }
         }
      }

      String type;
      if (isQueue) {
         type = new String("javax.jms.Queue");
      } else if (destination instanceof Topic) {
         type = new String("javax.jms.Topic");
      } else {
         type = null;
      }

      DDMemberInformation ddmInfo = new DDMemberInformation((String)null, type, 4, ddl.getDestinationName(), (DestinationImpl)null, (String)null, ddl.getDestinationName(), (String)null, (String)null, (String)null, (String)null);
      DDMemberInformation[] ddMemberInformation = new DDMemberInformation[]{ddmInfo};
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("processDD(): The destination with JNDI name " + ddl.getDestinationName() + " is processed as foreign destination");
      }

      return ddMemberInformation;
   }

   private static DDMemberInformation[] processNonDD(DD2Listener ddl, Object destinationIn) {
      if (!(destinationIn instanceof DestinationImpl)) {
         return processForeign(ddl, destinationIn);
      } else {
         DestinationImpl destination = (DestinationImpl)destinationIn;
         ddl.setDestinationImpl(destination);
         DDMemberInformation[] ddMemberInformation = null;
         String type = null;
         String configName = destination.getName();
         String clusterName = null;
         String migratableTargetName = null;
         if (destination.isQueue()) {
            type = new String("javax.jms.Queue");
         } else {
            type = new String("javax.jms.Topic");
         }

         String jmsServerConfigName = destination.getJMSServerConfigName();
         if (ddl.isLocal() && jmsServerConfigName != null && localCDSServer != null) {
            migratableTargetName = localCDSServer.getMigratableTargetName(jmsServerConfigName);
         }

         DDMemberInformation ddmInfo = new DDMemberInformation(destination.getName(), type, 4, ddl.getDestinationName(), destination, (String)null, ddl.getDestinationName(), (String)null, (String)null, migratableTargetName, destination.getPartitionName());
         ddMemberInformation = new DDMemberInformation[]{ddmInfo};
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("processDD(): The destination with config name " + configName + " is processed as physical destination");
         }

         return ddMemberInformation;
      }
   }

   private DDMemberInformation[] processDD(Context ctx, DD2Listener ddl, Object destination) throws javax.jms.JMSException {
      boolean non_DD = false;
      if (!(destination instanceof DistributedDestinationImpl) || destination instanceof DistributedDestinationImpl && ((DestinationImpl)destination).isPre90()) {
         non_DD = true;
      }

      Exception e = null;
      boolean lookupException = false;

      try {
         this.checkForeignContext(ctx, ddl);
      } catch (Exception var18) {
         e = var18;
         if (var18 instanceof NamingException) {
            lookupException = true;
         }
      }

      if (KernelStatus.isServer() && lookupException && ddl.envContext != null) {
         e = null;
         JMSServerUtilities.pushLocalJNDIContext(ddl.envContext);

         try {
            this.checkForeignContext(ctx, ddl);
         } catch (Exception var16) {
            e = var16;
         } finally {
            JMSServerUtilities.popLocalJNDIContext();
         }
      }

      if (e != null) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("The destination JNDI lookup Link failed for " + ddl.getJNDIName(), e);
         }

         throw new JMSException(e.getMessage(), e);
      } else if (non_DD) {
         ddl.setIsDD(false);
         return processNonDD(ddl, destination);
      } else {
         ddl.setDestinationImpl((DestinationImpl)destination);
         ddl.setIsDD(true);
         DDMemberInformation[] ddMemberInformation = null;
         String configName = ddl.getConfigName();
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("processDD(): The destination with config name " + configName + " is being processed as distributed destination");
         }

         if (ddl.isLocal()) {
            if (localCDSServer == null) {
               throw new AssertionError("MDB says that it is local, but the CDS is not able to find server symbols");
            }

            ddMemberInformation = localCDSServer.registerListener(ddl);
         } else {
            ddMemberInformation = localCDSProxy.registerListener(ddl);
         }

         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("processDD(): The destination with config name " + configName + " is a " + (ddl.isLocal() ? "local" : "remote") + "distributed destination,  getLocalDDMemberInformation(" + configName + ") returned " + ddMemberInformation.toString());
         }

         synchronized(this.dd2Listeners) {
            this.dd2Listeners.add(ddl);
            return ddMemberInformation;
         }
      }
   }

   private void checkForeignContext(final Context ctx, final DD2Listener ddl) throws Exception {
      Exception e = null;

      try {
         CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(ddl), new PrivilegedExceptionAction() {
            public Object run() throws NamingException, IOException {
               CDS.this.checkForeign(ctx, ddl);
               return null;
            }
         });
         ddl.setIsSecurityHandleReady(true);
      } catch (PrivilegedActionException var5) {
         e = var5.getException();
         throw e;
      }
   }

   private void checkForeign(Context ctx, CDSListListener ddl) throws NamingException, IOException {
      boolean foreign_non_DD = false;
      Object foreignObj = null;

      try {
         foreignObj = ctx.lookupLink(ddl.getJNDIName());
      } catch (UnsupportedOperationException var7) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("lookupLink Unsupported by vendor for destination " + ddl.getJNDIName());
         }

         return;
      }

      if (foreignObj instanceof ForeignOpaqueTag) {
         Hashtable foreignJNDIEnv = ((ForeignOpaqueTag)foreignObj).getJNDIEnvironment();
         String url = foreignJNDIEnv == null ? "unknown" : (String)foreignJNDIEnv.get("java.naming.provider.url");
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Destination " + ddl.getJNDIName() + " is instanceof ForeignOpaqueTag, with foreignJNDIEnv " + foreignJNDIEnv);
            JMSDebug.JMSCDS.debug("Changing context to foreign provider before registering listener " + (url == null ? "unknown" : url));
         }

         if (foreignJNDIEnv != null) {
            ddl.setForeign(foreignJNDIEnv);
         }
      }

   }

   public void postDeploymentsStart() {
      synchronized(this.pollerListenerLock) {
         if (this.ddPoller != null) {
            this.ddPoller.cancel();
            this.ddPoller = this.timerManager.schedule(new DDLookupTimerListener(), 0L, 10000L);
         }

         this.postDeploymentsStart = true;
      }
   }

   private static String getDestinationName(Destination dest) {
      return dest instanceof DistributedDestinationImpl ? ((DistributedDestinationImpl)dest).getInstanceName() : ((DestinationImpl)dest).getName();
   }

   public static void dumpDDMITable(DDMemberInformation[] ddmi) {
      if (ddmi != null && ddmi.length != 0) {
         for(int i = 0; i < ddmi.length; ++i) {
            if (ddmi[i].getDestination() != null) {
               JMSDebug.JMSCDS.debug("Entry[" + i + "] = " + getDestinationName(ddmi[i].getDestination()) + ":  with destination id " + ((DestinationImpl)ddmi[i].getDestination()).getDestinationId() + ": " + ddmi[i]);
            }
         }

      } else {
         JMSDebug.JMSCDS.debug("Table is empty");
      }
   }

   public static void dumpChangeEvent(DDMembershipChangeEventImpl changeEvent) {
      JMSDebug.JMSCDS.debug("Here is the added table:");
      dumpDDMITable(changeEvent.getAddedDDMemberInformation());
      JMSDebug.JMSCDS.debug("Here is the removed table:");
      dumpDDMITable(changeEvent.getRemovedDDMemberInformation());
   }

   private static boolean unchanged(DDMemberInformation member, DDMemberInformation oldMember) {
      if (member.isProductionPaused() != oldMember.isProductionPaused()) {
         return false;
      } else if (member.isInsertionPaused() != oldMember.isInsertionPaused()) {
         return false;
      } else if (member.isConsumptionPaused() != oldMember.isConsumptionPaused()) {
         return false;
      } else {
         DestinationImpl dImpl = (DestinationImpl)member.getDestination();
         DestinationImpl oldImpl = (DestinationImpl)oldMember.getDestination();
         if (!dImpl.getDestinationId().equals(oldImpl.getDestinationId())) {
            return false;
         } else if (dImpl.getDispatcherId() != null && !dImpl.getDispatcherId().equals(oldImpl.getDispatcherId())) {
            return false;
         } else if (!dImpl.getServerName().equals(oldImpl.getServerName())) {
            return false;
         } else {
            return dImpl.getJMSServerConfigName() == null || oldImpl.getJMSServerConfigName() == null || dImpl.getJMSServerConfigName().equals(oldImpl.getJMSServerConfigName());
         }
      }
   }

   private static DDMembershipChangeEventImpl makeChangeEvent(boolean isDD, String configName, String jndiName, DDMemberInformation[] newArray, DDMemberInformation[] oldArray) {
      if (oldArray == null) {
         return newArray == null ? null : new DDMembershipChangeEventImpl(isDD, configName, jndiName, newArray, oldArray);
      } else if (newArray == null) {
         return new DDMembershipChangeEventImpl(isDD, configName, jndiName, newArray, oldArray);
      } else {
         Map newMap = new HashMap();

         for(int i = 0; newArray != null && i < newArray.length; ++i) {
            newMap.put(newArray[i].getMemberName(), newArray[i]);
         }

         Map oldMap = new HashMap();

         for(int i = 0; oldArray != null && i < oldArray.length; ++i) {
            oldMap.put(oldArray[i].getMemberName(), oldArray[i]);
         }

         List added = new LinkedList();
         List removed = new LinkedList();
         Iterator iter = newMap.values().iterator();

         DDMemberInformation oldMember;
         DDMemberInformation oldMember;
         while(iter.hasNext()) {
            oldMember = (DDMemberInformation)iter.next();
            oldMember = (DDMemberInformation)oldMap.get(oldMember.getMemberName());
            if (oldMember == null) {
               added.add(oldMember);
            } else if (!unchanged(oldMember, oldMember)) {
               added.add(oldMember);
               removed.add(oldMember);
            }
         }

         iter = oldMap.values().iterator();

         while(iter.hasNext()) {
            oldMember = (DDMemberInformation)iter.next();
            oldMember = (DDMemberInformation)newMap.get(oldMember.getMemberName());
            if (oldMember == null) {
               removed.add(oldMember);
            }
         }

         if (added.size() == 0 && removed.size() == 0) {
            return null;
         } else {
            DDMemberInformation[] addedArray = null;
            if (added.size() != 0) {
               addedArray = (DDMemberInformation[])((DDMemberInformation[])added.toArray(new DDMemberInformation[0]));
            }

            DDMemberInformation[] removedArray = null;
            if (removed.size() != 0) {
               removedArray = (DDMemberInformation[])((DDMemberInformation[])removed.toArray(new DDMemberInformation[0]));
            }

            return new DDMembershipChangeEventImpl(isDD, configName, jndiName, addedArray, removedArray);
         }
      }
   }

   private void ddPollerLookup() {
      HashMap unsuccessfulDDLookup = null;
      synchronized(this.propertyLock) {
         if (this.ddPollerLookupIsRunning) {
            return;
         }

         this.ddPollerLookupIsRunning = true;
      }

      synchronized(this.pollerListenerLock) {
         unsuccessfulDDLookup = this.unsuccessfulDDLookup;
      }

      boolean var14 = false;

      try {
         var14 = true;
         this.lookupDDAndCalloutListener(unsuccessfulDDLookup, true);
         var14 = false;
      } finally {
         if (var14) {
            synchronized(this.propertyLock) {
               this.ddPollerLookupIsRunning = false;
            }
         }
      }

      synchronized(this.propertyLock) {
         this.ddPollerLookupIsRunning = false;
      }
   }

   private void ddRegistrationLookup() {
      HashMap pendingRegistration = null;
      synchronized(this.propertyLock) {
         if (this.ddRegistrationLookupIsRunning) {
            return;
         }

         this.ddRegistrationLookupIsRunning = true;
      }

      synchronized(this.pollerListenerLock) {
         pendingRegistration = this.pendingRegistration;
      }

      boolean var14 = false;

      try {
         var14 = true;
         this.lookupDDAndCalloutListener(pendingRegistration, false);
         var14 = false;
      } finally {
         if (var14) {
            synchronized(this.propertyLock) {
               this.ddRegistrationLookupIsRunning = false;
            }
         }
      }

      synchronized(this.propertyLock) {
         this.ddRegistrationLookupIsRunning = false;
      }
   }

   static {
      try {
         localCDSServer = (CDSListProvider)Class.forName("weblogic.jms.common.CDSServer").getDeclaredMethod("getSingleton", (Class[])null).invoke((Object)null, (Object[])null);
      } catch (Exception var4) {
      }

      int defThreadCount = 8;
      String tc = System.getProperty("weblogic.jms.CDS.AsyncRegisterationThreadCount", "8");

      try {
         defThreadCount = Integer.parseInt(tc);
      } catch (NumberFormatException var3) {
         var3.printStackTrace();
      }

      DEFAULT_CDS_ASYNC_REGISTRATION_THREAD_COUNT = defThreadCount;
   }

   private final class DDLookupTimerListener implements NakedTimerListener {
      DDLookupTimerListener() {
      }

      public void timerExpired(Timer timer) {
         CDS.this.ddPollerLookup();
      }
   }

   private final class DD2Listener implements CDSListListener, Runnable, CDSSecurityHandle {
      private DDMembershipChangeListener listener;
      private DDMemberInformation[] currentMemberList = null;
      private DDMemberInformation[] pendingMemberList = null;
      private DestinationImpl dImpl;
      private boolean isDD;
      private boolean running;
      private boolean moreToProcess;
      private WorkManager workManager;
      private AbstractSubject foreignSubject;
      private String providerURL;
      private Context foreignContext;
      private Context envContext;
      private AbstractSubject listenerThreadSubject;
      private int pollerRetryCount = 0;
      private int state;
      private Exception lastExceptionReported = null;
      private int privilegedActionExceptionReported = 0;
      private int namingExceptionReported = 0;
      private int ioExceptionReported = 0;
      private int unknownExceptionReported = 0;
      private boolean isSecurityHandleReady;
      private boolean isForeignJMSServer;
      private boolean isRemoteDomain;
      private Object foreignContextLock = new Object();
      private ComponentInvocationContext cic = null;
      private ClassLoader cLoader = null;

      public DD2Listener(DDMembershipChangeListener listener) {
         this.listener = listener;
         this.providerURL = listener.getProviderURL();
         this.envContext = listener.getEnvContext();
         this.listenerThreadSubject = CrossDomainSecurityManager.getCurrentSubject();
         if (KernelStatus.isServer()) {
            this.cLoader = Thread.currentThread().getContextClassLoader();
            this.cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDS: register CIC = " + this.cic + " cLoader = " + this.cLoader);
            }
         }

         this.workManager = WorkManagerFactory.getInstance().getSystem();
      }

      public synchronized void setState(int flag) {
         this.state |= flag;
      }

      public synchronized int getState() {
         return this.state;
      }

      public synchronized void incrementPollerRetryCount() {
         ++this.pollerRetryCount;
      }

      public synchronized void resetPollerRetryCount() {
         this.pollerRetryCount = 0;
      }

      public synchronized int getPollerRetryCount() {
         return this.pollerRetryCount;
      }

      public synchronized void setIsSecurityHandleReady(boolean isReady) {
         this.isSecurityHandleReady = isReady;
      }

      public void reportException(Exception exception) {
         if (exception instanceof PrivilegedActionException) {
            ++this.privilegedActionExceptionReported;
            if (this.privilegedActionExceptionReported > 1) {
               return;
            }
         }

         if (exception instanceof NamingException) {
            ++this.namingExceptionReported;
            if (this.namingExceptionReported > 1) {
               return;
            }
         }

         if (exception instanceof IOException) {
            ++this.ioExceptionReported;
            if (this.ioExceptionReported > 1) {
               return;
            }
         }

         this.listener.onFailure(this.getJNDIName(), exception);
         this.lastExceptionReported = exception;
      }

      public Exception getLastExceptionReported() {
         return this.lastExceptionReported;
      }

      public int getIOExceptionReported() {
         return this.ioExceptionReported;
      }

      public int getNamingExceptionReported() {
         return this.namingExceptionReported;
      }

      public int getPrivilegedActionExceptionReported() {
         return this.privilegedActionExceptionReported;
      }

      public int getUnknownExceptionReported() {
         return this.unknownExceptionReported;
      }

      public ComponentInvocationContext getCIC() {
         return this.cic;
      }

      public ClassLoader getCCL() {
         return this.cLoader;
      }

      public void setIsDD(boolean isDD) {
         this.isDD = isDD;
      }

      public boolean isDD() {
         return this.isDD;
      }

      public String getDestinationName() {
         return this.listener.getDestinationName();
      }

      public String getProviderURL() {
         return this.providerURL;
      }

      public String getJNDIName() {
         return this.listener.getDestinationName();
      }

      public void setForeign(Hashtable foreignJndiEnv) throws NamingException, IOException {
         assert foreignJndiEnv != null;

         Hashtable foreignJndiEnvCopy = new Hashtable(foreignJndiEnv);
         if (foreignJndiEnv.get("java.naming.factory.initial") == null) {
            foreignJndiEnvCopy.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         }

         if (!foreignJndiEnvCopy.containsKey("weblogic.jndi.connectTimeout")) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("setForeign()  set weblogic.jndi.connectTimeout to " + CDS.JNDI_CONNECT_TIMEOUT);
            }

            foreignJndiEnvCopy.put("weblogic.jndi.connectTimeout", new Long((long)CDS.JNDI_CONNECT_TIMEOUT));
         }

         if (!foreignJndiEnvCopy.containsKey("weblogic.jndi.responseReadTimeout")) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("setForeign()  set weblogic.jndi.responseReadTimeout to " + CDS.JNDI_RESPONSE_READ_TIMEOUT);
            }

            foreignJndiEnvCopy.put("weblogic.jndi.responseReadTimeout", new Long((long)CDS.JNDI_RESPONSE_READ_TIMEOUT));
         }

         foreignJndiEnvCopy.put("weblogic.jndi.disableLoggingOfWarningMsg", "true");
         synchronized(this.foreignContextLock) {
            this.foreignContext = new InitialContext(foreignJndiEnvCopy);
         }

         AbstractSubject subject = CrossDomainSecurityManager.getCurrentSubject();
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug(" setForeign env = " + JMSOBSHelper.filterProperties(foreignJndiEnvCopy));
         }

         this.providerURL = (String)foreignJndiEnvCopy.get("java.naming.provider.url");
         boolean isRemoteD = false;
         if (this.providerURL != null && foreignJndiEnvCopy.get("java.naming.factory.initial") != null && ((String)foreignJndiEnvCopy.get("java.naming.factory.initial")).indexOf("weblogic") != -1) {
            isRemoteD = CrossDomainSecurityManager.getCrossDomainSecurityUtil().isRemoteDomain(this.providerURL);
         }

         this.foreignSubject = subject;
         this.isForeignJMSServer = true;
         this.isRemoteDomain = isRemoteD;
         if (foreignJndiEnvCopy.get("java.naming.security.principal") == null && this.isRemoteDomain) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug(" setForeign is remote domain use anonynous");
            }

            this.foreignSubject = SubjectManager.getSubjectManager().getAnonymousSubject();
         }

      }

      public DDMembershipChangeListener getListener() {
         return this.listener;
      }

      public Context getInitialContext() throws NamingException {
         return this.getInitialContextFromListener();
      }

      public AbstractSubject getSubject() {
         return this.listener.getSubject();
      }

      public boolean isLocal() {
         return this.providerURL == null || this.providerURL.length() == 0;
      }

      public synchronized String getConfigName() {
         return this.dImpl == null ? null : this.dImpl.getName();
      }

      public Context getContext() throws NamingException {
         synchronized(this.foreignContextLock) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug(" getContext() foreign = " + this.foreignContext);
            }

            if (this.foreignContext != null) {
               return this.foreignContext;
            }
         }

         return this.getInitialContextFromListener();
      }

      private Context getInitialContextFromListener() throws NamingException {
         try {
            return (Context)CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.providerURL, this.listenerThreadSubject), new PrivilegedExceptionAction() {
               public Object run() throws NamingException {
                  return DD2Listener.this.listener.getInitialContext();
               }
            });
         } catch (PrivilegedActionException var3) {
            Exception e = var3.getException();
            if (e instanceof NamingException) {
               throw (NamingException)e;
            } else {
               throw new NamingException(e.getMessage());
            }
         }
      }

      public synchronized DDMemberInformation[] getCurrentMemberList() {
         return this.currentMemberList;
      }

      public synchronized void setCurrentMemberList(DDMemberInformation[] currentMemberList) {
         this.currentMemberList = currentMemberList;
      }

      public void listChange(DDMemberInformation[] information) {
         synchronized(this) {
            if (this.pendingMemberList == information || this.currentMemberList == information) {
               return;
            }

            this.pendingMemberList = information;
            this.moreToProcess = true;
            if (this.running) {
               return;
            }

            this.running = true;
         }

         this.workManager.schedule(this);
      }

      public void distributedDestinationGone(DispatcherId dispatcherId) {
         ArrayList informations = new ArrayList();
         DDMemberInformation member = null;
         synchronized(this) {
            if (this.currentMemberList != null) {
               for(int i = 0; i < this.currentMemberList.length; ++i) {
                  DDMemberInformation thisMember = this.currentMemberList[i];
                  DispatcherId dId = ((DestinationImpl)thisMember.getDestination()).getDispatcherId();
                  if (dId != null && dId.equals(dispatcherId)) {
                     member = thisMember;
                  } else {
                     informations.add(thisMember);
                  }
               }
            }
         }

         if (member != null) {
            this.listChange((DDMemberInformation[])((DDMemberInformation[])informations.toArray(new DDMemberInformation[informations.size()])));
         }

         CDS.this.startPolling(this);
      }

      public synchronized DestinationImpl getDestinationImpl() {
         return this.dImpl;
      }

      public synchronized DistributedDestinationImpl getDistributedDestinationImpl() {
         return (DistributedDestinationImpl)this.dImpl;
      }

      public synchronized void setDestinationImpl(DestinationImpl dImpl) {
         this.dImpl = dImpl;
      }

      public boolean isRemoteDomain() {
         if (!this.isReady()) {
            throw new java.lang.IllegalStateException("The handle is not ready");
         } else {
            return this.isRemoteDomain;
         }
      }

      public void close() {
         synchronized(this.foreignContextLock) {
            try {
               if (this.foreignContext != null) {
                  this.foreignContext.close();
               }
            } catch (NamingException var4) {
            }

         }
      }

      public synchronized boolean isReady() {
         return this.isSecurityHandleReady;
      }

      public boolean isForeignJMSServer() {
         if (!this.isReady()) {
            throw new java.lang.IllegalStateException("The handle is not ready");
         } else {
            return this.isForeignJMSServer;
         }
      }

      public AbstractSubject getForeignSubject() {
         if (!this.isReady()) {
            throw new java.lang.IllegalStateException("The handle is not ready");
         } else {
            return this.foreignSubject;
         }
      }

      public void run() {
         DDMembershipChangeEventImpl changeEvent = null;

         while(true) {
            synchronized(this) {
               changeEvent = CDS.makeChangeEvent(this.isDD(), this.getConfigName(), this.getJNDIName(), this.pendingMemberList, this.currentMemberList);
               if (changeEvent == null) {
                  this.running = false;
                  return;
               }

               this.currentMemberList = this.pendingMemberList;
               this.moreToProcess = false;
            }

            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               CDS.dumpChangeEvent(changeEvent);
            }

            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("informDDMembershipChangeLocally(): Invoking the onMembershipChange() of the local  DDMembershipChangeListener " + this.listener + " for DD JNDIName " + this.getJNDIName());
            }

            if (this.cic != null && KernelStatus.isServer() && JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDS pushCIC(): " + this.cic + " for listener@" + this.listener.hashCode() + "[" + this.listener + "]");
            }

            try {
               ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
               Throwable var3 = null;

               try {
                  this.listener.onDDMembershipChange(changeEvent);
               } catch (Throwable var17) {
                  var3 = var17;
                  throw var17;
               } finally {
                  if (mic != null) {
                     if (var3 != null) {
                        try {
                           mic.close();
                        } catch (Throwable var16) {
                           var3.addSuppressed(var16);
                        }
                     } else {
                        mic.close();
                     }
                  }

               }
            } catch (Throwable var19) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Exception when calling user code: ", var19);
               }
            }

            synchronized(this) {
               if (!this.moreToProcess) {
                  this.running = false;
                  return;
               }
            }
         }
      }
   }

   private final class DDListenerRegistrationTimerListener implements NakedTimerListener {
      DDListenerRegistrationTimerListener() {
      }

      public void timerExpired(Timer timer) {
         Boolean postDeploymentsStartCheck = false;
         synchronized(CDS.this.pollerListenerLock) {
            if (CDS.this.postDeploymentsStart) {
               postDeploymentsStartCheck = CDS.this.postDeploymentsStart;
               CDS.this.clearListenerRegistrarTimer();
            }
         }

         if (postDeploymentsStartCheck || !KernelStatus.isServer()) {
            CDS.this.ddRegistrationLookup();
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("ddListenerRegistrar has finished registering all of the listeners pendingRegistration map.");
            }
         }

      }
   }
}
