package weblogic.elasticity;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DynamicClusterScalingService {
   int updateMaxServerCount(String var1, int var2);
}
