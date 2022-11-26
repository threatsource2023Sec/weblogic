package weblogic.jms.backend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import weblogic.jms.JMSLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.MultiListener;

final class BEMultiSender implements MultiListener {
   private static final boolean debug = false;

   public void multiDeliver(Message m, List destList) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Pushing message " + ((MessageImpl)m).getJMSMessageID() + " to " + destList.size() + " consumers");
      }

      Iterator iterator = destList.iterator();
      HashMap requestMap = new HashMap(4);

      while(iterator.hasNext()) {
         MultiListener.DeliveryInfo info = (MultiListener.DeliveryInfo)iterator.next();
         BEConsumerImpl consumer = (BEConsumerImpl)info.getListener();

         try {
            consumer.checkPermission(true, false);
         } catch (JMSSecurityException var18) {
            continue;
         }

         MessageElement element = info.getMessageElement();
         MessageImpl message = (MessageImpl)m;
         BESessionImpl session = consumer.getSession();
         element.setUserSequenceNum(session.getNextSequenceNumber());
         element.setUserData(consumer);
         boolean clientResponsible = consumer.allowsImplicitAcknowledge();
         boolean implicitAcknowledge = clientResponsible || session.getAcknowledgeMode() == 4;
         JMSPushEntry pushEntry = consumer.createPushEntry(element, clientResponsible, implicitAcknowledge);
         JMSDispatcher dispatcher = session.getConnection().getDispatcher();
         JMSPushRequest pushRequest = (JMSPushRequest)requestMap.get(dispatcher);
         if (pushRequest == null) {
            pushRequest = new JMSPushRequest(13, session.getSequencerId(), message, pushEntry);
            requestMap.put(dispatcher, pushRequest);
         } else {
            pushRequest.setInvocableType(1);
            pushRequest.addPushEntry(pushEntry);
         }

         if (!implicitAcknowledge) {
            consumer.adjustUnackedCount(1);
            session.addPendingMessage(element, consumer);
         }

         if (implicitAcknowledge && !consumer.isKernelAutoAcknowledge()) {
            try {
               KernelRequest req = consumer.getKernelQueue().acknowledge(element);
               if (req != null) {
                  req.getResult();
               }
            } catch (KernelException var17) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug("Unexpected exception while implicitly acknowledging: " + var17, var17);
               }
            }
         }
      }

      Iterator entries = requestMap.entrySet().iterator();

      while(entries.hasNext()) {
         Map.Entry entry = (Map.Entry)entries.next();
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Pushing entries to dispatcher " + entry.getKey());
         }

         try {
            JMSServerUtilities.anonDispatchNoReply((JMSPushRequest)entry.getValue(), (JMSDispatcher)entry.getKey(), true);
         } catch (JMSException var16) {
            JMSLogger.logErrorPushingMessage(var16.toString(), var16);
         }
      }

   }
}
