package weblogic.diagnostics.snmp.agent;

import weblogic.socket.MuxableSocket;

public interface SNMPTransportProvider {
   int UDP = 0;
   int TCP = 1;

   void pushMessage(MuxableSocket var1, byte[] var2);

   int getType();
}
