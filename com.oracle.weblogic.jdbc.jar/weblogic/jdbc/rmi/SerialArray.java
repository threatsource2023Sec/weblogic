package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import weblogic.jdbc.JDBCLogger;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialArray extends RMIStubWrapperImpl implements Serializable {
   private static final long serialVersionUID = 3806747738272517092L;
   private Array rmiArray = null;
   private boolean closed = false;

   public Object postInvocationHandler(String methodName, Object[] params, Object ret) throws Exception {
      super.postInvocationHandler(methodName, params, ret);
      if (ret == null) {
         return null;
      } else {
         try {
            return ret instanceof ResultSet ? SerialResultSet.makeSerialResultSet((ResultSet)ret, (SerialStatement)null) : ret;
         } catch (Exception var5) {
            JDBCLogger.logStackTrace(var5);
            return ret;
         }
      }
   }

   public void init(Array anArray) {
      this.rmiArray = anArray;
   }

   public static Array makeSerialArrayFromStub(Array anArray) throws SQLException {
      if (anArray == null) {
         return null;
      } else {
         SerialArray rmi_array = (SerialArray)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialArray", anArray, false);
         rmi_array.init(anArray);
         return (Array)rmi_array;
      }
   }

   public void internalClose() {
      if (!this.closed) {
         try {
            ((weblogic.jdbc.rmi.internal.Array)this.rmiArray).internalClose();
         } catch (Throwable var2) {
         }

         this.closed = true;
      }
   }
}
