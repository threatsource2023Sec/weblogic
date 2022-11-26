package weblogic.ejb;

import java.rmi.RemoteException;
import java.util.Properties;
import javax.ejb.EJBHome;

public interface QueryHome extends EJBHome {
   Query createQuery() throws RemoteException;

   PreparedQuery prepareQuery(String var1) throws RemoteException;

   PreparedQuery prepareQuery(String var1, Properties var2) throws RemoteException;

   Query createSqlQuery() throws RemoteException;

   String nativeQuery(String var1) throws RemoteException;

   String getDatabaseProductName() throws RemoteException;

   String getDatabaseProductVersion() throws RemoteException;
}
