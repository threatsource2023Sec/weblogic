package weblogic.servlet.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HealthCheck extends Remote {
   String JNDI_NAME = "HealthCheck";

   int getServerID() throws RemoteException;

   void ping() throws RemoteException;
}
