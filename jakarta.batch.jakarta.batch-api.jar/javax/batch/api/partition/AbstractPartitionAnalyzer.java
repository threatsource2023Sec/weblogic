package javax.batch.api.partition;

import java.io.Serializable;
import javax.batch.runtime.BatchStatus;

public abstract class AbstractPartitionAnalyzer implements PartitionAnalyzer {
   public void analyzeCollectorData(Serializable data) throws Exception {
   }

   public void analyzeStatus(BatchStatus batchStatus, String exitStatus) throws Exception {
   }
}
