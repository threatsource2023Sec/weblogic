package weblogic.diagnostics.archive.dbstore;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import javax.naming.NamingException;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;

public class HarvestedJdbcDataArchive extends JdbcDataArchive implements DataWriter {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");

   public HarvestedJdbcDataArchive(String name, String jndiName, String schemaName) throws NamingException, ManagementException, SQLException {
      this(name, jndiName, schemaName, (String)null, (String)null, (String)null);
   }

   public HarvestedJdbcDataArchive(String name, String jndiName, String schemaName, String url, String userName, String password) throws NamingException, ManagementException, SQLException {
      super(name, jndiName, schemaName, "WLS_HVST", ArchiveConstants.getColumns(2), url, userName, password);
   }

   public String getDescription() {
      return "Harvested diagnostic data";
   }

   protected DataRecord getDataRecord(ResultSet rs) throws SQLException {
      int size = ArchiveConstants.HARVESTER_ARCHIVE_COLUMNS_COUNT;
      Object[] data = new Object[size];
      int cnt = 0;
      data[cnt] = new Long(rs.getLong(cnt + 1));
      ++cnt;
      data[cnt] = new Long(rs.getLong(cnt + 1));
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = new Integer(rs.getString(cnt + 1));
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      return new DataRecord(data);
   }

   protected void insertDataRecord(PreparedStatement pStmt, Object data) throws SQLException {
      DataRecord record = (DataRecord)data;
      int cnt = 1;
      pStmt.setLong(cnt, (Long)record.get(cnt));
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setInt(cnt, (Integer)record.get(cnt));
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.executeUpdate();
   }

   public static void main(String[] args) throws Exception {
      String jndiName = args[0];
      String query = args.length > 1 ? args[1] : null;
      String schemaName = System.getProperty("schema");
      HarvestedJdbcDataArchive archive = new HarvestedJdbcDataArchive("HarvestedDataArchive", jndiName, schemaName);
      int cnt = 0;
      long t0 = System.currentTimeMillis();
      Iterator it = archive.getDataRecords(query);

      while(it.hasNext()) {
         DataRecord dataRecord = (DataRecord)it.next();
         ++cnt;
         DebugLogger.println(dataRecord.toString());
      }

      long t1 = System.currentTimeMillis();
      DebugLogger.println("Found " + cnt + " record(s) in " + (t1 - t0) + " ms");
   }
}
