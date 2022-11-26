package weblogic.ejb.container.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import weblogic.ejb.spi.WLDeploymentException;

public interface WLCMPPersistenceManager {
   boolean createDefaultDBMSTable(String var1) throws WLDeploymentException;

   Connection getConnection() throws SQLException;

   String getEjbName();

   void dropAndCreateDefaultDBMSTable(String var1) throws WLDeploymentException;

   void alterDefaultDBMSTable(String var1, Set var2, Set var3) throws WLDeploymentException;
}
