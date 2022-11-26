package org.apache.openjpa.jdbc.kernel;

import java.sql.Connection;
import java.util.Collection;
import org.apache.openjpa.jdbc.sql.RowManager;

public class BatchingOperationOrderUpdateManager extends OperationOrderUpdateManager {
   protected PreparedStatementManager newPreparedStatementManager(JDBCStore store, Connection conn) {
      int batchLimit = this.dict.getBatchLimit();
      return new BatchingPreparedStatementManagerImpl(store, conn, batchLimit);
   }

   protected Collection flush(RowManager rowMgr, PreparedStatementManager psMgr, Collection exceps) {
      Collection rtnCol = super.flush(rowMgr, psMgr, exceps);
      BatchingPreparedStatementManagerImpl bPsMgr = (BatchingPreparedStatementManagerImpl)psMgr;
      bPsMgr.flushBatch();
      return rtnCol;
   }
}
