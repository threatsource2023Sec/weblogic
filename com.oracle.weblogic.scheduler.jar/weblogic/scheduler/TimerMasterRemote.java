package weblogic.scheduler;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface TimerMasterRemote extends Remote {
   List getReadyTimers(Set var1) throws RemoteException, TimerException;
}
