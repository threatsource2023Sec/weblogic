package weblogic.jms.backend.udd;

import weblogic.j2ee.descriptor.wl.QueueBean;

public class SyntheticQueueBean extends SyntheticDestinationBean implements QueueBean {
   public int getForwardDelay() {
      return ((QueueBean)this.udd.getUDestBean()).getForwardDelay();
   }

   public void setForwardDelay(int forwardDelay) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public boolean getResetDeliveryCountOnForward() {
      return ((QueueBean)this.udd.getUDestBean()).getResetDeliveryCountOnForward();
   }

   public void setResetDeliveryCountOnForward(boolean reset) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public SyntheticQueueBean(UDDEntity udd, String jmsServerInstanceName, String jmsServerConfigName) {
      super(udd, jmsServerInstanceName, jmsServerConfigName);
   }
}
