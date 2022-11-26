package weblogic.jdbc.common.internal;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilderFactory;
import weblogic.common.internal.PeerInfo;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.jdbc.wrapper.Connection;
import weblogic.utils.XXEUtils;

public class JDBCHelperImpl extends JDBCHelper {
   public Object interopReplace(Connection conn, PeerInfo peerInfo) {
      return conn;
   }

   public boolean getAutoConnectionClose() {
      return true;
   }

   public String getDefaultURL() {
      try {
         return "http: " + InetAddress.getLocalHost() + "9001";
      } catch (UnknownHostException var2) {
         throw new AssertionError(var2);
      }
   }

   public String getCurrentApplicationName() {
      return null;
   }

   public String getDomainName() {
      return "domain";
   }

   public String getServerName() {
      return "server";
   }

   public boolean isServerShuttingDown() {
      return false;
   }

   public boolean isPartitionStartingShuttingDown(String partition) {
      return false;
   }

   public DocumentBuilderFactory getDocumentBuilderFactory() {
      return XXEUtils.createDocumentBuilderFactoryInstance();
   }

   public Object createJNDIAlias(String name, Object o) {
      return o;
   }

   public boolean isProductionModeEnabled() {
      return false;
   }

   public String getXAMultiPoolName(JDBCDataSourceBean dsBean) {
      return dsBean.getName();
   }

   public List dsToList(String dataSourceList) {
      if (dataSourceList == null) {
         return null;
      } else {
         StringTokenizer st = new StringTokenizer(dataSourceList, ",");
         int count = st.countTokens();
         List newList = new ArrayList();

         for(int i = 0; i < count; ++i) {
            newList.add(st.nextToken());
         }

         return newList;
      }
   }

   public boolean isRACPool(String name, String appName, String moduleName, String compName) {
      ConnectionEnv cc = null;

      boolean var9;
      try {
         boolean var7;
         try {
            cc = ConnectionPoolManager.reserve(name, appName, moduleName, compName, -2);
            DatabaseMetaData metaData = cc.conn.jconn.getMetaData();
            if (metaData == null) {
               var7 = true;
               return var7;
            }

            String databaseVer = metaData.getDatabaseProductVersion();
            int isRac = false;
            int isRac = databaseVer.indexOf("Real Application Clusters");
            if (isRac == -1) {
               var9 = false;
               return var9;
            }

            var9 = true;
         } catch (Exception var21) {
            var7 = true;
            return var7;
         }
      } finally {
         if (cc != null) {
            try {
               ConnectionPoolManager.release(cc);
            } catch (Exception var20) {
            }
         }

      }

      return var9;
   }

   public boolean isLLRPool(JDBCDataSourceBean dsBean) {
      return dsBean != null && dsBean.getJDBCDataSourceParams().getGlobalTransactionsProtocol().equals("LoggingLastResource");
   }

   public boolean isLLRTablePerDataSource(String poolName) {
      String tableProperty = "weblogic.llr.table." + poolName;
      String table = System.getProperty(tableProperty);
      return table != null;
   }

   public String getJDBCLLRTableName(String serverName) {
      return null;
   }

   public Boolean isUseFusionForLLR(String serverName) {
      return false;
   }

   public int getJDBCLLRTableXIDColumnSize(String serverName) {
      return 40;
   }

   public int getJDBCLLRTablePoolColumnSize(String serverName) {
      return 64;
   }

   public int getJDBCLLRTableRecordColumnSize(String serverName) {
      return 2000;
   }

   public boolean isJNDIEnabled() {
      return true;
   }

   public boolean isRMISecure() {
      try {
         Class cls = Class.forName("weblogic.jdbc.common.internal.JDBCServerHelperImpl");
         Method m = cls.getMethod("rmiSecure", (Class[])null);
         Boolean res = (Boolean)m.invoke((Object)null);
         return res;
      } catch (Exception var4) {
         return false;
      }
   }

   public static boolean isExalogicOptimizationsEnabled() {
      try {
         Class cls = Class.forName("weblogic.jdbc.common.internal.JDBCServerHelperImpl");
         Method m = cls.getMethod("isExalogicOptimizationsEnabled", (Class[])null);
         Boolean res = (Boolean)m.invoke((Object)null);
         return res;
      } catch (Exception var3) {
         return false;
      }
   }

   public static boolean getInvokeBeginEndRequestDefault(JDBCDataSourceBean dsBean) {
      if (dsBean != null) {
         JDBCDriverParamsBean driverParams = dsBean.getJDBCDriverParams();
         if (driverParams != null) {
            String url = driverParams.getUrl();
            String driver = driverParams.getDriverName();
            if (url != null && url.startsWith("jdbc:oracle:") && driver != null && !driver.startsWith("oracle.jdbc.xa")) {
               return true;
            }
         }
      }

      return false;
   }

   public int getServerPort() {
      return -1;
   }

   public int getServerSslPort() {
      return -1;
   }
}
