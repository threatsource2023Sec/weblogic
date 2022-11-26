package org.apache.openjpa.jdbc.sql;

import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;

public interface Joins {
   boolean isEmpty();

   boolean isOuter();

   Joins crossJoin(Table var1, Table var2);

   Joins join(ForeignKey var1, boolean var2, boolean var3);

   Joins outerJoin(ForeignKey var1, boolean var2, boolean var3);

   Joins joinRelation(String var1, ForeignKey var2, ClassMapping var3, int var4, boolean var5, boolean var6);

   Joins outerJoinRelation(String var1, ForeignKey var2, ClassMapping var3, int var4, boolean var5, boolean var6);

   Joins setVariable(String var1);

   Joins setSubselect(String var1);
}
