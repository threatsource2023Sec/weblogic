package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Struct;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialStruct extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = -1333590052490436895L;
   private Struct rmiStruct = null;

   public void init(Struct anStruct) {
      this.rmiStruct = anStruct;
   }

   public static Struct makeSerialStructFromStub(Struct anStruct) throws SQLException {
      if (anStruct == null) {
         return null;
      } else {
         SerialStruct rmi_struct = (SerialStruct)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialStruct", anStruct, false);
         rmi_struct.init(anStruct);
         return (Struct)rmi_struct;
      }
   }
}
