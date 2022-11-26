package weblogic.socket;

import java.net.UnknownHostException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ServerChannel;

@Contract
public interface SocketRuntimeFactory {
   SocketRuntime createSocketRuntimeImpl(SocketRuntime var1);

   ServerChannel createBootstrapChannel(String var1) throws UnknownHostException;
}
