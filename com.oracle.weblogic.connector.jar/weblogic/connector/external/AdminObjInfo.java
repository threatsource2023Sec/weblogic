package weblogic.connector.external;

import java.util.Hashtable;

public interface AdminObjInfo {
   String getInterface();

   String getAdminObjClass();

   Hashtable getConfigProps();

   String getJndiName();

   String getKey();
}
