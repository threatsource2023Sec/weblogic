package weblogic.jdbc.common.internal;

import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import weblogic.common.ResourceException;
import weblogic.common.internal.PeerInfo;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.wrapper.Connection;

public abstract class JDBCHelper {
   private static JDBCHelper singleton = new JDBCHelperImpl();

   public static JDBCHelper getHelper() {
      return singleton;
   }

   public static void setHelper(JDBCHelper helper) {
      singleton = helper;
   }

   public abstract Object interopReplace(Connection var1, PeerInfo var2);

   public abstract boolean getAutoConnectionClose();

   public abstract String getDefaultURL();

   public abstract String getCurrentApplicationName();

   public abstract String getDomainName();

   public abstract String getServerName();

   public abstract boolean isServerShuttingDown();

   public abstract boolean isPartitionStartingShuttingDown(String var1);

   public abstract DocumentBuilderFactory getDocumentBuilderFactory();

   public abstract Object createJNDIAlias(String var1, Object var2);

   public abstract boolean isProductionModeEnabled();

   public abstract String getXAMultiPoolName(JDBCDataSourceBean var1);

   public abstract boolean isLLRTablePerDataSource(String var1);

   public abstract boolean isLLRPool(JDBCDataSourceBean var1);

   public abstract List dsToList(String var1);

   public abstract boolean isRACPool(String var1, String var2, String var3, String var4);

   public abstract String getJDBCLLRTableName(String var1);

   public abstract Boolean isUseFusionForLLR(String var1);

   public abstract int getJDBCLLRTableXIDColumnSize(String var1);

   public abstract int getJDBCLLRTablePoolColumnSize(String var1);

   public abstract int getJDBCLLRTableRecordColumnSize(String var1);

   public abstract boolean isJNDIEnabled();

   public void addRMIContext(Object remote) throws Exception {
   }

   public void removeRMIContext(Object remote) throws Exception {
   }

   public abstract boolean isRMISecure();

   public String getVersionId(String applicationName) {
      return null;
   }

   public abstract int getServerPort();

   public abstract int getServerSslPort();

   public JDBCConnectionPool getConnectionPool(String jndiName) throws ResourceException {
      return null;
   }

   public boolean isAdminServer() {
      return false;
   }

   public boolean isServerStarting() {
      return false;
   }

   public void setServerFailedState(Throwable t) {
   }

   public boolean isServerStarted() {
      return false;
   }
}
