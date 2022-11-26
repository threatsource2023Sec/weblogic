package javax.batch.runtime;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.operations.JobOperator;

public class BatchRuntime {
   private static final String sourceClass = BatchRuntime.class.getName();
   private static final Logger logger;

   public static JobOperator getJobOperator() {
      JobOperator operator = (JobOperator)AccessController.doPrivileged(new PrivilegedAction() {
         public JobOperator run() {
            ServiceLoader loader = ServiceLoader.load(JobOperator.class);
            JobOperator returnVal = null;
            Iterator i$ = loader.iterator();

            while(i$.hasNext()) {
               JobOperator provider = (JobOperator)i$.next();
               if (provider != null) {
                  if (BatchRuntime.logger.isLoggable(Level.FINE)) {
                     BatchRuntime.logger.fine("Loaded BatchContainerServiceProvider with className = " + provider.getClass().getCanonicalName());
                  }

                  returnVal = provider;
                  break;
               }
            }

            return returnVal;
         }
      });
      if (operator == null && logger.isLoggable(Level.WARNING)) {
         logger.warning("The ServiceLoader was unable to find an implementation for JobOperator. Check classpath for META-INF/services/javax.batch.operations.JobOperator file.");
      }

      return operator;
   }

   static {
      logger = Logger.getLogger(sourceClass);
   }
}
