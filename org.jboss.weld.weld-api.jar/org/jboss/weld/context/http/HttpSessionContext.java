package org.jboss.weld.context.http;

import javax.servlet.http.HttpSession;
import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.SessionContext;

public interface HttpSessionContext extends BoundContext, SessionContext {
   void invalidate();

   boolean isValid();

   boolean destroy(HttpSession var1);
}
