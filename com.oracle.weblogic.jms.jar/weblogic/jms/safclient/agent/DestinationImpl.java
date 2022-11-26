package weblogic.jms.safclient.agent;

import java.util.List;
import javax.jms.JMSException;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSMessageExpirationHelper;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.safclient.agent.internal.ErrorHandler;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.kernel.SendOptions;

public final class DestinationImpl extends weblogic.jms.common.DestinationImpl implements RedirectionListener {
   private static final String SERVER_PFX = "client.saf.server.";
   private static final String MODULE_PFX = "client.saf.module.";
   private static final long serialVersionUID = 6099783323740404732L;
   private transient Queue kernelQueue;
   private transient boolean loggingEnabled = true;
   private transient ErrorHandler errorHandler;
   private transient String sequenceName;
   private transient String nonPersistentQOS;

   public DestinationImpl(String paramGroupName, String paramDestinationName, boolean paramIsQueue) {
      super((byte)(paramIsQueue ? 1 : 2));
      JMSEnvironment.getJMSEnvironment();
      String generatedName = JMSEnvironment.generateClientDispatcherName();
      String serverName = "client.saf.server." + generatedName;
      String moduleName = "client.saf.module." + generatedName;
      this.setName(paramDestinationName);
      this.setApplicationName(paramGroupName);
      this.setServerName(serverName);
      this.setModuleName(moduleName);
   }

   public String getCreateDestinationArgument() {
      return AgentManager.constructDestinationName(this.getApplicationName(), this.getName());
   }

   public String toString() {
      return AgentManager.constructDestinationName(this.getApplicationName(), this.getName());
   }

   public void setKernelQueue(Queue paramKernelQueue) {
      this.kernelQueue = paramKernelQueue;
   }

   public void setLoggingEnabled(boolean paramLoggingEnabled) {
      this.loggingEnabled = paramLoggingEnabled;
   }

   public Queue getKernelQueue() {
      return this.kernelQueue;
   }

   public void setErrorHandler(ErrorHandler paramErrorHandler) throws JMSException {
      this.errorHandler = paramErrorHandler;
      if (this.errorHandler != null && this.kernelQueue != null) {
         try {
            this.kernelQueue.setProperty("RedirectionListener", this);
            if (this.errorHandler.getPolicy() == 3) {
               this.kernelQueue.setProperty("IgnoreExpiration", new Boolean(true));
            }
         } catch (KernelException var3) {
            throw new weblogic.jms.common.JMSException(var3);
         }
      }

   }

   private static SendOptions createSendOptions(MessageImpl message) {
      SendOptions retVal = new SendOptions();
      retVal.setPersistent(message.getAdjustedDeliveryMode() == 2);
      retVal.setExpirationTime(message.getExpirationTime());
      retVal.setRedeliveryLimit(message.getRedeliveryLimit());
      return retVal;
   }

   private static void overrideMessageProperties(MessageImpl message, boolean forExpiration) {
      message.setDeliveryTime(0L);
      message._setJMSRedeliveryLimit(-1);
      if (forExpiration) {
         message._setJMSExpiration(0L);
      }

      message.setSAFSequenceName((String)null);
      message.setSAFSeqNumber(0L);
   }

   public void expirationTimeReached(RedirectionListener.Info redirectionInfo, boolean alreadyReported) {
      if (this.errorHandler == null) {
         if (!alreadyReported && this.loggingEnabled) {
            JMSLogger.logExpiredSAFMessageNoHeaderProperty("'" + redirectionInfo.getMessage().getMessageID() + "'");
         }

      } else {
         MessageImpl messageImpl = (MessageImpl)redirectionInfo.getMessage();
         int policy = this.errorHandler.getPolicy();
         switch (policy) {
            case 0:
            case 3:
               return;
            case 1:
               StringBuffer tempProperties = new StringBuffer(256);
               List expirationLoggingJMSHeaders = JMSMessageExpirationHelper.extractJMSHeaderAndProperty(this.errorHandler.getLogFormat(), tempProperties);
               List expirationLoggingUserProperties = JMSMessageExpirationHelper.convertStringToLinkedList(tempProperties.toString());
               JMSMessageExpirationHelper.logExpiredSAFMessage(messageImpl, expirationLoggingJMSHeaders, expirationLoggingUserProperties);
               return;
            case 2:
               DestinationImpl errorDestination = this.errorHandler.getErrorDestination();
               Queue kernelErrorDestination = errorDestination.getKernelQueue();
               overrideMessageProperties(messageImpl, true);
               redirectionInfo.setSendOptions(createSendOptions(messageImpl));
               redirectionInfo.setRedirectDestination(kernelErrorDestination);
               return;
            default:
               throw new AssertionError("Unknown policy: " + policy);
         }
      }
   }

   public void deliveryLimitReached(RedirectionListener.Info redirectionInfo) {
      DestinationImpl errorDestination = this.errorHandler.getErrorDestination();
      if (errorDestination != null) {
         Queue kernelErrorDestination = errorDestination.getKernelQueue();
         MessageImpl messageImpl = (MessageImpl)redirectionInfo.getMessage();
         overrideMessageProperties(messageImpl, false);
         redirectionInfo.setSendOptions(createSendOptions(messageImpl));
         redirectionInfo.setRedirectDestination(kernelErrorDestination);
      }
   }

   public void setSequenceName(String name) {
      this.sequenceName = name;
   }

   public String getSequenceName() {
      return this.sequenceName;
   }

   public void setNonPersistentQOS(String qos) {
      this.nonPersistentQOS = qos;
   }

   public String getNonPersistentQOS() {
      return this.nonPersistentQOS;
   }
}
