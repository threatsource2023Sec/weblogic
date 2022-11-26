package weblogic.jms.backend;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.JMSSecurityException;
import javax.jms.Session;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSMessageOpenDataConverter;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.jms.common.JMSSQLFilter;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.DestinationInfo;
import weblogic.jms.extensions.JMSForwardHelper;
import weblogic.jms.extensions.JMSMessageInfo;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.management.ManagementException;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.QuotaException;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.runtime.CursorRuntimeImpl;
import weblogic.messaging.runtime.OpenDataConverter;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

final class BEMessageManagementImpl {
   private final String name;
   private long messagesDeletedCurrentCount;
   private long messagesMovedCurrentCount;
   private final Queue queue;
   private final BEDestinationImpl destination;
   private final JMSSQLFilter filter;
   private final CursorRuntimeImpl cursorRuntime;
   private final OpenDataConverter messageHeaderConverter;
   private final OpenDataConverter messageBodyConverter;
   private final TransactionManager tm;

   BEMessageManagementImpl(String name, Queue queue, BEDestinationImpl destination, CursorRuntimeImpl cursorRuntime) {
      this.name = name;
      this.queue = queue;
      this.destination = destination;
      this.filter = new JMSSQLFilter(queue.getKernel());
      this.cursorRuntime = cursorRuntime;
      this.messageHeaderConverter = new JMSMessageOpenDataConverter(false);
      this.messageBodyConverter = new JMSMessageOpenDataConverter(true);
      this.tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
   }

   Long getMessagesMovedCurrentCount() {
      return new Long(this.messagesMovedCurrentCount);
   }

   Long getMessagesDeletedCurrentCount() {
      return new Long(this.messagesDeletedCurrentCount);
   }

   String getMessages(String selector, Integer timeout) throws ManagementException {
      return this.getMessages(selector, timeout, new Integer(Integer.MAX_VALUE));
   }

   String getMessages(String selector, Integer timeout, Integer state) throws ManagementException {
      try {
         this.destination.getJMSDestinationSecurity().checkBrowsePermission();
      } catch (JMSSecurityException var7) {
         this.throwManagementException("Authorization failure.", var7);
      }

      Cursor cursor = null;

      try {
         cursor = this.queue.createCursor(true, this.filter.createExpression(new JMSSQLExpression(selector)), state);
      } catch (KernelException var6) {
         this.throwManagementException("Error creating message cursor for " + this.name, var6);
      }

      JMSMessageCursorDelegate delegate = new JMSMessageCursorDelegate(this.cursorRuntime, this.messageHeaderConverter, cursor, this.messageBodyConverter, this.destination, timeout);
      this.cursorRuntime.addCursorDelegate(delegate);
      return delegate.getHandle();
   }

   Integer moveMessages(String selector, CompositeData targetDestination) throws ManagementException {
      return this.moveMessages(selector, targetDestination, new Integer(0));
   }

   Integer moveMessages(String selector, CompositeData targetDestination, Integer timeout) throws ManagementException {
      if (targetDestination == null) {
         this.throwManagementException("moveMessages operation failed. The target destination is null.");
      }

      if (timeout == null) {
         this.throwManagementException("moveMessages operation failed.  The timeout value is null.");
      }

      DestinationImpl target = null;

      try {
         DestinationInfo info = new DestinationInfo(targetDestination);
         target = (DestinationImpl)info.getDestination();
      } catch (OpenDataException var61) {
         this.throwManagementException("moveMessages operation failed.", var61);
      }

      try {
         this.destination.getJMSDestinationSecurity().checkBrowsePermission();
      } catch (JMSSecurityException var60) {
         this.throwManagementException("Authorization failure.", var60);
      }

      try {
         this.destination.getJMSDestinationSecurity().checkReceivePermission();
      } catch (JMSSecurityException var59) {
         this.throwManagementException("Authorization failure.", var59);
      }

      Transaction suspendedTx = this.suspendTransaction();
      int size = 0;
      Cursor cursor = null;
      ConnectionFactory cf = null;
      Connection connection = null;
      Session session = null;
      WLMessageProducer wlmp = null;

      Integer var66;
      try {
         cf = JMSServerUtilities.getXAConnectionFactory(this.destination.getBackEnd().getJmsService());

         try {
            connection = cf.createConnection();
            session = connection.createSession(false, 2);
            wlmp = (WLMessageProducer)session.createProducer(target);
         } catch (JMSException var58) {
            this.moveError(this.name, target.getName(), var58);
         }

         try {
            this.tm.begin("JMS Move Messages", timeout);
         } catch (NotSupportedException var56) {
            this.moveError(this.name, target.getName(), var56);
         } catch (SystemException var57) {
            this.moveError(this.name, target.getName(), var57);
         }

         MessageElement el = null;

         try {
            cursor = this.queue.createCursor(true, this.filter.createExpression(new JMSSQLExpression(selector)), 1073);
            size = cursor.size();

            while((el = cursor.next()) != null) {
               MessageImpl m = (MessageImpl)el.getMessage();
               m.setForward(true);
               JMSForwardHelper.ForwardFromMessage(wlmp, m, true, true);
               KernelRequest kernelRequest = this.queue.delete(el);
               if (kernelRequest != null) {
                  kernelRequest.getResult();
               }
            }
         } catch (KernelException var62) {
            this.moveError(this.name, target.getName(), var62);
         } catch (JMSException var63) {
            this.moveError(this.name, target.getName(), var63);
         }

         try {
            this.tm.commit();
         } catch (SecurityException var50) {
            this.moveError(this.name, target.getName(), var50);
         } catch (IllegalStateException var51) {
            this.moveError(this.name, target.getName(), var51);
         } catch (RollbackException var52) {
            this.moveError(this.name, target.getName(), var52);
         } catch (HeuristicMixedException var53) {
            this.moveError(this.name, target.getName(), var53);
         } catch (HeuristicRollbackException var54) {
            this.moveError(this.name, target.getName(), var54);
         } catch (SystemException var55) {
            this.moveError(this.name, target.getName(), var55);
         }

         this.incrementMessagesMovedCurrentCount(size);
         var66 = new Integer(size);
      } finally {
         try {
            if (this.tm.getTransaction() != null) {
               this.tm.rollback();
            }
         } catch (IllegalStateException var47) {
            this.debug("Error rolling back move messages transaction", var47);
         } catch (SecurityException var48) {
            this.debug("Error rolling back move messages transaction", var48);
         } catch (SystemException var49) {
            this.debug("Error rolling back move messages transaction", var49);
         }

         if (wlmp != null) {
            try {
               wlmp.close();
            } catch (JMSException var46) {
               this.debug("Unable to close move messages producer", var46);
            }
         }

         if (session != null) {
            try {
               session.close();
            } catch (JMSException var45) {
               this.debug("Unable to close move messages session", var45);
            }
         }

         if (connection != null) {
            try {
               connection.close();
            } catch (JMSException var44) {
               this.debug("Unable to close move messages connection", var44);
            }
         }

         if (cursor != null) {
            cursor.close();
         }

         if (suspendedTx != null) {
            this.resumeTransaction(suspendedTx);
         }

      }

      return var66;
   }

   CompositeData getMessage(String messageID) throws ManagementException {
      try {
         this.destination.getJMSDestinationSecurity().checkBrowsePermission();
      } catch (JMSSecurityException var10) {
         this.throwManagementException("Authorization failure.", var10);
      }

      CompositeData cd = null;
      Cursor cursor = null;

      MessageElement msg;
      try {
         cursor = this.queue.createCursor(true, this.filter.createExpression(new JMSSQLExpression("JMSMessageID = '" + messageID + "'")), 1);
         if (cursor.size() != 0) {
            if (cursor.size() > 1) {
               this.throwManagementException("Multiple messages exist for messageID " + messageID);
            }

            msg = cursor.next();
            cd = this.messageBodyConverter.createCompositeData(msg);
            return cd;
         }

         msg = null;
      } catch (OpenDataException var11) {
         this.throwManagementException("Failed to convert message with message ID " + messageID + " to open data representation.", var11);
         return cd;
      } catch (KernelException var12) {
         this.throwManagementException("Failed to get message with message ID " + messageID, var12);
         return cd;
      } finally {
         if (cursor != null) {
            cursor.close();
         }

      }

      return msg;
   }

   Integer deleteMessages(String selector) throws ManagementException {
      try {
         this.destination.getJMSDestinationSecurity().checkReceivePermission();
      } catch (JMSSecurityException var11) {
         this.throwManagementException("Authorization failure.", var11);
      }

      Transaction suspendedTx = this.suspendTransaction();
      Cursor cursor = null;
      int numDeleted = 0;

      try {
         cursor = this.queue.createCursor(true, this.filter.createExpression(new JMSSQLExpression(selector)), 1073);

         for(MessageElement el = null; (el = cursor.next(true)) != null; ++numDeleted) {
            KernelRequest kernelRequest = this.queue.delete(el);
            if (kernelRequest != null) {
               kernelRequest.getResult();
            }
         }

         this.incrementMessagesDeletedCurrentCount(numDeleted);
      } catch (KernelException var12) {
         this.throwManagementException("Error while deleting messages", var12);
      } finally {
         if (cursor != null) {
            cursor.close();
         }

         if (suspendedTx != null) {
            this.resumeTransaction(suspendedTx);
         }

      }

      return new Integer(numDeleted);
   }

   Void importMessages(CompositeData[] messages, Boolean replaceOnly) throws ManagementException {
      return this.importMessages(messages, replaceOnly, false);
   }

   Void importMessages(CompositeData[] messages, Boolean replaceOnly, Boolean applyOverrides) throws ManagementException {
      if (replaceOnly) {
         String msg = "importMessages operation not supported with replaceOnly=true";
         this.debug(msg);
         throw new UnsupportedOperationException(msg);
      } else if (messages != null && messages.length != 0) {
         try {
            this.destination.getJMSDestinationSecurity().checkSendPermission();
         } catch (JMSSecurityException var21) {
            this.throwManagementException("Authorization failure.", var21);
         }

         Transaction suspendedTx = this.suspendTransaction();

         try {
            for(int i = 0; i < messages.length; ++i) {
               if (messages[i] != null) {
                  MessageImpl message = null;

                  try {
                     JMSMessageInfo mi = new JMSMessageInfo(messages[i]);
                     message = (MessageImpl)mi.getMessage();
                  } catch (OpenDataException var20) {
                     this.throwManagementException("Unable to convert Open Data type to Message", var20);
                  }

                  if (message.getDeliveryCount() < 0) {
                     throw new ManagementException("Import of message " + message.getJMSMessageID() + " to destination " + this.name + " failed due to an invalid delivery count of " + message.getDeliveryCount());
                  }

                  if (message.getAdjustedDeliveryMode() == 2) {
                     if (this.destination.isTemporary()) {
                        this.downgradeMessage(message);
                     } else if (!this.destination.getBackEnd().isStoreEnabled()) {
                        if (this.destination.getBackEnd().isAllowsPersistentDowngrade()) {
                           this.downgradeMessage(message);
                        } else {
                           this.throwManagementException("Unable to downgrade message " + message.getJMSMessageID() + " while importing to destination " + this.name + " because persistence downgrade is not supported.");
                        }
                     }
                  }

                  if (!replaceOnly) {
                     message.setId(JMSService.getNextMessageId());
                  }

                  if (applyOverrides) {
                     try {
                        this.destination.applyOverrides(message, (JMSProducerSendResponse)null);
                     } catch (JMSException var19) {
                        this.throwManagementException("Unable to apply destination overrides on message " + message.getJMSMessageID() + " while importing to destination " + this.name);
                     }
                  }

                  try {
                     KernelRequest sendRequest = this.queue.send(message, this.destination.createSendOptions(0L, (Sequence)null, message));
                     if (sendRequest != null) {
                        sendRequest.getResult();
                     }
                  } catch (QuotaException var16) {
                     this.throwManagementException("Quota exceeded on target", var16);
                  } catch (weblogic.messaging.kernel.IllegalStateException var17) {
                     this.throwManagementException("Destination " + this.name + " state does not allow message production", var17);
                  } catch (KernelException var18) {
                     this.throwManagementException("Internal error during import operation", var18);
                  }
               }
            }
         } finally {
            if (suspendedTx != null) {
               this.resumeTransaction(suspendedTx);
            }

         }

         return null;
      } else {
         return null;
      }
   }

   private synchronized void incrementMessagesDeletedCurrentCount(int count) {
      this.messagesDeletedCurrentCount += (long)count;
   }

   private synchronized void incrementMessagesMovedCurrentCount(int count) {
      this.messagesMovedCurrentCount += (long)count;
   }

   private Transaction suspendTransaction() throws ManagementException {
      try {
         return this.tm.suspend();
      } catch (SystemException var2) {
         throw new ManagementException("Unable to suspend existing transaction.", var2);
      }
   }

   private void resumeTransaction(Transaction tx) {
      try {
         this.tm.resume(tx);
      } catch (SystemException var3) {
         this.debug("Error restoring transaction context.", var3);
      } catch (InvalidTransactionException var4) {
         this.debug("Error restoring transaction context.", var4);
      } catch (IllegalStateException var5) {
         this.debug("Error restoring transaction context.", var5);
      }

   }

   private void downgradeMessage(MessageImpl message) throws ManagementException {
      message.setAdjustedDeliveryMode(1);

      try {
         message.setJMSDeliveryMode(1);
      } catch (JMSException var3) {
         this.throwManagementException("Unable to downgrade message.", var3);
      }

   }

   private void moveError(String source, String target, Throwable t) throws ManagementException {
      String msg = "Error occurred while processing the requested move operation from source " + this.name + " to destination " + target;
      this.throwManagementException(msg, t);
   }

   private void throwManagementException(String msg) throws ManagementException {
      this.debug(msg);
      throw new ManagementException(msg);
   }

   private void throwManagementException(String msg, Throwable t) throws ManagementException {
      this.debug(msg, t);
      throw new ManagementException(msg, t);
   }

   private void debug(String msg) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(msg);
      }

   }

   private void debug(String msg, Throwable t) {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug(msg, t);
      }

   }
}
