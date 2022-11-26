package weblogic.jdbc.rowset;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.RowSetInternal;

/** @deprecated */
@Deprecated
public interface WLRowSetInternal extends RowSetInternal, WLCachedRowSet {
   List getCachedRows() throws SQLException;

   void setCachedRows(ArrayList var1);

   void setIsComplete(boolean var1);
}
