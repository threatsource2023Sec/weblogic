package org.apache.openjpa.lib.jdbc;

public interface JDBCListener {
   void beforePrepareStatement(JDBCEvent var1);

   void afterPrepareStatement(JDBCEvent var1);

   void beforeCreateStatement(JDBCEvent var1);

   void afterCreateStatement(JDBCEvent var1);

   void beforeExecuteStatement(JDBCEvent var1);

   void afterExecuteStatement(JDBCEvent var1);

   void beforeCommit(JDBCEvent var1);

   void afterCommit(JDBCEvent var1);

   void beforeRollback(JDBCEvent var1);

   void afterRollback(JDBCEvent var1);

   void afterConnect(JDBCEvent var1);

   void beforeClose(JDBCEvent var1);
}
