package weblogic.jms.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.jms.dd.DDHandler;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dd.DDMember;
import weblogic.jms.dd.DDStatusListener;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.utils.LocatorUtilities;

@Service
public final class CDSServer implements CDSListProvider {
   private final List listeners = new LinkedList();
   private final RuntimeAccess runtimeAccess;
   private final ServerRuntimeMBean serverRuntimeMBean;

   public static synchronized CDSServer getSingleton() {
      return CDSServer.CDSServerInitializer.INSTANCE;
   }

   @Inject
   private CDSServer(RuntimeAccess runtimeAccess) {
      this.runtimeAccess = runtimeAccess;
      ServerRuntimeMBean localMBean = null;

      try {
         localMBean = runtimeAccess.getServerRuntime();
      } catch (Exception var4) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Failed to get server runtime mbean:" + var4);
         }
      }

      this.serverRuntimeMBean = localMBean;
   }

   public String getMigratableTargetName(String jmsServerConfigName) {
      JMSServerMBean jmsServer = this.runtimeAccess.getDomain().lookupJMSServer(jmsServerConfigName);
      if (jmsServer == null) {
         return null;
      } else {
         TargetMBean[] targets = jmsServer.getTargets();
         if (targets != null && targets.length != 0) {
            return !(targets[0] instanceof MigratableTargetMBean) ? null : targets[0].getName();
         } else {
            return null;
         }
      }
   }

   private String getLocalClusterName() {
      ServerMBean myServer = this.runtimeAccess.getServer();
      return myServer.getCluster() != null ? myServer.getCluster().getName() : null;
   }

   public synchronized DDMemberInformation[] registerListener(CDSListListener listener) throws javax.jms.JMSException {
      DDHandlerChangeListener changeListener = new DDHandlerChangeListener(listener);
      this.listeners.add(changeListener);
      return changeListener.getDDMemberInformation();
   }

   public synchronized void unregisterListener(CDSListListener listener) {
      Iterator iter = this.listeners.listIterator();

      while(iter.hasNext()) {
         DDHandlerChangeListener changeListener = (DDHandlerChangeListener)iter.next();
         if (changeListener.getListener() == listener) {
            changeListener.quit();
            iter.remove();
            break;
         }
      }

   }

   private final class DDHandlerChangeListener implements DDStatusListener {
      private final DDHandler ddHandler;
      private final CDSListListener listener;

      public DDHandlerChangeListener(CDSListListener listener) throws javax.jms.JMSException {
         this.ddHandler = DDManager.findDDHandlerByDDName(listener.getConfigName());
         if (this.ddHandler == null) {
            throw new javax.jms.JMSException("There is no DD named " + listener.getConfigName());
         } else {
            this.listener = listener;
            this.ddHandler.addStatusListener(this, 19);
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDSServer created DDHandlerChangeListener@" + this.hashCode() + " for CDSListListener [" + listener + "] with ddHandler@" + this.ddHandler.hashCode() + "[" + this.ddHandler + "]");
            }

         }
      }

      public CDSListListener getListener() {
         return this.listener;
      }

      public void quit() {
         this.ddHandler.removeStatusListener(this);
      }

      private void waitForServerUp() {
         int count = 0;
         if (CDSServer.this.serverRuntimeMBean != null) {
            while(CDSServer.this.serverRuntimeMBean.getStateVal() != 2 && CDSServer.this.serverRuntimeMBean.getStateVal() != 7 && CDSServer.this.serverRuntimeMBean.getStateVal() != 0) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var3) {
               }

               ++count;
               if (count >= 10) {
                  break;
               }
            }

         }
      }

      public synchronized DDMemberInformation[] getDDMemberInformation() {
         List list = new LinkedList();
         Iterator iter = this.ddHandler.memberCloneIterator();

         while(iter.hasNext()) {
            DDMember member = (DDMember)iter.next();
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("CDSServer.getDDMemberInformation: member.getName()=" + member.getName() + ", isUp=" + member.isUp());
            }

            if (member.isUp()) {
               DestinationImpl dImpl = new DestinationImpl(this.ddHandler.isQueue() ? 1 : 2, member.getJMSServerInstanceName(), member.getJMSServerConfigName(), member.getPersistentStoreName(), member.getName(), this.ddHandler.getApplicationName(), this.ddHandler.getEARModuleName(), member.getBackEndId(), member.getDestinationId(), member.getDispatcherId(), this.ddHandler.getPartitionName());
               list.add(new DDMemberInformation(this.ddHandler.getName(), this.ddHandler.isQueue() ? new String("javax.jms.Queue") : new String("javax.jms.Topic"), member.getDeploymentMemberType(), this.ddHandler.getJNDIName(), this.ddHandler.getForwardingPolicy(), dImpl, member.getWLSServerName(), member.getGlobalJNDIName(), member.getLocalJNDIName(), CDSServer.this.getLocalClusterName(), member.getMigratableTargetName(), member.getDomainName(), member.isConsumptionPaused(), member.isInsertionPaused(), member.isProductionPaused(), true, this.ddHandler.getPartitionName()));
            }
         }

         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            if (list.size() == 0) {
               JMSDebug.JMSCDS.debug("list is null");
            } else {
               JMSDebug.JMSCDS.debug("list has in it:");
               CDS.dumpDDMITable((DDMemberInformation[])((DDMemberInformation[])list.toArray(new DDMemberInformation[0])));
            }
         }

         return (DDMemberInformation[])((DDMemberInformation[])list.toArray(new DDMemberInformation[0]));
      }

      public void statusChangeNotification(DDHandler notifier, int events) {
         if ((events & 16) != 0) {
            this.quit();
            this.listener.distributedDestinationGone((DispatcherId)null);
         } else {
            this.waitForServerUp();
            this.listener.listChange(this.getDDMemberInformation());
         }

      }
   }

   private static final class CDSServerInitializer {
      private static final CDSServer INSTANCE = (CDSServer)LocatorUtilities.getService(CDSServer.class);
   }
}
