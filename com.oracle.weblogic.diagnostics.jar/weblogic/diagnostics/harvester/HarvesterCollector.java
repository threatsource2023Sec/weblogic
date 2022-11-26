package weblogic.diagnostics.harvester;

import java.util.List;
import java.util.Map;

public interface HarvesterCollector {
   String getName();

   void initialize();

   void enable();

   void disable();

   HarvesterCollectorStatistics getStatistics();

   List getCurrentlyHarvestedAttributes(String var1);

   List getCurrentlyHarvestedInstances(String var1);

   Map retrieveSnapshot() throws Exception;

   long getSamplePeriod();
}
