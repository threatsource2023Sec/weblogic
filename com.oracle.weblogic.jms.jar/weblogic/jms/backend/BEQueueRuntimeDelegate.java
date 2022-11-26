package weblogic.jms.backend;

import javax.jms.JMSException;
import javax.naming.Context;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.ModuleName;
import weblogic.management.ManagementException;

public final class BEQueueRuntimeDelegate extends BEDestinationRuntimeDelegate {
   public BEQueueRuntimeDelegate(EntityName entityName, BackEnd backEnd, Context applicationContext, boolean temporary, ModuleName auxiliaryModuleName, JMSBean jmsBean, QueueBean queueBean) {
      super(entityName, applicationContext, backEnd, jmsBean, queueBean, temporary, auxiliaryModuleName);
   }

   protected void initialize(int duration) throws ModuleException {
      BackEnd mybackEnd = this.checkBackEndWithModuleException();

      try {
         BEQueueImpl beQueue = new BEQueueImpl(mybackEnd, this.entityName.toString(), this.temporary, new BEDestinationSecurityImpl(this.entityName, "queue", this.backEnd.isClusterTargeted(), this.specificBean));
         this.setManagedDestination(beQueue);
         super.initialize(duration);
         this.getRuntimeMBean().setMessageManagementDelegate(new BEMessageManagementImpl(this.entityName.toString(), beQueue.getKernelQueue(), beQueue, this.getRuntimeMBean()));
      } catch (JMSException var4) {
         throw new ModuleException(var4);
      } catch (ManagementException var5) {
         throw new ModuleException(var5);
      }
   }
}
