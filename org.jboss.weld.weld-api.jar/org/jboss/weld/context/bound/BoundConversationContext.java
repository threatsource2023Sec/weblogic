package org.jboss.weld.context.bound;

import java.util.Map;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.ConversationContext;

public interface BoundConversationContext extends ConversationContext, BoundContext {
   boolean destroy(Map var1);
}
