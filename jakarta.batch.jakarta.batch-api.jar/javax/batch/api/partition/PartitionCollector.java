package javax.batch.api.partition;

import java.io.Serializable;

public interface PartitionCollector {
   Serializable collectPartitionData() throws Exception;
}
