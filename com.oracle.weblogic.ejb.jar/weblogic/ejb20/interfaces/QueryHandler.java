package weblogic.ejb20.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.FinderException;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.WLQueryProperties;

public interface QueryHandler extends Remote {
   Object executeQuery(String var1, WLQueryProperties var2, boolean var3) throws RemoteException, FinderException;

   Object executeQuery(String var1, WLQueryProperties var2, boolean var3, boolean var4) throws RemoteException, FinderException;

   Object executePreparedQuery(String var1, PreparedQuery var2, Map var3, Map var4, boolean var5) throws RemoteException, FinderException;
}
