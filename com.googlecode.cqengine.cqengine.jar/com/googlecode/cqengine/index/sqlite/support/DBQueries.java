package com.googlecode.cqengine.index.sqlite.support;

import com.googlecode.concurrenttrees.common.CharSequences;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.simple.Between;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.GreaterThan;
import com.googlecode.cqengine.query.simple.Has;
import com.googlecode.cqengine.query.simple.In;
import com.googlecode.cqengine.query.simple.LessThan;
import com.googlecode.cqengine.query.simple.StringStartsWith;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.SynchronousMode;

public class DBQueries {
   public static void createIndexTable(String tableName, Class objectKeyClass, Class valueClass, Connection connection) {
      String objectKeySQLiteType = DBUtils.getDBTypeForClass(objectKeyClass);
      String objectValueSQLiteType = DBUtils.getDBTypeForClass(valueClass);
      String sqlCreateTable = String.format("CREATE TABLE IF NOT EXISTS cqtbl_%s (objectKey %s, value %s, PRIMARY KEY (objectKey, value)) WITHOUT ROWID;", tableName, objectKeySQLiteType, objectValueSQLiteType);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.executeUpdate(sqlCreateTable);
      } catch (SQLException var12) {
         throw new IllegalStateException("Unable to create index table: " + tableName, var12);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static boolean indexTableExists(String tableName, Connection connection) {
      String selectSql = String.format("SELECT 1 FROM sqlite_master WHERE type='table' AND name='cqtbl_%s';", tableName);
      Statement statement = null;

      boolean var5;
      try {
         statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(selectSql);
         var5 = resultSet.next();
      } catch (Exception var9) {
         throw new IllegalStateException("Unable to determine if table exists: " + tableName, var9);
      } finally {
         DBUtils.closeQuietly(statement);
      }

      return var5;
   }

   public static void createIndexOnTable(String tableName, Connection connection) {
      String sqlCreateIndex = String.format("CREATE INDEX IF NOT EXISTS cqidx_%s_value ON cqtbl_%s (value);", tableName, tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.executeUpdate(sqlCreateIndex);
      } catch (SQLException var8) {
         throw new IllegalStateException("Unable to add index on table: " + tableName, var8);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static void suspendSyncAndJournaling(Connection connection) {
      setSyncAndJournaling(connection, SynchronousMode.OFF, JournalMode.OFF);
   }

   public static void setSyncAndJournaling(Connection connection, SQLiteConfig.SynchronousMode pragmaSynchronous, SQLiteConfig.JournalMode pragmaJournalMode) {
      Statement statement = null;

      try {
         boolean autoCommit = DBUtils.setAutoCommit(connection, true);
         statement = connection.createStatement();
         statement.execute("PRAGMA synchronous = " + pragmaSynchronous.getValue());
         statement.execute("PRAGMA journal_mode = " + pragmaJournalMode.getValue());
         DBUtils.setAutoCommit(connection, autoCommit);
      } catch (SQLException var8) {
         throw new IllegalStateException("Unable to set the 'synchronous' and 'journal_mode' pragmas", var8);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static SQLiteConfig.SynchronousMode getPragmaSynchronousOrNull(Connection connection) {
      Statement statement = null;

      Object var3;
      try {
         statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("PRAGMA synchronous;");
         if (resultSet.next()) {
            int syncPragmaId = resultSet.getInt(1);
            if (!resultSet.wasNull()) {
               SQLiteConfig.SynchronousMode var4;
               switch (syncPragmaId) {
                  case 0:
                     var4 = SynchronousMode.OFF;
                     return var4;
                  case 1:
                     var4 = SynchronousMode.NORMAL;
                     return var4;
                  case 2:
                     var4 = SynchronousMode.FULL;
                     return var4;
                  default:
                     var4 = null;
                     return var4;
               }
            }
         }

         var3 = null;
         return (SQLiteConfig.SynchronousMode)var3;
      } catch (Exception var8) {
         var3 = null;
      } finally {
         DBUtils.closeQuietly(statement);
      }

      return (SQLiteConfig.SynchronousMode)var3;
   }

   public static SQLiteConfig.JournalMode getPragmaJournalModeOrNull(Connection connection) {
      Statement statement = null;

      String journalMode;
      try {
         statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery("PRAGMA journal_mode;");
         if (resultSet.next()) {
            journalMode = resultSet.getString(1);
            SQLiteConfig.JournalMode var4 = journalMode != null ? JournalMode.valueOf(journalMode.toUpperCase()) : null;
            return var4;
         }

         journalMode = null;
      } catch (Exception var8) {
         journalMode = null;
         return journalMode;
      } finally {
         DBUtils.closeQuietly(statement);
      }

      return journalMode;
   }

   public static void dropIndexOnTable(String tableName, Connection connection) {
      String sqlDropIndex = String.format("DROP INDEX IF EXISTS cqidx_%s_value;", tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.executeUpdate(sqlDropIndex);
      } catch (SQLException var8) {
         throw new IllegalStateException("Unable to drop index on table: " + tableName, var8);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static void dropIndexTable(String tableName, Connection connection) {
      String sqlDropIndex = String.format("DROP INDEX IF EXISTS cqidx_%s_value;", tableName);
      String sqlDropTable = String.format("DROP TABLE IF EXISTS cqtbl_%s;", tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.executeUpdate(sqlDropIndex);
         statement.executeUpdate(sqlDropTable);
      } catch (SQLException var9) {
         throw new IllegalStateException("Unable to drop index table: " + tableName, var9);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static void clearIndexTable(String tableName, Connection connection) {
      String clearTable = String.format("DELETE FROM cqtbl_%s;", tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.executeUpdate(clearTable);
      } catch (SQLException var8) {
         throw new IllegalStateException("Unable to clear index table: " + tableName, var8);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static void compactDatabase(Connection connection) {
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.execute("VACUUM;");
      } catch (SQLException var6) {
         throw new IllegalStateException("Unable to compact database", var6);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static void expandDatabase(Connection connection, long numBytes) {
      Statement statement = null;

      try {
         statement = connection.createStatement();
         statement.execute("CREATE TABLE IF NOT EXISTS cq_expansion (val);");
         statement.execute("INSERT INTO cq_expansion VALUES (zeroblob(" + numBytes + "));");
         statement.execute("DROP TABLE cq_expansion;");
      } catch (SQLException var8) {
         throw new IllegalStateException("Unable to expand database by bytes: " + numBytes, var8);
      } finally {
         DBUtils.closeQuietly(statement);
      }

   }

   public static long getDatabaseSize(Connection connection) {
      long pageCount = readPragmaLong(connection, "PRAGMA page_count;");
      long pageSize = readPragmaLong(connection, "PRAGMA page_size;");
      return pageCount * pageSize;
   }

   static long readPragmaLong(Connection connection, String query) {
      Statement statement = null;

      long var4;
      try {
         statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(query);
         if (!resultSet.next()) {
            throw new IllegalStateException("Unable to read long from pragma query. The ResultSet returned no row. Query: " + query);
         }

         var4 = resultSet.getLong(1);
      } catch (SQLException var9) {
         throw new IllegalStateException("Unable to read long from pragma query", var9);
      } finally {
         DBUtils.closeQuietly(statement);
      }

      return var4;
   }

   public static int bulkAdd(Iterable rows, String tableName, Connection connection) {
      String sql = String.format("INSERT OR IGNORE INTO cqtbl_%s values(?, ?);", tableName);
      PreparedStatement statement = null;
      int totalRowsModified = 0;

      try {
         boolean rolledBack;
         try {
            statement = connection.prepareStatement(sql);
            Iterator var6 = rows.iterator();

            while(var6.hasNext()) {
               Row row = (Row)var6.next();
               statement.setObject(1, row.getObjectKey());
               statement.setObject(2, row.getValue());
               statement.addBatch();
            }

            int[] rowsModified = statement.executeBatch();
            int[] var20 = rowsModified;
            int var22 = rowsModified.length;

            for(int var9 = 0; var9 < var22; ++var9) {
               int m = var20[var9];
               ensureNotNegative(m);
               totalRowsModified += m;
            }

            int var21 = totalRowsModified;
            return var21;
         } catch (NullPointerException var15) {
            rolledBack = DBUtils.rollback(connection);
            NullPointerException npe = new NullPointerException("Unable to bulk add rows containing a null object to the index table: " + tableName + ". Rolled back: " + rolledBack);
            npe.initCause(var15);
            throw npe;
         } catch (Exception var16) {
            rolledBack = DBUtils.rollback(connection);
            throw new IllegalStateException("Unable to bulk add rows to the index table: " + tableName + ". Rolled back: " + rolledBack, var16);
         }
      } finally {
         DBUtils.closeQuietly((Statement)statement);
      }
   }

   public static int bulkRemove(Iterable objectKeys, String tableName, Connection connection) {
      String sql = String.format("DELETE FROM cqtbl_%s WHERE objectKey = ?;", tableName);
      PreparedStatement statement = null;
      Boolean previousAutocommit = null;
      int totalRowsModified = 0;

      try {
         boolean rolledBack;
         try {
            previousAutocommit = DBUtils.setAutoCommit(connection, false);
            statement = connection.prepareStatement(sql);
            Iterator var7 = objectKeys.iterator();

            while(var7.hasNext()) {
               Object objectKey = var7.next();
               statement.setObject(1, objectKey);
               statement.addBatch();
            }

            int[] rowsModified = statement.executeBatch();
            int[] var21 = rowsModified;
            int var23 = rowsModified.length;

            for(int var10 = 0; var10 < var23; ++var10) {
               int m = var21[var10];
               ensureNotNegative(m);
               totalRowsModified += m;
            }

            DBUtils.commit(connection);
            int var22 = totalRowsModified;
            return var22;
         } catch (NullPointerException var16) {
            rolledBack = DBUtils.rollback(connection);
            NullPointerException npe = new NullPointerException("Unable to bulk remove rows containing a null object from the index table: " + tableName + ". Rolled back: " + rolledBack);
            npe.initCause(var16);
            throw npe;
         } catch (Exception var17) {
            rolledBack = DBUtils.rollback(connection);
            throw new IllegalStateException("Unable to remove rows from the index table: " + tableName + ". Rolled back: " + rolledBack, var17);
         }
      } finally {
         DBUtils.closeQuietly((Statement)statement);
         if (previousAutocommit != null) {
            DBUtils.setAutoCommit(connection, previousAutocommit);
         }

      }
   }

   static PreparedStatement createAndBindSelectPreparedStatement(String selectPrefix, String groupingAndSorting, List additionalWhereClauses, Query query, Connection connection) throws SQLException {
      int bindingIndex = 1;
      StringBuilder stringBuilder = (new StringBuilder(selectPrefix)).append(' ');
      StringBuilder suffix = new StringBuilder();
      Class queryClass = query.getClass();
      PreparedStatement statement;
      Iterator iterator;
      WhereClause additionalWhereClause;
      if (queryClass == Has.class) {
         if (additionalWhereClauses.isEmpty()) {
            suffix.append(groupingAndSorting);
            suffix.append(';');
         } else {
            stringBuilder.append("WHERE ");
            iterator = additionalWhereClauses.iterator();

            while(iterator.hasNext()) {
               additionalWhereClause = (WhereClause)iterator.next();
               suffix.append(additionalWhereClause.whereClause);
               if (iterator.hasNext()) {
                  suffix.append(" AND ");
               }
            }

            suffix.append(groupingAndSorting);
            suffix.append(';');
         }

         stringBuilder.append(suffix);
         statement = connection.prepareStatement(stringBuilder.toString());
      } else {
         if (additionalWhereClauses.isEmpty()) {
            suffix.append(groupingAndSorting);
            suffix.append(';');
         } else {
            iterator = additionalWhereClauses.iterator();

            while(iterator.hasNext()) {
               additionalWhereClause = (WhereClause)iterator.next();
               suffix.append(" AND ").append(additionalWhereClause.whereClause);
            }

            suffix.append(groupingAndSorting);
            suffix.append(';');
         }

         if (queryClass == Equal.class) {
            Equal equal = (Equal)query;
            stringBuilder.append("WHERE value = ?").append(suffix);
            statement = connection.prepareStatement(stringBuilder.toString());
            DBUtils.setValueToPreparedStatement(bindingIndex++, statement, equal.getValue());
         } else {
            int i;
            if (queryClass == In.class) {
               In in = (In)query;
               Set values = in.getValues();
               stringBuilder.append("WHERE value IN ( ");

               for(i = 0; i < values.size(); ++i) {
                  if (i > 0) {
                     stringBuilder.append(", ");
                  }

                  stringBuilder.append("?");
               }

               stringBuilder.append(")").append(suffix);
               statement = connection.prepareStatement(stringBuilder.toString());
               bindingIndex = DBUtils.setValuesToPreparedStatement(bindingIndex, statement, values);
            } else {
               boolean isValueInclusive;
               if (queryClass == LessThan.class) {
                  LessThan lessThan = (LessThan)query;
                  isValueInclusive = lessThan.isValueInclusive();
                  if (isValueInclusive) {
                     stringBuilder.append("WHERE value <= ?").append(suffix);
                  } else {
                     stringBuilder.append("WHERE value < ?").append(suffix);
                  }

                  statement = connection.prepareStatement(stringBuilder.toString());
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, lessThan.getValue());
               } else if (queryClass == StringStartsWith.class) {
                  StringStartsWith stringStartsWith = (StringStartsWith)query;
                  stringBuilder.append("WHERE value >= ? AND value < ?").append(suffix);
                  String lowerBoundInclusive = CharSequences.toString(stringStartsWith.getValue());
                  i = lowerBoundInclusive.length();
                  String allButLast = lowerBoundInclusive.substring(0, i - 1);
                  String upperBoundExclusive = allButLast + Character.toChars(lowerBoundInclusive.charAt(i - 1) + 1)[0];
                  statement = connection.prepareStatement(stringBuilder.toString());
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, lowerBoundInclusive);
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, upperBoundExclusive);
               } else if (queryClass == GreaterThan.class) {
                  GreaterThan greaterThan = (GreaterThan)query;
                  isValueInclusive = greaterThan.isValueInclusive();
                  if (isValueInclusive) {
                     stringBuilder.append("WHERE value >= ?").append(suffix);
                  } else {
                     stringBuilder.append("WHERE value > ?").append(suffix);
                  }

                  statement = connection.prepareStatement(stringBuilder.toString());
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, greaterThan.getValue());
               } else {
                  if (queryClass != Between.class) {
                     throw new IllegalStateException("Query " + queryClass + " not supported.");
                  }

                  Between between = (Between)query;
                  if (between.isLowerInclusive()) {
                     stringBuilder.append("WHERE value >= ?");
                  } else {
                     stringBuilder.append("WHERE value > ?");
                  }

                  if (between.isUpperInclusive()) {
                     stringBuilder.append(" AND value <= ?");
                  } else {
                     stringBuilder.append(" AND value < ?");
                  }

                  stringBuilder.append(suffix);
                  statement = connection.prepareStatement(stringBuilder.toString());
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, between.getLowerValue());
                  DBUtils.setValueToPreparedStatement(bindingIndex++, statement, between.getUpperValue());
               }
            }
         }
      }

      iterator = additionalWhereClauses.iterator();

      while(iterator.hasNext()) {
         additionalWhereClause = (WhereClause)iterator.next();
         DBUtils.setValueToPreparedStatement(bindingIndex++, statement, additionalWhereClause.objectToBind);
      }

      return statement;
   }

   public static int count(Query query, String tableName, Connection connection) {
      String selectSql = String.format("SELECT COUNT(objectKey) FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      int var6;
      try {
         statement = createAndBindSelectPreparedStatement(selectSql, "", Collections.emptyList(), query, connection);
         ResultSet resultSet = statement.executeQuery();
         if (!resultSet.next()) {
            throw new IllegalStateException("Unable to execute count. The ResultSet returned no row. Query: " + query);
         }

         var6 = resultSet.getInt(1);
      } catch (Exception var10) {
         throw new IllegalStateException("Unable to execute count. Query: " + query, var10);
      } finally {
         DBUtils.closeQuietly((Statement)statement);
      }

      return var6;
   }

   public static int countDistinct(Query query, String tableName, Connection connection) {
      String selectSql = String.format("SELECT COUNT(1) AS countDistinct FROM (SELECT objectKey FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      int var6;
      try {
         statement = createAndBindSelectPreparedStatement(selectSql, " GROUP BY objectKey)", Collections.emptyList(), query, connection);
         ResultSet resultSet = statement.executeQuery();
         if (!resultSet.next()) {
            throw new IllegalStateException("Unable to execute count. The ResultSet returned no row. Query: " + query);
         }

         var6 = resultSet.getInt(1);
      } catch (Exception var10) {
         throw new IllegalStateException("Unable to execute count. Query: " + query, var10);
      } finally {
         DBUtils.closeQuietly((Statement)statement);
      }

      return var6;
   }

   public static ResultSet search(Query query, String tableName, Connection connection) {
      String selectSql = String.format("SELECT DISTINCT objectKey FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      try {
         statement = createAndBindSelectPreparedStatement(selectSql, "", Collections.emptyList(), query, connection);
         return statement.executeQuery();
      } catch (Exception var6) {
         DBUtils.closeQuietly((Statement)statement);
         throw new IllegalStateException("Unable to execute search. Query: " + query, var6);
      }
   }

   public static ResultSet getDistinctKeys(Query query, boolean descending, String tableName, Connection connection) {
      String selectSql = String.format("SELECT DISTINCT value FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      try {
         String orderByClause = descending ? " ORDER BY value DESC" : " ORDER BY value ASC";
         statement = createAndBindSelectPreparedStatement(selectSql, orderByClause, Collections.emptyList(), query, connection);
         return statement.executeQuery();
      } catch (Exception var7) {
         DBUtils.closeQuietly((Statement)statement);
         throw new IllegalStateException("Unable to look up keys. Query: " + query, var7);
      }
   }

   public static ResultSet getKeysAndValues(Query query, boolean descending, String tableName, Connection connection) {
      String selectSql = String.format("SELECT objectKey, value FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      try {
         String orderByClause = descending ? " ORDER BY value DESC" : " ORDER BY value ASC";
         statement = createAndBindSelectPreparedStatement(selectSql, orderByClause, Collections.emptyList(), query, connection);
         return statement.executeQuery();
      } catch (Exception var7) {
         DBUtils.closeQuietly((Statement)statement);
         throw new IllegalStateException("Unable to look up keys and values. Query: " + query, var7);
      }
   }

   public static int getCountOfDistinctKeys(String tableName, Connection connection) {
      String selectSql = String.format("SELECT COUNT(DISTINCT value) FROM cqtbl_%s", tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(selectSql);
         if (!resultSet.next()) {
            throw new IllegalStateException("Unable to execute count. The ResultSet returned no row. Query: " + selectSql);
         } else {
            return resultSet.getInt(1);
         }
      } catch (Exception var5) {
         DBUtils.closeQuietly(statement);
         throw new IllegalStateException("Unable to count distinct keys.", var5);
      }
   }

   public static ResultSet getDistinctKeysAndCounts(boolean sortByKeyDescending, String tableName, Connection connection) {
      String selectSql = String.format("SELECT DISTINCT value, COUNT(value) AS valueCount FROM cqtbl_%s GROUP BY (value) %s", tableName, sortByKeyDescending ? "ORDER BY value DESC" : "");
      Statement statement = null;

      try {
         statement = connection.createStatement();
         return statement.executeQuery(selectSql);
      } catch (Exception var6) {
         DBUtils.closeQuietly(statement);
         throw new IllegalStateException("Unable to look up index entries and counts.", var6);
      }
   }

   public static ResultSet getAllIndexEntries(String tableName, Connection connection) {
      String selectSql = String.format("SELECT objectKey, value FROM cqtbl_%s ORDER BY objectKey;", tableName);
      Statement statement = null;

      try {
         statement = connection.createStatement();
         return statement.executeQuery(selectSql);
      } catch (Exception var5) {
         DBUtils.closeQuietly(statement);
         throw new IllegalStateException("Unable to look up index entries.", var5);
      }
   }

   public static ResultSet getIndexEntryByObjectKey(Object key, String tableName, Connection connection) {
      String selectSql = String.format("SELECT objectKey, value FROM cqtbl_%s WHERE objectKey = ?", tableName);
      PreparedStatement statement = null;

      try {
         statement = connection.prepareStatement(selectSql);
         DBUtils.setValueToPreparedStatement(1, statement, key);
         return statement.executeQuery();
      } catch (Exception var6) {
         DBUtils.closeQuietly((Statement)statement);
         throw new IllegalStateException("Unable to look up index entries.", var6);
      }
   }

   public static boolean contains(Object objectKey, Query query, String tableName, Connection connection) {
      String selectSql = String.format("SELECT objectKey FROM cqtbl_%s", tableName);
      PreparedStatement statement = null;

      boolean var8;
      try {
         List additionalWhereClauses = Collections.singletonList(new WhereClause("objectKey = ?", objectKey));
         statement = createAndBindSelectPreparedStatement(selectSql, " LIMIT 1", additionalWhereClauses, query, connection);
         ResultSet resultSet = statement.executeQuery();
         var8 = resultSet.next();
      } catch (SQLException var12) {
         throw new IllegalStateException("Unable to execute contains. Query: " + query, var12);
      } finally {
         DBUtils.closeQuietly((Statement)statement);
      }

      return var8;
   }

   static void ensureNotNegative(int value) {
      if (value < 0) {
         throw new IllegalStateException("Update returned error code: " + value);
      }
   }

   static class WhereClause {
      final String whereClause;
      final Object objectToBind;

      WhereClause(String whereClause, Object objectToBind) {
         this.whereClause = whereClause;
         this.objectToBind = objectToBind;
      }
   }

   public static class Row {
      private final Object objectKey;
      private final Object value;

      public Row(Object objectKey, Object value) {
         this.objectKey = objectKey;
         this.value = value;
      }

      public Object getObjectKey() {
         return this.objectKey;
      }

      public Object getValue() {
         return this.value;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            Row row = (Row)o;
            if (!this.objectKey.equals(row.objectKey)) {
               return false;
            } else {
               return this.value.equals(row.value);
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.objectKey.hashCode();
         result = 31 * result + this.value.hashCode();
         return result;
      }
   }
}
