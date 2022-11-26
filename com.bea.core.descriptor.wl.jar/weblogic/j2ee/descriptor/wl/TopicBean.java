package weblogic.j2ee.descriptor.wl;

public interface TopicBean extends DestinationBean {
   MulticastParamsBean getMulticast();

   TopicSubscriptionParamsBean getTopicSubscriptionParams();

   String getForwardingPolicy();

   void setForwardingPolicy(String var1) throws IllegalArgumentException;
}
