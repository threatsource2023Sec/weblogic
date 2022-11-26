package weblogic.t3.srvr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevelController;
import org.glassfish.hk2.runlevel.RunLevelFuture;
import org.glassfish.hk2.runlevel.RunLevelController.ThreadingPolicy;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;

@Service
public class Hk2LifeCycleInitialization {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugServerLifeCycle");
   private static final String MAX_STARTUP_THREAD_PROPERTY = "com.oracle.weblogic.maxStartupThreads";
   private static final boolean USE_RANDOMIZER = Boolean.parseBoolean(System.getProperty("com.oracle.weblogic.debug.useServiceRandomizer", "false"));
   private static final String USE_SERVICE_LIST = System.getProperty("com.oracle.weblogic.debug.useServiceList");
   private static final int DEFAULT_STARTUP_THREADS = 4;
   @Inject
   private ServiceLocator locator;
   @Inject
   private Provider rls;
   @Inject
   private WebLogicExecutor executor;

   private Hk2LifeCycleInitialization() {
   }

   public RunLevelController getRunLevelController() {
      return (RunLevelController)this.rls.get();
   }

   private static List fileToList(String fileName) throws IOException {
      List coll = new LinkedList();
      BufferedReader reader = new BufferedReader(new FileReader(fileName));

      String line;
      try {
         while((line = reader.readLine()) != null) {
            if (line.length() != 0 && !line.startsWith("#")) {
               coll.add(line);
            }
         }
      } finally {
         reader.close();
      }

      return coll;
   }

   private static void maybePreloadClasses() {
      String fileName = System.getProperty("weblogic.debug.preloadClasses.file");
      if (fileName != null) {
         long currentTimeMillis = System.currentTimeMillis();
         int exceptions = 0;
         Exception holdException = null;

         try {
            Iterator var5 = fileToList(fileName).iterator();

            while(var5.hasNext()) {
               String className = (String)var5.next();

               try {
                  Class.forName(className, false, Hk2LifeCycleInitialization.class.getClassLoader());
               } catch (Exception var8) {
                  ++exceptions;
                  holdException = var8;
               }
            }
         } catch (IOException var9) {
            var9.printStackTrace();
            System.exit(1);
         }

         if (exceptions != 0) {
            System.out.println("We did get " + exceptions + " exceptions trying to load classes at startup.  Here's an example:");
            holdException.printStackTrace();
         }

         System.out.println("Done preloading classes! - it took " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
      }
   }

   void setThreadPolicy(RunLevelController controller) {
      controller.setThreadingPolicy(ThreadingPolicy.FULLY_THREADED);
   }

   public RunLevelFuture progressServer() {
      maybePreloadClasses();
      if (USE_RANDOMIZER && USE_SERVICE_LIST != null) {
         throw new IllegalStateException("Cannot use both the randomizer and a service list");
      } else {
         if (USE_RANDOMIZER) {
            ServiceLocatorUtilities.addClasses(this.locator, new Class[]{RandomServiceSorter.class});
         } else if (USE_SERVICE_LIST != null) {
            try {
               ServiceLocatorUtilities.addOneConstant(this.locator, new ListServiceSorter(USE_SERVICE_LIST));
            } catch (IOException var2) {
               throw new IllegalStateException(var2);
            }
         }

         this.setThreadPolicy((RunLevelController)this.rls.get());
         int numThreads = Integer.getInteger("com.oracle.weblogic.maxStartupThreads", 4);
         if (numThreads > 0) {
            if (logger.isDebugEnabled()) {
               logger.debug("Startup controller will use " + numThreads + " + threads");
            }

            ((RunLevelController)this.rls.get()).setMaximumUseableThreads(numThreads);
         } else {
            logger.debug("Startup controller will use infinite threads");
         }

         ((RunLevelController)this.rls.get()).setExecutor(this.executor);
         return ((RunLevelController)this.rls.get()).proceedToAsync(5);
      }
   }
}
