package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Ref;
import java.sql.SQLException;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialRef extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = 5037775703210750679L;
   private Ref rmiRef = null;

   public void init(Ref anRef) {
      this.rmiRef = anRef;
   }

   public static Ref makeSerialRefFromStub(Ref anRef) throws SQLException {
      if (anRef == null) {
         return null;
      } else {
         SerialRef rmi_ref = (SerialRef)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialRef", anRef, false);
         rmi_ref.init(anRef);
         return (Ref)rmi_ref;
      }
   }
}
