package weblogic.server.lifecycle;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WebLogicServerRunState {
   int getRunState();
}
