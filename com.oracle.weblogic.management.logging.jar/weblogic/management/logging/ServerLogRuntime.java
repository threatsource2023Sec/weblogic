package weblogic.management.logging;

import com.bea.logging.StatsHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.jvnet.hk2.annotations.Service;
import weblogic.i18n.logging.Severities;
import weblogic.management.ManagementException;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerLogRuntimeMBean;

public final class ServerLogRuntime extends LogRuntime implements ServerLogRuntimeMBean {
   public ServerLogRuntime(LogFileMBean logfileMBean, RuntimeMBean parent) throws ManagementException {
      super(logfileMBean, parent);
   }

   public Map getLoggedMessagesCountbySeverity() {
      Map result = new HashMap();
      Set countsBySeverityEntries = StatsHandler.getCountsBySeverity().entrySet();
      Iterator var3 = countsBySeverityEntries.iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         result.put(Severities.severityNumToString((Integer)entry.getKey()), ((AtomicInteger)entry.getValue()).get());
      }

      result.remove("Off");
      result.remove("Notice");
      result.remove("Info");
      result.remove("Debug");
      result.remove("Trace");
      return result;
   }

   @Service
   private static final class ServerLogRuntimeMBeanGeneratorImpl implements ServerLogRuntimeMBeanGenerator {
      public ServerLogRuntimeMBean createServerLogRuntimeMBean(LogFileMBean logfileMBean, RuntimeMBean parent) throws ManagementException {
         return new ServerLogRuntime(logfileMBean, parent);
      }
   }
}
