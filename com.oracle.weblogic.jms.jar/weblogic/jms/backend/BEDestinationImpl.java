package weblogic.jms.backend;

import java.security.AccessController;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.ObjectMessage;
import javax.jms.ResourceAllocationException;
import javax.jms.ServerSessionPool;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.BadSequenceNumberException;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DispatcherCompletionListener;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationSecurity;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageExpirationHelper;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSSecurityHelper;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.server.DestinationStatus;
import weblogic.jms.server.DestinationStatusListener;
import weblogic.jms.utils.tracing.MessageTimeStamp;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.DispatcherImpl;
import weblogic.messaging.dispatcher.Request;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.kernel.Statistics;
import weblogic.messaging.path.helper.PathHelper;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.transaction.TransactionHelper;

public abstract class BEDestinationImpl extends BEDestinationCommon implements RedirectionListener, DestinationStatus {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static final String QUOTA_PROP = "Quota";
   protected Destination destination;
   private Statistics statistics;
   protected BEMessageComparator comparator;
   static final int SEND_ISSUE_MESSAGE = 1102;
   private static final int SEND_COMPLETE = 1103;
   private static final int SEND_UNKNOWN = 1104;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static final char NAME_DELIMITER = '@';
   protected String fullyQualifiedDestinationName;
   public static final int LOG_MESSAGE_ALL = 15;
   private static final String APPLY_OVERRIDES_ON_DQFORWARD = "weblogic.jms.ApplyOverridesOnDQForward";
   private boolean applyOverridesOnDQForward = false;
   private volatile int state = 0;
   protected boolean deleted;
   protected final Set consumers = new HashSet();
   protected final HashMap producers = new HashMap();
   private int consumersHigh;
   private long consumersTotal;
   private BEThresholdHandler thresholdHandler;
   private LinkedList statusListeners = new LinkedList();
   private static LinkedList newDestinationListeners = new LinkedList();
   private boolean isUp = false;
   private BEExtension destExtension;
   private boolean ifReorder;
   private int throughputEmphasis = 25;
   private boolean isUOWDestination = false;
   private int incompleteWorkExpirationTime = 0;
   private byte[] signatureSecret;
   private TransitionChecker transitionChecker = new TransitionChecker();
   int count = 0;

   protected BEDestinationImpl(BackEnd backEnd, String name, boolean temporary, JMSDestinationSecurity jmsDestinationSecurity) {
      super(backEnd, name, temporary, jmsDestinationSecurity);
      this.fullyQualifiedDestinationName = this.getFullyQualifiedServerName() + '@' + name;
      if (PathHelper.retired || PathHelper.PathSvcVerbose.isDebugEnabled()) {
         (new Exception("DebugOnly PathServiceAdmin BEDestinationImpl ")).printStackTrace();
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Creating destination: " + name + " on " + backEnd.getName());
      }

      this.thresholdHandler = new BEThresholdHandler(backEnd.getName(), name);
      if (this instanceof BEQueueImpl) {
         String sApplyOverrideOnDQForward = System.getProperty("weblogic.jms.ApplyOverridesOnDQForward");
         String sApplyOverrideOnDQForwardByName = System.getProperty("weblogic.jms.ApplyOverridesOnDQForward." + name);
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Generic apply override on DQ forward " + sApplyOverrideOnDQForward + ", " + "weblogic.jms.ApplyOverridesOnDQForward" + "." + name + " = " + sApplyOverrideOnDQForwardByName);
         }

         if (sApplyOverrideOnDQForward != null && Boolean.valueOf(sApplyOverrideOnDQForward) == Boolean.TRUE || sApplyOverrideOnDQForwardByName != null && Boolean.valueOf(sApplyOverrideOnDQForwardByName) == Boolean.TRUE) {
            this.applyOverridesOnDQForward = true;
         }

         if (sApplyOverrideOnDQForwardByName != null && Boolean.valueOf(sApplyOverrideOnDQForwardByName) == Boolean.FALSE) {
            this.applyOverridesOnDQForward = false;
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Apply overrides on DD Forward for " + name + " is " + this.applyOverridesOnDQForward);
         }
      }

   }

   private String getClusterName(RuntimeAccess runtimeAccess) {
      String domainName = runtimeAccess.getDomainName();
      ClusterMBean clusterMBean = runtimeAccess.getServer().getCluster();
      return clusterMBean == null ? null : clusterMBean.getName();
   }

   protected String getFullyQualifiedServerName() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      String clusterName = this.getClusterName(runtimeAccess);
      String serverName = runtimeAccess.getServerName();
      return domainName + '@' + clusterName + '@' + serverName;
   }

   public abstract void suspendMessageLogging() throws JMSException;

   public abstract void resumeMessageLogging() throws JMSException;

   public abstract boolean isMessageLogging();

   public abstract void setQuota(Quota var1) throws BeanUpdateFailedException;

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public synchronized void addStatusListener(DestinationStatusListener listener) {
      LinkedList newStatusListeners = new LinkedList();
      newStatusListeners.add(listener);
      Iterator iter = this.statusListeners.listIterator();

      while(iter.hasNext()) {
         DestinationStatusListener statusListener = (DestinationStatusListener)iter.next();
         if (statusListener != listener) {
            newStatusListeners.add(statusListener);
         }
      }

      this.statusListeners = newStatusListeners;
   }

   public synchronized void removeStatusListener(DestinationStatusListener listener) {
      LinkedList newStatusListeners = new LinkedList();
      Iterator iter = this.statusListeners.listIterator();

      while(iter.hasNext()) {
         newStatusListeners.add(iter.next());
      }

      newStatusListeners.remove(listener);
      this.statusListeners = newStatusListeners;
   }

   public static void addNewDestinationListener(NewDestinationListener listener) {
      LinkedList newNewDestinationListeners = new LinkedList();
      newNewDestinationListeners.add(listener);
      Iterator iter = newDestinationListeners.listIterator();

      while(iter.hasNext()) {
         newNewDestinationListeners.add(iter.next());
      }

      newDestinationListeners = newNewDestinationListeners;
   }

   public static void removeNewDestinationListener(NewDestinationListener listener) {
      LinkedList newNewDestinationListeners = new LinkedList();
      Iterator iter = newDestinationListeners.listIterator();

      while(iter.hasNext()) {
         newNewDestinationListeners.add(iter.next());
      }

      newNewDestinationListeners.remove(listener);
      newDestinationListeners = newNewDestinationListeners;
   }

   protected void setKernel(Destination destination) throws JMSException {
      this.destination = destination;

      try {
         destination.setProperty("Durable", new Boolean(!this.isTemporary() && this.backEnd.isStoreEnabled()));
      } catch (KernelException var3) {
         throw new weblogic.jms.common.JMSException(var3);
      }
   }

   public void open() throws JMSException {
      try {
         this.destination.setProperty("MaximumMessageSize", new Integer(this.maximumMessageSize));
      } catch (KernelException var2) {
         throw new weblogic.jms.common.JMSException(var2);
      }

      this.statistics = this.destination.getStatistics();
      this.thresholdHandler.setTarget(this.destination);
      Iterator iter = newDestinationListeners.listIterator();

      while(iter.hasNext()) {
         ((NewDestinationListener)iter.next()).newDestination(this);
      }

   }

   public synchronized void setStateFlag(int flag) {
      this.state |= flag;
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("New state for " + this.name + ": " + JMSService.getStateName(this.state));
      }

   }

   public synchronized void clearStateFlag(int flag) {
      this.state &= ~flag;
   }

   private synchronized boolean checkStateFlag(int flag) {
      return (this.state & flag) != 0;
   }

   private boolean checkStateFlagFast(int flag) {
      return (this.state & flag) != 0;
   }

   public synchronized void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   public Destination getKernelDestination() {
      return this.destination;
   }

   public void setExtension(BEExtension e) {
      this.destExtension = e;
   }

   public BEExtension getExtension() {
      return this.destExtension;
   }

   public void setMessagingPerformancePreference(int throughputEmphasis) {
      this.throughputEmphasis = throughputEmphasis;
   }

   public int getMessagingPerformancePreference() {
      return this.throughputEmphasis;
   }

   public void setMaximumMessageSize(int maximumMessageSize) {
      if (maximumMessageSize < 0) {
         maximumMessageSize = Integer.MAX_VALUE;
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Destination " + this.name + " maximum message size " + maximumMessageSize);
      }

      super.setMaximumMessageSize(maximumMessageSize);
      if (this.destination != null) {
         try {
            this.destination.setProperty("MaximumMessageSize", new Integer(maximumMessageSize));
         } catch (KernelException var3) {
         }
      }

   }

   protected int getAdjustedExpirationPolicy(boolean alreadyReported) {
      return this.expirationPolicy;
   }

   public void expirationTimeReached(RedirectionListener.Info redirectionInfo, boolean alreadyReported) {
      int policy = this.getAdjustedExpirationPolicy(alreadyReported);
      MessageImpl newMessage;
      switch (policy) {
         case 2:
            synchronized(this.configurationLock) {
               newMessage = (MessageImpl)redirectionInfo.getMessage();
               newMessage.setJMSDestinationImpl(this.destinationImpl);
               JMSMessageExpirationHelper.logExpiredMessage(newMessage, this.getExpirationLoggingJMSHeaders(), this.getExpirationLoggingUserProperties());
               break;
            }
         case 4:
            BEDestinationImpl realErrorDest = this.getBEErrorDestination();
            if (this.errorDestinationName != null) {
               redirectionInfo.setRedirectDestinationName(this.errorDestinationName.toString());
            }

            if (realErrorDest == null) {
               return;
            }

            newMessage = null;
            synchronized(this.configurationLock) {
               newMessage = (MessageImpl)redirectionInfo.getMessageElement().getMessage();

               try {
                  if (!newMessage.propertyExists("JMS_BEA_DeliveryFailureReason")) {
                     if (JMSDebug.JMSModule.isDebugEnabled()) {
                        if (newMessage instanceof TextMessage) {
                           try {
                              ((TextMessage)newMessage).getText();
                           } catch (JMSException var11) {
                           }
                        }

                        JMSDebug.JMSModule.debug("Setting delivery failure reason for " + newMessage + " on " + this.name + " to EXPIRATION_TIME_REACHED");
                     }

                     newMessage.setPropertiesWritable(true);
                     newMessage.setIntProperty("JMS_BEA_DeliveryFailureReason", 0);
                     newMessage.setPropertiesWritable(false);
                     newMessage.setSAFSequenceName((String)null);
                     newMessage.setSAFSeqNumber(0L);
                  }
               } catch (JMSException var12) {
               }
            }

            this.applyRedirectOverrides(realErrorDest, newMessage, true);
            synchronized(this.configurationLock) {
               redirectionInfo.setSendOptions(this.createSendOptions(0L, (Sequence)null, newMessage));
               redirectionInfo.setRedirectDestination(realErrorDest.getKernelDestination());
            }
      }

   }

   public void deliveryLimitReached(RedirectionListener.Info redirectionInfo) {
      BEDestinationImpl realErrorDest = this.getBEErrorDestination();
      if (realErrorDest != null) {
         MessageImpl newMessage = null;
         synchronized(this.configurationLock) {
            newMessage = (MessageImpl)redirectionInfo.getMessageElement().getMessage();

            try {
               if (!newMessage.propertyExists("JMS_BEA_DeliveryFailureReason")) {
                  if (JMSDebug.JMSModule.isDebugEnabled()) {
                     if (newMessage instanceof TextMessage) {
                        try {
                           ((TextMessage)newMessage).getText();
                        } catch (JMSException var9) {
                        }
                     }

                     JMSDebug.JMSModule.debug("Setting delivery failure reason for " + newMessage + " on " + this.name + " to DELIVERY_LIMIT_REACHED");
                  }

                  newMessage.setPropertiesWritable(true);
                  newMessage.setIntProperty("JMS_BEA_DeliveryFailureReason", 2);
                  newMessage.setPropertiesWritable(false);
                  newMessage.setSAFSequenceName((String)null);
                  newMessage.setSAFSeqNumber(0L);
               }
            } catch (JMSException var10) {
            }
         }

         this.applyRedirectOverrides(realErrorDest, newMessage, true);
         synchronized(this.configurationLock) {
            redirectionInfo.setSendOptions(this.createSendOptions(0L, (Sequence)null, newMessage));
            redirectionInfo.setRedirectDestination(realErrorDest.getKernelDestination());
         }
      }

   }

   private void applyRedirectOverrides(BEDestinationImpl dest, MessageImpl message, boolean forExpiration) {
      message.setDeliveryTime(0L);
      message._setJMSRedeliveryLimit(-1);
      if (forExpiration) {
         message._setJMSExpiration(0L);
      }

      try {
         dest.applyOverrides(message, (JMSProducerSendResponse)null);
      } catch (JMSException var5) {
      }

   }

   private boolean isFlowControlRequired() {
      return this.thresholdHandler.isArmed() || this.backEnd.needsFlowControl();
   }

   public long getBytesHigh() {
      return this.thresholdHandler.getBytesThresholdHigh();
   }

   public long getBytesLow() {
      return this.thresholdHandler.getBytesThresholdLow();
   }

   public long getMessagesHigh() {
      return this.thresholdHandler.getMessagesThresholdHigh();
   }

   public long getMessagesLow() {
      return this.thresholdHandler.getMessagesThresholdLow();
   }

   public long getMessagesThresholdTime() {
      return this.thresholdHandler.getMessagesThresholdTime();
   }

   public long getBytesThresholdTime() {
      return this.thresholdHandler.getBytesThresholdTime();
   }

   public void setBytesMaximum(long value) {
      if (value <= 0L) {
         value = 2147483647L;
      }

      if (this.destination != null) {
         Quota quota = (Quota)this.destination.getProperty("Quota");
         quota.setBytesMaximum(value);
      }

   }

   public void setBytesHigh(long value) {
      this.thresholdHandler.setBytesThresholdHigh(value);
   }

   public void setBytesLow(long value) {
      this.thresholdHandler.setBytesThresholdLow(value);
   }

   public void setMessagesMaximum(long value) {
      if (value <= 0L || value > 2147483647L) {
         value = 2147483647L;
      }

      if (this.destination != null) {
         Quota quota = (Quota)this.destination.getProperty("Quota");
         quota.setMessagesMaximum((int)value);
      }

   }

   public void setMessagesHigh(long value) {
      this.thresholdHandler.setMessagesThresholdHigh(value);
   }

   public void setMessagesLow(long value) {
      this.thresholdHandler.setMessagesThresholdLow(value);
   }

   public long getMessagesPendingCount() {
      return (long)this.statistics.getMessagesPending();
   }

   public long getMessagesCurrentCount() {
      return (long)(this.statistics.getMessagesCurrent() - this.statistics.getMessagesPending());
   }

   public long getMessagesHighCount() {
      return (long)this.statistics.getMessagesHigh();
   }

   public long getMessagesReceivedCount() {
      return this.statistics.getMessagesReceived();
   }

   public long getBytesCurrentCount() {
      return this.statistics.getBytesCurrent() - this.statistics.getBytesPending();
   }

   public long getBytesPendingCount() {
      return this.statistics.getBytesPending();
   }

   public long getBytesHighCount() {
      return this.statistics.getBytesHigh();
   }

   public long getBytesReceivedCount() {
      return this.statistics.getBytesReceived();
   }

   public final void resetStatistics() {
   }

   public synchronized void setDestinationKeysList(List destinationKeysList) {
      this.destinationKeysList = destinationKeysList;
      if (destinationKeysList != null && !destinationKeysList.isEmpty()) {
         this.comparator = new BEMessageComparator(destinationKeysList);
         if (this.comparator.isDefault()) {
            this.comparator = null;
         }
      } else {
         this.comparator = null;
      }

   }

   public final void start() throws JMSException {
      try {
         this.destination.setProperty("Durable", new Boolean(!this.isTemporary() && this.backEnd.isStoreEnabled()));
         this.destination.resume(16384);
      } catch (KernelException var6) {
         throw new weblogic.jms.common.JMSException(var6);
      }

      if (this.runtimeMBean != null) {
         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Registering runtimeMBean " + this.runtimeMBean + " on " + this.backEnd.getName());
            }

            PrivilegedActionUtilities.register(this.runtimeMBean, kernelId);
         } catch (ManagementException var4) {
            JMSException jmse = new JMSException("Failed to register the JMSServerRuntimeMBean " + this.name);
            jmse.setLinkedException(var4);
            throw jmse;
         }
      }

      this.prepareSignature();
      synchronized(this) {
         this.state = 4;
         if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
            JMSDebug.JMSPauseResume.debug(this.name + " During destination start():  this.ProductionPausedAtStartup=" + this.productionPausedAtStartup + ", this.InsertionPausedAtStartup= " + this.insertionPausedAtStartup + ", this.ConsumptionPausedAtStartup=" + this.consumptionPausedAtStartup);
            JMSDebug.JMSPauseResume.debug(this.name + " During destination start():  backEnd.getProductionPausedAtStartup()=" + this.backEnd.getProductionPausedAtStartup() + ", backEnd.getInsertionPausedAtStartup()= " + this.backEnd.getInsertionPausedAtStartup() + ", backEnd.getConsumptionPausedAtStartup()=" + this.backEnd.getConsumptionPausedAtStartup());
         }

         if (this.backEnd.getProductionPausedAtStartup().equals("default")) {
            if (!this.productionPausedAtStartup) {
               if (this.isProductionPaused()) {
                  if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                     JMSDebug.JMSPauseResume.debug("Resuming new message production on destination(" + this.name + "), because the destination has ProductionPausedAtStartup parameter is either not set or set to true and the hosting JMSServer " + this.backEnd.getName() + " has the ProductionPausedAtStartup parameter set to default");
                  }

                  this.resumeProduction();
               }
            } else if (!this.isProductionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing new message production on destination(" + this.name + "), because the destination has ProductionPausedAtStartup parameter set to true and the hosting JMSServer " + this.backEnd.getName() + " has the ProductionPausedAtStartup parameter set to default");
               }

               this.pauseProduction();
            }
         } else if (this.backEnd.getProductionPausedAtStartup().equals("true")) {
            if (!this.isProductionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing new message production on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has ProductionPausedAtStartup parameter set to true");
               }

               this.pauseProduction();
            }
         } else if (this.backEnd.getProductionPausedAtStartup().equals("false") && this.isProductionPaused()) {
            if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
               JMSDebug.JMSPauseResume.debug("Resuming new message production on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has ProductionPausedAtStartup parameter set to false");
            }

            this.resumeProduction();
         }

         if (this.backEnd.getInsertionPausedAtStartup().equals("default")) {
            if (!this.insertionPausedAtStartup) {
               if (this.isInsertionPaused()) {
                  if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                     JMSDebug.JMSPauseResume.debug("Resuming message insertion (in-flight message insertion) on destination(" + this.name + "), because the destination has InsertionPausedAtStartup parameter is either not set or set to true and the hosting JMSServer " + this.backEnd.getName() + " has the InsertionPausedAtStartup parameter set to default");
                  }

                  this.resumeInsertion();
               }
            } else if (!this.isInsertionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing message insertion (in-flight message insertion) on destination(" + this.name + "), because the destination has InsertionPausedAtStartup parameter set to true and the hosting JMSServer " + this.backEnd.getName() + " has the InsertionPausedAtStartup parameter set to default");
               }

               this.pauseInsertion();
            }
         } else if (this.backEnd.getInsertionPausedAtStartup().equals("true")) {
            if (!this.isInsertionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing message insertion (in-flight message insertion) on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has InsertionPausedAtStartup parameter set to true");
               }

               this.pauseInsertion();
            }
         } else if (this.backEnd.getInsertionPausedAtStartup().equals("false") && this.isInsertionPaused()) {
            if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
               JMSDebug.JMSPauseResume.debug("Resuming message insertion (in-flight message insertion) on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has InsertionPausedAtStartup parameter set to false");
            }

            this.resumeInsertion();
         }

         if (this.backEnd.getConsumptionPausedAtStartup().equals("default")) {
            if (!this.consumptionPausedAtStartup) {
               if (this.isConsumptionPaused()) {
                  if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                     JMSDebug.JMSPauseResume.debug("Resuming new message consumption on destination(" + this.name + "), because the destination has ConsumptionPausedAtStartup parameter is either not set or set to true and the hosting JMSServer " + this.backEnd.getName() + " has the ConsumptionPausedAtStartup parameter set to default");
                  }

                  this.resumeConsumption();
               }
            } else if (!this.isConsumptionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing new message consumption on destination(" + this.name + "), because the destination has ConsumptionPausedAtStartup parameter set to true and the hosting JMSServer " + this.backEnd.getName() + " has the ConsumptionPausedAtStartup parameter set to default");
               }

               this.pauseConsumption();
            }
         } else if (this.backEnd.getConsumptionPausedAtStartup().equals("true")) {
            if (!this.isConsumptionPaused()) {
               if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
                  JMSDebug.JMSPauseResume.debug("Pausing new message consumption on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has ConsumptionPausedAtStartup parameter set to true");
               }

               this.pauseConsumption();
            }
         } else if (this.backEnd.getConsumptionPausedAtStartup().equals("false") && this.isConsumptionPaused()) {
            if (JMSDebug.JMSPauseResume.isDebugEnabled()) {
               JMSDebug.JMSPauseResume.debug("Resuming new message consumption on destination(" + this.name + "), because the hosting JMSServer(" + this.backEnd.getName() + ") has ConsumptionPausedAtStartup parameter set to false");
            }

            this.resumeConsumption();
         }
      }

      this.advertise();
      this.isUp = true;
      Iterator iter = this.statusListeners.listIterator();

      while(iter.hasNext()) {
         ((DestinationStatusListener)iter.next()).onUpStatusChange(this);
      }

   }

   public final void suspend() {
      synchronized(this) {
         if (this.checkStateFlag(25)) {
            return;
         }

         this.state = 1;
      }

      this.shutdownInternal();
   }

   public final void shutdown() {
      synchronized(this) {
         if (this.checkStateFlag(16)) {
            return;
         }

         if (this.checkStateFlag(8)) {
            this.state = 16;
         }
      }

      this.shutdownInternal();
   }

   private void shutdownInternal() {
      this.isUp = false;
      String reason;
      if (this.deleted) {
         reason = "Consumer destination was deleted";
      } else {
         reason = "Consumer destination was closed";
      }

      if (this.runtimeMBean != null) {
         try {
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("Unregistering runtimeMBean " + this.runtimeMBean + " on " + this.backEnd.getName());
            }

            PrivilegedActionUtilities.unregister(this.runtimeMBean, kernelId);
         } catch (ManagementException var9) {
            var9.printStackTrace();
         }
      }

      this.closeAllConsumers(reason);
      this.closeAllBrowsers(reason);
      this.unAdvertise();
      if (this.deleted) {
         try {
            KernelRequest request = new KernelRequest();
            this.destination.delete(request);
         } catch (KernelException var8) {
            JMSLogger.logErrorUnregisteringBackEndDestination(this.backEnd.getName(), this, var8);
         }
      } else {
         try {
            this.destination.suspend(16384);
         } catch (KernelException var7) {
            JMSLogger.logErrorUnregisteringBackEndDestination(this.backEnd.getName(), this, var7);
         }
      }

      Iterator iter = this.statusListeners.listIterator();

      while(iter.hasNext()) {
         ((DestinationStatusListener)iter.next()).onUpStatusChange(this);
      }

      this.transitionChecker.cancelTimer();
      Iterator itr;
      synchronized(this) {
         itr = ((HashMap)this.producers.clone()).values().iterator();
         this.producers.clear();
      }

      while(itr.hasNext()) {
         BEProducerSecurityParticipantImpl producer = (BEProducerSecurityParticipantImpl)itr.next();
         this.backEnd.getJmsService().unregisterSecurityParticipant(producer);
      }

   }

   protected abstract BEConsumerImpl createConsumer(BESessionImpl var1, boolean var2, BEConsumerCreateRequest var3) throws JMSException;

   abstract BEConnectionConsumerImpl createConnectionConsumer(JMSID var1, ServerSessionPool var2, String var3, String var4, String var5, boolean var6, int var7, long var8, boolean var10, boolean var11) throws JMSException;

   public boolean hasConsumers() {
      return !this.consumers.isEmpty();
   }

   public boolean isUp() {
      return this.isUp;
   }

   public boolean isLocal() {
      return true;
   }

   public String getPathServiceJndiName() {
      return this.getBackEnd().getPathServiceJndiName();
   }

   public boolean isPersistent() {
      DomainMBean domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      JMSServerMBean jmsServerMBean = null;
      String jmsServerName = this.getBackEnd().getConfigName();

      for(int i = 0; i < domain.getJMSServers().length; ++i) {
         if (domain.getJMSServers()[i].getName().equals(jmsServerName)) {
            jmsServerMBean = domain.getJMSServers()[i];
            break;
         }
      }

      boolean isStoreEnabled = false;
      if (jmsServerMBean != null) {
         isStoreEnabled = jmsServerMBean.getStoreEnabled();
      }

      return isStoreEnabled && this.deliveryModeOverride != 1;
   }

   public void addConsumer(BEConsumerCommon consumer) throws JMSException {
      boolean stateTransition = false;
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("New consumer for " + this.name);
      }

      synchronized(this) {
         if (this.consumers.isEmpty()) {
            stateTransition = true;
         }

         if (!this.consumers.add(consumer)) {
            throw new AssertionError("Duplicate consumer");
         }

         if (this.consumers.size() > this.consumersHigh) {
            this.consumersHigh = this.consumers.size();
         }

         ++this.consumersTotal;
      }

      if (stateTransition) {
         this.transitionChecker.check();
      }

   }

   public void removeConsumer(BEConsumerImpl consumer, boolean blocking) throws JMSException {
      boolean stateTransition = false;
      synchronized(this) {
         if (this.consumers.size() == 0) {
            return;
         }

         this.consumers.remove(consumer);
         if (this.consumers.size() == 0) {
            stateTransition = true;
         }
      }

      if (stateTransition) {
         this.transitionChecker.check();
      }

   }

   public synchronized long getConsumersCurrentCount() {
      return (long)this.consumers.size();
   }

   public synchronized long getConsumersHighCount() {
      return (long)this.consumersHigh;
   }

   public synchronized long getConsumersTotalCount() {
      return this.consumersTotal;
   }

   public BEBrowser createBrowser(BESession session, String selector) throws JMSException {
      throw new weblogic.jms.common.JMSException("Not implemented");
   }

   public synchronized Map getConsumersClone() {
      HashMap ret = new HashMap(this.consumers.size());
      Iterator i = this.consumers.iterator();

      while(i.hasNext()) {
         Object o = i.next();
         ret.put(o, o);
      }

      return ret;
   }

   protected void closeAllConsumers(String reason) {
      ArrayList consumersCopy;
      synchronized(this) {
         consumersCopy = new ArrayList(this.consumers);
         this.consumers.clear();
      }

      Iterator i = consumersCopy.iterator();

      while(i.hasNext()) {
         try {
            BEConsumerImpl consumer = (BEConsumerImpl)i.next();
            consumer.closeWithError(reason);
         } catch (JMSException var5) {
         }
      }

   }

   protected void closeAllBrowsers(String reason) {
   }

   public final boolean isStarted() {
      return this.checkStateFlag(4);
   }

   public final boolean isShutdown() {
      return this.checkStateFlag(120);
   }

   public final synchronized boolean isShutdownOrSuspended() {
      return this.state == 0 || this.checkStateFlagFast(123);
   }

   public final synchronized boolean isShutdownOrSuspending() {
      return this.state == 0 || this.checkStateFlag(106);
   }

   public final void checkShutdown(String operation) throws JMSException {
      if (this.isShutdown()) {
         throw new IllegalStateException(this.getOfflineStateMessage(operation));
      }
   }

   public final void checkShutdownOrSuspended(String operation) throws JMSException {
      if (this.isShutdownOrSuspended()) {
         throw new IllegalStateException(this.getOfflineStateMessage(operation));
      }
   }

   public final void checkShutdownOrSuspendedNeedLock(String operation) throws JMSException {
      if (this.state == 0 || this.checkStateFlagFast(123)) {
         throw new IllegalStateException(this.getOfflineStateMessage(operation));
      }
   }

   public final synchronized void markSuspending() {
      if (!this.checkStateFlag(121)) {
         this.state = 2;
      }

   }

   public final int getStateValue() {
      return this.state;
   }

   public final void setStateValue(int state) {
      this.state = state;
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("New state for " + this.name + ": " + JMSService.getStateName(state));
      }

   }

   public final synchronized String getState() {
      int i = this.state;
      if ((i & 4) != 0) {
         i &= -5;
         if (i == 0) {
            i = 4;
         }
      }

      return JMSService.getStateName(i);
   }

   private String getOfflineStateMessage(String operation) {
      StringBuffer stateMessage = new StringBuffer(256);
      if (operation != null) {
         stateMessage.append("Failed to " + operation + " because destination " + this.name);
      } else {
         stateMessage.append("Destination " + this.name);
      }

      if (this.deleted) {
         stateMessage.append(" is deleted.");
      } else if (this.isShutdown()) {
         stateMessage.append(" is shutdown.");
      } else if (this.isProductionPaused()) {
         stateMessage.append(" is being paused for production.");
      } else if (this.isInsertionPaused()) {
         stateMessage.append(" is being paused for insertion.");
      } else if (this.isConsumptionPaused()) {
         stateMessage.append(" is being paused for consumption.");
      } else {
         stateMessage.append(" is suspended.");
      }

      return stateMessage.toString();
   }

   public void deleteTempDestination() throws JMSException {
      synchronized(this) {
         if (!this.consumers.isEmpty()) {
            throw new weblogic.jms.common.JMSException("Temporary destination cannot be deleted, it still has consumers");
         }

         this.setDeleted(true);
      }

      if (this.runtimeMBean != null) {
         try {
            PrivilegedActionUtilities.unregister(this.runtimeMBean, kernelId);
         } catch (ManagementException var3) {
            JMSLogger.logErrorUnregisteringBackEndDestination(this.backEnd.getName(), this, var3);
            throw new weblogic.jms.common.JMSException("Error deleting temporary destination", var3);
         }
      }

   }

   public final void markShuttingDown() {
      boolean mustUnregister = false;
      synchronized(this) {
         if ((this.runtimeMBean != null && this.runtimeMBean.isRegistered() || !this.isTemporary()) && (this.getStateValue() & 3) == 0) {
            mustUnregister = true;
            this.setStateValue(8);
         }
      }

      if (this.runtimeMBean != null && mustUnregister) {
         try {
            PrivilegedActionUtilities.unregister(this.runtimeMBean, kernelId);
         } catch (ManagementException var6) {
         }
      }

      synchronized(this) {
         if (!this.isShutdownOrSuspended()) {
            this.setStateValue(8);
         }

      }
   }

   public synchronized boolean isDeleted() {
      return this.deleted;
   }

   public void expireReadersAtShutdown() {
   }

   protected void suspendKernelDestination(int mask) throws JMSException {
      try {
         this.destination.suspend(mask);
      } catch (KernelException var3) {
         throw new weblogic.jms.common.JMSException(var3);
      }
   }

   protected void resumeKernelDestination(int mask) throws JMSException {
      try {
         this.destination.resume(mask);
      } catch (KernelException var3) {
         throw new weblogic.jms.common.JMSException(var3);
      }
   }

   public void pause() {
      try {
         this.pauseProduction();
      } catch (JMSException var2) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Error pausing destination: " + var2);
         }
      }

   }

   public void resume() {
      try {
         this.resumeProduction();
      } catch (JMSException var2) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Error resuming destination: " + var2);
         }
      }

   }

   public boolean isPaused() {
      return this.isProductionPaused();
   }

   public void pauseProduction() throws JMSException {
      this.pauseProduction(true);
   }

   public void pauseProduction(boolean log) throws JMSException {
      if (!this.isProductionPaused()) {
         String reason = "Destination " + this.name + " has paused all send requests";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(512);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onProductionPauseChange(this);
         }

         if (log) {
            JMSLogger.logProductionPauseOfDestination(this.name);
         }
      }

   }

   public void resumeProduction() throws JMSException {
      this.resumeProduction(true);
   }

   public void resumeProduction(boolean log) throws JMSException {
      if (this.isProductionPaused()) {
         String reason = "Destination " + this.name + " is resuming all send requests";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(1024);
         this.clearStateFlag(1536);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onProductionPauseChange(this);
         }

         if (log) {
            JMSLogger.logProductionResumeOfDestination(this.name);
         }
      }

   }

   public boolean isProductionPaused() {
      return this.checkStateFlag(512);
   }

   public String getProductionPausedState() {
      return this.checkStateFlag(512) ? "Production-Paused" : "Production-Enabled";
   }

   public void pauseInsertion() throws JMSException {
      if (!this.isInsertionPaused()) {
         String reason = "Destination " + this.name + " has paused message insertion";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(2048);
         this.suspendKernelDestination(4);
         this.clearStateFlag(2048);
         this.setStateFlag(4096);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onInsertionPauseChange(this);
         }

         JMSLogger.logInsertionPauseOfDestination(this.name);
      }

   }

   public void resumeInsertion() throws JMSException {
      if (this.isInsertionPaused()) {
         String reason = "Destination " + this.name + " is resuming all message insertion";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(8192);
         this.resumeKernelDestination(4);
         this.clearStateFlag(12288);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onInsertionPauseChange(this);
         }

         JMSLogger.logInsertionResumeOfDestination(this.name);
      }

   }

   public boolean isInsertionPaused() {
      return this.checkStateFlag(6144);
   }

   public String getInsertionPausedState() {
      if (this.checkStateFlag(2048)) {
         return "Insertion-Pausing";
      } else {
         return this.checkStateFlag(4096) ? "Insertion-Paused" : "Insertion-Enabled";
      }
   }

   public void pauseConsumption() throws JMSException {
      this.pauseConsumption(true);
   }

   public void pauseConsumption(boolean log) throws JMSException {
      if (!this.isConsumptionPaused()) {
         String reason = "Destination " + this.name + " is paused for consumption";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(16384);
         this.suspendKernelDestination(2);
         this.clearStateFlag(16384);
         this.setStateFlag(32768);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onConsumptionPauseChange(this);
         }

         if (log) {
            JMSLogger.logConsumptionPauseOfDestination(this.name);
         }
      }

   }

   public void resumeConsumption() throws JMSException {
      this.resumeConsumption(true);
   }

   public void resumeConsumption(boolean log) throws JMSException {
      if (this.isConsumptionPaused()) {
         String reason = "Destination " + this.name + " is resuming for consumption";
         this.checkShutdownOrSuspended(reason);
         this.setStateFlag(65536);
         this.resumeKernelDestination(2);
         this.clearStateFlag(98304);
         Iterator iter = this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onConsumptionPauseChange(this);
         }

         if (log) {
            JMSLogger.logConsumptionResumeOfDestination(this.name);
         }
      }

   }

   public boolean isConsumptionPaused() {
      return this.checkStateFlag(49152);
   }

   public String getConsumptionPausedState() {
      if (this.checkStateFlag(16384)) {
         return "Consumption-Pausing";
      } else {
         return this.checkStateFlag(32768) ? "Consumption-Paused" : "Consumption-Enabled";
      }
   }

   public int invoke(Request request) throws JMSException {
      switch (request.getMethodId()) {
         case 12052:
            return this.wrappedSend((BEProducerSendRequest)request);
         case 17684:
            return this.tranForward((BEForwardRequest)request);
         case 17940:
            return this.uooUpdate(request);
         default:
            throw new weblogic.jms.common.JMSException("No such method " + this.getClass().getName() + ".<" + request.getMethodId() + ">");
      }
   }

   private void sendInitialize(BEProducerSendRequest request) throws JMSException {
      MessageTimeStamp.record(2, request.getMessage());
      if ((this.state & 512) != 0) {
         throw new IllegalStateException("Destination " + this.name + " is paused for new message production");
      } else {
         this.checkPermission(request);
         this.checkShutdownOrSuspendedNeedLock("send message");
         MessageImpl message = request.getMessage();
         request.setWorkManager(this.getBackEnd().getWorkManager());
         if (message.isForwardable() || this.attachSenderMode != 2 && (!message.isJMSXUserIDRequested() || this.attachSenderMode == 1)) {
            if (this.attachSenderMode == 1) {
               message.setJMSXUserID((String)null);
            }
         } else {
            message.setJMSXUserID(JMSSecurityHelper.getSimpleAuthenticatedName());
         }

         message.setJMSDestinationImpl(this.destinationImpl);

         assert request.getSequence() == null : "START state with SEQUENCE";

         request.setSequence(this.findOrCreateKernelSequence(request.getMessage()));
         if (request.getMessage().getUnitOfOrder() == null) {
            String uoo = this.getDefaultUnitOfOrder();
            if (uoo != null) {
               request.getMessage().setUnitOfOrderName(uoo);
            }
         }

      }
   }

   protected boolean clientSendResumeNeedsNewThread() {
      return false;
   }

   public Sequence findOrCreateKernelSequence(MessageImpl message) throws JMSException {
      if (this.hasNoSequence(message)) {
         return null;
      } else {
         String sequenceName = this.getSequenceName(message);
         int sequenceMode = this.getSequenceMode(message);

         try {
            return this.releasingSequence(message.getControlOpcode()) ? this.destination.findSequence(sequenceName) : this.destination.findOrCreateSequence(sequenceName, sequenceMode);
         } catch (KernelException var5) {
            throw new weblogic.jms.common.JMSException(var5);
         }
      }
   }

   private boolean hasNoSequence(MessageImpl message) throws JMSException {
      return this.getSequenceMode(message) == 0;
   }

   private boolean releasingSequence(int opcode) {
      return opcode != 0 && 131072 >= opcode;
   }

   protected String getSequenceName(WLMessage message) throws JMSException {
      String sequenceName;
      if (this.getSequenceMode(message) == 8) {
         sequenceName = message.getStringProperty("JMS_BEA_UnitOfWork");
      } else {
         sequenceName = message.getSAFSequenceName();
      }

      assert sequenceName != null;

      return sequenceName;
   }

   public boolean isUOWDestination() {
      return this.isUOWDestination;
   }

   public void setUnitOfWorkHandlingPolicy(String policy) {
      this.isUOWDestination = policy.equals("SingleMessageDelivery");
   }

   public void setIncompleteWorkExpirationTime(int expirationTime) {
      this.incompleteWorkExpirationTime = expirationTime;
   }

   public int getIncompleteWorkExpirationTime() {
      return this.incompleteWorkExpirationTime;
   }

   protected int getSequenceMode(WLMessage message) throws JMSException {
      if (this.isUOWDestination && message.propertyExists("JMS_BEA_UnitOfWork") && message.getStringProperty("JMS_BEA_UnitOfWork") != null) {
         if (!message.propertyExists("JMS_BEA_UnitOfWorkSequenceNumber")) {
            throw new BadSequenceNumberException("A JMS Unit of Work message must have a sequence number");
         }

         if (!message.getBooleanProperty("JMS_BEA_IsUnitOfWorkEnd") || !(message instanceof ObjectMessage) || !(((ObjectMessage)message).getObject() instanceof ArrayList)) {
            return 8;
         }

         if (message.getSAFSeqNumber() == 0L) {
            return 0;
         }
      }

      boolean hasSAFSeqNumber = message.getSAFSeqNumber() != 0L;
      return this.getSequenceMode(hasSAFSeqNumber, this.checkForwarded(message), ((MessageImpl)message).isSAFNeedReorder());
   }

   protected int getSequenceMode(boolean hasSAFSeqNumber, boolean forwarded) {
      return this.getSequenceMode(hasSAFSeqNumber, forwarded, false);
   }

   protected int getSequenceMode(boolean hasSAFSeqNumber, boolean forwarded, boolean needReorder) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Checking Sequence Mode:  needReorcer = " + needReorder + " forwarded = " + forwarded);
      }

      if (!hasSAFSeqNumber || !forwarded && !needReorder) {
         return 0;
      } else {
         return forwarded && !needReorder ? 2 : 4;
      }
   }

   protected boolean checkForwarded(WLMessage message) {
      boolean isForwarded = ((MessageImpl)message).isForwarded();
      if (message.getDDForwarded() && !isForwarded) {
         throw new AssertionError("DD Forwarded msg is not marked as forwarded");
      } else {
         return isForwarded;
      }
   }

   protected boolean isReorderNeeded(WLMessage message) {
      return ((MessageImpl)message).isSAFNeedReorder();
   }

   private boolean sendIssueMessage(BEProducerSendRequest request) throws JMSException {
      MessageImpl message = request.getMessage();
      JMSProducerSendResponse sendResponse = request.setupSendResponse();
      if (!message.isOldMessage() && sendResponse != null) {
         sendResponse.set90StyleMessageId();
      }

      this.applyOverrides(message, sendResponse);
      message.setConnectionId(request.getConnectionId());
      if (request.getSessionId() != null) {
         message.setSessionId(request.getSessionId());
      }

      message.setJMSDestinationImpl((DestinationImpl)null);
      if (message.getAdjustedDeliveryMode() == 2 && !this.backEnd.isStoreEnabled()) {
         if (!this.backEnd.isAllowsPersistentDowngrade()) {
            throw new JMSException(JMSExceptionLogger.logNoPersistentMessages(this.name, this.backEnd.getName()));
         }

         message.setAdjustedDeliveryMode(1);
         message.setJMSDeliveryMode(1);
         if (sendResponse != null) {
            sendResponse.setDeliveryMode(1);
         }
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Putting new message " + message.getId() + " on " + this.name);
      }

      request.setState(1103);
      BEExtension stableExtension = this.destExtension;
      int opcode = request.getMessage().getControlOpcode();
      if (opcode != 0) {
         assert stableExtension == null || opcode == 196608 : "opcode value is " + Integer.toHexString(opcode);

         return false;
      } else {
         message.setStoreCompression(this.getBackEnd().isStoreMessageCompressionEnabled());
         message.setPagingCompression(this.getBackEnd().isPagingMessageCompressionEnabled());
         message.setCompressionOption(this.getBackEnd().getMessageCompressionOptionsProp());
         message.setCompressionOptionOverride(this.getBackEnd().getMessageCompressionOptionsOverrideProp());

         try {
            KernelRequest kernelRequest = this.destination.send(message, this.createSendOptions(request.getSendTimeout(), request.getSequence(), request.getMessage()));
            if (kernelRequest == null) {
               MessageTimeStamp.record(8, message);
               return false;
            } else {
               request.setKernelRequest(kernelRequest);
               synchronized(kernelRequest) {
                  if (kernelRequest.hasResult()) {
                     MessageTimeStamp.record(8, message);
                     return false;
                  } else {
                     request.needOutsideResult();
                     kernelRequest.addListener(new DispatcherCompletionListener(request), this.backEnd.getWorkManager());
                     MessageTimeStamp.record(8, message);
                     return true;
                  }
               }
            }
         } catch (QuotaException var10) {
            ResourceAllocationException rae = new ResourceAllocationException(var10.toString());
            rae.setLinkedException(var10);
            throw rae;
         } catch (weblogic.messaging.kernel.IllegalStateException var11) {
            IllegalStateException ile = new IllegalStateException("Failed to send message to the destination " + this.name + ": " + var11.getMessage());
            ile.setLinkedException(var11);
            throw ile;
         } catch (KernelException var12) {
            if (var12.getCause() != null && var12.getCause() instanceof JMSException) {
               throw (JMSException)var12.getCause();
            } else {
               throw new weblogic.jms.common.JMSException(var12);
            }
         }
      }
   }

   public SendOptions createSendOptions(long timeout, Sequence sequence, MessageImpl message) {
      SendOptions sendOptions = this.createSendOptionsInternal(timeout, sequence, message);
      sendOptions.setDeliveryTime(message.getDeliveryTime());
      sendOptions.setNoDeliveryDelay(message.getJMSTimestamp() == message.getDeliveryTime());
      return sendOptions;
   }

   protected SendOptions createSendOptionsInternal(long timeout, Sequence sequence, MessageImpl message) {
      SendOptions sendOptions = new SendOptions();
      sendOptions.setPersistent(message.getAdjustedDeliveryMode() == 2);

      try {
         if (this.isUOWDestination && message.propertyExists("JMS_BEA_UnitOfWork")) {
            if (this.getSequenceMode(message) == 8 && message.getExpirationTime() == 0L) {
               sendOptions.setExpirationTime(Long.MAX_VALUE);
            } else {
               sendOptions.setExpirationTime(message.getExpirationTime());
            }
         } else {
            sendOptions.setExpirationTime(message.getExpirationTime());
         }
      } catch (Exception var7) {
      }

      sendOptions.setRedeliveryLimit(message.getRedeliveryLimit());
      sendOptions.setGroup(message.getGroup());
      if (timeout != 0L) {
         sendOptions.setTimeout(timeout);
      }

      if (sequence != null) {
         sendOptions.setSequence(sequence);
         if (sequence.getMode() != 1) {
            sendOptions.setSequenceNum(message.getSAFSeqNumber());
         }
      }

      return sendOptions;
   }

   private void sendComplete(BEProducerSendRequest request) throws JMSException {
      request.restoreResources(true);

      try {
         if (request.getKernelRequest() != null) {
            request.getKernelRequest().getResult();
         }
      } catch (QuotaException var4) {
         ResourceAllocationException rae = new ResourceAllocationException(var4.toString());
         rae.setLinkedException(var4);
         throw rae;
      } catch (weblogic.messaging.kernel.IllegalStateException var5) {
         IllegalStateException ile = new IllegalStateException("Destination " + this.name + " is paused for new message production");
         ile.setLinkedException(var5);
         throw ile;
      } catch (KernelException var6) {
         if (var6.getCause() != null && var6.getCause() instanceof JMSException) {
            throw (JMSException)var6.getCause();
         }

         throw new weblogic.jms.common.JMSException(var6);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Message " + request.getMessage().getId() + " successfully enqueued");
      }

      JMSProducerSendResponse sendResponse = null;
      sendResponse = (JMSProducerSendResponse)request.getResult();
      if (this.isFlowControlRequired()) {
         sendResponse.setNeedsFlowControl(true);
         sendResponse.setFlowControlTime(-1L);
      }

   }

   private int wrappedSend(BEProducerSendRequest request) throws JMSException {
      if (request.isJMSAsyncSend() && !DispatcherImpl.FASTDISPATCH) {
         throw new JMSException("JMS asynchronous send is not supported when JMSFastDispatchEnabled=false (back-end)");
      } else {
         boolean sendThrew = true;
         int state = 1104;

         try {
            state = this.send(request);
            sendThrew = false;
         } finally {
            if (sendThrew) {
               request.restoreResources(false);
            } else if (state == Integer.MAX_VALUE) {
               request.restoreResources(true);
            }

         }

         return state;
      }
   }

   private int send(BEProducerSendRequest request) throws JMSException {
      while(true) {
         switch (request.getState()) {
            case 0:
               request.setState(1102);
               this.sendInitialize(request);
               BEExtension stableExtension = this.destExtension;
               if (stableExtension == null) {
                  break;
               }

               stableExtension.sendExtension(request);
               synchronized(request) {
                  int nextState = request.getState();
                  if (nextState != 1102) {
                     return nextState;
                  }
                  break;
               }
            case 1101:
               JMSException e = new JMSException("BEDestinationImpl BEExtension.SEND_WAIT_FOR_COMPLETE");
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug(e.getMessage(), e);
               }

               throw e;
            case 1102:
               if (!this.sendIssueMessage(request)) {
                  break;
               }

               return 1103;
            case 1103:
               this.sendComplete(request);
               request.setState(Integer.MAX_VALUE);
               return Integer.MAX_VALUE;
            case Integer.MAX_VALUE:
               return Integer.MAX_VALUE;
            default:
               throw new AssertionError("Invalid state " + request.getState());
         }
      }
   }

   private int tranForward(BEForwardRequest request) throws JMSException {
      this.checkPermission(request);
      this.checkShutdownOrSuspendedNeedLock("forward message");
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Received a tranForward request for " + request.getSize() + " messages on " + this.name);
      }

      Transaction localTransaction = TransactionHelper.getTransactionHelper().getTransaction();
      if (localTransaction == null) {
         throw new weblogic.jms.common.JMSException("tranForward was invoked without a transaction");
      } else {
         MessageImpl message;
         do {
            message = request.getCurrentRequest().getMessage();
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("BEDestinationImpl.tranForward() message " + message + " deliveryCount " + message.getDeliveryCount());
            }

            request.incrementPosition();
            if (!(this instanceof BEQueueImpl)) {
               message.setDDForwarded(true);
            }

            if (this.applyOverridesOnDQForward) {
               this.applyOverrides(message, (JMSProducerSendResponse)null);
            }

            try {
               SendOptions sendOptions = this.createSendOptions(0L, (Sequence)null, message);
               if (message.getDeliveryCount() > 0) {
                  sendOptions.setDeliveryCount(message.getDeliveryCount());
               }

               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("BEDestinationImpl.tranForward, got message " + message + " with deliverycount " + message.getDeliveryCount() + " send options " + sendOptions.getDeliveryCount());
               }

               message.setStoreCompression(this.getBackEnd().isStoreMessageCompressionEnabled());
               message.setPagingCompression(this.getBackEnd().isPagingMessageCompressionEnabled());
               message.setCompressionOption(this.getBackEnd().getMessageCompressionOptionsProp());
               message.setCompressionOptionOverride(this.getBackEnd().getMessageCompressionOptionsOverrideProp());
               KernelRequest kernelRequest = this.destination.send(message, sendOptions);
               if (kernelRequest != null) {
                  synchronized(kernelRequest) {
                     if (!kernelRequest.hasResult()) {
                        request.needOutsideResult();
                        kernelRequest.addListener(new DispatcherCompletionListener(request), this.backEnd.getWorkManager());
                        return request.getState();
                     }

                     kernelRequest.getResult();
                  }
               }
            } catch (KernelException var9) {
               throw new weblogic.jms.common.JMSException(var9);
            }
         } while(request.getPosition() < request.getSize());

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Processed all forwarded messages on " + this.name);
         }

         request.setResult(new JMSProducerSendResponse(message.getId()));
         request.setState(Integer.MAX_VALUE);
         return Integer.MAX_VALUE;
      }
   }

   private int uooUpdate(Request invocableRequest) throws JMSException {
      BEOrderUpdateRequest request = (BEOrderUpdateRequest)invocableRequest;

      try {
         this.backEnd.findOrCreateServerInfo(request.getKey()).cachedRemove(request.getKey(), request.getOldMember(), 584);
      } catch (PathHelper.PathServiceException var4) {
         PathHelper.PathSvc.debug(var4.getMessage(), var4);
         throw new weblogic.jms.common.JMSException(var4);
      } catch (NamingException var5) {
         PathHelper.PathSvc.debug(var5.getMessage(), var5);
         throw new weblogic.jms.common.JMSException(var5);
      }

      invocableRequest.setResult(VoidResponse.THE_ONE);
      return Integer.MAX_VALUE;
   }

   static void addPropertyFlags(Destination destination, String property, int flags) throws JMSException {
      synchronized(destination) {
         Integer flagsInteger = (Integer)destination.getProperty(property);
         if (flagsInteger == null) {
            flagsInteger = new Integer(flags);
         } else {
            if ((flagsInteger & flags) == flags) {
               return;
            }

            flagsInteger = new Integer(flagsInteger | flags);
         }

         try {
            destination.setProperty(property, flagsInteger);
         } catch (KernelException var7) {
            throw new weblogic.jms.common.JMSException(var7);
         }

      }
   }

   protected static void removePropertyFlags(Destination destination, String property, int flags) throws JMSException {
      synchronized(destination) {
         Integer flagsInteger = (Integer)destination.getProperty(property);
         if (flagsInteger != null && (flagsInteger & flags) != 0) {
            flagsInteger = new Integer(flagsInteger & ~flags);

            try {
               destination.setProperty(property, flagsInteger);
            } catch (KernelException var7) {
               throw new weblogic.jms.common.JMSException(var7);
            }

         }
      }
   }

   public void lowMemory() throws JMSException {
      this.suspendMessageLogging();
   }

   public void normalMemory() throws JMSException {
      this.resumeMessageLogging();
   }

   public final void adminDeletion() {
      BackEnd backEnd = this.getBackEnd();
      synchronized(backEnd.getDestinationDeletionLock()) {
         synchronized(this.getConfigurationLock()) {
            synchronized(this) {
               try {
                  backEnd.getJmsService().checkShutdownOrSuspended("delete Destination");
                  backEnd.checkShutdownOrSuspended("delete Destination");
               } catch (JMSException var12) {
                  return;
               }

               this.setDeleted(true);
               Map consumersCopy = this.getConsumersClone();
               backEnd.removeDestination(this);
               Iterator itr = consumersCopy.values().iterator();

               while(itr.hasNext()) {
                  try {
                     BEConsumerCommon consumer = (BEConsumerCommon)itr.next();
                     if (consumer.isDurable() && !consumer.isActive()) {
                        consumer.delete(false, false);
                     }
                  } catch (JMSException var11) {
                  }
               }

               consumersCopy.clear();
            }
         }
      }
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      JMSDiagnosticImageSource.dumpDestinationImpl(xsw, this.destinationImpl);
      xsw.writeAttribute("state", JMSService.getStateName(this.state));
      xsw.writeAttribute("creationTime", String.valueOf(this.getCreationTime()));
      xsw.writeAttribute("kernelDestinationName", this.destination.getName());
   }

   public void removeProducer(JMSID producerId) {
      BEProducerSecurityParticipantImpl producer;
      synchronized(this) {
         producer = (BEProducerSecurityParticipantImpl)this.producers.remove(producerId);
      }

      if (producer != null) {
         this.backEnd.getJmsService().unregisterSecurityParticipant(producer);
      }

   }

   private void checkPermission(Request rqst) throws JMSSecurityException {
      JMSID producerId = null;
      if (rqst instanceof BEProducerSendRequest) {
         producerId = ((BEProducerSendRequest)rqst).getProducerId();
      }

      if (producerId == null) {
         this.verify((BEForwardRequest)rqst);
      } else {
         if (!this.backEnd.getJmsService().isSecurityCheckerStop()) {
            BEProducerSecurityParticipantImpl producer;
            synchronized(this) {
               producer = (BEProducerSecurityParticipantImpl)this.producers.get(producerId);
            }

            AuthenticatedSubject currentSubject = JMSSecurityHelper.getCurrentSubject();
            if (producer == null) {
               this.getJMSDestinationSecurity().checkSendPermission(currentSubject);
               producer = new BEProducerSecurityParticipantImpl(producerId, this, 5, currentSubject);
               synchronized(this) {
                  this.producers.put(producer.getProducerId(), producer);
               }

               this.backEnd.getJmsService().registerSecurityParticipant(this.getJMSDestinationSecurity().getJMSResourceForSend(), producer);
            } else if (producer.getSubject() != currentSubject && (producer.getSubject() == null || !producer.getSubject().equals(currentSubject))) {
               this.getJMSDestinationSecurity().checkSendPermission(currentSubject);
               producer.setSubject(currentSubject);
            }
         } else {
            this.getJMSDestinationSecurity().checkSendPermission();
         }

      }
   }

   private boolean checkMember(int securityMode) {
      int maxTries = 16;

      boolean ret;
      while(true) {
         ret = DDManager.handlerHasSecurityModeByMemberName(this.getDestinationImpl().getName(), securityMode);
         if (ret) {
            break;
         }

         --maxTries;
         if (maxTries == 0) {
            break;
         }

         try {
            Thread.currentThread();
            Thread.sleep(1000L);
         } catch (InterruptedException var5) {
         }
      }

      return ret;
   }

   private void verify(BEForwardRequest request) throws JMSSecurityException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Verifying forward request: securityMode= " + request.getSecurityMode() + " current user= " + JMSSecurityHelper.getSimpleAuthenticatedName());
      }

      String exceptionText = null;
      String debugText = "";

      try {
         switch (request.getSecurityMode()) {
            case 11:
            case 13:
               debugText = "REMOTE_SIGNED";
               if (!request.verify(this.signatureSecret)) {
                  exceptionText = "Verification failed.";
               }
               break;
            case 12:
               debugText = "REMOTE_UNSIGNED";
               if (!this.checkMember(12)) {
                  exceptionText = "Not initialized for perf mode, will retry later.";
               }
               break;
            case 14:
               debugText = "REMOTE_KERNELID";
               if (!this.checkMember(14)) {
                  exceptionText = "Not initialized for remote user, will retry later.";
               } else if (!WLSPrincipals.isKernelUsername(JMSSecurityHelper.getSimpleAuthenticatedName())) {
                  exceptionText = "Access denied for remote user, user=" + JMSSecurityHelper.getSimpleAuthenticatedName();
               }
               break;
            case 15:
               debugText = "LOCAL_KERNELID";
               if (!WLSPrincipals.isKernelUsername(JMSSecurityHelper.getSimpleAuthenticatedName())) {
                  exceptionText = "Access denied for local user, user=" + JMSSecurityHelper.getSimpleAuthenticatedName();
               }
               break;
            default:
               debugText = "default";
               exceptionText = "Unexpected mode.";
         }
      } finally {
         if (exceptionText != null) {
            exceptionText = JMSExceptionLogger.logDDForwardRequestDeniedLoggable(exceptionText, this.getName()).getMessage();
            if (exceptionText == null) {
               exceptionText = "Access denied.";
            }
         }

         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            System.out.println("CHECKING FOR " + debugText + ", " + request.getSecurityMode() + ", " + JMSSecurityHelper.getSimpleAuthenticatedName() + ", exc=" + exceptionText);
         }

         if (exceptionText != null) {
            throw new JMSSecurityException(exceptionText);
         }

      }

   }

   public void prepareSignature() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      String clusterName = this.getClusterName(runtimeAccess);
      if (clusterName != null) {
         this.signatureSecret = JMSServerUtilities.generateSecret(domainName + clusterName + this.getName() + this.destinationImpl.getId());
      }

   }

   private final class TransitionChecker implements NakedTimerListener {
      private static final int HISTORY_TOTAL = 20;
      private static final long PERIOD = 10000L;
      private static final long DELAY = 2000L;
      private Timer timer;
      private final Queue transitionHistory;

      private TransitionChecker() {
         this.timer = null;
         this.transitionHistory = new ArrayDeque();
      }

      public synchronized void timerExpired(Timer timer) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Timer expired to notify has consumer state transition.");
         }

         this.sendNotification();
         this.timer = null;
      }

      public synchronized void cancelTimer() {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

      }

      public synchronized void check() {
         long oldestTransitionTime = 0L;
         this.transitionHistory.add(System.currentTimeMillis());
         if (this.transitionHistory.size() > 20) {
            oldestTransitionTime = (Long)this.transitionHistory.remove();
         }

         if (System.currentTimeMillis() - oldestTransitionTime > 10000L) {
            this.sendNotification();
            this.cancelTimer();
         } else if (this.timer == null) {
            this.timer = BEDestinationImpl.this.getBackEnd().getTimerManager().schedule(this, 2000L);
         }

      }

      private void sendNotification() {
         Iterator iter = BEDestinationImpl.this.statusListeners.listIterator();

         while(iter.hasNext()) {
            ((DestinationStatusListener)iter.next()).onHasConsumersStatusChange(BEDestinationImpl.this);
         }

      }

      // $FF: synthetic method
      TransitionChecker(Object x1) {
         this();
      }
   }
}
