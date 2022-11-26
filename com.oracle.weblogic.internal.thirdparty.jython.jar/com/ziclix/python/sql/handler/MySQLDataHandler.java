package com.ziclix.python.sql.handler;

import com.ziclix.python.sql.DataHandler;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.util.StringUtil;

public class MySQLDataHandler extends RowIdHandler {
   public MySQLDataHandler(DataHandler datahandler) {
      super(datahandler);
   }

   protected String getRowIdMethodName() {
      return "getLastInsertID";
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      if (!DataHandler.checkNull(stmt, index, object, type)) {
         switch (type) {
            case -1:
               byte[] bytes;
               if (object instanceof PyFile) {
                  bytes = ((PyFile)object).read().toBytes();
               } else {
                  String varchar = (String)object.__tojava__(String.class);
                  bytes = StringUtil.toBytes(varchar);
               }

               InputStream stream = new ByteArrayInputStream(bytes);
               InputStream stream = new BufferedInputStream(stream);
               stmt.setAsciiStream(index, stream, bytes.length);
               break;
            default:
               super.setJDBCObject(stmt, index, object, type);
         }

      }
   }
}
