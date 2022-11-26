package weblogic.transaction.internal;

import java.rmi.RemoteException;
import java.util.Map;

public interface SubCoordinator3 extends SubCoordinator2 {
   Map getSubCoordinatorInfo(String var1) throws RemoteException;
}
