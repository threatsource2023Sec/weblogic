package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Clob;
import weblogic.jdbc.rmi.internal.OracleTClob;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialOracleClob extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -3424919475796496658L;
   private Clob rmi_c = null;
   private boolean closed = false;

   public void init(Clob c) {
      this.rmi_c = c;
   }

   public void internalClose() {
      if (!this.closed) {
         try {
            ((OracleTClob)this.rmi_c).internalClose();
         } catch (Throwable var2) {
         }

         this.closed = true;
      }
   }

   public static Clob makeSerialOracleClob(Clob anClob) {
      if (anClob == null) {
         return null;
      } else {
         SerialOracleClob rmi_clob = (SerialOracleClob)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialOracleClob", anClob, false);
         rmi_clob.init(anClob);
         return (Clob)rmi_clob;
      }
   }
}
