package weblogic.connector.monitoring.inbound;

import java.util.HashSet;
import java.util.Set;
import weblogic.connector.inbound.RAInboundManager;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectorInboundRuntimeMBean;
import weblogic.management.runtime.MessageDrivenEJBRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class ConnectorInboundRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConnectorInboundRuntimeMBean {
   private String msgListenerType;
   private String activationSpecClass;
   private Set mdbRuntimes;
   private RAInboundManager raInboundManager;

   public ConnectorInboundRuntimeMBeanImpl(String name, String msgListenerType, String activationSpecClass, RuntimeMBean parent, RAInboundManager raInboundManager) throws ManagementException {
      super(name, parent, false);
      this.msgListenerType = msgListenerType;
      this.activationSpecClass = activationSpecClass;
      this.mdbRuntimes = new HashSet();
      this.raInboundManager = raInboundManager;
      this.register();
   }

   public String getMsgListenerType() {
      return this.msgListenerType;
   }

   public String getActivationSpecClass() {
      return this.activationSpecClass;
   }

   public MessageDrivenEJBRuntimeMBean[] getMDBRuntimes() {
      return (MessageDrivenEJBRuntimeMBean[])((MessageDrivenEJBRuntimeMBean[])this.mdbRuntimes.toArray(new MessageDrivenEJBRuntimeMBean[this.mdbRuntimes.size()]));
   }

   public boolean addMDBRuntime(MessageDrivenEJBRuntimeMBean mdbRuntime) {
      boolean returnFlag = false;
      if (mdbRuntime != null) {
         returnFlag = this.mdbRuntimes.add(mdbRuntime);
      }

      return returnFlag;
   }

   public boolean removeMDBRuntime(MessageDrivenEJBRuntimeMBean mdbRuntime) {
      boolean returnFlag = false;
      if (mdbRuntime != null) {
         returnFlag = this.mdbRuntimes.remove(mdbRuntime);
      }

      return returnFlag;
   }

   public void removeAllMDBRuntimes() {
      this.mdbRuntimes.clear();
   }

   public String getState() {
      return this.raInboundManager.getState();
   }
}
