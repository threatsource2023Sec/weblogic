package weblogic.jdbc.rowset;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import javax.sql.RowSetInternal;
import javax.sql.RowSetReader;

public final class CachedRowSetJDBCReader implements RowSetReader, Serializable {
   private static final long serialVersionUID = 8780947650868222680L;

   public void readData(RowSetInternal caller) throws SQLException {
      if (!(caller instanceof CachedRowSetImpl)) {
         throw new SQLException("WLSyncProvider only supports WLS CachedRowSet Implmentation.");
      } else {
         CachedRowSetImpl rowSet = (CachedRowSetImpl)caller;
         Connection con = rowSet.getConnection();
         CallableStatement cs = null;
         ResultSet rs = null;

         try {
            try {
               cs = con.prepareCall(rowSet.getCommand(), 1004, 1008);
            } catch (SQLException var18) {
               cs = con.prepareCall(rowSet.getCommand());
            }

            Iterator it = rowSet.getParameters().iterator();

            while(it.hasNext()) {
               WLParameter p = (WLParameter)it.next();
               p.setParam(cs);
            }

            int queryTimeout = rowSet.getQueryTimeout();
            if (queryTimeout != 0) {
               cs.setQueryTimeout(queryTimeout);
            }

            if (!rowSet.getEscapeProcessing()) {
               cs.setEscapeProcessing(false);
            }

            cs.execute();
            rs = cs.getResultSet();
            if (rs != null) {
               rowSet.populateInternal(rs);
            }
         } finally {
            try {
               if (rs != null) {
                  rs.close();
               }
            } catch (Exception var17) {
            }

            try {
               if (cs != null) {
                  cs.close();
               }
            } catch (Exception var16) {
            }

         }

      }
   }
}
