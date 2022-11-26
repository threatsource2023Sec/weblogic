package weblogic.rmi.utils.enumerations;

import java.rmi.Remote;
import java.util.Enumeration;

public interface RemoteBatchingEnumeration extends Remote {
   boolean hasMoreElements();

   Enumeration nextBatch(int var1);
}
