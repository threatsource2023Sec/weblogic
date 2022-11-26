package weblogic.jdbc.common.internal;

import java.util.Properties;
import java.util.StringTokenizer;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jdbc.extensions.DataSourceSwitchingCallback;

public class DataSourceSwitchingCallbackImpl implements DataSourceSwitchingCallback {
   private static boolean DEBUG = false;
   private String oldval = null;
   private Properties oldprops = null;

   public DataSource getDataSource(String hostDataSource, String datasourceSwitchingProperties) {
      if (datasourceSwitchingProperties == null) {
         return null;
      } else {
         Properties properties;
         if (!datasourceSwitchingProperties.equals(this.oldval)) {
            properties = this.getSwitchingMap(datasourceSwitchingProperties);
            this.oldval = datasourceSwitchingProperties;
            this.oldprops = properties;
         } else {
            properties = this.oldprops;
         }

         String dataSourceName = this.getDataSourceName(hostDataSource, properties);
         if (dataSourceName == null) {
            return null;
         } else {
            DataSource ds = null;
            if (DEBUG) {
               System.out.println("host-ds-name : " + hostDataSource + ", dsName : " + dataSourceName);
            }

            InitialContext context = null;

            try {
               context = new InitialContext();
               ds = (DataSource)context.lookup(dataSourceName);
            } catch (NamingException var15) {
               throw new RuntimeException(var15);
            } finally {
               if (context != null) {
                  try {
                     context.close();
                  } catch (Exception var14) {
                  }
               }

            }

            return ds;
         }
      }
   }

   private String getDataSourceName(String hostDataSource, Properties properties) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext context = manager.getCurrentComponentInvocationContext();
      String dataSourceName;
      if (context != null && !context.isGlobalRuntime()) {
         String partitionName = context.getPartitionName();
         dataSourceName = (String)properties.get(partitionName);
         if (dataSourceName == null) {
            dataSourceName = (String)properties.get("default");
         }
      } else {
         dataSourceName = (String)properties.get("default");
         if (DEBUG) {
            System.out.println("No current tenant, using default datasource name [" + dataSourceName + "]");
         }
      }

      return dataSourceName;
   }

   private Properties getSwitchingMap(String switchingProperties) {
      Properties properties = new Properties();
      if (switchingProperties != null) {
         StringTokenizer tokenizer = new StringTokenizer(switchingProperties, ";");

         while(tokenizer.hasMoreElements()) {
            String property = (String)tokenizer.nextElement();
            int index = property.indexOf("=");
            if (index > 0 && property.length() >= index + 1) {
               String name = property.substring(0, index);
               String value = property.substring(index + 1).trim();
               properties.put(name, value);
            }
         }
      }

      return properties;
   }
}
