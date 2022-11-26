package weblogic.jdbc.rowset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.Locale;

public class OracleTableWriter extends TableWriter {
   OracleTableWriter(WLRowSetInternal rowSet, String tableName, BitSet columnMask) throws SQLException {
      super(rowSet, tableName, columnMask);
   }

   public void issueSQL(Connection con) throws SQLException {
      this.checkBatchUpdateCounts = false;
      if (this.batchDeletes) {
         this.batchDeletes = false;
         this.groupDeletes = true;
      }

      super.issueSQL(con);
   }

   protected void executeBatchVerifySelects(Connection con) throws SQLException {
      if (!this.batchVerifyParams.isEmpty()) {
         int start = 0;

         do {
            this.executeBatchVerifySelects(con, start);
            start += this.batchVerifySize;
         } while(start < this.batchVerifyParams.size());

      }
   }

   private void executeBatchVerifySelects(Connection con, int start) throws SQLException {
      StringBuffer sb = new StringBuffer(500);
      sb.append("SELECT 1 from ").append(this.tableName).append(" WHERE ");
      String sep = "";
      int stop = Math.min(start + this.batchVerifySize, this.batchVerifyParams.size());

      for(int i = start; i < stop; ++i) {
         sb.append(sep);
         sep = " OR ";
         TableWriter.BatchVerifyParam b = (TableWriter.BatchVerifyParam)this.batchVerifyParams.get(i);
         sb.append(b.getWhereClause());
      }

      if (con.getMetaData().getDatabaseProductName().toLowerCase(Locale.ENGLISH).indexOf("oracle") != -1) {
         sb.append(" FOR UPDATE NOWAIT");
      }

      String sql = sb.toString();
      PreparedStatement ps = null;
      ResultSet rs = null;

      try {
         ps = con.prepareStatement(sql);
         int param = 1;

         int count;
         for(count = start; count < stop; ++count) {
            TableWriter.BatchVerifyParam b = (TableWriter.BatchVerifyParam)this.batchVerifyParams.get(count);
            param = this.setWhereParameters(ps, b.getCols(), b.getFilter(), param);
         }

         rs = ps.executeQuery();

         for(count = 0; rs.next(); ++count) {
         }

         if (count != stop - start) {
            this.throwOCE(sql, (CachedRow)null);
         }
      } finally {
         if (rs != null) {
            try {
               rs.close();
            } catch (Exception var20) {
            }
         }

         if (ps != null) {
            try {
               ps.close();
            } catch (Exception var19) {
            }
         }

      }

   }
}
