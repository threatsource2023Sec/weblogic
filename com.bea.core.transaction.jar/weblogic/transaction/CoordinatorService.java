package weblogic.transaction;

import java.rmi.RemoteException;
import javax.transaction.SystemException;

public interface CoordinatorService {
   Object invokeCoordinatorService(String var1, Object var2) throws RemoteException, SystemException;
}
