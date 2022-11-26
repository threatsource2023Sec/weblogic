package weblogic.management.provider.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class DynamicMBeanProcessor {
   private static final DynamicMBeanProcessor instance = new DynamicMBeanProcessor();
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private List processors = new ArrayList();

   private DynamicMBeanProcessor() {
   }

   public static DynamicMBeanProcessor getInstance() {
      return instance;
   }

   public synchronized void register(ConfigurationProcessor processor) {
      this.processors.add(processor);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("DynamicMBeanProcessor: registered " + processor);
      }

   }

   public synchronized void unregister(ConfigurationProcessor processor) {
      Iterator itr = this.processors.iterator();

      ConfigurationProcessor aProcessor;
      do {
         if (!itr.hasNext()) {
            return;
         }

         aProcessor = (ConfigurationProcessor)itr.next();
      } while(aProcessor != processor);

      itr.remove();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("DynamicMBeanProcessor: unregistered " + aProcessor);
      }

   }

   public synchronized void updateConfiguration(DomainMBean root) throws UpdateException {
      ConfigurationProcessor aProcessor;
      for(Iterator var2 = this.processors.iterator(); var2.hasNext(); aProcessor.updateConfiguration(root)) {
         aProcessor = (ConfigurationProcessor)var2.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("DynamicMBeanProcessor: will notify updateConfiguration on " + aProcessor);
         }
      }

   }
}
