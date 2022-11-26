package weblogic.socket;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.runtime.ServerConnectionRuntime;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.MessageReceiverStatistics;
import weblogic.protocol.MessageSenderStatistics;

@Contract
public interface ServerConnectionRuntimeFactory {
   ServerConnectionRuntime createServerConnectionRuntimeImpl(MessageSenderStatistics var1, MessageReceiverStatistics var2, SocketRuntime var3);
}
