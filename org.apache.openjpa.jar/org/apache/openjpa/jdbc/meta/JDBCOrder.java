package org.apache.openjpa.jdbc.meta;

import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.meta.Order;

interface JDBCOrder extends Order {
   boolean isInRelation();

   void order(Select var1, ClassMapping var2, Joins var3);
}
