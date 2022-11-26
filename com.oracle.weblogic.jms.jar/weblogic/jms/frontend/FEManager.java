package weblogic.jms.frontend;

import java.security.AccessController;
import java.util.Iterator;
import java.util.Map;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.BEDestinationCreateRequest;
import weblogic.jms.backend.BEDestinationImpl;
import weblogic.jms.backend.BERemoveSubscriptionRequest;
import weblogic.jms.backend.BEServerSessionPool;
import weblogic.jms.backend.BackEnd;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DurableSubscription;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPushEntry;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.common.Sequencer;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JMSConnectionRuntimeMBean;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.common.PartitionNameUtils;

public final class FEManager implements Invocable {
   private FETempDestinationFactory feTemporaryDestinationFactory = null;
   private final InvocableMonitor invocableMonitor;
   private final JMSService jmsService;

   public FEManager(InvocableMonitor invocableMonitor, JMSService jmsservice) {
      this.invocableMonitor = invocableMonitor;
      this.jmsService = jmsservice;
   }

   public JMSConnectionRuntimeMBean[] getConnections() {
      Map invocableMap = this.jmsService.getInvocableManagerDelegate().getInvocableMap(7);
      synchronized(invocableMap) {
         JMSConnectionRuntimeMBean[] connections = new JMSConnectionRuntimeMBean[invocableMap.size()];
         Iterator it = invocableMap.values().iterator();

         for(int i = 0; it.hasNext(); connections[i++] = ((FEConnection)it.next()).getRuntimeDelegate()) {
         }

         return connections;
      }
   }

   public long getConnectionsCurrentCount() {
      return (long)this.jmsService.getInvocableManagerDelegate().getInvocablesCurrentCount(7);
   }

   public long getConnectionsHighCount() {
      return (long)this.jmsService.getInvocableManagerDelegate().getInvocablesHighCount(7);
   }

   public long getConnectionsTotalCount() {
      return (long)this.jmsService.getInvocableManagerDelegate().getInvocablesTotalCount(7);
   }

   public JMSID getJMSID() {
      return null;
   }

   public ID getId() {
      return this.getJMSID();
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.jmsService.getDispatcherPartitionContext();
   }

   public InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   FETempDestinationFactory getTemporaryDestinationFactory() throws JMSException {
      synchronized(this) {
         if (this.feTemporaryDestinationFactory == null) {
            try {
               this.feTemporaryDestinationFactory = (FETempDestinationFactory)this.jmsService.getCtx().lookup("weblogic.jms.TempDestinationFactory");
            } catch (NamingException var4) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logNoTemporaryTemplatesLoggable(), var4);
            }
         }
      }

      return this.feTemporaryDestinationFactory;
   }

   private void serverSessionPoolClose(FEServerSessionPoolCloseRequest request) throws JMSException {
      BackEnd backEnd = this.jmsService.getBEDeployer().findBackEnd(request.getBackEndId());
      backEnd.checkShutdownOrSuspended("close server session pool");
      BEServerSessionPool serverSessionPool = backEnd.serverSessionPoolFind(request.getServerSessionPoolId());
      if (serverSessionPool != null) {
         backEnd.serverSessionPoolRemove(serverSessionPool);
         serverSessionPool.close();
      } else {
         throw new JMSException("Server session pool not found");
      }
   }

   private int destinationCreate(Request invocableRequest) throws JMSException {
      FEDestinationCreateRequest request = (FEDestinationCreateRequest)invocableRequest;
      DestinationImpl destination = null;
      switch (request.getState()) {
         case 0:
            String destinationName = request.getDestinationName();
            if (destinationName == null || destinationName.length() <= 0) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logNoDestinationNameLoggable());
            }

            destination = DDManager.findDDImplByDDNameWithShortName(destinationName);
            if (destination != null) {
               this.checkAndProcessCreateDestination(destination, request);
               break;
            } else {
               int index;
               if ((index = destinationName.indexOf(47)) != -1 && index != destinationName.length() - 1) {
                  String backEndName = destinationName.startsWith("./") ? null : destinationName.substring(0, index);
                  if (PartitionNameUtils.isInPartition() && backEndName.indexOf("$" + PartitionNameUtils.getPartitionName()) <= 0) {
                     backEndName = backEndName + "$" + PartitionNameUtils.getPartitionName();
                  }

                  String jndiName = "weblogic.jms.backend." + backEndName;
                  FrontEnd frontEnd = this.jmsService.getFEDeployer().getFrontEnd();
                  destinationName = destinationName.substring(index + 1, destinationName.length());
                  if (backEndName == null) {
                     frontEnd.checkShutdownOrSuspended();
                     BackEnd[] backEnds = this.jmsService.getBEDeployer().getBackEnds();

                     for(int i = 0; backEnds != null && i < backEnds.length; ++i) {
                        assert !backEnds[i].getName().startsWith("!weblogicreserved!");

                        try {
                           backEnds[i].checkShutdownOrSuspendedNeedLock("create destination");
                        } catch (JMSException var17) {
                           continue;
                        }

                        BEDestinationImpl beDestination = backEnds[i].findDestinationByCreateName(destinationName);
                        if (beDestination != null && beDestination.getDestinationImpl().getType() == request.getDestType() && beDestination.isStarted() && beDestination.isAvailableForCreateDestination()) {
                           request.setResult(new JMSDestinationCreateResponse(beDestination.getDestinationImpl()));
                           request.setState(Integer.MAX_VALUE);
                           break;
                        }
                     }

                     if (request.getState() != Integer.MAX_VALUE) {
                        AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                        throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logDestinationNotFoundLoggable(destinationName, ManagementService.getRuntimeAccess(kernelId).getServerName()));
                     }
                     break;
                  } else {
                     Object backEndObj;
                     try {
                        backEndObj = this.jmsService.getCtx().lookup(jndiName);
                     } catch (NamingException var16) {
                        throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logBackEndUnreachableLoggable(), var16);
                     }

                     if (!(backEndObj instanceof JMSServerId)) {
                        throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logBackEndUnknownLoggable());
                     }

                     JMSServerId backEndId = (JMSServerId)backEndObj;
                     frontEnd.checkShutdownOrSuspended();
                     BEDestinationCreateRequest child = new BEDestinationCreateRequest(backEndId.getId(), destinationName, request.getDestType(), true);
                     request.setBackEndId(backEndId);
                     request.setDestinationName(destinationName);
                     synchronized(request) {
                        request.rememberChild(child);
                        request.setState(1);
                     }

                     try {
                        JMSDispatcher dispatcher = this.jmsService.dispatcherFindOrCreate(backEndId.getDispatcherId());
                        request.dispatchAsync(dispatcher, child);
                        break;
                     } catch (DispatcherException var14) {
                        throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logFindFailedLoggable(), var14);
                     }
                  }
               }

               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logInvalidDestinationFormatLoggable(destinationName));
            }
         case 1:
         default:
            JMSDestinationCreateResponse response;
            if (request.getChild() != null) {
               response = (JMSDestinationCreateResponse)request.useChildResult(JMSDestinationCreateResponse.class);
            } else {
               response = (JMSDestinationCreateResponse)request.getResult();
            }

            request.setState(Integer.MAX_VALUE);
            DestinationImpl destination = response.getDestination();
      }

      return request.getState();
   }

   private void checkAndProcessCreateDestination(DestinationImpl destination, FEDestinationCreateRequest request) throws weblogic.jms.common.JMSException {
      if (destination.getType() != request.getDestType()) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logInvalidDestinationTypeLoggable(request.getDestinationName(), request.getDestType() == 2 ? "topic" : "queue"));
      } else {
         request.setResult(new JMSDestinationCreateResponse(destination));
         request.setState(Integer.MAX_VALUE);
      }
   }

   private int pushMessage(Request invocableRequest) {
      JMSPushRequest request = (JMSPushRequest)invocableRequest;
      MessageImpl message = request.getMessage();
      JMSPushEntry prevPushEntry = null;
      JMSPushEntry pushEntry = request.getFirstPushEntry();
      JMSID sequencerId = pushEntry.getSequencerId();

      do {
         if (!sequencerId.equals(pushEntry.getSequencerId())) {
            try {
               prevPushEntry.setNext((JMSPushEntry)null);
               request.setLastPushEntry(prevPushEntry);
               ((Sequencer)this.jmsService.getInvocableManagerDelegate().invocableFind(13, sequencerId)).pushMessage(request);
            } catch (JMSException var9) {
            }

            request = new JMSPushRequest(0, (JMSID)null, message);
            request.setFirstPushEntry(pushEntry);
            sequencerId = pushEntry.getSequencerId();
         }

         prevPushEntry = pushEntry;
      } while((pushEntry = pushEntry.getNext()) != null);

      try {
         prevPushEntry.setNext((JMSPushEntry)null);
         request.setLastPushEntry(prevPushEntry);
         ((Sequencer)this.jmsService.getInvocableManagerDelegate().invocableFind(13, sequencerId)).pushMessage(request);
      } catch (JMSException var8) {
      }

      return Integer.MAX_VALUE;
   }

   private int removeSubscription(Request invocableRequest) throws JMSException {
      FERemoveSubscriptionRequest request = (FERemoveSubscriptionRequest)invocableRequest;
      switch (request.getState()) {
         case 0:
            JMSServerId backEndId = request.getBackEndId();
            if (backEndId == null) {
               if (request.getClientIdPolicy() == 1) {
                  throw new InvalidDestinationException(JMSExceptionLogger.logInvalidUnrestrictedUnsubscribeLoggable(request.getName(), request.getClientId()).getMessage());
               }

               try {
                  Context context = this.jmsService.getCtx();
                  String jndiName = BEConsumerImpl.JNDINameForSubscription(BEConsumerImpl.clientIdPlusName(request.getClientId(), request.getName()));
                  backEndId = ((DurableSubscription)context.lookup(jndiName)).getBackEndId();
               } catch (NameNotFoundException var9) {
                  throw new InvalidDestinationException("Subscription " + request.getName() + " not found.");
               } catch (NamingException var10) {
                  InvalidDestinationException ide = new InvalidDestinationException("Subscription " + request.getName() + " not found");
                  ide.setLinkedException(var10);
                  throw ide;
               }
            }

            BERemoveSubscriptionRequest childRequest = new BERemoveSubscriptionRequest(backEndId, request.getDestinationName(), request.getClientId(), request.getClientIdPolicy(), request.getName());
            synchronized(request) {
               request.rememberChild(childRequest);
               request.setState(1);
            }

            try {
               JMSDispatcher dispatcher = this.jmsService.dispatcherFindOrCreate(backEndId.getDispatcherId());
               request.dispatchAsync(dispatcher, childRequest);
            } catch (DispatcherException var7) {
               throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logErrorRemovingSubscriptionLoggable(), var7);
            }

            return request.getState();
         case 1:
         default:
            request.useChildResult(VoidResponse.class);
            return Integer.MAX_VALUE;
      }
   }

   public FEConnection[] getFEConnections() {
      Map invocableMap = this.jmsService.getInvocableManagerDelegate().getInvocableMap(7);
      synchronized(invocableMap) {
         FEConnection[] connections = new FEConnection[invocableMap.size()];
         return (FEConnection[])((FEConnection[])invocableMap.values().toArray(connections));
      }
   }

   public int invoke(Request request) throws JMSException {
      JMSService.checkThreadInJMSServicePartition(this.jmsService, "FEManger");
      switch (request.getMethodId()) {
         case 3841:
            return this.destinationCreate(request);
         case 5377:
            FERemoveSubscriptionRequest remSubReq = (FERemoveSubscriptionRequest)request;
            DestinationImpl destination = remSubReq.getDestination();
            if (destination != null) {
               this.checkPartition(destination);
               JMSServerId refreshedBackEndId = this.refreshBackEndId(destination.getServerName());
               destination.setBackEndID(refreshedBackEndId);
               remSubReq.setBackEndId(refreshedBackEndId);
            }

            return this.removeSubscription(remSubReq);
         case 5633:
            this.serverSessionPoolClose((FEServerSessionPoolCloseRequest)request);
            request.setResult(new VoidResponse());
            request.setState(Integer.MAX_VALUE);
            return Integer.MAX_VALUE;
         case 15617:
            return this.pushMessage(request);
         default:
            throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logNoMethodLoggable(request.getMethodId()));
      }
   }

   JMSServerId refreshBackEndId(String backEndName) throws JMSException {
      JMSServerId backEndId = null;
      String jndiName = "weblogic.jms.backend." + backEndName;

      try {
         Object found = this.jmsService.getCtx().lookup(jndiName);

         try {
            backEndId = (JMSServerId)found;
            return backEndId;
         } catch (ClassCastException var8) {
            String info = debugClassCastException(jndiName, backEndName, found);
            if (var8.getCause() == null) {
               var8.initCause(new ClassCastException(info));
               throw var8;
            } else {
               ClassCastException cce2 = new ClassCastException(info);
               cce2.initCause(var8);
               throw cce2;
            }
         }
      } catch (NamingException var9) {
         throw new weblogic.jms.common.JMSException(var9);
      }
   }

   private static String debugClassCastException(String jndiName, String backEndName, Object found) {
      String info = "destination for jndi " + jndiName + " has backend " + backEndName + " found object <";
      if (found == null) {
         info = info + found + ">";
      } else {
         info = info + found.getClass().getName() + " " + found + ">";
      }

      return info;
   }

   static boolean isStaleDestEx(JMSException jmsEx) {
      Throwable t = null;

      for(t = jmsEx; t != null; t = ((Throwable)t).getCause()) {
         if (t instanceof InvalidDestinationException) {
            return true;
         }
      }

      return false;
   }

   final void checkPartition(DestinationImpl destination) throws JMSException {
      String partition = JMSService.getSafePartitionNameFromThread();
      String destPartition = destination.getPartitionName();
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FEManager.checkPartition: partitionFromThread=" + partition + ", destPartition=" + destPartition);
      }

      if (!PartitionUtils.isSamePartition(partition, destPartition)) {
         throw new JMSException("The given topic is not in the same partition as the JMS connection/session where the unsubscribe is invoked");
      }
   }
}
