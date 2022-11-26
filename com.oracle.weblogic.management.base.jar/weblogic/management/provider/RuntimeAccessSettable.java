package weblogic.management.provider;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.ServerRuntimeMBean;

@Contract
public interface RuntimeAccessSettable {
   void setServerRuntime(ServerRuntimeMBean var1);
}
