package weblogic.work.api;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WorkManagerCollectorGenerator {
   WorkManagerCollector getWorkManagerCollector();

   WorkManagerCollector getWorkManagerCollector(String var1);
}
