package org.jboss.weld.contexts.conversation;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class ConversationIdGenerator implements Callable, Serializable {
   public static final String CONVERSATION_ID_GENERATOR_ATTRIBUTE_NAME = ConversationIdGenerator.class.getName();
   private static final long serialVersionUID = 8489811313900825684L;
   private final AtomicInteger id = new AtomicInteger(1);

   public String call() {
      int nextId = this.id.getAndIncrement();
      return String.valueOf(nextId);
   }
}
