package weblogic.management.configuration.util;

import org.aopalliance.intercept.MethodInvocation;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface PartitionManagerInterceptorDataLoader {
   void loadUserData(MethodInvocation var1) throws Exception;
}
