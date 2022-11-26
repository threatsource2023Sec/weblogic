package weblogic.jdbc.rmi;

import java.sql.SQLException;

public interface RmiStatement {
   int getRmiFetchSize() throws SQLException;

   void setRmiFetchSize(int var1) throws SQLException;
}
