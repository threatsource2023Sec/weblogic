package weblogic.diagnostics.archive.dbstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import javax.naming.NamingException;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.ArchiveConstants;
import weblogic.diagnostics.archive.DataWriter;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.management.ManagementException;

public class EventsJdbcDataArchive extends JdbcDataArchive implements DataWriter {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");

   public EventsJdbcDataArchive(String name, String jndiName, String schemaName) throws NamingException, ManagementException, SQLException {
      this(name, jndiName, schemaName, (String)null, (String)null, (String)null);
   }

   public EventsJdbcDataArchive(String name, String jndiName, String schemaName, String url, String userName, String password) throws NamingException, ManagementException, SQLException {
      super(name, jndiName, schemaName, "WLS_EVENTS", ArchiveConstants.getColumns(1), url, userName, password);
   }

   public String getDescription() {
      return "Diagnostic Events";
   }

   protected DataRecord getDataRecord(ResultSet rs) throws SQLException {
      int size = ArchiveConstants.EVENTS_ARCHIVE_COLUMNS_COUNT;
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
      data[cnt] = new Integer(rs.getInt(cnt + 1));
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
      data[cnt] = this.getPayloadObject(rs.getBytes(cnt + 1));
      ++cnt;
      data[cnt] = rs.getString(cnt + 1);
      ++cnt;
      data[cnt] = new Long(rs.getLong(cnt + 1));
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
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setBytes(cnt, this.getPayloadBytes(record.get(cnt)));
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setLong(cnt, (Long)record.get(cnt));
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.setString(cnt, record.get(cnt).toString());
      ++cnt;
      pStmt.executeUpdate();
   }

   private byte[] getPayloadBytes(Object payload) {
      byte[] barr = new byte[0];
      ByteArrayOutputStream bos = null;
      ObjectOutputStream oos = null;

      try {
         bos = new ByteArrayOutputStream();
         oos = new ObjectOutputStream(bos);
         oos.writeObject(payload);
         barr = bos.toByteArray();
         oos.close();
      } catch (Exception var14) {
         UnexpectedExceptionHandler.handle("Could not persist payload", var14);
      } finally {
         if (oos != null) {
            try {
               oos.close();
            } catch (Exception var13) {
            }
         }

      }

      return barr;
   }

   private Object getPayloadObject(byte[] barr) {
      ByteArrayInputStream bis = null;
      ObjectInputStream ois = null;
      Object payload = null;

      try {
         if (barr != null && barr.length > 0) {
            bis = new ByteArrayInputStream(barr);
            ois = new ObjectInputStream(bis);
            payload = ois.readObject();
         }
      } catch (Exception var14) {
         UnexpectedExceptionHandler.handle("Could not reconstruct payload", var14);
      } finally {
         if (ois != null) {
            try {
               ois.close();
            } catch (Exception var13) {
            }
         }

      }

      return payload;
   }

   public static void main(String[] args) throws Exception {
      String jndiName = args[0];
      String query = args.length > 1 ? args[1] : null;
      String schemaName = System.getProperty("schema");
      EventsJdbcDataArchive archive = new EventsJdbcDataArchive("EventsDataArchive", jndiName, schemaName);
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
