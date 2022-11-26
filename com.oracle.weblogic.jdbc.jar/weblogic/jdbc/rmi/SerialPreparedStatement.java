package weblogic.jdbc.rmi;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialPreparedStatement extends SerialStatement {
   private static final long serialVersionUID = -8138909829902801692L;
   private PreparedStatement rmi_pstmt = null;

   public void init(PreparedStatement s, SerialConnection c) {
      super.init(s, c);
      this.rmi_pstmt = s;
   }

   public static PreparedStatement makeSerialPreparedStatement(PreparedStatement s, SerialConnection conn) {
      if (s == null) {
         return null;
      } else {
         SerialPreparedStatement serial_stmt = (SerialPreparedStatement)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialPreparedStatement", s, false);
         serial_stmt.init(s, conn);
         return (PreparedStatement)serial_stmt;
      }
   }

   public ResultSetMetaData getMetaData() throws SQLException {
      ResultSetMetaData ret = null;
      String methodName = "getMetaData";
      Object[] params = new Object[0];

      try {
         this.preInvocationHandler(methodName, params);
         ret = new SerialResultSetMetaData(this.rmi_pstmt.getMetaData());
         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var5) {
         this.invocationExceptionHandler(methodName, params, var5);
      }

      return ret;
   }
}
