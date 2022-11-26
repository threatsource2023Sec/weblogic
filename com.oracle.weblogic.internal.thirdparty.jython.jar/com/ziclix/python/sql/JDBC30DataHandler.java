package com.ziclix.python.sql;

import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.python.core.PyObject;

public class JDBC30DataHandler extends FilterDataHandler {
   public JDBC30DataHandler(DataHandler datahandler) {
      super(datahandler);
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object) throws SQLException {
      ParameterMetaData meta = stmt.getParameterMetaData();
      super.setJDBCObject(stmt, index, object, meta.getParameterType(index));
   }

   static {
      try {
         Class.forName("java.sql.ParameterMetaData");
      } catch (ClassNotFoundException var1) {
         throw new RuntimeException("JDBC3.0 required to use this DataHandler");
      }
   }
}
