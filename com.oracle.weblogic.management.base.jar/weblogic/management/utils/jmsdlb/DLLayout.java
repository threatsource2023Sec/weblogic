package weblogic.management.utils.jmsdlb;

import java.util.Map;

public interface DLLayout {
   void setMaxInstancesPerNode(int var1);

   Map balance(DLCluster var1);
}
