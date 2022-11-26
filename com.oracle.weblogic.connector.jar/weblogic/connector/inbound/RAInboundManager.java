package weblogic.connector.inbound;

import com.bea.connector.diagnostic.EndpointType;
import com.bea.connector.diagnostic.InboundAdapterType;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.SystemException;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RAInboundException;
import weblogic.connector.external.ElementNotFoundException;
import weblogic.connector.external.InboundInfo;
import weblogic.connector.external.SuspendableEndpointFactory;
import weblogic.connector.monitoring.ConnectorComponentRuntimeMBeanImpl;
import weblogic.connector.monitoring.inbound.ConnectorInboundRuntimeMBeanImpl;
import weblogic.connector.transaction.inbound.InboundRecoveryManager;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class RAInboundManager {
   private RAInstanceManager raInstanceMgr;
   private InboundRecoveryManager recoveryMgr;
   private Hashtable msgListenerToEndptFactoryMap;
   private Hashtable endptFactorySuspendStateMap;
   private Hashtable msgListenerToEJBMap;
   private HashMap msgListenerToInboundRuntimeMap;
   private String state;
   private static final String RUNNING = Debug.getStringRunning();
   private static final String SUSPENDED = Debug.getStringSuspended();
   private boolean isInbound;

   public RAInboundManager(RAInstanceManager aRA) throws RAInboundException {
      this.raInstanceMgr = aRA;
      this.msgListenerToEndptFactoryMap = new Hashtable();
      this.msgListenerToEJBMap = new Hashtable();
      this.msgListenerToInboundRuntimeMap = new HashMap();
      this.endptFactorySuspendStateMap = new Hashtable();
      this.recoveryMgr = new InboundRecoveryManager();
      this.state = RUNNING;
   }

   public void activate() throws RAInboundException {
   }

   public void deactivate() throws RAInboundException {
      this.stop();
   }

   public void rollback() throws RAInboundException {
   }

   public void suspend(Properties props) throws RAInboundException {
      Debug.enter(this, "suspend(properties)");

      try {
         this.suspendOrResumeMEF(1, props);
         this.state = SUSPENDED;
      } finally {
         Debug.exit(this, "suspend(properties)");
      }

   }

   public void resume(Properties props) throws RAInboundException {
      Debug.enter(this, "resume(properties)");

      try {
         this.suspendOrResumeMEF(2, props);
         this.state = RUNNING;
      } finally {
         Debug.exit(this, "resume(properties)");
      }

   }

   public void stop() throws RAInboundException {
      Debug.enter(this, "stop()");
      RAInboundException raInboundException = new RAInboundException();

      try {
         this.recoveryMgr.onRAStop(raInboundException);
         this.disconnectMEFs(raInboundException);
         if (!raInboundException.isEmpty()) {
            throw raInboundException;
         }
      } finally {
         this.msgListenerToEndptFactoryMap.clear();
         this.removeAllMDBRuntimeBeans();
         this.cleanupRuntimes();
         Debug.exit(this, "stop()");
      }

   }

   private void removeAllMDBRuntimeBeans() {
      Iterator inboundBeans = this.msgListenerToInboundRuntimeMap.values().iterator();

      while(inboundBeans.hasNext()) {
         ConnectorInboundRuntimeMBeanImpl inboundBean = (ConnectorInboundRuntimeMBeanImpl)inboundBeans.next();
         inboundBean.removeAllMDBRuntimes();
      }

   }

   public void addEndpointFactory(String msgListenerType, MessageEndpointFactory endpointFactory, MessageDrivenEJBRuntimeMBean mdbRuntime) {
      List endpointFactories = null;
      if (this.msgListenerToEndptFactoryMap.containsKey(msgListenerType)) {
         endpointFactories = (List)this.msgListenerToEndptFactoryMap.get(msgListenerType);
      } else {
         endpointFactories = new Vector();
         this.msgListenerToEndptFactoryMap.put(msgListenerType, endpointFactories);
      }

      ((List)endpointFactories).add(endpointFactory);
      this.endptFactorySuspendStateMap.put(endpointFactory, Boolean.FALSE);
      ConnectorInboundRuntimeMBeanImpl connectorInboundRuntimeMBeanImpl = (ConnectorInboundRuntimeMBeanImpl)this.msgListenerToInboundRuntimeMap.get(msgListenerType);
      if (connectorInboundRuntimeMBeanImpl != null) {
         connectorInboundRuntimeMBeanImpl.addMDBRuntime(mdbRuntime);
      }

   }

   public void removeEndpointFactory(String msgListenerType, MessageEndpointFactory endpointFactory, MessageDrivenEJBRuntimeMBean mdbRuntime) throws SystemException {
      List endpointFactories = null;
      if (this.msgListenerToEndptFactoryMap.containsKey(msgListenerType)) {
         endpointFactories = (List)this.msgListenerToEndptFactoryMap.get(msgListenerType);
         endpointFactories.remove(endpointFactory);
         if (endpointFactories.isEmpty()) {
            this.msgListenerToEndptFactoryMap.remove(msgListenerType);
         }

         this.endptFactorySuspendStateMap.remove(endpointFactory);
         ConnectorInboundRuntimeMBeanImpl connectorInboundRuntimeMBeanImpl = (ConnectorInboundRuntimeMBeanImpl)this.msgListenerToInboundRuntimeMap.get(msgListenerType);
         if (connectorInboundRuntimeMBeanImpl != null) {
            connectorInboundRuntimeMBeanImpl.removeMDBRuntime(mdbRuntime);
         }
      }

   }

   public void setupForRecovery(ActivationSpec activationSpec, String endpointName) throws SystemException {
      this.recoveryMgr.onActivateEndpoint(this.raInstanceMgr, activationSpec, endpointName);
   }

   public void cleanupForRecovery(ActivationSpec activationSpec) throws SystemException {
      this.recoveryMgr.onDeActivateEndpoint(activationSpec);
   }

   public void addEJB(String msgListenerType, String ejbName) {
      List ejbs = null;
      if (this.msgListenerToEJBMap.containsKey(msgListenerType)) {
         ejbs = (List)this.msgListenerToEJBMap.get(msgListenerType);
      } else {
         ejbs = new Vector();
         this.msgListenerToEJBMap.put(msgListenerType, ejbs);
      }

      ((List)ejbs).add(ejbName);
   }

   public void removeEJB(String msgListenerType, String ejbName) {
      List ejbs = null;
      if (this.msgListenerToEJBMap.containsKey(msgListenerType)) {
         ejbs = (List)this.msgListenerToEJBMap.get(msgListenerType);
         ejbs.remove(ejbName);
         if (ejbs.isEmpty()) {
            this.msgListenerToEJBMap.remove(msgListenerType);
         }
      }

   }

   private void suspendOrResumeMEF(int action, Properties props) throws RAInboundException {
      Debug.enter(this, "suspendOrResumeMEF( action , props )");

      try {
         Iterator mefs = this.getEndpointFactories().iterator();
         MessageEndpointFactory mef = null;
         RAInboundException raInboundException = null;

         while(true) {
            do {
               do {
                  if (!mefs.hasNext()) {
                     if (raInboundException != null) {
                        throw raInboundException;
                     }

                     return;
                  }

                  mef = (MessageEndpointFactory)mefs.next();
               } while(mef == null);
            } while(!(mef instanceof SuspendableEndpointFactory));

            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

            try {
               if (action == 1) {
                  this.raInstanceMgr.getAdapterLayer().suspend((SuspendableEndpointFactory)mef, props, kernelId);
               } else {
                  this.raInstanceMgr.getAdapterLayer().resume((SuspendableEndpointFactory)mef, props, kernelId);
               }
            } catch (ResourceException var11) {
               if (raInboundException == null) {
                  raInboundException = new RAInboundException();
               }

               Utils.consolidateException(raInboundException, var11);
            }
         }
      } finally {
         Debug.exit(this, "suspendOrResumeMEF( action , props )");
      }
   }

   private void disconnectMEFs(RAInboundException raInboundException) {
      Debug.enter(this, "disconnect()");

      try {
         Iterator mefs = this.getEndpointFactories().iterator();
         MessageEndpointFactory mef = null;

         while(mefs.hasNext()) {
            mef = (MessageEndpointFactory)mefs.next();
            if (mef != null && mef instanceof SuspendableEndpointFactory) {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

               try {
                  this.raInstanceMgr.getAdapterLayer().disconnect((SuspendableEndpointFactory)mef, kernelId);
               } catch (ResourceException var9) {
                  Utils.consolidateException(raInboundException, var9);
               }
            }
         }
      } finally {
         Debug.exit(this, "suspendOrResumeMEF( action , props )");
      }

   }

   public RAInstanceManager getRA() {
      return this.raInstanceMgr;
   }

   public List getEndpointFactories() {
      List allEndpointFactories = new Vector();
      Iterator iterator = this.msgListenerToEndptFactoryMap.values().iterator();
      MessageEndpointFactory mef = null;

      while(iterator.hasNext()) {
         List mefList = (List)iterator.next();
         Iterator mefIterator = mefList.iterator();

         while(mefIterator.hasNext()) {
            mef = (MessageEndpointFactory)mefIterator.next();
            if (mef != null) {
               allEndpointFactories.add(mef);
            }
         }
      }

      return allEndpointFactories;
   }

   public List getEndpointFactories(String msgListenerType) {
      return (List)this.msgListenerToEndptFactoryMap.get(msgListenerType);
   }

   public List getEJBs(String msgListenerType) {
      return (List)this.msgListenerToEJBMap.get(msgListenerType);
   }

   public int getAvailableInboundConnectionsCount() {
      int returnCount = 0;

      try {
         returnCount = this.raInstanceMgr.getRAInfo().getInboundInfos().size();
      } catch (ElementNotFoundException var3) {
      }

      return returnCount;
   }

   public int getSubscribedInboundConnectionsCount() {
      return this.msgListenerToEJBMap.size();
   }

   public int getActiveInboundConnectionsCount() {
      return 0;
   }

   public InboundAdapterType[] getXMLBeans(ConnectorDiagnosticImageSource src) {
      boolean timedout = src != null ? src.timedout() : false;
      int numInbound = this.getAvailableInboundConnectionsCount();
      InboundAdapterType[] inboundXBeans = new InboundAdapterType[numInbound];
      if (numInbound > 0 && !timedout) {
         Iterator msgListenersIter = null;

         try {
            msgListenersIter = this.raInstanceMgr.getRAInfo().getInboundInfos().iterator();
         } catch (ElementNotFoundException var14) {
         }

         for(int listenerIdx = 0; msgListenersIter.hasNext(); ++listenerIdx) {
            InboundInfo inboundInfo = (InboundInfo)msgListenersIter.next();
            InboundAdapterType inboundXBean = InboundAdapterType.Factory.newInstance();
            inboundXBean.setName(inboundInfo.getDisplayName());
            inboundXBean.setState(this.getState());
            List ejbs = (List)this.msgListenerToEJBMap.get(inboundInfo.getMsgType());
            if (ejbs != null && ejbs.size() > 0) {
               EndpointType[] endpointXBeans = new EndpointType[ejbs.size()];
               Iterator endpointIter = ejbs.iterator();

               for(int endpointIdx = 0; endpointIter.hasNext(); ++endpointIdx) {
                  EndpointType endpointXBean = EndpointType.Factory.newInstance();
                  endpointXBean.setMsgType(inboundInfo.getMsgType());
                  endpointXBean.setName((String)endpointIter.next());
                  endpointXBeans[endpointIdx] = endpointXBean;
               }

               inboundXBean.addNewEndpoints().setEndpointArray(endpointXBeans);
            }

            inboundXBeans[listenerIdx] = inboundXBean;
         }
      }

      return inboundXBeans;
   }

   public void setupRuntimes(ConnectorComponentRuntimeMBeanImpl parent) {
      List inboundInfoList = null;

      try {
         inboundInfoList = this.raInstanceMgr.getRAInfo().getInboundInfos();
      } catch (ElementNotFoundException var9) {
      }

      if (inboundInfoList != null) {
         Iterator inboundIterator = inboundInfoList.iterator();
         InboundInfo inboundInfo = null;

         while(inboundIterator.hasNext()) {
            this.isInbound = true;
            inboundInfo = (InboundInfo)inboundIterator.next();
            String name = inboundInfo.getMsgType();

            try {
               Debug.deployment("Creating a new ConnectorInboundRuntimeMBeanImpl for " + name);
               ConnectorInboundRuntimeMBeanImpl connectorInboundRuntimeMBeanImpl = new ConnectorInboundRuntimeMBeanImpl(name, inboundInfo.getMsgType(), inboundInfo.getActivationSpec().getActivationSpecClass(), parent, this);
               this.msgListenerToInboundRuntimeMap.put(inboundInfo.getMsgType(), connectorInboundRuntimeMBeanImpl);
               parent.addConnInboundRuntime(connectorInboundRuntimeMBeanImpl);
            } catch (Exception var8) {
               Debug.logCreateInboundRuntimeMBeanFailed(name, var8.toString());
               Debug.deployment("Exception Creating a new ConnectorInboundRuntimeMBeanImpl for " + name, var8);
            }
         }
      }

   }

   public void cleanupRuntimes() {
      Iterator var1 = this.msgListenerToInboundRuntimeMap.values().iterator();

      while(var1.hasNext()) {
         ConnectorInboundRuntimeMBeanImpl rt = (ConnectorInboundRuntimeMBeanImpl)var1.next();
         Utils.unregisterRuntimeMBean(rt);
         ((ConnectorComponentRuntimeMBeanImpl)this.raInstanceMgr.getConnectorComponentRuntimeMBean()).removeConnInboundRuntime(rt);
      }

   }

   public String getState() {
      return this.state;
   }

   public boolean isInboundRA() {
      return this.isInbound;
   }

   public void setEndpointFactorySuspendedState(MessageEndpointFactory endptFactory, boolean isSuspended) {
      if (this.endptFactorySuspendStateMap.containsKey(endptFactory)) {
         this.endptFactorySuspendStateMap.put(endptFactory, isSuspended);
      } else {
         throw new AssertionError("Attempt to set suspend state for endpoint factory not in map");
      }
   }
}
