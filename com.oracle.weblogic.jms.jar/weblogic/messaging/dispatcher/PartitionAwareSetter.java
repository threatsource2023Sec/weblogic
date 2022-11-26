package weblogic.messaging.dispatcher;

import java.io.IOException;

public interface PartitionAwareSetter extends PartitionAware {
   void setPartitionId(String var1) throws IOException;

   void setPartitionName(String var1) throws IOException;

   void setConnectionPartitionName(String var1) throws IOException;
}
