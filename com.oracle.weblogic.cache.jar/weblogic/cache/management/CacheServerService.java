package weblogic.cache.management;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;

@Service
@Named
@RunLevel(20)
public final class CacheServerService extends AbstractServerService {
   @Inject
   @Named("WaitForBackgroundCompletion")
   private ServerService dependencyOnWaitForBackgroundCompletion;
   private static List shutdownListeners = new ArrayList();

   public static synchronized void addShutdownListener(ServerShutdownListener listener) {
      shutdownListeners.add(listener);
   }

   public void stop() {
      this.halt();
   }

   public void halt() {
      ServerShutdownListener[] listeners = getListeners();
      if (listeners != null) {
         for(int i = 0; i < listeners.length; ++i) {
            listeners[i].serverShutdown();
         }

      }
   }

   private static synchronized ServerShutdownListener[] getListeners() {
      if (shutdownListeners.size() == 0) {
         return null;
      } else {
         ServerShutdownListener[] listeners = new ServerShutdownListener[shutdownListeners.size()];
         shutdownListeners.toArray(listeners);
         return listeners;
      }
   }
}
