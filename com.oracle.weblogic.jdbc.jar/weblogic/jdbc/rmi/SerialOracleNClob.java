package weblogic.jdbc.rmi;

import java.sql.NClob;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialOracleNClob extends SerialOracleClob {
   private static final long serialVersionUID = 6168612739413116233L;

   public static NClob makeSerialOracleNClob(NClob aNClob) {
      if (aNClob == null) {
         return null;
      } else {
         SerialOracleNClob rmi_nclob = (SerialOracleNClob)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialOracleNClob", aNClob, false);
         rmi_nclob.init(aNClob);
         return (NClob)rmi_nclob;
      }
   }
}
