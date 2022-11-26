package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.JMSService;
import weblogic.jms.common.BadSequenceNumberException;
import weblogic.jms.common.DuplicateSequenceNumberException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.ObjectMessageImpl;
import weblogic.jms.common.OutOfSequenceRangeException;
import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.UOWCallback;
import weblogic.messaging.kernel.UOWCallbackCaller;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class BEUOWCallback implements UOWCallback, TimerListener {
   private ArrayList messages = new ArrayList();
   private int lastSequenceNumber = Integer.MAX_VALUE;
   private int numberVisible = 0;
   private ObjectMessageImpl oneBigMessage = null;
   private BEDestinationImpl dest = null;
   private UOWCallbackCaller caller;
   private Timer expirationTimer;
   private String name;
   private int oneBigMessageNumber = -1;
   private Set adminDeletedMessages = new HashSet();
   private boolean expired = false;

   public BEUOWCallback(UOWCallbackCaller caller, String name) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Creating BEUOWCallback(UOW) of name " + caller + " on queue " + name);
      }

      this.caller = caller;
      this.name = name;
   }

   public synchronized Message newVisibleMessage(Message kernelMessage) {
      ++this.numberVisible;
      this.removeMarkerFromMessage(kernelMessage);
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Number visible in UOW " + this.caller + " is now " + this.numberVisible + " msg " + kernelMessage.getMessageID());
      }

      if (this.numberVisible != this.lastSequenceNumber + 1) {
         return null;
      } else {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("UOW complete " + this.caller);
         }

         MessageImpl lastMessage = (MessageImpl)this.messages.get(this.lastSequenceNumber);
         this.oneBigMessage = new ObjectMessageImpl();

         try {
            this.oneBigMessage.initializeFromMessage(lastMessage);
            this.oneBigMessage.setObject(this.messages, PeerInfo.VERSION_920);
            this.oneBigMessage.setJMSDeliveryMode(1);
            this.oneBigMessage.setId(lastMessage.getId());
         } catch (JMSException var4) {
            var4.printStackTrace();
         }

         return this.oneBigMessage;
      }
   }

   public void checkReplacement(Message kOneBigMessage, Message kModifiedMessage) {
      try {
         MessageImpl oneBigMessage = (MessageImpl)kOneBigMessage;
         MessageImpl modifiedMessage = (MessageImpl)kModifiedMessage;
         if (modifiedMessage instanceof TextMessage) {
            try {
               ((TextMessage)modifiedMessage).getText();
            } catch (JMSException var7) {
            }
         }

         if (oneBigMessage instanceof TextMessage) {
            try {
               ((TextMessage)oneBigMessage).getText();
            } catch (JMSException var6) {
            }
         }

         if (!modifiedMessage.propertyExists("JMS_BEA_DeliveryFailureReason")) {
            return;
         }

         oneBigMessage.setPropertiesWritable(true);
         oneBigMessage.setIntProperty("JMS_BEA_DeliveryFailureReason", modifiedMessage.getIntProperty("JMS_BEA_DeliveryFailureReason"));
         oneBigMessage.setPropertiesWritable(false);
      } catch (JMSException var8) {
      }

   }

   public synchronized Message getOneBigMessageReplacee() {
      return this.oneBigMessageNumber == -1 ? null : (Message)this.messages.get(this.oneBigMessageNumber);
   }

   private void clean(boolean clearCaller) {
      this.messages = new ArrayList();
      this.lastSequenceNumber = Integer.MAX_VALUE;
      this.numberVisible = 0;
      this.oneBigMessageNumber = -1;
      this.oneBigMessage = null;
      this.dest = null;
      if (clearCaller) {
         this.caller = null;
      }

      if (this.expirationTimer != null) {
         this.expirationTimer.cancel();
         this.expirationTimer = null;
      }

      this.adminDeletedMessages = new HashSet();
   }

   public synchronized void adminDeletedMessage(Message deletedMessage) {
      if (deletedMessage instanceof TextMessage) {
         try {
            ((TextMessage)deletedMessage).getText();
         } catch (JMSException var3) {
         }
      }

      this.adminDeletedMessages.add(deletedMessage);
   }

   public synchronized boolean removeMessage(Message kernelMessage) {
      if (this.caller == null) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Caller is null, we've already cleaned up");
         }

         return true;
      } else {
         MessageImpl message = (MessageImpl)kernelMessage;
         if (this.adminDeletedMessages.contains(message)) {
            this.adminDeletedMessages.remove(message);
         } else if (message == this.getOneBigMessageReplacee()) {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               if (message instanceof TextMessage) {
                  try {
                     ((TextMessage)message).getText();
                  } catch (JMSException var4) {
                  }
               }

               JMSDebug.JMSBackEnd.debug("Removing one big message: " + message + " within UOW: " + this.caller + " on " + this.name);
            }

            this.clean(true);
            return true;
         }

         try {
            if (!message.propertyExists("JMS_BEA_DeliveryFailureReason")) {
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  if (message instanceof TextMessage) {
                     ((TextMessage)message).getText();
                  }

                  JMSDebug.JMSBackEnd.debug("No delivery failure reason on " + message + " within UOW: " + this.caller + " on " + this.name);
               }

               int sequenceNumber = message.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber") - 1;
               --this.numberVisible;
               if (this.messages.size() >= sequenceNumber) {
                  this.messages.set(sequenceNumber, (Object)null);
               }

               if (this.numberVisible == 0) {
                  if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                     JMSDebug.JMSBackEnd.debug(this.caller + ": Last message gone, clean up ");
                  }

                  this.clean(true);
                  return true;
               }

               if (this.expirationTimer == null) {
                  this.setWorkExpirationTimerIfNecessary(message.getJMSDeliveryMode() == 2);
               }

               this.oneBigMessageNumber = -1;
               this.oneBigMessage = null;
               return false;
            }
         } catch (JMSException var5) {
         }

         this.clean(false);
         return true;
      }
   }

   public void timerExpired(Timer timer) {
      UOWCallbackCaller caller = null;
      Iterator iter = null;
      synchronized(this) {
         caller = this.caller;
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("Incomplete Work expiration timer " + timer + " fired  for work : " + caller + " on " + this.name);
         }

         this.expired = true;
         if (this.messages.isEmpty()) {
            return;
         }

         iter = ((ArrayList)((ArrayList)this.messages.clone())).iterator();
      }

      while(iter.hasNext()) {
         MessageImpl message = (MessageImpl)iter.next();

         try {
            if (message != null) {
               message.setPropertiesWritable(true);
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  if (message instanceof TextMessage) {
                     ((TextMessage)message).getText();
                  }

                  JMSDebug.JMSBackEnd.debug("Setting delivery failure reason to WORK_EXPIRED on message: " + message + " seq " + message.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber") + " within UOW: " + caller + " on " + this.name);
               }

               message.setIntProperty("JMS_BEA_DeliveryFailureReason", 1);
               message.setPropertiesWritable(false);
            }
         } catch (JMSException var6) {
         }
      }

      if (caller != null) {
         caller.expireAll();
      }

   }

   private void markMessageExpired(MessageImpl message) {
      if (message != null) {
         try {
            message.setPropertiesWritable(true);
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("markMessageExpired), setting delivery failure reason to WORK_EXPIRED on message: " + message + " seq " + message.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber") + " within UOW: " + this.caller + " on " + this.name);
            }

            message.setIntProperty("JMS_BEA_DeliveryFailureReason", 1);
            message.setPropertiesWritable(false);
         } catch (JMSException var3) {
         }

         if (this.caller != null) {
            this.caller.expire(message, true);
         }

      }
   }

   private TimerManager getTimerManager() {
      return TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.backend.BEUOWCallback", WorkManagerFactory.getInstance().getSystem());
   }

   private boolean allMessagesHaveArrived() {
      if (this.lastSequenceNumber == Integer.MAX_VALUE) {
         return false;
      } else {
         Iterator iter = this.messages.listIterator();

         Object obj;
         do {
            if (!iter.hasNext()) {
               return true;
            }

            obj = iter.next();
         } while(obj != null);

         return false;
      }
   }

   private void setWorkExpirationTimerIfNecessary(boolean setUserData) {
      if (this.dest == null) {
         JMSService jmsService = JMSService.getJMSServiceWithPartitionName(JMSService.getSafePartitionNameFromThread());
         if (jmsService != null) {
            this.dest = jmsService.getBEDeployer().findBEDestination(this.name);
         }
      }

      if (this.dest == null) {
         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug(this.caller + ": Cannot find destination: " + this.name + " assuming that we're going down");
         }

      } else {
         if (this.dest.getIncompleteWorkExpirationTime() > 0) {
            long expirationTime = System.currentTimeMillis() + (long)this.dest.getIncompleteWorkExpirationTime();
            if (this.caller != null && setUserData) {
               this.caller.setUserData(new Expiration(expirationTime));
            }

            this.expirationTimer = this.getTimerManager().schedule(this, (long)this.dest.getIncompleteWorkExpirationTime());
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug(this.caller + ": Scheduling Work Expiration Timer for " + this.name + " expirationTimer " + this.expirationTimer);
            }
         }

      }
   }

   private void removeMarkerFromMessage(Message kernelMessage) {
      try {
         MessageImpl message = (MessageImpl)kernelMessage;
         if (!message.propertyExists("JMS_BEA_OneBigMessageNumber")) {
            return;
         }

         if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            if (message instanceof TextMessage) {
               ((TextMessage)message).getText();
            }

            JMSDebug.JMSBackEnd.debug(this.caller + ": Removing marker from in-memory copy of message");
         }

         message.removeProperty("JMS_BEA_OneBigMessageNumber");
      } catch (JMSException var3) {
         var3.printStackTrace();
      }

   }

   private void makeMarkerMessage(MessageImpl message, int sequenceNumber) {
      if (this.lastSequenceNumber == 0) {
         this.oneBigMessageNumber = 0;
      } else {
         if (sequenceNumber == 0) {
            this.oneBigMessageNumber = 1;
         } else {
            this.oneBigMessageNumber = 0;
         }

         message.setPropertiesWritable(true);

         try {
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               if (message instanceof TextMessage) {
                  ((TextMessage)message).getText();
               }

               JMSDebug.JMSBackEnd.debug("Setting one big message to message number " + this.oneBigMessageNumber + ", the message is " + this.getOneBigMessageReplacee() + " within UOW: " + this.caller + " on " + this.name);
            }

            message.setIntProperty("JMS_BEA_OneBigMessageNumber", this.oneBigMessageNumber);
         } catch (JMSException var4) {
            var4.printStackTrace();
         }

         message.setPropertiesWritable(false);
      }
   }

   public synchronized boolean sendMessage(Message kernelMessage) throws KernelException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(this.caller + ": Message sent in UOW: " + kernelMessage + " expired " + this.expired + " on " + this.name);
      }

      MessageImpl message = (MessageImpl)kernelMessage;
      if (message == this.oneBigMessage) {
         return false;
      } else if (this.expired) {
         this.markMessageExpired(message);
         return false;
      } else {
         try {
            int sequenceNumber = message.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber") - 1;
            if (sequenceNumber < 0) {
               throw new KernelException("Dummy", new BadSequenceNumberException("A sequence number must be greater than or equal to zero"));
            }

            if (sequenceNumber > this.lastSequenceNumber) {
               throw new KernelException("Dummy", new OutOfSequenceRangeException("Normal message out of range"));
            }

            boolean isEnd = message.propertyExists("JMS_BEA_IsUnitOfWorkEnd") && message.getBooleanProperty("JMS_BEA_IsUnitOfWorkEnd");
            if (sequenceNumber < this.messages.size() && this.messages.get(sequenceNumber) != null) {
               if (message.getSAFSequenceName() != null) {
                  return true;
               }

               throw new KernelException("Dummy", new DuplicateSequenceNumberException("Duplicate message"));
            }

            if (isEnd) {
               if (this.lastSequenceNumber != Integer.MAX_VALUE) {
                  throw new KernelException("Dummy", new DuplicateSequenceNumberException("Can't send two last messages"));
               }

               if (sequenceNumber < this.messages.size()) {
                  throw new KernelException("Dummy", new OutOfSequenceRangeException("End is too low"));
               }
            }

            while(this.messages.size() <= sequenceNumber) {
               this.messages.add((Object)null);
            }

            this.messages.set(sequenceNumber, message);
            if (isEnd) {
               this.lastSequenceNumber = sequenceNumber;
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug(this.caller + ": lastSequenceNumber set to " + this.lastSequenceNumber);
               }
            }

            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug(this.caller + ": sendMessage " + message + " messages.size() " + this.messages.size() + " allMessagesHaveArrived() " + this.allMessagesHaveArrived() + " expirationTimer " + this.expirationTimer);
            }

            if ((this.messages.size() == 1 || this.expirationTimer == null) && !this.allMessagesHaveArrived()) {
               this.setWorkExpirationTimerIfNecessary(message.getJMSDeliveryMode() == 2);
            }

            if (this.expirationTimer != null && this.allMessagesHaveArrived()) {
               this.expirationTimer.cancel();
               this.expirationTimer = null;
            }

            if (this.allMessagesHaveArrived()) {
               this.makeMarkerMessage(message, sequenceNumber);
            }
         } catch (JMSException var5) {
            var5.printStackTrace();
         }

         return false;
      }
   }

   public synchronized void recoverMessage(Message kernelMessage) throws KernelException {
      MessageImpl message = (MessageImpl)kernelMessage;
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(this.caller + ": Message recovered in UOW: " + kernelMessage);
      }

      try {
         int sequenceNumber = message.getIntProperty("JMS_BEA_UnitOfWorkSequenceNumber") - 1;
         boolean isEnd = message.propertyExists("JMS_BEA_IsUnitOfWorkEnd") && message.getBooleanProperty("JMS_BEA_IsUnitOfWorkEnd");

         while(this.messages.size() <= sequenceNumber) {
            this.messages.add((Object)null);
         }

         this.messages.set(sequenceNumber, message);
         if (isEnd) {
            this.lastSequenceNumber = sequenceNumber;
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug(this.caller + ": End message: lastSequenceNumber set to " + this.lastSequenceNumber);
            }
         }

         if (this.allMessagesHaveArrived()) {
            this.makeMarkerMessage(message, sequenceNumber);
         }

         if (message.propertyExists("JMS_BEA_OneBigMessageNumber")) {
            this.oneBigMessageNumber = message.getIntProperty("JMS_BEA_OneBigMessageNumber");
         }
      } catch (JMSException var5) {
         var5.printStackTrace();
      }

   }

   public void recoveryComplete() {
      int oneBigMessageNumber = true;
      UOWCallbackCaller caller = null;
      int oneBigMessageNumber;
      synchronized(this) {
         caller = this.caller;
         oneBigMessageNumber = this.oneBigMessageNumber;
         if (caller == null) {
            return;
         }

         if (caller.getUserData() == null) {
            return;
         }

         if (this.allMessagesHaveArrived()) {
            return;
         }
      }

      if (oneBigMessageNumber != -1) {
         caller.deleteAll();
      } else {
         Expiration expirationObject = (Expiration)caller.getUserData();
         long expirationTime = expirationObject.getTime();
         long currentTime = System.currentTimeMillis();
         if (currentTime > expirationTime) {
            this.timerExpired((Timer)null);
         } else {
            synchronized(this) {
               this.expirationTimer = this.getTimerManager().schedule(this, expirationTime - currentTime);
               if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
                  JMSDebug.JMSBackEnd.debug(caller + ": recoveryComplete: Schedule Work Expiration Timer " + this.expirationTimer + " name " + this.name);
               }

            }
         }
      }
   }

   public static class Expiration implements Externalizable {
      long expirationTime;

      public Expiration() {
      }

      public Expiration(long expirationTime) {
         this.expirationTime = expirationTime;
      }

      public long getTime() {
         return this.expirationTime;
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeLong(this.expirationTime);
      }

      public void readExternal(ObjectInput in) throws IOException {
         this.expirationTime = in.readLong();
      }
   }
}
