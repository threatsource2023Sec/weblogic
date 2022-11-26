package weblogic.connector.outbound;

import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import weblogic.diagnostics.instrumentation.EventPayload;

public class ProfileDataRecord implements EventPayload {
   public static final String TYPE_CONN_USAGE = "WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.USAGE";
   public static final String TYPE_CONN_LEAK = "WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.LEAK";
   public static final String TYPE_CONN_WAIT = "WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.WAIT";
   public static final String TYPE_CONN_RESV_FAIL = "WEBLOGIC.CONNECTOR.OUTBOUND.CONNECTIONPOOL.RESVFAIL";
   private String type;
   private String poolName;
   private String timestamp;
   private Properties props;

   public ProfileDataRecord(String type, String poolName, Properties props) {
      this.type = type;
      this.poolName = poolName;
      this.timestamp = (new Date()).toString();
      this.props = props;
   }

   public String getType() {
      return this.type;
   }

   public String getPoolName() {
      return this.poolName;
   }

   public String getTimestamp() {
      return this.timestamp;
   }

   public Properties getProperties() {
      if (this.props == null) {
         this.props = new Properties();
      }

      return this.props;
   }

   public String getPropertiesString() {
      String propertiesString = "{ ";
      Enumeration propNames = this.getProperties().propertyNames();

      while(propNames.hasMoreElements()) {
         String propName = (String)propNames.nextElement();
         String propVal = this.props.getProperty(propName);
         propertiesString = propertiesString + propName + " = " + propVal;
         if (propNames.hasMoreElements()) {
            propertiesString = propertiesString + ", ";
         }
      }

      propertiesString = propertiesString + " }";
      return propertiesString;
   }
}
