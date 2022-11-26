package com.ziclix.python.sql.handler;

import com.ziclix.python.sql.DataHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyObject;

public class PostgresqlDataHandler extends RowIdHandler {
   public PostgresqlDataHandler(DataHandler datahandler) {
      super(datahandler);
   }

   protected String getRowIdMethodName() {
      return "getLastOID";
   }

   public PyObject getPyObject(ResultSet set, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      switch (type) {
         case 2:
         case 3:
            BigDecimal bd = set.getBigDecimal(col);
            obj = bd == null ? Py.None : Py.newDecimal(bd.toString());
            break;
         case 1111:
            try {
               obj = super.getPyObject(set, col, type);
            } catch (SQLException var7) {
               obj = super.getPyObject(set, col, 12);
            }
            break;
         default:
            obj = super.getPyObject(set, col, type);
      }

      return !set.wasNull() && obj != null ? obj : Py.None;
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      if (!DataHandler.checkNull(stmt, index, object, type)) {
         switch (type) {
            case -1:
               String varchar;
               if (object instanceof PyFile) {
                  varchar = ((PyFile)object).read().asString();
               } else {
                  varchar = (String)object.__tojava__(String.class);
               }

               stmt.setObject(index, varchar, type);
               break;
            default:
               super.setJDBCObject(stmt, index, object, type);
         }

      }
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object) throws SQLException {
      Object value = object.__tojava__(Object.class);
      if (value instanceof BigInteger) {
         super.setJDBCObject(stmt, index, object, -5);
      } else {
         super.setJDBCObject(stmt, index, object);
      }

   }
}
