package weblogic.deploy.service;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface StatusListenerManager {
   void registerStatusListener(String var1, StatusListener var2);

   void unregisterStatusListener(String var1);
}
