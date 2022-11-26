package weblogic.jdbc.rowset;

import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.WebRowSet;

/** @deprecated */
@Deprecated
public class JdbcRowSetFactory implements javax.sql.rowset.RowSetFactory {
   public CachedRowSet createCachedRowSet() throws SQLException {
      RowSetFactory rsfact = RowSetFactory.newInstance();
      return rsfact.newCachedRowSet();
   }

   public FilteredRowSet createFilteredRowSet() throws SQLException {
      RowSetFactory rsfact = RowSetFactory.newInstance();
      return rsfact.newFilteredRowSet();
   }

   public JdbcRowSet createJdbcRowSet() throws SQLException {
      RowSetFactory rsfact = RowSetFactory.newInstance();
      return rsfact.newJdbcRowSet();
   }

   public WebRowSet createWebRowSet() throws SQLException {
      RowSetFactory rsfact = RowSetFactory.newInstance();
      return rsfact.newWebRowSet();
   }

   public JoinRowSet createJoinRowSet() throws SQLException {
      RowSetFactory rsfact = RowSetFactory.newInstance();
      return rsfact.newJoinRowSet();
   }
}
