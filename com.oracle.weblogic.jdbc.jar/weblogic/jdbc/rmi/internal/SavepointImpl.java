package weblogic.jdbc.rmi.internal;

import java.rmi.NoSuchObjectException;
import java.sql.SQLException;
import java.sql.Savepoint;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;
import weblogic.rmi.server.UnicastRemoteObject;

public class SavepointImpl extends RMISkelWrapperImpl {
   private Savepoint t2_sp = null;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      if (ret == null) {
         super.postInvocationHandler(methodName, params, (Object)null);
         return null;
      } else {
         super.postInvocationHandler(methodName, params, ret);
         return ret;
      }
   }

   public void init(Savepoint sp) {
      this.t2_sp = sp;
   }

   public static Savepoint makeSavepointImpl(Savepoint sp) {
      if (sp == null) {
         return null;
      } else {
         SavepointImpl rmi_sp = (SavepointImpl)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.internal.SavepointImpl", sp, true);
         rmi_sp.init(sp);
         return (Savepoint)rmi_sp;
      }
   }

   void close() throws SQLException {
      try {
         UnicastRemoteObject.unexportObject(this, true);
      } catch (NoSuchObjectException var2) {
      }

   }
}
