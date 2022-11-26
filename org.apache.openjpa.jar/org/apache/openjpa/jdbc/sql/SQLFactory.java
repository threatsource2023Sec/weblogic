package org.apache.openjpa.jdbc.sql;

public interface SQLFactory {
   Select newSelect();

   Union newUnion(int var1);

   Union newUnion(Select[] var1);
}
