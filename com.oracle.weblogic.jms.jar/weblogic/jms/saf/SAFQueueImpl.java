package weblogic.jms.saf;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.backend.BEBrowser;
import weblogic.jms.backend.BEConnectionConsumerImpl;
import weblogic.jms.backend.BEConsumerCreateRequest;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.BEDestinationSecurityImpl;
import weblogic.jms.backend.BEQueueImpl;
import weblogic.jms.backend.BESession;
import weblogic.jms.backend.BESessionImpl;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.WLMessage;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;
import weblogic.messaging.kernel.Sequence;

public final class SAFQueueImpl extends BEQueueImpl {
   private String errorHandlingName;
   private ErrorHandler errorHandler;
   private boolean updatedErrorHandler;
   private SAFAgentAdmin safAgent;
   private SAFRemoteEndpointRuntimeMBean safRuntimeMBean;
   private long timeToLiveDefault = -1L;
   private int nonPersistentQos = 1;
   private int persistentQos = 2;
   private static transient AuditableThreadLocal consumersAllowedThreadLocal = AuditableThreadLocalFactory.createThreadLocal();
   private String refreshedSequenceName;

   public SAFQueueImpl(SAFAgentAdmin safAgent, String name, boolean temporary, EntityName entityName, String destinationType, String alternativeName) throws JMSException {
      super(safAgent.getBackEnd(), name, temporary, new BEDestinationSecurityImpl(entityName, destinationType, safAgent.getBackEnd().isClusterTargeted(), (DestinationBean)null), alternativeName);
      this.safAgent = safAgent;
      this.moduleName = entityName.getFullyQualifiedModuleName();
      this.refreshedSequenceName = this.refreshSequenceName(this.alternativeFullyQualifiedDestinationName != null ? this.alternativeFullyQualifiedDestinationName : this.fullyQualifiedDestinationName);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Refreshed SAF sequence name is " + this.refreshedSequenceName);
      }

      Queue kernelQueue = this.getKernelQueue();
      kernelQueue.setSAFImportedDestination(true);
   }

   static void allowCreateConsumer() {
      consumersAllowedThreadLocal.set(Boolean.TRUE);
   }

   static void disallowCreateConsumer() {
      consumersAllowedThreadLocal.set(Boolean.FALSE);
   }

   protected BEConsumerImpl createConsumer(BESessionImpl session, boolean started, BEConsumerCreateRequest createRequest) throws JMSException {
      this.checkConsumption("consumer");
      return super.createConsumer(session, started, createRequest);
   }

   protected BEConnectionConsumerImpl createConnectionConsumer(JMSID id, ServerSessionPool ssp, String clientId, String name, String selector, boolean noLocal, int messagesMaximum, long redeliveryDelay, boolean isDurable, boolean started) throws JMSException {
      this.checkConsumption("connection consumer");
      return super.createConnectionConsumer(id, ssp, clientId, name, selector, noLocal, messagesMaximum, redeliveryDelay, isDurable, started);
   }

   public BEBrowser createBrowser(BESession session, String selector) throws JMSException {
      this.checkConsumption("browser");
      return super.createBrowser(session, selector);
   }

   private void checkConsumption(String op) throws JMSException {
      if (consumersAllowedThreadLocal.get() != Boolean.TRUE) {
         throw new JMSException("Cannot create " + op + " on imported (store-and-forward) destination because it is not allowed to receive messages from an imported destination");
      }
   }

   public void setTimeToLiveDefault(long timeToLiveDefault) {
      this.timeToLiveDefault = timeToLiveDefault;
   }

   protected int getSequenceMode(WLMessage message) {
      int deliveryMode = ((MessageImpl)message).getJMSDeliveryMode();
      if (deliveryMode == 1 && this.nonPersistentQos != 2) {
         return 0;
      } else if (deliveryMode == 2 && this.persistentQos != 2) {
         return 0;
      } else {
         boolean isForwarded = this.checkForwarded(message);
         boolean hasSAFSeqNumber = message.getSAFSeqNumber() != 0L;
         return hasSAFSeqNumber && isForwarded ? super.getSequenceMode(hasSAFSeqNumber, isForwarded) : 1;
      }
   }

   protected String getSequenceName(WLMessage message) {
      String sequenceName = message.getSAFSequenceName();
      if (sequenceName != null) {
         return sequenceName;
      } else {
         String uoo = message.getUnitOfOrder();
         return uoo == null ? this.refreshedSequenceName : this.getKernelQueue().getHashedBasedName(uoo, this.refreshedSequenceName);
      }
   }

   private String versionedName(String sequenceName) {
      SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
      return sequenceName + fmt.format(new Date(this.getCreationTime()));
   }

   public SendOptions createSendOptions(long timeout, Sequence sequence, MessageImpl message) {
      Sequence useSequence = sequence;
      Sequence newSeq = null;
      long saveSAFSeqNum = message.getSAFSeqNumber();
      if (sequence != null && message.getSAFSequenceName() != null && this.checkForwarded(message) && this.safAgent.isSAFMultiHubEnabled()) {
         message.setSAFSequenceName((String)null);
         message.setSAFSeqNumber(0L);

         try {
            newSeq = this.findOrCreateKernelSequence(message);
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("SAF Queue createSendOptions() msg " + message + " override sequence " + newSeq);
            }

            if (newSeq != null) {
               assert newSeq.getMode() == 1;

               message.setSAFSequenceName(newSeq.getName());
               useSequence = newSeq;
               sequence.setOverride(true);
            }
         } catch (JMSException var12) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Failed to find or create override sequence " + var12.toString());
            }
         }
      }

      SendOptions sendOptions = this.createSendOptionsInternal(timeout, useSequence, message);
      if (this.timeToLiveDefault != -1L) {
         if (this.timeToLiveDefault == 0L) {
            sendOptions.setExpirationTime(0L);
         } else {
            long expirationTime = System.currentTimeMillis() + this.timeToLiveDefault;
            sendOptions.setExpirationTime(expirationTime);
         }
      }

      if (sequence != null && sequence.isOverride()) {
         sendOptions.setInboundSequence(sequence);
         sendOptions.setInboundSequenceNum(saveSAFSeqNum);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("SAFQueueImpl.createSendOptions() msg " + message + " setInboundSeq " + sendOptions.getInboundSequence() + " setInboundSeqNum " + sendOptions.getInboundSequenceNum() + " isOverride ");
         }
      }

      Sequence sendSeq = sendOptions.getSequence();
      if (sendSeq != null && sendOptions.getGroup() != null) {
         sendOptions.setGroup(sendSeq.getName());
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("SAFQueueImpl.createSendOptions() msg " + message + " Setting group to " + sendSeq.getName());
         }
      }

      return sendOptions;
   }

   public void expirationTimeReached(RedirectionListener.Info redirectionInfo, boolean alreadyReported) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("SAFQueueImpl's expirationTimeReached() is called");
      }

      this.errorHandler = this.getErrorHandler();
      if (this.errorHandler == null) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Error Handler == null, alreadyReported " + alreadyReported + " loggingEnabled " + this.safAgent.isLoggingEnabled());
         }

         if (!alreadyReported && this.safAgent.isLoggingEnabled()) {
            JMSLogger.logExpiredSAFMessageNoHeaderProperty("'" + redirectionInfo.getMessage().getMessageID() + "'");
         }

         ((SAFRemoteEndpointRuntimeMBeanImpl)this.safRuntimeMBean).updateFailedMessagesCount(1L);
      } else {
         int policy = this.errorHandler.getPolicyAsInt();
         if (policy != 4) {
            ((SAFRemoteEndpointRuntimeMBeanImpl)this.safRuntimeMBean).updateFailedMessagesCount(1L);

            try {
               this.errorHandler.handleFailure(redirectionInfo, this.getBackEnd().getName(), (MessageImpl)redirectionInfo.getMessage());
            } catch (KernelException var5) {
            } catch (JMSException var6) {
            }
         }

      }
   }

   void initializeErrorHandling(String errorHandlingName) {
      this.errorHandlingName = errorHandlingName;
      this.errorHandler = this.getErrorHandler();
      if (this.errorHandler != null) {
         Queue kernelQueue = this.getKernelQueue();

         try {
            if (this.errorHandler.getPolicyAsInt() == 4) {
               kernelQueue.setProperty("IgnoreExpiration", new Boolean(true));
            }
         } catch (KernelException var4) {
         }

      }
   }

   ErrorHandler getErrorHandler() {
      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("Name = " + this.name + " ModuleName = " + this.moduleName + " ErrorHandlingName " + this.errorHandlingName);
      }

      if (this.errorHandlingName == null) {
         return null;
      } else {
         synchronized(this) {
            if (!this.updatedErrorHandler) {
               this.errorHandler = JMSSAFManager.manager.getErrorHandler(JMSBeanHelper.getDecoratedName(this.moduleName, this.errorHandlingName));
               this.updatedErrorHandler = true;
            }
         }

         if (JMSDebug.JMSModule.isDebugEnabled()) {
            JMSDebug.JMSModule.debug("ErrorHandler= " + (this.errorHandler == null ? "null" : this.errorHandler.toString()));
         }

         return this.errorHandler;
      }
   }

   synchronized void setSAFErrorHandlingName(String errorHandlingName) {
      this.errorHandlingName = errorHandlingName;
      this.updatedErrorHandler = false;
   }

   synchronized void setSAFRuntimeMBean(SAFRemoteEndpointRuntimeMBean safRuntimeMBean) {
      this.safRuntimeMBean = safRuntimeMBean;
   }

   void setNonPersistentQos(int nonPersistentQos) {
      this.nonPersistentQos = nonPersistentQos;
   }

   void setPersistentQos(int persistentQos) {
      this.persistentQos = persistentQos;
   }

   protected boolean clientSendResumeNeedsNewThread() {
      return true;
   }

   private String refreshSequenceName(String sequenceName) {
      String reVal = null;
      Queue kernelQueue = this.getKernelQueue();
      Collection sequences = kernelQueue.getSequences();
      Iterator itr = sequences.iterator();
      String latestSequenceName = sequenceName;
      String latestStrippedSequenceName = null;

      while(itr.hasNext()) {
         Sequence sequence = (Sequence)itr.next();
         String seqName = sequence.getName();
         if (seqName.contains(sequenceName) && seqName.compareTo(latestSequenceName) >= 0) {
            if (!seqName.contains("%uV1@")) {
               latestSequenceName = sequence.getName();
               reVal = latestSequenceName;
            } else {
               int index = seqName.indexOf("%uV1@");
               latestStrippedSequenceName = seqName.substring(0, index);
            }
         }

         if (reVal == null && latestStrippedSequenceName != null) {
            reVal = latestStrippedSequenceName;
         }
      }

      if (reVal == null || this.isNewlyCreated()) {
         reVal = this.versionedName(sequenceName);
      }

      return reVal;
   }
}
