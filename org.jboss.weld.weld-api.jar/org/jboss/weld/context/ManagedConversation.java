package org.jboss.weld.context;

import javax.enterprise.context.Conversation;

public interface ManagedConversation extends Conversation {
   boolean unlock();

   boolean lock(long var1);

   long getLastUsed();

   void touch();
}
