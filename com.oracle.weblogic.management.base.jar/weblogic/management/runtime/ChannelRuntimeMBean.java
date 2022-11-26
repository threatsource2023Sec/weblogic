package weblogic.management.runtime;

import java.util.List;

public interface ChannelRuntimeMBean extends RuntimeMBean, com.bea.httppubsub.runtime.ChannelRuntimeMBean {
   String getName();

   ChannelRuntimeMBean[] getSubChannels();

   int getSubscriberCount();

   List getSubscribers();

   long getPublishedMessageCount();
}
