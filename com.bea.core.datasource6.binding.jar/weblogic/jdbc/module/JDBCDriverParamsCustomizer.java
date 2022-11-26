package weblogic.jdbc.module;

import java.io.Serializable;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;
import weblogic.j2ee.descriptor.wl.customizers.JDBCDriverParamsBeanCustomizer;
import weblogic.jdbc.JDBCLogger;

public class JDBCDriverParamsCustomizer implements JDBCDriverParamsBeanCustomizer, Serializable {
   private JDBCDriverParamsBean customized;
   private static boolean convertWLDriverURL = false;

   public JDBCDriverParamsCustomizer(JDBCDriverParamsBean paramCustomized) {
      this.customized = paramCustomized;
   }

   public void _postCreate() {
      String driverURL = this.customized.getUrl();
      if (driverURL != null && driverURL.startsWith("jdbc:bea:")) {
         if (driverURL.startsWith("jdbc:bea:oracle:")) {
            JDBCLogger.logWLOracleDriverWarning(driverURL);
         } else if (convertWLDriverURL) {
            driverURL = "jdbc:weblogic:" + driverURL.substring("jdbc:bea:".length());
            JDBCLogger.logConvertWLDriverURL(this.customized.getUrl(), driverURL);
            this.customized.setUrl(driverURL);
         }
      }

   }

   private static boolean getCommandLineProps() {
      String commandlineProps = System.getProperty("weblogic.jdbc.convertWLDriverURL");
      return commandlineProps != null && commandlineProps.equalsIgnoreCase("true");
   }

   static {
      convertWLDriverURL = getCommandLineProps();
   }
}
