package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.FieldStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public interface LRSMapFieldStrategy extends FieldStrategy {
   FieldMapping getFieldMapping();

   ClassMapping[] getIndependentKeyMappings(boolean var1);

   ClassMapping[] getIndependentValueMappings(boolean var1);

   ForeignKey getJoinForeignKey(ClassMapping var1);

   Column[] getKeyColumns(ClassMapping var1);

   Column[] getValueColumns(ClassMapping var1);

   void selectKey(Select var1, ClassMapping var2, OpenJPAStateManager var3, JDBCStore var4, JDBCFetchConfiguration var5, Joins var6);

   Object loadKey(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4, Joins var5) throws SQLException;

   Object deriveKey(JDBCStore var1, Object var2);

   Object deriveValue(JDBCStore var1, Object var2);

   void selectValue(Select var1, ClassMapping var2, OpenJPAStateManager var3, JDBCStore var4, JDBCFetchConfiguration var5, Joins var6);

   Object loadValue(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4, Joins var5) throws SQLException;

   Result[] getResults(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, int var4, Joins[] var5, boolean var6) throws SQLException;

   Joins joinKeyRelation(Joins var1, ClassMapping var2);

   Joins joinValueRelation(Joins var1, ClassMapping var2);
}
