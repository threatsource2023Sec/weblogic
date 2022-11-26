package org.jboss.weld.context;

import java.util.Collection;

public interface ConversationContext extends ManagedContext {
   void invalidate();

   void activate(String var1);

   void activate();

   void setParameterName(String var1);

   String getParameterName();

   void setConcurrentAccessTimeout(long var1);

   long getConcurrentAccessTimeout();

   void setDefaultTimeout(long var1);

   long getDefaultTimeout();

   Collection getConversations();

   ManagedConversation getConversation(String var1);

   String generateConversationId();

   ManagedConversation getCurrentConversation();
}
