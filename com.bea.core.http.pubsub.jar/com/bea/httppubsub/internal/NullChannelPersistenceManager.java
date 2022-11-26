package com.bea.httppubsub.internal;

import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.descriptor.ChannelPersistenceBean;
import java.util.Collections;
import java.util.List;

public class NullChannelPersistenceManager implements ChannelPersistenceManager {
   public static final ChannelPersistenceManager INSTANCE = new NullChannelPersistenceManager();

   private NullChannelPersistenceManager() {
   }

   public List loadEvents(PersistedClientRecord clientRec, ChannelId subscription) {
      return Collections.EMPTY_LIST;
   }

   public void storeEvent(DeliverEventMessage event) {
   }

   public ChannelPersistenceBean getChannelPersistenceBean() {
      return null;
   }

   public int getMessageCount() {
      return 0;
   }

   public void init() {
   }

   public void destory() {
   }
}
