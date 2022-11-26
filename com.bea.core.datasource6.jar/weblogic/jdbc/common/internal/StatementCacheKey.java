package weblogic.jdbc.common.internal;

import java.util.Arrays;
import weblogic.jdbc.wrapper.Statement;

public final class StatementCacheKey {
   private final boolean isCallable;
   private final String sql;
   private final int resultSetType;
   private final int resultSetConcurrency;
   private final int resultSetHoldability;
   private final int autoGeneratedKeys;
   private int[] columnIndexes;
   private String[] columnNames;
   private final int hashCode;

   public int hashCode() {
      return this.hashCode;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else {
         if (other instanceof StatementCacheKey) {
            StatementCacheKey that = (StatementCacheKey)other;
            if (that.isCallable == this.isCallable && (that.sql == this.sql || this.sql != null && this.sql.equals(that.sql)) && that.resultSetType == this.resultSetType && that.resultSetConcurrency == this.resultSetConcurrency && that.resultSetHoldability == this.resultSetHoldability && that.autoGeneratedKeys == this.autoGeneratedKeys && Arrays.equals(this.columnIndexes, that.columnIndexes) && Arrays.equals(this.columnNames, that.columnNames)) {
               return true;
            }
         }

         return false;
      }
   }

   public StatementCacheKey(boolean isCallable, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability, int autoGeneratedKeys, int[] columnIndexes, String[] columnNames) {
      this.columnIndexes = null;
      this.columnNames = null;
      this.isCallable = isCallable;
      this.sql = sql;
      this.resultSetType = resultSetType;
      this.resultSetConcurrency = resultSetConcurrency;
      this.resultSetHoldability = resultSetHoldability;
      this.autoGeneratedKeys = autoGeneratedKeys;
      if (columnIndexes != null) {
         this.columnIndexes = new int[columnIndexes.length];
         System.arraycopy(columnIndexes, 0, this.columnIndexes, 0, columnIndexes.length);
      }

      if (columnNames != null) {
         this.columnNames = new String[columnNames.length];
         System.arraycopy(columnNames, 0, this.columnNames, 0, columnNames.length);
      }

      this.hashCode = this.calculateHashCode();
   }

   public StatementCacheKey(Statement statement) {
      this(statement.isCallable, statement.sql, statement.resultSetType, statement.resultSetConcurrency, statement.resultSetHoldability, statement.autoGeneratedKeys, statement.columnIndexes, statement.columnNames);
   }

   public StatementCacheKey(boolean isCallable, String sql, int resultSetType, int resultSetConcurrency) {
      this(isCallable, sql, resultSetType, resultSetConcurrency, -1, -1, (int[])null, (String[])null);
   }

   public String getSQL() {
      return this.sql;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.isCallable);
      sb.append(":");
      sb.append(this.sql);
      sb.append(":");
      sb.append(this.resultSetType);
      sb.append(":");
      sb.append(this.resultSetConcurrency);
      sb.append(":");
      sb.append(this.resultSetHoldability);
      sb.append(":");
      sb.append(this.autoGeneratedKeys);
      sb.append(":");
      sb.append(Arrays.toString(this.columnIndexes));
      sb.append(":");
      sb.append(Arrays.toString(this.columnNames));
      return sb.toString();
   }

   private int calculateHashCode() {
      int result = 17;
      result = 37 * result + (this.isCallable ? 0 : 1);
      result = 37 * result + (this.sql == null ? 0 : this.sql.hashCode());
      result = 37 * result + this.resultSetType;
      result = 37 * result + this.resultSetConcurrency;
      result = 37 * result + this.resultSetHoldability;
      result = 37 * result + this.autoGeneratedKeys;
      result = 37 * result + Arrays.hashCode(this.columnIndexes);
      result = 37 * result + Arrays.hashCode(this.columnNames);
      return result;
   }
}