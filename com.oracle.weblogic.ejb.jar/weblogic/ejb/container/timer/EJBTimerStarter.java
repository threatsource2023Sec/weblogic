package weblogic.ejb.container.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.TimerManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.server.ActivatedService;
import weblogic.server.ServiceFailureException;

@Service
@Singleton
public final class EJBTimerStarter extends ActivatedService {
   private static boolean started = false;
   private static Map timerManagers = new HashMap();

   public synchronized void stopService() throws ServiceFailureException {
      started = false;
   }

   public synchronized void haltService() throws ServiceFailureException {
      this.stopService();
   }

   public synchronized boolean startService() throws ServiceFailureException {
      started = true;
      synchronized(timerManagers) {
         Iterator var2 = timerManagers.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry me = (Map.Entry)var2.next();
            BeanInfo bi = (BeanInfo)me.getValue();
            ManagedInvocationContext mic = bi.setCIC();
            Throwable var6 = null;

            try {
               ((TimerManager)me.getKey()).start();
            } catch (Throwable var17) {
               var6 = var17;
               throw var17;
            } finally {
               if (mic != null) {
                  if (var6 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var16) {
                        var6.addSuppressed(var16);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         }

         timerManagers.clear();
         return true;
      }
   }

   public static void addTimerManagerStarter(TimerManager tm, BeanInfo bi) {
      synchronized(timerManagers) {
         if (!started) {
            timerManagers.put(tm, bi);
            return;
         }
      }

      tm.start();
   }

   public static void removeTimerManagerStarter(TimerManager tm) {
      synchronized(timerManagers) {
         timerManagers.remove(tm);
      }
   }
}
