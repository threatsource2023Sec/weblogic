package weblogic.jdbc.rowset;

import javax.sql.RowSetInternal;
import javax.sql.RowSetReader;
import javax.sql.RowSetWriter;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;

public class WLSyncProvider extends SyncProvider {
   private static final String PROVIDER_ID = "weblogic.jdbc.rowset.WLSyncProvider";
   private RowSetReader reader = new CachedRowSetJDBCReader();
   private RowSetWriter writer = new CachedRowSetJDBCWriter();

   public String getProviderID() {
      return "weblogic.jdbc.rowset.WLSyncProvider";
   }

   public RowSetReader getRowSetReader() {
      return this.reader;
   }

   public RowSetWriter getRowSetWriter() {
      return this.writer;
   }

   public RowSetInternal getRowSetInternal() {
      return null;
   }

   public int getProviderGrade() {
      return 3;
   }

   public void setDataSourceLock(int lock) throws SyncProviderException {
      if (lock != 1) {
         throw new SyncProviderException("WLS RowSet only supports DATASOURCE_NO_LOCK");
      }
   }

   public int getDataSourceLock() throws SyncProviderException {
      return 1;
   }

   public int supportsUpdatableView() {
      return 5;
   }

   public String getVendor() {
      return "BEA Systems, Inc.";
   }

   public String getVersion() {
      return "9.0";
   }
}
