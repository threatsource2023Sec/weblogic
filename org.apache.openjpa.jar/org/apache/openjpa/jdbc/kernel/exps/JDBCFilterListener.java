package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.exps.FilterListener;

public interface JDBCFilterListener extends FilterListener {
   void appendTo(SQLBuffer var1, FilterValue var2, FilterValue[] var3, ClassMapping var4, JDBCStore var5);
}
