package weblogic.application.services;

import com.oracle.classloader.ClassLoaderOptimize;
import com.oracle.classloader.PolicyClassLoader;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.GenericClassLoaderRegistry;
import weblogic.work.WorkManagerFactory;

@Service
@Named
@RunLevel(20)
public class ClassLoaderOptimizationService extends AbstractServerService {
   private static final long PERIOD = 3000L;
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependencyOnDeploymentServerService;
   @Inject
   BackgroundDeploymentManagerService backgroundDeploymentManagerService;
   @Inject
   GenericClassLoaderRegistry genericClassLoaderRegistry;
   private Timer myTimer = null;

   public void start() throws ServiceFailureException {
      final ClassLoader system = ClassLoader.getSystemClassLoader();
      final TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      final Runnable repeatedOptimize = new Runnable() {
         public void run() {
            if (system instanceof ClassLoaderOptimize) {
               ((ClassLoaderOptimize)system).optimize();
               ClassLoaderOptimizationService.optimizeIncludingAncestors(system.getParent());
            } else {
               ClassLoaderOptimizationService.optimizeIncludingAncestors(system);
            }

            Iterator var1 = ClassLoaderOptimizationService.this.genericClassLoaderRegistry.listGenericClassLoaders().iterator();

            while(var1.hasNext()) {
               GenericClassLoader loader = (GenericClassLoader)var1.next();
               if (loader != null) {
                  loader.optimize();
               }
            }

         }
      };
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            ClassLoaderOptimizationService.this.backgroundDeploymentManagerService.waitForCompletion();
            synchronized(ClassLoaderOptimizationService.this) {
               if (ClassLoaderOptimizationService.this.myTimer == null) {
                  ClassLoaderOptimizationService.this.myTimer = tm.schedule(new TimerListener() {
                     public void timerExpired(Timer timer) {
                        repeatedOptimize.run();
                     }
                  }, 0L, 3000L);
               }

            }
         }
      });
   }

   private static void optimizeIncludingAncestors(ClassLoader loader) {
      if (loader != null) {
         PolicyClassLoader.clearLockMap(loader);
         optimizeIncludingAncestors(loader.getParent());
      }

   }

   public void stop() throws ServiceFailureException {
      synchronized(this) {
         if (this.myTimer != null) {
            this.myTimer.cancel();
            this.myTimer = null;
         }

      }
   }
}
