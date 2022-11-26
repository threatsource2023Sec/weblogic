package weblogic.transaction.internal;

import java.rmi.Remote;
import java.util.Map;

public interface SubCoordinatorRM extends Remote {
   Map getProperties(String var1);
}
