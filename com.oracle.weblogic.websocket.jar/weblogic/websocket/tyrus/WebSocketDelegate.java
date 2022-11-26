package weblogic.websocket.tyrus;

import java.util.Set;
import java.util.concurrent.Executor;
import javax.servlet.ServletContext;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.glassfish.tyrus.server.TyrusServerContainer;

public interface WebSocketDelegate {
   TyrusServerContainer createServerContainer(Set var1, ServletContext var2, boolean var3, Integer var4, Integer var5, ApplicationEventListener var6, Executor var7);

   void createServletFilter(ServletContext var1, TyrusServerContainer var2, CoherenceServletFilterService var3);
}
