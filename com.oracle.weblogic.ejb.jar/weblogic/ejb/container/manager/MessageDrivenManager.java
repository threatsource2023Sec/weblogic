package weblogic.ejb.container.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.naming.Context;
import javax.transaction.Transaction;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.application.ApplicationAccess;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.cluster.migration.Migratable;
import weblogic.cluster.migration.MigratableMDB;
import weblogic.cluster.migration.MigrationException;
import weblogic.cluster.migration.MigrationManager;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.interfaces.MessageDrivenManagerIntf;
import weblogic.ejb.container.interfaces.PoolIntf;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.ejb.container.internal.EJBRuntimeUtils;
import weblogic.ejb.container.internal.InvocationContextStack;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.JCABindingManager;
import weblogic.ejb.container.internal.JMSConnectionPoller;
import weblogic.ejb.container.internal.LifecycleInvocationContextImpl;
import weblogic.ejb.container.internal.MDConnectionManager;
import weblogic.ejb.container.internal.MessageDrivenEJBContextImpl;
import weblogic.ejb.container.internal.SecurityHelper;
import weblogic.ejb.container.monitoring.MessageDrivenEJBRuntimeMBeanImpl;
import weblogic.ejb.container.pool.MessageDrivenPool;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.jms.extensions.DestinationDetail;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.EJBTimerRuntimeMBean;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StackTraceUtilsClient;

public final class MessageDrivenManager extends BaseEJBManager implements MessageDrivenManagerIntf, Migratable, MigratableMDB {
   private static final DebugCategory DEBUG_APP_VERSION;
   private static final AuthenticatedSubject KERNEL_ID;
   private MessageDrivenBeanInfo info;
   private PoolIntf pool;
   private MessageDrivenEJBRuntimeMBeanImpl runtimeMBean;
   private DestinationDetail destinationInfo;
   private MessageDrivenContext messageDrivenContext;
   private Method createMethod;
   private boolean started;
   private String destinationName;
   private String uniqueGlobalId;
   private String jmsClientId;
   private String subscriptionName;
   private String messageSelector;
   private boolean deleteDurableSubscription;
   private int topicMessagesDistributionMode;
   private String connectionFactoryJNDIName;
   private String providerURL;
   private TargetMBean targetMBean;
   private MDConnectionManager mdConnManager;
   private AuthenticatedSubject fileDesc;
   private AuthenticatedSubject filePtr;
   private AuthenticatedSubject fileSegment;
   private volatile boolean disableCheckMDBS_TO_SUSPEND_ON_STARTUP = false;
   private static final String[] TAGS;
   private static final List MDBS_TO_SUSPEND_ON_STARTUP;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -1066320072041190478L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.manager.MessageDrivenManager");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public MessageDrivenManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remote, BaseEJBLocalHomeIntf local, BeanInfo bi, Context envContext, ISecurityHelper sh) throws WLDeploymentException {
      this.info = (MessageDrivenBeanInfo)bi;
      super.setup(remote, local, this.info, envContext, sh);
      if (!this.info.getIsJMSBased()) {
         this.destinationName = this.info.getResourceAdapterJndiName();
      }

      this.beanClass = this.info.getBeanClassToInstantiate();
      if (this.info.getRunAsPrincipalName() != null || this.info.getCreateAsPrincipalName() != null || this.info.getRemoveAsPrincipalName() != null) {
         try {
            if (this.info.getRunAsPrincipalName() != null) {
               this.fileDesc = sh.getSubjectForPrincipal(this.info.getRunAsPrincipalName());
            }

            if (this.info.getCreateAsPrincipalName() != null) {
               this.filePtr = sh.getSubjectForPrincipal(this.info.getCreateAsPrincipalName());
            }

            if (this.info.getRemoveAsPrincipalName() != null) {
               this.fileSegment = sh.getSubjectForPrincipal(this.info.getRemoveAsPrincipalName());
            }
         } catch (PrincipalNotFoundException var7) {
            throw new WLDeploymentException(var7.getMessage(), var7);
         }
      }

      this.setCreateMethod();
      this.suspendActiveVersion();
      this.initialize();
      this.messageDrivenContext = new MessageDrivenEJBContextImpl(this);
   }

   public void setup(MessageDrivenBeanInfo info, Context environmentContext, String destinationName, TargetMBean targetMBean, DestinationDetail destDetail, ISecurityHelper sh) throws WLDeploymentException {
      this.destinationName = destinationName;
      this.targetMBean = targetMBean;
      this.destinationInfo = destDetail;
      this.setup((BaseEJBRemoteHomeIntf)null, (BaseEJBLocalHomeIntf)null, info, environmentContext, sh);
   }

   protected void createEJBTimerManager() {
      if (this.info.getTimerManagerFactory() != null) {
         this.timerManager = this.info.getTimerManagerFactory().createEJBTimerManager(this);
      }

   }

   public MessageDrivenContext getMessageDrivenContext() {
      return this.messageDrivenContext;
   }

   public PoolIntf getPool() {
      return this.pool;
   }

   public void doEjbRemove(Object bean) {
      boolean isSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.fileSegment, this.fileDesc);
      EJBContextManager.pushEjbContext(this.messageDrivenContext);
      InvocationContextStack.push(new LifecycleInvocationContextImpl());

      try {
         if (MessageDrivenBean.class.isAssignableFrom(bean.getClass())) {
            ((MessageDrivenBean)bean).ejbRemove();
         } else {
            this.ejbComponentCreator.invokePreDestroy(bean, this.info.getEJBName());
         }
      } finally {
         InvocationContextStack.pop();
         EJBContextManager.popEjbContext();
         if (isSet) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

      }

   }

   public Object preInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[2];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         var3.resetPostBegin();
      }

      Object var6;
      try {
         super.preInvoke();
         int txTimeoutSeconds = wrap.getMethodDescriptor().getTxTimeoutSeconds();
         if (debugLogger.isDebugEnabled()) {
            debug("In preInvoke with timeout in seconds:" + txTimeoutSeconds + " on manager: " + this);
         }

         var6 = this.pool.getBean((long)txTimeoutSeconds * 1000L);
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            var3.ret = null;
            InstrumentationSupport.process(var3);
         }

         throw var5;
      }

      if (var3 != null) {
         var3.ret = var6;
         InstrumentationSupport.process(var3);
      }

      return var6;
   }

   public void postInvoke(InvocationWrapper wrap) throws InternalException {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = new Object[2];
            Object[] var10000 = var2.args;
            var10000[0] = this;
            var10000[1] = wrap;
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.process(var2);
         var2.resetPostBegin();
      }

      if (debugLogger.isDebugEnabled()) {
         debug("In postInvoke on " + this);
      }

      if (wrap.isLocal()) {
         this.pool.releaseBean(wrap.getBean());
      }

   }

   public void destroyInstance(InvocationWrapper wrap, Throwable ee) {
      if (debugLogger.isDebugEnabled()) {
         debug("In destroyInstance for manager: " + this);
      }

      this.ejbComponentCreator.destroyBean(wrap.getBean());
      this.pool.destroyBean((Object)null);
   }

   public void beforeCompletion(InvocationWrapper wrap) {
   }

   public void beforeCompletion(Collection primaryKeys, Transaction tx) {
   }

   public void afterCompletion(InvocationWrapper wrap) {
   }

   public void afterCompletion(Collection pks, Transaction tx, int result, Object ignore) {
   }

   public Object createBean() throws InternalException {
      if (null == this.createMethod) {
         this.setCreateMethod();
      }

      MethodInvocationHelper.pushMethodObject(this.beanInfo);
      boolean isSet = SecurityHelper.pushSpecificRunAsMaybe(KERNEL_ID, this.filePtr, this.fileDesc);
      this.pushEnvironment();
      EJBContextManager.pushEjbContext(this.messageDrivenContext);
      Object result = null;

      try {
         result = this.allocateBean();
         if (MessageDrivenBean.class.isAssignableFrom(result.getClass())) {
            ((MessageDrivenBean)result).setMessageDrivenContext(this.messageDrivenContext);
         }

         Thread currentThread = Thread.currentThread();
         ClassLoader clSave = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(this.info.getModuleClassLoader());
         InvocationContextStack.push(new LifecycleInvocationContextImpl());

         try {
            if (this.info.isEJB30() && this.createMethod == null) {
               this.ejbComponentCreator.invokePostConstruct(result, this.info.getEJBName());
            } else if (this.createMethod != null) {
               this.createMethod.invoke(result, (Object[])null);
            }
         } finally {
            InvocationContextStack.pop();
            if (clSave != null) {
               currentThread.setContextClassLoader(clSave);
            }

         }
      } catch (InvocationTargetException | IllegalAccessException var14) {
         EJBLogger.logStackTrace(var14);
         EJBRuntimeUtils.throwInternalException("Error invoking bean's constructor: ", var14);
      } finally {
         EJBContextManager.popEjbContext();
         this.popEnvironment();
         if (isSet) {
            SecurityHelper.popRunAsSubject(KERNEL_ID);
         }

         MethodInvocationHelper.popMethodObject(this.beanInfo);
      }

      return result;
   }

   public EJBObject remoteCreate(InvocationWrapper iw, Method create, Method postCreate, Object[] args) {
      throw new UnsupportedOperationException("Message Driven Beans cannot allocate EO");
   }

   public EJBLocalObject localCreate(InvocationWrapper iw, Method create, Method postCreate, Object[] args) {
      throw new UnsupportedOperationException("Message Driven Beans cannot allocate ELO");
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      this.remove();
   }

   public void beanImplClassChangeNotification() {
      this.beanClass = this.info.getBeanClassToInstantiate();
      this.pool.reset();
      this.setCreateMethod();
   }

   public void releaseBean(InvocationWrapper wrap) {
      this.pool.releaseBean(wrap.getBean());
   }

   private void setCreateMethod() {
      if (!this.info.isEJB30()) {
         try {
            this.createMethod = this.beanClass.getMethod("ejbCreate", (Class[])null);
         } catch (NoSuchMethodException var2) {
            throw new AssertionError("Could not find ejbCreate()" + StackTraceUtilsClient.throwable2StackTrace(var2));
         }
      }
   }

   public void reInitializePool() {
      this.pool.reInitializePool();
   }

   public String getDestinationName() {
      return this.destinationName;
   }

   public String getDDMemberName() {
      return this.destinationInfo == null ? this.destinationName : this.destinationInfo.getMemberConfigName();
   }

   public String getSubscriberName() {
      return this.subscriptionName;
   }

   public String getUniqueGlobalId() {
      return this.uniqueGlobalId;
   }

   public String getJMSClientId() {
      return this.jmsClientId;
   }

   public String getMessageSelector() {
      return this.messageSelector;
   }

   public int getTopicMessagesDistributionMode() {
      return this.topicMessagesDistributionMode;
   }

   public TargetMBean getTargetMBean() {
      return this.targetMBean;
   }

   public String getName() {
      return this.info.getEJBName();
   }

   private String getRuntimeMBeanName() {
      return !this.info.getIsJMSBased() ? this.info.getEJBName() : this.info.getEJBName() + "_" + (this.isNonDDMD() ? this.destinationName : this.getDDMemberName());
   }

   private void initialize() throws WLDeploymentException {
      this.resetConnValues();
      if (this.runtimeMBean == null) {
         try {
            this.runtimeMBean = new MessageDrivenEJBRuntimeMBeanImpl(this.getRuntimeMBeanName(), this.info, this.info.getDeploymentInfo().getApplicationName(), this.getEJBComponentRuntime(), this.destinationName, this.getTimerManager());
            this.setEJBRuntimeMBean(this.runtimeMBean);
            this.addEJBRuntimeMBean(this.runtimeMBean);
         } catch (ManagementException var3) {
            Loggable l = EJBLogger.logFailedToCreateRuntimeMBeanLoggable();
            throw new WLDeploymentException(l.getMessageText(), var3);
         }
      }

      this.perhapsSetupTimerManager(this.runtimeMBean.getTimerRuntime());
      this.pool = new MessageDrivenPool(this, this.info, this.runtimeMBean.getPoolRuntime());
      this.mdConnManager = (MDConnectionManager)(this.info.getIsJMSBased() ? new JMSConnectionPoller(this.info, this.runtimeMBean) : new JCABindingManager(this.info, this.runtimeMBean));
   }

   private void unInitialize() {
      try {
         if (this.runtimeMBean != null) {
            this.runtimeMBean.unregister();
            this.removeEJBRuntimeMBean(this.runtimeMBean);
         }
      } catch (ManagementException var2) {
         if (debugLogger.isDebugEnabled()) {
            debug("Error occured unregistering MBean: ", var2);
         }
      }

      this.runtimeMBean = null;
   }

   private boolean runtimeValidation() {
      String msg;
      if (this.runtimeMBean.statusAsString(0).equals(this.runtimeMBean.getMDBStatus())) {
         msg = "The ejb " + this.info.getDisplayName() + " could not be activated because the destination " + this.destinationName + " is not available now!";
         this.runtimeMBean.setLastException(new WLDeploymentException(msg));
         return false;
      } else if (this.destinationInfo == null) {
         this.updateStatus(4);
         this.runtimeMBean.setLastException(new AssertionError("Illegal status for MDB " + this.info.getDisplayName()));
         return false;
      } else {
         if (this.destinationInfo.getType() == 6) {
            if (this.getTopicMessagesDistributionMode() == 0) {
               this.updateStatus(4);
               msg = EJBLogger.logIllegalPermutationOnPDTAndComp(this.info.getDisplayName());
               this.runtimeMBean.setLastException(new WLDeploymentException(msg));
               return false;
            }
         } else if ((this.destinationInfo.getType() == 4 || this.destinationInfo.getType() == 2 || this.destinationInfo.getType() == 0) && this.getTopicMessagesDistributionMode() > 0) {
            Loggable l = EJBLogger.logInvalidConfigurationForTopicMessagesDistributionModeLoggable(this.info.getDisplayName());
            this.runtimeMBean.setMDBStatus(this.runtimeMBean.statusAsString(4) + ":" + l.getMessage());
            return false;
         }

         return true;
      }
   }

   private static List getMDBsToSuspendOnDeployment() {
      String property = System.getProperty("weblogic.mdbs.suspendConnectionOnStart");
      if (property == null) {
         return Collections.emptyList();
      } else {
         String[] mdbElements = property.trim().split(" ");
         List mdbList = new ArrayList(mdbElements.length);
         String[] var3 = mdbElements;
         int var4 = mdbElements.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String mdbElement = var3[var5];
            mdbElement = mdbElement.trim();
            Map m = new HashMap(5);

            for(int i = 0; i < TAGS.length; ++i) {
               m.put(TAGS[i], "*");
            }

            if (!mdbElement.isEmpty()) {
               Map indexToTag = new HashMap();
               int[] indexes = new int[5];

               int i;
               String value;
               for(i = 0; i < TAGS.length; ++i) {
                  value = TAGS[i];
                  if (mdbElement.contains(value)) {
                     indexes[i] = mdbElement.indexOf(value);
                     indexToTag.put(indexes[i], value);
                  } else {
                     indexes[i] = -1;
                     m.put(value, "*");
                  }
               }

               Arrays.sort(indexes);

               for(i = 0; i < indexes.length; ++i) {
                  if (indexes[i] != -1) {
                     if (i == indexes.length - 1) {
                        value = mdbElement.substring(indexes[i] + ((String)indexToTag.get(indexes[i])).length()).trim();
                     } else {
                        value = mdbElement.substring(indexes[i] + ((String)indexToTag.get(indexes[i])).length(), indexes[i + 1]).trim();
                     }

                     if (value.isEmpty()) {
                        m.put(indexToTag.get(indexes[i]), "*");
                     } else {
                        m.put(indexToTag.get(indexes[i]), value);
                     }
                  }
               }

               mdbList.add(m);
            }
         }

         return mdbList;
      }
   }

   public boolean isConnectionSuspendOnStartPropertySet() {
      return !MDBS_TO_SUSPEND_ON_STARTUP.isEmpty();
   }

   public void disableCheckMDBS_TO_SUSPEND_ON_STARTUP() {
      this.disableCheckMDBS_TO_SUSPEND_ON_STARTUP = true;
   }

   public boolean shouldConnectionSuspendOnStart() {
      if (this.info.shouldManagerStartSuspended()) {
         if (debugLogger.isDebugEnabled()) {
            debug("shouldConnectionSuspendOnStart(): info.shouldManagerStartSuspended() = true");
         }

         return true;
      } else if (!this.disableCheckMDBS_TO_SUSPEND_ON_STARTUP && !MDBS_TO_SUSPEND_ON_STARTUP.isEmpty()) {
         String serverName = ((RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class)).getServerName();
         if (debugLogger.isDebugEnabled()) {
            debug("shouldConnectionSuspendOnStart(): \nserverName=" + serverName + "\n applicationName=" + this.info.getDeploymentInfo().getApplicationName() + "\n moduleName=" + this.info.getDeploymentInfo().getModuleName() + "\n ejbName=" + this.info.getEJBName() + "\n Exporting MDBS_TO_SUSPEND_ON_STARTUP map:\n" + MDBS_TO_SUSPEND_ON_STARTUP);
         }

         Iterator var2 = MDBS_TO_SUSPEND_ON_STARTUP.iterator();

         Map mdb;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var2.hasNext()) {
                           return false;
                        }

                        mdb = (Map)var2.next();
                     } while(!((String)mdb.get(TAGS[0])).equalsIgnoreCase(serverName) && !((String)mdb.get(TAGS[0])).equals("*"));
                  } while(!((String)mdb.get(TAGS[1])).equalsIgnoreCase(this.getPartitionName()) && !((String)mdb.get(TAGS[1])).equals("*"));
               } while(!((String)mdb.get(TAGS[2])).equalsIgnoreCase(this.info.getDeploymentInfo().getApplicationName()) && !((String)mdb.get(TAGS[2])).equals("*"));
            } while(!((String)mdb.get(TAGS[3])).equalsIgnoreCase(this.info.getDeploymentInfo().getModuleName()) && !((String)mdb.get(TAGS[3])).equals("*"));
         } while(!((String)mdb.get(TAGS[4])).equalsIgnoreCase(this.info.getEJBName()) && !((String)mdb.get(TAGS[4])).equals("*") && !this.isEJBNameEndsWithSuffix(this.info.getEJBName(), (String)mdb.get(TAGS[4])));

         return true;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("shouldConnectionSuspendOnStart() returns 'false' : disableCheckMDBS_TO_SUSPEND_ON_STARTUP = " + this.disableCheckMDBS_TO_SUSPEND_ON_STARTUP + " or MDBS_TO_SUSPEND_ON_STARTUP.isEmpty() = " + MDBS_TO_SUSPEND_ON_STARTUP.isEmpty());
         }

         return false;
      }
   }

   private boolean isEJBNameEndsWithSuffix(String ejbName, String suffix) {
      boolean hasMDBNameSuffix;
      for(hasMDBNameSuffix = false; suffix.startsWith("*"); suffix = suffix.substring(1)) {
         hasMDBNameSuffix = true;
      }

      return hasMDBNameSuffix && ejbName.endsWith(suffix);
   }

   public synchronized void start() throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("start");
      }

      if (!this.info.getIsJMSBased() && this.info.getIsInactive()) {
         this.updateStatus(5);
      } else if (this.info.getIsJMSBased() && !this.runtimeValidation()) {
         this.runtimeMBean.setJMSConnectionAlive(false);
      } else if (!this.started) {
         this.pool.createInitialBeans();
         if (this.mdConnManager != null) {
            this.mdConnManager.startConnectionPolling();
            this.started = true;
         }
      }

   }

   public void stop() {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("stop");
      }

      if (this.runtimeMBean != null && this.mdConnManager != null && this.pool != null) {
         this.mdConnManager.cancelConnectionPolling();
         this.pool.cleanup();
         this.started = false;
      }

      if (this.runtimeMBean != null) {
         this.updateStatus(5);
      }

   }

   public void resume() {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("resume");
      }

      this.mdConnManager.resume(false);
   }

   public void suspend() {
      String oldstatus = this.updateStatus(6);
      boolean done = false;

      try {
         if (debugLogger.isDebugEnabled()) {
            this.debugOperationStart("suspend, current status=" + oldstatus);
         }

         this.mdConnManager.suspend(false);
         done = true;
      } finally {
         if (!done) {
            this.restoreStatusIf(oldstatus, 6);
         }

      }

   }

   public void undeploy() {
      String oldstatus = this.updateStatus(7);

      try {
         if (debugLogger.isDebugEnabled()) {
            this.debugOperationStart("undeploy, current status=" + oldstatus);
         }

         super.undeploy();
         if (this.isNonDDMD() && this.targetMBean instanceof MigratableTargetMBean) {
            try {
               ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
               MigrationManager mm = (MigrationManager)sl.getService(MigrationManager.class, new Annotation[0]);
               mm.unregister(this, (MigratableTargetMBean)this.targetMBean);
            } catch (MultiException | IllegalStateException var7) {
               EJBLogger.logErrorUndeploying(this.destinationName, new MigrationException(var7));
            }
         }

         this.info.removeManager(this);
         if (this.mdConnManager != null) {
            this.mdConnManager.cancelConnectionPolling();
         }

         if (this.pool != null) {
            this.pool.cleanup();
         }

         this.unInitialize();
         this.setIsDeployed(false);
         this.updateStatus(8);
      } finally {
         this.restoreStatusIf(oldstatus, 7);
      }

   }

   protected void undeployTimerManager() {
      if (this.info.getTimerManagerFactory() != null) {
         this.info.getTimerManagerFactory().undeploy(this);
      }

   }

   public void remove() {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("remove");
      }

      if (this.mdConnManager != null) {
         this.mdConnManager.deleteDurableSubscriber(this.getJMSClientId());
      }

      this.mdConnManager = null;
   }

   public void onRAUndeploy() {
      if (this.mdConnManager != null) {
         ((JCABindingManager)this.mdConnManager).onRAUndeploy();
      }

   }

   public void resetMessageConsumer(boolean unsubscribe) {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("resetMessageConsumer");
      }

      if (this.info.isDurableSubscriber() && unsubscribe) {
         this.mdConnManager.deleteDurableSubscriber(this.getJMSClientId());
      }

      this.resetConnValues();

      try {
         this.mdConnManager.startConnectionPolling();
      } catch (WLDeploymentException var3) {
         debug("resetMessageConsumer failed to start connection polling", var3);
      }

   }

   public void migratableInitialize() throws MigrationException {
   }

   public void migratableActivate() throws MigrationException {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("migratableActivate");
      }

      try {
         this.start();
      } catch (WLDeploymentException var2) {
         throw new MigrationException(var2.getErrorMessage());
      }
   }

   public void migratableDeactivate() throws MigrationException {
      if (debugLogger.isDebugEnabled()) {
         this.debugOperationStart("migratableDeactivate");
      }

      this.stop();
   }

   private void suspendActiveVersion() {
      String appId = ApplicationAccess.getApplicationAccess().getCurrentApplicationName();
      String curVersion = ApplicationVersionUtils.getVersionId(appId);
      if (curVersion != null) {
         String appName = ApplicationVersionUtils.getApplicationName(appId);
         ApplicationRuntimeMBean activeApp = ApplicationVersionUtils.getActiveApplicationRuntime(appName);
         if (activeApp != null) {
            String activeVersion = activeApp.getApplicationVersion();
            if (DEBUG_APP_VERSION.isEnabled()) {
               Debug.say("Active version of app: " + activeApp.getName() + activeVersion);
            }

            if (!this.isAppScopedJMSDestination()) {
               ComponentRuntimeMBean[] var6 = activeApp.getComponentRuntimes();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  ComponentRuntimeMBean comp = var6[var8];
                  if (this.isSameEJBComponent(comp, activeVersion, curVersion)) {
                     EJBRuntimeMBean[] var10 = ((EJBComponentRuntimeMBean)comp).getEJBRuntimes();
                     int var11 = var10.length;

                     for(int var12 = 0; var12 < var11; ++var12) {
                        EJBRuntimeMBean ejb = var10[var12];
                        if (this.isSameMDB(ejb, activeVersion, curVersion)) {
                           MessageDrivenEJBRuntimeMBean mdb = (MessageDrivenEJBRuntimeMBean)ejb;
                           if (!this.info.isDestinationQueue() && !this.info.isDurableSubscriber()) {
                              EJBLogger.logSuspendNonDurableSubscriber(this.info.getEJBName(), ApplicationVersionUtils.getDisplayName(appName, activeVersion), this.info.getDestinationName(), curVersion);
                           } else {
                              EJBLogger.logSuspendMDB(this.info.getEJBName(), ApplicationVersionUtils.getDisplayName(appName, activeVersion), this.info.getDestinationName(), curVersion);
                           }

                           try {
                              mdb.suspend();
                           } catch (Exception var16) {
                              EJBLogger.logStackTrace(var16);
                           }
                        }
                     }
                  }
               }

            }
         }
      }
   }

   private boolean isAppScopedJMSDestination() {
      String destResourceLink = this.info.getDestinationResourceLink();
      return destResourceLink != null && destResourceLink.length() > 0;
   }

   private boolean isSameEJBComponent(ComponentRuntimeMBean comp, String activeVersion, String curVersion) {
      if (!(comp instanceof EJBComponentRuntimeMBean)) {
         return false;
      } else {
         EJBRuntimeHolder curRT = this.getEJBComponentRuntime();
         return curRT != null && ApplicationVersionUtils.isSameComponent(comp.getName(), activeVersion, curRT.getName(), curVersion);
      }
   }

   private boolean isSameMDB(EJBRuntimeMBean ejb, String activeVersion, String curVersion) {
      return !(ejb instanceof MessageDrivenEJBRuntimeMBean) ? false : ApplicationVersionUtils.isSameComponent(ejb.getName(), activeVersion, this.getRuntimeMBeanName(), curVersion);
   }

   public boolean isNonDDMD() {
      if (this.info.getIsJMSBased() && this.destinationInfo != null) {
         return this.destinationInfo.getType() != 6 && this.destinationInfo.getType() != 5 && this.destinationInfo.getType() != 4;
      } else {
         return false;
      }
   }

   public boolean getDeleteDurableSubscription() {
      return this.deleteDurableSubscription;
   }

   protected void removeEJBRuntimeMBean(EJBRuntimeMBean mb) {
      this.getEJBComponentRuntime().removeEJBRuntimeMBean(mb.getName());
   }

   public String getDDJNDIName() {
      return this.destinationInfo == null ? this.destinationName : this.destinationInfo.getJNDIName();
   }

   private void resetConnValues() {
      this.messageSelector = this.info.getMessageSelector();
      this.deleteDurableSubscription = this.info.getDeleteDurableSubscription();
      this.topicMessagesDistributionMode = this.info.getTopicMessagesDistributionMode();
      this.connectionFactoryJNDIName = this.info.getConnectionFactoryJNDIName();
      this.providerURL = this.info.getProviderURL();
      this.uniqueGlobalId = this.info.computeUniqueGlobalId(this.getDDMemberName(), this.targetMBean);
      this.jmsClientId = this.info.computeJmsClientId(this.getDDMemberName(), this.targetMBean);
      this.subscriptionName = this.info.computeSubscriptionName(this.jmsClientId);
      if (debugLogger.isDebugEnabled()) {
         debug("JMS Client Id: " + this.jmsClientId + ", Destination: " + this.getDDJNDIName());
      }

   }

   public String getConnectionFactoryJNDIName() {
      return this.connectionFactoryJNDIName;
   }

   public String getProviderURL() {
      return this.providerURL;
   }

   public DestinationDetail getDestination() {
      return this.destinationInfo;
   }

   public void enableDestination(DestinationDetail dest, TargetMBean tm) throws WLDeploymentException {
      this.destinationInfo = dest;
      this.targetMBean = tm;
      if (this.targetMBean != null) {
         this.unInitialize();
         this.initialize();
      }

      this.updateStatus(1);
   }

   public String updateStatus(int newStatus) {
      MessageDrivenEJBRuntimeMBeanImpl rtmbean = this.runtimeMBean;
      if (rtmbean == null) {
         return null;
      } else {
         String oldstatus = rtmbean.getMDBStatus();
         rtmbean.setMDBStatus(rtmbean.statusAsString(newStatus));
         return oldstatus;
      }
   }

   private void restoreStatusIf(String statusToRestore, int ifstatus) {
      MessageDrivenEJBRuntimeMBeanImpl rtmbean = this.runtimeMBean;
      if (rtmbean != null) {
         String status = rtmbean.getMDBStatus();
         if (status != null) {
            if (status.equals(rtmbean.statusAsString(ifstatus))) {
               rtmbean.setMDBStatus(statusToRestore);
            }

         }
      }
   }

   protected void addEJBRuntimeMBean(EJBRuntimeMBean mb) {
      EJBRuntimeHolder compRTMBean = this.getEJBComponentRuntime();
      MessageDrivenEJBRuntimeMBean mdMBean = (MessageDrivenEJBRuntimeMBean)mb;
      compRTMBean.removeEJBRuntimeMBean(mdMBean.getEJBName());
      compRTMBean.removeEJBRuntimeMBean(mdMBean.getEJBName() + "_" + mdMBean.getDestination());
      compRTMBean.addEJBRuntimeMBean(mb.getName(), mb);
   }

   protected void perhapsSetupTimerManager(EJBTimerRuntimeMBean timerRT) throws WLDeploymentException {
      if (!$assertionsDisabled && this.runtimeMBean == null) {
         throw new AssertionError();
      } else {
         if (this.info != null && this.info.getTimerManagerFactory() != null) {
            this.info.getTimerManagerFactory().setup(this, timerRT);
         }

      }
   }

   public int getOrder() {
      return Integer.MAX_VALUE;
   }

   public String toString() {
      return "[MessageDrivenManager] @" + this.hashCode() + " pool: " + this.pool;
   }

   private void debugOperationStart(String opName) {
      debug(" Operation '" + opName + "' is invoked on " + this + " with destination JNDI " + this.getDDJNDIName());
   }

   private static void debug(String s) {
      debugLogger.debug("[MessageDrivenManager] " + s);
   }

   private static void debug(String s, Throwable th) {
      debugLogger.debug("[MessageDrivenManager] " + s, th);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium");
      _WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageDrivenManager.java", "weblogic.ejb.container.manager.MessageDrivenManager", "preInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)Ljava/lang/Object;", 226, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Preinvoke_After_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "MessageDrivenManager.java", "weblogic.ejb.container.manager.MessageDrivenManager", "postInvoke", "(Lweblogic/ejb/container/internal/InvocationWrapper;)V", 238, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("wrap", "weblogic.diagnostics.instrumentation.gathering.EJBInvocationWrapperRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Pool_Manager_Postinvoke_Before_Medium};
      $assertionsDisabled = !MessageDrivenManager.class.desiredAssertionStatus();
      DEBUG_APP_VERSION = Debug.getCategory("weblogic.AppVersion");
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      TAGS = new String[]{"%server:", "%partition:", "%app-name:", "%module-name:", "%ejb-name:"};
      MDBS_TO_SUSPEND_ON_STARTUP = getMDBsToSuspendOnDeployment();
   }
}
