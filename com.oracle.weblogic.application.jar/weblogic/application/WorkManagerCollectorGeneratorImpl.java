package weblogic.application;

import javax.inject.Inject;
import org.jvnet.hk2.annotations.Service;
import weblogic.work.api.WorkManagerCollector;
import weblogic.work.api.WorkManagerCollectorGenerator;

@Service
public class WorkManagerCollectorGeneratorImpl implements WorkManagerCollectorGenerator {
   @Inject
   private ApplicationAccess aa;

   public WorkManagerCollector getWorkManagerCollector() {
      return this.aa.getCurrentApplicationContext();
   }

   public WorkManagerCollector getWorkManagerCollector(String appName) {
      return this.aa.getApplicationContext(appName);
   }
}
