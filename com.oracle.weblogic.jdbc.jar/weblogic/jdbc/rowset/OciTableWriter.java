package weblogic.jdbc.rowset;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.BitSet;

public class OciTableWriter extends OracleTableWriter {
   public OciTableWriter(WLRowSetInternal rowSet, String tableName, BitSet columnMask) throws SQLException {
      super(rowSet, tableName, columnMask);
   }

   protected Object insertedObject(Connection con, Object o) {
      return this.convert(o);
   }

   protected Object updatedObject(Object o) {
      return this.convert(o);
   }

   private Object convert(Object o) {
      if (o == null) {
         return null;
      } else {
         Class c = o.getClass();
         if (c == RowSetClob.class) {
            return new String(((RowSetClob)o).getData());
         } else {
            return c == RowSetBlob.class ? ((RowSetBlob)o).getData() : o;
         }
      }
   }
}
