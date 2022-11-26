package weblogic.jdbc.wrapper;

import java.sql.SQLException;
import java.util.Properties;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import weblogic.transaction.nonxa.DataSourceEmulatedTwoPhaseResource;

public class JTSEmulateXAResourceImpl extends JTSXAResourceImpl implements DataSourceEmulatedTwoPhaseResource {
   private String name;
   private boolean isOnePhaseCommit;

   public JTSEmulateXAResourceImpl(JTSConnection aConnection) {
      super(aConnection);
      this.name = aConnection.getPool();
   }

   public JTSEmulateXAResourceImpl(String poolName, Properties props) throws SQLException {
      super(new JTSConnection(poolName, props.getProperty("applicationName"), props.getProperty("moduleName"), props.getProperty("compName"), isEmulate2PCCapable(props)));
      this.name = poolName;
      this.isOnePhaseCommit = this.isOnePhaseCommit();
   }

   public boolean isSameRM(XAResource xar) throws XAException {
      return !(xar instanceof JTSEmulateXAResourceImpl) ? false : this.name.equals(((JTSEmulateXAResourceImpl)xar).name);
   }

   public boolean isOnePhaseCommit() {
      return this.isOnePhaseCommit;
   }

   private static boolean isEmulate2PCCapable(Properties props) {
      return isPropertyTrue(props, "EmulateTwoPhaseCommit");
   }

   private static boolean isOnePhaseCommit(Properties props) {
      return isPropertyTrue(props, "OnePhaseCommit");
   }

   private static boolean isPropertyTrue(Properties props, String propName) {
      if (props != null) {
         String val = (String)props.get(propName);
         if (val != null) {
            return "true".equals(val);
         }
      }

      return false;
   }
}
