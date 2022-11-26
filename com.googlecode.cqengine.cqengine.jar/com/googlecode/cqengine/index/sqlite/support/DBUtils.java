package com.googlecode.cqengine.index.sqlite.support;

import com.googlecode.concurrenttrees.common.CharSequences;
import java.io.Closeable;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

public class DBUtils {
   public static Closeable wrapAsCloseable(final ResultSet resultSet) {
      return new Closeable() {
         public void close() throws IOException {
            DBUtils.closeQuietly(resultSet);
         }
      };
   }

   public static boolean setAutoCommit(Connection connection, boolean value) {
      try {
         boolean previousValue = connection.getAutoCommit();
         connection.setAutoCommit(value);
         return previousValue;
      } catch (Exception var3) {
         throw new IllegalStateException("Unable to set the Connection autoCommit to " + value, var3);
      }
   }

   public static void commit(Connection connection) {
      try {
         connection.commit();
      } catch (Exception var2) {
         throw new IllegalStateException("Commit failed", var2);
      }
   }

   public static boolean rollback(Connection connection) {
      try {
         connection.rollback();
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   public static void closeQuietly(ResultSet resultSet) {
      if (resultSet != null) {
         try {
            Statement statement = resultSet.getStatement();
            if (statement != null) {
               statement.close();
            }
         } catch (Exception var3) {
         }

         try {
            resultSet.close();
         } catch (Exception var2) {
         }

      }
   }

   public static void closeQuietly(Statement statement) {
      if (statement != null) {
         try {
            statement.close();
         } catch (Exception var2) {
         }

      }
   }

   public static void closeQuietly(Connection connection) {
      if (connection != null) {
         try {
            connection.close();
         } catch (Exception var2) {
         }

      }
   }

   public static String getDBTypeForClass(Class valueType) {
      if (!CharSequence.class.isAssignableFrom(valueType) && !BigDecimal.class.isAssignableFrom(valueType)) {
         if (!Long.class.isAssignableFrom(valueType) && !Integer.class.isAssignableFrom(valueType) && !Short.class.isAssignableFrom(valueType) && !Boolean.class.isAssignableFrom(valueType) && !Date.class.isAssignableFrom(valueType)) {
            if (!Float.class.isAssignableFrom(valueType) && !Double.class.isAssignableFrom(valueType)) {
               if (valueType == byte[].class) {
                  return "BLOB";
               } else {
                  throw new IllegalStateException("Type " + valueType + " not supported.");
               }
            } else {
               return "REAL";
            }
         } else {
            return "INTEGER";
         }
      } else {
         return "TEXT";
      }
   }

   public static void setValueToPreparedStatement(int index, PreparedStatement preparedStatement, Object value) throws SQLException {
      if (value instanceof Date) {
         preparedStatement.setLong(index, ((Date)value).getTime());
      } else if (value instanceof CharSequence) {
         preparedStatement.setString(index, CharSequences.toString((CharSequence)value));
      } else {
         preparedStatement.setObject(index, value);
      }

   }

   public static int setValuesToPreparedStatement(int startIndex, PreparedStatement preparedStatement, Iterable values) throws SQLException {
      int index = startIndex;
      Iterator var4 = values.iterator();

      while(var4.hasNext()) {
         Object value = var4.next();
         setValueToPreparedStatement(index++, preparedStatement, value);
      }

      return index;
   }

   public static Object getValueFromResultSet(int index, ResultSet resultSet, Class type) {
      try {
         long time;
         if (java.sql.Date.class.isAssignableFrom(type)) {
            time = resultSet.getLong(index);
            return new java.sql.Date(time);
         } else if (Time.class.isAssignableFrom(type)) {
            time = resultSet.getLong(index);
            return new Time(time);
         } else if (Timestamp.class.isAssignableFrom(type)) {
            time = resultSet.getLong(index);
            return new Timestamp(time);
         } else if (Date.class.isAssignableFrom(type)) {
            time = resultSet.getLong(index);
            return new Date(time);
         } else if (Long.class.isAssignableFrom(type)) {
            return resultSet.getLong(index);
         } else if (Integer.class.isAssignableFrom(type)) {
            return resultSet.getInt(index);
         } else if (Short.class.isAssignableFrom(type)) {
            return resultSet.getShort(index);
         } else if (Float.class.isAssignableFrom(type)) {
            return resultSet.getFloat(index);
         } else if (Double.class.isAssignableFrom(type)) {
            return resultSet.getDouble(index);
         } else if (Boolean.class.isAssignableFrom(type)) {
            return resultSet.getBoolean(index);
         } else if (BigDecimal.class.isAssignableFrom(type)) {
            return resultSet.getBigDecimal(index);
         } else if (CharSequence.class.isAssignableFrom(type)) {
            return resultSet.getString(index);
         } else if (byte[].class.isAssignableFrom(type)) {
            return resultSet.getBytes(index);
         } else {
            throw new IllegalStateException("Type " + type + " not supported.");
         }
      } catch (Exception var5) {
         throw new IllegalStateException("Unable to read the value from the resultSet. Index:" + index + ", type: " + type, var5);
      }
   }

   public static String sanitizeForTableName(String input) {
      return input.replaceAll("[^A-Za-z0-9]", "");
   }
}
