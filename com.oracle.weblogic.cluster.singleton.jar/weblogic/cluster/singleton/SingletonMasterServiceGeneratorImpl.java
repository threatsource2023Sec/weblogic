package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Service;

@Service
public class SingletonMasterServiceGeneratorImpl implements SingletonMasterServiceGenerator {
   public SingletonMasterService createSingletonMasterService(LeaseManager manager, int leaseRenewInterval) {
      return new SingletonMaster(manager, leaseRenewInterval);
   }
}
