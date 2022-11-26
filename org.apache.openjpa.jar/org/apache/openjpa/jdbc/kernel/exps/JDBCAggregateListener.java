package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.kernel.exps.AggregateListener;

public interface JDBCAggregateListener extends AggregateListener {
   void appendTo(SQLBuffer var1, FilterValue[] var2, ClassMapping var3, JDBCStore var4);
}
