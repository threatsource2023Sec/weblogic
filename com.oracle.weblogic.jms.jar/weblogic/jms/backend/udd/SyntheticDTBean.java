package weblogic.jms.backend.udd;

import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.MulticastParamsBean;
import weblogic.j2ee.descriptor.wl.TopicSubscriptionParamsBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;

public class SyntheticDTBean extends SyntheticDDBean implements UniformDistributedTopicBean {
   SyntheticDTBean(UDDEntity udd) {
      super(udd);
   }

   public DistributedDestinationMemberBean[] getDistributedTopicMembers() {
      return (DistributedDestinationMemberBean[])((DistributedDestinationMemberBean[])this.members.toArray(new DistributedDestinationMemberBean[0]));
   }

   public DistributedDestinationMemberBean createDistributedTopicMember(String name) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public void destroyDistributedTopicMember(DistributedDestinationMemberBean distributedTopicMember) {
      throw new AssertionError("Don't want to modify fake bean");
   }

   public DistributedDestinationMemberBean lookupDistributedTopicMember(String name) {
      return null;
   }

   public String getForwardingPolicy() {
      return ((UniformDistributedTopicBean)this.udd.getUDestBean()).getForwardingPolicy();
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
