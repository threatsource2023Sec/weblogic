package com.bea.httppubsub.internal;

import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.MessageFilter;
import java.util.Iterator;
import java.util.List;

public class MessageFilterChain {
   private Iterator filtersIterator;

   public MessageFilterChain(List messageFilters) {
      this.filtersIterator = messageFilters.iterator();
   }

   public void handleMessage(EventMessage message) {
      while(true) {
         if (this.filtersIterator.hasNext()) {
            MessageFilter filter = (MessageFilter)this.filtersIterator.next();
            if (filter.handleMessage(message)) {
               continue;
            }
         }

         return;
      }
   }
}
