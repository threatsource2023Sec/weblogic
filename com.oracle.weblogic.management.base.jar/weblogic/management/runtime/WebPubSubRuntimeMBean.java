package weblogic.management.runtime;

public interface WebPubSubRuntimeMBean extends RuntimeMBean, com.bea.httppubsub.runtime.WebPubSubRuntimeMBean {
   String getName();

   ChannelRuntimeMBean getChannel(String var1);

   ChannelRuntimeMBean getRootChannel();
}
