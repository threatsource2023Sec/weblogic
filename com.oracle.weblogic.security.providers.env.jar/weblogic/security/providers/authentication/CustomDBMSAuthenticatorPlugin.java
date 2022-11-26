package weblogic.security.providers.authentication;

import java.sql.Connection;
import java.sql.SQLException;
import weblogic.management.security.ProviderMBean;

public interface CustomDBMSAuthenticatorPlugin {
   void initialize(ProviderMBean var1);

   void shutdown();

   String lookupPassword(Connection var1, String var2) throws SQLException;

   boolean userExists(Connection var1, String var2) throws SQLException;

   String[] lookupUserGroups(Connection var1, String var2) throws SQLException;
}
