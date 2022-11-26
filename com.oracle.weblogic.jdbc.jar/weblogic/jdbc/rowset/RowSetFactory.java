package weblogic.jdbc.rowset;

import javax.sql.DataSource;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.WebRowSet;

/** @deprecated */
@Deprecated
public abstract class RowSetFactory {
   protected RowSetFactory() {
   }

   public static RowSetFactory newInstance() {
      return new RowSetFactoryImpl();
   }

   public abstract WLCachedRowSet newCachedRowSet();

   public abstract JoinRowSet newJoinRowSet();

   public abstract JdbcRowSet newJdbcRowSet();

   public abstract FilteredRowSet newFilteredRowSet();

   public abstract WebRowSet newWebRowSet();

   public abstract String getDataSourceName();

   public abstract void setDataSourceName(String var1);

   public abstract String getUsername();

   public abstract void setUsername(String var1);

   public abstract String getPassword();

   public abstract void setPassword(String var1);

   public abstract String getUrl();

   public abstract void setUrl(String var1);

   public abstract DataSource getDataSource();

   public abstract void setDataSource(DataSource var1);
}
