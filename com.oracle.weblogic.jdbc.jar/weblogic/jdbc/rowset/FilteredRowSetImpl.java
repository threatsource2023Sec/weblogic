package weblogic.jdbc.rowset;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import javax.sql.rowset.FilteredRowSet;

/** @deprecated */
@Deprecated
public class FilteredRowSetImpl extends CachedRowSetImpl implements FilteredRowSet {
   static final long serialVersionUID = -8547038078811651350L;

   public FilteredRowSetImpl() {
   }

   public FilteredRowSetImpl(Hashtable env) {
      super(env);
   }

   public Object getObject(int columnIndex, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Object getObject(String columnLabel, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }
}
