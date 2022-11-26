package org.jboss.weld.context.http;

import java.util.function.Consumer;
import javax.servlet.http.HttpSession;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.ConversationContext;

public interface HttpConversationContext extends BoundContext, ConversationContext {
   boolean destroy(HttpSession var1);

   default void activateLazily(Consumer transientConversationInitializationCallback) {
      throw new UnsupportedOperationException();
   }
}
