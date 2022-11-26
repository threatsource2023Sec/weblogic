package monfox.toolkit.snmp.agent.x.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public interface AgentXServerSocketFactory {
   ServerSocket newServerSocket(InetAddress var1, int var2, int var3) throws IOException;
}
