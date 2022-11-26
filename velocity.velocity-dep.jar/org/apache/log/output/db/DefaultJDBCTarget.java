package org.apache.log.output.db;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.sql.DataSource;
import org.apache.log.ContextMap;
import org.apache.log.LogEvent;

public class DefaultJDBCTarget extends AbstractJDBCTarget {
   private final String m_table;
   private final ColumnInfo[] m_columns;
   private PreparedStatement m_statement;

   public DefaultJDBCTarget(DataSource dataSource, String table, ColumnInfo[] columns) {
      super(dataSource);
      this.m_table = table;
      this.m_columns = columns;
      if (null == table) {
         throw new NullPointerException("table property must not be null");
      } else if (null == columns) {
         throw new NullPointerException("columns property must not be null");
      } else if (0 == columns.length) {
         throw new NullPointerException("columns must have at least 1 element");
      } else {
         this.open();
      }
   }

   protected synchronized void output(LogEvent event) {
      try {
         for(int i = 0; i < this.m_columns.length; ++i) {
            this.specifyColumn(this.m_statement, i, event);
         }

         this.m_statement.executeUpdate();
      } catch (SQLException var3) {
         this.getErrorHandler().error("Error executing statement", var3, event);
      }

   }

   protected synchronized void openConnection() {
      super.openConnection();
      this.m_statement = null;

      try {
         Connection connection = this.getConnection();
         if (null != connection) {
            this.m_statement = connection.prepareStatement(this.getStatementSQL());
         }
      } catch (SQLException var2) {
         this.getErrorHandler().error("Error preparing statement", var2, (LogEvent)null);
      }

   }

   protected String getStatementSQL() {
      StringBuffer sb = new StringBuffer("INSERT INTO ");
      sb.append(this.m_table);
      sb.append(" (");
      sb.append(this.m_columns[0].getName());

      for(int i = 1; i < this.m_columns.length; ++i) {
         sb.append(", ");
         sb.append(this.m_columns[i].getName());
      }

      sb.append(") VALUES (?");

      for(int i = 1; i < this.m_columns.length; ++i) {
         sb.append(", ?");
      }

      sb.append(")");
      return sb.toString();
   }

   protected boolean isStale() {
      return super.isStale();
   }

   protected synchronized void closeConnection() {
      super.closeConnection();
      if (null != this.m_statement) {
         try {
            this.m_statement.close();
         } catch (SQLException var2) {
            this.getErrorHandler().error("Error closing statement", var2, (LogEvent)null);
         }

         this.m_statement = null;
      }

   }

   protected void specifyColumn(PreparedStatement statement, int index, LogEvent event) throws SQLException {
      ColumnInfo info = this.m_columns[index];
      switch (info.getType()) {
         case 1:
            statement.setString(index + 1, info.getAux());
            break;
         case 2:
            statement.setString(index + 1, event.getCategory());
            break;
         case 3:
            statement.setString(index + 1, this.getContextMap(event.getContextMap(), info.getAux()));
            break;
         case 4:
            statement.setString(index + 1, event.getMessage());
            break;
         case 5:
            statement.setTimestamp(index + 1, new Timestamp(event.getTime()));
            break;
         case 6:
            statement.setLong(index + 1, event.getRelativeTime());
            break;
         case 7:
            statement.setString(index + 1, this.getStackTrace(event.getThrowable()));
            break;
         case 8:
            statement.setString(index + 1, event.getPriority().getName());
            break;
         default:
            throw new IllegalStateException("Unknown ColumnType: " + info.getType());
      }

   }

   protected final String getTable() {
      return this.m_table;
   }

   protected final ColumnInfo getColumn(int index) {
      return this.m_columns[index];
   }

   private String getStackTrace(Throwable throwable) {
      if (null == throwable) {
         return "";
      } else {
         StringWriter sw = new StringWriter();
         throwable.printStackTrace(new PrintWriter(sw));
         return sw.toString();
      }
   }

   private String getContextMap(ContextMap map, String aux) {
      return null == map ? "" : map.get(aux, "").toString();
   }
}
