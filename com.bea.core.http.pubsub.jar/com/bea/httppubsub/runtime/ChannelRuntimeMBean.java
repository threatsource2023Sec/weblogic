package com.bea.httppubsub.runtime;

import java.util.List;

public interface ChannelRuntimeMBean {
   String getName();

   int getSubscriberCount();

   List getSubscribers();

   long getPublishedMessageCount();
}
