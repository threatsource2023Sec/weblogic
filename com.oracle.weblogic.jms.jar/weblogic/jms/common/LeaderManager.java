package weblogic.jms.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.cluster.ClusterMembersChangeEvent;
import weblogic.cluster.ClusterMembersChangeListener;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.jms.JMSService;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.management.ManagementException;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;

public final class LeaderManager implements Invocable, ClusterMembersChangeListener {
   private static final LeaderManager leaderManager = new LeaderManager();
   private JMSID requestStatsSourceId;
   private long sequenceNumber;
   private final Map hashByName = new HashMap();

   private LeaderManager() {
      ClusterServices cm = Locator.locateClusterServices();
      if (cm != null) {
         cm.addClusterMembersListener(this);
      }

   }

   public JMSID getJMSID() {
      return null;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return null;
   }

   public InvocableMonitor getInvocableMonitor() {
      return null;
   }

   private void setRequestStatsSourceId(JMSID requestStatsSourceId) {
      this.requestStatsSourceId = requestStatsSourceId;
   }

   private JMSID getRequestStatsSourceId() {
      return this.requestStatsSourceId;
   }

   public int invoke(Request request) throws Throwable {
      switch (request.getMethodId()) {
         case 16405:
            return this.leaderBindSingular((LeaderBindRequest)request);
         case 16661:
            LeaderBindFailedRequest lbfr = (LeaderBindFailedRequest)request;
            this.aggregatableDidBind(lbfr.getJNDIName(), lbfr.getLeaderID(), lbfr.getSequenceNumber());
         default:
            return Integer.MAX_VALUE;
      }
   }

   private synchronized long getNextSequenceNumber() {
      return (long)(this.sequenceNumber++);
   }

   private synchronized Map getPartitionHashByName() {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      HashMap partitionHashByName = (HashMap)this.hashByName.get(partitionName);
      if (partitionHashByName == null) {
         partitionHashByName = new HashMap();
         this.hashByName.put(partitionName, partitionHashByName);
      }

      return partitionHashByName;
   }

   public synchronized void removePartitionHashbyName() {
      this.hashByName.remove(JMSService.getSafePartitionNameFromThread());
   }

   private int leaderBindSingular(LeaderBindRequest request) {
      String jndiName = request.getJNDIName();
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(" + request + ")");
      }

      if (this.requestStatsSourceId == null) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Not Granted 10)");
         }

         request.setResult(new LeaderBindResponse(false, this.requestStatsSourceId, this.getNextSequenceNumber(), "The statistics source id is null"));
         request.setState(Integer.MAX_VALUE);
         return Integer.MAX_VALUE;
      } else if (jndiName == null) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Not Granted 30)");
         }

         request.setResult(new LeaderBindResponse(false, this.requestStatsSourceId, this.getNextSequenceNumber(), "The requested JNDI name was null"));
         request.setState(Integer.MAX_VALUE);
         return Integer.MAX_VALUE;
      } else {
         Map partitionHashByName = this.getPartitionHashByName();
         long sequenceNumber;
         synchronized(partitionHashByName) {
            label71: {
               int var10000;
               try {
                  Object object = JMSService.getJMSServiceWithManagementException().lookupWithCIC(true, jndiName);
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Not Granted 40)");
                  }

                  request.setResult(new LeaderBindResponse(false, this.requestStatsSourceId, this.getNextSequenceNumber(), "The JNDI name '" + jndiName + "' was found, and was bound to an object of type '" + object.getClass().getName() + "' : " + object + ". This may be caused by 1) multiple JMS client connections using the same RESTRICTED client ID, or 2) a delay in JNDI synchronization among the cluster members. This may be resolved by 1) using different client IDs among different applications, or using UNRESTRICTED client IDs, or 2) by periodically retrying JMS connection creation upon hitting this exception in your application code. "));
                  request.setState(Integer.MAX_VALUE);
                  var10000 = Integer.MAX_VALUE;
               } catch (ManagementException | NamingException var10) {
                  if (!(var10 instanceof NameNotFoundException)) {
                     if (JMSDebug.JMSCommon.isDebugEnabled()) {
                        JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Not Granted 50)");
                     }

                     request.setResult(new LeaderBindResponse(false, this.requestStatsSourceId, this.getNextSequenceNumber(), "An exception occured looking up " + jndiName + ".  The error was " + var10));
                     request.setState(Integer.MAX_VALUE);
                     return Integer.MAX_VALUE;
                  }

                  String serverName = request.getServerName();
                  String realJndiName = jndiName.replace('/', '.');
                  if (partitionHashByName.get(realJndiName) != null) {
                     if (JMSDebug.JMSCommon.isDebugEnabled()) {
                        JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Not Granted 60)");
                     }

                     request.setResult(new LeaderBindResponse(false, this.requestStatsSourceId, this.getNextSequenceNumber(), "The name " + realJndiName + " has been previously reserved by server " + serverName));
                     request.setState(Integer.MAX_VALUE);
                     return Integer.MAX_VALUE;
                  }

                  sequenceNumber = this.getNextSequenceNumber();
                  partitionHashByName.put(realJndiName, new SerAndSeq(serverName, sequenceNumber));
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular name put in pending list=" + realJndiName);
                  }
                  break label71;
               }

               return var10000;
            }
         }

         request.setResult(new LeaderBindResponse(true, this.requestStatsSourceId, sequenceNumber));
         request.setState(Integer.MAX_VALUE);
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.leaderBindSingular(Granted " + jndiName + " to " + request.getServerName() + ")");
         }

         return Integer.MAX_VALUE;
      }
   }

   public void aggregatableDidBind(String jndiName, JMSID leaderId, long sequenceNumber) {
      if (jndiName != null && leaderId != null && this.requestStatsSourceId != null && leaderId.equals(this.requestStatsSourceId)) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.aggregatableDidBind(" + jndiName + ":" + leaderId + ":" + sequenceNumber + ")");
         }

         SerAndSeq sas = null;
         Map partitionHashByName = this.getPartitionHashByName();
         synchronized(partitionHashByName) {
            sas = (SerAndSeq)partitionHashByName.get(jndiName);
            if (sas != null && (sequenceNumber < 0L || sequenceNumber == sas.getSequenceNumber())) {
               partitionHashByName.remove(jndiName);
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("LeaderManager.aggregatableDidBind(removed " + jndiName + " with value " + sas + ")");
               }
            } else if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("LeaderManager.aggregatableDidBind(not matched, not removed sequenceNumber " + sequenceNumber + " for " + jndiName + " with value " + sas + ")");
            }

         }
      } else {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.aggregatableDidBind( aborted on mismatched " + jndiName + ":" + leaderId + ":" + sequenceNumber + " [myId " + this.requestStatsSourceId + "])");
         }

      }
   }

   public void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      if (cece.getAction() == 1) {
         String serverName = cece.getClusterMemberInfo().serverName();
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("LeaderManager.clusterMembersChanged(" + serverName + ") is being removed");
         }

         Iterator partitionIterator = this.hashByName.entrySet().iterator();

         while(partitionIterator.hasNext()) {
            Map partitionHashByName = (Map)((Map.Entry)partitionIterator.next()).getValue();
            synchronized(partitionHashByName) {
               Iterator i = partitionHashByName.entrySet().iterator();

               while(i.hasNext()) {
                  Map.Entry entry = (Map.Entry)i.next();
                  SerAndSeq sas = (SerAndSeq)entry.getValue();
                  String entryServerName = sas.getServerName();
                  if (serverName.equals(entryServerName)) {
                     i.remove();
                  }
               }
            }
         }

      }
   }

   public static synchronized LeaderManager getLeaderManager() {
      return leaderManager;
   }

   public static synchronized LeaderManager setupLeaderManager(JMSID statsSource) {
      if (leaderManager.getRequestStatsSourceId() == null) {
         leaderManager.setRequestStatsSourceId(statsSource);
      }

      return leaderManager;
   }

   private static class SerAndSeq {
      private String serverName;
      private long sequenceNumber;

      private SerAndSeq(String paramServerName, long paramSequenceNumber) {
         this.serverName = paramServerName;
         this.sequenceNumber = paramSequenceNumber;
      }

      private String getServerName() {
         return this.serverName;
      }

      private long getSequenceNumber() {
         return this.sequenceNumber;
      }

      public String toString() {
         return "SerAndSeq(" + this.serverName + "/" + this.sequenceNumber + ")";
      }

      // $FF: synthetic method
      SerAndSeq(String x0, long x1, Object x2) {
         this(x0, x1);
      }
   }
}
