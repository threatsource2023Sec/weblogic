package weblogic.jms.forwarder.dd.internal;

import java.security.AccessControlException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.jms.cache.CacheEntry;
import weblogic.jms.common.DDMemberInformation;
import weblogic.jms.common.DDTxLoadBalancingOptimizer;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.LoadBalancer;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.PerJVMLoadBalancer;
import weblogic.jms.common.RRLoadBalancer;
import weblogic.jms.common.RandomLoadBalancer;
import weblogic.jms.forwarder.DestinationName;
import weblogic.jms.forwarder.SessionRuntimeContext;
import weblogic.jms.forwarder.dd.DDForwardStore;
import weblogic.jms.forwarder.dd.DDInfo;
import weblogic.jms.forwarder.dd.DDLBTable;
import weblogic.jms.forwarder.dd.DDLoadBalancerDelegate;
import weblogic.jms.forwarder.dd.DDMemberInfo;
import weblogic.jms.forwarder.dd.DDMembersCache;
import weblogic.jms.forwarder.dd.DDMembersCacheChangeListener;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.store.PersistentStoreException;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.utils.StackTraceUtilsClient;

public class DDLoadBalancerDelegateImpl implements DDLoadBalancerDelegate {
   private String name;
   private DDInfo ddInfo;
   private SessionRuntimeContext jmsSessionRuntimeContext;
   private DDForwardStore ddForwardStore;
   private DDLBTable ddLBTable;
   private LoadBalancer loadBalancer = new RRLoadBalancer();
   private ArrayList ddImplArrayList = new ArrayList();
   private HashMap memberInformationDDImplMap = new HashMap();
   private HashMap ddMemberNameDImplMap = new HashMap();
   private LoadBalancerHandler loadBalancerHandler;
   private static final int ADD_MEMBER = 1;
   private static final int REMOVE_MEMBER = 2;
   private ArrayList notifications = new ArrayList();
   private boolean freezed;
   private String exactlyOnceLBPolicy;
   private AbstractSubject subject;
   private static final AbstractSubject kernelID = getKernelIdentity();
   private boolean isFirstPushedMessageNotInFailedMap;

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public DDLoadBalancerDelegateImpl(SessionRuntimeContext jmsSessionRuntimeContext, DDInfo ddInfo, PersistentStoreXA persistentStore, String exactlyOnceLoadBalancingPolicy) throws JMSException {
      this.exactlyOnceLBPolicy = JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER;
      this.subject = null;
      this.isFirstPushedMessageNotInFailedMap = true;
      if (jmsSessionRuntimeContext == null) {
         throw new AssertionError();
      } else {
         this.name = jmsSessionRuntimeContext.getName();
         this.ddInfo = ddInfo;
         this.jmsSessionRuntimeContext = jmsSessionRuntimeContext;
         this.exactlyOnceLBPolicy = exactlyOnceLoadBalancingPolicy;
         this.createDDForwardStore(persistentStore, this.exactlyOnceLBPolicy);
         this.createLoadBalancer(this.exactlyOnceLBPolicy);
      }
   }

   private void createDDForwardStore(PersistentStoreXA persistentStore, String exactlyOnceLBPolicy) throws JMSException {
      String ddForwardStoreConnectionName = persistentStore.getName() + "." + this.name + "." + this.ddInfo.getDestinationName().getConfigName();
      this.ddForwardStore = new DDForwardStoreImpl(ddForwardStoreConnectionName, this.ddInfo, persistentStore);
      this.ddLBTable = this.ddForwardStore.getDDLBTable();
      this.ddLBTable.setPerJVMLBEnabled(JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM.equalsIgnoreCase(exactlyOnceLBPolicy));
   }

   private void createLoadBalancer(String exactlyOnceLBPolicy) {
      switch (this.ddInfo.getLoadBalancingPolicy()) {
         case 0:
            this.loadBalancer = new RRLoadBalancer();
            break;
         case 1:
            this.loadBalancer = new RandomLoadBalancer();
      }

      if (JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM.equalsIgnoreCase(exactlyOnceLBPolicy)) {
         this.loadBalancer = new PerJVMLoadBalancer(this.loadBalancer, this.ddLBTable);
      }

      this.loadBalancerHandler = new LoadBalancerHandler();
   }

   public DestinationName getDestinationName() {
      return this.ddInfo.getDestinationName();
   }

   public int getLoadBalancingPolicy() {
      return this.ddInfo.getLoadBalancingPolicy();
   }

   public int getForwardingPolicy() {
      return this.ddInfo.getForwardingPolicy();
   }

   public boolean isDDInLocalCluster() {
      return this.jmsSessionRuntimeContext.isForLocalCluster();
   }

   public synchronized Destination loadBalance() {
      DistributedDestinationImpl dd = this.loadBalancer.getNext((DDTxLoadBalancingOptimizer)null);
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("loadbalance() found: dd's memberName =" + dd.getMemberName() + " memebr's dispatcherID = " + dd.getDispatcherId());
      }

      return (Destination)this.ddMemberNameDImplMap.get(dd.getMemberName());
   }

   public Destination loadBalance(MessageImpl message) {
      long sequenceNumber = message.getSAFSeqNumber();
      DDMemberInfo ddMemberInfo = null;
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("loadBalance() for message " + message.getId() + " with sequence number " + sequenceNumber + "\n" + this);
      }

      boolean isPersistent = message.getJMSDeliveryMode() == 2;

      DestinationImpl inDoubtddMemberInfo;
      try {
         if ((ddMemberInfo = this.ddLBTable.getFailedDDMemberInfo(sequenceNumber)) != null) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("loadbalance() : failedDDMemberInfo=" + ddMemberInfo);
            }

            this.ddLBTable.removeFailedDDMemberInfo(sequenceNumber);
            Destination destination = this.findOrCreateDestination(ddMemberInfo);
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("loadbalance() : destination=" + destination);
            }

            if (destination == null) {
               return null;
            }

            if (!this.checkIfDDMemberInfoHadFailedInLastRun(ddMemberInfo)) {
               this.addDestination(ddMemberInfo, this.ddInfo.getDestinationName().getConfigName(), this.ddInfo.getDestinationName().getJNDIName());
            }

            return destination;
         }
      } catch (JMSException var20) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("loadbalance() failed, ddMemberInfo " + ddMemberInfo + " sequenceNumber " + sequenceNumber + "\n" + StackTraceUtilsClient.throwable2StackTrace(var20));
         }

         inDoubtddMemberInfo = null;
         if (ddMemberInfo != null) {
            this.ddLBTable.addFailedDDMemberInfo(sequenceNumber, ddMemberInfo);
            this.removeDestination(ddMemberInfo, isPersistent);
            inDoubtddMemberInfo = ddMemberInfo.getDestination();
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("loadbalance() returning " + inDoubtddMemberInfo);
         }

         return inDoubtddMemberInfo;
      }

      DDMemberInfo[] inDoubtDDMemberInfos = this.ddLBTable.getInDoubtDDMemberInfos();
      inDoubtddMemberInfo = null;

      int index;
      label294: {
         Destination var23;
         try {
            DestinationImpl retDest;
            try {
               if (!isPersistent || !this.isFirstPushedMessageNotInFailedMap || inDoubtDDMemberInfos == null || inDoubtDDMemberInfos.length == 0 || this.ddLBTable.getInDoubtDDMemberInfosTimestamp() <= message.getJMSTimestamp()) {
                  break label294;
               }

               this.ddLBTable.removeInDoubtDDMemberInfos();
               int inDoubtDDMembersNum = inDoubtDDMemberInfos.length;
               if (inDoubtDDMembersNum == 0) {
                  retDest = null;
                  return retDest;
               }

               index = (int)(sequenceNumber % (long)inDoubtDDMembersNum);
               if (JMSDebug.JMSSAF.isDebugEnabled() && (index < 0 || index >= inDoubtDDMembersNum)) {
                  JMSDebug.JMSSAF.debug("loadbalance() index OutOfBounds, sequenceNumber:" + sequenceNumber + " inDoubtDDMembersNum: " + inDoubtDDMembersNum + " index: " + index);
               }

               DDMemberInfo inDoubtddMemberInfo = inDoubtDDMemberInfos[index];
               var23 = this.findOrCreateDestination(inDoubtddMemberInfo);
            } catch (JMSException var18) {
               if (JMSDebug.JMSSAF.isDebugEnabled()) {
                  JMSDebug.JMSSAF.debug("loadbalance() failed,  inDoubtddMemberInfo " + inDoubtddMemberInfo + " sequenceNumber " + sequenceNumber + "\n" + StackTraceUtilsClient.throwable2StackTrace(var18));
               }

               retDest = null;
               if (inDoubtddMemberInfo != null) {
                  this.ddLBTable.addFailedDDMemberInfo(sequenceNumber, inDoubtddMemberInfo);
                  this.removeDestination(inDoubtddMemberInfo, isPersistent);
                  retDest = inDoubtddMemberInfo.getDestination();
               }

               if (JMSDebug.JMSSAF.isDebugEnabled()) {
                  JMSDebug.JMSSAF.debug("loadbalance() returning " + retDest);
               }

               DestinationImpl var10 = retDest;
               return var10;
            }
         } finally {
            this.isFirstPushedMessageNotInFailedMap = false;
         }

         return var23;
      }

      synchronized(this) {
         if (this.ddImplArrayList.size() != 0 && this.loadBalancer.getSize() != 0) {
            this.freezeDDLBTable();
            index = (int)(sequenceNumber % (long)this.loadBalancer.getSize());
            if (JMSDebug.JMSSAF.isDebugEnabled() && (index < 0 || index >= this.loadBalancer.getSize())) {
               JMSDebug.JMSSAF.debug("loadbalance() : index OutOfBounds, sequenceNumber:" + sequenceNumber + " ddImplArrayList.size():" + this.ddImplArrayList.size() + " index: " + index);
            }

            DistributedDestinationImpl dd = this.loadBalancer.getNext(index);
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("loadbalance(1) found: dd's memberName =" + dd.getMemberName() + " memebr's dispatcherID = " + dd.getDispatcherId());
            }

            return (Destination)this.ddMemberNameDImplMap.get(dd.getMemberName());
         } else {
            return null;
         }
      }
   }

   private Destination findOrCreateDestination(DDMemberInfo ddMemberInfo) throws JMSException {
      DestinationImpl dImpl = ddMemberInfo.getDestination();
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("findOrCreateDestination() :  ddMemberInfo = " + ddMemberInfo + " dImpl = " + dImpl);
         if (dImpl != null) {
            JMSDebug.JMSSAF.debug("findOrCreateDestination() :  dImpl.isStale= " + dImpl.isStale() + "dImpl.id= " + dImpl.getId());
         }
      }

      if (dImpl != null && !dImpl.isStale() && dImpl.getId() != null) {
         return dImpl;
      } else {
         String destinationType = ddMemberInfo.getType();
         String jmsServerInstanceName = ddMemberInfo.getJMSServerInstanceName();
         String jmsServerConfigName = ddMemberInfo.getJMSServerConfigName();
         String ddMemberConfigName = ddMemberInfo.getDDMemberConfigName();
         String fullyQualifiedDDMemberConfigName = ddMemberInfo.getMemberName();
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("findOrCreateDestination() :  about to create " + destinationType + " for " + fullyQualifiedDDMemberConfigName);
         }

         this.subject = this.jmsSessionRuntimeContext.getSubject();
         this.pushSubject();

         try {
            if (destinationType.equals("javax.jms.Queue")) {
               dImpl = (DestinationImpl)this.jmsSessionRuntimeContext.getJMSSession().createQueue(fullyQualifiedDDMemberConfigName);
            } else {
               dImpl = (DestinationImpl)this.jmsSessionRuntimeContext.getJMSSession().createTopic(fullyQualifiedDDMemberConfigName);
            }
         } finally {
            this.popSubject();
         }

         ddMemberInfo.setDestination(dImpl);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("findOrCreateDestination() found destination " + dImpl);
         }

         return dImpl;
      }
   }

   private boolean checkIfDDMemberInfoHadFailedInLastRun(DDMemberInfo ddMemberInfo) {
      synchronized(this.ddLBTable) {
         List failedDDMemberInfos = this.ddLBTable.getFailedDDMemberInfos();
         boolean thisDDMemberInfoHadFailedInLastRun = failedDDMemberInfos.remove(ddMemberInfo);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("checkifDDMemberInfoHadFailedInlastRun() : " + thisDDMemberInfoHadFailedInLastRun + " ddMemberInfo= " + ddMemberInfo + " failedDDMemberInfos= " + failedDDMemberInfos);
         }

         if (thisDDMemberInfoHadFailedInLastRun) {
            failedDDMemberInfos.add(ddMemberInfo);
         }

         return thisDDMemberInfoHadFailedInLastRun;
      }
   }

   private void addDestination(DDMemberInformation ddMemberInformation) throws JMSException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("addDestination() single : ddJNDIname= " + this.ddInfo.getDestinationName().getJNDIName() + " ddConfigName = " + ddMemberInformation.getDDConfigName() + " isProductionPaused= " + ddMemberInformation.isProductionPaused() + " isInsertionPaused= " + ddMemberInformation.isInsertionPaused() + "\n" + this);
      }

      if (!ddMemberInformation.isProductionPaused() && !ddMemberInformation.isInsertionPaused()) {
         String ddJNDIName = this.ddInfo.getDestinationName().getJNDIName();
         String ddConfigName = ddMemberInformation.getDDConfigName();
         DestinationImpl destination = (DestinationImpl)ddMemberInformation.getDestination();
         String destinationType = ddMemberInformation.getDDType();
         DDMemberInfo ddMemberInfo = new DDMemberInfoImpl(destination.getServerName(), destination.getJMSServerConfigName(), destination.getName(), destinationType, destination);
         if (!this.checkIfDDMemberInfoHadFailedInLastRun(ddMemberInfo)) {
            this.addDestination(ddMemberInfo, ddConfigName, ddJNDIName);
         }
      }
   }

   private void addDestination(DDMemberInfo ddMemberInfo, String ddConfigName, String ddJNDIName) throws JMSException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("addDestination() : ddMemberInfo = " + ddMemberInfo + " ddConfigName = " + ddConfigName + " ddJNIDName = " + ddJNDIName + "\n" + this);
      }

      String jmsServerInstanceName = ddMemberInfo.getJMSServerInstanceName();
      String jmsServerConfigName = ddMemberInfo.getJMSServerConfigName();
      String ddMemberConfigName = ddMemberInfo.getDDMemberConfigName();
      DestinationImpl destinationImpl = ddMemberInfo.getDestination();
      String fullyQualifiedDDMemberConfigName = jmsServerInstanceName + "/" + ddMemberConfigName;
      destinationImpl = (DestinationImpl)this.findOrCreateDestination(ddMemberInfo);
      DistributedDestinationImpl ddDistributedDestination = new DistributedDestinationImpl(destinationImpl.getType(), destinationImpl.getServerName(), destinationImpl.getJMSServerConfigName(), ddConfigName, destinationImpl.getApplicationName(), destinationImpl.getModuleName(), this.getLoadBalancingPolicy(), this.getForwardingPolicy(), fullyQualifiedDDMemberConfigName, ddJNDIName, destinationImpl.getBackEndId(), destinationImpl.getDestinationId(), destinationImpl.getBackEndId().getDispatcherId(), true, destinationImpl.getPersistentStoreName(), this.ddInfo.getSAFExportPolicy(), false, destinationImpl.getPartitionName());
      synchronized(this) {
         if (this.freezed) {
            this.notifications.add(new NotificationItemAdd(ddDistributedDestination, fullyQualifiedDDMemberConfigName, destinationImpl, ddMemberInfo));
         } else {
            this.addDestinationToList(ddDistributedDestination, fullyQualifiedDDMemberConfigName, destinationImpl, ddMemberInfo);
         }

      }
   }

   private synchronized void addDestinationToList(DistributedDestinationImpl ddDistributedDestination, String fullyQualifiedDDMemberConfigName, DestinationImpl destinationImpl, DDMemberInfo ddMemberInfo) {
      if (!this.checkIfDDMemberInfoHadFailedInLastRun(ddMemberInfo)) {
         int index = this.ddImplArrayList.indexOf(ddDistributedDestination);
         if (index != -1) {
            this.ddImplArrayList.set(index, ddDistributedDestination);
         } else {
            this.ddImplArrayList.add(ddDistributedDestination);
         }

         this.memberInformationDDImplMap.put(fullyQualifiedDDMemberConfigName, ddDistributedDestination);
         this.ddMemberNameDImplMap.put(fullyQualifiedDDMemberConfigName, destinationImpl);
         DistributedDestinationImpl[] ddDestinations = this.getDDImpls(this.ddImplArrayList);
         this.loadBalancer.refresh(ddDestinations);
         this.persistLoadBalancerInfo(1, ddMemberInfo);
      }
   }

   private void removeDestination(DDMemberInformation ddMemberInformation) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("removeDestination() single : " + ddMemberInformation + "\n" + this);
      }

      DestinationImpl destination = (DestinationImpl)ddMemberInformation.getDestination();
      String destinationType = ddMemberInformation.getDDType();
      DDMemberInfo ddMemberInfo = new DDMemberInfoImpl(destination.getServerName(), destination.getJMSServerConfigName(), destination.getName(), destinationType, destination);
      this.removeDestination(ddMemberInfo, true);
   }

   private void removeDestination(DDMemberInfo ddMemberInfo, boolean persist) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("removeDestination() : ddMemberInfo = " + ddMemberInfo + " persist=" + persist + "\n" + this);
      }

      String jmsServerInstanceName = ddMemberInfo.getJMSServerInstanceName();
      String jmsServerConfigName = ddMemberInfo.getJMSServerConfigName();
      String ddMemberConfigName = ddMemberInfo.getDDMemberConfigName();
      String fullyQualifiedDDMemberName = jmsServerInstanceName + "/" + ddMemberConfigName;
      synchronized(this) {
         if (this.freezed) {
            this.notifications.add(new NotificationItemRemove(fullyQualifiedDDMemberName, ddMemberInfo, persist));
         } else {
            this.removeDestinationFromList(fullyQualifiedDDMemberName, ddMemberInfo, persist);
         }

      }
   }

   private synchronized void removeDestinationFromList(String fullyQualifiedDDMemberName, DDMemberInfo ddMemberInfo, boolean persist) {
      DistributedDestinationImpl dd = (DistributedDestinationImpl)this.memberInformationDDImplMap.remove(fullyQualifiedDDMemberName);
      this.ddImplArrayList.remove(dd);
      this.ddMemberNameDImplMap.remove(fullyQualifiedDDMemberName);
      DistributedDestinationImpl[] ddDestinations = this.getDDImpls(this.ddImplArrayList);
      this.loadBalancer.refresh(ddDestinations);
      if (persist) {
         this.persistLoadBalancerInfo(2, ddMemberInfo);
      }

   }

   public void close() {
      this.loadBalancerHandler.close();
      this.ddForwardStore.close();
   }

   public boolean hasNonFailedDDMembers() {
      DDMemberInfo[] ddMemberInfos = this.ddLBTable.getDDMemberInfos();
      if (ddMemberInfos == null) {
         return false;
      } else {
         return ddMemberInfos.length != 0;
      }
   }

   public void addFailedEndPoint(MessageImpl message, DestinationImpl destination) throws PersistentStoreException {
      String type = null;
      if (destination.isQueue()) {
         type = new String("javax.jms.Queue");
      } else if (destination.isTopic()) {
         type = new String("javax.jms.Topic");
      }

      destination.markStale();
      DDMemberInfo ddMemberInfo = new DDMemberInfoImpl(destination.getServerName(), destination.getJMSServerConfigName(), destination.getName(), type, destination);
      this.ddLBTable.addFailedDDMemberInfo(message.getSAFSeqNumber(), ddMemberInfo);
      this.removeDestination(ddMemberInfo, message.getJMSDeliveryMode() == 2);
   }

   private DistributedDestinationImpl[] getDDImpls(Collection ddImplCollection) {
      Iterator iterator = ddImplCollection.iterator();
      int size = ddImplCollection.size();
      DistributedDestinationImpl[] ddDestinations = new DistributedDestinationImpl[size];

      for(int i = 0; iterator.hasNext(); ++i) {
         ddDestinations[i] = (DistributedDestinationImpl)iterator.next();
      }

      return ddDestinations;
   }

   private void addDestination(DDMemberInformation[] ddMemberInformation) throws JMSException {
      for(int i = 0; i < ddMemberInformation.length; ++i) {
         this.addDestination(ddMemberInformation[i]);
      }

   }

   private void removeDestination(DDMemberInformation[] ddMemberInformation) {
      for(int i = 0; i < ddMemberInformation.length; ++i) {
         this.removeDestination(ddMemberInformation[i]);
      }

   }

   private void persistLoadBalancerInfo(int loadBalancerEvent, DDMemberInfo ddMemberInfo) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("persistLoadBalancerInfo() : event = " + loadBalancerEvent + " ddMemberInfo = " + ddMemberInfo + "\n" + this);
      }

      try {
         switch (loadBalancerEvent) {
            case 1:
               synchronized(this.ddLBTable) {
                  this.ddLBTable.addDDMemberInfo(ddMemberInfo);
                  this.ddForwardStore.addOrUpdateDDLBTable(this.ddLBTable);
                  break;
               }
            case 2:
               synchronized(this.ddLBTable) {
                  this.ddLBTable.removeDDMemberInfo(ddMemberInfo);
                  this.ddForwardStore.addOrUpdateDDLBTable(this.ddLBTable);
               }
         }

      } catch (PersistentStoreException var8) {
         var8.printStackTrace();
         throw new AssertionError(var8);
      }
   }

   public void refreshSessionRuntimeContext(Context ctx, Connection connection, Session session, AbstractSubject subject) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("refreshSessionRuntimeContext \n" + this);
      }

      this.jmsSessionRuntimeContext.refresh(ctx, connection, session, subject);
   }

   private synchronized void freezeDDLBTable() {
      this.freezed = true;
   }

   public void unfreezeDDLBTable() {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("DDLoadBalancerDelegateImpl: unfreeze the LB table");
      }

      synchronized(this) {
         if (this.freezed) {
            this.freezed = false;
            Iterator itr = this.notifications.iterator();

            try {
               while(itr.hasNext()) {
                  NotificationItem item = (NotificationItem)itr.next();
                  item.processNotification();
               }
            } finally {
               this.notifications.clear();
            }

         }
      }
   }

   private synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(kernelID, this.subject);
      }

   }

   private synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(kernelID);
      }

   }

   public String toString() {
      return "[DDLoadBalancerDelegateImpl:  ddLBTable = " + this.ddLBTable + "]";
   }

   private final class NotificationItemRemove extends NotificationItem {
      private String name;
      private DDMemberInfo ddMemberInfo;
      private boolean persist;

      public NotificationItemRemove(String name, DDMemberInfo ddMemberInfo, boolean persist) {
         super(null);
         this.name = name;
         this.ddMemberInfo = ddMemberInfo;
         this.persist = persist;
      }

      void processNotification() {
         DDLoadBalancerDelegateImpl.this.removeDestinationFromList(this.name, this.ddMemberInfo, this.persist);
      }
   }

   private final class NotificationItemAdd extends NotificationItem {
      private DistributedDestinationImpl ddImpl;
      private String name;
      private DestinationImpl destImpl;
      private DDMemberInfo ddMemberInfo;

      public NotificationItemAdd(DistributedDestinationImpl ddImpl, String name, DestinationImpl destImpl, DDMemberInfo ddMemberInfo) {
         super(null);
         this.ddImpl = ddImpl;
         this.name = name;
         this.destImpl = destImpl;
         this.ddMemberInfo = ddMemberInfo;
      }

      void processNotification() {
         DDLoadBalancerDelegateImpl.this.addDestinationToList(this.ddImpl, this.name, this.destImpl, this.ddMemberInfo);
      }
   }

   private abstract class NotificationItem {
      private NotificationItem() {
      }

      abstract void processNotification();

      // $FF: synthetic method
      NotificationItem(Object x1) {
         this();
      }
   }

   private class LoadBalancerHandler implements DDMembersCacheChangeListener {
      private DDMembersCache ddMembersCache;

      public LoadBalancerHandler() {
         this.ddMembersCache = new DDMembersCacheImpl(DDLoadBalancerDelegateImpl.this.jmsSessionRuntimeContext, DDLoadBalancerDelegateImpl.this.ddInfo.getDestinationName(), DDLoadBalancerDelegateImpl.this.jmsSessionRuntimeContext.isForLocalCluster());
         this.ddMembersCache.addDDMembersCacheChangeListener(this);
      }

      public JMSID getId() {
         return null;
      }

      public void close() {
         this.ddMembersCache.removeDDMembersCacheChangeListener(this);
      }

      public void onCacheEntryAdd(CacheEntry cacheEntry) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("onCacheEntryAdd() single:  Entry = " + cacheEntry + " isProductionPaused= " + ((DDMemberInformation)cacheEntry).isProductionPaused() + " isInsertionPaused= " + ((DDMemberInformation)cacheEntry).isInsertionPaused() + "\n" + this);
         }

         synchronized(DDLoadBalancerDelegateImpl.this) {
            try {
               DDLoadBalancerDelegateImpl.this.addDestination((DDMemberInformation)cacheEntry);
            } catch (Throwable var5) {
               var5.printStackTrace();
            }

         }
      }

      public void onCacheEntryRemove(CacheEntry cacheEntry) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("onCacheEntryRemove() single:  Entry = " + cacheEntry + "\n" + this);
         }

         synchronized(DDLoadBalancerDelegateImpl.this) {
            DDLoadBalancerDelegateImpl.this.removeDestination((DDMemberInformation)cacheEntry);
         }
      }

      public void onCacheEntryAdd(CacheEntry[] cacheEntries) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("onCacheEntryAdd() multiple \n" + this);
         }

         synchronized(DDLoadBalancerDelegateImpl.this) {
            try {
               DDLoadBalancerDelegateImpl.this.addDestination((DDMemberInformation[])((DDMemberInformation[])cacheEntries));
            } catch (JMSException var5) {
               var5.printStackTrace();
            }

         }
      }

      public void onCacheEntryRemove(CacheEntry[] cacheEntries) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("onCacheEntryRemove() multiple \n" + this);
         }

         synchronized(DDLoadBalancerDelegateImpl.this) {
            DDLoadBalancerDelegateImpl.this.removeDestination((DDMemberInformation[])((DDMemberInformation[])cacheEntries));
         }
      }
   }
}
