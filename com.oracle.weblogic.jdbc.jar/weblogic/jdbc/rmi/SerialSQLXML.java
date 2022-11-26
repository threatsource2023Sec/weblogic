package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLXML;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialSQLXML extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = 5044862558599850283L;
   private SQLXML rmiSQLXML = null;
   private boolean closed = false;

   public void init(SQLXML anSQLXML) {
      this.rmiSQLXML = anSQLXML;
   }

   public static SQLXML makeSerialSQLXMLFromStub(SQLXML anSQLXML) throws SQLException {
      if (anSQLXML == null) {
         return null;
      } else {
         SerialSQLXML rmi_SQLXML = (SerialSQLXML)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialSQLXML", anSQLXML, false);
         rmi_SQLXML.init(anSQLXML);
         return (SQLXML)rmi_SQLXML;
      }
   }

   public void internalClose() {
      if (!this.closed) {
         try {
            ((weblogic.jdbc.rmi.internal.SQLXML)this.rmiSQLXML).internalClose();
         } catch (Throwable var2) {
         }

         this.closed = true;
      }
   }
}
