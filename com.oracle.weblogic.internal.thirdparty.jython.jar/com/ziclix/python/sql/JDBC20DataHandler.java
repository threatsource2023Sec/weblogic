package com.ziclix.python.sql;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.util.StringUtil;

public class JDBC20DataHandler extends FilterDataHandler {
   public JDBC20DataHandler(DataHandler datahandler) {
      super(datahandler);
   }

   public void setJDBCObject(PreparedStatement stmt, int index, PyObject object, int type) throws SQLException {
      if (!DataHandler.checkNull(stmt, index, (PyObject)object, type)) {
         switch (type) {
            case 2004:
               byte[] lob = null;
               Object jobject = null;
               if (object instanceof PyFile) {
                  jobject = ((PyObject)object).__tojava__(InputStream.class);
               } else {
                  jobject = ((PyObject)object).__tojava__(Object.class);
               }

               if (jobject instanceof InputStream) {
                  lob = read((InputStream)jobject);
               } else if (jobject instanceof byte[]) {
                  lob = (byte[])((byte[])jobject);
               }

               if (lob != null) {
                  stmt.setBytes(index, lob);
                  break;
               }
            default:
               super.setJDBCObject(stmt, index, (PyObject)object, type);
               break;
            case 2005:
               if (object instanceof PyFile) {
                  object = ((PyFile)object).read();
               }

               String clob = (String)((PyObject)object).__tojava__(String.class);
               int length = clob.length();
               InputStream stream = new ByteArrayInputStream(StringUtil.toBytes(clob));
               InputStream stream = new BufferedInputStream(stream);
               stmt.setBinaryStream(index, stream, length);
         }

      }
   }

   public PyObject getPyObject(ResultSet set, int col, int type) throws SQLException {
      PyObject obj = Py.None;
      Object obj;
      switch (type) {
         case 2:
         case 3:
            try {
               BigDecimal bd = set.getBigDecimal(col);
               obj = bd == null ? Py.None : Py.newFloat(bd.doubleValue());
            } catch (SQLException var8) {
               obj = super.getPyObject(set, col, type);
            }
            break;
         case 2003:
            Array array = set.getArray(col);
            obj = array == null ? Py.None : Py.java2py(array.getArray());
            break;
         case 2004:
            Blob blob = set.getBlob(col);
            obj = blob == null ? Py.None : Py.java2py(read(blob.getBinaryStream()));
            break;
         case 2005:
            Reader reader = set.getCharacterStream(col);
            obj = reader == null ? Py.None : Py.newUnicode(read(reader));
            break;
         default:
            return super.getPyObject(set, col, type);
      }

      return (PyObject)(!set.wasNull() && obj != null ? obj : Py.None);
   }
}
