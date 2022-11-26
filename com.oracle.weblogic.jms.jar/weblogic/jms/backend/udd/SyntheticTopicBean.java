package weblogic.jms.backend.udd;

import weblogic.j2ee.descriptor.wl.MulticastParamsBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;

public class SyntheticTopicBean extends SyntheticDestinationBean implements TopicBean {
   public SyntheticTopicBean(UDDEntity udd, String jmsServerInstanceName, String jmsServerConfigName) {
      super(udd, jmsServerInstanceName, jmsServerConfigName);
   }

   public String getForwardingPolicy() {
      return ((TopicBean)this.udd.getUDestBean()).getForwardingPolicy();
   }

   public void setForwardingPolicy(String forwardingPolicy) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public MulticastParamsBean getMulticast() {
      return ((UniformDistributedTopicBean)this.udd.getUDestBean()).getMulticast();
   }

   public TopicSubscriptionParamsBean getTopicSubscriptionParams() {
      return ((UniformDistributedTopicBean)this.udd.getUDestBean()).getTopicSubscriptionParams();
   }
}
