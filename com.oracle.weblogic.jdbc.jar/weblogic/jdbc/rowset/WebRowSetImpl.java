package weblogic.jdbc.rowset;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import javax.sql.rowset.WebRowSet;

/** @deprecated */
@Deprecated
public class WebRowSetImpl extends CachedRowSetImpl implements WebRowSet {
   static final long serialVersionUID = -4142451464489136731L;

   public WebRowSetImpl() {
   }

   public WebRowSetImpl(Hashtable env) {
      super(env);
   }

   public Object getObject(int columnIndex, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Object getObject(String columnLabel, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }
}
