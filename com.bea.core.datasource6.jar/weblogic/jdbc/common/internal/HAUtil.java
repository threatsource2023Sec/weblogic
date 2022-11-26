package weblogic.jdbc.common.internal;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;

public abstract class HAUtil {
   static HAUtil instance;

   static boolean isHADataSource(JDBCDataSourceBean dsBean) {
      if (dsBean == null) {
         return false;
      } else {
         String type = JDBCUtil.getDataSourceType(dsBean);
         return type.equals("AGL");
      }
   }

   public static void setInstance(HAUtil hautil) {
      instance = hautil;
   }

   public static HAUtil getInstance() {
      return instance;
   }

   public abstract PooledResourceFactory createConnectionEnvFactory(ConnectionPool var1, String var2, String var3, String var4, Properties var5) throws ResourceException;

   public abstract XAConnectionEnvFactory createXAConnectionEnvFactory(ConnectionPool var1, String var2, String var3, String var4, String var5, Properties var6) throws ResourceException, SQLException;

   public abstract PooledResourceFactory createPooledConnectionEnvFactory(ConnectionPool var1, String var2, String var3, String var4, Properties var5) throws ResourceException, SQLException;

   public String getAffinityContextKey(String databaseName, String serviceName) {
      return "weblogic.jdbc.affinity." + databaseName + "." + serviceName;
   }

   public String getInstanceName(PooledResourceInfo pri) {
      if (pri instanceof HAPooledResourceInfo) {
         HAPooledResourceInfo hapri = (HAPooledResourceInfo)pri;
         if (hapri.getInstanceName() != null) {
            return ((HAPooledResourceInfo)pri).getInstanceName();
         }

         if (hapri.getServiceInstances() != null) {
            Set instances = hapri.getServiceInstances();
            if (instances.size() > 0) {
               Iterator it = instances.iterator();
               int pos = (int)((double)instances.size() * Math.random());

               for(int i = 0; i < pos; ++i) {
                  it.next();
               }

               return (String)it.next();
            }
         }
      }

      return null;
   }
}
