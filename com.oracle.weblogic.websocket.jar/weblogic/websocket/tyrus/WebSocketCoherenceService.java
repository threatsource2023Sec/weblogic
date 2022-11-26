package weblogic.websocket.tyrus;

import java.util.Set;
import java.util.concurrent.Executor;
import javax.servlet.ServletContext;
import org.glassfish.tyrus.core.cluster.ClusterContext;
import org.glassfish.tyrus.core.monitoring.ApplicationEventListener;
import org.jvnet.hk2.annotations.Contract;
import weblogic.servlet.internal.WebAppServletContext;

@Contract
public interface WebSocketCoherenceService {
   ClusterContext createClusterContext(Executor var1, Integer var2);

   void onStartup(Set var1, ServletContext var2, boolean var3, Integer var4, Integer var5, WebAppServletContext var6, ApplicationEventListener var7, WebSocketDelegate var8);
}
