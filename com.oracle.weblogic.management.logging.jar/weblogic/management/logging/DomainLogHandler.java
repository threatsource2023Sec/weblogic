package weblogic.management.logging;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import weblogic.logging.LogEntry;

public interface DomainLogHandler extends Remote {
   String JNDI_NAME = "weblogic.logging.DomainLogHandler";

   void publishLogEntries(LogEntry[] var1) throws RemoteException;

   void sendTrap(String var1, List var2) throws RemoteException;

   void sendALAlertTrap(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, String var11, String var12) throws RemoteException;

   void ping() throws RemoteException;
}
