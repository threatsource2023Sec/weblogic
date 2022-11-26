package weblogic.jdbc.rmi;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class SerialResultSetMetaData implements ResultSetMetaData, Serializable {
   private static final long serialVersionUID = -6370560484041523204L;
   private ResultSetMetaData rmi_rsmd;

   public SerialResultSetMetaData() {
      this((ResultSetMetaData)null);
   }

   public SerialResultSetMetaData(ResultSetMetaData rsmd) {
      this.rmi_rsmd = null;
      this.rmi_rsmd = rsmd;
   }

   public int getColumnCount() throws SQLException {
      try {
         return this.rmi_rsmd.getColumnCount();
      } catch (Exception var2) {
         if (var2 instanceof SQLException) {
            throw (SQLException)var2;
         } else {
            throw new SQLException(var2.toString());
         }
      }
   }

   public boolean isAutoIncrement(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isAutoIncrement(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isCaseSensitive(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isCaseSensitive(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isSearchable(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isSearchable(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isCurrency(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isCurrency(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public int isNullable(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isNullable(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isSigned(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isSigned(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public int getColumnDisplaySize(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnDisplaySize(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getColumnLabel(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnLabel(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getColumnName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnName(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getSchemaName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getSchemaName(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public int getPrecision(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getPrecision(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public int getScale(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getScale(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getTableName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getTableName(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getCatalogName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getCatalogName(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public int getColumnType(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnType(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getColumnTypeName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnTypeName(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isReadOnly(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isReadOnly(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isWritable(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isWritable(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public boolean isDefinitelyWritable(int column) throws SQLException {
      try {
         return this.rmi_rsmd.isDefinitelyWritable(column);
      } catch (Exception var3) {
         if (var3 instanceof SQLException) {
            throw (SQLException)var3;
         } else {
            throw new SQLException(var3.toString());
         }
      }
   }

   public String getColumnClassName(int column) throws SQLException {
      try {
         return this.rmi_rsmd.getColumnClassName(column);
      } catch (Exception var3) {
         throw new SQLException(var3.toString());
      }
   }

   public Object unwrap(Class iface) throws SQLException {
      if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }
}
