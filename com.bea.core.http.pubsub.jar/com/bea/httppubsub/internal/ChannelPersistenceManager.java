package com.bea.httppubsub.internal;

import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.descriptor.ChannelPersistenceBean;
import java.util.List;

public interface ChannelPersistenceManager {
   List loadEvents(PersistedClientRecord var1, ChannelId var2);

   void storeEvent(DeliverEventMessage var1);

   ChannelPersistenceBean getChannelPersistenceBean();

   int getMessageCount();

   void init();

   void destory();
}
