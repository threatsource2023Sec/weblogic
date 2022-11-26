package weblogic.jms.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.jms.JMSException;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.JMSSessionPoolRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class JMSServerRuntimeMBeanImpl extends JMSMessageCursorRuntimeImpl implements JMSServerRuntimeMBean {
   static final long serialVersionUID = -3550452657980202118L;
   private Object myLock = new Object();
   private List destRuntimeDelegates = null;
   private BackEnd backend;
   private LogRuntimeMBean logRuntime;
   private HealthState savedHealthState = null;

   public JMSServerRuntimeMBeanImpl(BackEnd backend, String name, boolean registerNow, RuntimeMBean restParent) throws ManagementException {
      super(name, registerNow);
      this.setRestParent(restParent);
      this.backend = backend;
   }

   public HealthState getHealthState() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? this.savedHealthState : mybackend.getHealthState();
   }

   public JMSSessionPoolRuntimeMBean[] getSessionPoolRuntimes() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? new JMSSessionPoolRuntimeMBean[0] : mybackend.getSessionPoolRuntimes();
   }

   public long getSessionPoolsCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getSessionPoolsCurrentCount();
   }

   public long getSessionPoolsHighCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getSessionPoolsHighCount();
   }

   public long getSessionPoolsTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getSessionPoolsTotalCount();
   }

   public JMSDestinationRuntimeMBean[] getDestinations() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? new JMSDestinationRuntimeMBean[0] : mybackend.getDestinations();
   }

   public long getDestinationsCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getDestinationsCurrentCount();
   }

   public long getDestinationsHighCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getDestinationsHighCount();
   }

   public long getDestinationsTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getDestinationsTotalCount();
   }

   public long getMessagesCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getMessagesCurrentCount();
   }

   public long getMessagesPendingCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getMessagesPendingCount();
   }

   public long getMessagesHighCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getMessagesHighCount();
   }

   public long getMessagesReceivedCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getMessagesReceivedCount();
   }

   public long getMessagesThresholdTime() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getMessagesThresholdTime();
   }

   public long getBytesCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesCurrentCount();
   }

   public long getBytesPendingCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesPendingCount();
   }

   public long getBytesHighCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesHighCount();
   }

   public long getBytesReceivedCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesReceivedCount();
   }

   public long getBytesThresholdTime() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesThresholdTime();
   }

   public void pauseProduction() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.pauseProduction();
   }

   public boolean isProductionPaused() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? true : mybackend.isProductionPaused();
   }

   public String getProductionPausedState() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? "Production-Paused" : mybackend.getProductionPausedState();
   }

   public void resumeProduction() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.resumeProduction();
   }

   public void pauseInsertion() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.pauseInsertion();
   }

   public boolean isInsertionPaused() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? true : mybackend.isInsertionPaused();
   }

   public String getInsertionPausedState() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? "Insertion-Paused" : mybackend.getInsertionPausedState();
   }

   public void resumeInsertion() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.resumeInsertion();
   }

   public void pauseConsumption() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.pauseConsumption();
   }

   public boolean isConsumptionPaused() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? true : mybackend.isConsumptionPaused();
   }

   public String getConsumptionPausedState() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? "Consumption-Paused" : mybackend.getConsumptionPausedState();
   }

   public void resumeConsumption() throws JMSException {
      BackEnd mybackend = this.checkBackEndWithJMSException();
      mybackend.resumeConsumption();
   }

   public String[] getTransactions() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? new String[0] : mybackend.getTransactions();
   }

   public String[] getPendingTransactions() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? new String[0] : mybackend.getPendingTransactions();
   }

   public Integer getTransactionStatus(String xid) {
      BackEnd mybackend = this.backend;
      return mybackend == null ? new Integer(6) : mybackend.getTransactionStatus(xid);
   }

   public String getMessages(String xid, Integer timeoutSeconds) throws ManagementException {
      BackEnd mybackend = this.checkBackEndWithManagementException();
      return mybackend.getMessages(xid, timeoutSeconds);
   }

   public Void forceCommit(String xid) throws ManagementException {
      BackEnd mybackend = this.checkBackEndWithManagementException();
      return mybackend.forceCommit(xid);
   }

   public Void forceRollback(String xid) throws ManagementException {
      BackEnd mybackend = this.checkBackEndWithManagementException();
      return mybackend.forceRollback(xid);
   }

   public int getMessagesPageableCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0 : mybackend.getMessagesPageableCurrentCount();
   }

   public long getBytesPageableCurrentCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesPageableCurrentCount();
   }

   public int getMessagesPagedOutTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0 : mybackend.getMessagesPagedOutTotalCount();
   }

   public int getMessagesPagedInTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0 : mybackend.getMessagesPagedInTotalCount();
   }

   public long getBytesPagedOutTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesPagedOutTotalCount();
   }

   public long getBytesPagedInTotalCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getBytesPagedInTotalCount();
   }

   public long getPagingAllocatedWindowBufferBytes() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getPagingAllocatedWindowBufferBytes();
   }

   public long getPagingAllocatedIoBufferBytes() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getPagingAllocatedIoBufferBytes();
   }

   public long getPagingPhysicalWriteCount() {
      BackEnd mybackend = this.backend;
      return mybackend == null ? 0L : mybackend.getPagingPhysicalWriteCount();
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.logRuntime;
   }

   public void setLogRuntime(LogRuntimeMBean logRuntime) {
      this.logRuntime = logRuntime;
   }

   void addBEDestinationRuntimeDelegate(BEDestinationRuntimeDelegate d) {
      synchronized(this.myLock) {
         if (this.destRuntimeDelegates == null) {
            this.destRuntimeDelegates = new ArrayList();
         }

         this.destRuntimeDelegates.add(d);
      }
   }

   void removeBEDestinationRuntimeDelegate(BEDestinationRuntimeDelegate d) {
      synchronized(this.myLock) {
         if (this.destRuntimeDelegates != null) {
            this.destRuntimeDelegates.remove(d);
         }
      }
   }

   void backendDestroyed() {
      synchronized(this.myLock) {
         if (this.backend != null) {
            this.savedHealthState = this.backend.getHealthState();
            this.backend = null;
            this.logRuntime = null;
            if (this.destRuntimeDelegates != null) {
               Iterator itr = this.destRuntimeDelegates.iterator();

               while(itr.hasNext()) {
                  ((BEDestinationRuntimeDelegate)itr.next()).backendDestroyed();
                  itr.remove();
               }

               this.destRuntimeDelegates.clear();
               this.destRuntimeDelegates = null;
            }
         }
      }
   }

   private BackEnd checkBackEndWithManagementException() throws ManagementException {
      synchronized(this.myLock) {
         if (this.backend == null) {
            throw new ManagementException("BackEnd destroyed");
         } else {
            return this.backend;
         }
      }
   }

   private BackEnd checkBackEndWithJMSException() throws JMSException {
      synchronized(this.myLock) {
         if (this.backend == null) {
            throw new JMSException("BackEnd destroyed");
         } else {
            return this.backend;
         }
      }
   }
}
