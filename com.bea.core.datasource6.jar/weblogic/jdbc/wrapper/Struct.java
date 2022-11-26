package weblogic.jdbc.wrapper;

import java.sql.NClob;
import java.sql.SQLException;
import java.sql.SQLXML;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class Struct extends DataType {
   public static java.sql.Struct makeStruct(java.sql.Struct struct, java.sql.Connection conn) {
      if (struct == null) {
         return null;
      } else {
         if (conn != null && conn instanceof Connection) {
            ConnectionEnv cc = ((Connection)conn).getConnectionEnv();
            if (cc != null && !cc.isWrapTypes()) {
               return struct;
            }
         }

         Struct wrapperStruct = (Struct)JDBCWrapperFactory.getWrapper(10, struct, false);
         wrapperStruct.init(conn);
         return (java.sql.Struct)wrapperStruct;
      }
   }

   private static void wrapArray(Object[] array, java.sql.Connection conn) throws SQLException {
      if (array != null) {
         for(int i = 0; i < array.length; ++i) {
            Object ret = array[i];
            if (!(ret instanceof JDBCWrapperImpl)) {
               if (ret instanceof java.sql.Clob) {
                  ret = Clob.makeClob((java.sql.Clob)ret, conn);
               } else if (ret instanceof java.sql.Blob) {
                  ret = Blob.makeBlob((java.sql.Blob)ret, conn);
               } else if (ret instanceof java.sql.Struct) {
                  ret = makeStruct((java.sql.Struct)ret, conn);
               } else if (ret instanceof java.sql.Ref) {
                  ret = Ref.makeRef((java.sql.Ref)ret, conn);
               } else if (ret instanceof java.sql.Array) {
                  ret = Array.makeArray((java.sql.Array)ret, conn);
               } else if (ret instanceof NClob) {
                  ret = WrapperNClob.makeNClob((NClob)ret, conn);
               } else if (ret instanceof SQLXML) {
                  ret = WrapperSQLXML.makeSQLXML((SQLXML)ret, conn);
               }

               array[i] = ret;
            }
         }

      }
   }
}
