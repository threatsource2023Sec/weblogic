package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface SingletonMasterServiceGenerator {
   SingletonMasterService createSingletonMasterService(LeaseManager var1, int var2);
}
