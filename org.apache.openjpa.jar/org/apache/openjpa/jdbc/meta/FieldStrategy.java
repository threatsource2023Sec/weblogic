package org.apache.openjpa.jdbc.meta;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.RowManager;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.SelectExecutor;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface FieldStrategy extends Strategy {
   void setFieldMapping(FieldMapping var1);

   int supportsSelect(Select var1, int var2, OpenJPAStateManager var3, JDBCStore var4, JDBCFetchConfiguration var5);

   void selectEagerParallel(SelectExecutor var1, OpenJPAStateManager var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5);

   void selectEagerJoin(Select var1, OpenJPAStateManager var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5);

   boolean isEagerSelectToMany();

   int select(Select var1, OpenJPAStateManager var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5);

   Object loadEagerParallel(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Object var4) throws SQLException;

   void loadEagerJoin(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4) throws SQLException;

   void load(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4) throws SQLException;

   void load(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3) throws SQLException;

   Object toDataStoreValue(Object var1, JDBCStore var2);

   Object toKeyDataStoreValue(Object var1, JDBCStore var2);

   void appendIsEmpty(SQLBuffer var1, Select var2, Joins var3);

   void appendIsNotEmpty(SQLBuffer var1, Select var2, Joins var3);

   void appendIsNull(SQLBuffer var1, Select var2, Joins var3);

   void appendIsNotNull(SQLBuffer var1, Select var2, Joins var3);

   void appendSize(SQLBuffer var1, Select var2, Joins var3);

   Joins join(Joins var1, boolean var2);

   Joins joinKey(Joins var1, boolean var2);

   Joins joinRelation(Joins var1, boolean var2, boolean var3);

   Joins joinKeyRelation(Joins var1, boolean var2, boolean var3);

   Object loadProjection(JDBCStore var1, JDBCFetchConfiguration var2, Result var3, Joins var4) throws SQLException;

   Object loadKeyProjection(JDBCStore var1, JDBCFetchConfiguration var2, Result var3, Joins var4) throws SQLException;

   boolean isVersionable();

   void where(OpenJPAStateManager var1, JDBCStore var2, RowManager var3, Object var4) throws SQLException;
}
