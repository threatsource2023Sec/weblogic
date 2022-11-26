package org.apache.openjpa.jdbc.meta;

import java.io.Serializable;
import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface Strategy extends Serializable {
   String getAlias();

   void map(boolean var1);

   void initialize();

   void insert(OpenJPAStateManager var1, JDBCStore var2, RowManager var3) throws SQLException;

   void update(OpenJPAStateManager var1, JDBCStore var2, RowManager var3) throws SQLException;

   void delete(OpenJPAStateManager var1, JDBCStore var2, RowManager var3) throws SQLException;

   Boolean isCustomInsert(OpenJPAStateManager var1, JDBCStore var2);

   Boolean isCustomUpdate(OpenJPAStateManager var1, JDBCStore var2);

   Boolean isCustomDelete(OpenJPAStateManager var1, JDBCStore var2);

   void customInsert(OpenJPAStateManager var1, JDBCStore var2) throws SQLException;

   void customUpdate(OpenJPAStateManager var1, JDBCStore var2) throws SQLException;

   void customDelete(OpenJPAStateManager var1, JDBCStore var2) throws SQLException;
}
