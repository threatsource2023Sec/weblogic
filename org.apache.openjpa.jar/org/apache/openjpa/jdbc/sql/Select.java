package org.apache.openjpa.jdbc.sql;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;

public interface Select extends SelectExecutor {
   int EAGER_INNER = 0;
   int EAGER_OUTER = 1;
   int EAGER_PARALLEL = 2;
   int TYPE_JOINLESS = 3;
   int TYPE_TWO_PART = 4;
   int SUBS_JOINABLE = 1;
   int SUBS_NONE = 2;
   int SUBS_ANY_JOINABLE = 3;
   int SUBS_EXACT = 4;
   String FROM_SELECT_ALIAS = "s";

   int indexOf();

   List getSubselects();

   Select getParent();

   String getSubselectPath();

   void setParent(Select var1, String var2);

   Select getFromSelect();

   void setFromSelect(Select var1);

   boolean hasEagerJoin(boolean var1);

   boolean hasJoin(boolean var1);

   boolean isSelected(Table var1);

   Collection getTableAliases();

   List getSelects();

   List getSelectAliases();

   List getIdentifierAliases();

   SQLBuffer getOrdering();

   SQLBuffer getGrouping();

   SQLBuffer getWhere();

   SQLBuffer getHaving();

   void addJoinClassConditions();

   Joins getJoins();

   Iterator getJoinIterator();

   long getStartIndex();

   long getEndIndex();

   void setRange(long var1, long var3);

   String getColumnAlias(Column var1);

   String getColumnAlias(Column var1, Joins var2);

   String getColumnAlias(String var1, Table var2);

   String getColumnAlias(String var1, Table var2, Joins var3);

   boolean isAggregate();

   void setAggregate(boolean var1);

   boolean isLob();

   void setLob(boolean var1);

   void clearSelects();

   void selectPlaceholder(String var1);

   boolean select(SQLBuffer var1, Object var2);

   boolean select(SQLBuffer var1, Object var2, Joins var3);

   boolean select(String var1, Object var2);

   boolean select(String var1, Object var2, Joins var3);

   boolean select(Column var1);

   boolean select(Column var1, Joins var2);

   int select(Column[] var1);

   int select(Column[] var1, Joins var2);

   void select(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5);

   void select(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5, Joins var6);

   boolean selectIdentifier(Column var1);

   boolean selectIdentifier(Column var1, Joins var2);

   int selectIdentifier(Column[] var1);

   int selectIdentifier(Column[] var1, Joins var2);

   void selectIdentifier(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5);

   void selectIdentifier(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4, int var5, Joins var6);

   int selectPrimaryKey(ClassMapping var1);

   int selectPrimaryKey(ClassMapping var1, Joins var2);

   void clearOrdering();

   int orderByPrimaryKey(ClassMapping var1, boolean var2, boolean var3);

   int orderByPrimaryKey(ClassMapping var1, boolean var2, Joins var3, boolean var4);

   boolean orderBy(Column var1, boolean var2, boolean var3);

   boolean orderBy(Column var1, boolean var2, Joins var3, boolean var4);

   int orderBy(Column[] var1, boolean var2, boolean var3);

   int orderBy(Column[] var1, boolean var2, Joins var3, boolean var4);

   boolean orderBy(SQLBuffer var1, boolean var2, boolean var3);

   boolean orderBy(SQLBuffer var1, boolean var2, Joins var3, boolean var4);

   boolean orderBy(String var1, boolean var2, boolean var3);

   boolean orderBy(String var1, boolean var2, Joins var3, boolean var4);

   void wherePrimaryKey(Object var1, ClassMapping var2, JDBCStore var3);

   void whereForeignKey(ForeignKey var1, Object var2, ClassMapping var3, JDBCStore var4);

   void where(Joins var1);

   void where(SQLBuffer var1);

   void where(SQLBuffer var1, Joins var2);

   void where(String var1);

   void where(String var1, Joins var2);

   void having(SQLBuffer var1);

   void having(SQLBuffer var1, Joins var2);

   void having(String var1);

   void having(String var1, Joins var2);

   void groupBy(Column var1);

   void groupBy(Column var1, Joins var2);

   void groupBy(Column[] var1);

   void groupBy(Column[] var1, Joins var2);

   void groupBy(SQLBuffer var1);

   void groupBy(SQLBuffer var1, Joins var2);

   void groupBy(String var1);

   void groupBy(String var1, Joins var2);

   void groupBy(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4);

   void groupBy(ClassMapping var1, int var2, JDBCStore var3, JDBCFetchConfiguration var4, Joins var5);

   SelectExecutor whereClone(int var1);

   SelectExecutor fullClone(int var1);

   SelectExecutor eagerClone(FieldMapping var1, int var2, boolean var3, int var4);

   SelectExecutor getEager(FieldMapping var1);

   Joins newJoins();

   Joins newOuterJoins();

   void append(SQLBuffer var1, Joins var2);

   Joins and(Joins var1, Joins var2);

   Joins or(Joins var1, Joins var2);

   Joins outer(Joins var1);

   String toString();
}
