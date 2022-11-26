package weblogic.jms.backend;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSMessageManagementRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public abstract class BEMessageManagementRuntimeDelegate extends JMSMessageCursorRuntimeImpl implements JMSMessageManagementRuntimeMBean {
   private BEMessageManagementImpl delegate;

   public BEMessageManagementRuntimeDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow) throws ManagementException {
      super(nameArg, parentArg, registerNow);
   }

   public BEMessageManagementRuntimeDelegate(String nameArg, RuntimeMBean parentArg) throws ManagementException {
      super(nameArg, parentArg);
   }

   public BEMessageManagementRuntimeDelegate(String nameArg, RuntimeMBean parentArg, boolean registerNow, BEQueueImpl queue) throws ManagementException {
      this(nameArg, parentArg, registerNow);
      this.setMessageManagementDelegate(new BEMessageManagementImpl(nameArg, queue.getKernelQueue(), queue, this));
   }

   protected void setMessageManagementDelegate(BEMessageManagementImpl delegate) {
      this.delegate = delegate;
   }

   public Long getMessagesMovedCurrentCount() {
      BEMessageManagementImpl mydelegate = this.delegate;
      return mydelegate == null ? new Long(0L) : mydelegate.getMessagesMovedCurrentCount();
   }

   public Long getMessagesDeletedCurrentCount() {
      BEMessageManagementImpl mydelegate = this.delegate;
      return mydelegate == null ? new Long(0L) : mydelegate.getMessagesDeletedCurrentCount();
   }

   public CompositeData getMessage(String messageID) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("getMessage(String) not valid for " + this.getClass());
      } else {
         return mydelegate.getMessage(messageID);
      }
   }

   public Integer moveMessages(String selector, CompositeData targetDestination) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("moveMessages(String, CompositeData) not valid for " + this.getClass());
      } else {
         return mydelegate.moveMessages(selector, targetDestination);
      }
   }

   public Integer moveMessages(String selector, CompositeData targetDestination, Integer timeout) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("moveMessages(String, CompositeData) not valid for " + this.getClass());
      } else {
         return mydelegate.moveMessages(selector, targetDestination, timeout);
      }
   }

   public Integer deleteMessages(String selector) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("deleteMessages(String) not valid for " + this.getClass());
      } else {
         return mydelegate.deleteMessages(selector);
      }
   }

   public Void importMessages(CompositeData[] messages, Boolean replaceOnly) throws ManagementException {
      return this.importMessages(messages, replaceOnly, false);
   }

   public Void importMessages(CompositeData[] messages, Boolean replaceOnly, Boolean applyOverrides) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("importMessages(CompositeData[], Boolean) not valid for " + this.getClass());
      } else {
         return mydelegate.importMessages(messages, replaceOnly, applyOverrides);
      }
   }

   public String getMessages(String selector, Integer timeout) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("getMessages(String, Integer) not valid for " + this.getClass());
      } else {
         return mydelegate.getMessages(selector, timeout);
      }
   }

   public String getMessages(String selector, Integer timeout, Integer state) throws ManagementException {
      BEMessageManagementImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         throw new UnsupportedOperationException("getMessages(String, Integer, Integer) not valid for " + this.getClass());
      } else {
         return mydelegate.getMessages(selector, timeout, state);
      }
   }
}
