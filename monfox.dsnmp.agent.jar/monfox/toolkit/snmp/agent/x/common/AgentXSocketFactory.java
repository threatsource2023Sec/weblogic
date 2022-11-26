package monfox.toolkit.snmp.agent.x.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public interface AgentXSocketFactory {
   Socket newSocket(InetAddress var1, int var2) throws IOException;
}
