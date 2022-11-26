package weblogic.jms.frontend;

import java.util.Iterator;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.client.WLConnectionImpl;
import weblogic.jms.common.DestroyConnectionException;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSConnectionRuntimeMBean;
import weblogic.management.runtime.JMSSessionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.rjvm.JVMID;

public class FEConnectionRuntimeDelegate extends RuntimeMBeanDelegate implements JMSConnectionRuntimeMBean {
   private int connectionConsumersCurrentCount;
   private int connectionConsumersHighCount;
   private int connectionConsumersTotalCount;
   private long sessionsHighCount = 0L;
   private long sessionsTotalCount = 0L;
   private long browsersHighCount = 0L;
   private long browsersTotalCount = 0L;
   private FEConnection managedConnection;
   private String connectionRuntimeMBeanName;

   public FEConnectionRuntimeDelegate(String connectionRuntimeMBeanName, FEConnection managedConnection, JMSService jmsService) throws JMSException, ManagementException {
      super(connectionRuntimeMBeanName, jmsService, false);
      this.connectionRuntimeMBeanName = connectionRuntimeMBeanName;
      this.managedConnection = managedConnection;
      super.register();
   }

   public String getConnectionRuntimeMBeanName() {
      return this.connectionRuntimeMBeanName;
   }

   public String getClientID() {
      return this.managedConnection.getConnectionClientId();
   }

   public String getClientIDPolicy() {
      return WLConnectionImpl.convertClientIdPolicy(this.managedConnection.getClientIdPolicy());
   }

   public void destroy() {
      JMSPushExceptionRequest request = new JMSPushExceptionRequest(3, this.managedConnection.getConnectionId(), new DestroyConnectionException("Connection administratively closed"));

      try {
         JMSServerUtilities.anonDispatchNoReply(request, this.managedConnection.getClientDispatcher());
      } catch (JMSException var11) {
      } finally {
         try {
            this.managedConnection.normalClose();
         } catch (JMSException var10) {
         }

      }

   }

   public JMSSessionRuntimeMBean[] getSessions() {
      synchronized(this.managedConnection) {
         int i = 0;
         synchronized(this) {
            Iterator iterator = this.managedConnection.getSessionMap().values().iterator();

            JMSSessionRuntimeMBean[] retValue;
            for(retValue = new JMSSessionRuntimeMBean[this.managedConnection.getSessionMap().size()]; iterator.hasNext(); retValue[i++] = (JMSSessionRuntimeMBean)iterator.next()) {
            }

            JMSSessionRuntimeMBean[] var10000 = retValue;
            return var10000;
         }
      }
   }

   public String getHostAddress() {
      JMSDispatcher clientDispatcher;
      try {
         clientDispatcher = this.managedConnection.getClientDispatcher();
      } catch (JMSException var3) {
         return "0.0.0.0";
      }

      if (clientDispatcher.isLocal()) {
         return JVMID.localID().getAddress();
      } else {
         String hostAddress = clientDispatcher.getId().getHostAddress();
         if (hostAddress.equals("0.0.0.0")) {
            hostAddress = this.managedConnection.getAddress();
         }

         return hostAddress;
      }
   }

   public long getSessionsCurrentCount() {
      synchronized(this.managedConnection) {
         long var10000;
         synchronized(this) {
            var10000 = (long)this.managedConnection.getSessionMap().size();
         }

         return var10000;
      }
   }

   public synchronized long getSessionsHighCount() {
      return this.sessionsHighCount;
   }

   public synchronized void setSessionsHighCount(long sessionsHighCount) {
      this.sessionsHighCount = sessionsHighCount;
   }

   public synchronized void increaseSessionsHighCount() {
      ++this.sessionsHighCount;
   }

   public synchronized void decreaseSessionsHighCount() {
      --this.sessionsHighCount;
   }

   public synchronized long getSessionsTotalCount() {
      return this.sessionsTotalCount;
   }

   public synchronized void increaseSessionsTotalCount() {
      ++this.sessionsTotalCount;
   }

   public synchronized void decreaseSessionsTotalCount() {
      --this.sessionsTotalCount;
   }

   public int getConnectionConsumersCurrentCount() {
      synchronized(this.managedConnection) {
         int var10000;
         synchronized(this) {
            var10000 = this.managedConnection.getConnectionConsumerMap().size();
         }

         return var10000;
      }
   }

   public synchronized void increaseConnectionConsumersCurrentCount() {
      ++this.connectionConsumersCurrentCount;
   }

   public synchronized void decreaseConnectionConsumersCurrentCount() {
      --this.connectionConsumersCurrentCount;
   }

   public synchronized int getConnectionConsumersHighCount() {
      return this.connectionConsumersHighCount;
   }

   public synchronized void increaseConnectionConsumersHighCount() {
      ++this.connectionConsumersHighCount;
   }

   public synchronized void decreaseConnectionConsumersHighCount() {
      --this.connectionConsumersHighCount;
   }

   public synchronized int getConnectionConsumersTotalCount() {
      return this.connectionConsumersTotalCount;
   }

   public synchronized void increaseConnectionConsumersTotalCount() {
      ++this.connectionConsumersTotalCount;
   }

   public synchronized void decreaseConnectionConsumersTotalCount() {
      --this.connectionConsumersTotalCount;
   }

   public long getBrowsersCurrentCount() {
      synchronized(this.managedConnection) {
         long var10000;
         synchronized(this) {
            var10000 = (long)this.managedConnection.getBrowserMap().size();
         }

         return var10000;
      }
   }

   public synchronized long getBrowsersHighCount() {
      return this.browsersHighCount;
   }

   public synchronized void setBrowsersHighCount(long browsersHighCount) {
      this.browsersHighCount = browsersHighCount;
   }

   public synchronized void increaseBrowsersHighCount() {
      ++this.browsersHighCount;
   }

   public synchronized void decreaseBrowsersHighCount() {
      --this.browsersHighCount;
   }

   public synchronized long getBrowsersTotalCount() {
      return this.browsersTotalCount;
   }

   public synchronized void increaseBrowsersTotalCount() {
      ++this.browsersTotalCount;
   }

   public synchronized void decreaseBrowsersTotalCount() {
      --this.browsersTotalCount;
   }
}
