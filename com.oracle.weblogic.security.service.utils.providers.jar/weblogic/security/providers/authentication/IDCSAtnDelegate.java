package weblogic.security.providers.authentication;

import com.bea.common.logger.spi.LoggerSpi;

public interface IDCSAtnDelegate {
   IDCSGroupsAppRolesHolder authenticateUser(String var1, char[] var2, String var3, String var4) throws Exception;

   IDCSGroupsAppRolesHolder validateUser(String var1, String var2, String var3, String var4, boolean var5) throws Exception;

   IDCSGroupsAppRolesHolder validateClient(String var1, String var2, String var3, String var4, boolean var5) throws Exception;

   IDCSGroupsAppRolesHolder getGroups(String var1, String var2) throws Exception;

   IDCSGroupsAppRolesHolder getGroupsFromId(String var1, String var2) throws Exception;

   String getPrimaryTenant();

   LoggerSpi getServiceLogger();

   void refresh(IDCSConfiguration var1);
}
