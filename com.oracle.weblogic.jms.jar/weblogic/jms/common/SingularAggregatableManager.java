package weblogic.jms.common;

import java.security.AccessController;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import javax.naming.NamingException;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.jms.JMSService;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.Request;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.Response;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManagerFactory;

public final class SingularAggregatableManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final Hashtable pendingRequests = new Hashtable();
   private final Hashtable boundAggregatables = new Hashtable();
   private final String serverName;
   private final JMSService jmsService;

   public static SingularAggregatableManager findThrowsJMSException() throws javax.jms.JMSException {
      String partitionName = JMSService.getSafePartitionNameFromThread();
      JMSService jmsService = JMSService.getJMSServiceWithPartitionName(partitionName);
      if (null == jmsService) {
         JMSException jmsException = new JMSException("JMSService for partition " + partitionName + " has been shutdown or not started");
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatableManager.findThrowsJMSException() ", jmsException);
         }

         throw jmsException;
      } else {
         return jmsService.getSingularAggregatableManagerWithJMSException();
      }
   }

   public SingularAggregatableManager(JMSService jmsService) {
      this.jmsService = jmsService;
      this.serverName = ManagementService.getRuntimeAccess(kernelId).getServerName();
   }

   private JMSDispatcher findLeader() throws javax.jms.JMSException {
      ClusterServices cs = Locator.locateClusterServices();
      JMSDispatcher result;
      ClusterMemberInfo cmi;
      if (cs != null && (cmi = cs.getLocalMember()) != null) {
         String bestServer = cmi.serverName();
         Collection remoteMembers = cs.getRemoteMembers();
         ClusterMemberInfo bestCMI = cmi;
         if (remoteMembers != null) {
            Iterator it = remoteMembers.iterator();

            while(it.hasNext()) {
               cmi = (ClusterMemberInfo)it.next();
               String candidateServer = cmi.serverName();
               if (bestServer.compareTo(candidateServer) < 0) {
                  bestCMI = cmi;
               }
            }
         }

         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatableManager.findLeader Leader is " + bestCMI.serverName());
         }

         DispatcherId dispatcherId = new DispatcherId(bestCMI.serverName(), bestCMI.identity().getPersistentIdentity().toString());

         try {
            result = this.jmsService.dispatcherFindOrCreate(dispatcherId);
         } catch (DispatcherException var9) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Exception:", var9);
            }

            result = this.jmsService.localDispatcherFind();
         }
      } else {
         result = this.jmsService.localDispatcherFind();
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.findLeader(" + result + ")");
      }

      return result;
   }

   public void singularBindStart(String jndiName, SingularAggregatable sAg, Request parentRequest) throws javax.jms.JMSException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindStart(" + jndiName + ":" + sAg + ")");
      }

      if (jndiName != null && sAg != null) {
         sAg.setJNDIName(jndiName);
         JMSDispatcher leaderDispatcher = this.findLeader();
         if (leaderDispatcher == null) {
            throw new JMSException("Could not get the leader, impossible to bind");
         } else {
            sAg.setLeaderDispatcher(leaderDispatcher);
            LeaderBindRequest lbr;
            synchronized(this.pendingRequests) {
               lbr = new LeaderBindRequest(this.serverName, jndiName);
            }

            try {
               if (parentRequest != null) {
                  parentRequest.rememberChild(lbr);
                  parentRequest.dispatchAsync(leaderDispatcher, lbr);
               } else {
                  leaderDispatcher.dispatchAsync(lbr);
                  synchronized(this.pendingRequests) {
                     this.pendingRequests.put(jndiName, lbr);
                  }
               }

            } catch (DispatcherException var10) {
               throw new JMSException("Could not dispatch request to leader", var10);
            }
         }
      } else {
         throw new JMSException("Invalid arguments to SingularAggregatable.bind");
      }
   }

   private static void sendFailedToBind(SingularAggregatable sAg, LeaderBindResponse lbr) {
      LeaderBindFailedRequest lbfr = new LeaderBindFailedRequest(sAg.getJNDIName(), sAg.getLeaderJMSID(), sAg.getLeaderSequenceNumber());
      JMSDispatcher leaderDispatcher = sAg.getLeaderDispatcher();
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.sendFailedToBind(" + sAg + ":" + lbr + ")");
      }

      if (leaderDispatcher == null) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatableManager.sendFailedToBind failed because leaderDispatcher is null");
         }

      } else {
         try {
            JMSServerUtilities.anonDispatchNoReply(lbfr, leaderDispatcher);
         } catch (javax.jms.JMSException var5) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish(failed to dispatch" + var5 + ")");
            }
         }

      }
   }

   public String singularBindFinish(SingularAggregatable sAg, Request parentRequest) throws javax.jms.JMSException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish(" + sAg + ")");
      }

      if (sAg == null) {
         throw new JMSException("Invalid input parameters");
      } else {
         String jndiName = sAg.getJNDIName();

         LeaderBindResponse lbr;
         try {
            Response response;
            if (parentRequest != null && parentRequest.getChild() != null) {
               response = parentRequest.useChildResult(LeaderBindResponse.class);
            } else {
               LeaderBindRequest originalRequest;
               synchronized(this.pendingRequests) {
                  originalRequest = (LeaderBindRequest)this.pendingRequests.remove(jndiName);
               }

               if (originalRequest == null) {
                  throw new JMSException("Cannot find the request to complete");
               }

               response = originalRequest.getResult();
            }

            if (response == null) {
               throw new AssertionError("In singularBindFinish the response is null");
            }

            lbr = (LeaderBindResponse)response;
         } catch (javax.jms.JMSException var15) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("Exception:", var15);
            }

            throw new JMSException("Unexpected response from leader", var15);
         }

         if (!lbr.doBind()) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish leader denied request");
            }

            return lbr.getReasonForRejection();
         } else {
            sAg.setLeaderJMSID(lbr.getLeaderJMSID());
            sAg.setLeaderSequenceNumber(lbr.getLeaderSequenceNumber());
            SingularAggregatable old;
            synchronized(this.boundAggregatables) {
               old = (SingularAggregatable)this.boundAggregatables.put(jndiName, sAg);
               if (old != null) {
                  this.boundAggregatables.put(jndiName, old);
               }
            }

            if (old != null) {
               sendFailedToBind(sAg, lbr);
               throw new JMSException("Attempt to bind to a name we have already bound: (" + lbr + "/" + sAg + "/" + old + ")");
            } else {
               try {
                  this.jmsService.bindWithCICSU(true, jndiName, sAg, kernelId);
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish bind worked for " + jndiName + " + on " + sAg);
                  }
               } catch (ManagementException | NamingException var13) {
                  if (JMSDebug.JMSCommon.isDebugEnabled()) {
                     JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish bind got exception (" + sAg + " jndiName" + jndiName + ")" + var13.getMessage());
                  }

                  synchronized(this.boundAggregatables) {
                     this.boundAggregatables.remove(jndiName);
                  }

                  sendFailedToBind(sAg, lbr);
                  throw new JMSException("Failed to bind name " + jndiName, var13);
               }

               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBindFinish bound (" + sAg + ")");
               }

               return null;
            }
         }
      }
   }

   public String singularBind(String jndiName, SingularAggregatable sAg) throws javax.jms.JMSException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularBind() " + jndiName);
      }

      this.singularBindStart(jndiName, sAg, (Request)null);
      String result = this.singularBindFinish(sAg, (Request)null);
      return result;
   }

   public void singularUnbind(String jndiName) throws javax.jms.JMSException {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularUnbind(" + jndiName + ")");
      }

      if (jndiName == null) {
         throw new JMSException("Invalid paramter to singularUnbind");
      } else {
         SingularAggregatable sAg;
         synchronized(this.boundAggregatables) {
            if ((sAg = (SingularAggregatable)this.boundAggregatables.remove(jndiName)) == null) {
               return;
            }
         }

         try {
            this.jmsService.unbindWithCICSU(true, jndiName, kernelId);
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularUnind unbind worked for " + jndiName);
            }
         } catch (NamingException var5) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("SingularAggregatableManager.singularUnbind got exception unbinding " + jndiName + ";  The exception is: " + var5);
            }
         }

         LeaderBindResponse lbr = new LeaderBindResponse(true, sAg.getLeaderJMSID(), sAg.getLeaderSequenceNumber());
         sendFailedToBind(sAg, lbr);
      }
   }

   public void aggregatableDidBind(String jndiName, SingularAggregatable sAg) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatableManager.aggregatableDidBind(" + jndiName + ":" + sAg + ")");
      }

      if (jndiName != null && sAg != null) {
         SingularAggregatable foundsAg;
         synchronized(this.boundAggregatables) {
            if ((foundsAg = (SingularAggregatable)this.boundAggregatables.get(jndiName)) == null) {
               return;
            }
         }

         JMSID foundsAgJMSID;
         if ((foundsAgJMSID = foundsAg.getLeaderJMSID()) != null) {
            JMSID inputJMSID;
            if ((inputJMSID = sAg.getLeaderJMSID()) != null) {
               int compared;
               if ((compared = foundsAgJMSID.compareTo(inputJMSID)) != 0 || foundsAg.getLeaderSequenceNumber() != sAg.getLeaderSequenceNumber()) {
                  byte compared;
                  if (compared == 0) {
                     if (foundsAg.getLeaderSequenceNumber() <= sAg.getLeaderSequenceNumber()) {
                        compared = -1;
                     } else {
                        compared = 1;
                     }
                  } else {
                     compared = -1;
                  }

                  boolean amWinner = compared > 0;
                  foundsAg.hadConflict(amWinner);
                  if (!amWinner) {
                     WorkManagerFactory.getInstance().getSystem().schedule(new SingularUnbindOnConflictThread(jndiName));
                  }

               }
            }
         }
      } else {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatableManager.aggregatableDidBind failed due to bad input parameters");
         }

      }
   }

   private class SingularUnbindOnConflictThread implements Runnable {
      String jndiName;

      private SingularUnbindOnConflictThread(String jndiName) {
         this.jndiName = jndiName;
      }

      public void run() {
         try {
            SingularAggregatableManager.this.singularUnbind(this.jndiName);
         } catch (javax.jms.JMSException var2) {
         }

      }

      // $FF: synthetic method
      SingularUnbindOnConflictThread(String x1, Object x2) {
         this(x1);
      }
   }
}
