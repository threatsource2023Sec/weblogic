package weblogic.management.runtime;

import java.io.Serializable;
import java.util.Map;
import javax.transaction.xa.Xid;

public interface JTATransaction extends Serializable {
   String getName();

   Xid getXid();

   String getStatus();

   Map getUserProperties();

   int getSecondsActiveCurrentCount();

   String[] getServers();

   Map getResourceNamesAndStatus();

   String getCoordinatorURL();

   Map getServersAndStatus();

   boolean isTransactionLogWritten();
}
