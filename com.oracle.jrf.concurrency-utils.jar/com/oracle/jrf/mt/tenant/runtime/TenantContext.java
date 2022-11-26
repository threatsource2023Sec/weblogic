package com.oracle.jrf.mt.tenant.runtime;

import com.oracle.jrf.mt.tenant.PDBInfo;
import com.oracle.jrf.mt.tenant.Tenant;
import com.oracle.weblogic.lifecycle.Environment;
import java.util.UUID;

public interface TenantContext {
   String GLOBAL_NAME = "GLOBAL";
   /** @deprecated */
   @Deprecated
   String GLOBAL_ID = "0";
   UUID GLOBAL_UUID = new UUID(0L, 0L);

   /** @deprecated */
   @Deprecated
   Tenant getCurrentTenant();

   Tenant getTenant();

   String getServiceName();

   String getServiceType();

   UUID getServiceUUID();

   PDBInfo getPDB();

   String getTopLevelDir();

   Environment getEnvironment();

   boolean isGlobal();

   String getIdentityDomain();

   String getTwoTask();
}
