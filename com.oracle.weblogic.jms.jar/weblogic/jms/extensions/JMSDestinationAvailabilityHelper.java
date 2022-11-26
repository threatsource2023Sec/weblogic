package weblogic.jms.extensions;

import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.common.CDS;
import weblogic.jms.common.CDSSecurityHandle;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.DDMemberInformation;
import weblogic.jms.common.DDMembershipChangeEventImpl;
import weblogic.jms.common.DDMembershipChangeListener;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSOBSHelper;
import weblogic.jms.common.PartitionUtils;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.work.InheritableThreadContext;

public class JMSDestinationAvailabilityHelper {
   private static final int REGISTRATION_MODE_DEFAULT = 0;
   private static final int REGISTRATION_MODE_LOCAL_ONLY = 1;
   private static final int REGISTRATION_MODE_ALL = 2;
   private static final int JNDI_CONNECT_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.connectTimeout", "60000"));
   private static final int JNDI_RESPONSE_READ_TIMEOUT = Integer.parseInt(System.getProperty("weblogic.jndi.responseReadTimeout", "60000"));
   private static JMSDestinationAvailabilityHelper INSTANCE = new JMSDestinationAvailabilityHelper();
   private static boolean IS_COMPATIBLE;
   private String currentServerName = null;
   private String currentClusterName = null;
   private String currentDomainName = null;
   private boolean isServer;
   private static boolean coreEngine = false;
   private static boolean initialized = false;
   private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private static final String OBS_JNDI_FACTORY = "weblogic.jms.WLInitialContextFactory";

   private JMSDestinationAvailabilityHelper() {
   }

   private void initialize() {
      try {
         if (KernelStatus.isServer() && !coreEngine) {
            Class clz = Class.forName("weblogic.jms.common.JMSManagementHelper");
            Object jmsServiceInstance = clz.newInstance();
            Method m = clz.getMethod("getServerName");
            this.currentServerName = (String)m.invoke(jmsServiceInstance);
            m = clz.getMethod("getClusterName");
            this.currentClusterName = (String)m.invoke(jmsServiceInstance);
            m = clz.getMethod("getDomainName");
            this.currentDomainName = (String)m.invoke(jmsServiceInstance);
         } else {
            this.currentServerName = "";
            CrossDomainSecurityManager.ensureSubjectManagerInitialized();
         }
      } catch (Exception var4) {
         throw new AssertionError(var4);
      }

      initialized = true;
   }

   public static synchronized JMSDestinationAvailabilityHelper getInstance() {
      if (!initialized) {
         INSTANCE.initialize();
      }

      return INSTANCE;
   }

   public RegistrationHandle register(Hashtable properties, String destJNDIName, DestinationAvailabilityListener listener) {
      return this.register(new PropertiesContextFactory(properties), destJNDIName, listener, 2, (Context)null);
   }

   public RegistrationHandle register(Hashtable properties, String destJNDIName, DestinationAvailabilityListener listener, Context envContext) {
      return this.register(new PropertiesContextFactory(properties), destJNDIName, listener, 2, envContext);
   }

   private RegistrationHandle register(Hashtable properties, String destJNDIName, DestinationAvailabilityListener listener, int mode) {
      return this.register(new PropertiesContextFactory(properties), destJNDIName, listener, mode, (Context)null);
   }

   private RegistrationHandle register(ContextFactory ctxFactory, String destJNDIName, DestinationAvailabilityListener listener, int mode, Context envContext) {
      if (destJNDIName == null) {
         throw new AssertionError("register(): destJNDIName cannot be null");
      } else if (mode != 0 && mode != 1 && mode != 2) {
         throw new AssertionError("register(): Invalid registration mode " + mode);
      } else {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Registering the listener " + listener + " for destJNDIName=" + destJNDIName);
         }

         DestinationAvailabilityListenerWrapper destinationAvailabilityListenerWrapper = new DestinationAvailabilityListenerWrapper(ctxFactory, destJNDIName, listener, mode, envContext);
         CDSSecurityHandle secHandle = CDS.getCDS().registerForDDMembershipInformation(destinationAvailabilityListenerWrapper);
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Successfully registered the listener " + listener + " for destJNDIName=" + destJNDIName);
         }

         ctxFactory.setSecurityHandle(secHandle);
         return destinationAvailabilityListenerWrapper;
      }
   }

   private boolean isLocalWLSServer(DDMemberInformation memberInformation) {
      return this.currentDomainName != null && this.currentDomainName.equals(memberInformation.getDDMemberDomainName()) && this.currentServerName != null && this.currentServerName.equals(memberInformation.getDDMemberServerName());
   }

   private boolean isLocalCluster(DDMemberInformation memberInformation) {
      return this.currentDomainName != null && this.currentDomainName.equals(memberInformation.getDDMemberDomainName()) && this.currentClusterName != null && this.currentClusterName.equals(memberInformation.getDDMemberClusterName());
   }

   private boolean isLocalServerDDMember(DDMemberInformation memberInformation) {
      return this.currentServerName != null && this.currentServerName.equals(memberInformation.getDDMemberServerName()) && this.currentClusterName.equals(memberInformation.getDDMemberClusterName()) && this.currentDomainName.equals(memberInformation.getDDMemberDomainName());
   }

   private boolean isRemoteClusterDDMember(DDMemberInformation memberInformation) {
      return this.currentDomainName == null || this.currentClusterName == null || !this.currentClusterName.equals(memberInformation.getDDMemberClusterName()) || !this.currentDomainName.equals(memberInformation.getDDMemberDomainName());
   }

   private DDMemberInformation[] getLocalServerDDMembers(DDMemberInformation[] ddMemberInfo) {
      ArrayList localDDMembersList = new ArrayList();
      if (ddMemberInfo != null) {
         for(int i = 0; i < ddMemberInfo.length; ++i) {
            if (this.isLocalServerDDMember(ddMemberInfo[i])) {
               localDDMembersList.add(ddMemberInfo[i]);
            }
         }
      }

      return localDDMembersList.size() != 0 ? (DDMemberInformation[])((DDMemberInformation[])localDDMembersList.toArray(new DDMemberInformation[localDDMembersList.size()])) : null;
   }

   private DDMemberInformation[] getRemoteClusterDDMembers(DDMemberInformation[] ddMemberInfo) {
      ArrayList localDDMembersList = new ArrayList();
      if (ddMemberInfo != null) {
         for(int i = 0; i < ddMemberInfo.length; ++i) {
            if (this.isRemoteClusterDDMember(ddMemberInfo[i])) {
               localDDMembersList.add(ddMemberInfo[i]);
            }
         }
      }

      return localDDMembersList.size() != 0 ? (DDMemberInformation[])((DDMemberInformation[])localDDMembersList.toArray(new DDMemberInformation[localDDMembersList.size()])) : null;
   }

   private DDMemberInformation[][] filterDDMembers(DDMemberInformation[] removedDDMemberInfo, DDMemberInformation[] addedDDMemberInfo) {
      Map newMap = new HashMap();

      for(int i = 0; addedDDMemberInfo != null && i < addedDDMemberInfo.length; ++i) {
         newMap.put(addedDDMemberInfo[i].getMemberName(), addedDDMemberInfo[i]);
      }

      Map oldMap = new HashMap();

      for(int i = 0; removedDDMemberInfo != null && i < removedDDMemberInfo.length; ++i) {
         oldMap.put(removedDDMemberInfo[i].getMemberName(), removedDDMemberInfo[i]);
      }

      List added = new LinkedList();
      List removed = new LinkedList();
      Iterator var7 = oldMap.values().iterator();

      while(var7.hasNext()) {
         DDMemberInformation oldMember = (DDMemberInformation)var7.next();
         DDMemberInformation newMember = (DDMemberInformation)newMap.remove(oldMember.getMemberName());
         if (newMember == null) {
            removed.add(oldMember);
         } else if (this.changed(oldMember, newMember)) {
            removed.add(oldMember);
            added.add(newMember);
         }
      }

      added.addAll(newMap.values());
      DDMemberInformation[] removedArray = null;
      if (removed.size() > 0) {
         removedArray = (DDMemberInformation[])removed.toArray(new DDMemberInformation[0]);
      }

      DDMemberInformation[] addedArray = null;
      if (added.size() > 0) {
         addedArray = (DDMemberInformation[])added.toArray(new DDMemberInformation[0]);
      }

      return new DDMemberInformation[][]{removedArray, addedArray};
   }

   private boolean changed(DDMemberInformation oldMember, DDMemberInformation member) {
      DestinationImpl dImpl = (DestinationImpl)member.getDestination();
      DestinationImpl oldImpl = (DestinationImpl)oldMember.getDestination();
      if (!dImpl.getDestinationId().equals(oldImpl.getDestinationId())) {
         return true;
      } else if (dImpl.getDispatcherId() != null && !dImpl.getDispatcherId().equals(oldImpl.getDispatcherId())) {
         return true;
      } else if (!dImpl.getServerName().equals(oldImpl.getServerName())) {
         return true;
      } else {
         return dImpl.getJMSServerConfigName() != null && oldImpl.getJMSServerConfigName() != null && !dImpl.getJMSServerConfigName().equals(oldImpl.getJMSServerConfigName());
      }
   }

   private DDMemberInformation[] filterDDMembers(DestinationAvailabilityListenerWrapper listener, DDMemberInformation[] ddMemberInfo) {
      DDMemberInformation[] filteredDDMembers = null;

      assert listener != null;

      if (!this.isServer) {
         return ddMemberInfo;
      } else {
         int mode = listener.getMode();
         if (ddMemberInfo != null) {
            DDMemberInformation[] localDDMembers;
            if (mode == 0) {
               localDDMembers = this.getLocalServerDDMembers(ddMemberInfo);
               if (localDDMembers != null) {
                  filteredDDMembers = localDDMembers;
               } else {
                  filteredDDMembers = this.getRemoteClusterDDMembers(ddMemberInfo);
               }
            } else if (mode == 1) {
               localDDMembers = this.getLocalServerDDMembers(ddMemberInfo);
               if (localDDMembers != null) {
                  filteredDDMembers = localDDMembers;
               }
            } else {
               filteredDDMembers = ddMemberInfo;
            }
         }

         return filteredDDMembers;
      }
   }

   private int getDestinationTypeAsInt(DDMemberInformation ddMemberInfo) {
      boolean isDD = ddMemberInfo.isDD();
      String type = ddMemberInfo.getDDType();
      if (!type.equals("javax.jms.Queue") && !type.equals("javax.jms.Topic")) {
         throw new AssertionError("Unknown Destination Type");
      } else {
         boolean isQueue = type.equals("javax.jms.Queue");
         if (ddMemberInfo.getDestination() != null) {
            if (isDD) {
               if (isQueue) {
                  return 4;
               } else {
                  return ddMemberInfo.getForwardingPolicy() == 0 ? 6 : 5;
               }
            } else {
               return isQueue ? 0 : 1;
            }
         } else {
            return isQueue ? 2 : 3;
         }
      }
   }

   private DestinationDetail createDestinationDetailFromDDMemberInformation(DDMemberInformation ddMemberInfo) {
      int dType = this.getDestinationTypeAsInt(ddMemberInfo);
      String partitionName = ddMemberInfo.getPartitionName();
      String ddConfigName = ddMemberInfo.getDDConfigName();
      String ddJndiName = ddMemberInfo.getDDJNDIName();
      int destinationType = this.getDestinationTypeAsInt(ddMemberInfo);
      String memberConfigName = ddMemberInfo.getMemberName();
      String memberJndiName = ddMemberInfo.getDDMemberJndiName();
      String memberLocalJndiName = ddMemberInfo.getDDMemberLocalJndiName();
      String serverName = ddMemberInfo.getDDMemberServerName();
      String migratableTargetName = ddMemberInfo.getDDMemberMigratableTargetName();
      boolean isLocalWLSServer = this.isLocalWLSServer(ddMemberInfo);
      boolean isLocalCluster = this.isLocalCluster(ddMemberInfo);
      boolean isAdvancedTopicSupported = ddMemberInfo.isAdvancedTopicSupported();
      String createDestinationIdentifier;
      String jmsServerInstanceName;
      String jmsServerConfigName;
      String persistentStoreName;
      DestinationImpl destination;
      int deploymentMemberType;
      if (dType != 2 && dType != 3) {
         DestinationImpl destinationImpl = (DestinationImpl)ddMemberInfo.getDestination();
         createDestinationIdentifier = destinationImpl.getCreateDestinationArgument();
         jmsServerInstanceName = destinationImpl.isPre90() ? null : destinationImpl.getServerName();
         jmsServerConfigName = destinationImpl.isPre90() ? null : destinationImpl.getJMSServerConfigName();
         persistentStoreName = destinationImpl.getPersistentStoreName();
         destination = destinationImpl;
         deploymentMemberType = ddMemberInfo.getDeploymentDDMemberType();
         if (!PartitionUtils.isDomain(partitionName)) {
            ddConfigName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, ddConfigName);
            memberConfigName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberConfigName);
            memberJndiName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberJndiName);
            memberLocalJndiName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberLocalJndiName);
            createDestinationIdentifier = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, "!@/", createDestinationIdentifier);
            jmsServerInstanceName = PartitionUtils.stripDecoratedPartitionName(partitionName, jmsServerInstanceName);
            jmsServerConfigName = PartitionUtils.stripDecoratedPartitionName(partitionName, jmsServerConfigName);
         }
      } else {
         createDestinationIdentifier = null;
         jmsServerInstanceName = null;
         jmsServerConfigName = null;
         persistentStoreName = null;
         destination = null;
         deploymentMemberType = 4;
         if (!PartitionUtils.isDomain(partitionName)) {
            ddConfigName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, ddConfigName);
            memberConfigName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberConfigName);
            memberJndiName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberJndiName);
            memberLocalJndiName = PartitionUtils.stripDecoratedPartitionNamesFromCombinedName(partitionName, memberLocalJndiName);
         }
      }

      return new DestinationDetailImpl(ddConfigName, ddJndiName, destinationType, memberConfigName, memberJndiName, memberLocalJndiName, createDestinationIdentifier, jmsServerInstanceName, jmsServerConfigName, persistentStoreName, destination, serverName, migratableTargetName, isLocalWLSServer, isLocalCluster, isAdvancedTopicSupported, deploymentMemberType, partitionName);
   }

   private void printDDMemberInfo(DDMemberInformation[] ddMemberInfo) {
      if (ddMemberInfo != null) {
         for(int i = 0; i < ddMemberInfo.length; ++i) {
            System.out.println("member[" + i + "]:" + ddMemberInfo[i].toString());
         }

      }
   }

   static {
      try {
         Class.forName("com.bea.core.encryption.EncryptionService");
         coreEngine = true;
      } catch (Throwable var2) {
         coreEngine = false;
      }

      try {
         String compatibilityProp = System.getProperty("weblogic.jms.dahelper.compatibility", "false");
         IS_COMPATIBLE = Boolean.parseBoolean(compatibilityProp);
      } catch (RuntimeException var1) {
      }

   }

   private interface ContextFactory {
      Context getJNDIContext() throws NamingException;

      AbstractSubject getRegistrationSubject();

      void close();

      String getProviderURL();

      void refreshCtx() throws NamingException;

      void setSecurityHandle(CDSSecurityHandle var1);

      Object lookup(String var1) throws NamingException;

      AbstractSubject getRightJNDISubject();

      AbstractSubject getRightJMSSubject();

      AbstractSubject getOrigSubject();

      boolean isOrigSubDowngraded();
   }

   private final class RefreshWaitLock {
      int waiters;
      NamingException ne;

      private RefreshWaitLock() {
      }

      private synchronized void complete(NamingException exception) {
         this.ne = exception;
         if (this.waiters > 0) {
            this.notifyAll();
         }

      }

      private synchronized boolean waitUntilComplete() throws NamingException {
         if (this.waiters == 0) {
            return false;
         } else {
            ++this.waiters;

            try {
               this.wait();
            } catch (InterruptedException var2) {
            }

            --this.waiters;
            if (this.ne != null) {
               throw this.ne;
            } else {
               return true;
            }
         }
      }

      // $FF: synthetic method
      RefreshWaitLock(Object x1) {
         this();
      }
   }

   private class PropertiesContextFactory implements ContextFactory {
      final Hashtable properties;
      AbstractSubject origSubject;
      AbstractSubject registrationSubject;
      Context ctx;
      CDSSecurityHandle secHandle;
      boolean origSubDowngraded = false;
      RefreshWaitLock refreshWaitLock = JMSDestinationAvailabilityHelper.this.new RefreshWaitLock();
      InheritableThreadContext originalContext;

      PropertiesContextFactory(Hashtable properties) {
         this.properties = properties == null ? new Hashtable() : new Hashtable(properties);
         if (JMSDebug.JMSOBS.isDebugEnabled()) {
            JMSDebug.JMSOBS.debug("PropertiesContextFactory:constructor properties=" + JMSOBSHelper.filterProperties(properties));
         }

         if (this.properties.containsKey("java.naming.factory.initial")) {
            String jndiInitContextFactory = (String)this.properties.get("java.naming.factory.initial");
            if ("weblogic.jms.WLInitialContextFactory".equals(jndiInitContextFactory)) {
               this.properties.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
               if (JMSDebug.JMSOBS.isDebugEnabled()) {
                  JMSDebug.JMSOBS.debug("PropertiesContextFactory:constructor restrict OBS internally, replace weblogic.jms.WLInitialContextFactory with weblogic.jndi.WLInitialContextFactory this.properties=" + JMSOBSHelper.filterProperties(this.properties));
               }
            }
         }

         if (!this.properties.containsKey("weblogic.jndi.connectTimeout")) {
            if (JMSDebug.JMSOBS.isDebugEnabled()) {
               JMSDebug.JMSOBS.debug("PropertiesContextFactory:constructor set weblogic.jndi.connectTimeout to " + JMSDestinationAvailabilityHelper.JNDI_CONNECT_TIMEOUT);
            }

            this.properties.put("weblogic.jndi.connectTimeout", new Long((long)JMSDestinationAvailabilityHelper.JNDI_CONNECT_TIMEOUT));
         }

         if (!this.properties.containsKey("weblogic.jndi.responseReadTimeout")) {
            if (JMSDebug.JMSOBS.isDebugEnabled()) {
               JMSDebug.JMSOBS.debug("PropertiesContextFactory:constructor set weblogic.jndi.responseReadTimeout to " + JMSDestinationAvailabilityHelper.JNDI_RESPONSE_READ_TIMEOUT);
            }

            this.properties.put("weblogic.jndi.responseReadTimeout", new Long((long)JMSDestinationAvailabilityHelper.JNDI_RESPONSE_READ_TIMEOUT));
         }

         this.properties.put("weblogic.jndi.disableLoggingOfWarningMsg", "true");
         this.origSubject = CrossDomainSecurityManager.getCurrentSubject();
         if (this.origSubject == null || JMSDestinationAvailabilityHelper.this.isServer && CrossDomainSecurityManager.getCrossDomainSecurityUtil().isKernelIdentity(this.origSubject)) {
            this.origSubject = SubjectManager.getSubjectManager().getAnonymousSubject();
            this.origSubDowngraded = true;
         }

         this.registrationSubject = this.origSubject;
         this.originalContext = InheritableThreadContext.getContext();
      }

      public AbstractSubject getOrigSubject() {
         return this.origSubject;
      }

      public boolean isOrigSubDowngraded() {
         return this.origSubDowngraded;
      }

      public Context getJNDIContext() throws NamingException {
         this.refreshCtx();
         synchronized(this) {
            return this.ctx;
         }
      }

      public void refreshCtx() throws NamingException {
         ClassLoader saved = this.originalContext.pushMultiThread();

         try {
            if (this.refreshWaitLock.waitUntilComplete()) {
               return;
            }

            NamingException gotException = null;
            Context newCtx = null;
            AbstractSubject regSub = null;

            try {
               newCtx = this.properties == null ? new InitialContext() : new InitialContext(this.properties);
               regSub = CrossDomainSecurityManager.getCurrentSubject();
            } catch (NamingException var15) {
               gotException = var15;
            } catch (Throwable var16) {
               gotException = new NamingException(var16.getMessage());
               gotException.setRootCause(var16);
            }

            synchronized(this) {
               if (this.ctx != null) {
                  try {
                     this.ctx.close();
                  } catch (NamingException var14) {
                  }
               }

               this.ctx = newCtx;
               if (gotException == null && !regSub.equals(SubjectManager.getSubjectManager().getAnonymousSubject())) {
                  this.registrationSubject = regSub;
               }
            }

            this.refreshWaitLock.complete(gotException);
            if (gotException != null) {
               throw gotException;
            }
         } finally {
            this.originalContext.popMultiThread(saved);
         }

      }

      public synchronized AbstractSubject getRegistrationSubject() {
         return this.registrationSubject;
      }

      public synchronized void close() {
         try {
            if (this.ctx != null) {
               this.ctx.close();
            }

            if (this.secHandle != null) {
               this.secHandle.close();
            }
         } catch (NamingException var2) {
         }

      }

      public String getProviderURL() {
         return this.properties == null ? null : (String)this.properties.get("java.naming.provider.url");
      }

      public void setSecurityHandle(CDSSecurityHandle secHandle) {
         this.secHandle = secHandle;
      }

      private boolean hasRegistrationCredentials() {
         if (this.properties == null) {
            return false;
         } else {
            String value = (String)this.properties.get("java.naming.security.principal");
            return value != null;
         }
      }

      public AbstractSubject getRightJNDISubject() throws IllegalStateException {
         if (this.hasRegistrationCredentials()) {
            return this.getRegistrationSubject();
         } else {
            try {
               if (JMSDestinationAvailabilityHelper.this.isServer && this.secHandle.isRemoteDomain()) {
                  return SubjectManager.getSubjectManager().getAnonymousSubject();
               }
            } catch (IllegalStateException var2) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("The security handle is not ready yet, listener is not connected");
               }

               throw var2;
            }

            return this.origSubject;
         }
      }

      public AbstractSubject getRightJMSSubject() {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Getting right jms subject: properties= " + this.properties);
         }

         AbstractSubject foreignSubject = null;

         try {
            foreignSubject = this.getForeignSubject();
         } catch (IllegalStateException var3) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("The listener has failed and is trying to reconnect, we cannot get the right subject, use anonymous");
            }

            if (!this.secHandle.isReady()) {
               return SubjectManager.getSubjectManager().getAnonymousSubject();
            }
         }

         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("foreign subject= " + foreignSubject);
         }

         if (foreignSubject != null) {
            return foreignSubject;
         } else if (!this.hasRegistrationCredentials()) {
            return this.origSubject;
         } else {
            return JMSDestinationAvailabilityHelper.this.isServer && (this.secHandle.isRemoteDomain() || CrossDomainSecurityManager.getCrossDomainSecurityUtil().isKernelIdentity(this.registrationSubject)) ? SubjectManager.getSubjectManager().getAnonymousSubject() : this.registrationSubject;
         }
      }

      private AbstractSubject getForeignSubject() {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Getting foreign subject isforeign= " + this.secHandle.isForeignJMSServer());
         }

         return this.secHandle.isForeignJMSServer() ? this.secHandle.getForeignSubject() : null;
      }

      public Object lookup(final String jndiName) throws NamingException {
         try {
            AbstractSubject subject = this.getRightJNDISubject();
            return CrossDomainSecurityManager.runAs(subject, new PrivilegedExceptionAction() {
               public Object run() throws NamingException {
                  try {
                     return PropertiesContextFactory.this.ctx.lookup(jndiName);
                  } catch (NamingException var2) {
                     if (var2 instanceof NameNotFoundException) {
                        throw var2;
                     } else {
                        PropertiesContextFactory.this.refreshCtx();
                        return PropertiesContextFactory.this.ctx.lookup(jndiName);
                     }
                  }
               }
            });
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof NamingException) {
               throw (NamingException)e;
            } else {
               NamingException nex = new NamingException(e.getMessage());
               throw nex;
            }
         } catch (IllegalStateException var6) {
            NamingException ne = new NamingException(var6.getMessage());
            throw ne;
         }
      }
   }

   private final class DestinationAvailabilityListenerWrapper implements DDMembershipChangeListener, RegistrationHandle {
      private ContextFactory ctxFactory;
      private Context envContext;
      private String destJNDIName;
      private DestinationAvailabilityListener listener;
      private int mode;
      InheritableThreadContext originalContext;
      private ComponentInvocationContext cic = null;

      public DestinationAvailabilityListenerWrapper(ContextFactory ctxFactory, String destJNDIName, DestinationAvailabilityListener listener, int mode, Context envContext) {
         this.ctxFactory = ctxFactory;
         this.destJNDIName = destJNDIName;
         this.listener = listener;
         this.mode = mode;
         this.originalContext = InheritableThreadContext.getContext();
         this.envContext = envContext;
         if (KernelStatus.isServer()) {
            this.cic = JMSCICHelper.getCurrentCIC();
         }

      }

      public String getDDJNDIName() {
         return this.destJNDIName;
      }

      public DestinationAvailabilityListener getDestinationAvailabilityListener() {
         return this.listener;
      }

      public int getMode() {
         return this.mode;
      }

      public String getDestinationName() {
         return this.destJNDIName;
      }

      public String getProviderURL() {
         return this.ctxFactory.getProviderURL();
      }

      public InitialContext getInitialContext() throws NamingException {
         return (InitialContext)this.ctxFactory.getJNDIContext();
      }

      public Context getEnvContext() {
         return this.envContext;
      }

      public AbstractSubject getSubject() {
         return this.ctxFactory.getRegistrationSubject();
      }

      public void onDDMembershipChange(DDMembershipChangeEventImpl evt) {
         if (this.listener != null) {
            DDMemberInformation[] removedDDMemberInfo = JMSDestinationAvailabilityHelper.this.filterDDMembers(this, evt.getRemovedDDMemberInformation());
            DDMemberInformation[] addedDDMemberInfo = JMSDestinationAvailabilityHelper.this.filterDDMembers(this, evt.getAddedDDMemberInformation());
            if (!JMSDestinationAvailabilityHelper.IS_COMPATIBLE && removedDDMemberInfo != null && addedDDMemberInfo != null) {
               DDMemberInformation[][] DDMemberInfo = JMSDestinationAvailabilityHelper.this.filterDDMembers(removedDDMemberInfo, addedDDMemberInfo);
               removedDDMemberInfo = DDMemberInfo[0];
               addedDDMemberInfo = DDMemberInfo[1];
            }

            final DestinationDetailImpl[] addedDestinationsDetail;
            int i;
            int numberOfAddedDDMembers;
            if (removedDDMemberInfo != null) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Valid Removed DDMembers info:");
                  JMSDestinationAvailabilityHelper.this.printDDMemberInfo(removedDDMemberInfo);
               }

               numberOfAddedDDMembers = removedDDMemberInfo.length;
               addedDestinationsDetail = new DestinationDetailImpl[numberOfAddedDDMembers];

               for(i = 0; i < numberOfAddedDDMembers; ++i) {
                  addedDestinationsDetail[i] = JMSDestinationAvailabilityHelper.this.createDestinationDetailFromDDMemberInformation(removedDDMemberInfo[i]);
               }

               this.callOutListener(new PrivilegedAction() {
                  public Object run() {
                     DestinationAvailabilityListenerWrapper.this.listener.onDestinationsUnavailable(DestinationAvailabilityListenerWrapper.this.destJNDIName, Arrays.asList(addedDestinationsDetail));
                     return null;
                  }
               });
            }

            if (addedDDMemberInfo != null) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Valid Added DDMembers info:");
                  JMSDestinationAvailabilityHelper.this.printDDMemberInfo(addedDDMemberInfo);
               }

               numberOfAddedDDMembers = addedDDMemberInfo.length;
               addedDestinationsDetail = new DestinationDetailImpl[numberOfAddedDDMembers];

               for(i = 0; i < numberOfAddedDDMembers; ++i) {
                  addedDestinationsDetail[i] = JMSDestinationAvailabilityHelper.this.createDestinationDetailFromDDMemberInformation(addedDDMemberInfo[i]);
               }

               this.callOutListener(new PrivilegedAction() {
                  public Object run() {
                     DestinationAvailabilityListenerWrapper.this.listener.onDestinationsAvailable(DestinationAvailabilityListenerWrapper.this.destJNDIName, Arrays.asList(addedDestinationsDetail));
                     return null;
                  }
               });
            }

         }
      }

      private void callOutListener(PrivilegedAction action) {
         ClassLoader saved = this.originalContext.pushMultiThread();

         try {
            if (this.ctxFactory.isOrigSubDowngraded()) {
               CrossDomainSecurityManager.runAs(this.ctxFactory.getOrigSubject(), action);
            } else {
               action.run();
            }
         } finally {
            this.originalContext.popMultiThread(saved);
         }

      }

      public void unregister() {
         synchronized(this) {
            if (this.listener != null) {
               CDS.getCDS().unregisterDDMembershipChangeListener(this);
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Successfully unregistered the listener " + this.listener + " for destJNDIName=" + this.destJNDIName);
               }

               this.listener = null;
               this.ctxFactory.close();
            }
         }
      }

      public void onFailure(final String destJNDIName, final Exception ex) {
         if (this.listener != null) {
            this.callOutListener(new PrivilegedAction() {
               public Object run() {
                  DestinationAvailabilityListenerWrapper.this.listener.onFailure(destJNDIName, ex);
                  return null;
               }
            });
         }
      }

      public final ConnectionFactory lookupConnectionFactory(String jndiName) throws NamingException {
         ClassLoader saved = this.originalContext.pushMultiThread();

         ConnectionFactory var3;
         try {
            var3 = (ConnectionFactory)this.ctxFactory.lookup(jndiName);
         } finally {
            this.originalContext.popMultiThread(saved);
         }

         return var3;
      }

      public final Destination lookupDestination(String jndiName) throws NamingException {
         ClassLoader saved = this.originalContext.pushMultiThread();

         Destination var3;
         try {
            var3 = (Destination)this.ctxFactory.lookup(jndiName);
         } finally {
            this.originalContext.popMultiThread(saved);
         }

         return var3;
      }

      public final Object runAs(PrivilegedExceptionAction action) throws PrivilegedActionException {
         ClassLoader saved = this.originalContext.pushMultiThread();

         Object var5;
         try {
            ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
            Throwable var4 = null;

            try {
               var5 = CrossDomainSecurityManager.runAs(this.ctxFactory.getRightJMSSubject(), action);
            } catch (Throwable var21) {
               var5 = var21;
               var4 = var21;
               throw var21;
            } finally {
               if (mic != null) {
                  if (var4 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var20) {
                        var4.addSuppressed(var20);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } finally {
            this.originalContext.popMultiThread(saved);
         }

         return var5;
      }
   }
}
