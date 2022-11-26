package javax.batch.api.partition;

import java.io.Serializable;
import javax.batch.runtime.BatchStatus;

public interface PartitionAnalyzer {
   void analyzeCollectorData(Serializable var1) throws Exception;

   void analyzeStatus(BatchStatus var1, String var2) throws Exception;
}
