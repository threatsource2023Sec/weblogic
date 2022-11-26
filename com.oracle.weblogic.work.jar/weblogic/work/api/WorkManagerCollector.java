package weblogic.work.api;

import org.jvnet.hk2.annotations.Contract;
import weblogic.work.WorkManagerCollection;

@Contract
public interface WorkManagerCollector {
   WorkManagerCollection getWorkManagerCollection();
}
