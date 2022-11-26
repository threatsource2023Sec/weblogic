package weblogic.store.admin.util;

import java.io.IOException;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertiesBean;
import weblogic.j2ee.descriptor.wl.JDBCPropertyBean;
import weblogic.jdbc.JDBCHelper;
import weblogic.jdbc.common.internal.JDBCUtil;
import weblogic.kernel.KernelStatus;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.FileStoreMBean;
import weblogic.management.configuration.JDBCStoreMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ReplicatedStoreMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.PersistentStoreException;
import weblogic.store.common.PartitionNameUtils;
import weblogic.store.common.StoreDebug;
import weblogic.store.io.file.FileStoreIO;
import weblogic.store.io.file.ReplicatedStoreIO;
import weblogic.store.io.jdbc.BasicDataSource;
import weblogic.store.io.jdbc.JDBCStoreIO;

@Service
public class PersistentStoreUtils implements StoreExistenceChecker {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SINGLETON_JDBC_STORE_PREFIX_DEFAULT = "S";

   public static String computeShortName(String deploymentName) {
      return PartitionNameUtils.stripDecoratedPartitionName(deploymentName);
   }

   public static String computeDirectory(String deploymentName, String serverName, FileStoreMBean fStoreBean) {
      String shortName = computeShortName(deploymentName);
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return PartitionFileSystemUtils.locateStoreDirectory(cic, serverName, fStoreBean.getDirectory(), shortName);
   }

   public static String computeFullyDecoratedTableName(JDBCStoreMBean jBean, String instanceName) {
      if (jBean == null) {
         throw new AssertionError("JDBCStoreMBean is null, while trying to compute the fully decorated table name");
      } else {
         String distributionPolicy = jBean.getDistributionPolicy();
         String prefix = jBean.getPrefixName();
         if (prefix == null) {
            prefix = "";
         }

         prefix = prefix.trim();
         String suffix = "WLStore";
         String tableName;
         if (instanceName == null) {
            tableName = prefix + suffix;
         } else if (distributionPolicy.equalsIgnoreCase("Distributed")) {
            if (prefix.length() == 0) {
               tableName = instanceName + "_" + suffix;
            } else {
               tableName = prefix + "_" + instanceName + "_" + suffix;
            }
         } else if (prefix.length() != 0 && JDBCHelper.parseTable(prefix).length() != 0 && !prefix.endsWith(".")) {
            tableName = prefix + "_" + instanceName + "_" + suffix;
         } else {
            tableName = prefix + "S" + "_" + instanceName + "_" + suffix;
         }

         if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
            StoreDebug.storeIOPhysicalVerbose.debug("computeFullyDecoratedTableName(): storeName=" + jBean.getName() + ", distributionPolicy=" + distributionPolicy + ", instanceName=" + instanceName + ", prefix=" + prefix + ", suffix=" + suffix + ", tableName=" + tableName);
         }

         return tableName;
      }
   }

   public static DataSource createDataSource(JDBCStoreMBean jBean, ClearOrEncryptedService encryptionService, boolean useJNDI) throws PersistentStoreException {
      try {
         if (jBean.getDataSource() == null) {
            throw new PersistentStoreException("The JDBC store " + jBean.getName() + " has no data source");
         } else {
            JDBCDataSourceBean dsBean = jBean.getDataSource().getJDBCResource();
            if (dsBean != null && dsBean.getJDBCDataSourceParams() != null) {
               if (useJNDI && KernelStatus.isServer() && JDBCUtil.getLegacyType(dsBean) == 0) {
                  String jndiName = parseJNDIName(dsBean.getJDBCDataSourceParams().getJNDINames());

                  try {
                     InitialContext ic = new InitialContext();

                     DataSource var27;
                     try {
                        var27 = (DataSource)ic.lookup(jndiName);
                     } finally {
                        ic.close();
                     }

                     return var27;
                  } catch (NamingException var18) {
                     throw new PersistentStoreException("Can't find JDBC DataSource " + jndiName + ": " + var18, var18);
                  }
               } else {
                  JDBCDriverParamsBean driverBean = dsBean.getJDBCDriverParams();
                  if (driverBean == null) {
                     throw new PersistentStoreException("Can't connect using JDBC DataSource because there is no jdbc-driver-params element and no JNDI name");
                  } else {
                     if (KernelStatus.isServer()) {
                        try {
                           DataSource ret = new BasicDataSource("jdbc:weblogic:pool:" + dsBean.getName(), "weblogic.jdbc.pool.Driver", (Properties)null, (String)null);
                           Connection conn = ret.getConnection();
                           conn.close();
                           return ret;
                        } catch (Exception var20) {
                        }
                     }

                     String password = driverBean.getPassword();
                     if (encryptionService != null && isEmptyString(password) && !isEmptyBytes(driverBean.getPasswordEncrypted())) {
                        byte[] pwBytes = encryptionService.decryptBytes(driverBean.getPasswordEncrypted());

                        try {
                           password = new String(pwBytes, "UTF-8");
                        } catch (IOException var19) {
                           throw new PersistentStoreException(var19);
                        }
                     }

                     String url = driverBean.getUrl();
                     String driver = driverBean.getDriverName();
                     Properties driverProps = new Properties();
                     JDBCPropertiesBean propsBean = driverBean.getProperties();
                     if (propsBean != null) {
                        JDBCPropertyBean[] props = propsBean.getProperties();

                        for(int inc = 0; props != null && inc < props.length; ++inc) {
                           driverProps.put(props[inc].getName(), props[inc].getValue());
                        }
                     }

                     return new BasicDataSource(url, driver, driverProps, password);
                  }
               }
            } else {
               throw new PersistentStoreException("The data source for JDBC store " + jBean.getName() + " does not exist");
            }
         }
      } catch (SQLException var21) {
         throw new PersistentStoreException(var21);
      }
   }

   private static String parseJNDIName(String[] names) {
      assert names != null && names.length > 0;

      assert !isEmptyString(names[0]);

      return names[0];
   }

   private static boolean isEmptyString(String str) {
      return str == null || str.length() == 0;
   }

   private static boolean isEmptyBytes(byte[] bytes) {
      return bytes == null || bytes.length == 0;
   }

   public List storesExist(List storesToCheck) {
      StoreQueryStatus queryStatus = null;
      List results = new ArrayList();

      Throwable throwable;
      StoreQueryParam queryParam;
      for(Iterator var7 = storesToCheck.iterator(); var7.hasNext(); results.add(new StoreQueryResult(queryParam, queryStatus, throwable))) {
         queryParam = (StoreQueryParam)var7.next();
         PersistentStoreMBean storeMBean = queryParam.getStoreBean();
         boolean itExists = false;
         queryStatus = null;
         throwable = null;

         try {
            if (storeMBean instanceof FileStoreMBean) {
               itExists = fileStoreExists(queryParam.getDeploymentName(), queryParam.getServerInstanceName(), queryParam.getStoreBean());
            } else if (storeMBean instanceof JDBCStoreMBean) {
               itExists = jdbcStoreExists(queryParam.getDeploymentName(), queryParam.getServerInstanceName(), queryParam.getStoreBean());
            } else if (storeMBean instanceof ReplicatedStoreMBean) {
               itExists = replicatedStoreExists(queryParam.getDeploymentName(), queryParam.getServerInstanceName(), queryParam.getStoreBean());
            }

            queryStatus = itExists ? StoreQueryStatus.FOUND : StoreQueryStatus.NOTFOUND;
         } catch (Throwable var10) {
            queryStatus = StoreQueryStatus.FAILURE;
            throwable = var10;
         }
      }

      return results;
   }

   private static boolean fileStoreExists(String deploymentName, String serverInstanceName, PersistentStoreMBean storeBean) throws DeploymentException {
      boolean itExists = false;
      String shortName = computeShortName(deploymentName);
      String dirName = computeDirectory(deploymentName, serverInstanceName, (FileStoreMBean)storeBean);

      try {
         FileStoreIO fileStoreIO = new FileStoreIO(shortName, dirName, false);
         itExists = fileStoreIO.exists(new HashMap());
         return itExists;
      } catch (Throwable var8) {
         throw new DeploymentException(var8);
      }
   }

   private static boolean jdbcStoreExists(String deploymentName, String serverInstanceName, PersistentStoreMBean storeBean) throws DeploymentException {
      boolean itExists = false;

      try {
         JDBCStoreMBean mBean = (JDBCStoreMBean)storeBean;
         DataSource dataSource = createDataSource(mBean, (ClearOrEncryptedService)null, true);
         String tableRef = computeFullyDecoratedTableName(mBean, serverInstanceName);
         JDBCStoreIO dummyStoreIO = new JDBCStoreIO(deploymentName, dataSource, tableRef, (String)null, 0, 0, 0);
         itExists = dummyStoreIO.exists((Map)null);
         return itExists;
      } catch (Throwable var8) {
         throw new DeploymentException(var8);
      }
   }

   private static boolean replicatedStoreExists(String deploymentName, String serverInstanceName, PersistentStoreMBean storeBean) throws DeploymentException {
      boolean itExists = false;

      try {
         HashMap configMap = new HashMap();
         ReplicatedStoreMBean repStoreBean = (ReplicatedStoreMBean)storeBean;
         String shortName = PartitionNameUtils.stripDecoratedPartitionName(deploymentName);
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         String dirName = PartitionFileSystemUtils.locateStoreDirectory(cic, serverInstanceName, repStoreBean.getDirectory(), shortName);
         ReplicatedStoreIO replicatedStoreIO = new ReplicatedStoreIO(shortName, dirName, false);
         configMap.put("DomainName", ManagementService.getRuntimeAccess(kernelId).getDomainName());
         itExists = replicatedStoreIO.exists(configMap);
         return itExists;
      } catch (Throwable var10) {
         throw new DeploymentException(var10);
      }
   }
}
