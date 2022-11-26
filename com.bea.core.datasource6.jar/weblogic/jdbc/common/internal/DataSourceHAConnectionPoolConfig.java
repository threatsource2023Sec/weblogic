package weblogic.jdbc.common.internal;

import java.util.Properties;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.jdbc.JDBCLogger;

public class DataSourceHAConnectionPoolConfig extends DataSourceConnectionPoolConfig {
   private int gravitationShrinkFrequencySeconds;

   public DataSourceHAConnectionPoolConfig(JDBCDataSourceBean dsBean, ClassLoader classLoader, String appName, String moduleName, String compName) {
      super(dsBean, classLoader, appName, moduleName, compName);
   }

   protected Properties getPoolParameters() {
      Properties props = super.getPoolParameters();
      String strVal = System.getProperty("weblogic.jdbc.gravitationShrinkFrequencySeconds");
      if (strVal != null) {
         try {
            this.gravitationShrinkFrequencySeconds = Integer.parseInt(strVal);
         } catch (NumberFormatException var4) {
            JDBCLogger.logErrorMessage("Invalid system property value weblogic.jdbc.gravitationShrinkFrequencySeconds=" + strVal);
            this.gravitationShrinkFrequencySeconds = Integer.parseInt("30");
         }
      } else {
         this.gravitationShrinkFrequencySeconds = Integer.parseInt("30");
      }

      return props;
   }

   public int getGravitationShrinkFrequencySeconds() {
      return this.gravitationShrinkFrequencySeconds;
   }
}
