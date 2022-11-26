package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Blob;
import weblogic.jdbc.rmi.internal.OracleTBlob;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialOracleBlob extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -8657894292226207928L;
   private Blob rmi_b = null;
   private boolean closed = false;

   public void init(Blob c) {
      this.rmi_b = c;
   }

   public void internalClose() {
      if (!this.closed) {
         try {
            ((OracleTBlob)this.rmi_b).internalClose();
         } catch (Throwable var2) {
         }

         this.closed = true;
      }
   }

   public static Blob makeSerialOracleBlob(Blob anBlob) {
      if (anBlob == null) {
         return null;
      } else {
         SerialOracleBlob rmi_blob = (SerialOracleBlob)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialOracleBlob", anBlob, false);
         rmi_blob.init(anBlob);
         return (Blob)rmi_blob;
      }
   }
}
