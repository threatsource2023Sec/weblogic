package weblogic.jms.backend;

import java.security.AccessController;
import java.text.ParseException;
import java.util.List;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationSecurity;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageExpirationHelper;
import weblogic.jms.common.JMSMessageLogHelper;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageStatisticsLogger;
import weblogic.jms.common.WrappedDestinationImpl;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.extensions.Schedule;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.common.PartitionNameUtils;

public abstract class BEDestinationCommon implements Invocable, MessageStatisticsLogger, ActiveMessageExpiration {
   public static final int NOT_TEMPORARY = 1;
   public static final int TEMPORARY = 0;
   public static final int SUPPORTS = 0;
   public static final int NEVER = 1;
   public static final int AUTH_PRINS = 2;
   public static final String DEFAULT_TIME_TO_DELIVER_OVERRIDE = "-1";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected final BackEnd backEnd;
   protected final String name;
   protected String moduleName;
   protected BEDestinationRuntimeMBeanImpl runtimeMBean;
   private final InvocableMonitor invocableMonitor;
   private long creationTime = 1L;
   protected int expirationPolicy = 1;
   private List expirationLoggingJMSHeaders = null;
   private List expirationLoggingUserProperties = null;
   protected int maximumMessageSize = Integer.MAX_VALUE;
   private boolean messageLoggingEnabled = false;
   private String messageLoggingFormat = null;
   private List messageLoggingJMSHeaders = null;
   private List messageLoggingUserProperties = null;
   protected List destinationKeysList;
   private boolean bound;
   private boolean localBound;
   private boolean applicationBound;
   protected boolean ddBound;
   protected String jndiName;
   private String localJNDIName;
   private boolean advertised = false;
   protected DestinationImpl destinationImpl;
   protected String internalDistributedJNDIName;
   protected final JMSID id;
   private JMSID connectionId;
   protected int deliveryModeOverride = -1;
   protected int duration;
   private int priorityOverride;
   protected final Object configurationLock = new Object();
   private long redeliveryDelayOverride;
   private int redeliveryLimit;
   private boolean foundBEErrorDestination;
   public BEDestinationImpl errorDestination;
   protected EntityName errorDestinationName;
   private String timeToDeliverOverride;
   private long timeToLiveOverride;
   protected int attachSenderMode = 0;
   protected String safExportPolicy = "All";
   protected boolean defaultTargetingEnabled;
   protected boolean productionPausedAtStartup;
   protected boolean insertionPausedAtStartup;
   protected boolean consumptionPausedAtStartup;
   private String jmsCreateDestinationIdentifier;
   private String expirationLoggingPolicy;
   private String defaultUnitOfOrder;
   private String applicationJNDIName;
   private Context applicationContext;
   private JMSDestinationSecurity jmsDestinationSecurity;
   private final String DEFAULT_UOO_NAME;

   protected BEDestinationCommon(BackEnd backEnd, String name, boolean temporary, JMSDestinationSecurity jmsDestinationSecurity) {
      this.backEnd = backEnd;
      this.name = name;
      this.duration = temporary ? 0 : 1;
      this.id = JMSService.getNextId();
      this.invocableMonitor = backEnd.getInvocableMonitor();
      this.creationTime = System.currentTimeMillis();
      this.jmsDestinationSecurity = jmsDestinationSecurity;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      this.DEFAULT_UOO_NAME = "UOO-" + domainName + ":" + name;
   }

   public abstract void setStateFlag(int var1);

   public abstract void clearStateFlag(int var1);

   public abstract void checkShutdown(String var1) throws JMSException;

   public final BackEnd getBackEnd() {
      return this.backEnd;
   }

   final void setRuntimeMBean(BEDestinationRuntimeMBeanImpl mbean) {
      this.runtimeMBean = mbean;
   }

   public final JMSDestinationRuntimeMBean getRuntimeMBean() {
      return this.runtimeMBean;
   }

   public final long getCreationTime() {
      return this.creationTime;
   }

   public final void setProductionPausedAtStartup(boolean productionPausedAtStartup) {
      this.productionPausedAtStartup = productionPausedAtStartup;
   }

   public final void setInsertionPausedAtStartup(boolean insertionPausedAtStartup) {
      this.insertionPausedAtStartup = insertionPausedAtStartup;
   }

   public final void setConsumptionPausedAtStartup(boolean consumptionPausedAtStartup) {
      this.consumptionPausedAtStartup = consumptionPausedAtStartup;
   }

   public void setMaximumMessageSize(int paramMaximumMessageSize) {
      this.maximumMessageSize = paramMaximumMessageSize;
   }

   public final DestinationImpl getDestinationImpl() {
      return this.destinationImpl;
   }

   public final void setDestinationImpl(DestinationImpl destinationImpl) {
      this.destinationImpl = destinationImpl;
   }

   private final void internalValJndiName(String proposedJNDIName, boolean isLocal) throws BeanUpdateRejectedException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      if (proposedJNDIName != null) {
         if (this.advertised) {
            String matchJndiName;
            if (!isLocal) {
               matchJndiName = this.jndiName;
            } else {
               matchJndiName = this.localJNDIName;
            }

            if (matchJndiName != null && matchJndiName.equals(proposedJNDIName)) {
               return;
            }
         }

         Context context = this.getBackEnd().getJmsService().getCtx(!isLocal);
         Object object = null;

         for(int i = 0; i < 40; ++i) {
            try {
               object = context.lookup(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", proposedJNDIName));
            } catch (NameNotFoundException var7) {
               return;
            } catch (NamingException var8) {
               throw new BeanUpdateRejectedException(var8.getMessage(), var8);
            }

            try {
               Thread.sleep(500L);
            } catch (InterruptedException var9) {
               break;
            }
         }

         throw new BeanUpdateRejectedException("The proposed " + (isLocal ? "Local" : "") + " JNDI name " + proposedJNDIName + " for destination " + this.name + " is already bound by another object of type " + (object == null ? "null" : object.getClass().getName()));
      }
   }

   public final void valJNDIName(String proposedJNDIName) throws BeanUpdateRejectedException {
      this.internalValJndiName(proposedJNDIName, false);
   }

   public final void setJNDIName(String proposedJNDIName) throws IllegalArgumentException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      String proposedNonNullName = proposedJNDIName == null ? "" : proposedJNDIName;
      String nonNullName = this.jndiName == null ? "" : this.jndiName;
      if (this.advertised && !proposedNonNullName.equals(nonNullName)) {
         boolean myBound = false;
         if (proposedJNDIName != null) {
            try {
               PrivilegedActionUtilities.bindAsSU(this.getBackEnd().getJmsService().getCtx(true), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", proposedJNDIName), new WrappedDestinationImpl(this.destinationImpl), kernelId);
               myBound = true;
            } catch (NamingException var7) {
               if (!(var7 instanceof NameAlreadyBoundException)) {
                  throw new IllegalArgumentException("Error binding destination to JNDI (jndi name = " + proposedJNDIName + ")");
               }

               JMSLogger.logNameConflictChangingGlobalJNDIName(this.jndiName, proposedJNDIName, this.name, this.moduleName);
            }
         }

         if (this.bound) {
            try {
               PrivilegedActionUtilities.unbindAsSU(this.getBackEnd().getJmsService().getCtx(true), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.jndiName), kernelId);
            } catch (NamingException var6) {
               JMSLogger.logCouldNotUnbindGlobalJNDIName(this.jndiName, this.name, this.moduleName);
            }
         }

         this.bound = myBound;
         if (this.bound) {
            this.setStateFlag(131072);
         } else {
            this.clearStateFlag(131072);
         }
      }

      this.jndiName = proposedJNDIName;
   }

   public final void valLocalJNDIName(String proposedLocalJNDIName) throws BeanUpdateRejectedException {
      this.internalValJndiName(proposedLocalJNDIName, true);
   }

   public final String getJNDIName() {
      return this.jndiName;
   }

   public final String getLocalJNDIName() {
      return this.localJNDIName;
   }

   public final void setLocalJNDIName(String proposedJNDIName) throws IllegalArgumentException {
      proposedJNDIName = JMSServerUtilities.transformJNDIName(proposedJNDIName);
      String proposedNonNullName = proposedJNDIName == null ? "" : proposedJNDIName;
      String nonNullName = this.localJNDIName == null ? "" : this.localJNDIName;
      if (this.advertised && !proposedNonNullName.equals(nonNullName)) {
         boolean myBound = false;
         if (proposedJNDIName != null) {
            try {
               PrivilegedActionUtilities.bindAsSU(this.getBackEnd().getJmsService().getCtx(false), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", proposedJNDIName), this.destinationImpl, kernelId);
               myBound = true;
            } catch (NamingException var7) {
               if (!(var7 instanceof NameAlreadyBoundException)) {
                  throw new IllegalArgumentException("Error binding destination to local JNDI (jndi name = " + proposedJNDIName + ")");
               }

               JMSLogger.logNameConflictChangingLocalJNDIName(this.localJNDIName, proposedJNDIName, this.name, this.moduleName);
            }
         }

         if (this.localBound) {
            try {
               PrivilegedActionUtilities.unbindAsSU(this.getBackEnd().getJmsService().getCtx(false), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.localJNDIName), kernelId);
            } catch (NamingException var6) {
               JMSLogger.logCouldNotUnbindLocalJNDIName(this.localJNDIName, this.name, this.moduleName);
            }
         }

         this.localBound = myBound;
         if (this.localBound) {
            this.setStateFlag(262144);
         } else {
            this.clearStateFlag(262144);
         }
      }

      this.localJNDIName = proposedJNDIName;
   }

   void setApplicationJNDIName(String applicationJNDIName) {
      applicationJNDIName = JMSServerUtilities.transformJNDIName(applicationJNDIName);
      this.applicationJNDIName = applicationJNDIName;
   }

   void setApplicationContext(Context applicationContext) {
      this.applicationContext = applicationContext;
   }

   public final int getDestType() {
      return this.destinationImpl.getType();
   }

   public final void logMessagesThresholdHigh() {
      JMSLogger.logMessagesThresholdHighDestination(this.backEnd.getName(), this.name);
   }

   public final void logMessagesThresholdLow() {
      JMSLogger.logMessagesThresholdLowDestination(this.backEnd.getName(), this.name);
   }

   public final void logBytesThresholdHigh() {
      JMSLogger.logBytesThresholdHighDestination(this.backEnd.getName(), this.name);
   }

   public final void logBytesThresholdLow() {
      JMSLogger.logBytesThresholdLowDestination(this.backEnd.getName(), this.name);
   }

   public final void setPriority(int priorityOverride) {
      this.priorityOverride = priorityOverride;
   }

   public final Object getConfigurationLock() {
      return this.configurationLock;
   }

   public final long getDirtyRedeliveryDelayOverride() {
      return this.redeliveryDelayOverride;
   }

   public final int getDirtyRedeliveryLimit() {
      return this.redeliveryLimit;
   }

   public final long getRedeliveryDelay() {
      synchronized(this.configurationLock) {
         return this.redeliveryDelayOverride;
      }
   }

   public final void setRedeliveryDelay(long redeliveryDelayOverride) {
      synchronized(this.configurationLock) {
         this.redeliveryDelayOverride = redeliveryDelayOverride;
      }
   }

   public void setRedeliveryLimit(int redeliveryLimit) {
      synchronized(this.configurationLock) {
         this.redeliveryLimit = redeliveryLimit;
      }
   }

   public final BEDestinationImpl getBEErrorDestination() {
      synchronized(this.configurationLock) {
         if (this.foundBEErrorDestination) {
            return this.errorDestination;
         } else {
            this.errorDestination = this.errorDestinationName == null ? null : this.backEnd.findDestination(this.errorDestinationName.toString());
            if (this.errorDestination != null || this.errorDestinationName == null) {
               this.foundBEErrorDestination = true;
            }

            return this.errorDestination;
         }
      }
   }

   public final String getTimeToDeliver() {
      synchronized(this.configurationLock) {
         return this.timeToDeliverOverride;
      }
   }

   public final void setTimeToDeliver(String timeToDeliverOverride) {
      synchronized(this.configurationLock) {
         this.timeToDeliverOverride = timeToDeliverOverride;
      }
   }

   public final long getTimeToDeliverOverrideInMillisRelative() {
      String timeToDeliverOverride = this.getTimeToDeliver();
      if (timeToDeliverOverride != null && timeToDeliverOverride != "-1" && timeToDeliverOverride.trim().length() != 0) {
         try {
            return Long.parseLong(timeToDeliverOverride);
         } catch (NumberFormatException var4) {
            try {
               return Schedule.nextScheduledTimeInMillisRelative(timeToDeliverOverride, System.currentTimeMillis());
            } catch (ParseException var3) {
               return 0L;
            }
         }
      } else {
         return -1L;
      }
   }

   public final long getTimeToLive() {
      synchronized(this.configurationLock) {
         return this.timeToLiveOverride;
      }
   }

   public final void setTimeToLive(long timeToLiveOverride) {
      synchronized(this.configurationLock) {
         this.timeToLiveOverride = timeToLiveOverride;
      }
   }

   public final void setDeliveryMode(String deliveryModeOverrideString) {
      if (deliveryModeOverrideString == null) {
         this.deliveryModeOverride = -1;
      } else {
         if (deliveryModeOverrideString.equalsIgnoreCase("Non-Persistent")) {
            this.deliveryModeOverride = 1;
         } else if (deliveryModeOverrideString.equalsIgnoreCase("Persistent")) {
            this.deliveryModeOverride = 2;
         } else {
            this.deliveryModeOverride = -1;
         }

      }
   }

   public void setDestinationKeysList(List destinationKeysList) {
      this.destinationKeysList = destinationKeysList;
   }

   public final boolean isTemporary() {
      return this.duration == 0;
   }

   public final void setDuration(int duration) {
      this.duration = duration;
   }

   public void setDestinationKeys(String[] destinationKeyArray) {
   }

   public final String getName() {
      return this.name;
   }

   public final Destination getDestination() {
      return this.getDestinationImpl();
   }

   public final String getDestinationType() {
      int i = this.getDestType();
      return i != 1 && i != 4 ? "Topic" : "Queue";
   }

   public abstract int getDestinationTypeIndicator();

   public final void setConnectionId(JMSID connectionId) {
      this.connectionId = connectionId;
   }

   public final JMSID getConnectionId() {
      return this.connectionId;
   }

   public final void setExpirationPolicy(String policyString) {
      this.expirationPolicy = expirationPolicyIntFromString(policyString);
   }

   public static final int expirationPolicyIntFromString(String policyString) {
      if (policyString == null) {
         return 1;
      } else if (policyString.equalsIgnoreCase("Log")) {
         return 2;
      } else {
         return policyString.equalsIgnoreCase("Redirect") ? 4 : 1;
      }
   }

   public final List getExpirationLoggingJMSHeaders() {
      return this.expirationLoggingJMSHeaders;
   }

   private final void setExpirationLoggingJMSHeaders(List expirationLoggingJMSHeaders) {
      this.expirationLoggingJMSHeaders = expirationLoggingJMSHeaders;
   }

   public final List getExpirationLoggingUserProperties() {
      return this.expirationLoggingUserProperties;
   }

   private final void setExpirationLoggingUserProperties(List expirationLoggingUserProperties) {
      this.expirationLoggingUserProperties = expirationLoggingUserProperties;
   }

   public final InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   public final JMSID getJMSID() {
      return this.id;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.backEnd.getDispatcherPartition4rmic();
   }

   private static int attachStringToAttachMode(String attachString) {
      if (attachString == null) {
         return 0;
      } else if (attachString.equalsIgnoreCase("supports")) {
         return 0;
      } else if (attachString.equalsIgnoreCase("never")) {
         return 1;
      } else {
         return attachString.equalsIgnoreCase("always") ? 2 : 0;
      }
   }

   public final void setAttachSender(String attachSender) {
      this.attachSenderMode = attachStringToAttachMode(attachSender);
   }

   public String getSAFExportPolicy() {
      return this.safExportPolicy;
   }

   public void setSAFExportPolicy(String safExportPolicy) {
      if (safExportPolicy == null) {
         this.safExportPolicy = "All";
      } else {
         this.safExportPolicy = safExportPolicy;
      }

   }

   public boolean isDefaultTargetingEnabled() {
      return this.defaultTargetingEnabled;
   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) {
      this.defaultTargetingEnabled = defaultTargetingEnabled;
   }

   protected void applyOverrides(MessageImpl message, JMSProducerSendResponse sendResponse) throws JMSException {
      long override;
      if ((override = (long)this.deliveryModeOverride) != -1L) {
         message.setJMSDeliveryMode((int)override);
         if (sendResponse != null) {
            sendResponse.setDeliveryMode((int)override);
         }
      }

      if ((override = (long)this.priorityOverride) != -1L) {
         message.setJMSPriority((int)override);
         if (sendResponse != null) {
            sendResponse.setPriority((int)override);
         }
      }

      if ((override = this.getTimeToLive()) >= 0L) {
         if (override > 0L) {
            long expireTime = System.currentTimeMillis() + override;
            if (expireTime <= 0L) {
               expireTime = Long.MAX_VALUE;
            }

            message.setJMSExpiration(expireTime);
         } else {
            message.setJMSExpiration(0L);
         }

         if (sendResponse != null) {
            sendResponse.setTimeToLive(override);
         }
      }

      override = this.getTimeToDeliverOverrideInMillisRelative();
      if (override >= 0L) {
         if (override > 0L) {
            message.setDeliveryTime(System.currentTimeMillis() + override);
         } else {
            message.setDeliveryTime(0L);
         }

         if (sendResponse != null) {
            sendResponse.setTimeToDeliver(override);
         }
      }

      if ((override = (long)this.redeliveryLimit) != -1L) {
         message.setJMSRedeliveryLimit((int)override);
         if (sendResponse != null) {
            sendResponse.setRedeliveryLimit((int)override);
         }
      }

   }

   protected void advertise() throws JMSException {
      this.advertiseDestinationImpl();
      this.advertised = true;
   }

   private void advertiseDestinationImpl() throws JMSException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("BEDestinationCommon#advertiseDestinationImpl called for this=" + this + " on jndiName=" + this.jndiName);
      }

      this.checkShutdown("start destination");
      if (this.duration != 0) {
         JMSService jmsService = this.getBackEnd().getJmsService();
         ComponentInvocationContext threadCIC;
         if (!this.bound && this.jndiName != null) {
            try {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
                  if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon cluster advertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
                  } else {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon cluster advertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
                  }
               }

               String strippedDecoratedPartitionName = PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.jndiName);
               WrappedDestinationImpl wrappedDestinationImpl = new WrappedDestinationImpl(this.destinationImpl);
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("BEDestinationCommon#advertiseDestinationImpl calling bindAsSU for wrappedDestinationImpl=" + wrappedDestinationImpl + " to strippedDecoratedPartitionName=" + strippedDecoratedPartitionName);
               }

               PrivilegedActionUtilities.bindAsSU(jmsService.getCtx(true), strippedDecoratedPartitionName, wrappedDestinationImpl, kernelId);
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("BEDestinationCommon#advertiseDestinationImpl successfully returned from bindAsSU for wrappedDestinationImpl=" + wrappedDestinationImpl + " to strippedDecoratedPartitionName=" + strippedDecoratedPartitionName);
               }

               this.bound = true;
               this.setStateFlag(131072);
            } catch (NamingException var6) {
               if (!(var6 instanceof NameAlreadyBoundException)) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logBindNamingExceptionLoggable(this.name, this.jndiName), var6);
               }

               JMSLogger.logNameConflictBindingGlobalJNDIName(this.jndiName, this.name, this.moduleName);
            }
         }

         if (!this.localBound && this.getLocalJNDIName() != null) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
               if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                  JMSDebug.JMSCommon.debug("BEDestinationCommon local advertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
               } else {
                  JMSDebug.JMSCommon.debug("BEDestinationCommon local advertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
               }
            }

            try {
               PrivilegedActionUtilities.bindAsSU(jmsService.getCtx(false), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.getLocalJNDIName()), this.destinationImpl, kernelId);
               this.localBound = true;
               this.setStateFlag(262144);
            } catch (NamingException var5) {
               if (!(var5 instanceof NameAlreadyBoundException)) {
                  throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logLocalBindNamingExceptionLoggable(this.name, this.localJNDIName), var5);
               }

               JMSLogger.logNameConflictBindingLocalJNDIName(this.localJNDIName, this.name, this.moduleName);
            }
         }

         if (this.applicationJNDIName != null && this.applicationContext != null) {
            try {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
                  if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon applicationContext advertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
                  } else {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon applicationContext advertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
                  }
               }

               PrivilegedActionUtilities.bindAsSU(this.applicationContext, PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.applicationJNDIName), this.destinationImpl, kernelId);
               this.applicationBound = true;
            } catch (NamingException var4) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logAppBindNamingExceptionLoggable(this.name, this.applicationJNDIName), var4);
            }
         }

      }
   }

   public void unAdvertise() {
      this.advertised = false;
      this.unAdvertiseDestinationImpl();
   }

   private void unAdvertiseDestinationImpl() {
      boolean isLateMigrationDeactivate = this.backEnd.isLateMigrationDeactivate();
      if (!this.isTemporary()) {
         JMSService jmsService = this.getBackEnd().getJmsService();
         ComponentInvocationContext threadCIC;
         if (this.jndiName != null && this.bound) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("unAdvertise destination jndiName: " + this.jndiName + " isLateMigrationDeactivate: " + isLateMigrationDeactivate);
            }

            try {
               if (!isLateMigrationDeactivate) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
                     if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                        JMSDebug.JMSCommon.debug("BEDestinationCommon cluster unAdvertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
                     } else {
                        JMSDebug.JMSCommon.debug("BEDestinationCommon cluster unAdvertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
                     }
                  }

                  PrivilegedActionUtilities.unbindAsSU(jmsService.getCtx(true), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.jndiName), kernelId);
               }

               this.bound = false;
               this.clearStateFlag(131072);
            } catch (NamingException var6) {
            }
         }

         if (this.getLocalJNDIName() != null && this.localBound) {
            try {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
                  if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon local unAdvertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
                  } else {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon local unAdvertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
                  }
               }

               PrivilegedActionUtilities.unbindAsSU(jmsService.getCtx(false), PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.getLocalJNDIName()), kernelId);
               this.localBound = false;
               this.clearStateFlag(262144);
            } catch (NamingException var5) {
            }
         }

         if (this.applicationBound && this.applicationContext != null && this.applicationJNDIName != null) {
            try {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  threadCIC = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
                  if (jmsService.getComponentInvocationContext().equals(threadCIC)) {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon applicationContext unAdvertiseDestinationImpl thread matches thread " + jmsService.debugCIC(threadCIC));
                  } else {
                     JMSDebug.JMSCommon.debug("BEDestinationCommon applicationContext unAdvertiseDestinationImpl this.cic is [" + jmsService.debugCIC(jmsService.getComponentInvocationContext()) + "] thread cic is [" + jmsService.debugCIC(threadCIC) + " ]");
                  }
               }

               PrivilegedActionUtilities.unbindAsSU(this.applicationContext, PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", this.applicationJNDIName), kernelId);
               this.applicationBound = false;
            } catch (NamingException var4) {
            }
         }
      }

   }

   public void setErrorDestination(EntityName paramErrorDestinationName) {
      synchronized(this.configurationLock) {
         this.errorDestinationName = paramErrorDestinationName;
         this.foundBEErrorDestination = false;
      }
   }

   public void setExpirationLoggingPolicy(String paramExpirationLoggingPolicy) {
      this.expirationLoggingPolicy = paramExpirationLoggingPolicy;
      StringBuffer tempProperties = new StringBuffer(256);
      List expirationLoggingJMSHeaders = JMSMessageExpirationHelper.extractJMSHeaderAndProperty(this.expirationLoggingPolicy, tempProperties);
      List expirationLoggingUserProperties = JMSMessageExpirationHelper.convertStringToLinkedList(tempProperties.toString());
      this.setExpirationLoggingJMSHeaders(expirationLoggingJMSHeaders);
      this.setExpirationLoggingUserProperties(expirationLoggingUserProperties);
   }

   public final String getJMSCreateDestinationIdentifier() {
      return this.jmsCreateDestinationIdentifier;
   }

   public final void setJMSCreateDestinationIdentifier(String paramJMSCreateDestinationIdentifier) {
      this.jmsCreateDestinationIdentifier = paramJMSCreateDestinationIdentifier;
   }

   public void setMessageLoggingEnabled(boolean value) {
      this.messageLoggingEnabled = value;
   }

   public boolean isMessageLoggingEnabled() {
      return this.backEnd.getJmsService().shouldMessageLogAll() ? true : this.messageLoggingEnabled;
   }

   public final List getMessageLoggingJMSHeaders() {
      return this.messageLoggingJMSHeaders;
   }

   private final void setMessageLoggingJMSHeaders(List messageLoggingJMSHeaders) {
      this.messageLoggingJMSHeaders = messageLoggingJMSHeaders;
   }

   public final List getMessageLoggingUserProperties() {
      return this.messageLoggingUserProperties;
   }

   private final void setMessageLoggingUserProperties(List messageLoggingUserProperties) {
      this.messageLoggingUserProperties = messageLoggingUserProperties;
   }

   public void setMessageLoggingFormat(String paramMessageLoggingFormat) {
      this.messageLoggingFormat = paramMessageLoggingFormat;
      StringBuffer tempProperties = new StringBuffer(256);
      List messageLoggingJMSHeaders = JMSMessageLogHelper.extractJMSHeaderAndProperty(this.messageLoggingFormat, tempProperties);
      List messageLoggingUserProperties = JMSMessageLogHelper.convertStringToLinkedList(tempProperties.toString());
      this.setMessageLoggingJMSHeaders(messageLoggingJMSHeaders);
      this.setMessageLoggingUserProperties(messageLoggingUserProperties);
   }

   public void setDefaultUnitOfOrder(boolean defaultUnitOfOrderFlag) throws IllegalArgumentException {
      this.defaultUnitOfOrder = defaultUnitOfOrderFlag ? this.DEFAULT_UOO_NAME : null;
   }

   public String getDefaultUnitOfOrder() {
      return this.defaultUnitOfOrder;
   }

   public final String getListenerName() {
      return this.getName();
   }

   public final JMSDestinationSecurity getJMSDestinationSecurity() {
      return this.jmsDestinationSecurity;
   }

   public boolean isAvailableForCreateDestination() {
      if (this.destinationImpl != null && this.jmsCreateDestinationIdentifier == null && this.destinationImpl.getModuleName() != null) {
         return this.jndiName != null || this.localJNDIName != null;
      } else {
         return true;
      }
   }
}
