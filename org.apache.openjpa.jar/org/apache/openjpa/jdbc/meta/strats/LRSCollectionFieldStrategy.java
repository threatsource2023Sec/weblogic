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

public interface LRSCollectionFieldStrategy extends FieldStrategy {
   FieldMapping getFieldMapping();

   ClassMapping[] getIndependentElementMappings(boolean var1);

   ForeignKey getJoinForeignKey(ClassMapping var1);

   Column[] getElementColumns(ClassMapping var1);

   void selectElement(Select var1, ClassMapping var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5, Joins var6);

   Object loadElement(OpenJPAStateManager var1, JDBCStore var2, JDBCFetchConfiguration var3, Result var4, Joins var5) throws SQLException;

   Joins joinElementRelation(Joins var1, ClassMapping var2);
}
