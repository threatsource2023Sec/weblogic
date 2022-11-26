package weblogic.elasticity;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServerStateInspector {
   boolean isNodemanagerForServerReachable(String var1);
}
